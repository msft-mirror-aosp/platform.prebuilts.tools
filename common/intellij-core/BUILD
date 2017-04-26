load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

java_jarjar(
    name = "intellij-core-171.4249.32-all",
    srcs = [
        ":171.4249.32/annotations.jar",
        ":171.4249.32/asm-all.jar",
        ":171.4249.32/intellij-core.jar",
        ":171.4249.32/picocontainer.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "intellij-core-171.4249.32",
    jars = [
        ":intellij-core-171.4249.32-all",
    ],
    pom = ":intellij-core-171.4249.32.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "intellij-core-171.4249.32.pom",
    artifact = "intellij-core",
    group = "com.android.tools.external.intellij",
    version = "171.4249.32",
)

java_jarjar(
    name = "intellij-core-171.3780.107-all",
    srcs = [
        ":171.3780.107/annotations.jar",
        ":171.3780.107/asm-all.jar",
        ":171.3780.107/intellij-core.jar",
        ":171.3780.107/picocontainer.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "intellij-core-171.3780.107",
    jars = [
        ":intellij-core-171.3780.107-all",
    ],
    pom = ":intellij-core-171.3780.107.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "intellij-core-171.3780.107.pom",
    artifact = "intellij-core",
    group = "com.android.tools.external.intellij",
    version = "171.3780.107",
)
