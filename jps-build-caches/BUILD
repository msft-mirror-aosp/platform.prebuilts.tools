load("//tools/adt/idea/jps-build:idea.bzl", "jps_cache")

jps_cache(
    name = "kotlin.jvm-debugger.test_lib",
    srcs = glob([
        "kotlin.jvm-debugger.test_lib/**",
    ]),
    strip_prefix = "prebuilts/tools/jps-build-caches/kotlin.jvm-debugger.test_lib/",
    visibility = ["//tools/adt/idea/ij-debugger-tests:__pkg__"],
)

jps_cache(
    name = "kotlin.jvm-debugger.test_tests",
    srcs = glob([
        "kotlin.jvm-debugger.test_tests/**",
    ]),
    strip_prefix = "prebuilts/tools/jps-build-caches/kotlin.jvm-debugger.test_tests/",
    visibility = ["//tools/adt/idea/ij-debugger-tests:__pkg__"],
)
