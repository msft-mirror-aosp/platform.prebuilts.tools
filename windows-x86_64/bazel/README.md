The Windows bazel binary is a custom built binary on top of an existing release
version. The patch files in this directory contain the changes made to this
custom binary, with filenames specifying what version of bazel the patch was
made on. For example, 3.1.0_unprivileged-symlink.patch would be a patch applied
to bazel release tags/3.1.0.

Additional documentation
https://g3doc.corp.google.com/company/teams/android-studio/Infrastructure/update_bazel.md#windows

## Patches

`4.0.0_downloads-in-progress.patch` resolves
https://github.com/bazelbuild/bazel/issues/12972
