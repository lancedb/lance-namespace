# AddVirtualColumnEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | **Vec<String>** | List of input column names for the virtual column | 
**data_type** | [**serde_json::Value**](.md) | Data type of the virtual column using JSON representation | 
**image** | **String** | Docker image to use for the UDF | 
**udf** | **String** | Base64 encoded pickled UDF | 
**udf_name** | **String** | Name of the UDF | 
**udf_version** | **String** | Version of the UDF | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


