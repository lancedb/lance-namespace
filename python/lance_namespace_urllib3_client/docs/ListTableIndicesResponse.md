# ListTableIndicesResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**indexes** | [**List[IndexListItemResponse]**](IndexListItemResponse.md) | List of indexes on the table | 

## Example

```python
from lance_namespace_urllib3_client.models.list_table_indices_response import ListTableIndicesResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ListTableIndicesResponse from a JSON string
list_table_indices_response_instance = ListTableIndicesResponse.from_json(json)
# print the JSON string representation of the object
print(ListTableIndicesResponse.to_json())

# convert the object into a dict
list_table_indices_response_dict = list_table_indices_response_instance.to_dict()
# create an instance of ListTableIndicesResponse from a dict
list_table_indices_response_from_dict = ListTableIndicesResponse.from_dict(list_table_indices_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


