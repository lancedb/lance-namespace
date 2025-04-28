# CreateCatalogRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**mode** | **str** |  | 
**options** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.create_catalog_request import CreateCatalogRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateCatalogRequest from a JSON string
create_catalog_request_instance = CreateCatalogRequest.from_json(json)
# print the JSON string representation of the object
print(CreateCatalogRequest.to_json())

# convert the object into a dict
create_catalog_request_dict = create_catalog_request_instance.to_dict()
# create an instance of CreateCatalogRequest from a dict
create_catalog_request_from_dict = CreateCatalogRequest.from_dict(create_catalog_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


