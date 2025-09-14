"""
Tests for DirectoryNamespace implementation.
"""
import tempfile
import shutil
import os
from pathlib import Path

import pytest

try:
    import opendal
    from lance_namespace.dir import DirectoryNamespace
except ImportError:
    DirectoryNamespace = None
    opendal = None

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


@pytest.mark.skipif(opendal is None, reason="opendal not available")
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
    
    def create_test_ipc_data(self):
        """Create test Arrow IPC stream data."""
        import pyarrow as pa
        import io
        
        # Create an empty Arrow table with schema
        arrow_schema = pa.schema([
            pa.field('id', pa.int32(), nullable=False),
            pa.field('name', pa.utf8(), nullable=True),
        ])
        # Create empty arrays for each field
        empty_arrays = [
            pa.array([], type=pa.int32()),
            pa.array([], type=pa.utf8())
        ]
        empty_table = pa.table(empty_arrays, schema=arrow_schema)
        
        # Convert to Arrow IPC stream
        buffer = io.BytesIO()
        with pa.ipc.RecordBatchStreamWriter(buffer, arrow_schema) as writer:
            writer.write_table(empty_table)
        return buffer.getvalue()
    
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
            id=["test_table"]
        )
        
        response = self.namespace.create_table(request, self.create_test_ipc_data())
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
                id=[table_name]
            )
            self.namespace.create_table(request, self.create_test_ipc_data())
        
        # List tables
        request = ListTablesRequest()
        response = self.namespace.list_tables(request)
        
        assert len(response.tables) == 3
        assert set(response.tables) == {"table1", "table2", "table3"}
    
    def test_drop_table(self):
        """Test dropping a table."""
        # First create a table
        create_request = CreateTableRequest(
            id=["test_table"]
        )
        self.namespace.create_table(create_request, self.create_test_ipc_data())
        
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
            id=["test_table"]
        )
        self.namespace.create_table(create_request, self.create_test_ipc_data())
        
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
            id=[]  # Empty ID
        )
        
        with pytest.raises(ValueError, match="table ID cannot be empty"):
            self.namespace.create_table(request, self.create_test_ipc_data())
    
    
    
    def test_create_table_with_invalid_multi_level_id(self):
        """Test creating table with invalid multi-level ID."""
        request = CreateTableRequest(
            id=["namespace1", "test_table"]
        )
        
        with pytest.raises(ValueError, match="single-level table IDs"):
            self.namespace.create_table(request, self.create_test_ipc_data())
    
    def test_list_tables_with_root_namespace_id(self):
        """Test listing tables with empty namespace ID (root)."""
        # Create a table first
        request = CreateTableRequest(
            id=["test_table"]
        )
        self.namespace.create_table(request, self.create_test_ipc_data())
        
        # List tables with empty namespace ID (root)
        list_request = ListTablesRequest()
        list_request.id = []
        response = self.namespace.list_tables(list_request)
        
        assert len(response.tables) == 1
        assert "test_table" in response.tables
    
    def test_list_tables_with_non_empty_namespace_id(self):
        """Test listing tables with non-empty namespace ID should fail."""
        # Create a table first
        request = CreateTableRequest(
            id=["test_table"]
        )
        self.namespace.create_table(request, self.create_test_ipc_data())
        
        # List tables with non-empty namespace ID should fail
        list_request = ListTablesRequest()
        list_request.id = ["default"]
        
        with pytest.raises(ValueError, match="root namespace operations"):
            self.namespace.list_tables(list_request)
    
    def test_list_tables_with_invalid_namespace_id(self):
        """Test listing tables with invalid namespace ID."""
        list_request = ListTablesRequest()
        list_request.id = ["namespace1"]

        with pytest.raises(ValueError, match="root namespace operations"):
            self.namespace.list_tables(list_request)

    def test_create_empty_table(self):
        """Test creating an empty table with .lance-reserved file."""
        from lance_namespace_urllib3_client.models import CreateEmptyTableRequest

        request = CreateEmptyTableRequest(
            id=["test_empty_table"]
        )

        response = self.namespace.create_empty_table(request)
        assert response.location is not None
        assert "test_empty_table" in response.location

        # Verify the .lance-reserved file was created in the correct location
        table_dir = Path(self.temp_dir) / "test_empty_table.lance"
        reserved_file = table_dir / ".lance-reserved"
        assert reserved_file.exists()
        assert reserved_file.is_file()

        # Verify the table is listed
        list_request = ListTablesRequest()
        list_response = self.namespace.list_tables(list_request)
        assert "test_empty_table" in list_response.tables

        # Verify the table can be described
        describe_request = DescribeTableRequest()
        describe_request.id = ["test_empty_table"]
        describe_response = self.namespace.describe_table(describe_request)
        assert describe_response.location is not None
        assert "test_empty_table" in describe_response.location
