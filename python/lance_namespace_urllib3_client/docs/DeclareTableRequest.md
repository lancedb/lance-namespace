# DeclareTableRequest

Request for declaring a table. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** | Optional storage location for the table. If not provided, the namespace implementation should determine the table location.  | [optional] 
**vend_credentials** | **bool** | Whether to include vended credentials in the response &#x60;storage_options&#x60;. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials.  | [optional] 
**properties** | **Dict[str, str]** | Business logic properties stored and managed by the namespace implementation outside Lance context, if supported by the implementation.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.declare_table_request import DeclareTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeclareTableRequest from a JSON string
declare_table_request_instance = DeclareTableRequest.from_json(json)
# print the JSON string representation of the object
print(DeclareTableRequest.to_json())

# convert the object into a dict
declare_table_request_dict = declare_table_request_instance.to_dict()
# create an instance of DeclareTableRequest from a dict
declare_table_request_from_dict = DeclareTableRequest.from_dict(declare_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


