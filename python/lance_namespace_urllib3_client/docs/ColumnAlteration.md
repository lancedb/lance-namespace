# ColumnAlteration


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **str** | Name of the column to alter | 
**rename** | **str** | New name for the column (optional) | [optional] 
**cast_to** | **str** | New data type to cast the column to (optional) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.column_alteration import ColumnAlteration

# TODO update the JSON string below
json = "{}"
# create an instance of ColumnAlteration from a JSON string
column_alteration_instance = ColumnAlteration.from_json(json)
# print the JSON string representation of the object
print(ColumnAlteration.to_json())

# convert the object into a dict
column_alteration_dict = column_alteration_instance.to_dict()
# create an instance of ColumnAlteration from a dict
column_alteration_from_dict = ColumnAlteration.from_dict(column_alteration_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


