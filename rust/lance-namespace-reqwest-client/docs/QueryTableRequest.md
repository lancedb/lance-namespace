# QueryTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**namespace** | **Vec<String>** |  | 
**bypass_vector_index** | Option<**bool**> | Whether to bypass vector index | [optional]
**columns** | Option<**Vec<String>**> | Optional list of columns to return | [optional]
**distance_type** | Option<**String**> | Distance metric to use | [optional]
**ef** | Option<**i32**> | Search effort parameter for HNSW index | [optional]
**fast_search** | Option<**bool**> | Whether to use fast search | [optional]
**filter** | Option<**String**> | Optional SQL filter expression | [optional]
**full_text_query** | Option<[**models::QueryTableRequestFullTextQuery**](QueryTableRequest_full_text_query.md)> |  | [optional]
**k** | **i32** | Number of results to return | 
**lower_bound** | Option<**f32**> | Lower bound for search | [optional]
**nprobes** | Option<**i32**> | Number of probes for IVF index | [optional]
**offset** | Option<**i32**> | Number of results to skip | [optional]
**prefilter** | Option<**bool**> | Whether to apply filtering before vector search | [optional]
**refine_factor** | Option<**i32**> | Refine factor for search | [optional]
**upper_bound** | Option<**f32**> | Upper bound for search | [optional]
**vector** | [**models::QueryTableRequestVector**](QueryTableRequest_vector.md) |  | 
**vector_column** | Option<**String**> | Name of the vector column to search | [optional]
**version** | Option<**i64**> | Table version to query | [optional]
**with_row_id** | Option<**bool**> | If true, return the row id as a column called `_rowid` | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


