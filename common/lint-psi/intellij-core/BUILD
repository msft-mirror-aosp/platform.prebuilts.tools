load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")

filegroup(
    name = "agp_gradle_build_files",
    srcs = [
        ":intellij-core.jar",
        ":intellij-core-sources.jar",
    ],
    visibility = ["//tools/base/build-system:__pkg__"],
)

merge_jars(
    name = "intellij-core-all",
    out = "intellij-core-all.jar",
    jars = [
        ":intellij-core.jar",
    ],
)

maven_java_import(
    name = "intellij-core",
    jars = [
        ":intellij-core-all.jar",
    ],
    pom = ":intellij-core.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "intellij-core.pom",
    artifact = "intellij-core",
    group = "com.android.tools.external.intellij",
    source = "//tools/buildSrc/base:build_version",
)
