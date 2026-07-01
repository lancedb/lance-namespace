

# CreateMaterializedViewRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** | View identifier path (namespace + view name) |  [optional] |
|**kind** | [**KindEnum**](#KindEnum) | The materialized view kind. - &#x60;query&#x60; — plain query-backed view (no UDTF), 1:1 rows. - &#x60;udtf&#x60; — batch UDTF-backed view (N:M rows, full refresh). - &#x60;chunker&#x60;, aka &#39;scalar_udtf&#39; — chunker view (1:N row expansion, incremental refresh).  |  |
|**sourceQuery** | **String** | Opaque serialized representation of the source query that defines the view&#39;s input. The format is defined by the client; the namespace server stores it without interpreting it.  |  |
|**outputSchema** | **String** | Base64-encoded Arrow schema of the view output |  |
|**udtfSpec** | [**MaterializedViewUdtfEntry**](MaterializedViewUdtfEntry.md) |  |  [optional] |
|**withNoData** | **Boolean** | If false, the server kicks off an initial refresh immediately after creating the view and the response includes a job ID.  |  [optional] |
|**autoRefresh** | **Boolean** | If true, the view is automatically refreshed when source-table data changes past the deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on the deployment, not per-view.  |  [optional] |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| QUERY | &quot;query&quot; |
| UDTF | &quot;udtf&quot; |
| CHUNKER | &quot;chunker&quot; |



