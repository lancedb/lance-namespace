# AlterTransactionSetProperty


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **str** |  | [default to 'SetProperty']
**key** | **str** |  | [optional] 
**value** | **str** |  | [optional] 
**mode** | **str** | the behavior if the property key already exists | [optional] [default to 'OVERWRITE']

## Example

```python
from lance_catalog_urllib3_client.models.alter_transaction_set_property import AlterTransactionSetProperty

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTransactionSetProperty from a JSON string
alter_transaction_set_property_instance = AlterTransactionSetProperty.from_json(json)
# print the JSON string representation of the object
print(AlterTransactionSetProperty.to_json())

# convert the object into a dict
alter_transaction_set_property_dict = alter_transaction_set_property_instance.to_dict()
# create an instance of AlterTransactionSetProperty from a dict
alter_transaction_set_property_from_dict = AlterTransactionSetProperty.from_dict(alter_transaction_set_property_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


