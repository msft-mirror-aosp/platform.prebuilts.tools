load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/bazel:utils.bzl", "java_jarjar")
load("//tools/base/common:version.bzl", "BASE_VERSION")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")

java_jarjar(
    name = "uast-all",
    srcs = [
        ":uast-common.jar",
        ":uast-java.jar",
        ":uast-kotlin.jar",
    ],
    rules = "no_rules.txt",
)

merge_jars(
    name = "uast-all-sources",
    out = "intellij-core-all.jar",
    jars = [
        ":uast-common-sources.jar",
        ":uast-java-sources.jar",
        ":uast-kotlin-sources.jar",
    ],
)

# java_jarjar doesn't return a JavaInfo provider, so were-import the
# jar to obtain one.
java_import(
    name = "import_all",
    jars = [":uast-all.jar"],
    srcjar = "uast-all-sources",
)

maven_library(
    name = "uast",
    srcs = [],
    bundled_deps = [
        ":import_all",
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
