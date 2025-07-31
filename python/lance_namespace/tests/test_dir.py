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
        table_dir = Path(self.temp_dir) / "test_table.lance"
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
        
        table_dir = Path(self.temp_dir) / "test_table.lance"
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
        
        with pytest.raises(ValueError, match="table ID cannot be empty"):
            self.namespace.create_table(request, b"")
    
    def test_create_table_with_multi_level_default_id(self):
        """Test creating a table with multi-level default ID."""
        request = CreateTableRequest(
            id=["default", "test_table"],
            schema=self.create_test_schema()
        )
        
        response = self.namespace.create_table(request, b"")
        assert response.location is not None
        assert "test_table" in response.location
        assert response.version == 1
        
        # Verify Lance dataset was created
        table_dir = Path(self.temp_dir) / "test_table.lance"
        assert table_dir.exists()
        assert table_dir.is_dir()
    
    def test_create_table_with_multiple_levels_default(self):
        """Test creating a table with multiple levels of default."""
        request = CreateTableRequest(
            id=["default", "default", "test_table"],
            schema=self.create_test_schema()
        )
        
        response = self.namespace.create_table(request, b"")
        assert response.location is not None
        assert "test_table" in response.location
    
    def test_create_table_with_invalid_multi_level_id(self):
        """Test creating table with invalid multi-level ID."""
        request = CreateTableRequest(
            id=["namespace1", "test_table"],
            schema=self.create_test_schema()
        )
        
        with pytest.raises(ValueError, match="unsupported structure"):
            self.namespace.create_table(request, b"")
    
    def test_drop_table_with_multi_level_default_id(self):
        """Test dropping a table with multi-level default ID."""
        # First create a table with multi-level ID
        create_request = CreateTableRequest(
            id=["default", "test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(create_request, b"")
        
        table_dir = Path(self.temp_dir) / "test_table.lance"
        assert table_dir.exists()
        
        # Drop the table using multi-level ID
        drop_request = DropTableRequest()
        drop_request.id = ["default", "test_table"]
        response = self.namespace.drop_table(drop_request)
        
        assert response is not None
        assert not table_dir.exists()
    
    def test_describe_table_with_multi_level_default_id(self):
        """Test describing a table with multi-level default ID."""
        # First create a table with multi-level ID
        create_request = CreateTableRequest(
            id=["default", "test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(create_request, b"")
        
        # Now describe the table using multi-level ID
        describe_request = DescribeTableRequest()
        describe_request.id = ["default", "test_table"]
        response = self.namespace.describe_table(describe_request)
        
        assert response is not None
        assert response.location is not None
        assert "test_table" in response.location
    
    def test_list_tables_with_root_namespace_id(self):
        """Test listing tables with empty namespace ID (root)."""
        # Create a table first
        request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(request, b"")
        
        # List tables with empty namespace ID (root)
        list_request = ListTablesRequest()
        list_request.id = []
        response = self.namespace.list_tables(list_request)
        
        assert len(response.tables) == 1
        assert "test_table" in response.tables
    
    def test_list_tables_with_default_namespace_id(self):
        """Test listing tables with 'default' namespace ID."""
        # Create a table first
        request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(request, b"")
        
        # List tables with "default" namespace ID
        list_request = ListTablesRequest()
        list_request.id = ["default"]
        response = self.namespace.list_tables(list_request)
        
        assert len(response.tables) == 1
        assert "test_table" in response.tables
    
    def test_list_tables_with_invalid_namespace_id(self):
        """Test listing tables with invalid namespace ID."""
        list_request = ListTablesRequest()
        list_request.id = ["namespace1"]
        
        with pytest.raises(ValueError, match="root namespace operations"):
            self.namespace.list_tables(list_request)
    
    def test_list_tables_with_multiple_default_namespace_id(self):
        """Test listing tables with multiple 'default' namespace ID."""
        # Create a table first
        request = CreateTableRequest(
            id=["test_table"],
            schema=self.create_test_schema()
        )
        self.namespace.create_table(request, b"")
        
        # List tables with multiple "default" namespace ID
        list_request = ListTablesRequest()
        list_request.id = ["default", "default"]
        response = self.namespace.list_tables(list_request)
        
        assert len(response.tables) == 1
        assert "test_table" in response.tables
    
    def test_configurable_extra_level(self):
        """Test configurable extra level functionality."""
        # Create namespace with custom extra level
        namespace = DirectoryNamespace(root=self.temp_dir, extra_level="catalog")
        
        # Create a table with custom extra level
        create_request = CreateTableRequest(
            id=["catalog", "test_table"],
            schema=self.create_test_schema()
        )
        
        response = namespace.create_table(create_request, b"")
        assert response.location is not None
        assert "test_table" in response.location
        
        # List tables with custom extra level namespace ID
        list_request = ListTablesRequest()
        list_request.id = ["catalog"]
        list_response = namespace.list_tables(list_request)
        
        assert len(list_response.tables) == 1
        assert "test_table" in list_response.tables
        
        # Drop table with custom extra level
        drop_request = DropTableRequest()
        drop_request.id = ["catalog", "test_table"]
        drop_response = namespace.drop_table(drop_request)
        assert drop_response is not None
    
    def test_configurable_extra_level_validation(self):
        """Test configurable extra level validation."""
        # Create namespace with custom extra level
        namespace = DirectoryNamespace(root=self.temp_dir, extra_level="catalog")
        
        # Try to create table with wrong extra level (should fail)
        create_request = CreateTableRequest(
            id=["default", "test_table"],  # Wrong extra level
            schema=self.create_test_schema()
        )
        
        with pytest.raises(ValueError, match="unsupported structure"):
            namespace.create_table(create_request, b"")
        
        # Try to list tables with wrong extra level (should fail)
        list_request = ListTablesRequest()
        list_request.id = ["default"]  # Wrong extra level
        
        with pytest.raises(ValueError, match="root namespace operations"):
            namespace.list_tables(list_request)