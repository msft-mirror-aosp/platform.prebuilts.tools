load("//tools/base/bazel:maven.bzl", "maven_aar", "maven_java_import", "maven_pom")

maven_java_import(
    name = "jar",
    jars = ["fastutil-min-builder-8.3.1-23.jar"],
    pom = ":pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "pom",
    source = "fastutil-min-builder-8.3.1-23.pom",
    visibility = ["//visibility:public"],
)
