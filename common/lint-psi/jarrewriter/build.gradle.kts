import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
// Removed unused Jar/File/Stream imports here as they are not directly used in the build script logic
// They belong in the .kt source file where the JAR manipulation happens.

plugins {
    kotlin("jvm") version "1.9.23" // Or the latest stable Kotlin version
    application
}

repositories {
    mavenCentral()
}

val asmVersion = "9.7" // Or the latest ASM version

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.ow2.asm:asm:$asmVersion")
    implementation("org.ow2.asm:asm-commons:$asmVersion")

    testImplementation("junit:junit:4.13.2")
}

application {
    mainClass.set("jarrewriter.JarModifierKt") // Assumes main is in JarModifierKt.kt
}

// --- Custom Properties for JAR Rewriting ---
// Initialize the extra property first, then delegate the Kotlin val to it.
// This makes it clear that -PinputJarPath is the Gradle property to use.
project.extra.set("inputJarPath", project.findProperty("inputJarPath") as? String ?: "input/my-app.jar")
val inputJarPath: String by project.extra

project.extra.set("outputJarPath", project.findProperty("outputJarPath") as? String ?: "build/output/modified-app.jar")
val outputJarPath: String by project.extra

project.extra.set("targetClass", project.findProperty("targetClass") as? String ?: "com/example/TargetService") // Use '/' for package separator
val targetClass: String by project.extra

project.extra.set("targetMethodName", project.findProperty("targetMethodName") as? String ?: "methodToWipe")
val targetMethodName: String by project.extra

project.extra.set("targetMethodDesc", project.findProperty("targetMethodDesc") as? String ?: "()V")
val targetMethodDesc: String by project.extra


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17" // Or your desired JVM target
}

tasks.register<JavaExec>("rewriteType") {
    group = "custom"
    description = "Rewrites specific types in a JAR to new ones."

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("kotlincrewriter.JavaTypeRewriterKt")

    // Arguments for the main method, now using the correctly defined Kotlin properties
    args(
        inputJarPath,
        outputJarPath,
    )

    doFirst {
        val outputDir = project.file(outputJarPath).parentFile
        if (!outputDir.exists()) {
            println("Creating output directory: $outputDir")
            outputDir.mkdirs()
        }
        val inputFile = project.file(inputJarPath)
        if (!inputFile.exists()) {
            throw GradleException("Input JAR not found: ${inputFile.absolutePath}")
        }
    }
}

tasks.register<JavaExec>("wipeConstructor") {
    group = "custom"
    description = "Rewrites a specific constructor in a JAR to have a simple super call."

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("intellijrewriter.BinaryFileTypeDecompilersRewriterKt")

    // Arguments for the main method, now using the correctly defined Kotlin properties
    args(
        inputJarPath,
        outputJarPath,
    )

    doFirst {
        val outputDir = project.file(outputJarPath).parentFile
        if (!outputDir.exists()) {
            println("Creating output directory: $outputDir")
            outputDir.mkdirs()
        }
        val inputFile = project.file(inputJarPath)
        if (!inputFile.exists()) {
            throw GradleException("Input JAR not found: ${inputFile.absolutePath}")
        }
    }
}

tasks.register<JavaExec>("wipeMethod") {
    group = "custom"
    description = "Rewrites a specific method in a JAR to have an empty body."

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("intellijrewriter.JarModifierKt")

    // Arguments for the main method, now using the correctly defined Kotlin properties
    args(
        inputJarPath,
        outputJarPath,
        targetClass,
        targetMethodName,
        targetMethodDesc
    )

    doFirst {
        val outputDir = project.file(outputJarPath).parentFile
        if (!outputDir.exists()) {
            println("Creating output directory: $outputDir")
            outputDir.mkdirs()
        }
        val inputFile = project.file(inputJarPath)
        if (!inputFile.exists()) {
            throw GradleException("Input JAR not found: ${inputFile.absolutePath}")
        }
    }
}

tasks.register<JavaExec>("passthroughJar") {
    group = "custom"
    description = "Reads an input JAR and writes it to output JAR as-is (no modification)."

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("intellijrewriter.JarModifierKt")

    args(
        inputJarPath, // Uses the resolved inputJarPath
        outputJarPath, // Uses the resolved outputJarPath
        "DO_NOT_MODIFY_CLASS", // Special value to indicate pass-through
        "DO_NOT_MODIFY_METHOD",
        "()V"
    )

    doFirst {
        val outputDir = project.file(outputJarPath).parentFile
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val inputFile = project.file(inputJarPath)
        if (!inputFile.exists()) {
            throw GradleException("Input JAR not found: ${inputFile.absolutePath}")
        }
    }
}
