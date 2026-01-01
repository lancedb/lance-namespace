# RegisterTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** |  | 
**mode** | **str** | There are two modes when trying to register a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create (default): the operation fails with 409.   * Overwrite: the existing table registration is replaced with the new registration.  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.register_table_request import RegisterTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of RegisterTableRequest from a JSON string
register_table_request_instance = RegisterTableRequest.from_json(json)
# print the JSON string representation of the object
print(RegisterTableRequest.to_json())

# convert the object into a dict
register_table_request_dict = register_table_request_instance.to_dict()
# create an instance of RegisterTableRequest from a dict
register_table_request_from_dict = RegisterTableRequest.from_dict(register_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


