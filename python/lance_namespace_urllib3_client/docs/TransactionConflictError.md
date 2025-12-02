# TransactionConflictError

Transaction failed due to concurrent modification

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | Brief error message | 
**code** | **int** | HTTP status code | 
**type** | **str** | Error type identifier | 
**detail** | **str** | Detailed error explanation | [optional] 
**instance** | **str** | Specific occurrence identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.transaction_conflict_error import TransactionConflictError

# TODO update the JSON string below
json = "{}"
# create an instance of TransactionConflictError from a JSON string
transaction_conflict_error_instance = TransactionConflictError.from_json(json)
# print the JSON string representation of the object
print(TransactionConflictError.to_json())

# convert the object into a dict
transaction_conflict_error_dict = transaction_conflict_error_instance.to_dict()
# create an instance of TransactionConflictError from a dict
transaction_conflict_error_from_dict = TransactionConflictError.from_dict(transaction_conflict_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


