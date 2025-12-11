# DescribeNamespaceResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**properties** | **Dict[str, str]** | Properties stored on the namespace, if supported by the server. If the server does not support namespace properties, it should return null for this field. If namespace properties are supported, but none are set, it should return an empty object. | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_namespace_response import DescribeNamespaceResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeNamespaceResponse from a JSON string
describe_namespace_response_instance = DescribeNamespaceResponse.from_json(json)
# print the JSON string representation of the object
print(DescribeNamespaceResponse.to_json())

# convert the object into a dict
describe_namespace_response_dict = describe_namespace_response_instance.to_dict()
# create an instance of DescribeNamespaceResponse from a dict
describe_namespace_response_from_dict = DescribeNamespaceResponse.from_dict(describe_namespace_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


