# CreateTableBranchResponse

Response for create branch operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_branch_response import CreateTableBranchResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableBranchResponse from a JSON string
create_table_branch_response_instance = CreateTableBranchResponse.from_json(json)
# print the JSON string representation of the object
print(CreateTableBranchResponse.to_json())

# convert the object into a dict
create_table_branch_response_dict = create_table_branch_response_instance.to_dict()
# create an instance of CreateTableBranchResponse from a dict
create_table_branch_response_from_dict = CreateTableBranchResponse.from_dict(create_table_branch_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


