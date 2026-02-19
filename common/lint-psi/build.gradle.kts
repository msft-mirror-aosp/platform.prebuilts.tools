// This file assembles the prebuilt jars for Lint dependencies (IntelliJ/Kotlin/UAST).
// Consult the README for details.

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    kotlin("jvm") version "2.3.0" // Aim to match the Kotlin version below.
}

val kotlinVersion = getEnvOrError("KOTLIN_VERSION")
val kotlinDir = getEnvOrError("KOTLIN_DIR")

// For each jar "foo.jar" we generate the following:
//
//     * A Jar task named "foo-jar" to produce the jar.
//     * A configuration name "foo-content" which helps feed external dependencies into the jar.
//     * A Jar task named "foo-sources-jar" to produce a sources jar.
//
// All jar outputs are attached to the "assemble" lifecycle task.

val allJarNames = listOf("kotlin-compiler")

for (jarName in allJarNames) {
    val jarContent = configurations.create("$jarName-content")

    val jarTask = tasks.register<Jar>("$jarName-jar") {
        archiveFileName.set("$jarName.jar")
        dependsOn(jarContent)
        from(jarContent.map(::zipTree))
        includeEmptyDirs = false
        // To appease the singlejar tool, we must avoid duplicate jar entries.
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        // We remove the "__index__" jar entries, because (1) they are intended only for the IDE environment where
        // they are used for classloading optimizations, and (2) they lead to duplicate jar entries when
        // we merge the various UAST jars to be packaged into AGP.
        exclude("__index__")
        // Until the proper fix arrives (https://youtrack.jetbrains.com/issue/KT-74196)
        if (jarName == "kotlin-compiler") {
            exclude("com/intellij/util/lang/JavaVersion.class")
        }
    }

    val sourcesJarTask = tasks.register<Jar>("$jarName-sources-jar") {
        archiveFileName.set("$jarName-sources.jar")
        dependsOn(jarContent)
        from(collectSourcesFromTransitiveDependencies(jarContent))
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        includeEmptyDirs = false
    }

    tasks.assemble {
        dependsOn(jarTask, sourcesJarTask)
    }
}

tasks.jar { enabled = false }

// This is where we decide which files go into which jars. See the README for details.
dependencies {
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-jps-common-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-common-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-fe10-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-fir-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-ir-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-cli-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-scripting-compiler:$kotlinVersion-for-lint") // TODO: non transitive?
    "kotlin-compiler-content"("org.jetbrains.kotlin:assignment-compiler-plugin-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("io.vavr:vavr:0.10.4") // TODO: Somehow read this version directly from the Kotlin compiler build.

    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-platform-interface-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-standalone-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.4")
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-fe10-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-k2-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-impl-base-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:low-level-api-fir-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:symbol-light-classes-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("com.github.ben-manes.caffeine:caffeine:2.9.3") { isTransitive = false } // Used by the Kotlin compiler.
}

// Here we exclude some dependencies that are unnecessary.
for (jarName in allJarNames) {
    configurations.named("$jarName-content") {
        // The following are packaged separately
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-common")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
        // The following are not needed at all.
        exclude(group = "org.jetbrains.intellij.deps", module = "trove4j")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-scripting-js")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-util-klib")
        exclude(group = "dk.brics", module = "automaton") // Adds a lot of toplevel .aut files we do not need.
    }
}

// This task generates a version.txt file listing the maven coordinates of each
// dependency that is bundled in each jar. This is useful for diffing and debugging
// jar contents.
val versionsTxt = tasks.register("versions-txt") {
    outputs.file(layout.buildDirectory.file("versions.txt"))

    for (jarName in allJarNames) {
        dependsOn(configurations.named("$jarName-content"))
    }

    doLast {
        val versionsList = StringBuilder()

        versionsList.appendLine("This file is generated by the '$name' task in build.gradle.kts.")
        versionsList.appendLine("Below is the list of dependencies that go into each prebuilt jar.")

        for (jarName in allJarNames) {
            val configuration = configurations.named("$jarName-content").get()
            val resolveResult = configuration.incoming.resolutionResult
            val resolvedDependencies = resolveResult.allDependencies.filterIsInstance<ResolvedDependencyResult>()
            val resolvedVersions = resolvedDependencies.map { it.selected.moduleVersion.toString() }

            versionsList.appendLine()
            versionsList.appendLine("$jarName.jar")
            versionsList.appendLine("===")
            resolvedVersions.distinct().sorted().forEach(versionsList::appendLine)

            if (jarName == "uast-kotlin") {
                // Special case.
                check(resolvedVersions.isEmpty())
                versionsList.appendLine("Kotlin UAST custom-built from sources")
            }
        }

        outputs.files.single().writeText(versionsList.toString())
    }
}

tasks.assemble {
    dependsOn(versionsTxt)
}

// Some Gradle magic to collect sources from the transitive dependencies of our jars.
// Inspired by https://stackoverflow.com/questions/39975780/how-can-i-use-gradle-to-download-dependencies-and-their-source-files-and-place-t/39981143#39981143.
fun collectSourcesFromTransitiveDependencies(configuration: Configuration): Collection<FileTree> {
    val allDependencies = configuration.incoming.resolutionResult.allDependencies
    val dependencyIds = allDependencies.filterIsInstance<ResolvedDependencyResult>().map { it.selected.id }

    val sourcesQuery = dependencies.createArtifactResolutionQuery()
            .forComponents(dependencyIds)
            .withArtifacts(JvmLibrary::class, SourcesArtifact::class)
            .execute()

    val sourceArtifacts = sourcesQuery.resolvedComponents.flatMap { it.getArtifacts(SourcesArtifact::class) }
    val sourceFiles = sourceArtifacts.filterIsInstance<ResolvedArtifactResult>().map { it.file }

    return sourceFiles.map(::zipTree)
}

fun getEnvOrError(name: String): String {
    return System.getenv(name) ?: error("Missing environment variable: $name")
}

// See https://plugins.jetbrains.com/docs/intellij/intellij-artifacts.html and
// also the repositories listed in JetBrains/intellij-community/build/gant.xml.
repositories {
    maven("https://cache-redirector.jetbrains.com/repo1.maven.org/maven2") // Substitute for mavenCentral().
    maven("https://www.jetbrains.com/intellij-repository/releases")
    // maven("https://www.jetbrains.com/intellij-repository/snapshots")
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide-plugin-dependencies")
    maven("$kotlinDir/build/repo")
}
