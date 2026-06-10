# AlterVirtualColumnEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | Option<**Vec<String>**> | List of input Lance field paths for the virtual column. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Optional. | [optional]
**image** | Option<**String**> | Docker image to use for the UDF (optional) | [optional]
**udf** | Option<**String**> | Base64 encoded pickled UDF (optional) | [optional]
**udf_name** | Option<**String**> | Name of the UDF (optional) | [optional]
**udf_version** | Option<**String**> | Version of the UDF (optional) | [optional]
**udf_backend** | Option<**String**> | UDF backend type (e.g. DockerUDFSpecV1) (optional) | [optional]
**auto_backfill** | Option<**bool**> | Whether to automatically backfill the column (optional) | [optional]
**manifest** | Option<**String**> | JSON-serialized manifest for the UDF environment (optional) | [optional]
**manifest_checksum** | Option<**String**> | SHA-256 checksum of the manifest content (optional) | [optional]
**field_metadata** | Option<**std::collections::HashMap<String, String>**> | User-supplied field metadata (optional) | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


