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
package com.lancedb.lance.namespace.client;

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;
import com.lancedb.lance.namespace.client.apache.api.TableApi;
import com.lancedb.lance.namespace.client.apache.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetNamespaceResponse;
import com.lancedb.lance.namespace.client.apache.model.GetTableRequest;
import com.lancedb.lance.namespace.client.apache.model.GetTableResponse;
import com.lancedb.lance.namespace.client.apache.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.client.apache.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.client.apache.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.client.apache.model.RegisterTableRequest;
import com.lancedb.lance.namespace.client.apache.model.RegisterTableResponse;
import com.lancedb.lance.namespace.client.apache.model.TableExistsRequest;

public class LanceRestNamespace implements LanceNamespace {

  private final NamespaceApi namespaceApi;
  private final TableApi tableApi;

  public LanceRestNamespace(ApiClient client) {
    this.namespaceApi = new NamespaceApi(client);
    this.tableApi = new TableApi(client);
  }

  @Override
  public GetNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    try {
      return namespaceApi.createNamespace(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    try {
      // TODO: add pagination
      return namespaceApi.listNamespaces(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public GetNamespaceResponse getNamespace(GetNamespaceRequest request) {
    try {
      return namespaceApi.getNamespace(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public void dropNamespace(DropNamespaceRequest request) {
    try {
      namespaceApi.dropNamespace(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    try {
      namespaceApi.namespaceExists(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public GetTableResponse getTable(GetTableRequest request) {
    try {
      return tableApi.getTable(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    try {
      return tableApi.registerTable(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    try {
      tableApi.tableExists(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }
}
