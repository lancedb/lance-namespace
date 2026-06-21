#include "lance_namespace/operations.h"

#include "lance_namespace/errors.h"

#include <cstdlib>
#include <cstring>

namespace {

void set_error(
    lance_namespace_error_t *error,
    lance_namespace_status_t code,
    const char *message) {
    if (error == nullptr) {
        return;
    }

    std::free(error->message);
    error->message = nullptr;
    error->code = code;
    if (message != nullptr) {
        error->message = strdup(message);
    }
}

lance_namespace_status_t unsupported(
    lance_namespace_error_t *error,
    const char *operation) {
    set_error(error, LANCE_NAMESPACE_STATUS_UNSUPPORTED, operation);
    return LANCE_NAMESPACE_STATUS_UNSUPPORTED;
}

}  // namespace

// ============================================================================
// Namespace Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_list_namespaces(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_namespaces_request_t *request,
    lance_namespace_list_namespaces_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_namespaces");
}

extern "C" lance_namespace_status_t lance_namespace_describe_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_namespace_request_t *request,
    lance_namespace_describe_namespace_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: describe_namespace");
}

extern "C" lance_namespace_status_t lance_namespace_create_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_namespace_request_t *request,
    lance_namespace_create_namespace_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_namespace");
}

extern "C" lance_namespace_status_t lance_namespace_drop_namespace(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_namespace_request_t *request,
    lance_namespace_drop_namespace_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: drop_namespace");
}

extern "C" lance_namespace_status_t lance_namespace_namespace_exists(
    lance_namespace_handle_t *handle,
    const lance_namespace_namespace_exists_request_t *request,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    return unsupported(error, "Not supported: namespace_exists");
}

extern "C" lance_namespace_status_t lance_namespace_list_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_tables_request_t *request,
    lance_namespace_list_tables_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_tables");
}

// ============================================================================
// Table Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_describe_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_request_t *request,
    lance_namespace_describe_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: describe_table");
}

extern "C" lance_namespace_status_t lance_namespace_register_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_register_table_request_t *request,
    lance_namespace_register_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: register_table");
}

extern "C" lance_namespace_status_t lance_namespace_table_exists(
    lance_namespace_handle_t *handle,
    const lance_namespace_table_exists_request_t *request,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    return unsupported(error, "Not supported: table_exists");
}

extern "C" lance_namespace_status_t lance_namespace_drop_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_table_request_t *request,
    lance_namespace_drop_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: drop_table");
}

extern "C" lance_namespace_status_t lance_namespace_deregister_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_deregister_table_request_t *request,
    lance_namespace_deregister_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: deregister_table");
}

extern "C" lance_namespace_status_t lance_namespace_count_table_rows(
    lance_namespace_handle_t *handle,
    const lance_namespace_count_table_rows_request_t *request,
    int64_t *out_count,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)out_count;
    return unsupported(error, "Not supported: count_table_rows");
}

extern "C" lance_namespace_status_t lance_namespace_create_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_request_t *request,
    lance_namespace_create_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table");
}

extern "C" lance_namespace_status_t lance_namespace_declare_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_declare_table_request_t *request,
    lance_namespace_declare_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: declare_table");
}

extern "C" lance_namespace_status_t lance_namespace_insert_into_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_insert_into_table_request_t *request,
    lance_namespace_insert_into_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: insert_into_table");
}

extern "C" lance_namespace_status_t lance_namespace_merge_insert_into_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_merge_insert_into_table_request_t *request,
    lance_namespace_merge_insert_into_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: merge_insert_into_table");
}

extern "C" lance_namespace_status_t lance_namespace_update_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_request_t *request,
    lance_namespace_update_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: update_table");
}

extern "C" lance_namespace_status_t lance_namespace_delete_from_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_from_table_request_t *request,
    lance_namespace_delete_from_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: delete_from_table");
}

extern "C" lance_namespace_status_t lance_namespace_query_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_query_table_request_t *request,
    lance_namespace_buffer_t *out_data,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)out_data;
    return unsupported(error, "Not supported: query_table");
}

extern "C" lance_namespace_status_t lance_namespace_list_all_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_all_tables_request_t *request,
    lance_namespace_list_tables_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_all_tables");
}

extern "C" lance_namespace_status_t lance_namespace_rename_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_rename_table_request_t *request,
    lance_namespace_rename_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: rename_table");
}

extern "C" lance_namespace_status_t lance_namespace_restore_table(
    lance_namespace_handle_t *handle,
    const lance_namespace_restore_table_request_t *request,
    lance_namespace_restore_table_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: restore_table");
}

// ============================================================================
// Table Index Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_create_table_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_index_request_t *request,
    lance_namespace_create_table_index_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table_index");
}

extern "C" lance_namespace_status_t lance_namespace_create_table_scalar_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_index_request_t *request,
    lance_namespace_create_table_scalar_index_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table_scalar_index");
}

extern "C" lance_namespace_status_t lance_namespace_list_table_indices(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_indices_request_t *request,
    lance_namespace_list_table_indices_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_table_indices");
}

extern "C" lance_namespace_status_t lance_namespace_describe_table_index_stats(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_index_stats_request_t *request,
    lance_namespace_describe_table_index_stats_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: describe_table_index_stats");
}

extern "C" lance_namespace_status_t lance_namespace_drop_table_index(
    lance_namespace_handle_t *handle,
    const lance_namespace_drop_table_index_request_t *request,
    lance_namespace_drop_table_index_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: drop_table_index");
}

// ============================================================================
// Table Version Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_list_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_versions_request_t *request,
    lance_namespace_list_table_versions_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_table_versions");
}

extern "C" lance_namespace_status_t lance_namespace_create_table_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_version_request_t *request,
    lance_namespace_create_table_version_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table_version");
}

extern "C" lance_namespace_status_t lance_namespace_describe_table_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_table_version_request_t *request,
    lance_namespace_describe_table_version_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: describe_table_version");
}

extern "C" lance_namespace_status_t lance_namespace_batch_delete_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_delete_table_versions_request_t *request,
    lance_namespace_batch_delete_table_versions_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: batch_delete_table_versions");
}

extern "C" lance_namespace_status_t lance_namespace_batch_create_table_versions(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_create_table_versions_request_t *request,
    lance_namespace_batch_create_table_versions_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: batch_create_table_versions");
}

extern "C" lance_namespace_status_t lance_namespace_batch_commit_tables(
    lance_namespace_handle_t *handle,
    const lance_namespace_batch_commit_tables_request_t *request,
    lance_namespace_batch_commit_tables_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: batch_commit_tables");
}

// ============================================================================
// Table Schema Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_alter_table_add_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_add_columns_request_t *request,
    lance_namespace_alter_table_add_columns_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: alter_table_add_columns");
}

extern "C" lance_namespace_status_t lance_namespace_alter_table_alter_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_alter_columns_request_t *request,
    lance_namespace_alter_table_alter_columns_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: alter_table_alter_columns");
}

extern "C" lance_namespace_status_t lance_namespace_alter_table_drop_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_drop_columns_request_t *request,
    lance_namespace_alter_table_drop_columns_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: alter_table_drop_columns");
}

extern "C" lance_namespace_status_t lance_namespace_alter_table_backfill_columns(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_table_backfill_columns_request_t *request,
    lance_namespace_alter_table_backfill_columns_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: alter_table_backfill_columns");
}

// ============================================================================
// Table Metadata Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_update_table_schema_metadata(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_schema_metadata_request_t *request,
    lance_namespace_update_table_schema_metadata_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: update_table_schema_metadata");
}

extern "C" lance_namespace_status_t lance_namespace_update_field_metadata(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_field_metadata_request_t *request,
    lance_namespace_update_field_metadata_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: update_field_metadata");
}

extern "C" lance_namespace_status_t lance_namespace_get_table_stats(
    lance_namespace_handle_t *handle,
    const lance_namespace_get_table_stats_request_t *request,
    lance_namespace_get_table_stats_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: get_table_stats");
}

// ============================================================================
// Query Plan Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_explain_table_query_plan(
    lance_namespace_handle_t *handle,
    const lance_namespace_explain_table_query_plan_request_t *request,
    char **out_plan,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)out_plan;
    return unsupported(error, "Not supported: explain_table_query_plan");
}

extern "C" lance_namespace_status_t lance_namespace_analyze_table_query_plan(
    lance_namespace_handle_t *handle,
    const lance_namespace_analyze_table_query_plan_request_t *request,
    char **out_analysis,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)out_analysis;
    return unsupported(error, "Not supported: analyze_table_query_plan");
}

// ============================================================================
// Table Tag Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_list_table_tags(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_tags_request_t *request,
    lance_namespace_list_table_tags_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_table_tags");
}

extern "C" lance_namespace_status_t lance_namespace_get_table_tag_version(
    lance_namespace_handle_t *handle,
    const lance_namespace_get_table_tag_version_request_t *request,
    lance_namespace_get_table_tag_version_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: get_table_tag_version");
}

extern "C" lance_namespace_status_t lance_namespace_create_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_tag_request_t *request,
    lance_namespace_create_table_tag_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table_tag");
}

extern "C" lance_namespace_status_t lance_namespace_delete_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_table_tag_request_t *request,
    lance_namespace_delete_table_tag_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: delete_table_tag");
}

extern "C" lance_namespace_status_t lance_namespace_update_table_tag(
    lance_namespace_handle_t *handle,
    const lance_namespace_update_table_tag_request_t *request,
    lance_namespace_update_table_tag_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: update_table_tag");
}

// ============================================================================
// Table Branch Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_create_table_branch(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_table_branch_request_t *request,
    lance_namespace_create_table_branch_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_table_branch");
}

extern "C" lance_namespace_status_t lance_namespace_list_table_branches(
    lance_namespace_handle_t *handle,
    const lance_namespace_list_table_branches_request_t *request,
    lance_namespace_list_table_branches_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: list_table_branches");
}

extern "C" lance_namespace_status_t lance_namespace_delete_table_branch(
    lance_namespace_handle_t *handle,
    const lance_namespace_delete_table_branch_request_t *request,
    lance_namespace_delete_table_branch_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: delete_table_branch");
}

// ============================================================================
// Materialized View Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_create_materialized_view(
    lance_namespace_handle_t *handle,
    const lance_namespace_create_materialized_view_request_t *request,
    lance_namespace_create_materialized_view_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: create_materialized_view");
}

extern "C" lance_namespace_status_t lance_namespace_refresh_materialized_view(
    lance_namespace_handle_t *handle,
    const lance_namespace_refresh_materialized_view_request_t *request,
    lance_namespace_refresh_materialized_view_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: refresh_materialized_view");
}

// ============================================================================
// Transaction Operations
// ============================================================================

extern "C" lance_namespace_status_t lance_namespace_describe_transaction(
    lance_namespace_handle_t *handle,
    const lance_namespace_describe_transaction_request_t *request,
    lance_namespace_describe_transaction_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: describe_transaction");
}

extern "C" lance_namespace_status_t lance_namespace_alter_transaction(
    lance_namespace_handle_t *handle,
    const lance_namespace_alter_transaction_request_t *request,
    lance_namespace_alter_transaction_response_t *response,
    lance_namespace_error_t *error) {
    (void)handle;
    (void)request;
    (void)response;
    return unsupported(error, "Not supported: alter_transaction");
}
