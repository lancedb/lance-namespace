# UpdateFieldMetadataRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**updates** | [**List[UpdateFieldMetadataEntry]**](UpdateFieldMetadataEntry.md) | List of per-field metadata updates to apply | 

## Example

```python
from lance_namespace_urllib3_client.models.update_field_metadata_request import UpdateFieldMetadataRequest

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateFieldMetadataRequest from a JSON string
update_field_metadata_request_instance = UpdateFieldMetadataRequest.from_json(json)
# print the JSON string representation of the object
print(UpdateFieldMetadataRequest.to_json())

# convert the object into a dict
update_field_metadata_request_dict = update_field_metadata_request_instance.to_dict()
# create an instance of UpdateFieldMetadataRequest from a dict
update_field_metadata_request_from_dict = UpdateFieldMetadataRequest.from_dict(update_field_metadata_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


