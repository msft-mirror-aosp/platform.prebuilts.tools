load("@rules_java//java:defs.bzl", "java_binary", "java_import")

java_binary(
    name = "intellij-plugin-verifier",
    main_class = "com.jetbrains.pluginverifier.PluginVerifierMain",
    visibility = ["//visibility:public"],
    runtime_deps = [":lib"],
)

java_import(
    name = "lib",
    jars = ["verifier-cli-1.289-all.jar"],
)
