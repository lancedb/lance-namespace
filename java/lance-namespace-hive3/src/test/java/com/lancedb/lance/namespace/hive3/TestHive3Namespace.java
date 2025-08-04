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
package com.lancedb.lance.namespace.hive3;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.TestHelper;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;

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

public class TestHive3Namespace {

  private static BufferAllocator allocator;
  private static LocalHive3Metastore metastore;
  private static String tmpDirBase;
  private static LanceNamespace namespace;

  @BeforeAll
  public static void setup() throws IOException {
    allocator = new RootAllocator(Long.MAX_VALUE);
    metastore = new LocalHive3Metastore();
    metastore.start();

    File file =
        createTempDirectory("TestHive3Namespace", asFileAttribute(fromString("rwxrwxrwx")))
            .toFile();
    tmpDirBase = file.getAbsolutePath();

    HiveConf hiveConf = metastore.hiveConf();
    namespace = LanceNamespaces.connect("hive3", Maps.newHashMap(), hiveConf, allocator);

    // Setup: Create catalog and database for tests
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    Map<String, String> properties = Maps.newHashMap();
    properties.put("catalog.location.uri", "file://" + tmpDirBase + "/test_catalog");
    nsRequest.setProperties(properties);
    nsRequest.setId(Lists.list("test_catalog"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    nsRequest.setId(Lists.list("test_catalog", "test_db"));
    namespace.createNamespace(nsRequest);
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

    // Re-setup catalog and database after cleanup
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    Map<String, String> properties = Maps.newHashMap();
    properties.put("catalog.location.uri", "file://" + tmpDirBase + "/test_catalog");
    nsRequest.setProperties(properties);
    nsRequest.setId(Lists.list("test_catalog"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    nsRequest.setId(Lists.list("test_catalog", "test_db"));
    namespace.createNamespace(nsRequest);
  }

  @Disabled("Need to figure out the proper interface")
  @Test
  public void testCreateTable() throws IOException {
    // Test: Create table with valid parameters
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));
    request.setLocation(tmpDirBase + "/test_catalog/test_db/test_table.lance");
    request.setSchema(TestHelper.createTestSchema());

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
    // Setup: Create table
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));
    request.setLocation(tmpDirBase + "/test_catalog/test_db/test_table.lance");
    request.setSchema(TestHelper.createTestSchema());

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(request, testData);

    // Test: Create table that already exists
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.createTable(request, testData));
    assertTrue(error.getMessage().contains("Table test_catalog.test_db.test_table already exists"));
  }

  @Test
  public void testCreateTableManagedByImpl() throws IOException {
    // Test: Create table with managed_by=impl (not supported)
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "impl_table"));
    request.setLocation(tmpDirBase + "/test_catalog/test_db/impl_table.lance");
    request.setSchema(TestHelper.createTestSchema());

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
    // Test: Create table without data
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "no_data_table"));
    request.setLocation(tmpDirBase + "/test_catalog/test_db/no_data_table.lance");
    request.setSchema(TestHelper.createTestSchema());

    byte[] emptyData = TestHelper.createEmptyArrowData(allocator);
    CreateTableResponse response = namespace.createTable(request, emptyData);
    assertEquals(request.getLocation(), response.getLocation());
  }

  @Test
  public void testDescribeTable() throws IOException {
    // Setup: Create table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_catalog", "test_db", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_catalog/test_db/test_table.lance");
    createRequest.setSchema(TestHelper.createTestSchema());

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Describe existing Lance table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));

    DescribeTableResponse response = namespace.describeTable(request);
    assertEquals(
        "file:" + tmpDirBase + "/test_catalog/test_db/test_table.lance", response.getLocation());
  }

  @Test
  public void testDescribeNonExistentTable() {
    // Test: Describe non-existent table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "non_existent"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(request));
    assertTrue(error.getMessage().contains("Table does not exist"));
  }

  @Test
  public void testDropTable() throws IOException {
    // Setup: Create table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_catalog", "test_db", "test_table"));
    createRequest.setLocation(tmpDirBase + "/test_catalog/test_db/test_table.lance");
    createRequest.setSchema(TestHelper.createTestSchema());

    byte[] testData = TestHelper.createTestArrowData(allocator);
    namespace.createTable(createRequest, testData);

    // Test: Drop existing table
    DropTableRequest request = new DropTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));

    DropTableResponse response = namespace.dropTable(request);
    assertEquals(
        "file:" + tmpDirBase + "/test_catalog/test_db/test_table.lance", response.getLocation());
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
    // Test: Drop non-existent table
    DropTableRequest request = new DropTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "non_existent"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.dropTable(request));
    assertTrue(
        error.getMessage().contains("Table test_catalog.test_db.non_existent does not exist"));
  }
}
