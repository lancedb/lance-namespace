# InvalidIdentifierError

Invalid identifier format

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
from lance_namespace_urllib3_client.models.invalid_identifier_error import InvalidIdentifierError

# TODO update the JSON string below
json = "{}"
# create an instance of InvalidIdentifierError from a JSON string
invalid_identifier_error_instance = InvalidIdentifierError.from_json(json)
# print the JSON string representation of the object
print(InvalidIdentifierError.to_json())

# convert the object into a dict
invalid_identifier_error_dict = invalid_identifier_error_instance.to_dict()
# create an instance of InvalidIdentifierError from a dict
invalid_identifier_error_from_dict = InvalidIdentifierError.from_dict(invalid_identifier_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


