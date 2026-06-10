# AlterColumnsEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **str** | Lance field path to alter. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**data_type** | **object** | New data type for the column using JSON representation (optional) | [optional] 
**rename** | **str** | New name for the column (optional) | [optional] 
**nullable** | **bool** | Whether the column should be nullable (optional) | [optional] 
**virtual_column** | [**AlterVirtualColumnEntry**](AlterVirtualColumnEntry.md) |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.alter_columns_entry import AlterColumnsEntry

# TODO update the JSON string below
json = "{}"
# create an instance of AlterColumnsEntry from a JSON string
alter_columns_entry_instance = AlterColumnsEntry.from_json(json)
# print the JSON string representation of the object
print(AlterColumnsEntry.to_json())

# convert the object into a dict
alter_columns_entry_dict = alter_columns_entry_instance.to_dict()
# create an instance of AlterColumnsEntry from a dict
alter_columns_entry_from_dict = AlterColumnsEntry.from_dict(alter_columns_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


