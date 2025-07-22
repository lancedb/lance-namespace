# Java SDK

This guide explains how to use the Java SDK to interact with a Lance namespace.

## Installation

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>com.lancedb</groupId>
    <artifactId>lance-namespace-core</artifactId>
    <version>0.0.2</version>
</dependency>
```

The artifact is available on [Maven Central](https://central.sonatype.com/artifact/com.lancedb/lance-namespace-core).

## Configuration and Initialization

### Lance REST Catalog

Use the following initialization approach:

```java
import com.lancedb.lance.namespace.LanceRestNamespace;

ApiClient apiClient = new ApiClient();
apiClient.setBasePath("https://my-lance-namespace.com");
LanceRestNamespace namespace = new LanceRestNamespace(apiClient, config);
```

## Supported Endpoints

The Java SDK supports the following endpoints. Full API documentation is available at 
[javadoc.io](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html).

### Table Operations
- **countTableRows** - Count the number of rows in a table
- **createTable** - Create a new table with Arrow data
- **describeTable** - Get table metadata and schema
- **dropTable** - Delete a table
- **insertIntoTable** - Insert data into a table
- **updateTable** - Update rows in a table
- **deleteFromTable** - Delete rows from a table
- **mergeInsertIntoTable** - Upsert operation (update or insert)
- **queryTable** - Vector similarity search

### Index Operations
- **createTableIndex** - Create a vector index
- **createTableIndex** - Create a vector or scalar index
- **listTableIndices** - List all indices on a table
- **describeTableIndexStats** - Get statistics for a specific index

## Request and Response Structure

!!! note "Response Fields"
    The response structures contain fields like `location`, `name`, `namespace`, and `properties` that are part of the lance-namespace protocol. These fields will be empty in responses and should be ignored.

!!! note "Request Fields"
    The request structures contain field `name` which refers to the table name and is required. The `namespace` field is optional; if provided, the resulting table name will be in the format `namespace.name`.

For detailed request/response structures, refer to the [Apache Client documentation](https://javadoc.io/doc/com.lancedb/lance-namespace-apache-client/latest/index.html).

## Examples

### Creating a Table


Lance Namespace uses Apache Arrow format for data exchange. Here's a simple example creating a table with ID and embedding columns:

```java
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.types.pojo.*;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.util.Arrays;

// Define schema: id and embedding columns
Field idField = new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null);
Field embeddingField = new Field(
    "embedding",
    FieldType.nullable(new ArrowType.FixedSizeList(128)),  // 128-dimensional vectors
    Arrays.asList(
        new Field("item", 
            FieldType.nullable(new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE)), 
            null)
    )
);
Schema schema = new Schema(Arrays.asList(idField, embeddingField));

// Create table with 1000 rows
try (BufferAllocator allocator = new RootAllocator();
     VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
    
    int numRows = 1000;
    root.setRowCount(numRows);
    
    // Get vectors
    IntVector idVector = (IntVector) root.getVector("id");
    FixedSizeListVector embeddingVector = (FixedSizeListVector) root.getVector("embedding");
    Float4Vector dataVector = (Float4Vector) embeddingVector.getDataVector();
    
    // Allocate memory
    embeddingVector.allocateNew();
    
    // Populate data
    for (int i = 0; i < numRows; i++) {
        // Set ID
        idVector.setSafe(i, i + 1);
        
        // Set embedding vector
        embeddingVector.setNotNull(i);
        for (int dim = 0; dim < 128; dim++) {
            int index = i * 128 + dim;
            // Example: random values (in practice, use your actual embeddings)
            dataVector.setSafe(index, (float) Math.random());
        }
    }
    
    // Set value counts
    idVector.setValueCount(numRows);
    dataVector.setValueCount(numRows * 128);
    embeddingVector.setValueCount(numRows);
    
    // Serialize to Arrow IPC format
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
    }
    
    // Create table in LanceDB
    byte[] arrowData = out.toByteArray();
    
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setName("my_vectors");
    CreateTableResponse response = namespace.createTable(createRequest, arrowData);
    System.out.println("Created table with " + numRows + " rows");
}
```

For more complex schemas (e.g., with text fields for full-text search, categorical fields for filtering), you can add additional fields to the schema as needed.

### Querying a Table

Query results are returned in Arrow File format. Use `ArrowFileReader` to read the results.

#### Vector Search

```java
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestVector;
import org.apache.arrow.vector.ipc.ArrowFileReader;
import org.apache.arrow.memory.ArrowBuf;
import org.apache.arrow.vector.ipc.message.ArrowBlock;
import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Find similar items by vector
QueryTableRequest queryRequest = new QueryTableRequest();
queryRequest.setName("my_vectors");
queryRequest.setK(10);  // Get top 10 results

// Create query vector (in practice, this would be your actual query embedding)
List<Float> queryVector = new ArrayList<>();
for (int i = 0; i < 128; i++) {
    queryVector.add((float) Math.random());
}

QueryTableRequestVector vectorQuery = new QueryTableRequestVector();
vectorQuery.setSingleVector(queryVector);
queryRequest.setVector(vectorQuery);

// REQUIRED: Specify columns to return
queryRequest.setColumns(Arrays.asList("id", "embedding"));

// Optional: Set fast_search for better performance (only searches indexed data)
queryRequest.setFastSearch(true);

// Execute query
byte[] queryResult = namespace.queryTable(queryRequest);

// Parse results
try (BufferAllocator allocator = new RootAllocator();
     ArrowFileReader reader = new ArrowFileReader(
         new SeekableByteChannel() { /* See ArrowTestUtils for full implementation */ }, 
         allocator)) {
    
    reader.loadRecordBatch(reader.getRecordBlocks().get(0));
    VectorSchemaRoot root = reader.getVectorSchemaRoot();
    
    IntVector idVector = (IntVector) root.getVector("id");
    
    System.out.println("Found " + root.getRowCount() + " similar vectors:");
    for (int i = 0; i < Math.min(5, root.getRowCount()); i++) {
        System.out.println("  ID: " + idVector.get(i));
    }
}
```

##### Fast Search Option

Always use `fast_search=true` if possible. When enabled, the query will only search indexed data, providing better performance and avoiding potential data egress costs. When disabled, it will scan the entire unindexed portion of the column from storage, which can be slow and expensive.

```java
QueryTableRequest queryRequest = new QueryTableRequest();
queryRequest.setName("my_table");

QueryTableRequestVector vectorQuery = new QueryTableRequestVector();
vectorQuery.setSingleVector(queryVector);
queryRequest.setVector(vectorQuery);
queryRequest.setK(10);

queryRequest.setFastSearch(true); // Recommended for better performance
```

##### SQL Filters

You can use SQL filters with or without vector search:

```java
// Example 1: Filter-only query (no vector search)
QueryTableRequest filterQuery = new QueryTableRequest();
filterQuery.setName("my_table");
filterQuery.setK(20);  // Maximum results to return
filterQuery.setFilter("id >= 100 AND id < 200");
filterQuery.setColumns(Arrays.asList("id")); // Required!

byte[] filterResult = namespace.queryTable(filterQuery);

// Example 2: Vector search with filter
QueryTableRequest vectorWithFilter = new QueryTableRequest();
vectorWithFilter.setName("my_vectors");
vectorWithFilter.setK(5);

// Create query vector
List<Float> queryVector = new ArrayList<>();
for (int i = 0; i < 128; i++) {
    queryVector.add((float) Math.random());
}
QueryTableRequestVector vectorQuery2 = new QueryTableRequestVector();
vectorQuery2.setSingleVector(queryVector);
vectorWithFilter.setVector(vectorQuery2);

// Only search within specific ID range
vectorWithFilter.setFilter("id >= 500 AND id < 600");
vectorWithFilter.setColumns(Arrays.asList("id"));

byte[] filteredVectorResult = namespace.queryTable(vectorWithFilter);

// Example 3: Complex filter expressions
QueryTableRequest complexFilter = new QueryTableRequest();
complexFilter.setName("my_table");
complexFilter.setK(100);
complexFilter.setFilter("id >= 10 AND id <= 90");
complexFilter.setColumns(Arrays.asList("id"));

// Supported SQL operators:
// - Comparison: =, !=, <, >, <=, >=
// - Logical: AND, OR, NOT
// - IN: category IN ('category1', 'category2')
// - String: LIKE for pattern matching
```

##### Prefilter vs Postfilter

When combining vector search with filters, use `prefilter` to control the order of operations:
- `prefilter=true`: Apply filter BEFORE vector search (faster when filter is selective)
- `prefilter=false`: Apply filter AFTER vector search (better when filter matches many rows)

```java
// Prefiltering - filter first, then search vectors
QueryTableRequest prefilterQuery = new QueryTableRequest();
prefilterQuery.setName("my_table");
QueryTableRequestVector vectorQuery3 = new QueryTableRequestVector();
vectorQuery3.setSingleVector(queryVector);
prefilterQuery.setVector(vectorQuery3);
prefilterQuery.setK(10);
prefilterQuery.setFilter("status = 'active'");
prefilterQuery.setPrefilter(true);
prefilterQuery.setFastSearch(true);

// Postfiltering - search vectors first, then filter (default)
QueryTableRequest postfilterQuery = new QueryTableRequest();
postfilterQuery.setName("my_table");
QueryTableRequestVector vectorQuery4 = new QueryTableRequestVector();
vectorQuery4.setSingleVector(queryVector);
postfilterQuery.setVector(vectorQuery4);
postfilterQuery.setK(10);
postfilterQuery.setFilter("category = 'electronics'");
postfilterQuery.setPrefilter(false);
postfilterQuery.setFastSearch(true);
```

#### Full-Text Search

Lance supports full-text search on string columns. First create an FTS index, then use text queries:

```java
// Step 1: Create table with text content (add text columns to your schema)

// Step 2: Create FTS index
CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
ftsIndexRequest.setName("documents");
ftsIndexRequest.setColumn("content");
ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
// Set withPosition=true if you plan to use PhraseQuery
ftsIndexRequest.setWithPosition(true);

CreateTableIndexResponse ftsResponse = namespace.createTableIndex(ftsIndexRequest);
// Wait for index to be built
boolean indexReady = waitForIndexComplete("documents", "content_idx", 30);

// Step 3: Perform full-text search
import com.lancedb.lance.namespace.model.StringFtsQuery;
import com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery;

// Example 1: Simple keyword search
QueryTableRequest textQuery = new QueryTableRequest();
textQuery.setName("documents");
textQuery.setK(5);
textQuery.setColumns(Arrays.asList("id", "title", "content")); // Required!

QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("machine learning");  // Search for documents about machine learning
fullTextQuery.setStringQuery(fts);
textQuery.setFullTextQuery(fullTextQuery);

byte[] results = namespace.queryTable(textQuery);
// Expected: Documents containing "machine" and/or "learning"

// Example 2: Search specific columns
StringFtsQuery columnSearch = new StringFtsQuery();
columnSearch.setQuery("neural networks");
columnSearch.setColumns(Arrays.asList("content")); // Only search in content column
fullTextQuery.setStringQuery(columnSearch);

// Example 3: Full-text search with filter
QueryTableRequest ftsWithFilter = new QueryTableRequest();
ftsWithFilter.setName("documents");
ftsWithFilter.setK(10);
ftsWithFilter.setFilter("id <= 3");  // Only search in first 3 documents
ftsWithFilter.setColumns(Arrays.asList("id", "title", "content"));

StringFtsQuery filteredFts = new StringFtsQuery();
filteredFts.setQuery("learning");
QueryTableRequestFullTextQuery filteredFullText = new QueryTableRequestFullTextQuery();
filteredFullText.setStringQuery(filteredFts);
ftsWithFilter.setFullTextQuery(filteredFullText);

byte[] filteredResults = namespace.queryTable(ftsWithFilter);
// Expected: Documents 1-3 that contain "learning"
```

##### Advanced: Structured Full-Text Search

The Java SDK supports complex structured full-text queries including boolean queries and phrase queries:

```java
import com.lancedb.lance.namespace.model.*;

// Example 1: Boolean Query - Complex search logic
QueryTableRequest booleanSearchQuery = new QueryTableRequest();
booleanSearchQuery.setName("documents");
booleanSearchQuery.setK(10);
booleanSearchQuery.setColumns(Arrays.asList("id", "title", "content"));

// Create structured query wrapper
QueryTableRequestFullTextQuery fullTextQuery = new QueryTableRequestFullTextQuery();
StructuredFtsQuery structured = new StructuredFtsQuery();
FtsQuery ftsQuery = new FtsQuery();

// Boolean query: MUST contain "learning" AND (SHOULD contain "machine" OR "deep")
// Note: SHOULD clauses are optional when MUST clauses are present - they only affect ranking
BooleanQuery boolQuery = new BooleanQuery();

// MUST clause: documents must contain "learning"
FtsQuery mustQuery = new FtsQuery();
MatchQuery mustMatch = new MatchQuery();
mustMatch.setTerms("learning");
mustMatch.setColumn("content");
mustQuery.setMatch(mustMatch);
boolQuery.setMust(Arrays.asList(mustQuery));

// SHOULD clauses: prefer documents with "machine" or "deep"
FtsQuery shouldQuery1 = new FtsQuery();
MatchQuery shouldMatch1 = new MatchQuery();
shouldMatch1.setTerms("machine");
shouldMatch1.setColumn("content");
shouldQuery1.setMatch(shouldMatch1);

FtsQuery shouldQuery2 = new FtsQuery();
MatchQuery shouldMatch2 = new MatchQuery();
shouldMatch2.setTerms("deep");
shouldMatch2.setColumn("content");
shouldQuery2.setMatch(shouldMatch2);
boolQuery.setShould(Arrays.asList(shouldQuery1, shouldQuery2));

// Optional: MUST NOT clause
FtsQuery mustNotQuery = new FtsQuery();
MatchQuery mustNotMatch = new MatchQuery();
mustNotMatch.setTerms("beginner");  // Exclude beginner content
mustNotQuery.setMatch(mustNotMatch);
boolQuery.setMustNot(Arrays.asList(mustNotQuery));

// Set the boolean query
ftsQuery.setBoolean(boolQuery);
structured.setQuery(ftsQuery);
fullTextQuery.setStructuredQuery(structured);
booleanSearchQuery.setFullTextQuery(fullTextQuery);

byte[] boolResults = namespace.queryTable(booleanSearchQuery);
// Expected: Documents containing "learning" (required) and preferably "machine" or "deep"

// Example 2: Phrase Query - Find exact phrases
// IMPORTANT: PhraseQuery requires the FTS index to be created with withPosition=true
QueryTableRequest phraseSearchQuery = new QueryTableRequest();
phraseSearchQuery.setName("documents");
phraseSearchQuery.setK(5);
phraseSearchQuery.setColumns(Arrays.asList("id", "title", "content"));

// Create phrase query
QueryTableRequestFullTextQuery phraseFullText = new QueryTableRequestFullTextQuery();
StructuredFtsQuery phraseStructured = new StructuredFtsQuery();
FtsQuery phraseFtsQuery = new FtsQuery();

PhraseQuery phrase = new PhraseQuery();
phrase.setTerms("machine learning");  // Find exact phrase
phrase.setColumn("content");
phrase.setSlop(1);  // Allow 1 word between "machine" and "learning"
phraseFtsQuery.setPhrase(phrase);

phraseStructured.setQuery(phraseFtsQuery);
phraseFullText.setStructuredQuery(phraseStructured);
phraseSearchQuery.setFullTextQuery(phraseFullText);

byte[] phraseResults = namespace.queryTable(phraseSearchQuery);
// Expected: Documents with "machine learning" or "machine [word] learning"
// Note: Phrase queries search for terms in the specified order. 
// "learning machine" would NOT match this query.
```

!!! warning "PhraseQuery Requirements"
    PhraseQuery requires the FTS index to be created with `withPosition=true`. If you attempt to use PhraseQuery on an index created without position information, you will receive an error: "position is not found but required for phrase queries". 
    
    Always create your FTS index with position enabled if you plan to use phrase searches:
    ```java
    CreateTableIndexRequest ftsIndexRequest = new CreateTableIndexRequest();
    ftsIndexRequest.setName("documents");
    ftsIndexRequest.setColumn("content");
    ftsIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.FTS);
    ftsIndexRequest.setWithPosition(true);  // Required for PhraseQuery
    ```

### Creating a Vector Index

Lance automatically optimizes index parameters based on best practices for your workload.

**Best Practices:**
- **Index Type**: Use `IVF_PQ` for production workloads (default)
- **Metric Type**: 
  - Use `L2` for normalized vectors (faster computation)
  - Use `COSINE` for non-normalized vectors (more compute-intensive)
- Other parameters are automatically tuned by the system

```java
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;

// Create vector index
CreateTableIndexRequest indexRequest = new CreateTableIndexRequest();
indexRequest.setName("my_table");
indexRequest.setColumn("embedding");
indexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.IVF_PQ);
indexRequest.setMetricType(CreateTableIndexRequest.MetricTypeEnum.L2);

CreateTableIndexResponse response = namespace.createTableIndex(indexRequest);
```

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
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;

// Create scalar index
CreateTableIndexRequest scalarIndexRequest = new CreateTableIndexRequest();
scalarIndexRequest.setName("my_table");
scalarIndexRequest.setColumn("name");
scalarIndexRequest.setIndexType(CreateTableIndexRequest.IndexTypeEnum.BITMAP);

CreateTableIndexResponse scalarResponse = namespace.createTableIndex(scalarIndexRequest);
```

!!! note "Asynchronous Index Creation"
    Similar to vector index creation, scalar index creation is also asynchronous. Use `listTableIndices` and `describeTableIndexStats` to monitor index creation progress.

### List Indices

List all indices on a table:

```java
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;

ListTableIndicesRequest listRequest = new ListTableIndicesRequest();
listRequest.setName("my_table");

ListTableIndicesResponse listResponse = namespace.listTableIndices(listRequest);
if (listResponse.getIndexes() != null) {
    for (IndexListItemResponse index : listResponse.getIndexes()) {
        System.out.println("Index: " + index.getIndexName());
        System.out.println("  Columns: " + index.getColumns());
        System.out.println("  Index Type: " + index.getIndexType());
    }
}
```

### Get Index Statistics

Get detailed statistics for a specific index:
```java
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse;

DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
statsRequest.setName("my_table");

// Get stats for specific index (index name format: <column_name>_idx)
DescribeTableIndexStatsResponse stats = namespace.describeTableIndexStats(statsRequest, "embedding_idx");

System.out.println("Index Type: " + stats.getIndexType());
System.out.println("Distance Type: " + stats.getDistanceType());
System.out.println("Indexed Rows: " + stats.getNumIndexedRows());
System.out.println("Unindexed Rows: " + stats.getNumUnindexedRows());
System.out.println("Index Metadata: " + stats.getIndexMetadata());
```

### Monitoring Index Creation

!!! important "Wait for Index Completion"
    Index creation is asynchronous. Always wait for indexes to be fully built before running queries to ensure optimal performance and avoid scanning unindexed data.

Here's a helper method that combines `listTableIndices` and `describeTableIndexStats` to monitor index creation:

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
    
    ListTableIndicesRequest listRequest = new ListTableIndicesRequest();
    listRequest.setName(tableName);

    for (int i = 0; i < maxSeconds; i++) {
        ListTableIndicesResponse listResponse = namespace.listTableIndices(listRequest);
        if (listResponse.getIndexes() != null) {
            Optional<IndexListItemResponse> indexOpt = listResponse.getIndexes().stream()
                .filter(idx -> idx.getIndexName().equals(indexName))
                .findFirst();
            
            if (indexOpt.isPresent()) {
                DescribeTableIndexStatsRequest statsRequest = new DescribeTableIndexStatsRequest();
                statsRequest.setName(tableName);
                DescribeTableIndexStatsResponse stats = namespace.describeTableIndexStats(statsRequest, indexName);
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
CreateTableIndexResponse response = namespace.createTableIndex(indexRequest);
boolean indexReady = waitForIndexComplete("my_table", "embedding_idx", 60);
if (!indexReady) {
    System.out.println("Warning: Index creation timed out");
}
```

### Inserting Additional Data

```java
// Insert more rows into existing table
// Create Arrow data with same schema as original table
byte[] newData = createArrowData(/* new rows */);

InsertIntoTableRequest insertRequest = new InsertIntoTableRequest();
insertRequest.setName("my_table");
insertRequest.setMode(InsertIntoTableRequest.ModeEnum.APPEND);
InsertIntoTableResponse insertResponse = namespace.insertIntoTable(insertRequest, newData);
System.out.println("Inserted rows, new version: " + insertResponse.getVersion());
```

### Counting Rows

```java
CountTableRowsRequest countRequest = new CountTableRowsRequest();
countRequest.setName("my_table");

long rowCount = namespace.countTableRows(countRequest);
System.out.println("Table has " + rowCount + " rows");

// Count with filter
countRequest.setFilter("id >= 100 AND id < 200");
long filteredCount = namespace.countTableRows(countRequest);
System.out.println("Filtered count: " + filteredCount + " rows");
```

### Updating Data

```java
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Example: Update rows based on condition
UpdateTableRequest updateRequest = new UpdateTableRequest();
updateRequest.setName("my_table");
updateRequest.setNamespace(new ArrayList<>());
updateRequest.setPredicate("id >= 50 AND id <= 60");

List<List<String>> updates = new ArrayList<>();
// Note: string values need quotes, numeric values don't
updates.add(Arrays.asList("some_field", "'updated_value'"));
updateRequest.setUpdates(updates);

UpdateTableResponse updateResponse = namespace.updateTable(updateRequest);
System.out.println("Updated " + updateResponse.getUpdatedRows() + " rows");
```

### Deleting Data

```java
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;

// Delete specific rows
DeleteFromTableRequest deleteRequest = new DeleteFromTableRequest();
deleteRequest.setName("my_table");
deleteRequest.setNamespace(new ArrayList<>());
deleteRequest.setPredicate("id > 900");

DeleteFromTableResponse deleteResponse = namespace.deleteFromTable(deleteRequest);
System.out.println("Deleted rows, new version: " + deleteResponse.getVersion());
```

### Describing a Table

```java
DescribeTableRequest describeRequest = new DescribeTableRequest();
describeRequest.setName("my_table");

DescribeTableResponse tableInfo = namespace.describeTable(describeRequest);
System.out.println("Table location: " + tableInfo.getLocation());
System.out.println("Schema: " + tableInfo.getSchema());
System.out.println("Version: " + tableInfo.getVersion());
```

### Merge Insert (Upsert)

```java
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;

// Prepare data (similar to create table)
byte[] arrowIpcData = prepareArrowData();

// Create merge request
MergeInsertIntoTableRequest mergeRequest = new MergeInsertIntoTableRequest();
mergeRequest.setName("my_table");
mergeRequest.setOn("id");    // match on id column
mergeRequest.setWhenMatchedUpdateAll(true);    // when_matched_update_all
mergeRequest.setWhenNotMatchedInsertAll(true); // when_not_matched_insert_all

// Perform merge insert
MergeInsertIntoTableResponse response = namespace.mergeInsertIntoTable(
    mergeRequest,
    arrowIpcData
);

System.out.println("Updated rows: " + response.getNumUpdatedRows());
System.out.println("Inserted rows: " + response.getNumInsertedRows());
```

## Known Limitation
### Not Supported: Hybrid Search
Hybrid Search requires a vector search and a full text search, cannot run both in one query.
Need higher level of search orchestration to provide user level hybrid search operations.

## Additional Resources

- [API Javadoc](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html)
- [Apache Arrow Java Documentation](https://arrow.apache.org/docs/java/)