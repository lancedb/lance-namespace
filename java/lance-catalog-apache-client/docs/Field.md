

# Field


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**name** | **String** |  |  |
|**id** | **Integer** |  |  |
|**parentId** | **Integer** |  |  [optional] |
|**logicalType** | **String** | Logical types, currently support parameterized Arrow Type. PARENT types will always have logical type \&quot;struct\&quot;. Logical type \&quot;map\&quot; is represented as a struct with a single child field \&quot;entries\&quot;. \&quot;entries\&quot; is a struct with fields \&quot;key\&quot; and \&quot;value\&quot;. REPEATED types may have logical types: * \&quot;list\&quot; * \&quot;large_list\&quot; * \&quot;list.struct\&quot; * \&quot;large_list.struct\&quot; The final two are used if the list values are structs, and therefore the field is both implicitly REPEATED and PARENT. LEAF types may have logical types: * \&quot;null\&quot; * \&quot;bool\&quot; * \&quot;int8\&quot; / \&quot;uint8\&quot; * \&quot;int16\&quot; / \&quot;uint16\&quot; * \&quot;int32\&quot; / \&quot;uint32\&quot; * \&quot;int64\&quot; / \&quot;uint64\&quot; * \&quot;halffloat\&quot; / \&quot;float\&quot; / \&quot;double\&quot; * \&quot;string\&quot; / \&quot;large_string\&quot; * \&quot;binary\&quot; / \&quot;large_binary\&quot; * \&quot;date32:day\&quot; * \&quot;date64:ms\&quot; * \&quot;decimal:128:{precision}:{scale}\&quot; / \&quot;decimal:256:{precision}:{scale}\&quot; * \&quot;time:{unit}\&quot; / \&quot;timestamp:{unit}\&quot; / \&quot;duration:{unit}\&quot;, where unit is \&quot;s\&quot;, \&quot;ms\&quot;, \&quot;us\&quot;, \&quot;ns\&quot; * \&quot;dict:{value_type}:{index_type}:false\&quot;  |  |
|**nullable** | **Boolean** |  |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | optional field metadata (e.g. extension type name/parameters) |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| PARENT | &quot;PARENT&quot; |
| REPEATED | &quot;REPEATED&quot; |
| LEAF | &quot;LEAF&quot; |



