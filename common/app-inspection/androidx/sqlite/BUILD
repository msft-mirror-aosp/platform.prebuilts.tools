load("//tools/base/bazel:app_inspection.bzl", "app_inspection_proto")

app_inspection_proto(
    name = "sqlite_inspector_proto",
    jar = "sqlite-inspection.jar",
    proto_file_name = "live_sql_protocol.proto",
    visibility = ["//visibility:public"],
)

filegroup(
    name = "sqlite-inspection",
    srcs = glob(["*/**"]),
    visibility = ["//visibility:public"],
)
