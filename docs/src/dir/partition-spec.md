# Partitioned Namespace Spec

Partitioning is a common data organization strategy that divides data into physically separated units.
Lance tables do not natively support partitioning, instead promoting clustering to achieve similar performance benefits.

However, there are use cases where true partitioning makes sense.
For example, an organization might want to store one table per business unit, where each table is fully isolated yet shares a common schema and data management lifecycle.
Additionally, there may be a need to query across all business units as a unified dataset.

A **Partitioned Namespace** is designed for these use cases.
It is a [Directory Namespace](catalog-spec.md) containing a collection of tables that share a common schema.
These tables are physically separated and independent, but logically related through partition fields definition.

This document defines the storage format for Partitioned Namespace.
Similar to Lance being a storage-only format, the storage-only [Directory Namespace](catalog-spec.md) spec serves as the foundation for this Partitioned Namespace format.

The following example illustrates the storage layout of a partitioned namespace:

```text
/root
    __manifest table (Lance table)
    ┌─────────────────────────────────────────────────────────────────────┐
    │ Table metadata (root namespace properties):                         │
    │     - schema = <shared Schema>                                      │
    │     - partition_spec_v1 = [event_date]                              │
    │     - partition_spec_v2 = [event_year, country]                     │
    └─────────────────────────────────────────────────────────────────────┘
                              │
                      Spec Version Level
                              │
              ┬───────────────────────────────────────────────────────────┐
              │                                                           │
             v1                                                          v2
          (Namespace)                                                 (Namespace)
              │                                                           │
              │── <id1>  ← event_date=2025-12-10                          │── <id3>  ← event_year=2025
              │     └── dataset  (Table)                                  │     │
              │                                                           │     └── <id4>  ← country=US
              │── <id2>  ← event_date=2025-12-11                          │           └── dataset  (Table)
              │     └── dataset  (Table)                                  │
              └── ...                                                     └── ...
```

## Metadata Definition

A directory namespace is identified as a partitioned namespace if the `__manifest` table's [metadata](catalog-spec.md#root-namespace-properties) contains at least one partition spec version key.

The following properties are stored in the `__manifest` table's metadata map:

- `partition_spec_v<N>` (String): A JSON string representing an array of partition column definition objects for version N. Each object describes a partition column. See [Partitioning](#partitioning) for details.
- `schema` (String): A json string describing the Schema of the entire partitioned namespace, based on the `JsonArrowSchema` schema in client spec. See [Namespace Schema](#partitioned-namespace-schema) for more details.

See [Appendix A: Metadata Example](#appendix-a-metadata-example) for a complete example.

## Physical Layout and Naming

A partitioned namespace supports multi-level partitioning with the following physical hierarchy:

- **Root Namespace**: The root namespace is implicit and represented by the `__manifest` table itself. Its properties (partition specs, schema) are stored in the `__manifest` table's metadata.
- **Spec Version Namespace**: The first-level child namespace, named `v1`, `v2`, etc. This identifies which partition spec version the data underneath was written with.
- **Partition Namespace**: Each subsequent child `Namespace` represents a partition level. Namespace names are randomly generated identifiers (see [Namespace Naming](#namespace-naming)).
- **Leaf Table**: At the end of the partition hierarchy, a `Table` object with the fixed name `dataset` contains the actual data. This is a standard, independently accessible Lance `Dataset` containing a subset of the partitioned namespace's data.

See [Appendix B: Physical Layout Example](#appendix-b-physical-layout-example) for a complete directory structure example.

### Namespace Naming

Partition namespaces use **random identifier naming** to avoid issues with special characters in partition values. 

Partition namespace names are randomly generated 16-character base36 strings (using characters `a-z0-9`). 
This provides ~83 bits of entropy, ensuring virtually zero collision probability for any practical number of partitions.
This approach ensures:

- No conflicts with reserved characters (e.g., `$`, `/`, `=`) that may appear in partition column values
- Consistent namespace names across different client implementations
- Fixed-length, predictable namespace identifiers

### Partition Value Discovery

Since namespace names are random identifiers, the actual partition values are stored in the `__manifest` table's partition columns (see [Manifest Table Schema](#manifest-table-schema)). 
When retrieving namespace or table properties via API, partition values are dynamically converted to `partition.<field_name> = <value>` entries in the properties map.

## Partitioned Namespace Schema

The namespace `schema` represents the logical union of all columns across all leaf tables. 
Rather than tracking multiple schema versions, the schema is the superset containing all columns that have ever existed. 

### Schema Consistency Rules

The schema of a partitioned namespace could differ from the schema of a leaf partition (table). Schema conflicts are resolved during reading and writing, with the following rules:

| Operation  | Namespace Schema | Table Schema            | Behavior                                                                                                                                                          |
|------------|------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Reads**  | Exists           | Does not exist          | Reading it will produce null values                                                                                                                               |
|            | Does not exist   | Exists                  | Table schema column will be ignored                                                                                                                               |
|            | Exists           | Exists (different type) | The type from the namespace schema takes precedence. A type cast will be attempted during the read, and an error must be thrown if the conversion is not possible |
| **Writes** | Exists           | Does not exist          | An error must be thrown                                                                                                                                           |
|            | Does not exist   | Exists                  | A NULL value will be written. An error must be thrown if the column does not support NULL values                                                                  |
|            | Exists           | Exists (different type) | Its value will be converted to the type in the table schema during the write. An error must be thrown if the conversion is not possible                           |

### Schema Evolution Rules

- **Add column**: Add to schema with a new unique field ID (`lance:field_id`). The field ID must be greater than any previously assigned ID.
- **Drop column**: Keep in schema but add `"lance:deprecated": "true"` to the field's metadata. Deprecated columns should be nullable.
- **Rename column**: Update the field `name`; the field ID (`lance:field_id`) remains unchanged.
- **Type promotion**: Update to the widest compatible type (e.g., `int32` → `int64`, `float32` → `float64`). Type narrowing is not permitted.

**Field ID Stability**: Field IDs (`lance:field_id`) are never reused. Once a field ID is assigned, it permanently identifies that logical column even if the column is later deprecated. This ensures partition specs using `source_id` references remain valid.

**Partition Field Validity**: If a source column is deprecated, existing partition fields referencing it via `source_id` remain valid for reading existing data. However, new partition spec versions should not reference deprecated columns. To remove a partition field, create a new partition spec version without that field.

See [Appendix E: Schema Evolution Example](#appendix-e-schema-evolution-example) for an example of a schema with a deprecated column.

## Partitioning

Partitioning defines how to derive partition values from a record. 
The partitioning information is stored in `partition_spec_v<N>` (e.g., `partition_spec_v1`), which is a JSON array of partition field objects. Each partition field contains:

* A **field id** uniquely identifying this partition field
* A **name** for the partition field (used as the column name in `__manifest`)
* A **source field id** referencing a field in the namespace schema
* A **partition expression** that transforms the source field value into a partition value
* A **result type** declaring the output type of the partition expression

| Field             | JSON representation | Example                    | Description                                                    |
|-------------------|---------------------|----------------------------|----------------------------------------------------------------|
| **`field_id`**    | `JSON int`          | `1000`                     | Unique identifier for this partition field                     |
| **`name`**        | `JSON string`       | `"event_year"`             | Name of the partition field (used as `__manifest` column name) |
| **`source_id`**   | `JSON int`          | `1`                        | Field ID of the source column in the schema                    |
| **`expression`**  | `JSON string`       | `"date_part('year', col)"` | DataFusion SQL expression using `col` as the column reference  |
| **`result_type`** | `JSON object`       | `{ "type": "int32" }`      | The output type of the expression (JsonArrowDataType format)   |

**Partition Field ID**: The `field_id` uniquely identifies each partition field across all spec versions. Field IDs should be assigned sequentially and must never be reused once assigned. This enables stable references even if partition field names change.

**Field ID Reuse**: When evolving partition specs, if a new partition field has the same `source_id`, `name`, and `expression` as an existing field, the same `field_id` must be reused. Otherwise, a new unique `field_id` must be assigned.

**Partition Field Name**: The `name` is used as the column name in the `__manifest` table. Different partition fields should have unique names. If multiple spec versions define partition fields with the same name, they must have the same `result_type`.

**Source Field ID Reference**: The `source_id` value references the field ID stored in the schema's field metadata under the key `lance:field_id`. Using field IDs instead of column names ensures that partition specs remain valid even when source columns are renamed.

The order of partition fields in the partitioning corresponds to the order of partition values in the physical layout.

### Partition Expression

The `expression` field contains a [DataFusion SQL expression](https://datafusion.apache.org/user-guide/sql/index.html) that transforms the source column value into a partition value. 
The placeholder `col` represents the source column.
The expression result is cast to a string for use as the partition value.

All partition expressions must be deterministic and return NULL when the source column value is NULL. 
These requirements ensure consistent behavior across implementations and proper handling of nullable columns. 
The common expressions listed in [Appendix D](#appendix-d-common-partition-expressions) satisfy these requirements.

The `result_type` field declares the output type of the partition expression using [JsonArrowDataType](https://lance.org/format/namespace/client/operations/models/JsonArrowDataType/) format. 
This enables type checking without expression evaluation and ensures consistency across implementations.

### Partition Pruning

Partition pruning is performed via the `__manifest` table, which contains partition column values for efficient filtering. 
This approach avoids the need to parse namespace names and enables direct predicate pushdown.

#### Manifest Table Schema

The `__manifest` table schema is extended to include partition columns for efficient partition pruning. Instead of parsing namespace names to filter partitions, query engines can directly push down predicates to the manifest table.

**Extended Schema**: For each partition field defined in any partition spec version, the `__manifest` table includes an additional nullable column. The column name is the partition field's `name`, and the type is the partition field's `result_type`. When a new partition spec version is defined, the `__manifest` table schema is updated accordingly to include any new partition columns.

| Column                     | Type       | Description                                                                             |
|----------------------------|------------|-----------------------------------------------------------------------------------------|
| `object_id`                | `string`   | Full namespace path with `$` separator (existing)                                       |
| `object_type`              | `string`   | `"namespace"` or `"table"` (existing)                                                   |
| `metadata`                 | `string`   | JSON-encoded metadata/properties (existing)                                             |
| `read_version`             | `uint64`   | Table version for reads (optional, see [Transaction Guarantee](#transaction-guarantee)) |
| `<partition_field_name_1>` | `<type_1>` | Partition value (nullable, inherited from parent namespaces)                            |
| `<partition_field_name_2>` | `<type_2>` | Partition value (nullable, inherited from parent namespaces)                            |
| ...                        | ...        | Additional partition field columns as needed                                            |

Partition values are inherited from parent namespaces - each row has all partition values from its ancestors. See [Appendix C: Manifest Table Example](#appendix-c-manifest-table-example) for a complete example.

#### Partition Pruning Workflow

1. Query engine analyzes the query predicate to identify filters on partition columns
2. For each partition expression, the engine evaluates the expression with the query values to compute the expected partition value(s)
3. Engine queries `__manifest` with filters on the partition columns
4. Engine retrieves the paths of matching `dataset` tables
5. Engine scans only the relevant leaf tables

### Partition Evolution

The partition spec supports **versioning** to allow partition strategies to evolve over time. 
Each partition spec version defines its own set of partition columns and expressions. 
Data written to the partitioned namespace records which spec version it was created under via the version namespace (`v1/`, `v2/`, etc.).

**Evolution Scenarios**:

- **Adding partition columns**: Create a new spec version with additional partition columns. New data is written under the new version while existing partitions remain accessible.
- **Changing partition expressions**: Create a new spec version with different expressions (e.g., changing from daily to yearly partitioning). Both versions coexist.
- **Removing partition columns**: Create a new spec version without certain columns. Legacy data under old versions remains queryable.

**Compatibility**:

When querying across multiple spec versions, the query engine must handle each version according to its partition spec. 
For example, if `v1` partitions by `event_date` and `v2` partitions by `year(event_date)`, a query filtering on `event_date = '2025-12-10'` will:

1. Match exact partitions in `v1`
2. Compute `year('2025-12-10') = 2025` and scan all matching year partitions in `v2`

This design ensures backward compatibility while enabling partition strategy evolution without data migration.

## ACID Guarantees

By default, operations within a single partition (leaf table) are ACID-compliant according to the Lance table specification. 
However, operations across multiple partitions have weaker guarantees:

- **Writes across partitions are not atomic and consistent**: A write that affects multiple partitions may partially succeed, leaving some partitions updated while others are not.
- **Reads across partitions are not isolated**: A read spanning multiple partitions may observe different versions of each partition, leading to inconsistent views.

### ACID Enforcement

To enable stronger transactional guarantees across partitions, the `__manifest` table can optionally include a `read_version` column. 
This column records the table version to use when reading each partition.

When `read_version` is present:

- **Atomic and consistent multi-partition updates**: Writers can atomically update the `read_version` of multiple partitions in a single `__manifest` commit, ensuring all-or-nothing visibility of changes across partitions.
- **Isolated read**: Concurrent readers see a consistent view based on the `read_version` values at the time they read the manifest, isolated from ongoing writes.

When `read_version` is NULL or the column is not present, readers should read the latest version of each partition table.

## Appendices

### Appendix A: Metadata Example

A complete example of partitioned namespace metadata properties with two spec versions:

```json
{
  "partition_spec_v1": [
    {
      "field_id": 1,
      "name": "event_date",
      "source_id": 1,
      "expression": "col",
      "result_type": { "type": "date32" }
    }
  ],
  "partition_spec_v2": [
    {
      "field_id": 2,
      "name": "event_year",
      "source_id": 1,
      "expression": "date_part('year', col)",
      "result_type": { "type": "int32" }
    },
    {
      "field_id": 3,
      "name": "country",
      "source_id": 2,
      "expression": "col",
      "result_type": { "type": "utf8" }
    }
  ],
  "schema": {
    "fields": [
      {
        "name": "id",
        "nullable": false,
        "type": { "type": "int64" },
        "metadata": { "lance:field_id": "0" }
      },
      {
        "name": "event_date",
        "nullable": true,
        "type": { "type": "date32" },
        "metadata": { "lance:field_id": "1" }
      },
      {
        "name": "country",
        "nullable": true,
        "type": { "type": "utf8" },
        "metadata": { "lance:field_id": "2" }
      }
    ]
  }
}
```

In this example:
- `v1` partitions by `event_date` (field_id 1) using the identity expression with `result_type: date32`
- `v2` partitions first by year of `event_date` (field_id 2) with `result_type: int32`, then by `country` (field_id 3) with `result_type: utf8`
- The `__manifest` table will have three partition columns: `event_date` (date32), `event_year` (int32), `country` (utf8)
- The schema follows [JsonArrowSchema](https://lance.org/format/namespace/client/operations/models/JsonArrowSchema/) format

### Appendix B: Physical Layout Example

A partitioned namespace with two spec versions (`v1` partitioned by `event_date`, `v2` partitioned by `event_year` and `country`) in [V2 Manifest](https://lance.org/format/namespace/dir/catalog-spec/#v2-manifest):

Namespaces exist only as entries in the `__manifest` table - they do not have physical directories. Only tables (the leaf `dataset` objects) have directories, following the V2 format `<hash>_<object_id>`.

```text
.
└── /my/dir1/
    ├── __manifest/                                                 # The manifest table
    │   ├── data/
    │   │   └── ...
    │   └── _versions/
    │       └── ...
    ├── b4a3c2d1_v1$k7m2n9p4q8r5s3t6$dataset/                       # Table: event_date=2025-12-10
    │   └── ...
    ├── 55667788_v1$w1x2y3z4a5b6c7d8$dataset/                       # Table: event_date=2025-12-11
    │   └── ...
    ├── aabbccdd_v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4$dataset/      # Table: event_year=2025, country=US
    │   └── ...
    └── ...
```

The namespaces (`v1`, `v1$k7m2n9p4q8r5s3t6`, etc.) are tracked in the `__manifest` table but have no corresponding directories.

### Appendix C: Manifest Table Example

The `__manifest` table for a partitioned namespace with partition fields `event_date` (v1), `event_year` and `country` (v2), showing entries from both spec versions:

| object_id                                     | object_type | metadata | event_date   | event_year | country |
|-----------------------------------------------|-------------|----------|--------------|------------|---------|
| v1                                            | namespace   | {}       | NULL         | NULL       | NULL    |
| v1$k7m2n9p4q8r5s3t6                           | namespace   | {}       | 2025-12-10   | NULL       | NULL    |
| v1$k7m2n9p4q8r5s3t6$dataset                   | table       | {}       | 2025-12-10   | NULL       | NULL    |
| v2                                            | namespace   | {}       | NULL         | NULL       | NULL    |
| v2$e9f0g1h2i3j4k5l6                           | namespace   | {}       | NULL         | 2025       | NULL    |
| v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4          | namespace   | {}       | NULL         | 2025       | US      |
| v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4$dataset  | table       | {}       | NULL         | 2025       | US      |

Note: The root namespace properties (`partition_spec_v1`, `partition_spec_v2`, `schema`) are stored in the `__manifest` table's metadata, not as a row. The `object_id` uses `$` as the namespace path separator. Partition values are stored in partition columns and inherited from parent namespaces. When retrieving properties via API, partition values are converted to `partition.<field_name> = <value>` entries.

**Pruning Example**: For a query `WHERE event_year = 2025 AND country = 'US'`:
- Tables under `v1` do not have `event_year` partition field, so the engine must scan all `v1` partitions
- Tables under `v2` can be filtered directly using the `event_year` and `country` columns

### Appendix D: Common Partition Expressions

This appendix provides commonly used partition expressions.
All expressions use `col` as the placeholder for the source column.

| Name                    | Expression                | Result Type | Description                                    |
|-------------------------|---------------------------|-------------|------------------------------------------------|
| `identity`              | `col`                     | same as col | Source value, unmodified                       |
| `year`                  | `date_part('year', col)`  | `int32`     | Extract year from date/timestamp               |
| `month`                 | `date_part('month', col)` | `int32`     | Extract month (1-12) from date/timestamp       |
| `day`                   | `date_part('day', col)`   | `int32`     | Extract day of month from date/timestamp       |
| `hour`                  | `date_part('hour', col)`  | `int32`     | Extract hour (0-23) from timestamp             |
| `bucket[N]`             | `abs(hash(col)) % N`      | `int64`     | Hash into N buckets (see notes below)          |
| `truncate[W]` (string)  | `left(col, W)`            | `utf8`      | First W characters of string                   |
| `truncate[W]` (numeric) | `col - (col % W)`         | same as col | Truncate numeric to width W                    |

- **Hash function**: The `hash()` UDF uses xxhash64 with seed `0` for deterministic hashing across implementations. It returns NULL for NULL input. Lance provides this as a built-in UDF.

### Appendix E: Schema Evolution Example

A schema with a deprecated column (`legacy_field`):

```json
{
  "schema": {
    "fields": [
      {
        "name": "id",
        "nullable": false,
        "type": { "type": "int64" },
        "metadata": { "lance:field_id": "0" }
      },
      {
        "name": "event_date",
        "nullable": true,
        "type": { "type": "date32" },
        "metadata": { "lance:field_id": "1" }
      },
      {
        "name": "legacy_field",
        "nullable": true,
        "type": { "type": "utf8" },
        "metadata": { "lance:field_id": "2", "lance:deprecated": "true" }
      }
    ]
  }
}
```

In this example:
- `legacy_field` (field_id 2) has been deprecated but remains in the schema
- Existing partition specs referencing `source_id: 2` continue to work for reading old data
- New partition specs should not reference deprecated columns
