## Lance Partition Spec (Experimental)

### 1. Overview
This specification defines a standard for describing and operating on **Partitioned Tables** at the **Lance Namespace** level. A partitioned table is a logical table whose data is physically split and stored in multiple independent Lance `Table` objects.

The core objective of this specification is to provide a standardized and efficient method for organizing, managing, and accessing large-scale datasets without depending on a specific computation engine.

This specification follows the "convention over configuration" principle, defining a partitioned table as a **metadata specification**. It is the responsibility of compatible clients or computation engines to interpret and enforce it.

### 2. Core Concepts
#### 2.1. Partitioned Table
A partitioned table is a logical concept represented in the Lance Namespace by a special **`Namespace` object**. This `Namespace` object serves as the root of the partitioned table, and together with all its descendant objects, it forms a complete logical table.

#### 2.2. Partition Hierarchy
A partitioned table supports multi-level partitioning with the following physical hierarchy:

- **Root Namespace**: The `Namespace` object that represents the entire partitioned table.
- **Intermediate Partition**: Each child `Namespace` under the root namespace represents an intermediate-level virtual partition directory.
- **Leaf Partition**: Each `Table` object at the end of the partition hierarchy represents a leaf partition. It is a standard, independently accessible Lance `Dataset` containing a subset of the logical table's data.

All leaf partitions (`Table` objects) **must share the exact same Schema**. This schema is also the schema for the entire logical partitioned table.

```text
Root Namespace (logical partitioned table)
┌─────────────────────────────────────────────────────────────────────┐
│ /my_partitioned_table                                              │
│   Namespace properties:                                            │
│     - lance.partitioning.is_partitioned = "true"                   │
│     - lance.partitioning.schema = <shared logical Schema>          │
│     - lance.partitioning.partition_columns = [event_date, country] │
└─────────────────────────────────────────────────────────────────────┘
                          │
              Intermediate Partition(s)
                          │
          ├─────────────────────────────────────────────────────┬─────────────────────────┐
          │                                                     │                         │
  event_date=2025-12-10/                               event_date=2025-12-11/      ...
      (Namespace)                                          (Namespace)
         │                                                     │
         ├─ country=US.lance   (Leaf Partition / Table)        ├─ country=US.lance   (Leaf Partition / Table)
         └─ country=CN.lance   (Leaf Partition / Table)        └─ country=FR.lance   (Leaf Partition / Table)
```

#### 2.3. Partition Columns
Partition columns are a subset of columns from the Schema whose values determine which specific leaf partition a record belongs to. The values of partition columns are **not** physically stored in the data files of the leaf partitions, as this information is already embedded in the physical storage path.

#### 2.4. Partition Keys
Partition keys are the set of values from the partition columns for a specific record. The unique combination of these values determines the physical storage path for that record.

### 3. Specification Details
#### 3.1. Metadata Definition
A `Namespace` object is identified as the root of a partitioned table if its metadata properties contain the following key-value pairs:

- `lance.partitioning.is_partitioned` (String): The value must be `"true"`. This explicitly identifies the Namespace as the root of a partitioned table.
- `lance.partitioning.partition_columns` (String): A JSON string representing an array of partition column definition objects. Each object describes a partition column:
    - `name` (String): The name of the partition column, which must exist in the table's Schema.
    - `function` (String): The partitioning function applied to the column, such as `identity`, `bucket`, `year`, `month`, `day`, `hour`, `hash`, or `truncate(N)`. Defaults to `identity`.
    - `properties` (Dict): Attributes for the partition, such as the number of buckets for the `bucket` function.
- `lance.partitioning.schema` (String): A JSON string describing the logical Schema of the entire partitioned table, following the Arrow IPC JSON format. This Schema **must** include all columns defined in `partition_columns`.

**Example:**
```json
{
  "lance.partitioning.is_partitioned": "true",
  "lance.partitioning.partition_columns": "[{\"name\": \"event_date\", \"function\": \"identity\"}, {\"name\": \"tenant_id\", \"function\": \"bucket\", \"properties\": {\"num_buckets\": \"100\"}}]",
  "lance.partitioning.schema": "{... Arrow Schema JSON ...}"
}
```

#### 3.2. Physical Layout and Naming
The physical layout of partitions maps directly to the Lance Namespace hierarchy. The values of the partition keys determine the names of the sub-namespaces or tables.

For example, using a `Directory Namespace`, a table partitioned by `event_date` and `country` might have a data layout like this:

```text
/my_partitioned_table/ (Namespace, properties set)
  ├── event_date=2025-12-10/ (Namespace)
  │   └── country=US.lance (Table)
  │   └── country=CN.lance (Table)
  └── event_date=2025-12-11/ (Namespace)
      └── country=US.lance (Table)
      └── country=FR.lance (Table)
```

This `key=value` naming style is the recommended best practice (similar to Hive) to enhance interoperability. Clients should be able to parse this format to reconstruct the partition column values.

#### 3.3. Partition Discovery and Enumeration
Clients can discover all leaf partitions of a partitioned table by recursively calling the `ListNamespaces` and `ListTables` operations. Starting from the root namespace, they traverse the entire subtree, collecting all `Table` objects and their paths. By parsing these paths, the partition key combination for each leaf partition can be determined.

#### 3.4. Partition Pruning
During queries, clients or computation engines should use query conditions (e.g., a `WHERE` clause) to perform partition pruning.

- **Process**:
    1. Parse the filter conditions in the query to extract expressions involving partition columns.
    2. Based on these expressions, calculate the subset of leaf partitions that could potentially satisfy the conditions.
    3. During the scan, read only the `Table` objects within this subset, thus avoiding a full table scan.

- **Example**: For the partitioned table above, a query `WHERE event_date = '2025-12-11' AND country != 'FR'` would only scan the single leaf partition at `/my_partitioned_table/event_date=2025-12-11/country=US.lance`.

### 4. Partition Functions
To provide more flexible partitioning strategies, the specification supports applying functions to partition columns. The `function` field in `lance.partitioning.partition_columns` defines this behavior. Clients and engines are responsible for implementing these functions. When writing data, the partition function is applied to calculate the target path. When performing partition pruning, query conditions need to be transformed into filters on the results of the partition function.

The semantics, configuration, and behavior of each function are detailed below.

#### 4.1. `identity`
- **Semantics**: Uses the raw value of the column directly as the partition name. This is the most basic partitioning method, suitable for partition columns that are low-cardinality, business-meaningful enum values (e.g., country, business type).
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Write Path**: `partition_col_name=value`.
    - **Query Pruning**: Pruning is performed directly using equality, `IN`, or inequality queries on the partition column. For example, `WHERE country = 'US'` will directly match the `country=US` partition.
- **Supported Types**: All atomic types that can be reliably stringified (string, integer, date, etc.).

#### 4.2. Time Functions: `year`, `month`, `day`, `hour`
- **Semantics**: Extracts a specific time part from a date, time, or timestamp type column to be used as the partition value.
    - `year(col)`: Extracts the four-digit year.
    - `month(col)`: Extracts the two-digit month (01-12).
    - `day(col)`: Extracts the two-digit day (01-31).
    - `hour(col)`: Extracts the two-digit hour (00-23).
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Constraint**: The source column must be of a date, time, or timestamp type.
    - **Write Path**: For `year(ts)`, the path would be `ts_year=2025`. For multi-level time partitions like `year(ts)`, `month(ts)`, the path would be `ts_year=2025/ts_month=12/`.
    - **Query Pruning**: The query engine must be able to transform range queries on the original timestamp column into queries on the partition columns. For example, for a query `WHERE ts >= '2025-12-10T10:00:00Z' AND ts < '2025-12-11T00:00:00Z'`, if the table is partitioned by `year`, `month`, and `day`, it should be pruned to scan only the `ts_year=2025/ts_month=12/ts_day=10/` partition.
- **Timezone**: All time conversions should be performed based on the UTC standard timezone to ensure the consistency of partition values.

#### 4.3. `bucket`
- **Semantics**: Distributes high-cardinality column values into a fixed number of buckets using a hash function. This is used to prevent data skew and control the number of partition files.
- **Properties**:
    - `num_buckets` (Integer, required): Specifies the number of buckets. Must be a positive integer.
- **Behavior**:
    - **Hash Algorithm**: It is recommended to use the Murmur3 32-bit hash algorithm (`murmur3_32`) to ensure consistency across different client implementations.
    - **Calculation**: `bucket_index = murmur3_32(value) mod num_buckets`.
    - **Write Path**: `partition_col_name_bucket=N`, where N is the calculated bucket index.
    - **Query Pruning**: Only equality queries on the partition column can be used for partition pruning. For a query like `WHERE user_id = 'some_user_id'`, the client must first calculate the hash and bucket for `'some_user_id'` and then match the corresponding partition. Range queries cannot leverage `bucket` partitioning for pruning.
- **Stability**: Once defined, `num_buckets` should not be changed, as doing so would cause data to be written to inconsistent locations.

#### 4.4. `truncate`
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

#### 4.5. `hash`
- **Semantics**: The `hash` function is primarily used for mapping data of any type (especially complex or composite types) to a fixed partition value. It helps achieve uniform distribution or can be used as a form of anonymized partitioning. Unlike `bucket`, `hash` does not directly limit the number of partitions but generates a hash value string.
- **Properties**: This function requires no additional properties.
- **Behavior**:
    - **Hash Algorithm**: It is also recommended to use the Murmur3 32-bit or 64-bit hash (`murmur3_32`/`murmur3_64`) and output it as a hexadecimal string.
    - **Write Path**: `partition_col_name_hash=<hex_hash_string>`.
    - **Query Pruning**: Pruning can only be performed when the query condition provides the exact same value that was used for partitioning.

### 5. Compatibility and Error Handling
- **Backward Compatibility**: Clients that are not aware of this partitioning specification can still browse and operate on a partitioned table as a normal directory tree of namespaces and tables.
- **Schema Consistency**: When writing data, if the schema of the data to be written is incompatible with the defined logical schema of the partitioned table, an error should be returned. When creating a new leaf partition, the logical table's schema must be used.
- **Atomicity**: In non-transactional mode, write operations are not atomic. Users should either write only one partition per job, or adopt an idempotent approach (e.g., `INSERT OVERWRITE`) when writing multiple partitions.
