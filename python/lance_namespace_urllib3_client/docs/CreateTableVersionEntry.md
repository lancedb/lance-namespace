# CreateTableVersionEntry

An entry for creating a new table version in a batch operation. This supports `put_if_not_exists` semantics, where the operation fails if the version already exists. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **List[str]** | The table identifier | 
**version** | **int** | Version number to create | 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**manifest_path** | **str** | Path to the manifest file for this version | 
**manifest_size** | **int** | Size of the manifest file in bytes | [optional] 
**e_tag** | **str** | Optional ETag for the manifest file | [optional] 
**metadata** | **Dict[str, str]** | Optional metadata for the version | [optional] 
**naming_scheme** | **str** | The naming scheme used for manifest files in the &#x60;_versions/&#x60; directory.  Known values: - &#x60;V1&#x60;: &#x60;_versions/{version}.manifest&#x60; - Simple version-based naming - &#x60;V2&#x60;: &#x60;_versions/{inverted_version}.manifest&#x60; - Zero-padded, reversed version number   (uses &#x60;u64::MAX - version&#x60;) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_version_entry import CreateTableVersionEntry

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableVersionEntry from a JSON string
create_table_version_entry_instance = CreateTableVersionEntry.from_json(json)
# print the JSON string representation of the object
print(CreateTableVersionEntry.to_json())

# convert the object into a dict
create_table_version_entry_dict = create_table_version_entry_instance.to_dict()
# create an instance of CreateTableVersionEntry from a dict
create_table_version_entry_from_dict = CreateTableVersionEntry.from_dict(create_table_version_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


