# MaterializedViewUdtfEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**kind** | **str** | Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s &#x60;kind&#x60;.  | 
**udtf** | **str** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  | 
**udtf_sha** | **str** | SHA-256 checksum of the envelope; server validates. | 
**udtf_name** | **str** | Name of the UDTF | 
**udtf_version** | **str** | Version of the UDTF | 
**input_columns** | **List[str]** | Source columns the UDTF reads. Null means all columns (batch UDTF only).  | [optional] 
**partition_by** | **str** | Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually exclusive with &#x60;partition_by_indexed_column&#x60;.  | [optional] 
**partition_by_indexed_column** | **str** | Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The server validates the index exists at create time.  | [optional] 
**num_cpus** | **float** | Ray actor CPU request. | [optional] 
**num_gpus** | **float** | Ray actor GPU request. | [optional] 
**memory** | **int** | Ray actor memory request, in bytes. | [optional] 
**error_handling** | **object** | Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip behavior.  | [optional] 
**batch** | **bool** | Chunker only. True for a batched chunker; affects how the worker dispatches input rows.  | [optional] 
**manifest** | **str** | JSON-serialized GenevaManifest for the UDTF environment. | [optional] 
**manifest_checksum** | **str** | SHA-256 checksum of the manifest content. | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.materialized_view_udtf_entry import MaterializedViewUdtfEntry

# TODO update the JSON string below
json = "{}"
# create an instance of MaterializedViewUdtfEntry from a JSON string
materialized_view_udtf_entry_instance = MaterializedViewUdtfEntry.from_json(json)
# print the JSON string representation of the object
print(MaterializedViewUdtfEntry.to_json())

# convert the object into a dict
materialized_view_udtf_entry_dict = materialized_view_udtf_entry_instance.to_dict()
# create an instance of MaterializedViewUdtfEntry from a dict
materialized_view_udtf_entry_from_dict = MaterializedViewUdtfEntry.from_dict(materialized_view_udtf_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


