# CreateTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**mode** | **str** |  | [optional] [default to 'CREATE']
**location** | **str** |  | [optional] 
**var_schema** | [**ModelSchema**](ModelSchema.md) |  | 
**writer_version** | [**WriterVersion**](WriterVersion.md) |  | [optional] 
**config** | **Dict[str, str]** | optional configurations for the table. Keys with the prefix \&quot;lance.\&quot; are reserved for the Lance library.  Other libraries may wish to similarly prefix their configuration keys appropriately.  | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.create_table_request import CreateTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableRequest from a JSON string
create_table_request_instance = CreateTableRequest.from_json(json)
# print the JSON string representation of the object
print(CreateTableRequest.to_json())

# convert the object into a dict
create_table_request_dict = create_table_request_instance.to_dict()
# create an instance of CreateTableRequest from a dict
create_table_request_from_dict = CreateTableRequest.from_dict(create_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


