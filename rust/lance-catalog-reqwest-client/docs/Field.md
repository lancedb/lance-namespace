# Field

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**r#type** | **String** |  | 
**name** | **String** |  | 
**id** | [**models::Int**](int.md) |  | 
**parent_id** | Option<[**models::Int**](int.md)> |  | [optional]
**logical_type** | **String** | Logical types, currently support parameterized Arrow Type. PARENT types will always have logical type \"struct\". Logical type \"map\" is represented as a struct with a single child field \"entries\". \"entries\" is a struct with fields \"key\" and \"value\". REPEATED types may have logical types: * \"list\" * \"large_list\" * \"list.struct\" * \"large_list.struct\" The final two are used if the list values are structs, and therefore the field is both implicitly REPEATED and PARENT. LEAF types may have logical types: * \"null\" * \"bool\" * \"int8\" / \"uint8\" * \"int16\" / \"uint16\" * \"int32\" / \"uint32\" * \"int64\" / \"uint64\" * \"halffloat\" / \"float\" / \"double\" * \"string\" / \"large_string\" * \"binary\" / \"large_binary\" * \"date32:day\" * \"date64:ms\" * \"decimal:128:{precision}:{scale}\" / \"decimal:256:{precision}:{scale}\" * \"time:{unit}\" / \"timestamp:{unit}\" / \"duration:{unit}\", where unit is \"s\", \"ms\", \"us\", \"ns\" * \"dict:{value_type}:{index_type}:false\"  | 
**nullable** | Option<**bool**> |  | [optional]
**metadata** | Option<**std::collections::HashMap<String, String>**> | optional field metadata (e.g. extension type name/parameters) | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


