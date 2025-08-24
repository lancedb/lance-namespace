"""
Tests for schema conversion utilities.
"""
import pytest
import pyarrow as pa

from lance_namespace.schema import (
    convert_json_arrow_schema_to_pyarrow,
    convert_json_arrow_type_to_pyarrow,
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