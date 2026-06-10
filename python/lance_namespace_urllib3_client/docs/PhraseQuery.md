# PhraseQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | [optional] 
**slop** | **int** |  | [optional] 
**terms** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.phrase_query import PhraseQuery

# TODO update the JSON string below
json = "{}"
# create an instance of PhraseQuery from a JSON string
phrase_query_instance = PhraseQuery.from_json(json)
# print the JSON string representation of the object
print(PhraseQuery.to_json())

# convert the object into a dict
phrase_query_dict = phrase_query_instance.to_dict()
# create an instance of PhraseQuery from a dict
phrase_query_from_dict = PhraseQuery.from_dict(phrase_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


