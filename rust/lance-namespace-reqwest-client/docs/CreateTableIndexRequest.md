# CreateTableIndexRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**column** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | 
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


