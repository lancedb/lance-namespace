

# CreateTableIndexRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**column** | **String** | Name of the column to create index on |  |
|**indexType** | [**IndexTypeEnum**](#IndexTypeEnum) | Type of index to create |  |
|**metricType** | [**MetricTypeEnum**](#MetricTypeEnum) | Distance metric type for vector indexes |  [optional] |
|**withPosition** | **Boolean** | Optional FTS parameter for position tracking |  [optional] |
|**baseTokenizer** | **String** | Optional FTS parameter for base tokenizer |  [optional] |
|**language** | **String** | Optional FTS parameter for language |  [optional] |
|**maxTokenLength** | **Integer** | Optional FTS parameter for maximum token length |  [optional] |
|**lowerCase** | **Boolean** | Optional FTS parameter for lowercase conversion |  [optional] |
|**stem** | **Boolean** | Optional FTS parameter for stemming |  [optional] |
|**removeStopWords** | **Boolean** | Optional FTS parameter for stop word removal |  [optional] |
|**asciiFolding** | **Boolean** | Optional FTS parameter for ASCII folding |  [optional] |



## Enum: IndexTypeEnum

| Name | Value |
|---- | -----|
| BTREE | &quot;BTREE&quot; |
| BITMAP | &quot;BITMAP&quot; |
| LABEL_LIST | &quot;LABEL_LIST&quot; |
| IVF_FLAT | &quot;IVF_FLAT&quot; |
| IVF_PQ | &quot;IVF_PQ&quot; |
| IVF_HNSW_SQ | &quot;IVF_HNSW_SQ&quot; |
| FTS | &quot;FTS&quot; |



## Enum: MetricTypeEnum

| Name | Value |
|---- | -----|
| L2 | &quot;l2&quot; |
| COSINE | &quot;cosine&quot; |
| DOT | &quot;dot&quot; |



