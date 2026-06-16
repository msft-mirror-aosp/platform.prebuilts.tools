#!/usr/bin/env python3

import argparse
import os
import subprocess
import sys

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
    ("studio-linux", "artifacts/trace_processor_daemon", "linux/trace_processor_daemon"),
    ("studio-win", "trace_processor_daemon.exe", "windows/trace_processor_daemon.exe"),
    ("studio-mac-arm", "trace_processor_daemon", "darwin-arm64/trace_processor_daemon"),
    ("studio-mac", "trace_processor_daemon", "darwin-x86_64/trace_processor_daemon"),
  ]
  for target, artifact, dest_subpath in artifacts:
    destination = os.path.join(dir, dest_subpath)
    print(f"Fetching {artifact} from {target} to {destination}")
    cmd = [fetch_artifact, *auth_flags, "--bid", bid, "--target", target, artifact, destination]
    subprocess.check_call(cmd)
    if "win" not in target:
      subprocess.check_call(["chmod", "+x", destination])

  version_txt = os.path.join(dir, "version.txt")
  print(f"Updating {version_txt}")
  with open(version_txt, 'w') as file:
    file.write("# All binaries are obtained from AB with the specified build number.\n")
    for _, _, dest_subpath in artifacts:
      platform = dest_subpath.split("/")[0]
      file.write(f"ab/{bid} - {platform}\n")


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
