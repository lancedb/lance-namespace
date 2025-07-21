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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for table update and delete operations. */
public class TableUpdateDeleteTest extends BaseNamespaceTest {

  @Test
  public void testUpdateTable() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Update Table ===");
    String tableName = TestUtils.generateTableName("test_update");

    try {
      // Create table with 3 rows (IDs: 1, 2, 3)
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 3).build();
      CreateTableResponse createResponse = namespace.createTable(tableName, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      // Update all rows (id = id + 1)
      System.out.println("\n--- Step 2: Updating all rows (id = id + 1) ---");
      UpdateTableRequest updateRequest = new UpdateTableRequest();
      updateRequest.setName(tableName);
      updateRequest.setNamespace(new ArrayList<>());
      List<List<String>> updates = new ArrayList<>();
      updates.add(Arrays.asList("id", "id + 1"));
      updateRequest.setUpdates(updates);

      UpdateTableResponse updateResponse = namespace.updateTable(updateRequest);
      assertNotNull(updateResponse, "Update response should not be null");
      assertEquals(3, updateResponse.getUpdatedRows().longValue(), "Should have updated 3 rows");
      System.out.println("✓ Updated 3 rows, IDs should now be: 2, 3, 4");

      // Update rows with predicate (id > 2)
      System.out.println("\n--- Step 3: Updating rows with predicate (id > 2) ---");
      UpdateTableRequest predicateUpdateRequest = new UpdateTableRequest();
      predicateUpdateRequest.setName(tableName);
      predicateUpdateRequest.setNamespace(new ArrayList<>());
      predicateUpdateRequest.setPredicate("id > 2");
      List<List<String>> predicateUpdates = new ArrayList<>();
      predicateUpdates.add(Arrays.asList("id", "id + 10"));
      predicateUpdateRequest.setUpdates(predicateUpdates);

      UpdateTableResponse predicateUpdateResponse = namespace.updateTable(predicateUpdateRequest);
      assertNotNull(predicateUpdateResponse, "Predicate update response should not be null");
      assertEquals(
          2, predicateUpdateResponse.getUpdatedRows().longValue(), "Should have updated 2 rows");
      System.out.println("✓ Updated 2 rows, IDs should now be: 2, 13, 14");

      // Verify final state
      QueryTableRequest QueryTableRequest = TestUtils.createSimpleQuery(tableName, 3);
      byte[] queryResult = namespace.queryTable(QueryTableRequest);
      assertNotNull(queryResult, "Query result should not be null");

      List<Integer> idValues =
          ArrowTestUtils.extractColumn(queryResult, allocator, "id", Integer.class);
      Collections.sort(idValues);
      assertEquals(3, idValues.size(), "Should have exactly 3 rows");
      assertEquals(
          Arrays.asList(2, 13, 14), idValues, "ID values should be [2, 13, 14] after updates");
      System.out.println("✓ Verified final ID values: " + idValues);

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testDeleteFromTable() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Delete From Table ===");
    String tableName = TestUtils.generateTableName("test_delete");

    try {
      // Create table with 3 rows
      System.out.println("\n--- Step 1: Creating table with 3 rows ---");
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 3).build();
      CreateTableResponse createResponse = namespace.createTable(tableName, tableData);
      assertNotNull(createResponse, "Create table response should not be null");

      long initialCount = TestUtils.countRows(namespace, tableName);
      assertEquals(3, initialCount, "Initial row count should be 3");

      // Delete row with id=1
      System.out.println("\n--- Step 2: Deleting row with id=1 ---");
      DeleteFromTableRequest deleteRequest = new DeleteFromTableRequest();
      deleteRequest.setName(tableName);
      deleteRequest.setPredicate("id = 1");

      DeleteFromTableResponse deleteResponse = namespace.deleteFromTable(deleteRequest);
      assertEquals(2, deleteResponse.getVersion(), "Version should increment");

      long countAfterFirst = TestUtils.countRows(namespace, tableName);
      assertEquals(2, countAfterFirst, "Should have 2 rows after deleting id=1");
      System.out.println("✓ Deleted 1 row, remaining count: " + countAfterFirst);

      // Delete with complex predicate (id > 2)
      System.out.println("\n--- Step 3: Deleting with complex predicate (id > 2) ---");
      DeleteFromTableRequest complexDeleteRequest = new DeleteFromTableRequest();
      complexDeleteRequest.setName(tableName);
      complexDeleteRequest.setPredicate("id > 2");

      DeleteFromTableResponse complexDeleteResponse =
          namespace.deleteFromTable(complexDeleteRequest);
      assertEquals(3, complexDeleteResponse.getVersion(), "Version should increment again");

      long finalCount = TestUtils.countRows(namespace, tableName);
      assertEquals(1, finalCount, "Should have 1 row after deleting id > 2");
      System.out.println("✓ Deleted rows where id > 2, remaining count: " + finalCount);

      // Verify remaining row
      QueryTableRequest QueryTableRequest = TestUtils.createSimpleQuery(tableName, 1);
      byte[] queryResult = namespace.queryTable(QueryTableRequest);
      List<Integer> remainingIds =
          ArrowTestUtils.extractColumn(queryResult, allocator, "id", Integer.class);
      assertEquals(Arrays.asList(2), remainingIds, "Only ID 2 should remain");
      System.out.println("✓ Verified remaining row has ID: " + remainingIds.get(0));

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }
}
