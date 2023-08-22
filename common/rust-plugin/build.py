#!/usr/bin/env python3
from pathlib import Path
from zipfile import ZipFile
import argparse
import io
import os
import platform
import shlex
import shutil
import subprocess
import sys
import tempfile
import xml.etree.ElementTree as ET
import select
import fcntl
from typing import Tuple, List, Dict, TypedDict, NamedTuple, TypedDict
from enum import Enum, auto
from tqdm import tqdm
import filecmp
import re
import fnmatch

def main() -> None:
    """
    This script builds the Rust IDE plugin for IntelliJ-based IDEs from source,
    updates the prebuilt plugin files, and handles related metadata and
    configurations. It supports command-line arguments for clean builds and
    verbosity levels.

    Functionality includes:
    - Parsing command-line arguments for clean build and verbosity
    - Finding the workspace root and setting up necessary paths and environment
    variables
    - Building the Rust IDE plugin using Gradle
    - Copying built artifacts to the prebuilts directory
    - Writing a METADATA file containing version information
    - Generating a JPS library file for the Rust plugin
    - Handling Java home directory based on the platform
    - Wrapper for subprocess.run() with additional logging and output handling
    """
    parser = argparse.ArgumentParser(
        description='Builds the Rust IDE plugin and updates prebuilts.')

    parser.add_argument('-c', '--clean-build',
                        action='store_true',
                        help='Enable clean build. Default is to not clean.')
    parser.add_argument('-v', '--verbosity',
                        default='warn',
                        choices= [v.value for v in Verbosity],
                        help='Set the verbosity level:'
                            ' quiet (errors only),'
                            ' warn (warnings and errors),'
                            ' info (informational messages, warnings, and errors),'
                            ' debug (debug messages, informational messages, warnings, and errors)'
                            ' gradle (use Gradle default logging)')
    parser.add_argument('-k', '--keep-temporaries',
                    action='store_true',
                    default=False,
                    help='Keep temporary build directory for debugging purposes. Default is to remove it.')

    args = parser.parse_args()

    # Find dirs and environment.
    script_dir : Path = Path(__file__).parent
    workspace : Path = script_dir.joinpath('../../../..').resolve(strict=True)
    rust_ide_dir : Path = workspace.joinpath('external/jetbrains/rust')
    gitignore : Path = os.path.join(rust_ide_dir, ".gitignore")
    ignore_patterns = read_gitignore(gitignore)
    build_stage_dir = Path(os.path.abspath('rust-build-stage'))

    # Clean up temporaries if they exist (and we're not preserving them).
    if not args.keep_temporaries:
        if os.path.exists(build_stage_dir):
            print(f'\033[1;36mRemoving Temporary Build Directory\033[0m')
            shutil.rmtree(build_stage_dir)

    # Copy sources from original location in tree to a temporary build folder.
    # Don't copy files matching .gitignore because we don't want stray build
    # outputs from rust_ide_dir to be able to pollute our result.
    print(f'\033[1;36mCopying Rust Sources\033[0m\n  {rust_ide_dir}\n  {build_stage_dir}')
    copy_directory(
        rust_ide_dir,
        build_stage_dir,
        ignore_patterns
    )

    # Disable bundled update of plugin by modifying XML that contains
    # 'allow-bundled-update="true"'
    # Don't modify build outputs if present since they should be
    # propagated when the actual build is done.
    print(f'\033[1;36mUpdate Plugin XML to Disallow Bundling\033[0m')
    search_replace_in_xml(
        build_stage_dir,
        'allow-bundled-update="true"',
        'allow-bundled-update="false"',
        ignore_patterns +
            # Also ignore this test file that has an unknown encoding
            ['deps/clion-2022.3.1/bin/gdb/mac/lib/python3.10/test/xmltestdata/test.xml'])

    # Build.
    (plugin_zip, sources_zip) = build_rust_ide(
            args.clean_build,
            args.verbosity,
            workspace,
            build_stage_dir,
            build_stage_dir.joinpath('gradlew'),
            {
                'PATH': '/bin:/usr/bin',
                'JAVA_HOME': str(compute_java_home(workspace)),
            }
        )

    # Copy artifacts.
    copy_artifacts_to_prebuilts(workspace, plugin_zip, sources_zip)
    write_metadata_file(workspace)
    write_jps_lib_xml(workspace)

    # Clean up temporaries if they exist (and we're not preserving them).
    if not args.keep_temporaries:
        if os.path.exists(build_stage_dir):
            print(f'\033[1;36mRemoving Temporary Build Directory\033[0m')
            shutil.rmtree(build_stage_dir)

class Verbosity(Enum):
    quiet = "quiet"
    warn = "warn"
    info = "info"
    debug = "debug"
    gradle = "gradle"

class ArtifactPaths(NamedTuple):
    plugin_zip: Path
    sources_zip: Path

def build_rust_ide(
    clean_build: bool,
    verbosity: Verbosity,
    workspace: Path,
    rust_ide_dir: Path,
    gradlew: Path,
    cmd_env: Dict[str, str]) -> ArtifactPaths:
    """
    Builds the Rust IDE plugin from the sources in
    external/jetbrains/intellij-rust.
    Returns a tuple of two Path objects representing the built plugin ZIP and
    plugin source ZIP, respectively.

    Returns:
        A tuple of two Path objects representing the built plugin ZIP and
        plugin source ZIP, respectively.
    """
    cmd = [
        # We don't need the Rust compiler, as it is already available in the platform
        "-PcompileNativeCode=false",
        "-PbuildSearchableOptions=true",
        "-PplatformVersion=223",
        "--console=rich",
        "--parallel",
        "--build-cache",
        "buildPlugin",
    ]

    if verbosity != Verbosity.gradle.value:
        cmd = cmd + [f"--{verbosity}"]

    if clean_build:
        cmd = ["clean"] + cmd

    cmd = [str(gradlew)] + cmd

    run_subprocess(
        workspace,
        cmd,
        cmd_env,
        'Building the Rust IDE Plugin',
        rust_ide_dir
        )

    # Gather build outputs.
    artifacts_dir: Path = rust_ide_dir.joinpath('plugin/build')
    plugin_zips = [p for p in artifacts_dir.glob('distributions/*.zip')]
    assert len(plugin_zips) == 1
    plugin_zip = plugin_zips[0]
    sources_zip = artifacts_dir.joinpath('rust-plugin-sources.zip')
    return ArtifactPaths(plugin_zip, sources_zip)

def read_gitignore(gitignore_path):
    """
    Reads the .gitignore file and returns a list of patterns.

    Args:
        gitignore_path (str): Path to the .gitignore file.

    Returns:
        list: List of patterns read from the .gitignore file.
    """
    with open(gitignore_path, 'r') as gitignore_file:
        patterns = gitignore_file.read().splitlines()

    return patterns

def filename_matches_patterns(filename, patterns):
    """
    Checks if a file matches any pattern from the given list of patterns.

    Args:
        filename (str): Name of the file to be checked.
        patterns (list): List of patterns to match against.

    Returns:
        bool: True if the file matches any pattern, False otherwise.
    """
    for pattern in patterns:
        if fnmatch.fnmatch(filename, pattern):
            return True

    return False

def copy_directory(src_dir, dest_dir, ignore=[]):
    """
    Copy files from source directory to destination directory, preserving file attributes and displaying a progress bar for changed files.

    Args:
        src_dir (str): Path to the source directory.
        dest_dir (str): Path to the destination directory.
        ignore (list): List of .gitignore style patterns to ignore.
    """

    # Walk through the source directory recursively
    changed_files = []
    for root, dirs, files in os.walk(src_dir):
        # Determine the corresponding destination directory structure
        dest_root = os.path.join(dest_dir, os.path.relpath(root, src_dir))

        # Create the destination directory if it doesn't exist
        os.makedirs(dest_root, exist_ok=True)

        for file_name in files:
            src_file_path = os.path.join(root, file_name)
            dest_file_path = os.path.join(dest_root, file_name)

            if filename_matches_patterns(os.path.relpath(dest_file_path, dest_dir), ignore): continue

            # Check if the destination file already exists
            if os.path.exists(dest_file_path):
                # Compare file size and timestamp to determine if the files are different
                src_stat = os.stat(src_file_path)
                dest_stat = os.stat(dest_file_path)
                if src_stat.st_size == dest_stat.st_size and src_stat.st_mtime <= dest_stat.st_mtime:
                    # Files are identical, skip copying
                    continue

            # Add the file to the list of changed files
            changed_files.append((src_file_path, dest_file_path))

    # Get the total number of changed files
    file_count = len(changed_files)

    # Initialize the progress bar
    progress_bar = tqdm(
        bar_format='  {percentage:.1f}% | {bar} {n}/{total} | ETA: {remaining}',
        total=file_count,
        leave=False,
        unit=' file(s)')

    # Copy the changed files and update the progress bar
    for src_file_path, dest_file_path in changed_files:
        # Copy the file and preserve attributes
        shutil.copy2(src_file_path, dest_file_path)

        # Preserve the executable attribute if it exists on the source file
        if os.access(src_file_path, os.X_OK):
            os.chmod(dest_file_path, 0o755)

        # Update the progress bar
        progress_bar.update(1)

    # Close the progress bar
    progress_bar.close()

def search_replace_in_xml(directory, search_string, replace_string, ignore):
    """
    Search and replace a string for all *.xml files in a directory.

    Args:
        directory (str): Path to the directory containing the XML files.
        search_string (str): The string to search for.
        replace_string (str): The string to replace the search string with.
        ignore (list): List of .gitignore style patterns to ignore.
    """

    # Iterate over all files in the directory
    for root, dirs, files in os.walk(directory):
        for filename in files:
            # Check if the file has a .xml extension
            if filename.endswith('.xml'):
                # Create the full file path by joining the directory path and the filename
                file_path = os.path.join(root, filename)
                if filename_matches_patterns(os.path.relpath(file_path, directory), ignore): continue

                # Open the file in read mode
                try:
                    with open(file_path, 'r') as file:
                        # Read the content of the file
                        content = file.read()
                except UnicodeDecodeError:
                    print_relevant_file(directory, "  Unknown Encoding", file_path)
                    continue

                # Perform the search and replace operation using regular expressions
                updated_content = re.sub(search_string, replace_string, content)

                if content != updated_content:
                    print_relevant_file(directory, "  Modifing", file_path)

                    # Open the file in write mode to overwrite the content
                    with open(file_path, 'w') as file:
                        # Write the updated content back to the file
                        file.write(updated_content)

def copy_artifacts_to_prebuilts(
    workspace: Path,
    new_plugin_zip: Path,
    new_sources_zip: Path) -> None:
    """
    Copies the built Rust IDE plugin and source files to the prebuilts
    directory. This function is responsible for updating the prebuilt
    plugin files to match the newly built artifacts.

    The function does the following:
    - Computes the destination paths for the plugin directory, source JAR,
      and source ZIP files.
    - Removes old plugin directory, source JAR, and source ZIP files from
      the prebuilts directory.
    - Unpacks the new plugin ZIP and copies it to the target plugin directory.
    """

    # Compute destination paths.
    rust_prebuilts: Path = workspace.joinpath('prebuilts/tools/common/rust-plugin')
    target_plugin_dir = rust_prebuilts.joinpath('intellij-rust')

    # Remove old files.
    if target_plugin_dir.exists():
        shutil.rmtree(target_plugin_dir)

    # Copy new files.
    print_relevant_file(workspace, "Built Plugin Zip:", new_plugin_zip)
    print_relevant_file(workspace, "Unzipped Plugin:", target_plugin_dir.parent)
    shutil.unpack_archive(new_plugin_zip, target_plugin_dir.parent)

def write_metadata_file(workspace: Path) -> None:
    """
    Writes a METADATA file containing version information about the Rust
    IDE plugin. The file is placed in the prebuilts directory and includes
    the build ID, plugin version, and plugin platform.

    The function does the following:
    - Gathers the plugin version and IDEA version from the
      'META-INF/plugin.xml' file inside the plugin JAR.
    - Sets the build ID to '<local_build>'.
      TODO eventually set up a build on build server.
    - Writes the build_id, rust_plugin_version, and rust_plugin_platform
      to the METADATA file in the 'prebuilts/tools/common/rust-plugin' directory.
    """
    build_id : str = '<local_build>'
    rust_prebuilts : Path = workspace.joinpath('prebuilts/tools/common/rust-plugin')
    plugin_jar : Path  = next(rust_prebuilts.joinpath('intellij-rust/lib').glob('intellij-rust-*.jar'))
    metadata_file : Path = rust_prebuilts.joinpath('METADATA')
    print_relevant_file(workspace, 'Writing METADATA file:', metadata_file)
    with ZipFile(plugin_jar) as zip:
        with zip.open('META-INF/plugin.xml') as f:
            plugin_xml = ET.parse(f).getroot()
            plugin_version = plugin_xml.findtext('./version')
            idea_version_element = plugin_xml.find('./idea-version')
            if idea_version_element is None:
                sys.exit(f'./idea-version does not exist')
            else:
                idea_version = idea_version_element.get('since-build')

    # Write METADATA file.
    with open(metadata_file, 'w') as f:
        f.write(f'build_id: {build_id}\n')
        f.write(f'rust_plugin_version: {plugin_version}\n')
        f.write(f'rust_plugin_platform: {idea_version}\n')

def write_jps_lib_xml(workspace: Path) -> None:
    """
    Generates a JPS (Java Project Structure) library XML file for the Rust
    plugin. This file is used by the IntelliJ-based IDEs to include the Rust
    plugin's JAR files and source directories in the project.

    The function does the following:
    - Retrieves the paths of the plugin's JAR files and source directories.
    - Creates the XML structure for the JPS library, including the 'CLASSES'
      and 'SOURCES' elements with the corresponding paths.
    - Writes the XML structure to the '.idea/libraries/rust_plugin.xml'
      file in the project directory.
    """
    project_dir : Path = workspace.joinpath('tools/adt/idea')
    jars_sorted = sorted(workspace.glob('prebuilts/tools/common/rust-plugin/intellij-rust/lib/*.jar'))
    jars = [os.path.relpath(jar, project_dir) for jar in jars_sorted]

    rust_prebuilts : Path = workspace.joinpath('prebuilts/tools/common/rust-plugin')
    # TODO(jomof) Rust plugin has implemented a source jar but it is past our current revision. Reenable when we move the revision forward.
    # src = next(rust_prebuilts.joinpath('intellij-rust/lib/src').glob('intellij-rust-*.jar'))
    # print_relevant_file(workspace, 'Using sources zip:', src)
    # src = Path(os.path.relpath(src, project_dir))

    outfile : Path = project_dir.joinpath(f'.idea/libraries/rust_plugin.xml')
    print_relevant_file(workspace, 'Writing JPS library:', outfile)
    with open(outfile, 'w') as f:
        f.write(f'<component name="libraryTable">\n')
        f.write(f'  <library name="rust-plugin">\n')
        f.write(f'    <CLASSES>\n')
        for jar in jars:
            f.write(f'      <root url="jar://$PROJECT_DIR$/{jar}!/" />\n')
        f.write(f'    </CLASSES>\n')
        f.write(f'    <JAVADOC />\n')
        f.write(f'    <SOURCES>\n')
        f.write(f'      <root url="file://$PROJECT_DIR$/../../../external/jetbrains/rust/src/main/"/>\n')
        f.write(f'      <root url="file://$PROJECT_DIR$/../../../external/jetbrains/rust/src/gen/"/>\n')
        #f.write(f'      <root url="jar://$PROJECT_DIR$/{src}!/" />\n')
        f.write(f'    </SOURCES>\n')
        f.write(f'  </library>\n')
        f.write(f'</component>')


def compute_java_home(workspace : Path) -> Path:
    """
    Determines the Java home directory based on the platform and returns the
    path to the appropriate JDK directory.
    """
    jdk_base = workspace.joinpath('prebuilts/studio/jdk/jdk17')
    system = platform.system()
    if system == 'Linux':
        return jdk_base.joinpath('linux')
    elif system == 'Darwin':
        subdir = 'mac-arm64' if platform.machine() == 'arm64' else 'mac'
        return jdk_base.joinpath(subdir, 'Contents/Home')
    else:
        sys.exit(f'Unrecognized system: {system}')

def print_relevant_file(
    workspace: Path,
    description: str,
    file: Path) -> None:
    """
    Description of a file being used or written, along with its relative path
    to the workspace directory, to provide context and clarity in the script's
    output.
    """
    relative : str = os.path.relpath(file, workspace)
    print(f'{description} \033[36m{relative}\033[0m')

def run_subprocess(
    workspace: Path,
    cmd: List[str],
    env: Dict[str, str],
    description: str,
    cwd: Path):
    """
    Wrapper function for subprocess.Popen() that provides additional logging,
    output handling, and strict environment control. It displays the output
    with ANSI escape codes and sets the file descriptors to non-blocking mode
    for better output streaming.

    Raises:
    SystemExit: If the command execution fails (non-zero return code), the
        script will exit with an error message.
    """
    cmd_relative = cmd.copy()
    cmd_relative[0] = os.path.relpath(cmd_relative[0], workspace)
    cmd_quoted_relative = ' '.join([shlex.quote(arg) for arg in cmd_relative])
    print(f'\033[1;36m{description}\033[0m\n  {cmd_quoted_relative}')
    for key, value in env.items():
        print(f"    \033[2;33m{key}={value}\033[0m")

    # Execute the command and display the output with ANSI escape codes
    with subprocess.Popen(cmd, env=env, cwd=cwd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=False, text=True, bufsize=1, universal_newlines=True) as process:
        # Make stdout and stderr file descriptors non-blocking
        stdout = process.stdout
        stderr = process.stderr
        assert stdout is not None
        assert stderr is not None
        stdout.flush()
        stderr.flush()

        # Retrieve the file status flags of the stdout and stderr
        # file descriptors.
        flags_stdout = fcntl.fcntl(stdout, fcntl.F_GETFL)
        flags_stderr = fcntl.fcntl(stderr, fcntl.F_GETFL)

        # Set the stdout and stderr file descriptors to non-blocking
        # mode by updating its file status flags.
        fcntl.fcntl(stdout, fcntl.F_SETFL, flags_stdout | os.O_NONBLOCK)
        fcntl.fcntl(stderr, fcntl.F_SETFL, flags_stderr | os.O_NONBLOCK)

        while process.poll() is None:
            char = stdout.read(1)
            if char:
                sys.stdout.write(char)
                sys.stdout.flush()

            err_char = stderr.read(1)
            if err_char:
                sys.stderr.write(err_char)
                sys.stderr.flush()

        returncode = process.poll()
        if returncode != 0:
            sys.exit(f'ERROR: {description} failed (see logs).')

if __name__ == '__main__':
    main()
