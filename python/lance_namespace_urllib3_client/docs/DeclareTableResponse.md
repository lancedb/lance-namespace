# DeclareTableResponse

Response for declaring a table. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**location** | **str** |  | [optional] 
**storage_options** | **Dict[str, str]** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional] 
**properties** | **Dict[str, str]** | If the implementation does not support table properties, it should return null for this field. Otherwise it should return the properties.  | [optional] 
**managed_versioning** | **bool** | When true, the caller should use namespace table version operations (CreateTableVersion, BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to manage table versions instead of relying on Lance&#39;s native version management.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.declare_table_response import DeclareTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DeclareTableResponse from a JSON string
declare_table_response_instance = DeclareTableResponse.from_json(json)
# print the JSON string representation of the object
print(DeclareTableResponse.to_json())

# convert the object into a dict
declare_table_response_dict = declare_table_response_instance.to_dict()
# create an instance of DeclareTableResponse from a dict
declare_table_response_from_dict = DeclareTableResponse.from_dict(declare_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


