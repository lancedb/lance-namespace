# AlterVirtualColumnEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | **List[str]** | List of input column names for the virtual column (optional) | [optional] 
**image** | **str** | Docker image to use for the UDF (optional) | [optional] 
**udf** | **str** | Base64 encoded pickled UDF (optional) | [optional] 
**udf_name** | **str** | Name of the UDF (optional) | [optional] 
**udf_version** | **str** | Version of the UDF (optional) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.alter_virtual_column_entry import AlterVirtualColumnEntry

# TODO update the JSON string below
json = "{}"
# create an instance of AlterVirtualColumnEntry from a JSON string
alter_virtual_column_entry_instance = AlterVirtualColumnEntry.from_json(json)
# print the JSON string representation of the object
print(AlterVirtualColumnEntry.to_json())

# convert the object into a dict
alter_virtual_column_entry_dict = alter_virtual_column_entry_instance.to_dict()
# create an instance of AlterVirtualColumnEntry from a dict
alter_virtual_column_entry_from_dict = AlterVirtualColumnEntry.from_dict(alter_virtual_column_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


