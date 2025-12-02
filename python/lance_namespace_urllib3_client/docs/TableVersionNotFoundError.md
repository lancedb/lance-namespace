# TableVersionNotFoundError

The requested table version does not exist

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
from lance_namespace_urllib3_client.models.table_version_not_found_error import TableVersionNotFoundError

# TODO update the JSON string below
json = "{}"
# create an instance of TableVersionNotFoundError from a JSON string
table_version_not_found_error_instance = TableVersionNotFoundError.from_json(json)
# print the JSON string representation of the object
print(TableVersionNotFoundError.to_json())

# convert the object into a dict
table_version_not_found_error_dict = table_version_not_found_error_instance.to_dict()
# create an instance of TableVersionNotFoundError from a dict
table_version_not_found_error_from_dict = TableVersionNotFoundError.from_dict(table_version_not_found_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


