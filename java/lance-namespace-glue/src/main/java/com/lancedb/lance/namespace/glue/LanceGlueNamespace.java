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
package com.lancedb.lance.namespace.glue;

import com.lancedb.lance.namespace.LanceNamespace;
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LanceGlueNamespace implements LanceNamespace, Closeable {
  private static final int MAX_GLUE_LISTING_SIZE = 100;

  private GlueNamespaceProperties properties;
  public GlueClient glueClient;
  private String name;

  public LanceGlueNamespace() {}

  @Override
  public void initialize(String name, Map<String, String> properties) {
    GlueNamespaceProperties glueProperties = new GlueNamespaceProperties(properties);
    GlueClient glueClient =
        GlueClient.builder().applyMutation(glueProperties::configureClientBuilder).build();
    initialize(name, glueProperties, glueClient);
  }

  @VisibleForTesting
  void initialize(String name, GlueNamespaceProperties properties, GlueClient glueClient) {
    this.properties = properties;
    this.glueClient = glueClient;
    this.name = name;
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    validateSingleLevelNamespace(request.getParent());

    GetDatabasesRequest.Builder listRequest =
        GetDatabasesRequest.builder().catalogId(properties.glueCatalogId());
    int pageSize = request.getPageSize() != null ? request.getPageSize() : Integer.MAX_VALUE;
    int remaining = pageSize;
    String glueNextToken = request.getPageToken();
    Set<String> databases = Sets.newHashSet();
    do {
      int fetchSize = Math.min(remaining, MAX_GLUE_LISTING_SIZE);
      GetDatabasesResponse response =
          glueClient.getDatabases(
              listRequest.maxResults(fetchSize).nextToken(glueNextToken).build());
      response.databaseList().forEach(d -> databases.add(d.name()));
      glueNextToken = response.nextToken();
      remaining = pageSize - databases.size();
    } while (glueNextToken != null && remaining > 0);

    return new ListNamespacesResponse().namespaces(databases).nextPageToken(glueNextToken);
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: describeNamespace");
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: createNamespace");
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    throw new UnsupportedOperationException("Not supported: dropNamespace");
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: namespaceExists");
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTable");
  }

  @Override
  public DescribeTableResponseV2 describeTableV2(DescribeTableRequestV2 request) {
    throw new UnsupportedOperationException("Not supported: describeTableV2");
  }

  @Override
  public Long countTableRows(CountTableRowsRequest request) {
    throw new UnsupportedOperationException("Not supported: countTableRows");
  }

  @Override
  public CreateTableResponse createTable(String tableName, byte[] arrowIpcData) {
    throw new UnsupportedOperationException("Not supported: createTable");
  }

  @Override
  public InsertIntoTableResponse insertIntoTable(
      String tableName, byte[] arrowIpcData, String mode) {
    throw new UnsupportedOperationException("Not supported: insertIntoTable");
  }

  @Override
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request,
      byte[] arrowIpcData,
      String on,
      Boolean whenMatchedUpdateAll,
      Boolean whenNotMatchedInsertAll) {
    throw new UnsupportedOperationException("Not supported: mergeInsertIntoTable");
  }

  @Override
  public UpdateTableResponse updateTable(UpdateTableRequest request) {
    throw new UnsupportedOperationException("Not supported: updateTable");
  }

  @Override
  public DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deleteFromTable");
  }

  @Override
  public byte[] queryTable(QueryTableRequest request) {
    throw new UnsupportedOperationException("Not supported: queryTable");
  }

  @Override
  public CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableIndex");
  }

  @Override
  public CreateTableIndexResponse createTableScalarIndex(CreateTableIndexRequest request) {
    throw new UnsupportedOperationException("Not supported: createTableIndex");
  }

  @Override
  public ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request) {
    throw new UnsupportedOperationException("Not supported: listTableIndices");
  }

  @Override
  public DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    throw new UnsupportedOperationException("Not supported: describeTableIndexStats");
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: registerTable");
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    throw new UnsupportedOperationException("Not supported: tableExists");
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    throw new UnsupportedOperationException("Not supported: dropTable");
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    throw new UnsupportedOperationException("Not supported: deregisterTable");
  }

  @Override
  public DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: describeTransaction");
  }

  @Override
  public AlterTransactionResponse alterTransaction(AlterTransactionRequest request) {
    throw new UnsupportedOperationException("Not supported: alterTransaction");
  }

  private void validateSingleLevelNamespace(List<String> parent) {
    if (parent != null && !parent.isEmpty()) {
      throw new RuntimeException("Glue does not support nested namespace, found nested: " + parent);
    }
  }

  @Override
  public void close() {
    if (glueClient != null) {
      glueClient.close();
    }
  }
}
