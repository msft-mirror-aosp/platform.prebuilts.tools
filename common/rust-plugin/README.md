This directory contains the Rust IDE plugin that is bundled in Android Studio for Platform.
These artifacts are built from the sources in `platform/external/jetbrains/intellij-rust`.

Building
---
To build the Rust IDE plugin and update these artifacts, run `./build.py`.

Outputs from Building
---
`prebuilts/tools/common/rust-plugin`:
    The unzipped plugin that can be referenced by IJ library
`prebuilts/tools/common/rust-plugin/rust-plugin-sources.jar`:
    Sources for the plugin that can be referenced by IJ library.
`prebuilts/tools/common/rust-plugin/METADATA`:
    Version information about the prebuilts from the last time
    they were updated.
`tools/adt/idea/.idea/libraries/rust_plugin.xml`:
    IJ library definition that references this plugin.