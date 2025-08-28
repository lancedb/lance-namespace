# CreateEmptyTableResponse

Response for creating an empty table. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**location** | **str** |  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 
**storage_options** | **Dict[str, str]** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_empty_table_response import CreateEmptyTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateEmptyTableResponse from a JSON string
create_empty_table_response_instance = CreateEmptyTableResponse.from_json(json)
# print the JSON string representation of the object
print(CreateEmptyTableResponse.to_json())

# convert the object into a dict
create_empty_table_response_dict = create_empty_table_response_instance.to_dict()
# create an instance of CreateEmptyTableResponse from a dict
create_empty_table_response_from_dict = CreateEmptyTableResponse.from_dict(create_empty_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


