# Lance Partitioning Spec

Partitioning is a common data organization strategy that divides data into physically separated units.
Lance tables do not natively support partitioning, instead promoting clustering to achieve similar performance benefits.

However, there are use cases where true partitioning makes sense.
For example, an organization might want to store one table per business unit, 
where each table is fully isolated yet shares a common schema and data management lifecycle.
Most of the time, queries like vector search are only against a specific partition, but sometimes 
it would be convenient to query across all business units as a unified dataset.

A **Partitioned Namespace** is designed for these use cases.
It is a [Directory Catalog](catalog/dir/index.md) containing a collection of tables that share a common schema.
These tables are physically separated and independent, but logically related through partition fields definition.

This document defines the storage format for Partitioned Namespace.
Similar to Lance being a storage-only format, the storage-only [Directory Catalog](catalog/dir/index.md) spec serves as the foundation for this Partitioned Namespace format.

The following example illustrates the logical layout of a partitioned namespace:

```text
Root Namespace (__manifest Lance table)
┌──────────────────────────────────────────────────┐
│ Table metadata (root namespace properties):      │
│     - schema = <shared Schema>                   │
│     - partition_spec_v1 = [event_date]           │
│     - partition_spec_v2 = [event_year, country]  │
└──────────────────────────────────────────────────┘
                        │
                Spec Version Level
                        │
        ┬───────────────┴───────────────┐
        │                               │
       v1                              v2
    (Namespace)                     (Namespace)
        │                               │
        │── <id1>                       │── <id3>
        │   (Namespace)                 │   (Namespace)
        │   event_date=2025-12-10       │   event_year=2025
        │     └── dataset (Table)       │     │
        │                               │     └── <id4>
        │── <id2>                       │         (Namespace)
        │   (Namespace)                 │         country=US
        │   event_date=2025-12-11       │           └── dataset (Table)
        │     └── dataset (Table)       │
        └── ...                         └── ...
```

## Metadata Definition

A directory catalog is identified as a partitioned namespace if the `__manifest` table's
[metadata](catalog/dir/index.md#root-namespace-properties) contains at least one partition spec version key.

The following properties are stored in the `__manifest` table's metadata map:

- `partition_spec_v<N>` (String): A JSON string representing a partition spec object for version N. The object contains the spec ID and an array of partition field definitions. See [Partition Spec](#partition-spec) for details.
- `schema` (String): A json string describing the Schema of the entire partitioned namespace, based on the [JsonArrowSchema](namespace/operations/models/JsonArrowSchema.md) schema in the Namespace Client spec. See [Namespace Schema](#schema) for more details.

See [Appendix A: Metadata Example](#appendix-a-metadata-example) for a complete example.

## Schema

The **Namespace Schema** defines the schema for all partition tables in the partitioned namespace.
Implementations must enforce that **all partition table schemas must be consistent with each other, as well as with the namespace schema**.
Most importantly, each field in the schema has a unique field ID stored in metadata under the key `lance:field_id`.
Field IDs are never reused and must remain consistent across partition tables.
This ensures partition specs using `source_ids` remain valid even if columns are renamed.

## Partition Spec

The **Namespace Partition Spec** defines how to derive partition values from a record in a partitioned namespace.
The partitioning information is stored in `partition_spec_v<N>` (e.g., `partition_spec_v1`),
which is a JSON object containing a spec ID and an array of partition field definitions.

### Partition Spec Schema

A partition spec is a JSON object with the following fields:

| Field        | JSON representation     | Example | Description                                                                                  |
|--------------|-------------------------|---------|----------------------------------------------------------------------------------------------|
| **`id`**     | `JSON int`              | `1`     | The spec version ID, matching the `N` in the key name                                        |
| **`fields`** | `JSON array of objects` | `[...]` | Array of partition field definitions (see [Partition Field Schema](#partition-field-schema)) |

### Partition Field Schema

Each element in the `fields` array is a partition field object with the following fields:

| Field             | JSON representation | Example                     | Description                                                                                                                                     |
|-------------------|---------------------|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| **`field_id`**    | `JSON string`       | `"event_year"`              | Unique identifier for this partition field (must not be renamed)                                                                                |
| **`source_ids`**  | `JSON int array`    | `[1]`                       | Field IDs of the source columns in the schema                                                                                                   |
| **`transform`**   | `JSON object`       | `{ "type": "year" }`        | Well-known partition transform (see [Partition Transform](#partition-transform)). Exactly one of `transform` or `expression` must be specified. |
| **`expression`**  | `JSON string`       | `"date_part('year', col0)"` | DataFusion SQL expression using `col0`, `col1`, ... as column references. Exactly one of `transform` or `expression` must be specified.         |
| **`result_type`** | `JSON object`       | `{ "type": "int32" }`       | The output type of the partition value ([JsonArrowDataType](namespace/operations/models/JsonArrowDataType.md) format)                              |

**Transform vs Expression**: Exactly one of `transform` or `expression` must be specified. When `transform` is specified, the expression is derived from the transform type. Custom partition logic that doesn't fit a well-known transform must use `expression` directly.

**Partition Field ID**: The `field_id` is a string that uniquely identifies each partition field across all spec versions. It is used as the column name suffix in `__manifest` (e.g., `partition_field_event_year`). Once assigned, a `field_id` must never be renamed or reused. This ensures stable column names in the manifest table.

**Field ID Reuse**: When evolving partition specs, if a new partition field has the same `source_ids` and `transform` (or `expression`) as an existing field, the same `field_id` must be reused. Otherwise, a new unique `field_id` must be assigned.

**Source Field IDs**: The `source_ids` array references field IDs stored in the schema's field metadata under the key `lance:field_id`. Using field IDs instead of column names ensures that partition specs remain valid even when source columns are renamed. In the partition expression, source columns are referenced as `col0`, `col1`, `col2`, etc., corresponding to the order of field IDs in the `source_ids` array.

### Partition Expression

The `expression` field contains a [DataFusion SQL expression](https://datafusion.apache.org/user-guide/sql/index.html) that transforms source column values into a partition value.
The placeholders `col0`, `col1`, `col2`, etc. represent the source columns in order corresponding to the `source_ids` array.
For single-column partitions, only `col0` is used.
The expression result type is declared by the `result_type` field.

All partition expressions must satisfy the following requirements:

1. **Deterministic**: The same input value must always produce the same output value.
2. **Stateless**: The expression must not depend on external state (e.g., current time, random values, session variables).
3. **Type-promotion resistant**: The expression must produce the same result for equivalent values regardless of their numeric type (e.g., `int32(5)` and `int64(5)` must yield the same partition value).
4. **Column removal resistant**: If a source field ID is not found in the schema, the column should be interpreted as NULL.
5. **NULL safe**: The partition expression should properly handle NULL case and have defined behavior (e.g. return NULL if NULL for single-column expression, ignore the NULL column for multi-column expression)
6. **Consistent with result type**: The `result_type` field declares the output type of the partition expression as an Arrow data type.
  This enables type checking without expression evaluation and ensures consistency across implementations.
  The partition expression's return type must be consistent with the result type in non-NULL case.

### Partition Transform

Partition transforms are **well-known partition expressions** with structured metadata that enables query optimization such as [Storage Partitioned Join](#storage-partitioned-join). 
When a partition field uses a well-known transform, the `transform` field should be specified instead of the `expression` field.

#### Transform Schema

The `transform` field is a JSON object with the following structure:

| Field             | JSON representation | Required                | Description                          |
|-------------------|---------------------|-------------------------|--------------------------------------|
| **`type`**        | `JSON string`       | Yes                     | The transform type (see table below) |
| **`num_buckets`** | `JSON int`          | For bucket transforms   | Number of buckets N                  |
| **`width`**       | `JSON int`          | For truncate transforms | Truncation width W                   |

#### Supported Transforms

| Transform Type | Parameters    | Derived Expression                                        | Result Type    | Description                              |
|----------------|---------------|-----------------------------------------------------------|----------------|------------------------------------------|
| `identity`     | (none)        | `col0`                                                    | same as source | Source value, unmodified                 |
| `year`         | (none)        | `date_part('year', col0)`                                 | `int32`        | Extract year from date/timestamp         |
| `month`        | (none)        | `date_part('month', col0)`                                | `int32`        | Extract month (1-12) from date/timestamp |
| `day`          | (none)        | `date_part('day', col0)`                                  | `int32`        | Extract day of month from date/timestamp |
| `hour`         | (none)        | `date_part('hour', col0)`                                 | `int32`        | Extract hour (0-23) from timestamp       |
| `bucket`       | `num_buckets` | `abs(murmur3(col0)) % N`                                  | `int32`        | Hash single column into N buckets        |
| `multi_bucket` | `num_buckets` | `abs(murmur3_multi(col0, col1, ...)) % N`                 | `int32`        | Hash multiple columns into N buckets     |
| `truncate`     | `width`       | `left(col0, W)` (string) or `col0 - (col0 % W)` (numeric) | same as source | Truncate to width W                      |

#### Hash Functions

The `bucket` and `multi_bucket` transforms use Murmur3 hash functions provided as Lance extensions to DataFusion:

- **`murmur3(col)`**: Computes the 32-bit Murmur3 hash (x86 variant, seed 0) of a single column. Returns a signed 32-bit integer. Returns NULL if input is NULL.
- **`murmur3_multi(col0, col1, ...)`**: Computes the Murmur3 hash across multiple columns. Returns a signed 32-bit integer. NULL fields are ignored during hashing; returns NULL only if all inputs are NULL.

The hash result is wrapped with `abs()` and modulo `N` to produce a non-negative bucket number in the range `[0, N)`.
For implementations that do not use DataFusion, the same behavior for hashing should be preserved.

## Physical Layout and Naming

A partitioned namespace supports multi-level partitioning with the following physical hierarchy:

- **Root Namespace**: The root namespace is implicit and represented by the `__manifest` table itself. Its properties (partition specs, schema) are stored in the `__manifest` table's metadata.
- **Spec Version Namespace**: The first-level child namespace, named `v1`, `v2`, etc. This identifies which partition spec version the data underneath was written with. When retrieving properties via API, these namespaces dynamically include a `partition_spec` property containing the partition spec for that version (copied from the root's `partition_spec_v<N>`).
- **Partition Namespace**: Each subsequent level of child namespaces represents a partition field. The order of partition namespace levels corresponds to the order of partition fields in the partition spec. Namespace names are randomly generated identifiers (see [Namespace Naming](#partition-namespace-naming)).
- **Partition Table**: At the end of the partition hierarchy, a `Table` object with the fixed name `dataset` contains the actual data. This is a standard, independently accessible Lance `Dataset` containing a subset of the partitioned namespace's data.

See [Appendix B: Physical Layout Example](#appendix-b-physical-layout-example) for a complete directory structure example.

### Partition Namespace Naming

Partition namespaces use **random identifier naming** to avoid issues with special characters in partition values.

Partition namespace names are randomly generated 16-character base36 strings (using characters `a-z0-9`).
This provides ~83 bits of entropy, ensuring virtually zero collision probability for any practical number of partitions.
This approach ensures:

- No conflicts with reserved characters (e.g., `$`, `/`, `=`) that may appear in partition column values
- Consistent namespace names across different client implementations
- Fixed-length, predictable namespace identifiers

Since namespace names are random identifiers,
the actual partition values are stored in the `__manifest` table's partition columns (see [Manifest Table Schema](#manifest-table-schema)).

### Runtime Namespace Properties

Since namespace names are random identifiers, the actual partition values are stored in the
`__manifest` table's partition columns (see [Manifest Table Schema](#manifest-table-schema)).

Implementations may dynamically populate properties when retrieving namespace information via API:

- For partition namespaces: `partition.<field_id> = <value>` entries
- For spec version namespaces (v1, v2, etc.): `partition_spec` containing the partition spec for that version

These runtime properties are optional. Implementations may choose not to expose them for security or other reasons.
See [Appendix E: Runtime Namespace Properties Example](#appendix-e-runtime-namespace-properties-example) for examples.

## Query Optimization

This section describes query optimization techniques that leverage partitioned namespace metadata.

### Manifest Table Schema

The `__manifest` table schema is extended to include partition columns for efficient query optimization use cases. 
Instead of parsing namespace names to filter partitions, query engines can directly push down predicates to the manifest table.

**Extended Schema**: For each partition field defined in any partition spec version, 
the `__manifest` table includes an additional nullable column. 
The column name is `partition_field_{i}` where `{i}` is the partition field's `field_id`, and the type is the partition field's `result_type`. 
This naming convention avoids potential conflicts with user-defined column names. 
When a new partition spec version is defined, the `__manifest` table schema is updated accordingly to include any new partition columns.

| Column                       | Type     | Description                                                                 |
|------------------------------|----------|-----------------------------------------------------------------------------|
| `object_id`                  | `string` | Full namespace path with `$` separator (existing)                           |
| `object_type`                | `string` | `"namespace"` or `"table"` (existing)                                       |
| `metadata`                   | `string` | JSON-encoded metadata/properties (existing)                                 |
| `read_version`               | `uint64` | Table version for reads (optional, see [Transaction](#transaction))         |
| `read_branch`                | `string` | Table branch for reads (optional, see [Transaction](#transaction))          |
| `read_tag`                   | `string` | Table tag for reads (optional, see [Transaction](#transaction))             |
| `partition_field_{field_id}` | `<type>` | Partition value for the field (nullable, inherited from parent namespaces)  |
| ...                          | ...      | Additional partition field columns as needed                                |

Partition values are inherited from parent namespaces - each row has all partition values from its ancestors. 
See [Appendix C: Manifest Table Example](#appendix-c-manifest-table-example) for a complete example.

### Partition Pruning

Partition pruning is performed via the `__manifest` table, which contains partition column values for efficient filtering.

Here is the end-to-end workflow:

1. Query engine analyzes the query predicate to identify filters on partition columns
2. For each partition expression, the engine evaluates the expression with the query values to compute the expected partition value(s)
3. Engine queries `__manifest` with filters on the partition columns
4. Engine retrieves the paths of matching `dataset` tables
5. Engine scans only the relevant partition tables

### Storage Partitioned Join

Storage Partitioned Join (SPJ) is an optimization that eliminates or reduces shuffle operations when 
joining two partitioned datasets on their partition columns. 
When both sides of a join are partitioned by the same or compatible transforms on the join keys, 
the query engine can join partitions directly without redistributing data.

SPJ can be applied when:

1. Both datasets are partitioned by the same column(s) used in the join predicate
2. The partition transforms are compatible (see [Transform Compatibility](#transform-compatibility))
3. The query engine supports reporting partition information

For SPJ to work, the partition transforms must be compatible:

- **Same transform type**: Both sides use the same transform (e.g., both use `year` on a date column)
- **Bucket divisibility**: For bucket transforms, one bucket count must evenly divide the other. The side with fewer buckets becomes the "coarser" partition that may match multiple finer partitions.
- **Time hierarchy**: Coarser time transforms can match finer ones (e.g., `day` partitions can be grouped to match `month` partitions)

Here is the end-to-end workflow:

1. Query engine analyzes the join predicate to identify join keys
2. For each partitioned namespace, the engine reads the partition spec to determine the transform on join keys
3. If transforms are compatible, the engine computes which partitions can be joined without shuffle:
    - For identical transforms: Partitions with equal partition values are joined directly
    - For compatible bucket transforms: Partitions from the coarser side match multiple partitions from the finer side based on `finer_bucket % coarser_bucket_count`
    - For compatible time transforms: Partitions from the finer side are grouped to match coarser partitions
4. Engine executes the join partition-by-partition, avoiding full data shuffle

See [Appendix F: Storage Partitioned Join Example](#appendix-f-storage-partitioned-join-example) for a complete example.

## Partition Evolution

The partition spec supports **versioning** to allow partition strategies to evolve over time. 
Each partition spec version defines its own set of partition columns and expressions. 
Data written to the partitioned namespace records which spec version it was created under via the version namespace (`v1/`, `v2/`, etc.).

### Evolution Scenarios

- **Adding partition columns**: Create a new spec version with additional partition columns. New data is written under the new version while existing partitions remain accessible.
- **Changing partition expressions**: Create a new spec version with different expressions (e.g., changing from daily to yearly partitioning). Both versions coexist.
- **Removing partition columns**: Create a new spec version without certain columns. Legacy data under old versions remains queryable.

### Compatibility with Partition Pruning

When querying across multiple spec versions, the query engine must handle each version according to its partition spec. 
For example, if `v1` partitions by `event_date` and `v2` partitions by `year(event_date)`, a query filtering on `event_date = '2025-12-10'` will:

1. Match exact partitions in `v1`
2. Compute `year('2025-12-10') = 2025` and scan all matching year partitions in `v2`

This design ensures backward compatibility while enabling partition strategy evolution without data migration.

## Transaction

### Single-Partition Transaction

Operations within a single partition table are ACID-compliant according to the Lance table specification.
Each partition is an independent Lance table, so reads and writes to a single partition follow standard Lance transaction semantics.

### Multi-Partition Transaction

By default, operations across multiple partitions have weaker guarantees:

- **Writes across partitions are not atomic or consistent**: A write that affects multiple partitions may partially succeed, leaving some partitions updated while others are not.
- **Reads across partitions are not isolated**: A read spanning multiple partitions may observe different versions of each partition, leading to inconsistent views.

To enable stronger transactional guarantees across partitions, the `__manifest` table can optionally include `read_version`, `read_branch`, and `read_tag` columns for a table.
These columns record which version of each partition table to read.

#### Read Behavior

Users should specify one of the following combinations:

1. **`read_version` only**: Read the specified version from the main branch.
2. **`read_branch` + `read_version`**: Read the specified version from the specified branch.
3. **`read_tag` only**: Read the version referenced by the specified tag.

When all columns are NULL or not present, readers should read the latest version from the main branch.

#### Commit Behavior

Multi-partition transactions are guarded by commits against the `__manifest` table. A typical multi-partition write follows this pattern:

1. Write data to each affected partition table independently
2. Atomically update the `read_version` (and optionally `read_branch` or `read_tag`) of all affected partitions in a single `__manifest` commit

This ensures all-or-nothing visibility of changes across partitions.

#### Conflict Resolution

If concurrent commits have been committed to `__manifest` since the transaction began, the implementation must either:

1. Rebase the current commit onto the latest `__manifest` version and retry the commit, or
2. Fail the current commit and return an error to the caller

Implementations are responsible for ensuring the appropriate conflict detection and resolution strategy to guarantee ACID semantics during multi-partition transactions.

## Appendices

### Appendix A: Metadata Example

A complete example of partitioned namespace metadata properties with two spec versions:

```json
{
  "partition_spec_v1": {
    "id": 1,
    "fields": [
      {
        "field_id": "event_date",
        "source_ids": [1],
        "transform": { "type": "identity" },
        "result_type": { "type": "date32" }
      }
    ]
  },
  "partition_spec_v2": {
    "id": 2,
    "fields": [
      {
        "field_id": "event_year",
        "source_ids": [1],
        "transform": { "type": "year" },
        "result_type": { "type": "int32" }
      },
      {
        "field_id": "country",
        "source_ids": [2],
        "transform": { "type": "identity" },
        "result_type": { "type": "utf8" }
      }
    ]
  },
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
- `v1` partitions by `event_date` using the identity transform with `result_type: date32`
- `v2` partitions first by year of `event_date` using the year transform with `result_type: int32`, then by `country` using the identity transform with `result_type: utf8`
- The `__manifest` table will have three partition columns: `partition_field_event_date` (date32), `partition_field_event_year` (int32), `partition_field_country` (utf8)
- The schema follows [JsonArrowSchema](namespace/operations/models/JsonArrowSchema.md) format

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

The `__manifest` table for a partitioned namespace with partition fields `event_date` (v1), `event_year` (v2) and `country` (v2), showing entries from both spec versions:

| object_id                                     | object_type | metadata | read_version | read_branch | read_tag | partition_field_event_date | partition_field_event_year | partition_field_country |
|-----------------------------------------------|-------------|----------|--------------|-------------|----------|----------------------------|----------------------------|-------------------------|
| v1                                            | namespace   | {}       | NULL         | NULL        | NULL     | NULL                       | NULL                       | NULL                    |
| v1$k7m2n9p4q8r5s3t6                           | namespace   | {}       | NULL         | NULL        | NULL     | 2025-12-10                 | NULL                       | NULL                    |
| v1$k7m2n9p4q8r5s3t6$dataset                   | table       | {}       | 5            | NULL        | NULL     | 2025-12-10                 | NULL                       | NULL                    |
| v2                                            | namespace   | {}       | NULL         | NULL        | NULL     | NULL                       | NULL                       | NULL                    |
| v2$e9f0g1h2i3j4k5l6                           | namespace   | {}       | NULL         | NULL        | NULL     | NULL                       | 2025                       | NULL                    |
| v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4          | namespace   | {}       | NULL         | NULL        | NULL     | NULL                       | 2025                       | US                      |
| v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4$dataset  | table       | {}       | 3            | NULL        | NULL     | NULL                       | 2025                       | US                      |

Note: The root namespace properties (`partition_spec_v1`, `partition_spec_v2`, `schema`) are stored in the `__manifest` table's metadata, not as a row. The `object_id` uses `$` as the namespace path separator. Partition columns use the naming convention `partition_field_{field_id}` where `{field_id}` is the partition field's string identifier. Partition values are inherited from parent namespaces. When retrieving properties via API, partition values are converted to `partition.<field_id> = <value>` entries.

See [Appendix D: Partition Pruning Example](#appendix-d-partition-pruning-example) for an example of how partition pruning queries work.

### Appendix D: Partition Pruning Example

This example demonstrates how a query engine translates a user query into a partition pruning query against the `__manifest` table.

Given a user query:

```sql
SELECT * FROM partitioned_namespace
WHERE event_date = '2025-12-10' AND country = 'US'
```

The engine translates this to the following `__manifest` DataFusion query plan to examine related partition tables.

```sql
SELECT object_id, location, read_version, read_branch, read_tag
FROM __manifest
WHERE object_type = 'table'
  AND (
    (object_id LIKE 'v1$%'
      AND partition_field_event_date = DATE '2025-12-10')
    OR
    (object_id LIKE 'v2$%'
      AND partition_field_event_year = date_part('year', DATE '2025-12-10')
      AND partition_field_country = 'US')
  )
```
Notice here that the query plan can leverage the partition expression, in this case `date_part('year', col0)`.
One example way to perform such substitution is:

1. Parsing the expression string (e.g., `date_part('year', col0)`) into an expression AST using DataFusion's SQL parser
2. Traversing the AST and replacing all `col0`, `col1`, etc. column references with the corresponding literal query values (e.g., `DATE '2025-12-10'`)
3. Evaluating the modified expression to produce the partition filter value (e.g., `2025`)

This query returns:

| object_id                                    | location                                              | read_version | read_branch | read_tag |
|----------------------------------------------|-------------------------------------------------------|--------------|-------------|----------|
| v1$k7m2n9p4q8r5s3t6$dataset                  | b4a3c2d1_v1$k7m2n9p4q8r5s3t6$dataset                  | 5            | NULL        | NULL     |
| v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4$dataset | aabbccdd_v2$e9f0g1h2i3j4k5l6$m7n8o9p0q1r2s3t4$dataset | 3            | NULL        | NULL     |

- For partition spec v1, the `country = 'US'` filter cannot be pushed to partition pruning (v1 has no `country` partition), so it must be applied during the table scan
- For partition spec v2, both filters are pushed down: `partition_field_event_year = 2025` (computed from `year(event_date)`) and `partition_field_country = 'US'`
- The engine reads each table at the version specified by `read_version`, `read_branch`, or `read_tag` for consistent snapshot reads

### Appendix E: Runtime Namespace Properties Example

This appendix shows examples of runtime properties that implementations MAY return when describing namespaces.
These are optional behaviors - implementations may choose not to expose them for security or other reasons.

**Spec Version Namespace**

`DescribeNamespace(["v1"])` returns:

```json
{
  "properties": {
    "partition_spec": "{\"id\":1,\"fields\":[{\"field_id\":\"event_date\",\"source_ids\":[1],\"transform\":{\"type\":\"identity\"},\"result_type\":{\"type\":\"date32\"}}]}"
  }
}
```

**Partition Namespace (v1)**

`DescribeNamespace(["v1", "k7m2n9p4q8r5s3t6"])` returns:

```json
{
  "properties": {
    "partition.event_date": "2025-12-10"
  }
}
```

**Partition Namespace (v2, first level)**

`DescribeNamespace(["v2", "e9f0g1h2i3j4k5l6"])` returns:

```json
{
  "properties": {
    "partition.event_year": "2025"
  }
}
```

**Partition Namespace (v2, second level)**

`DescribeNamespace(["v2", "e9f0g1h2i3j4k5l6", "m7n8o9p0q1r2s3t4"])` returns:

```json
{
  "properties": {
    "partition.country": "US"
  }
}
```

Note: Each namespace only returns the partition value for its own level.
To get all partition values in a path, the client must query each ancestor namespace.

### Appendix F: Storage Partitioned Join Example

This example demonstrates how a query engine performs a Storage Partitioned Join (SPJ) between two partitioned namespaces.

**Setup**: Two partitioned namespaces with compatible bucket transforms:

- `orders` namespace: partitioned by `bucket(customer_id, 16)` with partition field `customer_bucket`
- `customers` namespace: partitioned by `bucket(id, 8)` with partition field `id_bucket`

**User Query**:

```sql
SELECT o.*, c.name
FROM orders o
JOIN customers c ON o.customer_id = c.id
```

**SPJ Analysis**:

1. The engine reads partition specs from both namespaces' `__manifest` tables
2. Both join keys use bucket transforms: `orders.customer_id` → `bucket(16)`, `customers.id` → `bucket(8)`
3. Since 8 divides 16 evenly, the transforms are compatible

**Partition Matching**:

For each `customers` partition with bucket value `i`, 
the matching `orders` partitions have bucket values where `bucket % 8 == i`:

| customers bucket | orders buckets |
|------------------|----------------|
| 0                | 0, 8           |
| 1                | 1, 9           |
| 2                | 2, 10          |
| 3                | 3, 11          |
| 4                | 4, 12          |
| 5                | 5, 13          |
| 6                | 6, 14          |
| 7                | 7, 15          |

**Execution Plan**:

The engine queries both `__manifest` tables to get partition locations:

```sql
-- Get orders partitions
SELECT partition_field_customer_bucket, location, read_version
FROM orders.__manifest
WHERE object_type = 'table'

-- Get customers partitions
SELECT partition_field_id_bucket, location, read_version
FROM customers.__manifest
WHERE object_type = 'table'
```

For each customers partition `i`, the engine:

1. Reads the customers partition where `partition_field_id_bucket = i`
2. Reads the orders partitions where `partition_field_customer_bucket % 8 = i`
3. Performs a local join without shuffle

**Result**: The join completes with 8 parallel partition-wise joins instead of a full shuffle of both datasets.
