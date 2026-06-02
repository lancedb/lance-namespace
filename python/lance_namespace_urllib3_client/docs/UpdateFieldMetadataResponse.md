# UpdateFieldMetadataResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | **int** | The commit version associated with the operation | 
**fields** | **Dict[str, Dict[str, str]]** | Resulting metadata for each updated field, keyed by field path.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.update_field_metadata_response import UpdateFieldMetadataResponse

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateFieldMetadataResponse from a JSON string
update_field_metadata_response_instance = UpdateFieldMetadataResponse.from_json(json)
# print the JSON string representation of the object
print(UpdateFieldMetadataResponse.to_json())

# convert the object into a dict
update_field_metadata_response_dict = update_field_metadata_response_instance.to_dict()
# create an instance of UpdateFieldMetadataResponse from a dict
update_field_metadata_response_from_dict = UpdateFieldMetadataResponse.from_dict(update_field_metadata_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


