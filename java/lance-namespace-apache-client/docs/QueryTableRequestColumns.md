

# QueryTableRequestColumns

Optional field paths to return. Provide either column_names or column_aliases, not both. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**columnNames** | **List&lt;String&gt;** | List of Lance field paths to return. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. |  [optional] |
|**columnAliases** | **Map&lt;String, String&gt;** | Object mapping output aliases to source Lance field paths. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. |  [optional] |



