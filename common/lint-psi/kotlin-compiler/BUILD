load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")

filegroup(
    name = "agp_gradle_build_files",
    srcs = [
        "kotlin-compiler.jar",
        "kotlin-compiler-sources.jar",
    ],
    visibility = ["//tools/base/build-system:__pkg__"],
)

merge_jars(
    name = "kotlin-compiler-all",
    out = "kotlin-compiler-all.jar",
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
