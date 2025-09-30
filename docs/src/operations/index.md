# Namespace Operations

The Lance Namespace Specification defines a list of operations that can be performed against any Lance namespace:

| Operation ID                                             | Current Version | Namespace | Table | Index | Metadata | Data | Transaction |
|----------------------------------------------------------|-----------------|-----------|-------|-------|----------|------|-------------|
| [CreateNamespace](create-namespace.md)                   | 1               | ✓         |       |       | ✓        |      |             |
| [ListNamespaces](list-namespaces.md)                     | 1               | ✓         |       |       | ✓        |      |             |
| [DescribeNamespace](describe-namespace.md)               | 1               | ✓         |       |       | ✓        |      |             |
| [DropNamespace](drop-namespace.md)                       | 1               | ✓         |       |       | ✓        |      |             |
| [NamespaceExists](namespace-exists.md)                   | 1               | ✓         |       |       | ✓        |      |             |
| [ListTables](list-tables.md)                             | 1               | ✓         | ✓     |       | ✓        |      |             |
| [RegisterTable](register-table.md)                       | 1               |           | ✓     |       | ✓        |      |             |
| [DescribeTable](describe-table.md)                       | 1               |           | ✓     |       | ✓        |      |             |
| [TableExists](table-exists.md)                           | 1               |           | ✓     |       | ✓        |      |             |
| [DropTable](drop-table.md)                               | 1               |           | ✓     |       | ✓        |      |             |
| [DeregisterTable](deregister-table.md)                   | 1               |           | ✓     |       | ✓        |      |             |
| [InsertIntoTable](insert-into-table.md)                  | 1               |           | ✓     |       |          | ✓    |             |
| [MergeInsertIntoTable](merge-insert-into-table.md)       | 1               |           | ✓     |       |          | ✓    |             |
| [UpdateTable](update-table.md)                           | 1               |           | ✓     |       |          | ✓    |             |
| [DeleteFromTable](delete-from-table.md)                  | 1               |           | ✓     |       |          | ✓    |             |
| [QueryTable](query-table.md)                             | 1               |           | ✓     |       |          | ✓    |             |
| [CountTableRows](count-table-rows.md)                    | 1               |           | ✓     |       |          | ✓    |             |
| [CreateTable](create-table.md)                           | 1               |           | ✓     |       |          | ✓    |             |
| [CreateTableIndex](create-table-index.md)                | 1               |           | ✓     | ✓     | ✓        |      |             |
| [ListTableIndices](list-table-indices.md)                | 1               |           | ✓     | ✓     | ✓        |      |             |
| [DescribeTableIndexStats](describe-table-index-stats.md) | 1               |           | ✓     | ✓     | ✓        |      |             |
| [RestoreTable](restore-table.md)                         | 1               |           | ✓     |       | ✓        |      |             |
| [ListTableVersions](list-table-versions.md)              | 1               |           | ✓     |       | ✓        |      |             |
| [ExplainTableQueryPlan](explain-table-query-plan.md)     | 1               |           | ✓     |       |          | ✓    |             |
| [AnalyzeTableQueryPlan](analyze-table-query-plan.md)     | 1               |           | ✓     |       |          | ✓    |             |
| [AlterTableAddColumns](alter-table-add-columns.md)       | 1               |           | ✓     |       |          | ✓    |             |
| [AlterTableAlterColumns](alter-table-alter-columns.md)   | 1               |           | ✓     |       | ✓        |      |             |
| [AlterTableDropColumns](alter-table-drop-columns.md)     | 1               |           | ✓     |       | ✓        |      |             |
| [GetTableStats](get-table-stats.md)                      | 1               |           | ✓     |       | ✓        |      |             |
| [ListTableTags](list-table-tags.md)                      | 1               |           | ✓     |       | ✓        |      |             |
| [GetTableTagVersion](get-table-tag-version.md)           | 1               |           | ✓     |       | ✓        |      |             |
| [CreateTableTag](create-table-tag.md)                    | 1               |           | ✓     |       | ✓        |      |             |
| [DeleteTableTag](delete-table-tag.md)                    | 1               |           | ✓     |       | ✓        |      |             |
| [UpdateTableTag](update-table-tag.md)                    | 1               |           | ✓     |       | ✓        |      |             |
| [DropTableIndex](drop-table-index.md)                    | 1               |           | ✓     | ✓     | ✓        |      |             |
| [DescribeTransaction](describe-transaction.md)           | 1               |           |       |       | ✓        |      | ✓           |
| [AlterTransaction](alter-transaction.md)                 | 1               |           |       |       | ✓        |      | ✓           |

## Recommended Basic Operations

To have a functional basic namespace implementation, the following metadata operations are recommended as a minimum:

**Namespace Metadata Operations:**
- [CreateNamespace](create-namespace.md) - Create a new namespace
- [ListNamespaces](list-namespaces.md) - List available namespaces
- [DescribeNamespace](describe-namespace.md) - Get namespace details
- [DropNamespace](drop-namespace.md) - Remove a namespace

**Table Metadata Operations:**
- [CreateEmptyTable](create-empty-table.md) - Create an empty table (metadata only)
- [ListTables](list-tables.md) - List tables in a namespace
- [DescribeTable](describe-table.md) - Get table details
- [DropTable](drop-table.md) - Remove a table

These operations provide the foundational metadata management capabilities needed for namespace and table administration without requiring data or index operation support. With the namespace able to provide basic information about the table, the Lance SDK can be used to fulfill the other operations.

## Operation Versioning

When backwards incompatible change is introduced,
a new operation version needs to be created, with a naming convention of `<operationId>V<version>`,
for example `ListNamespacesV2`, `DescribeTableV3`, etc.

## Operation Request and Response Schema

In general, each operation has a request and response.
The request and response schema is defined using JSON schema in the 
`components/schemas` section of the [OpenAPI specification](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/lancedb/lance-namespace/refs/heads/main/docs/src/rest.yaml).

!!! note
    For exceptions to this rule, see the Notes section of the operations

## Error Response Model

All error responses follow the JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807):

```yaml
--8<-- "src/rest.yaml:1622:1662"
```

## HTTP Status Codes and Responses

### 400 - Bad Request Error Response

```yaml
--8<-- "src/rest.yaml:3493:3510"
```

### 401 - Unauthorized Error Response

```yaml
--8<-- "src/rest.yaml:3511:3524"
```

### 403 - Forbidden Error Response

```yaml
--8<-- "src/rest.yaml:3525:3538"
```

### 404 - Not Found Error Response

```yaml
--8<-- "src/rest.yaml:3539:3553"
```

### 406 - Unsupported Operation Error Response

```yaml
--8<-- "src/rest.yaml:3554:3567"
```

### 409 - Conflict Error Response

```yaml
--8<-- "src/rest.yaml:3568:3581"
```

### 503 - Service Unavailable Error Response

```yaml
--8<-- "src/rest.yaml:3582:3597"
```

### 5XX - Server Error Response

```yaml
--8<-- "src/rest.yaml:3598:3614"
```