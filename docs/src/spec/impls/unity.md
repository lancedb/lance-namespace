# Lance Unity Catalog Namespace

**Lance Unity Catalog Namespace** is an implementation using Unity Catalog, an open-source catalog for lakehouse environments.
For more details about Unity Catalog, please read the [Unity Catalog Documentation](https://docs.unitycatalog.io/).

## Configuration

The Lance Unity Catalog namespace accepts the following configuration properties:

| Property           | Required | Description                                                     | Default                   | Example                                  |
|--------------------|----------|----------------------------------------------------------------|---------------------------|------------------------------------------|
| `endpoint`         | Yes      | Unity Catalog server endpoint                                  |                           | `http://localhost:8080`                 |
| `token`            | No       | Bearer token for authentication                                |                           | `your-auth-token`                       |
| `root`             | No       | Storage root location of the lakehouse on Unity catalog        | Current working directory | `/my/dir`, `s3://bucket/prefix`         |
| `storage.*`        | No       | Additional storage configurations to access table              |                           | `storage.region=us-west-2`               |

### Authentication

The Unity Catalog namespace supports the following authentication methods:

1. **No authentication**: When no token is provided, the client connects without authentication (suitable for local development)
2. **Bearer token**: Set `token` property for bearer token authentication

## Namespace Mapping

A Unity Catalog server can be viewed as the root Lance namespace.
A catalog in Unity Catalog maps to the first level Lance namespace,
and a schema in Unity Catalog maps to the second level Lance namespace
to form a 3-level Lance namespace as a whole.

## Table Definition

A Lance table should appear as a [TableInfo](https://github.com/unitycatalog/unitycatalog/blob/main/api/Models/TableInfo.md) 
object in Unity Catalog with the following requirements:

1. the [`table_type`](https://github.com/unitycatalog/unitycatalog/blob/main/api/Models/TableType.md) must be set to `EXTERNAL` to indicate this is not a Unity managed table
2. the [`data_source_format`](https://github.com/unitycatalog/unitycatalog/blob/main/api/Models/DataSourceFormat.md) should be set to `null` as Lance format is not a standard Unity format
3. the [`storage_location`](https://github.com/unitycatalog/unitycatalog/blob/main/api/Models/TableInfo.md) must point to the root location of the Lance table
4. the [`properties`](https://github.com/unitycatalog/unitycatalog/blob/main/api/Models/TableInfo.md) must follow:
    1. there is a key `table_format` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
    3. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use Unity Catalog's table versioning mechanism
through the [UpdateTable](https://docs.unitycatalog.io/api/tables#update-table) API. The implementation
must track and validate table versions to prevent concurrent modification conflicts.