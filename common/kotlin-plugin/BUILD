filegroup(
    name = "kotlin-plugin-for-studio",
    srcs = glob(["Kotlin/**"]),
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-plugin",
    jars = ["Kotlin/lib/kotlin-plugin.jar"],
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])
