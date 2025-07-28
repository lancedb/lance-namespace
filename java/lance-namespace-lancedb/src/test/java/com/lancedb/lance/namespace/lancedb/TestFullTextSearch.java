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

import com.lancedb.lance.namespace.model.BooleanQuery;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.FtsQuery;
import com.lancedb.lance.namespace.model.MatchQuery;
import com.lancedb.lance.namespace.model.PhraseQuery;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery;
import com.lancedb.lance.namespace.model.QueryTableRequestVector;
import com.lancedb.lance.namespace.model.StringFtsQuery;
import com.lancedb.lance.namespace.model.StructuredFtsQuery;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Have recall issue that needs to be investigated")
/** Tests for full-text search functionality. */
public class TestFullTextSearch extends LanceDbRestNamespaceTestBase {
  private static final Logger log = LoggerFactory.getLogger(TestFullTextSearch.class);

  @Test
  public void testSimpleFullTextSearch() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Simple Full-Text Search ===");
    String tableName = Utils.generateTableName("test_fts");

    try {
      // Create table with deterministic text data
      log.info("--- Creating table {} with text data ---", tableName);
      byte[] tableData =
          new Utils.TableDataBuilder(allocator)
              .withSchema(Utils.createSchemaWithText(128))
              .withText(1, "This is an important document about search functionality")
              .withText(2, "Another document containing critical information")
              .withText(3, "Sample text with important data and keywords")
              .withText(4, "Regular content without special keywords")
              .withText(5, "document with search terms and important notes")
              .addRows(1, 5)
              .build();

      CreateTableResponse createResponse = Utils.createTable(namespace, tableName, tableData);
      assertNotNull(createResponse, "Create response should not be null");

      // Create FTS index
      log.info("--- Creating FTS index ---");
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setId(Lists.newArrayList(tableName));
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);

      CreateTableIndexResponse ftsIndexResponse = namespace.createTableIndex(ftsIndexRequest);
      assertNotNull(ftsIndexResponse, "FTS index response should not be null");

      Utils.waitForIndexComplete(namespace, tableName, "text_idx", 180);

      // Test 1: Simple string FTS query for "document"
      log.info("--- Test 1: Simple string FTS query for 'document' ---");
      log.info("Expected to find rows 1, 2, 5 (contain 'document')");
      QueryTableRequest stringFtsQuery = new QueryTableRequest();
      stringFtsQuery.setId(Lists.newArrayList(tableName));
      stringFtsQuery.setK(10);
      stringFtsQuery.setColumns(Arrays.asList("id", "name", "text", "category"));

      StringFtsQuery simpleFts = new StringFtsQuery();
      simpleFts.setQuery("document");
      QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
      fullTextQuery.setStringQuery(simpleFts);
      stringFtsQuery.setFullTextQuery(fullTextQuery);

      byte[] ftsResult = namespace.queryTable(stringFtsQuery);
      assertNotNull(ftsResult, "FTS query result should not be null");

      int rowCount = Utils.countRows(ftsResult, allocator);
      assertEquals(3, rowCount, "Should find exactly 3 rows containing 'document'");

      List<Integer> foundIds = Utils.extractColumn(ftsResult, allocator, "id", Integer.class);
      assertTrue(foundIds.contains(1), "Should find row 1");
      assertTrue(foundIds.contains(2), "Should find row 2");
      assertTrue(foundIds.contains(5), "Should find row 5");

      log.info("✓ FTS query returned {} results with IDs: {}", rowCount, foundIds);

      // Test 2: FTS query for "important"
      log.info("--- Test 2: FTS query for 'important' ---");
      log.info("Expected to find rows 1, 3, 5 (contain 'important')");
      QueryTableRequest columnFtsQuery = new QueryTableRequest();
      columnFtsQuery.setId(Lists.newArrayList(tableName));
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

      int importantCount = Utils.countRows(columnFtsResult, allocator);
      assertEquals(3, importantCount, "Should find exactly 3 rows containing 'important'");

      List<Integer> importantIds =
          Utils.extractColumn(columnFtsResult, allocator, "id", Integer.class);
      assertTrue(importantIds.contains(1), "Should find row 1");
      assertTrue(importantIds.contains(3), "Should find row 3");
      assertTrue(importantIds.contains(5), "Should find row 5");

    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testFullTextSearchWithFilter() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Full-Text Search with Filter ===");
    String tableName = Utils.generateTableName("test_fts_filter");

    try {
      // Create table with deterministic text data
      log.info("--- Creating table {} with text data ---", tableName);
      Utils.TableDataBuilder builder =
          new Utils.TableDataBuilder(allocator).withSchema(Utils.createSchemaWithText(128));

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

      Utils.createTable(namespace, tableName, tableData);

      // Create FTS index
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setId(Lists.newArrayList(tableName));
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      namespace.createTableIndex(ftsIndexRequest);
      Utils.waitForIndexComplete(namespace, tableName, "text_idx", 180);

      // FTS with prefilter
      log.info("--- Testing FTS with prefilter (id < 25) ---");
      QueryTableRequest ftsPrefilterQuery = new QueryTableRequest();
      ftsPrefilterQuery.setId(Lists.newArrayList(tableName));
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

      List<Integer> ids = Utils.extractColumn(ftsPrefilterResult, allocator, "id", Integer.class);
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
      Utils.dropTable(namespace, tableName);
    }
  }

  @Test
  public void testStructuredFullTextSearch() throws IOException, InterruptedException {
    skipIfNotConfigured();

    log.info("=== Test: Structured Full-Text Search ===");
    String tableName = Utils.generateTableName("test_structured_fts");

    try {
      // Create table with specific text patterns for boolean query testing
      log.info("--- Creating table {} with text data for structured queries ---", tableName);
      Utils.TableDataBuilder builder =
          new Utils.TableDataBuilder(allocator).withSchema(Utils.createSchemaWithText(128));

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

      Utils.createTable(namespace, tableName, tableData);

      log.info("Created table {} with 15 rows:", tableName);
      log.info("  - Rows 1-5: contain both 'important' AND 'document'");
      log.info("  - Rows 6-8: contain only 'important'");
      log.info("  - Rows 9-11: contain only 'document'");
      log.info("  - Rows 12-15: contain neither");

      // Create FTS index with position for phrase queries
      CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
      ftsIndexRequest.setId(Lists.newArrayList(tableName));
      ftsIndexRequest.setColumn("text");
      ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
      ftsIndexRequest.setWithPosition(true); // Required for phrase queries
      namespace.createTableIndex(ftsIndexRequest);
      Utils.waitForIndexComplete(namespace, tableName, "text_idx", 180);

      // Test Boolean Query
      log.info("--- Testing Boolean Query ---");
      QueryTableRequest booleanQuery = new QueryTableRequest();
      booleanQuery.setId(Lists.newArrayList(tableName));
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

      int boolRowCount = Utils.countRows(boolResult, allocator);
      assertEquals(8, boolRowCount, "Should find exactly 8 rows with 'important'");

      List<Integer> boolIds = Utils.extractColumn(boolResult, allocator, "id", Integer.class);
      for (int i = 1; i <= 8; i++) {
        assertTrue(boolIds.contains(i), "Should find row " + i);
      }

      // Test Phrase Query
      log.info("--- Testing Phrase Query ---");
      QueryTableRequest phraseQuery = new QueryTableRequest();
      phraseQuery.setId(Lists.newArrayList(tableName));
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

      int phraseRowCount = Utils.countRows(phraseResult, allocator);
      assertEquals(
          4, phraseRowCount, "Should find exactly 4 rows with 'important' near 'document'");

      List<Integer> phraseIds = Utils.extractColumn(phraseResult, allocator, "id", Integer.class);
      // Rows 1, 2, 3, 5 should match (row 4 has terms in wrong order)
      assertTrue(phraseIds.contains(1), "Should find row 1");
      assertTrue(phraseIds.contains(2), "Should find row 2");
      assertTrue(phraseIds.contains(3), "Should find row 3");
      assertTrue(phraseIds.contains(5), "Should find row 5");
    } finally {
      Utils.dropTable(namespace, tableName);
    }
  }
}
