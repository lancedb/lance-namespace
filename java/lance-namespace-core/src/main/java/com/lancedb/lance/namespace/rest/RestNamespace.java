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
package com.lancedb.lance.namespace.rest;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;
import com.lancedb.lance.namespace.client.apache.api.TableApi;
import com.lancedb.lance.namespace.client.apache.api.TransactionApi;
import com.lancedb.lance.namespace.lancedb.jackson.LanceNamespaceJacksonModule;
import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.model.CountTableRowsRequest;
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
import com.lancedb.lance.namespace.util.JsonUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.arrow.memory.BufferAllocator;

import java.util.Map;

public class RestNamespace implements LanceNamespace {

  private NamespaceApi namespaceApi;
  private TableApi tableApi;
  private TransactionApi transactionApi;
  private RestNamespaceConfig config;

  public RestNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.config = new RestNamespaceConfig(configProperties);
    // Note: RestNamespace doesn't use BufferAllocator, parameter ignored

    // Create ApiClient and set URI if provided
    ApiClient client = new ApiClient();
    if (config.getUri() != null) {
      client.setBasePath(config.getUri());
    }

    // Register custom serializers before creating API instances
    ObjectMapper objectMapper = client.getObjectMapper();
    objectMapper.registerModule(new LanceNamespaceJacksonModule());
    client.setObjectMapper(objectMapper);

    // Initialize API instances
    this.namespaceApi = new NamespaceApi(client);
    this.tableApi = new TableApi(client);
    this.transactionApi = new TransactionApi(client);
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    try {
      return namespaceApi.createNamespace(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    try {
      // TODO: add pagination
      return namespaceApi.listNamespaces(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          config.getDelimiter(),
          request.getPageToken(),
          request.getLimit(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    try {
      return namespaceApi.describeNamespace(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    try {
      return namespaceApi.dropNamespace(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    try {
      namespaceApi.namespaceExists(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    try {
      return tableApi.describeTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public Long countTableRows(CountTableRowsRequest request) {
    try {
      return tableApi.countTableRows(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    try {
      String serializedTableProperties =
          JsonUtil.generate(gen -> JsonUtil.writeStringMap(request.getProperties(), gen), false);
      return tableApi.createTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          requestData,
          config.getDelimiter(),
          request.getMode() == null ? null : request.getMode().getValue(),
          request.getLocation(),
          serializedTableProperties,
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public InsertIntoTableResponse insertIntoTable(
      InsertIntoTableRequest request, byte[] requestData) {
    try {
      String modeStr = request.getMode() == null ? null : request.getMode().getValue();
      return tableApi.insertIntoTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          requestData,
          config.getDelimiter(),
          modeStr,
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, byte[] requestData) {
    try {
      return tableApi.mergeInsertIntoTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request.getOn(),
          requestData,
          config.getDelimiter(),
          request.getWhenMatchedUpdateAll(),
          request.getWhenMatchedUpdateAllFilt(),
          request.getWhenNotMatchedInsertAll(),
          request.getWhenNotMatchedBySourceDelete(),
          request.getWhenNotMatchedBySourceDeleteFilt(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public UpdateTableResponse updateTable(UpdateTableRequest request) {
    try {
      return tableApi.updateTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    try {
      return tableApi.deleteFromTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public byte[] queryTable(QueryTableRequest request) {
    try {
      return tableApi.queryTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    try {
      return tableApi.listTables(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          config.getDelimiter(),
          request.getPageToken(),
          request.getLimit(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request) {
    try {
      return tableApi.createTableIndex(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request) {
    try {
      return tableApi.listTableIndices(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    try {
      return tableApi.describeTableIndexStats(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          indexName,
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    try {
      return tableApi.registerTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    try {
      tableApi.tableExists(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    try {
      return tableApi.deregisterTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    try {
      return tableApi.dropTable(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public AlterTransactionResponse alterTransaction(AlterTransactionRequest request) {
    try {
      return transactionApi.alterTransaction(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request) {
    try {
      return transactionApi.describeTransaction(
          ObjectIdentifier.of(request.getId()).stringStyleId(config.getDelimiter()),
          request,
          config.getDelimiter(),
          config.getAdditionalHeaders());
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }
}
