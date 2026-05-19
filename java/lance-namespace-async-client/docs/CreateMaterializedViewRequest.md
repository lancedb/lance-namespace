

# CreateMaterializedViewRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
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



