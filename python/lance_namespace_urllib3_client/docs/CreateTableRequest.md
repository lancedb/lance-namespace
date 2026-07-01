# CreateTableRequest

Request for creating a table, excluding the Arrow IPC stream. The table location and any credential vending behavior are determined by the implementation and returned in the response, rather than specified in this request. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** |  | [optional] 
**mode** | **str** | There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing table is kept.   * Overwrite: the existing table is dropped and a new table with this name is created.  | [optional] 
**properties** | **Dict[str, str]** | Business logic properties stored and managed by the namespace implementation outside Lance context, if supported by the implementation.  | [optional] 
**storage_options** | **Dict[str, str]** | Storage options that configure overrides for writing table data and metadata during table creation. These are passed to Lance for the write path.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_request import CreateTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableRequest from a JSON string
create_table_request_instance = CreateTableRequest.from_json(json)
# print the JSON string representation of the object
print(CreateTableRequest.to_json())

# convert the object into a dict
create_table_request_dict = create_table_request_instance.to_dict()
# create an instance of CreateTableRequest from a dict
create_table_request_from_dict = CreateTableRequest.from_dict(create_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


