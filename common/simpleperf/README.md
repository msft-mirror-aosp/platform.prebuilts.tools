The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture.

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf prebuilts source](https://android.googlesource.com/platform/prebuilts/simpleperf/+/master/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were downloaded from AOSP simpleperf prebuilts (commit [2087fac969d4743643d562fc521dd9fffb398e3c](https://android.googlesource.com/platform/prebuilts/simpleperf/+/2087fac969d4743643d562fc521dd9fffb398e3c/bin/android/)).

