# Unity Catalog Namespace for Lance

This module provides Unity Catalog integration for Lance, allowing you to manage Lance tables through Unity Catalog's unified governance layer.

## Features

- **Namespace Management**: Create, list, describe, and drop Unity Catalog schemas
- **Table Management**: Create, list, describe, and drop Lance tables in Unity Catalog
- **Arrow Schema Support**: Full support for Apache Arrow schemas and IPC format
- **Authentication**: Support for Unity Catalog authentication tokens
- **REST API Integration**: Built on Unity Catalog REST API v2.1

## Installation

```bash
pip install lance-namespace[unity]
```

## Configuration

The Unity namespace requires the following configuration properties:

| Property | Description | Required | Default |
|----------|-------------|----------|---------|
| `unity.endpoint` | Unity Catalog server endpoint URL | Yes | - |
| `unity.catalog` | Default catalog name | No | `unity` |
| `unity.root` | Root directory for Lance data files | No | `/tmp/lance` |
| `unity.auth_token` | Bearer token for authentication | No | - |
| `unity.connect_timeout_millis` | Connection timeout in milliseconds | No | `10000` |
| `unity.read_timeout_millis` | Read timeout in milliseconds | No | `300000` |
| `unity.max_retries` | Maximum number of retry attempts | No | `3` |

## Usage

### Basic Connection

```python
from lance_namespace import connect

# Configure Unity Catalog connection
config = {
    "unity.endpoint": "https://your-unity-catalog.example.com",
    "unity.catalog": "main",
    "unity.root": "/data/lance",
    "unity.auth_token": "your-auth-token",  # Optional
}

# Connect to Unity Catalog
namespace = connect("unity", config)
```

### Managing Namespaces (Schemas)

```python
from lance_namespace import (
    ListNamespacesRequest,
    CreateNamespaceRequest,
    DescribeNamespaceRequest,
    DropNamespaceRequest,
)

# List schemas in a catalog
list_req = ListNamespacesRequest()
list_req.id = ["main"]
schemas = namespace.list_namespaces(list_req)

# Create a new schema
create_req = CreateNamespaceRequest()
create_req.id = ["main", "my_schema"]
create_req.properties = {"owner": "data_team"}
namespace.create_namespace(create_req)

# Describe a schema
desc_req = DescribeNamespaceRequest()
desc_req.id = ["main", "my_schema"]
info = namespace.describe_namespace(desc_req)

# Drop a schema
drop_req = DropNamespaceRequest()
drop_req.id = ["main", "my_schema"]
drop_req.behavior = DropNamespaceRequest.BehaviorEnum.CASCADE
namespace.drop_namespace(drop_req)
```

### Managing Tables

```python
import pyarrow as pa
import pyarrow.ipc as ipc
import io
from lance_namespace import (
    CreateTableRequest,
    CreateEmptyTableRequest,
    ListTablesRequest,
    DescribeTableRequest,
    DropTableRequest,
)

# Define Arrow schema
arrow_schema = pa.schema([
    pa.field("id", pa.int64()),
    pa.field("name", pa.string()),
    pa.field("value", pa.float64()),
])

# Create Arrow IPC stream
buf = io.BytesIO()
writer = ipc.new_stream(buf, arrow_schema)
writer.close()
ipc_data = buf.getvalue()

# Create a table with schema
create_req = CreateTableRequest()
create_req.id = ["main", "my_schema", "my_table"]
create_req.properties = {"description": "My Lance table"}
response = namespace.create_table(create_req, ipc_data)

# Create an empty table (metadata only)
empty_req = CreateEmptyTableRequest()
empty_req.id = ["main", "my_schema", "empty_table"]
namespace.create_empty_table(empty_req)

# List tables in a schema
list_req = ListTablesRequest()
list_req.id = ["main", "my_schema"]
tables = namespace.list_tables(list_req)

# Describe a table
desc_req = DescribeTableRequest()
desc_req.id = ["main", "my_schema", "my_table"]
info = namespace.describe_table(desc_req)

# Drop a table
drop_req = DropTableRequest()
drop_req.id = ["main", "my_schema", "my_table"]
namespace.drop_table(drop_req)
```

## Architecture

The Unity namespace implementation consists of:

1. **UnityNamespace**: Main implementation class that implements the `LanceNamespace` interface
2. **UnityNamespaceConfig**: Configuration handler for Unity-specific settings
3. **RestClient**: HTTP client for Unity Catalog REST API communication
4. **Data Models**: Unity-specific data models (SchemaInfo, TableInfo, ColumnInfo)
5. **Type Converters**: Arrow type to Unity type conversion utilities

## Unity Catalog Integration

### Table Metadata

Lance tables are registered in Unity Catalog as EXTERNAL tables with the following properties:

- `table_type`: Set to `"lance"` to identify Lance tables
- `managed_by`: Set to `"storage"` for data-backed tables or `"catalog"` for metadata-only tables
- `version`: Lance dataset version number
- Custom properties can be added through the API

### Data Storage

Lance dataset files are stored at the location specified by:
- Default: `{unity.root}/{catalog}/{schema}/{table}`
- Custom: Can be specified in `CreateEmptyTableRequest.location`

### Schema Conversion

The implementation provides automatic conversion between:
- Apache Arrow types → Unity Catalog types
- Arrow schemas → Unity column definitions

Supported type mappings:

| Arrow Type | Unity Type |
|------------|------------|
| string/large_string | STRING |
| int32 | INT |
| int64 | BIGINT |
| float32 | FLOAT |
| float64 | DOUBLE |
| bool | BOOLEAN |
| date32 | DATE |
| timestamp | TIMESTAMP |

## Error Handling

The implementation provides specific exception types:

- `LanceNamespaceException`: Base exception for namespace operations
  - Status codes: 400 (Bad Request), 404 (Not Found), 409 (Conflict), 500 (Internal Error)
- `RestClientException`: HTTP communication errors

## Testing

Run the test suite:

```bash
pytest python/lance_namespace/tests/test_unity.py
```

## Example

See [examples/unity_example.py](examples/unity_example.py) for a complete working example.

## Limitations

- Only Lance tables are supported (filtered by `table_type=lance` property)
- Unity Catalog must be configured to allow EXTERNAL table creation
- Arrow schema extraction from existing Lance datasets requires the dataset to be accessible

## Compatibility

- Unity Catalog API v2.1+
- Python 3.9+
- Apache Arrow 14.0.0+
- PyLance 0.18.0+