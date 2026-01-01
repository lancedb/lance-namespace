# DropNamespaceRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**mode** | **str** | The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Fail (default): the server must return 400 indicating the namespace to drop does not exist. - Skip: the server must return 204 indicating the drop operation has succeeded.  | [optional] 
**behavior** | **str** | The behavior for dropping a namespace. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Restrict (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - Cascade: all tables and child namespaces in the namespace are dropped before the namespace is dropped.  | [optional] 

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


