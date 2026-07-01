# TypeScript Modules

This folder contains the TypeScript modules for Lance Namespace:

- `@lance-format/lance-namespace-fetch-client`: generated fetch client from `docs/src/spec.yaml`
- `@lance-format/lance-namespace`: hand-written core interface, error model, and class-path registry

Namespace implementations (e.g. REST, directory) are **not** part of these packages.
Like the Python and Java cores, `@lance-format/lance-namespace` only provides the abstract
`LanceNamespace` interface and the generic `connect` / `registerNamespaceImpl` machinery;
concrete implementations are provided by separate packages and loaded dynamically by class path.

## Build

```bash
cd typescript
make build
```

## Test

```bash
cd typescript
make test
```

## Regenerate OpenAPI client

```bash
cd typescript
make gen-fetch-client
```

After regeneration, always run:

```bash
cd typescript
make build
```

For codegen reproducibility checks:

```bash
make clean-typescript
make gen-typescript
git diff --exit-code
```

## Usage

```ts
import {
  connect,
  registerNamespaceImpl,
  LanceNamespace,
  type ListNamespacesRequest,
} from "@lance-format/lance-namespace";

// Implement the interface, or bring one from an implementation package.
class MockNamespace extends LanceNamespace {
  namespaceId(): string {
    return "MockNamespace";
  }
}

// Register an implementation by class path, then connect by its alias.
registerNamespaceImpl("mock", "my-namespace-package#MockNamespace");
const ns = await connect("mock", {});
console.log(ns.namespaceId());

// You can also connect directly with a full class path.
const other = await connect("my-namespace-package#MockNamespace", {});

const listRequest: ListNamespacesRequest = { id: [] };
const listResp = await other.listNamespaces(listRequest);
console.log(listResp.namespaces);
```

### Error handling

```ts
import {
  ErrorCode,
  LanceNamespaceError,
} from "@lance-format/lance-namespace";

try {
  await ns.describeTable({ id: ["ns", "missing"] });
} catch (error) {
  if (error instanceof LanceNamespaceError) {
    if (error.code === ErrorCode.TABLE_NOT_FOUND) {
      console.log("table not found");
    } else {
      console.log(`namespace error: ${error.message}`);
    }
  } else {
    throw error;
  }
}
```

## Release notes for maintainers

- Versions are managed through `.bumpversion.toml`.
- Keep `typescript/Makefile` `VERSION` aligned with workspace version.
- The publish workflow is `.github/workflows/typescript-publish.yml`.
- Release channel is aligned with repo tags:
  - stable tag (`vX.Y.Z`) publishes stable npm packages.
  - preview tag (`vX.Y.Z-beta.N`) publishes prerelease npm packages.
