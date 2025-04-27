# AlterTransactionAction


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **str** |  | 
**status** | [**TransactionStatus**](TransactionStatus.md) |  | [optional] 
**key** | **str** |  | [optional] 
**value** | **str** |  | [optional] 
**mode** | **str** | the behavior if the property key to unset does not exist | [optional] [default to 'SKIP']

## Example

```python
from lance_catalog_urllib3_client.models.alter_transaction_action import AlterTransactionAction

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTransactionAction from a JSON string
alter_transaction_action_instance = AlterTransactionAction.from_json(json)
# print the JSON string representation of the object
print(AlterTransactionAction.to_json())

# convert the object into a dict
alter_transaction_action_dict = alter_transaction_action_instance.to_dict()
# create an instance of AlterTransactionAction from a dict
alter_transaction_action_from_dict = AlterTransactionAction.from_dict(alter_transaction_action_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


