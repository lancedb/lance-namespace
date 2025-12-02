# UpdateTableTag404Response


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
from lance_namespace_urllib3_client.models.update_table_tag404_response import UpdateTableTag404Response

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableTag404Response from a JSON string
update_table_tag404_response_instance = UpdateTableTag404Response.from_json(json)
# print the JSON string representation of the object
print(UpdateTableTag404Response.to_json())

# convert the object into a dict
update_table_tag404_response_dict = update_table_tag404_response_instance.to_dict()
# create an instance of UpdateTableTag404Response from a dict
update_table_tag404_response_from_dict = UpdateTableTag404Response.from_dict(update_table_tag404_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


