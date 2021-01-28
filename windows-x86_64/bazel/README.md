The Windows bazel binary is a custom built binary on top of an existing release
version. The patch files in this directory contain the changes made to this
custom binary, with filenames specifying what version of bazel the patch was
made on. For example, 3.1.0_unprivileged-symlink.patch was a patch applied to
bazel release tags/3.1.0.

Additional documentation
https://g3doc.corp.google.com/company/teams/android-studio/Infrastructure/update_bazel.md#windows

## Patches

`3.1.0_windows-enable-symlinks.patch`
https://github.com/bazelbuild/bazel/commit/cffd417a4c4ec4d082f815f57a771ad1010972dd

`3.1.0_stack-size.patch`
https://github.com/bazelbuild/bazel/commit/08a15e795107ec76d574579223485a3efb9da274

`3.1.0_unprivileged-symlink.patch`
https://github.com/bazelbuild/bazel/commit/a6fccbb5f28972475f57821ac67db7dd993b2174

`3.1.0_remote-hangs.patch
https://github.com/coeuvre/bazel/commit/6cd8bc333a83159cc4bb041686b923a16b4f0b37
https://github.com/coeuvre/bazel/commit/fb83b9815e329653a65be0d352963f6fdf9ff2fa

