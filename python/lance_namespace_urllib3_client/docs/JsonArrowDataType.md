# JsonArrowDataType

JSON representation of an Apache Arrow DataType

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fields** | [**List[JsonArrowField]**](JsonArrowField.md) | Fields for complex types like Struct, Union, etc. | [optional] 
**length** | **int** | Length for fixed-size types | [optional] 
**type** | **str** | The data type name | 

## Example

```python
from lance_namespace_urllib3_client.models.json_arrow_data_type import JsonArrowDataType

# TODO update the JSON string below
json = "{}"
# create an instance of JsonArrowDataType from a JSON string
json_arrow_data_type_instance = JsonArrowDataType.from_json(json)
# print the JSON string representation of the object
print(JsonArrowDataType.to_json())

# convert the object into a dict
json_arrow_data_type_dict = json_arrow_data_type_instance.to_dict()
# create an instance of JsonArrowDataType from a dict
json_arrow_data_type_from_dict = JsonArrowDataType.from_dict(json_arrow_data_type_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


