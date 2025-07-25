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
package com.lancedb.lance.namespace.lancedb.jackson;

import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestVector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryTableRequestSerializerTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new LanceNamespaceJacksonModule());
  }

  @Test
  public void testSingleVectorSerialization() throws Exception {
    // Create a query request with a single vector
    QueryTableRequest request = new QueryTableRequest();
    request.setId(Arrays.asList("test", "test_table"));
    request.setK(10);

    // Set single vector
    List<Float> vector = Arrays.asList(1.0f, 2.0f, 3.0f, 4.0f);
    QueryTableRequestVector vectorObj = new QueryTableRequestVector();
    vectorObj.setSingleVector(vector);
    request.setVector(vectorObj);

    // Serialize to JSON
    String json = objectMapper.writeValueAsString(request);
    System.out.println("Serialized JSON: " + json);

    // Verify the vector is serialized as a direct array
    assertTrue(json.contains("\"vector\":[1.0,2.0,3.0,4.0]"));
    assertTrue(json.contains("\"name\":\"test_table\""));
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
    System.out.println("Serialized JSON: " + json);

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
    System.out.println("Serialized JSON: " + json);

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
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String json = customMapper.writeValueAsString(request);
    System.out.println("Serialized JSON with empty vector: " + json);

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
    customMapper.registerModule(new LanceNamespaceJacksonModule());
    String json = customMapper.writeValueAsString(request);
    System.out.println("Serialized JSON with null vector: " + json);

    // Verify the vector is serialized as an empty array
    assertTrue(json.contains("\"vector\":[]"));
  }
}
