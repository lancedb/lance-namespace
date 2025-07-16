

# QueryRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  |
|**namespace** | **List&lt;String&gt;** |  |  |
|**bypassVectorIndex** | **Boolean** | Whether to bypass vector index |  [optional] |
|**columns** | [**Columns**](Columns.md) |  |  [optional] |
|**distanceType** | **String** | Distance metric to use |  [optional] |
|**ef** | **Integer** | Search effort parameter for HNSW index |  [optional] |
|**fastSearch** | **Boolean** | Whether to use fast search |  [optional] |
|**filter** | **String** | Optional SQL filter expression |  [optional] |
|**fullTextQuery** | [**FtsQueryInput**](FtsQueryInput.md) |  |  [optional] |
|**k** | **Integer** | Number of results to return |  |
|**lowerBound** | **Float** | Lower bound for search |  [optional] |
|**nprobes** | **Integer** | Number of probes for IVF index |  [optional] |
|**offset** | **Integer** | Number of results to skip |  [optional] |
|**prefilter** | **Boolean** | Whether to apply filtering before vector search |  [optional] |
|**refineFactor** | **Integer** | Refine factor for search |  [optional] |
|**upperBound** | **Float** | Upper bound for search |  [optional] |
|**vector** | [**QueryVector**](QueryVector.md) | Query vector(s) for similarity search |  |
|**vectorColumn** | **String** | Name of the vector column to search |  [optional] |
|**version** | **Long** | Table version to query |  [optional] |
|**withRowId** | **Boolean** | If true, return the row id as a column called &#x60;_rowid&#x60; |  [optional] |



