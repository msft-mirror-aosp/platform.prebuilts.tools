# Updating to a new ART Distribution

****************************************************
IMPORTANT: Update PREBUILT file with the sha1 of ART
****************************************************

Checkout Android [AOSP](https://android-build.corp.google.com/repo-init/)

Either branch will work but the `Internal Main` branch might be smaller.

Setup and build:

```shell
source build/envsetup.sh
lunch full-trunk_staging-eng
export SOONG_ALLOW_MISSING_DEPENDENCIES=true
time m -j16 build-art-host
```

Delete artifacts in the art folder:
```shell
rm -fr 'bin/* com.android.i18n/ framework/ lib64/ share/
```

Copy the output from ART:
```shell
cd out/host/linux-x86
art=<YOUR_STUDIO_MAIN_REPO>/prebuilts/tools/linux-x86_64/art
cp bin/art $art/bin
cp bin/dalvikvm64 $art/bin
cp bin/dex2oatd64 $art/bin
cp bin/dex2oat64 $art/bin
cp -r lib64 $art
cp -r framework $art
cp -r com.android.i18n $art
mkdir $art/usr
cp -r usr/share $art
```

To verify, run:
```shell
bazel test --config=remote //prebuilts/tools/linux-x86_64/art:test-art
```

Update `PREBUILT`:
```shell
art=<YOUR_STUDIO_MAIN_REPO>/prebuilts/tools/linux-x86_64/art
cd <YOUR_AOSP_REPO>/art
sed -i "s/SHA1: .*/SHA1: `git rev-parse HEAD`/" $art/PREBUILT
```

Add to GIT:
```shell
git add -A
```

Commit and upload.
