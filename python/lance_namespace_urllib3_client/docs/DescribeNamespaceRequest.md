# DescribeNamespaceRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_namespace_request import DescribeNamespaceRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeNamespaceRequest from a JSON string
describe_namespace_request_instance = DescribeNamespaceRequest.from_json(json)
# print the JSON string representation of the object
print(DescribeNamespaceRequest.to_json())

# convert the object into a dict
describe_namespace_request_dict = describe_namespace_request_instance.to_dict()
# create an instance of DescribeNamespaceRequest from a dict
describe_namespace_request_from_dict = DescribeNamespaceRequest.from_dict(describe_namespace_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


