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

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-tooling",
    jars = ["Kotlin/lib/kotlin-gradle-tooling.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/android-extensions-compiler",
    jars = ["Kotlin/lib/android-extensions-compiler.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/jps/kotlin-jps-plugin",
    jars = ["Kotlin/lib/jps/kotlin-jps-plugin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/jvm-ide",
    jars = ["Kotlin/lib/jvm-ide.jar"],
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])
