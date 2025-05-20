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

import com.lancedb.lance.namespace.client.LanceNamespace;
import com.lancedb.lance.namespace.server.springboot.api.NamespaceApi;
import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.GetNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class NamespaceController implements NamespaceApi {

  private final LanceNamespace delegate;

  public NamespaceController(LanceNamespace delegate) {
    this.delegate = delegate;
  }

  @Override
  public ResponseEntity<GetNamespaceResponse> createNamespace(
      CreateNamespaceRequest createNamespaceRequest) {
    return ResponseEntity.ok(
        ClientToServerResponse.getNamespace(
            delegate.createNamespace(
                ServerToClientRequest.createNamespace(createNamespaceRequest))));
  }

  @Override
  public ResponseEntity<Object> dropNamespace(DropNamespaceRequest dropNamespaceRequest) {
    delegate.dropNamespace(ServerToClientRequest.dropNamespace(dropNamespaceRequest));
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<GetNamespaceResponse> getNamespace(
      GetNamespaceRequest getNamespaceRequest) {
    return ResponseEntity.ok(
        ClientToServerResponse.getNamespace(
            delegate.getNamespace(ServerToClientRequest.getNamespace(getNamespaceRequest))));
  }

  @Override
  public ResponseEntity<ListNamespacesResponse> listNamespaces(
      ListNamespacesRequest listNamespacesRequest) {
    return ResponseEntity.ok(
        ClientToServerResponse.listNamespaces(
            delegate.listNamespaces(ServerToClientRequest.listNamespaces(listNamespacesRequest))));
  }

  @Override
  public ResponseEntity<Object> namespaceExists(NamespaceExistsRequest namespaceExistsRequest) {
    delegate.namespaceExists(ServerToClientRequest.namespaceExists(namespaceExistsRequest));
    return ResponseEntity.ok().build();
  }
}
