# CreateMaterializedViewRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** | View identifier path (namespace + view name) | [optional] 
**kind** | **str** | The materialized view kind. - &#x60;query&#x60; — plain query-backed view (no UDTF), 1:1 rows. - &#x60;udtf&#x60; — batch UDTF-backed view (N:M rows, full refresh). - &#x60;chunker&#x60;, aka &#39;scalar_udtf&#39; — chunker view (1:N row expansion, incremental refresh).  | 
**source_query** | **str** | Opaque serialized representation of the source query that defines the view&#39;s input. The format is defined by the client; the namespace server stores it without interpreting it.  | 
**output_schema** | **str** | Base64-encoded Arrow schema of the view output | 
**udtf_spec** | [**MaterializedViewUdtfEntry**](MaterializedViewUdtfEntry.md) |  | [optional] 
**with_no_data** | **bool** | If false, the server kicks off an initial refresh immediately after creating the view and the response includes a job ID.  | [optional] [default to True]
**auto_refresh** | **bool** | If true, the view is automatically refreshed when source-table data changes past the deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on the deployment, not per-view.  | [optional] [default to False]

## Example

```python
from lance_namespace_urllib3_client.models.create_materialized_view_request import CreateMaterializedViewRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateMaterializedViewRequest from a JSON string
create_materialized_view_request_instance = CreateMaterializedViewRequest.from_json(json)
# print the JSON string representation of the object
print(CreateMaterializedViewRequest.to_json())

# convert the object into a dict
create_materialized_view_request_dict = create_materialized_view_request_instance.to_dict()
# create an instance of CreateMaterializedViewRequest from a dict
create_materialized_view_request_from_dict = CreateMaterializedViewRequest.from_dict(create_materialized_view_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


