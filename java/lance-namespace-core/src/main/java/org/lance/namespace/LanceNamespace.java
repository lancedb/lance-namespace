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
   * Create an empty table (metadata only operation).
   *
   * @param request The create empty table request
   * @return The create empty table response
   */
  default CreateEmptyTableResponse createEmptyTable(CreateEmptyTableRequest request) {
    throw new UnsupportedOperationException("Not supported: createEmptyTable");
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
   */
  default ListTableVersionsResponse listTableVersions(ListTableVersionsRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableVersions");
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
