# CreateTableIndexRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**column** | **String** | Name of the column to create index on | 
**index_type** | **String** | Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS) | 
**name** | Option<**String**> | Optional name for the index. If not provided, a name will be auto-generated. | [optional]
**distance_type** | Option<**String**> | Distance metric type for vector indexes (e.g., l2, cosine, dot) | [optional]
**with_position** | Option<**bool**> | Optional FTS parameter for position tracking | [optional]
**base_tokenizer** | Option<**String**> | Optional FTS parameter for base tokenizer | [optional]
**language** | Option<**String**> | Optional FTS parameter for language | [optional]
**max_token_length** | Option<**i32**> | Optional FTS parameter for maximum token length | [optional]
**lower_case** | Option<**bool**> | Optional FTS parameter for lowercase conversion | [optional]
**stem** | Option<**bool**> | Optional FTS parameter for stemming | [optional]
**remove_stop_words** | Option<**bool**> | Optional FTS parameter for stop word removal | [optional]
**ascii_folding** | Option<**bool**> | Optional FTS parameter for ASCII folding | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


