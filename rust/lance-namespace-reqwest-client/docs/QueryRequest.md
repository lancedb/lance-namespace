# QueryRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**namespace** | **Vec<String>** |  | 
**vector** | **Vec<f32>** | Query vector for similarity search | 
**k** | **i32** | Number of results to return | 
**filter** | Option<**String**> | Optional SQL filter expression | [optional]
**columns** | Option<**Vec<String>**> | Optional list of columns to return | [optional]
**distance_type** | Option<**String**> | Distance metric to use | [optional]
**prefilter** | Option<**bool**> | Whether to apply filtering before vector search | [optional]
**bypass_vector_index** | Option<**bool**> | Whether to bypass vector index | [optional]
**ef** | Option<**i32**> | Search effort parameter for HNSW index | [optional]
**nprobes** | Option<**i32**> | Number of probes for IVF index | [optional]
**refine_factor** | Option<**i32**> | Refine factor for search | [optional]
**with_row_id** | Option<**bool**> | Whether to include row ID in results | [optional]
**offset** | Option<**i32**> | Number of results to skip | [optional]
**version** | Option<**i64**> | Table version to query | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


