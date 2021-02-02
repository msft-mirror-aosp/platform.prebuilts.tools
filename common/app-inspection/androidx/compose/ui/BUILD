load("//tools/base/app-inspection:app_inspection.bzl", "app_inspection_proto")
load("//tools/adt/idea/studio:studio.bzl", "studio_data")

app_inspection_proto(
    name = "layout_inspector_compose_java_proto",
    jar = "compose-ui-inspection.jar",
    proto_file_name = "compose_layout_inspection.proto",
    visibility = ["//visibility:public"],
)

studio_data(
    name = "bundle",
    files = ["compose-ui-inspection.jar"],
    mappings = {"prebuilts/tools/common/app-inspection/androidx/compose/ui/": "app-inspection/"},
    visibility = ["//visibility:public"],
)
