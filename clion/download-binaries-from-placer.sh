#!/bin/bash

# Download clangd binaries from placer at
# https://cnsviewer2.corp.google.com/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd
#
# Usage:
#
#   download-binaries-from-placer.sh [--nostrip] [--linux <build#>] [--mac <build#>] [--mac_arm64 <build#>] [--mac_x86_64 <build#>] [--win <build#>]
#
# To find the release number, go to http://go/as-clangd-kokoro and find the
# successful builds that you have triggered.

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
# Stripping, if enabled,  is only done for linux binaries.
STRIP_BINARIES=1

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
        MAC_UNIVERSAL_RELEASE="$2"
        shift 2
      else
        echo "Error: Argument for $1 is missing" >&2
        exit 1
      fi
      ;;
    --mac_arm64)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        MAC_ARM64_RELEASE="$2"
        shift 2
      else
        echo "Error: Argument for $1 is missing" >&2
        exit 1
      fi
      ;;
    --mac_x86_64)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        MAC_X86_64_RELEASE="$2"
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
    --nostrip)
        STRIP_BINARIES=0
        shift 1
      ;;
    -*|--*=)
      echo "Usage: download-binaries-from-placer.sh [--nostrip] [--linux <build#>] [--mac <build#>] [--mac_arm64 <build#>] [--mac_x86_64 <build#>] [--win <build#>]" >&2
      exit 1
      ;;
  esac
done

readonly BIN_DIR="${SCRIPT_DIR}/bin/clang"

if [[ -n "${LINUX_RELEASE}" ]]; then
  rm -rv "${BIN_DIR}/linux"
  mkdir -pv "${BIN_DIR}/linux/x64"
  fileutil cp -R -resume "/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/linux/release/${LINUX_RELEASE}/*/*" "${BIN_DIR}/linux/x64"
  rm -v "${BIN_DIR}/linux/x64"/*.intoto.jsonl
  chmod -v +x "${BIN_DIR}/linux/x64"/clangd
  if [[ "${STRIP_BINARIES}" == 1 ]]; then
    strip -v "${BIN_DIR}/linux/x64"/{clangd,libc++.so.1}
  else
    echo "Stripping is disabled"
  fi
fi

if [[ -n "${MAC_UNIVERSAL_RELEASE}" ]]; then
  # Do not delete the entire mac/ directory here, as it has aarch64/x64
  # subdirectories that should be controlled using other switches.
  rm -rv "${BIN_DIR}/mac/clangd"
  mkdir -pv "${BIN_DIR}/mac"
  fileutil cp -R -resume "/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/mac/release/${MAC_UNIVERSAL_RELEASE}/*/*" "${BIN_DIR}/mac"
  rm -v "${BIN_DIR}/mac"/*.intoto.jsonl
  chmod -v +x "${BIN_DIR}/mac"/clangd
fi

# Build has name mac_arm64, but the directory is called mac/aarch64.
if [[ -n "${MAC_ARM64_RELEASE}" ]]; then
  rm -rv "${BIN_DIR}/mac/aarch64"
  mkdir -pv "${BIN_DIR}/mac/aarch64"
  fileutil cp -R -resume "/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/mac_arm64/release/${MAC_ARM64_RELEASE}/*/*" "${BIN_DIR}/mac/aarch64"
  rm -v "${BIN_DIR}/mac/aarch64"/*.intoto.jsonl
  chmod -v +x "${BIN_DIR}/mac/aarch64"/clangd
fi

# Build has name mac_x86_64, but the directory is called mac/x64.
if [[ -n "${MAC_X86_64_RELEASE}" ]]; then
  rm -rv "${BIN_DIR}/mac/x64"
  mkdir -pv "${BIN_DIR}/mac/x64"
  fileutil cp -R -resume "/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/mac_x86_64/release/${MAC_X86_64_RELEASE}/*/*" "${BIN_DIR}/mac/x64"
  rm -v "${BIN_DIR}/mac/x64"/*.intoto.jsonl
  chmod -v +x "${BIN_DIR}/mac/x64"/clangd
fi

if [[ -n "${WIN_RELEASE}" ]]; then
  rm -rv "${BIN_DIR}/win"
  mkdir -pv "${BIN_DIR}/win/x64"
  fileutil cp -R -resume "/placer/prod/home/kokoro-dedicated/build_artifacts/prod/android-studio/clangd/win/release/${WIN_RELEASE}/*/*" "${BIN_DIR}/win/x64"
  rm -v "${BIN_DIR}/win/x64"/*.intoto.jsonl
fi
