

# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**on** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  |  [optional] |
|**whenMatchedUpdateAll** | **Boolean** | Update all columns when rows match |  [optional] |
|**whenMatchedUpdateAllFilt** | **String** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true. Field references must use Lance field path syntax. |  [optional] |
|**whenNotMatchedInsertAll** | **Boolean** | Insert all columns when rows don&#39;t match |  [optional] |
|**whenNotMatchedBySourceDelete** | **Boolean** | Delete all rows from target table that don&#39;t match a row in the source table |  [optional] |
|**whenNotMatchedBySourceDeleteFilt** | **String** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true. Field references must use Lance field path syntax. |  [optional] |
|**timeout** | **String** | Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) |  [optional] |
|**useIndex** | **Boolean** | Whether to use index for matching rows |  [optional] |



