# CreateIndexRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**column** | **str** | Name of the column to create index on | 
**index_type** | **str** | Type of index to create | 
**metric_type** | **str** | Distance metric type for vector indexes | [optional] 
**num_partitions** | **int** | Number of partitions for IVF indexes | [optional] 
**num_sub_vectors** | **int** | Number of sub-vectors for PQ indexes | [optional] 
**num_bits** | **int** | Number of bits for scalar quantization | [optional] 
**max_iterations** | **int** | Maximum iterations for index building | [optional] 
**sample_rate** | **int** | Sample rate for index building | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_index_request import CreateIndexRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateIndexRequest from a JSON string
create_index_request_instance = CreateIndexRequest.from_json(json)
# print the JSON string representation of the object
print(CreateIndexRequest.to_json())

# convert the object into a dict
create_index_request_dict = create_index_request_instance.to_dict()
# create an instance of CreateIndexRequest from a dict
create_index_request_from_dict = CreateIndexRequest.from_dict(create_index_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


