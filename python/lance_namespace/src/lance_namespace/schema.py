"""
Schema conversion utilities for Lance Namespace implementations.

This module contains functions for converting between JSON Arrow schema
representations and PyArrow schemas. These are generic conversions that
can be used by any namespace implementation.
"""

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


def convert_pyarrow_schema_to_json_arrow(schema: "pa.Schema") -> JsonArrowSchema:
    """Convert PyArrow schema to JSON Arrow schema.
    
    Args:
        schema: PyArrow schema to convert
        
    Returns:
        JsonArrowSchema object
        
    Raises:
        ImportError: If PyArrow is not available
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for schema conversion")
    
    fields = []
    for field in schema:
        json_field = JsonArrowField(
            name=field.name,
            nullable=field.nullable,
            type=convert_pyarrow_type_to_json_arrow(field.type),
            metadata=field.metadata
        )
        fields.append(json_field)
    
    return JsonArrowSchema(fields=fields)


def convert_pyarrow_type_to_json_arrow(dtype: "pa.DataType") -> JsonArrowDataType:
    """Convert PyArrow data type to JSON Arrow data type.
    
    Args:
        dtype: PyArrow data type to convert
        
    Returns:
        JsonArrowDataType object
        
    Raises:
        ImportError: If PyArrow is not available
    """
    if not HAS_PYARROW:
        raise ImportError("PyArrow is required for schema conversion")
    
    if pa.types.is_boolean(dtype):
        return JsonArrowDataType(name="bool")
    elif pa.types.is_int8(dtype):
        return JsonArrowDataType(name="int", bitWidth=8, isSigned=True)
    elif pa.types.is_int16(dtype):
        return JsonArrowDataType(name="int", bitWidth=16, isSigned=True)
    elif pa.types.is_int32(dtype):
        return JsonArrowDataType(name="int", bitWidth=32, isSigned=True)
    elif pa.types.is_int64(dtype):
        return JsonArrowDataType(name="int", bitWidth=64, isSigned=True)
    elif pa.types.is_uint8(dtype):
        return JsonArrowDataType(name="int", bitWidth=8, isSigned=False)
    elif pa.types.is_uint16(dtype):
        return JsonArrowDataType(name="int", bitWidth=16, isSigned=False)
    elif pa.types.is_uint32(dtype):
        return JsonArrowDataType(name="int", bitWidth=32, isSigned=False)
    elif pa.types.is_uint64(dtype):
        return JsonArrowDataType(name="int", bitWidth=64, isSigned=False)
    elif pa.types.is_float32(dtype):
        return JsonArrowDataType(name="floatingpoint", precision="SINGLE")
    elif pa.types.is_float64(dtype):
        return JsonArrowDataType(name="floatingpoint", precision="DOUBLE")
    elif pa.types.is_string(dtype):
        return JsonArrowDataType(name="utf8")
    elif pa.types.is_binary(dtype):
        return JsonArrowDataType(name="binary")
    elif pa.types.is_timestamp(dtype):
        return JsonArrowDataType(
            name="timestamp",
            unit=dtype.unit,
            timezone=dtype.tz
        )
    elif pa.types.is_date32(dtype):
        return JsonArrowDataType(name="date", unit="DAY")
    elif pa.types.is_date64(dtype):
        return JsonArrowDataType(name="date", unit="MILLISECOND")
    elif pa.types.is_decimal(dtype):
        return JsonArrowDataType(
            name="decimal",
            precision=dtype.precision,
            scale=dtype.scale
        )
    elif pa.types.is_list(dtype):
        return JsonArrowDataType(name="list")
    elif pa.types.is_struct(dtype):
        return JsonArrowDataType(name="struct")
    elif pa.types.is_map(dtype):
        return JsonArrowDataType(name="map")
    else:
        return JsonArrowDataType(name="unknown")


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