#!/usr/bin/env python3

# Copyright 2025 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import argparse
import os
import shutil
import stat

from pathlib import Path


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--root', required=True, help='The relative or absolute path to the GFXR repo. There should be a .git directory here.')
    parser.add_argument('--prebuilts', required=True, help='The relative or absolute path to the prebuilds directory. This is where \'tools/\' will be created.')

    args = parser.parse_args()

    root = Path(args.root)

    if not (root / '.git').exists():
        raise NameError(f'{root} does not have a git repo; it is not a GFXR repo.')

    prebuilts = Path(args.prebuilts).absolute()

    host = 'linux-x64'

    copy_actions = [
        ('android/tools/replay/build/outputs/apk/debug/replay-debug.apk', 'common', False),
        ('build/tools/capture/gfxrecon-capture.py', f'{host}/tools/capture', False),
        ('build/tools/capture-vulkan/gfxrecon-capture-vulkan.py', f'{host}/tools/capture-vulkan', False),
        ('build/tools/compress/gfxrecon-compress', f'{host}/tools/compress', False),
        ('build/tools/convert/gfxrecon-convert', f'{host}/tools/convert', True),
        ('build/tools/extract/gfxrecon-extract', f'{host}/tools/extract', False),
        ('build/tools/gfxrecon/gfxrecon.py', f'{host}/tools/gfxrecon', False),
        ('build/tools/info/gfxrecon-info', f'{host}/tools/info', True),
        ('build/tools/optimize/gfxrecon-optimize', f'{host}/tools/optimize', False),
        ('build/tools/replay/gfxrecon-replay', f'{host}/tools/replay', False),
        ('build/tools/tocpp/gfxrecon-tocpp', f'{host}/tools/tocpp', False),
    ]

    for src_file, dest_dir, stat_exec in copy_actions:
        src_path = root / src_file
        dest_path = prebuilts / dest_dir / src_path.name

        if dest_path.exists():
            os.remove(dest_path)

        if not dest_path.parent.exists():
            dest_path.parent.mkdir(parents=True)

        print(f'Copying file {root} to {dest_path}')
        shutil.copyfile(src_path, dest_path)

        if stat_exec:
            dest_path.chmod(dest_path.stat() | stat.S_IEXEC)


if __name__ == '__main__':
    main()
