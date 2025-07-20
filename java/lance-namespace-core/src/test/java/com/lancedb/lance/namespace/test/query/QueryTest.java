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
package com.lancedb.lance.namespace.test.query;

import com.lancedb.lance.namespace.model.*;
import com.lancedb.lance.namespace.test.BaseNamespaceTest;
import com.lancedb.lance.namespace.test.utils.ArrowTestUtils;
import com.lancedb.lance.namespace.test.utils.TestUtils;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for query operations including vector search, filters, and various query options. */
public class QueryTest extends BaseNamespaceTest {

  @Test
  public void testBasicVectorQuery() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Basic Vector Query ===");
    String tableName = TestUtils.generateTableName("test_vector_query");

    try {
      // Create table with 10 rows
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 10).build();
      namespace.createTable(tableName, tableData);

      // Create vector query
      QueryRequest queryRequest = TestUtils.createVectorQuery(tableName, 5, 128);

      queryRequest.setK(5);

      byte[] queryResult = namespace.queryTable(queryRequest);
      assertNotNull(queryResult, "Query result should not be null");

      // Verify results
      try (BufferAllocator verifyAllocator = new RootAllocator()) {
        ArrowTestUtils.readArrowFile(
            queryResult,
            verifyAllocator,
            root -> {
              Schema resultSchema = root.getSchema();
              List<String> resultColumns = new ArrayList<>();
              for (Field field : resultSchema.getFields()) {
                resultColumns.add(field.getName());
              }
              System.out.println("Result columns: " + resultColumns);

              // Verify columns
              assertTrue(resultColumns.contains("id"), "Result should contain 'id' column");
              assertTrue(resultColumns.contains("name"), "Result should contain 'name' column");
            });

        int totalRows = ArrowTestUtils.countRows(queryResult, verifyAllocator);
        System.out.println("Query returned " + totalRows + " rows");
        assertTrue(totalRows == 5, "Query should return exactly 5 rows");
      }

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testQueryWithFilter() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Query with Filter ===");
    String tableName = TestUtils.generateTableName("test_query_filter");

    try {
      // Create table with 100 rows for better filter testing
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 100).build();
      namespace.createTable(tableName, tableData);

      // Test 1: Filter-only query (no vector)
      System.out.println("\n--- Test 1: Filter-only query ---");
      QueryRequest filterQuery = new QueryRequest();
      filterQuery.setName(tableName);
      filterQuery.setK(10);
      filterQuery.setFilter("id > 50");
      filterQuery.setColumns(Arrays.asList("id", "name", "embedding"));

      byte[] filterResult = namespace.queryTable(filterQuery);
      assertNotNull(filterResult, "Filter query result should not be null");

      int rowCount = ArrowTestUtils.countRows(filterResult, allocator);
      assertEquals(10, rowCount, "Filter query should return exactly 10 rows");
      System.out.println("✓ Filter-only query returned " + rowCount + " rows");

      // Test 2: Vector query with filter
      System.out.println("\n--- Test 2: Vector query with filter ---");
      QueryRequest vectorFilterQuery = TestUtils.createVectorQuery(tableName, 5, 128);
      vectorFilterQuery.setFilter("id < 20");

      byte[] vectorFilterResult = namespace.queryTable(vectorFilterQuery);
      assertNotNull(vectorFilterResult, "Vector filter query result should not be null");

      List<Integer> ids =
          ArrowTestUtils.extractColumn(vectorFilterResult, allocator, "id", Integer.class);
      assertTrue(ids.stream().allMatch(id -> id < 20), "All IDs should be less than 20");
      System.out.println("✓ Vector query with filter returned IDs: " + ids);

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testQueryWithPrefilter() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Query with Prefilter ===");
    String tableName = TestUtils.generateTableName("test_prefilter");

    try {
      // Create table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 50).build();
      namespace.createTable(tableName, tableData);

      // Test prefilter = true
      System.out.println("\n--- Testing prefilter = true ---");
      QueryRequest prefilterQuery = new QueryRequest();
      prefilterQuery.setName(tableName);
      prefilterQuery.setK(5);
      prefilterQuery.setPrefilter(true);
      prefilterQuery.setFilter("id < 20");
      prefilterQuery.setColumns(Arrays.asList("id", "name"));

      byte[] prefilterResult = namespace.queryTable(prefilterQuery);
      assertNotNull(prefilterResult, "Prefilter query result should not be null");

      List<Integer> ids =
          ArrowTestUtils.extractColumn(prefilterResult, allocator, "id", Integer.class);
      assertTrue(
          ids.stream().allMatch(id -> id < 20), "All IDs should be less than 20 with prefilter");
      System.out.println("✓ Prefilter query returned " + ids.size() + " rows");

      // Test prefilter = false (postfilter)
      System.out.println("\n--- Testing prefilter = false (postfilter) ---");
      QueryRequest postfilterQuery = TestUtils.createVectorQuery(tableName, 10, 128);
      postfilterQuery.setPrefilter(false);
      postfilterQuery.setFilter("id % 2 = 0"); // Even IDs only

      byte[] postfilterResult = namespace.queryTable(postfilterQuery);
      assertNotNull(postfilterResult, "Postfilter query result should not be null");

      List<Integer> evenIds =
          ArrowTestUtils.extractColumn(postfilterResult, allocator, "id", Integer.class);
      assertTrue(
          evenIds.stream().allMatch(id -> id % 2 == 0), "All IDs should be even with postfilter");
      System.out.println("✓ Postfilter query returned " + evenIds.size() + " even IDs");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testQueryWithFastSearch() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Query with Fast Search ===");
    String tableName = TestUtils.generateTableName("test_fast_search");

    try {
      // Create table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 100).build();
      namespace.createTable(tableName, tableData);

      // Test fast_search = true
      System.out.println("\n--- Testing fast_search = true ---");
      QueryRequest fastSearchQuery = TestUtils.createVectorQuery(tableName, 10, 128);
      fastSearchQuery.setFastSearch(true);

      byte[] fastSearchResult = namespace.queryTable(fastSearchQuery);
      assertNotNull(fastSearchResult, "Fast search query result should not be null");

      int fastSearchRows = ArrowTestUtils.countRows(fastSearchResult, allocator);
      assertEquals(10, fastSearchRows, "Fast search should return requested k rows");
      System.out.println("✓ Fast search returned " + fastSearchRows + " rows");

      // Test fast_search = false
      System.out.println("\n--- Testing fast_search = false ---");
      QueryRequest noFastSearchQuery = TestUtils.createVectorQuery(tableName, 10, 128);
      noFastSearchQuery.setFastSearch(false);

      byte[] noFastSearchResult = namespace.queryTable(noFastSearchQuery);
      assertNotNull(noFastSearchResult, "No fast search query result should not be null");

      int noFastSearchRows = ArrowTestUtils.countRows(noFastSearchResult, allocator);
      assertEquals(10, noFastSearchRows, "No fast search should also return requested k rows");
      System.out.println("✓ No fast search returned " + noFastSearchRows + " rows");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testQueryWithColumnSelection() throws IOException {
    skipIfNotConfigured();

    System.out.println("=== Test: Query with Column Selection ===");
    String tableName = TestUtils.generateTableName("test_column_selection");

    try {
      // Create table
      byte[] tableData = new ArrowTestUtils.TableDataBuilder(allocator).addRows(1, 20).build();
      namespace.createTable(tableName, tableData);

      // Query with specific columns
      QueryRequest columnQuery = new QueryRequest();
      columnQuery.setName(tableName);
      columnQuery.setK(5);
      columnQuery.setColumns(Arrays.asList("id", "name")); // Don't include embedding

      byte[] result = namespace.queryTable(columnQuery);
      assertNotNull(result, "Column selection query result should not be null");

      // Verify only requested columns are returned
      try (BufferAllocator verifyAllocator = new RootAllocator()) {
        ArrowTestUtils.readArrowFile(
            result,
            verifyAllocator,
            root -> {
              Schema schema = root.getSchema();
              List<String> fieldNames = new ArrayList<>();
              for (Field field : schema.getFields()) {
                fieldNames.add(field.getName());
              }

              assertTrue(fieldNames.contains("id"), "Result should contain 'id' column");
              assertTrue(fieldNames.contains("name"), "Result should contain 'name' column");
              assertFalse(
                  fieldNames.contains("embedding"), "Result should NOT contain 'embedding' column");

              System.out.println("✓ Query returned only requested columns: " + fieldNames);
            });
      }

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }
}
