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

import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
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

/** TODO: add documentation */
public interface LanceNamespace {

  ListNamespacesResponse listNamespaces(ListNamespacesRequest request);

  GetNamespaceResponse getNamespace(GetNamespaceRequest request);

  GetNamespaceResponse createNamespace(CreateNamespaceRequest request);

  void dropNamespace(DropNamespaceRequest request);

  NamespaceExistsResponse namespaceExists(NamespaceExistsRequest request);

  GetTableResponse getTable(GetTableRequest request);

  RegisterTableResponse registerTable(RegisterTableRequest request);

  TableExistsResponse tableExists(TableExistsRequest request);
}
