"""
Tests for lance_namespace.rest_namespace module.
"""
import pytest
from unittest.mock import Mock, patch
from lance_namespace.rest import (
    LanceRestNamespace, 
    RestNamespaceConfig,
    object_id_str
)
from lance_namespace_urllib3_client.models import (
    ListNamespacesRequest,
    ListNamespacesResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    CreateTableRequest,
    CreateTableResponse,
    InsertIntoTableRequest,
    InsertIntoTableResponse,
    MergeInsertIntoTableRequest,
    MergeInsertIntoTableResponse,
    UpdateTableRequest,
    UpdateTableResponse,
    DeleteFromTableRequest,
    DeleteFromTableResponse,
    QueryTableRequest,
    CountTableRowsRequest,
    RegisterTableRequest,
    RegisterTableResponse,
    TableExistsRequest,
    DropTableRequest,
    DropTableResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
    CreateTableIndexRequest,
    CreateTableIndexResponse,
    ListTableIndicesRequest,
    ListTableIndicesResponse,
    ListTablesRequest,
    ListTablesResponse,
    DescribeTableIndexStatsRequest,
    DescribeTableIndexStatsResponse,
    DescribeTransactionRequest,
    DescribeTransactionResponse,
    AlterTransactionRequest,
    AlterTransactionResponse,
    AlterTransactionAction,
    CreateNamespaceRequest,
    CreateNamespaceResponse,
    DropNamespaceRequest,
    DropNamespaceResponse,
    DescribeNamespaceRequest,
    DescribeNamespaceResponse,
    NamespaceExistsRequest
)


def test_config_default_delimiter():
    """Test default delimiter is '.'."""
    config = RestNamespaceConfig({})
    assert config.delimiter() == "."


def test_config_custom_delimiter():
    """Test custom delimiter."""
    config = RestNamespaceConfig({"delimiter": "/"})
    assert config.delimiter() == "/"


def test_config_no_additional_headers():
    """Test no additional headers."""
    config = RestNamespaceConfig({"key": "value"})
    assert config.additional_headers() == {}


def test_config_additional_headers():
    """Test extracting additional headers."""
    config = RestNamespaceConfig({
        "header.Authorization": "Bearer token",
        "header.X-Custom": "custom-value",
        "other.property": "ignored"
    })
    headers = config.additional_headers()
    assert headers == {
        "Authorization": "Bearer token",
        "X-Custom": "custom-value"
    }

def test_object_id_with_list_id():
    """Test object with id field as list."""
    obj = Mock()
    obj.id = ["ns1", "ns2", "table1"]
    assert object_id_str(obj.id, ".", obj) == "ns1.ns2.table1"

def test_object_id_with_empty_list_id():
    """Test object with empty id list returns delimiter."""
    obj = Mock()
    obj.id = []
    assert object_id_str(obj.id, ".", obj) == "."

def test_object_id_with_string_id():
    """Test object with id field as list containing single string."""
    obj = Mock()
    obj.id = ["transaction123"]
    assert object_id_str(obj.id, ".", obj) == "transaction123"

def test_object_id_with_custom_delimiter():
    """Test object with custom delimiter."""
    obj = Mock()
    obj.id = ["ns1", "ns2", "table1"]
    assert object_id_str(obj.id, "/", obj) == "ns1/ns2/table1"

def test_object_id_no_id_field():
    """Test object with no id field raises ValueError."""
    obj = Mock(spec=[])
    with pytest.raises(ValueError, match="Object of type 'Mock' must have an 'id' field"):
        object_id_str(None, ".", obj)

def test_object_id_with_none_id():
    """Test object with None id raises ValueError."""
    obj = Mock()
    obj.id = None
    with pytest.raises(ValueError, match="Object of type 'Mock' must have an 'id' field"):
        object_id_str(obj.id, ".", obj)

class TestLanceRestNamespace:
    """Test the LanceRestNamespace class."""

    @pytest.fixture
    def namespace(self):
        with patch('lance_namespace_urllib3_client.ApiClient') as mock_client:
            with patch('lance_namespace_urllib3_client.NamespaceApi') as mock_ns_api:
                with patch('lance_namespace_urllib3_client.TableApi') as mock_table_api:
                    with patch('lance_namespace_urllib3_client.TransactionApi') as mock_tx_api:
                        namespace = LanceRestNamespace(**{
                            "uri": "http://localhost:8080",
                            "delimiter": "/",
                            "header.Authorization": "Bearer token"
                        })

                        assert namespace.config is not None
                        assert namespace.config.delimiter() == "/"
                        assert namespace.api_client is not None
                        assert namespace.namespace_api is not None
                        assert namespace.table_api is not None
                        assert namespace.transaction_api is not None
                        return namespace

    def test_list_namespaces(self, namespace):
        """Test list_namespaces method."""
        # Setup
        namespace.namespace_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = ListNamespacesRequest()
        request.id = ["root"]
        request.page_token = None
        request.limit = None

        expected_response = ListNamespacesResponse(namespaces=[])
        namespace.namespace_api.list_namespaces.return_value = expected_response

        # Execute
        response = namespace.list_namespaces(request)

        # Verify
        assert response == expected_response
        namespace.namespace_api.list_namespaces.assert_called_once_with(
            id="root",
            delimiter=".",
            page_token=None,
            limit=None,
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_describe_table(self, namespace):
        """Test describe_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DescribeTableRequest()
        request.id = ["ns1", "table1"]
        request.version = None

        expected_response = DescribeTableResponse(
            location="s3://location", 
            version=1,
            schema={"fields": [], "metadata": {}}
        )
        namespace.table_api.describe_table.return_value = expected_response

        # Execute
        response = namespace.describe_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.describe_table.assert_called_once_with(
            id="ns1.table1",
            describe_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_create_table(self, namespace):
        """Test create_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = CreateTableRequest()
        request.id = ["ns1", "table1"]
        request.location = "s3://bucket/path"
        request.properties = {"key": "value"}

        request_data = b"arrow data"

        expected_response = CreateTableResponse(
            location="s3://bucket/path",
            version=1
        )
        namespace.table_api.create_table.return_value = expected_response

        # Execute
        response = namespace.create_table(request, request_data)

        # Verify
        assert response == expected_response
        namespace.table_api.create_table.assert_called_once_with(
            id="ns1.table1",
            x_lance_table_location="s3://bucket/path",
            body=request_data,
            delimiter=".",
            x_lance_table_properties='{"key": "value"}',
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_insert_into_table(self, namespace):
        """Test insert_into_table with mode."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = InsertIntoTableRequest()
        request.id = ["ns1", "table1"]
        request.mode = "append"

        request_data = b"arrow data"

        expected_response = InsertIntoTableResponse(version=2)
        namespace.table_api.insert_into_table.return_value = expected_response

        # Execute
        response = namespace.insert_into_table(request, request_data)

        # Verify
        assert response == expected_response
        namespace.table_api.insert_into_table.assert_called_once_with(
            id="ns1.table1",
            body=request_data,
            delimiter=".",
            mode="append",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_namespace_exists(self, namespace):
        """Test namespace_exists method."""
        # Setup
        namespace.namespace_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = NamespaceExistsRequest()
        request.id = ["test_ns"]

        # Execute
        namespace.namespace_exists(request)

        # Verify
        namespace.namespace_api.namespace_exists.assert_called_once_with(
            id="test_ns",
            namespace_exists_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_create_namespace(self, namespace):
        """Test create_namespace method."""
        # Setup
        namespace.namespace_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = CreateNamespaceRequest()
        request.id = ["new_ns"]
        request.mode = "create"
        request.properties = {"owner": "test"}

        expected_response = CreateNamespaceResponse(properties={"owner": "test"})
        namespace.namespace_api.create_namespace.return_value = expected_response

        # Execute
        response = namespace.create_namespace(request)

        # Verify
        assert response == expected_response
        namespace.namespace_api.create_namespace.assert_called_once_with(
            id="new_ns",
            create_namespace_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_drop_namespace(self, namespace):
        """Test drop_namespace method."""
        # Setup
        namespace.namespace_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DropNamespaceRequest()
        request.id = ["drop_ns"]
        request.mode = "FAIL"
        request.behavior = "RESTRICT"

        expected_response = DropNamespaceResponse(properties={})
        namespace.namespace_api.drop_namespace.return_value = expected_response

        # Execute
        response = namespace.drop_namespace(request)

        # Verify
        assert response == expected_response
        namespace.namespace_api.drop_namespace.assert_called_once_with(
            id="drop_ns",
            drop_namespace_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_describe_namespace(self, namespace):
        """Test describe_namespace method."""
        # Setup
        namespace.namespace_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DescribeNamespaceRequest()
        request.id = ["test_ns"]

        expected_response = DescribeNamespaceResponse(properties={"owner": "test"})
        namespace.namespace_api.describe_namespace.return_value = expected_response

        # Execute
        response = namespace.describe_namespace(request)

        # Verify
        assert response == expected_response
        namespace.namespace_api.describe_namespace.assert_called_once_with(
            id="test_ns",
            describe_namespace_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_list_tables(self, namespace):
        """Test list_tables method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = ListTablesRequest()
        request.id = ["test_ns"]
        request.page_token = None
        request.limit = None

        expected_response = ListTablesResponse(tables=["table1", "table2"])
        namespace.table_api.list_tables.return_value = expected_response

        # Execute
        response = namespace.list_tables(request)

        # Verify
        assert response == expected_response
        namespace.table_api.list_tables.assert_called_once_with(
            id="test_ns",
            delimiter=".",
            page_token=None,
            limit=None,
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_register_table(self, namespace):
        """Test register_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = RegisterTableRequest(location="s3://bucket/table")
        request.id = ["ns1", "table1"]

        expected_response = RegisterTableResponse(
            location="s3://bucket/table",
            properties={}
        )
        namespace.table_api.register_table.return_value = expected_response

        # Execute
        response = namespace.register_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.register_table.assert_called_once_with(
            id="ns1.table1",
            register_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_table_exists(self, namespace):
        """Test table_exists method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = TableExistsRequest()
        request.id = ["ns1", "table1"]
        request.version = None

        # Execute
        namespace.table_exists(request)

        # Verify
        namespace.table_api.table_exists.assert_called_once_with(
            id="ns1.table1",
            table_exists_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_drop_table(self, namespace):
        """Test drop_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DropTableRequest()
        request.id = ["ns1", "table1"]

        expected_response = DropTableResponse(
            id=["ns1", "table1"],
            location="s3://bucket/table",
            properties={}
        )
        namespace.table_api.drop_table.return_value = expected_response

        # Execute
        response = namespace.drop_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.drop_table.assert_called_once_with(
            id="ns1.table1",
            drop_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_deregister_table(self, namespace):
        """Test deregister_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DeregisterTableRequest()
        request.id = ["ns1", "table1"]

        expected_response = DeregisterTableResponse(
            name="table1",
            namespace=["ns1"],
            location="s3://bucket/table",
            properties={}
        )
        namespace.table_api.deregister_table.return_value = expected_response

        # Execute
        response = namespace.deregister_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.deregister_table.assert_called_once_with(
            id="ns1.table1",
            deregister_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_count_table_rows(self, namespace):
        """Test count_table_rows method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = CountTableRowsRequest()
        request.id = ["ns1", "table1"]
        request.version = None
        request.filter = None

        expected_response = 1000
        namespace.table_api.count_table_rows.return_value = expected_response

        # Execute
        response = namespace.count_table_rows(request)

        # Verify
        assert response == expected_response
        namespace.table_api.count_table_rows.assert_called_once_with(
            id="ns1.table1",
            count_table_rows_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_merge_insert_into_table(self, namespace):
        """Test merge_insert_into_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = MergeInsertIntoTableRequest()
        request.id = ["ns1", "table1"]
        request.on = "id"
        request.when_matched_update_all = True
        request.when_not_matched_insert_all = True
        request.when_matched_update_all_filt = None
        request.when_not_matched_by_source_delete = None
        request.when_not_matched_by_source_delete_filt = None

        request_data = b"arrow data"

        expected_response = MergeInsertIntoTableResponse(
            num_updated_rows=10,
            num_inserted_rows=5,
            num_deleted_rows=0,
            version=3
        )
        namespace.table_api.merge_insert_into_table.return_value = expected_response

        # Execute
        response = namespace.merge_insert_into_table(request, request_data)

        # Verify
        assert response == expected_response
        namespace.table_api.merge_insert_into_table.assert_called_once_with(
            id="ns1.table1",
            on="id",
            body=request_data,
            delimiter=".",
            when_matched_update_all=True,
            when_matched_update_all_filt=None,
            when_not_matched_insert_all=True,
            when_not_matched_by_source_delete=None,
            when_not_matched_by_source_delete_filt=None,
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_update_table(self, namespace):
        """Test update_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = UpdateTableRequest(updates=[["name", "UPPER(name)"], ["age", "age + 1"]])
        request.id = ["ns1", "table1"]
        request.predicate = "age > 25"

        expected_response = UpdateTableResponse(
            updated_rows=50,
            version=4
        )
        namespace.table_api.update_table.return_value = expected_response

        # Execute
        response = namespace.update_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.update_table.assert_called_once_with(
            id="ns1.table1",
            update_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_delete_from_table(self, namespace):
        """Test delete_from_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DeleteFromTableRequest(predicate="age < 18")
        request.id = ["ns1", "table1"]

        expected_response = DeleteFromTableResponse(version=5)
        namespace.table_api.delete_from_table.return_value = expected_response

        # Execute
        response = namespace.delete_from_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.delete_from_table.assert_called_once_with(
            id="ns1.table1",
            delete_from_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_query_table(self, namespace):
        """Test query_table method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = QueryTableRequest(vector={"single_vector": [1.0, 2.0, 3.0]}, k=10)
        request.id = ["ns1", "table1"]
        request.filter = "category = 'books'"

        expected_response = b"arrow ipc data"
        namespace.table_api.query_table.return_value = expected_response

        # Execute
        response = namespace.query_table(request)

        # Verify
        assert response == expected_response
        namespace.table_api.query_table.assert_called_once_with(
            id="ns1.table1",
            query_table_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_create_table_index(self, namespace):
        """Test create_table_index method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = CreateTableIndexRequest(column="embedding", index_type="IVF_FLAT")
        request.id = ["ns1", "table1"]
        request.metric_type = "l2"

        expected_response = CreateTableIndexResponse(
            location="",
            properties={}
        )
        namespace.table_api.create_table_index.return_value = expected_response

        # Execute
        response = namespace.create_table_index(request)

        # Verify
        assert response == expected_response
        namespace.table_api.create_table_index.assert_called_once_with(
            id="ns1.table1",
            create_table_index_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_list_table_indices(self, namespace):
        """Test list_table_indices method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = ListTableIndicesRequest()
        request.id = ["ns1", "table1"]
        request.version = None

        expected_response = ListTableIndicesResponse(indexes=[])
        namespace.table_api.list_table_indices.return_value = expected_response

        # Execute
        response = namespace.list_table_indices(request)

        # Verify
        assert response == expected_response
        namespace.table_api.list_table_indices.assert_called_once_with(
            id="ns1.table1",
            list_table_indices_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_describe_table_index_stats(self, namespace):
        """Test describe_table_index_stats method."""
        # Setup
        namespace.table_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DescribeTableIndexStatsRequest()
        request.id = ["ns1", "table1"]
        request.version = None
        index_name = "embedding_idx"

        expected_response = DescribeTableIndexStatsResponse(
            distance_type="l2",
            index_type="IVF_FLAT",
            num_indexed_rows=1000,
            num_unindexed_rows=0
        )
        namespace.table_api.describe_table_index_stats.return_value = expected_response

        # Execute
        response = namespace.describe_table_index_stats(request, index_name)

        # Verify
        assert response == expected_response
        namespace.table_api.describe_table_index_stats.assert_called_once_with(
            id="ns1.table1",
            index_name=index_name,
            describe_table_index_stats_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_describe_transaction(self, namespace):
        """Test describe_transaction method."""
        # Setup
        namespace.transaction_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        request = DescribeTransactionRequest()
        request.id = ["tx123"]

        expected_response = DescribeTransactionResponse(
            status="SUCCEEDED",
            properties={}
        )
        namespace.transaction_api.describe_transaction.return_value = expected_response

        # Execute
        response = namespace.describe_transaction(request)

        # Verify
        assert response == expected_response
        namespace.transaction_api.describe_transaction.assert_called_once_with(
            id="tx123",
            describe_transaction_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )

    def test_alter_transaction(self, namespace):
        """Test alter_transaction method."""
        # Setup
        namespace.transaction_api = Mock()
        namespace.config = RestNamespaceConfig({
            "delimiter": ".",
            "header.Authorization": "Bearer test-token"
        })

        action = AlterTransactionAction()
        request = AlterTransactionRequest(actions=[action])
        request.id = ["tx123"]

        expected_response = AlterTransactionResponse(
            status="SUCCEEDED",
            properties={}
        )
        namespace.transaction_api.alter_transaction.return_value = expected_response

        # Execute
        response = namespace.alter_transaction(request)

        # Verify
        assert response == expected_response
        namespace.transaction_api.alter_transaction.assert_called_once_with(
            id="tx123",
            alter_transaction_request=request,
            delimiter=".",
            _headers={"Authorization": "Bearer test-token"}
        )