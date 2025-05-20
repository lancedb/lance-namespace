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

import com.lancedb.lance.namespace.client.apache.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetTableRequest;
import com.lancedb.lance.namespace.client.apache.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.client.apache.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.client.apache.model.RegisterTableRequest;
import com.lancedb.lance.namespace.client.apache.model.TableExistsRequest;

public class ServerToClientRequest {

  public static CreateNamespaceRequest createNamespace(
      com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest request) {
    CreateNamespaceRequest converted = new CreateNamespaceRequest();
    converted.setMode(CreateNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setParent(request.getParent());
    converted.setOptions(request.getOptions());
    converted.setName(request.getName());
    return converted;
  }

  public static DropNamespaceRequest dropNamespace(
      com.lancedb.lance.namespace.server.springboot.model.DropNamespaceRequest request) {
    DropNamespaceRequest converted = new DropNamespaceRequest();
    converted.setName(request.getName());
    return converted;
  }

  public static GetNamespaceRequest getNamespace(
      com.lancedb.lance.namespace.server.springboot.model.GetNamespaceRequest request) {
    GetNamespaceRequest converted = new GetNamespaceRequest();
    converted.setName(request.getName());
    return converted;
  }

  public static ListNamespacesRequest listNamespaces(
      com.lancedb.lance.namespace.server.springboot.model.ListNamespacesRequest request) {
    ListNamespacesRequest converted = new ListNamespacesRequest();
    converted.setParent(request.getParent());
    return converted;
  }

  public static NamespaceExistsRequest namespaceExists(
      com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsRequest request) {
    NamespaceExistsRequest converted = new NamespaceExistsRequest();
    converted.setName(request.getName());
    return converted;
  }

  public static GetTableRequest getTable(
      com.lancedb.lance.namespace.server.springboot.model.GetTableRequest request) {
    GetTableRequest converted = new GetTableRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    return converted;
  }

  public static RegisterTableRequest registerTable(
      com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest request) {
    RegisterTableRequest converted = new RegisterTableRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    converted.setOptions(request.getOptions());
    return converted;
  }

  public static TableExistsRequest tableExists(
      com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest request) {
    TableExistsRequest converted = new TableExistsRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    return converted;
  }
}
