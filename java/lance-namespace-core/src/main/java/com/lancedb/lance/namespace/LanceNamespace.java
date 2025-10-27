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
package com.lancedb.lance.namespace;

import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.model.CountTableRowsRequest;
import com.lancedb.lance.namespace.model.CreateEmptyTableRequest;
import com.lancedb.lance.namespace.model.CreateEmptyTableResponse;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.InsertIntoTableRequest;
import com.lancedb.lance.namespace.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;

import org.apache.arrow.memory.BufferAllocator;

import java.util.Map;

public interface LanceNamespace {

  /**
   * Initialize namespace with custom configuration properties and a BufferAllocator.
   *
   * <p>Any implementation must have an empty constructor and implement this method to initialize
   * itself from the config properties and use the provided allocator for Arrow operations.
   *
   * @param configProperties namespace conf properties
   * @param allocator BufferAllocator for Arrow operations
   */
  void initialize(Map<String, String> configProperties, BufferAllocator allocator);

  /**
   * Return a human-readable unique identifier for this namespace instance.
   *
   * <p>This is used for equality comparison and hashing when the namespace is used as part of a
   * storage options provider. Two namespace instances with the same ID are considered equal and
   * will share cached resources.
   *
   * <p>The ID should be human-readable for debugging and logging purposes. For example:
   *
   * <ul>
   *   <li>Glue namespace: "GlueNamespace { region: 'us-east-1', catalog: 'my-catalog' }"
   *   <li>REST namespace: "RestNamespace { endpoint: 'https://api.example.com', delimiter: '.' }"
   * </ul>
   *
   * @return A human-readable unique identifier string
   */
  String namespaceId();

  default ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    throw new UnsupportedOperationException("Not supported: listNamespaces");
  }

  default DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: describeNamespace");
  }

  default CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: createNamespace");
  }

  default DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: dropNamespace");
  }

  default void namespaceExists(NamespaceExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: namespaceExists");
  }

  default ListTablesResponse listTables(ListTablesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTables");
  }

  default DescribeTableResponse describeTable(DescribeTableRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTable");
  }

  default RegisterTableResponse registerTable(RegisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: registerTable");
  }

  default void tableExists(TableExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: tableExists");
  }

  default DropTableResponse dropTable(DropTableRequest request) {
    throw new UnsupportedOperationException("Not supported: dropTable");
  }

  default DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deregisterTable");
  }

  default Long countTableRows(CountTableRowsRequest request) {
    throw new UnsupportedOperationException("Not supported: countTableRows");
  }

  default CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: createTable");
  }

  default CreateEmptyTableResponse createEmptyTable(CreateEmptyTableRequest request) {
    throw new UnsupportedOperationException("Not supported: createEmptyTable");
  }

  default InsertIntoTableResponse insertIntoTable(
      InsertIntoTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: insertIntoTable");
  }

  default MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, byte[] requestData) {
    throw new UnsupportedOperationException("Not supported: mergeInsertIntoTable");
  }

  default UpdateTableResponse updateTable(UpdateTableRequest request) {
    throw new UnsupportedOperationException("Not supported: updateTable");
  }

  default DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deleteFromTable");
  }

  default byte[] queryTable(QueryTableRequest request) {
    throw new UnsupportedOperationException("Not supported: queryTable");
  }

  default CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableIndex");
  }

  default ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableIndices");
  }

  default DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    throw new UnsupportedOperationException("Not supported: describeTableIndexStats");
  }

  default DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTransaction");
  }

  default AlterTransactionResponse alterTransaction(AlterTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTransaction");
  }
}
