The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _8bfb5a22a9bdd72d14dbca73abeeaa781c96f120_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _73369b68383a614eb5ae1736fef0ea769c1de665_)
* **x86**: x86/simpleperf (SHA1 _06527a2d1c863e01a8f0feb60987b0b7ab0a90ac_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _abebbd139706d1f45ef9f0fd70cc6aee274a6820_)

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf prebuilts source](https://android.googlesource.com/platform/prebuilts/simpleperf/+/master/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were obtained from AOSP tree [ea762e1b99827e276f6059caea18f2feaa843c60](https://android.googlesource.com/platform/prebuilts/simpleperf/+/70aaa31245c7d12b0e679ce8acfffba3de3d9d54).

