

# CreateTableIndexRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**column** | **String** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  |  |
|**indexType** | **String** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) |  |
|**name** | **String** | Optional name for the index. If not provided, a name will be auto-generated. |  [optional] |
|**distanceType** | **String** | Distance metric type for vector indexes (e.g., l2, cosine, dot) |  [optional] |
|**withPosition** | **Boolean** | Optional FTS parameter for position tracking |  [optional] |
|**baseTokenizer** | **String** | Optional FTS parameter for base tokenizer |  [optional] |
|**language** | **String** | Optional FTS parameter for language |  [optional] |
|**maxTokenLength** | **Integer** | Optional FTS parameter for maximum token length |  [optional] |
|**lowerCase** | **Boolean** | Optional FTS parameter for lowercase conversion |  [optional] |
|**stem** | **Boolean** | Optional FTS parameter for stemming |  [optional] |
|**removeStopWords** | **Boolean** | Optional FTS parameter for stop word removal |  [optional] |
|**asciiFolding** | **Boolean** | Optional FTS parameter for ASCII folding |  [optional] |



