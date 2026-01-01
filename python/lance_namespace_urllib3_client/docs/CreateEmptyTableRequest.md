# CreateEmptyTableRequest

Request for creating an empty table.  **Deprecated**: Use `DeclareTableRequest` instead. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** | Optional storage location for the table. If not provided, the namespace implementation should determine the table location.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_empty_table_request import CreateEmptyTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateEmptyTableRequest from a JSON string
create_empty_table_request_instance = CreateEmptyTableRequest.from_json(json)
# print the JSON string representation of the object
print(CreateEmptyTableRequest.to_json())

# convert the object into a dict
create_empty_table_request_dict = create_empty_table_request_instance.to_dict()
# create an instance of CreateEmptyTableRequest from a dict
create_empty_table_request_from_dict = CreateEmptyTableRequest.from_dict(create_empty_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


