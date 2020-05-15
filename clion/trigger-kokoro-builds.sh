#!/bin/bash

COMMITISH=$1

if [ -z "$COMMITISH" ]; then
  cat <<EOF
Usage:
  trigger-kokoro-builds.sh <commitish>
For example,
  trigger-kokoro-builds.sh idea/201.6073.9
EOF
  exit 1
fi

echo "Triggering Windows build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/win/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "$1"
  }
}
EOF

echo "Triggering Mac build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/mac/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "$1"
  }
}
EOF

echo "Triggering Linux build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/linux/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "$1"
  }
}
EOF

echo "To track the progress, use http://go/as-clangd-kokoro. After the build is done. You can download all of them with"
echo "  download-binaries-from-placer.sh --linux <build#> --mac <build#> --win <build#>"
