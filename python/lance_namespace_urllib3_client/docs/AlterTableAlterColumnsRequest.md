# AlterTableAlterColumnsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**alterations** | [**List[AlterColumnsEntry]**](AlterColumnsEntry.md) | List of column alterations to perform | 

## Example

```python
from lance_namespace_urllib3_client.models.alter_table_alter_columns_request import AlterTableAlterColumnsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableAlterColumnsRequest from a JSON string
alter_table_alter_columns_request_instance = AlterTableAlterColumnsRequest.from_json(json)
# print the JSON string representation of the object
print(AlterTableAlterColumnsRequest.to_json())

# convert the object into a dict
alter_table_alter_columns_request_dict = alter_table_alter_columns_request_instance.to_dict()
# create an instance of AlterTableAlterColumnsRequest from a dict
alter_table_alter_columns_request_from_dict = AlterTableAlterColumnsRequest.from_dict(alter_table_alter_columns_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


