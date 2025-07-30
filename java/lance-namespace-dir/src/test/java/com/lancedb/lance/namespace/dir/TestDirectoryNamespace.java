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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDirectoryNamespace {

  private DirectoryNamespace namespace;
  private Path tempDir;

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
          .forEach(path -> {
            try {
              Files.delete(path);
            } catch (Exception e) {
              // Ignore
            }
          });
    }
  }

  @Test
  public void testInitializeWithAbsolutePath() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties);
  }

  @Test
  public void testInitializeWithFileUri() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", "file://" + tempDir.toString());
    namespace.initialize(properties);
  }

  @Test
  public void testInitializeWithRelativePath() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", "./test-namespace");
    namespace.initialize(properties);
  }

  @Test
  public void testInitializeWithoutRoot() {
    Map<String, String> properties = new HashMap<>();
    // Should use current directory when root is not specified
    namespace.initialize(properties);
  }

  @Test
  public void testRegisterTable() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties);

    RegisterTableRequest request = new RegisterTableRequest();
    request.setTable("test_table");
    
    RegisterTableResponse response = namespace.registerTable(request);
    assertNotNull(response);
    assertEquals("test_table", response.getTable());
    assertEquals("test_table/", response.getTableUri());

    // Verify table directory was created
    File tableDir = new File(tempDir.toFile(), "test_table");
    assertTrue(tableDir.exists());
    assertTrue(tableDir.isDirectory());
  }

  @Test
  public void testListTables() throws Exception {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties);

    // Create some tables
    RegisterTableRequest request1 = new RegisterTableRequest();
    request1.setTable("table1");
    namespace.registerTable(request1);

    RegisterTableRequest request2 = new RegisterTableRequest();
    request2.setTable("table2");
    namespace.registerTable(request2);

    RegisterTableRequest request3 = new RegisterTableRequest();
    request3.setTable("table3");
    namespace.registerTable(request3);

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
  public void testDeregisterTable() throws Exception {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties);

    // First register a table
    RegisterTableRequest registerRequest = new RegisterTableRequest();
    registerRequest.setTable("test_table");
    namespace.registerTable(registerRequest);

    // Verify it exists
    File tableDir = new File(tempDir.toFile(), "test_table");
    assertTrue(tableDir.exists());

    // Deregister the table
    DeregisterTableRequest deregisterRequest = new DeregisterTableRequest();
    deregisterRequest.setTable("test_table");
    DeregisterTableResponse response = namespace.deregisterTable(deregisterRequest);
    
    assertNotNull(response);
    assertEquals("test_table", response.getTable());

    // Verify table directory was removed
    assertTrue(!tableDir.exists());
  }

  @Test
  public void testEmptyListTables() {
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    namespace.initialize(properties);

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
    namespace.initialize(properties);

    // Test CreateNamespace
    assertThrows(UnsupportedOperationException.class, () -> {
      namespace.createNamespace(new CreateNamespaceRequest());
    });

    // Test ListNamespaces
    assertThrows(UnsupportedOperationException.class, () -> {
      namespace.listNamespaces(new ListNamespacesRequest());
    });

    // Test DescribeNamespace
    assertThrows(UnsupportedOperationException.class, () -> {
      namespace.describeNamespace(new DescribeNamespaceRequest());
    });

    // Test DropNamespace
    assertThrows(UnsupportedOperationException.class, () -> {
      namespace.dropNamespace(new DropNamespaceRequest());
    });

    // Test NamespaceExists
    assertThrows(UnsupportedOperationException.class, () -> {
      namespace.namespaceExists(new NamespaceExistsRequest());
    });
  }
}