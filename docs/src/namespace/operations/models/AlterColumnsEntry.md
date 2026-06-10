

# AlterColumnsEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**path** | **String** | Lance field path to alter. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. |  |
|**dataType** | **Object** | New data type for the column using JSON representation (optional) |  [optional] |
|**rename** | **String** | New name for the column (optional) |  [optional] |
|**nullable** | **Boolean** | Whether the column should be nullable (optional) |  [optional] |
|**virtualColumn** | [**AlterVirtualColumnEntry**](AlterVirtualColumnEntry.md) |  |  [optional] |



