

# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `on`: pass through query parameter of the same name - `when_matched_update_all`: pass through query parameter of the same name - `when_matched_update_all_filt`: pass through query parameter of the same name - `when_not_matched_insert_all`: pass through query parameter of the same name - `when_not_matched_by_source_delete`: pass through query parameter of the same name - `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | The table name |  [optional] |
|**namespace** | **List&lt;String&gt;** | The namespace identifier |  [optional] |
|**on** | **String** | Column name to use for matching rows (required) |  [optional] |
|**whenMatchedUpdateAll** | **Boolean** | Update all columns when rows match |  [optional] |
|**whenMatchedUpdateAllFilt** | **String** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true |  [optional] |
|**whenNotMatchedInsertAll** | **Boolean** | Insert all columns when rows don&#39;t match |  [optional] |
|**whenNotMatchedBySourceDelete** | **Boolean** | Delete all rows from target table that don&#39;t match a row in the source table |  [optional] |
|**whenNotMatchedBySourceDeleteFilt** | **String** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true |  [optional] |



