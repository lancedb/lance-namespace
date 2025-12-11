# NewColumnTransform


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | Name of the new column | 
**expression** | **str** | SQL expression to compute the column value (optional if virtual_column is specified) | [optional] 
**virtual_column** | [**AddVirtualColumnEntry**](AddVirtualColumnEntry.md) | Virtual column definition (optional if expression is specified) | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.new_column_transform import NewColumnTransform

# TODO update the JSON string below
json = "{}"
# create an instance of NewColumnTransform from a JSON string
new_column_transform_instance = NewColumnTransform.from_json(json)
# print the JSON string representation of the object
print(NewColumnTransform.to_json())

# convert the object into a dict
new_column_transform_dict = new_column_transform_instance.to_dict()
# create an instance of NewColumnTransform from a dict
new_column_transform_from_dict = NewColumnTransform.from_dict(new_column_transform_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


