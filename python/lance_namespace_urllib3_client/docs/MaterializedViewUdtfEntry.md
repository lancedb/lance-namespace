# MaterializedViewUdtfEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**kind** | **str** | Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s &#x60;kind&#x60;.  | 
**udtf** | **str** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  | 
**udtf_sha** | **str** | SHA-256 checksum of the envelope; server validates. | 
**udtf_name** | **str** | Name of the UDTF | 
**udtf_version** | **str** | Version of the UDTF | 
**input_columns** | **List[str]** | Source field paths the UDTF reads. Null means all fields (batch UDTF only).  | [optional] 
**partition_by** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional] 
**partition_by_indexed_column** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional] 
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


