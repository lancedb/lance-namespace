# ListTablesResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**tables** | **List[str]** |  | 
**next_page_token** | **str** | An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial client request for a list API, if the server cannot return all items in one response, or if there are more items than the &#x60;pageSize&#x60; specified in the client request, the server must return a &#x60;nextPageToken&#x60; in the response indicating there are more results available. After the initial request, the value of &#x60;nextPageToken&#x60; from each response must be used by the client as the &#x60;pageToken&#x60; parameter value for the next request. Clients must interpret either &#x60;null&#x60;, missing value or empty string value of &#x60;nextPageToken&#x60; from a server response as the end of the listing results. | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.list_tables_response import ListTablesResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ListTablesResponse from a JSON string
list_tables_response_instance = ListTablesResponse.from_json(json)
# print the JSON string representation of the object
print(ListTablesResponse.to_json())

# convert the object into a dict
list_tables_response_dict = list_tables_response_instance.to_dict()
# create an instance of ListTablesResponse from a dict
list_tables_response_from_dict = ListTablesResponse.from_dict(list_tables_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


