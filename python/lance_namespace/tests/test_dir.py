"""
Tests for DirectoryNamespace implementation.
"""
import tempfile
import shutil
import os
from pathlib import Path

import pytest

from lance_namespace.dir import DirectoryNamespace
from lance_namespace_urllib3_client.models import (
    CreateNamespaceRequest,
    ListNamespacesRequest,
    DescribeNamespaceRequest,
    DropNamespaceRequest,
    NamespaceExistsRequest,
    ListTablesRequest,
    RegisterTableRequest,
    DeregisterTableRequest,
)


class TestDirectoryNamespace:
    
    def setup_method(self):
        """Set up test environment."""
        self.temp_dir = tempfile.mkdtemp()
        self.namespace = DirectoryNamespace(root=self.temp_dir)
    
    def teardown_method(self):
        """Clean up test environment."""
        if os.path.exists(self.temp_dir):
            shutil.rmtree(self.temp_dir)
    
    def test_init_with_absolute_path(self):
        """Test initialization with absolute path."""
        namespace = DirectoryNamespace(root=self.temp_dir)
        assert namespace is not None
    
    def test_init_with_file_uri(self):
        """Test initialization with file:// URI."""
        namespace = DirectoryNamespace(root=f"file://{self.temp_dir}")
        assert namespace is not None
    
    def test_init_with_relative_path(self):
        """Test initialization with relative path."""
        namespace = DirectoryNamespace(root="./test-namespace")
        assert namespace is not None
    
    def test_init_without_root(self):
        """Test initialization without root uses current directory."""
        # Should not raise error, uses current directory
        namespace = DirectoryNamespace()
        assert namespace is not None
    
    def test_register_table(self):
        """Test registering a table."""
        request = RegisterTableRequest()
        request.table = "test_table"
        
        response = self.namespace.register_table(request)
        assert response.table == "test_table"
        assert response.table_uri == "test_table/"
        
        # Verify table directory was created
        table_dir = Path(self.temp_dir) / "test_table"
        assert table_dir.exists()
        assert table_dir.is_dir()
    
    def test_list_tables(self):
        """Test listing tables."""
        # Register some tables
        for table_name in ["table1", "table2", "table3"]:
            request = RegisterTableRequest()
            request.table = table_name
            self.namespace.register_table(request)
        
        # List tables
        request = ListTablesRequest()
        response = self.namespace.list_tables(request)
        
        assert len(response.tables) == 3
        assert set(response.tables) == {"table1", "table2", "table3"}
    
    def test_deregister_table(self):
        """Test deregistering a table."""
        # First register a table
        register_request = RegisterTableRequest()
        register_request.table = "test_table"
        self.namespace.register_table(register_request)
        
        table_dir = Path(self.temp_dir) / "test_table"
        assert table_dir.exists()
        
        # Deregister the table
        deregister_request = DeregisterTableRequest()
        deregister_request.table = "test_table"
        response = self.namespace.deregister_table(deregister_request)
        
        assert response.table == "test_table"
        # Verify table directory was removed
        assert not table_dir.exists()
    
    def test_empty_list_tables(self):
        """Test listing tables when none exist."""
        request = ListTablesRequest()
        response = self.namespace.list_tables(request)
        
        assert response.tables == []
    
    def test_namespace_operations_not_supported(self):
        """Test that namespace operations raise NotImplementedError."""
        
        # Test CreateNamespace
        with pytest.raises(NotImplementedError, match="flat list of tables"):
            self.namespace.create_namespace(CreateNamespaceRequest())
        
        # Test ListNamespaces
        with pytest.raises(NotImplementedError, match="flat list of tables"):
            self.namespace.list_namespaces(ListNamespacesRequest())
        
        # Test DescribeNamespace
        with pytest.raises(NotImplementedError, match="flat list of tables"):
            self.namespace.describe_namespace(DescribeNamespaceRequest())
        
        # Test DropNamespace
        with pytest.raises(NotImplementedError, match="flat list of tables"):
            self.namespace.drop_namespace(DropNamespaceRequest())
        
        # Test NamespaceExists
        with pytest.raises(NotImplementedError, match="flat list of tables"):
            self.namespace.namespace_exists(NamespaceExistsRequest())
    
    def test_register_table_invalid_name(self):
        """Test registering table with invalid name."""
        request = RegisterTableRequest()
        request.table = None
        
        with pytest.raises(ValueError, match="table name is required"):
            self.namespace.register_table(request)
    
    def test_deregister_table_invalid_name(self):
        """Test deregistering table with invalid name."""
        request = DeregisterTableRequest()
        request.table = None
        
        with pytest.raises(ValueError, match="table name is required"):
            self.namespace.deregister_table(request)