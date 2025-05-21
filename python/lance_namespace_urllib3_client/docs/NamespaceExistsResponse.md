# NamespaceExistsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**exists** | **bool** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.namespace_exists_response import NamespaceExistsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of NamespaceExistsResponse from a JSON string
namespace_exists_response_instance = NamespaceExistsResponse.from_json(json)
# print the JSON string representation of the object
print(NamespaceExistsResponse.to_json())

# convert the object into a dict
namespace_exists_response_dict = namespace_exists_response_instance.to_dict()
# create an instance of NamespaceExistsResponse from a dict
namespace_exists_response_from_dict = NamespaceExistsResponse.from_dict(namespace_exists_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


