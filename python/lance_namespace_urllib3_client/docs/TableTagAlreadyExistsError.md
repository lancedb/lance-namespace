# TableTagAlreadyExistsError

A tag with the same name already exists

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
from lance_namespace_urllib3_client.models.table_tag_already_exists_error import TableTagAlreadyExistsError

# TODO update the JSON string below
json = "{}"
# create an instance of TableTagAlreadyExistsError from a JSON string
table_tag_already_exists_error_instance = TableTagAlreadyExistsError.from_json(json)
# print the JSON string representation of the object
print(TableTagAlreadyExistsError.to_json())

# convert the object into a dict
table_tag_already_exists_error_dict = table_tag_already_exists_error_instance.to_dict()
# create an instance of TableTagAlreadyExistsError from a dict
table_tag_already_exists_error_from_dict = TableTagAlreadyExistsError.from_dict(table_tag_already_exists_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


