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

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.*;
import com.lancedb.lance.namespace.test.BaseNamespaceTest;
import com.lancedb.lance.namespace.test.utils.ArrowTestUtils;
import com.lancedb.lance.namespace.test.utils.TestUtils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for table lifecycle operations: create, describe, insert, drop.
 *
 * <p>Note: All table creation operations require a location parameter to be set. This specifies
 * where the table data will be stored (e.g., local filesystem, S3, GCS). For tests, we use file://
 * URIs pointing to /tmp/lance/ directory.
 */
public class TableLifecycleTest extends BaseNamespaceTest {

  @Test
  public void testTableLifecycle() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Table Lifecycle ===");
    String tableName = TestUtils.generateTableName("test_lifecycle");

    try {
      // Create table with 3 rows
      System.out.println("\n--- Creating table ---");
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 3).build();

      CreateTableRequest createRequest = new CreateTableRequest();
      createRequest.setName(tableName);
      // IMPORTANT: location is required for table creation. Use a unique path for each table.
      // For local testing, use file:// URIs. For production, use appropriate cloud storage URIs.
      createRequest.setLocation("file:///tmp/lance/" + tableName);
      CreateTableResponse createResponse = namespace.createTable(createRequest, tableData);
      assertNotNull(createResponse, "Create response should not be null");
      System.out.println("✓ Table created successfully: " + tableName);

      // Test count rows
      System.out.println("\n--- Testing count rows ---");
      long count = TestUtils.countRows(namespace, tableName);
      assertEquals(3, count, "Row count should match expected number");
      System.out.println("✓ Count rows verified: " + count);

      // Test describe table
      System.out.println("\n--- Testing describe table ---");
      DescribeTableRequest describeRequest = new DescribeTableRequest();
      describeRequest.setName(tableName);

      DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
      assertNotNull(describeResponse, "Describe response should not be null");
      assertNotNull(describeResponse.getSchema(), "Schema should not be null");

      // Verify schema
      JsonSchema responseSchema = describeResponse.getSchema();
      assertNotNull(responseSchema, "Schema object should not be null");
      assertNotNull(responseSchema.getFields(), "Schema fields should not be null");
      assertEquals(4, responseSchema.getFields().size(), "Schema should have 4 fields");

      List<String> fieldNames =
          responseSchema.getFields().stream().map(JsonField::getName).collect(Collectors.toList());
      assertTrue(fieldNames.contains("id"), "Schema should contain 'id' field");
      assertTrue(fieldNames.contains("name"), "Schema should contain 'name' field");
      assertTrue(fieldNames.contains("category"), "Schema should contain 'category' field");
      assertTrue(fieldNames.contains("embedding"), "Schema should contain 'embedding' field");
      System.out.println("✓ Table schema verified with fields: " + fieldNames);

      // Verify version and stats
      assertNotNull(describeResponse.getVersion(), "Version should not be null");
      assertTrue(describeResponse.getVersion() >= 1, "Version should be at least 1 for new table");
      System.out.println("✓ Table version: " + describeResponse.getVersion());

      // Test insert table
      System.out.println("\n--- Testing insert table ---");
      byte[] insertData1 =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRows(1000, 2) // Start IDs from 1000 to differentiate
              .build();

      InsertIntoTableRequest insertRequest = new InsertIntoTableRequest();
      insertRequest.setName(tableName);
      insertRequest.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      InsertIntoTableResponse insertResponse =
          namespace.insertIntoTable(insertRequest, insertData1);
      assertNotNull(insertResponse, "Insert response should not be null");
      assertNotNull(insertResponse.getVersion(), "Insert response version should not be null");
      System.out.println("✓ Inserted 2 rows, new version: " + insertResponse.getVersion());

      // Verify row count after first insert
      long count2 = TestUtils.countRows(namespace, tableName);
      assertEquals(5, count2, "Row count should be 5 after first insert");
      System.out.println("✓ Verified row count after first insert: " + count2);

      // Second insert
      System.out.println("\n--- Testing second insert ---");
      byte[] insertData2 =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .addRows(2000, 3) // Start IDs from 2000
              .build();

      InsertIntoTableRequest insertRequest2 = new InsertIntoTableRequest();
      insertRequest2.setName(tableName);
      insertRequest2.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      InsertIntoTableResponse secondInsertResponse =
          namespace.insertIntoTable(insertRequest2, insertData2);
      assertNotNull(secondInsertResponse, "Second insert response should not be null");
      System.out.println(
          "✓ Inserted 3 more rows, new version: " + secondInsertResponse.getVersion());

      // Verify final row count
      long finalCount = TestUtils.countRows(namespace, tableName);
      assertEquals(8, finalCount, "Row count should be 8 after second insert");
      System.out.println("✓ Verified final row count: " + finalCount);

      System.out.println("\n✓ Table lifecycle test passed!");

    } finally {
      // Clean up
      TestUtils.dropTable(namespace, tableName);

      // Verify table was dropped
      System.out.println("\n--- Verifying table was dropped ---");
      try {
        DescribeTableRequest verifyDropRequest = new DescribeTableRequest();
        verifyDropRequest.setName(tableName);
        namespace.describeTable(verifyDropRequest);
        fail("Expected exception when describing dropped table");
      } catch (LanceNamespaceException e) {
        assertEquals(404, e.getCode(), "Should get 404 error code for non-existent table");
        System.out.println("✓ Confirmed table no longer exists (404 error code)");
      }
    }
  }

  @Test
  public void testDescribeTableWithVersion() throws IOException {
    skipIfNotConfigured();

    System.out.println("\n=== Test: Describe Table With Version ===");
    String tableName = TestUtils.generateTableName("test_describe_version");

    try {
      // Create table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 5).build();
      CreateTableRequest createRequest = new CreateTableRequest();
      createRequest.setName(tableName);
      // IMPORTANT: location is required for table creation. Use a unique path for each table.
      // For local testing, use file:// URIs. For production, use appropriate cloud storage URIs.
      createRequest.setLocation("file:///tmp/lance/" + tableName);
      CreateTableResponse createResponse = namespace.createTable(createRequest, tableData);
      assertNotNull(createResponse, "Create response should not be null");

      // Get initial version
      DescribeTableRequest describeV1 = new DescribeTableRequest();
      describeV1.setName(tableName);
      DescribeTableResponse v1Response = namespace.describeTable(describeV1);
      Long version1 = v1Response.getVersion();
      System.out.println("Initial version: " + version1);

      // Insert more data to create new version
      byte[] insertData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(100, 5).build();
      InsertIntoTableRequest insertRequest = new InsertIntoTableRequest();
      insertRequest.setName(tableName);
      insertRequest.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
      namespace.insertIntoTable(insertRequest, insertData);

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

      // Stats are not part of the response according to the current API

      System.out.println("✓ Describe table with version tested successfully");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }
}
