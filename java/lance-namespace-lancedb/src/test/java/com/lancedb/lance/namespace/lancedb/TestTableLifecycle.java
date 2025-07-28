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

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.InsertIntoTableRequest;
import com.lancedb.lance.namespace.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.JsonDataType;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for table lifecycle operations: create, describe, insert, drop. */
public class TestTableLifecycle extends LanceDbRestNamespaceTestBase {
  private static final Logger log = LoggerFactory.getLogger(TestTableLifecycle.class);

  @Test
  public void testTableLifecycle() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Table Lifecycle ===");
    String tableName = Utils.generateTableName("test_lifecycle");

    try {
      // Create table with 3 rows
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 3);
      assertNotNull(createResponse, "Create response should not be null");

      // Test count rows
      log.info("--- Testing count rows ---");
      long count = Utils.countRows(namespace, tableName);
      assertEquals(3, count, "Row count should match expected number");
      log.info("✓ Count rows verified: {}", count);

      // Test describe table
      log.info("--- Testing describe table ---");
      DescribeTableRequest describeRequest = new DescribeTableRequest();
      describeRequest.setId(Lists.newArrayList(tableName));

      DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
      assertNotNull(describeResponse, "Describe response should not be null");
      assertNotNull(describeResponse.getSchema(), "Schema should not be null");

      // Verify schema
      JsonArrowSchema responseSchema = describeResponse.getSchema();
      assertNotNull(responseSchema, "Schema object should not be null");
      assertNotNull(responseSchema.getFields(), "Schema fields should not be null");
      assertEquals(4, responseSchema.getFields().size(), "Schema should have 4 fields");

      List<String> fieldNames =
          responseSchema.getFields().stream()
              .map(JsonArrowField::getName)
              .collect(Collectors.toList());
      assertTrue(fieldNames.contains("id"), "Schema should contain 'id' field");
      assertTrue(fieldNames.contains("name"), "Schema should contain 'name' field");
      assertTrue(fieldNames.contains("category"), "Schema should contain 'category' field");
      assertTrue(fieldNames.contains("embedding"), "Schema should contain 'embedding' field");
      log.info("✓ Table schema verified with fields: {}", fieldNames);

      // Verify version and stats
      assertNotNull(describeResponse.getVersion(), "Version should not be null");
      assertTrue(describeResponse.getVersion() >= 1, "Version should be at least 1 for new table");
      log.info("✓ Table version: {}", describeResponse.getVersion());

      // Test insert table
      log.info("--- Testing insert table ---");
      byte[] insertData1 =
          new Utils.TableDataBuilder(allocator)
              .addRows(1000, 2) // Start IDs from 1000 to differentiate
              .build();

      InsertIntoTableRequest insertRequest = new InsertIntoTableRequest();
      insertRequest.setId(Lists.newArrayList(tableName));
      insertRequest.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      InsertIntoTableResponse insertResponse =
          namespace.insertIntoTable(insertRequest, insertData1);
      assertNotNull(insertResponse, "Insert response should not be null");
      assertNotNull(insertResponse.getVersion(), "Insert response version should not be null");
      log.info("✓ Inserted 2 rows, new version: {}", insertResponse.getVersion());

      // Verify row count after first insert
      long count2 = Utils.countRows(namespace, tableName);
      assertEquals(5, count2, "Row count should be 5 after first insert");
      log.info("✓ Verified row count after first insert: {}", count2);

      // Second insert
      log.info("--- Testing second insert ---");
      byte[] insertData2 =
          new Utils.TableDataBuilder(allocator)
              .addRows(2000, 3) // Start IDs from 2000
              .build();

      InsertIntoTableRequest insertRequest2 = new InsertIntoTableRequest();
      insertRequest2.setId(Lists.newArrayList(tableName));
      insertRequest2.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      InsertIntoTableResponse secondInsertResponse =
          namespace.insertIntoTable(insertRequest2, insertData2);
      assertNotNull(secondInsertResponse, "Second insert response should not be null");
      log.info("✓ Inserted 3 more rows, new version: {}", secondInsertResponse.getVersion());

      // Verify final row count
      long finalCount = Utils.countRows(namespace, tableName);
      assertEquals(8, finalCount, "Row count should be 8 after second insert");
      log.info("✓ Verified final row count: {}", finalCount);

      log.info("✓ Table lifecycle test passed!");

    } finally {
      // Clean up
      Utils.dropTable(namespace, tableName);

      // Verify table was dropped
      log.info("--- Verifying table was dropped ---");
      try {
        DescribeTableRequest verifyDropRequest = new DescribeTableRequest();
        verifyDropRequest.setId(Lists.newArrayList(tableName));
        namespace.describeTable(verifyDropRequest);
        fail("Expected exception when describing dropped table");
      } catch (LanceNamespaceException e) {
        assertEquals(404, e.getCode(), "Should get 404 error code for non-existent table");
        log.info("✓ Confirmed table no longer exists (404 error code)");
      }
    }
  }

  @Test
  public void testDescribeTableWithVersion() throws IOException {
    skipIfNotConfigured();

    log.info("=== Test: Describe Table With Version ===");
    String tableName = Utils.generateTableName("test_describe_version");

    try {
      // Create table
      CreateTableResponse createResponse = Utils.createTable(namespace, allocator, tableName, 5);
      assertNotNull(createResponse, "Create response should not be null");

      // Get initial version
      DescribeTableRequest describeV1 = new DescribeTableRequest();
      describeV1.setId(Lists.newArrayList(tableName));
      DescribeTableResponse v1Response = namespace.describeTable(describeV1);
      Long version1 = v1Response.getVersion();
      log.info("Initial version: {}", version1);

      // Insert more data to create new version
      byte[] insertData = new Utils.TableDataBuilder(allocator).addRows(100, 5).build();
      InsertIntoTableRequest insertRequest = new InsertIntoTableRequest();
      insertRequest.setId(Lists.newArrayList(tableName));
      insertRequest.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      namespace.insertIntoTable(insertRequest, insertData);

      // Describe current version
      DescribeTableRequest describeCurrent = new DescribeTableRequest();
      describeCurrent.setId(Lists.newArrayList(tableName));
      DescribeTableResponse currentResponse = namespace.describeTable(describeCurrent);
      Long currentVersion = currentResponse.getVersion();
      log.info("Current version after insert: {}", currentVersion);
      assertTrue(currentVersion > version1, "Version should increase after insert");

      // Describe specific older version
      DescribeTableRequest describeOldVersion = new DescribeTableRequest();
      describeOldVersion.setId(Lists.newArrayList(tableName));
      describeOldVersion.setVersion(version1);
      DescribeTableResponse oldVersionResponse = namespace.describeTable(describeOldVersion);

      assertEquals(version1, oldVersionResponse.getVersion(), "Should return requested version");

      // Verify nested structures in response
      assertNotNull(oldVersionResponse.getSchema(), "Schema should not be null");
      assertNotNull(oldVersionResponse.getSchema().getFields(), "Schema fields should not be null");

      // Check JsonField structure
      for (JsonArrowField field : oldVersionResponse.getSchema().getFields()) {
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

      // Stats are not part of the response according to the current API

      log.info("✓ Describe table with version tested successfully");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }
}
