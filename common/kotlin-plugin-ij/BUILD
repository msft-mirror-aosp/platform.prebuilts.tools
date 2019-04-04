exports_files(glob(["kotlin-plugin*.zip"]))

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-stdlib",
    jars = ["Kotlin/kotlinc/lib/kotlin-stdlib.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-stdlib-jdk7",
    jars = ["Kotlin/kotlinc/lib/kotlin-stdlib-jdk7.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/kotlinc/lib/kotlin-stdlib-jdk8",
    jars = ["Kotlin/kotlinc/lib/kotlin-stdlib-jdk8.jar"],
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

# This contains all JARs in the Kotlin plugin built for IntelliJ.
# Targets which need to run IntelliJ with Kotlin enabled can simply
# add this as a runtime dependency.
java_import(
    name = "kotlin-plugin-for-intellij",
    jars = glob(["Kotlin/lib/**/*.jar"]),
    visibility = ["//visibility:public"],
)
