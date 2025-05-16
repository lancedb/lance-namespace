# DropNamespaceRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**parent** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_namespace_request import DropNamespaceRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DropNamespaceRequest from a JSON string
drop_namespace_request_instance = DropNamespaceRequest.from_json(json)
# print the JSON string representation of the object
print(DropNamespaceRequest.to_json())

# convert the object into a dict
drop_namespace_request_dict = drop_namespace_request_instance.to_dict()
# create an instance of DropNamespaceRequest from a dict
drop_namespace_request_from_dict = DropNamespaceRequest.from_dict(drop_namespace_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


