import pytest
from unittest.mock import Mock, patch
from lance_namespace import LanceNamespace, connect


class MockNamespace(LanceNamespace):

    def __init__(self, **kwargs):
        self.properties = kwargs

    def namespace_id(self) -> str:
        """Return a mock namespace ID."""
        return "MockNamespace { }"

def test_connect_with_short_name_rest():
    with patch('lance_namespace.namespace.importlib.import_module') as mock_import:
        mock_module = Mock()
        mock_module.LanceRestNamespace = MockNamespace
        mock_import.return_value = mock_module

        ns = connect("rest", {"uri": "http://localhost:8080"})

        assert isinstance(ns, MockNamespace)
        assert ns.properties["uri"] == "http://localhost:8080"


def test_connect_with_full_class_path():
    with patch('lance_namespace.namespace.importlib.import_module') as mock_import:
        mock_module = Mock()
        mock_module.CustomNamespace = MockNamespace
        mock_import.return_value = mock_module

        ns = connect("my.custom.CustomNamespace", {"custom": "value"})

        assert isinstance(ns, MockNamespace)
        assert ns.properties["custom"] == "value"


def test_connect_invalid_implementation():
    with patch('lance_namespace.namespace.importlib.import_module') as mock_import:
        mock_import.side_effect = ImportError("Module not found")

        with pytest.raises(ValueError) as exc_info:
            connect("invalid.module.Class", {})

        assert "Failed to construct namespace" in str(exc_info.value)


def test_connect_non_namespace_class():
    with patch('lance_namespace.namespace.importlib.import_module') as mock_import:
        mock_module = Mock()
        mock_module.NotANamespace = str
        mock_import.return_value = mock_module

        with pytest.raises(ValueError) as exc_info:
            connect("test.NotANamespace", {})

        assert "does not implement LanceNamespace interface" in str(exc_info.value)


def test_default_methods_raise_not_implemented():
    from lance_namespace_urllib3_client.models import (
        ListNamespacesRequest,
        DescribeNamespaceRequest,
        CreateNamespaceRequest,
    )

    ns = MockNamespace()

    with pytest.raises(NotImplementedError):
        ns.list_namespaces(ListNamespacesRequest())

    with pytest.raises(NotImplementedError):
        ns.describe_namespace(DescribeNamespaceRequest())

    with pytest.raises(NotImplementedError):
        ns.create_namespace(CreateNamespaceRequest())