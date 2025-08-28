"""
Tests for Lance Glue Namespace implementation.
"""
import pytest
from unittest.mock import Mock, MagicMock, patch
import pyarrow as pa

from lance_namespace.glue import GlueNamespace, GlueNamespaceConfig
from lance_namespace_urllib3_client.models import (
    ListNamespacesRequest,
    CreateNamespaceRequest,
    DescribeNamespaceRequest,
    DropNamespaceRequest,
    NamespaceExistsRequest,
    ListTablesRequest,
    CreateTableRequest,
    DropTableRequest,
    DescribeTableRequest,
    RegisterTableRequest,
    DeregisterTableRequest,
    TableExistsRequest,
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
)


@pytest.fixture
def mock_boto3():
    """Mock boto3 module."""
    with patch('lance_namespace.glue.boto3') as mock:
        mock.Session.return_value.client.return_value = MagicMock()
        yield mock


@pytest.fixture
def mock_lance():
    """Mock lance module."""
    with patch('lance_namespace.glue.lance') as mock:
        yield mock


@pytest.fixture
def glue_namespace(mock_boto3, mock_lance):
    """Create a GlueNamespace instance with mocked dependencies."""
    properties = {
        'region': 'us-east-1',
        'catalog_id': '123456789012'
    }
    namespace = GlueNamespace(**properties)
    return namespace


class TestGlueNamespaceConfig:
    """Test GlueNamespaceConfig class."""
    
    def test_config_initialization(self):
        """Test configuration initialization."""
        properties = {
            'catalog_id': '123456789012',
            'endpoint': 'https://glue.example.com',
            'region': 'us-west-2',
            'access_key_id': 'AKIAEXAMPLE',
            'secret_access_key': 'secret',
            'session_token': 'token',
            'profile_name': 'default',
            'max_retries': '5',
            'retry_mode': 'adaptive',
            'root': 's3://bucket/path',
            'storage.key1': 'value1',
            'storage.key2': 'value2',
        }
        
        config = GlueNamespaceConfig(properties)
        
        assert config.catalog_id == '123456789012'
        assert config.endpoint == 'https://glue.example.com'
        assert config.region == 'us-west-2'
        assert config.access_key_id == 'AKIAEXAMPLE'
        assert config.secret_access_key == 'secret'
        assert config.session_token == 'token'
        assert config.profile_name == 'default'
        assert config.max_retries == 5
        assert config.retry_mode == 'adaptive'
        assert config.root == 's3://bucket/path'
        assert config.storage_options == {'key1': 'value1', 'key2': 'value2'}
    
    def test_config_with_empty_properties(self):
        """Test configuration with empty properties."""
        config = GlueNamespaceConfig({})
        
        assert config.catalog_id is None
        assert config.endpoint is None
        assert config.region is None
        assert config.max_retries is None
        assert config.root is None
        assert config.storage_options == {}


class TestGlueNamespace:
    """Test GlueNamespace class."""
    
    def test_initialization_without_boto3(self):
        """Test that initialization fails without boto3."""
        with patch('lance_namespace.glue.HAS_BOTO3', False):
            with pytest.raises(ImportError, match="boto3 is required"):
                GlueNamespace()
    
    def test_list_namespaces(self, glue_namespace):
        """Test listing namespaces."""
        glue_namespace.glue.get_databases.return_value = {
            'DatabaseList': [
                {'Name': 'db1'},
                {'Name': 'db2'},
            ]
        }
        
        request = ListNamespacesRequest()
        response = glue_namespace.list_namespaces(request)
        
        assert response.namespaces == ['db1', 'db2']
        glue_namespace.glue.get_databases.assert_called_once()
    
    def test_list_namespaces_with_pagination(self, glue_namespace):
        """Test listing namespaces with pagination."""
        glue_namespace.glue.get_databases.side_effect = [
            {
                'DatabaseList': [{'Name': 'db1'}],
                'NextToken': 'token1'
            },
            {
                'DatabaseList': [{'Name': 'db2'}],
            }
        ]
        
        request = ListNamespacesRequest()
        response = glue_namespace.list_namespaces(request)
        
        assert response.namespaces == ['db1', 'db2']
        assert glue_namespace.glue.get_databases.call_count == 2
    
    def test_list_namespaces_hierarchical_not_supported(self, glue_namespace):
        """Test that hierarchical namespaces are not supported."""
        request = ListNamespacesRequest(id=['parent'])
        response = glue_namespace.list_namespaces(request)
        
        assert response.namespaces == []
        glue_namespace.glue.get_databases.assert_not_called()
    
    def test_list_namespaces_root(self, glue_namespace):
        """Test listing namespaces at root level."""
        glue_namespace.glue.get_databases.return_value = {
            'DatabaseList': [
                {'Name': 'db1'},
                {'Name': 'db2'},
            ]
        }
        
        # Empty id means root namespace
        request = ListNamespacesRequest(id=[])
        response = glue_namespace.list_namespaces(request)
        
        assert response.namespaces == ['db1', 'db2']
        glue_namespace.glue.get_databases.assert_called_once()
    
    def test_create_namespace(self, glue_namespace):
        """Test creating a namespace."""
        request = CreateNamespaceRequest(
            id=['test_db'],
            properties={'description': 'Test database', 'location': 's3://bucket/path'}
        )
        
        response = glue_namespace.create_namespace(request)
        
        glue_namespace.glue.create_database.assert_called_once()
        call_args = glue_namespace.glue.create_database.call_args
        assert call_args[1]['DatabaseInput']['Name'] == 'test_db'
        assert call_args[1]['DatabaseInput']['Description'] == 'Test database'
        assert call_args[1]['DatabaseInput']['LocationUri'] == 's3://bucket/path'
    
    def test_create_namespace_root(self, glue_namespace):
        """Test creating root namespace fails."""
        request = CreateNamespaceRequest(id=[])
        
        with pytest.raises(RuntimeError, match="Root namespace already exists"):
            glue_namespace.create_namespace(request)
        
        glue_namespace.glue.create_database.assert_not_called()
    
    def test_create_namespace_already_exists(self, glue_namespace):
        """Test creating a namespace that already exists."""
        # Create a custom exception with the right name
        class AlreadyExistsException(Exception):
            pass
        
        glue_namespace.glue.exceptions.AlreadyExistsException = AlreadyExistsException
        glue_namespace.glue.create_database.side_effect = AlreadyExistsException("Already exists")
        
        request = CreateNamespaceRequest(id=['test_db'])
        
        with pytest.raises(RuntimeError, match="Namespace already exists"):
            glue_namespace.create_namespace(request)
    
    def test_describe_namespace_root(self, glue_namespace):
        """Test describing root namespace."""
        request = DescribeNamespaceRequest(id=[])
        response = glue_namespace.describe_namespace(request)
        
        assert response.properties['description'] == 'Root Glue catalog namespace'
        glue_namespace.glue.get_database.assert_not_called()
    
    def test_describe_namespace(self, glue_namespace):
        """Test describing a namespace."""
        glue_namespace.glue.get_database.return_value = {
            'Database': {
                'Name': 'test_db',
                'Description': 'Test database',
                'LocationUri': 's3://bucket/path',
                'Parameters': {'key': 'value'}
            }
        }
        
        request = DescribeNamespaceRequest(id=['test_db'])
        response = glue_namespace.describe_namespace(request)
        
        assert response.properties['description'] == 'Test database'
        assert response.properties['location'] == 's3://bucket/path'
        assert response.properties['key'] == 'value'
    
    def test_drop_namespace_root(self, glue_namespace):
        """Test dropping root namespace fails."""
        request = DropNamespaceRequest(id=[])
        
        with pytest.raises(RuntimeError, match="Cannot drop root namespace"):
            glue_namespace.drop_namespace(request)
        
        glue_namespace.glue.get_tables.assert_not_called()
        glue_namespace.glue.delete_database.assert_not_called()
    
    def test_drop_namespace(self, glue_namespace):
        """Test dropping an empty namespace."""
        glue_namespace.glue.get_tables.return_value = {'TableList': []}
        
        request = DropNamespaceRequest(id=['test_db'])
        response = glue_namespace.drop_namespace(request)
        
        glue_namespace.glue.get_tables.assert_called_once_with(DatabaseName='test_db')
        glue_namespace.glue.delete_database.assert_called_once_with(Name='test_db')
    
    def test_drop_namespace_not_empty(self, glue_namespace):
        """Test dropping a non-empty namespace."""
        glue_namespace.glue.get_tables.return_value = {
            'TableList': [{'Name': 'table1'}]
        }
        
        request = DropNamespaceRequest(id=['test_db'])
        
        with pytest.raises(RuntimeError, match="Cannot drop non-empty namespace"):
            glue_namespace.drop_namespace(request)
    
    def test_namespace_exists_root(self, glue_namespace):
        """Test checking if root namespace exists."""
        request = NamespaceExistsRequest(id=[])
        glue_namespace.namespace_exists(request)  # Should not raise
        
        glue_namespace.glue.get_database.assert_not_called()
    
    def test_namespace_exists(self, glue_namespace):
        """Test checking if a namespace exists."""
        glue_namespace.glue.get_database.return_value = {'Database': {'Name': 'test_db'}}
        
        request = NamespaceExistsRequest(id=['test_db'])
        glue_namespace.namespace_exists(request)  # Should not raise
        
        glue_namespace.glue.get_database.assert_called_once_with(Name='test_db')
    
    def test_namespace_not_exists(self, glue_namespace):
        """Test checking if a namespace doesn't exist."""
        # Create a custom exception with the right name
        class EntityNotFoundException(Exception):
            pass
        
        glue_namespace.glue.exceptions.EntityNotFoundException = EntityNotFoundException
        glue_namespace.glue.get_database.side_effect = EntityNotFoundException("Not found")
        
        request = NamespaceExistsRequest(id=['test_db'])
        
        with pytest.raises(RuntimeError, match="Namespace does not exist"):
            glue_namespace.namespace_exists(request)
    
    def test_list_tables_root(self, glue_namespace):
        """Test listing tables at root namespace returns empty."""
        request = ListTablesRequest(id=[])
        response = glue_namespace.list_tables(request)
        
        assert response.tables == []
        glue_namespace.glue.get_tables.assert_not_called()
    
    def test_list_tables(self, glue_namespace):
        """Test listing tables in a namespace."""
        glue_namespace.glue.get_tables.return_value = {
            'TableList': [
                {'Name': 'table1', 'Parameters': {'table_type': 'LANCE'}},
                {'Name': 'table2', 'Parameters': {'table_type': 'LANCE'}},
                {'Name': 'table3', 'Parameters': {'table_type': 'HIVE'}},  # Not a Lance table
            ]
        }
        
        request = ListTablesRequest(id=['test_db'])
        response = glue_namespace.list_tables(request)
        
        assert response.tables == ['table1', 'table2']
        glue_namespace.glue.get_tables.assert_called_once_with(DatabaseName='test_db')
    
    def test_create_table(self, glue_namespace, mock_lance):
        """Test creating a table."""
        glue_namespace.glue.get_database.return_value = {
            'Database': {'LocationUri': 's3://bucket/db'}
        }
        
        schema = JsonArrowSchema(
            fields=[
                JsonArrowField(name='id', type=JsonArrowDataType(type='int64'), nullable=False),
                JsonArrowField(name='name', type=JsonArrowDataType(type='utf8'), nullable=True),
            ]
        )
        
        request = CreateTableRequest(
            id=['test_db', 'test_table'],
            var_schema=schema
        )
        
        # Create mock Arrow IPC stream data
        arrow_schema = pa.schema([
            pa.field('id', pa.int64(), nullable=False),
            pa.field('name', pa.string(), nullable=True),
        ])
        table = pa.table({'id': [1, 2], 'name': ['Alice', 'Bob']}, schema=arrow_schema)
        
        # Convert to IPC stream bytes
        with pa.BufferOutputStream() as sink:
            with pa.ipc.new_stream(sink, arrow_schema) as writer:
                writer.write_table(table)
            request_data = sink.getvalue().to_pybytes()
        
        response = glue_namespace.create_table(request, request_data)
        
        assert response.location == 's3://bucket/db/test_table.lance'
        assert response.version == 1
        
        # Verify Lance dataset was written
        mock_lance.write_dataset.assert_called_once()
        
        # Verify Glue table was created
        glue_namespace.glue.create_table.assert_called_once()
        call_args = glue_namespace.glue.create_table.call_args
        assert call_args[1]['DatabaseName'] == 'test_db'
        assert call_args[1]['TableInput']['Name'] == 'test_table'
        assert call_args[1]['TableInput']['Parameters']['table_type'] == 'LANCE'
    
    def test_create_table_empty_data(self, glue_namespace, mock_lance):
        """Test creating a table with empty data."""
        import pyarrow as pa
        import io
        
        glue_namespace.glue.get_database.return_value = {
            'Database': {'LocationUri': 's3://bucket/db'}
        }
        
        # Create an empty Arrow table with schema
        arrow_schema = pa.schema([
            pa.field('id', pa.int64(), nullable=False),
            pa.field('name', pa.utf8(), nullable=True),
        ])
        # Create empty arrays for each field
        empty_arrays = [
            pa.array([], type=pa.int64()),
            pa.array([], type=pa.utf8())
        ]
        empty_table = pa.table(empty_arrays, schema=arrow_schema)
        
        # Convert to Arrow IPC stream
        buffer = io.BytesIO()
        with pa.ipc.RecordBatchStreamWriter(buffer, arrow_schema) as writer:
            writer.write_table(empty_table)
        ipc_data = buffer.getvalue()
        
        request = CreateTableRequest(
            id=['test_db', 'test_table']
        )
        
        # Test with empty IPC stream
        response = glue_namespace.create_table(request, ipc_data)
        
        assert response.location == 's3://bucket/db/test_table.lance'
        assert response.version == 1
        
        # Verify Lance dataset was written with empty table
        mock_lance.write_dataset.assert_called_once()
        written_table = mock_lance.write_dataset.call_args[0][0]
        assert written_table.num_rows == 0
        assert len(written_table.schema) == 2  # id and name columns
    
    def test_drop_table(self, glue_namespace, mock_lance):
        """Test dropping a table."""
        # Mock the Glue get_table response
        glue_namespace.glue.get_table.return_value = {
            'Table': {
                'Name': 'test_table',
                'Parameters': {'table_type': 'LANCE'},
                'StorageDescriptor': {'Location': 's3://bucket/table.lance'}
            }
        }
        
        # Mock the Lance dataset
        mock_dataset = mock_lance.dataset.return_value
        
        request = DropTableRequest(id=['test_db', 'test_table'])
        response = glue_namespace.drop_table(request)
        
        # Verify Lance dataset was deleted first
        mock_lance.dataset.assert_called_once_with(
            's3://bucket/table.lance',
            storage_options={}
        )
        mock_dataset.delete.assert_called_once()
        
        # Then verify Glue table was deleted
        glue_namespace.glue.delete_table.assert_called_once_with(
            DatabaseName='test_db',
            Name='test_table'
        )
    
    def test_deregister_table(self, glue_namespace, mock_lance):
        """Test deregistering a table (only removes from Glue, keeps Lance dataset)."""
        request = DeregisterTableRequest(id=['test_db', 'test_table'])
        response = glue_namespace.deregister_table(request)
        
        # Verify only Glue table was deleted (no Lance operations)
        mock_lance.dataset.assert_not_called()
        glue_namespace.glue.delete_table.assert_called_once_with(
            DatabaseName='test_db',
            Name='test_table'
        )
    
    def test_describe_table(self, glue_namespace):
        """Test describing a table."""
        glue_namespace.glue.get_table.return_value = {
            'Table': {
                'Name': 'test_table',
                'Parameters': {'table_type': 'LANCE'},
                'StorageDescriptor': {'Location': 's3://bucket/table.lance'}
            }
        }
        
        request = DescribeTableRequest(id=['test_db', 'test_table'])
        response = glue_namespace.describe_table(request)
        
        assert response.location == 's3://bucket/table.lance'
    
    def test_describe_table_not_lance(self, glue_namespace):
        """Test describing a non-Lance table."""
        glue_namespace.glue.get_table.return_value = {
            'Table': {
                'Name': 'test_table',
                'Parameters': {'table_type': 'HIVE'},
                'StorageDescriptor': {'Location': 's3://bucket/table'}
            }
        }
        
        request = DescribeTableRequest(id=['test_db', 'test_table'])
        
        with pytest.raises(RuntimeError, match="Table is not a Lance table"):
            glue_namespace.describe_table(request)
    
    def test_register_table(self, glue_namespace, mock_lance):
        """Test registering an existing table."""
        # Mock Lance dataset
        mock_dataset = MagicMock()
        mock_dataset.schema = pa.schema([
            pa.field('id', pa.int64()),
            pa.field('name', pa.string()),
        ])
        mock_lance.dataset.return_value = mock_dataset
        
        request = RegisterTableRequest(
            id=['test_db', 'test_table'],
            location='s3://bucket/existing_table.lance'
        )
        
        response = glue_namespace.register_table(request)
        
        assert response.location == 's3://bucket/existing_table.lance'
        
        # Verify Lance dataset was read
        mock_lance.dataset.assert_called_once_with(
            's3://bucket/existing_table.lance',
            storage_options={}
        )
        
        # Verify Glue table was created
        glue_namespace.glue.create_table.assert_called_once()
        call_args = glue_namespace.glue.create_table.call_args
        assert call_args[1]['DatabaseName'] == 'test_db'
        assert call_args[1]['TableInput']['Name'] == 'test_table'
        assert call_args[1]['TableInput']['Parameters']['table_type'] == 'LANCE'
    
    def test_table_exists(self, glue_namespace):
        """Test checking if a table exists."""
        glue_namespace.glue.get_table.return_value = {
            'Table': {
                'Name': 'test_table',
                'Parameters': {'table_type': 'LANCE'}
            }
        }
        
        request = TableExistsRequest(id=['test_db', 'test_table'])
        glue_namespace.table_exists(request)  # Should not raise
        
        glue_namespace.glue.get_table.assert_called_once_with(
            DatabaseName='test_db',
            Name='test_table'
        )
    
    def test_table_not_exists(self, glue_namespace):
        """Test checking if a table doesn't exist."""
        # Create a custom exception with the right name
        class EntityNotFoundException(Exception):
            pass
        
        glue_namespace.glue.exceptions.EntityNotFoundException = EntityNotFoundException
        glue_namespace.glue.get_table.side_effect = EntityNotFoundException("Not found")
        
        request = TableExistsRequest(id=['test_db', 'test_table'])
        
        with pytest.raises(RuntimeError, match="Table does not exist"):
            glue_namespace.table_exists(request)
    
    def test_parse_table_identifier(self, glue_namespace):
        """Test parsing table identifier."""
        db, table = glue_namespace._parse_table_identifier(['db', 'table'])
        assert db == 'db'
        assert table == 'table'
        
        with pytest.raises(ValueError, match="exactly 2 parts"):
            glue_namespace._parse_table_identifier(['db'])
        
        with pytest.raises(ValueError, match="exactly 2 parts"):
            glue_namespace._parse_table_identifier(['db', 'schema', 'table'])
    
    def test_is_lance_table(self, glue_namespace):
        """Test checking if a Glue table is a Lance table."""
        lance_table = {'Parameters': {'table_type': 'LANCE'}}
        assert glue_namespace._is_lance_table(lance_table) is True
        
        lance_table_lower = {'Parameters': {'table_type': 'lance'}}
        assert glue_namespace._is_lance_table(lance_table_lower) is True
        
        hive_table = {'Parameters': {'table_type': 'HIVE'}}
        assert glue_namespace._is_lance_table(hive_table) is False
        
        no_params = {}
        assert glue_namespace._is_lance_table(no_params) is False
    
    def test_pyarrow_type_conversions(self, glue_namespace):
        """Test PyArrow to Glue type conversions."""
        # Test basic types
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.bool_()) == 'boolean'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.int32()) == 'int'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.int64()) == 'bigint'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.float32()) == 'float'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.float64()) == 'double'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.string()) == 'string'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.binary()) == 'binary'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.date32()) == 'date'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.timestamp('us')) == 'timestamp'
        
        # Test complex types
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.list_(pa.int32())) == 'array<int>'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(
            pa.struct([pa.field('a', pa.int32()), pa.field('b', pa.string())])
        ) == 'struct<a:int,b:string>'
        assert glue_namespace._convert_pyarrow_type_to_glue_type(
            pa.map_(pa.string(), pa.int32())
        ) == 'map<string,int>'
        
        # Test decimal
        assert glue_namespace._convert_pyarrow_type_to_glue_type(pa.decimal128(10, 2)) == 'decimal(10,2)'
    
    def test_pyarrow_schema_to_glue_columns(self, glue_namespace):
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
        
        columns = glue_namespace._convert_pyarrow_schema_to_glue_columns(schema)
        
        assert len(columns) == 4
        assert columns[0] == {'Name': 'id', 'Type': 'bigint'}
        assert columns[1] == {'Name': 'name', 'Type': 'string'}
        assert columns[2] == {'Name': 'scores', 'Type': 'array<float>'}
        assert columns[3] == {'Name': 'metadata', 'Type': 'struct<created:timestamp,version:int>'}
    
    def test_pickle_support(self, mock_boto3):
        """Test that GlueNamespace can be pickled and unpickled for Ray compatibility."""
        import pickle
        
        # Create a GlueNamespace instance
        properties = {
            'region': 'us-east-1',
            'catalog_id': '123456789012',
            'endpoint': 'https://glue.example.com',
            'storage.access_key_id': 'test-key',
            'storage.secret_access_key': 'test-secret'
        }
        namespace = GlueNamespace(**properties)
        
        # Test pickling
        pickled = pickle.dumps(namespace)
        assert pickled is not None
        
        # Test unpickling
        restored = pickle.loads(pickled)
        assert isinstance(restored, GlueNamespace)
        
        # Verify configuration is preserved
        assert restored.config.region == 'us-east-1'
        assert restored.config.catalog_id == '123456789012'
        assert restored.config.endpoint == 'https://glue.example.com'
        assert restored.config.storage_options['access_key_id'] == 'test-key'
        assert restored.config.storage_options['secret_access_key'] == 'test-secret'
        
        # Verify glue client is None after unpickling (will be lazily initialized)
        assert restored._glue is None
        
        # Test that glue client can be re-initialized after unpickling
        # This will create a new mock client when accessed
        client = restored.glue
        assert client is not None
        assert restored._glue is not None
    
