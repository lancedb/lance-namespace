# CreateTableIndex400Response


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | Brief error message | 
**code** | **int** | HTTP status code | 
**type** | **str** | Error type identifier | 
**detail** | **str** | Detailed error explanation | [optional] 
**instance** | **str** | Specific occurrence identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_index400_response import CreateTableIndex400Response

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableIndex400Response from a JSON string
create_table_index400_response_instance = CreateTableIndex400Response.from_json(json)
# print the JSON string representation of the object
print(CreateTableIndex400Response.to_json())

# convert the object into a dict
create_table_index400_response_dict = create_table_index400_response_instance.to_dict()
# create an instance of CreateTableIndex400Response from a dict
create_table_index400_response_from_dict = CreateTableIndex400Response.from_dict(create_table_index400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


