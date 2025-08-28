"""
Lance Glue Namespace implementation using AWS Glue Data Catalog.
"""
from typing import Dict, List, Optional, Any, Union
from urllib.parse import urlparse
import os

try:
    import boto3
    from botocore.config import Config
    HAS_BOTO3 = True
except ImportError:
    boto3 = None
    Config = None
    HAS_BOTO3 = False

import lance
import pyarrow as pa

from lance_namespace.namespace import LanceNamespace
from lance_namespace.schema import (
    convert_json_arrow_schema_to_pyarrow,
    convert_json_arrow_type_to_pyarrow,
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
    CreateTableRequest,
    CreateTableResponse,
    CreateEmptyTableRequest,
    CreateEmptyTableResponse,
    DropTableRequest,
    DropTableResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    RegisterTableRequest,
    RegisterTableResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
    TableExistsRequest,
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
)


LANCE_TABLE_TYPE = "LANCE"
TABLE_TYPE = "table_type"
LOCATION = "location"
EXTERNAL_TABLE = "EXTERNAL_TABLE"


class GlueNamespace(LanceNamespace):
    """Lance Glue Namespace implementation using AWS Glue Data Catalog.
    
    This namespace implementation integrates Lance with AWS Glue Data Catalog,
    allowing you to manage Lance table metadata in a centralized AWS service.
    
    Usage Examples:
    
        >>> from lance_namespace import connect
        
        >>> # Connect using default AWS credentials
        >>> namespace = connect("glue", {
        ...     "region": "us-east-1"
        ... })
        
        >>> # Connect with specific credentials
        >>> namespace = connect("glue", {
        ...     "region": "us-east-1",
        ...     "access_key_id": "YOUR_ACCESS_KEY",
        ...     "secret_access_key": "YOUR_SECRET_KEY"
        ... })
        
        >>> # Connect with custom catalog ID and endpoint
        >>> namespace = connect("glue", {
        ...     "region": "us-east-1",
        ...     "catalog_id": "123456789012",
        ...     "endpoint": "https://glue.example.com"
        ... })
        
        >>> # Create a database (namespace)
        >>> from lance_namespace_urllib3_client.models import CreateNamespaceRequest
        >>> namespace.create_namespace(CreateNamespaceRequest(
        ...     id=["my_database"],
        ...     properties={"description": "My Lance tables"}
        ... ))
        
        >>> # List databases
        >>> from lance_namespace_urllib3_client.models import ListNamespacesRequest
        >>> response = namespace.list_namespaces(ListNamespacesRequest())
        >>> print(response.namespaces)
        
        >>> # Create a table
        >>> from lance_namespace_urllib3_client.models import CreateTableRequest
        >>> namespace.create_table(CreateTableRequest(
        ...     id=["my_database", "my_table"],
        ...     var_schema=arrow_schema  # PyArrow schema
        ... ), data_bytes)
    
    Note:
        Requires boto3 to be installed: pip install lance-namespace[glue]
    """
    
    def __init__(self, **properties):
        """Initialize the Glue namespace.
        
        Args:
            catalog_id: Glue catalog ID (AWS account ID)
            endpoint: Optional custom Glue endpoint
            region: AWS region for Glue
            access_key_id: AWS access key ID
            secret_access_key: AWS secret access key
            session_token: AWS session token
            profile_name: AWS profile name
            max_retries: Maximum number of retries
            retry_mode: Retry mode (standard, adaptive, legacy)
            root: Storage root location of the lakehouse on Glue catalog
            storage.*: Storage configuration properties for Lance datasets
            **properties: Additional configuration properties
        """
        if not HAS_BOTO3:
            raise ImportError(
                "boto3 is required for GlueNamespace. "
                "Install with: pip install lance-namespace[glue]"
            )
        
        self.config = GlueNamespaceConfig(properties)
        self._glue = None  # Lazy initialization to support pickling
    
    @property
    def glue(self):
        """Get the Glue client, initializing it if necessary."""
        if self._glue is None:
            self._glue = self._initialize_glue_client()
        return self._glue
    
    def _initialize_glue_client(self):
        """Initialize the AWS Glue client."""
        session = boto3.Session(
            profile_name=self.config.profile_name,
            region_name=self.config.region,
            aws_access_key_id=self.config.access_key_id,
            aws_secret_access_key=self.config.secret_access_key,
            aws_session_token=self.config.session_token,
        )
        
        config_kwargs = {}
        if self.config.max_retries:
            config_kwargs['retries'] = {
                'max_attempts': self.config.max_retries,
                'mode': self.config.retry_mode or 'standard'
            }
        
        glue_client = session.client(
            'glue',
            endpoint_url=self.config.endpoint,
            config=Config(**config_kwargs) if config_kwargs else None
        )
        
        # Register catalog ID if provided
        if self.config.catalog_id:
            self._register_catalog_id(glue_client, self.config.catalog_id)
        
        return glue_client
    
    def _register_catalog_id(self, glue_client, catalog_id):
        """Register the Glue Catalog ID with the client."""
        event_system = glue_client.meta.events
        
        def add_catalog_id(params, **kwargs):
            if 'CatalogId' not in params:
                params['CatalogId'] = catalog_id
        
        event_system.register('provide-client-params.glue', add_catalog_id)
    
    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List namespaces (databases) in Glue."""
        # Only list databases if we're at root namespace (no id or empty id)
        if request.id and len(request.id) > 0:
            # Hierarchical namespaces are not supported in Glue
            return ListNamespacesResponse(namespaces=[])
        
        try:
            databases = []
            next_token = None
            
            while True:
                if next_token:
                    response = self.glue.get_databases(NextToken=next_token)
                else:
                    response = self.glue.get_databases()
                
                for db in response.get('DatabaseList', []):
                    databases.append(db['Name'])
                
                next_token = response.get('NextToken')
                if not next_token:
                    break
            
            return ListNamespacesResponse(namespaces=databases)
        except Exception as e:
            raise RuntimeError(f"Failed to list namespaces: {e}")
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        """Describe a namespace (database) in Glue."""
        # Handle root namespace
        if not request.id or len(request.id) == 0:
            # Root namespace always exists
            properties = {}
            if self.config.root:
                properties['location'] = self.config.root
            properties['description'] = 'Root Glue catalog namespace'
            return DescribeNamespaceResponse(properties=properties)
        
        if len(request.id) != 1:
            raise ValueError("Glue namespace requires exactly one level identifier")
        
        database_name = request.id[0]
        
        try:
            response = self.glue.get_database(Name=database_name)
            database = response['Database']
            
            properties = database.get('Parameters', {})
            if 'LocationUri' in database:
                properties['location'] = database['LocationUri']
            if 'Description' in database:
                properties['description'] = database['Description']
            
            return DescribeNamespaceResponse(properties=properties)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Namespace does not exist: {database_name}")
            raise RuntimeError(f"Failed to describe namespace: {e}")
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        """Create a namespace (database) in Glue."""
        # Handle root namespace
        if not request.id or len(request.id) == 0:
            raise RuntimeError("Root namespace already exists")
        
        if len(request.id) != 1:
            raise ValueError("Glue namespace requires exactly one level identifier")
        
        database_name = request.id[0]
        database_input = {'Name': database_name}
        
        if request.properties:
            parameters = {}
            for key, value in request.properties.items():
                if key == 'description':
                    database_input['Description'] = value
                elif key == 'location':
                    database_input['LocationUri'] = value
                else:
                    parameters[key] = value
            if parameters:
                database_input['Parameters'] = parameters
        
        try:
            self.glue.create_database(DatabaseInput=database_input)
            return CreateNamespaceResponse()
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'AlreadyExistsException':
                raise RuntimeError(f"Namespace already exists: {database_name}")
            raise RuntimeError(f"Failed to create namespace: {e}")
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop a namespace (database) in Glue."""
        # Handle root namespace
        if not request.id or len(request.id) == 0:
            raise RuntimeError("Cannot drop root namespace")
        
        if len(request.id) != 1:
            raise ValueError("Glue namespace requires exactly one level identifier")
        
        database_name = request.id[0]
        
        try:
            # Check if database is empty
            tables_response = self.glue.get_tables(DatabaseName=database_name)
            if tables_response.get('TableList'):
                raise RuntimeError(f"Cannot drop non-empty namespace: {database_name}")
            
            self.glue.delete_database(Name=database_name)
            return DropNamespaceResponse()
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Namespace does not exist: {database_name}")
            if isinstance(e, RuntimeError):
                raise
            raise RuntimeError(f"Failed to drop namespace: {e}")
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check if a namespace exists."""
        # Handle root namespace - it always exists
        if not request.id or len(request.id) == 0:
            return  # Root namespace always exists
        
        if len(request.id) != 1:
            raise ValueError("Glue namespace requires exactly one level identifier")
        
        database_name = request.id[0]
        
        try:
            self.glue.get_database(Name=database_name)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Namespace does not exist: {database_name}")
            raise RuntimeError(f"Failed to check namespace existence: {e}")
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List tables in a namespace."""
        # Handle root namespace - no tables at root level
        if not request.id or len(request.id) == 0:
            return ListTablesResponse(tables=[])
        
        if len(request.id) != 1:
            raise ValueError("Glue namespace requires exactly one level identifier")
        
        database_name = request.id[0]
        
        try:
            tables = []
            next_token = None
            
            while True:
                if next_token:
                    response = self.glue.get_tables(
                        DatabaseName=database_name,
                        NextToken=next_token
                    )
                else:
                    response = self.glue.get_tables(DatabaseName=database_name)
                
                for table in response.get('TableList', []):
                    # Only include Lance tables
                    if self._is_lance_table(table):
                        tables.append(table['Name'])
                
                next_token = response.get('NextToken')
                if not next_token:
                    break
            
            return ListTablesResponse(tables=tables)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Namespace does not exist: {database_name}")
            raise RuntimeError(f"Failed to list tables: {e}")
    
    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        try:
            response = self.glue.get_table(
                DatabaseName=database_name,
                Name=table_name
            )
            table = response['Table']
            
            if not self._is_lance_table(table):
                raise RuntimeError(f"Table is not a Lance table: {database_name}.{table_name}")
            
            location = table.get('StorageDescriptor', {}).get('Location')
            if not location:
                raise RuntimeError(f"Table has no location: {database_name}.{table_name}")
            
            return DescribeTableResponse(location=location)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Table does not exist: {database_name}.{table_name}")
            if isinstance(e, RuntimeError):
                raise
            raise RuntimeError(f"Failed to describe table: {e}")
    
    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        """Create a table with data from Arrow IPC stream."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        if not request_data:
            raise ValueError("Request data (Arrow IPC stream) is required for create_table")
        
        # Determine table location
        if request.location:
            table_location = request.location
        else:
            # Use default location pattern
            db_response = self.glue.get_database(Name=database_name)
            db_location = db_response['Database'].get('LocationUri', '')
            if db_location:
                table_location = f"{db_location}/{table_name}.lance"
            else:
                # Use S3 default location
                table_location = f"s3://lance-namespace/{database_name}/{table_name}.lance"
        
        # Extract table from Arrow IPC stream
        try:
            reader = pa.ipc.open_stream(pa.py_buffer(request_data))
            table = reader.read_all()
            schema = table.schema
        except Exception as e:
            raise ValueError(f"Invalid Arrow IPC stream: {e}")
        
        # Write Lance dataset
        lance.write_dataset(table, table_location, storage_options=self.config.storage_options)
        
        # Create Glue table entry
        table_input = {
            'Name': table_name,
            'TableType': EXTERNAL_TABLE,
            'Parameters': {
                TABLE_TYPE: LANCE_TABLE_TYPE,
            },
            'StorageDescriptor': {
                'Location': table_location,
                'Columns': self._convert_pyarrow_schema_to_glue_columns(schema)
            }
        }
        
        try:
            self.glue.create_table(
                DatabaseName=database_name,
                TableInput=table_input
            )
            return CreateTableResponse(location=table_location, version=1)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'AlreadyExistsException':
                raise RuntimeError(f"Table already exists: {database_name}.{table_name}")
            raise RuntimeError(f"Failed to create table: {e}")
    
    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only) in Glue catalog."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        # Determine table location
        if request.location:
            table_location = request.location
        else:
            # Use default location pattern
            db_response = self.glue.get_database(Name=database_name)
            db_location = db_response['Database'].get('LocationUri', '')
            if db_location:
                table_location = f"{db_location}/{table_name}.lance"
            else:
                # Use S3 default location
                table_location = f"s3://lance-namespace/{database_name}/{table_name}.lance"
        
        # Create a minimal schema for Glue (placeholder schema)
        glue_columns = [
            {
                'Name': '__placeholder_id',
                'Type': 'bigint',
                'Comment': 'Placeholder column for empty table'
            }
        ]
        
        # Create Glue table entry without creating actual Lance dataset
        table_input = {
            'Name': table_name,
            'TableType': EXTERNAL_TABLE,
            'Parameters': {
                TABLE_TYPE: LANCE_TABLE_TYPE,
                'empty_table': 'true',  # Mark as empty table
            },
            'StorageDescriptor': {
                'Location': table_location,
                'Columns': glue_columns,
                'InputFormat': 'org.apache.hadoop.mapred.TextInputFormat',
                'OutputFormat': 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat',
                'SerdeInfo': {
                    'SerializationLibrary': 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
                }
            }
        }
        
        # Add additional properties if specified
        if request.properties:
            table_input['Parameters'].update(request.properties)
        
        try:
            self.glue.create_table(
                DatabaseName=database_name,
                TableInput=table_input
            )
        except Exception as e:
            if 'AlreadyExistsException' in str(e):
                raise RuntimeError(f"Table already exists: {database_name}.{table_name}")
            raise RuntimeError(f"Failed to create empty table: {e}")
        
        return CreateEmptyTableResponse(location=table_location)
    
    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table - deletes both the Lance dataset and Glue catalog entry."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        try:
            # First get the table to find its location
            response = self.glue.get_table(
                DatabaseName=database_name,
                Name=table_name
            )
            table = response['Table']
            
            # Verify it's a Lance table
            if not self._is_lance_table(table):
                raise RuntimeError(f"Table is not a Lance table: {database_name}.{table_name}")
            
            # Get the table location
            location = table.get('StorageDescriptor', {}).get('Location')
            if not location:
                raise RuntimeError(f"Table has no location: {database_name}.{table_name}")
            
            # Drop the Lance dataset first
            lance_dataset = lance.dataset(location, storage_options=self.config.storage_options)
            lance_dataset.delete()
            
            # Then remove from Glue catalog
            self.glue.delete_table(
                DatabaseName=database_name,
                Name=table_name
            )
            return DropTableResponse()
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Table does not exist: {database_name}.{table_name}")
            if isinstance(e, RuntimeError):
                raise
            raise RuntimeError(f"Failed to drop table: {e}")
    
    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        """Register an existing Lance table in Glue."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        if not request.location:
            raise ValueError("Location is required to register a table")
        
        # Read Lance dataset to get schema
        try:
            dataset = lance.dataset(request.location, storage_options=self.config.storage_options)
            schema = dataset.schema
        except Exception as e:
            raise RuntimeError(f"Failed to read Lance dataset at {request.location}: {e}")
        
        # Create Glue table entry
        table_input = {
            'Name': table_name,
            'TableType': EXTERNAL_TABLE,
            'Parameters': {
                TABLE_TYPE: LANCE_TABLE_TYPE,
            },
            'StorageDescriptor': {
                'Location': request.location,
                'Columns': self._convert_pyarrow_schema_to_glue_columns(schema)
            }
        }
        
        try:
            self.glue.create_table(
                DatabaseName=database_name,
                TableInput=table_input
            )
            return RegisterTableResponse(location=request.location)
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'AlreadyExistsException':
                raise RuntimeError(f"Table already exists: {database_name}.{table_name}")
            raise RuntimeError(f"Failed to register table: {e}")
    
    def deregister_table(self, request: DeregisterTableRequest) -> DeregisterTableResponse:
        """Deregister a table - removes only the Glue catalog entry, keeps the Lance dataset."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        try:
            # Only remove from Glue catalog, don't delete the Lance dataset
            self.glue.delete_table(
                DatabaseName=database_name,
                Name=table_name
            )
            return DeregisterTableResponse()
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Table does not exist: {database_name}.{table_name}")
            raise RuntimeError(f"Failed to deregister table: {e}")
    
    def table_exists(self, request: TableExistsRequest) -> None:
        """Check if a table exists."""
        database_name, table_name = self._parse_table_identifier(request.id)
        
        try:
            response = self.glue.get_table(
                DatabaseName=database_name,
                Name=table_name
            )
            if not self._is_lance_table(response['Table']):
                raise RuntimeError(f"Table is not a Lance table: {database_name}.{table_name}")
        except Exception as e:
            error_name = e.__class__.__name__ if hasattr(e, '__class__') else ''
            if error_name == 'EntityNotFoundException':
                raise RuntimeError(f"Table does not exist: {database_name}.{table_name}")
            if isinstance(e, RuntimeError):
                raise
            raise RuntimeError(f"Failed to check table existence: {e}")
    
    def _parse_table_identifier(self, identifier: List[str]) -> tuple[str, str]:
        """Parse table identifier into database and table name."""
        if not identifier or len(identifier) != 2:
            raise ValueError("Table identifier must have exactly 2 parts: [database, table]")
        return identifier[0], identifier[1]
    
    def _is_lance_table(self, glue_table: Dict[str, Any]) -> bool:
        """Check if a Glue table is a Lance table."""
        return glue_table.get('Parameters', {}).get(TABLE_TYPE, '').upper() == LANCE_TABLE_TYPE
    
    def _convert_pyarrow_schema_to_glue_columns(self, schema: pa.Schema) -> List[Dict[str, str]]:
        """Convert PyArrow schema to Glue column definitions."""
        columns = []
        for field in schema:
            column = {
                'Name': field.name,
                'Type': self._convert_pyarrow_type_to_glue_type(field.type)
            }
            columns.append(column)
        return columns
    
    def _convert_pyarrow_type_to_glue_type(self, arrow_type: pa.DataType) -> str:
        """Convert PyArrow type to Glue/Hive type string."""
        if pa.types.is_boolean(arrow_type):
            return 'boolean'
        elif pa.types.is_int8(arrow_type) or pa.types.is_uint8(arrow_type):
            return 'tinyint'
        elif pa.types.is_int16(arrow_type) or pa.types.is_uint16(arrow_type):
            return 'smallint'
        elif pa.types.is_int32(arrow_type) or pa.types.is_uint32(arrow_type):
            return 'int'
        elif pa.types.is_int64(arrow_type) or pa.types.is_uint64(arrow_type):
            return 'bigint'
        elif pa.types.is_float32(arrow_type):
            return 'float'
        elif pa.types.is_float64(arrow_type):
            return 'double'
        elif pa.types.is_string(arrow_type):
            return 'string'
        elif pa.types.is_binary(arrow_type):
            return 'binary'
        elif pa.types.is_date32(arrow_type) or pa.types.is_date64(arrow_type):
            return 'date'
        elif pa.types.is_timestamp(arrow_type):
            return 'timestamp'
        elif pa.types.is_decimal(arrow_type):
            return f'decimal({arrow_type.precision},{arrow_type.scale})'
        elif pa.types.is_list(arrow_type):
            element_type = self._convert_pyarrow_type_to_glue_type(arrow_type.value_type)
            return f'array<{element_type}>'
        elif pa.types.is_struct(arrow_type):
            field_strs = []
            for field in arrow_type:
                field_type = self._convert_pyarrow_type_to_glue_type(field.type)
                field_strs.append(f'{field.name}:{field_type}')
            return f'struct<{",".join(field_strs)}>'
        elif pa.types.is_map(arrow_type):
            key_type = self._convert_pyarrow_type_to_glue_type(arrow_type.key_type)
            value_type = self._convert_pyarrow_type_to_glue_type(arrow_type.item_type)
            return f'map<{key_type},{value_type}>'
        else:
            # Default to string for unknown types
            return 'string'
    
    def __getstate__(self):
        """Prepare instance for pickling by excluding unpickleable objects."""
        state = self.__dict__.copy()
        # Remove the unpickleable Glue client
        state['_glue'] = None
        return state
    
    def __setstate__(self, state):
        """Restore instance from pickled state."""
        self.__dict__.update(state)
        # The Glue client will be re-initialized lazily via the property
    


class GlueNamespaceConfig:
    """Configuration for GlueNamespace."""
    
    # Glue configuration keys (without prefix as per documentation)
    CATALOG_ID = "catalog_id"
    ENDPOINT = "endpoint"
    REGION = "region"
    ACCESS_KEY_ID = "access_key_id"
    SECRET_ACCESS_KEY = "secret_access_key"
    SESSION_TOKEN = "session_token"
    PROFILE_NAME = "profile_name"
    MAX_RETRIES = "max_retries"
    RETRY_MODE = "retry_mode"
    ROOT = "root"
    
    # Storage configuration prefix
    STORAGE_OPTIONS_PREFIX = "storage."
    
    def __init__(self, properties: Optional[Dict[str, str]] = None):
        """Initialize configuration from properties.
        
        Args:
            properties: Dictionary of configuration properties
        """
        if properties is None:
            properties = {}
        
        # Store raw properties for pickling support
        self._properties = properties.copy()
        
        self._catalog_id = properties.get(self.CATALOG_ID)
        self._endpoint = properties.get(self.ENDPOINT)
        self._region = properties.get(self.REGION)
        self._access_key_id = properties.get(self.ACCESS_KEY_ID)
        self._secret_access_key = properties.get(self.SECRET_ACCESS_KEY)
        self._session_token = properties.get(self.SESSION_TOKEN)
        self._profile_name = properties.get(self.PROFILE_NAME)
        self._root = properties.get(self.ROOT)
        
        # Parse max retries
        max_retries_str = properties.get(self.MAX_RETRIES)
        self._max_retries = int(max_retries_str) if max_retries_str else None
        
        self._retry_mode = properties.get(self.RETRY_MODE)
        
        # Extract storage options
        self._storage_options = self._extract_storage_options(properties)
    
    def _extract_storage_options(self, properties: Dict[str, str]) -> Dict[str, str]:
        """Extract storage configuration properties by removing the prefix."""
        storage_options = {}
        for key, value in properties.items():
            if key.startswith(self.STORAGE_OPTIONS_PREFIX):
                storage_key = key[len(self.STORAGE_OPTIONS_PREFIX):]
                storage_options[storage_key] = value
        return storage_options
    
    @property
    def catalog_id(self) -> Optional[str]:
        return self._catalog_id
    
    @property
    def endpoint(self) -> Optional[str]:
        return self._endpoint
    
    @property
    def region(self) -> Optional[str]:
        return self._region
    
    @property
    def access_key_id(self) -> Optional[str]:
        return self._access_key_id
    
    @property
    def secret_access_key(self) -> Optional[str]:
        return self._secret_access_key
    
    @property
    def session_token(self) -> Optional[str]:
        return self._session_token
    
    @property
    def profile_name(self) -> Optional[str]:
        return self._profile_name
    
    @property
    def max_retries(self) -> Optional[int]:
        return self._max_retries
    
    @property
    def retry_mode(self) -> Optional[str]:
        return self._retry_mode
    
    @property
    def root(self) -> Optional[str]:
        return self._root
    
    @property
    def storage_options(self) -> Dict[str, str]:
        """Get the storage configuration properties."""
        return self._storage_options.copy()
    
    @property
    def properties(self) -> Dict[str, str]:
        """Get the raw properties dictionary."""
        return self._properties.copy()