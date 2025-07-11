# CreateIndexRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | The table name | 
**namespace** | **Vec<String>** | The namespace identifier | 
**column** | **String** | Name of the column to create index on | 
**index_type** | **String** | Type of index to create | 
**metric_type** | Option<**String**> | Distance metric type for vector indexes | [optional]
**num_partitions** | Option<**i32**> | Number of partitions for IVF indexes | [optional]
**num_sub_vectors** | Option<**i32**> | Number of sub-vectors for PQ indexes | [optional]
**num_bits** | Option<**i32**> | Number of bits for scalar quantization | [optional]
**max_iterations** | Option<**i32**> | Maximum iterations for index building | [optional]
**sample_rate** | Option<**i32**> | Sample rate for index building | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


