# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**on** | **List[str]** | Lance field paths to use for matching rows. Multiple fields form a composite match key; a row matches only when every listed field is equal. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | [optional] 
**when_matched_update_all** | **bool** | Update all columns when rows match | [optional] [default to False]
**when_matched_update_all_filt** | **str** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | [optional] 
**when_not_matched_insert_all** | **bool** | Insert all columns when rows don&#39;t match | [optional] [default to False]
**when_not_matched_by_source_delete** | **bool** | Delete all rows from target table that don&#39;t match a row in the source table | [optional] [default to False]
**when_not_matched_by_source_delete_filt** | **str** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | [optional] 
**timeout** | **str** | Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) | [optional] 
**use_index** | **bool** | Whether to use index for matching rows | [optional] [default to False]

## Example

```python
from lance_namespace_urllib3_client.models.merge_insert_into_table_request import MergeInsertIntoTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of MergeInsertIntoTableRequest from a JSON string
merge_insert_into_table_request_instance = MergeInsertIntoTableRequest.from_json(json)
# print the JSON string representation of the object
print(MergeInsertIntoTableRequest.to_json())

# convert the object into a dict
merge_insert_into_table_request_dict = merge_insert_into_table_request_instance.to_dict()
# create an instance of MergeInsertIntoTableRequest from a dict
merge_insert_into_table_request_from_dict = MergeInsertIntoTableRequest.from_dict(merge_insert_into_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


