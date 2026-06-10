# MergeInsertIntoTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**on** | Option<**String**> | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional]
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match | [optional][default to false]
**when_matched_update_all_filt** | Option<**String**> | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true. Field references must use Lance field path syntax. | [optional]
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match | [optional][default to false]
**when_not_matched_by_source_delete** | Option<**bool**> | Delete all rows from target table that don't match a row in the source table | [optional][default to false]
**when_not_matched_by_source_delete_filt** | Option<**String**> | Delete rows from the target table if there is no match AND the SQL expression evaluates to true. Field references must use Lance field path syntax. | [optional]
**timeout** | Option<**String**> | Timeout for the operation (e.g., \"30s\", \"5m\") | [optional]
**use_index** | Option<**bool**> | Whether to use index for matching rows | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


