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
import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.NamespaceCapabilities;
import com.lancedb.lance.namespace.NamespaceTCK;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;

import com.google.common.collect.Maps;
import org.apache.hadoop.hive.conf.HiveConf;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;

/**
 * TCK test implementation for Hive3Namespace.
 *
 * <p>Hive3Namespace characteristics: - Supports nested namespace operations (catalog -> database
 * hierarchy) - Supports tables in nested namespaces - Supports CASCADE and RESTRICT delete
 * behaviors - Uses catalog.database.table naming convention
 */
public class Hive3NamespaceTCKTest extends NamespaceTCK {

  private static LocalHive3Metastore metastore;
  private String tmpDirBase;
  private LanceNamespace hive3Namespace;
  private String testCatalogName = "tck_test_catalog";
  private String testDatabaseName = "tck_test_db";

  @Override
  protected LanceNamespace createNamespace() throws Exception {
    // Start local Hive3 metastore if not already running
    if (metastore == null) {
      metastore = new LocalHive3Metastore();
      metastore.start();
    }

    // Create temporary directory for test data
    File file =
        createTempDirectory("Hive3NamespaceTCK", asFileAttribute(fromString("rwxrwxrwx"))).toFile();
    tmpDirBase = file.getAbsolutePath();

    // Initialize Hive3 namespace
    HiveConf hiveConf = metastore.hiveConf();
    Map<String, String> properties = Maps.newHashMap();
    properties.put("root", tmpDirBase);
    hive3Namespace = LanceNamespaces.connect("hive3", properties, hiveConf, allocator);

    // Create test catalog
    CreateNamespaceRequest catalogRequest = new CreateNamespaceRequest();
    catalogRequest.setId(Lists.list(testCatalogName));
    catalogRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> catalogProperties = Maps.newHashMap();
    catalogProperties.put("catalog.location.uri", "file://" + tmpDirBase + "/" + testCatalogName);
    catalogRequest.setProperties(catalogProperties);

    try {
      hive3Namespace.createNamespace(catalogRequest);
    } catch (Exception e) {
      // Catalog may already exist from previous test
      if (!e.getMessage().contains("already exists")) {
        throw e;
      }
    }

    // Create test database within catalog (nested namespace)
    CreateNamespaceRequest dbRequest = new CreateNamespaceRequest();
    dbRequest.setId(Lists.list(testCatalogName, testDatabaseName));
    dbRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> dbProperties = Maps.newHashMap();
    dbProperties.put(
        "database.location-uri", tmpDirBase + "/" + testCatalogName + "/" + testDatabaseName);
    dbRequest.setProperties(dbProperties);

    try {
      hive3Namespace.createNamespace(dbRequest);
    } catch (Exception e) {
      // Database may already exist from previous test
      if (!e.getMessage().contains("already exists")) {
        throw e;
      }
    }

    return hive3Namespace;
  }

  @Override
  protected void cleanupNamespace() throws Exception {
    // Reset metastore between tests
    if (metastore != null) {
      metastore.reset();
    }

    // Clean up temporary directory
    if (tmpDirBase != null) {
      File file = new File(tmpDirBase);
      deleteRecursively(file);
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

  @AfterAll
  public static void tearDownMetastore() throws Exception {
    if (metastore != null) {
      metastore.stop();
      metastore = null;
    }
  }

  @Override
  protected String getTempDirectory() {
    return tmpDirBase;
  }

  @Override
  protected NamespaceCapabilities getCapabilities() {
    return NamespaceCapabilities.builder()
        .supportsNamespaceOperations(true) // Can create/drop catalogs and databases
        .supportsNestedNamespaces(true) // Catalog->Database hierarchy
        .supportsRootNamespaceOnly(false) // Can nest namespaces
        .supportsTablesInRootNamespace(false) // Tables must be in databases
        .supportsMultiLevelTableIds(true) // catalog.database.table format
        .supportsCascadeDelete(true) // Supports CASCADE
        .supportsRestrictDelete(true) // Supports RESTRICT
        .supportsTableVersioning(false) // No explicit versioning in Hive
        .supportsPagination(true) // Hive3 supports pagination
        .supportsRegisterDeregister(false) // No register/deregister
        .build();
  }

  @Override
  protected List<String> getTestNamespaceId() {
    // Hive3 uses two-level namespace IDs for databases (catalog.database)
    return Arrays.asList(testCatalogName, testDatabaseName);
  }

  @Override
  protected List<String> getTestTableId(String tableName) {
    // Hive3 uses three-level table identifiers: catalog.database.table
    return Arrays.asList(testCatalogName, testDatabaseName, tableName);
  }
}
