# InvalidTableSchemaError

The provided schema is invalid

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
from lance_namespace_urllib3_client.models.invalid_table_schema_error import InvalidTableSchemaError

# TODO update the JSON string below
json = "{}"
# create an instance of InvalidTableSchemaError from a JSON string
invalid_table_schema_error_instance = InvalidTableSchemaError.from_json(json)
# print the JSON string representation of the object
print(InvalidTableSchemaError.to_json())

# convert the object into a dict
invalid_table_schema_error_dict = invalid_table_schema_error_instance.to_dict()
# create an instance of InvalidTableSchemaError from a dict
invalid_table_schema_error_from_dict = InvalidTableSchemaError.from_dict(invalid_table_schema_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


