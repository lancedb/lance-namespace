# AlterTableAlterColumnsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**version** | **int** | Version of the table after altering columns | 

## Example

```python
from lance_namespace_urllib3_client.models.alter_table_alter_columns_response import AlterTableAlterColumnsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableAlterColumnsResponse from a JSON string
alter_table_alter_columns_response_instance = AlterTableAlterColumnsResponse.from_json(json)
# print the JSON string representation of the object
print(AlterTableAlterColumnsResponse.to_json())

# convert the object into a dict
alter_table_alter_columns_response_dict = alter_table_alter_columns_response_instance.to_dict()
# create an instance of AlterTableAlterColumnsResponse from a dict
alter_table_alter_columns_response_from_dict = AlterTableAlterColumnsResponse.from_dict(alter_table_alter_columns_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


