# Lance REST Namespace Implementation Spec

This document describes how the Lance REST Namespace catalog spec implements the Lance Namespace client spec.

## Object Mapping

### Namespace

| Client Spec Concept | REST Namespace Mapping |
|---------------------|------------------------|
| Root Namespace | The base URI endpoint specified by the `uri` configuration property |
| Child Namespace | Managed by the REST server; accessed via namespace routes |
| Namespace Identifier | String identifier using the configured delimiter (default `$`) |
| Namespace Properties | Managed by the REST server; accessed via DescribeNamespace |

### Table

| Client Spec Concept | REST Namespace Mapping |
|---------------------|------------------------|
| Table | Managed by the REST server |
| Table Identifier | String identifier using the configured delimiter (default `$`) |
| Table Location | Managed by the REST server; may be returned in DescribeTable response |
| Table Properties | Managed by the REST server; accessed via table operations |

## Operation Implementation

All operations are implemented by sending HTTP requests to the REST server endpoints.
The request and response bodies follow the OpenAPI specification, with exceptions documented in
[Non-Standard Operations](catalog-spec.md#non-standard-operations).

### Namespace Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| CreateNamespace | `/v1/namespace/{id}/create` | POST |
| ListNamespaces | `/v1/namespace/{id}/list` | GET |
| DescribeNamespace | `/v1/namespace/{id}/describe` | POST |
| DropNamespace | `/v1/namespace/{id}/drop` | POST |
| NamespaceExists | `/v1/namespace/{id}/exists` | POST |

### Table Metadata Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| CreateEmptyTable | `/v1/table/{id}/create_empty` | POST |
| RegisterTable | `/v1/table/{id}/register` | POST |
| ListTables | `/v1/namespace/{id}/table/list` | GET |
| DescribeTable | `/v1/table/{id}/describe` | POST |
| TableExists | `/v1/table/{id}/exists` | POST |
| DropTable | `/v1/table/{id}/drop` | POST |
| DeregisterTable | `/v1/table/{id}/deregister` | POST |
| RenameTable | `/v1/table/{id}/rename` | POST |

### Table Data Operations

| Operation | REST Route | HTTP Method | Content-Type |
|-----------|-----------|-------------|--------------|
| CreateTable | `/v1/table/{id}/create` | POST | `application/vnd.apache.arrow.stream` |
| InsertIntoTable | `/v1/table/{id}/insert` | POST | `application/vnd.apache.arrow.stream` |
| MergeInsertIntoTable | `/v1/table/{id}/merge_insert` | POST | `application/vnd.apache.arrow.stream` |
| UpdateTable | `/v1/table/{id}/update` | POST | `application/json` |
| DeleteFromTable | `/v1/table/{id}/delete` | POST | `application/json` |
| QueryTable | `/v1/table/{id}/query` | POST | `application/json` |
| CountTableRows | `/v1/table/{id}/count` | POST | `application/json` |

### Table Index Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| CreateTableIndex | `/v1/table/{id}/index/create` | POST |
| ListTableIndices | `/v1/table/{id}/index/list` | POST |
| DescribeTableIndexStats | `/v1/table/{id}/index/stats` | POST |
| DropTableIndex | `/v1/table/{id}/index/drop` | POST |

### Table Version Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| ListTableVersions | `/v1/table/{id}/versions/list` | POST |
| RestoreTable | `/v1/table/{id}/restore` | POST |

### Table Tag Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| ListTableTags | `/v1/table/{id}/tags/list` | POST |
| GetTableTagVersion | `/v1/table/{id}/tags/get` | POST |
| CreateTableTag | `/v1/table/{id}/tags/create` | POST |
| DeleteTableTag | `/v1/table/{id}/tags/delete` | POST |
| UpdateTableTag | `/v1/table/{id}/tags/update` | POST |

### Schema Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| AlterTableAddColumns | `/v1/table/{id}/alter/add_columns` | POST |
| AlterTableAlterColumns | `/v1/table/{id}/alter/alter_columns` | POST |
| AlterTableDropColumns | `/v1/table/{id}/alter/drop_columns` | POST |
| UpdateTableSchemaMetadata | `/v1/table/{id}/schema_metadata/update` | POST |

### Statistics Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| GetTableStats | `/v1/table/{id}/stats` | POST |

### Query Plan Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| ExplainTableQueryPlan | `/v1/table/{id}/explain` | POST |
| AnalyzeTableQueryPlan | `/v1/table/{id}/analyze` | POST |

### Transaction Operations

| Operation | REST Route | HTTP Method |
|-----------|-----------|-------------|
| DescribeTransaction | `/v1/transaction/{id}/describe` | POST |
| AlterTransaction | `/v1/transaction/{id}/alter` | POST |

## Data Transfer

For operations that transfer Arrow data (CreateTable, InsertIntoTable, MergeInsertIntoTable),
the HTTP request body contains Arrow IPC stream data with content type `application/vnd.apache.arrow.stream`.
Operation parameters are passed via query parameters and headers as documented in
[Non-Standard Operations](catalog-spec.md#non-standard-operations).

## Error Handling

All error responses follow the JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807).

The response body contains an [ErrorResponse](../client/operations/models/ErrorResponse.md) with a `code` field containing the Lance Namespace error code. See [Error Handling](../client/operations/errors.md) for the complete list of error codes and per-operation error documentation.

### Error Code to HTTP Status Mapping

REST namespace implementations must map Lance error codes to HTTP status codes as follows:

| Error Code | Name | HTTP Status |
|------------|------|-------------|
| 0 | Unsupported | 406 Not Acceptable |
| 1 | NamespaceNotFound | 404 Not Found |
| 2 | NamespaceAlreadyExists | 409 Conflict |
| 3 | NamespaceNotEmpty | 409 Conflict |
| 4 | TableNotFound | 404 Not Found |
| 5 | TableAlreadyExists | 409 Conflict |
| 6 | TableIndexNotFound | 404 Not Found |
| 7 | TableIndexAlreadyExists | 409 Conflict |
| 8 | TableTagNotFound | 404 Not Found |
| 9 | TableTagAlreadyExists | 409 Conflict |
| 10 | TransactionNotFound | 404 Not Found |
| 11 | TableVersionNotFound | 404 Not Found |
| 12 | TableColumnNotFound | 404 Not Found |
| 13 | InvalidInput | 400 Bad Request |
| 14 | ConcurrentModification | 409 Conflict |
| 15 | PermissionDenied | 403 Forbidden |
| 16 | Unauthenticated | 401 Unauthorized |
| 17 | ServiceUnavailable | 503 Service Unavailable |
| 18 | Internal | 500 Internal Server Error |
| 19 | InvalidTableState | 409 Conflict |
| 20 | TableSchemaValidationError | 400 Bad Request |

### Example Error Response

```json
{
  "error": "Table 'users' not found in namespace 'production'",
  "code": 4,
  "detail": "The table may have been dropped or renamed",
  "instance": "/v1/table/production$users/describe"
}
```
