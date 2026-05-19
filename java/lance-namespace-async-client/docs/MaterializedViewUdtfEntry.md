

# MaterializedViewUdtfEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s &#x60;kind&#x60;.  |  |
|**udtf** | **String** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  |  |
|**udtfSha** | **String** | SHA-256 checksum of the envelope; server validates. |  |
|**udtfName** | **String** | Name of the UDTF |  |
|**udtfVersion** | **String** | Version of the UDTF |  |
|**inputColumns** | **List&lt;String&gt;** | Source columns the UDTF reads. Null means all columns (batch UDTF only).  |  [optional] |
|**partitionBy** | **String** | Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually exclusive with &#x60;partition_by_indexed_column&#x60;.  |  [optional] |
|**partitionByIndexedColumn** | **String** | Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The server validates the index exists at create time.  |  [optional] |
|**numCpus** | **BigDecimal** | Ray actor CPU request. |  [optional] |
|**numGpus** | **BigDecimal** | Ray actor GPU request. |  [optional] |
|**memory** | **Integer** | Ray actor memory request, in bytes. |  [optional] |
|**errorHandling** | **Object** | Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip behavior.  |  [optional] |
|**batch** | **Boolean** | Chunker only. True for a batched chunker; affects how the worker dispatches input rows.  |  [optional] |
|**manifest** | **String** | JSON-serialized GenevaManifest for the UDTF environment. |  [optional] |
|**manifestChecksum** | **String** | SHA-256 checksum of the manifest content. |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| UDTF | &quot;udtf&quot; |
| CHUNKER | &quot;chunker&quot; |



