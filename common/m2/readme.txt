repository/ is a maven repository with libraries used to perform
certain tasks without accessing an external repository. e.g:
 - Compile tools/base, tools/swt and tools/buildSrc using Gradle
 - Convert artifacts from a maven repository to a p2 repository
   using the p2-maven plugin

Certain dependencies are using only during the build process,
but others are runtime dependencies that get shipped with the 
SDK Tools. Such runtime dependencies must include a NOTICE file
next to the artifact or the build will fail.

To add a new dependency, add it to tools/base/bazel/maven/artifacts.bzl and run the command:

   tools/base/bazel/maven/maven_fetch.sh

There is more information here:

   https://googleplex-android.git.corp.google.com/platform/tools/base/+/studio-master-dev/bazel/README.md#Additional-tools
