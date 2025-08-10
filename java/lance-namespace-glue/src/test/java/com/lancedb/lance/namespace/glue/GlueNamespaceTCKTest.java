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
package com.lancedb.lance.namespace.glue;

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.NamespaceCapabilities;
import com.lancedb.lance.namespace.NamespaceTCK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.CreateDatabaseRequest;
import software.amazon.awssdk.services.glue.model.CreateDatabaseResponse;
import software.amazon.awssdk.services.glue.model.CreateTableResponse;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseRequest;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseResponse;
import software.amazon.awssdk.services.glue.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.DeleteTableResponse;
import software.amazon.awssdk.services.glue.model.EntityNotFoundException;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabaseResponse;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;
import software.amazon.awssdk.services.glue.model.GetTableRequest;
import software.amazon.awssdk.services.glue.model.GetTableResponse;
import software.amazon.awssdk.services.glue.model.GetTablesRequest;
import software.amazon.awssdk.services.glue.model.GetTablesResponse;
import software.amazon.awssdk.services.glue.model.StorageDescriptor;
import software.amazon.awssdk.services.glue.model.Table;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lancedb.lance.namespace.glue.GlueNamespace.LANCE_TABLE_TYPE_VALUE;
import static com.lancedb.lance.namespace.glue.GlueNamespace.TABLE_TYPE_PROP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

/**
 * TCK test implementation for GlueNamespace.
 *
 * <p>GlueNamespace characteristics: - Supports namespace operations at root level only (databases)
 * - Does not support nested namespaces - Supports register/deregister operations - Supports CASCADE
 * and RESTRICT delete behaviors
 */
@ExtendWith(MockitoExtension.class)
public class GlueNamespaceTCKTest extends NamespaceTCK {

  @Mock private GlueClient glueClient;

  @TempDir private Path tempDir;

  private GlueNamespace glueNamespace;
  private String testDatabaseName = "tck_test_db";

  // Track created databases and tables for dynamic mocking
  private Map<String, Database> createdDatabases = new HashMap<>();
  private Map<String, Table> createdTables = new HashMap<>();

  @BeforeEach
  void resetMaps() {
    createdDatabases.clear();
    createdTables.clear();

    // Always ensure the test database exists for each test
    createdDatabases.put(
        testDatabaseName,
        Database.builder()
            .name(testDatabaseName)
            .locationUri(
                tempDir != null
                    ? tempDir.toString() + "/" + testDatabaseName
                    : "/tmp/" + testDatabaseName)
            .build());
  }

  @Override
  protected LanceNamespace createNamespace() throws Exception {
    glueNamespace = new GlueNamespace();

    // Create config with root property
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    GlueNamespaceConfig config = new GlueNamespaceConfig(properties);
    glueNamespace.initialize(config, glueClient, allocator);

    // Setup mock for database operations (this will create the test database in the map)
    setupGlueMocks();

    return glueNamespace;
  }

  // Helper method for Java 8 compatibility
  private Map<String, String> createMap(String key, String value) {
    Map<String, String> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  private void setupGlueMocks() {
    // Note: test database is initialized in @BeforeEach

    // Create database - adds to our tracking map
    lenient()
        .when(glueClient.createDatabase(any(CreateDatabaseRequest.class)))
        .thenAnswer(
            invocation -> {
              CreateDatabaseRequest req = invocation.getArgument(0);
              String dbName = req.databaseInput().name();

              if (createdDatabases.containsKey(dbName)) {
                throw software.amazon.awssdk.services.glue.model.AlreadyExistsException.builder()
                    .message("Database already exists: " + dbName)
                    .build();
              }

              // Store custom properties from the request
              Map<String, String> parameters = req.databaseInput().parameters();
              if (parameters == null) {
                parameters = new HashMap<>();
              }

              Database db =
                  Database.builder()
                      .name(dbName)
                      .locationUri(tempDir.toString() + "/" + dbName)
                      .parameters(parameters)
                      .build();
              createdDatabases.put(dbName, db);

              return CreateDatabaseResponse.builder().build();
            });

    // Get database - look up from our tracking map
    lenient()
        .when(glueClient.getDatabase(any(GetDatabaseRequest.class)))
        .thenAnswer(
            invocation -> {
              GetDatabaseRequest req = invocation.getArgument(0);
              Database db = createdDatabases.get(req.name());

              if (db == null) {
                throw EntityNotFoundException.builder()
                    .message("Database not found: " + req.name())
                    .build();
              }

              return GetDatabaseResponse.builder().database(db).build();
            });

    // List databases - return all from our tracking map
    lenient()
        .when(glueClient.getDatabases(any(GetDatabasesRequest.class)))
        .thenAnswer(
            invocation ->
                GetDatabasesResponse.builder().databaseList(createdDatabases.values()).build());

    // Create table - adds to our tracking map
    lenient()
        .when(
            glueClient.createTable(
                any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenAnswer(
            invocation -> {
              software.amazon.awssdk.services.glue.model.CreateTableRequest req =
                  invocation.getArgument(0);
              String dbName = req.databaseName();
              String tableName = req.tableInput().name();
              String tableKey = dbName + "." + tableName;

              if (createdTables.containsKey(tableKey)) {
                throw software.amazon.awssdk.services.glue.model.AlreadyExistsException.builder()
                    .message("Table already exists: " + tableKey)
                    .build();
              }

              String tableLocation = tempDir.toString() + "/" + dbName + "/" + tableName;

              // Create actual Lance dataset directory and files for testing
              try {
                java.nio.file.Path dbDir = java.nio.file.Paths.get(tempDir.toString(), dbName);
                java.nio.file.Path tableDir = java.nio.file.Paths.get(tableLocation);
                java.nio.file.Files.createDirectories(tableDir);

                // Create basic Lance dataset structure - _versions directory and manifest file
                java.nio.file.Path versionsDir = tableDir.resolve("_versions");
                java.nio.file.Files.createDirectories(versionsDir);

                // Create a simple version file to make it look like a Lance dataset
                java.nio.file.Path versionFile = versionsDir.resolve("1.manifest");
                java.nio.file.Files.write(versionFile, "{}".getBytes());

              } catch (Exception e) {
                // If file creation fails, continue with mock (for tests that don't need real files)
              }

              Table table =
                  Table.builder()
                      .name(tableName)
                      .databaseName(dbName)
                      .storageDescriptor(
                          StorageDescriptor.builder().location(tableLocation).build())
                      .parameters(createMap(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
                      .build();
              createdTables.put(tableKey, table);

              return CreateTableResponse.builder().build();
            });

    // Get table - look up from our tracking map
    lenient()
        .when(glueClient.getTable(any(GetTableRequest.class)))
        .thenAnswer(
            invocation -> {
              GetTableRequest req = invocation.getArgument(0);
              String tableKey = req.databaseName() + "." + req.name();
              Table table = createdTables.get(tableKey);

              if (table == null) {
                throw EntityNotFoundException.builder()
                    .message("Table not found: " + req.name())
                    .build();
              }

              return GetTableResponse.builder().table(table).build();
            });

    // List tables - return tables for the specified database with pagination support
    lenient()
        .when(glueClient.getTables(any(GetTablesRequest.class)))
        .thenAnswer(
            invocation -> {
              GetTablesRequest req = invocation.getArgument(0);
              String dbName = req.databaseName();

              List<Table> allTables =
                  createdTables.values().stream()
                      .filter(table -> dbName.equals(table.databaseName()))
                      .collect(Collectors.toList());

              // Handle pagination
              List<Table> resultTables = allTables;
              String nextToken = null;

              if (req.maxResults() != null && req.maxResults() > 0) {
                int maxResults = req.maxResults();
                int startIndex = 0;

                // Handle pagination token (simple implementation)
                if (req.nextToken() != null && !req.nextToken().isEmpty()) {
                  try {
                    startIndex = Integer.parseInt(req.nextToken());
                  } catch (NumberFormatException e) {
                    startIndex = 0;
                  }
                }

                int endIndex = Math.min(startIndex + maxResults, allTables.size());
                resultTables = allTables.subList(startIndex, endIndex);

                // Set next token if there are more results
                if (endIndex < allTables.size()) {
                  nextToken = String.valueOf(endIndex);
                }
              }

              return GetTablesResponse.builder()
                  .tableList(resultTables)
                  .nextToken(nextToken)
                  .build();
            });

    // Delete table - removes from our tracking map
    lenient()
        .when(glueClient.deleteTable(any(DeleteTableRequest.class)))
        .thenAnswer(
            invocation -> {
              DeleteTableRequest req = invocation.getArgument(0);
              String tableKey = req.databaseName() + "." + req.name();

              if (!createdTables.containsKey(tableKey)) {
                throw EntityNotFoundException.builder()
                    .message("Table not found: " + req.name())
                    .build();
              }

              // Clean up actual files if they exist
              Table table = createdTables.get(tableKey);
              if (table != null
                  && table.storageDescriptor() != null
                  && table.storageDescriptor().location() != null) {
                try {
                  java.nio.file.Path tableDir =
                      java.nio.file.Paths.get(table.storageDescriptor().location());
                  if (java.nio.file.Files.exists(tableDir)) {
                    // Simple recursive delete
                    java.nio.file.Files.walk(tableDir)
                        .sorted(java.util.Comparator.reverseOrder())
                        .forEach(
                            path -> {
                              try {
                                java.nio.file.Files.deleteIfExists(path);
                              } catch (Exception e) {
                                // Ignore cleanup errors
                              }
                            });
                  }
                } catch (Exception e) {
                  // Ignore cleanup errors
                }
              }

              createdTables.remove(tableKey);
              return DeleteTableResponse.builder().build();
            });

    // Delete database - removes from our tracking map
    lenient()
        .when(glueClient.deleteDatabase(any(DeleteDatabaseRequest.class)))
        .thenAnswer(
            invocation -> {
              DeleteDatabaseRequest req = invocation.getArgument(0);
              String dbName = req.name();

              if (!createdDatabases.containsKey(dbName)) {
                throw EntityNotFoundException.builder()
                    .message("Database not found: " + dbName)
                    .build();
              }

              // For simplicity, always allow database deletion and remove associated tables
              // Real Glue behavior may be different but this works for TCK testing
              createdDatabases.remove(dbName);
              createdTables.entrySet().removeIf(entry -> entry.getKey().startsWith(dbName + "."));

              return DeleteDatabaseResponse.builder().build();
            });
  }

  @Override
  protected void cleanupNamespace() throws Exception {
    // Mock cleanup - no real resources to clean
  }

  @Override
  protected String getTempDirectory() {
    return tempDir.toString();
  }

  @Override
  protected NamespaceCapabilities getCapabilities() {
    return NamespaceCapabilities.builder()
        .supportsNamespaceOperations(true) // Can create/drop databases
        .supportsRootNamespaceOnly(true) // Only root-level databases
        .supportsNestedNamespaces(false) // No nesting (no catalogs)
        .supportsTablesInRootNamespace(false) // Tables must be in databases
        .supportsMultiLevelTableIds(true) // database.table format
        .supportsCascadeDelete(true) // Supports CASCADE
        .supportsRestrictDelete(true) // Supports RESTRICT
        .supportsTableVersioning(false) // No explicit versioning in Glue
        .supportsPagination(true) // Glue supports pagination
        .supportsRegisterDeregister(true) // Can register/deregister tables
        .build();
  }

  @Override
  protected List<String> getTestNamespaceId() {
    // Glue uses single-level namespace IDs (database names)
    return Arrays.asList(testDatabaseName);
  }

  @Override
  protected List<String> getTestTableId(String tableName) {
    // Glue uses two-level table identifiers: database.table
    return Arrays.asList(testDatabaseName, tableName);
  }
}
