# DescribeTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_request import DescribeTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableRequest from a JSON string
describe_table_request_instance = DescribeTableRequest.from_json(json)
# print the JSON string representation of the object
print(DescribeTableRequest.to_json())

# convert the object into a dict
describe_table_request_dict = describe_table_request_instance.to_dict()
# create an instance of DescribeTableRequest from a dict
describe_table_request_from_dict = DescribeTableRequest.from_dict(describe_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


