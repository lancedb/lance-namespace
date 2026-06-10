

# MaterializedViewUdtfEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**kind** | [**KindEnum**](#KindEnum) | Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s &#x60;kind&#x60;.  |  |
|**udtf** | **String** | Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).  |  |
|**udtfSha** | **String** | SHA-256 checksum of the envelope; server validates. |  |
|**udtfName** | **String** | Name of the UDTF |  |
|**udtfVersion** | **String** | Version of the UDTF |  |
|**inputColumns** | **List&lt;String&gt;** | Source Lance field paths the UDTF reads. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Null means all fields (batch UDTF only).  |  [optional] |
|**partitionBy** | **String** |  |  [optional] |
|**partitionByIndexedColumn** | **String** |  |  [optional] |
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



