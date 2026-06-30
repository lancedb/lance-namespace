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
**with_position** | Option<**bool**> | Optional FTS parameter for position tracking | [optional]
**base_tokenizer** | Option<**String**> | Optional FTS parameter for base tokenizer | [optional]
**language** | Option<**String**> | Optional FTS parameter for language | [optional]
**max_token_length** | Option<**i32**> | Optional FTS parameter for maximum token length | [optional]
**lower_case** | Option<**bool**> | Optional FTS parameter for lowercase conversion | [optional]
**stem** | Option<**bool**> | Optional FTS parameter for stemming | [optional]
**remove_stop_words** | Option<**bool**> | Optional FTS parameter for stop word removal | [optional]
**ascii_folding** | Option<**bool**> | Optional FTS parameter for ASCII folding | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


