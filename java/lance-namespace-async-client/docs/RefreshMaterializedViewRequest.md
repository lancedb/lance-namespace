

# RefreshMaterializedViewRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**srcVersion** | **Integer** | Optional source version to refresh from |  [optional] |
|**maxRowsPerFragment** | **Integer** | Optional maximum rows per fragment |  [optional] |
|**concurrency** | **Integer** | Optional concurrency override |  [optional] |
|**intraApplierConcurrency** | **Integer** | Optional intra-applier concurrency override |  [optional] |
|**sourceTaskSize** | **Integer** | Optional number of source row ids per work item during expansion. Bounds per-actor memory for chunker materialized views.  |  [optional] |
|**cluster** | **String** | Optional cluster name (operational override) |  [optional] |
|**outputLimit** | **Integer** | Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds.  |  [optional] |
|**manifest** | **String** | Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view&#39;s snapshotted manifest. When omitted, the manifest stored in the view&#39;s metadata is used.  |  [optional] |



