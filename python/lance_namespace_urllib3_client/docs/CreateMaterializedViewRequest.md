# CreateMaterializedViewRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
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


