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

import org.lance.namespace.server.springboot.api.NamespaceApi;
import org.lance.namespace.server.springboot.model.CreateNamespaceRequest;
import org.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import org.lance.namespace.server.springboot.model.DescribeNamespaceRequest;
import org.lance.namespace.server.springboot.model.DescribeNamespaceResponse;
import org.lance.namespace.server.springboot.model.DropNamespaceRequest;
import org.lance.namespace.server.springboot.model.DropNamespaceResponse;
import org.lance.namespace.server.springboot.model.ListNamespacesResponse;
import org.lance.namespace.server.springboot.model.ListTablesResponse;
import org.lance.namespace.server.springboot.model.NamespaceExistsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Test-scope implementation of {@link NamespaceApi} backed by {@link InMemoryNamespaceFixtures}.
 *
 * <p>Active only when the {@code pact} Spring profile is enabled. Handles all 5 namespace
 * operations exercised by the Phase 2 Pact interactions.
 */
@Profile("pact")
@RestController
public class PactNamespaceController implements NamespaceApi {

  private final InMemoryNamespaceFixtures fixtures;

  @Autowired
  public PactNamespaceController(InMemoryNamespaceFixtures fixtures) {
    this.fixtures = fixtures;
  }

  // ─────────────────────────────────────────────────────────────────────────
  // ListNamespaces — GET /v1/namespace/{id}/list
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<ListNamespacesResponse> listNamespaces(
      String id, Optional<String> delimiter, Optional<String> pageToken, Optional<Integer> limit) {

    if (!fixtures.namespaceExists(id)) {
      throw new NamespaceNotFoundException(id);
    }

    Set<String> children = new LinkedHashSet<>(fixtures.listChildNamespaces(id));
    ListNamespacesResponse response = new ListNamespacesResponse(children);
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // DescribeNamespace — POST /v1/namespace/{id}/describe
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<DescribeNamespaceResponse> describeNamespace(
      String id, DescribeNamespaceRequest req, Optional<String> delimiter) {

    if (!fixtures.namespaceExists(id)) {
      throw new NamespaceNotFoundException(id);
    }

    DescribeNamespaceResponse response = new DescribeNamespaceResponse();
    response.setProperties(new LinkedHashMap<>());
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // CreateNamespace — POST /v1/namespace/{id}/create
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<CreateNamespaceResponse> createNamespace(
      String id, CreateNamespaceRequest req, Optional<String> delimiter) {

    if (fixtures.namespaceExists(id)) {
      throw new NamespaceAlreadyExistsException(id);
    }

    fixtures.createNamespace(id);
    CreateNamespaceResponse response = new CreateNamespaceResponse();
    response.setProperties(new LinkedHashMap<>());
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // DropNamespace — POST /v1/namespace/{id}/drop
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<DropNamespaceResponse> dropNamespace(
      String id, DropNamespaceRequest req, Optional<String> delimiter) {

    if (!fixtures.namespaceExists(id)) {
      throw new NamespaceNotFoundException(id);
    }

    DropNamespaceResponse response = new DropNamespaceResponse();
    response.setProperties(null);
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // NamespaceExists — POST /v1/namespace/{id}/exists
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<Void> namespaceExists(
      String id, NamespaceExistsRequest req, Optional<String> delimiter) {

    if (!fixtures.namespaceExists(id)) {
      throw new NamespaceNotFoundException(id);
    }

    return ResponseEntity.ok().build();
  }

  // ─────────────────────────────────────────────────────────────────────────
  // ListTables — GET /v1/namespace/{id}/table/list (via NamespaceApi)
  // ─────────────────────────────────────────────────────────────────────────

  @Override
  public ResponseEntity<ListTablesResponse> listTables(
      String id,
      Optional<String> delimiter,
      Optional<String> pageToken,
      Optional<Integer> limit,
      Optional<Boolean> includeDeclared) {

    if (!fixtures.namespaceExists(id)) {
      throw new NamespaceNotFoundException(id);
    }

    Set<String> tableNames = new LinkedHashSet<>(fixtures.listTables(id));
    ListTablesResponse response = new ListTablesResponse(tableNames);
    return ResponseEntity.ok(response);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Domain exceptions
  // ─────────────────────────────────────────────────────────────────────────

  /** Signals that a namespace does not exist in the in-memory fixture store. */
  static class NamespaceNotFoundException extends RuntimeException {
    private final String namespaceId;

    NamespaceNotFoundException(String namespaceId) {
      super("Namespace " + namespaceId + " does not exist");
      this.namespaceId = namespaceId;
    }

    String getNamespaceId() {
      return namespaceId;
    }
  }

  /** Signals that a namespace already exists (for CreateNamespace 409). */
  static class NamespaceAlreadyExistsException extends RuntimeException {
    private final String namespaceId;

    NamespaceAlreadyExistsException(String namespaceId) {
      super("Namespace " + namespaceId + " already exists");
      this.namespaceId = namespaceId;
    }

    String getNamespaceId() {
      return namespaceId;
    }
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Exception handler — maps domain exceptions → pact-compatible bodies
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Maps domain exceptions to HTTP responses whose JSON bodies include all four fields required by
   * the consumer pact: {@code error}, {@code code}, {@code type}, {@code detail}.
   *
   * <p>Pact's body matching rules use type-matchers, so only the field types matter.
   */
  @RestControllerAdvice
  @Profile("pact")
  static class PactExceptionHandler {

    @ExceptionHandler(NamespaceNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleNamespaceNotFound(NamespaceNotFoundException ex) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("error", "NAMESPACE_NOT_FOUND");
      body.put("code", 404);
      body.put("type", "org.lance.namespace.NamespaceNotFoundException");
      body.put("detail", "Namespace " + ex.getNamespaceId() + " does not exist");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(NamespaceAlreadyExistsException.class)
    ResponseEntity<Map<String, Object>> handleNamespaceAlreadyExists(
        NamespaceAlreadyExistsException ex) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("error", "NAMESPACE_ALREADY_EXISTS");
      body.put("code", 409);
      body.put("type", "org.lance.namespace.NamespaceAlreadyExistsException");
      body.put("detail", "Namespace " + ex.getNamespaceId() + " already exists");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(PactTableController.TableNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleTableNotFound(
        PactTableController.TableNotFoundException ex) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("error", "TABLE_NOT_FOUND");
      body.put("code", 404);
      body.put("type", "org.lance.namespace.TableNotFoundException");
      body.put("detail", "Table " + ex.getFullTableId() + " does not exist");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(PactTableController.TableAlreadyExistsException.class)
    ResponseEntity<Map<String, Object>> handleTableAlreadyExists(
        PactTableController.TableAlreadyExistsException ex) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("error", "TABLE_ALREADY_EXISTS");
      body.put("code", 409);
      body.put("type", "org.lance.namespace.TableAlreadyExistsException");
      body.put("detail", "Table " + ex.getFullTableId() + " already exists");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
  }
}
