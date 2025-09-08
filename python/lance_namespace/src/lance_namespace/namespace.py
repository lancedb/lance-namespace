"""
Lance Namespace base interface and implementations.
"""
from abc import ABC, abstractmethod
from typing import Dict, Any, Optional, List, Union
import importlib
import inspect

from lance_namespace_urllib3_client.models import (
    ListNamespacesRequest,
    ListNamespacesResponse,
    DescribeNamespaceRequest,
    DescribeNamespaceResponse,
    CreateNamespaceRequest,
    CreateNamespaceResponse,
    DropNamespaceRequest,
    DropNamespaceResponse,
    NamespaceExistsRequest,
    ListTablesRequest,
    ListTablesResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    RegisterTableRequest,
    RegisterTableResponse,
    TableExistsRequest,
    DropTableRequest,
    DropTableResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
    CountTableRowsRequest,
    CreateTableRequest,
    CreateTableResponse,
    CreateEmptyTableRequest,
    CreateEmptyTableResponse,
    InsertIntoTableRequest,
    InsertIntoTableResponse,
    MergeInsertIntoTableRequest,
    MergeInsertIntoTableResponse,
    UpdateTableRequest,
    UpdateTableResponse,
    DeleteFromTableRequest,
    DeleteFromTableResponse,
    QueryTableRequest,
    CreateTableIndexRequest,
    CreateTableIndexResponse,
    ListTableIndicesRequest,
    ListTableIndicesResponse,
    DescribeTableIndexStatsRequest,
    DescribeTableIndexStatsResponse,
    DescribeTransactionRequest,
    DescribeTransactionResponse,
    AlterTransactionRequest,
    AlterTransactionResponse,
)


class LanceNamespace(ABC):
    """Base interface for Lance Namespace implementations."""

    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List namespaces."""
        raise NotImplementedError("Not supported: list_namespaces")
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        """Describe a namespace."""
        raise NotImplementedError("Not supported: describe_namespace")
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        """Create a new namespace."""
        raise NotImplementedError("Not supported: create_namespace")
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop a namespace."""
        raise NotImplementedError("Not supported: drop_namespace")
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check if a namespace exists."""
        raise NotImplementedError("Not supported: namespace_exists")
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List tables in a namespace."""
        raise NotImplementedError("Not supported: list_tables")
    
    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table."""
        raise NotImplementedError("Not supported: describe_table")
    
    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        """Register a table."""
        raise NotImplementedError("Not supported: register_table")
    
    def table_exists(self, request: TableExistsRequest) -> None:
        """Check if a table exists."""
        raise NotImplementedError("Not supported: table_exists")
    
    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table."""
        raise NotImplementedError("Not supported: drop_table")
    
    def deregister_table(self, request: DeregisterTableRequest) -> DeregisterTableResponse:
        """Deregister a table."""
        raise NotImplementedError("Not supported: deregister_table")
    
    def count_table_rows(self, request: CountTableRowsRequest) -> int:
        """Count rows in a table."""
        raise NotImplementedError("Not supported: count_table_rows")
    
    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        """Create a new table with data from Arrow IPC stream."""
        raise NotImplementedError("Not supported: create_table")
    
    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only operation)."""
        raise NotImplementedError("Not supported: create_empty_table")
    
    def insert_into_table(self, request: InsertIntoTableRequest, request_data: bytes) -> InsertIntoTableResponse:
        """Insert data into a table."""
        raise NotImplementedError("Not supported: insert_into_table")
    
    def merge_insert_into_table(
        self, request: MergeInsertIntoTableRequest, request_data: bytes
    ) -> MergeInsertIntoTableResponse:
        """Merge insert data into a table."""
        raise NotImplementedError("Not supported: merge_insert_into_table")
    
    def update_table(self, request: UpdateTableRequest) -> UpdateTableResponse:
        """Update a table."""
        raise NotImplementedError("Not supported: update_table")
    
    def delete_from_table(self, request: DeleteFromTableRequest) -> DeleteFromTableResponse:
        """Delete from a table."""
        raise NotImplementedError("Not supported: delete_from_table")
    
    def query_table(self, request: QueryTableRequest) -> bytes:
        """Query a table."""
        raise NotImplementedError("Not supported: query_table")
    
    def create_table_index(self, request: CreateTableIndexRequest) -> CreateTableIndexResponse:
        """Create a table index."""
        raise NotImplementedError("Not supported: create_table_index")
    
    def list_table_indices(self, request: ListTableIndicesRequest) -> ListTableIndicesResponse:
        """List table indices."""
        raise NotImplementedError("Not supported: list_table_indices")
    
    def describe_table_index_stats(
        self, request: DescribeTableIndexStatsRequest
    ) -> DescribeTableIndexStatsResponse:
        """Describe table index statistics."""
        raise NotImplementedError("Not supported: describe_table_index_stats")
    
    def describe_transaction(self, request: DescribeTransactionRequest) -> DescribeTransactionResponse:
        """Describe a transaction."""
        raise NotImplementedError("Not supported: describe_transaction")
    
    def alter_transaction(self, request: AlterTransactionRequest) -> AlterTransactionResponse:
        """Alter a transaction."""
        raise NotImplementedError("Not supported: alter_transaction")


NATIVE_IMPLS = {
    "rest": "lance_namespace.rest.LanceRestNamespace",
    "dir": "lance_namespace.dir.DirectoryNamespace",
    "glue": "lance_namespace.glue.GlueNamespace",
    "hive2": "lance_namespace.hive.Hive2Namespace",
    "unity": "lance_namespace.unity.UnityNamespace",
}

def connect(impl: str, properties: Dict[str, str]) -> LanceNamespace:
    """
    Connect to a Lance namespace implementation.
    
    Args:
        impl: Implementation alias or full class path
        properties: Configuration properties
        
    Returns:
        LanceNamespace instance
    """
    impl_class = NATIVE_IMPLS.get(impl, impl)
    try:
        module_name, class_name = impl_class.rsplit(".", 1)
        module = importlib.import_module(module_name)
        namespace_class = getattr(module, class_name)

        if not issubclass(namespace_class, LanceNamespace):
            raise ValueError(f"Class {impl_class} does not implement LanceNamespace interface")

        return namespace_class(**properties)
    except Exception as e:
        raise ValueError(f"Failed to construct namespace impl {impl_class}: {e}")