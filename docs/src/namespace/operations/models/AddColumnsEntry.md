

# AddColumnsEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Name of the new column |  |
|**expression** | **String** | SQL expression for the column (optional if virtual_column or computed is specified). Evaluated once over existing rows; nothing is stored, so rows appended later read null. |  [optional] |
|**computed** | **String** | SQL expression declaring a maintained computed column (optional if expression or virtual_column is specified). The column is added all-null with the expression persisted as its binding in field metadata; its type and input columns are inferred from the expression. Rows are filled by backfill, never at declaration. |  [optional] |
|**virtualColumn** | [**AddVirtualColumnEntry**](AddVirtualColumnEntry.md) |  |  [optional] |



