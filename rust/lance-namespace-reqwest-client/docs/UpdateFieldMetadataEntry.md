# UpdateFieldMetadataEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with `.`. A `.` that is not inside backticks is always a path separator, so a field name that contains a literal `.` must be written as a backtick-quoted segment, for example `parent.`child.with.dot``. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or `_` quoted with backticks, for example `metadata.status`, `MetaData.userId`, and `meta-data`.`user-id`. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | 
**metadata** | **std::collections::HashMap<String, String>** | Metadata key-value pairs to apply to the field. A null value deletes that key.  | 
**replace** | Option<**bool**> | If true, replace the field's existing metadata entirely; otherwise merge into it (optional, defaults to false).  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


