# AlterTransactionAction

A single action that could be performed to alter a transaction. This action holds the model definition for all types of specific actions models, this is to minimize difference and compatibility issue across codegen in different languages. When used, only one of the actions should be non-null for each action. If you would like to perform multiple actions, set a list of actions in the AlterTransactionRequest. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**set_status_action** | [**AlterTransactionSetStatus**](AlterTransactionSetStatus.md) |  | [optional] 
**set_property_action** | [**AlterTransactionSetProperty**](AlterTransactionSetProperty.md) |  | [optional] 
**unset_property_action** | [**AlterTransactionUnsetProperty**](AlterTransactionUnsetProperty.md) |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.alter_transaction_action import AlterTransactionAction

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


