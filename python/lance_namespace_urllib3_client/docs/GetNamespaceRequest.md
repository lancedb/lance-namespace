# GetNamespaceRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**parent** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.get_namespace_request import GetNamespaceRequest

# TODO update the JSON string below
json = "{}"
# create an instance of GetNamespaceRequest from a JSON string
get_namespace_request_instance = GetNamespaceRequest.from_json(json)
# print the JSON string representation of the object
print(GetNamespaceRequest.to_json())

# convert the object into a dict
get_namespace_request_dict = get_namespace_request_instance.to_dict()
# create an instance of GetNamespaceRequest from a dict
get_namespace_request_from_dict = GetNamespaceRequest.from_dict(get_namespace_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


