# AlterTableBackfillColumnsRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**column** | **String** | Lance field path to backfill. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**r#where** | Option<**String**> | Optional WHERE clause filter | [optional]
**concurrency** | Option<**i32**> | Optional concurrency override | [optional]
**intra_applier_concurrency** | Option<**i32**> | Optional intra-applier concurrency override | [optional]
**min_checkpoint_size** | Option<**i32**> | Optional minimum checkpoint size | [optional]
**max_checkpoint_size** | Option<**i32**> | Optional maximum checkpoint size | [optional]
**batch_checkpoint_flush_interval_seconds** | Option<**f64**> | Optional batch checkpoint flush interval in seconds | [optional]
**read_version** | Option<**i32**> | Optional table version to read from | [optional]
**task_size** | Option<**i32**> | Optional task size | [optional]
**num_frags** | Option<**i32**> | Optional number of fragments | [optional]
**checkpoint_size** | Option<**i32**> | Optional checkpoint size | [optional]
**commit_granularity** | Option<**i32**> | Optional commit granularity | [optional]
**cluster** | Option<**String**> | Optional cluster name | [optional]
**manifest** | Option<**String**> | Optional manifest name | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


