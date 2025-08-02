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
package com.lancedb.lance.namespace.hive;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.Catalog;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.SerDeInfo;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.thrift.TException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestHiveNamespace {

  private static BufferAllocator allocator;
  private static LocalHiveMetastore metastore;
  private static String tmpDirBase;

  @BeforeAll
  public static void setup() throws IOException {
    allocator = new RootAllocator(Long.MAX_VALUE);
    metastore = new LocalHiveMetastore();
    metastore.start();

    File file =
        createTempDirectory("TestHiveNamespace", asFileAttribute(fromString("rwxrwxrwx"))).toFile();
    tmpDirBase = file.getAbsolutePath();
  }

  @AfterAll
  public static void teardown() throws Exception {

    if (allocator != null) {
      allocator.close();
    }
    if (metastore != null) {
      metastore.stop();
    }

    File file = new File(tmpDirBase);
    file.delete();
  }

  @AfterEach
  public void cleanup() throws Exception {
    metastore.reset();
  }

  @Test
  public void testListNamespacesV3() throws Exception {
    assumeTrue(HiveVersion.version() == HiveVersion.V3);

    List<List<String>> nsElements =
        Lists.list(
            Lists.list("hive", "default", "table1"),
            Lists.list("hive", "default", "table2"),
            Lists.list("hive", "mydb"),
            Lists.list("mycatalog", "default"),
            Lists.list("mycatalog", "mydb", "table3"));
    Map<String, Map<String, Set<String>>> namespaces = parseNamespaces(nsElements);
    initNamespaces(namespaces);

    HiveConf hiveConf = metastore.hiveConf();
    try (BufferAllocator allocator = new RootAllocator(Long.MAX_VALUE)) {
      HiveNamespace namespace =
          (HiveNamespace) LanceNamespaces.connect("hive", Maps.newHashMap(), hiveConf, allocator);

      // Case 1: list root.
      ListNamespacesRequest request = new ListNamespacesRequest();
      request.setId(Lists.list());
      assertEquals(namespaces.keySet(), namespace.listNamespaces(request).getNamespaces());

      // Case 2: list catalog.
      for (String catalog : namespaces.keySet()) {
        request.setId(Lists.list(catalog));
        assertEquals(
            namespaces.get(catalog).keySet(), namespace.listNamespaces(request).getNamespaces());
      }

      // Case 3: list database.
      for (String catalog : namespaces.keySet()) {
        for (String db : namespaces.get(catalog).keySet()) {
          request.setId(Lists.list(catalog, db));
          assertEquals(Sets.newHashSet(), namespace.listNamespaces(request).getNamespaces());
        }
      }

      // Case 4: list table.
      request.setId(Lists.list("mycatalog", "mydb", "table3"));
      assertThrows(IllegalArgumentException.class, () -> namespace.listNamespaces(request));

      // Case 5: non-existing catalog, database, table.
      request.setId(Lists.list("nonexistedcatalog"));
      assertEquals(Sets.newHashSet(), namespace.listNamespaces(request).getNamespaces());

      request.setId(Lists.list("hive", "nonexisteddb"));
      assertEquals(Sets.newHashSet(), namespace.listNamespaces(request).getNamespaces());

      request.setId(Lists.list("hive", "default", "nonexistedtable"));
      assertThrows(IllegalArgumentException.class, () -> namespace.listNamespaces(request));

      // Case 6: list long namespace.
      request.setId(Lists.list("mycatalog", "mydb", "table3", "a", "b", "c"));
      assertThrows(RuntimeException.class, () -> namespace.listNamespaces(request));
    }
  }

  private static Map<String, Map<String, Set<String>>> parseNamespaces(List<List<String>> nsList) {
    Map<String, Map<String, Set<String>>> namespaces = Maps.newHashMap();
    for (List<String> ns : nsList) {
      ObjectIdentifier oid = ObjectIdentifier.of(ns);

      String catalog = oid.levels() <= 1 ? null : oid.level(0);
      String db = oid.levels() <= 2 ? null : oid.level(1);
      String table = oid.levels() <= 3 ? null : oid.level(2);

      if (catalog != null) {
        namespaces.putIfAbsent(catalog, Maps.newHashMap());
      }
      if (db != null) {
        namespaces.get(catalog).putIfAbsent(db, Sets.newHashSet());
      }
      if (table != null) {
        namespaces.get(catalog).get(db).add(table);
      }
    }

    return namespaces;
  }

  @Test
  public void testListNamespacesByPage() throws Exception {
    assumeTrue(HiveVersion.version() == HiveVersion.V3);

    List<List<String>> nsElements =
        Lists.list(
            Lists.list("hive", "default"),
            Lists.list("hive", "db0"),
            Lists.list("hive", "db1"),
            Lists.list("hive", "db2"),
            Lists.list("hive", "db3"));
    Map<String, Map<String, Set<String>>> namespaces = parseNamespaces(nsElements);
    initNamespaces(namespaces);

    HiveConf hiveConf = metastore.hiveConf();
    try (BufferAllocator allocator = new RootAllocator(Long.MAX_VALUE)) {
      HiveNamespace namespace =
          (HiveNamespace) LanceNamespaces.connect("hive", Maps.newHashMap(), hiveConf, allocator);

      ListNamespacesRequest request = new ListNamespacesRequest();
      request.setId(Lists.list("hive"));
      request.setLimit(2);

      // Case 1: List by pages.
      Set<String> nss = Sets.newHashSet();
      for (int i = 0; i < 3; i++) {
        ListNamespacesResponse response = namespace.listNamespaces(request);

        int expectedSize = Math.min(2, nsElements.size() - nss.size());
        assertEquals(expectedSize, response.getNamespaces().size());
        for (String ns : response.getNamespaces()) {
          assertTrue(nss.add(ns));
        }

        request.setPageToken(response.getPageToken());
        if (i == 2) {
          assertNull(response.getPageToken());
        }
      }

      assertEquals(nsElements.stream().map(ns -> ns.get(1)).collect(Collectors.toSet()), nss);

      // Case 2: intra page token.
      request.setPageToken("db00");
      ListNamespacesResponse response = namespace.listNamespaces(request);
      assertEquals(2, response.getNamespaces().size());
      assertEquals(Sets.newHashSet("db1", "db2"), response.getNamespaces());

      // Case 3: out of bound page token.
      request.setPageToken("z");
      response = namespace.listNamespaces(request);
      assertEquals(0, response.getNamespaces().size());

      // Case 4: invalid page size.
      request.setLimit(0);
      assertThrows(IllegalArgumentException.class, () -> namespace.listNamespaces(request));
      request.setLimit(-1);
      assertThrows(IllegalArgumentException.class, () -> namespace.listNamespaces(request));
    }
  }

  @Test
  public void testCreateNamespaceV3() {
    assumeTrue(HiveVersion.version() == HiveVersion.V3);

    HiveConf hiveConf = metastore.hiveConf();
    HiveNamespace namespace =
        (HiveNamespace) LanceNamespaces.connect("hive", Maps.newHashMap(), hiveConf, allocator);

    testCreateCatalog(namespace);

    testCreateDatabase(namespace);
  }

  private void testCreateCatalog(LanceNamespace namespace) {
    // Case 1: Invalid id
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Lists.list(""));
    assertThrows(IllegalArgumentException.class, () -> namespace.createNamespace(request));

    request.setId(Lists.list("catalog", "db", "table"));
    assertThrows(IllegalArgumentException.class, () -> namespace.createNamespace(request));

    // Case 2: No location when create catalog
    request.setId(Lists.list("cat"));
    request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    assertThrows(IllegalArgumentException.class, () -> namespace.createNamespace(request));

    // Case 3: Create catalog
    Map<String, String> properties = Maps.newHashMap();
    properties.put(HiveNamespaceConfig.CATALOG_LOCATION_URI, "file:///tmp/cat");
    request.setProperties(properties);
    namespace.createNamespace(request);

    // Case 4: Create catalog while already exist
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.createNamespace(request));
    assertTrue(error.getMessage().contains("Catalog cat already exist"));

    // Case 5: Ignore exist catalog
    request.setMode(CreateNamespaceRequest.ModeEnum.EXIST_OK);
    namespace.createNamespace(request);

    // Case 6: Overwrite exist catalog
    request.setMode(CreateNamespaceRequest.ModeEnum.OVERWRITE);
    namespace.createNamespace(request);
  }

  private void testCreateDatabase(LanceNamespace namespace) {
    // Case 1: Unknown catalog
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Lists.list("cat_catalog", "db"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.createNamespace(request));
    assertTrue(error.getMessage().contains("Catalog cat_catalog doesn't exist"));

    // Case 2: Create database with default location
    Map<String, String> properties = Maps.newHashMap();
    properties.put(HiveNamespaceConfig.CATALOG_LOCATION_URI, "file:///tmp/cat");
    request.setProperties(properties);
    request.setId(Lists.list("cat"));
    request.setMode(CreateNamespaceRequest.ModeEnum.EXIST_OK);
    namespace.createNamespace(request);

    request.setId(Lists.list("cat", "db"));
    request.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    Map<String, String> respProperties = namespace.createNamespace(request).getProperties();
    assertEquals(
        "file:///tmp/cat/db", respProperties.get(HiveNamespaceConfig.DATABASE_LOCATION_URI));

    // Case 3: Create database while already exist
    error = assertThrows(LanceNamespaceException.class, () -> namespace.createNamespace(request));
    assertTrue(error.getMessage().contains("Database cat.db already exist"));

    // Case 4: Ignore exist database
    request.setMode(CreateNamespaceRequest.ModeEnum.EXIST_OK);
    namespace.createNamespace(request);

    // Case 5: Overwrite exist catalog
    properties.put(HiveNamespaceConfig.DATABASE_LOCATION_URI, "file:///tmp/mycat/db");
    request.setMode(CreateNamespaceRequest.ModeEnum.OVERWRITE);
    respProperties = namespace.createNamespace(request).getProperties();
    assertEquals(
        "file:///tmp/mycat/db", respProperties.get(HiveNamespaceConfig.DATABASE_LOCATION_URI));
  }

  private static void initNamespaces(Map<String, Map<String, Set<String>>> namespaces)
      throws TException, InterruptedException {
    for (Map.Entry<String, Map<String, Set<String>>> entry : namespaces.entrySet()) {
      String catalog = entry.getKey();
      if (!catalog.equals("hive")) {
        metastore
            .clientPool()
            .run(
                client -> {
                  client.createCatalog(
                      new Catalog(catalog, String.format("file://%s/%s", tmpDirBase, catalog)));
                  return null;
                });
      }

      Map<String, Set<String>> dbs = entry.getValue();
      for (Map.Entry<String, Set<String>> dbEntry : dbs.entrySet()) {
        String database = dbEntry.getKey();
        if (!database.equals("default")) {
          metastore
              .clientPool()
              .run(
                  client -> {
                    Database db = new Database();
                    db.setCatalogName(catalog);
                    db.setName(database);
                    client.createDatabase(db);
                    return null;
                  });
        }

        for (String table : dbEntry.getValue()) {
          metastore
              .clientPool()
              .run(
                  client -> {
                    Table t = new Table();
                    t.setCatName(catalog);
                    t.setDbName(database);
                    t.setTableName(table);
                    StorageDescriptor sd = new StorageDescriptor();
                    sd.setCols(Lists.list(new FieldSchema("c1", serdeConstants.INT_TYPE_NAME, "")));
                    sd.setSerdeInfo(new SerDeInfo());
                    t.setSd(sd);
                    t.setPartitionKeys(Lists.list());
                    client.createTable(t);
                    return null;
                  });
        }
      }
    }
  }

  @Test
  public void testTableOperationsV3() throws Exception {
    assumeTrue(HiveVersion.version() == HiveVersion.V3);

    HiveConf hiveConf = metastore.hiveConf();
    HiveNamespace namespace =
        (HiveNamespace) LanceNamespaces.connect("hive", Maps.newHashMap(), hiveConf, allocator);

    // Setup: Create catalog and database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    Map<String, String> properties = Maps.newHashMap();
    properties.put(
        HiveNamespaceConfig.CATALOG_LOCATION_URI, "file://" + tmpDirBase + "/test_catalog");
    nsRequest.setProperties(properties);
    nsRequest.setId(Lists.list("test_catalog"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    nsRequest.setId(Lists.list("test_catalog", "test_db"));
    namespace.createNamespace(nsRequest);

    testCreateTable(namespace);
    testDescribeTable(namespace);
    testDropTable(namespace);
  }

  private void testCreateTable(HiveNamespace namespace) throws IOException {
    // Case 1: Create table with valid parameters
    CreateTableRequest request = new CreateTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));
    request.setLocation(tmpDirBase + "/test_catalog/test_db/test_table.lance");

    // Create a simple schema
    JsonArrowSchema schema = createTestSchema();
    request.setSchema(schema);

    Map<String, String> properties = Maps.newHashMap();
    properties.put("custom_prop", "custom_value");
    request.setProperties(properties);

    // Create with data
    byte[] testData = createTestData();
    CreateTableResponse response = namespace.createTable(request, testData);

    assertEquals(request.getLocation(), response.getLocation());
    assertEquals(1L, response.getVersion());

    // Case 2: Create table that already exists
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.createTable(request, testData));
    assertTrue(error.getMessage().contains("Table test_catalog.test_db.test_table already exists"));

    // Case 3: Create table with managed_by=impl (not supported)
    CreateTableRequest implRequest = new CreateTableRequest();
    implRequest.setId(Lists.list("test_catalog", "test_db", "impl_table"));
    implRequest.setLocation(tmpDirBase + "/test_catalog/test_db/impl_table.lance");
    implRequest.setSchema(schema);
    Map<String, String> implProps = Maps.newHashMap();
    implProps.put("managed_by", "impl");
    implRequest.setProperties(implProps);

    error =
        assertThrows(
            UnsupportedOperationException.class,
            () -> namespace.createTable(implRequest, testData));
    assertTrue(error.getMessage().contains("managed_by=impl is not supported yet"));

    // Case 4: Create table without data
    CreateTableRequest noDataRequest = new CreateTableRequest();
    noDataRequest.setId(Lists.list("test_catalog", "test_db", "no_data_table"));
    noDataRequest.setLocation(tmpDirBase + "/test_catalog/test_db/no_data_table.lance");
    noDataRequest.setSchema(schema);

    byte[] emptyData = createEmptyArrowData();
    response = namespace.createTable(noDataRequest, emptyData);
    assertEquals(noDataRequest.getLocation(), response.getLocation());
  }

  private void testDescribeTable(HiveNamespace namespace) {
    // Case 1: Describe existing Lance table
    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));

    DescribeTableResponse response = namespace.describeTable(request);
    assertEquals(tmpDirBase + "/test_catalog/test_db/test_table.lance", response.getLocation());

    // Case 2: Describe non-existent table
    request.setId(Lists.list("test_catalog", "test_db", "non_existent"));
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(request));
    assertTrue(error.getMessage().contains("Table does not exist"));

    // Case 3: Describe non-Lance table
    // First create a non-Lance table in HMS
    try {
      metastore
          .clientPool()
          .run(
              client -> {
                Table t = new Table();
                t.setCatName("test_catalog");
                t.setDbName("test_db");
                t.setTableName("non_lance_table");
                t.setTableType("EXTERNAL_TABLE");
                StorageDescriptor sd = new StorageDescriptor();
                sd.setLocation(tmpDirBase + "/non_lance_table");
                sd.setCols(Lists.list(new FieldSchema("c1", serdeConstants.INT_TYPE_NAME, "")));
                sd.setSerdeInfo(new SerDeInfo());
                t.setSd(sd);
                t.setPartitionKeys(Lists.list());
                // Don't set Lance parameters
                t.setParameters(Maps.newHashMap());
                client.createTable(t);
                return null;
              });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    request.setId(Lists.list("test_catalog", "test_db", "non_lance_table"));
    error = assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(request));
    assertTrue(error.getMessage().contains("is not a Lance table"));
  }

  private void testDropTable(HiveNamespace namespace) {
    // Case 1: Drop existing table
    DropTableRequest request = new DropTableRequest();
    request.setId(Lists.list("test_catalog", "test_db", "test_table"));

    DropTableResponse response = namespace.dropTable(request);
    assertEquals(tmpDirBase + "/test_catalog/test_db/test_table.lance", response.getLocation());
    assertEquals(request.getId(), response.getId());

    // Verify table is dropped by trying to describe it
    DescribeTableRequest descRequest = new DescribeTableRequest();
    descRequest.setId(request.getId());
    Exception error =
        assertThrows(LanceNamespaceException.class, () -> namespace.describeTable(descRequest));
    assertTrue(error.getMessage().contains("Table does not exist"));

    // Case 2: Drop non-existent table
    request.setId(Lists.list("test_catalog", "test_db", "already_dropped"));
    error = assertThrows(LanceNamespaceException.class, () -> namespace.dropTable(request));
    assertTrue(
        error.getMessage().contains("Table test_catalog.test_db.already_dropped does not exist"));

    // Case 3: Drop non-Lance table
    request.setId(Lists.list("test_catalog", "test_db", "non_lance_table"));
    error = assertThrows(LanceNamespaceException.class, () -> namespace.dropTable(request));
    assertTrue(error.getMessage().contains("is not a Lance table"));
  }

  @Test
  public void testTableOperationsV2() throws Exception {
    assumeTrue(HiveVersion.version() == HiveVersion.V2);

    HiveConf hiveConf = metastore.hiveConf();
    HiveNamespace namespace =
        (HiveNamespace) LanceNamespaces.connect("hive", Maps.newHashMap(), hiveConf, allocator);

    // Setup: Create database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list("test_db_v2"));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);
    namespace.createNamespace(nsRequest);

    // Test create table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Lists.list("test_db_v2", "test_table_v2"));
    createRequest.setLocation(tmpDirBase + "/test_db_v2/test_table_v2.lance");
    createRequest.setSchema(createTestSchema());

    byte[] testData = createTestData();
    CreateTableResponse createResponse = namespace.createTable(createRequest, testData);
    assertEquals(createRequest.getLocation(), createResponse.getLocation());

    // Test describe table
    DescribeTableRequest descRequest = new DescribeTableRequest();
    descRequest.setId(Lists.list("test_db_v2", "test_table_v2"));
    DescribeTableResponse descResponse = namespace.describeTable(descRequest);
    assertEquals(createRequest.getLocation(), descResponse.getLocation());

    // Test drop table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(Lists.list("test_db_v2", "test_table_v2"));
    DropTableResponse dropResponse = namespace.dropTable(dropRequest);
    assertEquals(createRequest.getLocation(), dropResponse.getLocation());
  }

  private JsonArrowSchema createTestSchema() {
    JsonArrowSchema schema = new JsonArrowSchema();

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

    schema.setFields(Arrays.asList(idField, nameField));
    return schema;
  }

  private byte[] createTestData() throws IOException {
    Schema arrowSchema =
        new Schema(
            Arrays.asList(
                new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                new Field("name", FieldType.nullable(new ArrowType.Utf8()), null)));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(arrowSchema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");

      // Add some test data
      root.setRowCount(3);
      idVector.setSafe(0, 1);
      idVector.setSafe(1, 2);
      idVector.setSafe(2, 3);

      nameVector.setSafe(0, "Alice".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(1, "Bob".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(2, "Charlie".getBytes(StandardCharsets.UTF_8));

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      return out.toByteArray();
    }
  }

  private byte[] createEmptyArrowData() throws IOException {
    Schema arrowSchema =
        new Schema(
            Arrays.asList(
                new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                new Field("name", FieldType.nullable(new ArrowType.Utf8()), null)));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(arrowSchema, allocator)) {
      // Set row count to 0 for empty data
      root.setRowCount(0);

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      return out.toByteArray();
    }
  }
}
