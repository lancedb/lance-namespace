

# DescribeTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**table** | **String** | Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**namespace** | **List&lt;String&gt;** | The namespace identifier as a list of parts. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**version** | **Long** | Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**location** | **String** | Table storage location (e.g., S3/GCS path).  |  [optional] |
|**tableUri** | **String** | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when &#x60;with_table_uri&#x60; is true.  |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) | Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. When &#x60;vend_credentials&#x60; is true, this field may include vended credentials. If the vended credentials are temporary, the &#x60;expires_at_millis&#x60; key should be included to indicate the millisecond timestamp when the credentials expire.  |  [optional] |
|**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional table metadata as key-value pairs. This records the information of the table and requires loading the table. It is only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** | Properties stored on the table, if supported by the server. This records the information managed by the namespace. If the server does not support table properties, it should return null for this field. If table properties are supported, but none are set, it should return an empty object. |  [optional] |
|**managedVersioning** | **Boolean** | When true, the caller should use namespace table version operations (CreateTableVersion, BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to manage table versions instead of relying on Lance&#39;s native version management.  |  [optional] |
|**isOnlyDeclared** | **Boolean** | When true, indicates that the table has been declared in the namespace but not yet created on storage. This means the table exists in the namespace but has no data files on the underlying storage. When false, the table has storage components (data and metadata files). When null, the implementation did not check whether the table is only declared. Clients should treat an omitted value as null. Implementations should populate this field when &#x60;check_declared&#x60; is true or another option such as &#x60;load_detailed_metadata&#x60; requires checking declared-only table state. Operations like describe_table with load_detailed_metadata&#x3D;true may fail for declared-only tables.  |  [optional] |



