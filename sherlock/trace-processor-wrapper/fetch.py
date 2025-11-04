#!/usr/bin/env python3

import argparse
import os
import subprocess
import sys
import tempfile

def download(dir, bid):
  print(f"Fetching artifacts from ab/{bid}")
  fetch_artifact = "/google/data/ro/projects/android/fetch_artifact"
  auth_flags = []
  if os.path.exists("/usr/bin/prodcertstatus"):
    if subprocess.run("prodcertstatus").returncode != 0:
      sys.exit("You need prodaccess to download artifacts")
  else:
    fetch_artifact = "/usr/bin/fetch_artifact"
    auth_flags.append("--use_oauth2")
    if not os.path.exists(fetch_artifact):
      sys.exit("""You need to install fetch_artifact:
sudo glinux-add-repo android stable && \\
sudo apt update && \\
sudo apt install android-fetch-artifact""")

  artifacts = [
    ("studio-linux", "artifacts/sherlock_trace_processor", "linux-x64/main"),
    ("studio-win", "sherlock_trace_processor.exe", "windows-x64/main.exe"),
    ("studio-mac-arm", "sherlock_trace_processor", "darwin-arm64/main"),
  ]
  for target, artifact, dest_subpath in artifacts:
    destination = os.path.join(dir, dest_subpath)
    print(f"Fetching {artifact} from {target} to {destination}")
    cmd = [fetch_artifact, *auth_flags, "--bid", bid, "--target", target, artifact, destination]
    subprocess.check_call(cmd)

  build_txt = os.path.join(dir, "build.txt")
  print(f"Updating {build_txt}")
  with open(build_txt, 'w') as file:
    file.write(f"ab/{bid}")


if __name__ == "__main__":
  parser = argparse.ArgumentParser()
  parser.add_argument(
      "--bid",
      default="",
      dest="bid",
      help="The AB build to download")
  args = parser.parse_args()
  dir = os.path.dirname(os.path.realpath(__file__))

  if not args.bid:
    sys.exit("--bid argument needs to be set to download")

  download(dir, args.bid)
