The Android simpleperf binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM**: armeabi-v7a/simpleperf (SHA1 _76d5f1b95467469b548200c9dd70ac69fd73205b_)
* **ARM64**: arm64-v8a/simpleperf (SHA1 _2ae4a6c5df54848e364b1590b68aea6422c4d204_)
* **x86**: x86/simpleperf (SHA1 _55ae39556d62d02ba399889a5d833b9b840c4eab_)
* **x86\_64**: x86\_64/simpleperf (SHA1 _0b08527a7628248daab90212a7572317569ffcd6_)

The binaries should be obtained from [simpleperf source code](https://android.googlesource.com/platform/system/extras/+/master/simpleperf/scripts/bin/android/), and every time this is done the information below needs to be updated accordingly.

Current binaries were obtained from tree [8c87aaab0b468b3be199ca639f435023474b7b25](https://android.googlesource.com/platform/system/extras/+/f6915209b33025836a5135a79c6f429d8571b726/) and were updated in AOSP by simpleperf team in commit [376874](https://android-review.googlesource.com/#/c/376874/).

