# coding: utf-8

"""
    Lance Namespace Specification

    This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 

    The version of the OpenAPI document: 1.0.0
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


import unittest

from lance_namespace_urllib3_client.api.table_api import TableApi


class TestTableApi(unittest.TestCase):
    """TableApi unit test stubs"""

    def setUp(self) -> None:
        self.api = TableApi()

    def tearDown(self) -> None:
        pass

    def test_alter_table_add_columns(self) -> None:
        """Test case for alter_table_add_columns

        Add new columns to table schema
        """
        pass

    def test_alter_table_alter_columns(self) -> None:
        """Test case for alter_table_alter_columns

        Modify existing columns
        """
        pass

    def test_alter_table_drop_columns(self) -> None:
        """Test case for alter_table_drop_columns

        Remove columns from table
        """
        pass

    def test_analyze_table_query_plan(self) -> None:
        """Test case for analyze_table_query_plan

        Analyze query execution plan
        """
        pass

    def test_count_table_rows(self) -> None:
        """Test case for count_table_rows

        Count rows in a table
        """
        pass

    def test_create_table(self) -> None:
        """Test case for create_table

        Create a table with the given name
        """
        pass

    def test_create_table_index(self) -> None:
        """Test case for create_table_index

        Create an index on a table
        """
        pass

    def test_create_table_tag(self) -> None:
        """Test case for create_table_tag

        Create a new tag
        """
        pass

    def test_delete_from_table(self) -> None:
        """Test case for delete_from_table

        Delete rows from a table
        """
        pass

    def test_delete_table_tag(self) -> None:
        """Test case for delete_table_tag

        Delete a tag
        """
        pass

    def test_deregister_table(self) -> None:
        """Test case for deregister_table

        Deregister a table
        """
        pass

    def test_describe_table(self) -> None:
        """Test case for describe_table

        Describe information of a table
        """
        pass

    def test_describe_table_index_stats(self) -> None:
        """Test case for describe_table_index_stats

        Get table index statistics
        """
        pass

    def test_drop_table(self) -> None:
        """Test case for drop_table

        Drop a table
        """
        pass

    def test_drop_table_index(self) -> None:
        """Test case for drop_table_index

        Drop a specific index
        """
        pass

    def test_explain_table_query_plan(self) -> None:
        """Test case for explain_table_query_plan

        Get query execution plan explanation
        """
        pass

    def test_get_table_stats(self) -> None:
        """Test case for get_table_stats

        Get table statistics
        """
        pass

    def test_get_table_tag_version(self) -> None:
        """Test case for get_table_tag_version

        Get version for a specific tag
        """
        pass

    def test_insert_into_table(self) -> None:
        """Test case for insert_into_table

        Insert records into a table
        """
        pass

    def test_list_table_indices(self) -> None:
        """Test case for list_table_indices

        List indexes on a table
        """
        pass

    def test_list_table_tags(self) -> None:
        """Test case for list_table_tags

        List all tags for a table
        """
        pass

    def test_list_table_versions(self) -> None:
        """Test case for list_table_versions

        List all versions of a table
        """
        pass

    def test_list_tables(self) -> None:
        """Test case for list_tables

        List tables in a namespace
        """
        pass

    def test_merge_insert_into_table(self) -> None:
        """Test case for merge_insert_into_table

        Merge insert (upsert) records into a table
        """
        pass

    def test_query_table(self) -> None:
        """Test case for query_table

        Query a table
        """
        pass

    def test_register_table(self) -> None:
        """Test case for register_table

        Register a table to a namespace
        """
        pass

    def test_restore_table(self) -> None:
        """Test case for restore_table

        Restore table to a specific version
        """
        pass

    def test_table_exists(self) -> None:
        """Test case for table_exists

        Check if a table exists
        """
        pass

    def test_update_table(self) -> None:
        """Test case for update_table

        Update rows in a table
        """
        pass

    def test_update_table_tag(self) -> None:
        """Test case for update_table_tag

        Update a tag to point to a different version
        """
        pass


if __name__ == '__main__':
    unittest.main()
