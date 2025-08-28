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
package com.lancedb.lance.namespace.hive2;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.TestHelper;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
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
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import com.google.common.collect.Maps;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.hadoop.hive.conf.HiveConf;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHive2Namespace {

  private static BufferAllocator allocator;
  private static LocalHive2Metastore metastore;
  private static String tmpDirBase;
  private static LanceNamespace namespace;

  @BeforeAll
  public static void setup() throws IOException {
    allocator = new RootAllocator(Long.MAX_VALUE);
    metastore = new LocalHive2Metastore();
    metastore.start();

    File file =
        createTempDirectory("TestHive2Namespace", asFileAttribute(fromString("rwxrwxrwx")))
            .toFile();
    tmpDirBase = file.getAbsolutePath();

    HiveConf hiveConf = metastore.hiveConf();
    namespace = LanceNamespaces.connect("hive2", Maps.newHashMap(), hiveConf, allocator);
  }

  @AfterAll
  public static void teardown() throws Exception {
    if (allocator != null) {
      allocator.close();
    }
    if (metastore != null) {
      metastore.stop();
    }

    if (tmpDirBase != null) {
      File file = new File(tmpDirBase);
      file.delete();
    }
  }

  @AfterEach
  public void cleanup() throws Exception {
    metastore.reset();
  }

  @Disabled("Need to figure out the proper interface")
  @Test
  public void testCreateTable() throws IOException {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Create table with valid parameters
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db", "test_table"));
    request.setLocation(tmpDirBase + "/test_db/test_table.lance");

    Map<String, String> properties = Maps.newHashMap();
    properties.put("custom_prop", "custom_value");
    request.setProperties(properties);

    byte[] testData = TestHelper.createTestArrowData(allocator);
    CreateTableResponse response = namespace.createTable(request, testData);

    assertEquals(request.getLocation(), response.getLocation());
    assertEquals(1L, response.getVersion());
  }

  @Test
  public void testCreateTableAlreadyExists() throws IOException {
    // Setup: Create database and table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db", "test_table"));
    request.setLocation(tmpDirBase + "/test_db/test_table.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(request, testData);

    // Test: Create table that already exists
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.createTable(request, testData));
    assertTrue(error.getMessage().contains("Table test_db.test_table already exists"));
  }

  @Test
  public void testCreateTableManagedByImpl() throws IOException {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Create table with managed_by=impl (not supported)
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db", "impl_table"));
    request.setLocation(tmpDirBase + "/test_db/impl_table.lance");

    Map<String, String> properties = Maps.newHashMap();
    properties.put("managed_by", "impl");
    request.setProperties(properties);

    byte[] testData = TestHelper.createTestArrowData(allocator);
    Exception error =
        assertThrows(
            UnsupportedOperationException.class, () -> namespace.createTable(request, testData));
    assertTrue(error.getMessage().contains("managed_by=impl is not supported yet"));
  }

  @Test
  public void testCreateTableWithoutData() throws IOException {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Create table without data
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db", "no_data_table"));
    request.setLocation(tmpDirBase + "/test_db/no_data_table.lance");

    byte[] emptyData = TestHelper.createEmptyArrowData(allocator);
    CreateTableResponse response = namespace.createTable(request, emptyData);
    assertEquals(request.getLocation(), response.getLocation());
  }

  @Test
  public void testDescribeTable() throws IOException {
    // Setup: Create database and table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_db", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_db/test_table.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Describe existing Lance table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Lists.list("test_db", "test_table"));

    DescribeTableResponse response = namespace.describeTable(request);
    assertEquals("file:" + tmpDirBase + "/test_db/test_table.lance", response.getLocation());
  }

  @Test
  public void testDescribeNonExistentTable() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Describe non-existent table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Lists.list("test_db", "non_existent"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(request));
    assertTrue(error.getMessage().contains("Table does not exist"));
  }

  @Test
  public void testDropTable() throws IOException {
    // Setup: Create database and table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_db", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_db/test_table.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Drop existing table
    DropTableRequest request = new DropTableRequest();
    request.setId(Lists.list("test_db", "test_table"));

    DropTableResponse response = namespace.dropTable(request);
    assertEquals("file:" + tmpDirBase + "/test_db/test_table.lance", response.getLocation());
    assertEquals(request.getId(), response.getId());

    // Verify table is dropped by trying to describe it
    DescribeTableRequest descRequest = new DescribeTableRequest();
    descRequest.setId(request.getId());
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(descRequest));
    assertTrue(error.getMessage().contains("Table does not exist"));
  }

  @Test
  public void testDropNonExistentTable() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Drop non-existent table
    DropTableRequest request = new DropTableRequest();
    request.setId(Lists.list("test_db", "non_existent"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.dropTable(request));
    assertTrue(error.getMessage().contains("Table test_db.non_existent does not exist"));
  }

  @Test
  public void testCreateTableWithDefaultLocationFromRoot() throws IOException {
    // With our enhancement, databases created without explicit location
    // will use the root config location instead of Hive warehouse

    // Setup: Create namespace with custom root configuration
    Map<String, String> properties = Maps.newHashMap();
    properties.put("root", tmpDirBase);

    HiveConf hiveConf = metastore.hiveConf();
    LanceNamespace customNamespace =
        LanceNamespaces.connect("hive2", properties, hiveConf, allocator);

    // Setup: Create database (will use root location)
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    customNamespace.createNamespace(nsRequest);

    // Test: Create table without specifying location
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db", "test_table"));
    // Don't set location - it will be derived from database location

    // Create test Arrow IPC data
    byte[] testData = TestHelper.createTestArrowData(allocator);
    CreateTableResponse response = customNamespace.createTable(request, testData);

    // Verify: Location should be derived from root-based database location
    // Hive adds file: prefix to locations
    String expectedLocation = "file:" + tmpDirBase + "/test_db/test_table.lance";
    assertEquals(expectedLocation, response.getLocation());
    assertEquals(1L, response.getVersion());
  }

  @Test
  public void testCreateTableWithDefaultLocationFromDatabaseLocation() throws IOException {
    // Setup: Create namespace with custom root configuration
    Map<String, String> properties = Maps.newHashMap();
    properties.put("root", tmpDirBase);

    HiveConf hiveConf = metastore.hiveConf();
    LanceNamespace customNamespace =
        LanceNamespaces.connect("hive2", properties, hiveConf, allocator);

    // Setup: Create database with specific location
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db_with_location"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    // Set database location - this should take precedence over root config
    String databaseLocation = tmpDirBase + "/custom_db_location";
    Map<String, String> dbProperties = Maps.newHashMap();
    dbProperties.put("database.location-uri", databaseLocation);
    nsRequest.setProperties(dbProperties);

    customNamespace.createNamespace(nsRequest);

    // Test: Create table without specifying location (should derive from database location)
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_db_with_location", "test_table"));
    // Don't set location - it should be derived from database location

    // Create test Arrow IPC data
    byte[] testData = TestHelper.createTestArrowData(allocator);
    CreateTableResponse response = customNamespace.createTable(request, testData);

    // Verify: Location should be derived as {database_location}/{table}.lance
    // Database locations in Hive typically have file: prefix
    String expectedLocation = "file:" + databaseLocation + "/test_table.lance";
    assertEquals(expectedLocation, response.getLocation());
    assertEquals(1L, response.getVersion());
  }

  @Test
  public void testDescribeNamespace() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> properties = Maps.newHashMap();
    properties.put("database.description", "Test database description");
    properties.put("custom_param", "custom_value");
    nsRequest.setProperties(properties);

    namespace.createNamespace(nsRequest);

    // Test: Describe existing namespace
    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setId(Lists.list("test_db"));

    DescribeNamespaceResponse response = namespace.describeNamespace(request);

    assertEquals("Test database description", response.getProperties().get("database.description"));
    assertEquals("custom_value", response.getProperties().get("custom_param"));
    assertTrue(response.getProperties().containsKey("database.location-uri"));
    assertTrue(response.getProperties().containsKey("owner"));
    assertTrue(response.getProperties().containsKey("owner_type"));
  }

  @Test
  public void testDescribeNonExistentNamespace() {
    // Test: Describe non-existent namespace
    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setId(Lists.list("non_existent_db"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeNamespace(request));
    assertTrue(error.getMessage().contains("Namespace does not exist"));
  }

  @Test
  public void testNamespaceExists() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Check existing namespace
    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setId(Lists.list("test_db"));

    // Should not throw exception for existing namespace
    namespace.namespaceExists(request);
  }

  @Test
  public void testNamespaceExistsNonExistent() {
    // Test: Check non-existent namespace
    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setId(Lists.list("non_existent_db"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.namespaceExists(request));
    assertTrue(error.getMessage().contains("Namespace does not exist"));
  }

  @Test
  public void testTableExists() throws IOException {
    // Setup: Create database and table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_db", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_db/test_table.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Check existing table
    TableExistsRequest request = new TableExistsRequest();
    request.setId(Lists.list("test_db", "test_table"));

    // Should not throw exception for existing Lance table
    namespace.tableExists(request);
  }

  @Test
  public void testTableExistsNonExistent() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: Check non-existent table
    TableExistsRequest request = new TableExistsRequest();
    request.setId(Lists.list("test_db", "non_existent_table"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.tableExists(request));
    assertTrue(error.getMessage().contains("Table does not exist"));
  }

  @Test
  public void testListTables() throws IOException {
    // Setup: Create database and multiple tables
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Create first table
    CreateTableRequest createRequest1 = new CreateTableRequest();
    createRequest1.setId(Lists.list("test_db", "table1"));
    createRequest1.setLocation(tmpDirBase + "/test_db/table1.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest1, testData);

    // Create second table
    CreateTableRequest createRequest2 = new CreateTableRequest();
    createRequest2.setId(Lists.list("test_db", "table2"));
    createRequest2.setLocation(tmpDirBase + "/test_db/table2.lance");

    namespace.createTable(createRequest2, testData);

    // Test: List tables
    ListTablesRequest request = new ListTablesRequest();
    request.setId(Lists.list("test_db"));

    ListTablesResponse response = namespace.listTables(request);

    assertEquals(2, response.getTables().size());
    assertTrue(response.getTables().contains("table1"));
    assertTrue(response.getTables().contains("table2"));
  }

  @Test
  public void testListTablesEmpty() {
    // Setup: Create empty database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("empty_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test: List tables in empty database
    ListTablesRequest request = new ListTablesRequest();
    request.setId(Lists.list("empty_db"));

    ListTablesResponse response = namespace.listTables(request);

    assertEquals(0, response.getTables().size());
  }

  @Test
  public void testListTablesWithPagination() throws IOException {
    // Setup: Create database and multiple tables
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Create multiple tables
    for (int i = 1; i <= 5; i++) {
      CreateTableRequest createRequest = new CreateTableRequest();
      createRequest.setId(Lists.list("test_db", "table" + i));
      createRequest.setLocation(tmpDirBase + "/test_db/table" + i + ".lance");

      byte[] testData = TestHelper.createTestArrowData(allocator);
      namespace.createTable(createRequest, testData);
    }

    // Test: List tables with pagination (limit 3)
    ListTablesRequest request = new ListTablesRequest();
    request.setId(Lists.list("test_db"));
    request.setLimit(3);

    ListTablesResponse response = namespace.listTables(request);

    assertEquals(3, response.getTables().size());
    // Should have a page token for remaining results
    assertTrue(response.getPageToken() != null && !response.getPageToken().isEmpty());

    // Get remaining tables
    ListTablesRequest nextRequest = new ListTablesRequest();
    nextRequest.setId(Lists.list("test_db"));
    nextRequest.setPageToken(response.getPageToken());

    ListTablesResponse nextResponse = namespace.listTables(nextRequest);

    assertEquals(2, nextResponse.getTables().size());
    // No more pages
    assertTrue(nextResponse.getPageToken() == null || nextResponse.getPageToken().isEmpty());
  }

  @Test
  public void testListTablesNonExistentDatabase() {
    // Test: List tables in non-existent database
    ListTablesRequest request = new ListTablesRequest();
    request.setId(Lists.list("non_existent_db"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.listTables(request));
    assertTrue(error.getMessage().contains("Database non_existent_db doesn't exist"));
  }

  @Test
  public void testDropNamespaceBasic() {
    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db_basic"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> properties = Maps.newHashMap();
    properties.put("database.description", "Test database for dropping");
    properties.put("custom_param", "custom_value");
    nsRequest.setProperties(properties);

    namespace.createNamespace(nsRequest);

    // Test: Drop the namespace with default behavior (RESTRICT) and mode (FAIL)
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Lists.list("test_db_basic"));

    DropNamespaceResponse response = namespace.dropNamespace(dropRequest);

    // Verify properties were returned
    assertEquals(
        "Test database for dropping", response.getProperties().get("database.description"));
    assertEquals("custom_value", response.getProperties().get("custom_param"));

    // Verify namespace was dropped
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Lists.list("test_db_basic"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.namespaceExists(existsRequest));
    assertTrue(error.getMessage().contains("Namespace does not exist"));
  }

  @Test
  public void testDropNamespaceSkipMode() {
    // Test: Drop non-existent namespace with SKIP mode
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Lists.list("non_existent_db"));
    dropRequest.setMode(DropNamespaceRequest.ModeEnum.SKIP);

    DropNamespaceResponse response = namespace.dropNamespace(dropRequest);

    // Should return empty properties for SKIP mode
    assertEquals(0, response.getProperties().size());
  }

  @Test
  public void testDropNamespaceFailMode() {
    // Test: Drop non-existent namespace with FAIL mode (default)
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Lists.list("non_existent_db"));
    dropRequest.setMode(DropNamespaceRequest.ModeEnum.FAIL);

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.dropNamespace(dropRequest));
    assertTrue(error.getMessage().contains("Database non_existent_db doesn't exist"));
  }

  @Test
  public void testDropNamespaceRestrictWithTables() throws IOException {
    // Setup: Create database and table
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db_restrict"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_db_restrict", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_db_restrict/test_table.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Try to drop namespace with RESTRICT behavior (should fail)
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Lists.list("test_db_restrict"));
    dropRequest.setBehavior(DropNamespaceRequest.BehaviorEnum.RESTRICT);

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.dropNamespace(dropRequest));
    assertTrue(error.getMessage().contains("Database test_db_restrict is not empty"));
    assertTrue(error.getMessage().contains("Contains 1 tables"));
  }

  @Test
  public void testDropNamespaceCascadeWithTables() throws IOException {
    // Setup: Create database and multiple tables
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db_cascade"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Create first table
    CreateTableRequest createRequest1 = new CreateTableRequest();
    createRequest1.setId(Lists.list("test_db_cascade", "table1"));
    createRequest1.setLocation(tmpDirBase + "/test_db_cascade/table1.lance");

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest1, testData);

    // Create second table
    CreateTableRequest createRequest2 = new CreateTableRequest();
    createRequest2.setId(Lists.list("test_db_cascade", "table2"));
    createRequest2.setLocation(tmpDirBase + "/test_db_cascade/table2.lance");

    namespace.createTable(createRequest2, testData);

    // Test: Drop namespace with CASCADE behavior
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Lists.list("test_db_cascade"));
    dropRequest.setBehavior(DropNamespaceRequest.BehaviorEnum.CASCADE);

    DropNamespaceResponse response = namespace.dropNamespace(dropRequest);

    // Verify namespace properties were returned
    assertTrue(response.getProperties().containsKey("database.location-uri"));

    // Verify namespace was dropped
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Lists.list("test_db_cascade"));

    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.namespaceExists(existsRequest));
    assertTrue(error.getMessage().contains("Namespace does not exist"));
  }
}
