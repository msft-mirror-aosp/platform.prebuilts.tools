# Some tests, such as the android-kotlin tests, rely on files in
# Kotlin/kotlinc/lib. Ideally there could just be a data dependency from the
# kotlin-plugin rule to this kotlinc-files rule, but go/iml_to_build does
# not seem to properly include data dependencies transitively for tests. So,
# this rule is instead added as a test_data dependency directly to the tests
# which fail without it.
filegroup(
    name = "kotlinc-files",
    srcs = glob(["Kotlin/kotlinc/lib/**"]),
    visibility = ["//visibility:public"],
)

exports_files(["Kotlin/kotlinc/build.txt"])
