

# FtsQuery

Full-text search query. Exactly one query type field must be provided. This structure follows the same pattern as AlterTransactionAction to minimize differences and compatibility issues across codegen in different languages. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**match** | [**MatchQuery**](MatchQuery.md) |  |  [optional] |
|**phrase** | [**PhraseQuery**](PhraseQuery.md) |  |  [optional] |
|**boost** | [**BoostQuery**](BoostQuery.md) |  |  [optional] |
|**multiMatch** | [**MultiMatchQuery**](MultiMatchQuery.md) |  |  [optional] |
|**_boolean** | [**BooleanQuery**](BooleanQuery.md) |  |  [optional] |



