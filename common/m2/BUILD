load("//tools/adt/idea/studio:studio.bzl", "studio_data")
load("//tools/base/bazel:jvm_import.bzl", "jvm_import")

config_setting(
    name = "osx-arm64",
    values = {"host_cpu": "darwin_arm64"},
    visibility = ["//visibility:public"],
)

config_setting(
    name = "osx-x86_64",
    values = {"host_cpu": "darwin"},
    visibility = ["//visibility:public"],
)

config_setting(
    name = "windows-x86_64",
    values = {"host_cpu": "x64_windows"},
    visibility = ["//visibility:public"],
)

config_setting(
    name = "linux-x86_64",
    values = {"host_cpu": "k8"},
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
    name = "io.grpc.grpc-api.1.57.0",
    jars = ["repository/io/grpc/grpc-api/1.57.0/grpc-api-1.57.0.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "io.grpc.protoc-gen-grpc-java.1.57.0_exe",
    srcs = select({
        "//prebuilts/tools/common/m2:osx-arm64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-osx-aarch_64.exe"],
        "//prebuilts/tools/common/m2:osx-x86_64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-osx-x86_64.exe"],
        "//prebuilts/tools/common/m2:windows-x86_64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-windows-x86_64.exe"],
        "//prebuilts/tools/common/m2:linux-x86_64": ["repository/io/grpc/protoc-gen-grpc-java/1.57.0/protoc-gen-grpc-java-1.57.0-linux-x86_64.exe"],
    }),
    visibility = ["//visibility:public"],
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

aar_import(
    name = "androidx.inspection.inspection.1.0.0",
    aar = "repository/androidx/inspection/inspection/1.0.0/inspection-1.0.0.aar",
    visibility = ["//visibility:public"],
)

aar_import(
    name = "androidx-monitor",
    aar = "repository/androidx/test/monitor/1.6.1/monitor-1.6.1.aar",
    visibility = ["//visibility:public"],
)

java_import(
    name = "javax.annotation.javax.annotation-api.1.3.2",
    jars = ["repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar"],
    visibility = ["//visibility:public"],
)

java_import(
    name = "com.google.android.gms.play-services-location.11.8.0",
    jars = ["repository/com/google/android/gms/play-services-location/11.8.0/play-services-location-11.8.0.jar"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.3.19.6_exe",
    srcs = select({
        "//prebuilts/tools/common/m2:osx-arm64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-osx-aarch_64.exe"],
        "//prebuilts/tools/common/m2:osx-x86_64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-osx-x86_64.exe"],
        "//prebuilts/tools/common/m2:windows-x86_64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-windows-x86_64.exe"],
        "//prebuilts/tools/common/m2:linux-x86_64": ["repository/com/google/protobuf/protoc/3.19.6/protoc-3.19.6-linux-x86_64.exe"],
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

# Manually extrated proto files from the jar
# So bazel can consume them without wrapping the
# proto compiler in a extract jar step.
filegroup(
    name = "com.google.protobuf.protobuf-java.3.19.6.include_include",
    srcs = glob(["repository/com/google/protobuf/protobuf-java/3.19.6/**/*.proto"]),
    visibility = ["//visibility:public"],
)

filegroup(
    name = "com.google.protobuf.protoc.3.22.3_exe",
    srcs = select({
        "//prebuilts/tools/common/m2:osx-arm64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-osx-aarch_64.exe"],
        "//prebuilts/tools/common/m2:osx-x86_64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-osx-x86_64.exe"],
        "//prebuilts/tools/common/m2:windows-x86_64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-windows-x86_64.exe"],
        "//prebuilts/tools/common/m2:linux-x86_64": ["repository/com/google/protobuf/protoc/3.22.3/protoc-3.22.3-linux-x86_64.exe"],
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

# Manually extrated proto files from the jar
# So bazel can consume them without wrapping the
# proto compiler in a extract jar step.
filegroup(
    name = "com.google.protobuf.protobuf-java.3.22.3.include_include",
    srcs = glob(["repository/com/google/protobuf/protobuf-java/3.22.3/**/*.proto"]),
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
    srcs = ["repository/nl/jqno/equalsverifier/equalsverifier/3.15.6/equalsverifier-3.15.6.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "mockito_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/mockito/mockito-core/5.6.0/mockito-core-5.6.0.jar",
        "repository/net/bytebuddy/byte-buddy/1.14.8/byte-buddy-1.14.8.jar",
        "repository/net/bytebuddy/byte-buddy-agent/1.14.8/byte-buddy-agent-1.14.8.jar",
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
    jars = ["repository/com/squareup/javapoet/1.10.0/javapoet-1.10.0.jar"],
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
    name = "commons-lang",
    jars = ["repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar"],
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
    name = "HdrHistogram_files",
    srcs = ["repository/org/hdrhistogram/HdrHistogram/2.1.12/HdrHistogram-2.1.12.jar"],
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
    srcs = ["repository/org/xerial/sqlite-jdbc/3.34.0/sqlite-jdbc-3.34.0.jar"],
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
filegroup(
    name = "kotlinx-coroutines-guava_files",
    srcs = ["repository/org/jetbrains/kotlinx/kotlinx-coroutines-guava/1.6.4/kotlinx-coroutines-guava-1.6.4.jar"],
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
    name = "JSch",
    jars = ["repository/com/jcraft/jsch/0.1.55/jsch-0.1.55.jar"],
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
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "ffmpeg-platform_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/bytedeco/ffmpeg-platform/6.0-1.5.9/ffmpeg-platform-6.0-1.5.9.jar",
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-linux-x86_64.jar",
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-macosx-arm64.jar",
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-macosx-x86_64.jar",
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-windows-x86_64.jar",
        "repository/org/bytedeco/javacpp-platform/1.5.9/javacpp-platform-1.5.9.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-linux-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-macosx-arm64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-macosx-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-windows-x86_64.jar",
    ],
    visibility = ["//visibility:public"],
)

studio_data(
    name = "ffmpeg-platform-bundle",
    files = [
        "repository/org/bytedeco/ffmpeg-platform/6.0-1.5.9/ffmpeg-platform-6.0-1.5.9.jar",
        "repository/org/bytedeco/javacpp-platform/1.5.9/javacpp-platform-1.5.9.jar",
    ],
    files_linux = [
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-linux-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-linux-x86_64.jar",
    ],
    files_mac = [
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-macosx-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-macosx-x86_64.jar",
    ],
    files_mac_arm = [
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-macosx-arm64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-macosx-arm64.jar",
    ],
    files_win = [
        "repository/org/bytedeco/ffmpeg/6.0-1.5.9/ffmpeg-6.0-1.5.9-windows-x86_64.jar",
        "repository/org/bytedeco/javacpp/1.5.9/javacpp-1.5.9-windows-x86_64.jar",
    ],
    mappings = {
        "prebuilts/tools/common/m2/repository/org/bytedeco/ffmpeg-platform/6.0-1.5.9/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/ffmpeg/6.0-1.5.9/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/javacpp-platform/1.5.9/": "",
        "prebuilts/tools/common/m2/repository/org/bytedeco/javacpp/1.5.9/": "",
    },
    visibility = ["//visibility:public"],
)

# This is the Kotlin compiler used for building our Kotlin sources in Bazel.
# It is declared here manually in order to avoid affecting version resolution
# for the main maven repo---because for various reasons our compiler version
# might not match the version of the kotlin-stdlib we want on the classpath.
jvm_import(
    name = "kotlin-compiler",
    jars = [
        # This list can be computed by querying the deps of
        # @maven//:org.jetbrains.kotlin.kotlin-compiler_<VERSION>
        # then resolving any duplicate versions to the highest version present.
        "repository/org/jetbrains/annotations/13.0/annotations-13.0.jar",
        "repository/org/jetbrains/intellij/deps/trove4j/1.0.20200330/trove4j-1.0.20200330.jar",
        "repository/org/jetbrains/kotlin/kotlin-compiler/1.9.21/kotlin-compiler-1.9.21.jar",
        "repository/org/jetbrains/kotlin/kotlin-reflect/1.6.10/kotlin-reflect-1.6.10.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.9.21/kotlin-stdlib-jdk8-1.9.21.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib/1.9.21/kotlin-stdlib-1.9.21.jar",
        "repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.9.21/kotlin-stdlib-jdk7-1.9.21.jar",
        "repository/org/jetbrains/kotlin/kotlin-script-runtime/1.9.21/kotlin-script-runtime-1.9.21.jar",
        "repository/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.5.0/kotlinx-coroutines-core-jvm-1.5.0.jar",
        "repository/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.5.0/kotlinx-coroutines-core-1.5.0.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "jvm-abi-gen-plugin",
    jars = [
        "repository/org/jetbrains/kotlin/jvm-abi-gen/1.9.21/jvm-abi-gen-1.9.21.jar",
    ],
    visibility = ["//visibility:public"],
)

jvm_import(
    name = "compose-compiler-hosted",
    jars = [
        "repository/androidx/compose/compiler/compiler-hosted/1.5.7/compiler-hosted-1.5.7.jar",
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
    visibility = ["//tools/base/bazel:__pkg__"],
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
filegroup(
    name = "google-dexlib2_files",
    srcs = ["repository/com/android/tools/smali/smali-dexlib2/3.0.3/smali-dexlib2-3.0.3.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "google-baksmali_files",
    # do not sort: must match IML order
    srcs = [
        "repository/com/android/tools/smali/smali-baksmali/3.0.3/smali-baksmali-3.0.3.jar",
        "repository/com/android/tools/smali/smali-util/3.0.3/smali-util-3.0.3.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "commonmark-0.20.0",
    jars = ["repository/org/commonmark/commonmark/0.20.0/commonmark-0.20.0.jar"],
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
        "repository/com/google/auth/google-auth-library-oauth2-http/1.4.0/google-auth-library-oauth2-http-1.4.0.jar",
        "repository/com/google/oauth-client/google-oauth-client/1.34.1/google-oauth-client-1.34.1.jar",
        "repository/com/google/oauth-client/google-oauth-client-java6/1.34.1/google-oauth-client-java6-1.34.1.jar",
        "repository/com/google/auth/google-auth-library-credentials/1.4.0/google-auth-library-credentials-1.4.0.jar",
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
        "repository/com/google/oauth-client/google-oauth-client/1.34.1/google-oauth-client-1.34.1.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
filegroup(
    name = "tomlj_files",
    # do not sort: must match IML order
    srcs = [
        "repository/org/tomlj/tomlj/1.1.0/tomlj-1.1.0.jar",
        "repository/org/antlr/antlr4-runtime/4.11.1/antlr4-runtime-4.11.1.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "google-api-services-sourcerepo",
    jars = ["repository/com/google/apis/google-api-services-sourcerepo/v1-rev20230123-2.0.0/google-api-services-sourcerepo-v1-rev20230123-2.0.0.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "jewel-ide",
    # do not sort: must match IML order
    jars = [
        "repository/org/jetbrains/jewel/jewel-foundation/0.13.2/jewel-foundation-0.13.2.jar",
        "repository/org/jetbrains/jewel/jewel-ide-laf-bridge/0.13.2-ij-233/jewel-ide-laf-bridge-0.13.2-ij-233.jar",
        "repository/org/jetbrains/jewel/jewel-ide-laf-bridge-platform-specific/0.13.2-ij-233/jewel-ide-laf-bridge-platform-specific-0.13.2-ij-233.jar",
        "repository/org/jetbrains/jewel/jewel-ui/0.13.2/jewel-ui-0.13.2.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "compose-desktop",
    # do not sort: must match IML order
    jars = [
        "repository/org/jetbrains/compose/animation/animation-desktop/1.6.0-dev1369/animation-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/animation/animation-core-desktop/1.6.0-dev1369/animation-core-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/runtime/runtime-saveable-desktop/1.6.0-dev1369/runtime-saveable-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/runtime/runtime-desktop/1.6.0-dev1369/runtime-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/foundation/foundation-layout-desktop/1.6.0-dev1369/foundation-layout-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/foundation/foundation-desktop/1.6.0-dev1369/foundation-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-unit-desktop/1.6.0-dev1369/ui-unit-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-text-desktop/1.6.0-dev1369/ui-text-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-util-desktop/1.6.0-dev1369/ui-util-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-graphics-desktop/1.6.0-dev1369/ui-graphics-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-geometry-desktop/1.6.0-dev1369/ui-geometry-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-desktop/1.6.0-dev1369/ui-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/skiko/skiko-awt/0.7.85.5/skiko-awt-0.7.85.5.jar",
        "repository/org/jetbrains/skiko/skiko-awt-runtime-linux-x64/0.7.85.4/skiko-awt-runtime-linux-x64-0.7.85.4.jar",
        "repository/org/jetbrains/skiko/skiko-awt-runtime-macos-arm64/0.7.85.4/skiko-awt-runtime-macos-arm64-0.7.85.4.jar",
        "repository/org/jetbrains/skiko/skiko-awt-runtime-macos-x64/0.7.85.4/skiko-awt-runtime-macos-x64-0.7.85.4.jar",
        "repository/org/jetbrains/skiko/skiko-awt-runtime-windows-x64/0.7.85.4/skiko-awt-runtime-windows-x64-0.7.85.4.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "jewel-standalone",
    jars = ["repository/org/jetbrains/jewel/jewel-int-ui-standalone/0.13.2/jewel-int-ui-standalone-0.13.2.jar"],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "compose-desktop-test",
    # do not sort: must match IML order
    jars = [
        "repository/org/jetbrains/compose/ui/ui-test-junit4-desktop/1.6.0-dev1369/ui-test-junit4-desktop-1.6.0-dev1369.jar",
        "repository/org/jetbrains/compose/ui/ui-test-desktop/1.6.0-dev1369/ui-test-desktop-1.6.0-dev1369.jar",
    ],
    visibility = ["//visibility:public"],
)

# managed by go/iml_to_build
jvm_import(
    name = "google_api_services_testing",
    # do not sort: must match IML order
    jars = [
        "repository/com/google/apis/google-api-services-storage/v1-rev20230301-2.0.0/google-api-services-storage-v1-rev20230301-2.0.0.jar",
        "repository/com/google/apis/google-api-services-toolresults/v1beta3-rev20230410-2.0.0/google-api-services-toolresults-v1beta3-rev20230410-2.0.0.jar",
        "repository/com/google/apis/google-api-services-testing/v1-rev20240213-2.0.0/google-api-services-testing-v1-rev20240213-2.0.0.jar",
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
        "repository/com/google/apis/google-api-services-storage/v1-rev20230301-2.0.0/google-api-services-storage-v1-rev20230301-2.0.0.jar",
        "repository/com/google/apis/google-api-services-toolresults/v1beta3-rev20230410-2.0.0/google-api-services-toolresults-v1beta3-rev20230410-2.0.0.jar",
        "repository/com/google/apis/google-api-services-testing/v1-rev20240213-2.0.0/google-api-services-testing-v1-rev20240213-2.0.0.jar",
    ],
    visibility = ["//visibility:public"],
)
