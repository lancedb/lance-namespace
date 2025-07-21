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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for full-text search functionality. */
public class FullTextSearchTest extends BaseNamespaceTest {

  @Test
  public void testSimpleFullTextSearch() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Simple Full-Text Search ===");
    String tableName = TestUtils.generateTableName("test_fts");

    try {
      // Create table with deterministic text data
      System.out.println("\n--- Creating table with text data ---");
      byte[] tableData =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .withSchema(ArrowTestUtils.createSchemaWithText(128))
              .withText(1, "This is an important document about search functionality")
              .withText(2, "Another document containing critical information")
              .withText(3, "Sample text with important data and keywords")
              .withText(4, "Regular content without special keywords")
              .withText(5, "Document with search terms and important notes")
              .addRows(1, 5)
              .build();

      CreateTableResponse createResponse = namespace.createTable(tableName, tableData);
      assertNotNull(createResponse, "Create response should not be null");

      // Create FTS index
      System.out.println("\n--- Creating FTS index ---");
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);

      CreateTableIndexResponse ftsIndexResponse = namespace.createTableIndex(ftsIndexRequest);
      assertNotNull(ftsIndexResponse, "FTS index response should not be null");

      TestUtils.waitForIndexComplete(namespace, tableName, "text_idx", 30);

      // Test 1: Simple string FTS query for "document"
      System.out.println("\n--- Test 1: Simple string FTS query for 'document' ---");
      System.out.println("Expected to find rows 1, 2, 5 (contain 'document')");
      QueryTableRequest stringFtsQuery = new QueryTableRequest();
      stringFtsQuery.setName(tableName);
      stringFtsQuery.setK(10);
      stringFtsQuery.setColumns(Arrays.asList("id", "name", "text", "category"));

      StringFtsQuery simpleFts = new StringFtsQuery();
      simpleFts.setQuery("document");
      QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
      fullTextQuery.setStringQuery(simpleFts);
      stringFtsQuery.setFullTextQuery(fullTextQuery);

      byte[] ftsResult = namespace.queryTable(stringFtsQuery);
      assertNotNull(ftsResult, "FTS query result should not be null");

      int rowCount = ArrowTestUtils.countRows(ftsResult, allocator);
      assertEquals(3, rowCount, "Should find exactly 3 rows containing 'document'");

      List<Integer> foundIds =
          ArrowTestUtils.extractColumn(ftsResult, allocator, "id", Integer.class);
      assertTrue(foundIds.contains(1), "Should find row 1");
      assertTrue(foundIds.contains(2), "Should find row 2");
      assertTrue(foundIds.contains(5), "Should find row 5");

      System.out.println("✓ FTS query returned " + rowCount + " results with IDs: " + foundIds);

      // Test 2: FTS query for "important"
      System.out.println("\n--- Test 2: FTS query for 'important' ---");
      System.out.println("Expected to find rows 1, 3, 5 (contain 'important')");
      QueryTableRequest columnFtsQuery = new QueryTableRequest();
      columnFtsQuery.setName(tableName);
      columnFtsQuery.setK(5);
      columnFtsQuery.setColumns(Arrays.asList("id", "name", "text"));

      StringFtsQuery columnFts = new StringFtsQuery();
      columnFts.setQuery("important");
      columnFts.setColumns(Arrays.asList("text")); // Search only in text column
      QueryTableRequestFullTextQuery columnFullTextQuery = new QueryTableRequestFullTextQuery();
      columnFullTextQuery.setStringQuery(columnFts);
      columnFtsQuery.setFullTextQuery(columnFullTextQuery);

      byte[] columnFtsResult = namespace.queryTable(columnFtsQuery);
      assertNotNull(columnFtsResult, "Column FTS query result should not be null");

      int importantCount = ArrowTestUtils.countRows(columnFtsResult, allocator);
      assertEquals(3, importantCount, "Should find exactly 3 rows containing 'important'");

      List<Integer> importantIds =
          ArrowTestUtils.extractColumn(columnFtsResult, allocator, "id", Integer.class);
      assertTrue(importantIds.contains(1), "Should find row 1");
      assertTrue(importantIds.contains(3), "Should find row 3");
      assertTrue(importantIds.contains(5), "Should find row 5");

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testFullTextSearchWithFilter() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Full-Text Search with Filter ===");
    String tableName = TestUtils.generateTableName("test_fts_filter");

    try {
      // Create table with deterministic text data
      System.out.println("\n--- Creating table with text data ---");
      ArrowTestUtils.TableDataBuilder builder =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .withSchema(ArrowTestUtils.createSchemaWithText(128));

      // Add rows with specific text patterns
      for (int i = 1; i <= 30; i++) {
        if (i % 3 == 0) {
          builder.withText(i, "This is document " + i + " with important information");
        } else if (i % 5 == 0) {
          builder.withText(i, "Document " + i + " contains critical data");
        } else {
          builder.withText(i, "Regular text for row " + i);
        }
      }

      byte[] tableData = builder.addRows(1, 30).build();

      namespace.createTable(tableName, tableData);

      // Create FTS index
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      namespace.createTableIndex(ftsIndexRequest);
      TestUtils.waitForIndexComplete(namespace, tableName, "text_idx", 30);

      // FTS with prefilter
      System.out.println("\n--- Testing FTS with prefilter (id < 25) ---");
      QueryTableRequest ftsPrefilterQuery = new QueryTableRequest();
      ftsPrefilterQuery.setName(tableName);
      ftsPrefilterQuery.setK(20); // Set high to get all matches
      ftsPrefilterQuery.setPrefilter(true);
      ftsPrefilterQuery.setFilter("id < 25");
      ftsPrefilterQuery.setColumns(Arrays.asList("id", "text"));

      StringFtsQuery fts = new StringFtsQuery();
      fts.setQuery("document");
      QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
      fullTextQuery.setStringQuery(fts);
      ftsPrefilterQuery.setFullTextQuery(fullTextQuery);

      // Add empty vector to satisfy API requirement
      QueryTableRequestVector emptyVector = new QueryTableRequestVector();
      emptyVector.setSingleVector(new ArrayList<>());
      ftsPrefilterQuery.setVector(emptyVector);

      byte[] ftsPrefilterResult = namespace.queryTable(ftsPrefilterQuery);
      assertNotNull(ftsPrefilterResult, "FTS prefilter query result should not be null");

      List<Integer> ids =
          ArrowTestUtils.extractColumn(ftsPrefilterResult, allocator, "id", Integer.class);
      assertTrue(
          ids.stream().allMatch(id -> id < 25), "All IDs should be less than 25 with prefilter");

      // Expected document rows under 25: multiples of 3 or 5
      List<Integer> expectedIds = Arrays.asList(3, 5, 6, 9, 10, 12, 15, 18, 20, 21, 24);
      assertEquals(
          expectedIds.size(),
          ids.size(),
          "Should find exactly " + expectedIds.size() + " documents under ID 25");

      for (Integer expectedId : expectedIds) {
        assertTrue(ids.contains(expectedId), "Should find row " + expectedId);
      }

    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testStructuredFullTextSearch() throws IOException, InterruptedException {
    skipIfNotConfigured();

    System.out.println("=== Test: Structured Full-Text Search ===");
    String tableName = TestUtils.generateTableName("test_structured_fts");

    try {
      // Create table with specific text patterns for boolean query testing
      System.out.println("\n--- Creating table with text data for structured queries ---");
      ArrowTestUtils.TableDataBuilder builder =
          new ArrowTestUtils.TableDataBuilder(allocator)
              .withSchema(ArrowTestUtils.createSchemaWithText(128));

      // Create specific patterns:
      // Rows 1-5: contain both "important" and "document"
      builder
          .withText(1, "This is an important document about testing")
          .withText(2, "Another important document with data")
          .withText(3, "Important technical document here")
          .withText(4, "Document containing important information")
          .withText(5, "Very important document to read");

      // Rows 6-8: contain only "important"
      builder
          .withText(6, "Important notice without d-word")
          .withText(7, "This is important text")
          .withText(8, "Important data here");

      // Rows 9-11: contain only "document"
      builder
          .withText(9, "Simple document text")
          .withText(10, "Another document here")
          .withText(11, "Document without i-word");

      // Rows 12-15: contain neither
      builder
          .withText(12, "Regular text content")
          .withText(13, "Simple data entry")
          .withText(14, "Basic information")
          .withText(15, "Normal text here");

      byte[] tableData = builder.addRows(1, 15).build();

      System.out.println("Created table with 15 rows:");
      System.out.println("  - Rows 1-5: contain both 'important' AND 'document'");
      System.out.println("  - Rows 6-8: contain only 'important'");
      System.out.println("  - Rows 9-11: contain only 'document'");
      System.out.println("  - Rows 12-15: contain neither");

      namespace.createTable(tableName, tableData);

      // Create FTS index with position for phrase queries
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setName(tableName);
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      ftsIndexRequest.setWithPosition(true); // Required for phrase queries
      namespace.createTableIndex(ftsIndexRequest);
      TestUtils.waitForIndexComplete(namespace, tableName, "text_idx", 30);

      // Test Boolean Query
      System.out.println("\n--- Testing Boolean Query ---");
      QueryTableRequest booleanQuery = new QueryTableRequest();
      booleanQuery.setName(tableName);
      booleanQuery.setK(10);
      booleanQuery.setColumns(Arrays.asList("id", "text"));

      // Create boolean query: MUST contain "important" AND SHOULD contain "document"
      QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
      StructuredFtsQuery structured = new StructuredFtsQuery();
      FtsQuery ftsQuery = new FtsQuery();

      BooleanQuery boolQuery = new BooleanQuery();

      // Must clause
      FtsQuery mustQuery = new FtsQuery();
      MatchQuery mustMatch = new MatchQuery();
      mustMatch.setTerms("important");
      mustMatch.setColumn("text");
      mustQuery.setMatch(mustMatch);
      boolQuery.setMust(Arrays.asList(mustQuery));

      // Should clause
      FtsQuery shouldQuery = new FtsQuery();
      MatchQuery shouldMatch = new MatchQuery();
      shouldMatch.setTerms("document");
      shouldMatch.setColumn("text");
      shouldQuery.setMatch(shouldMatch);
      boolQuery.setShould(Arrays.asList(shouldQuery));

      ftsQuery.setBoolean(boolQuery);
      structured.setQuery(ftsQuery);
      fullTextQuery.setStructuredQuery(structured);
      booleanQuery.setFullTextQuery(fullTextQuery);

      byte[] boolResult = namespace.queryTable(booleanQuery);
      assertNotNull(boolResult, "Boolean query result should not be null");

      int boolRowCount = ArrowTestUtils.countRows(boolResult, allocator);
      assertEquals(8, boolRowCount, "Should find exactly 8 rows with 'important'");

      List<Integer> boolIds =
          ArrowTestUtils.extractColumn(boolResult, allocator, "id", Integer.class);
      for (int i = 1; i <= 8; i++) {
        assertTrue(boolIds.contains(i), "Should find row " + i);
      }

      // Test Phrase Query
      System.out.println("\n--- Testing Phrase Query ---");
      QueryTableRequest phraseQuery = new QueryTableRequest();
      phraseQuery.setName(tableName);
      phraseQuery.setK(10);
      phraseQuery.setColumns(Arrays.asList("id", "text"));

      QueryTableRequestFullTextQuery phraseFullTextQuery = new QueryTableRequestFullTextQuery();
      StructuredFtsQuery phraseStructured = new StructuredFtsQuery();
      FtsQuery phraseFtsQuery = new FtsQuery();

      PhraseQuery phrase = new PhraseQuery();
      phrase.setTerms("important document");
      phrase.setColumn("text");
      phrase.setSlop(1); // Allow 1 word between terms
      phraseFtsQuery.setPhrase(phrase);

      phraseStructured.setQuery(phraseFtsQuery);
      phraseFullTextQuery.setStructuredQuery(phraseStructured);
      phraseQuery.setFullTextQuery(phraseFullTextQuery);

      byte[] phraseResult = namespace.queryTable(phraseQuery);
      assertNotNull(phraseResult, "Phrase query result should not be null");

      int phraseRowCount = ArrowTestUtils.countRows(phraseResult, allocator);
      assertEquals(
          4, phraseRowCount, "Should find exactly 4 rows with 'important' near 'document'");

      List<Integer> phraseIds =
          ArrowTestUtils.extractColumn(phraseResult, allocator, "id", Integer.class);
      // Rows 1, 2, 3, 5 should match (row 4 has terms in wrong order)
      assertTrue(phraseIds.contains(1), "Should find row 1");
      assertTrue(phraseIds.contains(2), "Should find row 2");
      assertTrue(phraseIds.contains(3), "Should find row 3");
      assertTrue(phraseIds.contains(5), "Should find row 5");
    } finally {
      TestUtils.dropTable(namespace, tableName);
    }
  }
}
