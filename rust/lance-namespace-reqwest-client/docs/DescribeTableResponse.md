# DescribeTableResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**table** | Option<**String**> | Table name. Only populated when `load_detailed_metadata` is true.  | [optional]
**namespace** | Option<**Vec<String>**> | The namespace identifier as a list of parts. Only populated when `load_detailed_metadata` is true.  | [optional]
**version** | Option<**i64**> | Table version number. Only populated when `load_detailed_metadata` is true.  | [optional]
**location** | Option<**String**> | Table storage location (e.g., S3/GCS path).  | [optional]
**table_uri** | Option<**String**> | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when `with_table_uri` is true.  | [optional]
**schema** | Option<[**models::JsonArrowSchema**](JsonArrowSchema.md)> | Table schema in JSON Arrow format. Only populated when `load_detailed_metadata` is true.  | [optional]
**storage_options** | Option<**std::collections::HashMap<String, String>**> | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. When `vend_credentials` is true, this field may include vended credentials. If the vended credentials are temporary, the `expires_at_millis` key should be included to indicate the millisecond timestamp when the credentials expire.  | [optional]
**stats** | Option<[**models::TableBasicStats**](TableBasicStats.md)> | Table statistics. Only populated when `load_detailed_metadata` is true.  | [optional]
**metadata** | Option<**std::collections::HashMap<String, String>**> | Optional table metadata as key-value pairs. This records the information of the table and requires loading the table. It is only populated when `load_detailed_metadata` is true.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> | Properties stored on the table, if supported by the server. This records the information managed by the namespace. If the server does not support table properties, it should return null for this field. If table properties are supported, but none are set, it should return an empty object. | [optional][default to {}]
**managed_versioning** | Option<**bool**> | When true, the caller should use namespace table version operations (CreateTableVersion, BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to manage table versions instead of relying on Lance's native version management.  | [optional]
**is_only_declared** | Option<**bool**> | When true, indicates that the table has been declared in the namespace but not yet created on storage. This means the table exists in the namespace but has no data files on the underlying storage. When false, the table has storage components (data and metadata files). When null, the implementation did not check whether the table is only declared. Clients should treat an omitted value as null. Implementations should populate this field when `check_declared` is true or another option such as `load_detailed_metadata` requires checking declared-only table state. Operations like describe_table with load_detailed_metadata=true may fail for declared-only tables.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


