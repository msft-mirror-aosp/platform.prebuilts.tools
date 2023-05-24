The tracebox binaries should be placed in this directory under a specific subfolder according to their architecture:
* **ARM64**: arm64-v8a/[tracebox]
* **x86\_64**: x86\_64/[tracebox]

The above binaries were downloaded from https://github.com/google/perfetto/releases/tag/v35.0

* **tracebox**: Tracebox is a bundle containing all the tracing services and the perfetto
cmdline client in one binary. It can be used either to spawn manually the
various subprocess or in "autostart" mode, which will take care of starting
and tearing down the services for you.

Usage in autostart mode:
  tracebox -t 10s -o trace_file.perfetto-trace sched/sched_switch
  See tracebox --help for more options.

For more information see https://perfetto.dev/docs/