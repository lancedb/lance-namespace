# Lance Unity Namespace

**Lance Unity Namespace** is an implementation using Unity Catalog.
For more details about Unity Catalog, please read the [Unity Catalog Documentation](https://www.unitycatalog.io/).

## Configuration

The Lance Unity namespace accepts the following configuration properties:

| Property            | Required | Description                                                    | Default                   | Example                           |
|---------------------|----------|----------------------------------------------------------------|---------------------------|-----------------------------------|
| `endpoint`          | Yes      | Unity Catalog REST API endpoint                               |                           | `http://localhost:8080`           |
| `api_path`          | No       | API path prefix                                               | `/api/2.1/unity-catalog`  | `/api/2.1/unity-catalog`          |
| `auth_token`        | No       | Bearer token for authentication                               |                           | `dapi123456789abcdef`             |
| `catalog`           | Yes      | Unity Catalog name to use                                     |                           | `main`                            |
| `connect_timeout`   | No       | HTTP connection timeout in seconds                            | 10                        | `30`                              |
| `read_timeout`      | No       | HTTP read timeout in seconds                                  | 60                        | `120`                             |
| `max_retries`       | No       | Maximum number of retries for failed requests                 | 3                         | `5`                               |
| `root`              | No       | Storage root location of the lakehouse on Unity catalog       | Current working directory | `/my/dir`, `s3://bucket/prefix`   |
| `storage.*`         | No       | Additional storage configurations to access table             |                           | `storage.region=us-west-2`        |

### Authentication

The Unity namespace supports the following authentication methods:

1. **Bearer Token**: Set `auth_token` with a valid Unity Catalog access token
2. **No Authentication**: For local or unsecured Unity Catalog deployments

## Namespace Mapping

A Unity Catalog server provides a 3-level namespace hierarchy.

- A catalog in Unity Catalog maps to the first level Lance namespace
- A schema (database) in Unity Catalog maps to the second level Lance namespace
- Together they form a 3-level Lance namespace matching Unity's structure

## Table Definition

A Lance table appears as a [Table](https://github.com/unitycatalog/unitycatalog/blob/main/api/all.yaml) 
object in Unity Catalog with the following requirements:

1. the `table_type` must be set to `EXTERNAL` to indicate this is not a Unity managed table
2. the `data_source_format` should be `null` as Lance has its own format
3. the `storage_location` must point to the root location of the Lance table
4. the `columns` should be `null` as Lance manages its own schema
5. the `properties` must follow:
    1. there is a key `table_type` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
    3. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use Unity Catalog's table versioning mechanism
for conditional updates through the UpdateTable API. The `version` property must be updated atomically
to prevent concurrent modification conflicts.

## HTTP Client

The Unity namespace implementation uses a shared RestClient from lance-namespace-core for all HTTP operations.
This client provides:
- Connection pooling
- Automatic retries with exponential backoff
- Request/response logging
- Error handling and mapping to appropriate Lance exceptions