load("//tools/adt/idea/studio:studio.bzl", "intellij_plugin_import")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

intellij_plugin_import(
    name = "rust-plugin",
    files = glob(["intellij-rust/**"]),
    strip_prefix = "prebuilts/tools/common/rust-plugin/intellij-rust/",
    target_dir = "intellij-rust",
    visibility = ["//visibility:public"],
    exports = [":rust-plugin-lib"],
)

jvm_import(
    name = "rust-plugin-lib",
    jars = glob(
        include = ["intellij-rust/lib/*.jar"],
    ),
)
