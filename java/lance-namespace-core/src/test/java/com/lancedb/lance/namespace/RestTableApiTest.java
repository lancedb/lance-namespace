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
import org.apache.arrow.vector.ipc.ArrowFileReader;
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
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  private String testCreateTableName;

  private LanceRestNamespace namespace;
  private BufferAllocator allocator;

  @BeforeAll
  public static void setUpClass() {
    // Get configuration from environment variables
    // The top level folder name, e.g. s3://my-bucket/my-database
    // LANCEDB_DB is the 'my-database'
    DATABASE = System.getenv("LANCEDB_DB");
    API_KEY = System.getenv("LANCEDB_API_KEY");
    // e.g. http://localhost:10024
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
    // Only initialize if required environment variables are set
    // TODO add required environment variables as github secrets
    if (DATABASE != null && API_KEY != null) {
      namespace = initializeClient();
      allocator = new RootAllocator();
      // Generate unique table name for each test run
      testCreateTableName =
          "test_table_" + UUID.randomUUID().toString().replace("-", "_").substring(0, 8);
    }
  }

  @Test
  public void testTableLifecycle() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("=== Test: Table Lifecycle ===");

    // Create table with 3 rows using helper
    System.out.println("\n--- Creating table ---");
    CreateTableResponse createResponse = createTableHelper(testCreateTableName, 3);
    assertNotNull(createResponse, "Create response should not be null");

    // Test count rows
    System.out.println("\n--- Testing count rows ---");
    CountRowsRequest countRequest = new CountRowsRequest();
    countRequest.setName(testCreateTableName);

    Long countResponse = namespace.countRows(countRequest);
    assertNotNull(countResponse, "Count rows response should not be null");
    assertEquals(3, countResponse.longValue(), "Row count should match expected number");
    System.out.println("✓ Count rows verified: " + countResponse);

    // Test describe table
    System.out.println("\n--- Testing describe table ---");
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setName(testCreateTableName);

    DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
    assertNotNull(describeResponse, "Describe response should not be null");
    assertEquals(
        testCreateTableName, describeResponse.getTable(), "Table name should match in describe");
    assertNotNull(describeResponse.getSchema(), "Schema should not be null");
    assertNotNull(describeResponse.getStats(), "Stats should not be null");

    // Verify schema
    JsonSchema responseSchema = describeResponse.getSchema();
    assertNotNull(responseSchema, "Schema object should not be null");
    assertNotNull(responseSchema.getFields(), "Schema fields should not be null");
    assertEquals(3, responseSchema.getFields().size(), "Schema should have 3 fields");

    List<String> fieldNames =
        responseSchema.getFields().stream()
            .map(JsonField::getName)
            .collect(java.util.stream.Collectors.toList());
    assertTrue(fieldNames.contains("id"), "Schema should contain 'id' field");
    assertTrue(fieldNames.contains("name"), "Schema should contain 'name' field");
    assertTrue(fieldNames.contains("embedding"), "Schema should contain 'embedding' field");
    System.out.println("✓ Table schema verified with fields: " + fieldNames);

    // Verify version and stats
    assertNotNull(describeResponse.getVersion(), "Version should not be null");
    assertTrue(describeResponse.getVersion() >= 1, "Version should be at least 1 for new table");
    assertTrue(
        describeResponse.getStats().getNumFragments() >= 0,
        "Number of fragments should be non-negative");
    System.out.println("✓ Table version: " + describeResponse.getVersion());
    System.out.println("✓ Table fragments: " + describeResponse.getStats().getNumFragments());

    // Test insert table
    System.out.println("\n--- Testing insert table ---");
    // Insert 2 more rows (total should be 5)
    InsertTableResponse insertResponse = insertHelper(testCreateTableName, 2, "append", 5);

    // Insert 3 more rows (total should be 8)
    System.out.println("\n--- Testing second insert ---");
    InsertTableResponse secondInsertResponse = insertHelper(testCreateTableName, 3, "append", 8);

    // Test drop table using helper
    System.out.println("\n--- Testing drop table ---");
    DropTableResponse dropResponse = dropTableHelper(testCreateTableName);
    assertNotNull(dropResponse, "Drop table response should not be null");

    // Verify table was dropped
    System.out.println("\n--- Verifying table was dropped ---");
    try {
      DescribeTableRequest verifyDropRequest = new DescribeTableRequest();
      verifyDropRequest.setName(testCreateTableName);
      namespace.describeTable(verifyDropRequest);
      fail("Expected exception when describing dropped table");
    } catch (LanceNamespaceException e) {
      assertEquals(404, e.getCode(), "Should get 404 error code for non-existent table");
      System.out.println("✓ Confirmed table no longer exists (404 error code)");
    }

    System.out.println("\n✓ Table lifecycle test passed!");
  }

  /**
   * Helper method to create a table with the specified number of rows
   *
   * @param tableName The name of the table to create
   * @param numRows The number of rows to create in the table
   * @return CreateTableResponse from the server
   * @throws IOException if there's an error creating the table
   */
  private CreateTableResponse createTableHelper(String tableName, int numRows) throws IOException {
    // Create Arrow schema
    Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
    Field nameField = new Field("name", FieldType.nullable(new ArrowType.Utf8()), null);
    Field embeddingField =
        new Field(
            "embedding",
            FieldType.nullable(new ArrowType.FixedSizeList(128)),
            Arrays.asList(
                new Field(
                    "item",
                    FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)),
                    null)));

    Schema schema = new Schema(Arrays.asList(idField, nameField, embeddingField));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");
      FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("embedding");

      root.setRowCount(numRows);

      // Sample names for test data
      String[] names = {
        "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack"
      };

      // Populate data for specified number of rows
      for (int i = 0; i < numRows; i++) {
        idVector.setSafe(i, i + 1);
        nameVector.setSafe(i, names[i % names.length].getBytes(StandardCharsets.UTF_8));
      }

      // Populate vector field with dummy data
      Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
      vectorVector.allocateNew();

      // Create 128-dimensional vectors for each row
      for (int row = 0; row < numRows; row++) {
        vectorVector.setNotNull(row);
        for (int dim = 0; dim < 128; dim++) {
          int index = row * 128 + dim;
          dataVector.setSafe(index, (float) (Math.random() * 10.0)); // Random values 0-10
        }
      }

      // Mark vectors as populated
      idVector.setValueCount(numRows);
      nameVector.setValueCount(numRows);
      dataVector.setValueCount(numRows * 128); // numRows * 128 dimensions
      vectorVector.setValueCount(numRows);

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      byte[] arrowIpcData = out.toByteArray();
      System.out.println("Arrow IPC data size: " + arrowIpcData.length + " bytes");

      // Create table using Arrow IPC data
      CreateTableResponse response = namespace.createTable(tableName, arrowIpcData);
      System.out.println(
          "✓ Table created successfully: " + tableName + " with " + numRows + " rows");

      return response;
    }
  }

  /**
   * Helper method to drop a table
   *
   * @param tableName The name of the table to drop
   * @return DropTableResponse from the server
   */
  private DropTableResponse dropTableHelper(String tableName) {
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setName(tableName);

    DropTableResponse response = namespace.dropTable(dropRequest);
    System.out.println("✓ Table dropped successfully: " + tableName);

    return response;
  }

  /**
   * Helper method to insert data into a table
   *
   * @param tableName The name of the table to insert into
   * @param numRows The number of rows to insert
   * @param mode The insert mode ("append" or "overwrite")
   * @return InsertTableResponse from the server
   * @throws IOException if there's an error creating the Arrow data
   */
  private InsertTableResponse insertTableHelper(String tableName, int numRows, String mode)
      throws IOException {
    // Create Arrow schema (same as createTableHelper)
    Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
    Field nameField = new Field("name", FieldType.nullable(new ArrowType.Utf8()), null);
    Field vectorField =
        new Field(
            "embedding",
            FieldType.nullable(new ArrowType.FixedSizeList(128)),
            Arrays.asList(
                new Field(
                    "item",
                    FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)),
                    null)));

    Schema schema = new Schema(Arrays.asList(idField, nameField, vectorField));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");
      FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("embedding");

      root.setRowCount(numRows);

      // Sample names for test data
      String[] names = {
        "Liam", "Emma", "Noah", "Olivia", "William", "Ava", "James", "Isabella", "Logan", "Sophia"
      };

      // Populate data for specified number of rows
      // Start IDs from 1000 to differentiate from initial data
      for (int i = 0; i < numRows; i++) {
        idVector.setSafe(i, 1000 + i);
        nameVector.setSafe(i, names[i % names.length].getBytes(StandardCharsets.UTF_8));
      }

      // Populate vector field with dummy data
      Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
      vectorVector.allocateNew();

      // Create 128-dimensional vectors for each row
      for (int row = 0; row < numRows; row++) {
        vectorVector.setNotNull(row);
        for (int dim = 0; dim < 128; dim++) {
          int index = row * 128 + dim;
          dataVector.setSafe(index, (float) (Math.random() * 10.0)); // Random values 0-10
        }
      }

      // Mark vectors as populated
      idVector.setValueCount(numRows);
      nameVector.setValueCount(numRows);
      dataVector.setValueCount(numRows * 128); // numRows * 128 dimensions
      vectorVector.setValueCount(numRows);

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      byte[] arrowIpcData = out.toByteArray();
      System.out.println("Arrow IPC data size for insert: " + arrowIpcData.length + " bytes");

      // Insert data using Arrow IPC data
      InsertTableResponse response = namespace.insertTable(tableName, arrowIpcData, mode);
      System.out.println(
          "✓ Data inserted successfully: "
              + tableName
              + " with "
              + numRows
              + " rows ("
              + mode
              + " mode)");

      return response;
    }
  }

  /**
   * Helper method to insert data and verify the operation
   *
   * @param tableName The name of the table to insert into
   * @param numRows The number of rows to insert
   * @param mode The insert mode ("append" or "overwrite")
   * @param expectedTotalRows The expected total row count after insert
   * @return InsertTableResponse from the server
   * @throws IOException if there's an error creating the Arrow data
   */
  private InsertTableResponse insertHelper(
      String tableName, int numRows, String mode, long expectedTotalRows) throws IOException {
    // Insert data
    InsertTableResponse response = insertTableHelper(tableName, numRows, mode);
    assertNotNull(response, "Insert response should not be null");
    assertNotNull(response.getVersion(), "Insert response version should not be null");
    System.out.println("✓ Inserted " + numRows + " rows, new version: " + response.getVersion());

    // Verify row count
    CountRowsRequest countRequest = new CountRowsRequest();
    countRequest.setName(tableName);
    Long actualCount = namespace.countRows(countRequest);
    assertEquals(
        expectedTotalRows,
        actualCount.longValue(),
        "Row count should be " + expectedTotalRows + " after insert");
    System.out.println("✓ Verified row count: " + actualCount);

    return response;
  }

  @Test
  public void testQueryTable() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");
    String queryTableName =
        "test_query_table_" + UUID.randomUUID().toString().replace("-", "_").substring(0, 8);

    CreateTableResponse createResponse = createTableHelper(queryTableName, 10);
    assertNotNull(createResponse, "Create response should not be null");
    QueryRequest queryRequest = new QueryRequest();
    queryRequest.setName(queryTableName);
    List<Float> queryVector = new ArrayList<>();
    for (int i = 0; i < 128; i++) {
      queryVector.add((float) (i % 10)); // Use pattern 0-9 repeating
    }
    queryRequest.setVector(queryVector);
    queryRequest.setK(5); // Request top 5 results

    List<String> columns = Arrays.asList("id", "name", "embedding");
    queryRequest.setColumns(columns);

    byte[] queryResult;
    queryResult = namespace.queryTable(queryRequest);
    assertNotNull(queryResult, "Query result should not be null");

    // Read and verify the Arrow IPC result
    try (BufferAllocator verifyAllocator = new RootAllocator()) {
      // Arrow file format - use ArrowFileReader
      ByteArraySeekableByteChannel channel = new ByteArraySeekableByteChannel(queryResult);

      try (ArrowFileReader reader = new ArrowFileReader(channel, verifyAllocator)) {

        // Get schema
        Schema resultSchema = reader.getVectorSchemaRoot().getSchema();
        List<String> resultColumns = new ArrayList<>();
        for (Field field : resultSchema.getFields()) {
          resultColumns.add(field.getName());
        }
        System.out.println("Result columns: " + resultColumns);

        // Verify columns
        assertTrue(resultColumns.contains("id"), "Result should contain 'id' column");
        assertTrue(resultColumns.contains("name"), "Result should contain 'name' column");

        // Count total rows
        int totalRows = 0;
        for (int i = 0; i < reader.getRecordBlocks().size(); i++) {
          reader.loadRecordBatch(reader.getRecordBlocks().get(i));
          VectorSchemaRoot root = reader.getVectorSchemaRoot();
          totalRows += root.getRowCount();
        }

        System.out.println("Query returned " + totalRows + " rows");
        assertTrue(totalRows > 0, "Query should return at least some rows");
      }
    }
    DropTableResponse dropResponse = dropTableHelper(queryTableName);
    assertNotNull(dropResponse, "Drop table response should not be null");
  }

  @Test
  public void testAdvancedQueryFeatures() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Advanced Query Features ===");
    String tableName = "test_advanced_query_" + System.currentTimeMillis();

    try {
      // Create table with more rows for better testing
      CreateTableResponse createResponse = createTableHelper(tableName, 100);
      assertNotNull(createResponse, "Create response should not be null");

      // Test 1: Query with filter
      System.out.println("\n--- Test 1: Query with filter ---");
      QueryRequest filterQuery = new QueryRequest();
      filterQuery.setName(tableName);
      filterQuery.setK(10);
      filterQuery.setFilter("id > 50"); // SQL filter
      // Don't set vector for filter-only queries

      byte[] filterResult = namespace.queryTable(filterQuery);
      assertNotNull(filterResult, "Filter query result should not be null");
      verifyQueryResultRowCount(filterResult, 10, "Filter query");

      // Test 2: Query with prefilter
      System.out.println("\n--- Test 4: Query with prefilter ---");
      QueryRequest prefilterQuery = new QueryRequest();
      prefilterQuery.setName(tableName);
      prefilterQuery.setK(5);
      prefilterQuery.setPrefilter(true);
      prefilterQuery.setFilter("id < 20");

      byte[] prefilterResult = namespace.queryTable(prefilterQuery);
      assertNotNull(prefilterResult, "Prefilter query result should not be null");

      // Test 3: Query with fast_search true
      System.out.println("\n--- Test 5: Query with fast_search=true ---");
      QueryRequest fastSearchQuery = new QueryRequest();
      fastSearchQuery.setName(tableName);
      List<Float> fastSearchVector = new ArrayList<>();
      for (int i = 0; i < 128; i++) {
        fastSearchVector.add((float) (i % 10));
      }
      fastSearchQuery.setVector(fastSearchVector);
      fastSearchQuery.setK(10);
      fastSearchQuery.setFastSearch(true);

      byte[] fastSearchResult = namespace.queryTable(fastSearchQuery);
      assertNotNull(fastSearchResult, "Fast search query result should not be null");
      verifyQueryResultRowCount(fastSearchResult, 10, "Fast search query");

      // Test 4: Query with fast_search false
      System.out.println("\n--- Test 6: Query with fast_search=false ---");
      QueryRequest noFastSearchQuery = new QueryRequest();
      noFastSearchQuery.setName(tableName);
      List<Float> noFastSearchVector = new ArrayList<>();
      for (int i = 0; i < 128; i++) {
        noFastSearchVector.add((float) (i % 10));
      }
      noFastSearchQuery.setVector(noFastSearchVector);
      noFastSearchQuery.setK(10);
      noFastSearchQuery.setFastSearch(false);

      byte[] noFastSearchResult = namespace.queryTable(noFastSearchQuery);
      assertNotNull(noFastSearchResult, "No fast search query result should not be null");
      verifyQueryResultRowCount(noFastSearchResult, 10, "No fast search query");

    } finally {
      dropTableHelper(tableName);
    }
  }

  @Test
  public void testDescribeTableWithVersion() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Describe Table With Version ===");
    String tableName = "test_describe_version_" + System.currentTimeMillis();

    try {
      // Create table
      CreateTableResponse createResponse = createTableHelper(tableName, 5);
      assertNotNull(createResponse, "Create response should not be null");

      // Get initial version
      DescribeTableRequest describeV1 = new DescribeTableRequest();
      describeV1.setName(tableName);
      DescribeTableResponse v1Response = namespace.describeTable(describeV1);
      Long version1 = v1Response.getVersion();
      System.out.println("Initial version: " + version1);

      // Insert more data to create new version
      insertTableHelper(tableName, 5, "append");

      // Describe current version
      DescribeTableRequest describeCurrent = new DescribeTableRequest();
      describeCurrent.setName(tableName);
      DescribeTableResponse currentResponse = namespace.describeTable(describeCurrent);
      Long currentVersion = currentResponse.getVersion();
      System.out.println("Current version after insert: " + currentVersion);
      assertTrue(currentVersion > version1, "Version should increase after insert");

      // Describe specific older version
      DescribeTableRequest describeOldVersion = new DescribeTableRequest();
      describeOldVersion.setName(tableName);
      describeOldVersion.setVersion(version1);
      DescribeTableResponse oldVersionResponse = namespace.describeTable(describeOldVersion);

      assertEquals(version1, oldVersionResponse.getVersion(), "Should return requested version");

      // Verify nested structures in response
      assertNotNull(oldVersionResponse.getSchema(), "Schema should not be null");
      assertNotNull(oldVersionResponse.getSchema().getFields(), "Schema fields should not be null");

      // Check JsonField structure
      for (JsonField field : oldVersionResponse.getSchema().getFields()) {
        assertNotNull(field.getName(), "Field name should not be null");
        assertNotNull(field.getType(), "Field type should not be null");
        assertNotNull(field.getNullable(), "Field nullable should not be null");

        // Check JsonDataType structure
        JsonDataType dataType = field.getType();
        assertNotNull(dataType.getType(), "Data type name should not be null");

        // For FixedSizeList (embedding field), check nested fields
        if ("embedding".equals(field.getName())) {
          assertNotNull(dataType.getFields(), "Embedding field should have nested fields");
          assertFalse(dataType.getFields().isEmpty(), "Embedding field should have item field");
        }
      }

      // Verify TableBasicStats structure
      TableBasicStats stats = oldVersionResponse.getStats();
      assertNotNull(stats, "Stats should not be null");
      assertNotNull(stats.getNumDeletedRows(), "Num deleted rows should not be null");
      assertNotNull(stats.getNumFragments(), "Num fragments should not be null");
      assertTrue(stats.getNumFragments() >= 0, "Num fragments should be non-negative");

      System.out.println("✓ Describe table with version tested successfully");

    } finally {
      dropTableHelper(tableName);
    }
  }

  @Test
  public void testFtsQuery() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Full-Text Search Query ===");
    String tableName = "test_fts_query_" + System.currentTimeMillis();

    try {
      // Create table with text data
      CreateTableResponse createResponse = createTextTableHelper(tableName, 50);
      assertNotNull(createResponse, "Create response should not be null");

      // Create FTS index
      System.out.println("\n--- Creating FTS index ---");
      CreateIndexRequest ftsIndexRequest = new CreateIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.FTS);

      CreateIndexResponse ftsIndexResponse = namespace.createIndex(ftsIndexRequest);
      assertNotNull(ftsIndexResponse, "FTS index response should not be null");

      waitForIndexComplete(tableName, "text_idx", 30);

      // Test 1: Simple string FTS query
      System.out.println("\n--- Test 1: Simple string FTS query ---");
      QueryRequest stringFtsQuery = new QueryRequest();
      stringFtsQuery.setName(tableName);
      stringFtsQuery.setK(10);

      StringFtsQuery simpleFts = new StringFtsQuery();
      simpleFts.setQuery("document");
      stringFtsQuery.setFullTextQuery(simpleFts);

      byte[] ftsResult = namespace.queryTable(stringFtsQuery);
      assertNotNull(ftsResult, "FTS query result should not be null");

      // Test 2: FTS with prefilter
      System.out.println("\n--- Test 2: FTS with prefilter ---");
      QueryRequest ftsPrefilterQuery = new QueryRequest();
      ftsPrefilterQuery.setName(tableName);
      ftsPrefilterQuery.setK(5);
      ftsPrefilterQuery.setPrefilter(true);
      ftsPrefilterQuery.setFilter("id < 25");
      ftsPrefilterQuery.setFullTextQuery(simpleFts);

      byte[] ftsPrefilterResult = namespace.queryTable(ftsPrefilterQuery);
      assertNotNull(ftsPrefilterResult, "FTS prefilter query result should not be null");

      System.out.println("✓ FTS query tests completed successfully");

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      fail("Test interrupted");
    } finally {
      dropTableHelper(tableName);
    }
  }

  /** Helper method to create a table with text data for FTS testing */
  private CreateTableResponse createTextTableHelper(String tableName, int numRows)
      throws IOException {
    // Create Arrow schema with text field
    Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
    Field textField = new Field("text", FieldType.nullable(new ArrowType.Utf8()), null);
    Field embeddingField =
        new Field(
            "embedding",
            FieldType.nullable(new ArrowType.FixedSizeList(128)),
            Arrays.asList(
                new Field(
                    "item",
                    FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)),
                    null)));

    Schema schema = new Schema(Arrays.asList(idField, textField, embeddingField));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector textVector = (VarCharVector) root.getVector("text");
      FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("embedding");

      root.setRowCount(numRows);

      // Sample text patterns for FTS
      String[] textTemplates = {
        "This is document number %d",
        "Sample text for entry %d",
        "Test document with id %d",
        "Record %d contains important data",
        "Entry %d in the database"
      };

      // Populate data
      for (int i = 0; i < numRows; i++) {
        idVector.setSafe(i, i + 1);
        String text = String.format(textTemplates[i % textTemplates.length], i);
        textVector.setSafe(i, text.getBytes(StandardCharsets.UTF_8));
      }

      // Populate vector field
      Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
      vectorVector.allocateNew();

      for (int row = 0; row < numRows; row++) {
        vectorVector.setNotNull(row);
        for (int dim = 0; dim < 128; dim++) {
          int index = row * 128 + dim;
          dataVector.setSafe(index, (float) (Math.random() * 10.0));
        }
      }

      idVector.setValueCount(numRows);
      textVector.setValueCount(numRows);
      dataVector.setValueCount(numRows * 128);
      vectorVector.setValueCount(numRows);

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      byte[] arrowIpcData = out.toByteArray();

      CreateTableResponse response = namespace.createTable(tableName, arrowIpcData);
      System.out.println("✓ Text table created: " + tableName + " with " + numRows + " rows");

      return response;
    }
  }

  /** Helper method to wait for index creation */
  private boolean waitForIndex(String tableName, String indexName, int maxSeconds)
      throws InterruptedException {
    IndexListRequest listRequest = new IndexListRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
      IndexListResponse listResponse = namespace.listIndices(listRequest);
      if (listResponse.getIndexes() != null
          && listResponse.getIndexes().stream()
              .anyMatch(idx -> idx.getIndexName().equals(indexName))) {
        return true;
      }
      Thread.sleep(1000);
    }
    return false;
  }

  /** Helper method to wait for index to be fully built with no unindexed rows */
  private boolean waitForIndexComplete(String tableName, String indexName, int maxSeconds)
      throws InterruptedException {
    IndexListRequest listRequest = new IndexListRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
      IndexListResponse listResponse = namespace.listIndices(listRequest);
      if (listResponse.getIndexes() != null) {
        Optional<IndexListItemResponse> indexOpt =
            listResponse.getIndexes().stream()
                .filter(idx -> idx.getIndexName().equals(indexName))
                .findFirst();

        if (indexOpt.isPresent()) {
          // Index exists, now check if it's fully built
          IndexStatsRequest statsRequest = new IndexStatsRequest();
          statsRequest.setName(tableName);

          IndexStatsResponse stats = namespace.getIndexStats(statsRequest, indexName);
          if (stats != null
              && stats.getNumUnindexedRows() != null
              && stats.getNumUnindexedRows() == 0) {
            System.out.println("✓ Index " + indexName + " is fully built with 0 unindexed rows");
            return true;
          } else if (stats != null && stats.getNumUnindexedRows() != null) {
            System.out.println(
                "  Waiting for index... " + stats.getNumUnindexedRows() + " rows remaining");
          }
        }
      }
      Thread.sleep(1000);
    }
    return false;
  }

  /** Helper method to verify query result row count */
  private void verifyQueryResultRowCount(byte[] queryResult, int expectedRows, String testName)
      throws IOException {
    try (BufferAllocator verifyAllocator = new RootAllocator()) {
      ByteArraySeekableByteChannel channel = new ByteArraySeekableByteChannel(queryResult);
      try (ArrowFileReader reader = new ArrowFileReader(channel, verifyAllocator)) {
        int totalRows = 0;
        for (int i = 0; i < reader.getRecordBlocks().size(); i++) {
          reader.loadRecordBatch(reader.getRecordBlocks().get(i));
          VectorSchemaRoot root = reader.getVectorSchemaRoot();
          totalRows += root.getRowCount();
        }
        assertEquals(
            expectedRows, totalRows, testName + " should return " + expectedRows + " rows");
      }
    }
  }

  @Test
  public void testHybridSearch() throws IOException, InterruptedException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Hybrid Search (Vector + Full-Text) ===");
    String tableName = "test_hybrid_search_" + System.currentTimeMillis();

    try {
      // Create table with text and vector data
      CreateTableResponse createResponse = createTextTableHelper(tableName, 50);
      assertNotNull(createResponse, "Create response should not be null");

      // Create FTS index
      System.out.println("\n--- Creating FTS index for hybrid search ---");
      CreateIndexRequest ftsIndexRequest = new CreateIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.FTS);

      CreateIndexResponse ftsIndexResponse = namespace.createIndex(ftsIndexRequest);
      assertNotNull(ftsIndexResponse, "FTS index response should not be null");

      waitForIndexComplete(tableName, "text_idx", 30);

      // Test 1: Hybrid search with vector and text
      System.out.println("\n--- Test 1: Hybrid search with vector and full-text query ---");
      QueryRequest hybridQuery = new QueryRequest();
      hybridQuery.setName(tableName);

      // Add vector search
      List<Float> queryVector = new ArrayList<>();
      for (int i = 0; i < 128; i++) {
        queryVector.add((float) (i % 10));
      }
      hybridQuery.setVector(queryVector);
      hybridQuery.setK(10);

      // Add full-text search
      StringFtsQuery ftsQuery = new StringFtsQuery();
      ftsQuery.setQuery("document");
      hybridQuery.setFullTextQuery(ftsQuery);

      byte[] hybridResult = namespace.queryTable(hybridQuery);
      assertNotNull(hybridResult, "Hybrid search result should not be null");
      System.out.println("✓ Hybrid search completed successfully");

      // Test 2: Hybrid search with filter
      System.out.println("\n--- Test 2: Hybrid search with vector, text, and filter ---");
      QueryRequest hybridFilterQuery = new QueryRequest();
      hybridFilterQuery.setName(tableName);

      // Add vector search
      List<Float> queryVector2 = new ArrayList<>();
      for (int i = 0; i < 128; i++) {
        queryVector2.add((float) (i % 5));
      }
      hybridFilterQuery.setVector(queryVector2);
      hybridFilterQuery.setK(5);

      // Add full-text search
      StringFtsQuery ftsQuery2 = new StringFtsQuery();
      ftsQuery2.setQuery("entry");
      hybridFilterQuery.setFullTextQuery(ftsQuery2);

      // Add filter
      hybridFilterQuery.setFilter("id < 30");

      byte[] hybridFilterResult = namespace.queryTable(hybridFilterQuery);
      assertNotNull(hybridFilterResult, "Hybrid search with filter result should not be null");
      System.out.println("✓ Hybrid search with filter completed successfully");

      // Test 3: Hybrid search with column selection
      System.out.println("\n--- Test 3: Hybrid search with specific columns ---");
      QueryRequest hybridColumnsQuery = new QueryRequest();
      hybridColumnsQuery.setName(tableName);

      // Add vector search
      List<Float> queryVector3 = new ArrayList<>();
      for (int i = 0; i < 128; i++) {
        queryVector3.add((float) Math.random());
      }
      hybridColumnsQuery.setVector(queryVector3);
      hybridColumnsQuery.setK(8);

      // Add full-text search with columns
      StringFtsQuery ftsQuery3 = new StringFtsQuery();
      ftsQuery3.setQuery("test");
      ftsQuery3.setColumns(Arrays.asList("text")); // Search only in text column
      hybridColumnsQuery.setFullTextQuery(ftsQuery3);

      // Return specific columns
      hybridColumnsQuery.setColumns(Arrays.asList("id", "text"));

      byte[] hybridColumnsResult = namespace.queryTable(hybridColumnsQuery);
      assertNotNull(hybridColumnsResult, "Hybrid search with columns result should not be null");
      verifyQueryResultRowCount(hybridColumnsResult, 8, "Hybrid search with columns");

      System.out.println("✓ All hybrid search tests completed successfully");

    } finally {
      dropTableHelper(tableName);
    }
  }

  private LanceRestNamespace initializeClient() {
    Map<String, String> config = new HashMap<>();
    config.put("headers.x-lancedb-database", DATABASE);
    config.put("headers.x-api-key", API_KEY);

    if (HOST_OVERRIDE != null) {
      config.put("host_override", HOST_OVERRIDE);
    }
    if (REGION != null) {
      config.put("region", REGION);
    }

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

  @Test
  public void testCreateIndex() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Create Index ===");
    String indexTableName = "test_index_table_" + System.currentTimeMillis();

    // Step 1: Create table with 300 rows
    System.out.println("\n--- Step 1: Creating table with 300 rows ---");
    CreateTableResponse createResponse = createTableHelper(indexTableName, 300);
    assertNotNull(createResponse, "Create table response should not be null");

    try {
      // Step 2: List indices before creating index (should be empty)
      System.out.println("\n--- Step 2: Listing indices before index creation ---");
      IndexListRequest listRequestBefore = new IndexListRequest();
      listRequestBefore.setName(indexTableName);
      IndexListResponse listResponseBefore = namespace.listIndices(listRequestBefore);
      assertNotNull(listResponseBefore, "List indices response should not be null");
      assertNotNull(listResponseBefore.getIndexes(), "Indexes list should not be null");
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");
      System.out.println("✓ Confirmed no indices exist before creation");

      // Step 3: Create vector index
      System.out.println("\n--- Step 3: Creating vector index ---");
      CreateIndexRequest indexRequest = new CreateIndexRequest();
      indexRequest.setName(indexTableName);
      indexRequest.setColumn("embedding");
      indexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.IVF_PQ);
      indexRequest.setMetricType(CreateIndexRequest.MetricTypeEnum.L2);

      CreateIndexResponse indexResponse = namespace.createIndex(indexRequest);
      assertNotNull(indexResponse, "Create index response should not be null");
      System.out.println("✓ Index creation request submitted successfully");

      // Step 4: Wait for index creation completion (with timeout)
      System.out.println("\n--- Step 4: Waiting for index creation to complete ---");
      IndexListRequest listRequestAfter = new IndexListRequest();
      listRequestAfter.setName(indexTableName);

      boolean indexFound = false;
      int attempts = 0;
      int maxAttempts = 60; // 60 attempts = ~60 seconds with 1 second intervals

      while (!indexFound && attempts < maxAttempts) {
        attempts++;
        try {
          Thread.sleep(1000); // Wait 1 second between attempts

          IndexListResponse listResponseAfter = namespace.listIndices(listRequestAfter);
          assertNotNull(listResponseAfter, "List indices response should not be null");

          if (listResponseAfter.getIndexes() != null && !listResponseAfter.getIndexes().isEmpty()) {
            indexFound = true;
            assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
            assertTrue(
                listResponseAfter.getIndexes().get(0).getIndexName().equals("embedding_idx"));
          } else {
            System.out.println(
                "⏳ Attempt " + attempts + "/" + maxAttempts + " - Index not ready yet...");
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          fail("Test interrupted while waiting for index creation");
        }
      }
      assertTrue(indexFound, "Index should be found after creation");

      // Step 5: Get index stats
      System.out.println("\n--- Step 5: Getting index stats for embedding_idx ---");
      IndexStatsRequest statsRequest = new IndexStatsRequest();
      statsRequest.setName(indexTableName);

      IndexStatsResponse statsResponse = namespace.getIndexStats(statsRequest, "embedding_idx");
      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("IVF_PQ", statsResponse.getIndexType());
      assertEquals("l2", statsResponse.getDistanceType());
      assertEquals(300, statsResponse.getNumIndexedRows().intValue());
      assertEquals(0, statsResponse.getNumUnindexedRows().intValue());
    } finally {
      DropTableResponse dropResponse = dropTableHelper(indexTableName);
    }
  }

  @Test
  public void testCreateScalarIndex() throws IOException, InterruptedException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Create Scalar Index ===");
    String scalarIndexTableName = "test_scalar_index_table_" + System.currentTimeMillis();

    try {
      // Step 1: Create table with 300 rows
      System.out.println("\n--- Step 1: Creating table with 300 rows ---");
      CreateTableResponse createResponse = createTableHelper(scalarIndexTableName, 300);
      assertNotNull(createResponse, "Create table response should not be null");

      // Step 2: List indices before creating index (should be empty)
      System.out.println("\n--- Step 2: Listing indices before index creation ---");
      IndexListRequest listRequestBefore = new IndexListRequest();
      listRequestBefore.setName(scalarIndexTableName);
      IndexListResponse listResponseBefore = namespace.listIndices(listRequestBefore);
      assertNotNull(listResponseBefore, "List indices response should not be null");
      assertNotNull(listResponseBefore.getIndexes(), "Indexes list should not be null");
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");
      System.out.println("✓ Confirmed no indices exist before creation");

      // Step 3: Create scalar index on name column
      System.out.println("\n--- Step 3: Creating scalar index ---");
      CreateIndexRequest scalarIndexRequest = new CreateIndexRequest();
      scalarIndexRequest.setName(scalarIndexTableName);
      scalarIndexRequest.setColumn("name");
      scalarIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.BITMAP);

      CreateIndexResponse scalarIndexResponse = namespace.createScalarIndex(scalarIndexRequest);
      assertNotNull(scalarIndexResponse, "Create scalar index response should not be null");

      // Step 4: Wait for index creation completion (with timeout)
      System.out.println("\n--- Step 4: Waiting for scalar index creation to complete ---");
      IndexListRequest listRequestAfter = new IndexListRequest();
      listRequestAfter.setName(scalarIndexTableName);

      boolean indexFound = false;
      int attempts = 0;
      int maxAttempts = 60; // 60 attempts = ~60 seconds with 1 second intervals

      while (!indexFound && attempts < maxAttempts) {
        attempts++;
        try {
          Thread.sleep(1000); // Wait 1 second between attempts

          IndexListResponse listResponseAfter = namespace.listIndices(listRequestAfter);
          assertNotNull(listResponseAfter, "List indices response should not be null");

          if (listResponseAfter.getIndexes() != null && !listResponseAfter.getIndexes().isEmpty()) {
            indexFound = true;
            assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
            assertTrue(
                listResponseAfter.getIndexes().get(0).getColumns().contains("name"),
                "Index should be on name column");
          } else {
            System.out.println(
                "⏳ Attempt " + attempts + "/" + maxAttempts + " - Scalar index not ready yet...");
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          fail("Test interrupted while waiting for scalar index creation");
        }
      }

      assertTrue(indexFound, "Index should be found after creation");

      IndexStatsRequest statsRequest = new IndexStatsRequest();
      statsRequest.setName(scalarIndexTableName);

      IndexStatsResponse statsResponse = namespace.getIndexStats(statsRequest, "name_idx");
      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("BITMAP", statsResponse.getIndexType());
      assertEquals(300, statsResponse.getNumIndexedRows().intValue());
      assertEquals(0, statsResponse.getNumUnindexedRows().intValue());
    } finally {
      DropTableResponse dropResponse = dropTableHelper(scalarIndexTableName);
    }
  }

  @Test
  public void testUpdateTable() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Update Table ===");
    String updateTableName = "test_update_table_" + System.currentTimeMillis();

    try {
      CreateTableResponse createResponse = createTableHelper(updateTableName, 3);
      assertNotNull(createResponse, "Create table response should not be null");
      // Id: 1 2 3
      System.out.println("\n--- Step 2: Updating all rows (id = id + 1) ---");
      UpdateTableRequest updateRequest = new UpdateTableRequest();
      updateRequest.setName(updateTableName);
      updateRequest.setNamespace(new ArrayList<>());
      List<List<String>> updates = new ArrayList<>();
      updates.add(Arrays.asList("id", "id + 1"));
      updateRequest.setUpdates(updates);
      // Id: 2 3 4
      UpdateTableResponse updateResponse = namespace.updateTable(updateRequest);
      assertNotNull(updateResponse, "Update response should not be null");
      assertEquals(3, updateResponse.getUpdatedRows().longValue(), "Should have updated 3 rows");

      System.out.println("\n--- Step 3: Updating rows with predicate (id > 2) ---");
      UpdateTableRequest predicateUpdateRequest = new UpdateTableRequest();
      predicateUpdateRequest.setName(updateTableName);
      predicateUpdateRequest.setNamespace(new ArrayList<>());
      predicateUpdateRequest.setPredicate("id > 2");
      List<List<String>> predicateUpdates = new ArrayList<>();
      predicateUpdates.add(Arrays.asList("id", "id + 10"));
      predicateUpdateRequest.setUpdates(predicateUpdates);
      // Id: 2 13 14

      UpdateTableResponse predicateUpdateResponse = namespace.updateTable(predicateUpdateRequest);
      assertNotNull(predicateUpdateResponse, "Predicate update response should not be null");
      assertEquals(
          2, predicateUpdateResponse.getUpdatedRows().longValue(), "Should have updated 2 rows");

      // Use query to verify final state
      QueryRequest queryRequest = new QueryRequest();
      queryRequest.setName(updateTableName);
      queryRequest.setK(3);
      // Don't set columns or vector for simple queries

      byte[] queryResult;
      queryResult = namespace.queryTable(queryRequest);
      assertNotNull(queryResult, "Query result should not be null");

      try (BufferAllocator verifyAllocator = new RootAllocator()) {
        ByteArraySeekableByteChannel channel = new ByteArraySeekableByteChannel(queryResult);

        try (ArrowFileReader reader = new ArrowFileReader(channel, verifyAllocator)) {
          List<Integer> idValues = new ArrayList<>();
          for (int i = 0; i < reader.getRecordBlocks().size(); i++) {
            reader.loadRecordBatch(reader.getRecordBlocks().get(i));
            VectorSchemaRoot root = reader.getVectorSchemaRoot();
            IntVector idVector = (IntVector) root.getVector("id");

            // Extract ID values from this batch
            for (int j = 0; j < root.getRowCount(); j++) {
              if (!idVector.isNull(j)) {
                idValues.add(idVector.get(j));
              }
            }
          }

          Collections.sort(idValues);
          assertEquals(3, idValues.size(), "Should have exactly 3 rows");
          assertEquals(
              Arrays.asList(2, 13, 14), idValues, "ID values should be [2, 13, 14] after updates");
        }
      }
    } finally {
      DropTableResponse dropResponse = dropTableHelper(updateTableName);
    }
  }

  @Test
  public void testDeleteFromTable() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Delete From Table ===");
    String deleteTableName = "test_delete_table_" + System.currentTimeMillis();

    try {
      // Step 1: Create table with 3 rows
      System.out.println("\n--- Step 1: Creating table with 3 rows ---");
      CreateTableResponse createResponse = createTableHelper(deleteTableName, 3);
      assertNotNull(createResponse, "Create table response should not be null");

      System.out.println("\n--- Step 2: Deleting row with id=1 ---");
      DeleteFromTableRequest deleteRequest = new DeleteFromTableRequest();
      deleteRequest.setName(deleteTableName);
      deleteRequest.setPredicate("id = 1");

      DeleteFromTableResponse deleteResponse = namespace.deleteFromTable(deleteRequest);
      // Note that server didn't send back valid deletedRows info
      assertEquals(2, deleteResponse.getVersion(), "Version should increment");

      CountRowsRequest countRequest = new CountRowsRequest();
      countRequest.setName(deleteTableName);
      Long countResponse = namespace.countRows(countRequest);
      assertEquals(2, countResponse.longValue());

      System.out.println("\n--- Step 5: Deleting with complex predicate (id > 2) ---");
      DeleteFromTableRequest complexDeleteRequest = new DeleteFromTableRequest();
      complexDeleteRequest.setName(deleteTableName);
      complexDeleteRequest.setPredicate("id > 2");

      DeleteFromTableResponse complexDeleteResponse =
          namespace.deleteFromTable(complexDeleteRequest);
      assertEquals(3, complexDeleteResponse.getVersion(), "Version should increment");
      countResponse = namespace.countRows(countRequest);
      assertEquals(1, countResponse.longValue());
    } finally {
      DropTableResponse dropResponse = dropTableHelper(deleteTableName);
    }
  }

  @Test
  public void testMergeInsert() throws IOException {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");

    System.out.println("\n=== Test: Merge Insert (Upsert) ===");
    String mergeTableName = "test_merge_table_" + System.currentTimeMillis();

    try {
      // Step 1: Create table with 3 rows
      System.out.println("\n--- Step 1: Creating table with 3 rows ---");
      CreateTableResponse createResponse = createTableHelper(mergeTableName, 3);
      assertNotNull(createResponse, "Create table response should not be null");

      // Verify initial data
      CountRowsRequest countRequest = new CountRowsRequest();
      countRequest.setName(mergeTableName);
      Long initialCount = namespace.countRows(countRequest);
      assertEquals(3, initialCount.longValue(), "Initial row count should be 3");

      // Step 2: Merge insert with some matching and some new rows
      System.out.println("\n--- Step 2: Merge insert with mixed rows ---");
      // Create batch with rows: id=2,3,4 (2,3 exist, 4 is new)
      String[] names = {"Bob Updated", "Charlie Updated", "David"};
      Float4Vector[] vectors = new Float4Vector[3];

      Schema schema =
          new Schema(
              Arrays.asList(
                  new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                  new Field("name", FieldType.nullable(new ArrowType.Utf8()), null),
                  new Field(
                      "embedding",
                      FieldType.nullable(new ArrowType.FixedSizeList(128)),
                      Arrays.asList(
                          new Field(
                              "item",
                              FieldType.nullable(
                                  new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)),
                              null)))));

      try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
        IntVector idVector = (IntVector) root.getVector("id");
        VarCharVector nameVector = (VarCharVector) root.getVector("name");
        FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("embedding");

        root.setRowCount(3);

        // Set data for merge
        int[] ids = {2, 3, 4};
        for (int i = 0; i < 3; i++) {
          idVector.setSafe(i, ids[i]);
          nameVector.setSafe(i, names[i].getBytes(StandardCharsets.UTF_8));
        }

        // Populate vector field
        Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
        vectorVector.allocateNew();

        for (int row = 0; row < 3; row++) {
          vectorVector.setNotNull(row);
          for (int dim = 0; dim < 128; dim++) {
            int index = row * 128 + dim;
            dataVector.setSafe(index, (float) ((row + 2) * 10.0 + dim * 0.1));
          }
        }

        idVector.setValueCount(3);
        nameVector.setValueCount(3);
        dataVector.setValueCount(3 * 128);
        vectorVector.setValueCount(3);

        // Serialize to Arrow IPC
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ArrowStreamWriter writer =
            new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
          writer.start();
          writer.writeBatch();
          writer.end();
        }

        byte[] arrowIpcData = out.toByteArray();

        // Perform merge insert
        MergeInsertTableRequest mergeRequest = new MergeInsertTableRequest();
        mergeRequest.setName(mergeTableName);

        MergeInsertTableResponse mergeResponse =
            namespace.mergeInsertTable(
                mergeRequest,
                arrowIpcData,
                "id", // match on id column
                true, // when_matched_update_all
                true // when_not_matched_insert_all
                );

        assertNotNull(mergeResponse, "Merge response should not be null");
        assertEquals(
            2, mergeResponse.getNumUpdatedRows().longValue(), "Should have updated 2 rows");
        assertEquals(
            1, mergeResponse.getNumInsertedRows().longValue(), "Should have inserted 1 row");
        System.out.println(
            "✓ Merge insert completed: "
                + mergeResponse.getNumUpdatedRows()
                + " updated, "
                + mergeResponse.getNumInsertedRows()
                + " inserted");

        // Verify final count
        Long finalCount = namespace.countRows(countRequest);
        assertEquals(4, finalCount.longValue(), "Final row count should be 4");

        // Step 3: Verify the updates by querying the table
        System.out.println("\n--- Step 3: Verifying merged data ---");
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setName(mergeTableName);
        queryRequest.setK(4);
        // Don't set columns or vector for simple queries

        byte[] queryResult = namespace.queryTable(queryRequest);
        assertNotNull(queryResult, "Query result should not be null");

        // Read and verify the results
        try (BufferAllocator verifyAllocator = new RootAllocator()) {
          ByteArraySeekableByteChannel channel = new ByteArraySeekableByteChannel(queryResult);

          try (ArrowFileReader reader = new ArrowFileReader(channel, verifyAllocator)) {
            Map<Integer, String> idToName = new HashMap<>();

            for (int i = 0; i < reader.getRecordBlocks().size(); i++) {
              reader.loadRecordBatch(reader.getRecordBlocks().get(i));
              VectorSchemaRoot resultRoot = reader.getVectorSchemaRoot();
              IntVector resultIdVector = (IntVector) resultRoot.getVector("id");
              VarCharVector resultNameVector = (VarCharVector) resultRoot.getVector("name");

              for (int j = 0; j < resultRoot.getRowCount(); j++) {
                if (!resultIdVector.isNull(j)) {
                  int id = resultIdVector.get(j);
                  String name = new String(resultNameVector.get(j), StandardCharsets.UTF_8);
                  idToName.put(id, name);
                }
              }
            }

            // Verify the merged data
            assertEquals(4, idToName.size(), "Should have 4 rows total");
            assertEquals("Alice", idToName.get(1), "ID 1 should remain unchanged");
            assertEquals("Bob Updated", idToName.get(2), "ID 2 should be updated");
            assertEquals("Charlie Updated", idToName.get(3), "ID 3 should be updated");
            assertEquals("David", idToName.get(4), "ID 4 should be new");
            System.out.println("✓ Merge insert data verified successfully");
          }
        }
      }
    } finally {
      DropTableResponse dropResponse = dropTableHelper(mergeTableName);
    }
  }

  /** SeekableByteChannel implementation for reading Arrow file format from byte array */
  private static class ByteArraySeekableByteChannel implements SeekableByteChannel {
    private final byte[] data;
    private long position = 0;
    private boolean isOpen = true;

    public ByteArraySeekableByteChannel(byte[] data) {
      this.data = data;
    }

    @Override
    public long position() throws IOException {
      return position;
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
      if (newPosition < 0 || newPosition > data.length) {
        throw new IOException("Invalid position: " + newPosition);
      }
      position = newPosition;
      return this;
    }

    @Override
    public long size() throws IOException {
      return data.length;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
      if (!isOpen) {
        throw new IOException("Channel is closed");
      }
      int remaining = dst.remaining();
      int available = (int) (data.length - position);
      if (available <= 0) {
        return -1;
      }
      int toRead = Math.min(remaining, available);
      dst.put(data, (int) position, toRead);
      position += toRead;
      return toRead;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      throw new UnsupportedOperationException("Read-only channel");
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
      throw new UnsupportedOperationException("Read-only channel");
    }

    @Override
    public boolean isOpen() {
      return isOpen;
    }

    @Override
    public void close() throws IOException {
      isOpen = false;
    }
  }
}
