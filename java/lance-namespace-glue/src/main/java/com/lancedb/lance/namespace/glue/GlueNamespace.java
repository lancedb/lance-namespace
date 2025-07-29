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
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.AlreadyExistsException;
import software.amazon.awssdk.services.glue.model.CreateDatabaseRequest;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.DatabaseInput;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseRequest;
import software.amazon.awssdk.services.glue.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.EntityNotFoundException;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;
import software.amazon.awssdk.services.glue.model.GetTablesRequest;
import software.amazon.awssdk.services.glue.model.GetTablesResponse;
import software.amazon.awssdk.services.glue.model.GlueException;
import software.amazon.awssdk.services.glue.model.Table;

import java.io.Closeable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GlueNamespace implements LanceNamespace, Closeable {

  private static final String NAMESPACE_INSTANCE = "v1/namespace/";

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
    String instance = buildNamespaceInstance(null);
    validateParent(request.getId(), instance);

    GetDatabasesRequest.Builder listRequest =
        GetDatabasesRequest.builder().catalogId(config.glueCatalogId());
    int pageSize = request.getLimit() != null ? request.getLimit() : Integer.MAX_VALUE;
    int remaining = pageSize;
    String glueNextToken = request.getPageToken();
    Set<String> databases = Sets.newHashSet();
    do {
      int fetchSize = Math.min(remaining, GlueConstants.MAX_LISTING_SIZE);
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
    String namespaceName = extractNamespaceName(request.getId());
    String instance = buildNamespaceInstance(namespaceName);
    validateParent(request.getId(), instance);
    validateNamespaceName(namespaceName, instance);

    Database database = getDatabase(namespaceName, instance);
    Map<String, String> glueProperties = extractDatabaseProperties(database);

    return new DescribeNamespaceResponse().properties(glueProperties);
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    String namespaceName = extractNamespaceName(request.getId());
    String instance = buildNamespaceInstance(namespaceName);
    validateParent(request.getId(), instance);
    validateNamespaceName(namespaceName, instance);

    CreateNamespaceRequest.ModeEnum mode =
        request.getMode() != null ? request.getMode() : CreateNamespaceRequest.ModeEnum.CREATE;
    Map<String, String> params =
        request.getProperties() != null ? request.getProperties() : ImmutableMap.of();
    boolean namespaceExists = namespaceExists(namespaceName, instance);

    switch (mode) {
      case EXIST_OK:
        if (namespaceExists) {
          return describeAsCreateResponse(namespaceName, instance);
        }
        break;
      case OVERWRITE:
        if (namespaceExists) {
          deleteDatabase(namespaceName, instance);
        }
        break;
      case CREATE:
        if (namespaceExists) {
          throw LanceNamespaceException.conflict(
              "Namespace already exists: " + namespaceName,
              "/errors/namespace-already-exists",
              instance,
              "Namespace already exists: " + namespaceName + "Mode: " + mode);
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
        return describeAsCreateResponse(namespaceName, instance);
      }
      throw LanceNamespaceException.conflict(
          "Namespace already exists: " + namespaceName,
          "/errors/namespace-already-exists",
          instance,
          formatGlueExceptionDetail(e));
    } catch (GlueException e) {
      throw LanceNamespaceException.serverError(
          "Failed to create namespace: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    String namespaceName = extractNamespaceName(request.getId());
    String instance = buildNamespaceInstance(namespaceName);
    validateParent(request.getId(), instance);
    validateNamespaceName(namespaceName, instance);

    DropNamespaceRequest.ModeEnum mode =
        request.getMode() != null ? request.getMode() : DropNamespaceRequest.ModeEnum.FAIL;
    DropNamespaceRequest.BehaviorEnum behavior =
        request.getBehavior() != null
            ? request.getBehavior()
            : DropNamespaceRequest.BehaviorEnum.RESTRICT;

    if (!namespaceExists(namespaceName, instance)) {
      if (mode == DropNamespaceRequest.ModeEnum.SKIP) {
        return new DropNamespaceResponse();
      }
      throw LanceNamespaceException.notFound(
          "Namespace not found: " + namespaceName,
          "/errors/namespace-not-found",
          instance,
          "The requested namespace does not exist.");
    }

    switch (behavior) {
      case CASCADE:
        deleteAllTables(namespaceName, instance);
        break;
      case RESTRICT:
        ensureNamespaceEmpty(namespaceName, instance);
        break;
    }

    deleteDatabaseOrSkip(namespaceName, instance, mode);

    return new DropNamespaceResponse();
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    String namespaceName = extractNamespaceName(request.getId());
    String instance = buildNamespaceInstance(namespaceName);
    validateParent(request.getId(), instance);
    validateNamespaceName(namespaceName, instance);

    // Throws if database doesn't exist
    getDatabase(namespaceName, instance);
  }

  private void validateParent(List<String> id, String instance) {
    if (id != null && id.size() > 1) {
      throw LanceNamespaceException.badRequest(
          "Nested namespaces not supported",
          "/errors/bad-request",
          instance,
          "Glue does not support nested namespaces. Found nested path: " + String.join("/", id));
    }
  }

  private void validateNamespaceName(String name, String instance) {
    if (name == null || name.isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Invalid namespace name",
          "/errors/bad-request",
          instance,
          "Namespace name cannot be null or empty");
    }
  }

  private String extractNamespaceName(List<String> id) {
    if (id == null || id.isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Invalid namespace identifier",
          "/errors/bad-request",
          NAMESPACE_INSTANCE,
          "Namespace identifier cannot be null or empty");
    }
    return id.get(0);
  }

  private String buildNamespaceInstance(String namespaceName) {
    return namespaceName != null ? NAMESPACE_INSTANCE + namespaceName : NAMESPACE_INSTANCE;
  }

  private String formatGlueExceptionDetail(GlueException e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(e.getClass().getSimpleName() + ": " + e.getMessage());
    e.printStackTrace(pw);
    return sw.toString();
  }

  private static Map<String, String> extractDatabaseProperties(Database database) {
    Map<String, String> glueProperties = Maps.newHashMap(database.parameters());
    if (database.locationUri() != null) {
      glueProperties.put(GlueConstants.PARAM_LOCATION, database.locationUri());
    }
    if (database.description() != null) {
      glueProperties.put(GlueConstants.PARAM_DESCRIPTION, database.description());
    }
    return glueProperties;
  }

  private boolean namespaceExists(String namespaceName, String instance) {
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
      throw LanceNamespaceException.serverError(
          "Failed to get Glue Database: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  private Database getDatabase(String namespaceName, String instance) {
    try {
      return glueClient
          .getDatabase(
              GetDatabaseRequest.builder()
                  .catalogId(config.glueCatalogId())
                  .name(namespaceName)
                  .build())
          .database();
    } catch (EntityNotFoundException e) {
      throw LanceNamespaceException.notFound(
          "Glue database not found: " + namespaceName,
          "/errors/namespace-not-found",
          instance,
          formatGlueExceptionDetail(e));
    } catch (GlueException e) {
      throw LanceNamespaceException.serverError(
          "Failed to get Glue database: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  private void deleteDatabase(String namespaceName, String instance) {
    try {
      glueClient.deleteDatabase(
          DeleteDatabaseRequest.builder()
              .catalogId(config.glueCatalogId())
              .name(namespaceName)
              .build());
    } catch (GlueException e) {
      throw LanceNamespaceException.serverError(
          "Failed to drop Glue database: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  private void deleteDatabaseOrSkip(
      String namespaceName, String instance, DropNamespaceRequest.ModeEnum mode) {
    try {
      glueClient.deleteDatabase(
          DeleteDatabaseRequest.builder()
              .catalogId(config.glueCatalogId())
              .name(namespaceName)
              .build());
    } catch (EntityNotFoundException e) {
      if (mode != DropNamespaceRequest.ModeEnum.SKIP) {
        throw LanceNamespaceException.notFound(
            "Namespace not found: " + namespaceName,
            "/errors/namespace-not-found",
            instance,
            formatGlueExceptionDetail(e));
      }
    } catch (GlueException e) {
      throw LanceNamespaceException.serverError(
          "Failed to drop Glue database: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  private DatabaseInput buildDatabaseInput(String namespaceName, Map<String, String> params) {
    DatabaseInput.Builder builder = DatabaseInput.builder().name(namespaceName);

    if (params.containsKey(GlueConstants.PARAM_LOCATION)) {
      builder.locationUri(params.get(GlueConstants.PARAM_LOCATION));
    }
    if (params.containsKey(GlueConstants.PARAM_DESCRIPTION)) {
      builder.description(params.get(GlueConstants.PARAM_DESCRIPTION));
    }

    Map<String, String> parameters = Maps.newHashMap(params);
    parameters.remove(GlueConstants.PARAM_LOCATION);
    parameters.remove(GlueConstants.PARAM_DESCRIPTION);
    if (!parameters.isEmpty()) {
      builder.parameters(parameters);
    }

    return builder.build();
  }

  private CreateNamespaceResponse describeAsCreateResponse(String namespaceName, String instance) {
    Database existing = getDatabase(namespaceName, instance);
    Map<String, String> properties = extractDatabaseProperties(existing);
    return new CreateNamespaceResponse().properties(properties);
  }

  private void deleteAllTables(String namespaceName, String instance) {
    try {
      String nextToken;
      do {
        GetTablesResponse tablesResponse =
            glueClient.getTables(
                GetTablesRequest.builder()
                    .catalogId(config.glueCatalogId())
                    .databaseName(namespaceName)
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
      throw LanceNamespaceException.serverError(
          "Failed to delete tables in glue database: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  private void ensureNamespaceEmpty(String namespaceName, String instance) {
    try {
      GetTablesResponse tablesResponse =
          glueClient.getTables(
              GetTablesRequest.builder()
                  .catalogId(config.glueCatalogId())
                  .databaseName(namespaceName)
                  .build());

      if (!tablesResponse.tableList().isEmpty()) {
        throw LanceNamespaceException.badRequest(
            "Namespace not empty: " + namespaceName,
            "/errors/namespace-not-empty",
            instance,
            "Cannot drop namespace: " + namespaceName + " because it contains tables");
      }
    } catch (GlueException e) {
      throw LanceNamespaceException.serverError(
          "Failed to ensure Glue database is empty: " + namespaceName,
          "/errors/glue-service-error",
          instance,
          formatGlueExceptionDetail(e));
    }
  }

  @Override
  public void close() {
    if (glueClient != null) {
      glueClient.close();
    }
  }
}
