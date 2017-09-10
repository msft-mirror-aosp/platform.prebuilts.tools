config_setting(
    name = "osx-x86_64",
    values = {"host_cpu": "darwin"},
    visibility = ["//visibility:public"],
)

config_setting(
    name = "windows-x86_64",
    values = {"host_cpu": "x64_windows"},
    visibility = ["//visibility:public"],
)

config_setting(
    name = "linux-x86_64",
    values = {"host_cpu": "k8"},
    visibility = ["//visibility:public"],
)

filegroup(
    name = "cmake-3.8.2",
    srcs = select({
        "//prebuilts/tools/common/cmake:osx-x86_64": glob(
            include = ["darwin/**"],
            exclude = ["darwin/**/doc/**", "darwin/**/Help/**"]),

        "//prebuilts/tools/common/cmake:windows-x86_64": glob(
            include = ["windows/**"],
            exclude = ["windows/**/doc/**", "windows/**/Help/**"]),

        "//prebuilts/tools/common/cmake:linux-x86_64": glob(
            include = ["linux/**"],
            exclude = ["linux/**/doc/**", "linux/**/Help/**"]),
    }),
    visibility = ["//visibility:public"],
)
