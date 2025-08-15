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
package intellijrewriter

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.jar.JarEntry
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream

/** The internal name of the class to be modified. */
const val TARGET_CLASS_NAME = "com/intellij/openapi/fileTypes/BinaryFileTypeDecompilers"

/** The internal name of the superclass whose constructor we need to call. */
const val SUPER_CLASS_NAME = "com/intellij/openapi/fileTypes/FileTypeExtension"

/** The name of the extension point field. */
const val EP_NAME_FIELD = "EP_NAME"

/** The descriptor of the extension point field. */
const val EP_NAME_DESC = "Lcom/intellij/openapi/extensions/ExtensionPointName;"

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Usage: ./gradlew wipeConstructor -PinputJarPath=<input_jar_path> -PoutputJarPath=<output_jar_path>")
        return
    }
    val inputJarPath = args[0]
    val outputJarPath = args[1]

    if (!Files.exists(Paths.get(inputJarPath))) {
        println("Error: Input JAR not found at '$inputJarPath'")
        return
    }

    println("Starting JAR rewrite from '$inputJarPath' to '$outputJarPath'...")

    JarInputStream(FileInputStream(inputJarPath)).use { jis ->
        JarOutputStream(FileOutputStream(outputJarPath)).use { jos ->
            var entry: JarEntry? = jis.nextJarEntry
            while (entry != null) {
                jos.putNextEntry(JarEntry(entry.name))
                val bytes = jis.readBytes()

                if (entry.name == "$TARGET_CLASS_NAME.class") {
                    println("Found target class: ${entry.name}. Rewriting...")
                    val rewrittenBytes = rewriteConstructor(bytes)
                    jos.write(rewrittenBytes)
                } else {
                    jos.write(bytes)
                }

                jos.closeEntry()
                entry = jis.nextJarEntry
            }
        }
    }
    println("✅ JAR rewriting complete. Output saved to '$outputJarPath'")
}

/**
 * Rewrites the constructor of the `BinaryFileTypeDecompilers` class.
 */
fun rewriteConstructor(classBytes: ByteArray): ByteArray {
    val classReader = ClassReader(classBytes)
    val classNode = ClassNode()
    classReader.accept(classNode, 0)

    val constructor = classNode.methods.find { it.name == "<init>" && it.desc == "()V" }

    if (constructor == null) {
        println("⚠️ Warning: Private constructor ()V not found in $TARGET_CLASS_NAME. The class will not be modified.")
        return classBytes
    }

    // Create the bytecode for the original, simple constructor: super(EP_NAME);
    val newInstructions = InsnList().apply {
        add(VarInsnNode(ALOAD, 0)) // Load `this`
        add(FieldInsnNode(GETSTATIC, TARGET_CLASS_NAME, EP_NAME_FIELD, EP_NAME_DESC)) // Get the static EP_NAME field
        add(MethodInsnNode(
            INVOKESPECIAL,
            SUPER_CLASS_NAME,
            "<init>",
            "($EP_NAME_DESC)V",
            false // isInterface
        )) // Call super constructor: super(EP_NAME)
        add(InsnNode(RETURN)) // Return
    }

    // Replace the existing instructions
    constructor.instructions.clear()
    constructor.instructions.add(newInstructions)

    // The ClassWriter will recompute frames and max stack/locals size automatically.
    val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
    classNode.accept(classWriter)
    println("Constructor rewritten successfully.")

    return classWriter.toByteArray()
}

