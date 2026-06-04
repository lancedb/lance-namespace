# DeleteTableBranchResponse

Response for delete branch operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.delete_table_branch_response import DeleteTableBranchResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteTableBranchResponse from a JSON string
delete_table_branch_response_instance = DeleteTableBranchResponse.from_json(json)
# print the JSON string representation of the object
print(DeleteTableBranchResponse.to_json())

# convert the object into a dict
delete_table_branch_response_dict = delete_table_branch_response_instance.to_dict()
# create an instance of DeleteTableBranchResponse from a dict
delete_table_branch_response_from_dict = DeleteTableBranchResponse.from_dict(delete_table_branch_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


