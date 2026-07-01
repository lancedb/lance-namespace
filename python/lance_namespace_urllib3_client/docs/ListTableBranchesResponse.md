# ListTableBranchesResponse

Response containing table branches

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**branches** | [**Dict[str, BranchContents]**](BranchContents.md) | Map of branch names to their contents | 
**page_token** | **str** | An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation, if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request, the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either &#x60;null&#x60;, missing value or empty string value of the page token from the implementation&#39;s response as the end of the listing results.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.list_table_branches_response import ListTableBranchesResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ListTableBranchesResponse from a JSON string
list_table_branches_response_instance = ListTableBranchesResponse.from_json(json)
# print the JSON string representation of the object
print(ListTableBranchesResponse.to_json())

# convert the object into a dict
list_table_branches_response_dict = list_table_branches_response_instance.to_dict()
# create an instance of ListTableBranchesResponse from a dict
list_table_branches_response_from_dict = ListTableBranchesResponse.from_dict(list_table_branches_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


