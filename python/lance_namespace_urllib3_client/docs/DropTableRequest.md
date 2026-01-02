# DropTableRequest

If the table and its data can be immediately deleted, return information of the deleted table. Otherwise, return a transaction ID that client can use to track deletion progress. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_table_request import DropTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DropTableRequest from a JSON string
drop_table_request_instance = DropTableRequest.from_json(json)
# print the JSON string representation of the object
print(DropTableRequest.to_json())

# convert the object into a dict
drop_table_request_dict = drop_table_request_instance.to_dict()
# create an instance of DropTableRequest from a dict
drop_table_request_from_dict = DropTableRequest.from_dict(drop_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


