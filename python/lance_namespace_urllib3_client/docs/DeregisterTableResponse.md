# DeregisterTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** |  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.deregister_table_response import DeregisterTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DeregisterTableResponse from a JSON string
deregister_table_response_instance = DeregisterTableResponse.from_json(json)
# print the JSON string representation of the object
print(DeregisterTableResponse.to_json())

# convert the object into a dict
deregister_table_response_dict = deregister_table_response_instance.to_dict()
# create an instance of DeregisterTableResponse from a dict
deregister_table_response_from_dict = DeregisterTableResponse.from_dict(deregister_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


