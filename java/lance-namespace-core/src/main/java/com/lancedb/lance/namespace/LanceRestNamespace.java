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

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;
import com.lancedb.lance.namespace.client.apache.api.TableApi;
import com.lancedb.lance.namespace.client.apache.api.TransactionApi;
import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.model.CountRowsRequest;
import com.lancedb.lance.namespace.model.CreateIndexRequest;
import com.lancedb.lance.namespace.model.CreateIndexResponse;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.IndexListRequest;
import com.lancedb.lance.namespace.model.IndexListResponse;
import com.lancedb.lance.namespace.model.IndexStatsRequest;
import com.lancedb.lance.namespace.model.IndexStatsResponse;
import com.lancedb.lance.namespace.model.InsertTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.MergeInsertTableRequest;
import com.lancedb.lance.namespace.model.MergeInsertTableResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsResponse;
import com.lancedb.lance.namespace.model.QueryRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsResponse;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;

import java.util.Map;

public class LanceRestNamespace implements LanceNamespace {

  private final NamespaceApi namespaceApi;
  private final TableApi tableApi;
  private final TransactionApi transactionApi;
  private final RestConfig config;

  public LanceRestNamespace(ApiClient client, Map<String, String> config) {
    this.namespaceApi = new NamespaceApi(client);
    this.tableApi = new TableApi(client);
    this.transactionApi = new TransactionApi(client);
    this.config = new RestConfig(config);
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    try {
      return namespaceApi.createNamespace(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    try {
      // TODO: add pagination
      return namespaceApi.listNamespaces(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    try {
      return namespaceApi.describeNamespace(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    try {
      return namespaceApi.dropNamespace(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public NamespaceExistsResponse namespaceExists(NamespaceExistsRequest request) {
    try {
      return namespaceApi.namespaceExists(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    try {
      return tableApi.describeTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public Long countRows(CountRowsRequest request) {
    try {
      return tableApi.countRows(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public CreateTableResponse createTable(String tableName, byte[] arrowIpcData) {
    try {
      return tableApi.createTable(tableName, arrowIpcData, config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public InsertTableResponse insertTable(String tableName, byte[] arrowIpcData, String mode) {
    try {
      return tableApi.insertTable(tableName, arrowIpcData, mode, config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public MergeInsertTableResponse mergeInsertTable(
      MergeInsertTableRequest request,
      byte[] arrowIpcData,
      String on,
      Boolean whenMatchedUpdateAll,
      Boolean whenNotMatchedInsertAll) {
    try {
      return tableApi.mergeInsertTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          on,
          arrowIpcData,
          whenMatchedUpdateAll,
          whenNotMatchedInsertAll,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public UpdateTableResponse updateTable(UpdateTableRequest request) {
    try {
      return tableApi.updateTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    try {
      return tableApi.deleteFromTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public byte[] queryTable(QueryRequest request) {
    try {
      return tableApi.queryTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public CreateIndexResponse createIndex(CreateIndexRequest request) {
    try {
      return tableApi.createIndex(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public CreateIndexResponse createScalarIndex(CreateIndexRequest request) {
    try {
      return tableApi.createScalarIndex(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public IndexListResponse listIndices(IndexListRequest request) {
    try {
      return tableApi.listIndices(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public IndexStatsResponse getIndexStats(IndexStatsRequest request, String indexName) {
    try {
      return tableApi.getIndexStats(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          indexName,
          request,
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    try {
      return tableApi.registerTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public TableExistsResponse tableExists(TableExistsRequest request) {
    try {
      return tableApi.tableExists(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    try {
      return tableApi.deregisterTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    try {
      return tableApi.dropTable(
          ObjectIdentifiers.stringFrom(request, config.delimiter()),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public AlterTransactionResponse alterTransaction(AlterTransactionRequest request) {
    try {
      return transactionApi.alterTransaction(
          ObjectIdentifiers.stringFrom(request),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request) {
    try {
      return transactionApi.describeTransaction(
          ObjectIdentifiers.stringFrom(request),
          request,
          config.delimiter(),
          config.additionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }
}
