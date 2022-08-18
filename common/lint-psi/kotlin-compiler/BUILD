load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")
load("//tools/base/common:version.bzl", "BASE_VERSION")

merge_jars(
    name = "kotlin-compiler-all",
    out = "kotlin-compiler-all.jar",
    jars = [
        "kotlin-compiler.jar",
    ],
)

# merge_jars doesn't return a JavaInfo provider, so were-import the
# jar to obtain one.
java_import(
    name = "import_all",
    jars = [
        ":kotlin-compiler-all.jar",
    ],
    srcjar = "kotlin-compiler-sources.jar",
)

maven_library(
    name = "kotlin-compiler",
    srcs = [],
    bundled_deps = [
        ":import_all",
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
