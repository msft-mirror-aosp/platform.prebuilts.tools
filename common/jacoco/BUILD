java_import(
    name = "agent",
    jars = ["jacoco-agent-0.8.7.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "cli-jar",
    jars = ["jacoco-cli-0.8.7.jar"],
    visibility = ["//visibility:private"],
)

java_binary(
    name = "cli",
    main_class = "org/jacoco/cli/internal/Main",
    visibility = ["//visibility:public"],
    runtime_deps = [":cli-jar"],
)
