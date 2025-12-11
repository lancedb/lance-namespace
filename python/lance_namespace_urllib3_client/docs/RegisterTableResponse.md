# RegisterTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**location** | **str** |  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.register_table_response import RegisterTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of RegisterTableResponse from a JSON string
register_table_response_instance = RegisterTableResponse.from_json(json)
# print the JSON string representation of the object
print(RegisterTableResponse.to_json())

# convert the object into a dict
register_table_response_dict = register_table_response_instance.to_dict()
# create an instance of RegisterTableResponse from a dict
register_table_response_from_dict = RegisterTableResponse.from_dict(register_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


