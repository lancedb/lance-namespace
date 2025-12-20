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

"""Tests for lance_namespace interface and registry."""

import pytest

from lance_namespace import (
    LanceNamespace,
    connect,
    register_namespace_impl,
    NATIVE_IMPLS,
    _REGISTERED_IMPLS,
    # Test model re-exports
    ListNamespacesRequest,
    ListNamespacesResponse,
    # Error types
    UnsupportedOperationError,
)


class MockNamespace(LanceNamespace):
    """Mock namespace implementation for testing."""

    def __init__(self, **properties):
        self._properties = properties
        self._id = properties.get("id", "mock")

    def namespace_id(self) -> str:
        return f"MockNamespace {{ id: '{self._id}' }}"


class TestLanceNamespaceInterface:
    """Tests for the LanceNamespace ABC interface."""

    def test_abstract_method_namespace_id(self):
        """Test that namespace_id is abstract and must be implemented."""
        # MockNamespace implements namespace_id, so it should work
        ns = MockNamespace(id="test")
        assert ns.namespace_id() == "MockNamespace { id: 'test' }"

    def test_default_methods_raise_unsupported(self):
        """Test that default methods raise UnsupportedOperationError."""
        ns = MockNamespace()

        with pytest.raises(UnsupportedOperationError, match="list_namespaces"):
            ns.list_namespaces(ListNamespacesRequest(parent=[]))

        with pytest.raises(UnsupportedOperationError, match="list_tables"):
            from lance_namespace import ListTablesRequest

            ns.list_tables(ListTablesRequest(namespace=[]))


class TestRegisterNamespaceImpl:
    """Tests for register_namespace_impl function."""

    def setup_method(self):
        """Clear registered implementations before each test."""
        _REGISTERED_IMPLS.clear()

    def teardown_method(self):
        """Clear registered implementations after each test."""
        _REGISTERED_IMPLS.clear()

    def test_register_implementation(self):
        """Test registering a custom implementation."""
        register_namespace_impl("mock", "lance_namespace.tests.test_namespace.MockNamespace")
        assert "mock" in _REGISTERED_IMPLS
        assert _REGISTERED_IMPLS["mock"] == "lance_namespace.tests.test_namespace.MockNamespace"

    def test_register_overwrites_existing(self):
        """Test that registering with same name overwrites."""
        register_namespace_impl("mock", "path.to.OldNamespace")
        register_namespace_impl("mock", "path.to.NewNamespace")
        assert _REGISTERED_IMPLS["mock"] == "path.to.NewNamespace"


class TestConnect:
    """Tests for connect factory function."""

    def setup_method(self):
        """Clear registered implementations before each test."""
        _REGISTERED_IMPLS.clear()

    def teardown_method(self):
        """Clear registered implementations after each test."""
        _REGISTERED_IMPLS.clear()

    def test_connect_with_full_class_path(self):
        """Test connecting using full class path."""
        ns = connect(
            "lance_namespace.tests.test_namespace.MockNamespace",
            {"id": "test-full-path"},
        )
        assert isinstance(ns, LanceNamespace)
        assert isinstance(ns, MockNamespace)
        assert "test-full-path" in ns.namespace_id()

    def test_connect_with_registered_impl(self):
        """Test connecting using registered implementation alias."""
        register_namespace_impl("mock", "lance_namespace.tests.test_namespace.MockNamespace")
        ns = connect("mock", {"id": "test-registered"})
        assert isinstance(ns, MockNamespace)
        assert "test-registered" in ns.namespace_id()

    def test_connect_passes_properties(self):
        """Test that properties are passed to the constructor."""
        ns = connect(
            "lance_namespace.tests.test_namespace.MockNamespace",
            {"id": "prop-test", "extra": "value"},
        )
        assert ns._properties["id"] == "prop-test"
        assert ns._properties["extra"] == "value"

    def test_connect_invalid_class_path(self):
        """Test that invalid class path raises ValueError."""
        with pytest.raises(ValueError, match="Failed to construct"):
            connect("non.existent.Namespace", {})

    def test_connect_non_namespace_class(self):
        """Test that non-LanceNamespace class raises ValueError."""
        with pytest.raises(ValueError, match="does not implement LanceNamespace"):
            connect("lance_namespace.tests.test_namespace.NotANamespace", {})

    def test_native_impls_defined(self):
        """Test that native implementations are defined."""
        assert "dir" in NATIVE_IMPLS
        assert "rest" in NATIVE_IMPLS
        assert NATIVE_IMPLS["dir"] == "lance.namespace.DirectoryNamespace"
        assert NATIVE_IMPLS["rest"] == "lance.namespace.RestNamespace"


class TestModelReexports:
    """Tests for model re-exports from lance_namespace_urllib3_client."""

    def test_request_types_exported(self):
        """Test that request types are re-exported."""
        from lance_namespace import (
            CreateNamespaceRequest,
            ListTablesRequest,
            DescribeTableRequest,
        )

        # Just verify they can be imported and are the right types
        assert CreateNamespaceRequest is not None
        assert ListTablesRequest is not None
        assert DescribeTableRequest is not None

    def test_response_types_exported(self):
        """Test that response types are re-exported."""
        from lance_namespace import (
            CreateNamespaceResponse,
            ListTablesResponse,
            DescribeTableResponse,
        )

        assert CreateNamespaceResponse is not None
        assert ListTablesResponse is not None
        assert DescribeTableResponse is not None


# Helper class for testing non-namespace class rejection
class NotANamespace:
    """A class that doesn't implement LanceNamespace."""

    def __init__(self, **properties):
        pass
