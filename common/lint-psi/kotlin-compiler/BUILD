load("//tools/base/bazel:maven.bzl", "maven_library")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")
load("//tools/base/common:version.bzl", "BUILD_VERSION")

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

# merge_jars doesn't return a JavaInfo provider, so were-import the
# jar to obtain one.
java_import(
    name = "import_all",
    jars = [
        ":kotlin-compiler-all.jar",
    ],
)

maven_library(
    name = "kotlin-compiler",
    srcs = [],
    bundled_deps = [
        ":import_all",
    ],
    coordinates = "com.android.tools.external.com-intellij:kotlin-compiler:" + BUILD_VERSION,
    jar_name = "kotlin-compiler-mvn.jar",
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "lint-cli-psi-prebuilts_files",
    srcs = ["kotlin-compiler.jar"],
    visibility = ["//visibility:public"],
)
