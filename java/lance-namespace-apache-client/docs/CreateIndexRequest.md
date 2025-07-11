

# CreateIndexRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | The table name |  |
|**namespace** | **List&lt;String&gt;** | The namespace identifier |  |
|**column** | **String** | Name of the column to create index on |  |
|**indexType** | [**IndexTypeEnum**](#IndexTypeEnum) | Type of index to create |  |
|**metricType** | [**MetricTypeEnum**](#MetricTypeEnum) | Distance metric type for vector indexes |  [optional] |
|**numPartitions** | **Integer** | Number of partitions for IVF indexes |  [optional] |
|**numSubVectors** | **Integer** | Number of sub-vectors for PQ indexes |  [optional] |
|**numBits** | **Integer** | Number of bits for scalar quantization |  [optional] |
|**maxIterations** | **Integer** | Maximum iterations for index building |  [optional] |
|**sampleRate** | **Integer** | Sample rate for index building |  [optional] |



## Enum: IndexTypeEnum

| Name | Value |
|---- | -----|
| BTREE | &quot;BTREE&quot; |
| BITMAP | &quot;BITMAP&quot; |
| LABEL_LIST | &quot;LABEL_LIST&quot; |
| IVF_FLAT | &quot;IVF_FLAT&quot; |
| IVF_HNSW_SQ | &quot;IVF_HNSW_SQ&quot; |
| IVF_PQ | &quot;IVF_PQ&quot; |
| FTS | &quot;FTS&quot; |



## Enum: MetricTypeEnum

| Name | Value |
|---- | -----|
| L2 | &quot;l2&quot; |
| COSINE | &quot;cosine&quot; |
| DOT | &quot;dot&quot; |



