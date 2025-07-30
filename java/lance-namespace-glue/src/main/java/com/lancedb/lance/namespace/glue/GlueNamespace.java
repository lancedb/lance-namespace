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

public class GlueNamespace implements LanceNamespace, Closeable {

  private static final int MAX_GLUE_LISTING_SIZE = 100;

  private GlueNamespaceConfig config;
  private GlueClient glueClient;

  public GlueNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties) {
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
    validateSingleLevelNamespace(request.getId());

    GetDatabasesRequest.Builder listRequest =
        GetDatabasesRequest.builder().catalogId(config.glueCatalogId());
    int pageSize = request.getLimit() != null ? request.getLimit() : Integer.MAX_VALUE;
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

    return new ListNamespacesResponse().namespaces(databases).pageToken(glueNextToken);
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
