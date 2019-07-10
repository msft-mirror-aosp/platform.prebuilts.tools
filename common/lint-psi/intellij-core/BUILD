load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "singlejar")

singlejar(
    name = "intellij-core-all",
    jar_name = "intellij-core-all.jar",
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
