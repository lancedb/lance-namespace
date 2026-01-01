# CreateTableTagRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**tag** | **str** | Name of the tag to create | 
**version** | **int** | Version number for the tag to point to | 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_tag_request import CreateTableTagRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableTagRequest from a JSON string
create_table_tag_request_instance = CreateTableTagRequest.from_json(json)
# print the JSON string representation of the object
print(CreateTableTagRequest.to_json())

# convert the object into a dict
create_table_tag_request_dict = create_table_tag_request_instance.to_dict()
# create an instance of CreateTableTagRequest from a dict
create_table_tag_request_from_dict = CreateTableTagRequest.from_dict(create_table_tag_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


