# coding: utf-8

"""
    Lance Namespace Specification

    This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 

    The version of the OpenAPI document: 1.0.0
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


import unittest

from lance_namespace_urllib3_client.models.alter_table_add_columns_request import AlterTableAddColumnsRequest

class TestAlterTableAddColumnsRequest(unittest.TestCase):
    """AlterTableAddColumnsRequest unit test stubs"""

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def make_instance(self, include_optional) -> AlterTableAddColumnsRequest:
        """Test AlterTableAddColumnsRequest
            include_optional is a boolean, when False only required
            params are included, when True both required and
            optional params are included """
        # uncomment below to create an instance of `AlterTableAddColumnsRequest`
        """
        model = AlterTableAddColumnsRequest()
        if include_optional:
            return AlterTableAddColumnsRequest(
                id = [
                    ''
                    ],
                new_columns = [
                    lance_namespace_urllib3_client.models.new_column_transform.NewColumnTransform(
                        name = '', 
                        expression = '', )
                    ]
            )
        else:
            return AlterTableAddColumnsRequest(
                new_columns = [
                    lance_namespace_urllib3_client.models.new_column_transform.NewColumnTransform(
                        name = '', 
                        expression = '', )
                    ],
        )
        """

    def testAlterTableAddColumnsRequest(self):
        """Test AlterTableAddColumnsRequest"""
        # inst_req_only = self.make_instance(include_optional=False)
        # inst_req_and_optional = self.make_instance(include_optional=True)

if __name__ == '__main__':
    unittest.main()
