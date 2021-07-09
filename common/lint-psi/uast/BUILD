load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

filegroup(
    name = "agp_gradle_build_files",
    srcs = [
        ":uast-common.jar",
        ":uast-common-sources.jar",
        ":uast-java.jar",
        ":uast-java-sources.jar",
        ":uast-kotlin.jar",
        ":uast-kotlin-sources.jar",
    ],
    visibility = ["//tools/base/build-system:__pkg__"],
)

java_jarjar(
    name = "uast-all",
    srcs = [
        ":uast-common.jar",
        ":uast-java.jar",
        ":uast-kotlin.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "uast",
    jars = [
        ":uast-all.jar",
    ],
    pom = ":uast.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "uast.pom",
    artifact = "uast",
    group = "com.android.tools.external.intellij",
    source = "//tools/buildSrc/base:build_version",
)
