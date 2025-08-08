# Lance Glue Namespace

**Lance Glue Namespace** is an implementation using AWS Glue Data Catalog.
For more details about AWS Glue, please read the [AWS Glue Data Catalog Documentation](https://docs.aws.amazon.com/glue/).

## Configuration

The Lance Glue namespace accepts the following configuration properties:

| Property            | Required | Description                                                     | Default                   | Example                         |
|---------------------|----------|-----------------------------------------------------------------|---------------------------|---------------------------------|
| `catalog_id`        | No       | The Catalog ID of the Glue catalog (defaults to AWS account ID) |                           | `123456789012`                  |
| `endpoint`          | No       | Custom Glue service endpoint for API compatible metastores      |                           | `https://glue.example.com`      |
| `region`            | No       | AWS region for all Glue operations                              |                           | `us-west-2`                     |
| `access_key_id`     | No       | AWS access key ID for static credentials                        |                           |                                 |
| `secret_access_key` | No       | AWS secret access key for static credentials                    |                           |                                 |
| `session_token`     | No       | AWS session token for temporary credentials                     |                           |                                 |
| `root`              | No       | Storage root location of the lakehouse on Glue catalog          | Current working directory | `/my/dir`, `s3://bucket/prefix` |
| `storage.*`         | No       | Additional storage configurations to access table               |                           | `storage.region=us-west-2`      |

### Authentication

The Glue namespace supports multiple authentication methods:

1. **Default AWS credential provider chain**: When no explicit credentials are provided, the client uses the default AWS credential provider chain
2. **Static credentials**: Set `access_key_id` and `secret_access_key` for basic AWS credentials
3. **Session credentials**: Additionally provide `session_token` for temporary AWS credentials

## Namespace Mapping

An AWS Glue Data Catalog can be viewed as the root Lance namespace.
A database in Glue maps to the first level Lance namespace,
to form a 2-level Lance namespace as a whole.

## Table Definition

When fully implemented, a Lance table should appear as a [Table](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html) 
object in AWS Glue with the following requirements:

1. the [`TableType`](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html#Glue-Type-Table-TableType) must be set to `EXTERNAL_TABLE` to indicate this is not a Glue managed table
2. the [`StorageDescriptor.Location`](https://docs.aws.amazon.com/glue/latest/webapi/API_StorageDescriptor.html#Glue-Type-StorageDescriptor-Location) must point to the root location of the Lance table
3. the [`Parameters`](https://docs.aws.amazon.com/glue/latest/webapi/API_Table.html#Glue-Type-Table-Parameters) must follow:
    1. there is a key `table_type` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
    3. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use AWS Glue’s `VersionId` for conditional updates through the
[UpdateTable](https://docs.aws.amazon.com/glue/latest/webapi/API_UpdateTable.html) API. If the `VersionId` does not 
match the expected version, the update fails to prevent concurrent modification conflicts.
