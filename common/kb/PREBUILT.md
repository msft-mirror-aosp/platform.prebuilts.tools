# About dac.zip file

Android Knowledge Base (AKB) is live [in google3](
http://google3/devrel/android/knowledge_base/;rcl=804771441). You can get `dac.zip` file by running
```shell
g4d -f <CLIENT NAME>  # replace <CLIENT NAME> with your client name
blaze build //devrel/android/knowledge_base:latest_kb_archive
```
You can check the artifact in
`blaze-bin/devrel/android/knowledge_base/kb.zip`.

We will package the zip file into a Rapid project and put it in the Lorry. Then, we can download it
in Android Studio (via a subpath of dl.google.com). However, we still need `dac.zip` file under this
`resources` directory for the case where the download is not available (e.g., internet connection
issue) as a fallback.