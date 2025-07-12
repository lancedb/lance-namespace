# MergeInsertTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 

## Example

```python
from lance_namespace_urllib3_client.models.merge_insert_table_request import MergeInsertTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of MergeInsertTableRequest from a JSON string
merge_insert_table_request_instance = MergeInsertTableRequest.from_json(json)
# print the JSON string representation of the object
print(MergeInsertTableRequest.to_json())

# convert the object into a dict
merge_insert_table_request_dict = merge_insert_table_request_instance.to_dict()
# create an instance of MergeInsertTableRequest from a dict
merge_insert_table_request_from_dict = MergeInsertTableRequest.from_dict(merge_insert_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


