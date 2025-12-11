# DropNamespaceResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**properties** | **Dict[str, str]** |  | [optional] 
**transaction_id** | **List[str]** | If present, indicating the operation is long running and should be tracked using DescribeTransaction  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_namespace_response import DropNamespaceResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DropNamespaceResponse from a JSON string
drop_namespace_response_instance = DropNamespaceResponse.from_json(json)
# print the JSON string representation of the object
print(DropNamespaceResponse.to_json())

# convert the object into a dict
drop_namespace_response_dict = drop_namespace_response_instance.to_dict()
# create an instance of DropNamespaceResponse from a dict
drop_namespace_response_from_dict = DropNamespaceResponse.from_dict(drop_namespace_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


