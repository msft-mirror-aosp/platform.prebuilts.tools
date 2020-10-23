# Some tests, such as the android-kotlin tests, rely on files in
# Kotlin/kotlinc/lib. Ideally there could just be a data dependency from the
# kotlin-plugin rule to this kotlinc-files rule, but go/iml_to_build does
# not seem to properly include data dependencies transitively for tests. So,
# this rule is instead added as a test_data dependency directly to the tests
# which fail without it.
filegroup(
    name = "kotlinc-files",
    srcs = glob(["Kotlin/kotlinc/lib/**"]),
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/allopen-ide-plugin",
    jars = ["Kotlin/lib/allopen-ide-plugin.jar"],
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
    name = "Kotlin/lib/android-extensions-ide",
    jars = ["Kotlin/lib/android-extensions-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/annotations-13.0",
    jars = ["Kotlin/lib/annotations-13.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/completion-ranking-kotlin-0.1.2",
    jars = ["Kotlin/lib/completion-ranking-kotlin-0.1.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/ide-common",
    jars = ["Kotlin/lib/ide-common.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/javaslang-2.0.6",
    jars = ["Kotlin/lib/javaslang-2.0.6.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/javaslang-match-2.0.6",
    jars = ["Kotlin/lib/javaslang-match-2.0.6.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/javax.inject-1",
    jars = ["Kotlin/lib/javax.inject-1.jar"],
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
    name = "Kotlin/lib/kapt3-idea",
    jars = ["Kotlin/lib/kapt3-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-allopen-compiler-plugin",
    jars = ["Kotlin/lib/kotlin-allopen-compiler-plugin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-client-embeddable",
    jars = ["Kotlin/lib/kotlin-compiler-client-embeddable.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-coroutines-experimental-compat",
    jars = ["Kotlin/lib/kotlin-coroutines-experimental-compat.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-daemon-client",
    jars = ["Kotlin/lib/kotlin-daemon-client.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-daemon-client-new",
    jars = ["Kotlin/lib/kotlin-daemon-client-new.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-daemon",
    jars = ["Kotlin/lib/kotlin-daemon.jar"],
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
    name = "Kotlin/lib/kotlin-noarg-compiler-plugin",
    jars = ["Kotlin/lib/kotlin-noarg-compiler-plugin.jar"],
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
    name = "Kotlin/lib/kotlin-reflect",
    jars = ["Kotlin/lib/kotlin-reflect.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-sam-with-receiver-compiler-plugin",
    jars = ["Kotlin/lib/kotlin-sam-with-receiver-compiler-plugin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting-common",
    jars = ["Kotlin/lib/kotlin-scripting-common.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting-compiler-impl",
    jars = ["Kotlin/lib/kotlin-scripting-compiler-impl.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting-intellij",
    jars = ["Kotlin/lib/kotlin-scripting-intellij.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting-jvm",
    jars = ["Kotlin/lib/kotlin-scripting-jvm.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-script-runtime",
    jars = ["Kotlin/lib/kotlin-script-runtime.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-script-util",
    jars = ["Kotlin/lib/kotlin-script-util.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-stdlib-common",
    jars = ["Kotlin/lib/kotlin-stdlib-common.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-stdlib",
    jars = ["Kotlin/lib/kotlin-stdlib.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-stdlib-jdk7",
    jars = ["Kotlin/lib/kotlin-stdlib-jdk7.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-stdlib-jdk8",
    jars = ["Kotlin/lib/kotlin-stdlib-jdk8.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-util-io",
    jars = ["Kotlin/lib/kotlin-util-io.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-util-klib",
    jars = ["Kotlin/lib/kotlin-util-klib.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-util-klib-metadata",
    jars = ["Kotlin/lib/kotlin-util-klib-metadata.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-collections-immutable-jvm-0.3.1",
    jars = ["Kotlin/lib/kotlinx-collections-immutable-jvm-0.3.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-coroutines-core-1.3.7",
    jars = ["Kotlin/lib/kotlinx-coroutines-core-1.3.7.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-coroutines-jdk8-1.3.7",
    jars = ["Kotlin/lib/kotlinx-coroutines-jdk8-1.3.7.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-serialization-compiler-plugin",
    jars = ["Kotlin/lib/kotlinx-serialization-compiler-plugin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/markdown-0.1.25",
    jars = ["Kotlin/lib/markdown-0.1.25.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/noarg-ide-plugin",
    jars = ["Kotlin/lib/noarg-ide-plugin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/sam-with-receiver-ide-plugin",
    jars = ["Kotlin/lib/sam-with-receiver-ide-plugin.jar"],
    visibility = ["//visibility:public"],
)
