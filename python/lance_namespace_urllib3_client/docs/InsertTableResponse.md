# InsertTableResponse

Response from inserting records into a table

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | **int** | The version of the table after the insert | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.insert_table_response import InsertTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of InsertTableResponse from a JSON string
insert_table_response_instance = InsertTableResponse.from_json(json)
# print the JSON string representation of the object
print(InsertTableResponse.to_json())

# convert the object into a dict
insert_table_response_dict = insert_table_response_instance.to_dict()
# create an instance of InsertTableResponse from a dict
insert_table_response_from_dict = InsertTableResponse.from_dict(insert_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


