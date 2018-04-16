The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _b5a1ad4ddd34ee89c9c849cf6d4c67d534860ca0_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _a19318d15e371ce4accc2ce9291623d454fb7d37_)
* **x86**: x86/simpleperf (SHA1 _a806da7b84e1eadd32f2383f410a4a257c1ad851_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _dd77739b3e307bb450f579f386800cd3b776fcb8_)

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf prebuilts source](https://android.googlesource.com/platform/prebuilts/simpleperf/+/master/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were generated from AOSP source code (tree @ [5edcbb97e0eb453e33cfcf63cfdeba98c46859ed](https://android.googlesource.com/platform/system/extras/+/696377fe7516e1d540cb4b64ee025dc02ccf56b4/simpleperf/)).

