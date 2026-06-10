# AddVirtualColumnOutputEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **str** | Physical output column name | 
**struct_field** | **str** | Field name in the UDF output struct | 
**data_type** | **object** | Data type of the output column using JSON representation | 
**nullable** | **bool** | Whether the output column is nullable | 
**metadata** | **Dict[str, str]** | User-supplied output field metadata (string key-value pairs) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.add_virtual_column_output_entry import AddVirtualColumnOutputEntry

# TODO update the JSON string below
json = "{}"
# create an instance of AddVirtualColumnOutputEntry from a JSON string
add_virtual_column_output_entry_instance = AddVirtualColumnOutputEntry.from_json(json)
# print the JSON string representation of the object
print(AddVirtualColumnOutputEntry.to_json())

# convert the object into a dict
add_virtual_column_output_entry_dict = add_virtual_column_output_entry_instance.to_dict()
# create an instance of AddVirtualColumnOutputEntry from a dict
add_virtual_column_output_entry_from_dict = AddVirtualColumnOutputEntry.from_dict(add_virtual_column_output_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


