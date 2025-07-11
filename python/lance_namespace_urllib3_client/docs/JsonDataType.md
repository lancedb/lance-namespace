# JsonDataType

JSON representation of an Apache Arrow [DataType].

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fields** | **object** |  | [optional] 
**length** | **int** |  | [optional] 
**type** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.json_data_type import JsonDataType

# TODO update the JSON string below
json = "{}"
# create an instance of JsonDataType from a JSON string
json_data_type_instance = JsonDataType.from_json(json)
# print the JSON string representation of the object
print(JsonDataType.to_json())

# convert the object into a dict
json_data_type_dict = json_data_type_instance.to_dict()
# create an instance of JsonDataType from a dict
json_data_type_from_dict = JsonDataType.from_dict(json_data_type_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


