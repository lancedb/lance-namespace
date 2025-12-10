# AlterTableDropColumnsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**version** | **int** | Version of the table after dropping columns | 

## Example

```python
from lance_namespace_urllib3_client.models.alter_table_drop_columns_response import AlterTableDropColumnsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableDropColumnsResponse from a JSON string
alter_table_drop_columns_response_instance = AlterTableDropColumnsResponse.from_json(json)
# print the JSON string representation of the object
print(AlterTableDropColumnsResponse.to_json())

# convert the object into a dict
alter_table_drop_columns_response_dict = alter_table_drop_columns_response_instance.to_dict()
# create an instance of AlterTableDropColumnsResponse from a dict
alter_table_drop_columns_response_from_dict = AlterTableDropColumnsResponse.from_dict(alter_table_drop_columns_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


