load("//tools/base/bazel:maven.bzl", "maven_import", "maven_library")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")
load("//tools/base/common:version.bzl", "BASE_VERSION")

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
    srcjar = "intellij-core-sources.jar",
)

maven_library(
    name = "intellij-core",
    srcs = [],
    bundled_deps = [
        ":import_all",
    ],
    coordinates = "com.android.tools.external.com-intellij:intellij-core:" + BASE_VERSION,
    description = "IntelliJ IDEA Core",
    jar_name = "intellij-core-mvn.jar",
    notice = "NOTICE",
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:org.jetbrains.intellij.deps.trove4j",
    ],
)

# managed by go/iml_to_build
filegroup(
    name = "lint-cli-psi-prebuilts_files",
    srcs = ["intellij-core.jar"],
    visibility = ["//visibility:public"],
)
