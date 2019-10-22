filegroup(
    name = "art",
    srcs = glob(
        include = ["bin/art"],
    ),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "dex2oat",
    srcs = glob(
        include = ["bin/dex2oat"],
    ),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "art_deps",
    srcs = glob(
        include = [
            "framework/*.jar",
            "framework/x86_64/*",
            "lib64/*",
            "usr/**",
            "bin/dalvikvm64",
            "com.android.i18n/**",
        ],
    ),
    visibility = ["//visibility:public"],
)
