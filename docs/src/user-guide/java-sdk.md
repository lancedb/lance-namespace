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
    The request structures contains field `name` which refer to table name and it's required. The `namespace` field is optional, if provided the result table name will be in format of `namespace.name`.

For detailed request/response structures, refer to the [Apache Client documentation](https://javadoc.io/doc/com.lancedb/lance-namespace-apache-client/latest/index.html).

## Best Practices

1. **Always use `fast_search=true`** for queries to avoid scanning unindexed data from storage, which can be slow and incur data egress costs.
2. **Monitor index creation** using `waitForIndexComplete()` to ensure indexes are fully built before running queries.
3. **Use appropriate index types**:
   - IVF_PQ for vector columns
   - BITMAP for low-cardinality scalar columns
   - FTS for text search columns
4. **Combine search types** with hybrid queries for better relevance.

## Examples

### Creating a Table

LanceDB uses Apache Arrow format for data exchange. Arrow provides:
- Cross-language compatibility
- Rich data type support including nested types and tensors

```java
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.types.pojo.*;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;

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

#### Basic Vector Search

```java
import com.lancedb.lance.namespace.model.QueryRequest;

// Create query request
QueryRequest queryRequest = new QueryRequest();
queryRequest.setName("my_table");

// Set query vector (128-dimensional)
List<Float> queryVector = new ArrayList<>();
for (int i = 0; i < 128; i++) {
    queryVector.add((float) Math.random());
}
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

#### Advanced Query Options

##### Fast Search (Recommended)

!!! warning "Always use fast_search=true"
    When `fast_search` is true, the query will only search indexed data, providing better performance and avoiding potential data egress costs. When false, it will scan the entire column from storage, which can be slow and expensive.

```java
QueryRequest queryRequest = new QueryRequest();
queryRequest.setName("my_table");
queryRequest.setVector(queryVector);
queryRequest.setK(10);

// RECOMMENDED: Enable fast search to skip unindexed data
queryRequest.setFastSearch(true);

// Other query options
queryRequest.setDistanceType("cosine");  // "l2" or "cosine"
queryRequest.setFilter("price < 100");   // SQL filter
queryRequest.setPrefilter(true);        // Apply filter before vector search
queryRequest.setOffset(10);              // Skip first 10 results
queryRequest.setBypassVectorIndex(false); // Use index (default)
queryRequest.setWithRowId(true);         // Include row IDs in results
```

##### Filter Queries

```java
// Query with SQL filter
QueryRequest filterQuery = new QueryRequest();
filterQuery.setName("my_table");
filterQuery.setK(10);
filterQuery.setFilter("category = 'electronics' AND price < 500");
filterQuery.setFastSearch(true);  // Recommended

// For filter-only queries (no vector search), omit the vector
byte[] results = namespace.queryTable(filterQuery);
```

##### Hybrid Search with Prefilter

```java
// Combine vector search with prefiltering
QueryRequest hybridQuery = new QueryRequest();
hybridQuery.setName("my_table");
hybridQuery.setVector(queryVector);
hybridQuery.setK(5);
hybridQuery.setFilter("status = 'active'");
hybridQuery.setPrefilter(true);  // Filter BEFORE vector search
hybridQuery.setFastSearch(true);
```

### Creating an Index

LanceDB automatically optimizes index parameters based on best practices for your workload.

#### Vector Index Best Practices

- **Index Type**: Use IVF_PQ for production workloads (default)
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

#### Monitoring Index Creation

Index creation is asynchronous. Use the following helper method to wait for index completion:

```java
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
                // Index exists, now check if it's fully built
                IndexStatsRequest statsRequest = new IndexStatsRequest();
                statsRequest.setName(tableName);
                
                IndexStatsResponse stats = namespace.getIndexStats(statsRequest, indexName);
                if (stats != null && stats.getNumUnindexedRows() != null 
                    && stats.getNumUnindexedRows() == 0) {
                    System.out.println("✓ Index " + indexName + " is fully built");
                    return true;
                } else if (stats != null && stats.getNumUnindexedRows() != null) {
                    System.out.println("  Waiting... " + stats.getNumUnindexedRows() 
                        + " rows remaining");
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

#### Scalar Index Best Practices

Scalar indexes improve query performance when using filters: `table.query(embedding).where(filter)`

- **BITMAP Index**: Best for columns with low cardinality (< few thousand unique values)
  - Excellent search performance
  - Relatively small index size
- **BTREE Index**: Use when unique values are high
- **Optimization Tip**: Reduce data precision to enable bitmap indexing:
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

### Full-Text Search (FTS)

LanceDB supports full-text search on string columns. Create an FTS index first, then use text queries.

#### Creating an FTS Index

```java
// Create FTS index on text column
CreateIndexRequest ftsIndexRequest = new CreateIndexRequest();
ftsIndexRequest.setName("my_table");
ftsIndexRequest.setColumn("content");
ftsIndexRequest.setIndexType(CreateIndexRequest.IndexTypeEnum.FTS);

CreateIndexResponse ftsResponse = namespace.createIndex(ftsIndexRequest);

// Wait for index to be ready
waitForIndexComplete("my_table", "content_idx", 30);
```

#### Simple Text Search

```java
import com.lancedb.lance.namespace.model.StringFtsQuery;

// Simple text search
QueryRequest textQuery = new QueryRequest();
textQuery.setName("my_table");
textQuery.setK(10);

StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("search terms");
fts.setColumns(Arrays.asList("content", "title")); // Optional: specify columns
textQuery.setFullTextQuery(fts);
textQuery.setFastSearch(true);  // Recommended

byte[] results = namespace.queryTable(textQuery);
```

#### Hybrid Search: Vector + Text

Combine vector similarity search with full-text search for more relevant results:

```java
// Hybrid search: vector + text
QueryRequest hybridQuery = new QueryRequest();
hybridQuery.setName("my_table");

// Vector search component
List<Float> queryVector = generateQueryVector(); // Your vector
hybridQuery.setVector(queryVector);
hybridQuery.setK(10);

// Text search component
StringFtsQuery ftsQuery = new StringFtsQuery();
ftsQuery.setQuery("important document");
hybridQuery.setFullTextQuery(ftsQuery);

// Optional: Add filters
hybridQuery.setFilter("date > '2024-01-01'");
hybridQuery.setPrefilter(true);
hybridQuery.setFastSearch(true);  // Recommended

byte[] hybridResults = namespace.queryTable(hybridQuery);
```

#### Hybrid Search with Column Selection

```java
// Hybrid search with specific result columns
QueryRequest advancedHybrid = new QueryRequest();
advancedHybrid.setName("my_table");
advancedHybrid.setVector(queryVector);
advancedHybrid.setK(5);

// Text search on specific columns
StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("machine learning");
fts.setColumns(Arrays.asList("abstract", "keywords"));
advancedHybrid.setFullTextQuery(fts);

// Return only specific columns in results
advancedHybrid.setColumns(Arrays.asList("id", "title", "score"));
advancedHybrid.setFastSearch(true);

byte[] results = namespace.queryTable(advancedHybrid);
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

## Known Limitations

Due to limitations in the OpenAPI code generator for Java, some advanced features that use `oneOf` polymorphic types are not fully supported in this SDK:

### 1. Multi-Vector Queries
- **Limitation**: Only single vector queries are supported
- **Workaround**: Submit one vector at a time rather than batching multiple vectors

### 2. Complex Column Specifications  
- **Limitation**: Only simple column lists (array of strings) are supported
- **Workaround**: Use `Arrays.asList("col1", "col2")` to specify columns
- **Not Supported**: Advanced column specifications with include/exclude patterns

### 3. Structured Full-Text Search
- **Limitation**: Only simple string queries are supported via `StringFtsQuery`
- **Workaround**: Use basic text search with `query.setFullTextQuery(stringQuery)`
- **Not Supported**: Complex boolean queries, phrase queries, or boosted queries

### Example of Supported vs Unsupported Features

```java
// ✅ SUPPORTED: Simple vector query
QueryRequest query = new QueryRequest();
query.setName("my_table");
query.setK(10);
List<Float> vector = Arrays.asList(0.1f, 0.2f, 0.3f, ...);
query.setVector(vector);

// ❌ NOT SUPPORTED: Multi-vector query
// List<List<Float>> vectors = Arrays.asList(vector1, vector2, vector3);
// query.setVector(vectors); // This won't compile

// ✅ SUPPORTED: Simple column selection
query.setColumns(Arrays.asList("id", "name", "score"));

// ❌ NOT SUPPORTED: Complex column specification
// ColumnsObject cols = new ColumnsObject();
// cols.setInclude(Arrays.asList("*"));
// cols.setExclude(Arrays.asList("embedding"));

// ✅ SUPPORTED: Simple text search
StringFtsQuery fts = new StringFtsQuery();
fts.setQuery("search terms");
fts.setColumns(Arrays.asList("title", "content"));
query.setFullTextQuery(fts);

// ❌ NOT SUPPORTED: Structured boolean queries
// BooleanQuery bool = new BooleanQuery();
// bool.setMust(Arrays.asList(matchQuery1));
// bool.setShould(Arrays.asList(matchQuery2));
```

These limitations are due to the OpenAPI generator's inability to properly handle `oneOf` types in the specification. The simplified types ensure the SDK works reliably for the most common use cases.

## Additional Resources

- [LanceDB Documentation](https://lancedb.github.io/lancedb/)
- [API Javadoc](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html)
- [Apache Arrow Java Documentation](https://arrow.apache.org/docs/java/)