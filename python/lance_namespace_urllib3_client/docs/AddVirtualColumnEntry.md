# AddVirtualColumnEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | **List[str]** | List of input column names for the virtual column | 
**data_type** | **object** | Data type of the virtual column using JSON representation | 
**image** | **str** | Docker image to use for the UDF | 
**udf** | **str** | Base64 encoded pickled UDF | 
**udf_name** | **str** | Name of the UDF | 
**udf_version** | **str** | Version of the UDF | 

## Example

```python
from lance_namespace_urllib3_client.models.add_virtual_column_entry import AddVirtualColumnEntry

# TODO update the JSON string below
json = "{}"
# create an instance of AddVirtualColumnEntry from a JSON string
add_virtual_column_entry_instance = AddVirtualColumnEntry.from_json(json)
# print the JSON string representation of the object
print(AddVirtualColumnEntry.to_json())

# convert the object into a dict
add_virtual_column_entry_dict = add_virtual_column_entry_instance.to_dict()
# create an instance of AddVirtualColumnEntry from a dict
add_virtual_column_entry_from_dict = AddVirtualColumnEntry.from_dict(add_virtual_column_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


