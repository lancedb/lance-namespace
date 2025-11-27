# lance-namespace

Lance Namespace interface and plugin registry.

## Overview

This package provides:
- `LanceNamespace` ABC interface for namespace implementations
- `connect()` factory function for creating namespace instances
- `register_namespace_impl()` for external implementation registration
- Re-exported model types from `lance_namespace_urllib3_client`

## Installation

```bash
pip install lance-namespace
```

## Usage

```python
import lance_namespace

# Connect using native implementations (requires lance package)
ns = lance_namespace.connect("dir", {"root": "/path/to/data"})
ns = lance_namespace.connect("rest", {"uri": "http://localhost:4099"})

# Register a custom implementation
lance_namespace.register_namespace_impl("glue", "lance_glue.GlueNamespace")
ns = lance_namespace.connect("glue", {"catalog": "my_catalog"})
```

## Creating Custom Implementations

```python
from lance_namespace import LanceNamespace

class MyNamespace(LanceNamespace):
    def namespace_id(self) -> str:
        return "MyNamespace { ... }"

    # Override other methods as needed
```

## License

Apache-2.0
