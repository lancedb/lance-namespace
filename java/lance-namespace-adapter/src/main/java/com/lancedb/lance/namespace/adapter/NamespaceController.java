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

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.server.springboot.api.NamespaceApi;
import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTablesResponse;
import com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class NamespaceController implements NamespaceApi {

  private final LanceNamespace delegate;

  public NamespaceController(LanceNamespace delegate) {
    this.delegate = delegate;
  }

  @Override
  public ResponseEntity<CreateNamespaceResponse> createNamespace(
      String id, CreateNamespaceRequest createNamespaceRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.createNamespace(
            delegate.createNamespace(
                ServerToClientRequest.createNamespace(createNamespaceRequest))));
  }

  @Override
  public ResponseEntity<DropNamespaceResponse> dropNamespace(
      String id, DropNamespaceRequest dropNamespaceRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.dropNamespace(
            delegate.dropNamespace(ServerToClientRequest.dropNamespace(dropNamespaceRequest))));
  }

  @Override
  public ResponseEntity<DescribeNamespaceResponse> describeNamespace(
      String id, DescribeNamespaceRequest describeNamespaceRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.describeNamespace(
            delegate.describeNamespace(
                ServerToClientRequest.describeNamespace(describeNamespaceRequest))));
  }

  @Override
  public ResponseEntity<ListNamespacesResponse> listNamespaces(
      String id, Optional<String> delimiter, Optional<String> pageToken, Optional<Integer> limit) {
    return ResponseEntity.ok(
        ClientToServerResponse.listNamespaces(
            delegate.listNamespaces(
                ServerToClientRequest.listNamespaces(id, delimiter, pageToken, limit))));
  }

  @Override
  public ResponseEntity<ListTablesResponse> listTables(
      String id, Optional<String> delimiter, Optional<String> pageToken, Optional<Integer> limit) {
    return ResponseEntity.ok(
        ClientToServerResponse.listTables(
            delegate.listTables(
                ServerToClientRequest.listTables(id, delimiter, pageToken, limit))));
  }

  @Override
  public ResponseEntity<Void> namespaceExists(
      String id, NamespaceExistsRequest namespaceExistsRequest, Optional<String> delimiter) {
    delegate.namespaceExists(ServerToClientRequest.namespaceExists(namespaceExistsRequest));
    return ResponseEntity.ok().build();
  }
}
