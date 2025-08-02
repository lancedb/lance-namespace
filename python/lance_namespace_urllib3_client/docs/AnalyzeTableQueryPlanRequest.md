# AnalyzeTableQueryPlanRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **List[str]** |  | [optional] 
**query** | [**QueryTableRequest**](QueryTableRequest.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.analyze_table_query_plan_request import AnalyzeTableQueryPlanRequest

# TODO update the JSON string below
json = "{}"
# create an instance of AnalyzeTableQueryPlanRequest from a JSON string
analyze_table_query_plan_request_instance = AnalyzeTableQueryPlanRequest.from_json(json)
# print the JSON string representation of the object
print(AnalyzeTableQueryPlanRequest.to_json())

# convert the object into a dict
analyze_table_query_plan_request_dict = analyze_table_query_plan_request_instance.to_dict()
# create an instance of AnalyzeTableQueryPlanRequest from a dict
analyze_table_query_plan_request_from_dict = AnalyzeTableQueryPlanRequest.from_dict(analyze_table_query_plan_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


