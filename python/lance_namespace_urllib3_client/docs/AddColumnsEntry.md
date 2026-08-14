# AddColumnsEntry


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | Name of the new column | 
**expression** | **str** | SQL expression for the column (optional if virtual_column or computed is specified). Evaluated once over existing rows; nothing is stored, so rows appended later read null. | [optional] 
**computed** | **str** | SQL expression declaring a maintained computed column (optional if expression or virtual_column is specified). The column is added all-null with the expression persisted as its binding in field metadata; its type and input columns are inferred from the expression. Rows are filled by backfill, never at declaration. | [optional] 
**virtual_column** | [**AddVirtualColumnEntry**](AddVirtualColumnEntry.md) |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.add_columns_entry import AddColumnsEntry

# TODO update the JSON string below
json = "{}"
# create an instance of AddColumnsEntry from a JSON string
add_columns_entry_instance = AddColumnsEntry.from_json(json)
# print the JSON string representation of the object
print(AddColumnsEntry.to_json())

# convert the object into a dict
add_columns_entry_dict = add_columns_entry_instance.to_dict()
# create an instance of AddColumnsEntry from a dict
add_columns_entry_from_dict = AddColumnsEntry.from_dict(add_columns_entry_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


