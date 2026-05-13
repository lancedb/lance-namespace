

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
|**cluster** | **String** | Optional cluster name |  [optional] |
|**manifest** | **String** | Optional manifest name |  [optional] |



