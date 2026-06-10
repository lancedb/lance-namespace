

# UpdateFieldMetadataEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**path** | **String** | Lance field path whose metadata to update. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. |  |
|**metadata** | **Map&lt;String, String&gt;** | Metadata key-value pairs to apply to the field. A null value deletes that key.  |  |
|**replace** | **Boolean** | If true, replace the field&#39;s existing metadata entirely; otherwise merge into it (optional, defaults to false).  |  [optional] |



