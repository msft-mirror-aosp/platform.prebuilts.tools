# Gemini Coder Prebuilts

This directory contains prebuilt binaries for Gemini Coder components.

---

## 1. Gemini Coder Language Server

> **Note:** We should get rid of these language server prebuilts once we land all the support required for Q2 internal teamfooding. They will be superseded by LocalHarness binaries.

These are the commands that are used to build the language server binaries from google3. The commands were taken from https://source.corp.google.com/h/neural-gui-internal/Exafunction/+/maina:kokoro/rbe/3p-ls.cfg

## Linux x86_64

```bash
blaze build //third_party/jetski/cmd/language_server:language_server.external \
  --compilation_mode=opt \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  --features=-enable_mavx \
  --features=-enable_relr \
  --//tools/build_defs/go/internal:default_go_rpc_library_do_not_use_without_permission=//third_party/golang/grpc:grpc \
  --//tools/build_defs/go/internal:default_go_proto_toolchain_do_not_use_without_permission=//tools/proto/toolchains:go_grpc
```

## Windows x86_64

```bash
blaze build //third_party/jetski/cmd/language_server:language_server.external \
  --compilation_mode=opt \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  --config=windows_x86_64 \
  --features=-enable_mavx \
  --//tools/build_defs/go/internal:default_go_rpc_library_do_not_use_without_permission=//third_party/golang/grpc:grpc \
  --//tools/build_defs/go/internal:default_go_proto_toolchain_do_not_use_without_permission=//tools/proto/toolchains:go_grpc
```

## Mac x86_64

```bash
blaze build //third_party/jetski/cmd/language_server:language_server.external \
  --compilation_mode=opt \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  --config=darwin_x86_64 \
  --macos_minimum_os=12.0 \
  --//tools/build_defs/go/internal:default_go_rpc_library_do_not_use_without_permission=//third_party/golang/grpc:grpc \
  --//tools/build_defs/go/internal:default_go_proto_toolchain_do_not_use_without_permission=//tools/proto/toolchains:go_grpc
```

## Mac arm_64

```bash
blaze build //third_party/jetski/cmd/language_server:language_server.external \
  --compilation_mode=opt \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  --config=darwin_arm64 \
  --macos_minimum_os=12.0 \
  --//tools/build_defs/go/internal:default_go_rpc_library_do_not_use_without_permission=//third_party/golang/grpc:grpc \
  --//tools/build_defs/go/internal:default_go_proto_toolchain_do_not_use_without_permission=//tools/proto/toolchains:go_grpc
```

---

## 2. Jetski Local Harness

These are the commands used to build the local harness binaries from google3.
Derived from `third_party/jetski_prod/sdk/py/.kokoro/internal/build_release_wheel.sh`.

> **Note:** The latest prebuilts added are from google3 synced to change [cl/942053677](cl/942053677).

### Linux x86_64
```bash
blaze build //third_party/jetski_prod/localharness:localharness_external \
  --config=gce \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  -c opt \
  --features=-enable_relr
```


### Mac arm64 (Apple Silicon)
```bash
blaze build //third_party/jetski_prod/localharness:localharness_external \
  --config=darwin_arm64 \
  --go_tag=external \
  --target_environment=//buildenv/target:non_prod \
  -c opt
```
