# RefreshMaterializedViewRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**src_version** | **int** | Optional source version to refresh from | [optional] 
**max_rows_per_fragment** | **int** | Optional maximum rows per fragment | [optional] 
**concurrency** | **int** | Optional concurrency override | [optional] 
**intra_applier_concurrency** | **int** | Optional intra-applier concurrency override | [optional] 
**source_task_size** | **int** | Optional number of source row ids per work item during expansion. Bounds per-actor memory for chunker materialized views.  | [optional] 
**cluster** | **str** | Optional cluster name (operational override) | [optional] 
**output_limit** | **int** | Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds.  | [optional] 
**manifest** | **str** | Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view&#39;s snapshotted manifest. When omitted, the manifest stored in the view&#39;s metadata is used.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.refresh_materialized_view_request import RefreshMaterializedViewRequest

# TODO update the JSON string below
json = "{}"
# create an instance of RefreshMaterializedViewRequest from a JSON string
refresh_materialized_view_request_instance = RefreshMaterializedViewRequest.from_json(json)
# print the JSON string representation of the object
print(RefreshMaterializedViewRequest.to_json())

# convert the object into a dict
refresh_materialized_view_request_dict = refresh_materialized_view_request_instance.to_dict()
# create an instance of RefreshMaterializedViewRequest from a dict
refresh_materialized_view_request_from_dict = RefreshMaterializedViewRequest.from_dict(refresh_materialized_view_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


