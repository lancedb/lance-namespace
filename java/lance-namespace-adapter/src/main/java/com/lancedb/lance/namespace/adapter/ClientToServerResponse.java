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
package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.GetNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.GetTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsResponse;

public class ClientToServerResponse {

  public static GetNamespaceResponse getNamespace(
      com.lancedb.lance.namespace.model.GetNamespaceResponse response) {
    GetNamespaceResponse converted = new GetNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    return converted;
  }

  public static ListNamespacesResponse listNamespaces(
      com.lancedb.lance.namespace.model.ListNamespacesResponse response) {
    ListNamespacesResponse converted = new ListNamespacesResponse();
    converted.setNamespaces(response.getNamespaces());
    return converted;
  }

  public static CreateNamespaceResponse createNamespace(
      com.lancedb.lance.namespace.client.apache.model.CreateNamespaceResponse response) {
    CreateNamespaceResponse converted = new CreateNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    return converted;
  }

  public static DropNamespaceResponse dropNamespace(
      com.lancedb.lance.namespace.client.apache.model.DropNamespaceResponse response) {
    DropNamespaceResponse converted = new DropNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    return converted;
  }

  public static NamespaceExistsResponse namespaceExists(
      com.lancedb.lance.namespace.model.NamespaceExistsResponse response) {
    NamespaceExistsResponse converted = new NamespaceExistsResponse();
    converted.setExists(response.getExists());
    return converted;
  }

  public static GetTableResponse getTable(
      com.lancedb.lance.namespace.model.GetTableResponse response) {
    GetTableResponse converted = new GetTableResponse();
    converted.setNamespace(response.getNamespace());
    converted.setName(response.getName());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static RegisterTableResponse registerTable(
      com.lancedb.lance.namespace.model.RegisterTableResponse response) {
    RegisterTableResponse converted = new RegisterTableResponse();
    converted.setNamespace(response.getNamespace());
    converted.setName(response.getName());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static TableExistsResponse tableExists(
      com.lancedb.lance.namespace.model.TableExistsResponse response) {
    TableExistsResponse converted = new TableExistsResponse();
    converted.setExists(response.getExists());
    return converted;
  }
}
