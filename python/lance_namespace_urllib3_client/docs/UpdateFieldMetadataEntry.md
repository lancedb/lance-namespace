# UpdateFieldMetadataEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **str** | Field (column) path whose metadata to update | 
**metadata** | **Dict[str, Optional[str]]** | Metadata key-value pairs to apply to the field. A null value deletes that key.  | 
**replace** | **bool** | If true, replace the field&#39;s existing metadata entirely; otherwise merge into it (optional, defaults to false).  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.update_field_metadata_entry import UpdateFieldMetadataEntry

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateFieldMetadataEntry from a JSON string
update_field_metadata_entry_instance = UpdateFieldMetadataEntry.from_json(json)
# print the JSON string representation of the object
print(UpdateFieldMetadataEntry.to_json())

# convert the object into a dict
update_field_metadata_entry_dict = update_field_metadata_entry_instance.to_dict()
# create an instance of UpdateFieldMetadataEntry from a dict
update_field_metadata_entry_from_dict = UpdateFieldMetadataEntry.from_dict(update_field_metadata_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


