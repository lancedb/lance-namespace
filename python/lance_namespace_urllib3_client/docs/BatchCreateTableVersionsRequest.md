# BatchCreateTableVersionsRequest

Request to atomically create new version entries for multiple tables. The operation is atomic: all versions are created or none are. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**entries** | [**List[CreateTableVersionEntry]**](CreateTableVersionEntry.md) | List of table version entries to create atomically | 

## Example

```python
from lance_namespace_urllib3_client.models.batch_create_table_versions_request import BatchCreateTableVersionsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of BatchCreateTableVersionsRequest from a JSON string
batch_create_table_versions_request_instance = BatchCreateTableVersionsRequest.from_json(json)
# print the JSON string representation of the object
print(BatchCreateTableVersionsRequest.to_json())

# convert the object into a dict
batch_create_table_versions_request_dict = batch_create_table_versions_request_instance.to_dict()
# create an instance of BatchCreateTableVersionsRequest from a dict
batch_create_table_versions_request_from_dict = BatchCreateTableVersionsRequest.from_dict(batch_create_table_versions_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


