

# QueryRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  |
|**namespace** | **List&lt;String&gt;** |  |  |
|**vector** | **List&lt;Float&gt;** | Query vector for similarity search |  |
|**k** | **Integer** | Number of results to return |  |
|**filter** | **String** | Optional SQL filter expression |  [optional] |
|**columns** | **List&lt;String&gt;** | Optional list of columns to return |  [optional] |
|**distanceType** | [**DistanceTypeEnum**](#DistanceTypeEnum) | Distance metric to use |  [optional] |
|**prefilter** | **Boolean** | Whether to apply filtering before vector search |  [optional] |
|**bypassVectorIndex** | **Boolean** | Whether to bypass vector index |  [optional] |
|**ef** | **Integer** | Search effort parameter for HNSW index |  [optional] |
|**nprobes** | **Integer** | Number of probes for IVF index |  [optional] |
|**refineFactor** | **Integer** | Refine factor for search |  [optional] |
|**withRowId** | **Boolean** | Whether to include row ID in results |  [optional] |
|**offset** | **Integer** | Number of results to skip |  [optional] |
|**version** | **Long** | Table version to query |  [optional] |



## Enum: DistanceTypeEnum

| Name | Value |
|---- | -----|
| L2 | &quot;l2&quot; |
| COSINE | &quot;cosine&quot; |
| DOT | &quot;dot&quot; |



