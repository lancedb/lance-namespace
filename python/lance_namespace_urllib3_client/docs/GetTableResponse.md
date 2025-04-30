# GetTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 
**location** | **str** |  | 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_response import GetTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableResponse from a JSON string
get_table_response_instance = GetTableResponse.from_json(json)
# print the JSON string representation of the object
print(GetTableResponse.to_json())

# convert the object into a dict
get_table_response_dict = get_table_response_instance.to_dict()
# create an instance of GetTableResponse from a dict
get_table_response_from_dict = GetTableResponse.from_dict(get_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


