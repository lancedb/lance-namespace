# coding: utf-8

"""
    Lance Namespace Specification

    This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 

    The version of the OpenAPI document: 1.0.0
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


import unittest

from lance_namespace_urllib3_client.api.index_api import IndexApi


class TestIndexApi(unittest.TestCase):
    """IndexApi unit test stubs"""

    def setUp(self) -> None:
        self.api = IndexApi()

    def tearDown(self) -> None:
        pass

    def test_create_table_index(self) -> None:
        """Test case for create_table_index

        Create an index on a table
        """
        pass

    def test_describe_table_index_stats(self) -> None:
        """Test case for describe_table_index_stats

        Get table index statistics
        """
        pass

    def test_drop_table_index(self) -> None:
        """Test case for drop_table_index

        Drop a specific index
        """
        pass

    def test_list_table_indices(self) -> None:
        """Test case for list_table_indices

        List indexes on a table
        """
        pass


if __name__ == '__main__':
    unittest.main()
