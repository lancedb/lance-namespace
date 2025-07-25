# JsonArrowSchema

JSON representation of a Apache Arrow schema. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fields** | [**List[JsonArrowField]**](JsonArrowField.md) |  | 
**metadata** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.json_arrow_schema import JsonArrowSchema

# TODO update the JSON string below
json = "{}"
# create an instance of JsonArrowSchema from a JSON string
json_arrow_schema_instance = JsonArrowSchema.from_json(json)
# print the JSON string representation of the object
print(JsonArrowSchema.to_json())

# convert the object into a dict
json_arrow_schema_dict = json_arrow_schema_instance.to_dict()
# create an instance of JsonArrowSchema from a dict
json_arrow_schema_from_dict = JsonArrowSchema.from_dict(json_arrow_schema_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


