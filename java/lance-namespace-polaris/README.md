# Lance Namespace Polaris

Polaris Catalog implementation for Lance namespace management.

## Overview

This module provides integration between Lance and Polaris Catalog using the Generic Table API. It allows Lance tables to be managed through Polaris Catalog's namespace hierarchy.

## Features

- Create, list, describe, and drop Lance tables in Polaris Catalog
- Uses Polaris Generic Table API for catalog operations
- Reuses RestClient from lance-namespace-core for HTTP operations
- Support for Polaris namespace hierarchy (catalog.namespace.table)

## Configuration

The Polaris namespace implementation requires the following configuration properties:

| Property | Description | Required | Default |
|----------|-------------|----------|---------|
| `polaris.endpoint` | Polaris server endpoint URL | Yes | - |
| `polaris.catalog` | Catalog name in Polaris | Yes | - |
| `polaris.auth.token` | Bearer token for authentication | No | - |
| `polaris.connect.timeout` | Connection timeout in milliseconds | No | 10000 |
| `polaris.read.timeout` | Read timeout in milliseconds | No | 30000 |
| `polaris.max.retries` | Maximum number of retry attempts | No | 3 |

## Usage

```java
import com.lancedb.lance.namespace.polaris.PolarisNamespace;
import com.lancedb.lance.namespace.LanceNamespace;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;

// Create configuration
Map<String, String> config = new HashMap<>();
config.put("polaris.endpoint", "http://localhost:8182");
config.put("polaris.catalog", "my_catalog");
config.put("polaris.auth.token", "your-auth-token");

// Initialize namespace
BufferAllocator allocator = new RootAllocator();
LanceNamespace namespace = new PolarisNamespace();
namespace.initialize(config, allocator);

// Create a table
CreateTableRequest request = CreateTableRequest.builder()
    .parent(ObjectIdentifier.of("my_namespace"))
    .table(ObjectIdentifier.of("my_table"))
    .location("s3://my-bucket/lance/my_table")
    .build();
    
CreateTableResponse response = namespace.createTable(request);
```

## Table Storage

Lance tables in Polaris are stored as Generic Tables with:
- `format`: Set to "lance" to identify Lance tables
- `base-location`: Points to the actual Lance table data location
- `properties`: Custom properties including Lance-specific metadata

## Authentication

The Polaris namespace supports bearer token authentication. Provide the token via the `polaris.auth.token` configuration property.

## Limitations

- Polaris Generic Table API does not support schema management directly
- Tables must be managed through Lance's own format at the storage layer
- The integration focuses on catalog metadata management only

## Testing

Unit tests are provided in `TestPolarisNamespace.java`. To run integration tests against a real Polaris instance, configure the test environment with appropriate credentials.