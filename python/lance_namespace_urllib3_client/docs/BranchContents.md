# BranchContents


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**parent_branch** | **str** | Name of the branch this branch was created from. Absent when the branch was created from the main branch.  | [optional] 
**parent_version** | **int** | Version of the parent (branch or main) this branch was created from | 
**create_at** | **int** | Unix timestamp (in seconds) when the branch was created | 
**manifest_size** | **int** | Size of the branch&#39;s manifest file in bytes | 
**metadata** | **Dict[str, str]** | Key-value metadata associated with the branch | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.branch_contents import BranchContents

# TODO update the JSON string below
json = "{}"
# create an instance of BranchContents from a JSON string
branch_contents_instance = BranchContents.from_json(json)
# print the JSON string representation of the object
print(BranchContents.to_json())

# convert the object into a dict
branch_contents_dict = branch_contents_instance.to_dict()
# create an instance of BranchContents from a dict
branch_contents_from_dict = BranchContents.from_dict(branch_contents_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


