# CreateTableIndexRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**column** | **String** | Lance field path to create the index on. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**index_type** | **String** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) | 
**name** | Option<**String**> | Optional name for the index. If not provided, a name will be auto-generated. | [optional]
**distance_type** | Option<**String**> | Distance metric type for vector indexes (e.g., l2, cosine, dot) | [optional]
**num_partitions** | Option<**i32**> | Optional vector index parameter for the number of IVF partitions. Applies to all IVF index types. | [optional]
**num_sub_vectors** | Option<**i32**> | Optional vector index parameter for the number of PQ sub-vectors. Applies to IVF_PQ only. | [optional]
**num_bits** | Option<**i32**> | Optional vector index parameter for the number of bits used by the quantizer. | [optional]
**sample_rate** | Option<**i32**> | Optional vector index parameter for the per-partition sample rate used during IVF training. | [optional]
**max_iterations** | Option<**i32**> | Optional vector index parameter for the maximum number of IVF k-means training iterations. | [optional]
**target_partition_size** | Option<**i32**> | Optional vector index parameter for the target partition size. Alternative to num_partitions. | [optional]
**m** | Option<**i32**> | Optional vector index parameter for the number of edges per node in the HNSW graph. Applies to HNSW index types. | [optional]
**ef_construction** | Option<**i32**> | Optional vector index parameter for the number of candidates evaluated during HNSW graph construction. Applies to HNSW index types. | [optional]
**with_position** | Option<**bool**> | Optional FTS parameter for position tracking | [optional]
**base_tokenizer** | Option<**String**> | Optional FTS parameter for base tokenizer | [optional]
**language** | Option<**String**> | Optional FTS parameter for language | [optional]
**max_token_length** | Option<**i32**> | Optional FTS parameter for maximum token length | [optional]
**lower_case** | Option<**bool**> | Optional FTS parameter for lowercase conversion | [optional]
**stem** | Option<**bool**> | Optional FTS parameter for stemming | [optional]
**remove_stop_words** | Option<**bool**> | Optional FTS parameter for stop word removal | [optional]
**ascii_folding** | Option<**bool**> | Optional FTS parameter for ASCII folding | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


