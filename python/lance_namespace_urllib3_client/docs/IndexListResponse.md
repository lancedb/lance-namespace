# IndexListResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**location** | **str** | Table location (usually empty) | 
**properties** | **Dict[str, str]** | Additional properties (usually empty) | [optional] 
**indexes** | [**List[IndexListItemResponse]**](IndexListItemResponse.md) | List of indexes on the table | 

## Example

```python
from lance_namespace_urllib3_client.models.index_list_response import IndexListResponse

# TODO update the JSON string below
json = "{}"
# create an instance of IndexListResponse from a JSON string
index_list_response_instance = IndexListResponse.from_json(json)
# print the JSON string representation of the object
print(IndexListResponse.to_json())

# convert the object into a dict
index_list_response_dict = index_list_response_instance.to_dict()
# create an instance of IndexListResponse from a dict
index_list_response_from_dict = IndexListResponse.from_dict(index_list_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


