# FragmentStats


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**num_fragments** | **int** | The number of fragments in the table | 
**num_small_fragments** | **int** | The number of uncompacted fragments in the table | 
**lengths** | [**FragmentSummary**](FragmentSummary.md) | Statistics on the number of rows in the table fragments | 

## Example

```python
from lance_namespace_urllib3_client.models.fragment_stats import FragmentStats

# TODO update the JSON string below
json = "{}"
# create an instance of FragmentStats from a JSON string
fragment_stats_instance = FragmentStats.from_json(json)
# print the JSON string representation of the object
print(FragmentStats.to_json())

# convert the object into a dict
fragment_stats_dict = fragment_stats_instance.to_dict()
# create an instance of FragmentStats from a dict
fragment_stats_from_dict = FragmentStats.from_dict(fragment_stats_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


