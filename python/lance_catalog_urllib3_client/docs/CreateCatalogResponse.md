# CreateCatalogResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**properties** | **Dict[str, str]** | Properties stored on the catalog, if supported by the server. | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.create_catalog_response import CreateCatalogResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateCatalogResponse from a JSON string
create_catalog_response_instance = CreateCatalogResponse.from_json(json)
# print the JSON string representation of the object
print(CreateCatalogResponse.to_json())

# convert the object into a dict
create_catalog_response_dict = create_catalog_response_instance.to_dict()
# create an instance of CreateCatalogResponse from a dict
create_catalog_response_from_dict = CreateCatalogResponse.from_dict(create_catalog_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


