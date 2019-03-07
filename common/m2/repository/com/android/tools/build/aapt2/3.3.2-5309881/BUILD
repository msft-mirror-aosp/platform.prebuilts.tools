load("//tools/base/bazel:maven.bzl", "maven_java_import", "maven_pom", "maven_aar")

filegroup(
    name = "jar",
)

maven_pom(
    name = "jar_maven",
    classified_files = [
        "aapt2-3.3.2-5309881-linux.jar",
        "aapt2-3.3.2-5309881-osx.jar",
        "aapt2-3.3.2-5309881-windows.jar",
    ],
    classifiers = [
        "linux",
        "osx",
        "windows",
    ],
    source = "aapt2-3.3.2-5309881.pom",
    visibility = ["//visibility:public"],
)
