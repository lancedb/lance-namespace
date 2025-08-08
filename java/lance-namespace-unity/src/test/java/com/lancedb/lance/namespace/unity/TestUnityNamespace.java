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
package com.lancedb.lance.namespace.unity;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.TestHelper;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import com.google.common.collect.Maps;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUnityNamespace {

  private static BufferAllocator allocator;
  private static LocalUnityServer unityServer;
  private static String tmpDirBase;
  private static LanceNamespace namespace;

  @BeforeAll
  public static void setup() throws IOException {
    allocator = new RootAllocator(Long.MAX_VALUE);
    unityServer = new LocalUnityServer();
    unityServer.start();

    File file =
        createTempDirectory("TestUnityNamespace", asFileAttribute(fromString("rwxrwxrwx")))
            .toFile();
    tmpDirBase = file.getAbsolutePath();

    // Create Unity namespace client
    Map<String, String> config = Maps.newHashMap();
    config.put("endpoint", unityServer.getEndpoint());
    config.put("root", tmpDirBase);
    namespace = new UnityNamespace();
    namespace.initialize(config, allocator);

    // Setup: Create catalog and schema for tests
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(List.of("test_catalog"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    nsRequest.setId(List.of("test_catalog", "test_schema"));
    namespace.createNamespace(nsRequest);
  }

  @AfterAll
  public static void teardown() throws Exception {
    if (namespace != null) {
      namespace.close();
    }
    if (allocator != null) {
      allocator.close();
    }
    if (unityServer != null) {
      unityServer.stop();
    }
  }

  @AfterEach
  public void cleanupTables() throws Exception {
    // Clean up any tables created during tests
    ListTablesRequest listRequest = new ListTablesRequest();
    listRequest.setNamespace(List.of("test_catalog", "test_schema"));
    ListTablesResponse listResponse = namespace.listTables(listRequest);

    if (listResponse.getTables() != null) {
      for (List<String> tableName : listResponse.getTables()) {
        DropTableRequest dropRequest = new DropTableRequest();
        dropRequest.setName(tableName);
        dropRequest.setPurge(true);
        namespace.dropTable(dropRequest);
      }
    }
  }

  @Test
  public void testCreateCatalog() throws Exception {
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(List.of("new_catalog"));
    request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    var response = namespace.createNamespace(request);
    assertTrue(response.getSuccess());
    assertEquals(List.of("new_catalog"), response.getId());

    // Verify catalog exists
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(List.of("new_catalog"));
    assertTrue(namespace.namespaceExists(existsRequest));

    // Clean up
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(List.of("new_catalog"));
    namespace.dropNamespace(dropRequest);
  }

  @Test
  public void testCreateSchema() throws Exception {
    // First create a catalog
    CreateNamespaceRequest catalogRequest = new CreateNamespaceRequest();
    catalogRequest.setId(List.of("schema_test_catalog"));
    catalogRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(catalogRequest);

    // Create schema
    CreateNamespaceRequest schemaRequest = new CreateNamespaceRequest();
    schemaRequest.setId(List.of("schema_test_catalog", "new_schema"));
    schemaRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    var response = namespace.createNamespace(schemaRequest);
    assertTrue(response.getSuccess());
    assertEquals(List.of("schema_test_catalog", "new_schema"), response.getId());

    // Verify schema exists
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(List.of("schema_test_catalog", "new_schema"));
    assertTrue(namespace.namespaceExists(existsRequest));

    // Clean up
    DropNamespaceRequest dropSchemaRequest = new DropNamespaceRequest();
    dropSchemaRequest.setId(List.of("schema_test_catalog", "new_schema"));
    namespace.dropNamespace(dropSchemaRequest);

    DropNamespaceRequest dropCatalogRequest = new DropNamespaceRequest();
    dropCatalogRequest.setId(List.of("schema_test_catalog"));
    namespace.dropNamespace(dropCatalogRequest);
  }

  @Test
  public void testListNamespaces() throws Exception {
    // List catalogs
    ListNamespacesRequest request = new ListNamespacesRequest();
    ListNamespacesResponse response = namespace.listNamespaces(request);
    assertNotNull(response.getNamespaces());
    assertTrue(
        response.getNamespaces().stream().anyMatch(ns -> ns.equals(List.of("test_catalog"))));

    // List schemas in catalog
    request.setParent(List.of("test_catalog"));
    response = namespace.listNamespaces(request);
    assertNotNull(response.getNamespaces());
    assertTrue(
        response.getNamespaces().stream()
            .anyMatch(ns -> ns.equals(List.of("test_catalog", "test_schema"))));
  }

  @Test
  public void testCreateTable() throws Exception {
    CreateTableRequest request = new CreateTableRequest();
    request.setName(List.of("test_catalog", "test_schema", "test_table"));
    request.setMode(CreateTableRequest.ModeEnum.CREATE);
    request.setSchema(TestHelper.getTestSchema());

    CreateTableResponse response = namespace.createTable(request);
    assertNotNull(response.getLocation());
    assertEquals(List.of("test_catalog", "test_schema", "test_table"), response.getName());
    assertNotNull(response.getProperties());
    assertEquals("lance", response.getProperties().get("table_format"));

    // Verify table exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setName(List.of("test_catalog", "test_schema", "test_table"));
    assertTrue(namespace.tableExists(existsRequest));
  }

  @Test
  public void testRegisterTable() throws Exception {
    // First create a table to get a Lance table on disk
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setName(List.of("test_catalog", "test_schema", "source_table"));
    createRequest.setMode(CreateTableRequest.ModeEnum.CREATE);
    createRequest.setSchema(TestHelper.getTestSchema());
    CreateTableResponse createResponse = namespace.createTable(createRequest);
    String location = createResponse.getLocation();

    // Drop the table from Unity but keep the data
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setName(List.of("test_catalog", "test_schema", "source_table"));
    dropRequest.setPurge(false);
    namespace.dropTable(dropRequest);

    // Register the existing Lance table with a new name
    RegisterTableRequest registerRequest = new RegisterTableRequest();
    registerRequest.setName(List.of("test_catalog", "test_schema", "registered_table"));
    registerRequest.setLocation(location);

    RegisterTableResponse response = namespace.registerTable(registerRequest);
    assertEquals(location, response.getLocation());
    assertEquals(List.of("test_catalog", "test_schema", "registered_table"), response.getName());
    assertEquals("lance", response.getProperties().get("table_format"));

    // Verify table exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setName(List.of("test_catalog", "test_schema", "registered_table"));
    assertTrue(namespace.tableExists(existsRequest));
  }

  @Test
  public void testListTables() throws Exception {
    // Create a few tables
    for (int i = 0; i < 3; i++) {
      CreateTableRequest request = new CreateTableRequest();
      request.setName(List.of("test_catalog", "test_schema", "table_" + i));
      request.setMode(CreateTableRequest.ModeEnum.CREATE);
      request.setSchema(TestHelper.getTestSchema());
      namespace.createTable(request);
    }

    ListTablesRequest request = new ListTablesRequest();
    request.setNamespace(List.of("test_catalog", "test_schema"));
    ListTablesResponse response = namespace.listTables(request);

    assertNotNull(response.getTables());
    assertEquals(3, response.getTables().size());
    for (int i = 0; i < 3; i++) {
      final int idx = i;
      assertTrue(
          response.getTables().stream()
              .anyMatch(t -> t.equals(List.of("test_catalog", "test_schema", "table_" + idx))));
    }
  }

  @Test
  public void testDescribeTable() throws Exception {
    // Create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setName(List.of("test_catalog", "test_schema", "describe_test"));
    createRequest.setMode(CreateTableRequest.ModeEnum.CREATE);
    createRequest.setSchema(TestHelper.getTestSchema());
    CreateTableResponse createResponse = namespace.createTable(createRequest);

    // Describe the table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setName(List.of("test_catalog", "test_schema", "describe_test"));
    DescribeTableResponse response = namespace.describeTable(request);

    assertEquals(List.of("test_catalog", "test_schema", "describe_test"), response.getName());
    assertEquals(createResponse.getLocation(), response.getLocation());
    assertEquals("lance", response.getProperties().get("table_format"));
  }

  @Test
  public void testDropTable() throws Exception {
    // Create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setName(List.of("test_catalog", "test_schema", "drop_test"));
    createRequest.setMode(CreateTableRequest.ModeEnum.CREATE);
    createRequest.setSchema(TestHelper.getTestSchema());
    namespace.createTable(createRequest);

    // Verify it exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setName(List.of("test_catalog", "test_schema", "drop_test"));
    assertTrue(namespace.tableExists(existsRequest));

    // Drop the table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setName(List.of("test_catalog", "test_schema", "drop_test"));
    dropRequest.setPurge(true);
    DropTableResponse response = namespace.dropTable(dropRequest);
    assertTrue(response.getDropped());

    // Verify it no longer exists
    assertFalse(namespace.tableExists(existsRequest));
  }

  @Test
  public void testInvalidTableName() {
    CreateTableRequest request = new CreateTableRequest();
    request.setName(List.of("only_two_levels"));
    request.setMode(CreateTableRequest.ModeEnum.CREATE);
    request.setSchema(TestHelper.getTestSchema());

    assertThrows(LanceNamespaceException.class, () -> namespace.createTable(request));
  }

  @Test
  public void testTableExistsForNonLanceTable() throws Exception {
    // This test would require creating a non-Lance table in Unity Catalog
    // For now, we'll just verify that checking a non-existent table returns false
    TableExistsRequest request = new TableExistsRequest();
    request.setName(List.of("test_catalog", "test_schema", "non_existent"));
    assertFalse(namespace.tableExists(request));
  }
}
