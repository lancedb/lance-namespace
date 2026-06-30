# GetTableStatsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**total_bytes** | **int** | The total number of bytes in the table | 
**num_rows** | **int** | The number of rows in the table | 
**num_indices** | **int** | The number of indices in the table | 
**fragment_stats** | [**FragmentStats**](FragmentStats.md) | Statistics on table fragments | 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_stats_response import GetTableStatsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableStatsResponse from a JSON string
get_table_stats_response_instance = GetTableStatsResponse.from_json(json)
# print the JSON string representation of the object
print(GetTableStatsResponse.to_json())

# convert the object into a dict
get_table_stats_response_dict = get_table_stats_response_instance.to_dict()
# create an instance of GetTableStatsResponse from a dict
get_table_stats_response_from_dict = GetTableStatsResponse.from_dict(get_table_stats_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


