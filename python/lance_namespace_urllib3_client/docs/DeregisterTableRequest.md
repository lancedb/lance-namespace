# DeregisterTableRequest

The table content remains available in the storage. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.deregister_table_request import DeregisterTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeregisterTableRequest from a JSON string
deregister_table_request_instance = DeregisterTableRequest.from_json(json)
# print the JSON string representation of the object
print(DeregisterTableRequest.to_json())

# convert the object into a dict
deregister_table_request_dict = deregister_table_request_instance.to_dict()
# create an instance of DeregisterTableRequest from a dict
deregister_table_request_from_dict = DeregisterTableRequest.from_dict(deregister_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


