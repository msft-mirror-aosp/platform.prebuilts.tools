The Linux bazel binary is a custom built binary on top of an existing release
version. The patch files in this directory contain the changes made to this
custom binary, with filenames specifying what version of bazel the patch was
made on. For example, 3.1.0_remote-hangs.patch was a patch applied to
bazel release tags/3.1.0.

Additional documentation
https://g3doc.corp.google.com/company/teams/android-studio/Infrastructure/update_bazel.md

## Patches

`3.1.0_remote-hangs.patch`
https://github.com/coeuvre/bazel/commit/6cd8bc333a83159cc4bb041686b923a16b4f0b37
https://github.com/coeuvre/bazel/commit/fb83b9815e329653a65be0d352963f6fdf9ff2fa
