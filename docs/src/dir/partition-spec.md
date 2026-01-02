## Lance Partitioned Namespace Spec

### 1. Overview
This specification defines a standard for describing and operating on **Partitioned Namespaces**. A **Partitioned Namespace** is a special **Directory Namespace(V2)** with [constraints](#2-specification-constraints). 

A partitioned namespace can be treated as a logical `Table`, whose data is physically split and stored in multiple independent Lance `Table` objects. It is the responsibility of compatible clients or computation engines to interpret and enforce it.

```text
Root Namespace (partitioned namespace)
┌─────────────────────────────────────────────────────────────────────┐
│ /root                                                               │
│   Namespace properties:                                             │
│     - lance.partitioning.enabled = "true"                           │
│     - lance.partitioning.schema = <shared Schema>                   │
│     - lance.partitioning.partition-columns = [event_date, country]  │
└─────────────────────────────────────────────────────────────────────┘
                          │
              Intermediate Partition(s)
                          │
          ┬─────────────────────────────────────────────────────┬─────────────────────────┐
          │                                                     │                         │
  event_date=2025-12-10/                               event_date=2025-12-11/      ...
      (Namespace)                                          (Namespace)
          │                                                     │
          ├─ country=US.lance   (Leaf Partition / Table)        ├─ country=US.lance   (Leaf Partition / Table)
          └─ country=CN.lance   (Leaf Partition / Table)        └─ country=FR.lance   (Leaf Partition / Table)
```

### 2. Specification Constraints
#### 2.1. Metadata Definition
A `Namespace` object is identified as the root of a partitioned namespace if its metadata properties contain the following key-value pairs:

- `lance.partitioning.enabled` (String): The value must be `"true"`. This explicitly identifies the Namespace as the root of a partitioned namespace.
- `lance.partitioning.partition-columns` (String): A JSON string representing an array of partition column definition objects. Each object describes a partition column. See [Partitioning](#3-partitioning) for details.
- `lance.partitioning.schema` (String): A [JsonArrowSchema](https://lance.org/format/namespace/client/operations/models/JsonArrowSchema/) json string describing the Schema of the entire partitioned namespace. This Schema **must** include all columns defined in `partition-columns`.

**Example:**
```json
{
  "lance.partitioning.enabled": "true",
  "lance.partitioning.partition-columns": [
    {
      "name" : "country",
      "function" : "identity",
      "properties" : { }
    },
    {
      "name" : "city",
      "function" : "bucket",
      "properties" : {
        "num-buckets" : "4"
      }
    }
  ],
  "lance.partitioning.schema": {
    "fields" : [ {
      "metadata" : { },
      "name" : "id",
      "nullable" : false,
      "type" : {
        "fields" : [ ],
        "length" : null,
        "type" : "int64"
      }
    }, {
      "metadata" : { },
      "name" : "country",
      "nullable" : true,
      "type" : {
        "fields" : [ ],
        "length" : null,
        "type" : "utf8"
      }
    }, {
      "metadata" : { },
      "name" : "city",
      "nullable" : true,
      "type" : {
        "fields" : [ ],
        "length" : null,
        "type" : "utf8"
      }
    } ],
    "metadata" : { }
  }
}
```

#### 2.2. Physical Layout and Naming
A partitioned namespace supports multi-level partitioning with the following physical hierarchy:

- **Root Namespace**: The `Namespace` object that represents the entire partitioned namespace.
- **Intermediate Partition**: Each child `Namespace` under the root namespace represents an intermediate-level virtual partition directory.
- **Leaf Partition**: Each `Table` object at the end of the partition hierarchy represents a leaf partition. It is a standard, independently accessible Lance `Dataset` containing a subset of the partitioned namespace's data.

The schema of leaf partitions (Table objects) could differ from the schema of the partitioned namespace. Conflicts are resolved during read and write operations. See [Schema Consistency](#4-schema-consistency).

The values of the partition keys determine the names of the sub-namespaces or tables. For example, a partitioned namespace partitioned by `event_date` and `country` in [V2 Manifest](https://lance.org/format/namespace/dir/catalog-spec/#v2-manifest) might have a data layout and naming like this:

```text
.
└── /my/dir1/
    ├── __manifest/                                 # The manifest table
    │   ├── data/
    │   │   └── ...
    │   └── _versions/
    │       └── ...
    ├── root/                                       # Root of partitioned namespace
    │   └── ...
    ├── a1b2c3d4_event_date=2025-12-10$country=US/  # Leaf partition (Table)
    │   └── ...
    ├── a1b2c3d4_event_date=2025-12-10$country=CN/  # Leaf partition (Table)
    │   └── ...    
    ├── a1b2c3d4_event_date=2025-12-11$country=US/  # Leaf partition (Table)
    │   └── ...    
    └── a1b2c3d4_event_date=2025-12-11$country=FR   # Leaf partition (Table)
        └── ...
```

### 3. Partitioning
Partitioning defines how to parse partition values from a record. The partitioning information is stored in `lance.partitioning.partition-columns`, which is a JSON array of partition field objects. Each partition field contains:
* A **source column id** or a list of **source column ids** from the table’s schema
* A **function** that is applied to the source column(s) to produce a partition value. Partition value must be a string.
* A **properties** that contains the parameters of function.

| Field            | JSON representation | Example      |
|------------------|---------------------|--------------|
| **`source-id`**  | `JSON int`          | 1            |
| **`source-ids`** | `JSON list of ints` | `[1,2]`      |
| **`function`**   | `JSON string`       | `bucket[16]` |

The order of partition fields in the partitioning corresponds to the order of partition values in the physical layout.

#### 3.1. Partitioning Function Overview

| Function name  | Properties      | Description                                                  | Source types                                                         | Result type |
|----------------|-----------------|--------------------------------------------------------------|----------------------------------------------------------------------|-------------|
| **`identity`** | none            | Source value, unmodified                                     | Any type could be reliably stringified (string, integer, date, etc.) | `string`    |
| **`bucket`**   | bucket_size=N   | Hash of value, mod `N` (see below)                           | `int`, `long`, `decimal`, `date`, `timestamp`, `string`, `binary`    | `string`    |
| **`truncate`** | truncate_size=N | Value truncated to width `W` (see below)                     | `int`, `long`, `decimal`, `string`                                   | `string`    |
| **`hash`**     | none            | Hash of value                                                | `int`, `long`, `decimal`, `date`, `timestamp`, `string`, `binary`    | `string`    |
| **`year`**     | none            | Extract a date or timestamp year, as years from 1970         | `date`, `timestamp`                                                  | `string`    |
| **`month`**    | none            | Extract a date or timestamp month, as months from 1970-01-01 | `date`, `timestamp`                                                  | `string`    |
| **`day`**      | none            | Extract a date or timestamp day, as days from 1970-01-01     | `date`, `timestamp`                                                  | `string`    |
| **`hour`**     | none            | Extract a timestamp hour, as hours from 1970-01-01 00:00:00  | `timestamp`                                                          | `string`    |

#### 3.2. `identity`
- **Semantics**: Uses the raw value of the column directly as the partition name.
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Write Path**: `partition_col_name=value`.
    - **Query Pruning**: Pruning is performed directly using equality, `IN`, or inequality queries on the partition column.
- **Supported Types**: All atomic types that can be reliably stringified (string, integer, date, etc.).

#### 3.3. `bucket`
- **Semantics**: Distributes high-cardinality column values into a fixed number of buckets using a hash function. This is used to prevent data skew and control the number of partition files.
- **Properties**:
    - `num_buckets` (Integer, required): Specifies the number of buckets. Must be a positive integer.
- **Behavior**:
    - **Hash Algorithm**: It is recommended to use the Murmur3 32-bit hash algorithm (`murmur3_32`) to ensure consistency across different client implementations.
    - **Calculation**: `bucket_index = murmur3_32(value) mod num_buckets`.
    - **Write Path**: `partition_col_name_bucket=N`, where N is the calculated bucket index.
    - **Query Pruning**: Only equality queries on the partition column can be used for partition pruning.
- **Stability**: Once defined, `num_buckets` should not be changed, as doing so would cause data to be written to inconsistent locations.

#### 3.4. `truncate`
- **Semantics**: Truncates a value to reduce its cardinality. This function behaves differently for string and numeric types.
- **Properties**:
    - `width` (Integer, required): Specifies the truncation width.
- **Behavior**:
    - **String Type**: Truncates the string to its first `width` characters. For example, `truncate(s, 3)` applied to "abcdef" results in "abc".
        - **Write Path**: `s_trunc=abc`.
        - **Query Pruning**: Equality queries or `LIKE 'prefix%'` queries on the original string can be transformed into queries on the truncated partitions.
    - **Numeric Type**: For integers or long integers, truncation is achieved through a modulo operation: `truncated_value = value - (value mod width)`. For example, `truncate(i, 10)` applied to 123 results in 120.
        - **Write Path**: `i_trunc=120`.
        - **Query Pruning**: Range queries can be partially utilized. For example, a query `WHERE i > 125` can definitively exclude partitions `<= 120`.
- **Constraint**: `width` must be a positive integer.

#### 3.5. `hash`
- **Semantics**: The `hash` function is primarily used for mapping data of any type (especially complex or composite types) to a fixed partition value. It helps achieve uniform distribution or can be used as a form of anonymized partitioning. Unlike `bucket`, `hash` does not directly limit the number of partitions but generates a hash value string.
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Hash Algorithm**: It is also recommended to use the Murmur3 32-bit or 64-bit hash (`murmur3_32`/`murmur3_64`) and output it as a hexadecimal string.
    - **Write Path**: `partition_col_name_hash=<hex_hash_string>`.
    - **Query Pruning**: Pruning can only be performed when the query condition provides the exact same value that was used for partitioning.

#### 3.6. Time Functions: `year`, `month`, `day`, `hour`
- **Semantics**: Extracts a specific time part from a date, time, or timestamp type column to be used as the partition value.
    - `year(col)`: Extracts the four-digit year.
    - `month(col)`: Extracts the two-digit month (01-12).
    - `day(col)`: Extracts the two-digit day (01-31).
    - `hour(col)`: Extracts the two-digit hour (00-23).
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Constraint**: The source column must be of a date, time, or timestamp type.
    - **Write Path**: For `year(ts)`, the path would be `ts_year=2025`. For multi-level time partitions like `year(ts)`, `month(ts)`, the path would be `ts_year=2025/ts_month=12/`.
    - **Query Pruning**: The query engine must be able to transform range queries on the original timestamp column into queries on the partition columns. For example, for a query `WHERE ts >= '2025-12-10T10:00:00Z' AND ts < '2025-12-11T00:00:00Z'`, if the namespace is partitioned by `year`, `month`, and `day`, it should be pruned to scan only the `ts_year=2025/ts_month=12/ts_day=10/` partition.
- **Timezone**: All time conversions should be performed based on the UTC standard timezone to ensure the consistency of partition values.

### 4. Schema Consistency
The schema of a partitioned namespace could differ from the schema of a leaf partition (table). Schema conflicts are resolved during reading and writing, with the following rules:

| Operation  | Namespace Schema | Table Schema            | Behavior                                                                                                                                                          |
|------------|------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Reads**  | Exists           | Does not exist          | Reading it will produce null values                                                                                                                               |
|            | Does not exist   | Exists                  | Table schema column will be ignored                                                                                                                               |
|            | Exists           | Exists (different type) | The type from the namespace schema takes precedence. A type cast will be attempted during the read, and an error must be thrown if the conversion is not possible |
| **Writes** | Exists           | Does not exist          | An error must be thrown                                                                                                                                           |
|            | Does not exist   | Exists                  | A NULL value will be written. An error must be thrown if the column does not support NULL values                                                                  |
|            | Exists           | Exists (different type) | Its value will be converted to the type in the table schema during the write. An error must be thrown if the conversion is not possible                           |
