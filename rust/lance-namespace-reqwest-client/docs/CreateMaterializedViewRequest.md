# CreateMaterializedViewRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> | View identifier path (namespace + view name) | [optional]
**kind** | **String** | The materialized view kind. - `query` — plain query-backed view (no UDTF), 1:1 rows. - `udtf` — batch UDTF-backed view (N:M rows, full refresh). - `chunker`, aka 'scalar_udtf' — chunker view (1:N row expansion, incremental refresh).  | 
**source_query** | **String** | Opaque serialized representation of the source query that defines the view's input. The format is defined by the client; the namespace server stores it without interpreting it.  | 
**output_schema** | **String** | Base64-encoded Arrow schema of the view output | 
**udtf_spec** | Option<[**models::MaterializedViewUdtfEntry**](MaterializedViewUdtfEntry.md)> |  | [optional]
**with_no_data** | Option<**bool**> | If false, the server kicks off an initial refresh immediately after creating the view and the response includes a job ID.  | [optional][default to true]
**auto_refresh** | Option<**bool**> | If true, the view is automatically refreshed when source-table data changes past the deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on the deployment, not per-view.  | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


