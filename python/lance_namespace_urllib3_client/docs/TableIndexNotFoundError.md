# TableIndexNotFoundError

The requested index does not exist

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
from lance_namespace_urllib3_client.models.table_index_not_found_error import TableIndexNotFoundError

# TODO update the JSON string below
json = "{}"
# create an instance of TableIndexNotFoundError from a JSON string
table_index_not_found_error_instance = TableIndexNotFoundError.from_json(json)
# print the JSON string representation of the object
print(TableIndexNotFoundError.to_json())

# convert the object into a dict
table_index_not_found_error_dict = table_index_not_found_error_instance.to_dict()
# create an instance of TableIndexNotFoundError from a dict
table_index_not_found_error_from_dict = TableIndexNotFoundError.from_dict(table_index_not_found_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


