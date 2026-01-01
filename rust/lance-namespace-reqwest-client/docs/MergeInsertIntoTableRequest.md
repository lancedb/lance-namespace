# MergeInsertIntoTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**on** | Option<**String**> | Column name to use for matching rows (required) | [optional]
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match | [optional][default to false]
**when_matched_update_all_filt** | Option<**String**> | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true | [optional]
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match | [optional][default to false]
**when_not_matched_by_source_delete** | Option<**bool**> | Delete all rows from target table that don't match a row in the source table | [optional][default to false]
**when_not_matched_by_source_delete_filt** | Option<**String**> | Delete rows from the target table if there is no match AND the SQL expression evaluates to true | [optional]
**timeout** | Option<**String**> | Timeout for the operation (e.g., \"30s\", \"5m\") | [optional]
**use_index** | Option<**bool**> | Whether to use index for matching rows | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


