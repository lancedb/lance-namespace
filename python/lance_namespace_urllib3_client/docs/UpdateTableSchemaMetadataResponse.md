# UpdateTableSchemaMetadataResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**metadata** | **Dict[str, str]** | The updated schema metadata | [optional] 
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.update_table_schema_metadata_response import UpdateTableSchemaMetadataResponse

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableSchemaMetadataResponse from a JSON string
update_table_schema_metadata_response_instance = UpdateTableSchemaMetadataResponse.from_json(json)
# print the JSON string representation of the object
print(UpdateTableSchemaMetadataResponse.to_json())

# convert the object into a dict
update_table_schema_metadata_response_dict = update_table_schema_metadata_response_instance.to_dict()
# create an instance of UpdateTableSchemaMetadataResponse from a dict
update_table_schema_metadata_response_from_dict = UpdateTableSchemaMetadataResponse.from_dict(update_table_schema_metadata_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


