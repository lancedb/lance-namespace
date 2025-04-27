# AlterTransactionUnsetProperty


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **str** |  | 
**key** | **str** |  | [optional] 
**mode** | **str** | the behavior if the property key to unset does not exist | [optional] [default to 'SKIP']

## Example

```python
from lance_catalog_urllib3_client.models.alter_transaction_unset_property import AlterTransactionUnsetProperty

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTransactionUnsetProperty from a JSON string
alter_transaction_unset_property_instance = AlterTransactionUnsetProperty.from_json(json)
# print the JSON string representation of the object
print(AlterTransactionUnsetProperty.to_json())

# convert the object into a dict
alter_transaction_unset_property_dict = alter_transaction_unset_property_instance.to_dict()
# create an instance of AlterTransactionUnsetProperty from a dict
alter_transaction_unset_property_from_dict = AlterTransactionUnsetProperty.from_dict(alter_transaction_unset_property_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


