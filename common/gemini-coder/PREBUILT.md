# Gemini Coder Language Server Prebuilts

These are the commands that are used to build the binaries from google3:

```bash
# Windows
blaze build \
  --go_tag=external \
  --config=windows //third_party/jetski/cmd/language_server:language_server

# Mac Intel
blaze build \
  --go_tag=external \
  --config=darwin_x86_64 //third_party/jetski/cmd/language_server:language_server

# Mac Arm
blaze build \
  --go_tag=external \
  --config=darwin_arm64 //third_party/jetski/cmd/language_server:language_server

# Linux
# The _external target patches the binary to use the standard system interpreter instead of GRTE.
blaze build //third_party/jetski/cmd/language_server:language_server_external
```
