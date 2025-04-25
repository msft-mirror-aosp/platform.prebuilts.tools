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
package jarrewriter

import org.objectweb.asm.*
import java.io.*
import java.util.jar.JarEntry
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

private const val DO_NOT_MODIFY_CLASS_MARKER = "DO_NOT_MODIFY_CLASS"

fun main(args: Array<String>) {
    if (args.size < 5) {
        System.err.println("Usage: JarModifier <inputJarPath> <outputJarPath> <targetClassNameInternal> <targetMethodName> <targetMethodDescriptor>")
        System.err.println("Example: JarModifier input.jar output.jar com/example/MyClass myMethod ()V")
        System.err.println("For passthrough (no modification): JarModifier input.jar output.jar DO_NOT_MODIFY_CLASS DUMMY DUMMY")
        return
    }

    val inputJarPath = args[0]
    val outputJarPath = args[1]
    val targetClassName = args[2] // Expected in internal format: com/example/MyClass
    val targetMethodName = args[3]
    val targetMethodDescriptor = args[4]

    val modify = targetClassName != DO_NOT_MODIFY_CLASS_MARKER

    println("Input JAR: $inputJarPath")
    println("Output JAR: $outputJarPath")
    if (modify) {
        println("Target Class: $targetClassName")
        println("Target Method: $targetMethodName$targetMethodDescriptor")
    } else {
        println("Mode: Passthrough (no modification)")
    }

    val inputFile = File(inputJarPath)
    val outputFile = File(outputJarPath)

    outputFile.parentFile?.mkdirs() // Create parent directories if they don't exist

    val jarEntries = mutableMapOf<String, ByteArray>()
    var manifest: Manifest? = null

    JarInputStream(BufferedInputStream(FileInputStream(inputFile))).use { jis ->
        manifest = jis.manifest?.let { Manifest(it) }
        var entry: JarEntry? = jis.nextJarEntry
        while (entry != null) {
            if (!entry.isDirectory) {
                val baos = ByteArrayOutputStream()
                jis.copyTo(baos) // More concise way to copy stream
                jarEntries[entry.name] = baos.toByteArray()
            }
            jis.closeEntry()
            entry = jis.nextJarEntry
        }
    }

    val fos = FileOutputStream(outputFile)
    val bos = BufferedOutputStream(fos)
    (manifest?.let { JarOutputStream(bos, it) } ?: JarOutputStream(bos)).use { jos ->
        for ((entryName, entryBytes) in jarEntries) {
            jos.putNextEntry(JarEntry(entryName))

            if (modify && entryName == "$targetClassName.class") {
                println("Processing class for modification: $entryName")
                val classReader = ClassReader(entryBytes)
                val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
                val classVisitor = TargetMethodWiperClassVisitor(
                    Opcodes.ASM9,
                    classWriter,
                    targetMethodName,
                    targetMethodDescriptor
                )
                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                jos.write(classWriter.toByteArray())
                println("Finished processing and writing modified class: $entryName")
            } else {
                jos.write(entryBytes)
            }
            jos.closeEntry()
        }
    }
    println("JAR processing finished. Output written to: ${outputFile.absolutePath}")
}

class TargetMethodWiperClassVisitor(
    api: Int,
    classVisitor: ClassVisitor,
    private val targetMethodName: String,
    private val targetMethodDescriptor: String
) : ClassVisitor(api, classVisitor) {

    private var currentClassName: String? = null

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<String>?) {
        currentClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<String>?
    ): MethodVisitor? {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (mv != null && name == targetMethodName && descriptor == targetMethodDescriptor) {
            println("  -> Found target method: $name$descriptor in class $currentClassName. Wiping body.")
            return MethodBodyWiperMethodVisitor(api, mv, access, name, descriptor)
        }
        return mv
    }
}

class MethodBodyWiperMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor,
    private val methodAccess: Int, // Keep if needed for calculations, though ASM usually handles it
    name: String?, // For logging
    private val methodDescriptor: String
) : MethodVisitor(api, methodVisitor) {

    init {
        println("    -> Initializing MethodBodyWiper for $name$methodDescriptor")
    }

    override fun visitCode() {
        super.visitCode()
        println("    -> visitCode(): Wiping method body and inserting appropriate return.")

        val returnType = Type.getReturnType(methodDescriptor)
        when (returnType.sort) {
            Type.VOID -> super.visitInsn(Opcodes.RETURN)
            Type.BOOLEAN, Type.CHAR, Type.BYTE, Type.SHORT, Type.INT -> {
                super.visitInsn(Opcodes.ICONST_0)
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.FLOAT -> {
                super.visitInsn(Opcodes.FCONST_0)
                super.visitInsn(Opcodes.FRETURN)
            }
            Type.LONG -> {
                super.visitInsn(Opcodes.LCONST_0)
                super.visitInsn(Opcodes.LRETURN)
            }
            Type.DOUBLE -> {
                super.visitInsn(Opcodes.DCONST_0)
                super.visitInsn(Opcodes.DRETURN)
            }
            else -> { // OBJECT or ARRAY
                super.visitInsn(Opcodes.ACONST_NULL)
                super.visitInsn(Opcodes.ARETURN)
            }
        }
    }

    // To ensure the original method's instructions are truly wiped,
    // override all other visitXxxInsn methods to do nothing (NOP).
    // Kotlin doesn't reduce the boilerplate for these overrides significantly
    // as they still need to be explicitly listed if you want to intercept and NOP them.
    // If you don't override them, they would call the `super` implementation,
    // potentially copying original instructions if `mv` points to a `MethodWriter`.
    // However, since `MethodBodyWiperMethodVisitor` is placed in the chain *before* the `MethodWriter`
    // (it's given `mv` from `ClassVisitor.visitMethod`, which in turn gets it from `ClassWriter`),
    // its purpose is to filter/transform calls to the *next* visitor in the chain.
    // By *not* calling `super.visitXxxInsn()` for unwanted instructions, we effectively drop them.
    // The only instructions passed to the `ClassWriter` (via `super.visitInsn` in `visitCode`) are the new return sequence.

    override fun visitParameter(name: String?, access: Int) { /* NOP or super.visitParameter(name, access) */ }
    override fun visitAnnotationDefault(): AnnotationVisitor? = null /* NOP or super.visitAnnotationDefault() */
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP or super.visitAnnotation(descriptor, visible) */
    override fun visitTypeAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP or super.visitTypeAnnotation(typeRef, typePath, descriptor, visible) */
    override fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean) { /* NOP or super.visitAnnotableParameterCount(parameterCount, visible) */ }
    override fun visitParameterAnnotation(parameter: Int, descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP or super.visitParameterAnnotation(parameter, descriptor, visible) */
    override fun visitAttribute(attribute: Attribute?) { /* NOP or super.visitAttribute(attribute) */ }
    override fun visitFrame(type: Int, numLocal: Int, local: Array<out Any>?, numStack: Int, stack: Array<out Any>?) { /* NOP: Frames will be recomputed by COMPUTE_FRAMES */ }
    override fun visitInsn(opcode: Int) { /* NOP: We only write our own opcodes in visitCode */ }
    override fun visitIntInsn(opcode: Int, operand: Int) { /* NOP */ }
    override fun visitVarInsn(opcode: Int, variable: Int) { /* NOP */ }
    override fun visitTypeInsn(opcode: Int, type: String?) { /* NOP */ }
    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) { /* NOP */ }
    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) { /* NOP */ }
    override fun visitInvokeDynamicInsn(name: String?, descriptor: String?, bootstrapMethodHandle: Handle?, vararg bootstrapMethodArguments: Any?) { /* NOP */ }
    override fun visitJumpInsn(opcode: Int, label: Label?) { /* NOP */ }
    override fun visitLabel(label: Label?) { /* NOP (unless manually managing labels for new body) */ }
    override fun visitLdcInsn(value: Any?) { /* NOP */ }
    override fun visitIincInsn(variable: Int, increment: Int) { /* NOP */ }
    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) { /* NOP */ }
    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) { /* NOP */ }
    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) { /* NOP */ }
    override fun visitInsnAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP */
    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) { /* NOP */ }
    override fun visitTryCatchAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP */
    override fun visitLocalVariable(name: String?, descriptor: String?, signature: String?, start: Label?, end: Label?, index: Int) { /* NOP or super.visitLocalVariable(...) to preserve for debugging */ }
    override fun visitLocalVariableAnnotation(typeRef: Int, typePath: TypePath?, start: Array<out Label>?, end: Array<out Label>?, index: IntArray?, descriptor: String?, visible: Boolean): AnnotationVisitor? = null /* NOP */
    override fun visitLineNumber(line: Int, start: Label?) { /* NOP or super.visitLineNumber(line, start) to preserve for debugging */ }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        // This will be called by ClassWriter with the *newly computed* maxStack and maxLocals
        // for the minimal body we generated in visitCode(), thanks to COMPUTE_MAXS.
        println("    -> visitMaxs(): maxStack=$maxStack, maxLocals=$maxLocals (computed for wiped body)")
        super.visitMaxs(maxStack, maxLocals)
    }

    override fun visitEnd() {
        println("    -> visitEnd(): Finished visiting/wiping method.")
        super.visitEnd()
    }
}
