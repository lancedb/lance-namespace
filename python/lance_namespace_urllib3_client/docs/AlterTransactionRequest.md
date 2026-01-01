# AlterTransactionRequest

Alter a transaction with a list of actions. The server should either succeed and apply all actions, or fail and apply no action. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**actions** | [**List[AlterTransactionAction]**](AlterTransactionAction.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.alter_transaction_request import AlterTransactionRequest

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTransactionRequest from a JSON string
alter_transaction_request_instance = AlterTransactionRequest.from_json(json)
# print the JSON string representation of the object
print(AlterTransactionRequest.to_json())

# convert the object into a dict
alter_transaction_request_dict = alter_transaction_request_instance.to_dict()
# create an instance of AlterTransactionRequest from a dict
alter_transaction_request_from_dict = AlterTransactionRequest.from_dict(alter_transaction_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


