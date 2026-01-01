

# UpdateTableRequest

Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**predicate** | **String** | Optional SQL predicate to filter rows for update |  [optional] |
|**updates** | **List&lt;List&lt;String&gt;&gt;** | List of column updates as [column_name, expression] pairs |  |



