# ListTableBranchesResponse

Response containing table branches

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**branches** | [**Dict[str, BranchContents]**](BranchContents.md) | Map of branch names to their contents | 
**page_token** | **str** | An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation, if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request, the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either &#x60;null&#x60;, missing value or empty string value of the page token from the implementation&#39;s response as the end of the listing results.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.list_table_branches_response import ListTableBranchesResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ListTableBranchesResponse from a JSON string
list_table_branches_response_instance = ListTableBranchesResponse.from_json(json)
# print the JSON string representation of the object
print(ListTableBranchesResponse.to_json())

# convert the object into a dict
list_table_branches_response_dict = list_table_branches_response_instance.to_dict()
# create an instance of ListTableBranchesResponse from a dict
list_table_branches_response_from_dict = ListTableBranchesResponse.from_dict(list_table_branches_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


