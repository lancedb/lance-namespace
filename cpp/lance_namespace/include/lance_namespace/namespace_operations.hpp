/**
 * @file namespace_operations.hpp
 * @brief Base interface for Lance Namespace implementations.
 *
 * This module provides the NamespaceOperations abstract base class that
 * defines the contract for namespace implementations that manage Lance tables.
 * Implementations can provide different storage backends (directory-based,
 * REST API, cloud catalogs, etc.).
 *
 * To create a custom namespace implementation, subclass NamespaceOperations
 * and implement at least the namespace_id() method. Other methods have default
 * implementations that raise UnsupportedOperationError.
 *
 * All operations may raise the following common errors:
 * - UnsupportedOperationError: The operation is not supported by this backend
 * - InvalidInputError: The request contains invalid parameters
 * - PermissionDeniedError: The user lacks permission for this operation
 * - UnauthenticatedError: Authentication credentials are missing or invalid
 * - ServiceUnavailableError: The service is temporarily unavailable
 * - InternalError: An unexpected internal error occurred
 */

#ifndef LANCE_NAMESPACE_NAMESPACE_OPERATIONS_HPP
#define LANCE_NAMESPACE_NAMESPACE_OPERATIONS_HPP

#include "errors.hpp"

#include <cstdint>
#include <memory>
#include <string>
#include <vector>

namespace org {
namespace openapitools {
namespace client {
namespace model {

class AlterTableAddColumnsRequest;
class AlterTableAddColumnsResponse;
class AlterTableAlterColumnsRequest;
class AlterTableAlterColumnsResponse;
class AlterTableBackfillColumnsRequest;
class AlterTableBackfillColumnsResponse;
class AlterTableDropColumnsRequest;
class AlterTableDropColumnsResponse;
class AlterTransactionRequest;
class AlterTransactionResponse;
class AnalyzeTableQueryPlanRequest;
class BatchCommitTablesRequest;
class BatchCommitTablesResponse;
class BatchCreateTableVersionsRequest;
class BatchCreateTableVersionsResponse;
class BatchDeleteTableVersionsRequest;
class BatchDeleteTableVersionsResponse;
class CountTableRowsRequest;
class CreateMaterializedViewRequest;
class CreateMaterializedViewResponse;
class CreateNamespaceRequest;
class CreateNamespaceResponse;
class CreateTableBranchRequest;
class CreateTableBranchResponse;
class CreateTableIndexRequest;
class CreateTableIndexResponse;
class CreateTableScalarIndexResponse;
class CreateTableRequest;
class CreateTableResponse;
class CreateTableTagRequest;
class CreateTableTagResponse;
class CreateTableVersionRequest;
class CreateTableVersionResponse;
class DeclareTableRequest;
class DeclareTableResponse;
class DeleteFromTableRequest;
class DeleteFromTableResponse;
class DeleteTableBranchRequest;
class DeleteTableBranchResponse;
class DeleteTableTagRequest;
class DeleteTableTagResponse;
class DeregisterTableRequest;
class DeregisterTableResponse;
class DescribeNamespaceRequest;
class DescribeNamespaceResponse;
class DescribeTableIndexStatsRequest;
class DescribeTableIndexStatsResponse;
class DescribeTableRequest;
class DescribeTableResponse;
class DescribeTableVersionRequest;
class DescribeTableVersionResponse;
class DescribeTransactionRequest;
class DescribeTransactionResponse;
class DropNamespaceRequest;
class DropNamespaceResponse;
class DropTableIndexRequest;
class DropTableIndexResponse;
class DropTableRequest;
class DropTableResponse;
class ExplainTableQueryPlanRequest;
class GetTableStatsRequest;
class GetTableStatsResponse;
class GetTableTagVersionRequest;
class GetTableTagVersionResponse;
class InsertIntoTableRequest;
class InsertIntoTableResponse;
class ListNamespacesRequest;
class ListNamespacesResponse;
class ListTableBranchesRequest;
class ListTableBranchesResponse;
class ListTableIndicesRequest;
class ListTableIndicesResponse;
class ListTableTagsRequest;
class ListTableTagsResponse;
class ListTableVersionsRequest;
class ListTableVersionsResponse;
class ListTablesRequest;
class ListTablesResponse;
class MergeInsertIntoTableRequest;
class MergeInsertIntoTableResponse;
class NamespaceExistsRequest;
class QueryTableRequest;
class RefreshMaterializedViewRequest;
class RefreshMaterializedViewResponse;
class RegisterTableRequest;
class RegisterTableResponse;
class RenameTableRequest;
class RenameTableResponse;
class RestoreTableRequest;
class RestoreTableResponse;
class TableExistsRequest;
class UpdateFieldMetadataRequest;
class UpdateFieldMetadataResponse;
class UpdateTableRequest;
class UpdateTableResponse;
class UpdateTableSchemaMetadataRequest;
class UpdateTableSchemaMetadataResponse;
class UpdateTableTagRequest;
class UpdateTableTagResponse;

}
}
}
}

namespace lance_namespace {

using namespace org::openapitools::client::model;

/**
 * @brief Base interface for Lance Namespace implementations.
 *
 * This abstract base class defines the contract for namespace implementations
 * that manage Lance tables. Implementations can provide different storage
 * backends (directory-based, REST API, cloud catalogs, etc.).
 *
 * To create a custom namespace implementation, subclass this class and
 * implement at least the namespace_id() method. Other methods have default
 * implementations that raise UnsupportedOperationError.
 *
 * Native implementations (DirectoryNamespace, RestNamespace) are provided
 * by the lance package. External integrations (Glue, Hive, Unity) can be
 * registered using register_namespace_impl().
 */
class NamespaceOperations {
  public:
    virtual ~NamespaceOperations() = default;

    /**
     * @brief Return a human-readable unique identifier for this namespace instance.
     *
     * Used for equality comparison and hashing when the namespace is used as
     * part of a storage options provider. Two namespace instances with the same
     * ID are considered equal and will share cached resources.
     *
     * Examples:
     * - REST namespace: "RestNamespace { uri: 'https://api.example.com' }"
     * - Directory namespace: "DirectoryNamespace { root: '/path/to/data' }"
     *
     * @return A human-readable unique identifier string.
     */
    virtual std::string namespace_id() const = 0;

    // ========================================================================
    // Namespace operations
    // ========================================================================

    /**
     * @brief List namespaces.
     *
     * @param request The list namespaces request containing parent namespace ID
     *                and pagination parameters.
     * @return List of child namespace names.
     * @throws NamespaceNotFoundError If the parent namespace does not exist.
     */
    virtual std::shared_ptr<ListNamespacesResponse> list_namespaces(
        std::shared_ptr<ListNamespacesRequest> request) {
        throw UnsupportedOperationError("Not supported: list_namespaces");
    }

    /**
     * @brief Describe a namespace.
     *
     * @param request The describe namespace request containing the namespace ID.
     * @return Detailed namespace information including properties.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     */
    virtual std::shared_ptr<DescribeNamespaceResponse> describe_namespace(
        std::shared_ptr<DescribeNamespaceRequest> request) {
        throw UnsupportedOperationError("Not supported: describe_namespace");
    }

    /**
     * @brief Create a new namespace.
     *
     * @param request The create namespace request containing the namespace ID
     *                and optional properties.
     * @return The created namespace information.
     * @throws NamespaceAlreadyExistsError If a namespace with the same name already exists.
     */
    virtual std::shared_ptr<CreateNamespaceResponse> create_namespace(
        std::shared_ptr<CreateNamespaceRequest> request) {
        throw UnsupportedOperationError("Not supported: create_namespace");
    }

    /**
     * @brief Drop a namespace.
     *
     * @param request The drop namespace request containing the namespace ID.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws NamespaceNotEmptyError If the namespace contains tables or child namespaces.
     */
    virtual std::shared_ptr<DropNamespaceResponse> drop_namespace(
        std::shared_ptr<DropNamespaceRequest> request) {
        throw UnsupportedOperationError("Not supported: drop_namespace");
    }

    /**
     * @brief Check if a namespace exists.
     *
     * Behaves exactly like describe_namespace(), except it does not contain
     * a response body.
     *
     * @param request The namespace exists request containing the namespace ID.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     */
    virtual void namespace_exists(
        std::shared_ptr<NamespaceExistsRequest> request) {
        throw UnsupportedOperationError("Not supported: namespace_exists");
    }

    /**
     * @brief List tables in a namespace.
     *
     * @param request The list tables request containing the parent namespace ID
     *                and pagination parameters.
     * @return List of table names in the namespace.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     */
    virtual std::shared_ptr<ListTablesResponse> list_tables(
        std::shared_ptr<ListTablesRequest> request) {
        throw UnsupportedOperationError("Not supported: list_tables");
    }

    // ========================================================================
    // Table operations
    // ========================================================================

    /**
     * @brief Describe a table.
     *
     * @param request The describe table request containing the table ID.
     * @return Detailed table information including location, version, and properties.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     */
    virtual std::shared_ptr<DescribeTableResponse> describe_table(
        std::shared_ptr<DescribeTableRequest> request) {
        throw UnsupportedOperationError("Not supported: describe_table");
    }

    /**
     * @brief Register a table.
     *
     * Register an existing table at a given storage location.
     *
     * @param request The register table request containing the table ID and location.
     * @return The registered table information.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableAlreadyExistsError If a table with the same name already exists.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<RegisterTableResponse> register_table(
        std::shared_ptr<RegisterTableRequest> request) {
        throw UnsupportedOperationError("Not supported: register_table");
    }

    /**
     * @brief Check if a table exists.
     *
     * Behaves exactly like describe_table(), except it does not contain
     * a response body.
     *
     * @param request The table exists request containing the table ID.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual void table_exists(
        std::shared_ptr<TableExistsRequest> request) {
        throw UnsupportedOperationError("Not supported: table_exists");
    }

    /**
     * @brief Drop a table.
     *
     * Drop a table and delete its data.
     *
     * @param request The drop table request containing the table ID.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<DropTableResponse> drop_table(
        std::shared_ptr<DropTableRequest> request) {
        throw UnsupportedOperationError("Not supported: drop_table");
    }

    /**
     * @brief Deregister a table.
     *
     * Deregister a table from its namespace without deleting the underlying data.
     *
     * @param request The deregister table request containing the table ID.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<DeregisterTableResponse> deregister_table(
        std::shared_ptr<DeregisterTableRequest> request) {
        throw UnsupportedOperationError("Not supported: deregister_table");
    }

    /**
     * @brief Count rows in a table.
     *
     * @param request The count table rows request containing the table ID
     *                and optional version.
     * @return The number of rows in the table.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     */
    virtual int64_t count_table_rows(
        std::shared_ptr<CountTableRowsRequest> request) {
        throw UnsupportedOperationError("Not supported: count_table_rows");
    }

    /**
     * @brief Create a new table with data from Arrow IPC stream.
     *
     * Create a table in the namespace with the given data in Arrow IPC stream.
     * The schema of the Arrow IPC stream is used as the table schema.
     * If the stream is empty, a new empty table is created.
     *
     * @param request The create table request containing the table ID, mode,
     *                and optional properties/storage options.
     * @param request_data The Arrow IPC stream data for the initial table contents.
     * @return The created table information including location and version.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableAlreadyExistsError If a table with the same name already exists.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws TableSchemaValidationError If the schema validation fails.
     */
    virtual std::shared_ptr<CreateTableResponse> create_table(
        std::shared_ptr<CreateTableRequest> request,
        const std::vector<uint8_t> &request_data) {
        throw UnsupportedOperationError("Not supported: create_table");
    }

    /**
     * @brief Declare a table (metadata only operation).
     *
     * Declare a table with the given name without touching storage. This is a
     * metadata-only operation that records table existence and sets up aspects
     * like access control.
     *
     * @param request The declare table request containing the table ID and
     *                optional properties.
     * @return The declared table information.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableAlreadyExistsError If a table with the same name already exists.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<DeclareTableResponse> declare_table(
        std::shared_ptr<DeclareTableRequest> request) {
        throw UnsupportedOperationError("Not supported: declare_table");
    }

    /**
     * @brief Insert data into a table.
     *
     * Insert new records into a table via Arrow IPC stream. For tables that have
     * been declared but not yet created on storage, this operation will create
     * the table with the provided data.
     *
     * @param request The insert request containing the table ID, mode, and
     *                optional branch.
     * @param request_data The Arrow IPC stream containing records to insert.
     * @return The insert result including the number of rows inserted.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws InvalidTableStateError If the table is in an invalid state.
     * @throws TableSchemaValidationError If the schema validation fails.
     */
    virtual std::shared_ptr<InsertIntoTableResponse> insert_into_table(
        std::shared_ptr<InsertIntoTableRequest> request,
        const std::vector<uint8_t> &request_data) {
        throw UnsupportedOperationError("Not supported: insert_into_table");
    }

    /**
     * @brief Merge insert (upsert) data into a table.
     *
     * Performs a merge insert (upsert) operation on a table. This updates
     * existing rows based on a matching column and inserts new rows that
     * don't match.
     *
     * @param request The merge insert request containing the table ID, match
     *                column, and merge conditions.
     * @param request_data The Arrow IPC stream containing records to merge.
     * @return The merge result including row counts for inserted, updated,
     *         and deleted rows.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws InvalidTableStateError If the table is in an invalid state.
     */
    virtual std::shared_ptr<MergeInsertIntoTableResponse> merge_insert_into_table(
        std::shared_ptr<MergeInsertIntoTableRequest> request,
        const std::vector<uint8_t> &request_data) {
        throw UnsupportedOperationError("Not supported: merge_insert_into_table");
    }

    /**
     * @brief Update rows in a table.
     *
     * @param request The update table request containing the table ID and
     *                update expressions.
     * @return The update result including the number of rows updated.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws InvalidTableStateError If the table is in an invalid state.
     */
    virtual std::shared_ptr<UpdateTableResponse> update_table(
        std::shared_ptr<UpdateTableRequest> request) {
        throw UnsupportedOperationError("Not supported: update_table");
    }

    /**
     * @brief Delete rows from a table.
     *
     * @param request The delete request containing the table ID and predicate.
     * @return The delete result including the number of rows deleted.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws InvalidTableStateError If the table is in an invalid state.
     */
    virtual std::shared_ptr<DeleteFromTableResponse> delete_from_table(
        std::shared_ptr<DeleteFromTableRequest> request) {
        throw UnsupportedOperationError("Not supported: delete_from_table");
    }

    /**
     * @brief Query a table.
     *
     * Query a table with vector search, full text search and optional SQL
     * filtering. Returns results in Arrow IPC file or stream format.
     *
     * @param request The query request containing the table ID, vector,
     *                filter, and other query parameters.
     * @return Arrow IPC stream containing the query results.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     */
    virtual std::vector<uint8_t> query_table(
        std::shared_ptr<QueryTableRequest> request) {
        throw UnsupportedOperationError("Not supported: query_table");
    }

    /**
     * @brief List all tables across all namespaces.
     *
     * @param request The list tables request containing pagination parameters.
     * @return List of all tables with their namespace information.
     */
    virtual std::shared_ptr<ListTablesResponse> list_all_tables(
        std::shared_ptr<ListTablesRequest> request) {
        throw UnsupportedOperationError("Not supported: list_all_tables");
    }

    /**
     * @brief Rename a table.
     *
     * @param request The rename table request containing the table ID and new name.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableAlreadyExistsError If a table with the new name already exists.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<RenameTableResponse> rename_table(
        std::shared_ptr<RenameTableRequest> request) {
        throw UnsupportedOperationError("Not supported: rename_table");
    }

    /**
     * @brief Restore a table to a specific version.
     *
     * @param request The restore table request containing the table ID and
     *                target version.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<RestoreTableResponse> restore_table(
        std::shared_ptr<RestoreTableRequest> request) {
        throw UnsupportedOperationError("Not supported: restore_table");
    }

    // ========================================================================
    // Table index operations
    // ========================================================================

    /**
     * @brief Create a table index.
     *
     * Create an index on a table field for faster search operations. Supports
     * vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes
     * (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.
     *
     * @param request The create table index request containing the table ID,
     *                index name, type, and configuration.
     * @return The created index information including UUID.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableIndexAlreadyExistsError If an index with the same name already exists.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<CreateTableIndexResponse> create_table_index(
        std::shared_ptr<CreateTableIndexRequest> request) {
        throw UnsupportedOperationError("Not supported: create_table_index");
    }

    /**
     * @brief Create a scalar index on a table.
     *
     * Create a scalar index on a table field for faster filtering operations.
     * Supports BTREE, BITMAP, LABEL_LIST, FTS, etc. This is an alias for
     * create_table_index specifically for scalar indexes.
     *
     * @param request The create table index request containing the table ID,
     *                index name, type, and configuration.
     * @return The created scalar index information including UUID.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableIndexAlreadyExistsError If an index with the same name already exists.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<CreateTableScalarIndexResponse> create_table_scalar_index(
        std::shared_ptr<CreateTableIndexRequest> request) {
        throw UnsupportedOperationError("Not supported: create_table_scalar_index");
    }

    /**
     * @brief List table indices.
     *
     * @param request The list table indices request containing the table ID
     *                and pagination parameters.
     * @return List of indices with their name, type, status, and UUID.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<ListTableIndicesResponse> list_table_indices(
        std::shared_ptr<ListTableIndicesRequest> request) {
        throw UnsupportedOperationError("Not supported: list_table_indices");
    }

    /**
     * @brief Describe table index statistics.
     *
     * Get statistics for a specific index on a table, including index type,
     * distance type (for vector indices), and row counts.
     *
     * @param request The describe table index stats request containing the
     *                table ID and index name.
     * @return Index statistics including type, distance type, and row count.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableIndexNotFoundError If the index does not exist.
     */
    virtual std::shared_ptr<DescribeTableIndexStatsResponse> describe_table_index_stats(
        std::shared_ptr<DescribeTableIndexStatsRequest> request) {
        throw UnsupportedOperationError("Not supported: describe_table_index_stats");
    }

    /**
     * @brief Drop a table index.
     *
     * @param request The drop table index request containing the table ID
     *                and index name.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableIndexNotFoundError If the index does not exist.
     */
    virtual std::shared_ptr<DropTableIndexResponse> drop_table_index(
        std::shared_ptr<DropTableIndexRequest> request) {
        throw UnsupportedOperationError("Not supported: drop_table_index");
    }

    // ========================================================================
    // Table version operations
    // ========================================================================

    /**
     * @brief List all versions of a table.
     *
     * @param request The list table versions request containing the table ID
     *                and pagination parameters.
     * @return List of table versions with their manifest paths and metadata.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<ListTableVersionsResponse> list_table_versions(
        std::shared_ptr<ListTableVersionsRequest> request) {
        throw UnsupportedOperationError("Not supported: list_table_versions");
    }

    /**
     * @brief Create a new table version entry.
     *
     * This operation supports put_if_not_exists semantics: the operation fails
     * if the version already exists.
     *
     * @param request The create table version request containing the table ID,
     *                version number, and manifest path.
     * @return The created version information.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If the version already exists.
     */
    virtual std::shared_ptr<CreateTableVersionResponse> create_table_version(
        std::shared_ptr<CreateTableVersionRequest> request) {
        throw UnsupportedOperationError("Not supported: create_table_version");
    }

    /**
     * @brief Describe a specific table version.
     *
     * Returns the manifest path and metadata for the specified version.
     *
     * @param request The describe table version request containing the table ID
     *                and version number.
     * @return Version details including manifest path and timestamp.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     */
    virtual std::shared_ptr<DescribeTableVersionResponse> describe_table_version(
        std::shared_ptr<DescribeTableVersionRequest> request) {
        throw UnsupportedOperationError("Not supported: describe_table_version");
    }

    /**
     * @brief Delete table version metadata records.
     *
     * This deletes version tracking records, NOT the actual table data.
     * It supports deleting ranges of versions for efficient bulk cleanup.
     *
     * @param request The batch delete request containing the table ID and
     *                version range (start_version, end_version).
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<BatchDeleteTableVersionsResponse> batch_delete_table_versions(
        std::shared_ptr<BatchDeleteTableVersionsRequest> request) {
        throw UnsupportedOperationError("Not supported: batch_delete_table_versions");
    }

    /**
     * @brief Atomically create new version entries for multiple tables.
     *
     * This operation is atomic: either all table versions are created
     * successfully, or none are created. Each entry supports
     * put_if_not_exists semantics.
     *
     * @param request The batch create request containing multiple table
     *                version entries.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If any namespace does not exist.
     * @throws TableNotFoundError If any table does not exist.
     * @throws ConcurrentModificationError If any version already exists.
     */
    virtual std::shared_ptr<BatchCreateTableVersionsResponse> batch_create_table_versions(
        std::shared_ptr<BatchCreateTableVersionsRequest> request) {
        throw UnsupportedOperationError("Not supported: batch_create_table_versions");
    }

    /**
     * @brief Atomically commit a batch of mixed table operations.
     *
     * This is a generalized version of batch_create_table_versions that supports
     * mixed operation types (DeclareTable, CreateTableVersion,
     * DeleteTableVersions, DeregisterTable) within a single atomic transaction
     * at the metadata layer.
     *
     * @param request The batch commit request containing multiple operations.
     * @return Results for each operation in the batch.
     * @throws NamespaceNotFoundError If any namespace does not exist.
     * @throws TableNotFoundError If any table does not exist.
     * @throws ConcurrentModificationError If any operation conflicts.
     */
    virtual std::shared_ptr<BatchCommitTablesResponse> batch_commit_tables(
        std::shared_ptr<BatchCommitTablesRequest> request) {
        throw UnsupportedOperationError("Not supported: batch_commit_tables");
    }

    // ========================================================================
    // Table schema operations
    // ========================================================================

    /**
     * @brief Add columns to a table.
     *
     * Add new columns to a table using SQL expressions or default values.
     *
     * @param request The add columns request containing the table ID and
     *                column definitions.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws TableSchemaValidationError If the schema validation fails.
     */
    virtual std::shared_ptr<AlterTableAddColumnsResponse> alter_table_add_columns(
        std::shared_ptr<AlterTableAddColumnsRequest> request) {
        throw UnsupportedOperationError("Not supported: alter_table_add_columns");
    }

    /**
     * @brief Alter columns in a table.
     *
     * Modify existing columns in a table, such as renaming or changing
     * data types.
     *
     * @param request The alter columns request containing the table ID and
     *                column modifications.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     * @throws TableSchemaValidationError If the schema validation fails.
     */
    virtual std::shared_ptr<AlterTableAlterColumnsResponse> alter_table_alter_columns(
        std::shared_ptr<AlterTableAlterColumnsRequest> request) {
        throw UnsupportedOperationError("Not supported: alter_table_alter_columns");
    }

    /**
     * @brief Drop columns from a table.
     *
     * @param request The drop columns request containing the table ID and
     *                column names to remove.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableColumnNotFoundError If a referenced column does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<AlterTableDropColumnsResponse> alter_table_drop_columns(
        std::shared_ptr<AlterTableDropColumnsRequest> request) {
        throw UnsupportedOperationError("Not supported: alter_table_drop_columns");
    }

    /**
     * @brief Trigger an async backfill job for a computed column.
     *
     * @param request The backfill columns request containing the table ID and
     *                column names to backfill.
     * @return Response containing the async job ID.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<AlterTableBackfillColumnsResponse> alter_table_backfill_columns(
        std::shared_ptr<AlterTableBackfillColumnsRequest> request) {
        throw UnsupportedOperationError("Not supported: alter_table_backfill_columns");
    }

    // ========================================================================
    // Table metadata operations
    // ========================================================================

    /**
     * @brief Update table schema metadata.
     *
     * Replace the schema metadata with the provided key-value pairs.
     *
     * @param request The update schema metadata request containing the table ID
     *                and metadata key-value pairs.
     * @return The updated metadata.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<UpdateTableSchemaMetadataResponse> update_table_schema_metadata(
        std::shared_ptr<UpdateTableSchemaMetadataRequest> request) {
        throw UnsupportedOperationError("Not supported: update_table_schema_metadata");
    }

    /**
     * @brief Update per-field metadata.
     *
     * Each entry targets a field by path and merges the provided key-value
     * pairs into that field's existing metadata, or replaces it when the
     * replace flag is true. A null metadata value deletes that key.
     *
     * @param request The update field metadata request containing the table ID
     *                and field metadata entries.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<UpdateFieldMetadataResponse> update_field_metadata(
        std::shared_ptr<UpdateFieldMetadataRequest> request) {
        throw UnsupportedOperationError("Not supported: update_field_metadata");
    }

    /**
     * @brief Get table statistics.
     *
     * Get statistics for a table, including row counts, data sizes,
     * and column statistics.
     *
     * @param request The get table stats request containing the table ID.
     * @return Table statistics including row count, version count, and column stats.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<GetTableStatsResponse> get_table_stats(
        std::shared_ptr<GetTableStatsRequest> request) {
        throw UnsupportedOperationError("Not supported: get_table_stats");
    }

    // ========================================================================
    // Query plan operations
    // ========================================================================

    /**
     * @brief Explain a table query plan.
     *
     * Get a human-readable explanation of how a query will be executed.
     *
     * @param request The explain query plan request containing the table ID
     *                and query definition.
     * @return A human-readable query plan explanation string.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::string explain_table_query_plan(
        std::shared_ptr<ExplainTableQueryPlanRequest> request) {
        throw UnsupportedOperationError("Not supported: explain_table_query_plan");
    }

    /**
     * @brief Analyze a table query plan.
     *
     * Execute the query and return detailed statistics and analysis of
     * the query execution plan.
     *
     * @param request The analyze query plan request containing the table ID
     *                and query definition.
     * @return A detailed analysis string with execution statistics.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::string analyze_table_query_plan(
        std::shared_ptr<AnalyzeTableQueryPlanRequest> request) {
        throw UnsupportedOperationError("Not supported: analyze_table_query_plan");
    }

    // ========================================================================
    // Table tag operations
    // ========================================================================

    /**
     * @brief List all tags for a table.
     *
     * @param request The list table tags request containing the table ID
     *                and pagination parameters.
     * @return List of tags with their names and corresponding versions.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<ListTableTagsResponse> list_table_tags(
        std::shared_ptr<ListTableTagsRequest> request) {
        throw UnsupportedOperationError("Not supported: list_table_tags");
    }

    /**
     * @brief Get the version for a specific tag.
     *
     * @param request The get table tag version request containing the table ID
     *                and tag name.
     * @return The version number that the tag points to.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableTagNotFoundError If the tag does not exist.
     */
    virtual std::shared_ptr<GetTableTagVersionResponse> get_table_tag_version(
        std::shared_ptr<GetTableTagVersionRequest> request) {
        throw UnsupportedOperationError("Not supported: get_table_tag_version");
    }

    /**
     * @brief Create a tag for a table.
     *
     * Create a new tag that points to a specific version.
     *
     * @param request The create table tag request containing the table ID,
     *                tag name, and target version.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableTagAlreadyExistsError If a tag with the same name already exists.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<CreateTableTagResponse> create_table_tag(
        std::shared_ptr<CreateTableTagRequest> request) {
        throw UnsupportedOperationError("Not supported: create_table_tag");
    }

    /**
     * @brief Delete a tag from a table.
     *
     * @param request The delete table tag request containing the table ID
     *                and tag name.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableTagNotFoundError If the tag does not exist.
     */
    virtual std::shared_ptr<DeleteTableTagResponse> delete_table_tag(
        std::shared_ptr<DeleteTableTagRequest> request) {
        throw UnsupportedOperationError("Not supported: delete_table_tag");
    }

    /**
     * @brief Update a tag to point to a different version.
     *
     * @param request The update table tag request containing the table ID,
     *                tag name, and new target version.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableTagNotFoundError If the tag does not exist.
     * @throws TableVersionNotFoundError If the specified version does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<UpdateTableTagResponse> update_table_tag(
        std::shared_ptr<UpdateTableTagRequest> request) {
        throw UnsupportedOperationError("Not supported: update_table_tag");
    }

    // ========================================================================
    // Table branch operations
    // ========================================================================

    /**
     * @brief Create a branch for a table.
     *
     * Create a new branch starting from a source ref (another branch and/or
     * version), defaulting to the latest version of the main branch.
     *
     * @param request The create table branch request containing the table ID,
     *                branch name, and optional source version/branch.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     * @throws TableVersionNotFoundError If the source version does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<CreateTableBranchResponse> create_table_branch(
        std::shared_ptr<CreateTableBranchRequest> request) {
        throw UnsupportedOperationError("Not supported: create_table_branch");
    }

    /**
     * @brief List all branches for a table.
     *
     * @param request The list table branches request containing the table ID
     *                and pagination parameters.
     * @return List of branches with their names and version information.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<ListTableBranchesResponse> list_table_branches(
        std::shared_ptr<ListTableBranchesRequest> request) {
        throw UnsupportedOperationError("Not supported: list_table_branches");
    }

    /**
     * @brief Delete a branch from a table.
     *
     * @param request The delete table branch request containing the table ID
     *                and branch name.
     * @return Empty response on success.
     * @throws NamespaceNotFoundError If the namespace does not exist.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<DeleteTableBranchResponse> delete_table_branch(
        std::shared_ptr<DeleteTableBranchRequest> request) {
        throw UnsupportedOperationError("Not supported: delete_table_branch");
    }

    // ========================================================================
    // Materialized view operations
    // ========================================================================

    /**
     * @brief Create a materialized view.
     *
     * The view may be query-backed, UDTF-backed, or chunker-backed,
     * controlled by the kind discriminator.
     *
     * @param request The create materialized view request containing the view ID,
     *                kind, query, and optional properties.
     * @return The created materialized view information.
     * @throws TableAlreadyExistsError If a table with the same identifier already exists.
     */
    virtual std::shared_ptr<CreateMaterializedViewResponse> create_materialized_view(
        std::shared_ptr<CreateMaterializedViewRequest> request) {
        throw UnsupportedOperationError("Not supported: create_materialized_view");
    }

    /**
     * @brief Trigger an async materialized view refresh.
     *
     * @param request The refresh materialized view request containing the view ID
     *                and optional source task size.
     * @return Response containing the async job ID.
     * @throws TableNotFoundError If the table does not exist.
     */
    virtual std::shared_ptr<RefreshMaterializedViewResponse> refresh_materialized_view(
        std::shared_ptr<RefreshMaterializedViewRequest> request) {
        throw UnsupportedOperationError("Not supported: refresh_materialized_view");
    }

    // ========================================================================
    // Transaction operations
    // ========================================================================

    /**
     * @brief Describe a transaction.
     *
     * @param request The describe transaction request containing the transaction ID.
     * @return Transaction details including status and timestamps.
     * @throws TransactionNotFoundError If the transaction does not exist.
     */
    virtual std::shared_ptr<DescribeTransactionResponse> describe_transaction(
        std::shared_ptr<DescribeTransactionRequest> request) {
        throw UnsupportedOperationError("Not supported: describe_transaction");
    }

    /**
     * @brief Alter a transaction.
     *
     * Modify a transaction's status, set/unset properties, or perform
     * other alterations.
     *
     * @param request The alter transaction request containing the transaction ID
     *                and the alteration to perform.
     * @return The updated transaction information.
     * @throws TransactionNotFoundError If the transaction does not exist.
     * @throws ConcurrentModificationError If a concurrent modification conflict occurs.
     */
    virtual std::shared_ptr<AlterTransactionResponse> alter_transaction(
        std::shared_ptr<AlterTransactionRequest> request) {
        throw UnsupportedOperationError("Not supported: alter_transaction");
    }
};

}  // namespace lance_namespace

#endif  // LANCE_NAMESPACE_NAMESPACE_OPERATIONS_HPP
