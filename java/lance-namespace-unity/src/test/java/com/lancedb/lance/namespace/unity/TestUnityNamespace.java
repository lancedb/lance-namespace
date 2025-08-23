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
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for Unity Catalog namespace implementation.
 * 
 * To run these tests, start Unity Catalog with docker-compose:
 * ```
 * git clone https://github.com/unitycatalog/unitycatalog.git
 * cd unitycatalog
 * docker-compose up
 * ```
 * 
 * The server will be available at http://localhost:8080
 */
public class TestUnityNamespace {
  private static final String UNITY_ENDPOINT = "http://localhost:8080";
  private static final String UNITY_CATALOG = "unity";
  private static final String TEST_SCHEMA = "test_schema_" + UUID.randomUUID().toString().replace("-", "");
  
  private LanceNamespace namespace;
  private BufferAllocator allocator;
  private Path tempDir;
  
  @BeforeClass
  public static void checkUnityServer() {
    // Check if Unity server is running
    try {
      java.net.HttpURLConnection connection = 
          (java.net.HttpURLConnection) new java.net.URL(UNITY_ENDPOINT + "/api/2.1/unity-catalog/catalogs")
              .openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(1000);
      int responseCode = connection.getResponseCode();
      connection.disconnect();
      
      if (responseCode != 200) {
        System.err.println("Unity Catalog server not available. Please start with docker-compose.");
        System.err.println("Skipping Unity namespace tests.");
        org.junit.Assume.assumeTrue("Unity Catalog server is not running", false);
      }
    } catch (Exception e) {
      System.err.println("Unity Catalog server not available: " + e.getMessage());
      System.err.println("Please start Unity Catalog with docker-compose.");
      org.junit.Assume.assumeTrue("Unity Catalog server is not running", false);
    }
  }
  
  @Before
  public void setUp() throws IOException {
    allocator = new RootAllocator();
    tempDir = Files.createTempDirectory("unity_test_");
    
    Map<String, String> config = new HashMap<>();
    config.put("endpoint", UNITY_ENDPOINT);
    config.put("catalog", UNITY_CATALOG);
    config.put("root", tempDir.toString());
    
    namespace = new UnityNamespace();
    namespace.initialize(config, allocator);
  }
  
  @After
  public void tearDown() throws IOException {
    // Clean up test schema if it exists
    try {
      DropNamespaceRequest dropRequest = new DropNamespaceRequest();
      dropRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
      dropRequest.setPurge(true);
      namespace.dropNamespace(dropRequest);
    } catch (Exception e) {
      // Ignore cleanup errors
    }
    
    if (namespace != null) {
      namespace.close();
    }
    
    if (allocator != null) {
      allocator.close();
    }
    
    // Clean up temp directory
    if (tempDir != null) {
      deleteRecursively(tempDir.toFile());
    }
  }
  
  @Test
  public void testListCatalogs() {
    ListNamespacesRequest request = new ListNamespacesRequest();
    request.setId(Arrays.asList());
    
    ListNamespacesResponse response = namespace.listNamespaces(request);
    assertNotNull(response);
    assertNotNull(response.getIds());
    assertTrue(response.getIds().contains(UNITY_CATALOG));
  }
  
  @Test
  public void testCreateAndDropSchema() {
    // Create schema
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    Map<String, String> properties = new HashMap<>();
    properties.put("test_property", "test_value");
    createRequest.setProperties(properties);
    
    CreateNamespaceResponse createResponse = namespace.createNamespace(createRequest);
    assertNotNull(createResponse);
    assertEquals(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA), createResponse.getId());
    
    // Check schema exists
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    assertTrue(namespace.namespaceExists(existsRequest));
    
    // Describe schema
    DescribeNamespaceRequest describeRequest = new DescribeNamespaceRequest();
    describeRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    DescribeNamespaceResponse describeResponse = namespace.describeNamespace(describeRequest);
    assertNotNull(describeResponse);
    
    // List schemas
    ListNamespacesRequest listRequest = new ListNamespacesRequest();
    listRequest.setId(Arrays.asList(UNITY_CATALOG));
    ListNamespacesResponse listResponse = namespace.listNamespaces(listRequest);
    assertNotNull(listResponse);
    assertTrue(listResponse.getIds().contains(TEST_SCHEMA));
    
    // Drop schema
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    DropNamespaceResponse dropResponse = namespace.dropNamespace(dropRequest);
    assertNotNull(dropResponse);
    assertTrue(dropResponse.isDropped());
    
    // Check schema no longer exists
    assertFalse(namespace.namespaceExists(existsRequest));
  }
  
  @Test
  public void testCreateDuplicateSchema() {
    // Create schema first time
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    namespace.createNamespace(createRequest);
    
    // Try to create same schema again
    try {
      namespace.createNamespace(createRequest);
      fail("Expected exception for duplicate schema");
    } catch (LanceNamespaceException e) {
      assertTrue(e.getMessage().contains("already exists"));
    }
  }
  
  @Test
  public void testTableLifecycle() {
    // Create schema first
    CreateNamespaceRequest createNsRequest = new CreateNamespaceRequest();
    createNsRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    namespace.createNamespace(createNsRequest);
    
    String tableName = "test_table_" + UUID.randomUUID().toString().replace("-", "");
    List<String> tableId = Arrays.asList(UNITY_CATALOG, TEST_SCHEMA, tableName);
    
    // Create table
    CreateTableRequest createTableRequest = new CreateTableRequest();
    createTableRequest.setId(tableId);
    
    // Create a simple Arrow schema
    JsonArrowSchema arrowSchema = new JsonArrowSchema();
    JsonArrowField field1 = new JsonArrowField();
    field1.setName("id");
    field1.setType(Map.of("name", "int"));
    field1.setNullable(false);
    field1.setChildren(Arrays.asList());
    
    JsonArrowField field2 = new JsonArrowField();
    field2.setName("name");
    field2.setType(Map.of("name", "utf8"));
    field2.setNullable(true);
    field2.setChildren(Arrays.asList());
    
    arrowSchema.setFields(Arrays.asList(field1, field2));
    createTableRequest.setJsonArrowSchema(arrowSchema);
    
    Map<String, String> tableProps = new HashMap<>();
    tableProps.put("custom_prop", "custom_value");
    createTableRequest.setProperties(tableProps);
    createTableRequest.setManagedBy("storage");
    
    CreateTableResponse createTableResponse = namespace.createTable(createTableRequest);
    assertNotNull(createTableResponse);
    assertEquals(tableId, createTableResponse.getId());
    assertNotNull(createTableResponse.getPath());
    
    // Check table exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setId(tableId);
    assertTrue(namespace.tableExists(existsRequest));
    
    // List tables
    ListTablesRequest listRequest = new ListTablesRequest();
    listRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA));
    ListTablesResponse listResponse = namespace.listTables(listRequest);
    assertNotNull(listResponse);
    assertTrue(listResponse.getNames().contains(tableName));
    
    // Describe table
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setId(tableId);
    DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
    assertNotNull(describeResponse);
    assertEquals(tableId, describeResponse.getId());
    assertEquals("storage", describeResponse.getManagedBy());
    assertNotNull(describeResponse.getJsonArrowSchema());
    
    // Drop table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(tableId);
    dropRequest.setPurge(true);
    DropTableResponse dropResponse = namespace.dropTable(dropRequest);
    assertNotNull(dropResponse);
    assertTrue(dropResponse.isDropped());
    
    // Check table no longer exists
    assertFalse(namespace.tableExists(existsRequest));
  }
  
  @Test
  public void testInvalidCatalog() {
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Arrays.asList("invalid_catalog", "schema"));
    
    try {
      namespace.createNamespace(request);
      fail("Expected exception for invalid catalog");
    } catch (LanceNamespaceException e) {
      assertTrue(e.getMessage().contains("Cannot create namespace in catalog"));
    }
  }
  
  @Test
  public void testInvalidNamespaceLevel() {
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Arrays.asList(UNITY_CATALOG)); // Only 1 level, should be 2
    
    try {
      namespace.createNamespace(request);
      fail("Expected exception for invalid namespace level");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Expect a 2-level namespace"));
    }
  }
  
  @Test
  public void testPagination() {
    // Create multiple schemas
    for (int i = 0; i < 3; i++) {
      CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
      createRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA + "_" + i));
      namespace.createNamespace(createRequest);
    }
    
    // List with pagination
    ListNamespacesRequest listRequest = new ListNamespacesRequest();
    listRequest.setId(Arrays.asList(UNITY_CATALOG));
    listRequest.setMaxResults(2);
    
    ListNamespacesResponse response = namespace.listNamespaces(listRequest);
    assertNotNull(response);
    assertEquals(2, response.getIds().size());
    
    // Clean up
    for (int i = 0; i < 3; i++) {
      DropNamespaceRequest dropRequest = new DropNamespaceRequest();
      dropRequest.setId(Arrays.asList(UNITY_CATALOG, TEST_SCHEMA + "_" + i));
      namespace.dropNamespace(dropRequest);
    }
  }
  
  private void deleteRecursively(File file) {
    if (file.isDirectory()) {
      File[] children = file.listFiles();
      if (children != null) {
        for (File child : children) {
          deleteRecursively(child);
        }
      }
    }
    file.delete();
  }
}