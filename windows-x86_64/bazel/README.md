The Windows bazel binary is a custom built binary on top of an existing release
verison. The patch files in this directory contain the changes made to this
custom binary, with filenames specifying what version of bazel the patch was
made on. For example 1.1.0_build-runfiles-windows.patch was a patch applied to
bazel release tags/1.1.0.

Additional documentation
https://g3doc.corp.google.com/company/teams/android-studio/Infrastructure/update_bazel.md#windows

## Patches

`1.1.0_build-runfiles-windows.patch`
https://github.com/bazelbuild/bazel/commit/a6fccbb5f28972475f57821ac67db7dd993b2174
