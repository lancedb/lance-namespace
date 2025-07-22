# CreateTableRequest

Request for creating a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `location`: pass through header `x-lance-table-location` - `properties`: pass through header `x-lance-table-properties` 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | [optional] 
**namespace** | **List[str]** |  | [optional] 
**location** | **str** |  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_request import CreateTableRequest

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


