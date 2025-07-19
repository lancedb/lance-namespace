# LanceDB Rust Vector Query Implementation Analysis

## Overview

This document analyzes how LanceDB's Rust implementation handles vector query requests, focusing on the QueryVector handling and oneOf type patterns.

## Key Components

### 1. Query Structure Hierarchy

The Rust implementation uses an enum pattern for different query types:

```rust
pub enum AnyQuery {
    Query(QueryRequest),        // Regular queries without vector search
    VectorQuery(VectorQueryRequest),  // Vector-based queries
}
```

### 2. VectorQueryRequest Structure

```rust
pub struct VectorQueryRequest {
    pub base: QueryRequest,                    // Base query parameters
    pub column: Option<String>,                // Target vector column
    pub query_vector: Vec<Arc<dyn Array>>,     // Query vectors (supports multiple)
    pub minimum_nprobes: usize,
    pub maximum_nprobes: Option<usize>,
    pub lower_bound: Option<f32>,
    pub upper_bound: Option<f32>,
    pub distance_type: Option<DistanceType>,
    pub ef: Option<usize>,
    pub refine_factor: Option<usize>,
    pub use_index: bool,
}
```

### 3. Vector Handling

#### Input Types
- Vectors are stored as `Vec<Arc<dyn Array>>` allowing multiple query vectors
- The implementation supports various input types through the `IntoQueryVector` trait
- Common conversions: `Vec<f32>`, `&[f32]`, `&[f16]`, Arrow arrays

#### JSON Serialization
When sending to the server, vectors are converted to JSON:

```rust
fn vector_to_json(vector: &arrow_array::ArrayRef) -> Result<serde_json::Value> {
    match vector.data_type() {
        DataType::Float32 => {
            let array = vector.as_any().downcast_ref::<Float32Array>().unwrap();
            Ok(serde_json::Value::Array(
                array.values().iter()
                    .map(|v| serde_json::Value::Number(
                        serde_json::Number::from_f64(*v as f64).unwrap()
                    ))
                    .collect(),
            ))
        }
        _ => Err(Error::InvalidInput {
            message: "VectorQuery vector must be of type Float32".into(),
        }),
    }
}
```

### 4. Query Building Pattern

The Rust implementation uses a builder pattern:

```rust
table.query()
    .nearest_to(&[1.0, 2.0, 3.0])  // Sets the first query vector
    .add_query_vector(&[4.0, 5.0, 6.0])  // Adds additional vectors
    .column("my_vector_column")
    .distance_type(DistanceType::Cosine)
    .limit(10)
    .execute()
```

### 5. Server Request Format

The implementation prepares different request formats based on the number of query vectors:

1. **No vectors**: `"vector": []`
2. **Single vector**: `"vector": [1.0, 2.0, 3.0]`
3. **Multiple vectors** (if server supports):
   - New format: `"vector": [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]`
   - Old format: Sends multiple separate requests

### 6. Key Insights for Java Implementation

1. **OneOf Pattern**: The Rust code uses enum for Query/VectorQuery distinction, which maps to OpenAPI oneOf
2. **Vector Format**: Always Float32 arrays serialized as JSON number arrays
3. **Multiple Vectors**: Support for batch vector queries with query_index in results
4. **Builder Pattern**: Clean API for constructing complex queries
5. **Server Version Handling**: Different behavior based on server capabilities

### 7. Request Body Structure

A typical vector query request body:

```json
{
  "version": null,  // or specific version number
  "vector": [0.1, 0.2, 0.3],
  "vector_column": "embeddings",
  "distance_type": "cosine",
  "k": 10,
  "nprobes": 20,
  "minimum_nprobes": 20,
  "maximum_nprobes": 0,
  "lower_bound": null,
  "upper_bound": null,
  "ef": null,
  "refine_factor": null,
  "bypass_vector_index": false,
  "prefilter": true,
  "filter": "id > 100",
  "columns": ["id", "text", "metadata"]
}
```

### 8. Important Implementation Details

1. **Type Safety**: Vectors must be Float32 type
2. **Empty Vector Handling**: Empty array `[]` means no vector search
3. **Column Auto-detection**: If column is not specified, server auto-detects
4. **Backward Compatibility**: Handles both old (nprobes) and new (minimum/maximum_nprobes) formats
5. **Timeout Support**: Query execution supports timeouts via headers

## Recommendations for Java Implementation

1. Use a similar builder pattern for query construction
2. Implement proper type conversion for vectors (List<Float> -> JSON array)
3. Handle the oneOf pattern with proper query type discrimination
4. Support both single and multiple vector queries
5. Implement server version detection for feature compatibility
6. Ensure proper JSON serialization matching the expected format