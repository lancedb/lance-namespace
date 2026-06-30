# CreateTableIndexRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**column** | **str** | Lance field path to create the index on. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**index_type** | **str** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) | 
**name** | **str** | Optional name for the index. If not provided, a name will be auto-generated. | [optional] 
**distance_type** | **str** | Distance metric type for vector indexes (e.g., l2, cosine, dot) | [optional] 
**with_position** | **bool** | Optional FTS parameter for position tracking | [optional] 
**base_tokenizer** | **str** | Optional FTS parameter for base tokenizer | [optional] 
**language** | **str** | Optional FTS parameter for language | [optional] 
**max_token_length** | **int** | Optional FTS parameter for maximum token length | [optional] 
**lower_case** | **bool** | Optional FTS parameter for lowercase conversion | [optional] 
**stem** | **bool** | Optional FTS parameter for stemming | [optional] 
**remove_stop_words** | **bool** | Optional FTS parameter for stop word removal | [optional] 
**ascii_folding** | **bool** | Optional FTS parameter for ASCII folding | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_index_request import CreateTableIndexRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableIndexRequest from a JSON string
create_table_index_request_instance = CreateTableIndexRequest.from_json(json)
# print the JSON string representation of the object
print(CreateTableIndexRequest.to_json())

# convert the object into a dict
create_table_index_request_dict = create_table_index_request_instance.to_dict()
# create an instance of CreateTableIndexRequest from a dict
create_table_index_request_from_dict = CreateTableIndexRequest.from_dict(create_table_index_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


