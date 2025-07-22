

# CreateTableRequest

Request for creating a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `location`: pass through header `x-lance-table-location` - `properties`: pass through header `x-lance-table-properties` 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  [optional] |
|**namespace** | **List&lt;String&gt;** |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



