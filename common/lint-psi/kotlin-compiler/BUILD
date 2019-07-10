load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "singlejar")

singlejar(
    name = "kotlin-compiler-all",
    jar_name = "kotlin-compiler-all.jar",
    jars = [
        "kotlin-compiler.jar",
    ],
)

maven_java_import(
    name = "kotlin-compiler",
    jars = [
        ":kotlin-compiler-all.jar",
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
