#!/bin/bash

set -e

committish="$1"

if [[ -z "${committish}" ]]; then
  cat <<EOF
Usage:
  trigger-kokoro-builds.sh <committish>
  trigger-kokoro-builds.sh latest_idea_tag
For example,
  trigger-kokoro-builds.sh idea/201.6073.9
EOF
  exit 1
fi

if [[ "${committish}" == "latest_idea_tag" ]]; then
  repo="team/android-studio/third_party/llvm-project"
  echo "Finding latest idea tag from repository: '${repo}'"
  response="$(stubby call --proto2 blade:git GitFS.ListRefs \
    "repo: '${repo}' ref_prefix: 'refs/tags/idea/'")"
  tags="$(echo "${response}" \
    | grep "refs/tags/idea" \
    | sed 's|^.*refs/tags/\(.*\)"$|\1\n|')"
  # This assumes that the version numbers embedded in the tags
  # will be monotonically increasing alphabetically.
  latest_idea_tag="$(echo "${tags}" \
    | sort -V \
    | tail -1)"
  echo "Latest idea tag found: '${latest_idea_tag}'"
  committish="${latest_idea_tag}"
  if [[ -z "${committish}" ]]; then
    echo "Failed to find latest idea tag"
    exit 1
  fi
fi

echo "Triggering Windows build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/win/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "${committish}"
  }
}
EOF

echo "Triggering Mac build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/mac/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "${committish}"
  }
}
EOF

echo "Triggering Linux build..."
stubby call --proto2 blade:kokoro-api KokoroApi.Build <<EOF
full_job_name: "android-studio/clangd/linux/release"
multi_scm_revision {
  git_on_borg_scm_revision {
    name: "llvm-project"
    sha1: "${committish}"
  }
}
EOF

echo "To track the progress, use http://go/as-clangd-kokoro. After the build is done. You can download all of them with"
echo "  download-binaries-from-placer.sh --linux <build#> --mac <build#> --win <build#>"
