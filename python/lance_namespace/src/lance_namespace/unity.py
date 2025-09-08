"""
Unity Catalog namespace implementation for Lance.
"""

import json
import logging
import os
from typing import Dict, List, Optional, Any
from dataclasses import dataclass, field
import urllib3
import urllib.parse
from urllib.error import HTTPError
import io

import pyarrow as pa
import pyarrow.ipc as ipc
import lance

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
    TableExistsRequest,
    DropTableRequest,
    DropTableResponse,
    CreateTableRequest,
    CreateTableResponse,
    CreateEmptyTableRequest,
    CreateEmptyTableResponse,
)

from .namespace import LanceNamespace


logger = logging.getLogger(__name__)


@dataclass
class UnityNamespaceConfig:
    """Configuration for Unity Catalog namespace."""
    
    ENDPOINT = "unity.endpoint"
    CATALOG = "unity.catalog"
    ROOT = "unity.root"
    AUTH_TOKEN = "unity.auth_token"
    CONNECT_TIMEOUT = "unity.connect_timeout_millis"
    READ_TIMEOUT = "unity.read_timeout_millis"
    MAX_RETRIES = "unity.max_retries"
    
    endpoint: str
    catalog: str
    root: str
    auth_token: Optional[str] = None
    connect_timeout: int = 10000
    read_timeout: int = 300000
    max_retries: int = 3
    
    def __init__(self, properties: Dict[str, str]):
        self.endpoint = properties.get(self.ENDPOINT)
        if not self.endpoint:
            raise ValueError(f"Required property {self.ENDPOINT} is not set")
            
        self.catalog = properties.get(self.CATALOG, "unity")
        self.root = properties.get(self.ROOT, "/tmp/lance")
        self.auth_token = properties.get(self.AUTH_TOKEN)
        self.connect_timeout = int(properties.get(self.CONNECT_TIMEOUT, "10000"))
        self.read_timeout = int(properties.get(self.READ_TIMEOUT, "300000"))
        self.max_retries = int(properties.get(self.MAX_RETRIES, "3"))
    
    def get_full_api_url(self) -> str:
        """Get the full API URL with /api/2.1 path."""
        base = self.endpoint.rstrip('/')
        if not base.endswith('/api/2.1'):
            base = f"{base}/api/2.1"
        return base


@dataclass
class SchemaInfo:
    """Unity schema information."""
    name: str
    catalog_name: str
    comment: Optional[str] = None
    properties: Dict[str, str] = field(default_factory=dict)
    full_name: Optional[str] = None
    created_at: Optional[int] = None
    updated_at: Optional[int] = None
    schema_id: Optional[str] = None


@dataclass
class ColumnInfo:
    """Unity column information."""
    name: str
    type_text: str
    type_json: str
    type_name: str
    position: int
    nullable: bool = True
    comment: Optional[str] = None
    type_precision: Optional[int] = None
    type_scale: Optional[int] = None
    type_interval_type: Optional[str] = None
    partition_index: Optional[int] = None


@dataclass
class TableInfo:
    """Unity table information."""
    name: str
    catalog_name: str
    schema_name: str
    table_type: str
    data_source_format: str
    columns: List[ColumnInfo]
    storage_location: str
    comment: Optional[str] = None
    properties: Dict[str, str] = field(default_factory=dict)
    created_at: Optional[int] = None
    updated_at: Optional[int] = None
    table_id: Optional[str] = None
    full_name: Optional[str] = None


@dataclass
class CreateSchema:
    """Request to create a schema."""
    name: str
    catalog_name: str
    properties: Optional[Dict[str, str]] = None


@dataclass
class CreateTable:
    """Request to create a table."""
    name: str
    catalog_name: str
    schema_name: str
    table_type: str
    data_source_format: str
    columns: List[ColumnInfo]
    storage_location: str
    properties: Optional[Dict[str, str]] = None


class RestClient:
    """Simple REST client for Unity Catalog API."""
    
    def __init__(self, base_url: str, headers: Optional[Dict[str, str]] = None,
                 connect_timeout: int = 10, read_timeout: int = 300, max_retries: int = 3):
        self.base_url = base_url.rstrip('/')
        self.headers = headers or {}
        self.headers['Content-Type'] = 'application/json'
        self.headers['Accept'] = 'application/json'
        
        # Create urllib3 pool manager
        timeout = urllib3.Timeout(connect=connect_timeout/1000, read=read_timeout/1000)
        self.http = urllib3.PoolManager(
            timeout=timeout,
            retries=urllib3.Retry(total=max_retries, backoff_factor=0.3)
        )
    
    def _make_request(self, method: str, path: str, params: Optional[Dict[str, str]] = None,
                      body: Optional[Any] = None) -> Any:
        """Make HTTP request to Unity API."""
        url = f"{self.base_url}{path}"
        
        # Add query parameters
        if params:
            query_string = urllib.parse.urlencode(params)
            url = f"{url}?{query_string}"
        
        # Prepare body
        body_data = None
        if body is not None:
            if hasattr(body, '__dict__'):
                # Convert dataclass to dict
                body_dict = self._dataclass_to_dict(body)
            else:
                body_dict = body
            body_data = json.dumps(body_dict).encode('utf-8')
        
        try:
            response = self.http.request(
                method,
                url,
                headers=self.headers,
                body=body_data
            )
            
            if response.status >= 400:
                raise RestClientException(response.status, response.data.decode('utf-8'))
            
            if response.data:
                return json.loads(response.data.decode('utf-8'))
            return None
            
        except urllib3.exceptions.HTTPError as e:
            raise RestClientException(500, str(e))
    
    def _dataclass_to_dict(self, obj: Any) -> Dict[str, Any]:
        """Convert dataclass to dictionary, handling nested structures."""
        if hasattr(obj, '__dict__'):
            result = {}
            for key, value in obj.__dict__.items():
                if value is not None:
                    if isinstance(value, list):
                        result[key] = [self._dataclass_to_dict(item) for item in value]
                    elif hasattr(value, '__dict__'):
                        result[key] = self._dataclass_to_dict(value)
                    else:
                        result[key] = value
            return result
        return obj
    
    def get(self, path: str, params: Optional[Dict[str, str]] = None, 
            response_class: Optional[type] = None) -> Any:
        """Make GET request."""
        response = self._make_request('GET', path, params=params)
        if response_class and response:
            return self._dict_to_dataclass(response, response_class)
        return response
    
    def post(self, path: str, body: Any, response_class: Optional[type] = None) -> Any:
        """Make POST request."""
        response = self._make_request('POST', path, body=body)
        if response_class and response:
            return self._dict_to_dataclass(response, response_class)
        return response
    
    def delete(self, path: str, params: Optional[Dict[str, str]] = None) -> None:
        """Make DELETE request."""
        self._make_request('DELETE', path, params=params)
    
    def _dict_to_dataclass(self, data: Dict[str, Any], cls: type) -> Any:
        """Convert dictionary to dataclass instance."""
        if cls == SchemaInfo:
            return SchemaInfo(**data)
        elif cls == TableInfo:
            # Handle nested ColumnInfo objects
            columns_data = data.get('columns', [])
            columns = [ColumnInfo(**col) for col in columns_data]
            data['columns'] = columns
            return TableInfo(**data)
        return data
    
    def close(self):
        """Close the HTTP connection pool."""
        self.http.clear()


class RestClientException(Exception):
    """Exception raised by REST client."""
    
    def __init__(self, status_code: int, response_body: str):
        self.status_code = status_code
        self.response_body = response_body
        super().__init__(f"HTTP {status_code}: {response_body}")


class LanceNamespaceException(Exception):
    """Exception for Lance namespace operations."""
    
    def __init__(self, status_code: int, message: str):
        self.status_code = status_code
        super().__init__(message)
    
    @classmethod
    def not_found(cls, message: str, error_code: str, resource: str, details: str = ""):
        """Create a not found exception."""
        full_message = f"{message} [{error_code}]: {resource}"
        if details:
            full_message += f" - {details}"
        return cls(404, full_message)
    
    @classmethod
    def bad_request(cls, message: str, error_code: str, resource: str, details: str = ""):
        """Create a bad request exception."""
        full_message = f"{message} [{error_code}]: {resource}"
        if details:
            full_message += f" - {details}"
        return cls(400, full_message)
    
    @classmethod
    def conflict(cls, message: str, error_code: str, resource: str, details: str = ""):
        """Create a conflict exception."""
        full_message = f"{message} [{error_code}]: {resource}"
        if details:
            full_message += f" - {details}"
        return cls(409, full_message)


class UnityNamespace(LanceNamespace):
    """Unity Catalog namespace implementation for Lance."""
    
    TABLE_TYPE_LANCE = "lance"
    TABLE_TYPE_EXTERNAL = "EXTERNAL"
    MANAGED_BY_KEY = "managed_by"
    TABLE_TYPE_KEY = "table_type"
    VERSION_KEY = "version"
    
    def __init__(self, **properties):
        """Initialize Unity namespace with configuration properties."""
        self.config = UnityNamespaceConfig(properties)
        
        # Build REST client with authentication if provided
        headers = {}
        if self.config.auth_token:
            headers['Authorization'] = f"Bearer {self.config.auth_token}"
        
        self.rest_client = RestClient(
            base_url=self.config.get_full_api_url(),
            headers=headers,
            connect_timeout=self.config.connect_timeout,
            read_timeout=self.config.read_timeout,
            max_retries=self.config.max_retries
        )
        
        logger.info(f"Initialized Unity namespace with endpoint: {self.config.endpoint}")
    
    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List namespaces."""
        ns_id = self._parse_identifier(request.id)
        
        # Unity supports 3-level namespace: catalog.schema.table
        if len(ns_id) > 2:
            raise ValueError(f"Expect at most 2-level namespace but get {'.'.join(ns_id)}")
        
        try:
            namespaces = []
            
            if len(ns_id) == 0:
                # Return the configured catalog as the only top-level namespace
                namespaces = [self.config.catalog]
            elif len(ns_id) == 1:
                # List schemas in the catalog
                catalog = ns_id[0]
                if catalog != self.config.catalog:
                    raise LanceNamespaceException.not_found(
                        "Catalog not found",
                        "CATALOG_NOT_FOUND",
                        catalog,
                        f"Expected: {self.config.catalog}"
                    )
                
                params = {'catalog_name': catalog}
                if request.limit:
                    params['max_results'] = str(request.limit)
                if request.page_token:
                    params['page_token'] = request.page_token
                
                response = self.rest_client.get('/schemas', params=params)
                
                if response and 'schemas' in response:
                    namespaces = [schema['name'] for schema in response['schemas']]
            
            # Sort and deduplicate
            namespaces = sorted(set(namespaces))
            
            response = ListNamespacesResponse()
            response.namespaces = namespaces
            return response
            
        except Exception as e:
            if isinstance(e, LanceNamespaceException):
                raise
            raise LanceNamespaceException(500, f"Failed to list namespaces: {e}")
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        """Create a new namespace."""
        ns_id = self._parse_identifier(request.id)
        
        if len(ns_id) != 2:
            raise ValueError(f"Expect a 2-level namespace but get {'.'.join(ns_id)}")
        
        catalog = ns_id[0]
        schema = ns_id[1]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.bad_request(
                "Cannot create namespace in catalog",
                "INVALID_CATALOG",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            create_schema = CreateSchema(
                name=schema,
                catalog_name=catalog,
                properties=request.properties
            )
            
            schema_info = self.rest_client.post('/schemas', create_schema, SchemaInfo)
            
            response = CreateNamespaceResponse()
            response.properties = schema_info.properties
            return response
            
        except RestClientException as e:
            if e.status_code == 409:
                raise LanceNamespaceException.conflict(
                    "Namespace already exists",
                    "NAMESPACE_EXISTS",
                    '.'.join(request.id),
                    e.response_body
                )
            raise LanceNamespaceException(500, f"Failed to create namespace: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to create namespace: {e}")
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        """Describe a namespace."""
        ns_id = self._parse_identifier(request.id)
        
        if len(ns_id) != 2:
            raise ValueError(f"Expect a 2-level namespace but get {'.'.join(ns_id)}")
        
        catalog = ns_id[0]
        schema = ns_id[1]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.not_found(
                "Catalog not found",
                "CATALOG_NOT_FOUND", 
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            full_name = f"{catalog}.{schema}"
            schema_info = self.rest_client.get(f"/schemas/{full_name}", response_class=SchemaInfo)
            
            response = DescribeNamespaceResponse()
            response.properties = schema_info.properties
            return response
            
        except RestClientException as e:
            if e.status_code == 404:
                raise LanceNamespaceException.not_found(
                    "Namespace not found",
                    "NAMESPACE_NOT_FOUND",
                    '.'.join(request.id),
                    e.response_body
                )
            raise LanceNamespaceException(500, f"Failed to describe namespace: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to describe namespace: {e}")
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check if a namespace exists."""
        describe_request = DescribeNamespaceRequest()
        describe_request.id = request.id
        self.describe_namespace(describe_request)
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop a namespace."""
        ns_id = self._parse_identifier(request.id)
        
        if len(ns_id) != 2:
            raise ValueError(f"Expect a 2-level namespace but get {'.'.join(ns_id)}")
        
        catalog = ns_id[0]
        schema = ns_id[1]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.bad_request(
                "Cannot drop namespace in catalog",
                "INVALID_CATALOG",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            full_name = f"{catalog}.{schema}"
            params = {}
            if request.behavior == DropNamespaceRequest.BehaviorEnum.CASCADE:
                params['force'] = 'true'
            
            self.rest_client.delete(f"/schemas/{full_name}", params=params)
            
            return DropNamespaceResponse()
            
        except RestClientException as e:
            if e.status_code == 404:
                # Namespace doesn't exist, return success
                return DropNamespaceResponse()
            raise LanceNamespaceException(500, f"Failed to drop namespace: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to drop namespace: {e}")
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List tables in a namespace."""
        ns_id = self._parse_identifier(request.id)
        
        if len(ns_id) != 2:
            raise ValueError(f"Expect a 2-level namespace but get {'.'.join(ns_id)}")
        
        catalog = ns_id[0]
        schema = ns_id[1]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.not_found(
                "Catalog not found",
                "CATALOG_NOT_FOUND",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            params = {
                'catalog_name': catalog,
                'schema_name': schema
            }
            if request.limit:
                params['max_results'] = str(request.limit)
            if request.page_token:
                params['page_token'] = request.page_token
            
            response = self.rest_client.get('/tables', params=params)
            
            tables = []
            if response and 'tables' in response:
                # Filter only Lance tables
                for table_data in response['tables']:
                    if self._is_lance_table(table_data):
                        tables.append(table_data['name'])
            
            # Sort and deduplicate
            tables = sorted(set(tables))
            
            response = ListTablesResponse()
            response.tables = tables
            return response
            
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to list tables: {e}")
    
    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        """Create a new table with data from Arrow IPC stream."""
        if not request_data:
            raise ValueError("Request data (Arrow IPC stream) is required for createTable")
        
        table_id = self._parse_identifier(request.id)
        
        if len(table_id) != 3:
            raise ValueError(f"Expect a 3-level table identifier but get {'.'.join(table_id)}")
        
        catalog = table_id[0]
        schema = table_id[1]
        table = table_id[2]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.bad_request(
                "Cannot create table in catalog",
                "INVALID_CATALOG",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            # First create an empty Lance table dataset
            table_path = f"{self.config.root}/{catalog}/{schema}/{table}"
            
            # Extract schema from Arrow IPC stream
            arrow_schema = self._extract_schema_from_ipc(request_data)
            
            # Create Lance dataset
            lance.write_dataset(
                pa.table([], schema=arrow_schema),
                table_path,
                mode="create"
            )
            
            # Create Unity table metadata
            columns = self._convert_arrow_schema_to_unity_columns(arrow_schema)
            
            properties = {
                self.TABLE_TYPE_KEY: self.TABLE_TYPE_LANCE,
                self.MANAGED_BY_KEY: "storage",
                self.VERSION_KEY: "0"
            }
            if request.properties:
                properties.update(request.properties)
            
            create_table = CreateTable(
                name=table,
                catalog_name=catalog,
                schema_name=schema,
                table_type=self.TABLE_TYPE_EXTERNAL,
                data_source_format="TEXT",  # Unity doesn't recognize LANCE format
                columns=columns,
                storage_location=table_path,
                properties=properties
            )
            
            table_info = self.rest_client.post('/tables', create_table, TableInfo)
            
            response = CreateTableResponse()
            response.location = table_path
            response.version = 1
            response.properties = table_info.properties
            return response
            
        except RestClientException as e:
            if e.status_code == 409:
                raise LanceNamespaceException.conflict(
                    "Table already exists",
                    "TABLE_EXISTS",
                    '.'.join(request.id),
                    e.response_body
                )
            raise LanceNamespaceException(500, f"Failed to create table: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to create table: {e}")
    
    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only operation)."""
        table_id = self._parse_identifier(request.id)
        
        if len(table_id) != 3:
            raise ValueError(f"Expect a 3-level table identifier but get {'.'.join(table_id)}")
        
        catalog = table_id[0]
        schema = table_id[1]
        table = table_id[2]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.bad_request(
                "Cannot create empty table in catalog",
                "INVALID_CATALOG",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            # Determine table location
            table_path = request.location
            if not table_path:
                table_path = f"{self.config.root}/{catalog}/{schema}/{table}"
            
            # Create Unity table metadata without creating Lance dataset
            # For empty table, create minimal schema with just an ID column
            columns = [
                ColumnInfo(
                    name="__placeholder_id",
                    type_text="BIGINT",
                    type_json='{"type":"long"}',
                    type_name="BIGINT",
                    position=0,
                    nullable=True
                )
            ]
            
            properties = {
                self.TABLE_TYPE_KEY: self.TABLE_TYPE_LANCE,
                self.MANAGED_BY_KEY: "catalog"
            }
            if request.properties:
                properties.update(request.properties)
            
            create_table = CreateTable(
                name=table,
                catalog_name=catalog,
                schema_name=schema,
                table_type=self.TABLE_TYPE_EXTERNAL,
                data_source_format="TEXT",
                columns=columns,
                storage_location=table_path,
                properties=properties
            )
            
            table_info = self.rest_client.post('/tables', create_table, TableInfo)
            
            response = CreateEmptyTableResponse()
            response.location = table_path
            response.properties = table_info.properties
            return response
            
        except RestClientException as e:
            if e.status_code == 409:
                raise LanceNamespaceException.conflict(
                    "Table already exists",
                    "TABLE_EXISTS",
                    '.'.join(request.id),
                    e.response_body
                )
            raise LanceNamespaceException(500, f"Failed to create empty table: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to create empty table: {e}")
    
    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table."""
        table_id = self._parse_identifier(request.id)
        
        if len(table_id) != 3:
            raise ValueError(f"Expect a 3-level table identifier but get {'.'.join(table_id)}")
        
        catalog = table_id[0]
        schema = table_id[1]
        table = table_id[2]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.not_found(
                "Catalog not found",
                "CATALOG_NOT_FOUND",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            full_name = f"{catalog}.{schema}.{table}"
            table_info = self.rest_client.get(f"/tables/{full_name}", response_class=TableInfo)
            
            if not self._is_lance_table_info(table_info):
                raise LanceNamespaceException.bad_request(
                    "Not a Lance table",
                    "INVALID_TABLE",
                    '.'.join(request.id),
                    "Table is not managed by Lance"
                )
            
            # Get the actual schema from the Lance dataset
            dataset = lance.dataset(table_info.storage_location)
            arrow_schema = dataset.schema
            
            response = DescribeTableResponse()
            response.location = table_info.storage_location
            response.properties = table_info.properties
            # TODO: Convert Arrow schema to JsonArrowSchema if needed
            
            return response
            
        except RestClientException as e:
            if e.status_code == 404:
                raise LanceNamespaceException.not_found(
                    "Table not found",
                    "TABLE_NOT_FOUND",
                    '.'.join(request.id),
                    e.response_body
                )
            raise LanceNamespaceException(500, f"Failed to describe table: {e}")
        except Exception as e:
            raise LanceNamespaceException(500, f"Failed to describe table: {e}")
    
    def table_exists(self, request: TableExistsRequest) -> None:
        """Check if a table exists."""
        describe_request = DescribeTableRequest()
        describe_request.id = request.id
        self.describe_table(describe_request)
    
    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table."""
        table_id = self._parse_identifier(request.id)
        
        if len(table_id) != 3:
            raise ValueError(f"Expect a 3-level table identifier but get {'.'.join(table_id)}")
        
        catalog = table_id[0]
        schema = table_id[1]
        table = table_id[2]
        
        if catalog != self.config.catalog:
            raise LanceNamespaceException.bad_request(
                "Cannot drop table in catalog",
                "INVALID_CATALOG",
                catalog,
                f"Expected: {self.config.catalog}"
            )
        
        try:
            full_name = f"{catalog}.{schema}.{table}"
            
            # First get the table info to check if it's a Lance table
            try:
                table_info = self.rest_client.get(f"/tables/{full_name}", response_class=TableInfo)
            except RestClientException as e:
                if e.status_code == 404:
                    response = DropTableResponse()
                    response.id = request.id
                    return response
                raise
            
            if not self._is_lance_table_info(table_info):
                raise LanceNamespaceException.bad_request(
                    "Not a Lance table",
                    "INVALID_TABLE",
                    '.'.join(request.id),
                    "Table is not managed by Lance"
                )
            
            # Delete from Unity
            self.rest_client.delete(f"/tables/{full_name}")
            
            # Delete Lance dataset data
            try:
                import shutil
                if os.path.exists(table_info.storage_location):
                    shutil.rmtree(table_info.storage_location)
            except Exception as e:
                # Log warning but continue - Unity metadata already deleted
                logger.warning(f"Failed to delete Lance dataset at {table_info.storage_location}: {e}")
            
            response = DropTableResponse()
            response.id = request.id
            response.location = table_info.storage_location
            return response
            
        except Exception as e:
            if isinstance(e, LanceNamespaceException):
                raise
            raise LanceNamespaceException(500, f"Failed to drop table: {e}")
    
    def close(self):
        """Close the namespace connection."""
        if self.rest_client:
            self.rest_client.close()
    
    def _parse_identifier(self, identifier: List[str]) -> List[str]:
        """Parse identifier list."""
        return identifier if identifier else []
    
    def _is_lance_table(self, table_data: Dict[str, Any]) -> bool:
        """Check if a table dictionary represents a Lance table."""
        if not table_data or 'properties' not in table_data:
            return False
        properties = table_data.get('properties', {})
        table_type = properties.get(self.TABLE_TYPE_KEY)
        return table_type and table_type.lower() == self.TABLE_TYPE_LANCE.lower()
    
    def _is_lance_table_info(self, table_info: TableInfo) -> bool:
        """Check if a TableInfo represents a Lance table."""
        if not table_info or not table_info.properties:
            return False
        table_type = table_info.properties.get(self.TABLE_TYPE_KEY)
        return table_type and table_type.lower() == self.TABLE_TYPE_LANCE.lower()
    
    def _extract_schema_from_ipc(self, ipc_data: bytes) -> pa.Schema:
        """Extract Arrow schema from IPC stream."""
        try:
            reader = ipc.open_stream(io.BytesIO(ipc_data))
            return reader.schema
        except Exception as e:
            raise LanceNamespaceException.bad_request(
                f"Invalid Arrow IPC stream: {e}",
                "INVALID_ARROW_IPC",
                "",
                "Failed to extract schema from Arrow IPC stream"
            )
    
    def _convert_arrow_schema_to_unity_columns(self, arrow_schema: pa.Schema) -> List[ColumnInfo]:
        """Convert Arrow schema to Unity column definitions."""
        columns = []
        for i, field in enumerate(arrow_schema):
            unity_type = self._convert_arrow_type_to_unity_type(field.type)
            unity_type_json = self._convert_arrow_type_to_unity_type_json(field.type)
            
            column = ColumnInfo(
                name=field.name,
                type_text=unity_type,
                type_json=unity_type_json,
                type_name=unity_type,
                position=i,
                nullable=field.nullable
            )
            columns.append(column)
        
        return columns
    
    def _convert_arrow_type_to_unity_type(self, arrow_type: pa.DataType) -> str:
        """Convert Arrow type to Unity type string."""
        if pa.types.is_string(arrow_type) or pa.types.is_large_string(arrow_type):
            return "STRING"
        elif pa.types.is_int32(arrow_type):
            return "INT"
        elif pa.types.is_int64(arrow_type):
            return "BIGINT"
        elif pa.types.is_float32(arrow_type):
            return "FLOAT"
        elif pa.types.is_float64(arrow_type):
            return "DOUBLE"
        elif pa.types.is_boolean(arrow_type):
            return "BOOLEAN"
        elif pa.types.is_date(arrow_type):
            return "DATE"
        elif pa.types.is_timestamp(arrow_type):
            return "TIMESTAMP"
        else:
            # Default fallback
            return "STRING"
    
    def _convert_arrow_type_to_unity_type_json(self, arrow_type: pa.DataType) -> str:
        """Convert Arrow type to Unity type JSON string."""
        if pa.types.is_string(arrow_type) or pa.types.is_large_string(arrow_type):
            return '{"type":"string"}'
        elif pa.types.is_int32(arrow_type):
            return '{"type":"integer"}'
        elif pa.types.is_int64(arrow_type):
            return '{"type":"long"}'
        elif pa.types.is_float32(arrow_type):
            return '{"type":"float"}'
        elif pa.types.is_float64(arrow_type):
            return '{"type":"double"}'
        elif pa.types.is_boolean(arrow_type):
            return '{"type":"boolean"}'
        elif pa.types.is_date(arrow_type):
            return '{"type":"date"}'
        elif pa.types.is_timestamp(arrow_type):
            return '{"type":"timestamp"}'
        else:
            # Default fallback
            return '{"type":"string"}'