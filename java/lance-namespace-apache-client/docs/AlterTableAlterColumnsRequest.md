

# AlterTableAlterColumnsRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** | Table identifier path (namespace + table name) |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**alterations** | [**List&lt;AlterColumnsEntry&gt;**](AlterColumnsEntry.md) | List of column alterations to apply to the table |  |



