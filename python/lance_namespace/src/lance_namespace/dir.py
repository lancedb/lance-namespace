"""
Lance Directory Namespace implementation using OpenDAL.
"""
from typing import Dict, List, Optional
from urllib.parse import urlparse
import os

import opendal

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
    RegisterTableRequest,
    RegisterTableResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
)


class DirectoryNamespace(LanceNamespace):
    """Lance Directory Namespace implementation using OpenDAL."""
    
    def __init__(self, **properties):
        """Initialize the directory namespace.
        
        Args:
            root: The root directory of the namespace (optional, defaults to current directory)
            **properties: Additional configuration properties for specific storage backends
        """
        self.config = DirectoryNamespaceConfig(properties)
        root = self.config.root
        
        # Use current directory if root is not specified
        if not root:
            root = os.getcwd()
        
        self.namespace_path = self._parse_path(root)
        self.operator = self._initialize_operator(self.namespace_path, self.config.opendal_config)
    
    def create_namespace(self, request: CreateNamespaceRequest) -> CreateNamespaceResponse:
        """Create a namespace - not supported for directory namespace."""
        raise NotImplementedError(
            "Directory namespace only contains a flat list of tables and does not support creating namespaces"
        )
    
    def list_namespaces(self, request: ListNamespacesRequest) -> ListNamespacesResponse:
        """List namespaces - not supported for directory namespace."""
        raise NotImplementedError(
            "Directory namespace only contains a flat list of tables and does not support listing namespaces"
        )
    
    def describe_namespace(self, request: DescribeNamespaceRequest) -> DescribeNamespaceResponse:
        """Describe namespace - not supported for directory namespace."""
        raise NotImplementedError(
            "Directory namespace only contains a flat list of tables and does not support describing namespaces"
        )
    
    def drop_namespace(self, request: DropNamespaceRequest) -> DropNamespaceResponse:
        """Drop namespace - not supported for directory namespace."""
        raise NotImplementedError(
            "Directory namespace only contains a flat list of tables and does not support dropping namespaces"
        )
    
    def namespace_exists(self, request: NamespaceExistsRequest) -> None:
        """Check namespace exists - not supported for directory namespace."""
        raise NotImplementedError(
            "Directory namespace only contains a flat list of tables and does not support namespace existence checks"
        )
    
    def list_tables(self, request: ListTablesRequest) -> ListTablesResponse:
        """List all tables in the namespace."""
        try:
            tables = []
            entries = self.operator.list("")
            
            for entry in entries:
                metadata = self.operator.stat(entry.path)
                if metadata.is_dir():
                    table_name = entry.path.rstrip('/')
                    tables.append(table_name)
            
            response = ListTablesResponse()
            response.tables = tables
            return response
        except Exception as e:
            raise RuntimeError(f"Failed to list tables: {e}")
    
    def register_table(self, request: RegisterTableRequest) -> RegisterTableResponse:
        """Register a table by creating its directory."""
        table_name = request.table
        if not table_name:
            raise ValueError("table name is required")
        
        table_path = self._get_table_path(table_name)
        
        try:
            self.operator.create_dir(table_path)
            response = RegisterTableResponse()
            response.table = table_name
            response.table_uri = table_path
            return response
        except Exception as e:
            raise RuntimeError(f"Failed to register table {table_name}: {e}")
    
    def deregister_table(self, request: DeregisterTableRequest) -> DeregisterTableResponse:
        """Deregister a table by removing its directory."""
        table_name = request.table
        if not table_name:
            raise ValueError("table name is required")
        
        table_path = self._get_table_path(table_name)
        
        try:
            self.operator.remove_all(table_path)
            response = DeregisterTableResponse()
            response.table = table_name
            return response
        except Exception as e:
            raise RuntimeError(f"Failed to deregister table {table_name}: {e}")
    
    def _parse_path(self, path: str) -> str:
        """Parse the path and convert to a proper URI if needed."""
        parsed = urlparse(path)
        if parsed.scheme:
            return path
        
        # Handle absolute and relative POSIX paths
        if path.startswith('/'):
            return f"file://{path}"
        else:
            current_dir = os.getcwd()
            absolute_path = os.path.abspath(os.path.join(current_dir, path))
            return f"file://{absolute_path}"
    
    def _normalize_scheme(self, scheme: Optional[str]) -> str:
        """Normalize scheme with aliases."""
        if scheme is None:
            return 'fs'
        
        # Handle scheme aliases
        scheme_lower = scheme.lower()
        if scheme_lower in ['s3a', 's3n']:
            return 's3'
        elif scheme_lower == 'abfs':
            return 'azblob'
        elif scheme_lower == 'file':
            return 'fs'
        else:
            return scheme_lower
    
    def _initialize_operator(self, path: str, opendal_config: Dict[str, str]) -> opendal.Operator:
        """Initialize the OpenDAL operator based on the URI scheme."""
        parsed = urlparse(path)
        scheme = self._normalize_scheme(parsed.scheme)
        
        config = {}
        
        # Set basic config based on scheme
        if scheme == 'fs':
            config['root'] = parsed.path
        elif parsed.netloc:
            # For cloud storage, set bucket/container and root
            if scheme == 's3':
                config['bucket'] = parsed.netloc
            elif scheme == 'gcs':
                config['bucket'] = parsed.netloc
            elif scheme == 'azblob':
                config['container'] = parsed.netloc
            else:
                # For other schemes, try to set a generic "bucket" config
                config['bucket'] = parsed.netloc
            
            if parsed.path:
                config['root'] = parsed.path
        
        # Add OpenDAL configuration
        config.update(opendal_config)
        
        try:
            return opendal.Operator(scheme, **config)
        except Exception as e:
            raise RuntimeError(f"Failed to initialize operator for path {path}: {e}")
    
    def _get_table_path(self, table_name: str) -> str:
        """Get the path for a table directory."""
        return f"{table_name}/"


class DirectoryNamespaceConfig:
    """Configuration for DirectoryNamespace."""
    
    ROOT = "root"
    OPENDAL_PREFIX = "opendal."
    
    def __init__(self, properties: Optional[Dict[str, str]] = None):
        """Initialize configuration from properties.
        
        Args:
            properties: Dictionary of configuration properties
        """
        if properties is None:
            properties = {}
            
        self._root = properties.get(self.ROOT)
        self._opendal_config = self._extract_opendal_config(properties)
    
    def _extract_opendal_config(self, properties: Dict[str, str]) -> Dict[str, str]:
        """Extract OpenDAL configuration properties by removing the prefix."""
        opendal_config = {}
        for key, value in properties.items():
            if key.startswith(self.OPENDAL_PREFIX):
                opendal_key = key[len(self.OPENDAL_PREFIX):]
                opendal_config[opendal_key] = value
        return opendal_config
    
    @property
    def root(self) -> Optional[str]:
        """Get the namespace root directory."""
        return self._root
    
    @property
    def opendal_config(self) -> Dict[str, str]:
        """Get the OpenDAL configuration properties."""
        return self._opendal_config.copy()
