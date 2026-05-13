# Lance Directory Catalog

The **Lance Directory Catalog** is a storage-native catalog format that stores tables in a directory structure on any local or remote storage system. It requires no external metadata service — only a filesystem or object store.

Machine learning workloads frequently operate on datasets stored in object storage and favor minimal operational dependencies, even in production environments. However, existing lakehouse formats typically require an external catalog service, while storage-only approaches lack the transactional guarantees required for reliable production use. The Directory Catalog addresses this gap by providing a catalog built directly on top of the Lance table format.

The Directory Catalog has gone through 2 major spec versions:

- **V1 (Directory Listing)**: A lightweight, simple 1-level namespace that discovers tables by scanning the directory.
- **V2 (Manifest)**: A more advanced implementation backed by a manifest table (a Lance table) that supports nested namespaces and better performance at scale.

## V1: Directory Listing

V1 is a simple 1-level namespace where each table corresponds to a subdirectory with the format `<table_name>.lance`.
This mode is ideal for getting started quickly with Lance tables.

### Directory Layout

A directory catalog maps to a directory on storage, called the **catalog directory**.
A Lance table corresponds to a subdirectory in the catalog directory that has the format `<table_name>.lance`,
called a **table directory**.

Consider the following example catalog directory layout:

```
.
└── /my/dir1/
    ├── table1.lance/
    │   ├── data/
    │   │   ├── 0aa36d91-8293-406b-958c-faf9e7547938.lance
    │   │   └── ed7af55d-b064-4442-bcb5-47b524e98d0e.lance
    │   ├── _versions/
    │   │   └── 9223372036854775707.manifest
    │   └── _indices/
    │       └── 85814508-ed9a-41f2-b939-2050bb7a0ed5-fts/
    │           └── index.idx
    ├── table2.lance/
    ├── table3.lance/
    │   └── .lance-deregistered      # Marker: table3 is deregistered
    └── table4.lance/
        └── .lance-reserved          # Marker: table4 is reserved but not created
```

This describes a Lance Directory Catalog with the catalog directory at `/my/dir1/`.
It contains active tables `table1` and `table2` at table directories
`/my/dir1/table1.lance` and `/my/dir1/table2.lance`.
Table `table3` exists on storage but is deregistered (excluded from table listings).
Table `table4` is reserved but not yet created with data.

### Table Existence

In V1, a table exists in a Lance Directory Catalog if a table directory of the specific name exists
and the table is not marked as deregistered.
In object store terms, this means the prefix `<table_name>.lance/` has at least one file in it
and the file `<table_name>.lance/.lance-deregistered` does not exist.

### Marker Files

V1 uses marker files within table directories to track table state:

| Marker File           | Purpose                                                                 |
|-----------------------|-------------------------------------------------------------------------|
| `.lance-reserved`     | Indicates a table name/location is reserved but not yet created         |
| `.lance-deregistered` | Indicates a table has been deregistered but data is preserved           |

When a table is deregistered via the `DeregisterTable` operation, the `.lance-deregistered` marker file
is created inside the table directory. This causes the table to be excluded from `ListTables` results
and to return "not found" for `DescribeTable` and `TableExists` operations, while preserving the table data
for potential re-registration.

## V2: Manifest

V2 uses a special `__manifest` table (a Lance table) stored in the catalog directory to track all tables
and namespaces. This provides several advantages over V1:

- **Nested namespaces**: Support for hierarchical namespace organization
- **Better performance**: Table discovery queries the manifest table instead of scanning the directory and leverages Lance's random access capability.
- **Metadata support**: All operations can be supported, e.g. namespaces can have associated properties/metadata, tables can be renamed.
- **Optimized directory path**: Hash-based directory naming prevents conflicts and maximizes throughput in object storage.

Because the catalog metadata is itself stored as a Lance table, the catalog inherits the transactional semantics, snapshot isolation, and schema evolution guarantees of the table format, while also benefiting from Lance's random-access-friendly file layout and table-level indexing capabilities.

### Directory Layout

```
.
└── /my/dir1/
    ├── __manifest/                    # The manifest table
    │   ├── data/
    │   │   └── ...
    │   └── _versions/
    │       └── ...
    ├── table1.lance/                  # Root namespace table (compatibility mode)
    │   └── ...
    ├── a1b2c3d4_table2/               # Root namespace table (V2)
    │   └── ...
    └── e5f6g7h8_ns1$table3/           # Table in child namespace
        └── ...
```

### Manifest Table Schema

The `__manifest` table has the following schema:

| Column         | Type                    | Description                                                                                                                                                                     |
|----------------|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `object_id`    | String                  | Unique identifier for the object. For root-level objects, this is the name. For nested objects, this is the namespace path joined by `$` delimiter (e.g., `ns1$ns2$table_name`) |
| `object_type`  | String                  | Either `"namespace"` or `"table"`                                                                                                                                               |
| `location`     | String (nullable)       | Relative path to the table directory within the root (only for tables)                                                                                                          |
| `metadata`     | String (nullable)       | JSON-encoded metadata/properties (only for namespaces)                                                                                                                          |
| `base_objects` | List<String> (nullable) | Reserved for future use (e.g., view dependencies)                                                                                                                               |

**Primary Key**: The `object_id` column is the [unenforced primary key](https://lance.org/format/table/#unenforced-primary-key) for the manifest table. Implementation of this spec must always enforce the primary key uniqueness using features like Lance merge insert with primary key deduplication.

**Schema Extensibility**: The `__manifest` table schema may include additional columns beyond those listed above. Implementations should preserve unrecognized columns during updates, since extensions may add columns for filtering or other metadata-driven behaviors.

### Root Namespace Properties

In V2, the root namespace is implicit and does not have a row in the `__manifest` table. Instead, root namespace properties are stored in the `__manifest` Lance table's metadata map. Properties are stored as key-value pairs where the key is the property name and the value is a UTF-8 encoded byte array.

For example, implementations may store catalog-level properties in the `__manifest` table's metadata.

### Manifest Table Indexes

The following indexes are created on the manifest table for query performance:

- BTREE index on `object_id` for fast lookups
- Bitmap index on `object_type` for efficient type filtering
- LabelList index on `base_objects` for view dependency queries

### Manifest Table Commits

When adding a new entry in the manifest table, it must atomically check if the table already exists such entry,
as well as if any concurrent operation writes the same entry, and fail the operation accordingly if such conflict exists.

### Manifest Table Directory

In V2, table data is stored in directories with hash-based names in the format `<hash>_<object_id>`.
For example, a table `my_table` in namespace `ns1` would be stored in a directory like `a1b2c3d4_ns1$my_table`.

The hash prefix serves two purposes:

1. **Object store throughput**: Many object stores (e.g., S3) partition data by key prefix. Random hash prefixes distribute tables across partitions for better parallelism.
2. **Conflict prevention**: High entropy prevents issues when a table is created, deleted, and recreated with the same name in quick succession.

The `object_id` suffix ensures uniqueness and aids debugging.

In [compatibility mode](#compatibility-mode), root namespace tables use `<table_name>.lance` naming to remain compatible with V1.


### Table Version Management

V2 optionally supports managed table versioning, where table versions are tracked in the `__manifest` table instead of relying on Lance's native version management. When enabled, the directory catalog acts as an [external manifest store](https://lance.org/format/table/transaction/#external-manifest-store). This feature must be enabled for the entire catalog.

#### Enabling Table Version Management

To enable table version management, store `table_version_management=true` in the `__manifest` Lance table's metadata map. Once enabled, all table version operations must use the namespace APIs (`CreateTableVersion`, `BatchCreateTableVersions`, `DescribeTableVersion`, `ListTableVersions`, `BatchDeleteTableVersions`) instead of the default single-table storage-only version management.

#### Table Version Object ID

Table versions are stored in the `__manifest` table with `object_id` in the format `<table_id>$<version>`. For example:

- Table `users` version 1: `object_id = "users$1"`
- Table `analytics$events` (in namespace `analytics`) version 5: `object_id = "analytics$events$5"`

The `object_type` for table version entries is `"table_version"`.

#### Table Version Metadata Schema

The `metadata` column for table version entries contains a JSON object with the following schema:

| Field           | Type                        | Required | Description                                                                                                                                                                                                                     |
|-----------------|-----------------------------|----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `manifest_path` | string                      | Yes      | Path to the manifest file for this version                                                                                                                                                                                      |
| `manifest_size` | integer                     | No       | Size of the manifest file in bytes                                                                                                                                                                                              |
| `e_tag`         | string                      | No       | ETag for the manifest file, useful for S3 and similar object stores                                                                                                                                                             |
| `metadata`      | object (string -> string)   | No       | Optional key-value pairs of version metadata                                                                                                                                                                                    |
| `naming_scheme` | string                      | No       | The [naming scheme](https://lance.org/format/table/transaction/#manifest-naming-schemes) used for manifest files                                                                                                                |

Example metadata JSON:

```json
{
    "manifest_path": "_versions/9223372036854775806.manifest",
    "manifest_size": 4096,
    "e_tag": "abc123",
    "metadata": {"author": "user1", "description": "Initial schema"},
    "naming_scheme": "V2"
}
```

## Compatibility Mode

By default, the directory catalog operates in compatibility mode, supporting both V1 and V2 tables simultaneously. This allows gradual migration from V1 to V2 without disrupting existing workflows.

In compatibility mode:

1. When checking if a table exists in the root namespace, the implementation first checks the manifest table, then falls back to checking if a `<table_name>.lance` directory exists.
2. When listing tables in the root namespace, results from both the manifest table and directory listing are merged, with manifest entries taking precedence when duplicates exist.
3. When creating tables in the root namespace, the table is registered in the manifest and uses the V1 `<table_name>.lance` naming convention for backward compatibility.
4. If a table in the root namespace is renamed, it transitions to the V2 hash-based path naming.
5. For operations in child namespaces, only V2 behavior is used since V1 does not support nested namespaces.

### Migration from V1 to V2

To fully migrate from V1 to V2, add all existing V1 table directory paths to the manifest table. Once all tables are registered in the manifest, compatibility mode can be disabled to use only V2 behavior.
