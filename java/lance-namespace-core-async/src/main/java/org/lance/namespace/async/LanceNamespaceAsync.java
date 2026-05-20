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
package org.lance.namespace.async;

import org.lance.namespace.async.errors.UnsupportedOperationException;
import org.lance.namespace.model.*;

import org.apache.arrow.memory.BufferAllocator;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Async interface for LanceDB namespace operations.
 *
 * <p>A namespace provides hierarchical organization for tables and supports various storage
 * backends (local filesystem, S3, Azure, GCS) with optional credential vending for cloud providers.
 *
 * <p>This is the async version of LanceNamespace, where all operations return {@link
 * CompletableFuture} for non-blocking execution.
 *
 * <p>Implementations of this interface can provide different storage backends. Native
 * implementations (DirectoryNamespace, RestNamespace) are provided by the lance package. External
 * libraries can implement this interface to provide integration with catalog systems like AWS Glue,
 * Hive Metastore, or Databricks Unity Catalog.
 *
 * <p>Most methods have default implementations that return failed futures with {@link
 * org.lance.namespace.async.errors.UnsupportedOperationException}. Implementations should override
 * the methods they support.
 *
 * <p>Use {@link #connect(String, Map, BufferAllocator)} to create namespace instances, and {@link
 * #registerNamespaceImpl(String, String)} to register external implementations.
 *
 * <h2>Error Handling</h2>
 *
 * <p>All operations return CompletableFuture that may complete exceptionally with exceptions from
 * the {@link org.lance.namespace.async.errors} package. Common errors include:
 *
 * <ul>
 *   <li>{@link org.lance.namespace.async.errors.UnsupportedOperationException} - operation not
 *       supported
 *   <li>{@link org.lance.namespace.async.errors.InvalidInputException} - invalid request parameters
 *   <li>{@link org.lance.namespace.async.errors.PermissionDeniedException} - insufficient
 *       permissions
 *   <li>{@link org.lance.namespace.async.errors.UnauthenticatedException} - invalid credentials
 *   <li>{@link org.lance.namespace.async.errors.ServiceUnavailableException} - service unavailable
 *   <li>{@link org.lance.namespace.async.errors.InternalException} - unexpected internal error
 * </ul>
 *
 * <p>See individual method documentation for operation-specific errors.
 */
public interface LanceNamespaceAsync {

  // ========== Static Registry and Factory Methods ==========

  /** Native implementations (provided by lance package). */
  Map<String, String> NATIVE_IMPLS =
      Collections.unmodifiableMap(
          new HashMap<String, String>() {
            {
              put("dir", "org.lance.namespace.async.DirectoryNamespaceAsync");
              put("rest", "org.lance.namespace.async.RestNamespaceAsync");
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
   * @param className Full class name (e.g., "org.lance.namespace.async.glue.GlueNamespaceAsync")
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
   *     DirectoryNamespaceAsync, "rest" for RestNamespaceAsync (provided by lance package).
   *     External libraries can register additional aliases using {@link
   *     #registerNamespaceImpl(String, String)}.
   * @param properties Configuration properties passed to the namespace
   * @param allocator Arrow buffer allocator for memory management
   * @return The connected namespace instance
   * @throws IllegalArgumentException If the implementation class cannot be loaded or does not
   *     implement LanceNamespaceAsync interface
   */
  static LanceNamespaceAsync connect(
      String impl, Map<String, String> properties, BufferAllocator allocator) {
    String className = NATIVE_IMPLS.get(impl);
    if (className == null) {
      className = REGISTERED_IMPLS.get(impl);
    }
    if (className == null) {
      className = impl;
    }

    try {
      Class<?> clazz = Class.forName(className);

      if (!LanceNamespaceAsync.class.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException(
            "Class " + className + " does not implement LanceNamespaceAsync interface");
      }

      @SuppressWarnings("unchecked")
      Class<? extends LanceNamespaceAsync> namespaceClass =
          (Class<? extends LanceNamespaceAsync>) clazz;

      Constructor<? extends LanceNamespaceAsync> constructor = namespaceClass.getConstructor();
      LanceNamespaceAsync namespace = constructor.newInstance();
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
   * @return A CompletableFuture containing the list namespaces response
   */
  default CompletableFuture<ListNamespacesResponse> listNamespaces(ListNamespacesRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listNamespaces"));
  }

  /**
   * Describe a namespace.
   *
   * @param request The describe namespace request
   * @return A CompletableFuture containing the describe namespace response
   */
  default CompletableFuture<DescribeNamespaceResponse> describeNamespace(
      DescribeNamespaceRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: describeNamespace"));
  }

  /**
   * Create a new namespace.
   *
   * @param request The create namespace request
   * @return A CompletableFuture containing the create namespace response
   */
  default CompletableFuture<CreateNamespaceResponse> createNamespace(
      CreateNamespaceRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createNamespace"));
  }

  /**
   * Drop a namespace.
   *
   * @param request The drop namespace request
   * @return A CompletableFuture containing the drop namespace response
   */
  default CompletableFuture<DropNamespaceResponse> dropNamespace(DropNamespaceRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: dropNamespace"));
  }

  /**
   * Check if a namespace exists.
   *
   * @param request The namespace exists request
   * @return A CompletableFuture that completes successfully if namespace exists, or exceptionally
   *     if not
   */
  default CompletableFuture<Void> namespaceExists(NamespaceExistsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: namespaceExists"));
  }

  // Table operations

  /**
   * List tables in a namespace.
   *
   * @param request The list tables request
   * @return A CompletableFuture containing the list tables response
   */
  default CompletableFuture<ListTablesResponse> listTables(ListTablesRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listTables"));
  }

  /**
   * Describe a table.
   *
   * @param request The describe table request
   * @return A CompletableFuture containing the describe table response
   */
  default CompletableFuture<DescribeTableResponse> describeTable(DescribeTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: describeTable"));
  }

  /**
   * Register a table.
   *
   * @param request The register table request
   * @return A CompletableFuture containing the register table response
   */
  default CompletableFuture<RegisterTableResponse> registerTable(RegisterTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: registerTable"));
  }

  /**
   * Check if a table exists.
   *
   * @param request The table exists request
   * @return A CompletableFuture that completes successfully if table exists, or exceptionally if
   *     not
   */
  default CompletableFuture<Void> tableExists(TableExistsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: tableExists"));
  }

  /**
   * Drop a table.
   *
   * @param request The drop table request
   * @return A CompletableFuture containing the drop table response
   */
  default CompletableFuture<DropTableResponse> dropTable(DropTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: dropTable"));
  }

  /**
   * Deregister a table.
   *
   * @param request The deregister table request
   * @return A CompletableFuture containing the deregister table response
   */
  default CompletableFuture<DeregisterTableResponse> deregisterTable(
      DeregisterTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: deregisterTable"));
  }

  /**
   * Count rows in a table.
   *
   * @param request The count table rows request
   * @return A CompletableFuture containing the row count
   */
  default CompletableFuture<Long> countTableRows(CountTableRowsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: countTableRows"));
  }

  // Data operations

  /**
   * Create a new table with data from Arrow IPC stream.
   *
   * @param request The create table request
   * @param requestData Arrow IPC stream data
   * @return A CompletableFuture containing the create table response
   */
  default CompletableFuture<CreateTableResponse> createTable(
      CreateTableRequest request, byte[] requestData) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createTable"));
  }

  /**
   * Declare a table (metadata only operation).
   *
   * @param request The declare table request
   * @return A CompletableFuture containing the declare table response
   */
  default CompletableFuture<DeclareTableResponse> declareTable(DeclareTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: declareTable"));
  }

  /**
   * Insert data into a table.
   *
   * @param request The insert into table request
   * @param requestData Arrow IPC stream data
   * @return A CompletableFuture containing the insert into table response
   */
  default CompletableFuture<InsertIntoTableResponse> insertIntoTable(
      InsertIntoTableRequest request, byte[] requestData) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: insertIntoTable"));
  }

  /**
   * Merge insert data into a table.
   *
   * @param request The merge insert into table request
   * @param requestData Arrow IPC stream data
   * @return A CompletableFuture containing the merge insert into table response
   */
  default CompletableFuture<MergeInsertIntoTableResponse> mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, byte[] requestData) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: mergeInsertIntoTable"));
  }

  /**
   * Update a table.
   *
   * @param request The update table request
   * @return A CompletableFuture containing the update table response
   */
  default CompletableFuture<UpdateTableResponse> updateTable(UpdateTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: updateTable"));
  }

  /**
   * Delete from a table.
   *
   * @param request The delete from table request
   * @return A CompletableFuture containing the delete from table response
   */
  default CompletableFuture<DeleteFromTableResponse> deleteFromTable(
      DeleteFromTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: deleteFromTable"));
  }

  /**
   * Query a table.
   *
   * @param request The query table request
   * @return A CompletableFuture containing Arrow IPC stream data with query results
   */
  default CompletableFuture<byte[]> queryTable(QueryTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: queryTable"));
  }

  // Index operations

  /**
   * Create a table index.
   *
   * @param request The create table index request
   * @return A CompletableFuture containing the create table index response
   */
  default CompletableFuture<CreateTableIndexResponse> createTableIndex(
      CreateTableIndexRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createTableIndex"));
  }

  /**
   * Create a scalar index on a table.
   *
   * @param request The create table index request
   * @return A CompletableFuture containing the create table scalar index response
   */
  default CompletableFuture<CreateTableScalarIndexResponse> createTableScalarIndex(
      CreateTableIndexRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createTableScalarIndex"));
  }

  /**
   * List table indices.
   *
   * @param request The list table indices request
   * @return A CompletableFuture containing the list table indices response
   */
  default CompletableFuture<ListTableIndicesResponse> listTableIndices(
      ListTableIndicesRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listTableIndices"));
  }

  /**
   * Describe table index statistics.
   *
   * @param request The describe table index stats request
   * @param indexName The name of the index
   * @return A CompletableFuture containing the describe table index stats response
   */
  default CompletableFuture<DescribeTableIndexStatsResponse> describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: describeTableIndexStats"));
  }

  /**
   * Drop a table index.
   *
   * @param request The drop table index request
   * @param indexName The name of the index
   * @return A CompletableFuture containing the drop table index response
   */
  default CompletableFuture<DropTableIndexResponse> dropTableIndex(
      DropTableIndexRequest request, String indexName) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: dropTableIndex"));
  }

  // Table version and schema operations

  /**
   * List all tables across all namespaces.
   *
   * @param request The list tables request
   * @return A CompletableFuture containing the list tables response
   */
  default CompletableFuture<ListTablesResponse> listAllTables(ListTablesRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listAllTables"));
  }

  /**
   * Restore a table to a specific version.
   *
   * @param request The restore table request
   * @return A CompletableFuture containing the restore table response
   */
  default CompletableFuture<RestoreTableResponse> restoreTable(RestoreTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: restoreTable"));
  }

  /**
   * Rename a table.
   *
   * @param request The rename table request
   * @return A CompletableFuture containing the rename table response
   */
  default CompletableFuture<RenameTableResponse> renameTable(RenameTableRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: renameTable"));
  }

  /**
   * List all versions of a table.
   *
   * @param request The list table versions request
   * @return A CompletableFuture containing the list table versions response
   */
  default CompletableFuture<ListTableVersionsResponse> listTableVersions(
      ListTableVersionsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listTableVersions"));
  }

  /**
   * Create a new table version entry.
   *
   * @param request The create table version request
   * @return A CompletableFuture containing the create table version response
   */
  default CompletableFuture<CreateTableVersionResponse> createTableVersion(
      CreateTableVersionRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createTableVersion"));
  }

  /**
   * Describe a specific table version.
   *
   * @param request The describe table version request containing the version number
   * @return A CompletableFuture containing the describe table version response
   */
  default CompletableFuture<DescribeTableVersionResponse> describeTableVersion(
      DescribeTableVersionRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: describeTableVersion"));
  }

  /**
   * Delete table version metadata records.
   *
   * @param request The batch delete table versions request
   * @return A CompletableFuture containing the batch delete table versions response
   */
  default CompletableFuture<BatchDeleteTableVersionsResponse> batchDeleteTableVersions(
      BatchDeleteTableVersionsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: batchDeleteTableVersions"));
  }

  /**
   * Atomically create new version entries for multiple tables.
   *
   * @param request The batch create table versions request
   * @return A CompletableFuture containing the batch create table versions response
   */
  default CompletableFuture<BatchCreateTableVersionsResponse> batchCreateTableVersions(
      BatchCreateTableVersionsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: batchCreateTableVersions"));
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
   * @return A CompletableFuture containing the batch commit tables response
   * @throws org.lance.namespace.async.errors.NamespaceNotFoundException if any namespace does not
   *     exist
   * @throws org.lance.namespace.async.errors.TableNotFoundException if any table does not exist
   * @throws org.lance.namespace.async.errors.ConcurrentModificationException if any operation
   *     conflicts
   */
  default CompletableFuture<BatchCommitTablesResponse> batchCommitTables(
      BatchCommitTablesRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: batchCommitTables"));
  }

  /**
   * Update table schema metadata.
   *
   * @param request The update table schema metadata request
   * @return A CompletableFuture containing the update table schema metadata response
   */
  default CompletableFuture<UpdateTableSchemaMetadataResponse> updateTableSchemaMetadata(
      UpdateTableSchemaMetadataRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: updateTableSchemaMetadata"));
  }

  /**
   * Get table statistics.
   *
   * @param request The get table stats request
   * @return A CompletableFuture containing the get table stats response
   */
  default CompletableFuture<GetTableStatsResponse> getTableStats(GetTableStatsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: getTableStats"));
  }

  // Query plan operations

  /**
   * Explain a table query plan.
   *
   * @param request The explain table query plan request
   * @return A CompletableFuture containing the query plan explanation as a string
   */
  default CompletableFuture<String> explainTableQueryPlan(ExplainTableQueryPlanRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: explainTableQueryPlan"));
  }

  /**
   * Analyze a table query plan.
   *
   * @param request The analyze table query plan request
   * @return A CompletableFuture containing the query plan analysis as a string
   */
  default CompletableFuture<String> analyzeTableQueryPlan(AnalyzeTableQueryPlanRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: analyzeTableQueryPlan"));
  }

  // Column operations

  /**
   * Add columns to a table.
   *
   * @param request The alter table add columns request
   * @return A CompletableFuture containing the alter table add columns response
   */
  default CompletableFuture<AlterTableAddColumnsResponse> alterTableAddColumns(
      AlterTableAddColumnsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: alterTableAddColumns"));
  }

  /**
   * Alter columns in a table.
   *
   * @param request The alter table alter columns request
   * @return A CompletableFuture containing the alter table alter columns response
   */
  default CompletableFuture<AlterTableAlterColumnsResponse> alterTableAlterColumns(
      AlterTableAlterColumnsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: alterTableAlterColumns"));
  }

  /**
   * Trigger an async backfill job for a computed column.
   *
   * @param request The backfill columns request
   * @return A CompletableFuture containing the backfill columns response with a job ID
   */
  default CompletableFuture<AlterTableBackfillColumnsResponse> alterTableBackfillColumns(
      AlterTableBackfillColumnsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: alterTableBackfillColumns"));
  }

  /**
   * Trigger an async materialized view refresh.
   *
   * @param request The refresh materialized view request
   * @return A CompletableFuture containing the refresh response with a job ID
   */
  default CompletableFuture<RefreshMaterializedViewResponse> refreshMaterializedView(
      RefreshMaterializedViewRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: refreshMaterializedView"));
  }

  /**
   * Create a materialized view (query / UDTF / chunker) backed by a stored UDTF/chunker spec and an
   * optional initial refresh.
   *
   * @param request The create materialized view request
   * @return A CompletableFuture containing the create response with the view's location and an
   *     optional job ID
   */
  default CompletableFuture<CreateMaterializedViewResponse> createMaterializedView(
      CreateMaterializedViewRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createMaterializedView"));
  }

  /**
   * Drop columns from a table.
   *
   * @param request The alter table drop columns request
   * @return A CompletableFuture containing the alter table drop columns response
   */
  default CompletableFuture<AlterTableDropColumnsResponse> alterTableDropColumns(
      AlterTableDropColumnsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: alterTableDropColumns"));
  }

  // Tag operations

  /**
   * List all tags for a table.
   *
   * @param request The list table tags request
   * @return A CompletableFuture containing the list table tags response
   */
  default CompletableFuture<ListTableTagsResponse> listTableTags(ListTableTagsRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: listTableTags"));
  }

  /**
   * Get the version for a specific tag.
   *
   * @param request The get table tag version request
   * @return A CompletableFuture containing the get table tag version response
   */
  default CompletableFuture<GetTableTagVersionResponse> getTableTagVersion(
      GetTableTagVersionRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: getTableTagVersion"));
  }

  /**
   * Create a tag for a table.
   *
   * @param request The create table tag request
   * @return A CompletableFuture containing the create table tag response
   */
  default CompletableFuture<CreateTableTagResponse> createTableTag(CreateTableTagRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: createTableTag"));
  }

  /**
   * Delete a tag from a table.
   *
   * @param request The delete table tag request
   * @return A CompletableFuture containing the delete table tag response
   */
  default CompletableFuture<DeleteTableTagResponse> deleteTableTag(DeleteTableTagRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: deleteTableTag"));
  }

  /**
   * Update a tag for a table.
   *
   * @param request The update table tag request
   * @return A CompletableFuture containing the update table tag response
   */
  default CompletableFuture<UpdateTableTagResponse> updateTableTag(UpdateTableTagRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: updateTableTag"));
  }

  // Transaction operations

  /**
   * Describe a transaction.
   *
   * @param request The describe transaction request
   * @return A CompletableFuture containing the describe transaction response
   */
  default CompletableFuture<DescribeTransactionResponse> describeTransaction(
      DescribeTransactionRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: describeTransaction"));
  }

  /**
   * Alter a transaction.
   *
   * @param request The alter transaction request
   * @return A CompletableFuture containing the alter transaction response
   */
  default CompletableFuture<AlterTransactionResponse> alterTransaction(
      AlterTransactionRequest request) {
    return CompletableFuture.failedFuture(
        new UnsupportedOperationException("Not supported: alterTransaction"));
  }
}
