# UpdateFieldMetadataEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | 
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


