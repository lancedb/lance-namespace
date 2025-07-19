# QueryRequestVector

Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**single_vector** | **List[float]** | Single query vector | [optional] 
**multi_vector** | **List[List[float]]** | Multiple query vectors for batch search | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.query_request_vector import QueryRequestVector

# TODO update the JSON string below
json = "{}"
# create an instance of QueryRequestVector from a JSON string
query_request_vector_instance = QueryRequestVector.from_json(json)
# print the JSON string representation of the object
print(QueryRequestVector.to_json())

# convert the object into a dict
query_request_vector_dict = query_request_vector_instance.to_dict()
# create an instance of QueryRequestVector from a dict
query_request_vector_from_dict = QueryRequestVector.from_dict(query_request_vector_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


