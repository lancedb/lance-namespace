# MaterializedViewUdtfEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**kind** | **String** | Discriminates a batch UDTF (`udtf`, full-overwrite refresh) from a chunker (`chunker`, incremental 1:N refresh). Must match the enclosing request's `kind`.  | 
**udtf** | **String** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  | 
**udtf_sha** | **String** | SHA-256 checksum of the envelope; server validates. | 
**udtf_name** | **String** | Name of the UDTF | 
**udtf_version** | **String** | Version of the UDTF | 
**input_columns** | Option<**Vec<String>**> | Source columns the UDTF reads. Null means all columns (batch UDTF only).  | [optional]
**partition_by** | Option<**String**> | Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually exclusive with `partition_by_indexed_column`.  | [optional]
**partition_by_indexed_column** | Option<**String**> | Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The server validates the index exists at create time.  | [optional]
**num_cpus** | Option<**f64**> | Ray actor CPU request. | [optional]
**num_gpus** | Option<**f64**> | Ray actor GPU request. | [optional]
**memory** | Option<**i32**> | Ray actor memory request, in bytes. | [optional]
**error_handling** | Option<[**serde_json::Value**](.md)> | Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip behavior.  | [optional]
**batch** | Option<**bool**> | Chunker only. True for a batched chunker; affects how the worker dispatches input rows.  | [optional]
**manifest** | Option<**String**> | JSON-serialized GenevaManifest for the UDTF environment. | [optional]
**manifest_checksum** | Option<**String**> | SHA-256 checksum of the manifest content. | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


