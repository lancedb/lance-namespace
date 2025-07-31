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
    CreateTableRequest,
    CreateTableResponse,
    DropTableRequest,
    DropTableResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
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
    
    def create_test_schema(self):
        """Create a test schema with id (int32) and name (string) fields."""
        # Create int32 type
        int_type = JsonArrowDataType(type="int32")
        
        # Create string type  
        string_type = JsonArrowDataType(type="utf8")
        
        # Create id field
        id_field = JsonArrowField(
            name="id",
            type=int_type,
            nullable=False
        )
        
        # Create name field
        name_field = JsonArrowField(
            name="name",
            type=string_type,
            nullable=True
        )
        
        # Create schema
        schema = JsonArrowSchema(fields=[id_field, name_field])
        return schema
    
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
    
    def test_create_table(self):
        """Test creating a table."""
        request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        
        response = self.namespace.create_table(request, b"")
        assert response.location is not None
        assert "test_table" in response.location
        assert response.version == 1
        
        # Verify Lance dataset was created (check for _versions directory)
        table_dir = Path(self.temp_dir) / "test_table"
        assert table_dir.exists()
        assert table_dir.is_dir()
        
        versions_dir = table_dir / "_versions"
        assert versions_dir.exists()
        assert versions_dir.is_dir()
    
    def test_list_tables(self):
        """Test listing tables."""
        # Create some tables
        for table_name in ["table1", "table2", "table3"]:
            request = CreateTableRequest(
                id=[table_name],
                schema=self.create_test_schema()
            )
            self.namespace.create_table(request, b"")
        
        # List tables
        request = ListTablesRequest()
        response = self.namespace.list_tables(request)
        
        assert len(response.tables) == 3
        assert set(response.tables) == {"table1", "table2", "table3"}
    
    def test_drop_table(self):
        """Test dropping a table."""
        # First create a table
        create_request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(create_request, b"")
        
        table_dir = Path(self.temp_dir) / "test_table"
        assert table_dir.exists()
        
        # Drop the table
        drop_request = DropTableRequest()
        drop_request.id = ["test_table"]
        response = self.namespace.drop_table(drop_request)
        
        assert response is not None
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
    
    def test_describe_table(self):
        """Test describing a table."""
        # First create a table
        create_request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(create_request, b"")
        
        # Now describe the table
        describe_request = DescribeTableRequest()
        describe_request.id = ["test_table"]
        response = self.namespace.describe_table(describe_request)
        
        assert response is not None
        assert response.location is not None
        assert "test_table" in response.location
        assert self.temp_dir in response.location
    
    def test_describe_nonexistent_table(self):
        """Test describing a nonexistent table."""
        describe_request = DescribeTableRequest()
        describe_request.id = ["nonexistent_table"]
        
        with pytest.raises(RuntimeError, match="Table does not exist"):
            self.namespace.describe_table(describe_request)
    
    def test_create_table_invalid_id(self):
        """Test creating table with invalid ID."""
        request = CreateTableRequest(
            id=[],  # Empty ID
            schema=self.create_test_schema()
        )
        
        with pytest.raises(ValueError, match="table ID must have exactly 1 level"):
            self.namespace.create_table(request, b"")