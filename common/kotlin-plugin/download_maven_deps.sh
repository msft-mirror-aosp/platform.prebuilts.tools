#!/usr/bin/env bash
set -eu

if [[ $# -ne 1 ]]; then
    echo "Please specify the Kotlin version to download"
    exit 1
fi

VERSION="$1"
bazel run //tools/base/bazel:add_dependency -- \
    --repo="https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev" \
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$VERSION" \
    "org.jetbrains.kotlin:kotlin-reflect:$VERSION" \
    "org.jetbrains.kotlin:kotlin-script-runtime:$VERSION" \
    "org.jetbrains.kotlin:kotlin-test:$VERSION" \
    "org.jetbrains.kotlin:kotlin-android-extensions-runtime:$VERSION" \
    "org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:$VERSION"
