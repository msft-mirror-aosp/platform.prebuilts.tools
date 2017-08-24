The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _d18ba5066e186383c31c24c2620f52775f6334a0_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _919e027fc3dc15da3724f7748ccaf96567b33775_)
* **x86**: x86/simpleperf (SHA1 _ce1614053490d57b092d9aed8e919a72399d2801_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _d130e89ce59fabe38e014f8ceebb99cd90f760e0_)

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf prebuilts source](https://android.googlesource.com/platform/prebuilts/simpleperf/+/master/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were obtained from AOSP tree [83d053d4136a6e46111f34e8a1dd15c7de6de2d6](https://android.googlesource.com/platform/prebuilts/simpleperf/+/e41e920370b6444549c67c5e85bbaa52cc4a5223).

