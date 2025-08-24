# Polaris Namespace

The Polaris namespace implementation provides integration between Lance and [Polaris Catalog](https://github.com/polaris-catalog/polaris) using the Generic Table API.

## Overview

Polaris Catalog is an open-source catalog implementation that provides a REST API for managing tables and namespaces. The Lance integration uses Polaris's Generic Table API to store and manage Lance tables alongside other table formats in a unified catalog.

## Architecture

### Generic Table API

The Polaris namespace implementation uses the Generic Table API endpoints:

- **Namespace Operations**: Standard Iceberg REST API endpoints (`/namespaces`)
- **Table Operations**: Generic Table API endpoints (`/namespaces/{namespace}/generic-tables`)

### Table Storage Model

Lance tables in Polaris are stored as Generic Tables with the following structure:

```json
{
  "name": "my_table",
  "format": "lance",
  "base-location": "s3://bucket/path/to/table",
  "properties": {
    "table_type": "lance",
    "managed_by": "lance-namespace",
    "version": "1",
    "created_at": "2025-08-23T12:00:00Z"
  }
}
```

## Configuration

The Polaris namespace requires the following configuration properties:

| Property | Required | Description | Default |
|----------|----------|-------------|---------|
| `polaris.endpoint` | Yes | Polaris server endpoint URL (e.g., `http://localhost:8182`) | - |
| `polaris.catalog` | Yes | Catalog name in Polaris | - |
| `polaris.auth.token` | No | Bearer token for authentication | - |
| `polaris.connect.timeout` | No | Connection timeout in milliseconds | 10000 |
| `polaris.read.timeout` | No | Read timeout in milliseconds | 30000 |
| `polaris.max.retries` | No | Maximum retry attempts for failed requests | 3 |

### Example Configuration

```java
Map<String, String> config = new HashMap<>();
config.put("polaris.endpoint", "http://localhost:8182");
config.put("polaris.catalog", "my_catalog");
config.put("polaris.auth.token", "your-auth-token");
```

## Table Definition

### Creating Lance Tables

When creating a Lance table through the Polaris namespace:

1. The table is registered as a Generic Table with `format: "lance"`
2. The `base-location` points to the actual Lance table data location
3. Table properties include Lance-specific metadata

### Table Properties

The following properties are automatically set for Lance tables:

- `table_type`: Always set to `"lance"` for identification
- `managed_by`: Set to `"lance-namespace"` to indicate management by this integration
- `version`: Table format version
- `created_at`: ISO-8601 timestamp of table creation
- `comment`: Optional table description (stored in properties map)

## API Mapping

### Namespace Operations

| Lance Operation | Polaris API Endpoint | Method |
|-----------------|---------------------|---------|
| `createNamespace` | `/namespaces` | POST |
| `describeNamespace` | `/namespaces/{namespace}` | GET |
| `listNamespaces` | `/namespaces` | GET |
| `dropNamespace` | `/namespaces/{namespace}` | DELETE |
| `namespaceExists` | `/namespaces/{namespace}` | GET |

### Table Operations

| Lance Operation | Polaris API Endpoint | Method |
|-----------------|---------------------|---------|
| `createTable` | `/namespaces/{ns}/generic-tables` | POST |
| `describeTable` | `/namespaces/{ns}/generic-tables/{table}` | GET |
| `listTables` | `/namespaces/{ns}/generic-tables` | GET |
| `dropTable` | `/namespaces/{ns}/generic-tables/{table}` | DELETE |
| `tableExists` | `/namespaces/{ns}/generic-tables/{table}` | GET |

## Authentication

The Polaris namespace supports bearer token authentication:

```java
config.put("polaris.auth.token", "your-bearer-token");
```

The token is included in the `Authorization` header as `Bearer {token}` for all API requests.

## Error Handling

The implementation maps Polaris API errors to Lance namespace exceptions:

- **404 Not Found**: Thrown when a namespace or table doesn't exist
- **409 Conflict**: Thrown when attempting to create an existing table
- **500 Server Error**: Generic server errors are wrapped with context

## Limitations

1. **Schema Management**: The Generic Table API does not directly support Arrow schema storage. Schema information must be managed at the Lance storage layer.

2. **Table Properties**: While Polaris supports arbitrary properties, complex Lance-specific metadata may need special handling.

3. **Transaction Support**: The current implementation does not support multi-table transactions.

4. **Data Operations**: The Polaris integration focuses on catalog metadata management. Actual data operations (insert, update, delete, query) are performed directly against the Lance storage layer.

## Implementation Details

### RestClient Usage

The implementation reuses the `RestClient` from `lance-namespace-core` for HTTP operations:

```java
RestClient.builder()
    .baseUrl(config.getFullApiUrl())
    .connectTimeout(config.getConnectTimeout())
    .readTimeout(config.getReadTimeout())
    .maxRetries(config.getMaxRetries())
    .build();
```

### Response Translation

Polaris Generic Table responses are translated to Lance namespace responses:

- Table `base-location` → `location` in Lance response
- Table `doc` → `comment` property in Lance response
- Generic properties are passed through

### Table Identification

Lance tables are identified in Polaris by:
1. `format` field set to `"lance"`
2. `table_type` property set to `"lance"`

This allows Polaris to manage Lance tables alongside other formats (Iceberg, Delta, etc.).

## Example Usage

```java
// Initialize namespace
BufferAllocator allocator = new RootAllocator();
LanceNamespace namespace = new PolarisNamespace();
namespace.initialize(config, allocator);

// Create namespace
CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
nsRequest.setId(Arrays.asList("my_catalog", "my_schema"));
namespace.createNamespace(nsRequest);

// Create table
CreateTableRequest tableRequest = new CreateTableRequest();
tableRequest.setId(Arrays.asList("my_catalog", "my_schema", "my_table"));
tableRequest.setLocation("s3://my-bucket/lance/my_table");
CreateTableResponse response = namespace.createTable(tableRequest, new byte[0]);

// List tables
ListTablesRequest listRequest = new ListTablesRequest();
listRequest.setId(Arrays.asList("my_catalog", "my_schema"));
ListTablesResponse tables = namespace.listTables(listRequest);
```

## Testing

Unit tests use mocked `RestClient` to verify API interactions without requiring a running Polaris instance. Integration tests against a real Polaris deployment should be configured with appropriate credentials.

## References

- [Polaris Catalog](https://github.com/polaris-catalog/polaris)
- [Polaris Generic Table API Specification](https://github.com/polaris-catalog/polaris/blob/main/spec/polaris-catalog-apis/generic-tables-api.yaml)
- [Lance Format](https://github.com/lancedb/lance)