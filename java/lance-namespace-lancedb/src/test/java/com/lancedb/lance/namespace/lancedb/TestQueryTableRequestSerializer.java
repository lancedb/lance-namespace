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
package com.lancedb.lance.namespace.lancedb;

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.lancedb.jackson.LanceNamespaceJacksonModule;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery;
import com.lancedb.lance.namespace.model.QueryTableRequestVector;
import com.lancedb.lance.namespace.model.StringFtsQuery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestQueryTableRequestSerializer {
  private static final Logger log = LoggerFactory.getLogger(TestQueryTableRequestSerializer.class);

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new LanceNamespaceJacksonModule());
  }

  @Test
  public void testSingleVectorSerialization() throws Exception {
    // Create a query request with a single vector
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test_table"));
    request.setK(10);

    // Set single vector
    List<Float> vector = Arrays.asList(1.0f, 2.0f, 3.0f, 4.0f);
    QueryTableRequestVector vectorObj = new QueryTableRequestVector();
    vectorObj.setSingleVector(vector);
    request.setVector(vectorObj);

    // Serialize to JSON
    String json = objectMapper.writeValueAsString(request);
    log.debug("Serialized JSON: {}", json);

    // Verify the vector is serialized as a direct array
    assertTrue(json.contains("\"vector\":[1.0,2.0,3.0,4.0]"));
    assertTrue(json.contains("\"id\":[\"test_table\"]"));
    assertTrue(json.contains("\"k\":10"));
  }

  @Test
  public void testMultiVectorSerialization() throws Exception {
    // Create a query request with multiple vectors
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test", "test_table"));
    request.setK(5);

    // Set multiple vectors
    List<List<Float>> multiVector =
        Arrays.asList(
            Arrays.asList(1.0f, 2.0f), Arrays.asList(3.0f, 4.0f), Arrays.asList(5.0f, 6.0f));
    QueryTableRequestVector vectorObj = new QueryTableRequestVector();
    vectorObj.setMultiVector(multiVector);
    request.setVector(vectorObj);

    // Serialize to JSON
    String json = objectMapper.writeValueAsString(request);
    log.debug("Serialized JSON: {}", json);

    // Verify the vector is serialized as an array of arrays
    assertTrue(json.contains("\"vector\":[[1.0,2.0],[3.0,4.0],[5.0,6.0]]"));
  }

  @Test
  public void testOptionalFieldsSerialization() throws Exception {
    // Create a query request with optional fields
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test", "test_table"));
    request.setK(10);

    // Set vector
    QueryTableRequestVector vectorObj = new QueryTableRequestVector();
    vectorObj.setSingleVector(Arrays.asList(1.0f));
    request.setVector(vectorObj);

    // Set optional fields
    request.setFilter("id > 10");
    request.setPrefilter(true);
    request.setColumns(Arrays.asList("id", "name"));

    // Serialize to JSON
    String json = objectMapper.writeValueAsString(request);
    log.debug("Serialized JSON: {}", json);

    // Verify optional fields are included
    assertTrue(json.contains("\"filter\":\"id > 10\""));
    assertTrue(json.contains("\"prefilter\":true"));
    assertTrue(json.contains("\"columns\":[\"id\",\"name\"]"));
  }

  @Test
  public void testEmptyVectorSerialization() throws Exception {
    // Create a query request with an empty vector
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test", "test_table"));
    request.setK(10);

    // Set empty vector
    QueryTableRequestVector vectorObj = new QueryTableRequestVector();
    vectorObj.setSingleVector(new ArrayList<>()); // Empty list of Float
    request.setVector(vectorObj);

    // Serialize to JSON
    ObjectMapper customMapper = new ObjectMapper();
    customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String json = customMapper.writeValueAsString(request);
    log.debug("Serialized JSON with empty vector: {}", json);

    // Verify the vector is serialized as an empty array
    assertTrue(json.contains("\"vector\":[]"));
  }

  @Test
  public void testNullVectorSerialization() throws Exception {
    // Create a query request with null vector
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test", "test_table"));
    request.setK(10);
    // Don't set vector at all (leave it null)

    // Serialize to JSON
    ObjectMapper customMapper = new ObjectMapper();
    customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String json = customMapper.writeValueAsString(request);
    log.debug("Serialized JSON with null vector: {}", json);

    // Verify the vector is serialized as an empty array
    assertTrue(json.contains("\"vector\":[]"));
  }

  @Test
  public void debugFullTextQuerySerialization() throws Exception {
    // Create the same structure as in the failing test
    QueryTableRequest ftsPrefilterQuery = new QueryTableRequest();
    ftsPrefilterQuery.setId(Arrays.asList("test", "test_table"));
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
    log.debug("Default JSON (without custom serializer):");
    log.debug(defaultJson);

    // Test with custom serializer
    ObjectMapper customMapper = new ObjectMapper();
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String customJson = customMapper.writeValueAsString(ftsPrefilterQuery);
    log.debug("Custom JSON (with custom serializer):");
    log.debug(customJson);

    // Test with ApiClient's ObjectMapper
    ApiClient apiClient = new ApiClient();
    ObjectMapper apiClientMapper = apiClient.getObjectMapper();
    apiClientMapper.registerModule(new LanceNamespaceJacksonModule());
    String apiClientJson = apiClientMapper.writeValueAsString(ftsPrefilterQuery);
    log.debug("ApiClient JSON (with custom serializer):");
    log.debug(apiClientJson);
  }
}
