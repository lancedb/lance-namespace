# JsonField

JSON representation of an Apache Arrow [Field].

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**metadata** | **Dict[str, str]** |  | [optional] 
**name** | **str** |  | 
**nullable** | **bool** |  | 
**type** | [**JsonDataType**](JsonDataType.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.json_field import JsonField

# TODO update the JSON string below
json = "{}"
# create an instance of JsonField from a JSON string
json_field_instance = JsonField.from_json(json)
# print the JSON string representation of the object
print(JsonField.to_json())

# convert the object into a dict
json_field_dict = json_field_instance.to_dict()
# create an instance of JsonField from a dict
json_field_from_dict = JsonField.from_dict(json_field_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


