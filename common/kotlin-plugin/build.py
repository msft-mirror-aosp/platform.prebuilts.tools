#!/usr/bin/env python3
from pathlib import Path
from zipfile import ZipFile
import argparse
import shlex
import shutil
import subprocess
import sys
import xml.etree.ElementTree as ET


def main():
    parser = argparse.ArgumentParser(
        description='Builds the Kotlin IDE plugin and updates prebuilts.')

    parser.add_argument('--no-update-prebuilts', action='store_true')
    parser.add_argument('--clean-build', action='store_true')
    parser.add_argument('--kotlin-version', default='1.6.10')
    parser.add_argument('--intellij-version', default='213.6461.79')

    args = parser.parse_args()

    # Find workspace root.
    script_dir = Path(__file__).parent
    workspace = script_dir.joinpath('../../../..').resolve(strict=True)
    assert workspace.joinpath('WORKSPACE').exists(), 'failed to find workspace root'

    # Set additional properties which are not currently exposed as flags.
    args.workspace = workspace
    args.kotlinc_dir = workspace.joinpath('external/jetbrains/kotlin')
    args.kotlin_ide_dir = workspace.joinpath('external/jetbrains/intellij-kotlin')
    args.kotlin_version_full = f'{args.kotlin_version}-release-for-android-studio'
    args.gradlew = args.kotlinc_dir.joinpath('gradlew')

    # Build.
    build_kotlin_compiler(args)
    update_ide_project_model(args)
    build_kotlin_ide(args)
    if not args.no_update_prebuilts:
        copy_artifacts_to_prebuilts(args)
        write_metadata_file(args)
    print('\nDone.\n')


# Builds the Kotlin compiler from the sources in external/jetbrains/kotlin.
# In particular this builds artifacts needed by the Kotlin IDE plugin,
# such as the 'kotlin-compiler-for-ide' artifact.
def build_kotlin_compiler(args):
    clean_args = ['clean', '--no-daemon', '--no-build-cache'] if args.clean_build else []
    cmd = [
        str(args.gradlew),
        f'--project-dir={args.kotlinc_dir}',
        *clean_args,
        f'-PdeployVersion={args.kotlin_version_full}',
        f'-Pbuild.number={args.kotlin_version_full}',
        'installIdeArtifacts',
        ':prepare:ide-plugin-dependencies:kotlin-dist-for-ide:install',
        '-Ppublish.ide.plugin.dependencies=true',
        '-Pteamcity=true',  # Makes this a release build rather than a dev build.
        '-Pkotlin.build.isObsoleteJdkOverrideEnabled=true',  # Avoids the need for JDK 1.6.
        '-Dorg.gradle.dependency.verification=off',  # TODO: dependency verification fails currently.
    ]
    run_subprocess(cmd, 'Building the Kotlin compiler')


# Builds the Kotlin IDE plugin from the sources in external/jetbrains/intellij-kotlin.
def build_kotlin_ide(args):
    ant_launcher_jar = args.kotlin_ide_dir.joinpath('lib/ant/lib/ant-launcher.jar')
    build_xml = args.kotlin_ide_dir.joinpath('build.xml')
    cmd = [
        'java', '-jar', str(ant_launcher_jar),  # N.B. using system-default 'java' for now.
        '-f', str(build_xml),
        'kotlin_plugin',
        f'-Dbuild.number={args.intellij_version}',
        '-Dintellij.build.dev.mode=false',
        '-Dkotlin.plugin.kind=AS',
        '-Dcompile.parallel=true',
        # TODO: the search_index task currently fails: "Missing essential plugin
        # (com.intellij.java.ide)". This is probably because the Kotlin plugin
        # is being built in isolation, without the Java plugin.
        '-Dintellij.build.skip.build.steps=search_index',
    ]
    if not args.clean_build:
        cmd.append('-Dintellij.build.incremental.compilation=true')
    run_subprocess(cmd, 'Building the Kotlin IDE plugin')


# Runs the project-model-updater tool so that the Kotlin IDE plugin uses
# our locally built Kotlin compiler.
def update_ide_project_model(args):
    # Write our Kotlin version into project-model-updater/resources/model.properties.
    updater_dir = args.kotlin_ide_dir.joinpath('plugins/kotlin/util/project-model-updater')
    model_props = updater_dir.joinpath('resources/model.properties')
    with open(model_props, 'w') as f:
        f.write(f'kotlincVersion={args.kotlin_version_full}\n')
        f.write('kotlincArtifactsMode=MAVEN\n')

    # Run the updater.
    clean_args = ['clean', '--no-daemon', '--no-build-cache'] if args.clean_build else []
    cmd = [str(args.gradlew), f'--project-dir={updater_dir}', *clean_args, 'run']
    run_subprocess(cmd, 'Running project-model-updater')


def copy_artifacts_to_prebuilts(args):
    print('\nCopying build outputs to prebuilts.\n')

    # Compute destination paths.
    kotlin_prebuilts: Path = args.workspace.joinpath('prebuilts/tools/common/kotlin-plugin')
    target_plugin_dir = kotlin_prebuilts.joinpath('Kotlin')
    target_sources_zip = kotlin_prebuilts.joinpath('kotlin-plugin-sources.jar')

    # Remove old files.
    if target_plugin_dir.exists():
        shutil.rmtree(target_plugin_dir)
    target_sources_zip.unlink(missing_ok=True)

    # Copy build outputs.
    artifacts_dir: Path = args.kotlin_ide_dir.joinpath('out/idea-ce/artifacts')
    new_plugin_zip = artifacts_dir.joinpath(f'IC-plugins/Kotlin-{args.intellij_version}.zip')
    new_sources_zip = artifacts_dir.joinpath('kotlin-plugin-sources.zip')
    shutil.unpack_archive(new_plugin_zip, target_plugin_dir.parent)
    shutil.copy(new_sources_zip, target_sources_zip)


# Writes version info into a METADATA file in the prebuilts directory.
def write_metadata_file(args):
    print('\nWriting METADATA file.\n')

    # Gather version info.
    kotlin_prebuilts: Path = args.workspace.joinpath('prebuilts/tools/common/kotlin-plugin')
    compiler_version = kotlin_prebuilts.joinpath('Kotlin/kotlinc/build.txt').read_text()
    plugin_jar = kotlin_prebuilts.joinpath('Kotlin/lib/kotlin-plugin.jar')
    with ZipFile(plugin_jar) as zip:
        with zip.open('META-INF/plugin.xml') as f:
            plugin_xml = ET.parse(f).getroot()
            plugin_version = plugin_xml.findtext('./version')
            idea_version = plugin_xml.find('./idea-version').get('since-build')

    # Write METADATA file.
    metadata_file = kotlin_prebuilts.joinpath('METADATA')
    with open(metadata_file, 'w') as f:
        f.write(f'Kotlin compiler version: {compiler_version}\n')
        f.write(f'Kotlin plugin version: {plugin_version}\n')
        f.write(f'Kotlin plugin platform: {idea_version}\n')


# A wrapper around subprocess.run() with additional logging and stricter env.
def run_subprocess(cmd, description):
    cmd_quoted = shlex.join(cmd)
    print(f'\n{description}:\n\n{cmd_quoted}\n')
    result = subprocess.run(cmd, env={})
    if result.returncode != 0:
        sys.exit(f'\nERROR: {description} failed (see logs).\n')


if __name__ == '__main__':
    main()
