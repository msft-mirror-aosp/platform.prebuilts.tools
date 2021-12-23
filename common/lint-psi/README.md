This directory contains prebuilt dependencies for Lint CLI, including:

* IntelliJ Core (a small subset of IntelliJ Platform that includes Java PSI)
* Java UAST and Kotlin UAST (from the corresponding IDE plugins)
* Kotlin compiler

Since Lint also runs in Android Studio, the versions of these CLI dependencies should
generally match the corresponding versions used in Android Studio.


Updating
---
To update these artifacts, edit the dependency versions at the top of `build.sh` and then run:
```
CLEAN_BUILD=true ./build.sh
```
This will:
* Download a checkout of JetBrains/intellij-community and JetBrains/kotlin.
* Apply patches using the .diff files in this directory.
* Build the Kotlin compiler.
* Pack everything into jars using the tasks defined in `build.gradle.kts`.
* Copy the results here.

Note: the first time you do this it will download 1+ GB to check out
dependency sources. Subsequent builds will reuse these checkouts from the
`dependency-source-checkouts` directory---and the working trees will be cleaned
as needed to match the chosen IntelliJ/Kotlin versions.

Both `build.sh` and `build.gradle.kts` may require maintenance when updating
in order to react to changes in dependency build processes, module layout, etc.
You may also need to update the patch files if there are merge conflicts.

Tip: while iterating on an update you should omit `CLEAN_BUILD=true`.
Only use `CLEAN_BUILD=true` at the end when you are ready to upload new artifacts.
More details below.

Tip: the build scripts generate a versions.txt file that lists the dependencies that
are bundled into each jar. This is helpful for diffing and debugging jar contents.
Additionally, the individual jar entries are listed in the golden files for
`//tools/base/gmaven:tests`.


Incremental builds vs. clean builds
---
By default the build is fully incremental.

If you use `CLEAN_BUILD=true`, then the build scripts will additionally:

* Delete local Gradle caches (the `build` directories).
* Clean dependency checkouts using `git clean -fdx`.
* Disable the Gradle daemon via the `--no-daemon` flag.
* Disable the Gradle build cache via the `--no-build-cache` flag.
  This is especially important for the Kotlin compiler build, which normally
  caches task outputs in the Gradle user home.


Appendix: notes on the Kotlin compiler prebuilt
---

We cannot use the standard Kotlin compiler artifacts, because those bundle
an outdated version of IntelliJ Core. We used to solve this problem by editing
the Kotlin compiler build scripts to use the specific IntelliJ version we wanted
(and disabling shrinking to ensure we received the full IntelliJ Core dependency).
For a while this was easy to do because JetBrains maintained
'[bunch](https://github.com/JetBrains/bunches)' files which made the Kotlin compiler
compatible with each IntelliJ version that we cared about. However, after the
Kotlin IDE plugin was split off from the compiler project, the bunch files
no longer exist, and the Kotlinc build only support building against
a single IntelliJ version that tends to be relatively old. Now, upgrading the
IntelliJ version ourselves is difficult because it requires nontrival changes
in the Kotlinc sources and build scripts. So, instead we keep the Kotlin compiler
build untouched and bundle our own IntelliJ artifact separately afterward.
This is actually similar to what JetBrains does when bundling Kotlin compiler
classes in the IDE plugin. For example: they currently build the Kotlin compiler
against IJ 203 but then bundle those compiler classes in an IDE plugin which
runs with IJ 213. Binary incompatibilities are unlikely because the Kotlin
compiler only uses a handful of stable IntelliJ APIs (mainly for Java parsing).
If there are binary incompatibilities, then JetBrains will hit them before we do.

When JetBrains bundles Kotlin compiler classes in the Kotlin IDE plugin, they
use a special artifact called `org.jetbrains.kotlin:kotlin-compiler-for-ide`
which is similar to the normal Kotlin compiler artifact except it excludes
IntelliJ classes. This artifact is pretty close to what we want for Lint too,
so we just use it directly for now.

One inconvenience is that `kotlin-compiler-for-ide` does not include all the
library dependencies that the Kotlin compiler needs at runtime. So, we have to
add them back manually. Luckily there are not too many; the needed libraries
are listed in the `fatJarContents` configuration in
`JetBrains/kotlin/prepare/compiler/build.gradle.kts`, and practice we do not
need all of them. We are leaning on Lint's test coverage here to catch any
missing dependencies.

Here are some alternative ideas that were considered:

* We could use the main Kotlinc jars, but manually strip out classes which look like
  IntelliJ classes based on the package name (hacky).

* We could just use the same IntelliJ version that Kotlinc uses, and give up on having
  an up-to-date IntelliJ version that matches what we use in Android Studio. This
  might require using an old Kotlin UAST version too, since Kotlin UAST needs to be
  compatible with the IntelliJ version. This also increases the risk that Lint
  behaves differently in CLI than in Android Studio.

* We could write a custom Gradle task which reuses the `fatJarContents` configuration in
  `JetBrains/kotlin/prepare/compiler/build.gradle.kts` but filters out IntelliJ dependencies.

* If we want even more control over what goes into our Kotlin compiler prebuilt,
  we could write a custom Gradle task similar to kotlin-compiler-for-ide,
  and exclude many of the Kotlin compiler modules that Lint does not need.


Appendix: notes on the IntelliJ prebuilt
---
We previously used com.jetbrains.intellij.idea:intellij-core to populate intellij-core.jar.
But that artifact is deprecated, and now JetBrains recommends using IntelliJ modules directly.
For our purposes, the java-psi-impl module pulls in everything we need.
