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

cc_library(
    name = "libskia",
    srcs = select({
        ":linux-x86_64": ["linux-x86_64/libskia.a"],
        ":windows-x86_64": ["windows-x86_64/libskia.lib"],
        ":osx-x86_64": ["darwin-x86_64/libskia.a"],
    }),
    hdrs = glob(["include/**/*.h"]),
    includes = [
        "include/core/",
        "include/effects/",
    ],
    visibility = ["//visibility:public"],
    deps = ["@skia_extra//:skia_includes"],
)
