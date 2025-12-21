# Namespace Operations

The Lance Namespace Specification defines a list of operations that can be performed against any Lance namespace.

## OpenAPI Standardization

The spec uses [OpenAPI](https://www.openapis.org/) to define the request and response models for each operation.
This standardization allows clients in any language to generate a client library from the
[OpenAPI specification](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/lance-format/lance-namespace/refs/heads/main/docs/src/rest.yaml)
and use it to invoke operations with the corresponding request model, receiving responses in the expected response model.

The actual execution of an operation can be:

- **Client-side**: The operation is executed entirely within the client (e.g., directory namespace)
- **Server-side**: The operation is sent to a remote server for execution (e.g., REST namespace)
- **Hybrid**: A combination of both, depending on the integrated catalog spec and service

This flexibility allows the same client interface to work across different namespace implementations
while maintaining consistent request/response contracts.

## Duality with REST Namespace Spec

The request and response models defined here are designed to work seamlessly with the
[REST Namespace](../../rest/catalog-spec.md) spec. The REST namespace uses these same schemas directly as
HTTP request and response bodies, minimizing data conversion between client and server.

This duality explains why certain fields like `id` are marked as optional in the request models:

- **In REST Namespace Spec**: The object identifier is already present in the REST route path
  (e.g., `/v1/table/{id}/describe`), so the `id` field in the request body is optional and
  can be omitted to avoid redundancy.
- **In Client-Side Access Spec**: When invoking operations directly through a client library
  (e.g., for directory namespace), the `id` field **must be specified** in the request since
  there is no REST route to carry this information.

When both the route path and request body contain the `id`, the REST server must validate
that they match and return a 400 Bad Request error if they differ. 
See [REST Routes](../../rest/catalog-spec.md#rest-routes) for more details.

## Operation List

| Operation ID              | Current Version | Namespace | Table | Index | Metadata | Data | Transaction |
|---------------------------|-----------------|-----------|-------|-------|----------|------|-------------|
| CreateNamespace           | 1               | ✓         |       |       | ✓        |      |             |
| ListNamespaces            | 1               | ✓         |       |       | ✓        |      |             |
| DescribeNamespace         | 1               | ✓         |       |       | ✓        |      |             |
| DropNamespace             | 1               | ✓         |       |       | ✓        |      |             |
| NamespaceExists           | 1               | ✓         |       |       | ✓        |      |             |
| ListTables                | 1               | ✓         | ✓     |       | ✓        |      |             |
| ListAllTables             | 1               |           | ✓     |       | ✓        |      |             |
| RegisterTable             | 1               |           | ✓     |       | ✓        |      |             |
| DescribeTable             | 1               |           | ✓     |       | ✓        |      |             |
| TableExists               | 1               |           | ✓     |       | ✓        |      |             |
| DropTable                 | 1               |           | ✓     |       | ✓        |      |             |
| DeregisterTable           | 1               |           | ✓     |       | ✓        |      |             |
| InsertIntoTable           | 1               |           | ✓     |       |          | ✓    |             |
| MergeInsertIntoTable      | 1               |           | ✓     |       |          | ✓    |             |
| UpdateTable               | 1               |           | ✓     |       |          | ✓    |             |
| DeleteFromTable           | 1               |           | ✓     |       |          | ✓    |             |
| QueryTable                | 1               |           | ✓     |       |          | ✓    |             |
| CountTableRows            | 1               |           | ✓     |       |          | ✓    |             |
| CreateTable               | 1               |           | ✓     |       |          | ✓    |             |
| DeclareTable              | 1               |           | ✓     |       | ✓        |      |             |
| CreateEmptyTable          | 1 (deprecated)  |           | ✓     |       | ✓        |      |             |
| CreateTableIndex          | 1               |           | ✓     | ✓     | ✓        |      |             |
| CreateTableScalarIndex    | 1               |           | ✓     | ✓     | ✓        |      |             |
| ListTableIndices          | 1               |           | ✓     | ✓     | ✓        |      |             |
| DescribeTableIndexStats   | 1               |           | ✓     | ✓     | ✓        |      |             |
| RestoreTable              | 1               |           | ✓     |       | ✓        |      |             |
| RenameTable               | 1               |           | ✓     |       | ✓        |      |             |
| ListTableVersions         | 1               |           | ✓     |       | ✓        |      |             |
| ExplainTableQueryPlan     | 1               |           | ✓     |       |          | ✓    |             |
| AnalyzeTableQueryPlan     | 1               |           | ✓     |       |          | ✓    |             |
| AlterTableAddColumns      | 1               |           | ✓     |       |          | ✓    |             |
| AlterTableAlterColumns    | 1               |           | ✓     |       | ✓        |      |             |
| AlterTableDropColumns     | 1               |           | ✓     |       | ✓        |      |             |
| UpdateTableSchemaMetadata | 1               |           | ✓     |       | ✓        |      |             |
| GetTableStats             | 1               |           | ✓     |       | ✓        |      |             |
| ListTableTags             | 1               |           | ✓     |       | ✓        |      |             |
| GetTableTagVersion        | 1               |           | ✓     |       | ✓        |      |             |
| CreateTableTag            | 1               |           | ✓     |       | ✓        |      |             |
| DeleteTableTag            | 1               |           | ✓     |       | ✓        |      |             |
| UpdateTableTag            | 1               |           | ✓     |       | ✓        |      |             |
| DropTableIndex            | 1               |           | ✓     | ✓     | ✓        |      |             |
| DescribeTransaction       | 1               |           |       |       | ✓        |      | ✓           |
| AlterTransaction          | 1               |           |       |       | ✓        |      | ✓           |

## Recommended Basic Operations

To have a functional basic namespace implementation, 
the following metadata operations are recommended as a minimum:

**Namespace Metadata Operations:**

- CreateNamespace - Create a new namespace
- ListNamespaces - List available namespaces
- DescribeNamespace - Get namespace details
- DropNamespace - Remove a namespace

**Table Metadata Operations:**

- DeclareTable - Declare a table as exist
- ListTables - List tables in a namespace
- DescribeTable - Get table details
- DeregisterTable - Unregister a table while preserving its data

These operations provide the foundational metadata management capabilities needed for namespace and table administration
without requiring data or index operation support. With the namespace able to provide basic information about the table,
the Lance SDK can be used to fulfill the other operations.

### Why Not CreateTable and DropTable?

`CreateTable` and `DropTable` are intentionally excluded from the recommended basic operations because they involve
data operations that present challenges for catalog implementations:

**Data Operation Complexity:**
Both `CreateTable` and `DropTable` are considered data operations rather than pure metadata operations.
They can be long-running, especially when dealing with large datasets or remote storage systems.
This makes them difficult to implement reliably in catalog systems that are designed for fast metadata lookups.

**Atomicity Guarantees:**
Data operations require careful handling of atomicity. A failed `CreateTable` or `DropTable` operation
can leave the system in an inconsistent state with partially created or deleted data files.
Catalog implementations would need to implement complex cleanup and recovery mechanisms.

**CreateTable Challenges:**
`CreateTable` is particularly difficult for catalogs to fully implement because features like
CREATE TABLE AS SELECT (CTAS) require either complicated staging mechanisms or multi-table
multi-statement transaction support. Most catalog systems are not designed to handle such complex workflows.

Lance Namespace aims to enable as many catalogs as possible to adopt Lance format. By focusing on
`DeclareTable` and `DeregisterTable` instead of `CreateTable` and `DropTable`, namespace implementations only
need to handle metadata operations that are always fast and atomic across all catalog solutions.

**Recommended Approach:**
- Use **DeclareTable** to reserve a table name and location, then use the Lance SDK to write data
- Use **DeregisterTable** to unregister a table while preserving its data for potential re-registration
- Use the Lance SDK directly for data operations when full control over the data lifecycle is needed

## Operation Versioning

When a backwards incompatible change is introduced,
a new operation version needs to be created, with a naming convention of `<OperationId>V<version>`,
for example `ListNamespacesV2`, `DescribeTableV3`, etc.

## Request and Response Models

Each operation has a corresponding request and response model defined in the [Models](models/) section.
The naming convention is `<OperationId>Request` and `<OperationId>Response`.

For example:

- `CreateNamespaceRequest` / `CreateNamespaceResponse`
- `ListTablesRequest` / `ListTablesResponse`
- `DescribeTableRequest` / `DescribeTableResponse`

## Error Handling

All operations use a standardized error model with numeric error codes. 
Each operation documents the specific errors it may return.
See [Error Handling](errors.md) for the complete list of error codes and per-operation error documentation.
