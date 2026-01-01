# DeregisterTableRequest

The table content remains available in the storage. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.deregister_table_request import DeregisterTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeregisterTableRequest from a JSON string
deregister_table_request_instance = DeregisterTableRequest.from_json(json)
# print the JSON string representation of the object
print(DeregisterTableRequest.to_json())

# convert the object into a dict
deregister_table_request_dict = deregister_table_request_instance.to_dict()
# create an instance of DeregisterTableRequest from a dict
deregister_table_request_from_dict = DeregisterTableRequest.from_dict(deregister_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


