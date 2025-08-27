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
package com.lancedb.lance.namespace.gravitino;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.*;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.*;

import java.io.IOException;
import java.util.*;

/** Unit tests for GravitinoNamespace. */
public class TestGravitinoNamespace {
  
  private static WireMockServer wireMockServer;
  private GravitinoNamespace namespace;
  private BufferAllocator allocator;
  private ObjectMapper objectMapper;
  
  @BeforeClass
  public static void setupClass() {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8090));
    wireMockServer.start();
    WireMock.configureFor("localhost", 8090);
  }
  
  @AfterClass
  public static void teardownClass() {
    wireMockServer.stop();
  }
  
  @Before
  public void setup() {
    allocator = new RootAllocator();
    namespace = new GravitinoNamespace();
    objectMapper = new ObjectMapper();
    
    Map<String, String> config = new HashMap<>();
    config.put("endpoint", "http://localhost:8090");
    config.put("metalake", "test_metalake");
    config.put("catalog", "test_catalog");
    config.put("auth_token", "test-token");
    
    namespace.initialize(config, allocator);
    
    // Reset WireMock before each test
    WireMock.reset();
  }
  
  @After
  public void teardown() throws IOException {
    if (namespace != null) {
      namespace.close();
    }
    if (allocator != null) {
      allocator.close();
    }
  }
  
  @Test
  public void testCreateNamespace() throws Exception {
    // Prepare mock response
    GravitinoModels.ResponseWrapper<GravitinoModels.Schema> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    
    GravitinoModels.Schema schema = new GravitinoModels.Schema();
    schema.setName("test_schema");
    schema.setComment("Test schema");
    Map<String, String> props = new HashMap<>();
    props.put("key1", "value1");
    schema.setProperties(props);
    response.setData(schema);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(post(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas"))
        .withHeader("Authorization", equalTo("Bearer test-token"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setNamespace(ObjectIdentifier.of(ObjectIdentifier.Type.NAMESPACE, "test_schema"));
    Map<String, String> properties = new HashMap<>();
    properties.put("comment", "Test schema");
    properties.put("key1", "value1");
    request.setProperties(properties);
    
    CreateNamespaceResponse result = namespace.createNamespace(request);
    
    assertNotNull(result);
    assertEquals("test_schema", result.getNamespace().getName());
    assertEquals("Test schema", result.getProperties().get("comment"));
    assertEquals("value1", result.getProperties().get("key1"));
  }
  
  @Test
  public void testDescribeNamespace() throws Exception {
    // Prepare mock response
    GravitinoModels.ResponseWrapper<GravitinoModels.Schema> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    
    GravitinoModels.Schema schema = new GravitinoModels.Schema();
    schema.setName("test_schema");
    schema.setComment("Test schema description");
    Map<String, String> props = new HashMap<>();
    props.put("property1", "value1");
    schema.setProperties(props);
    response.setData(schema);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(get(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/test_schema"))
        .withHeader("Authorization", equalTo("Bearer test-token"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test
    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setNamespace(ObjectIdentifier.of(ObjectIdentifier.Type.NAMESPACE, "test_schema"));
    
    DescribeNamespaceResponse result = namespace.describeNamespace(request);
    
    assertNotNull(result);
    assertEquals("test_schema", result.getNamespace().getName());
    assertEquals("Test schema description", result.getProperties().get("comment"));
    assertEquals("value1", result.getProperties().get("property1"));
  }
  
  @Test
  public void testNamespaceExists() throws Exception {
    // Test existing namespace
    GravitinoModels.ResponseWrapper<GravitinoModels.Schema> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    GravitinoModels.Schema schema = new GravitinoModels.Schema();
    schema.setName("existing_schema");
    response.setData(schema);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(get(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/existing_schema"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setNamespace(ObjectIdentifier.of(ObjectIdentifier.Type.NAMESPACE, "existing_schema"));
    
    assertTrue(namespace.namespaceExists(request));
    
    // Test non-existing namespace
    stubFor(get(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/non_existing"))
        .willReturn(aResponse()
            .withStatus(404)));
    
    request.setNamespace(ObjectIdentifier.of(ObjectIdentifier.Type.NAMESPACE, "non_existing"));
    assertFalse(namespace.namespaceExists(request));
  }
  
  @Test
  public void testCreateTable() throws Exception {
    // Prepare mock response
    GravitinoModels.ResponseWrapper<GravitinoModels.Table> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    
    GravitinoModels.Table table = new GravitinoModels.Table();
    table.setName("test_table");
    
    List<GravitinoModels.Column> columns = new ArrayList<>();
    GravitinoModels.Column col1 = new GravitinoModels.Column();
    col1.setName("id");
    col1.setType(objectMapper.valueToTree("long"));
    col1.setNullable(false);
    columns.add(col1);
    
    GravitinoModels.Column col2 = new GravitinoModels.Column();
    col2.setName("name");
    col2.setType(objectMapper.valueToTree("string"));
    col2.setNullable(true);
    columns.add(col2);
    
    table.setColumns(columns);
    
    Map<String, String> props = new HashMap<>();
    props.put("format", "lance");
    props.put("provider", "lance");
    props.put("location", "s3://bucket/path/to/table");
    table.setProperties(props);
    
    response.setData(table);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(post(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/test_schema/tables"))
        .withHeader("Authorization", equalTo("Bearer test-token"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test
    CreateTableRequest request = new CreateTableRequest();
    request.setTableIdentifier(ObjectIdentifier.of(ObjectIdentifier.Type.TABLE, "test_schema", "test_table"));
    
    // Create Arrow schema
    List<Field> fields = Arrays.asList(
        Field.notNullable("id", new ArrowType.Int(64, true)),
        Field.nullable("name", ArrowType.Utf8.INSTANCE)
    );
    Schema arrowSchema = new Schema(fields);
    request.setSchema(arrowSchema);
    request.setLocation("s3://bucket/path/to/table");
    
    CreateTableResponse result = namespace.createTable(request);
    
    assertNotNull(result);
    assertEquals("test_table", result.getTableIdentifier().getName());
    assertEquals("s3://bucket/path/to/table", result.getLocation());
    assertEquals("lance", result.getProperties().get("format"));
    assertNotNull(result.getSchema());
    assertEquals(2, result.getSchema().getFields().size());
  }
  
  @Test
  public void testListTables() throws Exception {
    // Prepare mock response
    GravitinoModels.ResponseWrapper<GravitinoModels.EntityListResponse> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    
    GravitinoModels.EntityListResponse entityList = new GravitinoModels.EntityListResponse();
    List<GravitinoModels.NameIdentifier> identifiers = new ArrayList<>();
    
    GravitinoModels.NameIdentifier id1 = new GravitinoModels.NameIdentifier();
    id1.setName("table1");
    id1.setNamespace(Arrays.asList("test_metalake", "test_catalog", "test_schema"));
    identifiers.add(id1);
    
    GravitinoModels.NameIdentifier id2 = new GravitinoModels.NameIdentifier();
    id2.setName("table2");
    id2.setNamespace(Arrays.asList("test_metalake", "test_catalog", "test_schema"));
    identifiers.add(id2);
    
    entityList.setIdentifiers(identifiers);
    response.setData(entityList);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(get(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/test_schema/tables"))
        .withHeader("Authorization", equalTo("Bearer test-token"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test
    ListTablesRequest request = new ListTablesRequest();
    request.setNamespace(ObjectIdentifier.of(ObjectIdentifier.Type.NAMESPACE, "test_schema"));
    
    ListTablesResponse result = namespace.listTables(request);
    
    assertNotNull(result);
    assertEquals(2, result.getTables().size());
    assertEquals("table1", result.getTables().get(0).getName());
    assertEquals("table2", result.getTables().get(1).getName());
  }
  
  @Test
  public void testDropTable() throws Exception {
    // Prepare mock response
    GravitinoModels.ResponseWrapper<GravitinoModels.DropResponse> response = new GravitinoModels.ResponseWrapper<>();
    response.setCode(0);
    
    GravitinoModels.DropResponse dropResponse = new GravitinoModels.DropResponse();
    dropResponse.setDropped(true);
    response.setData(dropResponse);
    
    String jsonResponse = objectMapper.writeValueAsString(response);
    
    stubFor(delete(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/test_schema/tables/test_table?purge=true"))
        .withHeader("Authorization", equalTo("Bearer test-token"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test
    DropTableRequest request = new DropTableRequest();
    request.setTableIdentifier(ObjectIdentifier.of(ObjectIdentifier.Type.TABLE, "test_schema", "test_table"));
    request.setPurge(true);
    
    DropTableResponse result = namespace.dropTable(request);
    
    assertNotNull(result);
    assertTrue(result.isDropped());
  }
  
  @Test(expected = LanceNamespaceException.class)
  public void testCreateTableAlreadyExists() throws Exception {
    // Prepare mock 409 response
    GravitinoModels.ErrorResponse errorResponse = new GravitinoModels.ErrorResponse();
    errorResponse.setCode(409);
    errorResponse.setType("TableAlreadyExistsException");
    errorResponse.setMessage("Table already exists");
    
    String jsonResponse = objectMapper.writeValueAsString(errorResponse);
    
    stubFor(post(urlEqualTo("/api/v1/metalakes/test_metalake/catalogs/test_catalog/schemas/test_schema/tables"))
        .willReturn(aResponse()
            .withStatus(409)
            .withHeader("Content-Type", "application/json")
            .withBody(jsonResponse)));
    
    // Execute test - should throw exception
    CreateTableRequest request = new CreateTableRequest();
    request.setTableIdentifier(ObjectIdentifier.of(ObjectIdentifier.Type.TABLE, "test_schema", "existing_table"));
    
    List<Field> fields = Arrays.asList(Field.nullable("id", new ArrowType.Int(64, true)));
    Schema arrowSchema = new Schema(fields);
    request.setSchema(arrowSchema);
    
    namespace.createTable(request);
  }
}