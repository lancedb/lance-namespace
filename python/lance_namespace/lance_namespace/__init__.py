# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Lance Namespace interface and plugin registry.

This module provides:
1. LanceNamespace ABC interface for namespace implementations
2. connect() factory function for creating namespace instances
3. register_namespace_impl() for external implementation registration
4. Re-exported model types from lance_namespace_urllib3_client
5. Error types for Lance Namespace operations

The actual implementations (DirectoryNamespace, RestNamespace) are provided
by the lance package. This package only provides the abstract interface
and plugin registration mechanism.
"""

import importlib
import warnings
from abc import ABC, abstractmethod
from typing import Dict

from lance_namespace.errors import (
    ErrorCode,
    LanceNamespaceError,
    UnsupportedOperationError,
    NamespaceNotFoundError,
    NamespaceAlreadyExistsError,
    NamespaceNotEmptyError,
    TableNotFoundError,
    TableAlreadyExistsError,
    TableIndexNotFoundError,
    TableIndexAlreadyExistsError,
    TableTagNotFoundError,
    TableTagAlreadyExistsError,
    TransactionNotFoundError,
    TableVersionNotFoundError,
    TableColumnNotFoundError,
    InvalidInputError,
    ConcurrentModificationError,
    PermissionDeniedError,
    UnauthenticatedError,
    ServiceUnavailableError,
    InternalError,
    InvalidTableStateError,
    TableSchemaValidationError,
    from_error_code,
)

from lance_namespace_urllib3_client.models import (
    AlterTableAddColumnsRequest,
    AlterTableAddColumnsResponse,
    AlterTableAlterColumnsRequest,
    AlterTableAlterColumnsResponse,
    AlterTableDropColumnsRequest,
    AlterTableDropColumnsResponse,
    AlterTransactionRequest,
    AlterTransactionResponse,
    AnalyzeTableQueryPlanRequest,
    CountTableRowsRequest,
    CreateEmptyTableRequest,
    CreateEmptyTableResponse,
    CreateNamespaceRequest,
    CreateNamespaceResponse,
    CreateTableIndexRequest,
    CreateTableIndexResponse,
    CreateTableScalarIndexResponse,
    CreateTableRequest,
    CreateTableResponse,
    CreateTableTagRequest,
    CreateTableTagResponse,
    DeclareTableRequest,
    DeclareTableResponse,
    DeleteFromTableRequest,
    DeleteFromTableResponse,
    DeleteTableTagRequest,
    DeleteTableTagResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
    DescribeNamespaceRequest,
    DescribeNamespaceResponse,
    DescribeTableIndexStatsRequest,
    DescribeTableIndexStatsResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    DescribeTransactionRequest,
    DescribeTransactionResponse,
    DropNamespaceRequest,
    DropNamespaceResponse,
    DropTableIndexRequest,
    DropTableIndexResponse,
    DropTableRequest,
    DropTableResponse,
    ExplainTableQueryPlanRequest,
    GetTableStatsRequest,
    GetTableStatsResponse,
    GetTableTagVersionRequest,
    GetTableTagVersionResponse,
    InsertIntoTableRequest,
    InsertIntoTableResponse,
    ListNamespacesRequest,
    ListNamespacesResponse,
    ListTableIndicesRequest,
    ListTableIndicesResponse,
    ListTableTagsRequest,
    ListTableTagsResponse,
    ListTableVersionsRequest,
    ListTableVersionsResponse,
    ListTablesRequest,
    ListTablesResponse,
    MergeInsertIntoTableRequest,
    MergeInsertIntoTableResponse,
    NamespaceExistsRequest,
    QueryTableRequest,
    RegisterTableRequest,
    RegisterTableResponse,
    RenameTableRequest,
    RenameTableResponse,
    RestoreTableRequest,
    RestoreTableResponse,
    TableExistsRequest,
    UpdateTableRequest,
    UpdateTableResponse,
    UpdateTableSchemaMetadataRequest,
    UpdateTableSchemaMetadataResponse,
    UpdateTableTagRequest,
    UpdateTableTagResponse,
)

__all__ = [
    # Interface and factory
    "LanceNamespace",
    "connect",
    "register_namespace_impl",
    # Registry access
    "NATIVE_IMPLS",
    # Error types
    "ErrorCode",
    "LanceNamespaceError",
    "UnsupportedOperationError",
    "NamespaceNotFoundError",
    "NamespaceAlreadyExistsError",
    "NamespaceNotEmptyError",
    "TableNotFoundError",
    "TableAlreadyExistsError",
    "TableIndexNotFoundError",
    "TableIndexAlreadyExistsError",
    "TableTagNotFoundError",
    "TableTagAlreadyExistsError",
    "TransactionNotFoundError",
    "TableVersionNotFoundError",
    "TableColumnNotFoundError",
    "InvalidInputError",
    "ConcurrentModificationError",
    "PermissionDeniedError",
    "UnauthenticatedError",
    "ServiceUnavailableError",
    "InternalError",
    "InvalidTableStateError",
    "TableSchemaValidationError",
    "from_error_code",
    # Request/Response types (re-exported from lance_namespace_urllib3_client)
    "AlterTableAddColumnsRequest",
    "AlterTableAddColumnsResponse",
    "AlterTableAlterColumnsRequest",
    "AlterTableAlterColumnsResponse",
    "AlterTableDropColumnsRequest",
    "AlterTableDropColumnsResponse",
    "AlterTransactionRequest",
    "AlterTransactionResponse",
    "AnalyzeTableQueryPlanRequest",
    "CountTableRowsRequest",
    "CreateEmptyTableRequest",
    "CreateEmptyTableResponse",
    "CreateNamespaceRequest",
    "CreateNamespaceResponse",
    "CreateTableIndexRequest",
    "CreateTableIndexResponse",
    "CreateTableScalarIndexResponse",
    "CreateTableRequest",
    "CreateTableResponse",
    "CreateTableTagRequest",
    "CreateTableTagResponse",
    "DeclareTableRequest",
    "DeclareTableResponse",
    "DeleteFromTableRequest",
    "DeleteFromTableResponse",
    "DeleteTableTagRequest",
    "DeleteTableTagResponse",
    "DeregisterTableRequest",
    "DeregisterTableResponse",
    "DescribeNamespaceRequest",
    "DescribeNamespaceResponse",
    "DescribeTableIndexStatsRequest",
    "DescribeTableIndexStatsResponse",
    "DescribeTableRequest",
    "DescribeTableResponse",
    "DescribeTransactionRequest",
    "DescribeTransactionResponse",
    "DropNamespaceRequest",
    "DropNamespaceResponse",
    "DropTableIndexRequest",
    "DropTableIndexResponse",
    "DropTableRequest",
    "DropTableResponse",
    "ExplainTableQueryPlanRequest",
    "GetTableStatsRequest",
    "GetTableStatsResponse",
    "GetTableTagVersionRequest",
    "GetTableTagVersionResponse",
    "InsertIntoTableRequest",
    "InsertIntoTableResponse",
    "ListNamespacesRequest",
    "ListNamespacesResponse",
    "ListTableIndicesRequest",
    "ListTableIndicesResponse",
    "ListTableTagsRequest",
    "ListTableTagsResponse",
    "ListTableVersionsRequest",
    "ListTableVersionsResponse",
    "ListTablesRequest",
    "ListTablesResponse",
    "MergeInsertIntoTableRequest",
    "MergeInsertIntoTableResponse",
    "NamespaceExistsRequest",
    "QueryTableRequest",
    "RegisterTableRequest",
    "RegisterTableResponse",
    "RenameTableRequest",
    "RenameTableResponse",
    "RestoreTableRequest",
    "RestoreTableResponse",
    "TableExistsRequest",
    "UpdateTableRequest",
    "UpdateTableResponse",
    "UpdateTableSchemaMetadataRequest",
    "UpdateTableSchemaMetadataResponse",
    "UpdateTableTagRequest",
    "UpdateTableTagResponse",
]


class LanceNamespace(ABC):
    """Base interface for Lance Namespace implementations.

    This abstract base class defines the contract for namespace implementations
    that manage Lance tables. Implementations can provide different storage backends
    (directory-based, REST API, cloud catalogs, etc.).

    To create a custom namespace implementation, subclass this ABC and implement
    at least the `namespace_id()` method. Other methods have default implementations
    that raise `UnsupportedOperationError`.

    Native implementations (DirectoryNamespace, RestNamespace) are provided by the
    lance package. External integrations (Glue, Hive, Unity) can be registered
    using `register_namespace_impl()`.

    All operations may raise the following common errors:

    - UnsupportedOperationError: The operation is not supported by this backend
    - InvalidInputError: The request contains invalid parameters
    - PermissionDeniedError: The user lacks permission for this operation
    - UnauthenticatedError: Authentication credentials are missing or invalid
    - ServiceUnavailableError: The service is temporarily unavailable
    - InternalError: An unexpected internal error occurred

    See the individual method docstrings for operation-specific errors.
    """

    @abstractmethod
    def namespace_id(self) -> str:
        """Return a human-readable unique identifier for this namespace instance.

        This is used for equality comparison and hashing when the namespace is
        used as part of a storage options provider. Two namespace instances with
        the same ID are considered equal and will share cached resources.

        The ID should be human-readable for debugging and logging purposes.
        For example:
        - REST namespace: "RestNamespace { uri: 'https://api.example.com' }"
        - Directory namespace: "DirectoryNamespace { root: '/path/to/data' }"

        Returns
        -------
        str
            A human-readable unique identifier string
        """
        pass

    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List namespaces.

        Raises
        ------
        NamespaceNotFoundError
            If the parent namespace does not exist.
        """
        raise UnsupportedOperationError("Not supported: list_namespaces")

    def describe_namespace(
        self, request: DescribeNamespaceRequest
    ) -> DescribeNamespaceResponse:
        """Describe a namespace.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        """
        raise UnsupportedOperationError("Not supported: describe_namespace")

    def create_namespace(
        self, request: CreateNamespaceRequest
    ) -> CreateNamespaceResponse:
        """Create a new namespace.

        Raises
        ------
        NamespaceAlreadyExistsError
            If a namespace with the same name already exists.
        """
        raise UnsupportedOperationError("Not supported: create_namespace")

    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop a namespace.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        NamespaceNotEmptyError
            If the namespace contains tables or child namespaces.
        """
        raise UnsupportedOperationError("Not supported: drop_namespace")

    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check if a namespace exists.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        """
        raise UnsupportedOperationError("Not supported: namespace_exists")

    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List tables in a namespace.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        """
        raise UnsupportedOperationError("Not supported: list_tables")

    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableVersionNotFoundError
            If the specified version does not exist.
        """
        raise UnsupportedOperationError("Not supported: describe_table")

    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        """Register a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableAlreadyExistsError
            If a table with the same name already exists.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: register_table")

    def table_exists(self, request: TableExistsRequest) -> None:
        """Check if a table exists.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: table_exists")

    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: drop_table")

    def deregister_table(
        self, request: DeregisterTableRequest
    ) -> DeregisterTableResponse:
        """Deregister a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: deregister_table")

    def count_table_rows(self, request: CountTableRowsRequest) -> int:
        """Count rows in a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableVersionNotFoundError
            If the specified version does not exist.
        """
        raise UnsupportedOperationError("Not supported: count_table_rows")

    def create_table(
        self, request: CreateTableRequest, request_data: bytes
    ) -> CreateTableResponse:
        """Create a new table with data from Arrow IPC stream.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableAlreadyExistsError
            If a table with the same name already exists.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        TableSchemaValidationError
            If the schema validation fails.
        """
        raise UnsupportedOperationError("Not supported: create_table")

    def declare_table(
        self, request: DeclareTableRequest
    ) -> DeclareTableResponse:
        """Declare a table (metadata only operation).

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableAlreadyExistsError
            If a table with the same name already exists.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: declare_table")

    def create_empty_table(
        self, request: CreateEmptyTableRequest
    ) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only operation).

        .. deprecated::
            Use :meth:`declare_table` instead.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableAlreadyExistsError
            If a table with the same name already exists.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        warnings.warn(
            "create_empty_table is deprecated, use declare_table instead",
            DeprecationWarning,
            stacklevel=2,
        )
        raise UnsupportedOperationError("Not supported: create_empty_table")

    def insert_into_table(
        self, request: InsertIntoTableRequest, request_data: bytes
    ) -> InsertIntoTableResponse:
        """Insert data into a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        InvalidTableStateError
            If the table is in an invalid state for this operation.
        TableSchemaValidationError
            If the schema validation fails.
        """
        raise UnsupportedOperationError("Not supported: insert_into_table")

    def merge_insert_into_table(
        self, request: MergeInsertIntoTableRequest, request_data: bytes
    ) -> MergeInsertIntoTableResponse:
        """Merge insert data into a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        InvalidTableStateError
            If the table is in an invalid state for this operation.
        """
        raise UnsupportedOperationError("Not supported: merge_insert_into_table")

    def update_table(self, request: UpdateTableRequest) -> UpdateTableResponse:
        """Update a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        InvalidTableStateError
            If the table is in an invalid state for this operation.
        """
        raise UnsupportedOperationError("Not supported: update_table")

    def delete_from_table(
        self, request: DeleteFromTableRequest
    ) -> DeleteFromTableResponse:
        """Delete from a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        InvalidTableStateError
            If the table is in an invalid state for this operation.
        """
        raise UnsupportedOperationError("Not supported: delete_from_table")

    def query_table(self, request: QueryTableRequest) -> bytes:
        """Query a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableVersionNotFoundError
            If the specified version does not exist.
        TableColumnNotFoundError
            If a referenced column does not exist.
        """
        raise UnsupportedOperationError("Not supported: query_table")

    def create_table_index(
        self, request: CreateTableIndexRequest
    ) -> CreateTableIndexResponse:
        """Create a table index.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableIndexAlreadyExistsError
            If an index with the same name already exists.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: create_table_index")

    def create_table_scalar_index(
        self, request: CreateTableIndexRequest
    ) -> CreateTableScalarIndexResponse:
        """Create a scalar index on a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableIndexAlreadyExistsError
            If an index with the same name already exists.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: create_table_scalar_index")

    def list_table_indices(
        self, request: ListTableIndicesRequest
    ) -> ListTableIndicesResponse:
        """List table indices.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: list_table_indices")

    def describe_table_index_stats(
        self, request: DescribeTableIndexStatsRequest
    ) -> DescribeTableIndexStatsResponse:
        """Describe table index statistics.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableIndexNotFoundError
            If the index does not exist.
        """
        raise UnsupportedOperationError("Not supported: describe_table_index_stats")

    def drop_table_index(
        self, request: DropTableIndexRequest
    ) -> DropTableIndexResponse:
        """Drop a table index.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableIndexNotFoundError
            If the index does not exist.
        """
        raise UnsupportedOperationError("Not supported: drop_table_index")

    def list_all_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List all tables across all namespaces."""
        raise UnsupportedOperationError("Not supported: list_all_tables")

    def restore_table(self, request: RestoreTableRequest) -> RestoreTableResponse:
        """Restore a table to a specific version.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableVersionNotFoundError
            If the specified version does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: restore_table")

    def rename_table(self, request: RenameTableRequest) -> RenameTableResponse:
        """Rename a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableAlreadyExistsError
            If a table with the new name already exists.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: rename_table")

    def list_table_versions(
        self, request: ListTableVersionsRequest
    ) -> ListTableVersionsResponse:
        """List all versions of a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: list_table_versions")

    def update_table_schema_metadata(
        self, request: UpdateTableSchemaMetadataRequest
    ) -> UpdateTableSchemaMetadataResponse:
        """Update table schema metadata.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: update_table_schema_metadata")

    def get_table_stats(self, request: GetTableStatsRequest) -> GetTableStatsResponse:
        """Get table statistics.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: get_table_stats")

    def explain_table_query_plan(self, request: ExplainTableQueryPlanRequest) -> str:
        """Explain a table query plan.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: explain_table_query_plan")

    def analyze_table_query_plan(self, request: AnalyzeTableQueryPlanRequest) -> str:
        """Analyze a table query plan.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: analyze_table_query_plan")

    def alter_table_add_columns(
        self, request: AlterTableAddColumnsRequest
    ) -> AlterTableAddColumnsResponse:
        """Add columns to a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        TableSchemaValidationError
            If the schema validation fails.
        """
        raise UnsupportedOperationError("Not supported: alter_table_add_columns")

    def alter_table_alter_columns(
        self, request: AlterTableAlterColumnsRequest
    ) -> AlterTableAlterColumnsResponse:
        """Alter columns in a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        TableSchemaValidationError
            If the schema validation fails.
        """
        raise UnsupportedOperationError("Not supported: alter_table_alter_columns")

    def alter_table_drop_columns(
        self, request: AlterTableDropColumnsRequest
    ) -> AlterTableDropColumnsResponse:
        """Drop columns from a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableColumnNotFoundError
            If a referenced column does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: alter_table_drop_columns")

    def list_table_tags(self, request: ListTableTagsRequest) -> ListTableTagsResponse:
        """List all tags for a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        """
        raise UnsupportedOperationError("Not supported: list_table_tags")

    def get_table_tag_version(
        self, request: GetTableTagVersionRequest
    ) -> GetTableTagVersionResponse:
        """Get the version for a specific tag.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableTagNotFoundError
            If the tag does not exist.
        """
        raise UnsupportedOperationError("Not supported: get_table_tag_version")

    def create_table_tag(
        self, request: CreateTableTagRequest
    ) -> CreateTableTagResponse:
        """Create a tag for a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableTagAlreadyExistsError
            If a tag with the same name already exists.
        TableVersionNotFoundError
            If the specified version does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: create_table_tag")

    def delete_table_tag(
        self, request: DeleteTableTagRequest
    ) -> DeleteTableTagResponse:
        """Delete a tag from a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableTagNotFoundError
            If the tag does not exist.
        """
        raise UnsupportedOperationError("Not supported: delete_table_tag")

    def update_table_tag(
        self, request: UpdateTableTagRequest
    ) -> UpdateTableTagResponse:
        """Update a tag for a table.

        Raises
        ------
        NamespaceNotFoundError
            If the namespace does not exist.
        TableNotFoundError
            If the table does not exist.
        TableTagNotFoundError
            If the tag does not exist.
        TableVersionNotFoundError
            If the specified version does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: update_table_tag")

    def describe_transaction(
        self, request: DescribeTransactionRequest
    ) -> DescribeTransactionResponse:
        """Describe a transaction.

        Raises
        ------
        TransactionNotFoundError
            If the transaction does not exist.
        """
        raise UnsupportedOperationError("Not supported: describe_transaction")

    def alter_transaction(
        self, request: AlterTransactionRequest
    ) -> AlterTransactionResponse:
        """Alter a transaction.

        Raises
        ------
        TransactionNotFoundError
            If the transaction does not exist.
        ConcurrentModificationError
            If a concurrent modification conflict occurs.
        """
        raise UnsupportedOperationError("Not supported: alter_transaction")


# Native implementations (provided by lance package)
NATIVE_IMPLS: Dict[str, str] = {
    "rest": "lance.namespace.RestNamespace",
    "dir": "lance.namespace.DirectoryNamespace",
}

# Plugin registry for external implementations
_REGISTERED_IMPLS: Dict[str, str] = {}


def register_namespace_impl(name: str, class_path: str) -> None:
    """Register a namespace implementation with a short name.

    External libraries can use this to register their implementations,
    allowing users to use short names like "glue" instead of full class paths.

    Parameters
    ----------
    name : str
        Short name for the implementation (e.g., "glue", "hive2", "unity")
    class_path : str
        Full class path (e.g., "lance_glue.GlueNamespace")

    Examples
    --------
    >>> # Register a custom implementation
    >>> register_namespace_impl("glue", "lance_glue.GlueNamespace")
    >>> # Now users can use: connect("glue", {"catalog": "my_catalog"})
    """
    _REGISTERED_IMPLS[name] = class_path


def connect(impl: str, properties: Dict[str, str]) -> LanceNamespace:
    """Connect to a Lance namespace implementation.

    This factory function creates namespace instances based on implementation
    aliases or full class paths. It provides a unified way to instantiate
    different namespace backends.

    Parameters
    ----------
    impl : str
        Implementation alias or full class path. Built-in aliases:
        - "rest": RestNamespace (REST API client, provided by lance)
        - "dir": DirectoryNamespace (local/cloud filesystem, provided by lance)
        You can also use full class paths like "my.custom.Namespace"
        External libraries can register additional aliases using
        `register_namespace_impl()`.
    properties : Dict[str, str]
        Configuration properties passed to the namespace constructor

    Returns
    -------
    LanceNamespace
        The connected namespace instance

    Raises
    ------
    ValueError
        If the implementation class cannot be loaded or does not
        implement LanceNamespace interface

    Examples
    --------
    >>> # Connect to a directory namespace (requires lance package)
    >>> ns = connect("dir", {"root": "/path/to/data"})
    >>>
    >>> # Connect to a REST namespace (requires lance package)
    >>> ns = connect("rest", {"uri": "http://localhost:4099"})
    >>>
    >>> # Use a full class path
    >>> ns = connect("my_package.MyNamespace", {"key": "value"})
    """
    # Check native impls first, then registered plugins, then treat as full class path
    impl_class = NATIVE_IMPLS.get(impl) or _REGISTERED_IMPLS.get(impl) or impl
    try:
        module_name, class_name = impl_class.rsplit(".", 1)
        module = importlib.import_module(module_name)
        namespace_class = getattr(module, class_name)

        if not issubclass(namespace_class, LanceNamespace):
            raise ValueError(
                f"Class {impl_class} does not implement LanceNamespace interface"
            )

        return namespace_class(**properties)
    except Exception as e:
        raise ValueError(f"Failed to construct namespace impl {impl_class}: {e}")
