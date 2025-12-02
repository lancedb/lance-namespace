# NamespaceNotFoundError

The requested namespace does not exist

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
from lance_namespace_urllib3_client.models.namespace_not_found_error import NamespaceNotFoundError

# TODO update the JSON string below
json = "{}"
# create an instance of NamespaceNotFoundError from a JSON string
namespace_not_found_error_instance = NamespaceNotFoundError.from_json(json)
# print the JSON string representation of the object
print(NamespaceNotFoundError.to_json())

# convert the object into a dict
namespace_not_found_error_dict = namespace_not_found_error_instance.to_dict()
# create an instance of NamespaceNotFoundError from a dict
namespace_not_found_error_from_dict = NamespaceNotFoundError.from_dict(namespace_not_found_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


