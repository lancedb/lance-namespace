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
import com.lancedb.lance.namespace.server.springboot.api.TableApi;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TableController implements TableApi {

  private final LanceNamespace delegate;

  public TableController(LanceNamespace delegate) {
    this.delegate = delegate;
  }

  @Override
  public ResponseEntity<DescribeTableResponse> describeTable(
      String id, DescribeTableRequest describeTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.describeTable(
            delegate.describeTable(ServerToClientRequest.describeTable(describeTableRequest))));
  }

  @Override
  public ResponseEntity<RegisterTableResponse> registerTable(
      String id, RegisterTableRequest registerTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.registerTable(
            delegate.registerTable(ServerToClientRequest.registerTable(registerTableRequest))));
  }

  @Override
  public ResponseEntity<Void> tableExists(
      String id, TableExistsRequest tableExistsRequest, Optional<String> delimiter) {
    delegate.tableExists(ServerToClientRequest.tableExists(tableExistsRequest));
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<DropTableResponse> dropTable(
      String id, DropTableRequest dropTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.dropTable(
            delegate.dropTable(ServerToClientRequest.dropTable(dropTableRequest))));
  }

  @Override
  public ResponseEntity<DeregisterTableResponse> deregisterTable(
      String id, DeregisterTableRequest deregisterTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.deregisterTable(
            delegate.deregisterTable(
                ServerToClientRequest.deregisterTable(deregisterTableRequest))));
  }
}
