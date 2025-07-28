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

import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.model.IndexListItemResponse;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Long running and highly likely to timeout")
/** Tests for index operations: create, list, stats. */
public class TestIndex extends LanceDbRestNamespaceTestBase {
  private static final Logger log = LoggerFactory.getLogger(TestIndex.class);

  @Test
  public void testCreateVectorIndex() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Create Vector Index ===");
    String tableName = Utils.generateTableName("test_vector_index");

    try {
      // Step 1: Create table with 300 rows
      log.info("--- Step 1: Creating table {} with 300 rows ---", tableName);
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 300);
      assertNotNull(createResponse, "Create table response should not be null");

      // Step 2: List indices before creating index (should be empty)
      log.info("--- Step 2: Listing indices before index creation ---");
      ListTableIndicesRequest listRequestBefore = new ListTableIndicesRequest();
      listRequestBefore.setId(Lists.newArrayList(tableName));
      ListTableIndicesResponse listResponseBefore = namespace.listTableIndices(listRequestBefore);
      assertNotNull(listResponseBefore, "List indices response should not be null");
      assertNotNull(listResponseBefore.getIndexes(), "Indexes list should not be null");
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");
      log.info("✓ Confirmed no indices exist before creation");

      // Step 3: Create vector index
      log.info("--- Step 3: Creating vector index ---");
      CreateTableIndexRequest indexRequest = new CreateTableIndexRequest();
      indexRequest.setId(Lists.newArrayList(tableName));
      indexRequest.setColumn("embedding");
      indexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      indexRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.L2);

      CreateTableIndexResponse indexResponse = namespace.createTableIndex(indexRequest);
      assertNotNull(indexResponse, "Create index response should not be null");
      log.info("✓ Index creation request submitted successfully");

      // Step 4: Wait for index creation completion
      log.info("--- Step 4: Waiting for index creation to complete ---");
      boolean indexFound = Utils.waitForIndexComplete(namespace, tableName, "embedding_idx", 180);
      assertTrue(indexFound, "Index should be found after creation");
      log.info("✓ Index created successfully");

      // Step 5: Get index stats
      log.info("--- Step 5: Getting index stats for embedding_idx ---");
      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setId(Lists.newArrayList(tableName));

      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");
      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("IVF_PQ", statsResponse.getIndexType(), "Index type should be IVF_PQ");
      assertEquals("l2", statsResponse.getDistanceType(), "Distance type should be l2");

      // Wait for index to be fully built
      boolean indexComplete =
          Utils.waitForIndexComplete(namespace, tableName, "embedding_idx", 180);
      assertTrue(indexComplete, "Index should be fully built");

      // Verify final stats
      DescribeTableIndexStatsResponse finalStats =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");
      assertEquals(300, finalStats.getNumIndexedRows().intValue(), "Should have 300 indexed rows");
      assertEquals(0, finalStats.getNumUnindexedRows().intValue(), "Should have 0 unindexed rows");

      log.info("✓ Index stats verified:");
      log.info("  - Type: {}", finalStats.getIndexType());
      log.info("  - Distance: {}", finalStats.getDistanceType());
      log.info("  - Indexed rows: {}", finalStats.getNumIndexedRows());
      log.info("  - Unindexed rows: {}", finalStats.getNumUnindexedRows());

      // Verify index appears in list
      ListTableIndicesRequest listRequestAfter = new ListTableIndicesRequest();
      listRequestAfter.setId(Lists.newArrayList(tableName));
      ListTableIndicesResponse listResponseAfter = namespace.listTableIndices(listRequestAfter);
      assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
      assertEquals(
          "embedding_idx",
          listResponseAfter.getIndexes().get(0).getIndexName(),
          "Index name should be embedding_idx");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testCreateScalarIndex() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Create Scalar Index ===");
    String tableName = Utils.generateTableName("test_scalar_index");

    try {
      // Step 1: Create table with 300 rows
      log.info("--- Step 1: Creating table {} with 300 rows ---", tableName);
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 300);
      assertNotNull(createResponse, "Create table response should not be null");

      // Step 2: List indices before creating index
      log.info("--- Step 2: Listing indices before index creation ---");
      ListTableIndicesRequest listRequestBefore = new ListTableIndicesRequest();
      listRequestBefore.setId(Lists.newArrayList(tableName));
      ListTableIndicesResponse listResponseBefore = namespace.listTableIndices(listRequestBefore);
      assertEquals(
          0, listResponseBefore.getIndexes().size(), "Should have no indices before creation");

      // Step 3: Create scalar index on name column
      log.info("--- Step 3: Creating scalar index ---");
      CreateTableIndexRequest scalarIndexRequest = new CreateTableIndexRequest();
      scalarIndexRequest.setId(Lists.newArrayList(tableName));
      scalarIndexRequest.setColumn("name");
      scalarIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.BITMAP);

      CreateTableIndexResponse scalarIndexResponse = namespace.createTableIndex(scalarIndexRequest);
      assertNotNull(scalarIndexResponse, "Create scalar index response should not be null");
      log.info("✓ Scalar index creation request submitted");

      // Step 4: Wait for index creation completion
      log.info("--- Step 4: Waiting for scalar index creation to complete ---");
      boolean indexFound = Utils.waitForIndexComplete(namespace, tableName, "name_idx", 180);
      assertTrue(indexFound, "Scalar index should be found after creation");

      // Step 5: Verify scalar index stats
      log.info("--- Step 5: Getting scalar index stats ---");
      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setId(Lists.newArrayList(tableName));
      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "name_idx");

      assertNotNull(statsResponse, "Index stats response should not be null");
      assertEquals("BITMAP", statsResponse.getIndexType(), "Index type should be BITMAP");
      assertEquals(
          300, statsResponse.getNumIndexedRows().intValue(), "Should have 300 indexed rows");
      assertEquals(
          0, statsResponse.getNumUnindexedRows().intValue(), "Should have 0 unindexed rows");

      log.info("✓ Scalar index stats verified:");
      log.info("  - Type: {}", statsResponse.getIndexType());
      log.info("  - Indexed rows: {}", statsResponse.getNumIndexedRows());

      // Verify index in list
      ListTableIndicesRequest listRequestAfter = new ListTableIndicesRequest();
      listRequestAfter.setId(Lists.newArrayList(tableName));
      ListTableIndicesResponse listResponseAfter = namespace.listTableIndices(listRequestAfter);
      assertEquals(1, listResponseAfter.getIndexes().size(), "Should have exactly one index");
      assertTrue(
          listResponseAfter.getIndexes().get(0).getColumns().contains("name"),
          "Index should be on name column");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testMultipleIndices() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Multiple Indices ===");
    String tableName = Utils.generateTableName("test_multiple_indices");

    try {
      // Create table with text field
      log.info("--- Creating table {} with multiple fields ---", tableName);
      Utils.createTable(namespace, allocator, tableName, 300);

      // Create vector index
      log.info("--- Creating vector index ---");
      CreateTableIndexRequest vectorIndexRequest = new CreateTableIndexRequest();
      vectorIndexRequest.setId(Lists.newArrayList(tableName));
      vectorIndexRequest.setColumn("embedding");
      vectorIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      vectorIndexRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.COSINE);
      namespace.createTableIndex(vectorIndexRequest);

      // Wait for vector index completion (3 minutes timeout)
      log.info("--- Waiting for vector index completion ---");
      boolean vectorIndexComplete =
          Utils.waitForIndexComplete(namespace, tableName, "embedding_idx", 180);
      assertTrue(vectorIndexComplete, "Vector index should be fully built within 3 minutes");
      log.info("✓ Vector index completed");

      // Create scalar index
      log.info("--- Creating scalar index ---");
      CreateTableIndexRequest scalarIndexRequest = new CreateTableIndexRequest();
      scalarIndexRequest.setId(Lists.newArrayList(tableName));
      scalarIndexRequest.setColumn("id");
      scalarIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.BTREE);
      namespace.createTableIndex(scalarIndexRequest);

      // Wait for scalar index completion (3 minutes timeout)
      log.info("--- Waiting for scalar index completion ---");
      boolean scalarIndexComplete = Utils.waitForIndexComplete(namespace, tableName, "id_idx", 180);
      assertTrue(scalarIndexComplete, "Scalar index should be fully built within 3 minutes");
      log.info("✓ Scalar index completed");

      // Create FTS index
      log.info("--- Creating FTS index ---");
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setId(Lists.newArrayList(tableName));
      ftsIndexRequest.setColumn("name");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      namespace.createTableIndex(ftsIndexRequest);

      // Wait for FTS index completion (3 minutes timeout)
      log.info("--- Waiting for FTS index completion ---");
      boolean ftsIndexComplete = Utils.waitForIndexComplete(namespace, tableName, "name_idx", 180);
      assertTrue(ftsIndexComplete, "FTS index should be fully built within 3 minutes");
      log.info("✓ FTS index completed");

      log.info("--- All indices created and completed ---");

      // List all indices
      ListTableIndicesRequest listRequest = new ListTableIndicesRequest();
      listRequest.setId(Lists.newArrayList(tableName));
      ListTableIndicesResponse listResponse = namespace.listTableIndices(listRequest);

      assertNotNull(listResponse.getIndexes(), "Indexes list should not be null");
      assertTrue(listResponse.getIndexes().size() >= 3, "Should have at least 3 indices");

      log.info("✓ Created {} indices:", listResponse.getIndexes().size());
      for (IndexListItemResponse index : listResponse.getIndexes()) {
        log.info("  - {} on columns: {}", index.getIndexName(), index.getColumns());
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
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testIndexWithDifferentMetrics() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Index with Different Metrics ===");
    String tableName = Utils.generateTableName("test_index_metrics");

    try {
      // Create table
      Utils.createTable(namespace, allocator, tableName, 300);

      // Test COSINE metric
      log.info("--- Creating index with COSINE metric ---");
      CreateTableIndexRequest cosineRequest = new CreateTableIndexRequest();
      cosineRequest.setId(Lists.newArrayList(tableName));
      cosineRequest.setColumn("embedding");
      cosineRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
      cosineRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.COSINE);

      CreateTableIndexResponse cosineResponse = namespace.createTableIndex(cosineRequest);
      assertNotNull(cosineResponse, "Create index response should not be null");

      // Wait and verify
      Utils.waitForIndexComplete(namespace, tableName, "embedding_idx", 180);

      DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
      statsRequest.setId(Lists.newArrayList(tableName));
      DescribeTableIndexStatsResponse statsResponse =
          namespace.describeTableIndexStats(statsRequest, "embedding_idx");

      assertEquals("cosine", statsResponse.getDistanceType(), "Distance type should be cosine");
      log.info("✓ Created index with COSINE metric");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }
}
