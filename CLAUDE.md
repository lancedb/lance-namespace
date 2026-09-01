# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Repo Is

Lance Namespace specification, core interfaces, and OpenAPI-generated clients/servers.
The single source of truth is the OpenAPI spec at `docs/src/rest.yaml`.

**Scope:** Only spec changes, interface changes, and new generated clients/servers belong here.
Implementation changes (directory/REST namespaces) go to [lance-format/lance](https://github.com/lance-format/lance).
Other namespace implementations go to [lance-format/lance-namespace-impls](https://github.com/lance-format/lance-namespace-impls).

## Build Commands

Requires [uv](https://docs.astral.sh/uv/getting-started/installation/). First run: `make sync`

| Command | Description |
|---------|-------------|
| `make lint` | Validate OpenAPI spec with openapi-spec-validator |
| `make gen` | Clean + codegen + lint all languages |
| `make build` | Full build: lint + docs + gen + build all languages |
| `make gen-<lang>` | Codegen one language: `gen-python`, `gen-java`, `gen-rust` |
| `make build-<lang>` | Build one language: `build-python`, `build-java`, `build-rust` |
| `make serve-docs` | Preview docs (auto-runs `gen-java` first) |

Inside `java/` or `python/`, you can target specific modules:
`make gen-java-apache-client`, `make build-java-springboot-server`, etc.

### Running Tests

```bash
# Python
cd python/lance_namespace && uv sync && uv run pytest
cd python/lance_namespace_urllib3_client && uv sync && uv run pytest

# Java (checkstyle + spotless + maven build with tests)
cd java && make check   # style checks only
cd java && make build   # full build including tests

# Rust
cd rust && cargo test --all-features
```

### Java Style Checks

Java uses Spotless (formatting) and Checkstyle (linting). The `java/Makefile` `check` target
runs both. These are enforced in CI. Fix formatting issues with:
```bash
cd java && mvn spotless:apply
```

## Generated vs Hand-Written Code

**Never manually edit generated code.** CI (`spec.yml`) verifies that running `make clean && make gen`
produces no diff — any manual edits to generated files will be rejected.

### Hand-written (edit these):
- `docs/src/rest.yaml` — OpenAPI spec, the single source of truth
- `python/lance_namespace/` — Python core interface, connect factory, error hierarchy
- `java/lance-namespace-core/` — Java core interface, connect factory, errors
- `java/lance-namespace-core-async/` — Java async wrapper around core
- `java/openapi-templates/` — Custom Mustache templates for Java codegen

### Generated (do not edit):
- `python/lance_namespace_urllib3_client/` — Python HTTP client + all model classes
- `java/lance-namespace-apache-client/` — Java Apache HttpClient implementation
- `java/lance-namespace-async-client/` — Java native async HttpClient implementation
- `java/lance-namespace-springboot-server/` — Spring Boot server skeleton
- `rust/lance-namespace-reqwest-client/` — Rust reqwest client

Codegen uses `openapi-generator-cli` (v7.12.0 via uv). Language-specific ignore files
(e.g., `.apache-client-ignore`) control which generated artifacts are committed.

## Architecture

### Plugin/Registry Pattern

Both Python and Java use a plugin system where implementations are discovered at runtime:

**Python** (`lance_namespace/__init__.py`):
- `connect(impl, properties)` — factory that resolves an implementation name
- `register_namespace_impl(name, class_path)` — register external implementations
- Resolution: native aliases ("dir", "rest") → registered impls → full module.Class path
- Uses `importlib.import_module()` for dynamic loading

**Java** (`LanceNamespace.java`):
- `LanceNamespace.connect(impl, properties, allocator)` — static factory
- `registerNamespaceImpl(name, className)` / `unregisterNamespaceImpl(name)`
- Resolution: `NATIVE_IMPLS` map → `REGISTERED_IMPLS` concurrent map → full class name
- Uses reflection with no-arg constructor + `initialize()` call
- Requires Apache Arrow `BufferAllocator` parameter

### Error System

Consistent error codes (0-21) across all languages in `ErrorCode` enum/class.
Each code has a corresponding exception class. Factory function `from_error_code()` maps codes to exceptions.

### API Operations

The REST spec defines 40+ endpoints under `/v1/` organized as:
- **Namespace ops:** create, list, describe, drop, exists
- **Table ops:** CRUD, schema mutations, versioning, indexing, tags, query/insert/merge
- **Transaction ops:** describe, alter
- **Batch ops:** batch version create, batch commit (atomic multi-table)

All operations are default methods on `LanceNamespace` that throw `UnsupportedOperationError`,
allowing implementations to opt into only the operations they support.

### Documentation Build

Model docs are generated from the Java Apache Client's Javadoc and copied to `docs/src/operations/models/`.
This is why `build-docs` and `serve-docs` depend on `gen-java`.

## Dependency Structure

- **Rust:** Interface lives in the [lance](https://github.com/lance-format/lance) repo, not here. Only the generated reqwest client is here.
- **Python/Java:** Core interfaces live here; implementations are in the lance repo and consume these interfaces.
- The Python core package re-exports all model types from the generated urllib3 client, so downstream code only needs to import `lance_namespace`.
