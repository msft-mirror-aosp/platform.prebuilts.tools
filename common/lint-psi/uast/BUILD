load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")
load("//tools/base/bazel:utils.bzl", "java_jarjar")

java_jarjar(
    name = "uast-all",
    srcs = [
        ":uast-common.jar",
        ":uast-java.jar",
        ":uast-kotlin.jar",
    ],
    rules = "no_rules.txt",
)

maven_java_import(
    name = "uast",
    jars = [
        ":uast-all",
    ],
    pom = ":uast.pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "uast.pom",
    artifact = "uast",
    group = "com.android.tools.external.intellij",
    source = "//tools/buildSrc/base:build_version",
)

java_import(
    name = "uast/uast-common",
    jars = ["uast-common.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "uast/uast-java",
    jars = ["uast-java.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "uast/uast-kotlin",
    jars = ["uast-kotlin.jar"],
    tags = ["managed"],
    visibility = ["//visibility:public"],
)
