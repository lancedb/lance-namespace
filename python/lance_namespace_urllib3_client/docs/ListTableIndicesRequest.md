# ListTableIndicesRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**version** | **int** | Optional table version to list indexes from | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.list_table_indices_request import ListTableIndicesRequest

# TODO update the JSON string below
json = "{}"
# create an instance of ListTableIndicesRequest from a JSON string
list_table_indices_request_instance = ListTableIndicesRequest.from_json(json)
# print the JSON string representation of the object
print(ListTableIndicesRequest.to_json())

# convert the object into a dict
list_table_indices_request_dict = list_table_indices_request_instance.to_dict()
# create an instance of ListTableIndicesRequest from a dict
list_table_indices_request_from_dict = ListTableIndicesRequest.from_dict(list_table_indices_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


