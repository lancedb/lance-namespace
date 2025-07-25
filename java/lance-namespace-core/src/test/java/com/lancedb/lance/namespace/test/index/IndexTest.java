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
package com.lancedb.lance.namespace.test.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.model.IndexListItemResponse;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.test.BaseNamespaceTest;
import com.lancedb.lance.namespace.test.utils.ArrowTestUtils;
import com.lancedb.lance.namespace.test.utils.TestUtils;

/** Tests for index operations: create, list, stats. */
public class IndexTest extends BaseNamespaceTest {

  @Test
  public void testCreateVectorIndex() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Create Vector Index ===");
    String tableName = TestUtils.generateTableName("test_vector_index");

    try {
      // Step 1: Create table with 300 rows
      System.out.println("\n--- Step 1: Creating table with 300 rows ---");
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 300).build();
      CreateTableRequest createRequest = new CreateTableRequest();
      createRequest.setName(tableName);
      // Location is required by interface, but not used by server, put in random value
      createRequest.setLocation("random value");
      CreateTableResponse createResponse = namespace.createTable(createRequest, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      // Step 2: List indices before creating index (should be empty)
      System.out.println("\n--- Step 2: Listing indices before index creation ---");
      ListTableIndicesRequest listRequestBefore = new ListTableIndicesRequest();
      listRequestBefore.setName(tableName);
      ListTableIndicesResponse listResponseBefore = namespace.listTableIndices(listRequestBefore);
      assertNotNull(listResponseBefore, "List indices response should not be null");
      assertNotNull(listResponseBefore.getIndexes(), "Indexes list should not be null");
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");
      System.out.println("✓ Confirmed no indices exist before creation");

      // Step 3: Create vector index
      System.out.println("\n--- Step 3: Creating vector index ---");
      CreateTableIndexRequest indexRequest = new CreateTableIndexRequest();
      indexRequest.setName(tableName);
      indexRequest.setColumn("embedding");
      indexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      indexRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.L2);

      CreateTableIndexResponse indexResponse = namespace.createTableIndex(indexRequest);
      assertNotNull(indexResponse, "Create index response should not be null");
      System.out.println("✓ Index creation request submitted successfully");

      // Step 4: Wait for index creation completion
      System.out.println("\n--- Step 4: Waiting for index creation to complete ---");
      boolean indexFound = TestUtils.waitForIndex(namespace, tableName, "embedding_idx", 60);
      assertTrue(indexFound, "Index should be found after creation");
      System.out.println("✓ Index created successfully");

      // Step 5: Get index stats
      System.out.println("\n--- Step 5: Getting index stats for embedding_idx ---");
      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setName(tableName);

      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");
      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("IVF_PQ", statsResponse.getIndexType(), "Index type should be IVF_PQ");
      assertEquals("l2", statsResponse.getDistanceType(), "Distance type should be l2");

      // Wait for index to be fully built
      boolean indexComplete =
          TestUtils.waitForIndexComplete(namespace, tableName, "embedding_idx", 30);
      assertTrue(indexComplete, "Index should be fully built");

      // Verify final stats
      DescribeTableIndexStatsResponse finalStats =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");
      assertEquals(300, finalStats.getNumIndexedRows().intValue(), "Should have 300 indexed rows");
      assertEquals(0, finalStats.getNumUnindexedRows().intValue(), "Should have 0 unindexed rows");

      System.out.println("✓ Index stats verified:");
      System.out.println("  - Type: " + finalStats.getIndexType());
      System.out.println("  - Distance: " + finalStats.getDistanceType());
      System.out.println("  - Indexed rows: " + finalStats.getNumIndexedRows());
      System.out.println("  - Unindexed rows: " + finalStats.getNumUnindexedRows());

      // Verify index appears in list
      ListTableIndicesRequest listRequestAfter = new ListTableIndicesRequest();
      listRequestAfter.setName(tableName);
      ListTableIndicesResponse listResponseAfter = namespace.listTableIndices(listRequestAfter);
      assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
      assertEquals(
          "embedding_idx",
          listResponseAfter.getIndexes().get(0).getIndexName(),
          "Index name should be embedding_idx");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testCreateScalarIndex() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Create Scalar Index ===");
    String tableName = TestUtils.generateTableName("test_scalar_index");

    try {
      // Step 1: Create table with 300 rows
      System.out.println("\n--- Step 1: Creating table with 300 rows ---");
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 300).build();
      CreateTableRequest createRequest = new CreateTableRequest();
      createRequest.setName(tableName);
      // Location is required
      createRequest.setLocation("random value");
      CreateTableResponse createResponse = namespace.createTable(createRequest, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      // Step 2: List indices before creating index
      System.out.println("\n--- Step 2: Listing indices before index creation ---");
      ListTableIndicesRequest listRequestBefore = new ListTableIndicesRequest();
      listRequestBefore.setName(tableName);
      ListTableIndicesResponse listResponseBefore = namespace.listTableIndices(listRequestBefore);
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");

      // Step 3: Create scalar index on name column
      System.out.println("\n--- Step 3: Creating scalar index ---");
      CreateTableIndexRequest scalarIndexRequest = new CreateTableIndexRequest();
      scalarIndexRequest.setName(tableName);
      scalarIndexRequest.setColumn("name");
      scalarIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.BITMAP);

      CreateTableIndexResponse scalarIndexResponse = namespace.createTableIndex(scalarIndexRequest);
      assertNotNull(scalarIndexResponse, "Create scalar index response should not be null");
      System.out.println("✓ Scalar index creation request submitted");

      // Step 4: Wait for index creation completion
      System.out.println("\n--- Step 4: Waiting for scalar index creation to complete ---");
      boolean indexFound = TestUtils.waitForIndex(namespace, tableName, "name_idx", 60);
      assertTrue(indexFound, "Scalar index should be found after creation");

      // Step 5: Verify scalar index stats
      System.out.println("\n--- Step 5: Getting scalar index stats ---");
      boolean indexComplete = TestUtils.waitForIndexComplete(namespace, tableName, "name_idx", 30);
      assertTrue(indexComplete, "Scalar index should be fully built");

      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setName(tableName);
      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "name_idx");

      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("BITMAP", statsResponse.getIndexType(), "Index type should be BITMAP");
      assertEquals(
          300, statsResponse.getNumIndexedRows().intValue(), "Should have 300 indexed rows");
      assertEquals(
          0, statsResponse.getNumUnindexedRows().intValue(), "Should have 0 unindexed rows");

      System.out.println("✓ Scalar index stats verified:");
      System.out.println("  - Type: " + statsResponse.getIndexType());
      System.out.println("  - Indexed rows: " + statsResponse.getNumIndexedRows());

      // Verify index in list
      ListTableIndicesRequest listRequestAfter = new ListTableIndicesRequest();
      listRequestAfter.setName(tableName);
      ListTableIndicesResponse listResponseAfter = namespace.listTableIndices(listRequestAfter);
      assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
      assertTrue(
          listResponseAfter.getIndexes().get(0).getColumns().contains("name"),
          "Index should be on name column");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testMultipleIndices() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Multiple Indices ===");
    String tableName = TestUtils.generateTableName("test_multiple_indices");

    try {
      // Create table with text field
      System.out.println("\n--- Creating table with multiple fields ---");
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 300).build();
      CreateTableRequest createRequest2 = new CreateTableRequest();
      createRequest2.setName(tableName);
      // Location is required by interface, but not used by server, put in random value
      createRequest2.setLocation("random value");
      namespace.createTable(createRequest2, tableData);

      // Create vector index
      System.out.println("\n--- Creating vector index ---");
      CreateTableIndexRequest vectorIndexRequest = new CreateTableIndexRequest();
      vectorIndexRequest.setName(tableName);
      vectorIndexRequest.setColumn("embedding");
      vectorIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      vectorIndexRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.COSINE);
      namespace.createTableIndex(vectorIndexRequest);

      // Create scalar index
      System.out.println("\n--- Creating scalar index ---");
      CreateTableIndexRequest scalarIndexRequest = new CreateTableIndexRequest();
      scalarIndexRequest.setName(tableName);
      scalarIndexRequest.setColumn("id");
      scalarIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.BTREE);
      namespace.createTableIndex(scalarIndexRequest);

      // Create FTS index
      System.out.println("\n--- Creating FTS index ---");
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("name");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      namespace.createTableIndex(ftsIndexRequest);

      // Wait for all indices
      System.out.println("\n--- Waiting for all indices to be created ---");
      Thread.sleep(5000); // Give some time for all indices to appear

      // List all indices
      ListTableIndicesRequest listRequest = new ListTableIndicesRequest();
      listRequest.setName(tableName);
      ListTableIndicesResponse listResponse = namespace.listTableIndices(listRequest);

      assertNotNull(listResponse.getIndexes(), "Indexes list should not be null");
      assertTrue(listResponse.getIndexes().size() >= 3, "Should have at least 3 indices");

      System.out.println("✓ Created " + listResponse.getIndexes().size() + " indices:");
      for (IndexListItemResponse index : listResponse.getIndexes()) {
        System.out.println("  - " + index.getIndexName() + " on columns: " + index.getColumns());
      }

      // Verify each index type
      boolean hasVectorIndex =
          listResponse.getIndexes().stream()
              .anyMatch(idx -> idx.getIndexName().equals("embedding_idx"));
      boolean hasScalarIndex =
          listResponse.getIndexes().stream().anyMatch(idx -> idx.getIndexName().equals("id_idx"));
      boolean hasFtsIndex =
          listResponse.getIndexes().stream().anyMatch(idx -> idx.getIndexName().equals("name_idx"));

      assertTrue(hasVectorIndex, "Should have vector index");
      assertTrue(hasScalarIndex, "Should have scalar index");
      assertTrue(hasFtsIndex, "Should have FTS index");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testIndexWithDifferentMetrics() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Index with Different Metrics ===");
    String tableName = TestUtils.generateTableName("test_index_metrics");

    try {
      // Create table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 300).build();
      CreateTableRequest createRequest2 = new CreateTableRequest();
      createRequest2.setName(tableName);
      // Location is required by interface, but not used by server, put in random value
      createRequest2.setLocation("random value");
      namespace.createTable(createRequest2, tableData);

      // Test COSINE metric
      System.out.println("\n--- Creating index with COSINE metric ---");
      CreateTableIndexRequest cosineRequest = new CreateTableIndexRequest();
      cosineRequest.setName(tableName);
      cosineRequest.setColumn("embedding");
      cosineRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      cosineRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.COSINE);

      CreateTableIndexResponse cosineResponse = namespace.createTableIndex(cosineRequest);
      assertNotNull(cosineResponse, "Create index response should not be null");

      // Wait and verify
      TestUtils.waitForIndexComplete(namespace, tableName, "embedding_idx", 30);

      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setName(tableName);
      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");

      assertEquals("cosine", statsResponse.getDistanceType(), "Distance type should be cosine");
      System.out.println("✓ Created index with COSINE metric");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }
}
