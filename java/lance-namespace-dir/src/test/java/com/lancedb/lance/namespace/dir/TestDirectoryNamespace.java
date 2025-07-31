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
package com.lancedb.lance.namespace.dir;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDirectoryNamespace {

  private static BufferAllocator allocator;
  private DirectoryNamespace namespace;
  private Path tempDir;

  @BeforeAll
  public static void setUpAll() {
    allocator = new RootAllocator(Long.MAX_VALUE);
  }

  @AfterAll
  public static void tearDownAll() throws Exception {
    if (allocator != null) {
      allocator.close();
    }
  }

  @BeforeEach
  public void setUp() throws Exception {
    namespace = new DirectoryNamespace();
    tempDir = Files.createTempDirectory("test-namespace");
  }

  @AfterEach
  public void tearDown() throws Exception {
    if (tempDir != null) {
      Files.walk(tempDir)
          .sorted((a, b) -> -a.compareTo(b))
          .forEach(
              path -> {
                try {
                  Files.delete(path);
                } catch (Exception e) {
                  // Ignore
                }
              });
    }
  }

  private JsonArrowSchema createTestSchema() {
    // Create a simple schema with id (int32) and name (string) fields
    JsonArrowDataType intType = new JsonArrowDataType();
    intType.setType("int32");

    JsonArrowDataType stringType = new JsonArrowDataType();
    stringType.setType("utf8");

    JsonArrowField idField = new JsonArrowField();
    idField.setName("id");
    idField.setType(intType);
    idField.setNullable(false);

    JsonArrowField nameField = new JsonArrowField();
    nameField.setName("name");
    nameField.setType(stringType);
    nameField.setNullable(true);

    List<JsonArrowField> fields = new ArrayList<>();
    fields.add(idField);
    fields.add(nameField);

    JsonArrowSchema schema = new JsonArrowSchema();
    schema.setFields(fields);
    return schema;
  }

  private byte[] createTestArrowData() {
    // For testing, return empty byte array since we only need schema
    return new byte[0];
  }

  @Test
  public void testInitializeWithAbsolutePath() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);
  }

  @Test
  public void testInitializeWithFileUri() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", "file://" + tempDir.toString());
    namespace.initialize(properties, allocator);
  }

  @Test
  public void testInitializeWithRelativePath() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", "./test-namespace");
    namespace.initialize(properties, allocator);
  }

  @Test
  public void testInitializeWithoutRoot() {
    Map<String, String> properties = new HashMap<>();
    // Should use current directory when root is not specified
    namespace.initialize(properties, allocator);
  }

  @Test
  public void testCreateTable() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    CreateTableRequest request = new CreateTableRequest();
    List<String> tableId = new ArrayList<>();
    tableId.add("test_table");
    request.setId(tableId);
    request.setSchema(createTestSchema());

    CreateTableResponse response = namespace.createTable(request, createTestArrowData());
    assertNotNull(response);
    assertNotNull(response.getLocation());
    assertTrue(response.getLocation().contains("test_table"));
    assertEquals(Long.valueOf(1), response.getVersion());

    // Verify Lance dataset was created (check for _versions directory)
    File tableDir = new File(tempDir.toFile(), "test_table");
    assertTrue(tableDir.exists());
    assertTrue(tableDir.isDirectory());

    File versionsDir = new File(tableDir, "_versions");
    assertTrue(versionsDir.exists());
    assertTrue(versionsDir.isDirectory());

    // Verify dataset can be loaded and has expected schema
    try (Dataset dataset = Dataset.open(response.getLocation(), allocator)) {
      assertNotNull(dataset);
      assertNotNull(dataset.getSchema());
      assertEquals(2, dataset.getSchema().getFields().size());
      assertEquals("id", dataset.getSchema().getFields().get(0).getName());
      assertEquals("name", dataset.getSchema().getFields().get(1).getName());
    } catch (Exception e) {
      throw new RuntimeException("Failed to verify created dataset", e);
    }
  }

  @Test
  public void testListTables() throws Exception {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    // Create some tables
    CreateTableRequest request1 = new CreateTableRequest();
    List<String> tableId1 = new ArrayList<>();
    tableId1.add("table1");
    request1.setId(tableId1);
    request1.setSchema(createTestSchema());
    namespace.createTable(request1, createTestArrowData());

    CreateTableRequest request2 = new CreateTableRequest();
    List<String> tableId2 = new ArrayList<>();
    tableId2.add("table2");
    request2.setId(tableId2);
    request2.setSchema(createTestSchema());
    namespace.createTable(request2, createTestArrowData());

    CreateTableRequest request3 = new CreateTableRequest();
    List<String> tableId3 = new ArrayList<>();
    tableId3.add("table3");
    request3.setId(tableId3);
    request3.setSchema(createTestSchema());
    namespace.createTable(request3, createTestArrowData());

    // List tables
    ListTablesRequest listRequest = new ListTablesRequest();
    ListTablesResponse response = namespace.listTables(listRequest);

    assertNotNull(response);
    assertNotNull(response.getTables());
    assertEquals(3, response.getTables().size());
    assertTrue(response.getTables().contains("table1"));
    assertTrue(response.getTables().contains("table2"));
    assertTrue(response.getTables().contains("table3"));
  }

  @Test
  public void testDropTable() throws Exception {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    // First create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    List<String> tableId = new ArrayList<>();
    tableId.add("test_table");
    createRequest.setId(tableId);
    createRequest.setSchema(createTestSchema());
    namespace.createTable(createRequest, createTestArrowData());

    // Verify it exists
    File tableDir = new File(tempDir.toFile(), "test_table");
    assertTrue(tableDir.exists());
    File versionsDir = new File(tableDir, "_versions");
    assertTrue(versionsDir.exists());

    // Drop the table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(tableId);
    DropTableResponse response = namespace.dropTable(dropRequest);

    assertNotNull(response);

    // Verify table directory was removed
    assertFalse(tableDir.exists());
  }

  @Test
  public void testDescribeTable() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    // First create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    List<String> tableId = new ArrayList<>();
    tableId.add("test_table");
    createRequest.setId(tableId);
    createRequest.setSchema(createTestSchema());
    namespace.createTable(createRequest, createTestArrowData());

    // Now describe the table
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setId(tableId);
    DescribeTableResponse response = namespace.describeTable(describeRequest);

    assertNotNull(response);
    assertNotNull(response.getLocation());
    assertTrue(response.getLocation().contains("test_table"));
    assertTrue(response.getLocation().contains(tempDir.toString()));
  }

  @Test
  public void testDescribeNonExistentTable() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    DescribeTableRequest request = new DescribeTableRequest();
    List<String> tableId = new ArrayList<>();
    tableId.add("non_existent_table");
    request.setId(tableId);

    assertThrows(
        Exception.class,
        () -> {
          namespace.describeTable(request);
        });
  }

  @Test
  public void testEmptyListTables() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    ListTablesRequest request = new ListTablesRequest();
    ListTablesResponse response = namespace.listTables(request);

    assertNotNull(response);
    assertNotNull(response.getTables());
    assertEquals(0, response.getTables().size());
  }

  @Test
  public void testNamespaceOperationsNotSupported() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties, allocator);

    // Test CreateNamespace
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          namespace.createNamespace(new CreateNamespaceRequest());
        });

    // Test ListNamespaces
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          namespace.listNamespaces(new ListNamespacesRequest());
        });

    // Test DescribeNamespace
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          namespace.describeNamespace(new DescribeNamespaceRequest());
        });

    // Test DropNamespace
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          namespace.dropNamespace(new DropNamespaceRequest());
        });

    // Test NamespaceExists
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          namespace.namespaceExists(new NamespaceExistsRequest());
        });
  }
}
