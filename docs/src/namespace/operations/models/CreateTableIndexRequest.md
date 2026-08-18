

# CreateTableIndexRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**column** | **String** | Lance field path to create the index on. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. |  |
|**indexType** | **String** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) |  |
|**name** | **String** | Optional name for the index. If not provided, a name will be auto-generated. |  [optional] |
|**distanceType** | **String** | Distance metric type for vector indexes (e.g., l2, cosine, dot) |  [optional] |
|**numPartitions** | **Integer** | Optional vector index parameter for the number of IVF partitions. Applies to all IVF index types. |  [optional] |
|**numSubVectors** | **Integer** | Optional vector index parameter for the number of PQ sub-vectors. Applies to IVF_PQ only. |  [optional] |
|**numBits** | **Integer** | Optional vector index parameter for the number of bits used by the quantizer. |  [optional] |
|**sampleRate** | **Integer** | Optional vector index parameter for the per-partition sample rate used during IVF training. |  [optional] |
|**maxIterations** | **Integer** | Optional vector index parameter for the maximum number of IVF k-means training iterations. |  [optional] |
|**targetPartitionSize** | **Integer** | Optional vector index parameter for the target partition size. Alternative to num_partitions. |  [optional] |
|**m** | **Integer** | Optional vector index parameter for the number of edges per node in the HNSW graph. Applies to HNSW index types. |  [optional] |
|**efConstruction** | **Integer** | Optional vector index parameter for the number of candidates evaluated during HNSW graph construction. Applies to HNSW index types. |  [optional] |
|**withPosition** | **Boolean** | Optional FTS parameter for position tracking |  [optional] |
|**baseTokenizer** | **String** | Optional FTS parameter for base tokenizer |  [optional] |
|**language** | **String** | Optional FTS parameter for language |  [optional] |
|**maxTokenLength** | **Integer** | Optional FTS parameter for maximum token length |  [optional] |
|**lowerCase** | **Boolean** | Optional FTS parameter for lowercase conversion |  [optional] |
|**stem** | **Boolean** | Optional FTS parameter for stemming |  [optional] |
|**removeStopWords** | **Boolean** | Optional FTS parameter for stop word removal |  [optional] |
|**asciiFolding** | **Boolean** | Optional FTS parameter for ASCII folding |  [optional] |



