# Error Handling

All Lance Namespace operations use a standardized error model for
consistent error handling across different implementations and languages.

## Error Codes

Error codes are globally unique integers that identify the specific error type.
These codes are consistent across all Lance Namespace implementations (Python, Java, Rust, REST).

| Code | Name                       | Description                                       |
|------|----------------------------|---------------------------------------------------|
| 0    | Unsupported                | Operation not supported by this backend           |
| 1    | NamespaceNotFound          | The specified namespace does not exist            |
| 2    | NamespaceAlreadyExists     | A namespace with this name already exists         |
| 3    | NamespaceNotEmpty          | Namespace contains tables or child namespaces     |
| 4    | TableNotFound              | The specified table does not exist                |
| 5    | TableAlreadyExists         | A table with this name already exists             |
| 6    | TableIndexNotFound         | The specified table index does not exist          |
| 7    | TableIndexAlreadyExists    | A table index with this name already exists       |
| 8    | TableTagNotFound           | The specified table tag does not exist            |
| 9    | TableTagAlreadyExists      | A table tag with this name already exists         |
| 10   | TransactionNotFound        | The specified transaction does not exist          |
| 11   | TableVersionNotFound       | The specified table version does not exist        |
| 12   | TableColumnNotFound        | The specified table column does not exist         |
| 13   | InvalidInput               | Malformed request or invalid parameters           |
| 14   | ConcurrentModification     | Optimistic concurrency conflict                   |
| 15   | PermissionDenied           | User lacks permission for this operation          |
| 16   | Unauthenticated            | Authentication credentials are missing or invalid |
| 17   | ServiceUnavailable         | Service is temporarily unavailable                |
| 18   | Internal                   | Unexpected server/implementation error            |
| 19   | InvalidTableState          | Table is in an invalid state for the operation    |
| 20   | TableSchemaValidationError | Table schema validation failed                    |

## Per-Operation Errors

Each operation can return a specific set of errors.
The following sections document which errors are expected for each operation category.

### Common Errors

All operations may return the following errors:

- **0 (Unsupported)**: The operation is not supported by this backend
- **13 (InvalidInput)**: The request contains invalid parameters
- **15 (PermissionDenied)**: The user lacks permission for this operation
- **16 (Unauthenticated)**: Authentication credentials are missing or invalid
- **17 (ServiceUnavailable)**: The service is temporarily unavailable
- **18 (Internal)**: An unexpected internal error occurred

### Namespace Metadata Operations

| Operation         | Additional Errors                            |
|-------------------|----------------------------------------------|
| CreateNamespace   | 2 (NamespaceAlreadyExists)                   |
| ListNamespaces    | 1 (NamespaceNotFound)                        |
| DescribeNamespace | 1 (NamespaceNotFound)                        |
| DropNamespace     | 1 (NamespaceNotFound), 3 (NamespaceNotEmpty) |
| NamespaceExists   | 1 (NamespaceNotFound)                        |
| ListTables        | 1 (NamespaceNotFound)                        |

### Table Metadata Operations

| Operation                 | Additional Errors                                                                                                                |
|---------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| ListAllTables             | -                                                                                                                                |
| RegisterTable             | 1 (NamespaceNotFound), 5 (TableAlreadyExists), 14 (ConcurrentModification)                                                       |
| DescribeTable             | 1 (NamespaceNotFound), 4 (TableNotFound), 11 (TableVersionNotFound)                                                              |
| TableExists               | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                         |
| DropTable                 | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                         |
| DeregisterTable           | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                         |
| CreateEmptyTable          | 1 (NamespaceNotFound), 5 (TableAlreadyExists), 14 (ConcurrentModification)                                                       |
| RestoreTable              | 1 (NamespaceNotFound), 4 (TableNotFound), 11 (TableVersionNotFound), 14 (ConcurrentModification)                                 |
| RenameTable               | 1 (NamespaceNotFound), 4 (TableNotFound), 5 (TableAlreadyExists), 14 (ConcurrentModification)                                    |
| ListTableVersions         | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                         |
| GetTableStats             | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                         |
| AlterTableAlterColumns    | 1 (NamespaceNotFound), 4 (TableNotFound), 12 (TableColumnNotFound), 14 (ConcurrentModification), 20 (TableSchemaValidationError) |
| AlterTableDropColumns     | 1 (NamespaceNotFound), 4 (TableNotFound), 12 (TableColumnNotFound), 14 (ConcurrentModification)                                  |
| UpdateTableSchemaMetadata | 1 (NamespaceNotFound), 4 (TableNotFound), 14 (ConcurrentModification)                                                            |

### Table Data Operations

| Operation             | Additional Errors                                                                                                              |
|-----------------------|--------------------------------------------------------------------------------------------------------------------------------|
| InsertIntoTable       | 1 (NamespaceNotFound), 4 (TableNotFound), 14 (ConcurrentModification), 19 (InvalidTableState), 20 (TableSchemaValidationError) |
| MergeInsertIntoTable  | 1 (NamespaceNotFound), 4 (TableNotFound), 12 (TableColumnNotFound), 14 (ConcurrentModification), 19 (InvalidTableState)        |
| UpdateTable           | 1 (NamespaceNotFound), 4 (TableNotFound), 12 (TableColumnNotFound), 14 (ConcurrentModification), 19 (InvalidTableState)        |
| DeleteFromTable       | 1 (NamespaceNotFound), 4 (TableNotFound), 14 (ConcurrentModification), 19 (InvalidTableState)                                  |
| QueryTable            | 1 (NamespaceNotFound), 4 (TableNotFound), 11 (TableVersionNotFound), 12 (TableColumnNotFound)                                  |
| CountTableRows        | 1 (NamespaceNotFound), 4 (TableNotFound), 11 (TableVersionNotFound)                                                            |
| CreateTable           | 1 (NamespaceNotFound), 5 (TableAlreadyExists), 14 (ConcurrentModification), 20 (TableSchemaValidationError)                    |
| ExplainTableQueryPlan | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                       |
| AnalyzeTableQueryPlan | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                       |
| AlterTableAddColumns  | 1 (NamespaceNotFound), 4 (TableNotFound), 14 (ConcurrentModification), 20 (TableSchemaValidationError)                         |

### Index Metadata Operations

| Operation               | Additional Errors                                                                                                            |
|-------------------------|------------------------------------------------------------------------------------------------------------------------------|
| CreateTableIndex        | 1 (NamespaceNotFound), 4 (TableNotFound), 7 (TableIndexAlreadyExists), 12 (TableColumnNotFound), 14 (ConcurrentModification) |
| CreateTableScalarIndex  | 1 (NamespaceNotFound), 4 (TableNotFound), 7 (TableIndexAlreadyExists), 12 (TableColumnNotFound), 14 (ConcurrentModification) |
| ListTableIndices        | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                     |
| DescribeTableIndexStats | 1 (NamespaceNotFound), 4 (TableNotFound), 6 (TableIndexNotFound)                                                             |
| DropTableIndex          | 1 (NamespaceNotFound), 4 (TableNotFound), 6 (TableIndexNotFound)                                                             |

### Tag Metadata Operations

| Operation          | Additional Errors                                                                                                           |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------|
| ListTableTags      | 1 (NamespaceNotFound), 4 (TableNotFound)                                                                                    |
| GetTableTagVersion | 1 (NamespaceNotFound), 4 (TableNotFound), 8 (TableTagNotFound)                                                              |
| CreateTableTag     | 1 (NamespaceNotFound), 4 (TableNotFound), 9 (TableTagAlreadyExists), 11 (TableVersionNotFound), 14 (ConcurrentModification) |
| DeleteTableTag     | 1 (NamespaceNotFound), 4 (TableNotFound), 8 (TableTagNotFound)                                                              |
| UpdateTableTag     | 1 (NamespaceNotFound), 4 (TableNotFound), 8 (TableTagNotFound), 11 (TableVersionNotFound), 14 (ConcurrentModification)      |

### Transaction Metadata Operations

| Operation           | Additional Errors                                     |
|---------------------|-------------------------------------------------------|
| DescribeTransaction | 10 (TransactionNotFound)                              |
| AlterTransaction    | 10 (TransactionNotFound), 14 (ConcurrentModification) |
