# MergeInsertIntoTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 

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


