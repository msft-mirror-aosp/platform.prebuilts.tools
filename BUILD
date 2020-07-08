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
    files_win = glob([
        "windows/simpleperf/*",
        "windows-x86_64/simpleperf/*",
    ]),
    mappings = {
        "prebuilts/tools/common/simpleperf/": "simpleperf/",
        "prebuilts/tools/linux-x86_64/simpleperf/": "simpleperf/linux-x86_64/",
        "prebuilts/tools/windows/simpleperf/": "simpleperf/windows/",
        "prebuilts/tools/windows-x86_64/simpleperf/": "simpleperf/windows-x86_64/",
        "prebuilts/tools/darwin-x86_64/simpleperf/": "simpleperf/darwin-x86_64/",
    },
    visibility = ["//visibility:public"],
)
