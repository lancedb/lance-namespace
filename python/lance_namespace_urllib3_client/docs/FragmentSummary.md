# FragmentSummary


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**min** | **int** |  | 
**max** | **int** |  | 
**mean** | **int** |  | 
**p25** | **int** |  | 
**p50** | **int** |  | 
**p75** | **int** |  | 
**p99** | **int** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.fragment_summary import FragmentSummary

# TODO update the JSON string below
json = "{}"
# create an instance of FragmentSummary from a JSON string
fragment_summary_instance = FragmentSummary.from_json(json)
# print the JSON string representation of the object
print(FragmentSummary.to_json())

# convert the object into a dict
fragment_summary_dict = fragment_summary_instance.to_dict()
# create an instance of FragmentSummary from a dict
fragment_summary_from_dict = FragmentSummary.from_dict(fragment_summary_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


