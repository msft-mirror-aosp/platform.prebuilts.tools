# Gemini Coder Language Server Prebuilts

These are the commands that are used to build the binaries from google3:

```bash
blaze build \
  --go_tag=external \
  --define=cpu=linux_amd64 //third_party/jetski/cmd/language_server:language_server

blaze build \
  --go_tag=external \
  --config=windows //third_party/jetski/cmd/language_server:language_server

blaze build \
  --go_tag=external \
  --config=darwin_arm64 //third_party/jetski/cmd/language_server:language_server
```
