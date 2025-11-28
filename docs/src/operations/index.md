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
[REST Namespace](../rest.md) spec. The REST namespace uses these same schemas directly as
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
See [REST Routes](../rest.md#rest-routes) for more details.

## Operation List

| Operation ID             | Current Version | Namespace | Table | Index | Metadata | Data | Transaction |
|--------------------------|-----------------|-----------|-------|-------|----------|------|-------------|
| CreateNamespace          | 1               | ✓         |       |       | ✓        |      |             |
| ListNamespaces           | 1               | ✓         |       |       | ✓        |      |             |
| DescribeNamespace        | 1               | ✓         |       |       | ✓        |      |             |
| DropNamespace            | 1               | ✓         |       |       | ✓        |      |             |
| NamespaceExists          | 1               | ✓         |       |       | ✓        |      |             |
| ListTables               | 1               | ✓         | ✓     |       | ✓        |      |             |
| RegisterTable            | 1               |           | ✓     |       | ✓        |      |             |
| DescribeTable            | 1               |           | ✓     |       | ✓        |      |             |
| TableExists              | 1               |           | ✓     |       | ✓        |      |             |
| DropTable                | 1               |           | ✓     |       | ✓        |      |             |
| DeregisterTable          | 1               |           | ✓     |       | ✓        |      |             |
| InsertIntoTable          | 1               |           | ✓     |       |          | ✓    |             |
| MergeInsertIntoTable     | 1               |           | ✓     |       |          | ✓    |             |
| UpdateTable              | 1               |           | ✓     |       |          | ✓    |             |
| DeleteFromTable          | 1               |           | ✓     |       |          | ✓    |             |
| QueryTable               | 1               |           | ✓     |       |          | ✓    |             |
| CountTableRows           | 1               |           | ✓     |       |          | ✓    |             |
| CreateTable              | 1               |           | ✓     |       |          | ✓    |             |
| CreateEmptyTable         | 1               |           | ✓     |       | ✓        |      |             |
| CreateTableIndex         | 1               |           | ✓     | ✓     | ✓        |      |             |
| ListTableIndices         | 1               |           | ✓     | ✓     | ✓        |      |             |
| DescribeTableIndexStats  | 1               |           | ✓     | ✓     | ✓        |      |             |
| RestoreTable             | 1               |           | ✓     |       | ✓        |      |             |
| ListTableVersions        | 1               |           | ✓     |       | ✓        |      |             |
| ExplainTableQueryPlan    | 1               |           | ✓     |       |          | ✓    |             |
| AnalyzeTableQueryPlan    | 1               |           | ✓     |       |          | ✓    |             |
| AlterTableAddColumns     | 1               |           | ✓     |       |          | ✓    |             |
| AlterTableAlterColumns   | 1               |           | ✓     |       | ✓        |      |             |
| AlterTableDropColumns    | 1               |           | ✓     |       | ✓        |      |             |
| GetTableStats            | 1               |           | ✓     |       | ✓        |      |             |
| ListTableTags            | 1               |           | ✓     |       | ✓        |      |             |
| GetTableTagVersion       | 1               |           | ✓     |       | ✓        |      |             |
| CreateTableTag           | 1               |           | ✓     |       | ✓        |      |             |
| DeleteTableTag           | 1               |           | ✓     |       | ✓        |      |             |
| UpdateTableTag           | 1               |           | ✓     |       | ✓        |      |             |
| DropTableIndex           | 1               |           | ✓     | ✓     | ✓        |      |             |
| DescribeTransaction      | 1               |           |       |       | ✓        |      | ✓           |
| AlterTransaction         | 1               |           |       |       | ✓        |      | ✓           |

## Recommended Basic Operations

To have a functional basic namespace implementation, the following metadata operations are recommended as a minimum:

**Namespace Metadata Operations:**

- CreateNamespace - Create a new namespace
- ListNamespaces - List available namespaces
- DescribeNamespace - Get namespace details
- DropNamespace - Remove a namespace

**Table Metadata Operations:**

- CreateEmptyTable - Create an empty table (metadata only)
- ListTables - List tables in a namespace
- DescribeTable - Get table details
- DropTable - Remove a table

These operations provide the foundational metadata management capabilities needed for namespace and table administration
without requiring data or index operation support. With the namespace able to provide basic information about the table,
the Lance SDK can be used to fulfill the other operations.

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

## Error Response Model

All error responses follow the JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807).
See [ErrorResponse](models/ErrorResponse.md) for details.
