

# BranchContents


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**parentBranch** | **String** | Name of the branch this branch was created from. Absent when the branch was created from the main branch.  |  [optional] |
|**parentVersion** | **Long** | Version of the parent (branch or main) this branch was created from |  |
|**createAt** | **Long** | Unix timestamp (in seconds) when the branch was created |  |
|**manifestSize** | **Long** | Size of the branch&#39;s manifest file in bytes |  |
|**metadata** | **Map&lt;String, String&gt;** | Key-value metadata associated with the branch |  [optional] |



