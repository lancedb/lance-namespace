# NamespaceExistsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.namespace_exists_request import NamespaceExistsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of NamespaceExistsRequest from a JSON string
namespace_exists_request_instance = NamespaceExistsRequest.from_json(json)
# print the JSON string representation of the object
print(NamespaceExistsRequest.to_json())

# convert the object into a dict
namespace_exists_request_dict = namespace_exists_request_instance.to_dict()
# create an instance of NamespaceExistsRequest from a dict
namespace_exists_request_from_dict = NamespaceExistsRequest.from_dict(namespace_exists_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


