The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _1f7e0123ab80ce74dc893ca74abeeadcf4eef503_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _4a7872df6d00100518982e90f92dd81f4f7f629f_)
* **x86**: x86/simpleperf (SHA1 _c92175245e840a7f7a4ce61c95e72d3feccde183_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _195cf355401b03db4f65c9277ef82f248b2e811a_)

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf source code](https://android.googlesource.com/platform/system/extras/+/master/simpleperf/scripts/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were obtained from AOSP tree [6eca8eb1acb4ea4d70caa5fa553d7e4b9f19e54c](https://android.googlesource.com/platform/system/extras/+/d98c857902524791f7a050b5a4fc7cd9d969476f/).

