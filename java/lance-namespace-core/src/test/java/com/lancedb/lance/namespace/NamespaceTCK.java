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
package com.lancedb.lance.namespace;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Technology Compatibility Kit (TCK) for LanceNamespace implementations.
 *
 * <p>All namespace implementations should extend this class and implement the abstract methods to
 * ensure they meet the expected behavior contract.
 */
public abstract class NamespaceTCK {

  protected static BufferAllocator allocator;
  protected LanceNamespace namespace;

  // Abstract methods that implementations must provide

  /** Create and initialize a namespace instance with appropriate configuration. */
  protected abstract LanceNamespace createNamespace() throws Exception;

  /** Clean up any resources created by the namespace implementation. */
  protected abstract void cleanupNamespace() throws Exception;

  /** Get the temporary directory for test data. */
  protected abstract String getTempDirectory();

  /** Get the capabilities of this namespace implementation. */
  protected abstract NamespaceCapabilities getCapabilities();

  /**
   * Get test-specific namespace ID parts for creating test namespaces. Implementations should
   * return appropriate namespace hierarchy.
   */
  protected abstract List<String> getTestNamespaceId();

  /**
   * Get test-specific table ID parts for creating test tables. Implementations should return
   * appropriate table identifier hierarchy.
   */
  protected abstract List<String> getTestTableId(String tableName);

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
    namespace = createNamespace();
  }

  @AfterEach
  public void tearDown() throws Exception {
    cleanupNamespace();
  }

  // ========== Helper Methods ==========

  protected JsonArrowSchema createTestSchema() {
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

  protected byte[] createTestArrowData() {
    try {
      return TestHelper.createTestArrowData(allocator);
    } catch (IOException e) {
      throw new RuntimeException("Failed to create test arrow data", e);
    }
  }

  protected byte[] createEmptyArrowData() {
    try {
      return TestHelper.createEmptyArrowData(allocator);
    } catch (IOException e) {
      throw new RuntimeException("Failed to create empty arrow data", e);
    }
  }

  // ========== Table Operations Tests (Always Run) ==========

  @Test
  public void testCreateTable() {
    CreateTableRequest request = new CreateTableRequest();
    List<String> tableId = getTestTableId("test_table");
    request.setId(tableId);
    request.setSchema(createTestSchema());

    CreateTableResponse response = namespace.createTable(request, createTestArrowData());
    assertNotNull(response);
    assertNotNull(response.getLocation());
    assertTrue(response.getLocation().contains("test_table"));
    assertEquals(Long.valueOf(1), response.getVersion());

    // Verify dataset can be loaded if location is a file path
    if (response.getLocation().startsWith("file:") || !response.getLocation().contains("://")) {
      String location = response.getLocation().replace("file:", "");
      try (Dataset dataset = Dataset.open(location, allocator)) {
        assertNotNull(dataset);
        assertNotNull(dataset.getSchema());
        assertEquals(2, dataset.getSchema().getFields().size());
        assertEquals("id", dataset.getSchema().getFields().get(0).getName());
        assertEquals("name", dataset.getSchema().getFields().get(1).getName());
      } catch (Exception e) {
        throw new RuntimeException("Failed to verify created dataset", e);
      }
    }
  }

  @Test
  public void testCreateTableAlreadyExists() {
    CreateTableRequest request = new CreateTableRequest();
    List<String> tableId = getTestTableId("duplicate_table");
    request.setId(tableId);
    request.setSchema(createTestSchema());

    // Create the table first time
    namespace.createTable(request, createTestArrowData());

    // Try to create it again
    assertThrows(
        Exception.class,
        () -> namespace.createTable(request, createTestArrowData()),
        "Should throw exception when creating duplicate table");
  }

  @Test
  public void testCreateTableWithoutData() {
    CreateTableRequest request = new CreateTableRequest();
    List<String> tableId = getTestTableId("empty_table");
    request.setId(tableId);
    request.setSchema(createTestSchema());

    CreateTableResponse response = namespace.createTable(request, createEmptyArrowData());
    assertNotNull(response);
    assertNotNull(response.getLocation());
  }

  @Test
  public void testDescribeTable() {
    // First create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    List<String> tableId = getTestTableId("describe_test");
    createRequest.setId(tableId);
    createRequest.setSchema(createTestSchema());
    namespace.createTable(createRequest, createTestArrowData());

    // Now describe it
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setId(tableId);
    DescribeTableResponse response = namespace.describeTable(describeRequest);

    assertNotNull(response);
    assertNotNull(response.getLocation());
    assertTrue(response.getLocation().contains("describe_test"));
  }

  @Test
  public void testDescribeNonExistentTable() {
    DescribeTableRequest request = new DescribeTableRequest();
    List<String> tableId = getTestTableId("non_existent_table");
    request.setId(tableId);

    assertThrows(
        Exception.class,
        () -> namespace.describeTable(request),
        "Should throw exception when describing non-existent table");
  }

  @Test
  public void testDropTable() {
    // First create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    List<String> tableId = getTestTableId("drop_test");
    createRequest.setId(tableId);
    createRequest.setSchema(createTestSchema());
    namespace.createTable(createRequest, createTestArrowData());

    // Drop the table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(tableId);
    DropTableResponse response = namespace.dropTable(dropRequest);

    assertNotNull(response);

    // Verify table no longer exists
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setId(tableId);
    assertThrows(
        Exception.class,
        () -> namespace.describeTable(describeRequest),
        "Table should not exist after drop");
  }

  @Test
  public void testDropNonExistentTable() {
    DropTableRequest request = new DropTableRequest();
    List<String> tableId = getTestTableId("non_existent_drop");
    request.setId(tableId);

    assertThrows(
        Exception.class,
        () -> namespace.dropTable(request),
        "Should throw exception when dropping non-existent table");
  }

  @Test
  public void testListTables() {
    // Create multiple tables
    for (int i = 1; i <= 3; i++) {
      CreateTableRequest request = new CreateTableRequest();
      List<String> tableId = getTestTableId("list_table_" + i);
      request.setId(tableId);
      request.setSchema(createTestSchema());
      namespace.createTable(request, createTestArrowData());
    }

    // List tables
    ListTablesRequest listRequest = new ListTablesRequest();
    // Set appropriate namespace ID based on capabilities
    if (getCapabilities().supportsNamespaceOperations()) {
      listRequest.setId(getTestNamespaceId());
    }

    ListTablesResponse response = namespace.listTables(listRequest);

    assertNotNull(response);
    assertNotNull(response.getTables());
    assertTrue(response.getTables().size() >= 3, "Should have at least 3 tables");
    assertTrue(response.getTables().contains("list_table_1"));
    assertTrue(response.getTables().contains("list_table_2"));
    assertTrue(response.getTables().contains("list_table_3"));
  }

  @Test
  public void testListTablesEmpty() {
    ListTablesRequest request = new ListTablesRequest();
    // Set appropriate namespace ID based on capabilities
    if (getCapabilities().supportsNamespaceOperations()) {
      request.setId(getTestNamespaceId());
    }

    ListTablesResponse response = namespace.listTables(request);

    assertNotNull(response);
    assertNotNull(response.getTables());
    // Note: May not be exactly 0 if other tests have run
  }

  @Test
  public void testTableExists() {
    // First create a table
    CreateTableRequest createRequest = new CreateTableRequest();
    List<String> tableId = getTestTableId("exists_test");
    createRequest.setId(tableId);
    createRequest.setSchema(createTestSchema());
    namespace.createTable(createRequest, createTestArrowData());

    // Test that the table exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setId(tableId);

    // Should complete without throwing exception
    namespace.tableExists(existsRequest);
  }

  @Test
  public void testTableExistsNonExistent() {
    TableExistsRequest request = new TableExistsRequest();
    List<String> tableId = getTestTableId("non_existent_exists");
    request.setId(tableId);

    assertThrows(
        LanceNamespaceException.class,
        () -> namespace.tableExists(request),
        "Should throw LanceNamespaceException for non-existent table");
  }

  // ========== Conditional Table Tests ==========

  @Test
  public void testListTablesWithPagination() {
    Assumptions.assumeTrue(
        getCapabilities().supportsPagination(), "Pagination not supported by this namespace");

    // Create multiple tables for pagination
    for (int i = 1; i <= 5; i++) {
      CreateTableRequest request = new CreateTableRequest();
      List<String> tableId = getTestTableId("page_table_" + i);
      request.setId(tableId);
      request.setSchema(createTestSchema());
      namespace.createTable(request, createTestArrowData());
    }

    // List tables with limit
    ListTablesRequest request = new ListTablesRequest();
    if (getCapabilities().supportsNamespaceOperations()) {
      request.setId(getTestNamespaceId());
    }
    request.setLimit(3);

    ListTablesResponse response = namespace.listTables(request);
    assertEquals(3, response.getTables().size());
    assertNotNull(response.getPageToken(), "Should have page token for more results");

    // Get next page
    ListTablesRequest nextRequest = new ListTablesRequest();
    if (getCapabilities().supportsNamespaceOperations()) {
      nextRequest.setId(getTestNamespaceId());
    }
    nextRequest.setPageToken(response.getPageToken());

    ListTablesResponse nextResponse = namespace.listTables(nextRequest);
    assertTrue(nextResponse.getTables().size() >= 2, "Should have remaining tables");
  }

  // ========== Namespace Operations Tests (Conditional) ==========

  @Test
  public void testCreateNamespace() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Arrays.asList("test_namespace_create"));
    request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> properties = new HashMap<>();
    properties.put("description", "Test namespace");
    request.setProperties(properties);

    CreateNamespaceResponse response = namespace.createNamespace(request);
    assertNotNull(response);
    assertNotNull(response.getProperties());
  }

  @Test
  public void testCreateNamespaceAlreadyExists() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Arrays.asList("test_duplicate_ns"));
    request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    // Create first time
    namespace.createNamespace(request);

    // Try to create again
    assertThrows(
        Exception.class,
        () -> namespace.createNamespace(request),
        "Should throw exception when creating duplicate namespace");
  }

  @Test
  public void testDescribeNamespace() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    // First create a namespace
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList("test_describe_ns"));
    createRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> properties = new HashMap<>();
    properties.put("description", "Namespace to describe");
    properties.put("custom_key", "custom_value");
    createRequest.setProperties(properties);

    namespace.createNamespace(createRequest);

    // Describe it
    DescribeNamespaceRequest describeRequest = new DescribeNamespaceRequest();
    describeRequest.setId(Arrays.asList("test_describe_ns"));

    DescribeNamespaceResponse response = namespace.describeNamespace(describeRequest);
    assertNotNull(response);
    assertNotNull(response.getProperties());
    assertEquals("custom_value", response.getProperties().get("custom_key"));
  }

  @Test
  public void testDropNamespace() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    // First create a namespace
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList("test_drop_ns"));
    createRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(createRequest);

    // Drop it
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Arrays.asList("test_drop_ns"));
    dropRequest.setMode(DropNamespaceRequest.ModeEnum.FAIL);

    DropNamespaceResponse response = namespace.dropNamespace(dropRequest);
    assertNotNull(response);

    // Verify it's gone
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Arrays.asList("test_drop_ns"));

    assertThrows(
        LanceNamespaceException.class,
        () -> namespace.namespaceExists(existsRequest),
        "Namespace should not exist after drop");
  }

  @Test
  public void testListNamespaces() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    // Create some namespaces
    for (int i = 1; i <= 3; i++) {
      CreateNamespaceRequest request = new CreateNamespaceRequest();
      request.setId(Arrays.asList("test_list_ns_" + i));
      request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
      namespace.createNamespace(request);
    }

    // List them
    ListNamespacesRequest listRequest = new ListNamespacesRequest();
    ListNamespacesResponse response = namespace.listNamespaces(listRequest);

    assertNotNull(response);
    assertNotNull(response.getNamespaces());
    assertTrue(response.getNamespaces().size() >= 3);
  }

  @Test
  public void testNamespaceExists() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    // Create a namespace
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList("test_exists_ns"));
    createRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(createRequest);

    // Check it exists
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Arrays.asList("test_exists_ns"));

    // Should complete without exception
    namespace.namespaceExists(existsRequest);
  }

  // ========== Nested Namespace Tests (Conditional) ==========

  @Test
  public void testCreateNestedNamespace() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations()
            && getCapabilities().supportsNestedNamespaces(),
        "Nested namespace operations not supported");

    // Create parent namespace
    CreateNamespaceRequest parentRequest = new CreateNamespaceRequest();
    parentRequest.setId(Arrays.asList("parent_ns"));
    parentRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(parentRequest);

    // Create nested namespace
    CreateNamespaceRequest nestedRequest = new CreateNamespaceRequest();
    nestedRequest.setId(Arrays.asList("parent_ns", "child_ns"));
    nestedRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    CreateNamespaceResponse response = namespace.createNamespace(nestedRequest);
    assertNotNull(response);
  }

  @Test
  public void testCreateTableInNestedNamespace() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations()
            && getCapabilities().supportsNestedNamespaces(),
        "Nested namespace operations not supported");

    // Create parent and nested namespaces
    CreateNamespaceRequest parentRequest = new CreateNamespaceRequest();
    parentRequest.setId(Arrays.asList("parent_table_ns"));
    parentRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(parentRequest);

    CreateNamespaceRequest nestedRequest = new CreateNamespaceRequest();
    nestedRequest.setId(Arrays.asList("parent_table_ns", "child_table_ns"));
    nestedRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nestedRequest);

    // Create table in nested namespace
    CreateTableRequest tableRequest = new CreateTableRequest();
    tableRequest.setId(Arrays.asList("parent_table_ns", "child_table_ns", "nested_table"));
    tableRequest.setSchema(createTestSchema());

    CreateTableResponse response = namespace.createTable(tableRequest, createTestArrowData());
    assertNotNull(response);
  }

  // ========== Delete Behavior Tests (Conditional) ==========

  @Test
  public void testDropNamespaceWithRestrict() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations()
            && getCapabilities().supportsRestrictDelete(),
        "RESTRICT delete not supported");

    // Create namespace with table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Arrays.asList("restrict_test_ns"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest tableRequest = new CreateTableRequest();
    List<String> tableId = new ArrayList<>(Arrays.asList("restrict_test_ns"));
    tableId.add("test_table");
    tableRequest.setId(tableId);
    tableRequest.setSchema(createTestSchema());
    namespace.createTable(tableRequest, createTestArrowData());

    // Try to drop with RESTRICT - should fail
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Arrays.asList("restrict_test_ns"));
    dropRequest.setBehavior(DropNamespaceRequest.BehaviorEnum.RESTRICT);

    assertThrows(
        Exception.class,
        () -> namespace.dropNamespace(dropRequest),
        "Should fail to drop non-empty namespace with RESTRICT");
  }

  @Test
  public void testDropNamespaceWithCascade() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations()
            && getCapabilities().supportsCascadeDelete(),
        "CASCADE delete not supported");

    // Create namespace with tables
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Arrays.asList("cascade_test_ns"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    for (int i = 1; i <= 2; i++) {
      CreateTableRequest tableRequest = new CreateTableRequest();
      List<String> tableId = new ArrayList<>(Arrays.asList("cascade_test_ns"));
      tableId.add("cascade_table_" + i);
      tableRequest.setId(tableId);
      tableRequest.setSchema(createTestSchema());
      namespace.createTable(tableRequest, createTestArrowData());
    }

    // Drop with CASCADE - should succeed
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Arrays.asList("cascade_test_ns"));
    dropRequest.setBehavior(DropNamespaceRequest.BehaviorEnum.CASCADE);

    DropNamespaceResponse response = namespace.dropNamespace(dropRequest);
    assertNotNull(response);

    // Verify namespace is gone
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Arrays.asList("cascade_test_ns"));

    assertThrows(
        LanceNamespaceException.class,
        () -> namespace.namespaceExists(existsRequest),
        "Namespace should not exist after CASCADE drop");
  }

  // ========== Register/Deregister Tests (Conditional) ==========

  @Test
  public void testRegisterTable() {
    Assumptions.assumeTrue(
        getCapabilities().supportsRegisterDeregister(), "Register/Deregister not supported");

    RegisterTableRequest request = new RegisterTableRequest();
    request.setId(getTestTableId("registered_table"));
    request.setLocation(getTempDirectory() + "/registered_table.lance");

    RegisterTableResponse response = namespace.registerTable(request);
    assertNotNull(response);
    assertNotNull(response.getLocation());
  }

  @Test
  public void testDeregisterTable() {
    Assumptions.assumeTrue(
        getCapabilities().supportsRegisterDeregister(), "Register/Deregister not supported");

    // First register a table
    RegisterTableRequest registerRequest = new RegisterTableRequest();
    List<String> tableId = getTestTableId("deregister_test");
    registerRequest.setId(tableId);
    registerRequest.setLocation(getTempDirectory() + "/deregister_test.lance");
    namespace.registerTable(registerRequest);

    // Now deregister it
    DeregisterTableRequest deregisterRequest = new DeregisterTableRequest();
    deregisterRequest.setId(tableId);

    DeregisterTableResponse response = namespace.deregisterTable(deregisterRequest);
    assertNotNull(response);
    assertEquals(tableId, response.getId());
  }

  // ========== Edge Cases and Error Handling ==========

  @Test
  public void testInvalidTableIdentifiers() {
    // Test null table ID
    CreateTableRequest nullIdRequest = new CreateTableRequest();
    nullIdRequest.setSchema(createTestSchema());

    assertThrows(
        Exception.class,
        () -> namespace.createTable(nullIdRequest, createTestArrowData()),
        "Should throw exception for null table ID");

    // Test empty table ID
    CreateTableRequest emptyIdRequest = new CreateTableRequest();
    emptyIdRequest.setId(new ArrayList<>());
    emptyIdRequest.setSchema(createTestSchema());

    assertThrows(
        Exception.class,
        () -> namespace.createTable(emptyIdRequest, createTestArrowData()),
        "Should throw exception for empty table ID");
  }

  @Test
  public void testInvalidNamespaceIdentifiers() {
    Assumptions.assumeTrue(
        getCapabilities().supportsNamespaceOperations(), "Namespace operations not supported");

    // Test null namespace ID
    CreateNamespaceRequest nullIdRequest = new CreateNamespaceRequest();
    nullIdRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(
        Exception.class,
        () -> namespace.createNamespace(nullIdRequest),
        "Should throw exception for null namespace ID");

    // Test empty namespace ID
    CreateNamespaceRequest emptyIdRequest = new CreateNamespaceRequest();
    emptyIdRequest.setId(new ArrayList<>());
    emptyIdRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(
        Exception.class,
        () -> namespace.createNamespace(emptyIdRequest),
        "Should throw exception for empty namespace ID");
  }

  @Test
  public void testCreateTableWithInvalidSchema() {
    CreateTableRequest request = new CreateTableRequest();
    request.setId(getTestTableId("invalid_schema"));
    // Don't set schema

    assertThrows(
        Exception.class,
        () -> namespace.createTable(request, createTestArrowData()),
        "Should throw exception for missing schema");
  }
}
