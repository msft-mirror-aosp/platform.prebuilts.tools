#!/usr/bin/env bash
set -eu

function fail {
    echo "ERROR: $@" >&2
    exit 1
}

[[ $# -eq 1 ]] || fail "Please specify a download URL from https://plugins.jetbrains.com/plugin/6954-kotlin/versions/staging"

# Find workspace root.
WORKSPACE="$PWD"
while [ ! -d "$WORKSPACE/.repo" ]; do
    [ "$WORKSPACE" != / ] || fail "Failed to find the workspace root"
    WORKSPACE=$(dirname "$WORKSPACE")
done
cd "$WORKSPACE"

# Download.
KT_DIR=prebuilts/tools/common/kotlin-plugin
ARCHIVE="$KT_DIR/kotlin-plugin-download.zip"
echo "Downloading to $ARCHIVE."
curl --silent --show-error -o "$ARCHIVE" --location "$1"

# Unzip.
echo "Deleting old version."
rm -rf "$KT_DIR/Kotlin"
echo "Unzipping."
unzip -qd "$KT_DIR" "$ARCHIVE"
rm "$ARCHIVE"

# Update intellij-sdk.
IJ_DIR=prebuilts/studio/intellij-sdk
IJ_VERSION="$(sed -n 's:.*src = "\(.*\)",:\1:p' < "$IJ_DIR/BUILD")"
echo "Updating intellij-sdk: $IJ_VERSION."
for PLATFORM in linux/android-studio windows/android-studio darwin/android-studio/Contents; do
    SDK_KOTLIN="$IJ_DIR/$IJ_VERSION/$PLATFORM/plugins/Kotlin"
    rm -rf "$SDK_KOTLIN"
    cp -r "$KT_DIR/Kotlin" "$SDK_KOTLIN"
done
./tools/adt/idea/studio/update_sdk.py --existing_version "$IJ_VERSION"

# Print out some metadata for the commit message.
echo "Done."
echo "------"
PLUGIN_XML="$(unzip -p "$KT_DIR/Kotlin/lib/kotlin-resources-descriptors.jar" META-INF/plugin.xml)"
echo "Compiler version: $(cat "$KT_DIR/Kotlin/kotlinc/build.txt")"
echo "Plugin version: $(sed -n 's:.*<version>\(.*\)</version>:\1:p' <<< "$PLUGIN_XML")"
echo "Compatibility: $(sed -n 's:.*<idea-version \(.*\)/>:\1:p' <<< "$PLUGIN_XML")"
