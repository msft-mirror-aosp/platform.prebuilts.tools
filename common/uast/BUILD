load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

java_jarjar(
    name = "uast-171.4249.32-all",
    srcs = [
        ":1.0/uast-common-1.0.jar",
        ":1.0/uast-java-1.0.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "uast-171.4249.32",
    jars = [
        ":uast-171.4249.32-all",
    ],
    pom = ":uast-171.4249.32.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "uast-171.4249.32.pom",
    artifact = "uast",
    group = "com.android.tools.external.intellij",
    source = "//tools/buildSrc/base:build_version",
)

java_import(
    name = "1.0/uast-common-1.0",
    jars = ["1.0/uast-common-1.0.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "1.0/uast-java-1.0",
    jars = ["1.0/uast-java-1.0.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)
