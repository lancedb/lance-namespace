# TypeScript Modules

This folder contains the TypeScript modules for Lance Namespace:

- `@lance/namespace-fetch-client`: generated fetch client from `docs/src/rest.yaml`
- `@lance/lance-namespace`: hand-written core interface, error model, and class-path registry
- `@lance/lance-namespace-rest`: REST implementation package for `@lance/lance-namespace`

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
} from "@lance/lance-namespace";

const ns = await connect("rest", {
  uri: "http://localhost:2333",
  "headers.X-Request-ID": "req-1",
});

const listRequest: ListNamespacesRequest = { id: [] };
const listResp = await ns.listNamespaces(listRequest);
console.log(listResp.namespaces);

class MockNamespace extends LanceNamespace {
  namespaceId(): string {
    return "MockNamespace";
  }
}

registerNamespaceImpl("mock", "my-namespace-package#MockNamespace");
const mock = await connect("mock", {});
console.log(mock.namespaceId());
```

### Error handling

```ts
import {
  ErrorCode,
  LanceNamespaceError,
} from "@lance/lance-namespace";

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
