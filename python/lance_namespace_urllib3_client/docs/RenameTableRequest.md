# RenameTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | The table identifier | [optional] 
**new_table_name** | **str** | New name for the table | 
**new_namespace_id** | **List[str]** | New namespace identifier to move the table to (optional, if not specified the table stays in the same namespace) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.rename_table_request import RenameTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of RenameTableRequest from a JSON string
rename_table_request_instance = RenameTableRequest.from_json(json)
# print the JSON string representation of the object
print(RenameTableRequest.to_json())

# convert the object into a dict
rename_table_request_dict = rename_table_request_instance.to_dict()
# create an instance of RenameTableRequest from a dict
rename_table_request_from_dict = RenameTableRequest.from_dict(rename_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


