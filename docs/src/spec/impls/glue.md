# Lance Glue Namespace

**Lance Glue Namespace** is an implementation using AWS Glue Data Catalog.
For more details about AWS Glue, please read the [AWS Glue Data Catalog Documentation](https://docs.aws.amazon.com/glue/).

## Namespace Mapping

An AWS Glue Data Catalog can be viewed as the root Lance namespace.
A database in Glue maps to the first-level Lance namespace,
to form a 2-level Lance namespace as a whole.

## Table Definition

When fully implemented, a Lance table should appear as a [Table](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html) 
object in AWS Glue with the following requirements:

1. the [`TableType`](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html#Glue-Type-Table-TableType) must be set to `EXTERNAL_TABLE` to indicate this is not a Glue managed table
2. the [`StorageDescriptor.Location`](https://docs.aws.amazon.com/glue/latest/webapi/API_StorageDescriptor.html#Glue-Type-StorageDescriptor-Location) must point to the root location of the Lance table
3. the [`Parameters`](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html#Glue-Type-Table-Parameters) must follow:
    1. there is a key `table_type` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use AWS Glue’s `VersionId` for conditional updates through the
[UpdateTable](https://docs.aws.amazon.com/glue/latest/webapi/API_UpdateTable.html) API. If the `VersionId` does not 
match the expected version, the update fails to prevent concurrent modification conflicts.
