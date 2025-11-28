# Lance Directory Namespace

**Lance directory namespace** is a Lance namespace implementation that stores tables in a directory structure
on any local or remote storage system. It supports two modes:

- **V1 (Directory Listing)**: A lightweight, simple 1-level namespace that discovers tables by scanning the directory.
- **V2 (Manifest)**: A more advanced implementation backed by a manifest table (a Lance table) that supports nested namespaces and better performance at scale.

## V1: Directory Listing

V1 is a simple 1-level namespace where each table corresponds to a subdirectory with the format `<table_name>.lance`.
This mode is ideal for getting started quickly with Lance tables.

### Directory Layout

A directory namespace maps to a directory on storage, called the **namespace directory**.
A Lance table corresponds to a subdirectory in the namespace directory that has the format `<table_name>.lance`,
called a **table directory**.

Consider the following example namespace directory layout:

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
    └── table3.lance/
```

This describes a Lance directory namespace with the namespace directory at `/my/dir1/`.
It contains tables `table1`, `table2`, `table3` sitting at table directories
`/my/dir1/table1.lance`, `/my/dir1/table2.lance`, `/my/dir1/table3.lance` respectively.

### Table Existence

In V1, a table exists in a Lance directory namespace if a table directory of the specific name exists.
In object store terms, this means the prefix `<table_name>.lance/` has at least one file in it.

## V2: Manifest 

V2 uses a special `__manifest` table (a Lance table) stored in the namespace directory to track all tables
and namespaces. This provides several advantages over V1:

- **Nested namespaces**: Support for hierarchical namespace organization
- **Better performance**: Table listing queries the manifest table instead of scanning the directory
- **Metadata support**: All operations can be supported, e.g. namespaces can have associated properties/metadata, tables can be renamed.
- **Consistent naming**: Hash-based directory naming prevents conflicts and enables features like table renaming

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

## Configuration

The Lance directory namespace accepts the following configuration properties:

| Property              | Required | Description                                                  | Default | Example                         |
|-----------------------|----------|--------------------------------------------------------------|---------|---------------------------------|
| `root`                | Yes      | The root directory of the namespace where tables are stored  |         | `/my/dir`, `s3://bucket/prefix` |
| `manifest_enabled`    | No       | Enable the manifest table for tracking tables and namespaces | `true`  | `true`, `false`                 |
| `dir_listing_enabled` | No       | Enable directory scanning for table discovery (fallback)     | `true`  | `true`, `false`                 |
| `storage.*`           | No       | Storage-specific configuration options                       |         | `storage.region=us-west-2`      |

### Root Path

There are 3 ways to specify a root path:

1. **URI**: a URI that follows the [RFC 3986 specification](https://datatracker.ietf.org/doc/html/rfc3986), e.g. `s3://my-bucket/prefix`.
2. **Absolute POSIX storage path**: an absolute file path in a POSIX standard storage, e.g. `/my/dir`.
3. **Relative POSIX storage path**: a relative file path in a POSIX standard storage, e.g. `my/dir2`, `./my/dir3`.
   The absolute path of the root should be derived from the current working directory.

### Storage Options

The directory namespace is backed by Lance ObjectStore.
Properties with the `storage.` prefix are passed directly to the underlying Lance ObjectStore
after removing the prefix. For example, `storage.region` becomes `region` when passed to the storage layer.
Please visit [Lance ObjectStore Configurations](https://lance.org/guide/object_store/) for more details.

### Compatibility Mode

`manifest_enabled` and `dir_listing_enabled` are used to control using V1 or V2 scheme.
By default we enable both V1 and V2, this means:

1. When checking if a table exists in root namespace, it first checks if the table exists in the manifest, then checks if the `<table_name>.lance` exists.
2. When listing tables in root namespace, it merges tables from both manifest and directory listing, deduplicating by location and table names, manifest tables taking precedence.
3. When creating tables in root namespaces, it registers them in the manifest and uses V1 `<table_name>.lance` naming for root namespace tables.
4. If a table in root namespace is renamed, it will start to follow the V2 path definition. 
5. For operations in child namespaces, only V2 scheme is used.

### Migration from V1 to V2

A migration should add all the V1 table directory paths to the manifest. 
Once the user is certain there is no table following v1 scheme,
`dir_listing_enabled` can be set to `false` to disable the compatibility mode.