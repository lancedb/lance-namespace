# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**on** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional] 
**when_matched_update_all** | **bool** | Update all columns when rows match | [optional] [default to False]
**when_matched_update_all_filt** | **str** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true. Field references must use Lance field path syntax. | [optional] 
**when_not_matched_insert_all** | **bool** | Insert all columns when rows don&#39;t match | [optional] [default to False]
**when_not_matched_by_source_delete** | **bool** | Delete all rows from target table that don&#39;t match a row in the source table | [optional] [default to False]
**when_not_matched_by_source_delete_filt** | **str** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true. Field references must use Lance field path syntax. | [optional] 
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


