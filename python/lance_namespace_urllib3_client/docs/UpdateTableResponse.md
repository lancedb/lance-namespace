# UpdateTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**updated_rows** | **int** | Number of rows updated | 
**version** | **int** | The commit version associated with the operation | 

## Example

```python
from lance_namespace_urllib3_client.models.update_table_response import UpdateTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableResponse from a JSON string
update_table_response_instance = UpdateTableResponse.from_json(json)
# print the JSON string representation of the object
print(UpdateTableResponse.to_json())

# convert the object into a dict
update_table_response_dict = update_table_response_instance.to_dict()
# create an instance of UpdateTableResponse from a dict
update_table_response_from_dict = UpdateTableResponse.from_dict(update_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


