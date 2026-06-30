# TableExistsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to check existence. If not specified, server should resolve it to the latest version.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.table_exists_request import TableExistsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of TableExistsRequest from a JSON string
table_exists_request_instance = TableExistsRequest.from_json(json)
# print the JSON string representation of the object
print(TableExistsRequest.to_json())

# convert the object into a dict
table_exists_request_dict = table_exists_request_instance.to_dict()
# create an instance of TableExistsRequest from a dict
table_exists_request_from_dict = TableExistsRequest.from_dict(table_exists_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


