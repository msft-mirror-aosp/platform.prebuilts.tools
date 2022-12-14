load("//tools/adt/idea/studio:studio.bzl", "intellij_plugin_import")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

exports_files(["Kotlin/kotlinc/build.txt"])

# Note: clients should generally refer to //prebuilts/studio/intellij-sdk:studio-sdk-plugin-Kotlin
# rather than this target directly.
intellij_plugin_import(
    name = "kotlin-plugin",
    files_root_dir = "Kotlin",
    target_dir = "Kotlin",
    visibility = ["//prebuilts/studio/intellij-sdk:__pkg__"],
    exports = [":kotlin-plugin-lib"],
)

jvm_import(
    name = "kotlin-plugin-lib",
    jars = glob(
        include = ["Kotlin/lib/*.jar"],
        # We exclude kotlin-stdlib because it already exists in IntelliJ/lib.
        # And we prefer compiling against the IntelliJ version of kotlin-stdlib
        # because IntelliJ has a hack in PluginClassLoader.mustBeLoadedByPlatform()
        # which circumvents the normal classloader delegation hierarchy in order to
        # prioritize its own version of kotlin-stdlib.
        exclude = [
            # Currently, kotlin-stdlib is packaged into kotlinc-lib.jar.
            "Kotlin/lib/kotlinc-lib.jar",
        ],
    ),
)
