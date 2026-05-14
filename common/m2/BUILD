load("@rules_android//rules:rules.bzl", "aar_import")
load("@rules_java//java:defs.bzl", "java_binary", "java_import")
load("@rules_license//rules:license.bzl", "license")
load("//build/bazel/rules/gathering:prebuilt_package_metadata.bzl", "prebuilt_package_metadata")
load("//tools/adt/idea/studio:studio.bzl", "studio_data")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")
load("//tools/base/bazel:merge_archives.bzl", "merge_jars")

license(
    name = "leakcanary_license",
    license_text = "leakcanary/NOTICE",
    visibility = ["//visibility:public"],
)

prebuilt_package_metadata(
    name = "leakcanary_metadata",
    third_party_dependencies = "leakcanary/leakcanary.json",
    visibility = ["//visibility:public"],
)

java_import(
    name = "org.jetbrains.kotlin.kotlin-gradle-plugin.1.5.21",
    jars = ["repository/org/jetbrains/kotlin/kotlin-gradle-plugin/1.5.21/kotlin-gradle-plugin-1.5.21.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.apis.google-api-services-storage_v1-rev82-1.22.0",
    jars = ["repository/com/google/apis/google-api-services-storage/v1-rev82-1.22.0/google-api-services-storage-v1-rev82-1.22.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.apis.google-api-services-testing_v1-rev82-1.22.0",
    jars = ["repository/com/google/apis/google-api-services-testing/v1-rev82-1.22.0/google-api-services-testing-v1-rev82-1.22.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.apis.google-api-services-toolresults_v1beta3-rev82-1.22.0",
    jars = ["repository/com/google/apis/google-api-services-toolresults/v1beta3-rev82-1.22.0/google-api-services-toolresults-v1beta3-rev82-1.22.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.api-client.google-api-client_1.22.0",
    jars = ["repository/com/google/api-client/google-api-client/1.22.0/google-api-client-1.22.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.oauth-client.google-oauth-client_1.22.0",
    jars = ["repository/com/google/oauth-client/google-oauth-client/1.22.0/google-oauth-client-1.22.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "io.grpc.grpc-api.1.69.1",
    jars = ["repository/io/grpc/grpc-api/1.69.1/grpc-api-1.69.1.jar"],
    visibility = ["//visibility:public"],
)

exports_files(
    ["repository/com/google/api/grpc/proto-google-common-protos/2.17.0/proto-google-common-protos-2.17.0.jar"],
    visibility = ["//visibility:public"],
)

exports_files(
    [
        "repository/net/bytebuddy/byte-buddy/1.17.7/byte-buddy-1.17.7.jar",
        "repository/net/bytebuddy/byte-buddy-agent/1.17.7/byte-buddy-agent-1.17.7.jar",
        "repository/org/mockito/mockito-core/5.20.0/mockito-core-5.20.0.jar",
    ],
    visibility = ["//visibility:public"],
)

java_import(
    name = "io.grpc.grpc-kotlin-stub.1.4.1",
    jars = ["repository/io/grpc/grpc-kotlin-stub/1.4.1/grpc-kotlin-stub-1.4.1.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "io.grpc.protoc-gen-grpc-java.1.57.0_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "io.grpc.protoc-gen-grpc-java.1.66.0_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/io/grpc/protoc-gen-grpc-java/1.66.0/protoc-gen-grpc-java-1.66.0-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/io/grpc/protoc-gen-grpc-java/1.66.0/protoc-gen-grpc-java-1.66.0-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/io/grpc/protoc-gen-grpc-java/1.66.0/protoc-gen-grpc-java-1.66.0-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/io/grpc/protoc-gen-grpc-java/1.66.0/protoc-gen-grpc-java-1.66.0-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

java_binary(
    name = "io.grpc.protoc-gen-grpc-kotlin-1.4.1-jdk8",
    main_class = "io.grpc.kotlin.generator.GeneratorRunner",
    visibility = ["//visibility:public"],
    runtime_deps = ["repository/io/grpc/protoc-gen-grpc-kotlin/1.4.1/protoc-gen-grpc-kotlin-1.4.1-jdk8.jar"],
)

filegroup(
    name = "ffmpeg-licenses",
    srcs = glob([
        "repository/org/bytedeco/ffmpeg-LICENSE*",
        "repository/org/bytedeco/ffmpeg-SOURCE_CODE*",
    ]),
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "utp-core-proto",
    jars = ["repository/com/google/testing/platform/core-proto/0.0.8-alpha08/core-proto-0.0.8-alpha08.jar"],
    visibility = ["//visibility:public"],
)

# ImportDepsChecker was disabled for this target with a patch to rules_android in
# tools/base/bazel/toplevel.MODULE.bazel. For details, see b/405144196 and
# https://github.com/bazelbuild/rules_android/issues/332
aar_import(
    name = "androidx-monitor",
    aar = "repository/androidx/test/monitor/1.6.1/monitor-1.6.1.aar",
    visibility = ["//visibility:public"],
)

aar_import(
    name = "androidx-test-runner-1.4.0",
    aar = "repository/androidx/test/runner/1.4.0/runner-1.4.0.aar",
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:androidx.annotation.annotation",
        "@maven//:androidx.test.monitor",
        "@maven//:androidx.test.services.storage",
        "@maven//:junit.junit",
    ],
)

aar_import(
    name = "androidx.inspection.inspection.1.0.0",
    aar = "repository/androidx/inspection/inspection/1.0.0/inspection-1.0.0.aar",
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:androidx.annotation.annotation",
    ],
)

aar_import(
    name = "androidx-sqlite-2.5.0-beta01",
    aar = "repository/androidx/sqlite/sqlite-android/2.5.0-beta01/sqlite-android-2.5.0-beta01.aar",
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:androidx.annotation.annotation",
        "@maven//:org.jetbrains.kotlin.kotlin-stdlib",
    ],
)

aar_import(
    name = "androidx-sqlite-bundled-2.5.0-beta01",
    aar = "repository/androidx/sqlite/sqlite-bundled-android/2.5.0-beta01/sqlite-bundled-android-2.5.0-beta01.aar",
    visibility = ["//visibility:public"],
    deps = [
        ":androidx-sqlite-2.5.0-beta01",
    ],
)

java_import(
    name = "androidx-sqlite-bundled-jvm-2.5.0-beta01",
    jars = ["repository/androidx/sqlite/sqlite-bundled-jvm/2.5.0-beta01/sqlite-bundled-jvm-2.5.0-beta01.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "javax.annotation.javax.annotation-api.1.3.2",
    jars = ["repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.3.19.6_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java-util.3.19.6",
    jars = ["repository/com/google/protobuf/protobuf-java-util/3.19.6/protobuf-java-util-3.19.6.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java.3.19.6",
    jars = ["repository/com/google/protobuf/protobuf-java/3.19.6/protobuf-java-3.19.6.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.3.22.3_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java-util.3.22.3",
    jars = ["repository/com/google/protobuf/protobuf-java-util/3.22.3/protobuf-java-util-3.22.3.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java.3.22.3",
    jars = ["repository/com/google/protobuf/protobuf-java/3.22.3/protobuf-java-3.22.3.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-kotlin.3.24.4",
    jars = ["repository/com/google/protobuf/protobuf-kotlin/3.24.4/protobuf-kotlin-3.24.4.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.3.24.4_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/com/google/protobuf/protoc/3.24.4/protoc-3.24.4-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/com/google/protobuf/protoc/3.24.4/protoc-3.24.4-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/com/google/protobuf/protoc/3.24.4/protoc-3.24.4-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/com/google/protobuf/protoc/3.24.4/protoc-3.24.4-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.4.28.3_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/com/google/protobuf/protoc/4.28.3/protoc-4.28.3-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/com/google/protobuf/protoc/4.28.3/protoc-4.28.3-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/com/google/protobuf/protoc/4.28.3/protoc-4.28.3-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/com/google/protobuf/protoc/4.28.3/protoc-4.28.3-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "truth_files",
    # do not sort: must match IML order
    srcs = [
        "repository/com/google/truth/truth/0.42/truth-0.42.jar",
        "repository/com/googlecode/java-diff-utils/diffutils/1.3.0/diffutils-1.3.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "javax-inject_files",
    srcs = ["repository/javax/inject/javax.inject/1/javax.inject-1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "equalsverifier_files",
    # do not sort: must match IML order
    srcs = [
        "repository/nl/jqno/equalsverifier/equalsverifier/3.15.6/equalsverifier-3.15.6.jar",
        "repository/org/objenesis/objenesis/3.3/objenesis-3.3.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "mockito_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/mockito/mockito-core/5.20.0/mockito-core-5.20.0.jar",
        "repository/net/bytebuddy/byte-buddy/1.17.7/byte-buddy-1.17.7.jar",
        "repository/net/bytebuddy/byte-buddy-agent/1.17.7/byte-buddy-agent-1.17.7.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "junit-4.12",
    jars = ["repository/junit/junit/4.12/junit-4.12.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "hamcrest-core-1.3",
    jars = ["repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "antlr4-runtime",
    jars = ["repository/org/antlr/antlr4-runtime/4.5.3/antlr4-runtime-4.5.3.jar"],
    visibility = ["//tools/data-binding:__pkg__"],
)

# managed by go/iml_to_build
jvm_import(
    name = "juniversalchardet",
    jars = ["repository/com/googlecode/juniversalchardet/juniversalchardet/1.0.3/juniversalchardet-1.0.3.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "javapoet",
    jars = ["repository/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "jetifier-core",
    jars = ["repository/com/android/tools/build/jetifier/jetifier-core/1.0.0-beta10/jetifier-core-1.0.0-beta10.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "compile-testing_files",
    srcs = ["repository/com/google/testing/compile/compile-testing/0.16/compile-testing-0.16.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "auto-common",
    jars = ["repository/com/google/auto/auto-common/0.10/auto-common-0.10.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "kxml2_files",
    srcs = ["repository/net/sf/kxml/kxml2/2.3.0/kxml2-2.3.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "jimfs_files",
    srcs = ["repository/com/google/jimfs/jimfs/1.1/jimfs-1.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "asm-tools_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/ow2/asm/asm-analysis/9.6/asm-analysis-9.6.jar",
        "repository/org/ow2/asm/asm-commons/9.6/asm-commons-9.6.jar",
        "repository/org/ow2/asm/asm-tree/9.6/asm-tree-9.6.jar",
        "repository/org/ow2/asm/asm/9.6/asm-9.6.jar",
        "repository/org/ow2/asm/asm-util/9.6/asm-util-9.6.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "truth-java8-extension_files",
    srcs = ["repository/com/google/truth/extensions/truth-java8-extension/0.42/truth-java8-extension-0.42.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "flatbuffers-java",
    jars = ["repository/com/google/flatbuffers/flatbuffers-java/1.12.0/flatbuffers-java-1.12.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "tensorflow-lite-metadata",
    jars = ["repository/org/tensorflow/tensorflow-lite-metadata/0.1.0-rc2/tensorflow-lite-metadata-0.1.0-rc2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "sqlite_files",
    srcs = ["repository/org/xerial/sqlite-jdbc/3.51.1.0/sqlite-jdbc-3.51.1.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "guava-testlib_files",
    srcs = ["repository/com/google/guava/guava-testlib/31.1-jre/guava-testlib-31.1-jre.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "zxing-core_files",
    srcs = ["repository/com/google/zxing/core/3.5.0/core-3.5.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "aapt-proto_files",
    srcs = ["repository/com/android/tools/build/aapt2-proto/8.1.0-10154469/aapt2-proto-8.1.0-10154469.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "JUnit4",
    # do not sort: must match IML order
    jars = [
        "repository/junit/junit/4.12/junit-4.12.jar",
        "repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "commons-lang3-3.3.2",
    jars = ["repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "commons-io-2.4",
    jars = ["repository/commons-io/commons-io/2.4/commons-io-2.4.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "eclipse-layout-kernel",
    # do not sort: must match IML order
    jars = [
        "repository/org/eclipse/elk/org.eclipse.elk.alg.layered/0.3.0/org.eclipse.elk.alg.layered-0.3.0.jar",
        "repository/org/eclipse/elk/org.eclipse.elk.core/0.3.0/org.eclipse.elk.core-0.3.0.jar",
        "repository/org/eclipse/elk/org.eclipse.elk.graph/0.3.0/org.eclipse.elk.graph-0.3.0.jar",
        "repository/org/eclipse/emf/org.eclipse.emf.common/2.12.0/org.eclipse.emf.common-2.12.0.jar",
        "repository/org/eclipse/emf/org.eclipse.emf.ecore/2.12.0/org.eclipse.emf.ecore-2.12.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "gradle-tooling-api-5.3.1",
    jars = ["repository/org/gradle/gradle-tooling-api/5.3.1/gradle-tooling-api-5.3.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "ffmpeg_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "ffmpeg-platform_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/bytedeco/ffmpeg-platform/7.1.1-1.5.12/ffmpeg-platform-7.1.1-1.5.12.jar",
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-linux-x86_64.jar",
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-macosx-arm64.jar",
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-macosx-x86_64.jar",
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-windows-x86_64.jar",
        "repository/org/bytedeco/javacpp-platform/1.5.12/javacpp-platform-1.5.12.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-linux-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-macosx-arm64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-macosx-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-windows-x86_64.jar",
    ],
    visibility = ["//visibility:public"],
)

studio_data(
    name = "ffmpeg-platform-bundle",
    files = [
        "repository/org/bytedeco/ffmpeg-platform/7.1.1-1.5.12/ffmpeg-platform-7.1.1-1.5.12.jar",
        "repository/org/bytedeco/javacpp-platform/1.5.12/javacpp-platform-1.5.12.jar",
    ],
    files_linux = [
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-linux-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-linux-x86_64.jar",
    ],
    files_mac = [
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-macosx-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-macosx-x86_64.jar",
    ],
    files_mac_arm = [
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-macosx-arm64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-macosx-arm64.jar",
    ],
    files_win = [
        "repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/ffmpeg-7.1.1-1.5.12-windows-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.12/javacpp-1.5.12-windows-x86_64.jar",
    ],
    mappings = {
        "prebuilts/tools/common/m2/repository/org/bytedeco/ffmpeg-platform/7.1.1-1.5.12/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/ffmpeg/7.1.1-1.5.12/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/javacpp-platform/1.5.12/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/javacpp/1.5.12/": "",
    },
    visibility = ["//visibility:public"],
)

# This is the Kotlin compiler used for building our Kotlin sources in Bazel.
# It is declared here manually in order to avoid affecting version resolution
# for the main maven repo---because for various reasons our compiler version
# might not match the version of the kotlin-stdlib we want on the classpath.
# Note, the versions of 'jvm-abi-gen-plugin', 'kotlin-compose-compiler-plugin'
# and 'kotlin-serialization-compiler-plugin' must be consistent with the
# Kotlin compiler version.
jvm_import(
    name = "kotlin-compiler",
    jars = [
        # These are the transitive deps of target
        # @maven//:org.jetbrains.kotlin.kotlin-compiler_<VERSION>
        # defined in tools/base/bazel/maven/BUILD.maven.
        "repository/org/jetbrains/annotations/24.0.0/annotations-24.0.0.jar",
        "repository/org/jetbrains/kotlin/kotlin-compiler/2.3.20/kotlin-compiler-2.3.20.jar",
        "repository/org/jetbrains/kotlin/kotlin-reflect/1.6.10/kotlin-reflect-1.6.10.jar",
        "repository/org/jetbrains/kotlin/kotlin-script-runtime/2.3.20/kotlin-script-runtime-2.3.20.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/2.3.20/kotlin-stdlib-jdk7-2.3.20.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/2.3.20/kotlin-stdlib-jdk8-2.3.20.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib/2.3.20/kotlin-stdlib-2.3.20.jar",
        "repository/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.8.0/kotlinx-coroutines-core-jvm-1.8.0.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "jvm-abi-gen-plugin",
    jars = [
        "repository/org/jetbrains/kotlin/jvm-abi-gen/2.3.20/jvm-abi-gen-2.3.20.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "kotlin-compose-compiler-plugin",
    jars = [
        "repository/org/jetbrains/kotlin/kotlin-compose-compiler-plugin/2.3.20/kotlin-compose-compiler-plugin-2.3.20.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "kotlin-serialization-compiler-plugin",
    jars = [
        "repository/org/jetbrains/kotlin/kotlin-serialization-compiler-plugin/2.3.20/kotlin-serialization-compiler-plugin-2.3.20.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "groovy-3.0.9",
    jars = ["repository/org/codehaus/groovy/groovy/3.0.9/groovy-3.0.9.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "kotlin-test_files",
    srcs = ["repository/org/jetbrains/kotlin/kotlin-test/1.7.10/kotlin-test-1.7.10.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "jetbrains.kotlinx.coroutines.test_files",
    srcs = ["repository/org/jetbrains/kotlinx/kotlinx-coroutines-test-jvm/1.8.0-RC2/kotlinx-coroutines-test-jvm-1.8.0-RC2.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "all",
    testonly = True,
    srcs = glob(["**/*"]),
    visibility = [
        "//tools/adt/idea/aswb:__subpackages__",
        "//tools/base/bazel:__pkg__",
        "//tools/vendor/google3/aswb:__subpackages__",
    ],
)

# managed by go/iml_to_build
jvm_import(
    name = "trace-perfetto-library",
    jars = ["repository/androidx/tracing/tracing-perfetto-handshake/1.0.0-beta02/tracing-perfetto-handshake-1.0.0-beta02.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "jsr305-2.0.1",
    jars = ["repository/com/google/code/findbugs/jsr305/2.0.1/jsr305-2.0.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "javax-servlet",
    jars = ["repository/javax/servlet/javax.servlet-api/3.0.1/javax.servlet-api-3.0.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "gson_files",
    srcs = ["repository/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "moshi-1.6.0",
    jars = ["repository/com/squareup/moshi/moshi/1.6.0/moshi-1.6.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "okio-2.8.0",
    jars = ["repository/com/squareup/okio/okio/2.8.0/okio-2.8.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "okio-jvm-3.9.0",
    jars = ["repository/com/squareup/okio/okio-jvm/3.9.0/okio-jvm-3.9.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "google-dexlib2_files",
    srcs = ["repository/com/android/tools/smali/smali-dexlib2/3.0.9/smali-dexlib2-3.0.9.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "google-baksmali_files",
    # do not sort: must match IML order
    srcs = [
        "repository/com/android/tools/smali/smali-baksmali/3.0.9/smali-baksmali-3.0.9.jar",
        "repository/com/android/tools/smali/smali-util/3.0.9/smali-util-3.0.9.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "kotlinx-cli-jvm-0.3.1",
    jars = ["repository/org/jetbrains/kotlinx/kotlinx-cli-jvm/0.3.1/kotlinx-cli-jvm-0.3.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "oauth2",
    # do not sort: must match IML order
    jars = [
        "repository/com/google/apis/google-api-services-oauth2/v2-rev20200213-2.0.0/google-api-services-oauth2-v2-rev20200213-2.0.0.jar",
        "repository/com/google/auth/google-auth-library-oauth2-http/1.24.0/google-auth-library-oauth2-http-1.24.0.jar",
        "repository/com/google/oauth-client/google-oauth-client/1.35.0/google-oauth-client-1.35.0.jar",
        "repository/com/google/oauth-client/google-oauth-client-java6/1.35.0/google-oauth-client-java6-1.35.0.jar",
        "repository/com/google/auth/google-auth-library-credentials/1.24.0/google-auth-library-credentials-1.24.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "jetty_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/mortbay/jetty/jetty/6.1.26/jetty-6.1.26.jar",
        "repository/org/mortbay/jetty/jetty-util/6.1.26/jetty-util-6.1.26.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "cloudresourcemanager_files",
    srcs = ["repository/com/google/apis/google-api-services-cloudresourcemanager/v3-rev20230416-2.0.0/google-api-services-cloudresourcemanager-v3-rev20230416-2.0.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "google-api-client_files",
    # do not sort: must match IML order
    srcs = [
        "repository/com/google/api-client/google-api-client/2.2.0/google-api-client-2.2.0.jar",
        "repository/com/google/http-client/google-http-client-gson/1.42.3/google-http-client-gson-1.42.3.jar",
        "repository/com/google/http-client/google-http-client/1.42.3/google-http-client-1.42.3.jar",
        "repository/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
        "repository/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
        "repository/com/google/oauth-client/google-oauth-client/1.35.0/google-oauth-client-1.35.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "google-api-services",
    # do not sort: must match IML order
    jars = [
        "repository/com/google/apis/google-api-services-cloudbilling/v1-rev20240119-2.0.0/google-api-services-cloudbilling-v1-rev20240119-2.0.0.jar",
        "repository/com/google/apis/google-api-services-monitoring/v3-rev20230806-2.0.0/google-api-services-monitoring-v3-rev20230806-2.0.0.jar",
        "repository/com/google/apis/google-api-services-serviceusage/v1-rev20240220-2.0.0/google-api-services-serviceusage-v1-rev20240220-2.0.0.jar",
        "repository/com/google/apis/google-api-services-storage/v1-rev20230301-2.0.0/google-api-services-storage-v1-rev20230301-2.0.0.jar",
        "repository/com/google/apis/google-api-services-toolresults/v1beta3-rev20230410-2.0.0/google-api-services-toolresults-v1beta3-rev20230410-2.0.0.jar",
        "repository/com/google/apis/google-api-services-testing/v1-rev20250327-2.0.0/google-api-services-testing-v1-rev20250327-2.0.0.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "cloudcode-auth-libs",
    jars = [
        "repository/com/google/api-client/google-api-client/2.2.0/google-api-client-2.2.0.jar",
        "repository/com/google/apis/google-api-services-oauth2/v2-rev20200213-2.0.0/google-api-services-oauth2-v2-rev20200213-2.0.0.jar",
        "repository/com/google/http-client/google-http-client-apache-v2/1.42.0/google-http-client-apache-v2-1.42.0.jar",
        "repository/com/google/http-client/google-http-client-gson/1.43.3/google-http-client-gson-1.43.3.jar",
        "repository/com/google/http-client/google-http-client/1.43.3/google-http-client-1.43.3.jar",
        "repository/com/google/oauth-client/google-oauth-client-java6/1.35.0/google-oauth-client-java6-1.35.0.jar",
        "repository/com/google/oauth-client/google-oauth-client/1.35.0/google-oauth-client-1.35.0.jar",
        "repository/com/google/auth/google-auth-library-credentials/1.24.0/google-auth-library-credentials-1.24.0.jar",
        "repository/com/google/auth/google-auth-library-oauth2-http/1.24.0/google-auth-library-oauth2-http-1.24.0.jar",
        "repository/io/grpc/grpc-context/1.27.2/grpc-context-1.27.2.jar",
        "repository/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
        "repository/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "libgoogle_auth_transitive_deps",
    # do not sort: must match IML order
    jars = [
        "repository/androidx/annotation/annotation-jvm/1.7.1/annotation-jvm-1.7.1.jar",
        "repository/com/google/apis/google-api-services-cloudresourcemanager/v1beta1-rev20240128-2.0.0/google-api-services-cloudresourcemanager-v1beta1-rev20240128-2.0.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "okhttp.mockwebserver_files",
    # do not sort: must match IML order
    srcs = [
        "repository/com/squareup/okhttp3/okhttp/4.12.0/okhttp-4.12.0.jar",
        "repository/com/squareup/okhttp3/mockwebserver/4.12.0/mockwebserver-4.12.0.jar",
        "repository/com/squareup/okio/okio-jvm/3.6.0/okio-jvm-3.6.0.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "clikt",
    jars = [
        "repository/com/github/ajalt/clikt/clikt-jvm/3.5.3/clikt-jvm-3.5.3.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "opentest4j_files",
    srcs = ["repository/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "mockito-kotlin_files",
    srcs = ["repository/org/mockito/kotlin/mockito-kotlin/5.4.0/mockito-kotlin-5.4.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "brotli-dec-0.1.2",
    jars = ["repository/org/brotli/dec/0.1.2/dec-0.1.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "junit-jupiter-api-5.9.2",
    jars = ["repository/org/junit/jupiter/junit-jupiter-api/5.9.2/junit-jupiter-api-5.9.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "jopt-simple-4.9",
    jars = ["repository/net/sf/jopt-simple/jopt-simple/4.9/jopt-simple-4.9.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "junit-platform-commons-1.10.1",
    jars = ["repository/org/junit/platform/junit-platform-commons/1.10.1/junit-platform-commons-1.10.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "junit4_files",
    # do not sort: must match IML order
    srcs = [
        "repository/junit/junit/4.13.2/junit-4.13.2.jar",
        "repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "grpc-inprocess-1.69.1",
    jars = ["repository/io/grpc/grpc-inprocess/1.69.1/grpc-inprocess-1.69.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "protobuf-java-util-3.22.3",
    jars = ["repository/com/google/protobuf/protobuf-java-util/3.22.3/protobuf-java-util-3.22.3.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java-util.3.25.5",
    jars = ["repository/com/google/protobuf/protobuf-java-util/3.25.5/protobuf-java-util-3.25.5.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java.3.25.5",
    jars = ["repository/com/google/protobuf/protobuf-java/3.25.5/protobuf-java-3.25.5.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.4.33.2_exe",
    srcs = select({
        "//tools/base/bazel/platforms:macos-arm64": ["repository/com/google/protobuf/protoc/4.33.2/protoc-4.33.2-osx-aarch_64.exe"],
        "//tools/base/bazel/platforms:macos-x86_64": ["repository/com/google/protobuf/protoc/4.33.2/protoc-4.33.2-osx-x86_64.exe"],
        "@platforms//os:windows": ["repository/com/google/protobuf/protoc/4.33.2/protoc-4.33.2-windows-x86_64.exe"],
        "@platforms//os:linux": ["repository/com/google/protobuf/protoc/4.33.2/protoc-4.33.2-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java-util.4.33.2",
    jars = ["repository/com/google/protobuf/protobuf-java-util/4.33.2/protobuf-java-util-4.33.2.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.protobuf.protobuf-java.4.33.2",
    jars = ["repository/com/google/protobuf/protobuf-java/4.33.2/protobuf-java-4.33.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "google-api-services-drive-v3-rev20241027-2.0.0",
    jars = ["repository/com/google/apis/google-api-services-drive/v3-rev20241027-2.0.0/google-api-services-drive-v3-rev20241027-2.0.0.jar"],
    visibility = ["//visibility:public"],
)

# Temporary rule extracting the atomics package from kotlin-stdlib 2.1.20, to prevent linkage errors with the platform's older version
genrule(
    name = "filtered_kotlin_stdlib_atomics",
    srcs = ["repository/org/jetbrains/kotlin/kotlin-stdlib/2.1.20/kotlin-stdlib-2.1.20.jar"],
    outs = ["filtered_kotlin_stdlib_atomics.jar"],
    cmd = "$(location //tools/base/bazel:jar_filter) $(location repository/org/jetbrains/kotlin/kotlin-stdlib/2.1.20/kotlin-stdlib-2.1.20.jar) $(location filtered_kotlin_stdlib_atomics.jar) kotlin.concurrent.atomics kotlin.concurrent.internal",
    tools = ["//tools/base/bazel:jar_filter"],
    visibility = ["//visibility:public"],
)

# using merge_jars for now for simplicity of packaging jars
merge_jars(
    name = "mcp-sdk-libraries",
    out = "mcp-sdk-libraries.jar",
    jars = [
        "repository/io/github/oshai/kotlin-logging-jvm/7.0.0/kotlin-logging-jvm-7.0.0.jar",
        "repository/io/ktor/ktor-server-content-negotiation-jvm/3.3.2/ktor-server-content-negotiation-jvm-3.3.2.jar",
        "repository/io/ktor/ktor-server-sse-jvm/3.3.2/ktor-server-sse-jvm-3.3.2.jar",
        "repository/io/ktor/ktor-server-websockets-jvm/3.3.2/ktor-server-websockets-jvm-3.3.2.jar",
        "repository/org/jetbrains/kotlinx/atomicfu-jvm/0.29.0/atomicfu-jvm-0.29.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "sqlite-jdbc-3.43.0.0",
    jars = ["repository/org/xerial/sqlite-jdbc/3.43.0.0/sqlite-jdbc-3.43.0.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "org.eclipse.lsp4j-0.21.1",
    # do not sort: must match IML order
    jars = [
        "repository/org/eclipse/lsp4j/org.eclipse.lsp4j/0.21.1/org.eclipse.lsp4j-0.21.1.jar",
        "repository/org/eclipse/lsp4j/org.eclipse.lsp4j.debug/0.21.1/org.eclipse.lsp4j.debug-0.21.1.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "autolink-0.11.0",
    jars = ["repository/org/nibor/autolink/autolink/0.11.0/autolink-0.11.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "flexmark-0.64.8",
    # do not sort: must match IML order
    jars = [
        "repository/com/vladsch/flexmark/flexmark/0.64.8/flexmark-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-ext-autolink/0.64.8/flexmark-ext-autolink-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-ext-gfm-strikethrough/0.64.8/flexmark-ext-gfm-strikethrough-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-ext-tables/0.64.8/flexmark-ext-tables-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-data/0.64.8/flexmark-util-data-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-ast/0.64.8/flexmark-util-ast-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-dependency/0.64.8/flexmark-util-dependency-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-misc/0.64.8/flexmark-util-misc-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-builder/0.64.8/flexmark-util-builder-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-sequence/0.64.8/flexmark-util-sequence-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-html/0.64.8/flexmark-util-html-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-visitor/0.64.8/flexmark-util-visitor-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-format/0.64.8/flexmark-util-format-0.64.8.jar",
        "repository/com/vladsch/flexmark/flexmark-util-collection/0.64.8/flexmark-util-collection-0.64.8.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "duckdb_jdbc-1.3.0.0",
    jars = ["repository/org/duckdb/duckdb_jdbc/1.3.0.0/duckdb_jdbc-1.3.0.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "openai-java-sdk",
    # do not sort: must match IML order
    jars = [
        "repository/com/openai/openai-java-core/4.32.0/openai-java-core-4.32.0.jar",
        "repository/com/openai/openai-java-client-okhttp/4.32.0/openai-java-client-okhttp-4.32.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "anthropic-java-sdk",
    # do not sort: must match IML order
    jars = [
        "repository/com/anthropic/anthropic-java-core/2.26.0/anthropic-java-core-2.26.0.jar",
        "repository/com/anthropic/anthropic-java-client-okhttp/2.26.0/anthropic-java-client-okhttp-2.26.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "anthropic-openai-common-deps",
    # do not sort: must match IML order
    jars = [
        "repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.18.2/jackson-datatype-jdk8-2.18.2.jar",
        "repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.18.2/jackson-datatype-jsr310-2.18.2.jar",
        "repository/com/squareup/okhttp3/okhttp/4.12.0/okhttp-4.12.0.jar",
        "repository/com/squareup/okhttp3/logging-interceptor/4.12.0/logging-interceptor-4.12.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "protobuf-kotlin_files",
    srcs = ["repository/com/google/protobuf/protobuf-kotlin/3.24.4/protobuf-kotlin-3.24.4.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "openai-sdk-test-deps",
    # do not sort: must match IML order
    jars = [
        "repository/com/github/victools/jsonschema-generator/4.38.0/jsonschema-generator-4.38.0.jar",
        "repository/com/github/victools/jsonschema-module-jackson/4.38.0/jsonschema-module-jackson-4.38.0.jar",
        "repository/com/github/victools/jsonschema-module-swagger-2/4.38.0/jsonschema-module-swagger-2-4.38.0.jar",
        "repository/io/swagger/core/v3/swagger-annotations/2.2.31/swagger-annotations-2.2.31.jar",
        "repository/com/fasterxml/classmate/1.7.0/classmate-1.7.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "junit-platform-commons-1.9.2",
    jars = ["repository/org/junit/platform/junit-platform-commons/1.9.2/junit-platform-commons-1.9.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "junit-platform-engine-1.9.2",
    jars = ["repository/org/junit/platform/junit-platform-engine/1.9.2/junit-platform-engine-1.9.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "shark-2.14",
    jars = ["repository/com/squareup/leakcanary/shark/2.14/shark-2.14.jar"],
    package_metadata = [
        ":leakcanary_license",
        ":leakcanary_metadata",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "shark-android-2.14",
    jars = ["repository/com/squareup/leakcanary/shark-android/2.14/shark-android-2.14.jar"],
    package_metadata = [
        ":leakcanary_license",
        ":leakcanary_metadata",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "shark-graph-2.14",
    jars = ["repository/com/squareup/leakcanary/shark-graph/2.14/shark-graph-2.14.jar"],
    package_metadata = [
        ":leakcanary_license",
        ":leakcanary_metadata",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "shark-hprof-2.14",
    jars = ["repository/com/squareup/leakcanary/shark-hprof/2.14/shark-hprof-2.14.jar"],
    package_metadata = [
        ":leakcanary_license",
        ":leakcanary_metadata",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "shark-log-2.14",
    jars = ["repository/com/squareup/leakcanary/shark-log/2.14/shark-log-2.14.jar"],
    package_metadata = [
        ":leakcanary_license",
        ":leakcanary_metadata",
    ],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "lucene-10.3.0",
    srcs = [
        "repository/org/apache/lucene/lucene-analysis-common/10.3.0/lucene-analysis-common-10.3.0.jar",
        "repository/org/apache/lucene/lucene-backward-codecs/10.3.0/lucene-backward-codecs-10.3.0.jar",
        "repository/org/apache/lucene/lucene-core/10.3.0/lucene-core-10.3.0.jar",
        "repository/org/apache/lucene/lucene-facet/10.3.0/lucene-facet-10.3.0.jar",
        "repository/org/apache/lucene/lucene-highlighter/10.3.0/lucene-highlighter-10.3.0.jar",
        "repository/org/apache/lucene/lucene-memory/10.3.0/lucene-memory-10.3.0.jar",
        "repository/org/apache/lucene/lucene-queries/10.3.0/lucene-queries-10.3.0.jar",
        "repository/org/apache/lucene/lucene-queryparser/10.3.0/lucene-queryparser-10.3.0.jar",
        "repository/org/apache/lucene/lucene-sandbox/10.3.0/lucene-sandbox-10.3.0.jar",
    ],
    visibility = ["//tools/vendor/google/ml/aiplugin/rag:__subpackages__"],
)

# managed by go/iml_to_build
jvm_import(
    name = "grpc",
    # do not sort: must match IML order
    jars = [
        "repository/io/grpc/grpc-api/1.69.1/grpc-api-1.69.1.jar",
        "repository/io/grpc/grpc-auth/1.69.1/grpc-auth-1.69.1.jar",
        "repository/io/grpc/grpc-core/1.69.1/grpc-core-1.69.1.jar",
        "repository/io/grpc/grpc-kotlin-stub/1.4.1/grpc-kotlin-stub-1.4.1.jar",
        "repository/io/grpc/grpc-netty/1.69.1/grpc-netty-1.69.1.jar",
        "repository/io/grpc/grpc-netty-shaded/1.69.1/grpc-netty-shaded-1.69.1.jar",
        "repository/io/grpc/grpc-protobuf/1.69.1/grpc-protobuf-1.69.1.jar",
        "repository/io/grpc/grpc-protobuf-lite/1.69.1/grpc-protobuf-lite-1.69.1.jar",
        "repository/io/grpc/grpc-stub/1.69.1/grpc-stub-1.69.1.jar",
        "repository/io/grpc/grpc-context/1.69.1/grpc-context-1.69.1.jar",
        "repository/io/perfmark/perfmark-api/0.26.0/perfmark-api-0.26.0.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "collection-jvm-1.4.5",
    jars = ["repository/androidx/collection/collection-jvm/1.4.5/collection-jvm-1.4.5.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "litertlm-jvm",
    jars = ["repository/com/google/ai/edge/litertlm/litertlm-jvm/0.10.0/litertlm-jvm-0.10.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "google_api_services_testing",
    # do not sort: must match IML order
    jars = [
        "repository/com/google/apis/google-api-services-storage/v1-rev20230301-2.0.0/google-api-services-storage-v1-rev20230301-2.0.0.jar",
        "repository/com/google/apis/google-api-services-toolresults/v1beta3-rev20230410-2.0.0/google-api-services-toolresults-v1beta3-rev20230410-2.0.0.jar",
        "repository/com/google/apis/google-api-services-testing/v1-rev20250327-2.0.0/google-api-services-testing-v1-rev20250327-2.0.0.jar",
    ],
    visibility = ["//visibility:public"],
)

java_import(
    name = "error_prone_annotations-2.36.0",
    jars = ["repository/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "checker-compat-qual-2.5.5",
    jars = ["repository/org/checkerframework/checker-compat-qual/2.5.5/checker-compat-qual-2.5.5.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "auto-value-annotations-1.11.0",
    jars = ["repository/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "snakeyaml-engine-kmp-jvm-3.0.1",
    jars = ["repository/it/krzeminski/snakeyaml-engine-kmp-jvm/3.0.1/snakeyaml-engine-kmp-jvm-3.0.1.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "urlencoder-lib-jvm-1.5.0",
    jars = ["repository/net/thauvin/erik/urlencoder/urlencoder-lib-jvm/1.5.0/urlencoder-lib-jvm-1.5.0.jar"],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "coursier",
    jars = ["repository/io/get-coursier/coursier_2.13/2.1.8/coursier_2.13-2.1.8.jar"],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "coursier-interface",
    jars = ["repository/io/get-coursier/interface/1.0.19/interface-1.0.19.jar"],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "coursier-cache",
    jars = ["repository/io/get-coursier/coursier-cache_2.13/2.1.8/coursier-cache_2.13-2.1.8.jar"],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "coursier-core",
    jars = ["repository/io/get-coursier/coursier-core_2.13/2.1.8/coursier-core_2.13-2.1.8.jar"],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "coursier-util",
    jars = ["repository/io/get-coursier/coursier-util_2.13/2.1.8/coursier-util_2.13-2.1.8.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "kaml",
    jars = ["repository/com/charleskorn/kaml/kaml-jvm/0.61.0/kaml-jvm-0.61.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "wire-runtime-jvm-6.0.0",
    jars = ["repository/com/squareup/wire/wire-runtime-jvm/6.0.0/wire-runtime-jvm-6.0.0.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "mordant-jvm-2.7.2",
    jars = ["repository/com/github/ajalt/mordant/mordant-jvm/2.7.2/mordant-jvm-2.7.2.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "colormath-jvm-3.5.0",
    jars = ["repository/com/github/ajalt/colormath/colormath-jvm/3.5.0/colormath-jvm-3.5.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "tracing-desktop-2.0.0-alpha06",
    jars = ["repository/androidx/tracing/tracing-desktop/2.0.0-alpha06/tracing-desktop-2.0.0-alpha06.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "tracing-wire-desktop-2.0.0-alpha06",
    jars = ["repository/androidx/tracing/tracing-wire-desktop/2.0.0-alpha06/tracing-wire-desktop-2.0.0-alpha06.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "libktor-client-mock",
    jars = ["repository/io/ktor/ktor-client-mock-jvm/3.3.2/ktor-client-mock-jvm-3.3.2.jar"],
    visibility = ["//visibility:public"],
)
