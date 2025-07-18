# coding: utf-8

"""
    Lance REST Namespace Specification

    This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts: The `components/schemas`, `components/responses`, `components/examples` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details. The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 

    The version of the OpenAPI document: 0.0.1
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


import unittest

from lance_namespace_urllib3_client.models.describe_table_response_v2 import DescribeTableResponseV2

class TestDescribeTableResponseV2(unittest.TestCase):
    """DescribeTableResponseV2 unit test stubs"""

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def make_instance(self, include_optional) -> DescribeTableResponseV2:
        """Test DescribeTableResponseV2
            include_optional is a boolean, when False only required
            params are included, when True both required and
            optional params are included """
        # uncomment below to create an instance of `DescribeTableResponseV2`
        """
        model = DescribeTableResponseV2()
        if include_optional:
            return DescribeTableResponseV2(
                name = '',
                namespace = [
                    ''
                    ],
                location = '',
                properties = {
                    'key' : ''
                    }
            )
        else:
            return DescribeTableResponseV2(
                name = '',
                namespace = [
                    ''
                    ],
                location = '',
        )
        """

    def testDescribeTableResponseV2(self):
        """Test DescribeTableResponseV2"""
        # inst_req_only = self.make_instance(include_optional=False)
        # inst_req_and_optional = self.make_instance(include_optional=True)

if __name__ == '__main__':
    unittest.main()
