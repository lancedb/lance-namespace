# UpdateTable400Response


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
from lance_namespace_urllib3_client.models.update_table400_response import UpdateTable400Response

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTable400Response from a JSON string
update_table400_response_instance = UpdateTable400Response.from_json(json)
# print the JSON string representation of the object
print(UpdateTable400Response.to_json())

# convert the object into a dict
update_table400_response_dict = update_table400_response_instance.to_dict()
# create an instance of UpdateTable400Response from a dict
update_table400_response_from_dict = UpdateTable400Response.from_dict(update_table400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


