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
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
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
import com.lancedb.lance.namespace.model.DescribeTableRequestV2;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableResponseV2;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;
import com.lancedb.lance.namespace.util.DynConstructors;

import java.io.InputStream;
import java.util.Map;

/** TODO: add documentation */
public interface LanceNamespace {
  static LanceNamespace create(String name, Map<String, String> properties, Object conf) {
    String impl =
        properties.getOrDefault(NamespaceProperties.NS_IMPL, NamespaceProperties.NS_IMPL_DEFAULT);

    LanceNamespace ns;
    try {
      ns =
          (LanceNamespace)
              DynConstructors.builder(LanceNamespace.class).impl(impl).buildChecked().newInstance();
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          String.format("Failed to construct namespace, impl: %s", impl), e);
    }

    if (ns instanceof Configurable && conf != null) {
      ((Configurable) ns).setConf(conf);
    }

    ns.initialize(name, properties);
    return ns;
  }

  /**
   * Initialize namespace with custom name and configuration properties.
   *
   * @param name a custom name for the namespace
   * @param properties namespace conf properties
   */
  default void initialize(String name, Map<String, String> properties) {}

  ListNamespacesResponse listNamespaces(ListNamespacesRequest request);

  DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request);

  CreateNamespaceResponse createNamespace(CreateNamespaceRequest request);

  DropNamespaceResponse dropNamespace(DropNamespaceRequest request);

  void namespaceExists(NamespaceExistsRequest request);

  DescribeTableResponse describeTable(DescribeTableRequest request);

  DescribeTableResponseV2 describeTableV2(DescribeTableRequestV2 request);

  Long countTableRows(CountTableRowsRequest request);

  CreateTableResponse createTable(String tableName, InputStream requestData);

  InsertIntoTableResponse insertIntoTable(String tableName, InputStream requestData);

  MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, InputStream requestData);

  UpdateTableResponse updateTable(UpdateTableRequest request);

  DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request);

  byte[] queryTable(QueryTableRequest request);

  CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request);

  CreateTableIndexResponse createTableScalarIndex(CreateTableIndexRequest request);

  ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request);

  DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName);

  RegisterTableResponse registerTable(RegisterTableRequest request);

  void tableExists(TableExistsRequest request);

  DropTableResponse dropTable(DropTableRequest request);

  DeregisterTableResponse deregisterTable(DeregisterTableRequest request);

  DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request);

  AlterTransactionResponse alterTransaction(AlterTransactionRequest request);
}
