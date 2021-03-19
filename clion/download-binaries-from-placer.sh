#!/bin/bash

# Download clangd binaries from placer at
# https://cnsviewer2.corp.google.com/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd
#
# Usage:
#
#   download-binaries-from-placer.sh [--strip] [--linux <build#>] [--mac <build#>] [--win <build#>]
#
# To find the release number, go to http://go/as-clangd-kokoro and find the
# successful builds that you have triggered.

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
# Stripping, if enabled,  is only done for linux binaries.
STRIP_BINARIES=0

while (( "$#" )); do
  case "$1" in
    --linux)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        LINUX_RELEASE="$2"
        shift 2
      else
        echo "Error: Argument for $1 is missing" >&2
        exit 1
      fi
      ;;
    --mac)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        MAC_RELEASE="$2"
        shift 2
      else
        echo "Error: Argument for $1 is missing" >&2
        exit 1
      fi
      ;;
    --win)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        WIN_RELEASE="$2"
        shift 2
      else
        echo "Error: Argument for $1 is missing" >&2
        exit 1
      fi
      ;;
    --strip)
        STRIP_BINARIES=1
        shift 1
      ;;
    -*|--*=)
      echo "Usage: download-binaries-from-placer.sh [--strip] [--linux <build#>] [--mac <build#>] [--win <build#>]" >&2
      exit 1
      ;;
  esac
done

if [ -n "$LINUX_RELEASE" ]; then
  rm -r "$SCRIPT_DIR"/bin/clang/linux
  mkdir -p "$SCRIPT_DIR"/bin/clang/linux
  pushd "$SCRIPT_DIR"/bin/clang/linux
  fileutil cp -R -resume /placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/linux/release/"$LINUX_RELEASE"/'*/*' .
  chmod +x clangd
  chmod +x clang-tidy
  if [ $STRIP_BINARIES == 1 ]; then
    echo "Stripping Linux binaries"
    strip clangd
    strip clang-tidy
    strip libc++.so.1
  fi
  popd
fi
if [ -n "$MAC_RELEASE" ]; then
  rm -r "$SCRIPT_DIR"/bin/clang/mac
  mkdir -p "$SCRIPT_DIR"/bin/clang/mac
  pushd "$SCRIPT_DIR"/bin/clang/mac
  fileutil cp -R -resume /placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/mac/release/"$MAC_RELEASE"/'*/*' .
  chmod +x clangd
  chmod +x clang-tidy
  popd
fi
if [ -n "$WIN_RELEASE" ]; then
  rm -r "$SCRIPT_DIR"/bin/clang/win
  mkdir -p "$SCRIPT_DIR"/bin/clang/win
  pushd "$SCRIPT_DIR"/bin/clang/win
  fileutil cp -R -resume /placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/win/release/"$WIN_RELEASE"/'*/*' "$SCRIPT_DIR"/bin/clang/win
  popd
fi

