# Namespace Operations

The Lance Namespace Specification defines a list of operations that can be performed against any Lance namespace:

| Operation ID            | Latest Version | Description                                                                                                        | Notes                                               |
|-------------------------|----------------|--------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------|
| ListOperations          | 1              | List the operations that are supported by this Lance namespace                                                     |                                                     |
| ListNamespaces          | 1              | List the names of child namespaces in the parent namespace, or root namespace if parent namespace is not specified |                                                     |
| NamespaceExists         | 1              | Check if a namespace exists                                                                                        | Returns no response                                 |
| DescribeNamespace       | 1              | Describe information of a namespace                                                                                |                                                     |
| CreateNamespace         | 1              | Create a new namespace under a parent namespace, or root namespace if parent namespace is not specified            |                                                     |
| AlterNamespace          | 1              | Alter information of a namespace                                                                                   |                                                     |
| DropNamespace           | 1              | Drop a namespace from its a parent namespace, or root namespace if parent namespace is not specified               |                                                     |
| ListTables              | 1              | List the names of tables in a namespace                                                                            |                                                     |
| TableExists             | 1              | Check if a table exists                                                                                            | Returns no response                                 |
| DescribeTable           | 2              | Describe information of a Lance table in the namespace                                                             |                                                     |
| CreateTable             | 1              | Create a new Lance table under a namespace                                                                         |                                                     |
| RegisterTable           | 1              | Register an existing table at a given storage location to a namespace                                              |                                                     |
| AlterTable              | 1              | Alter information of a Lance table                                                                                 |                                                     |
| DropTable               | 1              | Drop a table from its namespace                                                                                    |                                                     |
| DeregisterTable         | 1              | Deregister a table from its namespace, table content is kept unchanged in storage                                  |                                                     |
| InsertIntoTable         | 1              | Insert Arrow record batches into a table                                                                           | Takes Arrow IRC record batches stream as request    |
| MergeInsertIntoTable    | 1              | Merge or insert Arrow record batches into a table based on matching condition of specific column values            | Takes Arrow IRC record batches stream as request    |
| UpdateTable             | 1              | Update rows in a table based on conditions                                                                         |                                                     |
| DeleteFromTable         | 1              | Delete rows from a table based on conditions                                                                       |                                                     |
| QueryTable              | 1              | Query a table and return Arrow record batches                                                                      | Returns Arrow IRC record batches stream as response |
| CountTableRows          | 1              | Count the number of rows in a table                                                                                | Returns table row count as response                 |
| CreateTableIndex        | 1              | Create an index on a table                                                                                         |                                                     |
| CreateScalarTableIndex  | 1              | Create a scalar index on a table                                                                                   |                                                     |
| DescribeTableIndexStats | 1              | Describe statistics of an index                                                                                    |                                                     |
| DescribeTransaction     | 1              | Describe information of a transaction                                                                              |                                                     |
| AlterTransaction        | 1              | Alter information of a transaction                                                                                 |                                                     |

## Operation Versioning

When backwards incompatible change is introduced,
a new operation version needs to be created, with a naming convention of `<operationId>V<version>`,
for example `ListNamespacesV2`, `DescribeTableV3`, etc.

## Operation Request and Response Schema

In general, each operation has a request and response.
The request and response schema is defined using JSON schema in the 
`components/schemas` section of the [OpenAPI specification](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/lancedb/lance-namespace/refs/heads/main/docs/src/spec/rest.yaml).

!!! note
    For exceptions to this rule, see the Notes section of the operations