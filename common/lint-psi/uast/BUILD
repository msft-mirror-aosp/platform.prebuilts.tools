load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/common:version.bzl", "BASE_VERSION")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

jvm_import(
    name = "uast-common",
    jars = ["uast-common.jar"],
    srcjar = "uast-common-sources.jar",
)

jvm_import(
    name = "uast-java",
    jars = ["uast-java.jar"],
    srcjar = "uast-java-sources.jar",
)

jvm_import(
    name = "uast-kotlin",
    jars = ["uast-kotlin.jar"],
    srcjar = "uast-kotlin-sources.jar",
)

maven_library(
    name = "uast",
    srcs = [],
    bundled_deps = [
        ":uast-common",
        ":uast-java",
        ":uast-kotlin",
    ],
    coordinates = "com.android.tools.external.org-jetbrains:uast:" + BASE_VERSION,
    description = "Unified AST",
    notice = "NOTICE",
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "lint-cli-psi-prebuilts_files",
    # do not sort: must match IML order
    srcs = [
        "uast-common.jar",
        "uast-java.jar",
        "uast-kotlin.jar",
    ],
    visibility = ["//visibility:public"],
)
