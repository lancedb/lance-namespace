

# IndexContent


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**indexName** | **String** | Name of the index |  |
|**indexUuid** | **String** | Unique identifier for the index |  |
|**columns** | **List&lt;String&gt;** | Columns covered by this index |  |
|**status** | **String** | Current status of the index |  |
|**indexType** | **String** | Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index. |  [optional] |
|**typeUrl** | **String** | Protobuf type URL, a precise type identifier for the index. |  [optional] |
|**numIndexedRows** | **Long** | Number of live rows covered by the index. This does not count rows that are in the index but have since been deleted. |  [optional] |
|**numUnindexedRows** | **Long** | Number of rows that are not indexed. |  [optional] |
|**sizeBytes** | **Long** | Total index size in bytes across all segments. Null for indices predating file-size tracking. |  [optional] |
|**numSegments** | **Integer** | Number of index deltas/segments. |  [optional] |
|**createdAt** | **OffsetDateTime** | Creation time for indexes. Null for legacy indices. |  [optional] |
|**indexVersion** | **Integer** | On-disk index format version. |  [optional] |
|**indexDetails** | **String** | Opaque, type-specific JSON with additional index details. For vector indices this carries metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters. |  [optional] |



