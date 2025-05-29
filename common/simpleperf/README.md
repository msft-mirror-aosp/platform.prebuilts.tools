# Simpleperf Prebuilt Binaries for Android (API < 29)

This directory provides prebuilt **Simpleperf binaries** for sideloading on Android devices with **API levels below 29**. The binaries are organized by architecture:

* **ARM**: `armeabi-v7a/simpleperf`
* **ARM64**: `arm64-v8a/simpleperf`
* **x86**: `x86/simpleperf`
* **x86_64**: `x86_64/simpleperf`

---

## Important Note:

The Simpleperf binaries included here are version **1.build.10661963**.
Version **1.build.10661963** is maintained due to newer versions (1.build.13464643) lacking support for API levels below 30.

For devices running **Android API level 29 and above**, the `simpleperf` binary already available at `/system/bin/simpleperf` **should be used**.