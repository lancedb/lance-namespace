# @lance-format/lance-namespace

TypeScript core interface and plugin registry for Lance Namespace.

This package provides:

- `LanceNamespace`: the abstract base class defining the operation contract shared
  across all Lance Namespace language SDKs.
- `connect` / `registerNamespaceImpl`: the generic factory for loading a namespace
  implementation by registered alias or full class path.
- Error types (`LanceNamespaceError`, `ErrorCode`, and per-code subclasses).
- Re-exported request/response model types from `@lance-format/lance-namespace-fetch-client`.

Namespace implementations (REST, directory, catalog integrations, etc.) are **not**
bundled here. They are published as separate packages and loaded dynamically, mirroring
the Python (`lance-namespace`) and Java (`lance-namespace-core`) cores.

See the [TypeScript modules README](../README.md) for usage examples.
