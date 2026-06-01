# AddVirtualColumnEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**input_columns** | **List[str]** | List of input column names for the virtual column | 
**outputs** | [**List[AddVirtualColumnOutputEntry]**](AddVirtualColumnOutputEntry.md) | Output columns produced by the virtual column UDF | 
**image** | **str** | Docker image to use for the UDF | 
**udf** | **str** | Base64 encoded pickled UDF | 
**udf_name** | **str** | Name of the UDF | 
**udf_version** | **str** | Version of the UDF | 
**udf_backend** | **str** | UDF backend type (e.g. DockerUDFSpecV1) | [optional] 
**auto_backfill** | **bool** | Whether to automatically backfill the column after creation | [optional] 
**manifest** | **str** | JSON-serialized manifest for the UDF environment | [optional] 
**manifest_checksum** | **str** | SHA-256 checksum of the manifest content | [optional] 
**field_metadata** | **Dict[str, str]** | User-supplied field metadata (string key-value pairs) | [optional] 

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


