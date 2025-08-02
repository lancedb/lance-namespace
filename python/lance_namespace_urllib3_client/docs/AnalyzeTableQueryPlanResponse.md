# AnalyzeTableQueryPlanResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**analysis** | **str** | Detailed analysis of the query execution plan | 

## Example

```python
from lance_namespace_urllib3_client.models.analyze_table_query_plan_response import AnalyzeTableQueryPlanResponse

# TODO update the JSON string below
json = "{}"
# create an instance of AnalyzeTableQueryPlanResponse from a JSON string
analyze_table_query_plan_response_instance = AnalyzeTableQueryPlanResponse.from_json(json)
# print the JSON string representation of the object
print(AnalyzeTableQueryPlanResponse.to_json())

# convert the object into a dict
analyze_table_query_plan_response_dict = analyze_table_query_plan_response_instance.to_dict()
# create an instance of AnalyzeTableQueryPlanResponse from a dict
analyze_table_query_plan_response_from_dict = AnalyzeTableQueryPlanResponse.from_dict(analyze_table_query_plan_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


