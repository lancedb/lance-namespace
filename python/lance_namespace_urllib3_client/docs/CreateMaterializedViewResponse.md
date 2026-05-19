# CreateMaterializedViewResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | **int** | The commit version that created the materialized view | 
**job_id** | **str** | Refresh job ID, populated only when &#x60;with_no_data&#x60; was false.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_materialized_view_response import CreateMaterializedViewResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateMaterializedViewResponse from a JSON string
create_materialized_view_response_instance = CreateMaterializedViewResponse.from_json(json)
# print the JSON string representation of the object
print(CreateMaterializedViewResponse.to_json())

# convert the object into a dict
create_materialized_view_response_dict = create_materialized_view_response_instance.to_dict()
# create an instance of CreateMaterializedViewResponse from a dict
create_materialized_view_response_from_dict = CreateMaterializedViewResponse.from_dict(create_materialized_view_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


