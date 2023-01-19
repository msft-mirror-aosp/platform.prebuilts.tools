// This file assembles the prebuilt jars for Lint dependencies (IntelliJ/Kotlin/UAST).
// Consult the README for details.

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.21" // Aim to match the Kotlin version below.
}

val intellijVersion = getEnvOrError("INTELLIJ_VERSION")
val kotlinVersion = getEnvOrError("KOTLIN_VERSION")
val intellijDir = getEnvOrError("INTELLIJ_DIR")
val kotlinDir = getEnvOrError("KOTLIN_DIR")

// We create several jars: intellij-core.jar, kotlin-compiler.jar, uast-common.jar, etc.
// For each jar "foo.jar" we generate the following:
//
//     * A Jar task named "foo-jar" to produce the jar.
//     * A configuration name "foo-content" which helps feed external dependencies into the jar.
//     * A Jar task named "foo-sources-jar" to produce a sources jar.
//
// All jar outputs are attached to the "assemble" lifecycle task.

val allJarNames = listOf(
    "intellij-core", "kotlin-compiler",
    "uast-common", "uast-java",
    "uast-kotlin"
)

for (jarName in allJarNames) {
    val jarContent = configurations.create("$jarName-content")

    val jarTask = tasks.register<Jar>("$jarName-jar") {
        archiveFileName.set("$jarName.jar")
        dependsOn(jarContent)
        from(jarContent.map(::zipTree))
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE // To appease the singlejar tool.
        includeEmptyDirs = false
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
    "intellij-core-content"("com.jetbrains.intellij.java:java-psi-impl:$intellijVersion")
    "intellij-core-content"("com.jetbrains.intellij.platform:jps-model-impl:$intellijVersion") // Contains JavaSdkUtil.
    "intellij-core-content"("com.jetbrains.intellij.platform:project-model:$intellijVersion") // safeAnalyzeUtils depends on it

    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-jps-common-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-compiler-cli-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:kotlin-scripting-compiler:$kotlinVersion-for-lint")
    "kotlin-compiler-content"("io.javaslang:javaslang:2.0.6") // TODO: Somehow read this version directly from the Kotlin compiler build.

    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-providers-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-project-structure-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:analysis-api-standalone-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.4")
    "kotlin-compiler-content"("org.jetbrains.kotlin:kt-references-fe10-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:high-level-api-fe10-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:high-level-api-fir-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:high-level-api-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:high-level-api-impl-base-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:low-level-api-fir-for-ide:$kotlinVersion-for-lint") { isTransitive = false }
    "kotlin-compiler-content"("org.jetbrains.kotlin:symbol-light-classes-for-ide:$kotlinVersion-for-lint") { isTransitive = false }

    "uast-common-content"("com.jetbrains.intellij.platform:uast:$intellijVersion") { isTransitive = false }
    "uast-java-content"("com.jetbrains.intellij.java:java-uast:$intellijVersion") { isTransitive = false }
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

// Unfortunately there is no publicly available CLI build for the Kotlin IDE plugin yet.
// Luckily we only need a small part of the IDE plugin (the UAST modules), so for now
// we just build the UAST modules ourselves using the following ad hoc build rules.
// If eventually we remove all our patches from Kotlin UAST, then maybe we can just
// download it from https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide instead.
sourceSets {
    create("kotlinUastBaseSrc") {
        java.srcDir("$intellijDir/plugins/kotlin/uast/uast-kotlin-base/src")
    }
    create("kotlinUastSrc") {
        compileClasspath += sourceSets["kotlinUastBaseSrc"].output
        java.srcDir("$intellijDir/plugins/kotlin/uast/uast-kotlin/src")
        java.srcDir("$intellijDir/plugins/kotlin/uast/uast-kotlin-fir/src")
    }
}

tasks.named<Jar>("uast-kotlin-jar") {
    from(sourceSets["kotlinUastBaseSrc"].output)
    from(sourceSets["kotlinUastSrc"].output)
}

tasks.named<Jar>("uast-kotlin-sources-jar") {
    from(sourceSets["kotlinUastBaseSrc"].allSource)
    from(sourceSets["kotlinUastSrc"].allSource)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xjvm-default=enable")
        suppressWarnings = true
    }
}

val kotlinUastAdHocCompileClasspath: Configuration by configurations.creating
configurations.named("kotlinUastBaseSrcCompileOnly") { extendsFrom(kotlinUastAdHocCompileClasspath) }
configurations.named("kotlinUastSrcCompileOnly") { extendsFrom(kotlinUastAdHocCompileClasspath) }

dependencies {
    kotlinUastAdHocCompileClasspath(files(tasks.named("intellij-core-jar")))
    kotlinUastAdHocCompileClasspath(files(tasks.named("kotlin-compiler-jar")))
    kotlinUastAdHocCompileClasspath("com.jetbrains.intellij.platform:uast:$intellijVersion") { isTransitive = false }
    kotlinUastAdHocCompileClasspath("org.jetbrains.intellij.deps:asm-all:9.1")
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
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide-plugin-dependencies")
    maven("$kotlinDir/build/repo")
}
