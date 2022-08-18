load("//tools/base/app-inspection:app_inspection.bzl", "app_inspection_proto")
load("//tools/adt/idea/studio:studio.bzl", "studio_data")

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

studio_data(
    name = "sqlite-inspection-bundle",
    files = ["sqlite-inspection.jar"],
    mappings = {"prebuilts/tools/common/app-inspection/androidx/sqlite/": "app-inspection/"},
    visibility = ["//visibility:public"],
)
