# lance-namespace

Lance Namespace interface and plugin registry.

## Overview

This package provides two layers:

1. A light-weight stable C ABI for registering namespace implementations and connecting to them.
2. A header-only C++ convenience layer with a simple `Namespace` base class and `connect()` helper.

The package is intentionally lightweight with the following public API:

- `register_namespace_impl(...)`
- `connect(...)`
- shared error/status codes
- generated API models and request/response structures from `lance_namespace_rest_client.hpp`

It is intended to build and run on Linux, macOS, and Windows.

## Build

Requires CMake 3.20+ and a compiler with C++17 support.

```bash
make build
```



## C usage

```c
#include <lance_namespace/lance_namespace.h>

static const char *namespace_id(const lance_namespace_handle_t *handle) {
    return "example";
}

static void destroy(void *instance) {
    free(instance);
}
```

## C++ usage

```cpp
#include <lance_namespace/lance_namespace.hpp>

struct ExampleNamespace : lance_namespace::Namespace {
  std::string namespace_id() const override { return "example"; }
};

lance_namespace::register_namespace_impl(
    "example",
    [](const lance_namespace::Properties &) {
      return std::make_unique<ExampleNamespace>();
    });
```
