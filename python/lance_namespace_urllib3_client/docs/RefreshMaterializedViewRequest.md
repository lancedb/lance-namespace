# RefreshMaterializedViewRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**src_version** | **int** | Optional source version to refresh from | [optional] 
**max_rows_per_fragment** | **int** | Optional maximum rows per fragment | [optional] 
**concurrency** | **int** | Optional concurrency override | [optional] 
**intra_applier_concurrency** | **int** | Optional intra-applier concurrency override | [optional] 
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


