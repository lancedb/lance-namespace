# AlterTransactionSetStatus


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | **str** | The status of a transaction. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Queued: the transaction is queued and not yet started - Running: the transaction is currently running - Succeeded: the transaction has completed successfully - Failed: the transaction has failed - Canceled: the transaction was canceled  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.alter_transaction_set_status import AlterTransactionSetStatus

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTransactionSetStatus from a JSON string
alter_transaction_set_status_instance = AlterTransactionSetStatus.from_json(json)
# print the JSON string representation of the object
print(AlterTransactionSetStatus.to_json())

# convert the object into a dict
alter_transaction_set_status_dict = alter_transaction_set_status_instance.to_dict()
# create an instance of AlterTransactionSetStatus from a dict
alter_transaction_set_status_from_dict = AlterTransactionSetStatus.from_dict(alter_transaction_set_status_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


