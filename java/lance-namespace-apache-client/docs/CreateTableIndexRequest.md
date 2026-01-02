

# CreateTableIndexRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**column** | **String** | Name of the column to create index on |  |
|**indexType** | **String** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) |  |
|**name** | **String** | Optional name for the index. If not provided, a name will be auto-generated. |  [optional] |
|**distanceType** | **String** | Distance metric type for vector indexes (e.g., l2, cosine, dot) |  [optional] |
|**withPosition** | **Boolean** | Optional FTS parameter for position tracking |  [optional] |
|**baseTokenizer** | **String** | Optional FTS parameter for base tokenizer |  [optional] |
|**language** | **String** | Optional FTS parameter for language |  [optional] |
|**maxTokenLength** | **Integer** | Optional FTS parameter for maximum token length |  [optional] |
|**lowerCase** | **Boolean** | Optional FTS parameter for lowercase conversion |  [optional] |
|**stem** | **Boolean** | Optional FTS parameter for stemming |  [optional] |
|**removeStopWords** | **Boolean** | Optional FTS parameter for stop word removal |  [optional] |
|**asciiFolding** | **Boolean** | Optional FTS parameter for ASCII folding |  [optional] |



