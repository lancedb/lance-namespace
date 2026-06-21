/**
 * @file operations.h
 * @brief C API operation functions for Lance Namespace.
 *
 * This header declares all namespace operation functions available through
 * the C API. Each function takes a namespace handle, a request struct,
 * an output response struct (or output parameter), and an error struct.
 *
 * All functions return lance_namespace_status_t:
 * - LANCE_NAMESPACE_STATUS_OK on success
 * - LANCE_NAMESPACE_STATUS_UNSUPPORTED if the operation is not supported
 * - LANCE_NAMESPACE_STATUS_NOT_FOUND if a resource was not found
 * - LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if a resource already exists
 * - LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT for invalid parameters
 * - LANCE_NAMESPACE_STATUS_INTERNAL for unexpected errors
 *
 * On failure, the error struct is populated with a code and message.
 * Callers must call lance_namespace_error_free() on the error struct.
 */

#ifndef LANCE_NAMESPACE_OPERATIONS_H
#define LANCE_NAMESPACE_OPERATIONS_H

#include "lance_namespace.h"
#include "models.h"

#ifdef __cplusplus
extern "C" {
#endif

/* ============================================================================
 * Namespace Operations
 * ==========================================================================*/

/**
 * List child namespaces of a parent namespace.
 *
 * @param handle       The namespace handle.
 * @param request      List namespaces request with parent ID and pagination.
 * @param response     Output: list of child namespace names.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_namespaces(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_namespaces_request_t *request,
    lance_namespace_list_namespaces_response_t *response,
    lance_namespace_error_t *error);

/**
 * Describe a namespace and return its properties.
 *
 * @param handle       The namespace handle.
 * @param request      Describe namespace request with the namespace ID.
 * @param response     Output: namespace details including properties.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_describe_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_namespace_request_t *request,
    lance_namespace_describe_namespace_response_t *response,
    lance_namespace_error_t *error);

/**
 * Create a new namespace.
 *
 * @param handle       The namespace handle.
 * @param request      Create namespace request with ID and optional properties.
 * @param response     Output: created namespace information.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if it already exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_namespace_request_t *request,
    lance_namespace_create_namespace_response_t *response,
    lance_namespace_error_t *error);

/**
 * Drop a namespace from its parent.
 *
 * @param handle       The namespace handle.
 * @param request      Drop namespace request with the namespace ID.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_NOT_FOUND if it does not exist.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_drop_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_namespace_request_t *request,
    lance_namespace_drop_namespace_response_t *response,
    lance_namespace_error_t *error);

/**
 * Check if a namespace exists.
 *
 * Behaves like describe_namespace but returns no response body.
 *
 * @param handle       The namespace handle.
 * @param request      Namespace exists request with the namespace ID.
 * @param error        Output: error details if namespace does not exist.
 * @return LANCE_NAMESPACE_STATUS_OK if exists,
 *         LANCE_NAMESPACE_STATUS_NOT_FOUND otherwise.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_namespace_exists(
    lance_namespace_handle_t *handle,
    const lance_namespace_namespace_exists_request_t *request,
    lance_namespace_error_t *error);

/**
 * List tables in a namespace.
 *
 * @param handle       The namespace handle.
 * @param request      List tables request with parent namespace ID and pagination.
 * @param response     Output: list of table names.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_tables_request_t *request,
    lance_namespace_list_tables_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Operations
 * ==========================================================================*/

/**
 * Describe a table and return its metadata.
 *
 * @param handle       The namespace handle.
 * @param request      Describe table request with the table ID.
 * @param response     Output: table details (location, version, properties).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_describe_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_request_t *request,
    lance_namespace_describe_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Register an existing table at a storage location.
 *
 * @param handle       The namespace handle.
 * @param request      Register table request with ID and storage location.
 * @param response     Output: registered table information.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if table already exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_register_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_register_table_request_t *request,
    lance_namespace_register_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Check if a table exists.
 *
 * Behaves like describe_table but returns no response body.
 *
 * @param handle       The namespace handle.
 * @param request      Table exists request with the table ID.
 * @param error        Output: error details if table does not exist.
 * @return LANCE_NAMESPACE_STATUS_OK if exists,
 *         LANCE_NAMESPACE_STATUS_NOT_FOUND otherwise.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_table_exists(
    lance_namespace_handle_t *handle,
    const lance_namespace_table_exists_request_t *request,
    lance_namespace_error_t *error);

/**
 * Drop a table and delete its data.
 *
 * @param handle       The namespace handle.
 * @param request      Drop table request with the table ID.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_drop_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_table_request_t *request,
    lance_namespace_drop_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Deregister a table without deleting underlying data.
 *
 * @param handle       The namespace handle.
 * @param request      Deregister table request with the table ID.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_deregister_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_deregister_table_request_t *request,
    lance_namespace_deregister_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Count the number of rows in a table.
 *
 * @param handle       The namespace handle.
 * @param request      Count table rows request with table ID and optional version.
 * @param out_count    Output: the row count.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_count_table_rows(
    lance_namespace_handle_t *handle,
    const lance_namespace_count_table_rows_request_t *request,
    int64_t *out_count,
    lance_namespace_error_t *error);

/**
 * Create a new table with data from an Arrow IPC stream.
 *
 * The schema of the Arrow IPC stream is used as the table schema.
 * If the stream is empty, an empty table is created.
 *
 * @param handle       The namespace handle.
 * @param request      Create table request with ID, mode, properties, and data.
 * @param response     Output: created table info (location, version).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if table already exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_request_t *request,
    lance_namespace_create_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Declare a table (metadata-only operation, does not touch storage).
 *
 * Records table existence and sets up access control without creating
 * actual Lance data files.
 *
 * @param handle       The namespace handle.
 * @param request      Declare table request with ID and optional properties.
 * @param response     Output: declared table information.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_declare_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_declare_table_request_t *request,
    lance_namespace_declare_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Insert records into a table via Arrow IPC stream.
 *
 * For declared-but-not-created tables, this creates the table with the
 * provided data.
 *
 * @param handle       The namespace handle.
 * @param request      Insert request with table ID, mode, branch, and data.
 * @param response     Output: number of rows inserted.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_insert_into_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_insert_into_table_request_t *request,
    lance_namespace_insert_into_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Merge insert (upsert) records into a table via Arrow IPC stream.
 *
 * Updates existing rows based on a matching column and inserts new rows
 * that don't match.
 *
 * @param handle       The namespace handle.
 * @param request      Merge insert request with match column and conditions.
 * @param response     Output: counts for inserted, updated, deleted rows.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_merge_insert_into_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_merge_insert_into_table_request_t *request,
    lance_namespace_merge_insert_into_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Update existing rows in a table.
 *
 * @param handle       The namespace handle.
 * @param request      Update request with table ID and update expressions.
 * @param response     Output: number of rows updated.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_update_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_request_t *request,
    lance_namespace_update_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Delete rows from a table matching a predicate.
 *
 * @param handle       The namespace handle.
 * @param request      Delete request with table ID and predicate.
 * @param response     Output: number of rows deleted.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_delete_from_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_from_table_request_t *request,
    lance_namespace_delete_from_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Query a table with vector search, full text search, and SQL filtering.
 *
 * Returns results in Arrow IPC file or stream format.
 *
 * @param handle       The namespace handle.
 * @param request      Query request with vector, filter, and parameters.
 * @param out_data     Output: Arrow IPC stream buffer (must be freed).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_query_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_query_table_request_t *request,
    lance_namespace_buffer_t *out_data,
    lance_namespace_error_t *error);

/**
 * List all tables across all namespaces.
 *
 * @param handle       The namespace handle.
 * @param request      List all tables request with pagination.
 * @param response     Output: list of all tables.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_all_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_all_tables_request_t *request,
    lance_namespace_list_tables_response_t *response,
    lance_namespace_error_t *error);

/**
 * Rename a table to a new name.
 *
 * @param handle       The namespace handle.
 * @param request      Rename table request with table ID and new name.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_rename_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_rename_table_request_t *request,
    lance_namespace_rename_table_response_t *response,
    lance_namespace_error_t *error);

/**
 * Restore a table to a specific version.
 *
 * @param handle       The namespace handle.
 * @param request      Restore table request with table ID and target version.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_restore_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_restore_table_request_t *request,
    lance_namespace_restore_table_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Index Operations
 * ==========================================================================*/

/**
 * Create an index on a table for faster search.
 *
 * Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and
 * scalar indexes (BTREE, BITMAP, FTS, etc.). Creation is asynchronous.
 *
 * @param handle       The namespace handle.
 * @param request      Create index request with table ID, name, type, config.
 * @param response     Output: created index UUID.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_index_request_t *request,
    lance_namespace_create_table_index_response_t *response,
    lance_namespace_error_t *error);

/**
 * Create a scalar index on a table for faster filtering.
 *
 * Supports BTREE, BITMAP, LABEL_LIST, FTS, etc. This is an alias for
 * create_table_index specifically for scalar indexes.
 *
 * @param handle       The namespace handle.
 * @param request      Create index request with table ID, name, type, config.
 * @param response     Output: created scalar index UUID.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table_scalar_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_index_request_t *request,
    lance_namespace_create_table_scalar_index_response_t *response,
    lance_namespace_error_t *error);

/**
 * List all indices on a table.
 *
 * @param handle       The namespace handle.
 * @param request      List indices request with table ID and pagination.
 * @param response     Output: list of index info (name, type, status, UUID).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_table_indices(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_indices_request_t *request,
    lance_namespace_list_table_indices_response_t *response,
    lance_namespace_error_t *error);

/**
 * Get statistics for a specific index on a table.
 *
 * @param handle       The namespace handle.
 * @param request      Describe index stats request with table ID and index name.
 * @param response     Output: index statistics (type, distance, row count).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_describe_table_index_stats(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_index_stats_request_t *request,
    lance_namespace_describe_table_index_stats_response_t *response,
    lance_namespace_error_t *error);

/**
 * Drop an index from a table.
 *
 * @param handle       The namespace handle.
 * @param request      Drop index request with table ID and index name.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_drop_table_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_table_index_request_t *request,
    lance_namespace_drop_table_index_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Version Operations
 * ==========================================================================*/

/**
 * List all versions (commits) of a table.
 *
 * @param handle       The namespace handle.
 * @param request      List versions request with table ID and pagination.
 * @param response     Output: list of version info (version, manifest, timestamp).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_versions_request_t *request,
    lance_namespace_list_table_versions_response_t *response,
    lance_namespace_error_t *error);

/**
 * Create a new version entry for a table.
 *
 * Supports put_if_not_exists semantics: fails if version already exists.
 *
 * @param handle       The namespace handle.
 * @param request      Create version request with table ID, version, manifest path.
 * @param response     Output: created version information.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if version exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_version_request_t *request,
    lance_namespace_create_table_version_response_t *response,
    lance_namespace_error_t *error);

/**
 * Describe a specific table version.
 *
 * @param handle       The namespace handle.
 * @param request      Describe version request with table ID and version number.
 * @param response     Output: version details (manifest path, timestamp, operation).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_describe_table_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_version_request_t *request,
    lance_namespace_describe_table_version_response_t *response,
    lance_namespace_error_t *error);

/**
 * Delete table version metadata records.
 *
 * Deletes version tracking records, NOT the actual table data. Supports
 * deleting ranges of versions for efficient bulk cleanup.
 *
 * @param handle       The namespace handle.
 * @param request      Batch delete request with table ID and version range.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_batch_delete_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_delete_table_versions_request_t *request,
    lance_namespace_batch_delete_table_versions_response_t *response,
    lance_namespace_error_t *error);

/**
 * Atomically create version entries for multiple tables.
 *
 * Either all versions are created or none are. Each entry supports
 * put_if_not_exists semantics.
 *
 * @param handle       The namespace handle.
 * @param request      Batch create request with multiple version entries.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_batch_create_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_create_table_versions_request_t *request,
    lance_namespace_batch_create_table_versions_response_t *response,
    lance_namespace_error_t *error);

/**
 * Atomically commit a batch of mixed table operations.
 *
 * Supports DeclareTable, CreateTableVersion, DeleteTableVersions, and
 * DeregisterTable operations within a single atomic transaction.
 *
 * @param handle       The namespace handle.
 * @param request      Batch commit request with multiple operations.
 * @param response     Output: per-operation results.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_batch_commit_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_commit_tables_request_t *request,
    lance_namespace_batch_commit_tables_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Schema Operations
 * ==========================================================================*/

/**
 * Add new columns to a table schema.
 *
 * @param handle       The namespace handle.
 * @param request      Add columns request with table ID and column definitions.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_alter_table_add_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_add_columns_request_t *request,
    lance_namespace_alter_table_add_columns_response_t *response,
    lance_namespace_error_t *error);

/**
 * Modify existing columns (rename, change types, set nullable).
 *
 * @param handle       The namespace handle.
 * @param request      Alter columns request with table ID and modifications.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_alter_table_alter_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_alter_columns_request_t *request,
    lance_namespace_alter_table_alter_columns_response_t *response,
    lance_namespace_error_t *error);

/**
 * Remove columns from a table.
 *
 * @param handle       The namespace handle.
 * @param request      Drop columns request with table ID and column names.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_alter_table_drop_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_drop_columns_request_t *request,
    lance_namespace_alter_table_drop_columns_response_t *response,
    lance_namespace_error_t *error);

/**
 * Trigger an async backfill job for computed columns.
 *
 * @param handle       The namespace handle.
 * @param request      Backfill columns request with table ID and column names.
 * @param response     Output: async job ID.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_alter_table_backfill_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_backfill_columns_request_t *request,
    lance_namespace_alter_table_backfill_columns_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Metadata Operations
 * ==========================================================================*/

/**
 * Replace schema metadata with provided key-value pairs.
 *
 * @param handle       The namespace handle.
 * @param request      Update schema metadata request with table ID and metadata.
 * @param response     Output: the updated metadata.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_update_table_schema_metadata(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_schema_metadata_request_t *request,
    lance_namespace_update_table_schema_metadata_response_t *response,
    lance_namespace_error_t *error);

/**
 * Update per-field (column) metadata.
 *
 * Each entry targets a field by path and merges key-value pairs into
 * existing metadata, or replaces when the replace flag is set.
 *
 * @param handle       The namespace handle.
 * @param request      Update field metadata request with entries.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_update_field_metadata(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_field_metadata_request_t *request,
    lance_namespace_update_field_metadata_response_t *response,
    lance_namespace_error_t *error);

/**
 * Get table statistics (row counts, version counts, column stats).
 *
 * @param handle       The namespace handle.
 * @param request      Get table stats request with the table ID.
 * @param response     Output: table statistics.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_get_table_stats(
    lance_namespace_handle_t *handle,
    const lance_namespace_get_table_stats_request_t *request,
    lance_namespace_get_table_stats_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Query Plan Operations
 * ==========================================================================*/

/**
 * Get a human-readable explanation of a query execution plan.
 *
 * @param handle       The namespace handle.
 * @param request      Explain query plan request with table ID and query.
 * @param out_plan     Output: allocated plan string (caller must free).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_explain_table_query_plan(
    lance_namespace_handle_t *handle,
    const lance_namespace_explain_table_query_plan_request_t *request,
    char **out_plan,
    lance_namespace_error_t *error);

/**
 * Analyze a query execution plan with actual execution statistics.
 *
 * @param handle       The namespace handle.
 * @param request      Analyze query plan request with table ID and query.
 * @param out_analysis Output: allocated analysis string (caller must free).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_analyze_table_query_plan(
    lance_namespace_handle_t *handle,
    const lance_namespace_analyze_table_query_plan_request_t *request,
    char **out_analysis,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Tag Operations
 * ==========================================================================*/

/**
 * List all tags for a table.
 *
 * @param handle       The namespace handle.
 * @param request      List tags request with table ID and pagination.
 * @param response     Output: list of tags (name, version).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_table_tags(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_tags_request_t *request,
    lance_namespace_list_table_tags_response_t *response,
    lance_namespace_error_t *error);

/**
 * Get the version number that a specific tag points to.
 *
 * @param handle       The namespace handle.
 * @param request      Get tag version request with table ID and tag name.
 * @param response     Output: the version number.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_get_table_tag_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_get_table_tag_version_request_t *request,
    lance_namespace_get_table_tag_version_response_t *response,
    lance_namespace_error_t *error);

/**
 * Create a new tag pointing to a specific version.
 *
 * @param handle       The namespace handle.
 * @param request      Create tag request with table ID, tag name, version.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if tag exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_tag_request_t *request,
    lance_namespace_create_table_tag_response_t *response,
    lance_namespace_error_t *error);

/**
 * Delete a tag from a table.
 *
 * @param handle       The namespace handle.
 * @param request      Delete tag request with table ID and tag name.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_NOT_FOUND if tag does not exist.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_delete_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_table_tag_request_t *request,
    lance_namespace_delete_table_tag_response_t *response,
    lance_namespace_error_t *error);

/**
 * Update a tag to point to a different version.
 *
 * @param handle       The namespace handle.
 * @param request      Update tag request with table ID, tag name, new version.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_update_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_tag_request_t *request,
    lance_namespace_update_table_tag_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Table Branch Operations
 * ==========================================================================*/

/**
 * Create a new branch for a table.
 *
 * Starts from a source ref (another branch and/or version), defaulting
 * to the latest version of the main branch.
 *
 * @param handle       The namespace handle.
 * @param request      Create branch request with table ID, branch name,
 *                     and optional source version/branch.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_table_branch(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_branch_request_t *request,
    lance_namespace_create_table_branch_response_t *response,
    lance_namespace_error_t *error);

/**
 * List all branches for a table.
 *
 * @param handle       The namespace handle.
 * @param request      List branches request with table ID and pagination.
 * @param response     Output: list of branches (name, version).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_list_table_branches(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_branches_request_t *request,
    lance_namespace_list_table_branches_response_t *response,
    lance_namespace_error_t *error);

/**
 * Delete a branch from a table.
 *
 * @param handle       The namespace handle.
 * @param request      Delete branch request with table ID and branch name.
 * @param response     Output: empty on success.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_delete_table_branch(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_table_branch_request_t *request,
    lance_namespace_delete_table_branch_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Materialized View Operations
 * ==========================================================================*/

/**
 * Create a materialized view.
 *
 * The view may be query-backed, UDTF-backed, or chunker-backed,
 * controlled by the kind discriminator.
 *
 * @param handle       The namespace handle.
 * @param request      Create MV request with view ID, kind, query, properties.
 * @param response     Output: created view information (ID, location).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_ALREADY_EXISTS if view exists.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_create_materialized_view(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_materialized_view_request_t *request,
    lance_namespace_create_materialized_view_response_t *response,
    lance_namespace_error_t *error);

/**
 * Trigger an async materialized view refresh.
 *
 * @param handle       The namespace handle.
 * @param request      Refresh MV request with view ID and optional task size.
 * @param response     Output: async job ID.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_refresh_materialized_view(
    lance_namespace_handle_t *handle,
    const lance_namespace_refresh_materialized_view_request_t *request,
    lance_namespace_refresh_materialized_view_response_t *response,
    lance_namespace_error_t *error);

/* ============================================================================
 * Transaction Operations
 * ==========================================================================*/

/**
 * Describe a transaction.
 *
 * @param handle       The namespace handle.
 * @param request      Describe transaction request with transaction ID.
 * @param response     Output: transaction details (status, timestamps).
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success,
 *         LANCE_NAMESPACE_STATUS_NOT_FOUND if transaction does not exist.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_describe_transaction(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_transaction_request_t *request,
    lance_namespace_describe_transaction_response_t *response,
    lance_namespace_error_t *error);

/**
 * Alter a transaction (set status, set/unset properties).
 *
 * @param handle       The namespace handle.
 * @param request      Alter transaction request with transaction ID and action.
 * @param response     Output: updated transaction information.
 * @param error        Output: error details on failure (must be freed).
 * @return LANCE_NAMESPACE_STATUS_OK on success, or an error status.
 */
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_alter_transaction(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_transaction_request_t *request,
    lance_namespace_alter_transaction_response_t *response,
    lance_namespace_error_t *error);

#ifdef __cplusplus
}
#endif

#endif  // LANCE_NAMESPACE_OPERATIONS_H
