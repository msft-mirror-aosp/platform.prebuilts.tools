This is a prebuilt distribution of the ide-perf plugin (https://github.com/google/ide-perf).

It is here so that debug builds of Android Studio can have ide-perf installed out-of-the-box
(via a -Dplugin.path JVM flag pointing into this directory).

To update the plugin:
* Download [ide-perf](https://github.com/google/ide-perf) and build it with `./gradlew assemble`
* Look for a zip file under `build/distributions` and unzip it into this prebuilts directory
* Copy the commit SHA or version number into `ide-perf/build.txt`
