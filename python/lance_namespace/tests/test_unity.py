"""
Tests for Unity Catalog namespace implementation.
"""

import unittest
from unittest.mock import Mock, patch, MagicMock
import json
import io

import pyarrow as pa
import pyarrow.ipc as ipc

from lance_namespace.unity import (
    UnityNamespace,
    UnityNamespaceConfig,
    RestClient,
    RestClientException,
    LanceNamespaceException,
    SchemaInfo,
    TableInfo,
    ColumnInfo,
)
from lance_namespace_urllib3_client.models import (
    ListNamespacesRequest,
    CreateNamespaceRequest,
    DescribeNamespaceRequest,
    DropNamespaceRequest,
    ListTablesRequest,
    CreateTableRequest,
    CreateEmptyTableRequest,
    DescribeTableRequest,
    DropTableRequest,
)


class TestUnityNamespaceConfig(unittest.TestCase):
    """Test Unity namespace configuration."""
    
    def test_config_initialization(self):
        """Test configuration initialization with required properties."""
        properties = {
            "unity.endpoint": "https://unity.example.com",
            "unity.catalog": "test_catalog",
            "unity.root": "/data/lance",
            "unity.auth_token": "test_token"
        }
        
        config = UnityNamespaceConfig(properties)
        
        self.assertEqual(config.endpoint, "https://unity.example.com")
        self.assertEqual(config.catalog, "test_catalog")
        self.assertEqual(config.root, "/data/lance")
        self.assertEqual(config.auth_token, "test_token")
    
    def test_config_defaults(self):
        """Test configuration with default values."""
        properties = {
            "unity.endpoint": "https://unity.example.com"
        }
        
        config = UnityNamespaceConfig(properties)
        
        self.assertEqual(config.catalog, "unity")
        self.assertEqual(config.root, "/tmp/lance")
        self.assertIsNone(config.auth_token)
        self.assertEqual(config.connect_timeout, 10000)
        self.assertEqual(config.read_timeout, 300000)
        self.assertEqual(config.max_retries, 3)
    
    def test_config_missing_endpoint(self):
        """Test configuration fails without endpoint."""
        properties = {}
        
        with self.assertRaises(ValueError) as context:
            UnityNamespaceConfig(properties)
        
        self.assertIn("unity.endpoint", str(context.exception))
    
    def test_get_full_api_url(self):
        """Test API URL generation."""
        properties = {
            "unity.endpoint": "https://unity.example.com"
        }
        config = UnityNamespaceConfig(properties)
        
        self.assertEqual(config.get_full_api_url(), "https://unity.example.com/api/2.1")
        
        # Test with endpoint already containing /api/2.1
        properties = {
            "unity.endpoint": "https://unity.example.com/api/2.1"
        }
        config = UnityNamespaceConfig(properties)
        
        self.assertEqual(config.get_full_api_url(), "https://unity.example.com/api/2.1")


class TestRestClient(unittest.TestCase):
    """Test REST client functionality."""
    
    @patch('lance_namespace.unity.urllib3.PoolManager')
    def test_get_request(self, mock_pool_manager):
        """Test GET request."""
        mock_http = MagicMock()
        mock_pool_manager.return_value = mock_http
        
        mock_response = MagicMock()
        mock_response.status = 200
        mock_response.data = b'{"name": "test_schema"}'
        mock_http.request.return_value = mock_response
        
        client = RestClient("https://api.example.com")
        result = client.get("/schemas/test")
        
        self.assertEqual(result, {"name": "test_schema"})
        mock_http.request.assert_called_once()
    
    @patch('lance_namespace.unity.urllib3.PoolManager')
    def test_post_request(self, mock_pool_manager):
        """Test POST request."""
        mock_http = MagicMock()
        mock_pool_manager.return_value = mock_http
        
        mock_response = MagicMock()
        mock_response.status = 201
        mock_response.data = b'{"id": "123"}'
        mock_http.request.return_value = mock_response
        
        client = RestClient("https://api.example.com")
        result = client.post("/schemas", {"name": "test"})
        
        self.assertEqual(result, {"id": "123"})
        mock_http.request.assert_called_once()
    
    @patch('lance_namespace.unity.urllib3.PoolManager')
    def test_delete_request(self, mock_pool_manager):
        """Test DELETE request."""
        mock_http = MagicMock()
        mock_pool_manager.return_value = mock_http
        
        mock_response = MagicMock()
        mock_response.status = 204
        mock_response.data = b''
        mock_http.request.return_value = mock_response
        
        client = RestClient("https://api.example.com")
        client.delete("/schemas/test")
        
        mock_http.request.assert_called_once()
    
    @patch('lance_namespace.unity.urllib3.PoolManager')
    def test_error_response(self, mock_pool_manager):
        """Test error response handling."""
        mock_http = MagicMock()
        mock_pool_manager.return_value = mock_http
        
        mock_response = MagicMock()
        mock_response.status = 404
        mock_response.data = b'{"error": "Not found"}'
        mock_http.request.return_value = mock_response
        
        client = RestClient("https://api.example.com")
        
        with self.assertRaises(RestClientException) as context:
            client.get("/schemas/test")
        
        self.assertEqual(context.exception.status_code, 404)
        self.assertIn("Not found", context.exception.response_body)


class TestUnityNamespace(unittest.TestCase):
    """Test Unity namespace implementation."""
    
    def setUp(self):
        """Set up test fixtures."""
        self.properties = {
            "unity.endpoint": "https://unity.example.com",
            "unity.catalog": "test_catalog",
            "unity.root": "/data/lance"
        }
    
    @patch('lance_namespace.unity.RestClient')
    def test_list_namespaces_top_level(self, mock_rest_client_class):
        """Test listing top-level namespaces (catalogs)."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        namespace = UnityNamespace(**self.properties)
        
        request = ListNamespacesRequest()
        request.id = []
        
        response = namespace.list_namespaces(request)
        
        self.assertEqual(response.namespaces, ["test_catalog"])
        mock_client.get.assert_not_called()
    
    @patch('lance_namespace.unity.RestClient')
    def test_list_namespaces_schemas(self, mock_rest_client_class):
        """Test listing schemas in a catalog."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_client.get.return_value = {
            "schemas": [
                {"name": "schema1"},
                {"name": "schema2"}
            ]
        }
        
        namespace = UnityNamespace(**self.properties)
        
        request = ListNamespacesRequest()
        request.id = ["test_catalog"]
        
        response = namespace.list_namespaces(request)
        
        self.assertEqual(sorted(response.namespaces), ["schema1", "schema2"])
        mock_client.get.assert_called_once_with(
            '/schemas',
            params={'catalog_name': 'test_catalog'}
        )
    
    @patch('lance_namespace.unity.RestClient')
    def test_create_namespace(self, mock_rest_client_class):
        """Test creating a namespace."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_schema_info = SchemaInfo(
            name="test_schema",
            catalog_name="test_catalog",
            properties={"key": "value"}
        )
        mock_client.post.return_value = mock_schema_info
        
        namespace = UnityNamespace(**self.properties)
        
        request = CreateNamespaceRequest()
        request.id = ["test_catalog", "test_schema"]
        request.properties = {"key": "value"}
        
        response = namespace.create_namespace(request)
        
        self.assertEqual(response.properties, {"key": "value"})
    
    @patch('lance_namespace.unity.RestClient')
    def test_describe_namespace(self, mock_rest_client_class):
        """Test describing a namespace."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_schema_info = SchemaInfo(
            name="test_schema",
            catalog_name="test_catalog",
            properties={"key": "value"}
        )
        mock_client.get.return_value = mock_schema_info
        
        namespace = UnityNamespace(**self.properties)
        
        request = DescribeNamespaceRequest()
        request.id = ["test_catalog", "test_schema"]
        
        response = namespace.describe_namespace(request)
        
        self.assertEqual(response.properties, {"key": "value"})
        mock_client.get.assert_called_once_with(
            "/schemas/test_catalog.test_schema",
            response_class=SchemaInfo
        )
    
    @patch('lance_namespace.unity.RestClient')
    def test_drop_namespace(self, mock_rest_client_class):
        """Test dropping a namespace."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        namespace = UnityNamespace(**self.properties)
        
        request = DropNamespaceRequest()
        request.id = ["test_catalog", "test_schema"]
        
        response = namespace.drop_namespace(request)
        
        self.assertIsNotNone(response)
        mock_client.delete.assert_called_once_with(
            "/schemas/test_catalog.test_schema",
            params={}
        )
    
    @patch('lance_namespace.unity.RestClient')
    def test_list_tables(self, mock_rest_client_class):
        """Test listing tables in a namespace."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_client.get.return_value = {
            "tables": [
                {"name": "table1", "properties": {"table_type": "lance"}},
                {"name": "table2", "properties": {"table_type": "delta"}},
                {"name": "table3", "properties": {"table_type": "lance"}}
            ]
        }
        
        namespace = UnityNamespace(**self.properties)
        
        request = ListTablesRequest()
        request.id = ["test_catalog", "test_schema"]
        
        response = namespace.list_tables(request)
        
        # Should only return Lance tables
        self.assertEqual(sorted(response.tables), ["table1", "table3"])
    
    @patch('lance_namespace.unity.lance')
    @patch('lance_namespace.unity.RestClient')
    def test_create_table(self, mock_rest_client_class, mock_lance):
        """Test creating a table."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        # Create test Arrow schema and IPC data
        arrow_schema = pa.schema([
            pa.field("id", pa.int64()),
            pa.field("name", pa.string())
        ])
        
        # Create IPC stream data
        buf = io.BytesIO()
        writer = ipc.new_stream(buf, arrow_schema)
        writer.close()
        ipc_data = buf.getvalue()
        
        mock_table_info = TableInfo(
            name="test_table",
            catalog_name="test_catalog",
            schema_name="test_schema",
            table_type="EXTERNAL",
            data_source_format="TEXT",
            columns=[],
            storage_location="/data/lance/test_catalog/test_schema/test_table",
            properties={"table_type": "lance", "version": "0"}
        )
        mock_client.post.return_value = mock_table_info
        
        namespace = UnityNamespace(**self.properties)
        
        request = CreateTableRequest()
        request.id = ["test_catalog", "test_schema", "test_table"]
        request.properties = {"custom": "property"}
        
        response = namespace.create_table(request, ipc_data)
        
        self.assertEqual(response.location, "/data/lance/test_catalog/test_schema/test_table")
        self.assertEqual(response.version, 1)
        mock_lance.write_dataset.assert_called_once()
    
    @patch('lance_namespace.unity.RestClient')
    def test_create_empty_table(self, mock_rest_client_class):
        """Test creating an empty table."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_table_info = TableInfo(
            name="test_table",
            catalog_name="test_catalog",
            schema_name="test_schema",
            table_type="EXTERNAL",
            data_source_format="TEXT",
            columns=[],
            storage_location="/data/lance/test_catalog/test_schema/test_table",
            properties={"table_type": "lance"}
        )
        mock_client.post.return_value = mock_table_info
        
        namespace = UnityNamespace(**self.properties)
        
        request = CreateEmptyTableRequest()
        request.id = ["test_catalog", "test_schema", "test_table"]
        
        response = namespace.create_empty_table(request)
        
        self.assertEqual(response.location, "/data/lance/test_catalog/test_schema/test_table")
    
    @patch('lance_namespace.unity.lance')
    @patch('lance_namespace.unity.RestClient')
    def test_describe_table(self, mock_rest_client_class, mock_lance):
        """Test describing a table."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_table_info = TableInfo(
            name="test_table",
            catalog_name="test_catalog",
            schema_name="test_schema",
            table_type="EXTERNAL",
            data_source_format="TEXT",
            columns=[],
            storage_location="/data/lance/test_catalog/test_schema/test_table",
            properties={"table_type": "lance"}
        )
        mock_client.get.return_value = mock_table_info
        
        # Mock Lance dataset
        mock_dataset = MagicMock()
        mock_dataset.schema = pa.schema([pa.field("id", pa.int64())])
        mock_lance.dataset.return_value = mock_dataset
        
        namespace = UnityNamespace(**self.properties)
        
        request = DescribeTableRequest()
        request.id = ["test_catalog", "test_schema", "test_table"]
        
        response = namespace.describe_table(request)
        
        self.assertEqual(response.location, "/data/lance/test_catalog/test_schema/test_table")
        self.assertEqual(response.properties, {"table_type": "lance"})
    
    @patch('lance_namespace.unity.os')
    @patch('lance_namespace.unity.shutil')
    @patch('lance_namespace.unity.RestClient')
    def test_drop_table(self, mock_rest_client_class, mock_shutil, mock_os):
        """Test dropping a table."""
        mock_client = MagicMock()
        mock_rest_client_class.return_value = mock_client
        
        mock_table_info = TableInfo(
            name="test_table",
            catalog_name="test_catalog",
            schema_name="test_schema",
            table_type="EXTERNAL",
            data_source_format="TEXT",
            columns=[],
            storage_location="/data/lance/test_catalog/test_schema/test_table",
            properties={"table_type": "lance"}
        )
        mock_client.get.return_value = mock_table_info
        mock_os.path.exists.return_value = True
        
        namespace = UnityNamespace(**self.properties)
        
        request = DropTableRequest()
        request.id = ["test_catalog", "test_schema", "test_table"]
        
        response = namespace.drop_table(request)
        
        self.assertEqual(response.location, "/data/lance/test_catalog/test_schema/test_table")
        mock_client.delete.assert_called_once_with("/tables/test_catalog.test_schema.test_table")
        mock_shutil.rmtree.assert_called_once_with("/data/lance/test_catalog/test_schema/test_table")
    
    def test_arrow_type_conversion(self):
        """Test Arrow type to Unity type conversion."""
        namespace = UnityNamespace(**self.properties)
        
        # Test various Arrow types
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.string()), "STRING")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.int32()), "INT")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.int64()), "BIGINT")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.float32()), "FLOAT")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.float64()), "DOUBLE")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.bool_()), "BOOLEAN")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.date32()), "DATE")
        self.assertEqual(namespace._convert_arrow_type_to_unity_type(pa.timestamp('us')), "TIMESTAMP")
    
    def test_arrow_type_to_json_conversion(self):
        """Test Arrow type to Unity JSON type conversion."""
        namespace = UnityNamespace(**self.properties)
        
        # Test various Arrow types
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.string()), '{"type":"string"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.int32()), '{"type":"integer"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.int64()), '{"type":"long"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.float32()), '{"type":"float"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.float64()), '{"type":"double"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.bool_()), '{"type":"boolean"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.date32()), '{"type":"date"}')
        self.assertEqual(namespace._convert_arrow_type_to_unity_type_json(pa.timestamp('us')), '{"type":"timestamp"}')


if __name__ == '__main__':
    unittest.main()