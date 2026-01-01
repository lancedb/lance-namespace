# DescribeTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional] 
**with_table_uri** | **bool** | Whether to include the table URI in the response. Default is false.  | [optional] [default to False]
**load_detailed_metadata** | **bool** | Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as &#x60;version&#x60;, &#x60;schema&#x60;, and &#x60;stats&#x60; which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return.  | [optional] 
**vend_credentials** | **bool** | Whether to include vended credentials in the response &#x60;storage_options&#x60;. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials.  | [optional] 

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


