# DeleteFromTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**version** | **int** | The commit version associated with the operation | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.delete_from_table_response import DeleteFromTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteFromTableResponse from a JSON string
delete_from_table_response_instance = DeleteFromTableResponse.from_json(json)
# print the JSON string representation of the object
print(DeleteFromTableResponse.to_json())

# convert the object into a dict
delete_from_table_response_dict = delete_from_table_response_instance.to_dict()
# create an instance of DeleteFromTableResponse from a dict
delete_from_table_response_from_dict = DeleteFromTableResponse.from_dict(delete_from_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


