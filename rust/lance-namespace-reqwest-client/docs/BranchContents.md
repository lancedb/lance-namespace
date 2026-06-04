# BranchContents

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**parent_branch** | Option<**String**> | Name of the branch this branch was created from. Absent when the branch was created from the main branch.  | [optional]
**parent_version** | **i64** | Version of the parent (branch or main) this branch was created from | 
**create_at** | **i64** | Unix timestamp (in seconds) when the branch was created | 
**manifest_size** | **i64** | Size of the branch's manifest file in bytes | 
**metadata** | Option<**std::collections::HashMap<String, String>**> | Key-value metadata associated with the branch | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


