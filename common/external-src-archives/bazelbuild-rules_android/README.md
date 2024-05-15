The https://github.com/bazelbuild/rules_android/ repository has a complex set of
dependencies for the Android rules. The rules are also still in development and
there is no immediate need for them, so they are not being made available.

However, starting in Bazel 7, Bazel expects native Android rules and transitions
to provide values for --android_platforms. So only a bare-minimum BUILD file is
provided to define these platforms.
