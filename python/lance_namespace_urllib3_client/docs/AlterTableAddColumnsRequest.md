# AlterTableAddColumnsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**new_columns** | [**List[NewColumnTransform]**](NewColumnTransform.md) | List of new columns to add | 

## Example

```python
from lance_namespace_urllib3_client.models.alter_table_add_columns_request import AlterTableAddColumnsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableAddColumnsRequest from a JSON string
alter_table_add_columns_request_instance = AlterTableAddColumnsRequest.from_json(json)
# print the JSON string representation of the object
print(AlterTableAddColumnsRequest.to_json())

# convert the object into a dict
alter_table_add_columns_request_dict = alter_table_add_columns_request_instance.to_dict()
# create an instance of AlterTableAddColumnsRequest from a dict
alter_table_add_columns_request_from_dict = AlterTableAddColumnsRequest.from_dict(alter_table_add_columns_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


