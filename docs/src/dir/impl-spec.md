# Lance Directory Namespace Implementation Spec

This document describes how the Lance Directory Namespace implements the Lance Namespace client spec.

## Background

The Lance Directory Namespace is a catalog that stores tables in a directory structure on any local or remote storage system. For details on the catalog design including V1 (directory listing), V2 (manifest), and compatibility mode, see the [Directory Namespace Catalog Spec](catalog-spec.md).

## Namespace Implementation Configuration Properties

The Lance directory namespace implementation accepts the following configuration properties:

The **root** property is required and specifies the root directory of the namespace where tables are stored. This can be a local path like `/my/dir` or a cloud storage URI like `s3://bucket/prefix`.

The **manifest_enabled** property controls whether the manifest table is used for tracking tables and namespaces (V2). Defaults to `true`.

The **dir_listing_enabled** property controls whether directory scanning is used for table discovery (V1). Defaults to `true`.

By default, both properties are enabled, which means the implementation operates in [Compatibility Mode](catalog-spec.md#compatibility-mode).

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

In a Directory Namespace, a Lance table is identified differently depending on the mode:

In **V1**, a Lance table is any directory with the `.lance` suffix (e.g., `users.lance/`). The directory must contain valid Lance table data to be usable. Only single-level table identifiers (e.g., `["users"]`) are supported in this mode.

In **V2**, a Lance table is identified by a row in the manifest table with `object_type="table"`. The row's `location` field points to the Lance table directory. Multi-level table identifiers (e.g., `["prod", "analytics", "users"]`) are supported.

A valid Lance table directory must be non-empty.

## Basic Operations

### CreateNamespace

This operation is only supported in V2. V1 does not support explicit namespace creation since it uses a flat directory structure.

The implementation creates a new namespace by inserting a row into the manifest table:

1. Validate the parent namespace exists (if not creating at root level)
2. Check that no namespace with the same identifier already exists
3. Insert a new row into the manifest table with:
     - `object_id` set to the namespace identifier (e.g., `prod$analytics`)
     - `object_type` set to `"namespace"`
     - `metadata` containing the namespace properties as JSON
     - `created_at` set to the current timestamp

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
4. Delete the namespace row from the manifest table

**Error Handling:**

If the namespace does not exist, return error code `1` (NamespaceNotFound).

If the namespace contains tables or child namespaces, return error code `3` (NamespaceNotEmpty).

### DeclareTable

This operation declares a new Lance table, reserving the table name and location without creating actual data files.

The implementation:

1. Validate the parent namespace exists (in V2)
2. Check that no table with the same identifier already exists
3. Determine the table location:
     - In V1: `<root>/<table_name>.lance`
     - In V2 with `dir_listing_enabled=true` at root level: `<root>/<table_name>.lance`
     - In V2 for child namespaces or with `dir_listing_enabled=false`: `<root>/<hash>_<object_id>/`
4. Create a `.lance-reserved` file at the location to mark the table's existence
5. In V2, insert a row into the manifest table with:
     - `object_id` set to the table identifier
     - `object_type` set to `"table"`
     - `location` set to the table directory path

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

When **both V1 and V2 are enabled** (the default [Compatibility Mode](catalog-spec.md#compatibility-mode)), 
the implementation performs both queries and merges results, with manifest entries taking precedence when duplicates exist.

**Error Handling:**

If the namespace does not exist (V2 only), return error code `1` (NamespaceNotFound).

### DescribeTable

This operation returns table metadata including schema, version, and properties.

The implementation:

1. Locate the table:
     - In V1, check for the `<table_name>.lance` directory
     - In V2, query the manifest table for the table location
     - When both V1 and V2 are enabled (the default [Compatibility Mode](catalog-spec.md#compatibility-mode)), 
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

### DropTable

This operation removes a table and its data.

In **V1**:

1. Locate the table by checking for the `<table_name>.lance` directory
2. Delete the table directory and all its contents from storage
3. If deletion fails midway (directory is still non-empty), the drop has failed and should be retried

In **V2**:

1. Locate the table by querying the manifest table for the table location
2. Remove the table row from the manifest table first
3. Delete the table directory and all its contents from storage 
   (failure here does not affect the success of the drop since the table is no longer reachable)

When **both V1 and V2 are enabled** (the default [Compatibility Mode](catalog-spec.md#compatibility-mode)), 
first check the manifest table, then fall back to checking the `.lance` directory. 
If found in manifest, follow V2 behavior; otherwise follow V1 behavior.

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If the table does not exist, return error code `4` (TableNotFound).

If there is a file system permission error, return error code `15` (PermissionDenied).

If there is an unexpected I/O error, return error code `18` (Internal).

### DeregisterTable

This operation deregisters a table from the namespace while preserving its data on storage. The table files remain at their storage location and can be re-registered later using RegisterTable.

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
2. Remove the table row from the manifest table
3. Keep the table files at the storage location
4. Return the table location and properties for reference

When **both V1 and V2 are enabled** (the default [Compatibility Mode](catalog-spec.md#compatibility-mode)),
first check the manifest table, then fall back to checking the `.lance` directory.
If found in manifest, follow V2 behavior; otherwise follow V1 behavior.

**Error Handling:**

If the parent namespace does not exist, return error code `1` (NamespaceNotFound).

If the table does not exist or is already deregistered, return error code `4` (TableNotFound).
