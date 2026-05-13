# Lance Directory Catalog

This document describes how the Lance Directory Catalog implements the Lance Namespace Client operations.

## Background

The Lance Directory Catalog is a storage-native catalog that stores tables in a directory structure on any local or remote storage system. For details on the catalog design including V1 (directory listing), V2 (manifest), and compatibility mode, see the [Lance Directory Catalog](../../catalog/dir/index.md) specification.

## Implementation Configuration Properties

The Lance Directory Catalog implementation accepts the following configuration properties:

The **root** property is required and specifies the root directory of the catalog where tables are stored. This can be a local path like `/my/dir` or a cloud storage URI like `s3://bucket/prefix`.

The **manifest_enabled** property controls whether the manifest table is used for tracking tables and namespaces (V2). Defaults to `true`.

The **dir_listing_enabled** property controls whether directory scanning is used for table discovery (V1). Defaults to `true`.

By default, both properties are enabled, which means the implementation operates in [Compatibility Mode](../../catalog/dir/index.md#compatibility-mode).

Properties with the **storage.** prefix are passed directly to the underlying Lance ObjectStore after removing the prefix. For example, `storage.region` becomes `region` when passed to the storage layer.

## Object Mapping

### Namespace

The **root namespace** is the root directory specified by the `root` configuration property. This is the base path where all tables are stored.

A **child namespace** is a logical container tracked in the manifest table. Child namespaces are only supported in V2; V1 treats the root directory as a flat namespace containing only tables. Child namespaces do not correspond to physical subdirectories.

The **namespace identifier** is a list of strings representing the namespace path. For example, a namespace `["prod", "analytics"]` is serialized to `prod$analytics` when stored in the manifest table's `object_id` column.

**Namespace properties** are stored as JSON in the `metadata` column of the manifest table. This is only available in V2.

### Table

A **table** is a subdirectory containing Lance table data. The directory must contain valid Lance format files including the `_versions/` directory with version manifests.

The **table identifier** is a list of strings representing the namespace path followed by the table name. For example, a table `["prod", "analytics", "users"]` represents a table named `users` in namespace `["prod", "analytics"]`. This is serialized to `prod$analytics$users` when stored in the manifest table's `object_id` column.

The **table location** depends on the mode and namespace level:

- In V1 (root namespace only), tables are stored as `<table_name>.lance` directories
- In V2 with `dir_listing_enabled=true` and an empty namespace (root level), tables use the `<table_name>.lance` naming convention for backward compatibility
- In V2 for child namespaces, or when `dir_listing_enabled=false`, tables are stored as `<hash>_<object_id>` directories where hash provides entropy for object store throughput

**Table properties** are stored in Lance table metadata and can be accessed via the Lance SDK.

## Lance Table Identification

In a Directory Catalog, a Lance table is identified differently depending on the mode:

In **V1**, a Lance table is any directory with the `.lance` suffix (e.g., `users.lance/`). The directory must contain valid Lance table data to be usable. Only single-level table identifiers (e.g., `["users"]`) are supported in this mode.

In **V2**, a Lance table is identified by a row in the manifest table with `object_type="table"`. The row's `location` field points to the Lance table directory. Multi-level table identifiers (e.g., `["prod", "analytics", "users"]`) are supported.

A valid Lance table directory must be non-empty.

## Basic Operations

### CreateNamespace

This operation is only supported in V2. V1 does not support explicit namespace creation since it uses a flat directory structure.

The implementation creates a new namespace using a merge-insert operation on the manifest table:

1. Validate the parent namespace exists (if not creating at root level)
2. Merge-insert a new row into the manifest table with:
     - `object_id` set to the namespace identifier (e.g., `prod$analytics`)
     - `object_type` set to `"namespace"`
     - `metadata` containing the namespace properties as JSON
     - `created_at` set to the current timestamp

   Primary-key deduplication on `object_id` ensures no duplicate rows are inserted. If a namespace with the same identifier already exists, the operation fails.

**Error Handling:**

If a namespace with the same identifier already exists, return error code `2` (NamespaceAlreadyExists).

If the parent namespace does not exist (for nested namespaces), return error code `1` (NamespaceNotFound).

If the identifier format is invalid, return error code `13` (InvalidInput).

### ListNamespaces

This operation lists child namespaces within a parent namespace.

In **V1**, this operation returns an empty list since namespaces are not supported.

In **V2**, the implementation queries the manifest table:

1. Query for rows where `object_type = "namespace"`
2. Filter to rows where `object_id` starts with the parent namespace prefix
3. Further filter to rows where `object_id` has exactly one more level than the parent
4. Return the list of namespace names (the last component of each identifier)

**Error Handling:**

If the parent namespace does not exist (V2 only), return error code `1` (NamespaceNotFound).

### DescribeNamespace

This operation is only supported in V2 and returns namespace metadata.

The implementation:

1. Query the manifest table for the row with the matching `object_id`
2. Parse the `metadata` column as JSON
3. Return the namespace name and properties

**Error Handling:**

If the namespace does not exist, return error code `1` (NamespaceNotFound).

### DropNamespace

This operation is only supported in V2 and removes a namespace.

The implementation:

1. Check that the namespace exists in the manifest table
2. Query for any child namespaces or tables with identifiers starting with this namespace's prefix
3. If any children exist, the operation fails
4. Delete the namespace row from the manifest table using the `object_id` primary key

**Error Handling:**

If the namespace does not exist, return error code `1` (NamespaceNotFound).

If the namespace contains tables or child namespaces, return error code `3` (NamespaceNotEmpty).

### DeclareTable

This operation declares a new Lance table, reserving the table name and location without creating actual data files.

The implementation:

1. Validate the parent namespace exists (in V2)
2. Determine the table location:
     - In V1: `<root>/<table_name>.lance`
     - In V2 with `dir_listing_enabled=true` at root level: `<root>/<table_name>.lance`
     - In V2 for child namespaces or with `dir_listing_enabled=false`: `<root>/<hash>_<object_id>/`
3. Create a `.lance-reserved` file at the location to mark the table's existence
4. In V2, merge-insert a row into the manifest table with:
     - `object_id` set to the table identifier
     - `object_type` set to `"table"`
     - `location` set to the table directory path

   Primary-key deduplication on `object_id` ensures no duplicate rows are inserted. If a table with the same identifier already exists, the operation fails.

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If a table with the same identifier already exists, return error code `5` (TableAlreadyExists).

If there is a concurrent creation attempt, return error code `14` (ConcurrentModification).

### ListTables

This operation lists tables within a namespace.

In **V1**:

1. List all entries in the root directory
2. Filter to directories matching the `*.lance` pattern
3. Return the table names (directory names without the `.lance` suffix)

In **V2**:

1. Query the manifest table for rows where `object_type = "table"`
2. Filter to rows where `object_id` starts with the namespace prefix
3. Further filter to rows where `object_id` has exactly one more level than the namespace
4. Return the list of table names

When **both V1 and V2 are enabled** (the default [Compatibility Mode](../../catalog/dir/index.md#compatibility-mode)),
the implementation performs both queries and merges results, with manifest entries taking precedence when duplicates exist.

**Error Handling:**

If the namespace does not exist (V2 only), return error code `1` (NamespaceNotFound).

### DescribeTable

This operation returns table metadata including schema, version, and properties.

The implementation:

1. Locate the table:
     - In V1, check for the `<table_name>.lance` directory
     - In V2, query the manifest table for the table location
     - When both V1 and V2 are enabled (the default [Compatibility Mode](../../catalog/dir/index.md#compatibility-mode)),
       first check the manifest table, then fall back to checking the `.lance` directory
2. Open the Lance table using the Lance SDK
3. Read the table metadata and return:
     - `name`: The table name
     - `schema`: The Arrow schema of the table
     - `version`: The current version number
     - `location`: The table directory path

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If the table does not exist, return error code `4` (TableNotFound).

If a specific version is requested and does not exist, return error code `11` (TableVersionNotFound).

### DeregisterTable

This operation deregisters a table from the catalog while preserving its data on storage. The table files remain at their storage location and can be re-registered later using RegisterTable.

In **V1**:

1. Locate the table by checking for the `<table_name>.lance` directory
2. Verify the table exists and is not already deregistered
3. Create a `.lance-deregistered` marker file inside the table directory
4. Return the table location for reference

The marker file approach ensures that:
- Table data remains intact at its original location
- The table is excluded from `ListTables` results
- The table returns `TableNotFound` for `DescribeTable` and `TableExists` operations
- The table can be re-registered by removing the marker file and calling `RegisterTable`
- `DropTable` still works on deregistered tables (removes both data and marker file)

In **V2**:

1. Locate the table by querying the manifest table for the table location
2. Remove the table row from the manifest table using the `object_id` primary key
3. Keep the table files at the storage location
4. Return the table location and properties for reference

When **both V1 and V2 are enabled** (the default [Compatibility Mode](../../catalog/dir/index.md#compatibility-mode)),
first check the manifest table, then fall back to checking the `.lance` directory.
If found in manifest, follow V2 behavior; otherwise follow V1 behavior.

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If the table does not exist or is already deregistered, return error code `4` (TableNotFound).

## Additional Operations

### DropTable

This operation removes a table and its data.

In **V1**:

1. Locate the table by checking for the `<table_name>.lance` directory
2. Delete the table directory and all its contents from storage
3. If deletion fails midway (directory is still non-empty), the drop has failed and should be retried

In **V2**:

1. Locate the table by querying the manifest table for the table location
2. Remove the table row from the manifest table using the `object_id` primary key
3. Delete the table directory and all its contents from storage
   (failure here does not affect the success of the drop since the table is no longer reachable)

When **both V1 and V2 are enabled** (the default [Compatibility Mode](../../catalog/dir/index.md#compatibility-mode)),
first check the manifest table, then fall back to checking the `.lance` directory.
If found in manifest, follow V2 behavior; otherwise follow V1 behavior.

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If the table does not exist, return error code `4` (TableNotFound).

If there is a file system permission error, return error code `15` (PermissionDenied).

If there is an unexpected I/O error, return error code `18` (Internal).

### CreateTableVersion

This operation creates a new version entry for a table. It supports `put_if_not_exists` semantics.

When **table version management is not enabled**:

1. Resolve the table location
2. Parse the staging manifest path from the request
3. Determine the final manifest path based on the naming scheme (V1 or V2)
4. Copy the staging manifest to the final path in the `_versions/` directory using `put_if_not_exists` semantics
5. Delete the staging manifest file
6. Return the created version info including the final manifest path

When **table version management is enabled** (V2 with `table_version_management=true` in `__manifest` metadata), the directory catalog acts as an external manifest store. The commit process follows these steps:

1. **Stage manifest in object storage**: The caller writes the new manifest to a staging path (e.g., `{table_location}/_versions/{version}.manifest-{uuid}`). This staged manifest is not yet visible to readers.
2. **Atomically commit to manifest table**: Merge-insert a new row into the `__manifest` table with:
    - `object_id` set to `<table_id>$<version>` (e.g., `users$1` or `ns1$users$1`)
    - `object_type` set to `"table_version"`
    - `metadata` containing the JSON-encoded version metadata including the staging manifest path

   Primary-key deduplication on `object_id` ensures no duplicate rows are inserted. The commit is effectively complete after this step. If this fails, another writer has already committed that version.
3. **Finalize in object storage**: Copy the staged manifest to the standard location (`{table_location}/_versions/{version}.manifest`). This makes it discoverable by readers that do not use the manifest table.
4. **Update manifest table pointer**: Update the `metadata` in the manifest table row to point to the finalized manifest path, synchronizing both systems.

**Error Handling:**

If the table does not exist, return error code `4` (TableNotFound).

If the version already exists, return error code `12` (TableVersionAlreadyExists).

If there is a concurrent creation attempt, return error code `14` (ConcurrentModification).

### BatchCreateTableVersions

This operation atomically creates version entries for multiple tables.

When **table version management is not enabled**, this operation iterates through each entry and calls `CreateTableVersion` for each one. Atomicity is not guaranteed.

When **table version management is enabled**, the batch commit process follows these steps:

1. **Stage manifests in object storage**: For each entry, the caller writes the new manifest to a staging path (e.g., `{table_location}/_versions/{version}.manifest-{uuid}`).
2. **Atomically commit to manifest table**: Merge-insert all version rows into the `__manifest` table in a single atomic commit, each with:
    - `object_id` set to `<table_id>$<version>`
    - `object_type` set to `"table_version"`
    - `metadata` containing the JSON-encoded version metadata including the staging manifest path

   Primary-key deduplication on `object_id` ensures no duplicate rows are inserted. The commit is effectively complete after this step. If any version already exists, the entire batch fails.
3. **Finalize in object storage**: For each entry, copy the staged manifest to the standard location.
4. **Update manifest table pointers**: Update the `metadata` in each manifest table row to point to the finalized manifest paths.

**Error Handling:**

If any table does not exist, return error code `4` (TableNotFound).

If any version already exists, return error code `12` (TableVersionAlreadyExists).

If there is a concurrent modification, return error code `14` (ConcurrentModification).

### ListTableVersions

This operation lists version entries for a table.

When **table version management is not enabled**:

1. Resolve the table location
2. List all files in the `_versions/` directory
3. Parse version numbers from manifest filenames (handling both V1 and V2 naming schemes)
4. Extract metadata from file attributes (size, e_tag, last_modified timestamp)
5. Sort results by version number (descending if `descending=true`)
6. Apply pagination using `page_token` and `limit`

When **table version management is enabled**:

1. Query the manifest table for rows where:
    - `object_type = "table_version"`
    - `object_id` starts with `<table_id>$`
2. Parse the version number from each `object_id`
3. Parse the `metadata` column as JSON to extract version details
4. Sort results by version number (descending if `descending=true`)
5. Apply pagination using `page_token` and `limit`

**Error Handling:**

If the table does not exist, return error code `4` (TableNotFound).

### DescribeTableVersion

This operation retrieves details for a specific table version.

When **table version management is not enabled**:

1. Resolve the table location
2. Open the Lance dataset at the specified version
3. Read the manifest file to extract version metadata
4. Return the version information including manifest_path, manifest_size, e_tag, timestamp_millis, and metadata

When **table version management is enabled**, the read process validates and synchronizes the manifest:

1. **Query manifest table**: Retrieve the manifest path for the requested version from the row with `object_id = <table_id>$<version>`. If the path matches the expected path based on the naming scheme, synchronization is complete.
2. **Synchronize to object storage**: If the manifest path does not match the expected path based on the naming scheme (i.e., it is a staging path), copy the staged manifest to its final location (`{table_location}/_versions/{version}.manifest`). This is an idempotent operation.
3. **Update manifest table**: Update the `metadata` in the manifest table row to reflect the finalized path for future readers.
4. **Return version information**: Return the version information with the finalized manifest path, or error if synchronization fails.

**Error Handling:**

If the table does not exist, return error code `4` (TableNotFound).

If the version does not exist, return error code `11` (TableVersionNotFound).

### BatchDeleteTableVersions

This operation deletes multiple version entries for a table.

When **table version management is not enabled**:

1. Resolve the table location
2. Delete the manifest files in the `_versions/` directory for each specified version
3. Return the count of deleted versions

When **table version management is enabled**:

1. Delete the manifest files in the `_versions/` directory for each specified version
2. Delete rows from the manifest table using the `object_id` primary key for each specified version
3. Return the count of deleted versions

**Error Handling:**

If the table does not exist, return error code `4` (TableNotFound).

If any specified version does not exist, the operation may either skip it silently or return error code `11` (TableVersionNotFound), depending on the `ignore_missing` parameter.
