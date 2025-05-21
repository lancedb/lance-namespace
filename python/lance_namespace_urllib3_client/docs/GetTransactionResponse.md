# GetTransactionResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **str** |  | 
**status** | [**TransactionStatus**](TransactionStatus.md) |  | 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.get_transaction_response import GetTransactionResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetTransactionResponse from a JSON string
get_transaction_response_instance = GetTransactionResponse.from_json(json)
# print the JSON string representation of the object
print(GetTransactionResponse.to_json())

# convert the object into a dict
get_transaction_response_dict = get_transaction_response_instance.to_dict()
# create an instance of GetTransactionResponse from a dict
get_transaction_response_from_dict = GetTransactionResponse.from_dict(get_transaction_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


