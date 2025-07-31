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

import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
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

import java.io.File;
import java.io.IOException;
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
  private static LocalHiveMetastore metastore;
  private static String tmpDirBase;

  @BeforeAll
  public static void setup() throws IOException {
    metastore = new LocalHiveMetastore();
    metastore.start();

    File file =
        createTempDirectory("TestHiveNamespace", asFileAttribute(fromString("rwxrwxrwx"))).toFile();
    tmpDirBase = file.getAbsolutePath();
  }

  @AfterAll
  public static void teardown() throws Exception {
    metastore.stop();

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
          (HiveNamespace) LanceNamespaces.create("hive", Maps.newHashMap(), hiveConf, allocator);

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
          (HiveNamespace) LanceNamespaces.create("hive", Maps.newHashMap(), hiveConf, allocator);

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
}
