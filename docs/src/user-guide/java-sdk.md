# Java SDK for LanceDB Cloud/Enterprise (Experimental)

This guide explains how to use the Java SDK to interact with LanceDB Cloud and Enterprise deployments.

## Installation

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>com.lancedb</groupId>
    <artifactId>lance-namespace-core</artifactId>
    <version>0.0.1</version>
</dependency>
```

The artifact is available on [Maven Central](https://central.sonatype.com/artifact/com.lancedb/lance-namespace-core).

## Configuration and Initialization

### LanceDB Cloud

For LanceDB Cloud, use the following initialization approach:

```java
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.LanceRestNamespace;
import java.util.HashMap;
import java.util.Map;

// If your DB url is db://example-db, then your db here is example-db
String lancedbDatabase = "your_database_name";
String lancedbApiKey = "your_lancedb_cloud_api_key";

Map<String, String> config = new HashMap<>();
config.put("headers.x-lancedb-database", lancedbDatabase);
config.put("headers.x-api-key", lancedbApiKey);

ApiClient apiClient = new ApiClient();
String baseUrl = String.format("https://%s.us-east-1.api.lancedb.com", lancedbDatabase);
apiClient.setBasePath(baseUrl);
LanceRestNamespace namespace = new LanceRestNamespace(apiClient, config);
```

### LanceDB Enterprise

For Enterprise deployments, use your VPC endpoint:

```java
// Your top level folder under your cloud bucket, e.g. s3://your-bucket/your-top-dir/
String lancedbDatabase = "your-top-dir";
String lancedbApiKey = "your_lancedb_enterprise_api_key";
// Your enterprise connection url
String lancedbHostOverride = "http://<vpc_endpoint_dns_name>:80";

Map<String, String> config = new HashMap<>();
config.put("headers.x-lancedb-database", lancedbDatabase);
config.put("headers.x-api-key", lancedbApiKey);

ApiClient apiClient = new ApiClient();
apiClient.setBasePath(lancedbHostOverride);
LanceRestNamespace namespace = new LanceRestNamespace(apiClient, config);
```

## Supported Endpoints

The Java SDK supports the following endpoints. Full API documentation is available at [javadoc.io](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html).

### Table Operations
- **countRows** - Count the number of rows in a table
- **createTable** - Create a new table with Arrow data
- **describeTable** - Get table metadata and schema
- **dropTable** - Delete a table
- **insertTable** - Insert data into a table
- **updateTable** - Update rows in a table
- **deleteFromTable** - Delete rows from a table
- **mergeInsertTable** - Upsert operation (update or insert)
- **queryTable** - Vector similarity search

### Index Operations
- **createIndex** - Create a vector index
- **createScalarIndex** - Create a scalar index
- **listIndices** - List all indices on a table
- **getIndexStats** - Get statistics for a specific index

## Request and Response Structure

!!! note "Response Fields"
    The response structures contain fields like `location`, `name`, `namespace`, and `properties` that are part of the lance-namespace protocol. These fields will be empty in responses and should be ignored.

!!! note "Request Fields"
    The request structures contain field `name` which refers to the table name and is required. The `namespace` field is optional; if provided, the resulting table name will be in the format `namespace.name`.

For detailed request/response structures, refer to the [Apache Client documentation](https://javadoc.io/doc/com.lancedb/lance-namespace-apache-client/latest/index.html).

## Examples

### Creating a Table

LanceDB uses Apache Arrow format for data exchange. Arrow provides:
- Cross-language compatibility
- Rich data type support including nested types and tensors

```java
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.types.pojo.*;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

// Define Arrow schema
Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
Field nameField = new Field("name", FieldType.nullable(new ArrowType.Utf8()), null);
Field embeddingField = new Field(
    "embedding",
    FieldType.nullable(new ArrowType.FixedSizeList(128)),
    Arrays.asList(
        new Field("item", 
            FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)), 
            null)
    )
);
Schema schema = new Schema(Arrays.asList(idField, nameField, embeddingField));

// Create data
try (BufferAllocator allocator = new RootAllocator();
     VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
    
    IntVector idVector = (IntVector) root.getVector("id");
    VarCharVector nameVector = (VarCharVector) root.getVector("name");
    FixedSizeListVector vectorVector = (FixedSizeListVector) root.getVector("embedding");
    
    // Set row count
    root.setRowCount(3);
    
    // Populate data
    for (int i = 0; i < 3; i++) {
        idVector.setSafe(i, i + 1);
        nameVector.setSafe(i, ("User" + i).getBytes(StandardCharsets.UTF_8));
    }
    
    // Populate embeddings
    Float4Vector dataVector = (Float4Vector) vectorVector.getDataVector();
    vectorVector.allocateNew();
    
    for (int row = 0; row < 3; row++) {
        vectorVector.setNotNull(row);
        for (int dim = 0; dim < 128; dim++) {
            int index = row * 128 + dim;
            dataVector.setSafe(index, (float) Math.random());
        }
    }
    
    // Set value counts
    idVector.setValueCount(3);
    nameVector.setValueCount(3);
    dataVector.setValueCount(3 * 128);
    vectorVector.setValueCount(3);
    
    // Serialize to Arrow IPC format
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
    }
    
    // Create table
    byte[] arrowIpcData = out.toByteArray();
    CreateTableResponse response = namespace.createTable("my_table", arrowIpcData);
}
```

### Querying a Table

Query results are returned in Arrow File format. Use `ArrowFileReader` to read the results.

#### Vector Search

```java
import com.lancedb.lance.namespace.model.QueryRequest;
import com.lancedb.lance.namespace.model.QueryRequestVector;
import org.apache.arrow.vector.ipc.ArrowFileReader;
import org.apache.arrow.vector.ipc.ArrowBlock;
import org.apache.arrow.vector.ipc.SeekableReadChannel;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Create query request
QueryRequest queryRequest = new QueryRequest();
queryRequest.setName("my_table");

// Single vector search (most common use case)
List<Float> queryVector = new ArrayList<>();
for (int i = 0; i < 128; i++) {
    queryVector.add((float) Math.random());
}

// For single vector, you can set it directly
queryRequest.setVector(queryVector);
queryRequest.setK(5);  // Get top 5 results

// Specify columns to return
queryRequest.setColumns(Arrays.asList("id", "name", "embedding"));

// Execute query
byte[] queryResult = namespace.queryTable(queryRequest);

// Parse Arrow result
try (BufferAllocator allocator = new RootAllocator();
     ArrowFileReader reader = new ArrowFileReader(
         new SeekableReadChannel(Channels.newChannel(new ByteArrayInputStream(queryResult))), 
         allocator)) {
    
    // Process results
    for (ArrowBlock block : reader.getRecordBlocks()) {
        reader.loadRecordBatch(block);
        VectorSchemaRoot root = reader.getVectorSchemaRoot();
        
        // Access data from vectors
        IntVector idVector = (IntVector) root.getVector("id");
        VarCharVector nameVector = (VarCharVector) root.getVector("name");
        
        for (int i = 0; i < root.getRowCount(); i++) {
            int id = idVector.get(i);
            String name = new String(nameVector.get(i), StandardCharsets.UTF_8);
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }
}
```

##### Fast Search Option

Always use `fast_search=true` if possible. When enabled, the query will only search indexed data, providing better performance and avoiding potential data egress costs. When disabled, it will scan the entire unindexed portion of the column from storage, which can be slow and expensive.

```java
QueryRequest queryRequest = new QueryRequest();
queryRequest.setName("my_table");
queryRequest.setVector(queryVector);
queryRequest.setK(10);

queryRequest.setFastSearch(true); // Recommended for better performance
```

##### SQL Filters

You can use SQL filters with or without vector search:

```java
// SQL filter only (no vector search)
QueryRequest filterOnlyQuery = new QueryRequest();
filterOnlyQuery.setName("my_table");
filterOnlyQuery.setK(10);
filterOnlyQuery.setFilter("category = 'electronics' AND price < 500");
filterOnlyQuery.setFastSearch(true);

// Vector search with SQL filter
QueryRequest vectorWithFilter = new QueryRequest();
vectorWithFilter.setName("my_table");
vectorWithFilter.setVector(queryVector);
vectorWithFilter.setK(10);
vectorWithFilter.setFilter("status = 'active' AND price < 1000");
vectorWithFilter.setFastSearch(true);
```

##### Prefilter vs Postfilter

When combining vector search with filters, use `prefilter` to control the order of operations:
- `prefilter=true`: Apply filter BEFORE vector search (faster when filter is selective)
- `prefilter=false`: Apply filter AFTER vector search (better when filter matches many rows)

```java
// Prefiltering - filter first, then search vectors
QueryRequest prefilterQuery = new QueryRequest();
prefilterQuery.setName("my_table");
prefilterQuery.setVector(queryVector);
prefilterQuery.setK(10);
prefilterQuery.setFilter("status = 'active'");
prefilterQuery.setPrefilter(true);
prefilterQuery.setFastSearch(true);

// Postfiltering - search vectors first, then filter (default)
QueryRequest postfilterQuery = new QueryRequest();
postfilterQuery.setName("my_table");
postfilterQuery.setVector(queryVector);
postfilterQuery.setK(10);
postfilterQuery.setFilter("category = 'electronics'");
postfilterQuery.setPrefilter(false);
postfilterQuery.setFastSearch(true);
```

#### Full-Text Search

LanceDB supports full-text search on string columns. First create an FTS index, then use text queries:

```java
// Create FTS index
CreateIndexRequest ftsIndexRequest = new CreateIndexRequest();
ftsIndexRequest.setName("my_table");
ftsIndexRequest.setColumn("content");
ftsIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.FTS);

CreateIndexResponse ftsResponse = namespace.createIndex(ftsIndexRequest);
waitForIndexComplete("my_table", "content_idx", 30);

// Perform full-text search
import com.lancedb.lance.namespace.model.StringFtsQuery;
import com.lancedb.lance.namespace.model.QueryRequestFullTextQuery;

QueryRequest textQuery = new QueryRequest();
textQuery.setName("my_table");
textQuery.setK(10);

// Simple string query
QueryRequestFullTextQuery fullTextQuery = new QueryRequestFullTextQuery();
StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("search terms"); // The search query
fts.setColumns(Arrays.asList("content", "title")); // Optional: columns to search
fullTextQuery.setStringQuery(fts);
textQuery.setFullTextQuery(fullTextQuery);

byte[] results = namespace.queryTable(textQuery);
```

##### Advanced: Structured Full-Text Search

The Java SDK now supports complex structured full-text queries including boolean queries, phrase queries, and boosted queries:

```java
import com.lancedb.lance.namespace.model.*;

// Boolean query example: (must contain "important" AND should contain "feature" OR "update")
QueryRequest structuredQuery = new QueryRequest();
structuredQuery.setName("my_table");
structuredQuery.setK(10);

QueryRequestFullTextQuery fullTextQuery = new QueryRequestFullTextQuery();
StructuredFtsQuery structured = new StructuredFtsQuery();
FtsQuery ftsQuery = new FtsQuery();

// Create a boolean query
BooleanQuery boolQuery = new BooleanQuery();

// Must clause: documents must contain "important"
FtsQuery mustQuery = new FtsQuery();
MatchQuery mustMatch = new MatchQuery();
mustMatch.setTerms("important");
mustMatch.setColumn("content");
mustQuery.setMatch(mustMatch);
boolQuery.setMust(Arrays.asList(mustQuery));

// Should clauses: documents should contain "feature" OR "update" 
FtsQuery shouldQuery1 = new FtsQuery();
MatchQuery shouldMatch1 = new MatchQuery();
shouldMatch1.setTerms("feature");
shouldQuery1.setMatch(shouldMatch1);

FtsQuery shouldQuery2 = new FtsQuery();
MatchQuery shouldMatch2 = new MatchQuery();
shouldMatch2.setTerms("update");
shouldQuery2.setMatch(shouldMatch2);
boolQuery.setShould(Arrays.asList(shouldQuery1, shouldQuery2));

// Must NOT clause: exclude documents with "deprecated"
FtsQuery mustNotQuery = new FtsQuery();
MatchQuery mustNotMatch = new MatchQuery();
mustNotMatch.setTerms("deprecated");
mustNotQuery.setMatch(mustNotMatch);
boolQuery.setMustNot(Arrays.asList(mustNotQuery));

ftsQuery.setBoolean(boolQuery);
structured.setQuery(ftsQuery);
fullTextQuery.setStructuredQuery(structured);
structuredQuery.setFullTextQuery(fullTextQuery);

byte[] boolResults = namespace.queryTable(structuredQuery);
```

Other supported query types include:
- **Phrase Query**: Find exact phrases with optional slop (word distance)
- **Boost Query**: Boost documents matching certain criteria
- **Multi-Match Query**: Search across multiple fields with different weights

#### Hybrid Search

Combining vector similarity search with full-text search often provides more relevant results than using either method alone. This is especially useful for semantic search applications where both conceptual similarity and keyword matching are important.

```java
// Hybrid search: vector + text
QueryRequest hybridQuery = new QueryRequest();
hybridQuery.setName("my_table");

// Vector search component
List<Float> queryVector = generateQueryVector(); // Your vector
hybridQuery.setVector(queryVector);
hybridQuery.setK(10);

// Text search component
QueryRequestFullTextQuery fullTextQuery = new QueryRequestFullTextQuery();
StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("search terms");
fts.setColumns(Arrays.asList("content", "title")); // Optional: columns to search
fullTextQuery.setStringQuery(fts);
hybridQuery.setFullTextQuery(fullTextQuery);

// Optional: Add filters
hybridQuery.setFilter("date > '2024-01-01'");
hybridQuery.setPrefilter(true);
hybridQuery.setFastSearch(true);

byte[] hybridResults = namespace.queryTable(hybridQuery);
```

### Creating a Vector Index

LanceDB automatically optimizes index parameters based on best practices for your workload.

**Best Practices:**
- **Index Type**: Use `IVF_PQ` for production workloads (default)
- **Metric Type**: 
  - Use `L2` for normalized vectors (faster computation)
  - Use `COSINE` for non-normalized vectors (more compute-intensive)
- Other parameters are automatically tuned by the system

```java
import com.lancedb.lance.namespace.model.CreateIndexRequest;

// Create vector index
CreateIndexRequest indexRequest = new CreateIndexRequest();
indexRequest.setName("my_table");
indexRequest.setColumn("embedding");
indexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.IVF_PQ);
indexRequest.setMetricType(CreateIndexRequest.MetricTypeEnum.L2);

CreateIndexResponse response = namespace.createIndex(indexRequest);
```

!!! note "Asynchronous Index Creation"
    LanceDB Cloud/Enterprise handles index creation asynchronously. Use the `listIndices` and `getIndexStats` operations to monitor index creation progress.

### Creating a Scalar Index

Scalar indexes improve query performance when using filters.

**Index Type Selection:**
- **BITMAP Index**: Best for columns with low cardinality (< few thousand unique values)
  - Excellent search performance
  - Relatively small index size
- **BTREE Index**: Use when unique values are high

!!! tip "Optimization Tip"
    To enable BITMAP indexing on high-cardinality columns, reduce data precision:
    - Round floating-point values
    - Reduce timestamp precision (e.g., second → day)

```java
import com.lancedb.lance.namespace.model.CreateIndexRequest;

// Create scalar index
CreateIndexRequest scalarIndexRequest = new CreateIndexRequest();
scalarIndexRequest.setName("my_table");
scalarIndexRequest.setColumn("name");
scalarIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.BITMAP);

CreateIndexResponse scalarResponse = namespace.createScalarIndex(scalarIndexRequest);
```

!!! note "Asynchronous Index Creation"
    Similar to vector index creation, scalar index creation is also asynchronous. Use `listIndices` and `getIndexStats` to monitor index creation progress.

### List Indices

List all indices on a table:

```java
import com.lancedb.lance.namespace.model.IndexListRequest;
import com.lancedb.lance.namespace.model.IndexListResponse;

IndexListRequest listRequest = new IndexListRequest();
listRequest.setName("my_table");

IndexListResponse listResponse = namespace.listIndices(listRequest);
if (listResponse.getIndexes() != null) {
    for (IndexListItemResponse index : listResponse.getIndexes()) {
        System.out.println("Index: " + index.getIndexName());
        System.out.println("  Columns: " + index.getColumns());
        System.out.println("  Index Type: " + index.getIndexType());
    }
}
```

### Get Index Statistics

!!! info "Auto-reindexing"
    LanceDB Cloud/Enterprise automatically reindexes columns when table data is modified. Use `getNumUnindexedRows()` to check whether the column is fully indexed.

Get detailed statistics for a specific index:
```java
import com.lancedb.lance.namespace.model.IndexStatsRequest;
import com.lancedb.lance.namespace.model.IndexStatsResponse;

IndexStatsRequest statsRequest = new IndexStatsRequest();
statsRequest.setName("my_table");

// Get stats for specific index (index name format: <column_name>_idx)
IndexStatsResponse stats = namespace.getIndexStats(statsRequest, "embedding_idx");

System.out.println("Index Type: " + stats.getIndexType());
System.out.println("Distance Type: " + stats.getDistanceType());
System.out.println("Indexed Rows: " + stats.getNumIndexedRows());
System.out.println("Unindexed Rows: " + stats.getNumUnindexedRows());
System.out.println("Index Metadata: " + stats.getIndexMetadata());
```

### Monitoring Index Creation

!!! important "Wait for Index Completion"
    Index creation is asynchronous. Always wait for indexes to be fully built before running queries to ensure optimal performance and avoid scanning unindexed data.

Here's a helper method that combines `listIndices` and `getIndexStats` to monitor index creation:

```java
import java.util.Optional;

/**
 * Wait for index to be fully built with no unindexed rows
 * @param tableName The name of the table
 * @param indexName The expected index name (usually column_name + "_idx")
 * @param maxSeconds Maximum seconds to wait
 * @return true if index is complete, false if timeout
 */
private boolean waitForIndexComplete(String tableName, String indexName, int maxSeconds) 
    throws InterruptedException {
    
    IndexListRequest listRequest = new IndexListRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
        IndexListResponse listResponse = namespace.listIndices(listRequest);
        if (listResponse.getIndexes() != null) {
            Optional<IndexListItemResponse> indexOpt = listResponse.getIndexes().stream()
                .filter(idx -> idx.getIndexName().equals(indexName))
                .findFirst();
            
            if (indexOpt.isPresent()) {
                IndexStatsRequest statsRequest = new IndexStatsRequest();
                statsRequest.setName(tableName);
                IndexStatsResponse stats = namespace.getIndexStats(statsRequest, indexName);
                if (stats != null && stats.getNumUnindexedRows() != null 
                    && stats.getNumUnindexedRows() == 0) {
                    return true;
                }
            }
        }
        Thread.sleep(1000);
    }
    return false;
}

// Usage example
CreateIndexResponse response = namespace.createIndex(indexRequest);
boolean indexReady = waitForIndexComplete("my_table", "embedding_idx", 60);
if (!indexReady) {
    System.out.println("Warning: Index creation timed out");
}
```

### Updating and Deleting Data

```java
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;

// Update rows
UpdateTableRequest updateRequest = new UpdateTableRequest();
updateRequest.setName("my_table");
updateRequest.setPredicate("id > 100");
List<List<String>> updates = new ArrayList<>();
updates.add(Arrays.asList("status", "'active'"));  // Set status = 'active'
updateRequest.setUpdates(updates);

UpdateTableResponse updateResponse = namespace.updateTable(updateRequest);

// Delete rows
DeleteFromTableRequest deleteRequest = new DeleteFromTableRequest();
deleteRequest.setName("my_table");
deleteRequest.setPredicate("status = 'inactive'");

DeleteFromTableResponse deleteResponse = namespace.deleteFromTable(deleteRequest);
```

### Merge Insert (Upsert)

```java
import com.lancedb.lance.namespace.model.MergeInsertTableRequest;

// Prepare data (similar to create table)
byte[] arrowIpcData = prepareArrowData();

// Create merge request
MergeInsertTableRequest mergeRequest = new MergeInsertTableRequest();
mergeRequest.setName("my_table");

// Perform merge insert
MergeInsertTableResponse response = namespace.mergeInsertTable(
    mergeRequest,
    arrowIpcData,
    "id",    // match on id column
    true,    // when_matched_update_all
    true     // when_not_matched_insert_all
);

System.out.println("Updated rows: " + response.getNumUpdatedRows());
System.out.println("Inserted rows: " + response.getNumInsertedRows());
```


## Additional Resources

- [LanceDB Documentation](https://lancedb.github.io/lancedb/)
- [API Javadoc](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html)
- [Apache Arrow Java Documentation](https://arrow.apache.org/docs/java/)