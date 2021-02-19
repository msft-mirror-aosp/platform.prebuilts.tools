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
    name = "Kotlin/lib/kotlin-coroutines-experimental-compat",
    jars = ["Kotlin/lib/kotlin-coroutines-experimental-compat.jar"],
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
    name = "Kotlin/lib/kotlinx-collections-immutable-jvm-0.3.1",
    jars = ["Kotlin/lib/kotlinx-collections-immutable-jvm-0.3.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/allopen-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/allopen-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/android-extensions-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/android-extensions-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/completion-ranking-kotlin-0.1.3",
    jars = ["Kotlin/lib/completion-ranking-kotlin-0.1.3.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-android-extensions-runtime",
    jars = ["Kotlin/lib/kotlin-android-extensions-runtime.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-common",
    jars = ["Kotlin/lib/kotlin-common.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-allopen",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-allopen.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-annotation-based-compiler-support",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-annotation-based-compiler-support.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-kapt",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-kapt.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-kotlinx-serialization",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-kotlinx-serialization.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-noarg",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-noarg.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-sam-with-receiver",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-sam-with-receiver.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-compiler-plugins-scripting",
    jars = ["Kotlin/lib/kotlin-compiler-plugins-scripting.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-core",
    jars = ["Kotlin/lib/kotlin-core.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-eval4j",
    jars = ["Kotlin/lib/kotlin-eval4j.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-formatter",
    jars = ["Kotlin/lib/kotlin-formatter.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-frontend-independent",
    jars = ["Kotlin/lib/kotlin-frontend-independent.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-git",
    jars = ["Kotlin/lib/kotlin-git.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-gradle-idea",
    jars = ["Kotlin/lib/kotlin-gradle-gradle-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-gradle-native",
    jars = ["Kotlin/lib/kotlin-gradle-gradle-native.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-gradle-tooling",
    jars = ["Kotlin/lib/kotlin-gradle-gradle-tooling.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-gradle-statistics-for-ide",
    jars = ["Kotlin/lib/kotlin-gradle-statistics-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-idea",
    jars = ["Kotlin/lib/kotlin-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-j2k-idea",
    jars = ["Kotlin/lib/kotlin-j2k-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-j2k-new",
    jars = ["Kotlin/lib/kotlin-j2k-new.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-j2k-old",
    jars = ["Kotlin/lib/kotlin-j2k-old.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-j2k-services",
    jars = ["Kotlin/lib/kotlin-j2k-services.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jps-common",
    jars = ["Kotlin/lib/kotlin-jps-common.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm-debugger-core",
    jars = ["Kotlin/lib/kotlin-jvm-debugger-core.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm-debugger-coroutines",
    jars = ["Kotlin/lib/kotlin-jvm-debugger-coroutines.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm-debugger-evaluation",
    jars = ["Kotlin/lib/kotlin-jvm-debugger-evaluation.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm-debugger-sequence",
    jars = ["Kotlin/lib/kotlin-jvm-debugger-sequence.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm-debugger-util",
    jars = ["Kotlin/lib/kotlin-jvm-debugger-util.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-jvm",
    jars = ["Kotlin/lib/kotlin-jvm.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-line-indent-provider",
    jars = ["Kotlin/lib/kotlin-line-indent-provider.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-maven",
    jars = ["Kotlin/lib/kotlin-maven.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-native",
    jars = ["Kotlin/lib/kotlin-native.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-parcelize",
    jars = ["Kotlin/lib/kotlin-parcelize.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-project-wizard-cli",
    jars = ["Kotlin/lib/kotlin-project-wizard-cli.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-project-wizard-core",
    jars = ["Kotlin/lib/kotlin-project-wizard-core.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-project-wizard-idea",
    jars = ["Kotlin/lib/kotlin-project-wizard-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-repl",
    jars = ["Kotlin/lib/kotlin-repl.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting-ide-services",
    jars = ["Kotlin/lib/kotlin-scripting-ide-services.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-scripting",
    jars = ["Kotlin/lib/kotlin-scripting.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-uast-kotlin-idea",
    jars = ["Kotlin/lib/kotlin-uast-kotlin-idea.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlin-uast-kotlin",
    jars = ["Kotlin/lib/kotlin-uast-kotlin.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-coroutines-core-1.3.9",
    jars = ["Kotlin/lib/kotlinx-coroutines-core-1.3.9.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-coroutines-jdk8-1.3.9",
    jars = ["Kotlin/lib/kotlinx-coroutines-jdk8-1.3.9.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/kotlinx-serialization-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/kotlinx-serialization-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/markdown-0.1.41",
    jars = ["Kotlin/lib/markdown-0.1.41.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/noarg-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/noarg-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/parcelize-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/parcelize-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
java_import(
    name = "Kotlin/lib/sam-with-receiver-compiler-plugin-for-ide",
    jars = ["Kotlin/lib/sam-with-receiver-compiler-plugin-for-ide.jar"],
    visibility = ["//visibility:public"],
)
