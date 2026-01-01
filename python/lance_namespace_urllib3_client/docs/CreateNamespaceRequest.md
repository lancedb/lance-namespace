# CreateNamespaceRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**mode** | **str** | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing namespace is kept.   * Overwrite: the existing namespace is dropped and a new empty namespace with this name is created.  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_namespace_request import CreateNamespaceRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateNamespaceRequest from a JSON string
create_namespace_request_instance = CreateNamespaceRequest.from_json(json)
# print the JSON string representation of the object
print(CreateNamespaceRequest.to_json())

# convert the object into a dict
create_namespace_request_dict = create_namespace_request_instance.to_dict()
# create an instance of CreateNamespaceRequest from a dict
create_namespace_request_from_dict = CreateNamespaceRequest.from_dict(create_namespace_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


