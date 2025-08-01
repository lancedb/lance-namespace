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
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.AlreadyExistsException;
import software.amazon.awssdk.services.glue.model.CreateDatabaseRequest;
import software.amazon.awssdk.services.glue.model.CreateTableRequest;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.DatabaseInput;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseRequest;
import software.amazon.awssdk.services.glue.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.EntityNotFoundException;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;
import software.amazon.awssdk.services.glue.model.GetTableRequest;
import software.amazon.awssdk.services.glue.model.GetTableVersionRequest;
import software.amazon.awssdk.services.glue.model.GetTablesRequest;
import software.amazon.awssdk.services.glue.model.GetTablesResponse;
import software.amazon.awssdk.services.glue.model.GlueException;
import software.amazon.awssdk.services.glue.model.StorageDescriptor;
import software.amazon.awssdk.services.glue.model.Table;
import software.amazon.awssdk.services.glue.model.TableInput;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GlueNamespace implements LanceNamespace, Closeable {

  private static final String PARAM_LOCATION = "location";
  private static final String PARAM_DESCRIPTION = "description";
  public static final String TABLE_TYPE_PROP = "table_type";
  public static final String LANCE_TABLE_TYPE_VALUE = "lance";
  public static final String MANAGED_BY_PROP = "managed_by";
  public static final String STORAGE_VALUE = "storage";
  private static final int MAX_LISTING_SIZE = 100;

  private GlueNamespaceConfig config;
  private GlueClient glueClient;
  private BufferAllocator allocator;

  public GlueNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    GlueNamespaceConfig glueProperties = new GlueNamespaceConfig(configProperties);
    GlueClient glueClient =
        GlueClient.builder().applyMutation(glueProperties::configureClientBuilder).build();
    initialize(glueProperties, glueClient);
  }

  @VisibleForTesting
  void initialize(GlueNamespaceConfig properties, GlueClient glueClient) {
    this.config = properties;
    this.glueClient = glueClient;
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    validateParent(request.getId());

    GetDatabasesRequest.Builder listRequest =
        GetDatabasesRequest.builder().catalogId(config.glueCatalogId());
    int pageSize = request.getLimit() != null ? request.getLimit() : Integer.MAX_VALUE;
    int remaining = pageSize;
    String glueNextToken = request.getPageToken();
    Set<String> databases = Sets.newHashSet();
    do {
      int fetchSize = Math.min(remaining, MAX_LISTING_SIZE);
      GetDatabasesResponse response =
          glueClient.getDatabases(
              listRequest.maxResults(fetchSize).nextToken(glueNextToken).build());
      response.databaseList().forEach(d -> databases.add(d.name()));
      glueNextToken = response.nextToken();
      remaining = pageSize - databases.size();
    } while (glueNextToken != null && remaining > 0);

    return new ListNamespacesResponse().namespaces(databases).pageToken(glueNextToken);
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    String namespaceName = namespaceFromId(request.getId());

    Database database = getDatabase(namespaceName);
    Map<String, String> glueProperties = extractDatabaseProperties(database);
    return new DescribeNamespaceResponse().properties(glueProperties);
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    String namespaceName = namespaceFromId(request.getId());

    CreateNamespaceRequest.ModeEnum mode =
        request.getMode() != null ? request.getMode() : CreateNamespaceRequest.ModeEnum.CREATE;
    Map<String, String> params =
        request.getProperties() != null ? request.getProperties() : ImmutableMap.of();
    boolean namespaceExists = databaseExists(namespaceName);

    switch (mode) {
      case EXIST_OK:
        if (namespaceExists) {
          return describeNamespaceAsCreateResponse(namespaceName);
        }
        break;
      case OVERWRITE:
        if (namespaceExists) {
          deleteDatabase(namespaceName);
        }
        break;
    }

    try {
      glueClient.createDatabase(
          CreateDatabaseRequest.builder()
              .catalogId(config.glueCatalogId())
              .databaseInput(buildDatabaseInput(namespaceName, params))
              .build());
      return new CreateNamespaceResponse().properties(params);
    } catch (AlreadyExistsException e) {
      if (mode == CreateNamespaceRequest.ModeEnum.EXIST_OK) {
        return describeNamespaceAsCreateResponse(namespaceName);
      }
      throw GlueToLanceErrorConverter.conflict(e, "Namespace already exists: %s", namespaceName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to create namespace: %s", namespaceName);
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    String namespaceName = namespaceFromId(request.getId());

    DropNamespaceRequest.ModeEnum mode =
        request.getMode() != null ? request.getMode() : DropNamespaceRequest.ModeEnum.FAIL;
    DropNamespaceRequest.BehaviorEnum behavior =
        request.getBehavior() != null
            ? request.getBehavior()
            : DropNamespaceRequest.BehaviorEnum.RESTRICT;

    if (!databaseExists(namespaceName)) {
      if (mode == DropNamespaceRequest.ModeEnum.SKIP) {
        return new DropNamespaceResponse();
      }
      throw LanceNamespaceException.badRequest(
          "Namespace not found: " + namespaceName,
          "NAMESPACE_NOT_FOUND",
          namespaceName,
          "The requested namespace does not exist");
    }

    switch (behavior) {
      case CASCADE:
        deleteAllTables(namespaceName);
        break;
      case RESTRICT:
        ensureNamespaceEmpty(namespaceName);
        break;
    }

    deleteDatabase(namespaceName);
    return new DropNamespaceResponse();
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    String namespaceName = namespaceFromId(request.getId());
    // Throws if database doesn't exist
    getDatabase(namespaceName);
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    String namespaceName = namespaceFromId(request.getId());

    try {
      GetTablesRequest.Builder listRequest =
          GetTablesRequest.builder().catalogId(config.glueCatalogId()).databaseName(namespaceName);

      int pageSize = request.getLimit() != null ? request.getLimit() : Integer.MAX_VALUE;
      int remaining = pageSize;
      String glueNextToken = request.getPageToken();
      Set<String> tables = Sets.newHashSet();

      do {
        int fetchSize = Math.min(remaining, MAX_LISTING_SIZE);
        GetTablesResponse response =
            glueClient.getTables(
                listRequest.maxResults(fetchSize).nextToken(glueNextToken).build());
        response.tableList().stream().filter(this::isLanceTable).forEach(t -> tables.add(t.name()));
        glueNextToken = response.nextToken();
        remaining = pageSize - tables.size();
      } while (glueNextToken != null && remaining > 0);
      return new ListTablesResponse().tables(tables).pageToken(glueNextToken);
    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(e, "Glue database not found: %s", namespaceName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to list tables in Glue database: %s", namespaceName);
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    validateTableId(request.getId());
    String namespaceName = request.getId().get(0);
    String tableName = request.getId().get(1);

    try {
      Table table =
          glueClient
              .getTable(
                  GetTableRequest.builder()
                      .catalogId(config.glueCatalogId())
                      .databaseName(namespaceName)
                      .name(tableName)
                      .build())
              .table();

      ensureLanceTable(table);

      DescribeTableResponse response = new DescribeTableResponse();
      if (table.storageDescriptor() != null && table.storageDescriptor().location() != null) {
        response.setLocation(table.storageDescriptor().location());
      }
      return response;
    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(
          e, "Glue table not found: %s.%s", namespaceName, tableName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to get Glue table: %s.%s", namespaceName, tableName);
    }
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    validateTableId(request.getId());
    String namespaceName = request.getId().get(0);
    String tableName = request.getId().get(1);

    if (request.getLocation().isEmpty()) {
      throw LanceNamespaceException.badRequest("Table location is required", "BAD_REQUEST", "", "");
    }

    try {
      // TODO: register table mode
      Map<String, String> params = Maps.newHashMap();
      if (request.getProperties() != null) {
        params.putAll(request.getProperties());
      }
      params.put(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE);
      params.put(MANAGED_BY_PROP, STORAGE_VALUE); // Always storage for existing tables

      TableInput tableInput =
          TableInput.builder()
              .name(tableName)
              .storageDescriptor(
                  StorageDescriptor.builder()
                      .location(request.getLocation())
                      .parameters(params)
                      .build())
              .build();

      glueClient.createTable(
          CreateTableRequest.builder()
              .catalogId(config.glueCatalogId())
              .databaseName(namespaceName)
              .tableInput(tableInput)
              .build());

      RegisterTableResponse response = new RegisterTableResponse();
      response.setLocation(request.getLocation());
      response.setProperties(request.getProperties());
      return response;
    } catch (AlreadyExistsException e) {
      throw GlueToLanceErrorConverter.conflict(
          e, "Table already exists: %s.%s", namespaceName, tableName);
    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(e, "Namespace not found: %s", namespaceName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to register table: %s.%s", namespaceName, tableName);
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    validateTableId(request.getId());
    String namespaceName = request.getId().get(0);
    String tableName = request.getId().get(1);

    try {
      Table table =
          glueClient
              .getTable(
                  GetTableRequest.builder()
                      .catalogId(config.glueCatalogId())
                      .databaseName(namespaceName)
                      .name(tableName)
                      .build())
              .table();

      ensureLanceTable(table);

      glueClient.deleteTable(
          DeleteTableRequest.builder()
              .catalogId(config.glueCatalogId())
              .databaseName(namespaceName)
              .name(tableName)
              .build());
      DeregisterTableResponse response = new DeregisterTableResponse();
      response.setId(request.getId());
      if (table.storageDescriptor() != null && table.storageDescriptor().location() != null) {
        response.setLocation(table.storageDescriptor().location());
      }
      if (table.parameters() != null && !table.parameters().isEmpty()) {
        response.setProperties(table.parameters());
      }
      return response;

    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(
          e, "Table not found: %s.%s", namespaceName, tableName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to deregister table: %s.%s", namespaceName, tableName);
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    validateTableId(request.getId());
    String namespaceName = request.getId().get(0);
    String tableName = request.getId().get(1);

    try {
      Table table;
      if (request.getVersion() != null) {
        // Check specific table version
        String tableVersion = String.valueOf(request.getVersion());
        table =
            glueClient
                .getTableVersion(
                    GetTableVersionRequest.builder()
                        .catalogId(config.glueCatalogId())
                        .databaseName(namespaceName)
                        .tableName(tableName)
                        .versionId(tableVersion)
                        .build())
                .tableVersion()
                .table();
      } else {
        table =
            glueClient
                .getTable(
                    GetTableRequest.builder()
                        .catalogId(config.glueCatalogId())
                        .databaseName(namespaceName)
                        .name(tableName)
                        .build())
                .table();
        ensureLanceTable(table);
      }
    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(
          e, "Table not found: %s.%s", namespaceName, tableName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to check table existence: %s.%s", namespaceName, tableName);
    }
  }

  private void validateParent(List<String> id) {
    if (id != null && id.size() > 1) {
      String instance = String.join("/", id);
      throw LanceNamespaceException.badRequest(
          "Glue does not support nested namespaces. Found nested path: " + String.join("/", id),
          "BAD_REQUEST",
          instance,
          "Nested namespaces must have only one parent");
    }
  }

  private String namespaceFromId(List<String> id) {
    if (id == null || id.isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Namespace identifier cannot be null or empty", "BAD_REQUEST", "", "");
    }

    validateParent(id);
    String namespace = id.get(0);
    if (namespace == null || namespace.isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Namespace name cannot be empty", "BAD_REQUEST", "", "");
    }
    return namespace;
  }

  private void validateTableId(List<String> id) {
    if (id == null || id.size() != 2) {
      throw LanceNamespaceException.badRequest(
          "Table identifier must contain exactly 2 elements: [namespace, table]",
          "BAD_REQUEST",
          id != null ? String.join("/", id) : "",
          "Expected format: [namespace, table]");
    }

    if (id.get(0) == null || id.get(0).isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Namespace name cannot be empty", "BAD_REQUEST", "", "");
    }
    if (id.get(1) == null || id.get(1).isEmpty()) {
      throw LanceNamespaceException.badRequest("Table name cannot be empty", "BAD_REQUEST", "", "");
    }
  }

  private static Map<String, String> extractDatabaseProperties(Database database) {
    Map<String, String> glueProperties = Maps.newHashMap(database.parameters());
    if (database.locationUri() != null) {
      glueProperties.put(PARAM_LOCATION, database.locationUri());
    }
    if (database.description() != null) {
      glueProperties.put(PARAM_DESCRIPTION, database.description());
    }
    return glueProperties;
  }

  private boolean databaseExists(String namespaceName) {
    try {
      glueClient.getDatabase(
          GetDatabaseRequest.builder()
              .catalogId(config.glueCatalogId())
              .name(namespaceName)
              .build());
      return true;
    } catch (EntityNotFoundException e) {
      return false;
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to get Glue database: %s", namespaceName);
    }
  }

  private Database getDatabase(String namespaceName) {
    try {
      return glueClient
          .getDatabase(
              GetDatabaseRequest.builder()
                  .catalogId(config.glueCatalogId())
                  .name(namespaceName)
                  .build())
          .database();
    } catch (EntityNotFoundException e) {
      throw GlueToLanceErrorConverter.notFound(e, "Glue database not found: %s", namespaceName);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to get Glue database: %s", namespaceName);
    }
  }

  private void deleteDatabase(String namespaceName) {
    try {
      glueClient.deleteDatabase(
          DeleteDatabaseRequest.builder()
              .catalogId(config.glueCatalogId())
              .name(namespaceName)
              .build());
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to drop Glue namespace: %s", namespaceName);
    }
  }

  private DatabaseInput buildDatabaseInput(String namespaceName, Map<String, String> params) {
    DatabaseInput.Builder builder = DatabaseInput.builder().name(namespaceName);

    if (params.containsKey(PARAM_LOCATION)) {
      builder.locationUri(params.get(PARAM_LOCATION));
    }
    if (params.containsKey(PARAM_DESCRIPTION)) {
      builder.description(params.get(PARAM_DESCRIPTION));
    }

    Map<String, String> parameters = Maps.newHashMap(params);
    parameters.remove(PARAM_LOCATION);
    parameters.remove(PARAM_DESCRIPTION);
    if (!parameters.isEmpty()) {
      builder.parameters(parameters);
    }

    return builder.build();
  }

  private CreateNamespaceResponse describeNamespaceAsCreateResponse(String namespaceName) {
    Database existing = getDatabase(namespaceName);
    Map<String, String> properties = extractDatabaseProperties(existing);
    return new CreateNamespaceResponse().properties(properties);
  }

  private void deleteAllTables(String namespaceName) {
    // TODO: This should also delete the actual table data, not just the Glue table instance.
    try {
      String nextToken = null;
      do {
        GetTablesResponse tablesResponse =
            glueClient.getTables(
                GetTablesRequest.builder()
                    .catalogId(config.glueCatalogId())
                    .databaseName(namespaceName)
                    .nextToken(nextToken)
                    .build());
        for (Table table : tablesResponse.tableList()) {
          try {
            glueClient.deleteTable(
                DeleteTableRequest.builder()
                    .catalogId(config.glueCatalogId())
                    .databaseName(namespaceName)
                    .name(table.name())
                    .build());
          } catch (EntityNotFoundException e) {
            // Table already deleted, continue
          }
        }
        nextToken = tablesResponse.nextToken();
      } while (nextToken != null);
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to delete tables in glue database: %s", namespaceName);
    }
  }

  private void ensureNamespaceEmpty(String namespaceName) {
    try {
      GetTablesResponse tablesResponse =
          glueClient.getTables(
              GetTablesRequest.builder()
                  .catalogId(config.glueCatalogId())
                  .databaseName(namespaceName)
                  .build());
      if (!tablesResponse.tableList().isEmpty()) {
        throw LanceNamespaceException.badRequest(
            "Namespace not empty: " + namespaceName, "BAD_REQUEST", namespaceName, "");
      }
    } catch (GlueException e) {
      throw GlueToLanceErrorConverter.serverError(
          e, "Failed to ensure Glue database is empty: %s", namespaceName);
    }
  }

  private boolean isLanceTable(Table table) {
    if (table == null || table.parameters() == null) {
      return false;
    }
    return LANCE_TABLE_TYPE_VALUE.equalsIgnoreCase(table.parameters().get(TABLE_TYPE_PROP));
  }

  private void ensureLanceTable(Table table) {
    if (!isLanceTable(table)) {
      throw LanceNamespaceException.notFound(
          String.format("Table not found: %s.%s", table.databaseName(), table.name()),
          "NOT_LANCE_TABLE",
          table.databaseName() + "." + table.name(),
          "");
    }
  }

  @Override
  public void close() {
    if (glueClient != null) {
      glueClient.close();
    }
  }
}
