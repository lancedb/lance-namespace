# coding: utf-8

"""
    Lance Namespace Specification

    This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 

    The version of the OpenAPI document: 1.0.0
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


from __future__ import annotations
import pprint
import re  # noqa: F401
import json

from pydantic import BaseModel, ConfigDict, Field, StrictStr
from typing import Any, ClassVar, Dict, List, Optional
from lance_namespace_urllib3_client.models.index_content import IndexContent
from typing import Optional, Set
from typing_extensions import Self

class ListTableIndicesResponse(BaseModel):
    """
    ListTableIndicesResponse
    """ # noqa: E501
    indexes: List[IndexContent] = Field(description="List of indexes on the table")
    page_token: Optional[StrictStr] = Field(default=None, description="An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation,  if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request,  the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either `null`,  missing value or empty string value of the page token from the implementation's response as the end of the listing results. ")
    __properties: ClassVar[List[str]] = ["indexes", "page_token"]

    model_config = ConfigDict(
        populate_by_name=True,
        validate_assignment=True,
        protected_namespaces=(),
    )


    def to_str(self) -> str:
        """Returns the string representation of the model using alias"""
        return pprint.pformat(self.model_dump(by_alias=True))

    def to_json(self) -> str:
        """Returns the JSON representation of the model using alias"""
        # TODO: pydantic v2: use .model_dump_json(by_alias=True, exclude_unset=True) instead
        return json.dumps(self.to_dict())

    @classmethod
    def from_json(cls, json_str: str) -> Optional[Self]:
        """Create an instance of ListTableIndicesResponse from a JSON string"""
        return cls.from_dict(json.loads(json_str))

    def to_dict(self) -> Dict[str, Any]:
        """Return the dictionary representation of the model using alias.

        This has the following differences from calling pydantic's
        `self.model_dump(by_alias=True)`:

        * `None` is only added to the output dict for nullable fields that
          were set at model initialization. Other fields with value `None`
          are ignored.
        """
        excluded_fields: Set[str] = set([
        ])

        _dict = self.model_dump(
            by_alias=True,
            exclude=excluded_fields,
            exclude_none=True,
        )
        # override the default output from pydantic by calling `to_dict()` of each item in indexes (list)
        _items = []
        if self.indexes:
            for _item_indexes in self.indexes:
                if _item_indexes:
                    _items.append(_item_indexes.to_dict())
            _dict['indexes'] = _items
        return _dict

    @classmethod
    def from_dict(cls, obj: Optional[Dict[str, Any]]) -> Optional[Self]:
        """Create an instance of ListTableIndicesResponse from a dict"""
        if obj is None:
            return None

        if not isinstance(obj, dict):
            return cls.model_validate(obj)

        _obj = cls.model_validate({
            "indexes": [IndexContent.from_dict(_item) for _item in obj["indexes"]] if obj.get("indexes") is not None else None,
            "page_token": obj.get("page_token")
        })
        return _obj


