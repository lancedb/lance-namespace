# GetTransactionRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.get_transaction_request import GetTransactionRequest

# TODO update the JSON string below
json = "{}"
# create an instance of GetTransactionRequest from a JSON string
get_transaction_request_instance = GetTransactionRequest.from_json(json)
# print the JSON string representation of the object
print(GetTransactionRequest.to_json())

# convert the object into a dict
get_transaction_request_dict = get_transaction_request_instance.to_dict()
# create an instance of GetTransactionRequest from a dict
get_transaction_request_from_dict = GetTransactionRequest.from_dict(get_transaction_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


