# Kotlin plugin for IJ version kotlin-plugin-1.1.0-release-IJ2017.1-1/

exports_files(glob(["kotlin-plugin*.zip"]))

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-runtime",
    jars = ["Kotlin/kotlinc/lib/kotlin-runtime.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-reflect",
    jars = ["Kotlin/kotlinc/lib/kotlin-reflect.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-test",
    jars = ["Kotlin/kotlinc/lib/kotlin-test.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "Kotlin/kotlinc/lib/kotlin-compiler",
    jars = ["Kotlin/kotlinc/lib/kotlin-compiler.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "kotlin_plugin",
    srcs = glob(["Kotlin/**"]),
    visibility = ["//visibility:public"],
)
