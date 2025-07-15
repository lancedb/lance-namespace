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

Query results are returned in Arrow File format. Use `ArrowFileReader` to read the results:

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

## Current Limitations

The Java SDK is generated from an OpenAPI specification created by utoipa, which has some limitations with recursive structures. We are actively working to address these limitations.

### Schema Representation

The schema structure (`JsonSchema` → `JsonField` → `JsonDataType` → `JsonField`) has limitations in representing nested types. This affects `describeTable` calls where complex nested schemas may not be fully represented.

```rust
// Recursive structure that causes issues
pub struct JsonField {
    name: String,
    type_: JsonDataType,
    nullable: bool,
    metadata: Option<HashMap<String, String>>,
}

pub struct JsonDataType {
    type_: String,
    fields: Option<Vec<JsonField>>, // Recursive reference
    length: Option<usize>,
}
```

### Full-Text Search (FTS) Queries

Advanced FTS queries with boolean combinations are limited due to recursive query structures:

```rust
pub enum FtsQuery {
    Match(MatchQuery),
    Phrase(PhraseQuery),
    Boolean(BooleanQuery), // Recursive structure
}

pub struct BooleanQuery {
    pub should: Vec<FtsQuery>,
    pub must: Vec<FtsQuery>,
    pub must_not: Vec<FtsQuery>,
}
```

## Additional Resources

- [LanceDB Documentation](https://lancedb.github.io/lancedb/)
- [API Javadoc](https://javadoc.io/doc/com.lancedb/lance-namespace-core/latest/index.html)
- [Apache Arrow Java Documentation](https://arrow.apache.org/docs/java/)