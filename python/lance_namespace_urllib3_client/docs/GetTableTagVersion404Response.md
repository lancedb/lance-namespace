# GetTableTagVersion404Response


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
from lance_namespace_urllib3_client.models.get_table_tag_version404_response import GetTableTagVersion404Response

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableTagVersion404Response from a JSON string
get_table_tag_version404_response_instance = GetTableTagVersion404Response.from_json(json)
# print the JSON string representation of the object
print(GetTableTagVersion404Response.to_json())

# convert the object into a dict
get_table_tag_version404_response_dict = get_table_tag_version404_response_instance.to_dict()
# create an instance of GetTableTagVersion404Response from a dict
get_table_tag_version404_response_from_dict = GetTableTagVersion404Response.from_dict(get_table_tag_version404_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


