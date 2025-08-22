# lance-namespace

Rust implementation of the Lance Namespace specification.

For documentation and usage examples, visit: https://lancedb.github.io/lance-namespace/

## Installation

Add this to your `Cargo.toml`:

```toml
[dependencies]
lance-namespace = "0.1.0"
```

## Quick Start

```rust
use lance_namespace::connect;
use std::collections::HashMap;

let mut properties = HashMap::new();
properties.insert("root".to_string(), "/path/to/data".to_string());
let namespace = connect("dir", properties)?;
```

For complete documentation, see the [Lance Namespace specification](https://lancedb.github.io/lance-namespace/).