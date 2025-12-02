# AlterTableAlterColumns400Response


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
from lance_namespace_urllib3_client.models.alter_table_alter_columns400_response import AlterTableAlterColumns400Response

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableAlterColumns400Response from a JSON string
alter_table_alter_columns400_response_instance = AlterTableAlterColumns400Response.from_json(json)
# print the JSON string representation of the object
print(AlterTableAlterColumns400Response.to_json())

# convert the object into a dict
alter_table_alter_columns400_response_dict = alter_table_alter_columns400_response_instance.to_dict()
# create an instance of AlterTableAlterColumns400Response from a dict
alter_table_alter_columns400_response_from_dict = AlterTableAlterColumns400Response.from_dict(alter_table_alter_columns400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


