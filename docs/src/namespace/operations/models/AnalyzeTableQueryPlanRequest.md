

# AnalyzeTableQueryPlanRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**bypassVectorIndex** | **Boolean** | Whether to bypass vector index |  [optional] |
|**columns** | [**QueryTableRequestColumns**](QueryTableRequestColumns.md) |  |  [optional] |
|**distanceType** | **String** | Distance metric to use |  [optional] |
|**ef** | **Integer** | Search effort parameter for HNSW index |  [optional] |
|**fastSearch** | **Boolean** | Whether to use fast search |  [optional] |
|**filter** | **String** | Optional SQL filter expression. Field references in the expression must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled.  |  [optional] |
|**fullTextQuery** | [**QueryTableRequestFullTextQuery**](QueryTableRequestFullTextQuery.md) |  |  [optional] |
|**k** | **Integer** | Number of results to return |  |
|**lowerBound** | **Float** | Lower bound for search |  [optional] |
|**nprobes** | **Integer** | Number of probes for IVF index |  [optional] |
|**offset** | **Integer** | Number of results to skip |  [optional] |
|**prefilter** | **Boolean** | Whether to apply filtering before vector search |  [optional] |
|**refineFactor** | **Integer** | Refine factor for search |  [optional] |
|**upperBound** | **Float** | Upper bound for search |  [optional] |
|**vector** | [**QueryTableRequestVector**](QueryTableRequestVector.md) |  |  |
|**vectorColumn** | **String** | Lance field path of the vector field to search. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. |  [optional] |
|**version** | **Long** | Table version to query |  [optional] |
|**withRowId** | **Boolean** | If true, return the row id as a column called &#x60;_rowid&#x60; |  [optional] |



