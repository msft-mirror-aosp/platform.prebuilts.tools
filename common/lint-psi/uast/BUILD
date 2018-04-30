load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

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
        ":uast-all",
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
