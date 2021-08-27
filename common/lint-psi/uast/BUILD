load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_library")
load("//tools/base/bazel:utils.bzl", "java_jarjar")
load("//tools/base/common:version.bzl", "BUILD_VERSION")

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

# java_jarjar doesn't return a JavaInfo provider, so were-import the
# jar to obtain one.
java_import(
    name = "import_all",
    jars = [":uast-all.jar"],
)

maven_library(
    name = "uast",
    srcs = [],
    bundled_deps = [
        ":import_all",
    ],
    coordinates = "com.android.tools.external.org-jetbrains:uast:" + BUILD_VERSION,
    visibility = ["//visibility:public"],
)
