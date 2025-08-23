"""
Tests for schema conversion utilities.
"""
import pytest
import pyarrow as pa

from lance_namespace.schema import (
    convert_json_arrow_schema_to_pyarrow,
    convert_json_arrow_type_to_pyarrow,
    convert_pyarrow_schema_to_glue_columns,
    convert_pyarrow_type_to_glue_type,
)
from lance_namespace_urllib3_client.models import (
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
)


class TestJsonArrowToPyArrow:
    """Test JSON Arrow to PyArrow conversions."""
    
    def test_convert_basic_types(self):
        """Test conversion of basic Arrow types."""
        # Test null
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='null')) == pa.null()
        
        # Test boolean
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='bool')) == pa.bool_()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='boolean')) == pa.bool_()
        
        # Test integers
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='int8')) == pa.int8()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='uint8')) == pa.uint8()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='int16')) == pa.int16()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='uint16')) == pa.uint16()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='int32')) == pa.int32()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='uint32')) == pa.uint32()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='int64')) == pa.int64()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='uint64')) == pa.uint64()
        
        # Test floats
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='float32')) == pa.float32()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='float64')) == pa.float64()
        
        # Test strings and binary
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='utf8')) == pa.utf8()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='binary')) == pa.binary()
        
        # Test dates
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='date32')) == pa.date32()
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='date64')) == pa.date64()
    
    def test_convert_timestamp_types(self):
        """Test conversion of timestamp types."""
        # Without timezone
        assert convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='timestamp')) == pa.timestamp('us')
        
        # With timezone
        assert convert_json_arrow_type_to_pyarrow(
            JsonArrowDataType(type='timestamp[tz=UTC]')
        ) == pa.timestamp('us', tz='UTC')
        
        assert convert_json_arrow_type_to_pyarrow(
            JsonArrowDataType(type='timestamp[tz=America/New_York]')
        ) == pa.timestamp('us', tz='America/New_York')
    
    def test_convert_decimal_types(self):
        """Test conversion of decimal types."""
        # With precision and scale
        assert convert_json_arrow_type_to_pyarrow(
            JsonArrowDataType(type='decimal(10, 2)')
        ) == pa.decimal128(10, 2)
        
        assert convert_json_arrow_type_to_pyarrow(
            JsonArrowDataType(type='decimal(38,10)')
        ) == pa.decimal128(38, 10)
        
        # Default precision/scale
        assert convert_json_arrow_type_to_pyarrow(
            JsonArrowDataType(type='decimal')
        ) == pa.decimal128(38, 10)
    
    def test_convert_unsupported_type(self):
        """Test that unsupported types raise an error."""
        with pytest.raises(ValueError, match="Unsupported Arrow type: unknown_type"):
            convert_json_arrow_type_to_pyarrow(JsonArrowDataType(type='unknown_type'))
    
    def test_convert_json_arrow_schema(self):
        """Test conversion of complete JSON Arrow schema."""
        json_schema = JsonArrowSchema(
            fields=[
                JsonArrowField(name='id', type=JsonArrowDataType(type='int64'), nullable=False),
                JsonArrowField(name='name', type=JsonArrowDataType(type='utf8'), nullable=True),
                JsonArrowField(name='score', type=JsonArrowDataType(type='float64'), nullable=True),
            ],
            metadata={'created_by': 'test'}
        )
        
        pyarrow_schema = convert_json_arrow_schema_to_pyarrow(json_schema)
        
        assert len(pyarrow_schema) == 3
        assert pyarrow_schema.field('id').type == pa.int64()
        assert pyarrow_schema.field('id').nullable == False
        assert pyarrow_schema.field('name').type == pa.utf8()
        assert pyarrow_schema.field('name').nullable == True
        assert pyarrow_schema.field('score').type == pa.float64()
        assert pyarrow_schema.metadata == {b'created_by': b'test'}


class TestPyArrowToGlue:
    """Test PyArrow to Glue type conversions."""
    
    def test_convert_basic_types(self):
        """Test conversion of basic PyArrow types to Glue types."""
        assert convert_pyarrow_type_to_glue_type(pa.bool_()) == 'boolean'
        assert convert_pyarrow_type_to_glue_type(pa.int8()) == 'tinyint'
        assert convert_pyarrow_type_to_glue_type(pa.uint8()) == 'tinyint'
        assert convert_pyarrow_type_to_glue_type(pa.int16()) == 'smallint'
        assert convert_pyarrow_type_to_glue_type(pa.uint16()) == 'smallint'
        assert convert_pyarrow_type_to_glue_type(pa.int32()) == 'int'
        assert convert_pyarrow_type_to_glue_type(pa.uint32()) == 'int'
        assert convert_pyarrow_type_to_glue_type(pa.int64()) == 'bigint'
        assert convert_pyarrow_type_to_glue_type(pa.uint64()) == 'bigint'
        assert convert_pyarrow_type_to_glue_type(pa.float32()) == 'float'
        assert convert_pyarrow_type_to_glue_type(pa.float64()) == 'double'
        assert convert_pyarrow_type_to_glue_type(pa.string()) == 'string'
        assert convert_pyarrow_type_to_glue_type(pa.binary()) == 'binary'
        assert convert_pyarrow_type_to_glue_type(pa.date32()) == 'date'
        assert convert_pyarrow_type_to_glue_type(pa.date64()) == 'date'
        assert convert_pyarrow_type_to_glue_type(pa.timestamp('us')) == 'timestamp'
    
    def test_convert_decimal_type(self):
        """Test conversion of decimal type."""
        assert convert_pyarrow_type_to_glue_type(pa.decimal128(10, 2)) == 'decimal(10,2)'
        assert convert_pyarrow_type_to_glue_type(pa.decimal128(38, 10)) == 'decimal(38,10)'
    
    def test_convert_complex_types(self):
        """Test conversion of complex PyArrow types to Glue types."""
        # Test list/array type
        assert convert_pyarrow_type_to_glue_type(pa.list_(pa.int32())) == 'array<int>'
        assert convert_pyarrow_type_to_glue_type(pa.list_(pa.string())) == 'array<string>'
        
        # Test struct type
        struct_type = pa.struct([
            pa.field('a', pa.int32()),
            pa.field('b', pa.string())
        ])
        assert convert_pyarrow_type_to_glue_type(struct_type) == 'struct<a:int,b:string>'
        
        # Test map type
        map_type = pa.map_(pa.string(), pa.int32())
        assert convert_pyarrow_type_to_glue_type(map_type) == 'map<string,int>'
    
    def test_convert_nested_complex_types(self):
        """Test conversion of nested complex types."""
        # Array of structs
        struct_type = pa.struct([pa.field('x', pa.int32())])
        array_of_structs = pa.list_(struct_type)
        assert convert_pyarrow_type_to_glue_type(array_of_structs) == 'array<struct<x:int>>'
        
        # Map with complex value type
        map_with_array = pa.map_(pa.string(), pa.list_(pa.int32()))
        assert convert_pyarrow_type_to_glue_type(map_with_array) == 'map<string,array<int>>'
    
    def test_convert_schema_to_glue_columns(self):
        """Test conversion of PyArrow schema to Glue column definitions."""
        schema = pa.schema([
            pa.field('id', pa.int64()),
            pa.field('name', pa.string()),
            pa.field('scores', pa.list_(pa.float32())),
            pa.field('metadata', pa.struct([
                pa.field('created', pa.timestamp('us')),
                pa.field('version', pa.int32())
            ]))
        ])
        
        columns = convert_pyarrow_schema_to_glue_columns(schema)
        
        assert len(columns) == 4
        assert columns[0] == {'Name': 'id', 'Type': 'bigint'}
        assert columns[1] == {'Name': 'name', 'Type': 'string'}
        assert columns[2] == {'Name': 'scores', 'Type': 'array<float>'}
        assert columns[3] == {'Name': 'metadata', 'Type': 'struct<created:timestamp,version:int>'}
    
    def test_unknown_type_defaults_to_string(self):
        """Test that unknown types default to string."""
        # Create a custom type that isn't recognized
        unknown_type = pa.null()  # null type as an example
        assert convert_pyarrow_type_to_glue_type(unknown_type) == 'string'