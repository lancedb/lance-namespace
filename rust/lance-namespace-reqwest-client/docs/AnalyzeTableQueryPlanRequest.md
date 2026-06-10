# AnalyzeTableQueryPlanRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**bypass_vector_index** | Option<**bool**> | Whether to bypass vector index | [optional]
**columns** | Option<[**models::QueryTableRequestColumns**](QueryTableRequest_columns.md)> |  | [optional]
**distance_type** | Option<**String**> | Distance metric to use | [optional]
**ef** | Option<**i32**> | Search effort parameter for HNSW index | [optional]
**fast_search** | Option<**bool**> | Whether to use fast search | [optional]
**filter** | Option<**String**> | Optional SQL filter expression. Field references in the expression must use Lance field path syntax.  | [optional]
**full_text_query** | Option<[**models::QueryTableRequestFullTextQuery**](QueryTableRequest_full_text_query.md)> |  | [optional]
**k** | **i32** | Number of results to return | 
**lower_bound** | Option<**f32**> | Lower bound for search | [optional]
**nprobes** | Option<**i32**> | Number of probes for IVF index | [optional]
**offset** | Option<**i32**> | Number of results to skip | [optional]
**prefilter** | Option<**bool**> | Whether to apply filtering before vector search | [optional]
**refine_factor** | Option<**i32**> | Refine factor for search | [optional]
**upper_bound** | Option<**f32**> | Upper bound for search | [optional]
**vector** | [**models::QueryTableRequestVector**](QueryTableRequest_vector.md) |  | 
**vector_column** | Option<**String**> | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional]
**version** | Option<**i64**> | Table version to query | [optional]
**with_row_id** | Option<**bool**> | If true, return the row id as a column called `_rowid` | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


