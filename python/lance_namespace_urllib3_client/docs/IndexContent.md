# IndexContent


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**index_name** | **str** | Name of the index | 
**index_uuid** | **str** | Unique identifier for the index | 
**columns** | **List[str]** | Columns covered by this index | 
**status** | **str** | Current status of the index | 
**index_type** | **str** | Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index. | [optional] 
**type_url** | **str** | Protobuf type URL, a precise type identifier for the index. | [optional] 
**num_indexed_rows** | **int** | Number of live rows covered by the index. This does not count rows that are in the index but have since been deleted. | [optional] 
**num_unindexed_rows** | **int** | Number of rows that are not indexed. | [optional] 
**size_bytes** | **int** | Total index size in bytes across all segments. Null for indices predating file-size tracking. | [optional] 
**num_segments** | **int** | Number of index deltas/segments. | [optional] 
**created_at** | **datetime** | Creation time for indexes. Null for legacy indices. | [optional] 
**index_version** | **int** | On-disk index format version. | [optional] 
**index_details** | **str** | Opaque, type-specific JSON with additional index details. For vector indices this carries metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters. | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.index_content import IndexContent

# TODO update the JSON string below
json = "{}"
# create an instance of IndexContent from a JSON string
index_content_instance = IndexContent.from_json(json)
# print the JSON string representation of the object
print(IndexContent.to_json())

# convert the object into a dict
index_content_dict = index_content_instance.to_dict()
# create an instance of IndexContent from a dict
index_content_from_dict = IndexContent.from_dict(index_content_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


