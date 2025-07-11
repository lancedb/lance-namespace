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
import java.util.HashMap;
import java.util.List;
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
    describeRequest.setWithTableUri(true);

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
      scalarIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.BTREE);

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
    } finally {
      DropTableResponse dropResponse = dropTableHelper(scalarIndexTableName);
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
