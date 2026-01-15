load("//tools/base/bazel:jvm_import.bzl", "jvm_import")
load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/common:version.bzl", "BASE_VERSION")

jvm_import(
    name = "uast-lib",
    jars = ["uast.jar"],
    srcjar = "uast-sources.jar",
)

maven_library(
    name = "uast",
    srcs = [],
    bundled_deps = [":uast-lib"],
    coordinates = "com.android.tools.external.org-jetbrains:uast:" + BASE_VERSION,
    description = "Unified AST",
    notice = "NOTICE",
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "lint-cli-psi-prebuilts_files",
    # do not sort: must match IML order
    srcs = ["uast.jar"],
    visibility = ["//visibility:public"],
)
