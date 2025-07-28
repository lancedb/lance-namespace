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

import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.model.QueryTableRequest;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for merge insert (upsert) operations. */
public class TestMergeInsertIntoTable extends LanceDbRestNamespaceTestBase {
  private static final Logger log = LoggerFactory.getLogger(TestMergeInsertIntoTable.class);

  @Test
  public void testMergeInsert() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Merge Insert (Upsert) ===");
    String tableName = Utils.generateTableName("test_merge");

    try {
      // Step 1: Create table with 3 rows
      log.info("--- Step 1: Creating table {} with 3 rows ---", tableName);
      byte[] tableData =
          new Utils.TableDataBuilder(allocator)
              .addRow(1, "Alice", generateVector(1))
              .addRow(2, "Bob", generateVector(2))
              .addRow(3, "Charlie", generateVector(3))
              .build();

      CreateTableResponse createResponse = Utils.createTable(namespace, tableName, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      // Verify initial data
      long initialCount = Utils.countRows(namespace, tableName);
      assertEquals(3, initialCount, "Initial row count should be 3");

      // Step 2: Merge insert with some matching and some new rows
      log.info("--- Step 2: Merge insert with mixed rows ---");
      // Create batch with rows: id=2,3,4 (2,3 exist, 4 is new)
      byte[] mergeData =
          new Utils.TableDataBuilder(allocator)
              .addRow(2, "Bob Updated", generateVector(20))
              .addRow(3, "Charlie Updated", generateVector(30))
              .addRow(4, "David", generateVector(4))
              .build();

      // Perform merge insert
      MergeInsertIntoTableRequest mergeRequest = new MergeInsertIntoTableRequest();
      mergeRequest.setId(Lists.newArrayList(tableName));

      mergeRequest.setOn("id"); // match on id column
      mergeRequest.setWhenMatchedUpdateAll(true); // when_matched_update_all
      mergeRequest.setWhenNotMatchedInsertAll(true); // when_not_matched_insert_all

      MergeInsertIntoTableResponse mergeResponse =
          namespace.mergeInsertIntoTable(mergeRequest, mergeData);

      assertNotNull(mergeResponse, "Merge response should not be null");
      assertEquals(2, mergeResponse.getNumUpdatedRows().longValue(), "Should have updated 2 rows");
      assertEquals(1, mergeResponse.getNumInsertedRows().longValue(), "Should have inserted 1 row");
      log.info(
          "✓ Merge insert completed: {} updated, {} inserted",
          mergeResponse.getNumUpdatedRows(),
          mergeResponse.getNumInsertedRows());

      // Verify final count
      long finalCount = Utils.countRows(namespace, tableName);
      assertEquals(4, finalCount, "Final row count should be 4");

      // Step 3: Verify the updates by querying the table
      log.info("--- Step 3: Verifying merged data ---");
      QueryTableRequest QueryTableRequest = Utils.createSimpleQuery(tableName, 4);
      byte[] queryResult = namespace.queryTable(QueryTableRequest);
      assertNotNull(queryResult, "Query result should not be null");

      // Extract and verify the data
      List<Integer> ids = Utils.extractColumn(queryResult, allocator, "id", Integer.class);
      List<String> names = Utils.extractColumn(queryResult, allocator, "name", String.class);

      Map<Integer, String> idToName = new HashMap<>();
      for (int i = 0; i < ids.size(); i++) {
        idToName.put(ids.get(i), names.get(i));
      }

      // Verify the merged data
      assertEquals(4, idToName.size(), "Should have 4 rows total");
      assertEquals("Alice", idToName.get(1), "ID 1 should remain unchanged");
      assertEquals("Bob Updated", idToName.get(2), "ID 2 should be updated");
      assertEquals("Charlie Updated", idToName.get(3), "ID 3 should be updated");
      assertEquals("David", idToName.get(4), "ID 4 should be new");
      log.info("✓ Merge insert data verified successfully");

      // Test merge with only inserts
      log.info("--- Step 4: Testing merge with only new rows ---");
      byte[] insertOnlyData =
          new Utils.TableDataBuilder(allocator)
              .addRow(5, "Eve", generateVector(5))
              .addRow(6, "Frank", generateVector(6))
              .build();

      MergeInsertIntoTableRequest insertOnlyRequest = new MergeInsertIntoTableRequest();
      insertOnlyRequest.setId(Lists.newArrayList(tableName));

      insertOnlyRequest.setOn("id");
      insertOnlyRequest.setWhenMatchedUpdateAll(true);
      insertOnlyRequest.setWhenNotMatchedInsertAll(true);

      MergeInsertIntoTableResponse insertOnlyResponse =
          namespace.mergeInsertIntoTable(insertOnlyRequest, insertOnlyData);

      assertEquals(
          0, insertOnlyResponse.getNumUpdatedRows().longValue(), "Should have updated 0 rows");
      assertEquals(
          2, insertOnlyResponse.getNumInsertedRows().longValue(), "Should have inserted 2 rows");
      log.info("✓ Insert-only merge completed successfully");

      long finalCount2 = Utils.countRows(namespace, tableName);
      assertEquals(6, finalCount2, "Final row count should be 6");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testMergeInsertWithUpdateOnly() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Merge Insert with Update Only ===");
    String tableName = Utils.generateTableName("test_merge_update_only");

    try {
      // Create initial table
      Utils.createTable(namespace, allocator, tableName, 5);

      // Try to merge with when_not_matched_insert_all = false
      byte[] mergeData =
          new Utils.TableDataBuilder(allocator)
              .addRow(3, "Updated Three", generateVector(30))
              .addRow(6, "New Six", generateVector(6)) // This should NOT be inserted
              .build();

      MergeInsertIntoTableRequest mergeRequest = new MergeInsertIntoTableRequest();
      mergeRequest.setId(Lists.newArrayList(tableName));

      mergeRequest.setOn("id");
      mergeRequest.setWhenMatchedUpdateAll(true); // when_matched_update_all
      mergeRequest.setWhenNotMatchedInsertAll(false); // when_not_matched_insert_all = false

      MergeInsertIntoTableResponse mergeResponse =
          namespace.mergeInsertIntoTable(mergeRequest, mergeData);

      assertEquals(1, mergeResponse.getNumUpdatedRows().longValue(), "Should have updated 1 row");
      assertEquals(
          0, mergeResponse.getNumInsertedRows().longValue(), "Should have inserted 0 rows");

      // Verify count remains 5
      long finalCount = Utils.countRows(namespace, tableName);
      assertEquals(5, finalCount, "Row count should remain 5");

      log.info("✓ Update-only merge completed successfully");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  private static float[] generateVector(int seed) {
    float[] vector = new float[128];
    for (int i = 0; i < 128; i++) {
      vector[i] = seed * 10.0f + i * 0.1f;
    }
    return vector;
  }
}
