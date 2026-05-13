

# PartitionField

Partition field definition

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**fieldId** | **String** | Unique identifier for this partition field (must not be renamed) |  |
|**sourceIds** | **List&lt;Integer&gt;** | Field IDs of the source columns in the schema |  |
|**transform** | [**PartitionTransform**](PartitionTransform.md) | Well-known partition transform. Exactly one of transform or expression must be specified. |  [optional] |
|**expression** | **String** | DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform or expression must be specified. |  [optional] |
|**resultType** | [**JsonArrowDataType**](JsonArrowDataType.md) | The output type of the partition value (JsonArrowDataType format) |  |



