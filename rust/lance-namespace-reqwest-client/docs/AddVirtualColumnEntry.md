# AddVirtualColumnEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | **Vec<String>** | List of input column names for the virtual column | 
**outputs** | [**Vec<models::AddVirtualColumnOutputEntry>**](AddVirtualColumnOutputEntry.md) | Output columns produced by the virtual column UDF | 
**image** | **String** | Docker image to use for the UDF | 
**udf** | **String** | Base64 encoded pickled UDF | 
**udf_name** | **String** | Name of the UDF | 
**udf_version** | **String** | Version of the UDF | 
**udf_backend** | Option<**String**> | UDF backend type (e.g. DockerUDFSpecV1) | [optional]
**auto_backfill** | Option<**bool**> | Whether to automatically backfill the column after creation | [optional]
**manifest** | Option<**String**> | JSON-serialized manifest for the UDF environment | [optional]
**manifest_checksum** | Option<**String**> | SHA-256 checksum of the manifest content | [optional]
**field_metadata** | Option<**std::collections::HashMap<String, String>**> | User-supplied field metadata (string key-value pairs) | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


