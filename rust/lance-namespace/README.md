# lance-namespace

Rust implementation of the Lance Namespace trait with directory and REST implementations.

## Overview

This crate provides a Rust implementation of the Lance Namespace specification, with two main implementations:

- **DirNamespace**: A directory-based namespace using OpenDAL for storage abstraction
- **RestNamespace**: A REST client implementation for communicating with Lance REST servers

## Features

- Async trait-based design for high performance
- Type-safe error handling
- Support for multiple storage backends through OpenDAL
- Full REST API client functionality
- Comprehensive test coverage

## Usage

```rust
use lance_namespace::{connect, LanceNamespace};
use std::collections::HashMap;

// Connect to a directory namespace
let mut properties = HashMap::new();
properties.insert("root".to_string(), "/path/to/data".to_string());
let namespace = connect("dir", properties).unwrap();

// Connect to a REST namespace
let mut properties = HashMap::new();
properties.insert("uri".to_string(), "http://localhost:8080".to_string());
let rest_namespace = connect("rest", properties).unwrap();

// Use the namespace
let request = ListTablesRequest {
    id: None,
    page_token: None,
    limit: None,
};
let response = namespace.list_tables(request).await.unwrap();
```

## Implementations

### DirNamespace

The `DirNamespace` implementation provides a file system-based namespace using OpenDAL for storage abstraction. It supports:

- Local filesystem operations
- Table creation and management (basic operations)
- Integration with Lance datasets
- Storage abstraction for future cloud support

### RestNamespace

The `RestNamespace` implementation provides a full REST client for Lance namespace servers. It supports:

- All namespace operations (create, list, describe, drop)
- All table operations (create, drop, query, insert, update, etc.)
- Index management
- Transaction support
- Configurable headers and delimiters

## Error Handling

The crate uses a custom `NamespaceError` type that covers:

- Operation not supported errors
- Configuration errors
- IO errors
- HTTP errors
- Arrow/Lance specific errors

## Dependencies

- `async-trait`: For async trait definitions
- `opendal`: For storage abstraction
- `reqwest`: For HTTP client functionality
- `arrow`: For Arrow schema handling
- `lance-namespace-reqwest-client`: For REST API models
- `serde`: For serialization/deserialization
- `tokio`: For async runtime

## Testing

Run the tests with:

```bash
cargo test
```

The tests include unit tests for configuration and integration tests for both namespace implementations.