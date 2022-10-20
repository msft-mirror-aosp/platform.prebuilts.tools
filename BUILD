load("//tools/adt/idea/studio:studio.bzl", "studio_data")

filegroup(
    name = "offline-sdk",
    srcs = glob(["*/offline-sdk/**"]),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "simpleperf",
    srcs = glob(["*/simpleperf/**"]),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "bazel-dist",
    srcs = glob(["*/bazel/**"]),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "lldb",
    srcs = glob(["*/lldb/**"]),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "clion",
    srcs = glob(["clion/**"]),
    visibility = ["//visibility:public"],
)

studio_data(
    name = "simpleperf-bundle",
    files = ["//prebuilts/tools/common/simpleperf"],
    files_linux = glob(["linux-x86_64/simpleperf/*"]),
    files_mac = glob(["darwin-x86_64/simpleperf/*"]),
    files_mac_arm = glob(["darwin-x86_64/simpleperf/*"]),
    files_win = glob(["windows-x86_64/simpleperf/*"]),
    mappings = {
        "prebuilts/tools/common/simpleperf/": "simpleperf/",
        "prebuilts/tools/linux-x86_64/simpleperf/": "simpleperf/linux-x86_64/",
        "prebuilts/tools/windows-x86_64/simpleperf/": "simpleperf/windows-x86_64/",
        "prebuilts/tools/darwin-x86_64/simpleperf/": "simpleperf/darwin-x86_64/",
    },
    visibility = ["//visibility:public"],
)

studio_data(
    name = "lldb-bundle",
    files = glob(["common/lldb/**"]),
    files_linux = glob(["linux-x86_64/lldb/**"]) + ["//prebuilts/python/linux-x86:linux-x86-bundle"],
    files_mac = glob(["darwin-x86_64/lldb/**"]) + ["//prebuilts/python/darwin-x86:darwin-x86-bundle"],
    files_mac_arm = glob(["darwin-x86_64/lldb/**"]) + ["//prebuilts/python/darwin-x86:darwin-x86-bundle"],
    files_win = glob(["windows-x86_64/lldb/**"]) + ["//prebuilts/python/windows-x86:windows-x86-bundle"],
    mappings = {
        "prebuilts/tools/common/lldb/": "",
        "prebuilts/tools/windows-x86_64/lldb/": "",
        "prebuilts/tools/linux-x86_64/lldb/": "",
        "prebuilts/tools/darwin-x86_64/lldb/": "",
        "prebuilts/python/windows-x86/x64/Lib/": "lib/",
        "prebuilts/python/windows-x86/x64/DLLs/": "dlls/",
        "prebuilts/python/linux-x86/lib/python3.10/": "lib/python3.10/",
        "prebuilts/python/darwin-x86/lib/python3.10/": "lib/python3.10/",
    },
    visibility = ["//visibility:public"],
)
