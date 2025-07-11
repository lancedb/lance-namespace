# QueryRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 
**vector** | **List[float]** | Query vector for similarity search | 
**k** | **int** | Number of results to return | 
**filter** | **str** | Optional SQL filter expression | [optional] 
**columns** | **List[str]** | Optional list of columns to return | [optional] 
**distance_type** | **str** | Distance metric to use | [optional] 
**prefilter** | **bool** | Whether to apply filtering before vector search | [optional] 
**bypass_vector_index** | **bool** | Whether to bypass vector index | [optional] 
**ef** | **int** | Search effort parameter for HNSW index | [optional] 
**nprobes** | **int** | Number of probes for IVF index | [optional] 
**refine_factor** | **int** | Refine factor for search | [optional] 
**with_row_id** | **bool** | Whether to include row ID in results | [optional] 
**offset** | **int** | Number of results to skip | [optional] 
**version** | **int** | Table version to query | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.query_request import QueryRequest

# TODO update the JSON string below
json = "{}"
# create an instance of QueryRequest from a JSON string
query_request_instance = QueryRequest.from_json(json)
# print the JSON string representation of the object
print(QueryRequest.to_json())

# convert the object into a dict
query_request_dict = query_request_instance.to_dict()
# create an instance of QueryRequest from a dict
query_request_from_dict = QueryRequest.from_dict(query_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


