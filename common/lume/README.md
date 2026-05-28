# Lume Releases

This directory contains release artifacts for Lume, organized by version subdirectories.

## Directory Structure

Each release version should have its own subdirectory named after the version (e.g., `0.0.10-alpha01/`).

Inside each version directory, you should place the platform-specific zip files:
*   `lume-daemon-linux_x64-<BUILD_ID>.zip`
*   `lume-daemon-darwin_x64-<BUILD_ID>.zip`
*   `lume-daemon-darwin_aarch64-<BUILD_ID>.zip`
*   `lume-daemon-windows_x64-<BUILD_ID>.zip`

## Bazel Integration

The `BUILD.bazel` file in this directory exposes these releases to Bazel using a platform-aware `filegroup` target (via `select`).

When adding a new version:
1. Create a new directory for the version (e.g., `0.0.11/`).
2. Place the corresponding platform zips inside that directory (typically downloaded from the release build or a staged Rapid version).
3. Update the `BUILD.bazel` file in this directory to add a new target or update the existing one to point to the new files.
