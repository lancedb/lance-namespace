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
package com.lancedb.lance.namespace.test.table;

import com.lancedb.lance.namespace.model.*;
import com.lancedb.lance.namespace.test.BaseNamespaceTest;
import com.lancedb.lance.namespace.test.utils.ArrowTestUtils;
import com.lancedb.lance.namespace.test.utils.TestUtils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for merge insert (upsert) operations. */
public class TableMergeInsertTest extends BaseNamespaceTest {

  @Test
  public void testMergeInsert() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Merge Insert (Upsert) ===");
    String tableName = TestUtils.generateTableName("test_merge");

    try {
      // Step 1: Create table with 3 rows
      System.out.println("\n--- Step 1: Creating table with 3 rows ---");
      byte[] tableData =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRow(1, "Alice", generateVector(1))
              .addRow(2, "Bob", generateVector(2))
              .addRow(3, "Charlie", generateVector(3))
              .build();

      CreateTableResponse createResponse = namespace.createTable(tableName, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      // Verify initial data
      long initialCount = TestUtils.countRows(namespace, tableName);
      assertEquals(3, initialCount, "Initial row count should be 3");

      // Step 2: Merge insert with some matching and some new rows
      System.out.println("\n--- Step 2: Merge insert with mixed rows ---");
      // Create batch with rows: id=2,3,4 (2,3 exist, 4 is new)
      byte[] mergeData =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRow(2, "Bob Updated", generateVector(20))
              .addRow(3, "Charlie Updated", generateVector(30))
              .addRow(4, "David", generateVector(4))
              .build();

      // Perform merge insert
      MergeInsertTableRequest mergeRequest = new MergeInsertTableRequest();
      mergeRequest.setName(tableName);

      MergeInsertTableResponse mergeResponse =
          namespace.mergeInsertTable(
              mergeRequest,
              mergeData,
              "id", // match on id column
              true, // when_matched_update_all
              true // when_not_matched_insert_all
              );

      assertNotNull(mergeResponse, "Merge response should not be null");
      assertEquals(2, mergeResponse.getNumUpdatedRows().longValue(), "Should have updated 2 rows");
      assertEquals(1, mergeResponse.getNumInsertedRows().longValue(), "Should have inserted 1 row");
      System.out.println(
          "✓ Merge insert completed: "
              + mergeResponse.getNumUpdatedRows()
              + " updated, "
              + mergeResponse.getNumInsertedRows()
              + " inserted");

      // Verify final count
      long finalCount = TestUtils.countRows(namespace, tableName);
      assertEquals(4, finalCount, "Final row count should be 4");

      // Step 3: Verify the updates by querying the table
      System.out.println("\n--- Step 3: Verifying merged data ---");
      QueryRequest queryRequest = TestUtils.createSimpleQuery(tableName, 4);
      byte[] queryResult = namespace.queryTable(queryRequest);
      assertNotNull(queryResult, "Query result should not be null");

      // Extract and verify the data
      List<Integer> ids = ArrowTestUtils.extractColumn(queryResult, allocator, "id", Integer.class);
      List<String> names =
          ArrowTestUtils.extractColumn(queryResult, allocator, "name", String.class);

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
      System.out.println("✓ Merge insert data verified successfully");

      // Test merge with only inserts
      System.out.println("\n--- Step 4: Testing merge with only new rows ---");
      byte[] insertOnlyData =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRow(5, "Eve", generateVector(5))
              .addRow(6, "Frank", generateVector(6))
              .build();

      MergeInsertTableRequest insertOnlyRequest = new MergeInsertTableRequest();
      insertOnlyRequest.setName(tableName);

      MergeInsertTableResponse insertOnlyResponse =
          namespace.mergeInsertTable(insertOnlyRequest, insertOnlyData, "id", true, true);

      assertEquals(
          0, insertOnlyResponse.getNumUpdatedRows().longValue(), "Should have updated 0 rows");
      assertEquals(
          2, insertOnlyResponse.getNumInsertedRows().longValue(), "Should have inserted 2 rows");
      System.out.println("✓ Insert-only merge completed successfully");

      long finalCount2 = TestUtils.countRows(namespace, tableName);
      assertEquals(6, finalCount2, "Final row count should be 6");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testMergeInsertWithUpdateOnly() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Merge Insert with Update Only ===");
    String tableName = TestUtils.generateTableName("test_merge_update_only");

    try {
      // Create initial table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 5).build();
      namespace.createTable(tableName, tableData);

      // Try to merge with when_not_matched_insert_all = false
      byte[] mergeData =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRow(3, "Updated Three", generateVector(30))
              .addRow(6, "New Six", generateVector(6)) // This should NOT be inserted
              .build();

      MergeInsertTableRequest mergeRequest = new MergeInsertTableRequest();
      mergeRequest.setName(tableName);

      MergeInsertTableResponse mergeResponse =
          namespace.mergeInsertTable(
              mergeRequest,
              mergeData,
              "id",
              true, // when_matched_update_all
              false // when_not_matched_insert_all = false
              );

      assertEquals(1, mergeResponse.getNumUpdatedRows().longValue(), "Should have updated 1 row");
      assertEquals(
          0, mergeResponse.getNumInsertedRows().longValue(), "Should have inserted 0 rows");

      // Verify count remains 5
      long finalCount = TestUtils.countRows(namespace, tableName);
      assertEquals(5, finalCount, "Row count should remain 5");

      System.out.println("✓ Update-only merge completed successfully");

    } finally {
      TestUtils.dropTable(namespace, tableName);
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
