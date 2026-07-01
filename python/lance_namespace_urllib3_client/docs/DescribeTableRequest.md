# DescribeTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional] 
**tag** | **str** | Tag name to describe the table at. If specified, the server should resolve the tag to a version number and describe that version. Cannot be used together with &#x60;version&#x60; or &#x60;branch&#x60;.  | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**with_table_uri** | **bool** | Whether to include the table URI in the response. Default is false.  | [optional] [default to False]
**load_detailed_metadata** | **bool** | Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as &#x60;version&#x60;, &#x60;schema&#x60;, and &#x60;stats&#x60; which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return.  | [optional] 
**check_declared** | **bool** | Whether to check if the table exists only as a namespace declaration without storage data. Default is false. When true, the response should populate &#x60;is_only_declared&#x60;. When false, the implementation should return null for &#x60;is_only_declared&#x60; unless another option such as &#x60;load_detailed_metadata&#x60; requires checking declared-only table state.  | [optional] [default to False]
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


