# Identity

Identity information of a request. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**api_key** | **str** | API key for authentication.  REST NAMESPACE ONLY This is passed via the &#x60;x-api-key&#x60; header.  | [optional] 
**auth_token** | **str** | Bearer token for authentication.  REST NAMESPACE ONLY This is passed via the &#x60;Authorization&#x60; header with the Bearer scheme (e.g., &#x60;Bearer &lt;token&gt;&#x60;).  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.identity import Identity

# TODO update the JSON string below
json = "{}"
# create an instance of Identity from a JSON string
identity_instance = Identity.from_json(json)
# print the JSON string representation of the object
print(Identity.to_json())

# convert the object into a dict
identity_dict = identity_instance.to_dict()
# create an instance of Identity from a dict
identity_from_dict = Identity.from_dict(identity_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


