/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lance.namespace;

import org.lance.namespace.errors.UnsupportedOperationException;
import org.lance.namespace.model.*;

import org.apache.arrow.memory.BufferAllocator;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface for LanceDB namespace operations.
 *
 * <p>A namespace provides hierarchical organization for tables and supports various storage
 * backends (local filesystem, S3, Azure, GCS) with optional credential vending for cloud providers.
 *
 * <p>Implementations of this interface can provide different storage backends. Native
 * implementations (DirectoryNamespace, RestNamespace) are provided by the lance package. External
 * libraries can implement this interface to provide integration with catalog systems like AWS Glue,
 * Hive Metastore, or Databricks Unity Catalog.
 *
 * <p>Most methods have default implementations that throw {@link
 * org.lance.namespace.errors.UnsupportedOperationException}. Implementations should override the
 * methods they support.
 *
 * <p>Use {@link #connect(String, Map, BufferAllocator)} to create namespace instances, and {@link
 * #registerNamespaceImpl(String, String)} to register external implementations.
 *
 * <h2>Error Handling</h2>
 *
 * <p>All operations may throw exceptions from the {@link org.lance.namespace.errors} package.
 * Common errors that may be thrown by any operation include:
 *
 * <ul>
 *   <li>{@link org.lance.namespace.errors.UnsupportedOperationException} - operation not supported
 *   <li>{@link org.lance.namespace.errors.InvalidInputException} - invalid request parameters
 *   <li>{@link org.lance.namespace.errors.PermissionDeniedException} - insufficient permissions
 *   <li>{@link org.lance.namespace.errors.UnauthenticatedException} - invalid credentials
 *   <li>{@link org.lance.namespace.errors.ServiceUnavailableException} - service unavailable
 *   <li>{@link org.lance.namespace.errors.InternalException} - unexpected internal error
 * </ul>
 *
 * <p>See individual method documentation for operation-specific errors.
 */
public interface LanceNamespace {

  // ========== Static Registry and Factory Methods ==========

  /** Native implementations (provided by lance package). */
  Map<String, String> NATIVE_IMPLS =
      Collections.unmodifiableMap(
          new HashMap<String, String>() {
            {
              put("dir", "org.lance.namespace.DirectoryNamespace");
              put("rest", "org.lance.namespace.RestNamespace");
            }
          });

  /** Plugin registry for external implementations. Thread-safe for concurrent access. */
  Map<String, String> REGISTERED_IMPLS = new ConcurrentHashMap<>();

  /**
   * Register a namespace implementation with a short name.
   *
   * <p>External libraries can use this to register their implementations, allowing users to use
   * short names like "glue" instead of full class paths.
   *
   * @param name Short name for the implementation (e.g., "glue", "hive2", "unity")
   * @param className Full class name (e.g., "org.lance.namespace.glue.GlueNamespace")
   */
  static void registerNamespaceImpl(String name, String className) {
    REGISTERED_IMPLS.put(name, className);
  }

  /**
   * Unregister a previously registered namespace implementation.
   *
   * @param name Short name of the implementation to unregister
   * @return true if an implementation was removed, false if it wasn't registered
   */
  static boolean unregisterNamespaceImpl(String name) {
    return REGISTERED_IMPLS.remove(name) != null;
  }

  /**
   * Check if an implementation is registered with the given name.
   *
   * @param name Short name or class name to check
   * @return true if the implementation is available
   */
  static boolean isRegistered(String name) {
    return NATIVE_IMPLS.containsKey(name) || REGISTERED_IMPLS.containsKey(name);
  }

  /**
   * Connect to a Lance namespace implementation.
   *
   * <p>This factory method creates namespace instances based on implementation aliases or full
   * class names. It provides a unified way to instantiate different namespace backends.
   *
   * @param impl Implementation alias or full class name. Built-in aliases: "dir" for
   *     DirectoryNamespace, "rest" for RestNamespace (provided by lance package). External
   *     libraries can register additional aliases using {@link #registerNamespaceImpl(String,
   *     String)}.
   * @param properties Configuration properties passed to the namespace
   * @param allocator Arrow buffer allocator for memory management
   * @return The connected namespace instance
   * @throws IllegalArgumentException If the implementation class cannot be loaded or does not
   *     implement LanceNamespace interface
   */
  static LanceNamespace connect(
      String impl, Map<String, String> properties, BufferAllocator allocator) {
    // Check native impls first, then registered plugins, then treat as full class name
    String className = NATIVE_IMPLS.get(impl);
    if (className == null) {
      className = REGISTERED_IMPLS.get(impl);
    }
    if (className == null) {
      className = impl;
    }

    try {
      Class<?> clazz = Class.forName(className);

      if (!LanceNamespace.class.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException(
            "Class " + className + " does not implement LanceNamespace interface");
      }

      @SuppressWarnings("unchecked")
      Class<? extends LanceNamespace> namespaceClass = (Class<? extends LanceNamespace>) clazz;

      Constructor<? extends LanceNamespace> constructor = namespaceClass.getConstructor();
      LanceNamespace namespace = constructor.newInstance();
      namespace.initialize(properties, allocator);

      return namespace;
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Namespace implementation class not found: " + className);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          "Namespace implementation class " + className + " must have a no-arg constructor");
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Failed to construct namespace impl " + className + ": " + e.getMessage(), e);
    }
  }

  // ========== Instance Methods ==========

  /**
   * Initialize the namespace with configuration properties.
   *
   * @param configProperties Configuration properties (e.g., root path, storage options)
   * @param allocator Arrow buffer allocator for memory management
   */
  void initialize(Map<String, String> configProperties, BufferAllocator allocator);

  /**
   * Return a human-readable unique identifier for this namespace instance.
   *
   * <p>This is used for equality comparison and caching. Two namespace instances with the same ID
   * are considered equal and will share cached resources.
   *
   * @return A human-readable unique identifier string
   */
  String namespaceId();

  // Namespace operations

  /**
   * List namespaces.
   *
   * @param request The list namespaces request
   * @return The list namespaces response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the parent namespace does not
   *     exist
   */
  default ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    throw new UnsupportedOperationException("Not supported: listNamespaces");
  }

  /**
   * Describe a namespace.
   *
   * @param request The describe namespace request
   * @return The describe namespace response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   */
  default DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: describeNamespace");
  }

  /**
   * Create a new namespace.
   *
   * @param request The create namespace request
   * @return The create namespace response
   * @throws org.lance.namespace.errors.NamespaceAlreadyExistsException if a namespace with the same
   *     name already exists
   */
  default CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: createNamespace");
  }

  /**
   * Drop a namespace.
   *
   * @param request The drop namespace request
   * @return The drop namespace response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   * @throws org.lance.namespace.errors.NamespaceNotEmptyException if the namespace contains tables
   *     or child namespaces
   */
  default DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: dropNamespace");
  }

  /**
   * Check if a namespace exists.
   *
   * @param request The namespace exists request
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   */
  default void namespaceExists(NamespaceExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: namespaceExists");
  }

  // Table operations

  /**
   * List tables in a namespace.
   *
   * @param request The list tables request
   * @return The list tables response
   */
  default ListTablesResponse listTables(ListTablesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTables");
  }

  /**
   * Describe a table.
   *
   * @param request The describe table request
   * @return The describe table response
   */
  default DescribeTableResponse describeTable(DescribeTableRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTable");
  }

  /**
   * Register a table.
   *
   * @param request The register table request
   * @return The register table response
   */
  default RegisterTableResponse registerTable(RegisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: registerTable");
  }

  /**
   * Check if a table exists.
   *
   * @param request The table exists request
   * @throws RuntimeException if the table does not exist
   */
  default void tableExists(TableExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: tableExists");
  }

  /**
   * Drop a table.
   *
   * @param request The drop table request
   * @return The drop table response
   */
  default DropTableResponse dropTable(DropTableRequest request) {
    throw new UnsupportedOperationException("Not supported: dropTable");
  }

  /**
   * Deregister a table.
   *
   * @param request The deregister table request
   * @return The deregister table response
   */
  default DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deregisterTable");
  }

  /**
   * Count rows in a table.
   *
   * @param request The count table rows request
   * @return The row count
   */
  default Long countTableRows(CountTableRowsRequest request) {
    throw new UnsupportedOperationException("Not supported: countTableRows");
  }

  // Data operations

  /**
   * Create a new table with data from Arrow IPC stream.
   *
   * @param request The create table request
   * @param requestData Arrow IPC stream data
   * @return The create table response
   */
  default CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: createTable");
  }

  /**
   * Declare a table (metadata only operation).
   *
   * @param request The declare table request
   * @return The declare table response
   */
  default DeclareTableResponse declareTable(DeclareTableRequest request) {
    throw new UnsupportedOperationException("Not supported: declareTable");
  }

  /**
   * Insert data into a table.
   *
   * @param request The insert into table request
   * @param requestData Arrow IPC stream data
   * @return The insert into table response
   */
  default InsertIntoTableResponse insertIntoTable(
      InsertIntoTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: insertIntoTable");
  }

  /**
   * Merge insert data into a table.
   *
   * @param request The merge insert into table request
   * @param requestData Arrow IPC stream data
   * @return The merge insert into table response
   */
  default MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: mergeInsertIntoTable");
  }

  /**
   * Update a table.
   *
   * @param request The update table request
   * @return The update table response
   */
  default UpdateTableResponse updateTable(UpdateTableRequest request) {
    throw new UnsupportedOperationException("Not supported: updateTable");
  }

  /**
   * Delete from a table.
   *
   * @param request The delete from table request
   * @return The delete from table response
   */
  default DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deleteFromTable");
  }

  /**
   * Query a table.
   *
   * @param request The query table request
   * @return Arrow IPC stream data containing query results
   */
  default byte[] queryTable(QueryTableRequest request) {
    throw new UnsupportedOperationException("Not supported: queryTable");
  }

  // Index operations

  /**
   * Create a table index.
   *
   * @param request The create table index request
   * @return The create table index response
   */
  default CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableIndex");
  }

  /**
   * Create a scalar index on a table.
   *
   * @param request The create table index request
   * @return The create table scalar index response
   */
  default CreateTableScalarIndexResponse createTableScalarIndex(CreateTableIndexRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableScalarIndex");
  }

  /**
   * List table indices.
   *
   * @param request The list table indices request
   * @return The list table indices response
   */
  default ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableIndices");
  }

  /**
   * Describe table index statistics.
   *
   * @param request The describe table index stats request
   * @param indexName The name of the index
   * @return The describe table index stats response
   */
  default DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    throw new UnsupportedOperationException("Not supported: describeTableIndexStats");
  }

  /**
   * Drop a table index.
   *
   * @param request The drop table index request
   * @param indexName The name of the index
   * @return The drop table index response
   */
  default DropTableIndexResponse dropTableIndex(DropTableIndexRequest request, String indexName) {
    throw new UnsupportedOperationException("Not supported: dropTableIndex");
  }

  // Table version and schema operations

  /**
   * List all tables across all namespaces.
   *
   * @param request The list tables request
   * @return The list tables response
   */
  default ListTablesResponse listAllTables(ListTablesRequest request) {
    throw new UnsupportedOperationException("Not supported: listAllTables");
  }

  /**
   * Restore a table to a specific version.
   *
   * @param request The restore table request
   * @return The restore table response
   */
  default RestoreTableResponse restoreTable(RestoreTableRequest request) {
    throw new UnsupportedOperationException("Not supported: restoreTable");
  }

  /**
   * Rename a table.
   *
   * @param request The rename table request
   * @return The rename table response
   */
  default RenameTableResponse renameTable(RenameTableRequest request) {
    throw new UnsupportedOperationException("Not supported: renameTable");
  }

  /**
   * List all versions of a table.
   *
   * @param request The list table versions request
   * @return The list table versions response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if the table does not exist
   */
  default ListTableVersionsResponse listTableVersions(ListTableVersionsRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableVersions");
  }

  /**
   * Create a new table version entry.
   *
   * <p>This operation supports put_if_not_exists semantics, where the operation fails if the
   * version already exists.
   *
   * @param request The create table version request
   * @return The create table version response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if the table does not exist
   * @throws org.lance.namespace.errors.ConcurrentModificationException if the version already
   *     exists
   */
  default CreateTableVersionResponse createTableVersion(CreateTableVersionRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableVersion");
  }

  /**
   * Describe a specific table version.
   *
   * <p>Returns the manifest path and metadata for the specified version.
   *
   * @param request The describe table version request containing the version number
   * @return The describe table version response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if the table does not exist
   * @throws org.lance.namespace.errors.TableVersionNotFoundException if the version does not exist
   */
  default DescribeTableVersionResponse describeTableVersion(DescribeTableVersionRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTableVersion");
  }

  /**
   * Delete table version metadata records.
   *
   * <p>This operation deletes version tracking records, NOT the actual table data. It supports
   * deleting ranges of versions for efficient bulk cleanup.
   *
   * @param request The batch delete table versions request
   * @return The batch delete table versions response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if the namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if the table does not exist
   */
  default BatchDeleteTableVersionsResponse batchDeleteTableVersions(
      BatchDeleteTableVersionsRequest request) {
    throw new UnsupportedOperationException("Not supported: batchDeleteTableVersions");
  }

  /**
   * Atomically create new version entries for multiple tables.
   *
   * <p>This operation is atomic: either all table versions are created successfully, or none are
   * created. If any version creation fails (e.g., due to conflict), the entire batch operation
   * fails.
   *
   * <p>Each entry in the request specifies the table identifier and version details. This supports
   * put_if_not_exists semantics for each version entry.
   *
   * @param request The batch create table versions request
   * @return The batch create table versions response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if any namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if any table does not exist
   * @throws org.lance.namespace.errors.ConcurrentModificationException if any version already
   *     exists
   */
  default BatchCreateTableVersionsResponse batchCreateTableVersions(
      BatchCreateTableVersionsRequest request) {
    throw new UnsupportedOperationException("Not supported: batchCreateTableVersions");
  }

  /**
   * Atomically commit a batch of mixed table operations.
   *
   * <p>This is a generalized version of {@link
   * #batchCreateTableVersions(BatchCreateTableVersionsRequest)} that supports mixed operation types
   * (DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable) within a single atomic
   * transaction at the metadata layer.
   *
   * <p>All operations are committed atomically: either all succeed or none are applied.
   *
   * @param request The batch commit tables request
   * @return The batch commit tables response
   * @throws org.lance.namespace.errors.NamespaceNotFoundException if any namespace does not exist
   * @throws org.lance.namespace.errors.TableNotFoundException if any table does not exist
   * @throws org.lance.namespace.errors.ConcurrentModificationException if any operation conflicts
   */
  default BatchCommitTablesResponse batchCommitTables(BatchCommitTablesRequest request) {
    throw new UnsupportedOperationException("Not supported: batchCommitTables");
  }

  /**
   * Update table schema metadata.
   *
   * @param request The update table schema metadata request
   * @return The update table schema metadata response
   */
  default UpdateTableSchemaMetadataResponse updateTableSchemaMetadata(
      UpdateTableSchemaMetadataRequest request) {
    throw new UnsupportedOperationException("Not supported: updateTableSchemaMetadata");
  }

  /**
   * Update per-field metadata.
   *
   * @param request The update field metadata request
   * @return The update field metadata response
   */
  default UpdateFieldMetadataResponse updateFieldMetadata(UpdateFieldMetadataRequest request) {
    throw new UnsupportedOperationException("Not supported: updateFieldMetadata");
  }

  /**
   * Get table statistics.
   *
   * @param request The get table stats request
   * @return The get table stats response
   */
  default GetTableStatsResponse getTableStats(GetTableStatsRequest request) {
    throw new UnsupportedOperationException("Not supported: getTableStats");
  }

  // Query plan operations

  /**
   * Explain a table query plan.
   *
   * @param request The explain table query plan request
   * @return The query plan explanation as a string
   */
  default String explainTableQueryPlan(ExplainTableQueryPlanRequest request) {
    throw new UnsupportedOperationException("Not supported: explainTableQueryPlan");
  }

  /**
   * Analyze a table query plan.
   *
   * @param request The analyze table query plan request
   * @return The query plan analysis as a string
   */
  default String analyzeTableQueryPlan(AnalyzeTableQueryPlanRequest request) {
    throw new UnsupportedOperationException("Not supported: analyzeTableQueryPlan");
  }

  // Column operations

  /**
   * Add columns to a table.
   *
   * @param request The alter table add columns request
   * @return The alter table add columns response
   */
  default AlterTableAddColumnsResponse alterTableAddColumns(AlterTableAddColumnsRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTableAddColumns");
  }

  /**
   * Alter columns in a table.
   *
   * @param request The alter table alter columns request
   * @return The alter table alter columns response
   */
  default AlterTableAlterColumnsResponse alterTableAlterColumns(
      AlterTableAlterColumnsRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTableAlterColumns");
  }

  /**
   * Trigger an async backfill job for a computed column.
   *
   * @param request The backfill columns request
   * @return The backfill columns response containing a job ID
   */
  default AlterTableBackfillColumnsResponse alterTableBackfillColumns(
      AlterTableBackfillColumnsRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTableBackfillColumns");
  }

  /**
   * Trigger an async materialized view refresh.
   *
   * @param request The refresh materialized view request
   * @return The refresh response containing a job ID
   */
  default RefreshMaterializedViewResponse refreshMaterializedView(
      RefreshMaterializedViewRequest request) {
    throw new UnsupportedOperationException("Not supported: refreshMaterializedView");
  }

  /**
   * Create a materialized view (query / UDTF / chunker) backed by a stored UDTF/chunker spec and an
   * optional initial refresh.
   *
   * @param request The create materialized view request
   * @return The create response containing the view's location and an optional job ID
   */
  default CreateMaterializedViewResponse createMaterializedView(
      CreateMaterializedViewRequest request) {
    throw new UnsupportedOperationException("Not supported: createMaterializedView");
  }

  /**
   * Drop columns from a table.
   *
   * @param request The alter table drop columns request
   * @return The alter table drop columns response
   */
  default AlterTableDropColumnsResponse alterTableDropColumns(
      AlterTableDropColumnsRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTableDropColumns");
  }

  // Tag operations

  /**
   * List all tags for a table.
   *
   * @param request The list table tags request
   * @return The list table tags response
   */
  default ListTableTagsResponse listTableTags(ListTableTagsRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableTags");
  }

  /**
   * Get the version for a specific tag.
   *
   * @param request The get table tag version request
   * @return The get table tag version response
   */
  default GetTableTagVersionResponse getTableTagVersion(GetTableTagVersionRequest request) {
    throw new UnsupportedOperationException("Not supported: getTableTagVersion");
  }

  /**
   * Create a tag for a table.
   *
   * @param request The create table tag request
   * @return The create table tag response
   */
  default CreateTableTagResponse createTableTag(CreateTableTagRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableTag");
  }

  /**
   * Delete a tag from a table.
   *
   * @param request The delete table tag request
   * @return The delete table tag response
   */
  default DeleteTableTagResponse deleteTableTag(DeleteTableTagRequest request) {
    throw new UnsupportedOperationException("Not supported: deleteTableTag");
  }

  /**
   * Update a tag for a table.
   *
   * @param request The update table tag request
   * @return The update table tag response
   */
  default UpdateTableTagResponse updateTableTag(UpdateTableTagRequest request) {
    throw new UnsupportedOperationException("Not supported: updateTableTag");
  }

  // Branch operations

  /**
   * Create a branch for a table.
   *
   * @param request The create table branch request
   * @return The create table branch response
   */
  default CreateTableBranchResponse createTableBranch(CreateTableBranchRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableBranch");
  }

  /**
   * List all branches for a table.
   *
   * @param request The list table branches request
   * @return The list table branches response
   */
  default ListTableBranchesResponse listTableBranches(ListTableBranchesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableBranches");
  }

  /**
   * Delete a branch from a table.
   *
   * @param request The delete table branch request
   * @return The delete table branch response
   */
  default DeleteTableBranchResponse deleteTableBranch(DeleteTableBranchRequest request) {
    throw new UnsupportedOperationException("Not supported: deleteTableBranch");
  }

  // Transaction operations

  /**
   * Describe a transaction.
   *
   * @param request The describe transaction request
   * @return The describe transaction response
   */
  default DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTransaction");
  }

  /**
   * Alter a transaction.
   *
   * @param request The alter transaction request
   * @return The alter transaction response
   */
  default AlterTransactionResponse alterTransaction(AlterTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTransaction");
  }
}
