

# AlterTableBackfillColumnsRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**column** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  |  |
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



