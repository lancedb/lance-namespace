# CreateTable400Response


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
from lance_namespace_urllib3_client.models.create_table400_response import CreateTable400Response

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTable400Response from a JSON string
create_table400_response_instance = CreateTable400Response.from_json(json)
# print the JSON string representation of the object
print(CreateTable400Response.to_json())

# convert the object into a dict
create_table400_response_dict = create_table400_response_instance.to_dict()
# create an instance of CreateTable400Response from a dict
create_table400_response_from_dict = CreateTable400Response.from_dict(create_table400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


