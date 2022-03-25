This directory contains the Kotlin IDE plugin that is bundled in Android Studio.
These artifacts are built from the sources in `platform/external/jetbrains/intellij-kotlin`.
The Kotlin IDE plugin also bundles the Kotlin compiler, which we build from the sources in
`platform/external/jetbrains/kotlin`.


Building
---
To build the Kotlin IDE plugin and update these artifacts, run `./build.py`.

Pass `--clean-build` to avoid reusing any previous build outputs.

Pass `--stage /path/to/out/dir` to prepare a Kotlin plugin zip without updating prebuilts.

Pass `--download BUILD_ID` to download a build from AB instead of building locally.
This is the official way to update these artifacts because it gives us verifiable builds.
Always use an AB build if the artifacts will end up in a public release.


Upstream release process
---
The Kotlin IDE plugin is developed in
[JetBrains/intellij-community](https://github.com/JetBrains/intellij-community),
primarily in `master`; most Kotlin-plugin features are timed with
IntelliJ IDEA releases. However, there are additional Kotlin-plugin updates
timed with Kotlin-compiler releases; these mostly just bundle the new compiler.

A Kotlin version has two parts: the IntelliJ Platform version, and the
Kotlin-compiler version. For example, version 213-1.6.0 denotes a Kotlin
plugin built for IntelliJ 213 (2021.3), bundling Kotlin compiler 1.6.0.

In order to release a Kotlin-plugin update for a stable version of IntelliJ,
JetBrains creates special "kt" branches. For example, Kotlin IDE plugin
213-1.6.0 is released from branch kt-213-1.6.0. Eventually we merge these Kotlin
releases into our fork at `platform/external/jetbrains/intellij-kotlin`.


Updating our fork
---
Merging a Kotlin update is not quite as simple as running `git merge`. This is
because we merge _releases_, which may contain changes that should not
be carried forward to the next version. To illustrate, let's say we are
updating from kt-213-1.6.10 to kt-221-1.6.10. We can call these `$OLD_UPSTREAM`
and `$NEW_UPSTREAM`, respectively. The branching structure would look like this:
```
-------------------------.------------------------studio-main
                        /
          .------$OLD_UPSTREAM      .------$NEW_UPSTREAM
         /                         /
----merge-base------------------------------------upstream-master
```
In this case, we essentially want to drop all changes between `merge-base`
and `$OLD_UPSTREAM` when merging `$NEW_UPSTREAM` into studio-main. However, we
still want to preserve our own patches, and we still want to end up with a
normal-looking merge commit parented by `studio-main` and `$NEW_UPSTREAM`.

There are several ways to accomplish this. One possible strategy is to rebase our
patches on top of `$NEW_UPSTREAM`, and then use `git-commit-tree` to synthesize a
merge commit. That would look roughly like this:
```bash
# Consolidate all our patches into a single commit.
PATCHES="$(git commit-tree -p "$OLD_UPSTREAM" -m patches m/studio-main^{tree})"

# Reapply our patches to the new Kotlin version.
git checkout --detach "$NEW_UPSTREAM"
git cherry-pick "$PATCHES"

# Synthesize the merge commit.
git commit-tree -p m/studio-main -p "$NEW_UPSTREAM" -m merge HEAD^{tree}
```
In practice it might be helpful to drop some of our patches before starting this
process. For example, changes that run the project-model-updater
(e.g. [Change I689141bb0](http://ag/I689141bb0)) should be reverted in order
to avoid merge conflicts.

In the end, there should be a single merge commit parented by both `studio-main`
and `$NEW_UPSTREAM`. The output of `git diff $NEW_UPSTREAM HEAD` should be small,
and it should only show our own patches. The output of `git show` on the merge
commit should ideally show that no new lines were added (belonging to neither parent);
if resolving some merge conflict would require adding new lines, then it is generally
better to drop the entire conflicting commit on our side, and reapply it in a separate
commit after the merge.

To upload the merge commit to Gerrit, you will first need to push `$NEW_UPSTREAM` to
to the `upstream-master` branch. This is because Gerrit will create a new change for
every commit that is not an ancestor of any of its branches---and we do not want
upstream commits to go through the normal code-review process. Note, there is a chance that
Gerrit will block the upload if any commit happens to contain a banned word. It is okay
to bypass this check for third-party code. To do so, use `-o uploadvalidator~skip`
when uploading.


Appendix: alternative merge strategy
---
Another merge strategy is to merge `$OLD_UPSTREAM` into `$NEW_UPSTREAM`, and then
merge _that_ into `studio-main`:
```
git merge $(git commit-tree -p $OLD_UPSTREAM -p $NEW_UPSTREAM -m snap $NEW_UPSTREAM^{tree})
```
Then use `git-commit-tree` to synthesize a merge commit parented directly by
`studio-main` and `$NEW_UPSTREAM`.
