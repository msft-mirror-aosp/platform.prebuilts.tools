filegroup(
    name = "cmake-3.8.2",
    srcs = select({
        "@platforms//os:macos": glob(
            include = ["darwin/**"],
            exclude = [
                "darwin/**/doc/**",
                "darwin/**/Help/**",
            ],
        ),
        "@platforms//os:windows": glob(
            include = ["windows/**"],
            exclude = [
                "windows/**/doc/**",
                "windows/**/Help/**",
            ],
        ),
        "@platforms//os:linux": glob(
            include = ["linux/**"],
            exclude = [
                "linux/**/doc/**",
                "linux/**/Help/**",
            ],
        ),
    }),
    visibility = ["//visibility:public"],
)
