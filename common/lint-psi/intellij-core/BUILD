load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom", "maven_import", "maven_library")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")
load("//tools/base/common:version.bzl", "BUILD_VERSION")

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

# merge_jars doesn't return a JavaInfo provider, so were-import the
# jar to obtain one.
java_import(
    name = "import_all",
    jars = [":intellij-core-all.jar"],
)

maven_library(
    name = "intellij-core",
    srcs = [],
    bundled_deps = [
        ":import_all",
    ],
    coordinates = "com.android.tools.external.intellij:intellij-core:" + BUILD_VERSION,
    jar_name = "intellij-core-mvn.jar",
    visibility = ["//visibility:public"],
)
