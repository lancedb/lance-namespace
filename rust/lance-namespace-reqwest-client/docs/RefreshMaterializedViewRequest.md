# RefreshMaterializedViewRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**src_version** | Option<**i32**> | Optional source version to refresh from | [optional]
**max_rows_per_fragment** | Option<**i32**> | Optional maximum rows per fragment | [optional]
**concurrency** | Option<**i32**> | Optional concurrency override | [optional]
**intra_applier_concurrency** | Option<**i32**> | Optional intra-applier concurrency override | [optional]
**cluster** | Option<**String**> | Optional cluster name (operational override) | [optional]
**output_limit** | Option<**i32**> | Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds.  | [optional]
**manifest** | Option<**String**> | Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view's snapshotted manifest. When omitted, the manifest stored in the view's metadata is used.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


