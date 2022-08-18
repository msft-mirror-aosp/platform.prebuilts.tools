The perfetto binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/[perfetto, traced, traced_probes]
* **ARM64**: arm64-v8a/[perfetto, traced, traced_probes]
* **x86**: x86/[perfetto, traced, traced_probes]
* **x86\_64**: x86\_64/[perfetto, traced, traced_probes]

The build produces 4 files.
* **libperfetto.so**: Library containing a common core shared by the 3 other binaries. This includes things like proto serialization, atrace execution, memory management.
* **perfetto**: Process that is used to trigger tracing and confgure tracing information.
* **traced**: Server to collect data collected by probes. The data collected is handled according to the config defined by the **perfetto** process.
* **traced_probes**: Client to do data collection. The client connects to the **traced** process and pushes collected data.

For more information see http://perfetto.dev

These binaries were built from source located in external/perfetto.

To build the binaries on linux/mac run the following from external/perfetto directory
Note: Alternative to manually running the scripts is to use ./build_and_copy.sh
```console
# This will take some time to download the NDK, llvm, gn, clang, ninja ect..
# Consumes ~4GB of disk.
./tools/install-build-deps

# Generate build configs for android arm, arm64, x86, x64
 ./tools/gn gen out/android_release_arm64 --args='target_os="android" is_clang=true is_debug=false target_cpu="arm64" extra_ldflags="-s"'
 ./tools/gn gen out/android_release_arm --args='target_os="android" is_clang=true is_debug=false target_cpu="arm" extra_ldflags="-s"'
 ./tools/gn gen out/android_release_x86 --args='target_os="android" is_clang=true is_debug=false target_cpu="x86" extra_ldflags="-s"'
 ./tools/gn gen out/android_release_x64 --args='target_os="android" is_clang=true is_debug=false target_cpu="x64" extra_ldflags="-s"'

# Run ninja on android directories generted in previous line.
# Note: The loop below is shorthand for ./tools/ninja -C ./out/android_release_* perfetto traced traced_probes
for i in $(ls out/ | grep android_*); do ./tools/ninja -C ./out/$i perfetto traced traced_probes; done;
```
