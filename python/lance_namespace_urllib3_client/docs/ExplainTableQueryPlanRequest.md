# ExplainTableQueryPlanRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**query** | [**QueryTableRequest**](QueryTableRequest.md) |  | 
**verbose** | **bool** | Whether to return verbose explanation | [optional] [default to False]

## Example

```python
from lance_namespace_urllib3_client.models.explain_table_query_plan_request import ExplainTableQueryPlanRequest

# TODO update the JSON string below
json = "{}"
# create an instance of ExplainTableQueryPlanRequest from a JSON string
explain_table_query_plan_request_instance = ExplainTableQueryPlanRequest.from_json(json)
# print the JSON string representation of the object
print(ExplainTableQueryPlanRequest.to_json())

# convert the object into a dict
explain_table_query_plan_request_dict = explain_table_query_plan_request_instance.to_dict()
# create an instance of ExplainTableQueryPlanRequest from a dict
explain_table_query_plan_request_from_dict = ExplainTableQueryPlanRequest.from_dict(explain_table_query_plan_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


