# ModelField


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **str** |  | 
**name** | **str** |  | 
**id** | [**Int**](Int.md) |  | 
**parent_id** | [**Int**](Int.md) |  | [optional] 
**logical_type** | **str** | Logical types, currently support parameterized Arrow Type. PARENT types will always have logical type \&quot;struct\&quot;. Logical type \&quot;map\&quot; is represented as a struct with a single child field \&quot;entries\&quot;. \&quot;entries\&quot; is a struct with fields \&quot;key\&quot; and \&quot;value\&quot;. REPEATED types may have logical types: * \&quot;list\&quot; * \&quot;large_list\&quot; * \&quot;list.struct\&quot; * \&quot;large_list.struct\&quot; The final two are used if the list values are structs, and therefore the field is both implicitly REPEATED and PARENT. LEAF types may have logical types: * \&quot;null\&quot; * \&quot;bool\&quot; * \&quot;int8\&quot; / \&quot;uint8\&quot; * \&quot;int16\&quot; / \&quot;uint16\&quot; * \&quot;int32\&quot; / \&quot;uint32\&quot; * \&quot;int64\&quot; / \&quot;uint64\&quot; * \&quot;halffloat\&quot; / \&quot;float\&quot; / \&quot;double\&quot; * \&quot;string\&quot; / \&quot;large_string\&quot; * \&quot;binary\&quot; / \&quot;large_binary\&quot; * \&quot;date32:day\&quot; * \&quot;date64:ms\&quot; * \&quot;decimal:128:{precision}:{scale}\&quot; / \&quot;decimal:256:{precision}:{scale}\&quot; * \&quot;time:{unit}\&quot; / \&quot;timestamp:{unit}\&quot; / \&quot;duration:{unit}\&quot;, where unit is \&quot;s\&quot;, \&quot;ms\&quot;, \&quot;us\&quot;, \&quot;ns\&quot; * \&quot;dict:{value_type}:{index_type}:false\&quot;  | 
**nullable** | **bool** |  | [optional] 
**metadata** | **Dict[str, str]** | optional field metadata (e.g. extension type name/parameters) | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.model_field import ModelField

# TODO update the JSON string below
json = "{}"
# create an instance of ModelField from a JSON string
model_field_instance = ModelField.from_json(json)
# print the JSON string representation of the object
print(ModelField.to_json())

# convert the object into a dict
model_field_dict = model_field_instance.to_dict()
# create an instance of ModelField from a dict
model_field_from_dict = ModelField.from_dict(model_field_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


