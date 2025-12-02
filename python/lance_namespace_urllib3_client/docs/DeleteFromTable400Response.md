# DeleteFromTable400Response


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | Brief error message | 
**code** | **int** | HTTP status code | 
**type** | **str** | Error type identifier | 
**detail** | **str** | Detailed error explanation | [optional] 
**instance** | **str** | Specific occurrence identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.delete_from_table400_response import DeleteFromTable400Response

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteFromTable400Response from a JSON string
delete_from_table400_response_instance = DeleteFromTable400Response.from_json(json)
# print the JSON string representation of the object
print(DeleteFromTable400Response.to_json())

# convert the object into a dict
delete_from_table400_response_dict = delete_from_table400_response_instance.to_dict()
# create an instance of DeleteFromTable400Response from a dict
delete_from_table400_response_from_dict = DeleteFromTable400Response.from_dict(delete_from_table400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


