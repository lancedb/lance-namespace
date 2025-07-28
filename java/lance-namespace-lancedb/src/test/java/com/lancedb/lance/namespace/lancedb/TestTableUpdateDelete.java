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
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for table update and delete operations. */
public class TestTableUpdateDelete extends LanceDbRestNamespaceTestBase {
  private static final Logger log = LoggerFactory.getLogger(TestTableUpdateDelete.class);

  @Test
  public void testUpdateTable() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Update Table ===");
    String tableName = Utils.generateTableName("test_update");

    try {
      // Create table with 3 rows (IDs: 1, 2, 3)
      log.info("--- Step 1: Creating table {} with 3 rows ---", tableName);
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 3);
      assertNotNull(createResponse, "Create table response should not be null");

      // Update all rows (id = id + 1)
      log.info("--- Step 2: Updating all rows (id = id + 1) ---");
      UpdateTableRequest updateRequest = new UpdateTableRequest();
      updateRequest.setId(Lists.newArrayList(tableName));
      List<List<String>> updates = new ArrayList<>();
      updates.add(Arrays.asList("id", "id + 1"));
      updateRequest.setUpdates(updates);

      UpdateTableResponse updateResponse = namespace.updateTable(updateRequest);
      assertNotNull(updateResponse, "Update response should not be null");
      assertEquals(3, updateResponse.getUpdatedRows().longValue(), "Should have updated 3 rows");
      log.info("✓ Updated 3 rows, IDs should now be: 2, 3, 4");

      // Update rows with predicate (id > 2)
      log.info("--- Step 3: Updating rows with predicate (id > 2) ---");
      UpdateTableRequest predicateUpdateRequest = new UpdateTableRequest();
      predicateUpdateRequest.setId(Lists.newArrayList(tableName));
      predicateUpdateRequest.setPredicate("id > 2");
      List<List<String>> predicateUpdates = new ArrayList<>();
      predicateUpdates.add(Arrays.asList("id", "id + 10"));
      predicateUpdateRequest.setUpdates(predicateUpdates);

      UpdateTableResponse predicateUpdateResponse = namespace.updateTable(predicateUpdateRequest);
      assertNotNull(predicateUpdateResponse, "Predicate update response should not be null");
      assertEquals(
          2, predicateUpdateResponse.getUpdatedRows().longValue(), "Should have updated 2 rows");
      log.info("✓ Updated 2 rows, IDs should now be: 2, 13, 14");

      // Verify final state
      QueryTableRequest QueryTableRequest = Utils.createSimpleQuery(tableName, 3);
      byte[] queryResult = namespace.queryTable(QueryTableRequest);
      assertNotNull(queryResult, "Query result should not be null");

      List<Integer> idValues = Utils.extractColumn(queryResult, allocator, "id", Integer.class);
      Collections.sort(idValues);
      assertEquals(3, idValues.size(), "Should have exactly 3 rows");
      assertEquals(
          Arrays.asList(2, 13, 14), idValues, "ID values should be [2, 13, 14] after updates");
      log.info("✓ Verified final ID values: {}", idValues);

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testDeleteFromTable() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Delete From Table ===");
    String tableName = Utils.generateTableName("test_delete");

    try {
      // Create table with 3 rows
      log.info("--- Step 1: Creating table {} with 3 rows ---", tableName);
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 3);
      assertNotNull(createResponse, "Create table response should not be null");

      long initialCount = Utils.countRows(namespace, tableName);
      assertEquals(3, initialCount, "Initial row count should be 3");

      // Delete row with id=1
      log.info("--- Step 2: Deleting row with id=1 ---");
      DeleteFromTableRequest deleteRequest = new DeleteFromTableRequest();
      deleteRequest.setId(Lists.newArrayList(tableName));
      deleteRequest.setPredicate("id = 1");

      DeleteFromTableResponse deleteResponse = namespace.deleteFromTable(deleteRequest);
      assertEquals(2, deleteResponse.getVersion(), "Version should increment");

      long countAfterFirst = Utils.countRows(namespace, tableName);
      assertEquals(2, countAfterFirst, "Should have 2 rows after deleting id=1");
      log.info("✓ Deleted 1 row, remaining count: {}", countAfterFirst);

      // Delete with complex predicate (id > 2)
      log.info("--- Step 3: Deleting with complex predicate (id > 2) ---");
      DeleteFromTableRequest complexDeleteRequest = new DeleteFromTableRequest();
      complexDeleteRequest.setId(Lists.newArrayList(tableName));
      complexDeleteRequest.setPredicate("id > 2");

      DeleteFromTableResponse complexDeleteResponse =
          namespace.deleteFromTable(complexDeleteRequest);
      assertEquals(3, complexDeleteResponse.getVersion(), "Version should increment again");

      long finalCount = Utils.countRows(namespace, tableName);
      assertEquals(1, finalCount, "Should have 1 row after deleting id > 2");
      log.info("✓ Deleted rows where id > 2, remaining count: {}", finalCount);

      // Verify remaining row
      QueryTableRequest QueryTableRequest = Utils.createSimpleQuery(tableName, 1);
      byte[] queryResult = namespace.queryTable(QueryTableRequest);
      List<Integer> remainingIds = Utils.extractColumn(queryResult, allocator, "id", Integer.class);
      assertEquals(Arrays.asList(2), remainingIds, "Only ID 2 should remain");
      log.info("✓ Verified remaining row has ID: {}", remainingIds.get(0));

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }
}
