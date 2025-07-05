# Namespace Operations

The Lance Namespace Specification defines a list of operations that can be performed against any Lance namespace:

| Operation ID        | Description                                                                                                        |
|---------------------|--------------------------------------------------------------------------------------------------------------------|
| ListOperations      | List the operations that are supported by this Lance namespace                                                     |
| ListNamespaces      | List the names of child namespaces in the parent namespace, or root namespace if parent namespace is not specified |
| NamespaceExists     | Check if a namespace exists                                                                                        |
| DescribeNamespace   | Describe information of a namespace                                                                                |
| CreateNamespace     | Create a new namespace under a parent namespace, or root namespace if parent namespace is not specified            |
| AlterNamespace      | Alter information of a namespace                                                                                   |
| DropNamespace       | Drop a namespace from its a parent namespace, or root namespace if parent namespace is not specified               |
| ListTables          | List the names of tables in a namespace                                                                            |
| TableExists         | Check if a table exists                                                                                            |
| DescribeTable       | Describe information of a Lance table in the namespace                                                             |
| CreateTable         | Create a new Lance table under a namespace                                                                         |
| RegisterTable       | Register an existing table at a given storage location to a namespace                                              |
| AlterTable          | Alter information of a Lance table                                                                                 |
| DropTable           | Drop a table from its namespace                                                                                    |
| DeregisterTable     | Deregister a table from its namespace, table content is kept unchanged in storage                                  |
| DescribeTransaction | Describe information of a transaction                                                                              |
| AlterTransaction    | Alter information of a transaction                                                                                 |

## Operation Versioning

There is no versioning concept within an operation. When backwards incompatible change is introduced,
a new operation needs to be created, with a naming convention of `<operationId>V<version>`,
for example `ListNamespacesV2`, `DescribeTableV3`, etc.

## Operation Request and Response Schema

Each operation has a request and response.
The request and response schema is defined using JSON schema in the 
`components/schemas` section of the [OpenAPI specification](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/lancedb/lance-namespace/refs/heads/main/docs/src/spec/rest.yaml).
