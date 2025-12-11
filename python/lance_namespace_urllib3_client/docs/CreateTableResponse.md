# CreateTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**location** | **str** |  | [optional] 
**version** | **int** |  | [optional] 
**storage_options** | **Dict[str, str]** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_response import CreateTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableResponse from a JSON string
create_table_response_instance = CreateTableResponse.from_json(json)
# print the JSON string representation of the object
print(CreateTableResponse.to_json())

# convert the object into a dict
create_table_response_dict = create_table_response_instance.to_dict()
# create an instance of CreateTableResponse from a dict
create_table_response_from_dict = CreateTableResponse.from_dict(create_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


