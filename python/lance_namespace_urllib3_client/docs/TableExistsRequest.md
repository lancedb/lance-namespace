# TableExistsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  | [optional] 
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


