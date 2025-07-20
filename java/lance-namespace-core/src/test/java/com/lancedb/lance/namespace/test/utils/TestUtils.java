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
package com.lancedb.lance.namespace.test.utils;

import com.lancedb.lance.namespace.LanceRestNamespace;
import com.lancedb.lance.namespace.model.*;

import java.util.Optional;
import java.util.UUID;

/** Common test utilities for Lance Namespace tests. */
public class TestUtils {

  /** Generate a unique table name for testing. */
  public static String generateTableName(String prefix) {
    return prefix + "_" + UUID.randomUUID().toString().replace("-", "_").substring(0, 8);
  }

  /** Generate a unique table name with default prefix. */
  public static String generateTableName() {
    return generateTableName("test_table");
  }

  /** Wait for an index to be created. */
  public static boolean waitForIndex(
      LanceRestNamespace namespace, String tableName, String indexName, int maxSeconds)
      throws InterruptedException {
    IndexListRequest listRequest = new IndexListRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
      IndexListResponse listResponse = namespace.listIndices(listRequest);
      if (listResponse.getIndexes() != null
          && listResponse.getIndexes().stream()
              .anyMatch(idx -> idx.getIndexName().equals(indexName))) {
        return true;
      }
      Thread.sleep(1000);
    }
    return false;
  }

  /** Wait for an index to be fully built with no unindexed rows. */
  public static boolean waitForIndexComplete(
      LanceRestNamespace namespace, String tableName, String indexName, int maxSeconds)
      throws InterruptedException {
    IndexListRequest listRequest = new IndexListRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
      IndexListResponse listResponse = namespace.listIndices(listRequest);
      if (listResponse.getIndexes() != null) {
        Optional<IndexListItemResponse> indexOpt =
            listResponse.getIndexes().stream()
                .filter(idx -> idx.getIndexName().equals(indexName))
                .findFirst();

        if (indexOpt.isPresent()) {
          // Index exists, now check if it's fully built
          IndexStatsRequest statsRequest = new IndexStatsRequest();
          statsRequest.setName(tableName);

          IndexStatsResponse stats = namespace.getIndexStats(statsRequest, indexName);
          if (stats != null
              && stats.getNumUnindexedRows() != null
              && stats.getNumUnindexedRows() == 0) {
            System.out.println("✓ Index " + indexName + " is fully built with 0 unindexed rows");
            return true;
          } else if (stats != null && stats.getNumUnindexedRows() != null) {
            System.out.println(
                "  Waiting for index... " + stats.getNumUnindexedRows() + " rows remaining");
          }
        }
      }
      Thread.sleep(1000);
    }
    return false;
  }

  /** Drop a table and print confirmation. */
  public static DropTableResponse dropTable(LanceRestNamespace namespace, String tableName) {
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setName(tableName);

    DropTableResponse response = namespace.dropTable(dropRequest);
    System.out.println("✓ Table dropped successfully: " + tableName);

    return response;
  }

  /** Count rows in a table. */
  public static long countRows(LanceRestNamespace namespace, String tableName) {
    CountRowsRequest countRequest = new CountRowsRequest();
    countRequest.setName(tableName);
    return namespace.countRows(countRequest);
  }

  /** Create a simple query request for testing. */
  public static QueryRequest createSimpleQuery(String tableName, int k) {
    QueryRequest query = new QueryRequest();
    query.setName(tableName);
    query.setK(k);
    // Add default columns to avoid "no columns selected" error
    query.setColumns(java.util.Arrays.asList("id", "name", "category", "embedding"));
    return query;
  }

  /** Create a vector query request with a specific target value. */
  public static QueryRequest createVectorQuery(String tableName, int k, int dimensions) {
    return createVectorQuery(tableName, k, dimensions, 10.0f);
  }

  /** Create a vector query request with a specific target value for all dimensions. */
  public static QueryRequest createVectorQuery(
      String tableName, int k, int dimensions, float targetValue) {
    QueryRequest query = new QueryRequest();
    query.setName(tableName);
    query.setK(k);

    // Generate a vector with all elements set to targetValue
    // This will find rows where the embedding values are closest to targetValue
    java.util.List<Float> vector = new java.util.ArrayList<>();
    for (int i = 0; i < dimensions; i++) {
      vector.add(targetValue);
    }
    query.setVector(vector);

    // Add default columns to avoid "no columns selected" error
    query.setColumns(java.util.Arrays.asList("id", "name", "category", "embedding"));

    return query;
  }
}
