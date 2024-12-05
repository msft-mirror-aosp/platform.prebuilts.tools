filegroup(
    name = "art",
    srcs = ["bin/art"],
    visibility = ["//visibility:public"],
)

# TODO: The file "bin/dex2oat" doesn't exist anymore so this target doesn't export anything
filegroup(
    name = "dex2oat",
    srcs = glob(
        include = ["bin/dex2oat"],
    ),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "art_deps",
    srcs = ["bin/dalvikvm64"] + glob(
        include = [
            "framework/*.jar",
            "framework/x86_64/*",
            "lib64/*",
            "usr/**",
            "com.android.i18n/**",
        ],
    ),
    visibility = ["//visibility:public"],
)

java_library(
    name = "test-lib",
    srcs = ["HelloWorld.java"],
)

sh_test(
    name = "test-art",
    size = "small",
    srcs = [
        "test_art.sh",
    ],
    data = [
        "bin/dex2oat64",
        "expected-stdout.txt",
        ":art",
        ":art_deps",
        ":test-lib",
        "//prebuilts/r8",
    ],
    tags = [
        "noci:studio-win",
    ],
)
