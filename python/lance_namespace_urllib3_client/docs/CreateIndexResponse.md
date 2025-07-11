# CreateIndexResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**location** | **str** | Table location (usually empty) | 
**properties** | **Dict[str, str]** | Additional properties (usually empty) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_index_response import CreateIndexResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateIndexResponse from a JSON string
create_index_response_instance = CreateIndexResponse.from_json(json)
# print the JSON string representation of the object
print(CreateIndexResponse.to_json())

# convert the object into a dict
create_index_response_dict = create_index_response_instance.to_dict()
# create an instance of CreateIndexResponse from a dict
create_index_response_from_dict = CreateIndexResponse.from_dict(create_index_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


