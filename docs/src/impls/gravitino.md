# Lance Gravitino Namespace

**Lance Gravitino Namespace** is an implementation using Apache Gravitino Catalog.
For more details about Apache Gravitino, please read the [Gravitino Documentation](https://gravitino.apache.org/).

## Configuration

The Lance Gravitino namespace accepts the following configuration properties:

| Property                | Required | Description                                                    | Default                   | Example                           |
|-------------------------|----------|----------------------------------------------------------------|---------------------------|-----------------------------------|
| `gravitino.endpoint`    | No       | Gravitino server endpoint URL                                 | `http://localhost:8090`   | `http://gravitino.example.com`   |
| `gravitino.metalake`    | Yes      | Gravitino metalake name                                       |                           | `my_metalake`                     |
| `gravitino.catalog`     | Yes      | Gravitino catalog name within the metalake                    |                           | `lance_catalog`                   |
| `gravitino.auth.token`  | No       | Bearer token for authentication                               |                           | `Bearer eyJhbGciOiJIUzI1...`     |
| `gravitino.connect.timeout` | No   | HTTP connection timeout in seconds                            | 10                        | `30`                              |
| `gravitino.read.timeout`    | No   | HTTP read timeout in seconds                                  | 60                        | `120`                             |
| `gravitino.max.retries`     | No   | Maximum number of retries for failed requests                 | 3                         | `5`                               |

### Authentication

The Gravitino namespace supports the following authentication methods:

1. **Bearer Token**: Set `gravitino.auth.token` with a valid Gravitino access token
2. **No Authentication**: For local or unsecured Gravitino deployments

## Namespace Mapping

Apache Gravitino provides a 4-level namespace hierarchy:

- **Metalake**: Top-level organizational unit (read-only in Lance namespace)
- **Catalog**: Data source catalog within a metalake (read-only in Lance namespace)
- **Schema**: Database or schema within a catalog (maps to Lance namespace)
- **Table**: Individual table within a schema

The Lance namespace operates within a pre-configured metalake and catalog:
- A schema in Gravitino maps to a Lance namespace
- Tables are created within schemas

## Table Definition

A Lance table appears as a [Table](https://github.com/apache/gravitino/blob/main/docs/open-api/tables.yaml) 
object in Gravitino with the following requirements:

1. The table `name` identifies the table within its schema
2. The `columns` must be provided with the table schema converted from Lance's Arrow schema to Gravitino's column format
3. The `properties` must follow:
    1. There is a key `format` set to `lance` to identify this as a Lance table
    2. There is a key `provider` set to `lance` to specify the data provider
    3. There is a key `location` pointing to the storage location of the Lance table data
    4. Additional properties can be set for table metadata

### Column Type Mapping

The following type mappings are used between Arrow and Gravitino:

| Arrow Type              | Gravitino Type     | Notes                                    |
|-------------------------|-------------------|------------------------------------------|
| Bool                    | `boolean`         |                                          |
| Int8                    | `byte`            | TINYINT                                 |
| Int16                   | `short`           | SMALLINT                                |
| Int32                   | `integer`         | INT                                     |
| Int64                   | `long`            | BIGINT                                  |
| Float32                 | `float`           |                                          |
| Float64                 | `double`          |                                          |
| Utf8                    | `string`          | VARCHAR                                 |
| Binary                  | `binary`          | VARBINARY                               |
| FixedSizeBinary(n)      | `fixed(n)`        | Fixed-size binary                      |
| Date32/Date64           | `date`            |                                          |
| Timestamp               | `timestamp`       | With microsecond precision              |
| Decimal(p,s)            | `decimal(p,s)`    | Precision and scale preserved           |
| List                    | `array<T>`        | Element type needs recursive mapping   |
| Struct                  | `struct<...>`     | Field definitions need mapping          |
| Map                     | `map<K,V>`        | Key and value types need mapping       |

## Schema Operations

Schemas (databases) in Gravitino can be:
- **Created**: With optional properties and comments
- **Listed**: Within the configured catalog
- **Described**: To retrieve properties and metadata
- **Dropped**: With cascade option for contained tables
- **Checked for existence**: Without throwing exceptions

## Table Operations

Lance tables in Gravitino support:
- **Creation**: With Arrow schema, storage location, and properties
- **Listing**: Within a schema, filtered by Lance format
- **Description**: Retrieving schema and properties
- **Deletion**: With optional purge to remove data
- **Existence checks**: Without throwing exceptions

## Storage Management

Unlike Unity Catalog, Gravitino separates metadata management from storage:
- Table `location` property specifies where Lance data is stored
- Storage can be on various systems (S3, HDFS, local filesystem, etc.)
- The namespace implementation manages metadata only
- Actual Lance dataset operations require separate storage access

## API Compatibility

The implementation uses Gravitino REST API v1:
- Base path: `/api/v1`
- Schema endpoints: `/metalakes/{metalake}/catalogs/{catalog}/schemas`
- Table endpoints: `/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables`

All responses are wrapped in a standard format:
```json
{
  "code": 0,
  "data": { ... }
}
```

## Limitations

1. **Read-only metalake and catalog**: These must be pre-configured and cannot be modified through the Lance namespace
2. **Simplified type mapping**: Complex types (arrays, structs, maps) have basic support that may need enhancement
3. **No direct dataset operations**: `openTable` and `createTable` with WriteParams are not implemented as they require Lance Java bindings
4. **No cross-catalog operations**: All operations are confined to the configured catalog

## Future Enhancements

- Support for table partitioning and distribution specifications
- Enhanced complex type mapping with full recursion
- Integration with Gravitino's access control and audit features
- Support for table evolution and schema updates
- Direct Lance dataset operations when Java bindings are available