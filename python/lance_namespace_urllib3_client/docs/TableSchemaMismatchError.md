# TableSchemaMismatchError

The data schema does not match the table schema

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
from lance_namespace_urllib3_client.models.table_schema_mismatch_error import TableSchemaMismatchError

# TODO update the JSON string below
json = "{}"
# create an instance of TableSchemaMismatchError from a JSON string
table_schema_mismatch_error_instance = TableSchemaMismatchError.from_json(json)
# print the JSON string representation of the object
print(TableSchemaMismatchError.to_json())

# convert the object into a dict
table_schema_mismatch_error_dict = table_schema_mismatch_error_instance.to_dict()
# create an instance of TableSchemaMismatchError from a dict
table_schema_mismatch_error_from_dict = TableSchemaMismatchError.from_dict(table_schema_mismatch_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


