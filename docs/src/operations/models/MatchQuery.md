

# MatchQuery


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**boost** | **Float** |  |  [optional] |
|**column** | **String** |  |  |
|**fuzziness** | **Integer** |  |  [optional] |
|**maxExpansions** | **Integer** | The maximum number of terms to expand for fuzzy matching. Default to 50. |  [optional] |
|**operator** | **Operator** | The operator to use for combining terms. This can be either &#x60;And&#x60; or &#x60;Or&#x60;, it&#39;s &#39;Or&#39; by default. - &#x60;And&#x60;: All terms must match. - &#x60;Or&#x60;: At least one term must match. |  [optional] |
|**prefixLength** | **Integer** | The number of beginning characters being unchanged for fuzzy matching. Default to 0. |  [optional] |
|**terms** | **String** |  |  |



