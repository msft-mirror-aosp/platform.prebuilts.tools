# This BUILD file was generated by //tools/base/bazel:java_import_generator, please do not edit.
load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom")

maven_java_import(
    name = "jar",
    jars = ["hamcrest-library-1.3.jar"],
    pom = ":pom",
    visibility = ["//visibility:public"],
)

maven_pom(
    name = "pom",
    parent = "//prebuilts/tools/common/m2/repository/org/hamcrest/hamcrest-parent/1.3:pom",
    source = "hamcrest-library-1.3.pom",
    visibility = ["//visibility:public"],
)
