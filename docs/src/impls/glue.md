# Lance Glue Namespace

**Lance Glue Namespace** is an implementation using AWS Glue Data Catalog.
For more details about AWS Glue, please read the [AWS Glue Data Catalog Documentation](https://docs.aws.amazon.com/glue/).

## Python Usage

To use the Glue namespace in Python, first install the required dependencies:

```bash
pip install lance-namespace[glue]
```

Then connect to the Glue namespace:

```python
from lance_namespace import connect

# Connect using default AWS credentials
namespace = connect("glue", {
    "glue.region": "us-east-1"
})

# Connect with specific credentials
namespace = connect("glue", {
    "glue.region": "us-east-1",
    "glue.access-key-id": "YOUR_ACCESS_KEY",
    "glue.secret-access-key": "YOUR_SECRET_KEY"
})

# Connect with custom catalog ID
namespace = connect("glue", {
    "glue.region": "us-east-1",
    "glue.catalog-id": "123456789012"
})
```

## Configuration

The Lance Glue namespace accepts the following configuration properties:

| Property                 | Required | Description                                                     | Default                   | Example                         |
|--------------------------|----------|-----------------------------------------------------------------|---------------------------|---------------------------------|
| `glue.catalog-id`        | No       | The Catalog ID of the Glue catalog (defaults to AWS account ID) |                           | `123456789012`                  |
| `glue.endpoint`          | No       | Custom Glue service endpoint for API compatible metastores      |                           | `https://glue.example.com`      |
| `glue.region`            | No       | AWS region for all Glue operations                              |                           | `us-west-2`                     |
| `glue.access-key-id`     | No       | AWS access key ID for static credentials                        |                           |                                 |
| `glue.secret-access-key` | No       | AWS secret access key for static credentials                    |                           |                                 |
| `glue.session-token`     | No       | AWS session token for temporary credentials                     |                           |                                 |
| `glue.profile-name`      | No       | AWS profile name for credentials                                |                           | `default`                       |
| `glue.max-retries`       | No       | Maximum number of retries for Glue API calls                    | 10                        | `5`                             |
| `glue.retry-mode`        | No       | Retry mode for Glue API calls                                   | `standard`                | `adaptive`, `legacy`            |
| `storage.*`              | No       | Additional storage configurations to access table               |                           | `storage.region=us-west-2`      |

### Authentication

The Glue namespace supports multiple authentication methods:

1. **Default AWS credential provider chain**: When no explicit credentials are provided, the client uses the default AWS credential provider chain
2. **Static credentials**: Set `glue.access-key-id` and `glue.secret-access-key` for basic AWS credentials
3. **Session credentials**: Additionally provide `glue.session-token` for temporary AWS credentials
4. **AWS Profile**: Set `glue.profile-name` to use a specific AWS CLI profile

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
    1. there is a key `table_type` set to `LANCE` (case insensitive)
    2. there is a key `metadata_location` set to the Lance table storage location
    3. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
    4. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use AWS Glue’s `VersionId` for conditional updates through the
[UpdateTable](https://docs.aws.amazon.com/glue/latest/webapi/API_UpdateTable.html) API. If the `VersionId` does not 
match the expected version, the update fails to prevent concurrent modification conflicts.
