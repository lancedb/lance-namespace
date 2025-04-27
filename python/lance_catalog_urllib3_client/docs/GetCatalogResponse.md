# GetCatalogResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**catalog** | **str** |  | 
**properties** | **Dict[str, str]** | Properties stored on the catalog, if supported by the server. If the server does not support catalog properties, it should return null for this field. If catalog properties are supported, but none are set, it should return an empty object. | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.get_catalog_response import GetCatalogResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetCatalogResponse from a JSON string
get_catalog_response_instance = GetCatalogResponse.from_json(json)
# print the JSON string representation of the object
print(GetCatalogResponse.to_json())

# convert the object into a dict
get_catalog_response_dict = get_catalog_response_instance.to_dict()
# create an instance of GetCatalogResponse from a dict
get_catalog_response_from_dict = GetCatalogResponse.from_dict(get_catalog_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


