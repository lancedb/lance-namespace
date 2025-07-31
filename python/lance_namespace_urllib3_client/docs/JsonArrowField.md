# JsonArrowField

JSON representation of an Apache Arrow field. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**metadata** | **Dict[str, str]** |  | [optional] 
**name** | **str** |  | 
**nullable** | **bool** |  | 
**type** | [**JsonArrowDataType**](JsonArrowDataType.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.json_arrow_field import JsonArrowField

# TODO update the JSON string below
json = "{}"
# create an instance of JsonArrowField from a JSON string
json_arrow_field_instance = JsonArrowField.from_json(json)
# print the JSON string representation of the object
print(JsonArrowField.to_json())

# convert the object into a dict
json_arrow_field_dict = json_arrow_field_instance.to_dict()
# create an instance of JsonArrowField from a dict
json_arrow_field_from_dict = JsonArrowField.from_dict(json_arrow_field_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


