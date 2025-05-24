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
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.model.GetNamespaceResponse;
import com.lancedb.lance.namespace.model.GetTableRequest;
import com.lancedb.lance.namespace.model.GetTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsResponse;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsResponse;

public class LanceRestNamespace implements LanceNamespace {

  private final NamespaceApi namespaceApi;
  private final TableApi tableApi;

  public LanceRestNamespace(ApiClient client) {
    this.namespaceApi = new NamespaceApi(client);
    this.tableApi = new TableApi(client);
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
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
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    try {
      return namespaceApi.dropNamespace(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }

  @Override
  public NamespaceExistsResponse namespaceExists(NamespaceExistsRequest request) {
    try {
      return namespaceApi.namespaceExists(request);
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
  public TableExistsResponse tableExists(TableExistsRequest request) {
    try {
      return tableApi.tableExists(request);
    } catch (ApiException e) {
      throw new LanceNamespaceException(e);
    }
  }
}
