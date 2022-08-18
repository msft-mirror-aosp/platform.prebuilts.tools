load("//tools/base/app-inspection:app_inspection.bzl", "app_inspection_proto")
load("//tools/adt/idea/studio:studio.bzl", "studio_data")

app_inspection_proto(
    name = "workmanager_inspector_proto",
    jar = "workmanager-inspection.jar",
    proto_file_name = "workmanager_protocol.proto",
    visibility = ["//visibility:public"],
)

studio_data(
    name = "workmanager-inspection-bundle",
    files = ["workmanager-inspection.jar"],
    mappings = {"prebuilts/tools/common/app-inspection/androidx/work/": "app-inspection/"},
    visibility = ["//visibility:public"],
)
