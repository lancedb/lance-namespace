# Lance Gravitino Namespace

**Lance Gravitino Namespace** is an implementation using Apache Gravitino Catalog.
For more details about Apache Gravitino, please read the [Gravitino Documentation](https://gravitino.apache.org/).

## Configuration

The Lance Gravitino namespace accepts the following configuration properties:

| Property            | Required | Description                                                    | Default                   | Example                           |
|---------------------|----------|----------------------------------------------------------------|---------------------------|-----------------------------------|
| `endpoint`          | No       | Gravitino server endpoint URL                                 | `http://localhost:8090`   | `http://gravitino.example.com`   |
| `metalake`          | Yes      | Gravitino metalake name                                       |                           | `my_metalake`                     |
| `catalog`           | Yes      | Gravitino catalog name within the metalake                    |                           | `lance_catalog`                   |
| `auth_token`        | No       | Bearer token for authentication                               |                           | `eyJhbGciOiJIUzI1...`            |
| `connect_timeout`   | No       | HTTP connection timeout in seconds                            | 10                        | `30`                              |
| `read_timeout`      | No       | HTTP read timeout in seconds                                  | 60                        | `120`                             |
| `max_retries`       | No       | Maximum number of retries for failed requests                 | 3                         | `5`                               |

### Authentication

The Gravitino namespace supports the following authentication methods:

1. **Bearer Token**: Set `auth_token` with a valid Gravitino access token
2. **No Authentication**: For local or unsecured Gravitino deployments

## Namespace Mapping

Apache Gravitino provides a 4-level namespace hierarchy. The Lance namespace operates within a pre-configured metalake and catalog:

- A metalake in Gravitino is configured at initialization (read-only)
- A catalog within the metalake is configured at initialization (read-only)  
- A schema (database) in Gravitino maps to the first level Lance namespace
- Together they form the Lance namespace structure

## Table Definition

A Lance table appears as a [Table](https://github.com/apache/gravitino/blob/main/docs/open-api/tables.yaml) 
object in Gravitino with the following requirements:

1. the table `name` identifies the table within its schema
2. the `columns` must be provided with the table schema converted from Lance's Arrow schema to Gravitino's column format
3. the `properties` must follow:
    1. there is a key `format` set to `lance` to identify this as a Lance table
    2. there is a key `provider` set to `lance` to specify the data provider
    3. there is a key `location` pointing to the storage location of the Lance table data

## Requirement for Implementation Managed Table

Updates to implementation-managed Lance tables must use Gravitino's table versioning mechanism
for conditional updates through the UpdateTable API. The table properties must be updated atomically
to prevent concurrent modification conflicts.