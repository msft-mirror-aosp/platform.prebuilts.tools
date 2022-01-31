load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/common:version.bzl", "BASE_VERSION")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

jvm_import(
    name = "kotlin-compiler_import",
    jars = ["kotlin-compiler.jar"],
    srcjar = "kotlin-compiler-sources.jar",
)

maven_library(
    name = "kotlin-compiler",
    srcs = [],
    bundled_deps = [
        ":kotlin-compiler_import",
    ],
    coordinates = "com.android.tools.external.com-intellij:kotlin-compiler:" + BASE_VERSION,
    description = "Kotlin Compiler",
    jar_name = "kotlin-compiler-mvn.jar",
    notice = "NOTICE",
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "lint-cli-psi-prebuilts_files",
    srcs = ["kotlin-compiler.jar"],
    visibility = ["//visibility:public"],
)
