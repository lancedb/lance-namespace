# RenameTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.rename_table_response import RenameTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of RenameTableResponse from a JSON string
rename_table_response_instance = RenameTableResponse.from_json(json)
# print the JSON string representation of the object
print(RenameTableResponse.to_json())

# convert the object into a dict
rename_table_response_dict = rename_table_response_instance.to_dict()
# create an instance of RenameTableResponse from a dict
rename_table_response_from_dict = RenameTableResponse.from_dict(rename_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


