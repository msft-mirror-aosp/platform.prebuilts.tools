This directory contains clangd and clang-tidy binaries built from JetBrains'
fork of llvm-project. The specific folder structure is enforced by logic by
`ClangUtils#getBuiltinClangToolPath()` defined in
//tools/vendor/intellij/cidr/cidr-lang/src/com/jetbrains/cidr/lang/daemon/clang/ClangUtils.java.
All binaries are 64 bit and 32 bit is not supported as for now.
