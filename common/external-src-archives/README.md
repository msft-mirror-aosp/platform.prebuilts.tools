These are archives containing the sources of dependencies required to build.

They can be used to create repositories using Bazel's http\_archive, like:
```
  http_archive(
    "name": "my-dependency",
    "url": "file:///prebuilts/tools/common/external-src-archives/mydep/1.0.0/mydep-1.0.0.zip",
    # sha256 to verify archive integrity
    "sha256": "...",
    # optional build file in case the archive file doesn't contain one already.
    "build_file": "...",
  )
```
