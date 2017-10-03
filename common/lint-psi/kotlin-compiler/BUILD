load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

java_jarjar(
    name = "kotlin-compiler-all",
    srcs = [
        ":kotlin-compiler.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "kotlin-compiler",
    jars = [
        ":kotlin-compiler-all",
    ],
    pom = ":kotlin-compiler.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "kotlin-compiler.pom",
    artifact = "kotlin-compiler",
    group = "com.android.tools.external.intellij",
    source = "//tools/buildSrc/base:build_version",
)
