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
package com.lancedb.lance.namespace.test.jackson;

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.jackson.LanceNamespaceJacksonModule;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery;
import com.lancedb.lance.namespace.model.StringFtsQuery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DebugSerializerTest {

  @Test
  public void debugFullTextQuerySerialization() throws Exception {
    // Create the same structure as in the failing test
    QueryTableRequest ftsPrefilterQuery = new QueryTableRequest();
    ftsPrefilterQuery.setName("test_table");
    ftsPrefilterQuery.setNamespace(Arrays.asList("test"));
    ftsPrefilterQuery.setK(20);
    ftsPrefilterQuery.setPrefilter(true);
    ftsPrefilterQuery.setFilter("id < 25");
    ftsPrefilterQuery.setColumns(Arrays.asList("id", "text"));

    StringFtsQuery fts = new StringFtsQuery();
    fts.setQuery("document");
    QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
    fullTextQuery.setStringQuery(fts);
    ftsPrefilterQuery.setFullTextQuery(fullTextQuery);

    // Test with default ObjectMapper (no custom serializer)
    ObjectMapper defaultMapper = new ObjectMapper();
    String defaultJson = defaultMapper.writeValueAsString(ftsPrefilterQuery);
    System.out.println("Default JSON (without custom serializer):");
    System.out.println(defaultJson);
    System.out.println();

    // Test with custom serializer
    ObjectMapper customMapper = new ObjectMapper();
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String customJson = customMapper.writeValueAsString(ftsPrefilterQuery);
    System.out.println("Custom JSON (with custom serializer):");
    System.out.println(customJson);
    System.out.println();

    // Test with ApiClient's ObjectMapper
    ApiClient apiClient = new ApiClient();
    ObjectMapper apiClientMapper = apiClient.getObjectMapper();
    apiClientMapper.registerModule(new LanceNamespaceJacksonModule());
    String apiClientJson = apiClientMapper.writeValueAsString(ftsPrefilterQuery);
    System.out.println("ApiClient JSON (with custom serializer):");
    System.out.println(apiClientJson);
  }
}
