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
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LanceGlueNamespace implements LanceNamespace, Closeable {
  private static final int MAX_GLUE_LISTING_SIZE = 100;

  private GlueNamespaceProperties properties;
  public GlueClient glueClient;
  private String name;

  public LanceGlueNamespace() {}

  @Override
  public void initialize(String name, Map<String, String> properties) {
    GlueNamespaceProperties glueProperties = new GlueNamespaceProperties(properties);
    GlueClient glueClient =
        GlueClient.builder().applyMutation(glueProperties::configureClientBuilder).build();
    initialize(name, glueProperties, glueClient);
  }

  @VisibleForTesting
  void initialize(String name, GlueNamespaceProperties properties, GlueClient glueClient) {
    this.properties = properties;
    this.glueClient = glueClient;
    this.name = name;
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    validateSingleLevelNamespace(request.getParent());

    GetDatabasesRequest.Builder listRequest =
        GetDatabasesRequest.builder().catalogId(properties.glueCatalogId());
    int pageSize = request.getPageSize() != null ? request.getPageSize() : Integer.MAX_VALUE;
    int remaining = pageSize;
    String glueNextToken = request.getPageToken();
    Set<String> databases = Sets.newHashSet();
    do {
      int fetchSize = Math.min(remaining, MAX_GLUE_LISTING_SIZE);
      GetDatabasesResponse response =
          glueClient.getDatabases(
              listRequest.maxResults(fetchSize).nextToken(glueNextToken).build());
      response.databaseList().forEach(d -> databases.add(d.name()));
      glueNextToken = response.nextToken();
      remaining = pageSize - databases.size();
    } while (glueNextToken != null && remaining > 0);

    return new ListNamespacesResponse().namespaces(databases).nextPageToken(glueNextToken);
  }

  private void validateSingleLevelNamespace(List<String> parent) {
    if (parent != null && !parent.isEmpty()) {
      throw new RuntimeException("Glue does not support nested namespace, found nested: " + parent);
    }
  }

  @Override
  public void close() {
    if (glueClient != null) {
      glueClient.close();
    }
  }
}
