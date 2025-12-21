# Lance REST Namespace Implementation Spec

This document describes how the Lance REST Namespace implements the Lance Namespace client spec.

## Background

The Lance REST Namespace is a catalog that provides access to Lance tables via a REST API. For details on the API design, endpoints, and data models, see the [REST Namespace Catalog Spec](catalog-spec.md).

## Namespace Implementation Configuration Properties

The Lance REST namespace implementation accepts the following configuration properties:

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

In a REST Namespace, the server is responsible for managing Lance tables. The client identifies tables by their string identifier and delegates all table operations to the server.

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
6. Remove the table registration from the namespace

**Response:**

```json
{}
```

**Error Handling:**

If the parent namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).

If the table does not exist, return HTTP `404 Not Found` with error code `4` (TableNotFound).

If there is a storage permission error, return HTTP `403 Forbidden` with error code `15` (PermissionDenied).

If there is an unexpected server error, return HTTP `500 Internal Server Error` with error code `18` (Internal).

## Error Response Format

All error responses follow the JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807).

The response body contains an [ErrorResponse](../client/operations/models/ErrorResponse.md) with a `code` field containing the Lance Namespace error code. See [Error Handling](../client/operations/errors.md) for the complete list of error codes.

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

REST namespace implementations must map Lance error codes to HTTP status codes as follows:

- Error code `0` (Unsupported) maps to HTTP `406 Not Acceptable`
- Error codes `1`, `4`, `6`, `8`, `10`, `11`, `12` (not found errors) map to HTTP `404 Not Found`
- Error codes `2`, `3`, `5`, `7`, `9`, `14`, `19` (conflict errors) map to HTTP `409 Conflict`
- Error codes `13`, `20` (input validation errors) map to HTTP `400 Bad Request`
- Error code `15` (PermissionDenied) maps to HTTP `403 Forbidden`
- Error code `16` (Unauthenticated) maps to HTTP `401 Unauthorized`
- Error code `17` (ServiceUnavailable) maps to HTTP `503 Service Unavailable`
- Error code `18` (Internal) maps to HTTP `500 Internal Server Error`
