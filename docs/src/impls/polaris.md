# Lance Polaris Namespace

**Lance Polaris Namespace** is an implementation using Polaris Catalog's Generic Table API.
The Polaris namespace uses the following API endpoints:
- **Namespace operations**: Standard Iceberg REST API endpoints (`/namespaces`)
- **Table operations**: Generic Table API endpoints (`/namespaces/{namespace}/generic-tables`)

For more details about Polaris Catalog, please read the [Polaris Catalog Documentation](https://github.com/polaris-catalog/polaris).

## Configuration

The Lance Polaris namespace accepts the following configuration properties:

| Property          | Required | Description                                    | Default | Example                    |
|-------------------|----------|------------------------------------------------|---------|----------------------------|
| `endpoint`        | Yes      | Polaris server endpoint URL                   |         | `http://localhost:8182`    |
| `auth_token`      | No       | Bearer token for authentication               |         | `your-auth-token`          |
| `connect_timeout` | No       | Connection timeout in milliseconds            | 10000   | `30000`                    |
| `read_timeout`    | No       | Read timeout in milliseconds                  | 30000   | `60000`                    |
| `max_retries`     | No       | Maximum number of retries for failed requests | 3       | `5`                        |

### Authentication

The Polaris namespace supports bearer token authentication:

1. **Bearer Token**: Set `auth_token` with a valid Polaris access token
2. **No Authentication**: For local or unsecured Polaris deployments

## Namespace Mapping

Polaris provides a flexible namespace hierarchy:

- A catalog in Polaris maps to the first level Lance namespace
- Nested namespaces in Polaris map to subsequent Lance namespace levels
- Polaris supports arbitrary nesting depth, allowing flexible namespace organization

## Table Definition

A Lance table appears as a [Generic Table](https://github.com/polaris-catalog/polaris/blob/main/spec/polaris-catalog-apis/generic-tables-api.yaml)
object in Polaris with the following requirements:

1. the `format` must be set to `lance` to indicate this is a Lance table
2. the `base-location` must point to the root location of the Lance table
3. the `doc` field can be used to store an optional table description
4. the `properties` must follow:
   1. there is a key `table_type` set to `lance` (case insensitive)
   2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
   3. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use Polaris's table versioning mechanism
for conditional updates through the UpdateTable API. The `version` property must be updated atomically
to prevent concurrent modification conflicts.