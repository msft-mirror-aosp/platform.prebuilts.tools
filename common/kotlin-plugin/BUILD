# For historical and now technical debt reasons, this target is used as
# a test data dependency rather than a runtime dependency, and we load
# the Kotlin plugin at runtime using IntelliJ's plugin.path property.
filegroup(
    name = "kotlin-plugin-for-studio",
    srcs = glob(["Kotlin/**"]),
    visibility = ["//visibility:public"],
)

# This contains all JARs in the Kotlin plugin and is intended as a runtime
# dependency for ASwB tests which need the Kotlin plugin enabled.
java_import(
    name = "kotlin-plugin-for-aswb",
    jars = glob(["Kotlin/lib/**/*.jar"]),
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-plugin",
    jars = ["Kotlin/lib/kotlin-plugin.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-tooling",
    jars = ["Kotlin/lib/kotlin-gradle-tooling.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/android-extensions-compiler",
    jars = ["Kotlin/lib/android-extensions-compiler.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/jps/kotlin-jps-plugin",
    jars = ["Kotlin/lib/jps/kotlin-jps-plugin.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/jvm-ide",
    jars = ["Kotlin/lib/jvm-ide.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/android-lint",
    jars = ["Kotlin/lib/android-lint.jar"],
    neverlink = 1,
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kapt3-idea",
    jars = ["Kotlin/lib/kapt3-idea.jar"],
    visibility = ["//visibility:public"],
)
