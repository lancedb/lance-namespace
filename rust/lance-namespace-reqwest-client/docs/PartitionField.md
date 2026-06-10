# PartitionField

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**field_id** | **String** | Unique identifier for this partition field (must not be renamed) | 
**source_ids** | **Vec<i32>** | Field IDs of the source fields in the schema | 
**transform** | Option<[**models::PartitionTransform**](PartitionTransform.md)> | Well-known partition transform. Exactly one of transform or expression must be specified. | [optional]
**expression** | Option<**String**> | DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform or expression must be specified. | [optional]
**result_type** | [**models::JsonArrowDataType**](JsonArrowDataType.md) | The output type of the partition value (JsonArrowDataType format) | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


