load("//tools/adt/idea/studio:studio.bzl", "studio_data")

filegroup(
    name = "perfetto",
    srcs = glob(["*/**"]),
    visibility = ["//visibility:public"],
)

studio_data(
    name = "perfetto-bundle",
    files = glob(["*/**"]),
    mappings = {"prebuilts/tools/common/perfetto/": "perfetto/"},
    visibility = ["//visibility:public"],
)
