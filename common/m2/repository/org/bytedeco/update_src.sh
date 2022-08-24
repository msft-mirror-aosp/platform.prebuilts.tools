#!/bin/bash

# Updates sources and license files for JavaCPP Presets For FFmpeg.
# When upgrading to a new JavaCPP version, please change the version variable
# in this file. Please don't delete the javacpp-presets-platform-*-src.zip files
# from prior JavaCPP versions since otherwise they would not be mirrored in
# https://android.googlesource.com/platform/prebuilts/tools/+/refs/heads/studio-main
#
# Usage: ./update_src.sh

version=1.5.7
mirror_url="https://android.googlesource.com/platform/prebuilts/tools/+/refs/heads/studio-main/common/m2/repository/org/bytedeco"

mkdir -p /tmp/update_src
rm -rf /tmp/update_src/*
pushd /tmp/update_src
wget https://github.com/bytedeco/javacpp-presets/releases/download/${version}/javacpp-presets-platform-${version}-src.zip
unzip -q javacpp-presets-platform-${version}-src.zip
zip -r -q -y javacpp-presets-platform-ffmpeg-${version}-src.zip javacpp-presets-platform-${version}/ffmpeg
popd
mv -f /tmp/update_src/javacpp-presets-platform-ffmpeg-${version}-src.zip .
mv -f /tmp/update_src/javacpp-presets-platform-${version}/ffmpeg/LICENSE.TXT ffmpeg-LICENSE.TXT
mv -f /tmp/update_src/javacpp-presets-platform-${version}/ffmpeg/LICENSE.md ffmpeg-LICENSE.md
mv -f /tmp/update_src/javacpp-presets-platform-${version}/ffmpeg/LICENSE.openh264 ffmpeg-LICENSE.openh264
echo "Source code for JavaCPP Presets For FFmpeg can be downloaded from ${mirror_url}/javacpp-presets-platform-ffmpeg-${version}-src.zip" >ffmpeg-SOURCE_CODE.TXT
rm -rf /tmp/update_src
