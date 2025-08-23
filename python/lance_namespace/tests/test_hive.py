"""
Tests for Lance Hive2 Namespace implementation.
"""
import os
import pytest
import tempfile
from unittest.mock import Mock, MagicMock, patch
import pyarrow as pa

from lance_namespace import connect
from lance_namespace_urllib3_client.models import (
    ListNamespacesRequest,
    DescribeNamespaceRequest,
    CreateNamespaceRequest,
    DropNamespaceRequest,
    NamespaceExistsRequest,
    ListTablesRequest,
    DescribeTableRequest,
    RegisterTableRequest,
    DeregisterTableRequest,
    TableExistsRequest,
    DropTableRequest,
    CreateTableRequest,
)


@pytest.fixture
def mock_hive_client():
    """Create a mock Hive client."""
    with patch("lance_namespace.hive.HIVE_AVAILABLE", True):
        with patch("lance_namespace.hive.HiveMetastoreClient") as mock_client_class:
            mock_client = MagicMock()
            mock_client_class.return_value = mock_client
            yield mock_client


@pytest.fixture
def hive_namespace(mock_hive_client):
    """Create a Hive2Namespace instance with mocked client."""
    with patch("lance_namespace.hive.HIVE_AVAILABLE", True):
        namespace = connect("hive2", {
            "uri": "thrift://localhost:9083",
            "warehouse": "/tmp/warehouse"
        })
        namespace._client = mock_hive_client
        return namespace


class TestHive2Namespace:
    """Test cases for Hive2Namespace."""
    
    def test_initialization(self):
        """Test namespace initialization."""
        with patch("lance_namespace.hive.HIVE_AVAILABLE", True):
            with patch("lance_namespace.hive.HiveMetastoreClient") as mock_client:
                namespace = connect("hive2", {
                    "uri": "thrift://localhost:9083",
                    "warehouse": "/tmp/warehouse",
                    "ugi": "user:group1,group2"
                })
                
                assert namespace.uri == "thrift://localhost:9083"
                assert namespace.warehouse == "/tmp/warehouse"
                assert namespace.ugi == "user:group1,group2"
                mock_client.assert_called_once_with("thrift://localhost:9083", "user:group1,group2")
    
    def test_initialization_without_hive_deps(self):
        """Test that initialization fails gracefully without Hive dependencies."""
        with patch("lance_namespace.hive.HIVE_AVAILABLE", False):
            with pytest.raises(ValueError, match="Hive dependencies not installed"):
                connect("hive2", {"uri": "thrift://localhost:9083"})
    
    def test_list_namespaces(self, hive_namespace, mock_hive_client):
        """Test listing namespaces (databases)."""
        mock_client_instance = MagicMock()
        mock_client_instance.get_all_databases.return_value = ["default", "test_db", "prod_db"]
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = ListNamespacesRequest()
        response = hive_namespace.list_namespaces(request)
        
        assert response.namespaces == ["test_db", "prod_db"]
        mock_client_instance.get_all_databases.assert_called_once()
    
    def test_describe_namespace(self, hive_namespace, mock_hive_client):
        """Test describing a namespace (database)."""
        mock_database = MagicMock()
        mock_database.description = "Test database"
        mock_database.ownerName = "test_user"
        mock_database.locationUri = "/tmp/warehouse/test_db.db"
        mock_database.parameters = {"key": "value"}
        
        mock_client_instance = MagicMock()
        mock_client_instance.get_database.return_value = mock_database
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = DescribeNamespaceRequest(id=["test_db"])
        response = hive_namespace.describe_namespace(request)
        
        # Response doesn't include id, only properties
        assert response.properties["comment"] == "Test database"
        assert response.properties["owner"] == "test_user"
        assert response.properties["location"] == "/tmp/warehouse/test_db.db"
        assert response.properties["key"] == "value"
        mock_client_instance.get_database.assert_called_once_with("test_db")
    
    def test_create_namespace(self, hive_namespace, mock_hive_client):
        """Test creating a namespace (database)."""
        mock_client_instance = MagicMock()
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        # Mock HiveDatabase class
        with patch("lance_namespace.hive.HiveDatabase") as mock_hive_db_class:
            mock_hive_db = MagicMock()
            mock_hive_db_class.return_value = mock_hive_db
            
            request = CreateNamespaceRequest(
                id=["test_db"],
                properties={
                    "comment": "Test database",
                    "owner": "test_user",
                    "location": "/custom/location"
                }
            )
            response = hive_namespace.create_namespace(request)
            
            # Response doesn't include id
            mock_client_instance.create_database.assert_called_once_with(mock_hive_db)
            
            # Verify the database object properties were set
            assert mock_hive_db.name == "test_db"
            assert mock_hive_db.description == "Test database"
            assert mock_hive_db.ownerName == "test_user"
            assert mock_hive_db.locationUri == "/custom/location"
    
    def test_drop_namespace(self, hive_namespace, mock_hive_client):
        """Test dropping a namespace (database)."""
        mock_client_instance = MagicMock()
        mock_client_instance.get_all_tables.return_value = []
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = DropNamespaceRequest(id=["test_db"])
        response = hive_namespace.drop_namespace(request)
        
        mock_client_instance.get_all_tables.assert_called_once_with("test_db")
        mock_client_instance.drop_database.assert_called_once_with(
            "test_db", deleteData=True, cascade=False
        )
    
    def test_drop_namespace_cascade(self, hive_namespace, mock_hive_client):
        """Test dropping a non-empty namespace with cascade."""
        mock_client_instance = MagicMock()
        mock_client_instance.get_all_tables.return_value = ["table1", "table2"]
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = DropNamespaceRequest(id=["test_db"], behavior="CASCADE")
        response = hive_namespace.drop_namespace(request)
        
        mock_client_instance.drop_database.assert_called_once_with(
            "test_db", deleteData=True, cascade=True
        )
    
    def test_namespace_exists(self, hive_namespace, mock_hive_client):
        """Test checking if a namespace exists."""
        mock_client_instance = MagicMock()
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = NamespaceExistsRequest(id=["test_db"])
        hive_namespace.namespace_exists(request)
        
        mock_client_instance.get_database.assert_called_once_with("test_db")
    
    def test_list_tables(self, hive_namespace, mock_hive_client):
        """Test listing tables in a namespace."""
        mock_table1 = MagicMock()
        mock_table1.parameters = {"lance.table_type": "LANCE"}
        
        mock_table2 = MagicMock()
        mock_table2.parameters = {"other_type": "OTHER"}
        
        mock_table3 = MagicMock()
        mock_table3.parameters = {"lance.table_type": "LANCE"}
        
        mock_client_instance = MagicMock()
        mock_client_instance.get_all_tables.return_value = ["table1", "table2", "table3"]
        mock_client_instance.get_table.side_effect = [mock_table1, mock_table2, mock_table3]
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = ListTablesRequest(id=["test_db"])
        response = hive_namespace.list_tables(request)
        
        # Should only return Lance table names
        assert response.tables == ["table1", "table3"]
        mock_client_instance.get_all_tables.assert_called_once_with("test_db")
    
    def test_register_table(self, hive_namespace, mock_hive_client):
        """Test registering a Lance table."""
        with tempfile.TemporaryDirectory() as tmpdir:
            # Create a mock Lance dataset
            table_path = os.path.join(tmpdir, "test_table")
            
            # Create sample data
            data = pa.table({
                "id": [1, 2, 3],
                "name": ["Alice", "Bob", "Charlie"]
            })
            
            with patch("lance_namespace.hive.lance.dataset") as mock_dataset_func:
                mock_dataset = MagicMock()
                mock_dataset.schema = data.schema
                mock_dataset.version = 1
                mock_dataset_func.return_value = mock_dataset
                
                mock_client_instance = MagicMock()
                mock_hive_client.__enter__.return_value = mock_client_instance
                
                # Mock all Hive classes
                with patch("lance_namespace.hive.HiveTable") as mock_hive_table_class, \
                     patch("lance_namespace.hive.StorageDescriptor") as mock_sd_class, \
                     patch("lance_namespace.hive.SerDeInfo") as mock_serde_class, \
                     patch("lance_namespace.hive.FieldSchema") as mock_field_class:
                    
                    mock_hive_table = MagicMock()
                    mock_hive_table_class.return_value = mock_hive_table
                    mock_sd = MagicMock()
                    mock_sd_class.return_value = mock_sd
                    mock_serde = MagicMock()
                    mock_serde_class.return_value = mock_serde
                    mock_field_class.return_value = MagicMock()
                    
                    request = RegisterTableRequest(
                        id=["test_db", "test_table"],
                        location=table_path,
                        properties={"owner": "test_user"}
                    )
                    response = hive_namespace.register_table(request)
                    
                    # Response only includes location
                    assert response.location == table_path
                    
                    mock_client_instance.create_table.assert_called_once_with(mock_hive_table)
                    
                    # Verify the table object properties were set
                    assert mock_hive_table.dbName == "test_db"
                    assert mock_hive_table.tableName == "test_table"
                    assert mock_hive_table.tableType == "EXTERNAL_TABLE"
                    assert mock_sd.location == table_path
                    assert mock_hive_table.parameters["lance.table_type"] == "LANCE"
                    assert mock_hive_table.parameters["owner"] == "test_user"
    
    def test_table_exists(self, hive_namespace, mock_hive_client):
        """Test checking if a table exists."""
        mock_table = MagicMock()
        mock_table.parameters = {"lance.table_type": "LANCE"}
        
        mock_client_instance = MagicMock()
        mock_client_instance.get_table.return_value = mock_table
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = TableExistsRequest(id=["test_db", "test_table"])
        hive_namespace.table_exists(request)
        
        mock_client_instance.get_table.assert_called_once_with("test_db", "test_table")
    
    def test_drop_table(self, hive_namespace, mock_hive_client):
        """Test dropping a table."""
        mock_table = MagicMock()
        mock_table.parameters = {"lance.table_type": "LANCE"}
        
        mock_client_instance = MagicMock()
        mock_client_instance.get_table.return_value = mock_table
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = DropTableRequest(id=["test_db", "test_table"])
        response = hive_namespace.drop_table(request)
        
        mock_client_instance.get_table.assert_called_once_with("test_db", "test_table")
        mock_client_instance.drop_table.assert_called_once_with(
            "test_db", "test_table", deleteData=True
        )
    
    def test_deregister_table(self, hive_namespace, mock_hive_client):
        """Test deregistering a table without deleting data."""
        mock_table = MagicMock()
        mock_table.parameters = {"lance.table_type": "LANCE"}
        mock_table.sd.location = "/tmp/test_table"
        
        mock_client_instance = MagicMock()
        mock_client_instance.get_table.return_value = mock_table
        mock_hive_client.__enter__.return_value = mock_client_instance
        
        request = DeregisterTableRequest(id=["test_db", "test_table"])
        response = hive_namespace.deregister_table(request)
        
        assert response.location == "/tmp/test_table"
        mock_client_instance.drop_table.assert_called_once_with(
            "test_db", "test_table", deleteData=False
        )
    
    def test_normalize_identifier(self, hive_namespace):
        """Test identifier normalization."""
        # Single element should default to "default" database
        assert hive_namespace._normalize_identifier(["test_table"]) == ("default", "test_table")
        
        # Two elements should be (database, table)
        assert hive_namespace._normalize_identifier(["test_db", "test_table"]) == ("test_db", "test_table")
        
        # More than two elements should raise an error
        with pytest.raises(ValueError, match="Invalid identifier"):
            hive_namespace._normalize_identifier(["a", "b", "c"])
    
    def test_get_table_location(self, hive_namespace):
        """Test getting table location."""
        location = hive_namespace._get_table_location("test_db", "test_table")
        assert location == "/tmp/warehouse/test_db.db/test_table"