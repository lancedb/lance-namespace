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
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.server.springboot.api.TableApi;
import com.lancedb.lance.namespace.server.springboot.model.CountTableRowsRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateEmptyTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateEmptyTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.server.springboot.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.server.springboot.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.QueryTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest;
import com.lancedb.lance.namespace.server.springboot.model.UpdateTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.UpdateTableResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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

  @Override
  public ResponseEntity<Long> countTableRows(
      String id, CountTableRowsRequest countTableRowsRequest, Optional<String> delimiter) {
    Long count =
        delegate.countTableRows(ServerToClientRequest.countTableRows(countTableRowsRequest));
    return ResponseEntity.ok(count);
  }

  @Override
  public ResponseEntity<CreateTableResponse> createTable(
      String id,
      Resource body,
      Optional<String> delimiter,
      Optional<String> mode,
      Optional<String> xLanceTableLocation,
      Optional<String> xLanceTableProperties) {
    try {
      byte[] data = readAllBytes(body.getInputStream());
      com.lancedb.lance.namespace.server.springboot.model.CreateTableRequest request =
          new com.lancedb.lance.namespace.server.springboot.model.CreateTableRequest();
      request.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).listStyleId());
      request.setMode(mode.map(CreateTableRequest.ModeEnum::fromValue).orElse(null));
      xLanceTableLocation.ifPresent(request::setLocation);
      if (xLanceTableProperties.isPresent()) {
        // Parse JSON properties
        com.fasterxml.jackson.databind.ObjectMapper mapper =
            new com.fasterxml.jackson.databind.ObjectMapper();
        java.util.Map<String, String> props =
            mapper.readValue(
                xLanceTableProperties.get(),
                new com.fasterxml.jackson.core.type.TypeReference<
                    java.util.Map<String, String>>() {});
        request.setProperties(props);
      }
      return ResponseEntity.ok(
          ClientToServerResponse.createTable(
              delegate.createTable(ServerToClientRequest.createTable(request), data)));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read request body", e);
    }
  }

  @Override
  public ResponseEntity<CreateEmptyTableResponse> createEmptyTable(
      String id, CreateEmptyTableRequest createEmptyTableRequest, Optional<String> delimiter) {
    createEmptyTableRequest.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).listStyleId());
    return ResponseEntity.ok(
        ClientToServerResponse.createEmptyTable(
            delegate.createEmptyTable(
                ServerToClientRequest.createEmptyTable(createEmptyTableRequest))));
  }

  @Override
  public ResponseEntity<CreateTableIndexResponse> createTableIndex(
      String id, CreateTableIndexRequest createTableIndexRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.createTableIndex(
            delegate.createTableIndex(
                ServerToClientRequest.createTableIndex(createTableIndexRequest))));
  }

  @Override
  public ResponseEntity<DeleteFromTableResponse> deleteFromTable(
      String id, DeleteFromTableRequest deleteFromTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.deleteFromTable(
            delegate.deleteFromTable(
                ServerToClientRequest.deleteFromTable(deleteFromTableRequest))));
  }

  @Override
  public ResponseEntity<DescribeTableIndexStatsResponse> describeTableIndexStats(
      String id,
      String indexName,
      DescribeTableIndexStatsRequest describeTableIndexStatsRequest,
      Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.describeTableIndexStats(
            delegate.describeTableIndexStats(
                ServerToClientRequest.describeTableIndexStats(describeTableIndexStatsRequest),
                indexName)));
  }

  @Override
  public ResponseEntity<InsertIntoTableResponse> insertIntoTable(
      String id, Resource body, Optional<String> delimiter, Optional<String> mode) {
    try {
      byte[] data = readAllBytes(body.getInputStream());
      com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableRequest request =
          new com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableRequest();
      request.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).listStyleId());
      request.setMode(
          com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableRequest.ModeEnum
              .fromValue(mode.orElse("append")));
      return ResponseEntity.ok(
          ClientToServerResponse.insertIntoTable(
              delegate.insertIntoTable(ServerToClientRequest.insertIntoTable(request), data)));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read request body", e);
    }
  }

  @Override
  public ResponseEntity<ListTableIndicesResponse> listTableIndices(
      String id, ListTableIndicesRequest listTableIndicesRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.listTableIndices(
            delegate.listTableIndices(
                ServerToClientRequest.listTableIndices(listTableIndicesRequest))));
  }

  @Override
  public ResponseEntity<MergeInsertIntoTableResponse> mergeInsertIntoTable(
      String id,
      String on,
      Resource body,
      Optional<String> delimiter,
      Optional<Boolean> whenMatchedUpdateAll,
      Optional<String> whenMatchedUpdateAllFilt,
      Optional<Boolean> whenNotMatchedInsertAll,
      Optional<Boolean> whenNotMatchedBySourceDelete,
      Optional<String> whenNotMatchedBySourceDeleteFilt) {
    try {
      byte[] data = readAllBytes(body.getInputStream());
      com.lancedb.lance.namespace.server.springboot.model.MergeInsertIntoTableRequest request =
          new com.lancedb.lance.namespace.server.springboot.model.MergeInsertIntoTableRequest();
      request.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).listStyleId());
      request.setOn(on);
      request.setWhenMatchedUpdateAll(whenMatchedUpdateAll.orElse(false));
      whenMatchedUpdateAllFilt.ifPresent(request::setWhenMatchedUpdateAllFilt);
      request.setWhenNotMatchedInsertAll(whenNotMatchedInsertAll.orElse(false));
      request.setWhenNotMatchedBySourceDelete(whenNotMatchedBySourceDelete.orElse(false));
      whenNotMatchedBySourceDeleteFilt.ifPresent(request::setWhenNotMatchedBySourceDeleteFilt);
      return ResponseEntity.ok(
          ClientToServerResponse.mergeInsertIntoTable(
              delegate.mergeInsertIntoTable(
                  ServerToClientRequest.mergeInsertIntoTable(request), data)));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read request body", e);
    }
  }

  @Override
  public ResponseEntity<Resource> queryTable(
      String id, QueryTableRequest queryTableRequest, Optional<String> delimiter) {
    byte[] result = delegate.queryTable(ServerToClientRequest.queryTable(queryTableRequest));
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/vnd.apache.arrow.stream"))
        .body(new ByteArrayResource(result));
  }

  @Override
  public ResponseEntity<UpdateTableResponse> updateTable(
      String id, UpdateTableRequest updateTableRequest, Optional<String> delimiter) {
    return ResponseEntity.ok(
        ClientToServerResponse.updateTable(
            delegate.updateTable(ServerToClientRequest.updateTable(updateTableRequest))));
  }

  // JDK8 compatibility: readAllBytes() was added in JDK9
  private static byte[] readAllBytes(java.io.InputStream inputStream) throws IOException {
    java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
    int nRead;
    byte[] data = new byte[16384];
    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    return buffer.toByteArray();
  }
}
