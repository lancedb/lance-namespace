

# AlterTableBackfillColumnsRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**column** | **String** | Column name to backfill |  |
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



