load("//tools/base/bazel:maven.bzl", "maven_import", "maven_library")
load("//tools/base/common:version.bzl", "BASE_VERSION")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

jvm_import(
    name = "intellij-core_import",
    jars = ["intellij-core.jar"],
    srcjar = "intellij-core-sources.jar",
)

maven_library(
    name = "intellij-core",
    srcs = [],
    bundled_deps = [
        ":intellij-core_import",
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
