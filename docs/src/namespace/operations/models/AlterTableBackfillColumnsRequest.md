

# AlterTableBackfillColumnsRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
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



