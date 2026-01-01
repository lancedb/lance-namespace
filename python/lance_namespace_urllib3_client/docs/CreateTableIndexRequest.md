# CreateTableIndexRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**column** | **str** | Name of the column to create index on | 
**index_type** | **str** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) | 
**name** | **str** | Optional name for the index. If not provided, a name will be auto-generated. | [optional] 
**distance_type** | **str** | Distance metric type for vector indexes (e.g., l2, cosine, dot) | [optional] 
**with_position** | **bool** | Optional FTS parameter for position tracking | [optional] 
**base_tokenizer** | **str** | Optional FTS parameter for base tokenizer | [optional] 
**language** | **str** | Optional FTS parameter for language | [optional] 
**max_token_length** | **int** | Optional FTS parameter for maximum token length | [optional] 
**lower_case** | **bool** | Optional FTS parameter for lowercase conversion | [optional] 
**stem** | **bool** | Optional FTS parameter for stemming | [optional] 
**remove_stop_words** | **bool** | Optional FTS parameter for stop word removal | [optional] 
**ascii_folding** | **bool** | Optional FTS parameter for ASCII folding | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_index_request import CreateTableIndexRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableIndexRequest from a JSON string
create_table_index_request_instance = CreateTableIndexRequest.from_json(json)
# print the JSON string representation of the object
print(CreateTableIndexRequest.to_json())

# convert the object into a dict
create_table_index_request_dict = create_table_index_request_instance.to_dict()
# create an instance of CreateTableIndexRequest from a dict
create_table_index_request_from_dict = CreateTableIndexRequest.from_dict(create_table_index_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


