

# RefreshMaterializedViewRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**srcVersion** | **Integer** | Optional source version to refresh from |  [optional] |
|**maxRowsPerFragment** | **Integer** | Optional maximum rows per fragment |  [optional] |
|**concurrency** | **Integer** | Optional concurrency override |  [optional] |
|**intraApplierConcurrency** | **Integer** | Optional intra-applier concurrency override |  [optional] |
|**sourceTaskSize** | **Integer** | Optional number of source row ids per work item during expansion. Bounds per-actor memory for chunker materialized views.  |  [optional] |
|**cluster** | **String** | Optional cluster name (operational override) |  [optional] |
|**outputLimit** | **Integer** | Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds.  |  [optional] |
|**manifest** | **String** | Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view&#39;s snapshotted manifest. When omitted, the manifest stored in the view&#39;s metadata is used.  |  [optional] |



