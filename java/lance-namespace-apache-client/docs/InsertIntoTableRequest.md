

# InsertIntoTableRequest

Request for inserting records into a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | **String** | How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Create: create new table, fail if table already exists - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  |  [optional] |



