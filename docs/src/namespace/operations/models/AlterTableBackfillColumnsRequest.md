

# AlterTableBackfillColumnsRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**column** | **String** | Lance field path to backfill. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. |  |
|**where** | **String** | Optional WHERE clause filter |  [optional] |
|**concurrency** | **Integer** | Optional concurrency override |  [optional] |
|**intraApplierConcurrency** | **Integer** | Optional intra-applier concurrency override |  [optional] |
|**minCheckpointSize** | **Integer** | Optional minimum checkpoint size |  [optional] |
|**maxCheckpointSize** | **Integer** | Optional maximum checkpoint size |  [optional] |
|**batchCheckpointFlushIntervalSeconds** | **BigDecimal** | Optional batch checkpoint flush interval in seconds |  [optional] |
|**readVersion** | **Integer** | Optional table version to read from |  [optional] |
|**taskSize** | **Integer** | Optional task size |  [optional] |
|**numFrags** | **Integer** | Optional number of fragments |  [optional] |
|**checkpointSize** | **Integer** | Optional checkpoint size |  [optional] |
|**commitGranularity** | **Integer** | Optional commit granularity |  [optional] |
|**cluster** | **String** | Optional cluster name |  [optional] |
|**manifest** | **String** | Optional manifest name |  [optional] |



