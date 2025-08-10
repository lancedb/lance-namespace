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
import com.lancedb.lance.namespace.LanceNamespaces;
import com.lancedb.lance.namespace.NamespaceCapabilities;
import com.lancedb.lance.namespace.NamespaceTCK;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;

import com.google.common.collect.Maps;
import org.apache.arrow.memory.RootAllocator;
import org.apache.hadoop.hive.conf.HiveConf;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;

/**
 * TCK test implementation for Hive2Namespace.
 *
 * <p>Hive2Namespace characteristics: - Supports namespace operations at root level only (databases)
 * - Does not support nested namespaces (no catalog support in Hive2) - Supports CASCADE and
 * RESTRICT delete behaviors - Uses database.table naming convention
 */
public class Hive2NamespaceTCKTest extends NamespaceTCK {

  private static LocalHive2Metastore metastore;
  private static String tmpDirBase;
  private LanceNamespace hive2Namespace;
  private String testDatabaseName = "tck_test_db";

  @Override
  protected LanceNamespace createNamespace() throws Exception {
    // Start local Hive2 metastore if not already running
    if (metastore == null) {
      metastore = new LocalHive2Metastore();
      metastore.start();

      // Create temporary directory for test data
      File file =
              createTempDirectory("Hive2NamespaceTCK", asFileAttribute(fromString("rwxrwxrwx"))).toFile();
      tmpDirBase = file.getAbsolutePath();
    }



    // Initialize Hive2 namespace - use empty properties like TestHive2Namespace
    HiveConf hiveConf = metastore.hiveConf();
    Map<String, String> properties = Maps.newHashMap();
    // Don't set root property - use the same configuration as TestHive2Namespace
    hive2Namespace = LanceNamespaces.connect("hive2", properties, hiveConf, allocator);

    // Create test database
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Lists.list(testDatabaseName));
    nsRequest.setMode(CreateNamespaceRequest.ModeEnum.CREATE);

    Map<String, String> dbProperties = Maps.newHashMap();
    dbProperties.put("database.location-uri", tmpDirBase + "/" + testDatabaseName);
    nsRequest.setProperties(dbProperties);

    try {
      hive2Namespace.createNamespace(nsRequest);
    } catch (Exception e) {
      // Database may already exist from previous test
      if (!e.getMessage().contains("already exists")) {
        throw e;
      }
    }

    return hive2Namespace;
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
        .supportsNamespaceOperations(true) // Can create/drop databases
        .supportsRootNamespaceOnly(true) // Databases at root only
        .supportsNestedNamespaces(false) // No catalog support in Hive2
        .supportsTablesInRootNamespace(false) // Tables must be in databases
        .supportsMultiLevelTableIds(true) // database.table format
        .supportsCascadeDelete(true) // Supports CASCADE
        .supportsRestrictDelete(true) // Supports RESTRICT
        .supportsTableVersioning(false) // No explicit versioning in Hive
        .supportsPagination(true) // Hive supports pagination
        .supportsRegisterDeregister(false) // No register/deregister
        .build();
  }

  @Override
  protected List<String> getTestNamespaceId() {
    // Hive2 uses single-level namespace IDs (database names)
    return Arrays.asList(testDatabaseName);
  }

  @Override
  protected List<String> getTestTableId(String tableName) {
    // Hive2 uses two-level table identifiers: database.table
    return Arrays.asList(testDatabaseName, tableName);
  }
}
