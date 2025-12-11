

# InsertIntoTableRequest

Request for inserting records into a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | **String** | How the insert should behave. Case insensitive. Valid values are: - create: create new table, fail if table already exists - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it  |  [optional] |



