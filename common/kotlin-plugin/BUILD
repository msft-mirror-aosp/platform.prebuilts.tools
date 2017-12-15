filegroup(
    name = "kotlin-plugin-for-studio",
    srcs = glob(["Kotlin/**"]),
    visibility = ["//visibility:public"],
)

java_import(
    name = "Kotlin/lib/kotlin-plugin",
    jars = ["Kotlin/lib/kotlin-plugin.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "Kotlin/lib/android-lint",
    jars = ["Kotlin/lib/android-lint.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])
