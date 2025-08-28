"""
Lance REST Namespace implementation.
"""
from typing import Dict, Any, Optional
import json

from lance_namespace_urllib3_client import (
    ApiClient,
    Configuration,
    NamespaceApi,
    TableApi,
    TransactionApi,
)
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

from .namespace import LanceNamespace


class RestNamespaceConfig:
    """Configuration for REST namespace."""

    DELIMITER = "delimiter"
    HEADER_PREFIX = "header."
    
    def __init__(self, properties: Dict[str, str]):
        self._delimiter = properties.get(self.DELIMITER, ".")
        self._headers = {}

        for key, value in properties.items():
            if key.startswith(self.HEADER_PREFIX):
                header_name = key[len(self.HEADER_PREFIX):]
                self._headers[header_name] = value
    
    def delimiter(self) -> str:
        return self._delimiter
    
    def additional_headers(self) -> Dict[str, str]:
        return self._headers


def object_id_str(id_list: list[str], delimiter: str = ".", obj: Any = None) -> str:
    """Convert a list of strings to a string identifier using the specified delimiter.
    If the list is empty, returns the delimiter itself.
    
    Args:
        id_list: List of strings representing the identifier
        delimiter: Delimiter to join the strings
        obj: The object containing the id (for error messages)
        
    Returns:
        String identifier
        
    Raises:
        ValueError: If id_list is None
    """
    if id_list is None:
        object_type = type(obj).__name__ if obj is not None else "Unknown"
        raise ValueError(f"Object of type '{object_type}' must have an 'id' field")
    if len(id_list) == 0:
        return delimiter
    return delimiter.join(id_list)


class LanceRestNamespace(LanceNamespace):
    """REST implementation of Lance Namespace."""
    
    def __init__(self, **kwargs):
        configuration = Configuration()
        if "uri" in kwargs:
            configuration.host = kwargs["uri"]

        self.api_client = ApiClient(configuration)
        self.namespace_api = NamespaceApi(self.api_client)
        self.table_api = TableApi(self.api_client)
        self.transaction_api = TransactionApi(self.api_client)
        self.config = RestNamespaceConfig(kwargs)
    
    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        return self.namespace_api.list_namespaces(
            id=object_id_str(request.id, self.config.delimiter(), request),
            delimiter=self.config.delimiter(),
            page_token=request.page_token,
            limit=request.limit,
            _headers=self.config.additional_headers(),
        )
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        return self.namespace_api.describe_namespace(
            id=object_id_str(request.id, self.config.delimiter(), request),
            describe_namespace_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        return self.namespace_api.create_namespace(
            id=object_id_str(request.id, self.config.delimiter(), request),
            create_namespace_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        return self.namespace_api.drop_namespace(
            id=object_id_str(request.id, self.config.delimiter(), request),
            drop_namespace_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        self.namespace_api.namespace_exists(
            id=object_id_str(request.id, self.config.delimiter(), request),
            namespace_exists_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        return self.table_api.list_tables(
            id=object_id_str(request.id, self.config.delimiter(), request),
            delimiter=self.config.delimiter(),
            page_token=request.page_token,
            limit=request.limit,
            _headers=self.config.additional_headers(),
        )
    
    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        return self.table_api.describe_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            describe_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        return self.table_api.register_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            register_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def table_exists(self, request: TableExistsRequest) -> None:
        self.table_api.table_exists(
            id=object_id_str(request.id, self.config.delimiter(), request),
            table_exists_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        return self.table_api.drop_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            drop_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def deregister_table(self, request: DeregisterTableRequest) -> DeregisterTableResponse:
        return self.table_api.deregister_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            deregister_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )

    def count_table_rows(self, request: CountTableRowsRequest) -> int:
        return self.table_api.count_table_rows(
            id=object_id_str(request.id, self.config.delimiter(), request),
            count_table_rows_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        table_properties = json.dumps(request.properties) if request.properties else None
        return self.table_api.create_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            x_lance_table_location=request.location,
            body=request_data,
            delimiter=self.config.delimiter(),
            x_lance_table_properties=table_properties,
            _headers=self.config.additional_headers(),
        )
    
    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        return self.table_api.create_empty_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            create_empty_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def insert_into_table(self, request: InsertIntoTableRequest, request_data: bytes) -> InsertIntoTableResponse:
        mode = request.mode if request.mode else None
        
        return self.table_api.insert_into_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            body=request_data,
            delimiter=self.config.delimiter(),
            mode=mode,
            _headers=self.config.additional_headers(),
        )
    
    def merge_insert_into_table(
        self, request: MergeInsertIntoTableRequest, request_data: bytes
    ) -> MergeInsertIntoTableResponse:
        return self.table_api.merge_insert_into_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            on=request.on,
            body=request_data,
            delimiter=self.config.delimiter(),
            when_matched_update_all=request.when_matched_update_all,
            when_matched_update_all_filt=request.when_matched_update_all_filt,
            when_not_matched_insert_all=request.when_not_matched_insert_all,
            when_not_matched_by_source_delete=request.when_not_matched_by_source_delete,
            when_not_matched_by_source_delete_filt=request.when_not_matched_by_source_delete_filt,
            _headers=self.config.additional_headers(),
        )
    
    def update_table(self, request: UpdateTableRequest) -> UpdateTableResponse:
        return self.table_api.update_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            update_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def delete_from_table(self, request: DeleteFromTableRequest) -> DeleteFromTableResponse:
        return self.table_api.delete_from_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            delete_from_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def query_table(self, request: QueryTableRequest) -> bytes:
        return self.table_api.query_table(
            id=object_id_str(request.id, self.config.delimiter(), request),
            query_table_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def create_table_index(self, request: CreateTableIndexRequest) -> CreateTableIndexResponse:
        return self.table_api.create_table_index(
            id=object_id_str(request.id, self.config.delimiter(), request),
            create_table_index_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def list_table_indices(self, request: ListTableIndicesRequest) -> ListTableIndicesResponse:
        return self.table_api.list_table_indices(
            id=object_id_str(request.id, self.config.delimiter(), request),
            list_table_indices_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def describe_table_index_stats(
        self, request: DescribeTableIndexStatsRequest, index_name: str
    ) -> DescribeTableIndexStatsResponse:
        return self.table_api.describe_table_index_stats(
            id=object_id_str(request.id, self.config.delimiter(), request),
            index_name=index_name,
            describe_table_index_stats_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def describe_transaction(self, request: DescribeTransactionRequest) -> DescribeTransactionResponse:
        return self.transaction_api.describe_transaction(
            id=object_id_str(request.id, self.config.delimiter(), request),
            describe_transaction_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )
    
    def alter_transaction(self, request: AlterTransactionRequest) -> AlterTransactionResponse:
        return self.transaction_api.alter_transaction(
            id=object_id_str(request.id, self.config.delimiter(), request),
            alter_transaction_request=request,
            delimiter=self.config.delimiter(),
            _headers=self.config.additional_headers(),
        )