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
package org.lance.namespace.pact.provider;

import org.lance.namespace.server.springboot.api.TableApi;
import org.lance.namespace.server.springboot.model.CreateTableResponse;
import org.lance.namespace.server.springboot.model.DeregisterTableRequest;
import org.lance.namespace.server.springboot.model.DeregisterTableResponse;
import org.lance.namespace.server.springboot.model.DescribeTableRequest;
import org.lance.namespace.server.springboot.model.DescribeTableResponse;
import org.lance.namespace.server.springboot.model.DropTableResponse;
import org.lance.namespace.server.springboot.model.ListTablesResponse;
import org.lance.namespace.server.springboot.model.RegisterTableRequest;
import org.lance.namespace.server.springboot.model.RegisterTableResponse;
import org.lance.namespace.server.springboot.model.TableExistsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Test-scope implementation of {@link TableApi} backed by {@link InMemoryNamespaceFixtures}.
 *
 * <p>Active only when the {@code pact} Spring profile is enabled. Handles the 7 Table API
 * operations covered by Phase 2 Pact interactions:
 *
 * <ul>
 *   <li>ListTables ({@code GET /v1/namespace/{id}/table/list})
 *   <li>DescribeTable ({@code POST /v1/table/{id}/describe})
 *   <li>TableExists ({@code POST /v1/table/{id}/exists})
 *   <li>DropTable ({@code POST /v1/table/{id}/drop})
 *   <li>RegisterTable ({@code POST /v1/table/{id}/register})
 *   <li>DeregisterTable ({@code POST /v1/table/{id}/deregister})
 * </ul>
 *
 * <p>The table {@code id} path parameter uses dot-separated format: {@code namespace.tableName}.
 * Example: {@code ns_with_tables.table_alpha} → namespace={@code ns_with_tables}, table={@code
 * table_alpha}.
 */
@Profile("pact")
@RestController
public class PactTableController implements TableApi {

  private final InMemoryNamespaceFixtures fixtures;

  @Autowired
  public PactTableController(InMemoryNamespaceFixtures fixtures) {
    this.fixtures = fixtures;
  }

  // ─────────────────────────────────────────────────────────────────────────
  // DescribeTable — POST /v1/table/{id}/describe
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<DescribeTableResponse> describeTable(
      String id,
      DescribeTableRequest req,
      Optional<String> delimiter,
      Optional<Boolean> withTableUri,
      Optional<Boolean> loadDetailedMetadata,
      Optional<Boolean> checkDeclared) {

    TableRef ref = TableRef.parse(id);
    if (!fixtures.tableExists(ref.namespaceId(), ref.tableName())) {
      throw new TableNotFoundException(id);
    }

    String location =
        fixtures
            .getTableLocation(ref.namespaceId(), ref.tableName())
            .orElse("s3://example/" + ref.namespaceId() + "/" + ref.tableName());

    DescribeTableResponse response = new DescribeTableResponse();
    response.setLocation(location);
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // TableExists — POST /v1/table/{id}/exists
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<Void> tableExists(
      String id, TableExistsRequest req, Optional<String> delimiter) {

    TableRef ref = TableRef.parse(id);
    if (!fixtures.tableExists(ref.namespaceId(), ref.tableName())) {
      throw new TableNotFoundException(id);
    }
    return ResponseEntity.ok().build();
  }

  // ─────────────────────────────────────────────────────────────────────────
  // DropTable — POST /v1/table/{id}/drop
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<DropTableResponse> dropTable(String id, Optional<String> delimiter) {

    TableRef ref = TableRef.parse(id);
    if (!fixtures.tableExists(ref.namespaceId(), ref.tableName())) {
      throw new TableNotFoundException(id);
    }
    return ResponseEntity.ok(new DropTableResponse());
  }

  // ─────────────────────────────────────────────────────────────────────────
  // RegisterTable — POST /v1/table/{id}/register
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<RegisterTableResponse> registerTable(
      String id, RegisterTableRequest req, Optional<String> delimiter) {

    TableRef ref = TableRef.parse(id);
    if (fixtures.tableExists(ref.namespaceId(), ref.tableName())) {
      throw new TableAlreadyExistsException(id);
    }

    String location =
        req.getLocation() != null
            ? req.getLocation()
            : "s3://example/" + ref.namespaceId() + "/" + ref.tableName();

    fixtures.registerTableLocation(ref.namespaceId(), ref.tableName(), location);

    RegisterTableResponse response = new RegisterTableResponse();
    response.setLocation(location);
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // DeregisterTable — POST /v1/table/{id}/deregister
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<DeregisterTableResponse> deregisterTable(
      String id, DeregisterTableRequest req, Optional<String> delimiter) {

    TableRef ref = TableRef.parse(id);
    if (!fixtures.tableExists(ref.namespaceId(), ref.tableName())) {
      throw new TableNotFoundException(id);
    }
    return ResponseEntity.ok(new DeregisterTableResponse());
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Stub implementations for unsupported endpoints (return 501)
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<ListTablesResponse> listAllTables(
      Optional<String> delimiter,
      Optional<String> pageToken,
      Optional<Integer> limit,
      Optional<Boolean> includeDeclared) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<CreateTableResponse> createTable(
      String id,
      org.springframework.core.io.Resource body,
      Optional<String> delimiter,
      Optional<String> mode,
      Optional<String> properties,
      Optional<String> storageOptions) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Helpers
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Parses a dot-separated table id into namespace and table parts.
   *
   * <p>Format: {@code {namespaceId}.{tableName}} Example: {@code ns_with_tables.table_alpha} →
   * namespace={@code ns_with_tables}, table={@code table_alpha}
   *
   * <p>If no dot separator is found, the entire id is treated as the table name in the root
   * namespace.
   */
  private static final class TableRef {
    private final String namespaceId;
    private final String tableName;

    private TableRef(String namespaceId, String tableName) {
      this.namespaceId = namespaceId;
      this.tableName = tableName;
    }

    static TableRef parse(String id) {
      int dot = id.lastIndexOf('.');
      if (dot > 0 && dot < id.length() - 1) {
        return new TableRef(id.substring(0, dot), id.substring(dot + 1));
      }
      // Fallback: treat whole id as table name with no namespace
      return new TableRef("", id);
    }

    String namespaceId() {
      return namespaceId;
    }

    String tableName() {
      return tableName;
    }
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Domain exceptions
  // ─────────────────────────────────────────────────────────────────────────

  /** Signals that a table does not exist. */
  static class TableNotFoundException extends RuntimeException {
    private final String fullTableId;

    TableNotFoundException(String fullTableId) {
      super("Table " + fullTableId + " does not exist");
      this.fullTableId = fullTableId;
    }

    String getFullTableId() {
      return fullTableId;
    }
  }

  /** Signals that a table already exists (for RegisterTable 409). */
  static class TableAlreadyExistsException extends RuntimeException {
    private final String fullTableId;

    TableAlreadyExistsException(String fullTableId) {
      super("Table " + fullTableId + " already exists");
      this.fullTableId = fullTableId;
    }

    String getFullTableId() {
      return fullTableId;
    }
  }
}
