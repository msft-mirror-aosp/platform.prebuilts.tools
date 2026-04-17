This directory contains prebuilt dependencies for Lint CLI, including:

* IntelliJ Core (a small subset of IntelliJ Platform that includes Java PSI)
* Java UAST and Kotlin UAST (from the corresponding IDE plugins)
* Kotlin compiler

Since Lint also runs in Android Studio, the versions of these CLI dependencies should
generally match the corresponding versions used in Android Studio.


Building
---
To (re)build the artifacts, run:
```
CLEAN_BUILD=true ./build.sh
```
This will:
* Download a checkout of `JetBrains/kotlin` and `JetBrains/intellij-community`.
* Apply the patches in `kotlin-patches/` and `intellij-patches/`.
* Build the Kotlin compiler.
* Build IntelliJ jars via Bazel.
* Pack Kotlin jars using `build.gradle.kts`.
* Copy the final artifacts into `intellij-core/`, `kotlin-compiler/`, and `uast/`.
* Patch the bytecode of the Kotlin jar using `jarrewriter/` because the Kotlin repo
is still building against an old version of IntelliJ. Without this, the standalone
analysis API code fails at runtime due to IntelliJ API changes for plugin XML reading
(https://youtrack.jetbrains.com/issue/KT-79870).

Note: the first time you do this it will download 1+ GB to check out
dependency sources. Subsequent builds will reuse these checkouts from the
`dependency-source-checkouts` directory---and the working trees will be cleaned
as needed to match the chosen IntelliJ/Kotlin versions.

Updating
---
To update the artifacts, edit the dependency versions at the top of `build.sh`,
and then build (as above). The patches will most likely fail to apply. Try using
the following recipe to:
 * Apply the patches on top of the old SHA (which should always work).
 * Rebase on top of the new SHA (which may fail, but can be handled like a
   normal git rebase).
 * Recreate the patches using the rebased commits.

```bash
OLD_SHA=?
NEW_SHA=?
PATCHES=/path/to/patches

# From dependency-source-checkouts/intellij or dependency-source-checkouts/kotlin:

git branch old_sha $OLD_SHA
git branch new_sha $NEW_SHA

git branch patches old_sha

git switch patches

git am "$PATCHES"/*.patch

git rebase --onto new_sha old_sha patches

# Once the rebase looks good:

rm "$PATCHES"/*.patch
git format-patch new_sha -o "$PATCHES"
```

Both `build.sh` and `build.gradle.kts` may require maintenance when updating
in order to react to changes in dependency build processes, module layout, etc.

Tip: while iterating on an update you should omit `CLEAN_BUILD=true`.
Only use `CLEAN_BUILD=true` at the end when you are ready to upload new artifacts.
More details below.

Tip: `build.gradle.kts` generates a versions.txt file that lists the dependencies that
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


Using custom dependency sources
---
When running `build.sh`, you can set the environment variables `CUSTOM_KOTLIN_DIR`
and/or `CUSTOM_INTELLIJ_DIR` in order to build from a local fork of those dependencies.
This is useful for iterating on patches and upstream work. When using custom dependency
checkouts, the corresponding worktree is left unchanged (no patches are applied).

`CUSTOM_KOTLIN_DIR` should point to a checkout of the Kotlin compiler (JetBrains/kotlin)
and `CUSTOM_INTELLIJ_DIR` should point to a checkout of IntelliJ (JetBrains/intellij-community).
You should use absolute paths in all cases.


Appendix: notes on the Kotlin compiler prebuilt
---

We cannot use the standard Kotlin compiler artifacts because those bundle
an outdated version of IntelliJ Core.

<details>
<summary>(The old way)</summary>
We used to solve this problem by editing
the Kotlin compiler build scripts to use the specific IntelliJ version we wanted
(and disabling shrinking to ensure we received the full IntelliJ Core dependency).
For a while this was easy to do because JetBrains maintained
'[bunch](https://github.com/JetBrains/bunches)' files which made the Kotlin compiler
compatible with each IntelliJ version that we cared about. However, after the
Kotlin IDE plugin was split off from the compiler project, the bunch files
no longer exist, and the Kotlinc build only support building against
a single IntelliJ version that tends to be relatively old. Now, upgrading the
IntelliJ version ourselves is difficult because it requires nontrival changes
in the Kotlinc sources and build scripts.
</details>

Instead, we keep the Kotlin compiler build untouched and bundle our own IntelliJ
artifact separately. This is actually similar to what JetBrains does when
bundling Kotlin compiler classes in the IDE plugin. Binary incompatibilities are
unlikely because the Kotlin compiler only uses a handful of stable IntelliJ APIs
(mainly for Java parsing). If there are binary incompatibilities, JetBrains
should hit them before we do.

> Unfortunately, Kotlin is still building against an old version of
IntelliJ, and there _are_ currently some incompatibilities. These presumably are
not a problem for the Kotlin IntelliJ Plugin, as they only trigger when using
the standalone analysis API code. We currently patch the bytecode of the Kotlin
jar using `jarrewriter/`. Without this, the standalone analysis API code fails
at runtime due to IntelliJ API changes for plugin XML reading
(https://youtrack.jetbrains.com/issue/KT-79870).

The Kotlin repo contains a number of modules ending in "-for-ide". We include
some of these in our `build.gradle.kts` script, along with some dependencies.
Some dependencies can be found in the `fatJarContents` configuration in
`JetBrains/kotlin/prepare/compiler/build.gradle.kts`. In practice, we do not
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

