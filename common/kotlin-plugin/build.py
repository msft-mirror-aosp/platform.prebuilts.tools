#!/usr/bin/env python3
from pathlib import Path
from zipfile import ZipFile
import argparse
import os
import platform
import shlex
import shutil
import subprocess
import sys
import tempfile
import xml.etree.ElementTree as ET


def main():
    parser = argparse.ArgumentParser(
        description='Builds the Kotlin IDE plugin and updates prebuilts.')

    parser.add_argument('--download', metavar='BUILD_ID')
    parser.add_argument('--clean-build', action='store_true')
    parser.add_argument('--stage', metavar='DIR', type=Path)
    parser.add_argument('--kotlin-version', default='1.6.21')
    parser.add_argument('--intellij-version', default='213.6777.52')

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
    args.java_home = compute_java_home(args)
    args.java = args.java_home.joinpath('bin/java')
    args.cmd_env = {
        'PATH': '/bin:/usr/bin',
        'JAVA_HOME': str(args.java_home),
    }

    # Download or build.
    if args.download:
        (plugin_zip, sources_zip) = download_kotlin_ide_from_ab(args)
    else:
        build_kotlin_compiler(args)
        update_ide_project_model(args)
        (plugin_zip, sources_zip) = build_kotlin_ide(args)

    # Copy artifacts.
    if args.stage:
        args.stage.mkdir(parents=True, exist_ok=True)
        shutil.copy(plugin_zip, args.stage)
        shutil.copy(sources_zip, args.stage)
    else:
        copy_artifacts_to_prebuilts(args, plugin_zip, sources_zip)
        write_metadata_file(args)
        write_jps_lib_xml(args)

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
    run_subprocess(cmd, args.cmd_env, 'Building the Kotlin compiler')


# Builds the Kotlin IDE plugin from the sources in external/jetbrains/intellij-kotlin.
# Returns a tuple of build outputs.
def build_kotlin_ide(args):
    ant_launcher_jar = args.kotlin_ide_dir.joinpath('lib/ant/lib/ant-launcher.jar')
    build_xml = args.kotlin_ide_dir.joinpath('build.xml')
    cmd = [
        str(args.java), '-jar', str(ant_launcher_jar),
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
    run_subprocess(cmd, args.cmd_env, 'Building the Kotlin IDE plugin')

    # Gather build outputs.
    artifacts_dir: Path = args.kotlin_ide_dir.joinpath('out/idea-ce/artifacts')
    plugin_zip = artifacts_dir.joinpath(f'IC-plugins/Kotlin-{args.intellij_version}.zip')
    sources_zip = artifacts_dir.joinpath('kotlin-plugin-sources.zip')
    return (plugin_zip, sources_zip)


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
    run_subprocess(cmd, args.cmd_env, 'Running project-model-updater')


# Downloads the Kotlin IDE plugin from AB. Returns a tuple of build outputs.
def download_kotlin_ide_from_ab(args):
    # Download.
    bid = args.download
    fetch = '/google/data/ro/projects/android/fetch_artifact'
    tmp_dir = Path(tempfile.mkdtemp(prefix=f'kotlin-plugin-from-ab-{bid}-'))
    for artifact in ['Kotlin-*.zip', 'kotlin-plugin-sources.zip']:
        cmd = [fetch, '--bid', bid, '--target', 'IntelliJ-KotlinPlugin', artifact, str(tmp_dir)]
        run_subprocess(cmd, args.cmd_env, f'Downloading {artifact} into {tmp_dir}')

    # Gather artifacts.
    plugins = list(tmp_dir.glob('Kotlin-*.zip'))
    sources = list(tmp_dir.glob('kotlin-plugin-sources.zip'))
    assert len(plugins) == len(sources) == 1
    return (plugins[0], sources[0])


def copy_artifacts_to_prebuilts(args, new_plugin_zip, new_sources_zip):
    print('\nCopying build outputs to prebuilts.\n')

    # Compute destination paths.
    kotlin_prebuilts: Path = args.workspace.joinpath('prebuilts/tools/common/kotlin-plugin')
    target_plugin_dir = kotlin_prebuilts.joinpath('Kotlin')
    target_sources_zip = kotlin_prebuilts.joinpath('kotlin-plugin-sources.jar')

    # Remove old files.
    if target_plugin_dir.exists():
        shutil.rmtree(target_plugin_dir)
    target_sources_zip.unlink(missing_ok=True)

    # Copy new files.
    shutil.unpack_archive(new_plugin_zip, target_plugin_dir.parent)
    shutil.copy(new_sources_zip, target_sources_zip)


# Writes version info into a METADATA file in the prebuilts directory.
def write_metadata_file(args):
    print('\nWriting METADATA file.\n')

    # Gather version info.
    build_id = args.download if args.download else '<local_build>'
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
        f.write(f'build_id: {build_id}\n')
        f.write(f'kotlin_compiler_version: {compiler_version}\n')
        f.write(f'kotlin_plugin_version: {plugin_version}\n')
        f.write(f'kotlin_plugin_platform: {idea_version}\n')


def write_jps_lib_xml(args):
    project_dir = args.workspace.joinpath('tools/adt/idea')

    # Note: see comment in the BUILD file for why we exclude kotlin-stdlib and kotlin-reflect.
    jars = sorted(args.workspace.glob('prebuilts/tools/common/kotlin-plugin/Kotlin/lib/*.jar'))
    jars = [jar for jar in jars if jar.name not in ['kotlinc_kotlin-stdlib.jar', 'kotlinc_kotlin-reflect.jar']]
    jars = [os.path.relpath(jar, project_dir) for jar in jars]

    src = args.workspace.joinpath('prebuilts/tools/common/kotlin-plugin/kotlin-plugin-sources.jar')
    src = os.path.relpath(src, project_dir)

    outfile = project_dir.joinpath(f'.idea/libraries/studio_plugin_Kotlin.xml')
    print(f'\nWriting JPS library: {outfile}\n')
    with open(outfile, 'w') as f:
        f.write(f'<component name="libraryTable">\n')
        f.write(f'  <library name="studio-plugin-Kotlin">\n')
        f.write(f'    <CLASSES>\n')
        for jar in jars:
            f.write(f'      <root url="jar://$PROJECT_DIR$/{jar}!/" />\n')
        f.write(f'    </CLASSES>\n')
        f.write(f'    <JAVADOC />\n')
        f.write(f'    <SOURCES>\n')
        f.write(f'      <root url="jar://$PROJECT_DIR$/{src}!/" />\n')
        f.write(f'    </SOURCES>\n')
        f.write(f'  </library>\n')
        f.write(f'</component>')


def compute_java_home(args):
    jdk_base = args.workspace.joinpath('prebuilts/studio/jdk/jdk11')
    system = platform.system()
    if system == 'Linux':
        return jdk_base.joinpath('linux')
    elif system == 'Darwin':
        subdir = 'mac-arm64' if platform.machine() == 'arm64' else 'mac'
        return jdk_base.joinpath(subdir, 'Contents/Home')
    else:
        sys.exit(f'Unrecognized system: {system}')


# A wrapper around subprocess.run() with additional logging and stricter env.
def run_subprocess(cmd, env, description):
    cmd_quoted = ' '.join([shlex.quote(arg) for arg in cmd])
    print(f'\n{description}:\n\n{cmd_quoted}\n')
    sys.stdout.flush()
    result = subprocess.run(cmd, env=env)
    if result.returncode != 0:
        sys.exit(f'\nERROR: {description} failed (see logs).\n')


if __name__ == '__main__':
    main()
