# RestoreTable404Response


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
from lance_namespace_urllib3_client.models.restore_table404_response import RestoreTable404Response

# TODO update the JSON string below
json = "{}"
# create an instance of RestoreTable404Response from a JSON string
restore_table404_response_instance = RestoreTable404Response.from_json(json)
# print the JSON string representation of the object
print(RestoreTable404Response.to_json())

# convert the object into a dict
restore_table404_response_dict = restore_table404_response_instance.to_dict()
# create an instance of RestoreTable404Response from a dict
restore_table404_response_from_dict = RestoreTable404Response.from_dict(restore_table404_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


