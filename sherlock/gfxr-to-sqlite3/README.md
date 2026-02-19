# 🛠️ GFXR-to-SQLite3 Converter Utility

This folder contains the prebuilt executable for `gfxr-to-sqlite3`, a command-line utility designed to convert a binary **.gfxr** trace file into a structured **.sqlite3** database file.

---

## 🚀 Usage

Run the executable by providing the path to the input `.gfxr` file.

### Syntax

```bash
./gfxr-to-sqlite3 [-o <output_path>] <path_to_gfxr_file>
```

| Argument | Description |
| :--- | :--- |
| `<path_to_gfxr_file>` | **Required.** The path to the input GFXR trace file. |
| `[-o <output_path>]` | **Optional.** The path and filename for the output SQLite database. |

### Example

```bash
./gfxr-to-sqlite3 /path/to/input/trace.gfxr -o /output/data.sqlite3
```

-----

## 🍎 macOS First-Time Setup

**Note:** The following steps are required **only once** on macOS to correct the dynamic library linking for the prebuilt binary.

**Prerequisite:** This assumes you have the `lz4` and `zstd` compression libraries installed via Homebrew.

The prebuilt binary links against specific paths that may not match your local environment. Run these two commands to fix the dynamic library paths (`dylib`) to point to your locally installed versions:

```bash
install_name_tool -change /opt/homebrew/opt/lz4/lib/liblz4.1.dylib $(brew --prefix lz4)/lib/liblz4.1.dylib ./gfxr-to-sqlite3

install_name_tool -change /opt/homebrew/opt/zstd/lib/libzstd.1.dylib $(brew --prefix zstd)/lib/libzstd.1.dylib ./gfxr-to-sqlite3
```

After running these commands, you can use the utility normally.

-----

## 📦 Build Information

This executable was generated as an artifact from our automated Continuous Integration (CI) pipeline.

  * **Latest Update Date:** 2025-12-23
  * **Source Commit:** [`c01160a...`](https://github.com/android-graphics/sokatoa/commit/c01160a08363ed95e06d123d18eeddf42e3b9660) (The commit that triggered this build)
  * **CI Artifacts (GitHub Actions Run):** [Sokatoa Build Run](https://github.com/android-graphics/sokatoa/actions/runs/20385052775)

<!-- end list -->
