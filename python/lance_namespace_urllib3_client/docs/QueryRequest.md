# QueryRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 
**bypass_vector_index** | **bool** | Whether to bypass vector index | [optional] 
**columns** | [**Columns**](Columns.md) |  | [optional] 
**distance_type** | **str** | Distance metric to use | [optional] 
**ef** | **int** | Search effort parameter for HNSW index | [optional] 
**fast_search** | **bool** | Whether to use fast search | [optional] 
**filter** | **str** | Optional SQL filter expression | [optional] 
**full_text_query** | [**FtsQueryInput**](FtsQueryInput.md) |  | [optional] 
**k** | **int** | Number of results to return | 
**lower_bound** | **float** | Lower bound for search | [optional] 
**nprobes** | **int** | Number of probes for IVF index | [optional] 
**offset** | **int** | Number of results to skip | [optional] 
**prefilter** | **bool** | Whether to apply filtering before vector search | [optional] 
**refine_factor** | **int** | Refine factor for search | [optional] 
**upper_bound** | **float** | Upper bound for search | [optional] 
**vector** | [**QueryVector**](QueryVector.md) | Query vector(s) for similarity search | 
**vector_column** | **str** | Name of the vector column to search | [optional] 
**version** | **int** | Table version to query | [optional] 
**with_row_id** | **bool** | If true, return the row id as a column called &#x60;_rowid&#x60; | [optional] 

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


