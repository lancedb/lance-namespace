# IndexContent

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**index_name** | **String** | Name of the index | 
**index_uuid** | **String** | Unique identifier for the index | 
**columns** | **Vec<String>** | Canonical Lance field paths covered by this index. Nested fields use dot-separated segments; segments containing literal dots are backtick-quoted, and backticks inside quoted segments are doubled. | 
**status** | **String** | Current status of the index | 
**index_type** | Option<**String**> | Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index. | [optional]
**type_url** | Option<**String**> | Protobuf type URL, a precise type identifier for the index. | [optional]
**num_indexed_rows** | Option<**i64**> | Number of live rows covered by the index. This does not count rows that are in the index but have since been deleted. | [optional]
**num_unindexed_rows** | Option<**i64**> | Number of rows that are not indexed. | [optional]
**size_bytes** | Option<**i64**> | Total index size in bytes across all segments. Null for indices predating file-size tracking. | [optional]
**num_segments** | Option<**i32**> | Number of index deltas/segments. | [optional]
**created_at** | Option<**String**> | Creation time for indexes. Null for legacy indices. | [optional]
**index_version** | Option<**i32**> | On-disk index format version. | [optional]
**index_details** | Option<**String**> | Opaque, type-specific JSON with additional index details. For vector indices this carries metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters. | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


