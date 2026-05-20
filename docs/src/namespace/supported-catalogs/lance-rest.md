# Lance REST Catalog

This document describes how the Lance REST Catalog implements the Lance Namespace Client operations.

## Background

The Lance REST Catalog provides access to Lance tables via a REST API. For details on the API design, endpoints, and data models, see the [Lance REST Catalog](../../catalog/rest/index.md) specification.

## Implementation Configuration Properties

The Lance REST Catalog implementation accepts the following configuration properties:

The **uri** property is required and specifies the URI endpoint for the REST API, for example `https://api.example.com/lance`.

The **delimiter** property specifies the delimiter used to parse object string identifiers in REST routes. Defaults to `$`. Other examples include `::` or `__delim__`.

Properties with the **headers.** prefix are passed as HTTP headers with every request to the REST server after removing the prefix. For example, `headers.Authorization` becomes the `Authorization` header. Common configurations include `headers.Authorization` for authentication tokens, `headers.X-API-Key` for API key authentication, and `headers.X-Request-ID` for request tracking.

## Object Mapping

### Namespace

The **root namespace** is represented by the delimiter character itself in REST routes (e.g., `$`). All REST API calls are made relative to the base URI.

A **child namespace** is managed by the REST server and accessed via namespace routes. The server is responsible for storing and organizing namespace metadata.

The **namespace identifier** is a list of strings representing the namespace path. For example, a namespace `["prod", "analytics"]` is serialized to `prod$analytics` in the REST route path using the configured delimiter (default `$`).

**Namespace properties** are managed by the REST server and accessed via the DescribeNamespace operation.

### Table

A **table** is managed by the REST server. The server handles table storage, versioning, and metadata management.

The **table identifier** is a list of strings representing the namespace path followed by the table name. For example, a table `["prod", "analytics", "users"]` represents a table named `users` in namespace `["prod", "analytics"]`. This is serialized to `prod$analytics$users` in the REST route path using the configured delimiter.

The **table location** is managed by the REST server and returned in the DescribeTable response. This location points to where the Lance table data is stored (e.g., an S3 path).

**Table properties** are managed by the REST server and accessed via table operations.

## Lance Table Identification

In a REST Catalog, the server is responsible for managing Lance tables. The client identifies tables by their string identifier and delegates all table operations to the server.

The server implementation must ensure that:

- Tables are stored as valid Lance table directories on the underlying storage
- The `location` field in DescribeTable response points to the Lance table root directory
- Table properties include any Lance-specific metadata required by the Lance SDK

## Basic Operations

### CreateNamespace

Creates a new namespace.

**HTTP Request:**

```
POST /v1/namespace/{id}/create
Content-Type: application/json
```

The request body contains optional namespace properties:

```json
{
  "properties": {
    "description": "Production analytics namespace"
  }
}
```

The implementation:

1. Parse the namespace identifier from the route path `{id}`
2. Validate the request body format
3. Check if the parent namespace exists (for nested namespaces)
4. Check if a namespace with this identifier already exists
5. Create the namespace in the server's storage
6. Return the created namespace details

**Response:**

```json
{
  "name": "analytics",
  "properties": {
    "description": "Production analytics namespace"
  }
}
```

**Error Handling:**

If the request body is malformed, return HTTP `400 Bad Request` with error code `13` (InvalidInput).

If a namespace with the same identifier already exists, return HTTP `409 Conflict` with error code `2` (NamespaceAlreadyExists).

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

### ListNamespaces

Lists child namespaces within a parent namespace.

**HTTP Request:**

```
GET /v1/namespace/{id}/list?page_token=xxx&limit=100
```

The `page_token` and `limit` query parameters support pagination.

The implementation:

1. Parse the parent namespace identifier from the route path `{id}`
2. Validate the parent namespace exists
3. Query the server's storage for child namespaces
4. Apply pagination using `page_token` and `limit`
5. Return the list of namespace names

**Response:**

```json
{
  "namespaces": ["analytics", "ml", "reporting"],
  "next_page_token": "abc123"
}
```

The `next_page_token` field is only present if there are more results.

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

### DescribeNamespace

Returns namespace metadata.

**HTTP Request:**

```
POST /v1/namespace/{id}/describe
Content-Type: application/json
```

The request body is empty:

```json
{}
```

The implementation:

1. Parse the namespace identifier from the route path `{id}`
2. Look up the namespace in the server's storage
3. Return the namespace name and properties

**Response:**

```json
{
  "name": "analytics",
  "properties": {
    "description": "Production analytics namespace",
    "created_at": "2024-01-15T10:30:00Z"
  }
}
```

**Error Handling:**

If the namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

### DropNamespace

Removes a namespace.

**HTTP Request:**

```
POST /v1/namespace/{id}/drop
Content-Type: application/json
```

The request body is empty:

```json
{}
```

The implementation:

1. Parse the namespace identifier from the route path `{id}`
2. Check that the namespace exists
3. Check that the namespace is empty (no child namespaces or tables)
4. Delete the namespace from the server's storage

**Response:**

```json
{}
```

**Error Handling:**

If the namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If the namespace contains tables or child namespaces, return HTTP `409 Conflict` with error code `3` (NamespaceNotEmpty).

### DeclareTable

Declares a new Lance table, reserving the table name and location without creating actual data files.

**HTTP Request:**

```
POST /v1/table/{id}/declare
Content-Type: application/json
```

The request body contains an optional location:

```json
{
  "location": "s3://bucket/data/users.lance"
}
```

The implementation:

1. Parse the table identifier from the route path `{id}`
2. Extract the parent namespace from the identifier
3. Validate the parent namespace exists
4. Check if a table with this identifier already exists
5. Determine the table location (use provided location or generate one)
6. Reserve the table in the server's storage
7. Register the table in the namespace

**Response:**

```json
{
  "location": "s3://bucket/data/users.lance",
  "storage_options": {
    "aws_access_key_id": "...",
    "aws_secret_access_key": "..."
  }
}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If a table with the same identifier already exists, return HTTP `409 Conflict` with error code `5` (TableAlreadyExists).

If there is a concurrent creation attempt, return HTTP `409 Conflict` with error code `14` (ConcurrentModification).

### ListTables

Lists tables within a namespace.

**HTTP Request:**

```
GET /v1/namespace/{id}/table/list?page_token=xxx&limit=100
```

The `page_token` and `limit` query parameters support pagination.

The implementation:

1. Parse the namespace identifier from the route path `{id}`
2. Validate the namespace exists
3. Query the server's storage for tables in the namespace
4. Apply pagination using `page_token` and `limit`
5. Return the list of table names

**Response:**

```json
{
  "tables": ["users", "orders", "products"],
  "next_page_token": "def456"
}
```

The `next_page_token` field is only present if there are more results.

**Error Handling:**

If the namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

### DescribeTable

Returns table metadata including schema and version.

**HTTP Request:**

```
POST /v1/table/{id}/describe
Content-Type: application/json
```

The request body can optionally specify a version:

```json
{
  "version": 5
}
```

The implementation:

1. Parse the table identifier from the route path `{id}`
2. Extract the parent namespace from the identifier
3. Validate the parent namespace exists
4. Look up the table in the server's storage
5. If `version` is specified, retrieve that specific version's metadata
6. Return the table metadata

**Response:**

```json
{
  "name": "users",
  "location": "s3://bucket/data/users.lance",
  "schema": {
    "fields": [
      {"name": "id", "type": {"name": "int64"}, "nullable": false},
      {"name": "name", "type": {"name": "utf8"}, "nullable": true}
    ]
  },
  "version": 5
}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If the specified version does not exist, return HTTP `404 Not Found` with error code `11` (TableVersionNotFound).

### DeregisterTable

Deregisters a table from the catalog while preserving its data on storage. The table metadata is removed from the catalog but the table files remain at their storage location.

**HTTP Request:**

```
POST /v1/table/{id}/deregister
Content-Type: application/json
```

The request body is empty:

```json
{}
```

The implementation:

1. Parse the table identifier from the route path `{id}`
2. Extract the parent namespace from the identifier
3. Validate the parent namespace exists
4. Look up the table in the server's storage
5. Remove the table registration from the catalog
6. Return the table location and properties for reference

**Response:**

```json
{
  "location": "s3://bucket/data/users.lance",
  "properties": {
    "created_at": "2024-01-15T10:30:00Z"
  }
}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

## Additional Operations

The REST Catalog supports all operations defined in the [Lance Namespace Client spec](../operations/index.md). Each operation follows the same HTTP request/response pattern as the basic operations above.

### DropTable

Removes a table and its data.

**HTTP Request:**

```
POST /v1/table/{id}/drop
Content-Type: application/json
```

The request body is empty:

```json
{}
```

The implementation:

1. Parse the table identifier from the route path `{id}`
2. Extract the parent namespace from the identifier
3. Validate the parent namespace exists
4. Look up the table in the server's storage
5. Delete the table data from storage
6. Remove the table registration from the catalog

**Response:**

```json
{}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If there is a storage permission error, return HTTP `403 Forbidden` with error code `15` (PermissionDenied).

If there is an unexpected server error, return HTTP `500 Internal Server Error` with error code `18` (Internal).

### RegisterTable

Registers an existing Lance table at a given location.

**HTTP Request:**

```
POST /v1/table/{id}/register
Content-Type: application/json
```

```json
{
  "location": "s3://bucket/data/users.lance"
}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If a table with the same identifier already exists, return HTTP `409 Conflict` with error code `5` (TableAlreadyExists).

If the location does not contain a valid Lance table, return HTTP `400 Bad Request` with error code `13` (InvalidInput).

### RenameTable

Renames a table, optionally moving it to a different namespace.

**HTTP Request:**

```
POST /v1/table/{id}/rename
Content-Type: application/json
```

```json
{
  "new_id": ["new_namespace", "new_table_name"]
}
```

**Error Handling:**

If the source table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If a table with the new identifier already exists, return HTTP `409 Conflict` with error code `5` (TableAlreadyExists).

If the target namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

### CreateTableVersion

Creates a new version entry for a table.

**HTTP Request:**

```
POST /v1/table/{id}/version/create
Content-Type: application/json
```

```json
{
  "version": 2,
  "manifest_path": "s3://bucket/data/users.lance/_versions/staging-uuid.manifest",
  "naming_scheme": "V2"
}
```

**Error Handling:**

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If the version already exists, return HTTP `409 Conflict` with error code `12` (TableVersionAlreadyExists).

### ListTableVersions

Lists version entries for a table.

**HTTP Request:**

```
GET /v1/table/{id}/version/list?descending=true&limit=100
```

**Error Handling:**

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

### DescribeTableVersion

Retrieves details for a specific table version.

**HTTP Request:**

```
POST /v1/table/{id}/version/describe
Content-Type: application/json
```

```json
{
  "version": 2
}
```

**Error Handling:**

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If the version does not exist, return HTTP `404 Not Found` with error code `11` (TableVersionNotFound).

### BatchCreateTableVersions

Atomically creates version entries for multiple tables.

**HTTP Request:**

```
POST /v1/table/version/batch-create
Content-Type: application/json
```

```json
{
  "entries": [
    {
      "id": ["namespace", "table1"],
      "version": 2,
      "manifest_path": "s3://bucket/data/table1.lance/_versions/staging-uuid.manifest"
    },
    {
      "id": ["namespace", "table2"],
      "version": 3,
      "manifest_path": "s3://bucket/data/table2.lance/_versions/staging-uuid.manifest"
    }
  ]
}
```

**Error Handling:**

If any table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If any version already exists, return HTTP `409 Conflict` with error code `12` (TableVersionAlreadyExists).

### BatchDeleteTableVersions

Deletes multiple version entries for a table.

**HTTP Request:**

```
POST /v1/table/{id}/version/batch-delete
Content-Type: application/json
```

```json
{
  "versions": [1, 2, 3]
}
```

**Error Handling:**

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If any specified version does not exist and `ignore_missing` is false, return HTTP `404 Not Found` with error code `11` (TableVersionNotFound).

### NamespaceExists

Checks if a namespace exists.

**HTTP Request:**

```
POST /v1/namespace/{id}/exists
```

### TableExists

Checks if a table exists.

**HTTP Request:**

```
POST /v1/table/{id}/exists
```

### ListAllTables

Lists all tables across all namespaces.

**HTTP Request:**

```
GET /v1/table/list?page_token=xxx&limit=100
```

### RestoreTable

Restores a table to a previous version.

**HTTP Request:**

```
POST /v1/table/{id}/restore
Content-Type: application/json
```

```json
{
  "version": 5
}
```

### CreateTable

Creates a new table with initial data.

For REST namespace, `CreateTableRequest` fields are passed as follows:

- `id`: path parameter
- `mode`: query parameter
- `properties`: a single JSON-encoded query parameter such as
  `properties={"user":"alice","team":"eng"}`; these are business logic properties managed
  by the namespace implementation outside Lance context
- `storage_options`: a single JSON-encoded query parameter such as
  `storage_options={"aws_region":"us-east-1","timeout":"30s"}`; these configure write-time
  overrides for data and metadata written during table creation

**HTTP Request:**

```
POST /v1/table/{id}/create
Content-Type: application/vnd.apache.arrow.stream
```

**Response:**

```json
{
  "location": "s3://bucket/data/users.lance",
  "version": 1,
  "storage_options": {
    "aws_region": "us-east-1"
  },
  "properties": {
    "user": "alice"
  }
}
```

### GetTableStats

Returns statistics for a table.

**HTTP Request:**

```
POST /v1/table/{id}/stats
```

### UpdateTableSchemaMetadata

Updates schema-level metadata for a table.

**HTTP Request:**

```
POST /v1/table/{id}/schema/metadata
Content-Type: application/json
```

### AlterTableAddColumns

Adds new columns to a table.

**HTTP Request:**

```
POST /v1/table/{id}/add_columns
Content-Type: application/json
```

### AlterTableAlterColumns

Modifies existing columns in a table.

**HTTP Request:**

```
POST /v1/table/{id}/alter_columns
Content-Type: application/json
```

### AlterTableBackfillColumns

Triggers an async backfill job for a computed column.

**HTTP Request:**

```
POST /v1/table/{id}/backfill_column
Content-Type: application/json
```

### AlterTableDropColumns

Removes columns from a table.

**HTTP Request:**

```
POST /v1/table/{id}/drop_columns
Content-Type: application/json
```

### RefreshMaterializedView

Triggers an async materialized view refresh.

**HTTP Request:**

```
POST /v1/materialized_view/{id}/refresh
Content-Type: application/json
```

### InsertIntoTable

Inserts data into a table.

**HTTP Request:**

```
POST /v1/table/{id}/insert
Content-Type: application/json
```

### MergeInsertIntoTable

Performs a merge insert (upsert) operation.

**HTTP Request:**

```
POST /v1/table/{id}/merge-insert
Content-Type: application/json
```

### UpdateTable

Updates rows in a table.

**HTTP Request:**

```
POST /v1/table/{id}/update
Content-Type: application/json
```

### DeleteFromTable

Deletes rows from a table.

**HTTP Request:**

```
POST /v1/table/{id}/delete
Content-Type: application/json
```

### QueryTable

Queries data from a table.

**HTTP Request:**

```
POST /v1/table/{id}/query
Content-Type: application/json
```

### CountTableRows

Counts rows in a table.

**HTTP Request:**

```
POST /v1/table/{id}/count
Content-Type: application/json
```

### ExplainTableQueryPlan

Returns the query execution plan.

**HTTP Request:**

```
POST /v1/table/{id}/query/explain
Content-Type: application/json
```

### AnalyzeTableQueryPlan

Analyzes the query execution plan with statistics.

**HTTP Request:**

```
POST /v1/table/{id}/query/analyze
Content-Type: application/json
```

### CreateTableIndex

Creates a vector index on a table.

**HTTP Request:**

```
POST /v1/table/{id}/index/create
Content-Type: application/json
```

### CreateTableScalarIndex

Creates a scalar index on a table.

**HTTP Request:**

```
POST /v1/table/{id}/index/create-scalar
Content-Type: application/json
```

### ListTableIndices

Lists all indices on a table.

**HTTP Request:**

```
GET /v1/table/{id}/index/list
```

### DescribeTableIndexStats

Returns statistics for a table index.

**HTTP Request:**

```
POST /v1/table/{id}/index/{index_name}/stats
```

### DropTableIndex

Removes an index from a table.

**HTTP Request:**

```
POST /v1/table/{id}/index/{index_name}/drop
```

### ListTableTags

Lists all tags for a table.

**HTTP Request:**

```
GET /v1/table/{id}/tag/list
```

### GetTableTagVersion

Gets the version associated with a tag.

**HTTP Request:**

```
POST /v1/table/{id}/tag/{tag_name}/describe
```

### CreateTableTag

Creates a new tag for a table version.

**HTTP Request:**

```
POST /v1/table/{id}/tag/create
Content-Type: application/json
```

### DeleteTableTag

Deletes a tag from a table.

**HTTP Request:**

```
POST /v1/table/{id}/tag/{tag_name}/delete
```

### UpdateTableTag

Updates a tag to point to a different version.

**HTTP Request:**

```
POST /v1/table/{id}/tag/{tag_name}/update
Content-Type: application/json
```

### DescribeTransaction

Returns details about a transaction.

**HTTP Request:**

```
POST /v1/transaction/{id}/describe
```

### AlterTransaction

Modifies a transaction's state.

**HTTP Request:**

```
POST /v1/transaction/{id}/alter
Content-Type: application/json
```

## Error Response Format

All error responses follow the JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807).

The response body contains an [ErrorResponse](../operations/models/ErrorResponse.md) with a `code` field containing the Lance Namespace error code. See [Error Handling](../operations/errors.md) for the complete list of error codes.

**Example error response:**

```json
{
  "error": "Table 'users' not found in namespace 'production'",
  "code": 4,
  "detail": "java.lang.RuntimeException: Table not found\n\tat com.example.TableService.describe(TableService.java:42)\n\tat ...",
  "instance": "/v1/table/production$users/describe"
}
```

The `detail` field contains detailed error information such as stack traces for debugging purposes.

## Error Code to HTTP Status Mapping

REST Catalog implementations must map Lance error codes to HTTP status codes as follows:

- Error code `0` (Unsupported) maps to HTTP `406 Not Acceptable`
- Error codes `1`, `4`, `6`, `8`, `10`, `11`, `12` (not found errors) map to HTTP `404 Not Found`
- Error codes `2`, `3`, `5`, `7`, `9`, `14`, `19` (conflict errors) map to HTTP `409 Conflict`
- Error codes `13`, `20` (input validation errors) map to HTTP `400 Bad Request`
- Error code `15` (PermissionDenied) maps to HTTP `403 Forbidden`
- Error code `16` (Unauthenticated) maps to HTTP `401 Unauthorized`
- Error code `17` (ServiceUnavailable) maps to HTTP `503 Service Unavailable`
- Error code `18` (Internal) maps to HTTP `500 Internal Server Error`
- Error code `21` (Throttling) maps to HTTP `429 Too Many Requests`
