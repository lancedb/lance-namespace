

# AnalyzeTableQueryPlanRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**bypassVectorIndex** | **Boolean** | Whether to bypass vector index |  [optional] |
|**columns** | [**QueryTableRequestColumns**](QueryTableRequestColumns.md) |  |  [optional] |
|**distanceType** | **String** | Distance metric to use |  [optional] |
|**ef** | **Integer** | Search effort parameter for HNSW index |  [optional] |
|**fastSearch** | **Boolean** | Whether to use fast search |  [optional] |
|**filter** | **String** | Optional SQL filter expression |  [optional] |
|**fullTextQuery** | [**QueryTableRequestFullTextQuery**](QueryTableRequestFullTextQuery.md) |  |  [optional] |
|**k** | **Integer** | Number of results to return |  |
|**lowerBound** | **Float** | Lower bound for search |  [optional] |
|**nprobes** | **Integer** | Number of probes for IVF index |  [optional] |
|**offset** | **Integer** | Number of results to skip |  [optional] |
|**prefilter** | **Boolean** | Whether to apply filtering before vector search |  [optional] |
|**refineFactor** | **Integer** | Refine factor for search |  [optional] |
|**upperBound** | **Float** | Upper bound for search |  [optional] |
|**vector** | [**QueryTableRequestVector**](QueryTableRequestVector.md) |  |  |
|**vectorColumn** | **String** | Name of the vector column to search |  [optional] |
|**version** | **Long** | Table version to query |  [optional] |
|**withRowId** | **Boolean** | If true, return the row id as a column called &#x60;_rowid&#x60; |  [optional] |



