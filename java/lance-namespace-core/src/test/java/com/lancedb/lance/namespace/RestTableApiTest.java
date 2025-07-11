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
package com.lancedb.lance.namespace;

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.model.*;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.Float4Vector;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/** Test for Lance REST Table API using environment variables for configuration */
public class RestTableApiTest {

  // Configuration from environment variables
  private static String DATABASE;
  private static String API_KEY;
  private static String HOST_OVERRIDE;
  private static String REGION;

  // Test data
  private static final String TEST_TABLE_NAME = "vectors2";
  private String testCreateTableName;

  private LanceRestNamespace namespace;
  private BufferAllocator allocator;

  @BeforeAll
  public static void setUpClass() {
    // Get configuration from environment variables
    DATABASE = System.getenv("LANCEDB_DB");
    API_KEY = System.getenv("LANCEDB_API_KEY");
    HOST_OVERRIDE = System.getenv("LANCEDB_HOST_OVERRIDE");
    REGION = System.getenv("LANCEDB_REGION");

    // Default values if not set
    if (REGION == null) {
      REGION = "us-east-1";
    }

    if (DATABASE != null && API_KEY != null) {
      System.out.println("Using configuration:");
      System.out.println("  Database: " + DATABASE);
      System.out.println("  Region: " + REGION);
      System.out.println("  Host Override: " + (HOST_OVERRIDE != null ? HOST_OVERRIDE : "none"));
    }
  }

  @BeforeEach
  public void setUp() {
    namespace = initializeClient();
    allocator = new RootAllocator();
    // Generate unique table name for each test run
    testCreateTableName =
        "test_table_" + UUID.randomUUID().toString().replace("-", "_").substring(0, 8);
  }

  @Test
  public void testDescribeTable() {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("=== Test: Describe Table ===");

    DescribeTableRequest request = new DescribeTableRequest();
    // Table name goes in URL path, not request body
    // Database comes from headers automatically
    // Optional LanceDB fields:
    request.setName(TEST_TABLE_NAME);
    request.setVersion(null); // Latest version
    request.setWithTableUri(true); // Include table URI

    DescribeTableResponse response = namespace.describeTable(request);
    System.out.println("Table Description: " + response);

    // Validate the important LanceDB fields
    assertNotNull(response, "Response should not be null");

    // Validate LanceDB-specific fields
    assertEquals(TEST_TABLE_NAME, response.getTable(), "Table name should match");
    assertNotNull(response.getVersion(), "Version should not be null");
    assertTrue(response.getVersion() > 0, "Version should be positive");

    // Validate schema exists
    assertNotNull(response.getSchema(), "Schema should not be null");

    // Validate stats
    assertNotNull(response.getStats(), "Stats should not be null");
    assertTrue(
        response.getStats().getNumFragments() >= 0, "Number of fragments should be non-negative");
    assertTrue(
        response.getStats().getNumDeletedRows() >= 0,
        "Number of deleted rows should be non-negative");

    System.out.println("✓ Table name: " + response.getTable());
    System.out.println("✓ Version: " + response.getVersion());
    System.out.println(
        "✓ Schema fields count: " + (response.getSchema() != null ? "present" : "null"));
    System.out.println("✓ Stats - Fragments: " + response.getStats().getNumFragments());
    System.out.println("✓ Stats - Deleted rows: " + response.getStats().getNumDeletedRows());

    System.out.println("Test passed!");
  }

  @Test
  public void testCreateTable() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("=== Test: Create Table ===");
    System.out.println("Creating table: " + testCreateTableName);

    // Create Arrow schema
    Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
    Field nameField = new Field("name", FieldType.nullable(new ArrowType.Utf8()), null);
    Field vectorField =
        new Field(
            "vector",
            FieldType.nullable(new ArrowType.FixedSizeList(128)),
            Arrays.asList(
                new Field(
                    "item",
                    FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)),
                    null)));

    Schema schema = new Schema(Arrays.asList(idField, nameField, vectorField));

    // Create Arrow data
    try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
      // Allocate vectors
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");
      FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("vector");

      // Set row count
      root.setRowCount(3);

      // Populate data
      idVector.setSafe(0, 1);
      idVector.setSafe(1, 2);
      idVector.setSafe(2, 3);

      nameVector.setSafe(0, "Alice".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(1, "Bob".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(2, "Charlie".getBytes(StandardCharsets.UTF_8));

      // Populate vector field with dummy data
      Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
      vectorVector.allocateNew();

      // Create 128-dimensional vectors for each row
      for (int row = 0; row < 3; row++) {
        vectorVector.setNotNull(row);
        for (int dim = 0; dim < 128; dim++) {
          int index = row * 128 + dim;
          dataVector.setSafe(index, (float) (Math.random() * 10.0)); // Random values 0-10
        }
      }

      // Mark vectors as populated
      idVector.setValueCount(3);
      nameVector.setValueCount(3);
      dataVector.setValueCount(3 * 128); // 3 rows * 128 dimensions
      vectorVector.setValueCount(3);

      // Serialize to Arrow IPC format using ArrowStreamWriter
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      byte[] arrowIpcData = out.toByteArray();
      System.out.println("Arrow IPC data size: " + arrowIpcData.length + " bytes");

      // Create table using Arrow IPC data
      CreateTableResponse createResponse = namespace.createTable(testCreateTableName, arrowIpcData);
      System.out.println("Create Table Response: " + createResponse);

      // Validate the response
      assertNotNull(createResponse, "Response should not be null");

      System.out.println("✓ Table created successfully: " + testCreateTableName);
      System.out.println("✓ Table location: " + createResponse.getLocation());

      // Verify table exists by describing it
      System.out.println("\n--- Verifying table with describe ---");
      DescribeTableRequest describeRequest = new DescribeTableRequest();
      describeRequest.setName(testCreateTableName);
      describeRequest.setWithTableUri(true);

      DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
      System.out.println("Describe response after create: " + describeResponse);

      // Validate the table was created properly
      assertNotNull(describeResponse, "Describe response should not be null");
      assertEquals(
          testCreateTableName, describeResponse.getTable(), "Table name should match in describe");
      assertNotNull(describeResponse.getSchema(), "Schema should not be null");
      assertNotNull(describeResponse.getStats(), "Stats should not be null");

      System.out.println("✓ Table verified via describe");

      // Validate schema structure
      Object schemaObj = describeResponse.getSchema();
      assertNotNull(schemaObj, "Schema object should not be null");
      System.out.println("✓ Schema: " + schemaObj);

      // Validate version
      assertNotNull(describeResponse.getVersion(), "Version should not be null");
      assertTrue(describeResponse.getVersion() >= 1, "Version should be at least 1 for new table");
      System.out.println("✓ Table version: " + describeResponse.getVersion());

      // Validate stats
      assertTrue(
          describeResponse.getStats().getNumFragments() >= 0,
          "Number of fragments should be non-negative");
      System.out.println("✓ Stats - Fragments: " + describeResponse.getStats().getNumFragments());
      System.out.println(
          "✓ Stats - Deleted rows: " + describeResponse.getStats().getNumDeletedRows());

      System.out.println("\nTest passed!");
    }
  }

  private LanceRestNamespace initializeClient() {
    Map<String, String> config = new HashMap<>();
    config.put("headers.x-lancedb-database", DATABASE);
    config.put("headers.x-api-key", API_KEY);

    if (HOST_OVERRIDE != null) {
      config.put("host_override", HOST_OVERRIDE);
    }
    config.put("region", REGION);

    ApiClient apiClient = new ApiClient();

    // Set base URL based on configuration
    String baseUrl;
    if (HOST_OVERRIDE != null) {
      baseUrl = HOST_OVERRIDE;
    } else {
      baseUrl = String.format("https://%s.%s.api.lancedb.com", DATABASE, REGION);
    }
    apiClient.setBasePath(baseUrl);

    System.out.println("Initialized client with base URL: " + baseUrl);

    return new LanceRestNamespace(apiClient, config);
  }
}
