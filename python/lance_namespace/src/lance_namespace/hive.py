"""
Lance Hive2 Namespace implementation using Hive Metastore.

This module provides integration with Apache Hive Metastore for managing Lance tables.
Lance tables are registered as external tables in Hive with specific metadata properties
to identify them as Lance format.

Installation:
    pip install 'lance-namespace[hive2]'

Usage:
    from lance_namespace import connect
    
    # Connect to Hive Metastore
    namespace = connect("hive2", {
        "uri": "thrift://localhost:9083",
        "root": "/my/dir",  # Or "s3://bucket/prefix"
        "ugi": "user:group1,group2"  # Optional user/group info
    })
    
    # List databases
    from lance_namespace import ListNamespacesRequest
    response = namespace.list_namespaces(ListNamespacesRequest())
    
    # Create a table
    from lance_namespace import CreateTableRequest
    import pyarrow as pa
    import io
    
    data = pa.table({"col1": [1, 2, 3], "col2": ["a", "b", "c"]})
    buf = io.BytesIO()
    with pa.ipc.new_stream(buf, data.schema) as writer:
        writer.write_table(data)
    
    request = CreateTableRequest(
        id=["my_database", "my_table"],
        mode="create"
    )
    response = namespace.create_table(request, buf.getvalue())
    
    # Register existing Lance table
    from lance_namespace import RegisterTableRequest
    request = RegisterTableRequest(
        id=["my_database", "existing_table"],
        location="/path/to/lance/table"
    )
    response = namespace.register_table(request)

Configuration Properties:
    uri (str): Hive Metastore Thrift URI (e.g., "thrift://localhost:9083")
    root (str): Storage root location of the lakehouse on Hive catalog (default: current working directory)
    ugi (str): Optional User Group Information for authentication (format: "user:group1,group2")
    client.pool-size (int): Size of the HMS client connection pool (default: 3)
    storage.* (str): Additional storage configurations to access table
"""
from typing import Dict, List, Optional, Any
from urllib.parse import urlparse, unquote
import os
import logging

try:
    from hive_metastore.ThriftHiveMetastore import Client
    from hive_metastore.ttypes import (
        Database as HiveDatabase,
        Table as HiveTable,
        StorageDescriptor,
        SerDeInfo,
        FieldSchema,
        NoSuchObjectException,
        AlreadyExistsException,
        InvalidOperationException,
        MetaException,
    )
    from thrift.protocol import TBinaryProtocol
    from thrift.transport import TSocket, TTransport
    HIVE_AVAILABLE = True
except ImportError:
    HIVE_AVAILABLE = False
    Client = None
    HiveDatabase = None
    HiveTable = None
    StorageDescriptor = None
    SerDeInfo = None
    FieldSchema = None
    NoSuchObjectException = None
    AlreadyExistsException = None
    InvalidOperationException = None
    MetaException = None

import lance
import pyarrow as pa

from lance_namespace.namespace import LanceNamespace
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

logger = logging.getLogger(__name__)

# Table properties used by Lance (per hive.md specification)
TABLE_TYPE_KEY = "table_type"  # Case insensitive
LANCE_TABLE_FORMAT = "lance"  # Case insensitive
MANAGED_BY_KEY = "managed_by"  # Case insensitive, values: "storage" or "impl"
VERSION_KEY = "version"  # Numeric version number
EXTERNAL_TABLE = "EXTERNAL_TABLE"


class HiveMetastoreClient:
    """Helper class to manage Hive Metastore client connections."""
    
    def __init__(self, uri: str, ugi: Optional[str] = None):
        if not HIVE_AVAILABLE:
            raise ImportError(
                "Hive dependencies not installed. Please install with: "
                "pip install 'lance-namespace[hive2]'"
            )
        
        self._uri = uri
        self._ugi = ugi.split(":") if ugi else None
        self._transport = None
        self._client = None
        self._init_client()
    
    def _init_client(self):
        """Initialize the Thrift client connection."""
        url_parts = urlparse(self._uri)
        socket = TSocket.TSocket(url_parts.hostname, url_parts.port or 9083)
        self._transport = TTransport.TBufferedTransport(socket)
        protocol = TBinaryProtocol.TBinaryProtocol(self._transport)
        self._client = Client(protocol)
        
        if not self._transport.isOpen():
            self._transport.open()
        
        if self._ugi:
            self._client.set_ugi(*self._ugi)
    
    def __enter__(self):
        """Enter context manager."""
        if not self._transport or not self._transport.isOpen():
            self._init_client()
        return self._client
    
    def __exit__(self, exc_type, exc_val, exc_tb):
        """Exit context manager."""
        if self._transport and self._transport.isOpen():
            self._transport.close()
    
    def close(self):
        """Close the client connection."""
        if self._transport and self._transport.isOpen():
            self._transport.close()


class Hive2Namespace(LanceNamespace):
    """Lance Hive2 Namespace implementation using Hive Metastore."""
    
    def __init__(self, **properties):
        """Initialize the Hive2 namespace.
        
        Args:
            uri: The Hive Metastore URI (e.g., "thrift://localhost:9083")
            root: Storage root location of the lakehouse on Hive catalog (optional)
            ugi: User Group Information for authentication (optional, format: "user:group1,group2")
            client.pool-size: Size of the HMS client connection pool (optional, default: 3)
            storage.*: Additional storage configurations to access table
            **properties: Additional configuration properties
        """
        if not HIVE_AVAILABLE:
            raise ImportError(
                "Hive dependencies not installed. Please install with: "
                "pip install 'lance-namespace[hive2]'"
            )
        
        self.uri = properties.get("uri", "thrift://localhost:9083")
        self.ugi = properties.get("ugi")
        self.root = properties.get("root", os.getcwd())
        self.pool_size = int(properties.get("client.pool-size", "3"))
        # Extract storage properties
        self.storage_properties = {k[8:]: v for k, v in properties.items() if k.startswith("storage.")}
        
        # Store properties for pickling support
        self._properties = properties.copy()
        
        # Lazy initialization to support pickling
        self._client = None
    
    @property
    def client(self):
        """Get the Hive client, initializing it if necessary."""
        if self._client is None:
            self._client = HiveMetastoreClient(self.uri, self.ugi)
        return self._client
    
    def _normalize_identifier(self, identifier: List[str]) -> tuple:
        """Normalize identifier to (database, table) tuple."""
        if len(identifier) == 1:
            return ("default", identifier[0])
        elif len(identifier) == 2:
            return (identifier[0], identifier[1])
        else:
            raise ValueError(f"Invalid identifier: {identifier}")
    
    def _is_root_namespace(self, identifier: Optional[List[str]]) -> bool:
        """Check if the identifier refers to the root namespace."""
        return not identifier or len(identifier) == 0
    
    def _get_table_location(self, database: str, table: str) -> str:
        """Get the location for a table."""
        return os.path.join(self.root, f"{database}.db", table)
    
    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List all databases in the Hive Metastore."""
        try:
            # Only list namespaces if we're at the root level
            if not self._is_root_namespace(request.id):
                # Non-root namespaces don't have children in Hive2
                return ListNamespacesResponse(namespaces=[])
            
            with self.client as client:
                databases = client.get_all_databases()
                # Return just database names as strings (excluding default)
                namespaces = [db for db in databases if db != "default"]
                
                return ListNamespacesResponse(namespaces=namespaces)
        except Exception as e:
            logger.error(f"Failed to list namespaces: {e}")
            raise
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        """Describe a database in the Hive Metastore."""
        try:
            # Handle root namespace
            if self._is_root_namespace(request.id):
                properties = {
                    "location": self.root,
                    "description": "Root namespace (Hive Metastore)"
                }
                if self.ugi:
                    properties["ugi"] = self.ugi
                return DescribeNamespaceResponse(properties=properties)
            
            if len(request.id) != 1:
                raise ValueError(f"Invalid namespace identifier: {request.id}")
            
            database_name = request.id[0]
            
            with self.client as client:
                database = client.get_database(database_name)
                
                properties = {}
                if database.description:
                    properties["comment"] = database.description
                if database.ownerName:
                    properties["owner"] = database.ownerName
                if database.locationUri:
                    properties["location"] = database.locationUri
                if database.parameters:
                    properties.update(database.parameters)
                
                return DescribeNamespaceResponse(
                    properties=properties
                )
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Namespace {request.id} does not exist")
            logger.error(f"Failed to describe namespace {request.id}: {e}")
            raise
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        """Create a new database in the Hive Metastore."""
        try:
            # Cannot create root namespace
            if self._is_root_namespace(request.id):
                raise ValueError("Root namespace already exists")
            
            if len(request.id) != 1:
                raise ValueError(f"Invalid namespace identifier: {request.id}")
            
            database_name = request.id[0]
            
            # Create database object
            if not HiveDatabase:
                raise ImportError("Hive dependencies not available")
            database = HiveDatabase()
            database.name = database_name
            database.description = request.properties.get("comment", "")
            database.ownerName = request.properties.get("owner", os.getenv("USER", ""))
            database.locationUri = request.properties.get(
                "location", 
                os.path.join(self.root, f"{database_name}.db")
            )
            database.parameters = {
                k: v for k, v in request.properties.items() 
                if k not in ["comment", "owner", "location"]
            }
            
            with self.client as client:
                client.create_database(database)
            
            return CreateNamespaceResponse()
        except Exception as e:
            if AlreadyExistsException and isinstance(e, AlreadyExistsException):
                raise ValueError(f"Namespace {request.id} already exists")
            logger.error(f"Failed to create namespace {request.id}: {e}")
            raise
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop a database from the Hive Metastore."""
        try:
            # Cannot drop root namespace
            if self._is_root_namespace(request.id):
                raise ValueError("Cannot drop root namespace")
            
            if len(request.id) != 1:
                raise ValueError(f"Invalid namespace identifier: {request.id}")
            
            database_name = request.id[0]
            
            with self.client as client:
                # Check if database is empty
                tables = client.get_all_tables(database_name)
                cascade = request.behavior == "CASCADE" if request.behavior else False
                if tables and not cascade:
                    raise ValueError(f"Namespace {request.id} is not empty")
                
                # Drop database
                client.drop_database(database_name, deleteData=True, cascade=cascade)
            
            return DropNamespaceResponse()
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Namespace {request.id} does not exist")
            logger.error(f"Failed to drop namespace {request.id}: {e}")
            raise
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check if a database exists in the Hive Metastore."""
        try:
            # Root namespace always exists
            if self._is_root_namespace(request.id):
                return
            
            if len(request.id) != 1:
                raise ValueError(f"Invalid namespace identifier: {request.id}")
            
            database_name = request.id[0]
            
            with self.client as client:
                client.get_database(database_name)
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Namespace {request.id} does not exist")
            logger.error(f"Failed to check namespace existence {request.id}: {e}")
            raise
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List tables in a database."""
        try:
            # Root namespace has no tables
            if self._is_root_namespace(request.id):
                return ListTablesResponse(tables=[])
            
            if len(request.id) != 1:
                raise ValueError(f"Invalid namespace identifier: {request.id}")
            
            database_name = request.id[0]
            
            with self.client as client:
                table_names = client.get_all_tables(database_name)
                
                # Filter for Lance tables if needed
                tables = []
                for table_name in table_names:
                    try:
                        table = client.get_table(database_name, table_name)
                        # Check if it's a Lance table (case insensitive)
                        if table.parameters:
                            table_type = table.parameters.get(TABLE_TYPE_KEY, "").lower()
                            if table_type == LANCE_TABLE_FORMAT:
                                # Return just table name, not full identifier
                                tables.append(table_name)
                    except Exception:
                        # Skip tables we can't read
                        continue
                
                return ListTablesResponse(tables=tables)
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Namespace {request.id} does not exist")
            logger.error(f"Failed to list tables in namespace {request.id}: {e}")
            raise
    
    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table in the Hive Metastore."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            with self.client as client:
                table = client.get_table(database, table_name)
                
                # Check if it's a Lance table (case insensitive)
                if not table.parameters:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                table_type = table.parameters.get(TABLE_TYPE_KEY, "").lower()
                if table_type != LANCE_TABLE_FORMAT:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                
                # Get table location
                location = table.sd.location if table.sd else None
                if not location:
                    raise ValueError(f"Table {request.id} has no location")
                
                # Build properties from Hive metadata
                properties = {}
                if table.parameters:
                    properties.update(table.parameters)
                if table.owner:
                    properties["owner"] = table.owner
                
                # Get version from table parameters if available
                version = None
                if table.parameters and VERSION_KEY in table.parameters:
                    try:
                        version = int(table.parameters[VERSION_KEY])
                    except (ValueError, TypeError):
                        pass
                
                # Note: We don't load the Lance dataset here, just return Hive metadata
                # Schema will be None as we're not opening the dataset
                return DescribeTableResponse(
                    var_schema=None,
                    location=location,
                    version=version,
                    properties=properties
                )
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Table {request.id} does not exist")
            logger.error(f"Failed to describe table {request.id}: {e}")
            raise
    
    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        """Register an existing Lance table in the Hive Metastore.
        
        Note: This will open the Lance dataset to get schema and version information.
        If you want to avoid opening the dataset, you can provide 'version' in properties.
        """
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            # Determine managed_by value
            managed_by = request.properties.get(MANAGED_BY_KEY, "storage") if request.properties else "storage"
            
            # We always need to open the dataset to get schema for Hive columns
            dataset = lance.dataset(request.location)
            schema = dataset.schema
            
            # Only track version if managed_by is "impl"
            version = None
            if managed_by == "impl":
                # Get version from properties or dataset
                version = request.properties.get(VERSION_KEY) if request.properties else None
                if version is None:
                    version = str(dataset.version)
            
            # Create Hive table object
            if not HiveTable:
                raise ImportError("Hive dependencies not available")
            hive_table = HiveTable()
            hive_table.dbName = database
            hive_table.tableName = table_name
            hive_table.owner = request.properties.get("owner", os.getenv("USER", "")) if request.properties else os.getenv("USER", "")
            # Use current time if file doesn't exist yet
            import time
            current_time = int(time.time())
            try:
                hive_table.createTime = int(os.path.getctime(request.location))
                hive_table.lastAccessTime = int(os.path.getatime(request.location))
            except (OSError, FileNotFoundError):
                hive_table.createTime = current_time
                hive_table.lastAccessTime = current_time
            hive_table.tableType = EXTERNAL_TABLE
            
            # Set storage descriptor
            if not StorageDescriptor:
                raise ImportError("Hive dependencies not available")
            sd = StorageDescriptor()
            sd.location = request.location
            sd.inputFormat = "com.lancedb.lance.mapred.LanceInputFormat"
            sd.outputFormat = "com.lancedb.lance.mapred.LanceOutputFormat"
            sd.compressed = False
            sd.cols = self._pyarrow_schema_to_hive_fields(schema)
            
            # Set SerDe info
            if not SerDeInfo:
                raise ImportError("Hive dependencies not available")
            serde = SerDeInfo()
            serde.serializationLib = "com.lancedb.lance.mapred.LanceSerDe"
            sd.serdeInfo = serde
            
            hive_table.sd = sd
            
            # Set table parameters per hive.md specification
            hive_table.parameters = {
                TABLE_TYPE_KEY: LANCE_TABLE_FORMAT,
                MANAGED_BY_KEY: managed_by,
            }
            
            # Only set version if managed_by is "impl"
            if managed_by == "impl" and version is not None:
                hive_table.parameters[VERSION_KEY] = version
            
            if request.properties:
                # Add other properties but don't override the required ones
                for k, v in request.properties.items():
                    if k not in [TABLE_TYPE_KEY, MANAGED_BY_KEY, VERSION_KEY]:
                        hive_table.parameters[k] = v
            
            with self.client as client:
                client.create_table(hive_table)
            
            return RegisterTableResponse(
                location=request.location,
                properties=request.properties
            )
        except Exception as e:
            if AlreadyExistsException and isinstance(e, AlreadyExistsException):
                raise ValueError(f"Table {request.id} already exists")
            logger.error(f"Failed to register table {request.id}: {e}")
            raise
    
    def table_exists(self, request: TableExistsRequest) -> None:
        """Check if a table exists in the Hive Metastore."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            with self.client as client:
                table = client.get_table(database, table_name)
                
                # Check if it's a Lance table (case insensitive)
                if not table.parameters:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                table_type = table.parameters.get(TABLE_TYPE_KEY, "").lower()
                if table_type != LANCE_TABLE_FORMAT:
                    raise ValueError(f"Table {request.id} is not a Lance table")
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Table {request.id} does not exist")
            logger.error(f"Failed to check table existence {request.id}: {e}")
            raise
    
    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table from the Hive Metastore."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            with self.client as client:
                # Get table to check if it's a Lance table
                table = client.get_table(database, table_name)
                
                # Check if it's a Lance table (case insensitive)
                if not table.parameters:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                table_type = table.parameters.get(TABLE_TYPE_KEY, "").lower()
                if table_type != LANCE_TABLE_FORMAT:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                
                # Drop the table (always delete data for Lance tables)
                client.drop_table(database, table_name, deleteData=True)
            
            return DropTableResponse()
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Table {request.id} does not exist")
            logger.error(f"Failed to drop table {request.id}: {e}")
            raise
    
    def deregister_table(self, request: DeregisterTableRequest) -> DeregisterTableResponse:
        """Deregister a table from the Hive Metastore without deleting data."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            with self.client as client:
                # Get table to check if it's a Lance table
                table = client.get_table(database, table_name)
                
                # Check if it's a Lance table (case insensitive)
                if not table.parameters:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                table_type = table.parameters.get(TABLE_TYPE_KEY, "").lower()
                if table_type != LANCE_TABLE_FORMAT:
                    raise ValueError(f"Table {request.id} is not a Lance table")
                
                location = table.sd.location if table.sd else None
                
                # Drop the table metadata only (don't delete data)
                client.drop_table(database, table_name, deleteData=False)
                
                return DeregisterTableResponse(location=location)
        except Exception as e:
            if NoSuchObjectException and isinstance(e, NoSuchObjectException):
                raise ValueError(f"Table {request.id} does not exist")
            logger.error(f"Failed to deregister table {request.id}: {e}")
            raise
    
    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        """Create a new Lance table and register it in the Hive Metastore."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            if not request_data:
                raise ValueError("Request data (Arrow IPC stream) is required for create_table")
            
            # Determine table location
            location = request.location
            if not location:
                location = self._get_table_location(database, table_name)
            
            # Extract table from Arrow IPC stream
            try:
                reader = pa.ipc.open_stream(request_data)
                table = reader.read_all()
            except Exception as e:
                raise ValueError(f"Invalid Arrow IPC stream: {e}")
            
            # Create Lance dataset
            if request.mode == "create":
                # Check if dataset already exists
                if os.path.exists(location):
                    raise ValueError(f"Table {request.id} already exists at {location}")
                dataset = lance.write_dataset(table, location)
            elif request.mode == "create_or_replace":
                dataset = lance.write_dataset(table, location, mode="overwrite")
            else:
                raise ValueError(f"Unsupported create mode: {request.mode}")
            
            # Register in Hive Metastore
            register_request = RegisterTableRequest(
                id=request.id,
                location=location,
                properties=request.properties
            )
            self.register_table(register_request)
            
            return CreateTableResponse(
                id=request.id,
                location=location,
                version=dataset.version
            )
        except Exception as e:
            logger.error(f"Failed to create table {request.id}: {e}")
            raise
    
    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only) in Hive metastore."""
        try:
            database, table_name = self._normalize_identifier(request.id)
            
            # Determine table location
            location = request.location
            if not location:
                location = self._get_table_location(database, table_name)
            
            # Create a minimal schema for Hive (placeholder schema)
            if not FieldSchema:
                raise ImportError("Hive dependencies not available")
            
            fields = [
                FieldSchema(
                    name='__placeholder_id',
                    type='bigint',
                    comment='Placeholder column for empty table'
                )
            ]
            
            # Create Hive table metadata without creating actual Lance dataset
            storage_descriptor = StorageDescriptor(
                cols=fields,
                location=location,
                inputFormat='org.apache.hadoop.mapred.TextInputFormat',
                outputFormat='org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat',
                serdeInfo=SerDeInfo(
                    serializationLib='org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
                )
            )
            
            # Set table parameters to identify it as Lance table
            parameters = {
                TABLE_TYPE_KEY: "LANCE",
                MANAGED_BY_KEY: "storage",
                'empty_table': 'true',  # Mark as empty table
            }
            
            if request.properties:
                parameters.update(request.properties)
            
            hive_table = HiveTable(
                tableName=table_name,
                dbName=database,
                sd=storage_descriptor,
                parameters=parameters,
                tableType='EXTERNAL_TABLE'
            )
            
            # Create table in Hive
            with self.client_pool.get_client() as client:
                client.create_table(hive_table)
            
            return CreateEmptyTableResponse(location=location)
            
        except AlreadyExistsException:
            raise ValueError(f"Table {request.id} already exists")
        except Exception as e:
            logger.error(f"Failed to create empty table {request.id}: {e}")
            raise
    
    def _pyarrow_schema_to_hive_fields(self, schema: pa.Schema) -> List[FieldSchema]:
        """Convert PyArrow schema to Hive field schemas."""
        fields = []
        for field in schema:
            hive_type = self._pyarrow_type_to_hive_type(field.type)
            if not FieldSchema:
                raise ImportError("Hive dependencies not available")
            hive_field = FieldSchema(
                name=field.name,
                type=hive_type,
                comment=""
            )
            fields.append(hive_field)
        return fields
    
    def _pyarrow_type_to_hive_type(self, dtype: pa.DataType) -> str:
        """Convert PyArrow data type to Hive type string."""
        if pa.types.is_boolean(dtype):
            return "boolean"
        elif pa.types.is_int8(dtype):
            return "tinyint"
        elif pa.types.is_int16(dtype):
            return "smallint"
        elif pa.types.is_int32(dtype):
            return "int"
        elif pa.types.is_int64(dtype):
            return "bigint"
        elif pa.types.is_float32(dtype):
            return "float"
        elif pa.types.is_float64(dtype):
            return "double"
        elif pa.types.is_string(dtype):
            return "string"
        elif pa.types.is_binary(dtype):
            return "binary"
        elif pa.types.is_timestamp(dtype):
            return "timestamp"
        elif pa.types.is_date32(dtype) or pa.types.is_date64(dtype):
            return "date"
        elif pa.types.is_list(dtype):
            inner_type = self._pyarrow_type_to_hive_type(dtype.value_type)
            return f"array<{inner_type}>"
        elif pa.types.is_struct(dtype):
            field_strs = []
            for i in range(dtype.num_fields):
                field = dtype.field(i)
                field_type = self._pyarrow_type_to_hive_type(field.type)
                field_strs.append(f"{field.name}:{field_type}")
            return f"struct<{','.join(field_strs)}>"
        else:
            return "string"  # Default to string for unknown types
    
    def __getstate__(self):
        """Prepare instance for pickling by excluding unpickleable objects."""
        state = self.__dict__.copy()
        # Remove the unpickleable Hive client
        state['_client'] = None
        return state
    
    def __setstate__(self, state):
        """Restore instance from pickled state."""
        self.__dict__.update(state)
        # The Hive client will be re-initialized lazily via the property