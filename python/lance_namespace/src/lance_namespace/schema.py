"""
Schema conversion utilities for Lance Namespace implementations.
"""
from typing import List, Dict

try:
    import pyarrow as pa
    HAS_PYARROW = True
except ImportError:
    pa = None
    HAS_PYARROW = False

from lance_namespace_urllib3_client.models import (
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
)


def convert_json_arrow_schema_to_pyarrow(json_schema: JsonArrowSchema) -> "pa.Schema":
    """Convert JsonArrowSchema to PyArrow Schema.
    
    Args:
        json_schema: JsonArrowSchema from the client models
        
    Returns:
        PyArrow Schema object
        
    Raises:
        ImportError: If PyArrow is not available
        ValueError: If unsupported Arrow type is encountered
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for schema conversion")
    
    fields = []
    for json_field in json_schema.fields:
        arrow_type = convert_json_arrow_type_to_pyarrow(json_field.type)
        field = pa.field(json_field.name, arrow_type, nullable=json_field.nullable)
        fields.append(field)
    
    return pa.schema(fields, metadata=json_schema.metadata)


def convert_json_arrow_type_to_pyarrow(json_type: JsonArrowDataType) -> "pa.DataType":
    """Convert JsonArrowDataType to PyArrow DataType.
    
    Args:
        json_type: JsonArrowDataType from the client models
        
    Returns:
        PyArrow DataType object
        
    Raises:
        ImportError: If PyArrow is not available
        ValueError: If unsupported Arrow type is encountered
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for type conversion")
    
    # Convert type name to lowercase but preserve timezone case
    type_name = json_type.type
    type_name_lower = type_name.lower()
    
    if type_name_lower == "null":
        return pa.null()
    elif type_name_lower in ["bool", "boolean"]:
        return pa.bool_()
    elif type_name_lower == "int8":
        return pa.int8()
    elif type_name_lower == "uint8":
        return pa.uint8()
    elif type_name_lower == "int16":
        return pa.int16()
    elif type_name_lower == "uint16":
        return pa.uint16()
    elif type_name_lower == "int32":
        return pa.int32()
    elif type_name_lower == "uint32":
        return pa.uint32()
    elif type_name_lower == "int64":
        return pa.int64()
    elif type_name_lower == "uint64":
        return pa.uint64()
    elif type_name_lower == "float32":
        return pa.float32()
    elif type_name_lower == "float64":
        return pa.float64()
    elif type_name_lower == "utf8":
        return pa.utf8()
    elif type_name_lower == "binary":
        return pa.binary()
    elif type_name_lower == "date32":
        return pa.date32()
    elif type_name_lower == "date64":
        return pa.date64()
    elif type_name_lower.startswith("timestamp"):
        # Handle timestamp with timezone
        if "tz=" in type_name:
            tz = type_name.split("tz=")[1].rstrip("]")
            return pa.timestamp('us', tz=tz)
        else:
            return pa.timestamp('us')
    elif type_name_lower.startswith("decimal"):
        # Parse decimal(precision, scale)
        import re
        match = re.match(r'decimal\((\d+),\s*(\d+)\)', type_name)
        if match:
            precision = int(match.group(1))
            scale = int(match.group(2))
            return pa.decimal128(precision, scale)
        else:
            return pa.decimal128(38, 10)  # Default precision/scale
    else:
        raise ValueError(f"Unsupported Arrow type: {type_name_lower}")


def convert_pyarrow_schema_to_glue_columns(schema: "pa.Schema") -> List[Dict[str, str]]:
    """Convert PyArrow schema to Glue column definitions.
    
    Args:
        schema: PyArrow Schema object
        
    Returns:
        List of Glue column dictionaries
        
    Raises:
        ImportError: If PyArrow is not available
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for schema conversion")
    
    columns = []
    for field in schema:
        column = {
            'Name': field.name,
            'Type': convert_pyarrow_type_to_glue_type(field.type)
        }
        columns.append(column)
    return columns


def convert_pyarrow_type_to_glue_type(arrow_type: "pa.DataType") -> str:
    """Convert PyArrow type to Glue/Hive type string.
    
    Args:
        arrow_type: PyArrow DataType object
        
    Returns:
        String representation of Glue/Hive type
        
    Raises:
        ImportError: If PyArrow is not available
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for type conversion")
    
    if pa.types.is_boolean(arrow_type):
        return 'boolean'
    elif pa.types.is_int8(arrow_type) or pa.types.is_uint8(arrow_type):
        return 'tinyint'
    elif pa.types.is_int16(arrow_type) or pa.types.is_uint16(arrow_type):
        return 'smallint'
    elif pa.types.is_int32(arrow_type) or pa.types.is_uint32(arrow_type):
        return 'int'
    elif pa.types.is_int64(arrow_type) or pa.types.is_uint64(arrow_type):
        return 'bigint'
    elif pa.types.is_float32(arrow_type):
        return 'float'
    elif pa.types.is_float64(arrow_type):
        return 'double'
    elif pa.types.is_string(arrow_type):
        return 'string'
    elif pa.types.is_binary(arrow_type):
        return 'binary'
    elif pa.types.is_date32(arrow_type) or pa.types.is_date64(arrow_type):
        return 'date'
    elif pa.types.is_timestamp(arrow_type):
        return 'timestamp'
    elif pa.types.is_decimal(arrow_type):
        return f'decimal({arrow_type.precision},{arrow_type.scale})'
    elif pa.types.is_list(arrow_type):
        element_type = convert_pyarrow_type_to_glue_type(arrow_type.value_type)
        return f'array<{element_type}>'
    elif pa.types.is_struct(arrow_type):
        field_strs = []
        for field in arrow_type:
            field_type = convert_pyarrow_type_to_glue_type(field.type)
            field_strs.append(f'{field.name}:{field_type}')
        return f'struct<{",".join(field_strs)}>'
    elif pa.types.is_map(arrow_type):
        key_type = convert_pyarrow_type_to_glue_type(arrow_type.key_type)
        value_type = convert_pyarrow_type_to_glue_type(arrow_type.item_type)
        return f'map<{key_type},{value_type}>'
    else:
        # Default to string for unknown types
        return 'string'