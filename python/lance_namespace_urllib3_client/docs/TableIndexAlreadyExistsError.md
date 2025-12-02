# TableIndexAlreadyExistsError

An index with the same name already exists

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
from lance_namespace_urllib3_client.models.table_index_already_exists_error import TableIndexAlreadyExistsError

# TODO update the JSON string below
json = "{}"
# create an instance of TableIndexAlreadyExistsError from a JSON string
table_index_already_exists_error_instance = TableIndexAlreadyExistsError.from_json(json)
# print the JSON string representation of the object
print(TableIndexAlreadyExistsError.to_json())

# convert the object into a dict
table_index_already_exists_error_dict = table_index_already_exists_error_instance.to_dict()
# create an instance of TableIndexAlreadyExistsError from a dict
table_index_already_exists_error_from_dict = TableIndexAlreadyExistsError.from_dict(table_index_already_exists_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


