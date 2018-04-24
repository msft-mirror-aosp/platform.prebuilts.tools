The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _e59bab19fdfb910a056cd758a15206dc15d582e4_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _932c45c9bd96176aacc81cac6939a23d161da014_)
* **x86**: x86/simpleperf (SHA1 _cab750edbf71c3a45e9273a99d05d929bf712a4e_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _9dc74fd23d2e7adc45d080332b2da2c0865ac90f_)

There are two ways of obtaining the binaries:

1) Download the binaries directly from [simpleperf prebuilts source](https://android.googlesource.com/platform/prebuilts/simpleperf/+/master/bin/android/). In this case, please edit the paragraph on the bottom of this file mentioning the source tree where the files were obtained from and the AOSP commit that added them.

2) [Download and build](https://source.android.com/source/initializing) the latest (or a specific) version of AOSP, then copy the simpleperf binaries generated as artifacts to this directory. Also, update the text on the bottom of this file mentioning the AOSP source tree built.

Current binaries were downloaded from AOSP simpleperf prebuilts (tree @ [7fba37f8bf2509733858ee38dcb55182cf0c1058](https://android.googlesource.com/platform/prebuilts/simpleperf/+/9758ecc857a0a13dd8c01419b7561b467b703306/bin/android/)).

