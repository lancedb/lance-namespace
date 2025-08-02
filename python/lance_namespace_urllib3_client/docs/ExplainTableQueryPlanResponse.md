# ExplainTableQueryPlanResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**plan** | **str** | Human-readable query execution plan | 

## Example

```python
from lance_namespace_urllib3_client.models.explain_table_query_plan_response import ExplainTableQueryPlanResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ExplainTableQueryPlanResponse from a JSON string
explain_table_query_plan_response_instance = ExplainTableQueryPlanResponse.from_json(json)
# print the JSON string representation of the object
print(ExplainTableQueryPlanResponse.to_json())

# convert the object into a dict
explain_table_query_plan_response_dict = explain_table_query_plan_response_instance.to_dict()
# create an instance of ExplainTableQueryPlanResponse from a dict
explain_table_query_plan_response_from_dict = ExplainTableQueryPlanResponse.from_dict(explain_table_query_plan_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


