/**
 * @file models.h
 * @brief C-compatible model types for Lance Namespace request and response structs.
 *
 * This header defines C-compatible struct types for all Lance Namespace
 * operations. These types mirror the generated C++ model types from the
 * REST client but use plain C types (const char*, int64_t, etc.) for
 * cross-language compatibility.
 *
 * String fields (const char*) are not owned by the struct unless noted.
 * Callers must ensure string pointers remain valid for the lifetime of
 * the request. Response structs with allocated strings must be freed by
 * the caller.
 *
 * Helper types:
 * - lance_namespace_map_t: Key-value string map for properties and metadata.
 * - lance_namespace_buffer_t: Byte buffer for binary data (Arrow IPC streams).
 *
 * Use the provided helper functions to create and manage these types:
 * - lance_namespace_map_create() / lance_namespace_map_free()
 * - lance_namespace_map_set()
 * - lance_namespace_buffer_create() / lance_namespace_buffer_free()
 */

#ifndef LANCE_NAMESPACE_MODELS_H
#define LANCE_NAMESPACE_MODELS_H

#include <stddef.h>
#include <stdint.h>

#include "lance_namespace.h"

#ifdef __cplusplus
extern "C" {
#endif

/**
 * @brief Generic key-value string map for properties and metadata.
 *
 * Used to pass properties on requests and receive properties on responses.
 * Keys and values are string pointers. When created via
 * lance_namespace_map_create(), the map owns copies of all strings and
 * must be freed with lance_namespace_map_free().
 */
typedef struct lance_namespace_map {
    /** Array of key strings. */
    const char **keys;
    /** Array of value strings. */
    const char **values;
    /** Number of key-value pairs. */
    size_t count;
} lance_namespace_map_t;

/**
 * @brief Byte buffer for binary data (Arrow IPC streams, etc.).
 *
 * When created via lance_namespace_buffer_create(), the buffer owns the
 * allocated memory and must be freed with lance_namespace_buffer_free().
 */
typedef struct lance_namespace_buffer {
    /** Pointer to the byte data. */
    uint8_t *data;
    /** Size of the data in bytes. */
    size_t size;
} lance_namespace_buffer_t;

// ============================================================================
// Namespace Operations
// ============================================================================

typedef struct lance_namespace_list_namespaces_request {
    const char *id;
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_namespaces_request_t;

typedef struct lance_namespace_list_namespaces_response {
    char **namespaces;
    size_t count;
    char *page_token;
} lance_namespace_list_namespaces_response_t;

typedef struct lance_namespace_describe_namespace_request {
    const char *id;
    const char *delimiter;
} lance_namespace_describe_namespace_request_t;

typedef struct lance_namespace_describe_namespace_response {
    char *id;
    lance_namespace_map_t properties;
} lance_namespace_describe_namespace_response_t;

typedef struct lance_namespace_create_namespace_request {
    const char *id;
    const char *delimiter;
    lance_namespace_map_t properties;
} lance_namespace_create_namespace_request_t;

typedef struct lance_namespace_create_namespace_response {
    char *id;
    lance_namespace_map_t properties;
} lance_namespace_create_namespace_response_t;

typedef struct lance_namespace_drop_namespace_request {
    const char *id;
    const char *delimiter;
} lance_namespace_drop_namespace_request_t;

typedef struct lance_namespace_drop_namespace_response {
    // Empty response
} lance_namespace_drop_namespace_response_t;

typedef struct lance_namespace_namespace_exists_request {
    const char *id;
    const char *delimiter;
} lance_namespace_namespace_exists_request_t;

typedef struct lance_namespace_list_tables_request {
    const char *id;
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_tables_request_t;

typedef struct lance_namespace_list_tables_response {
    char **tables;
    size_t count;
    char *page_token;
} lance_namespace_list_tables_response_t;

// ============================================================================
// Table Operations
// ============================================================================

typedef struct lance_namespace_describe_table_request {
    const char *id;
    const char *delimiter;
} lance_namespace_describe_table_request_t;

typedef struct lance_namespace_describe_table_response {
    char *id;
    char *location;
    int64_t version;
    lance_namespace_map_t properties;
} lance_namespace_describe_table_response_t;

typedef struct lance_namespace_register_table_request {
    const char *id;
    const char *delimiter;
    const char *location;
    lance_namespace_map_t properties;
} lance_namespace_register_table_request_t;

typedef struct lance_namespace_register_table_response {
    char *id;
    char *location;
} lance_namespace_register_table_response_t;

typedef struct lance_namespace_table_exists_request {
    const char *id;
    const char *delimiter;
} lance_namespace_table_exists_request_t;

typedef struct lance_namespace_drop_table_request {
    const char *id;
    const char *delimiter;
} lance_namespace_drop_table_request_t;

typedef struct lance_namespace_drop_table_response {
    // Empty response
} lance_namespace_drop_table_response_t;

typedef struct lance_namespace_deregister_table_request {
    const char *id;
    const char *delimiter;
} lance_namespace_deregister_table_request_t;

typedef struct lance_namespace_deregister_table_response {
    // Empty response
} lance_namespace_deregister_table_response_t;

typedef struct lance_namespace_count_table_rows_request {
    const char *id;
    const char *delimiter;
    int64_t version;
} lance_namespace_count_table_rows_request_t;

typedef struct lance_namespace_create_table_request {
    const char *id;
    const char *delimiter;
    const char *mode;
    lance_namespace_map_t properties;
    lance_namespace_map_t storage_options;
    lance_namespace_buffer_t data;
} lance_namespace_create_table_request_t;

typedef struct lance_namespace_create_table_response {
    char *id;
    char *location;
    int64_t version;
} lance_namespace_create_table_response_t;

typedef struct lance_namespace_declare_table_request {
    const char *id;
    const char *delimiter;
    lance_namespace_map_t properties;
} lance_namespace_declare_table_request_t;

typedef struct lance_namespace_declare_table_response {
    char *id;
    char *location;
} lance_namespace_declare_table_response_t;

typedef struct lance_namespace_insert_into_table_request {
    const char *id;
    const char *delimiter;
    const char *mode;
    const char *branch;
    lance_namespace_buffer_t data;
} lance_namespace_insert_into_table_request_t;

typedef struct lance_namespace_insert_into_table_response {
    int64_t rows_inserted;
} lance_namespace_insert_into_table_response_t;

typedef struct lance_namespace_query_table_request {
    const char *id;
    const char *delimiter;
    int64_t version;
    const char *branch;
    int64_t limit;
    const char *filter;
    lance_namespace_buffer_t vector;
    int32_t k;
    const char *columns;
} lance_namespace_query_table_request_t;

typedef struct lance_namespace_query_table_response {
    lance_namespace_buffer_t data;
} lance_namespace_query_table_response_t;

typedef struct lance_namespace_update_table_request {
    const char *id;
    const char *delimiter;
    const char *updates;
} lance_namespace_update_table_request_t;

typedef struct lance_namespace_update_table_response {
    int64_t rows_updated;
} lance_namespace_update_table_response_t;

typedef struct lance_namespace_delete_from_table_request {
    const char *id;
    const char *delimiter;
    const char *predicate;
} lance_namespace_delete_from_table_request_t;

typedef struct lance_namespace_delete_from_table_response {
    int64_t rows_deleted;
} lance_namespace_delete_from_table_response_t;

typedef struct lance_namespace_merge_insert_into_table_request {
    const char *id;
    const char *delimiter;
    const char *branch;
    const char *on;
    int32_t when_matched_update_all;
    const char *when_matched_update_all_filt;
    int32_t when_not_matched_insert_all;
    int32_t when_not_matched_by_source_delete;
    const char *when_not_matched_by_source_delete_filt;
    lance_namespace_buffer_t data;
} lance_namespace_merge_insert_into_table_request_t;

typedef struct lance_namespace_merge_insert_into_table_response {
    int64_t rows_inserted;
    int64_t rows_updated;
    int64_t rows_deleted;
} lance_namespace_merge_insert_into_table_response_t;

typedef struct lance_namespace_rename_table_request {
    const char *id;
    const char *delimiter;
    const char *new_id;
} lance_namespace_rename_table_request_t;

typedef struct lance_namespace_rename_table_response {
    // Empty response
} lance_namespace_rename_table_response_t;

typedef struct lance_namespace_restore_table_request {
    const char *id;
    const char *delimiter;
    int64_t version;
} lance_namespace_restore_table_request_t;

typedef struct lance_namespace_restore_table_response {
    // Empty response
} lance_namespace_restore_table_response_t;

typedef struct lance_namespace_list_all_tables_request {
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_all_tables_request_t;

// ============================================================================
// Table Index Operations
// ============================================================================

typedef struct lance_namespace_create_table_index_request {
    const char *id;
    const char *delimiter;
    const char *index_name;
    const char *index_type;
    const char *distance_type;
    const char *column;
    int32_t num_partitions;
    int32_t num_sub_vectors;
    lance_namespace_map_t params;
} lance_namespace_create_table_index_request_t;

typedef struct lance_namespace_create_table_index_response {
    char *index_uuid;
} lance_namespace_create_table_index_response_t;

typedef struct lance_namespace_create_table_scalar_index_response {
    char *index_uuid;
} lance_namespace_create_table_scalar_index_response_t;

typedef struct lance_namespace_list_table_indices_request {
    const char *id;
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_table_indices_request_t;

typedef struct lance_namespace_table_index_info {
    char *index_name;
    char *index_type;
    char *column;
    char *index_uuid;
    char *status;
} lance_namespace_table_index_info_t;

typedef struct lance_namespace_list_table_indices_response {
    lance_namespace_table_index_info_t *indices;
    size_t count;
    char *page_token;
} lance_namespace_list_table_indices_response_t;

typedef struct lance_namespace_describe_table_index_stats_request {
    const char *id;
    const char *delimiter;
    const char *index_name;
} lance_namespace_describe_table_index_stats_request_t;

typedef struct lance_namespace_describe_table_index_stats_response {
    char *index_type;
    char *distance_type;
    int64_t num_rows_indexed;
    char *status;
} lance_namespace_describe_table_index_stats_response_t;

typedef struct lance_namespace_drop_table_index_request {
    const char *id;
    const char *delimiter;
    const char *index_name;
} lance_namespace_drop_table_index_request_t;

typedef struct lance_namespace_drop_table_index_response {
    // Empty response
} lance_namespace_drop_table_index_response_t;

// ============================================================================
// Table Version Operations
// ============================================================================

typedef struct lance_namespace_list_table_versions_request {
    const char *id;
    const char *delimiter;
    const char *branch;
    const char *page_token;
    int32_t limit;
    int32_t descending;
} lance_namespace_list_table_versions_request_t;

typedef struct lance_namespace_table_version_info {
    int64_t version;
    char *manifest_path;
    char *timestamp;
    char *operation;
} lance_namespace_table_version_info_t;

typedef struct lance_namespace_list_table_versions_response {
    lance_namespace_table_version_info_t *versions;
    size_t count;
    char *page_token;
} lance_namespace_list_table_versions_response_t;

typedef struct lance_namespace_create_table_version_request {
    const char *id;
    const char *delimiter;
    int64_t version;
    const char *manifest_path;
    int32_t put_if_not_exists;
} lance_namespace_create_table_version_request_t;

typedef struct lance_namespace_create_table_version_response {
    int64_t version;
    char *manifest_path;
} lance_namespace_create_table_version_response_t;

typedef struct lance_namespace_describe_table_version_request {
    const char *id;
    const char *delimiter;
    int64_t version;
    const char *branch;
} lance_namespace_describe_table_version_request_t;

typedef struct lance_namespace_describe_table_version_response {
    int64_t version;
    char *manifest_path;
    char *timestamp;
    char *operation;
} lance_namespace_describe_table_version_response_t;

typedef struct lance_namespace_batch_delete_table_versions_request {
    const char *id;
    const char *delimiter;
    int64_t start_version;
    int64_t end_version;
} lance_namespace_batch_delete_table_versions_request_t;

typedef struct lance_namespace_batch_delete_table_versions_response {
    // Empty response
} lance_namespace_batch_delete_table_versions_response_t;

typedef struct lance_namespace_create_table_version_entry {
    const char *id;
    int64_t version;
    const char *manifest_path;
} lance_namespace_create_table_version_entry_t;

typedef struct lance_namespace_batch_create_table_versions_request {
    lance_namespace_create_table_version_entry_t *entries;
    size_t count;
    const char *delimiter;
} lance_namespace_batch_create_table_versions_request_t;

typedef struct lance_namespace_batch_create_table_versions_response {
    // Empty response
} lance_namespace_batch_create_table_versions_response_t;

typedef struct lance_namespace_commit_table_operation {
    const char *type;
    const char *id;
    lance_namespace_map_t properties;
} lance_namespace_commit_table_operation_t;

typedef struct lance_namespace_batch_commit_tables_request {
    lance_namespace_commit_table_operation_t *operations;
    size_t count;
    const char *delimiter;
} lance_namespace_batch_commit_tables_request_t;

typedef struct lance_namespace_commit_table_result {
    const char *id;
    int32_t success;
    char *error_message;
} lance_namespace_commit_table_result_t;

typedef struct lance_namespace_batch_commit_tables_response {
    lance_namespace_commit_table_result_t *results;
    size_t count;
} lance_namespace_batch_commit_tables_response_t;

// ============================================================================
// Table Schema Operations
// ============================================================================

typedef struct lance_namespace_add_columns_entry {
    char *name;
    char *type;
    char *value;
} lance_namespace_add_columns_entry_t;

typedef struct lance_namespace_alter_table_add_columns_request {
    const char *id;
    const char *delimiter;
    lance_namespace_add_columns_entry_t *columns;
    size_t count;
} lance_namespace_alter_table_add_columns_request_t;

typedef struct lance_namespace_alter_table_add_columns_response {
    // Empty response
} lance_namespace_alter_table_add_columns_response_t;

typedef struct lance_namespace_alter_columns_entry {
    char *path;
    char *rename;
    char *data_type;
    int32_t nullable;
} lance_namespace_alter_columns_entry_t;

typedef struct lance_namespace_alter_table_alter_columns_request {
    const char *id;
    const char *delimiter;
    lance_namespace_alter_columns_entry_t *columns;
    size_t count;
} lance_namespace_alter_table_alter_columns_request_t;

typedef struct lance_namespace_alter_table_alter_columns_response {
    // Empty response
} lance_namespace_alter_table_alter_columns_response_t;

typedef struct lance_namespace_alter_table_drop_columns_request {
    const char *id;
    const char *delimiter;
    char **column_names;
    size_t count;
} lance_namespace_alter_table_drop_columns_request_t;

typedef struct lance_namespace_alter_table_drop_columns_response {
    // Empty response
} lance_namespace_alter_table_drop_columns_response_t;

typedef struct lance_namespace_alter_table_backfill_columns_request {
    const char *id;
    const char *delimiter;
    char **column_names;
    size_t count;
} lance_namespace_alter_table_backfill_columns_request_t;

typedef struct lance_namespace_alter_table_backfill_columns_response {
    char *job_id;
} lance_namespace_alter_table_backfill_columns_response_t;

// ============================================================================
// Table Metadata Operations
// ============================================================================

typedef struct lance_namespace_update_table_schema_metadata_request {
    const char *id;
    const char *delimiter;
    const char *branch;
    lance_namespace_map_t metadata;
} lance_namespace_update_table_schema_metadata_request_t;

typedef struct lance_namespace_update_table_schema_metadata_response {
    lance_namespace_map_t metadata;
} lance_namespace_update_table_schema_metadata_response_t;

typedef struct lance_namespace_update_field_metadata_entry {
    char *path;
    lance_namespace_map_t metadata;
    int32_t replace;
} lance_namespace_update_field_metadata_entry_t;

typedef struct lance_namespace_update_field_metadata_request {
    const char *id;
    const char *delimiter;
    lance_namespace_update_field_metadata_entry_t *entries;
    size_t count;
} lance_namespace_update_field_metadata_request_t;

typedef struct lance_namespace_update_field_metadata_response {
    // Empty response
} lance_namespace_update_field_metadata_response_t;

typedef struct lance_namespace_get_table_stats_request {
    const char *id;
    const char *delimiter;
} lance_namespace_get_table_stats_request_t;

typedef struct lance_namespace_get_table_stats_response {
    int64_t num_rows;
    int64_t num_versions;
    int64_t num_deleted_rows;
    lance_namespace_map_t column_stats;
} lance_namespace_get_table_stats_response_t;

// ============================================================================
// Query Plan Operations
// ============================================================================

typedef struct lance_namespace_explain_table_query_plan_request {
    const char *id;
    const char *delimiter;
    const char *query;
} lance_namespace_explain_table_query_plan_request_t;

typedef struct lance_namespace_analyze_table_query_plan_request {
    const char *id;
    const char *delimiter;
    const char *query;
} lance_namespace_analyze_table_query_plan_request_t;

// ============================================================================
// Table Tag Operations
// ============================================================================

typedef struct lance_namespace_list_table_tags_request {
    const char *id;
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_table_tags_request_t;

typedef struct lance_namespace_table_tag_info {
    char *tag_name;
    int64_t version;
} lance_namespace_table_tag_info_t;

typedef struct lance_namespace_list_table_tags_response {
    lance_namespace_table_tag_info_t *tags;
    size_t count;
    char *page_token;
} lance_namespace_list_table_tags_response_t;

typedef struct lance_namespace_get_table_tag_version_request {
    const char *id;
    const char *delimiter;
    const char *tag_name;
} lance_namespace_get_table_tag_version_request_t;

typedef struct lance_namespace_get_table_tag_version_response {
    int64_t version;
} lance_namespace_get_table_tag_version_response_t;

typedef struct lance_namespace_create_table_tag_request {
    const char *id;
    const char *delimiter;
    const char *tag_name;
    int64_t version;
} lance_namespace_create_table_tag_request_t;

typedef struct lance_namespace_create_table_tag_response {
    // Empty response
} lance_namespace_create_table_tag_response_t;

typedef struct lance_namespace_delete_table_tag_request {
    const char *id;
    const char *delimiter;
    const char *tag_name;
} lance_namespace_delete_table_tag_request_t;

typedef struct lance_namespace_delete_table_tag_response {
    // Empty response
} lance_namespace_delete_table_tag_response_t;

typedef struct lance_namespace_update_table_tag_request {
    const char *id;
    const char *delimiter;
    const char *tag_name;
    int64_t version;
} lance_namespace_update_table_tag_request_t;

typedef struct lance_namespace_update_table_tag_response {
    // Empty response
} lance_namespace_update_table_tag_response_t;

// ============================================================================
// Table Branch Operations
// ============================================================================

typedef struct lance_namespace_create_table_branch_request {
    const char *id;
    const char *delimiter;
    const char *branch;
    int64_t source_version;
    const char *source_branch;
} lance_namespace_create_table_branch_request_t;

typedef struct lance_namespace_create_table_branch_response {
    // Empty response
} lance_namespace_create_table_branch_response_t;

typedef struct lance_namespace_list_table_branches_request {
    const char *id;
    const char *delimiter;
    const char *page_token;
    int32_t limit;
} lance_namespace_list_table_branches_request_t;

typedef struct lance_namespace_table_branch_info {
    char *branch_name;
    int64_t version;
} lance_namespace_table_branch_info_t;

typedef struct lance_namespace_list_table_branches_response {
    lance_namespace_table_branch_info_t *branches;
    size_t count;
    char *page_token;
} lance_namespace_list_table_branches_response_t;

typedef struct lance_namespace_delete_table_branch_request {
    const char *id;
    const char *delimiter;
    const char *branch;
} lance_namespace_delete_table_branch_request_t;

typedef struct lance_namespace_delete_table_branch_response {
    // Empty response
} lance_namespace_delete_table_branch_response_t;

// ============================================================================
// Materialized View Operations
// ============================================================================

typedef struct lance_namespace_create_materialized_view_request {
    const char *id;
    const char *delimiter;
    const char *kind;
    const char *query;
    lance_namespace_map_t properties;
} lance_namespace_create_materialized_view_request_t;

typedef struct lance_namespace_create_materialized_view_response {
    char *id;
    char *location;
} lance_namespace_create_materialized_view_response_t;

typedef struct lance_namespace_refresh_materialized_view_request {
    const char *id;
    const char *delimiter;
    int64_t source_task_size;
} lance_namespace_refresh_materialized_view_request_t;

typedef struct lance_namespace_refresh_materialized_view_response {
    char *job_id;
} lance_namespace_refresh_materialized_view_response_t;

// ============================================================================
// Transaction Operations
// ============================================================================

typedef struct lance_namespace_describe_transaction_request {
    const char *transaction_id;
} lance_namespace_describe_transaction_request_t;

typedef struct lance_namespace_describe_transaction_response {
    char *transaction_id;
    char *status;
    char *created_at;
    char *updated_at;
} lance_namespace_describe_transaction_response_t;

typedef struct lance_namespace_alter_transaction_request {
    const char *transaction_id;
    const char *action;
    lance_namespace_map_t properties;
} lance_namespace_alter_transaction_request_t;

typedef struct lance_namespace_alter_transaction_response {
    char *transaction_id;
    char *status;
} lance_namespace_alter_transaction_response_t;

/* ============================================================================
 * Helper functions for creating and managing model types
 *
 * Use these functions to construct and destroy map and buffer types used
 * in request and response structs. All functions are safe to call with
 * zero-capacity/zero-size values.
 * ==========================================================================*/

/**
 * Create a key-value map with the given initial capacity.
 *
 * The map is initialized empty (count = 0). Use lance_namespace_map_set()
 * to add entries. Must be freed with lance_namespace_map_free().
 *
 * @param capacity  Initial capacity hint (0 is valid for an empty map).
 * @return An initialized map struct.
 */
LANCE_NAMESPACE_API lance_namespace_map_t lance_namespace_map_create(size_t capacity);

/**
 * Free all memory owned by a map, including string copies.
 *
 * After calling this function, the map's keys, values, and count are
 * zeroed out. The struct itself is not freed (caller owns the struct).
 *
 * @param map  Pointer to the map to free. Safe to pass NULL.
 */
LANCE_NAMESPACE_API void lance_namespace_map_free(lance_namespace_map_t *map);

/**
 * Set a key-value pair in the map.
 *
 * If the key already exists, its value is replaced. Both key and value
 * strings are copied internally.
 *
 * @param map    Pointer to the map.
 * @param key    The key string (copied internally).
 * @param value  The value string (copied internally), or NULL.
 * @return 1 on success, 0 on failure (allocation error or NULL args).
 */
LANCE_NAMESPACE_API int lance_namespace_map_set(lance_namespace_map_t *map, const char *key, const char *value);

/**
 * Create a byte buffer with the given size.
 *
 * Memory is zero-initialized. Must be freed with
 * lance_namespace_buffer_free().
 *
 * @param size  Number of bytes to allocate (0 is valid).
 * @return An initialized buffer struct.
 */
LANCE_NAMESPACE_API lance_namespace_buffer_t lance_namespace_buffer_create(size_t size);

/**
 * Free all memory owned by a buffer.
 *
 * After calling this function, the buffer's data and size are zeroed out.
 * The struct itself is not freed (caller owns the struct).
 *
 * @param buffer  Pointer to the buffer to free. Safe to pass NULL.
 */
LANCE_NAMESPACE_API void lance_namespace_buffer_free(lance_namespace_buffer_t *buffer);

#ifdef __cplusplus
}
#endif

#endif  // LANCE_NAMESPACE_MODELS_H
