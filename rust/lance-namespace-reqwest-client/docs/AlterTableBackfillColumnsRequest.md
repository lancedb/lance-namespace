# AlterTableBackfillColumnsRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**column** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | 
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


