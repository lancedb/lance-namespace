# PartitionField

Partition field definition

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**field_id** | **str** | Unique identifier for this partition field (must not be renamed) | 
**source_ids** | **List[int]** | Field IDs of the source fields in the schema | 
**transform** | [**PartitionTransform**](PartitionTransform.md) | Well-known partition transform. Exactly one of transform or expression must be specified. | [optional] 
**expression** | **str** | DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform or expression must be specified. | [optional] 
**result_type** | [**JsonArrowDataType**](JsonArrowDataType.md) | The output type of the partition value (JsonArrowDataType format) | 

## Example

```python
from lance_namespace_urllib3_client.models.partition_field import PartitionField

# TODO update the JSON string below
json = "{}"
# create an instance of PartitionField from a JSON string
partition_field_instance = PartitionField.from_json(json)
# print the JSON string representation of the object
print(PartitionField.to_json())

# convert the object into a dict
partition_field_dict = partition_field_instance.to_dict()
# create an instance of PartitionField from a dict
partition_field_from_dict = PartitionField.from_dict(partition_field_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


