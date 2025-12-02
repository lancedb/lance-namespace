# NamespaceAlreadyExistsError

A namespace with the same name already exists

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
from lance_namespace_urllib3_client.models.namespace_already_exists_error import NamespaceAlreadyExistsError

# TODO update the JSON string below
json = "{}"
# create an instance of NamespaceAlreadyExistsError from a JSON string
namespace_already_exists_error_instance = NamespaceAlreadyExistsError.from_json(json)
# print the JSON string representation of the object
print(NamespaceAlreadyExistsError.to_json())

# convert the object into a dict
namespace_already_exists_error_dict = namespace_already_exists_error_instance.to_dict()
# create an instance of NamespaceAlreadyExistsError from a dict
namespace_already_exists_error_from_dict = NamespaceAlreadyExistsError.from_dict(namespace_already_exists_error_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


