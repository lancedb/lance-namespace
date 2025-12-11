# CreateNamespaceResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**properties** | **Dict[str, str]** | Properties after the namespace is created.  If the server does not support namespace properties, it should return null for this field. If namespace properties are supported, but none are set, it should return an empty object.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_namespace_response import CreateNamespaceResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateNamespaceResponse from a JSON string
create_namespace_response_instance = CreateNamespaceResponse.from_json(json)
# print the JSON string representation of the object
print(CreateNamespaceResponse.to_json())

# convert the object into a dict
create_namespace_response_dict = create_namespace_response_instance.to_dict()
# create an instance of CreateNamespaceResponse from a dict
create_namespace_response_from_dict = CreateNamespaceResponse.from_dict(create_namespace_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


