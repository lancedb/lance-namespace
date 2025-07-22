# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `on`: pass through query parameter of the same name - `when_matched_update_all`: pass through query parameter of the same name - `when_matched_update_all_filt`: pass through query parameter of the same name - `when_not_matched_insert_all`: pass through query parameter of the same name - `when_not_matched_by_source_delete`: pass through query parameter of the same name - `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | [optional] 
**namespace** | **List[str]** | The namespace identifier | [optional] 
**on** | **str** | Column name to use for matching rows (required) | [optional] 
**when_matched_update_all** | **bool** | Update all columns when rows match | [optional] [default to False]
**when_matched_update_all_filt** | **str** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true | [optional] 
**when_not_matched_insert_all** | **bool** | Insert all columns when rows don&#39;t match | [optional] [default to False]
**when_not_matched_by_source_delete** | **bool** | Delete all rows from target table that don&#39;t match a row in the source table | [optional] [default to False]
**when_not_matched_by_source_delete_filt** | **str** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true | [optional] 

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


