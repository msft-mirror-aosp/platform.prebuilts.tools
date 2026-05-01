/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kotlincrewriter

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import org.objectweb.asm.*
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.SimpleRemapper

// --- Configuration for the transformation ---

// == Part 1: Type Name Mapping ==
val typeMappings =
  mapOf(
    "com/intellij/ide/plugins/RawPluginDescriptor" to "com/intellij/platform/pluginSystem/parser/impl/RawPluginDescriptor",
    "com/intellij/ide/plugins/ReadModuleContext" to "com/intellij/platform/pluginSystem/parser/impl/PluginDescriptorReaderContext",
  )

// == Part 2: Constructor to Builder Replacement ==
// These constants must use the NEW type names, as they run after the remapper.

private const val TARGET_CLASS_INTERNAL_NAME = "com/intellij/platform/pluginSystem/parser/impl/RawPluginDescriptor"
private const val TARGET_CONSTRUCTOR_DESCRIPTOR = "()V"
private const val BUILDER_CLASS_INTERNAL_NAME = "com/intellij/platform/pluginSystem/parser/impl/PluginDescriptorBuilder"

// -- New constants for the Companion Object --
// The name of the public static final field holding the companion instance.
private const val COMPANION_FIELD_NAME = "Companion"
// The internal name of the generated class for the companion object.
private const val COMPANION_OBJECT_CLASS_INTERNAL_NAME = "$BUILDER_CLASS_INTERNAL_NAME\$Companion"
// The descriptor for the companion object field.
private const val COMPANION_FIELD_DESCRIPTOR = "L$COMPANION_OBJECT_CLASS_INTERNAL_NAME;"

// -- Method constants --
private const val BUILDER_FACTORY_METHOD_NAME = "builder"
// The descriptor for `builder()` is the same, but it will be called via INVOKEVIRTUAL.
private const val BUILDER_FACTORY_METHOD_DESCRIPTOR = "()L$BUILDER_CLASS_INTERNAL_NAME;"
private const val BUILD_METHOD_NAME = "build"
private const val BUILD_METHOD_DESCRIPTOR = "()L$TARGET_CLASS_INTERNAL_NAME;"

// == Part 3: Signature change of resolvePath
// which drops the last parameter of RawPluginDescriptor type
private const val PATH_RESOLVER = "com/intellij/ide/plugins/PathResolver"
private const val RESOLVE_PATH_NAME = "resolvePath"
private const val RESOLVE_PATH_DESCRIPTOR_OLD =
  "(Lcom/intellij/platform/pluginSystem/parser/impl/PluginDescriptorReaderContext;Lcom/intellij/ide/plugins/DataLoader;Ljava/lang/String;L$TARGET_CLASS_INTERNAL_NAME;)L$TARGET_CLASS_INTERNAL_NAME;"
private const val RESOLVE_PATH_DESCRIPTOR_NEW =
  "(Lcom/intellij/platform/pluginSystem/parser/impl/PluginDescriptorReaderContext;Lcom/intellij/ide/plugins/DataLoader;Ljava/lang/String;)L$BUILDER_CLASS_INTERNAL_NAME;"

// == Part 4: Field Access to Method Call Replacement ==
private const val OLD_FIELD_OWNER_INTERNAL_NAME = "com/intellij/ide/plugins/RawPluginDescriptor"
private const val NEW_FIELD_OWNER_INTERNAL_NAME = "com/intellij/platform/pluginSystem/parser/impl/RawPluginDescriptor"
private const val NEW_FIELD_TYPE_INTERNAL_NAME = "com/intellij/platform/pluginSystem/parser/impl/ScopedElementsContainer"
private const val CONVERSION_FUNCTION_OWNER_INTERNAL_NAME = "com/intellij/ide/plugins/ParserElementsConversionKt"
private const val CONVERSION_FUNCTION_NAME = "convert"
// The descriptor for the conversion function. Note the receiver is the first param.
private const val CONTAINER_DESCRIPTOR_INTERNAL_NAME = "com/intellij/ide/plugins/ContainerDescriptor"
private const val CONVERSION_FUNCTION_DESCRIPTOR = "(L$NEW_FIELD_TYPE_INTERNAL_NAME;)L$CONTAINER_DESCRIPTOR_INTERNAL_NAME;"

fun main(args: Array<String>) {
  val inputJarPath: Path = Paths.get(args[0])
  val outputJarPath: Path = Paths.get(args[1])

  rewriteJar(inputJarPath, outputJarPath)
  println("✅ JAR rewriting with companion object logic complete. Output at: $outputJarPath")
}

fun rewriteJar(inputJarPath: Path, outputJarPath: Path) {
  val remapper = SimpleRemapper(typeMappings)

  JarFile(inputJarPath.toFile()).use { inputJar ->
    JarOutputStream(Files.newOutputStream(outputJarPath)).use { outputJar ->
      for (originalEntry in inputJar.entries().iterator()) {
        val newEntry = JarEntry(originalEntry.name)
        outputJar.putNextEntry(newEntry)

        inputJar.getInputStream(originalEntry).use { inputStream ->
          when {
            originalEntry.name == "${ResourceDataLoaderRewriterClassVisitor.TARGET_CLASS_NAME}.class" -> {
              // ResourceDataLoaderRewriterClassVisitor requires ClassWriter.COMPUTE_FRAMES:
              // - This is expensive and unnecessary for the other visitors.
              // - If we did this for all rewritten classes, it would trigger a lot of class loading to find
              //   common superclasses, etc.
              // - The class loading would require us to include additional jars on the classpath (IntelliJ jars, etc.).
              // For ResourceDataLoaderRewriterClassVisitor, we don't actually need any additional jars on the classpath,
              // so we create a specific visitor chain for this target class, and only use ClassWriter.COMPUTE_FRAMES here.
              val classReader = ClassReader(inputStream)
              val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
              val loaderRewriter = ResourceDataLoaderRewriterClassVisitor(Opcodes.ASM9, classWriter)
              classReader.accept(loaderRewriter, ClassReader.SKIP_FRAMES)
              outputJar.write(classWriter.toByteArray())
            }
            originalEntry.name.endsWith(".class") -> {
              val classReader = ClassReader(inputStream)
              val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)

              // Build the full visitor chain
              val fieldVisitor = FieldAccessRewriterClassVisitor(classWriter)
              val constructorVisitor = ConstructorToBuilderClassVisitor(fieldVisitor)
              val classRemapper = ClassRemapper(constructorVisitor, remapper)

              classReader.accept(classRemapper, ClassReader.EXPAND_FRAMES)
              outputJar.write(classWriter.toByteArray())
            }
            else -> inputStream.copyTo(outputJar)
          }
        }
        outputJar.closeEntry()
      }
    }
  }
}

/**
 * Rewrites class `org/jetbrains/kotlin/analysis/api/standalone/base/projectStructure/PluginStructureProvider$ResourceDataLoader`.
 *
 * ```
 *   private class ResourceDataLoader(val classLoader: ClassLoader) : DataLoader {
 *       override fun load(path: String, pluginDescriptorSourceOnly: Boolean): InputStream? = classLoader.getResource(path)?.openStream()
 *       override fun toString(): String = "resources data loader"
 *   }
 * ```
 *
 * to:
 * ```
 *   private class ResourceDataLoader(val classLoader: ClassLoader) : DataLoader {
 *     override fun load(path: String, pluginDescriptorSourceOnly: Boolean): ByteArray? = classLoader.getResource(path)?.readBytes()
 *                                                                           ^^^^^^^^^^                                  ^^^^^^^^^^^
 *     override fun toString(): String = "resources data loader"
 *   }
 * ```
 *
 * Because the super method (defined in IntelliJ) return type has changed, and this override needs to match.
 *
 * https://gemini.google.com/share/c6784d178a54
 */
class ResourceDataLoaderRewriterClassVisitor(api: Int, classVisitor: ClassVisitor) : ClassVisitor(api, classVisitor) {

  private var isTargetClass = false

  companion object {
    const val TARGET_CLASS_NAME =
      "org/jetbrains/kotlin/analysis/api/standalone/base/projectStructure/PluginStructureProvider\$ResourceDataLoader"
  }

  override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
    // Check if the class currently being visited matches our target
    isTargetClass = (name == TARGET_CLASS_NAME)

    super.visit(version, access, name, signature, superName, interfaces)
  }

  override fun visitMethod(
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?,
  ): MethodVisitor {
    // Only apply the rewrite if we are inside the correct class AND on the correct method.
    if (isTargetClass && name == "load" && descriptor == "(Ljava/lang/String;Z)Ljava/io/InputStream;") {

      val newDescriptor = "(Ljava/lang/String;Z)[B"

      // Pass null for signature to strip generics
      val mv = super.visitMethod(access, name, newDescriptor, null, exceptions)

      return ResourceDataLoaderRewriterMethodVisitor(api, mv)
    }

    return super.visitMethod(access, name, descriptor, signature, exceptions)
  }
}

/**
 * MethodVisitor that rewrites the URL.openStream() invocation to TextStreamsKt.readBytes(URL).
 *
 * https://gemini.google.com/share/c6784d178a54
 */
class ResourceDataLoaderRewriterMethodVisitor(api: Int, methodVisitor: MethodVisitor) : MethodVisitor(api, methodVisitor) {

  override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
    // Target: INVOKEVIRTUAL java/net/URL.openStream ()Ljava/io/InputStream;
    if (opcode == Opcodes.INVOKEVIRTUAL && owner == "java/net/URL" && name == "openStream" && descriptor == "()Ljava/io/InputStream;") {
      // Replace with: INVOKESTATIC kotlin/io/TextStreamsKt.readBytes (Ljava/net/URL;)[B
      super.visitMethodInsn(Opcodes.INVOKESTATIC, "kotlin/io/TextStreamsKt", "readBytes", "(Ljava/net/URL;)[B", false)
    } else {
      // Delegate all other method invocations untouched
      super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }
  }
}

// --- Visitor for Field Access Rewriting ---
class FieldAccessRewriterClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, classVisitor) {
  override fun visitMethod(
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?,
  ): MethodVisitor {
    val downstreamVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
    return FieldAccessRewriterMethodVisitor(downstreamVisitor)
  }
}

class FieldAccessRewriterMethodVisitor(methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM9, methodVisitor) {
  override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
    // We only care about getting a field's value
    if (opcode == Opcodes.GETFIELD && (owner == OLD_FIELD_OWNER_INTERNAL_NAME || owner == NEW_FIELD_OWNER_INTERNAL_NAME)) {
      when (name) {
        "appContainerDescriptor" -> "getAppElementsContainer"
        "projectContainerDescriptor" -> "getProjectElementsContainer"
        else -> null
      }?.let { newGetterName ->
        println("Found and replaced access to field: $name")
        // 1. Call the new getter, e.g., .getAppElementsContainer()
        // The object instance is already on the stack
        super.visitMethodInsn(
          Opcodes.INVOKEVIRTUAL,
          NEW_FIELD_OWNER_INTERNAL_NAME,
          newGetterName,
          "()L$NEW_FIELD_TYPE_INTERNAL_NAME;",
          false,
        )
        /* private access?
        // 1. Replace field name
        // The object instance is already on the stack
        super.visitFieldInsn(
            Opcodes.GETFIELD,
            NEW_FIELD_OWNER_INTERNAL_NAME,
            newFieldName,
            "L$NEW_FIELD_TYPE_INTERNAL_NAME;"
        )
        */
        // 2. Call the static conversion function
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          CONVERSION_FUNCTION_OWNER_INTERNAL_NAME,
          CONVERSION_FUNCTION_NAME,
          CONVERSION_FUNCTION_DESCRIPTOR,
          false,
        )
        return // We have replaced the instruction, so we return.
      }

      // Rewrites:
      //   pluginDescriptor.epNameToExtensions // field access
      // to:
      //   pluginDescriptor.getExtensions().convertExtensionElements()
      // Needed for Kotlin's: PluginStructureProvider#registerExtensionPointImplementations
      if (name == "epNameToExtensions") {
        println("Found and replaced access to field: $name")
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, NEW_FIELD_OWNER_INTERNAL_NAME, "getExtensions", "()Ljava/util/Map;", false)

        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          "com/intellij/ide/plugins/ParserElementsConversionKt",
          "convertExtensionElements",
          "(Ljava/util/Map;)Ljava/util/Map;",
          false,
        )
        return
      }
    }
    // For all other field instructions, pass them on as-is.
    super.visitFieldInsn(opcode, owner, name, descriptor)
  }
}

// --- Visitor for Constructor to Builder Rewriting ---
class ConstructorToBuilderClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, classVisitor) {
  override fun visitMethod(
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?,
  ): MethodVisitor {
    val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
    return ConstructorToBuilderMethodVisitor(methodVisitor)
  }
}

class ConstructorToBuilderMethodVisitor(methodVisitor: MethodVisitor) : MethodVisitor(Opcodes.ASM9, methodVisitor) {
  private var foundNewInstruction = false

  override fun visitTypeInsn(opcode: Int, type: String?) {
    if (opcode == Opcodes.NEW && type == TARGET_CLASS_INTERNAL_NAME) {
      foundNewInstruction = true
      return
    }
    super.visitTypeInsn(opcode, type)
  }

  override fun visitInsn(opcode: Int) {
    if (opcode == Opcodes.DUP && foundNewInstruction) {
      return
    }
    super.visitInsn(opcode)
  }

  override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
    // Part 5: @JvmField services
    if (
      opcode == Opcodes.INVOKEVIRTUAL &&
        owner == CONTAINER_DESCRIPTOR_INTERNAL_NAME &&
        name == "getServices" &&
        descriptor == "()Ljava/util/List;"
    ) {
      println("Found and replaced getServices call in method")
      super.visitFieldInsn(Opcodes.GETFIELD, owner, "services", "Ljava/util/List;")
      return
    }

    // Part 3: signature change of resolvePath
    if (
      opcode == Opcodes.INVOKEINTERFACE && owner == PATH_RESOLVER && name == RESOLVE_PATH_NAME && descriptor == RESOLVE_PATH_DESCRIPTOR_OLD
    ) {
      println("Found and replaced resolvePath call in method")
      // 1. We don't need the last argument (of RawPluginDescriptor)
      super.visitInsn(Opcodes.POP)
      // 2. signature change (drop the last parameter)
      super.visitMethodInsn(Opcodes.INVOKEINTERFACE, PATH_RESOLVER, RESOLVE_PATH_NAME, RESOLVE_PATH_DESCRIPTOR_NEW, true)
      // 3. return type changed, so need to build() it
      super.visitMethodInsn(Opcodes.INVOKEINTERFACE, BUILDER_CLASS_INTERNAL_NAME, BUILD_METHOD_NAME, BUILD_METHOD_DESCRIPTOR, true)
      return
    }

    if (
      foundNewInstruction &&
        opcode == Opcodes.INVOKESPECIAL &&
        owner == TARGET_CLASS_INTERNAL_NAME &&
        name == "<init>" &&
        descriptor == TARGET_CONSTRUCTOR_DESCRIPTOR
    ) {
      // This is the constructor call we want to replace.
      println("Found and replaced constructor call in method")

      // ### CORRECTED REPLACEMENT LOGIC ###

      // 1. Get the companion object instance: RawPluginDescriptorBuilder.Companion
      super.visitFieldInsn(
        Opcodes.GETSTATIC,
        BUILDER_CLASS_INTERNAL_NAME, // The outer interface
        COMPANION_FIELD_NAME,
        COMPANION_FIELD_DESCRIPTOR,
      )

      // 2. Call the builder method on the companion instance: .builder()
      super.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        COMPANION_OBJECT_CLASS_INTERNAL_NAME,
        BUILDER_FACTORY_METHOD_NAME,
        BUILDER_FACTORY_METHOD_DESCRIPTOR,
        false,
      )

      // 3. Call the final build method on the builder: .build()
      super.visitMethodInsn(Opcodes.INVOKEINTERFACE, BUILDER_CLASS_INTERNAL_NAME, BUILD_METHOD_NAME, BUILD_METHOD_DESCRIPTOR, true)

      foundNewInstruction = false
      return
    }

    if (foundNewInstruction) {
      super.visitTypeInsn(Opcodes.NEW, TARGET_CLASS_INTERNAL_NAME)
      super.visitInsn(Opcodes.DUP)
      foundNewInstruction = false
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}
