# QueryTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**bypass_vector_index** | **bool** | Whether to bypass vector index | [optional] 
**columns** | [**QueryTableRequestColumns**](QueryTableRequestColumns.md) |  | [optional] 
**distance_type** | **str** | Distance metric to use | [optional] 
**ef** | **int** | Search effort parameter for HNSW index | [optional] 
**fast_search** | **bool** | Whether to use fast search | [optional] 
**filter** | **str** | Optional SQL filter expression. Field references in the expression must use Lance field path syntax.  | [optional] 
**full_text_query** | [**QueryTableRequestFullTextQuery**](QueryTableRequestFullTextQuery.md) |  | [optional] 
**k** | **int** | Number of results to return | 
**lower_bound** | **float** | Lower bound for search | [optional] 
**nprobes** | **int** | Number of probes for IVF index | [optional] 
**offset** | **int** | Number of results to skip | [optional] 
**prefilter** | **bool** | Whether to apply filtering before vector search | [optional] 
**refine_factor** | **int** | Refine factor for search | [optional] 
**upper_bound** | **float** | Upper bound for search | [optional] 
**vector** | [**QueryTableRequestVector**](QueryTableRequestVector.md) |  | 
**vector_column** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional] 
**version** | **int** | Table version to query | [optional] 
**with_row_id** | **bool** | If true, return the row id as a column called &#x60;_rowid&#x60; | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.query_table_request import QueryTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of QueryTableRequest from a JSON string
query_table_request_instance = QueryTableRequest.from_json(json)
# print the JSON string representation of the object
print(QueryTableRequest.to_json())

# convert the object into a dict
query_table_request_dict = query_table_request_instance.to_dict()
# create an instance of QueryTableRequest from a dict
query_table_request_from_dict = QueryTableRequest.from_dict(query_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


