# NamespaceNotEmptyError

Cannot drop namespace because it contains tables or child namespaces

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
from lance_namespace_urllib3_client.models.namespace_not_empty_error import NamespaceNotEmptyError

# TODO update the JSON string below
json = "{}"
# create an instance of NamespaceNotEmptyError from a JSON string
namespace_not_empty_error_instance = NamespaceNotEmptyError.from_json(json)
# print the JSON string representation of the object
print(NamespaceNotEmptyError.to_json())

# convert the object into a dict
namespace_not_empty_error_dict = namespace_not_empty_error_instance.to_dict()
# create an instance of NamespaceNotEmptyError from a dict
namespace_not_empty_error_from_dict = NamespaceNotEmptyError.from_dict(namespace_not_empty_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


