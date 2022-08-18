load("//tools/adt/idea/studio:studio.bzl", "studio_data")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "trace-processor-daemon",
    srcs = glob(["**/trace_processor_daemon*"]),
)

studio_data(
    name = "trace-processor-daemon-bundle",
    files_linux = glob(["linux/*"]),
    files_mac = glob(["darwin/*"]),
    files_mac_arm = glob(["darwin/*"]),
    files_win = glob(["windows/*"]),
    mappings = {
        "prebuilts/tools/common/trace-processor-daemon/linux/": "trace_processor_daemon/",
        "prebuilts/tools/common/trace-processor-daemon/darwin/": "trace_processor_daemon/",
        "prebuilts/tools/common/trace-processor-daemon/windows/": "trace_processor_daemon/",
    },
    visibility = ["//visibility:public"],
)
