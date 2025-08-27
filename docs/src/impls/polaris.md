# Lance Polaris Namespace

**Lance Polaris Namespace** is an implementation using Polaris Catalog's Generic Table API.
For more details about Polaris Catalog, please read the [Polaris Catalog Documentation](https://github.com/polaris-catalog/polaris).

## Configuration

The Lance Polaris namespace accepts the following configuration properties:

| Property              | Required | Description                                    | Default | Example                    |
|-----------------------|----------|------------------------------------------------|---------|----------------------------|
| `polaris.endpoint`    | Yes      | Polaris server endpoint URL                   |         | `http://localhost:8182`    |
| `polaris.catalog`     | Yes      | Catalog name in Polaris                       |         | `my_catalog`               |
| `polaris.auth.token`  | No       | Bearer token for authentication               |         | `your-auth-token`          |
| `polaris.connect.timeout` | No   | Connection timeout in milliseconds            | 10000   | `30000`                    |
| `polaris.read.timeout`    | No   | Read timeout in milliseconds                  | 30000   | `60000`                    |
| `polaris.max.retries`     | No   | Maximum number of retries for failed requests | 3       | `5`                        |

### Authentication

The Polaris namespace supports bearer token authentication:

1. **Bearer Token**: Set `polaris.auth.token` with a valid Polaris access token
2. **No Authentication**: For local or unsecured Polaris deployments

## Namespace Mapping

A Polaris Catalog provides a multi-level namespace hierarchy:

- A catalog in Polaris maps to the first level Lance namespace
- A namespace in Polaris maps to the second level Lance namespace
- Together they form a 3-level Lance namespace matching Polaris's structure

## Table Definition

A Lance table appears as a [Generic Table](https://github.com/polaris-catalog/polaris/blob/main/spec/polaris-catalog-apis/generic-tables-api.yaml)
object in Polaris with the following requirements:

1. the `format` must be set to `lance` to indicate this is a Lance table
2. the `base-location` must point to the root location of the Lance table
3. the `doc` field can be used to store an optional table description
4. the `properties` must follow:
   1. there is a key `table_type` set to `lance` (case insensitive)
   2. there is a key `managed_by` set to `lance-namespace`
   3. there is a key `version` set to the table format version
   4. there is a key `created_at` set to the ISO-8601 timestamp of table creation

## API Endpoints

The Polaris namespace uses the following API endpoints:

- **Namespace operations**: Standard Iceberg REST API endpoints (`/namespaces`)
- **Table operations**: Generic Table API endpoints (`/namespaces/{namespace}/generic-tables`)

## Limitations

1. The Generic Table API does not directly support Arrow schema storage - schema information must be managed at the Lance storage layer
2. Multi-table transactions are not supported in the current implementation
3. Data operations (insert, update, delete, query) are performed directly against the Lance storage layer