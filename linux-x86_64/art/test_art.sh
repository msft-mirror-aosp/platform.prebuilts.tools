#!/bin/sh

set -e

art=prebuilts/tools/linux-x86_64/art

java -cp prebuilts/r8/r8.jar com.android.tools.r8.D8 --min-api 1 --output dex.jar "${art}/libtest-lib.jar"
"${art}/bin/art" --64  "-Xbootclasspath:${art}/framework/core-libart-hostdex.jar:${art}/framework/core-oj-hostdex.jar:${art}/framework/core-icu4j-hostdex.jar" -classpath dex.jar HelloWorld | tee stdout.txt

diff stdout.txt "${art}/expected-stdout.txt"
