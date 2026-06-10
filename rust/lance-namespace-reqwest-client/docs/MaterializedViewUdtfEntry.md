# MaterializedViewUdtfEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**kind** | **String** | Discriminates a batch UDTF (`udtf`, full-overwrite refresh) from a chunker (`chunker`, incremental 1:N refresh). Must match the enclosing request's `kind`.  | 
**udtf** | **String** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  | 
**udtf_sha** | **String** | SHA-256 checksum of the envelope; server validates. | 
**udtf_name** | **String** | Name of the UDTF | 
**udtf_version** | **String** | Version of the UDTF | 
**input_columns** | Option<**Vec<String>**> | Source field paths the UDTF reads. Null means all fields (batch UDTF only).  | [optional]
**partition_by** | Option<**String**> | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional]
**partition_by_indexed_column** | Option<**String**> | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional]
**num_cpus** | Option<**f64**> | Ray actor CPU request. | [optional]
**num_gpus** | Option<**f64**> | Ray actor GPU request. | [optional]
**memory** | Option<**i32**> | Ray actor memory request, in bytes. | [optional]
**error_handling** | Option<[**serde_json::Value**](.md)> | Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip behavior.  | [optional]
**batch** | Option<**bool**> | Chunker only. True for a batched chunker; affects how the worker dispatches input rows.  | [optional]
**manifest** | Option<**String**> | JSON-serialized GenevaManifest for the UDTF environment. | [optional]
**manifest_checksum** | Option<**String**> | SHA-256 checksum of the manifest content. | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


