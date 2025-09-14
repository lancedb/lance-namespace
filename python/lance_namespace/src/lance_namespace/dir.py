"""
Lance Directory Namespace implementation using OpenDAL.
"""
from typing import Dict, List, Optional
from urllib.parse import urlparse
import os

try:
    import opendal
except ImportError:
    opendal = None

import lance.dataset
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
    JsonArrowSchema,
    JsonArrowField,
    JsonArrowDataType,
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
        self.operator = self._initialize_operator(root)

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
        self._validate_root_namespace_id(request.id)

        try:
            tables = []
            entries = self.operator.list("", recursive=False)

            for entry in entries:
                path = entry.path.rstrip('/')

                # Only process paths that contain ".lance"
                if ".lance" not in path:
                    continue

                # Strip .lance suffix to get clean table name
                table_name = path[:-6]  # Remove '.lance' (6 characters)

                # Check if it's a valid Lance dataset or has .lance-reserved file
                is_table = False

                # First check for .lance-reserved file
                try:
                    reserved_file_path = f"{table_name}.lance/.lance-reserved"
                    if self.operator.stat(reserved_file_path):
                        is_table = True
                except:
                    pass

                # If not found, check for _versions directory
                if not is_table:
                    try:
                        versions_path = f"{table_name}.lance/_versions/"
                        version_entries = list(self.operator.list(versions_path, limit=1))
                        if version_entries:
                            is_table = True
                    except:
                        pass

                if is_table:
                    tables.append(table_name)  # Add clean name without .lance

            response = ListTablesResponse(tables=tables)
            return response
        except Exception as e:
            raise RuntimeError(f"Failed to list tables: {e}")


    def create_table(self, request: CreateTableRequest, request_data: bytes) -> CreateTableResponse:
        """Create a table using Lance dataset from Arrow IPC stream."""
        if not request.id:
            raise ValueError("table ID cannot be empty")

        if not request_data:
            raise ValueError("Request data (Arrow IPC stream) is required for create_table")

        table_name = self._normalize_table_id(request.id)
        table_path = self._get_table_path(table_name)

        if request.location and request.location != table_path:
            raise ValueError(f"Cannot create table {table_name} at location {request.location}, must be at location {table_path}")

        # Extract table from Arrow IPC stream
        try:
            reader = pa.ipc.open_stream(request_data)
            table = reader.read_all()
        except Exception as e:
            raise ValueError(f"Invalid Arrow IPC stream: {e}")

        # Create Lance dataset with the data
        lance.dataset.write_dataset(table, table_path, storage_options=self.config.storage_options)

        response = CreateTableResponse(location=table_path, version=1)
        return response

    def create_empty_table(self, request: CreateEmptyTableRequest) -> CreateEmptyTableResponse:
        """Create an empty table (metadata only) by writing a .lance-reserved file."""
        if not request.id:
            raise ValueError("table ID cannot be empty")

        table_name = self._normalize_table_id(request.id)
        table_path = self._get_table_path(table_name)

        if request.location and request.location != table_path:
            raise ValueError(f"Cannot create table {table_name} at location {request.location}, must be at location {table_path}")

        # Create the .lance-reserved file - use relative path for operator
        reserved_file_path = f"{table_name}.lance/.lance-reserved"
        self.operator.write(reserved_file_path, b"")

        response = CreateEmptyTableResponse(location=table_path)
        return response

    def drop_table(self, request: DropTableRequest) -> DropTableResponse:
        """Drop a table by removing its Lance dataset."""
        if not request.id:
            raise ValueError("table ID cannot be empty")

        table_name = self._normalize_table_id(request.id)
        table_path = self._get_table_path(table_name)

        try:
            # Remove the entire table directory
            self.operator.remove_all(f"{table_name}.lance/")
            response = DropTableResponse()
            return response
        except Exception as e:
            raise RuntimeError(f"Failed to drop table {table_name}: {e}")

    def describe_table(self, request: DescribeTableRequest) -> DescribeTableResponse:
        """Describe a table by checking its existence and returning location."""
        if not request.id:
            raise ValueError("table ID cannot be empty")

        table_name = self._normalize_table_id(request.id)
        table_path = self._get_table_path(table_name)

        # Check if table exists - either as Lance dataset or with .lance-reserved file
        table_exists = False

        # First check for .lance-reserved file
        try:
            reserved_file_path = f"{table_name}.lance/.lance-reserved"
            if self.operator.stat(reserved_file_path):
                table_exists = True
        except:
            pass

        # If not found, check if it's a Lance dataset by looking for objects with _versions/ prefix
        if not table_exists:
            try:
                versions_path = f"{table_name}.lance/_versions/"
                version_entries = list(self.operator.list(versions_path, limit=1))
                if version_entries:
                    table_exists = True
            except:
                pass

        if not table_exists:
            raise RuntimeError(f"Table does not exist: {table_name}")

        response = DescribeTableResponse(location=table_path)
        return response

    def _normalize_table_id(self, id: List[str]) -> str:
        """Normalize table ID - only single-level IDs are supported."""
        if not id:
            raise ValueError("Directory namespace table ID cannot be empty")

        if len(id) != 1:
            raise ValueError(
                f"Directory namespace only supports single-level table IDs, but got: {id}"
            )

        return id[0]

    def _validate_root_namespace_id(self, id: Optional[List[str]]) -> None:
        """Validate that the namespace ID represents a root namespace."""
        if id:
            raise ValueError(
                f"Directory namespace only supports root namespace operations, "
                f"but got namespace ID: {id}. Expected empty ID."
            )

    def _get_table_path(self, table_name: str) -> str:
        """Get the full path for a table."""
        root = self.config.root if self.config.root else os.getcwd()
        return f"{root}/{table_name}.lance"

    def _convert_json_arrow_schema_to_pyarrow(self, json_schema: JsonArrowSchema) -> pa.Schema:
        """Convert JsonArrowSchema to PyArrow Schema."""
        fields = []
        for json_field in json_schema.fields:
            arrow_type = self._convert_json_arrow_type_to_pyarrow(json_field.type)
            field = pa.field(json_field.name, arrow_type, nullable=json_field.nullable)
            fields.append(field)

        return pa.schema(fields, metadata=json_schema.metadata)

    def _convert_json_arrow_type_to_pyarrow(self, json_type: JsonArrowDataType) -> pa.DataType:
        """Convert JsonArrowDataType to PyArrow DataType."""
        type_name = json_type.type.lower()

        if type_name == "null":
            return pa.null()
        elif type_name in ["bool", "boolean"]:
            return pa.bool_()
        elif type_name == "int8":
            return pa.int8()
        elif type_name == "uint8":
            return pa.uint8()
        elif type_name == "int16":
            return pa.int16()
        elif type_name == "uint16":
            return pa.uint16()
        elif type_name == "int32":
            return pa.int32()
        elif type_name == "uint32":
            return pa.uint32()
        elif type_name == "int64":
            return pa.int64()
        elif type_name == "uint64":
            return pa.uint64()
        elif type_name == "float32":
            return pa.float32()
        elif type_name == "float64":
            return pa.float64()
        elif type_name == "utf8":
            return pa.utf8()
        elif type_name == "binary":
            return pa.binary()
        else:
            raise ValueError(f"Unsupported Arrow type: {type_name}")

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

    def _initialize_operator(self, root: str) -> opendal.Operator:
        """Initialize the OpenDAL operator based on the root path."""
        scheme_split = root.split("://", 1)

        # Local file system path
        if len(scheme_split) < 2:
            return opendal.Operator("fs", root=root)

        scheme = self._normalize_scheme(scheme_split[0])
        authority_split = scheme_split[1].split("/", 1)
        authority = authority_split[0]
        path = authority_split[1] if len(authority_split) > 1 else ""

        if scheme in ["s3", "gcs"]:
            return opendal.Operator(scheme, root=path, bucket=authority)
        elif scheme == "azblob":
            return opendal.Operator(scheme, root=path, container=authority)
        else:
            return opendal.Operator(scheme, root=scheme_split[1])



class DirectoryNamespaceConfig:
    """Configuration for DirectoryNamespace."""

    ROOT = "root"
    STORAGE_OPTIONS_PREFIX = "storage."

    def __init__(self, properties: Optional[Dict[str, str]] = None):
        """Initialize configuration from properties.

        Args:
            properties: Dictionary of configuration properties
        """
        if properties is None:
            properties = {}

        self._root = properties.get(self.ROOT)
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
    def root(self) -> Optional[str]:
        """Get the namespace root directory."""
        return self._root

    @property
    def storage_options(self) -> Dict[str, str]:
        """Get the storage configuration properties."""
        return self._storage_options.copy()
