# MatchQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**boost** | **float** |  | [optional] 
**column** | **str** |  | 
**fuzziness** | **int** |  | [optional] 
**max_expansions** | **int** | The maximum number of terms to expand for fuzzy matching. Default to 50. | [optional] 
**operator** | [**Operator**](Operator.md) | The operator to use for combining terms. This can be either &#x60;And&#x60; or &#x60;Or&#x60;, it&#39;s &#39;Or&#39; by default. - &#x60;And&#x60;: All terms must match. - &#x60;Or&#x60;: At least one term must match. | [optional] 
**prefix_length** | **int** | The number of beginning characters being unchanged for fuzzy matching. Default to 0. | [optional] 
**terms** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.match_query import MatchQuery

# TODO update the JSON string below
json = "{}"
# create an instance of MatchQuery from a JSON string
match_query_instance = MatchQuery.from_json(json)
# print the JSON string representation of the object
print(MatchQuery.to_json())

# convert the object into a dict
match_query_dict = match_query_instance.to_dict()
# create an instance of MatchQuery from a dict
match_query_from_dict = MatchQuery.from_dict(match_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


