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
"""
Pact consumer tests for the Table API — consumer lance-namespace-python-urllib3.

Covers all 7 MVP Table operations:
  - ListTables      (GET  /v1/namespace/{id}/table/list)
  - DescribeTable   (POST /v1/table/{id}/describe)
  - TableExists     (POST /v1/table/{id}/exists)
  - DropTable       (POST /v1/table/{id}/drop)
  - RegisterTable   (POST /v1/table/{id}/register)
  - DeregisterTable (POST /v1/table/{id}/deregister)

Each operation has a success (2xx) and error (4xx) interaction.
Provider state strings MUST match contract-pack/provider-states.lock.json verbatim.

Uses pact-python v3 API: each test builds its own Pact and starts the mock
server with ``pact.serve()`` as a context manager.
"""
import json
from typing import Any

import urllib3  # type: ignore[import-untyped]
from pact import Pact, match

from tests.pact_tests.conftest import CONSUMER_NAME, PACT_DIR, PROVIDER_NAME
from tests.pact_tests.error_response_dsl import error_response_body


JSON_CONTENT_TYPE = "application/json"


def _get(base_url: str, path: str) -> tuple[int, Any]:
    """Issue a GET request and return (status, parsed_body)."""
    http = urllib3.PoolManager()
    resp = http.request("GET", f"{base_url}{path}", headers={"Accept": JSON_CONTENT_TYPE})
    return resp.status, json.loads(resp.data.decode("utf-8"))


def _post(base_url: str, path: str, body: dict | None = None) -> tuple[int, Any]:
    """Issue a POST request and return (status, parsed_body_or_None)."""
    http = urllib3.PoolManager()
    encoded = json.dumps(body or {}).encode("utf-8")
    resp = http.request(
        "POST",
        f"{base_url}{path}",
        body=encoded,
        headers={"Content-Type": JSON_CONTENT_TYPE, "Accept": JSON_CONTENT_TYPE},
    )
    if resp.data:
        try:
            return resp.status, json.loads(resp.data.decode("utf-8"))
        except ValueError:
            return resp.status, None
    return resp.status, None


def _new_pact() -> Pact:
    """Return a fresh Pact for each test."""
    return Pact(CONSUMER_NAME, PROVIDER_NAME)


# ─────────────────────────────────────────────────────────────────────────────
# ListTables
# ─────────────────────────────────────────────────────────────────────────────


class TestListTables:
    """GET /v1/namespace/{id}/table/list"""

    def test_list_tables_returns_items(self) -> None:
        """200: ns_with_tables has 2 tables → returns array of string names."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("List tables in ns_with_tables returns 2 tables")
            .given("namespace 'ns_with_tables' has 2 tables")
            .with_request(method="GET", path="/v1/namespace/ns_with_tables/table/list")
            .with_header("Accept", JSON_CONTENT_TYPE)
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"tables": match.each_like("table_alpha", min=1)})
        )

        with pact.serve() as srv:
            status, body = _get(srv.url, "/v1/namespace/ns_with_tables/table/list")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert isinstance(body.get("tables"), list)
        assert len(body["tables"]) >= 1
        for tbl in body["tables"]:
            assert isinstance(tbl, str)

    def test_list_tables_returns_404(self) -> None:
        """404: ns_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("List tables in non-existent namespace returns 404")
            .given("namespace 'ns_missing' does not exist")
            .with_request(method="GET", path="/v1/namespace/ns_missing/table/list")
            .with_header("Accept", JSON_CONTENT_TYPE)
            .will_respond_with(404)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "NAMESPACE_NOT_FOUND",
                404,
                "org.lance.namespace.NamespaceNotFoundException",
                "Namespace ns_missing does not exist",
            ))
        )

        with pact.serve() as srv:
            status, body = _get(srv.url, "/v1/namespace/ns_missing/table/list")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)
        assert isinstance(body.get("code"), int)


# ─────────────────────────────────────────────────────────────────────────────
# DescribeTable
# ─────────────────────────────────────────────────────────────────────────────


class TestDescribeTable:
    """POST /v1/table/{id}/describe"""

    def test_describe_table_returns_200(self) -> None:
        """200: table_alpha exists → returns location field."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Describe table_alpha in ns_with_tables returns table info")
            .given("table 'ns_with_tables.table_alpha' exists")
            .with_request(method="POST", path="/v1/table/ns_with_tables.table_alpha/describe")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"location": match.like("s3://example/ns_with_tables/table_alpha")})
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/table/ns_with_tables.table_alpha/describe")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert isinstance(body.get("location"), str)

    def test_describe_table_returns_404(self) -> None:
        """404: table_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Describe non-existent table returns 404")
            .given("table 'ns_existing.table_missing' does not exist")
            .with_request(method="POST", path="/v1/table/ns_existing.table_missing/describe")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(404)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "TABLE_NOT_FOUND",
                404,
                "org.lance.namespace.TableNotFoundException",
                "Table ns_existing.table_missing does not exist",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/table/ns_existing.table_missing/describe")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)


# ─────────────────────────────────────────────────────────────────────────────
# TableExists
# ─────────────────────────────────────────────────────────────────────────────


class TestTableExists:
    """POST /v1/table/{id}/exists"""

    def test_table_exists_returns_200(self) -> None:
        """200: table_alpha exists → 200 no-content."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Check existence of table_alpha in ns_with_tables returns 200")
            .given("table 'ns_with_tables.table_alpha' exists")
            .with_request(method="POST", path="/v1/table/ns_with_tables.table_alpha/exists")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
        )

        with pact.serve() as srv:
            status, _body = _post(srv.url, "/v1/table/ns_with_tables.table_alpha/exists")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200

    def test_table_exists_returns_404(self) -> None:
        """404: table_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Check existence of non-existent table returns 404")
            .given("table 'ns_existing.table_missing' does not exist")
            .with_request(method="POST", path="/v1/table/ns_existing.table_missing/exists")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(404)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "TABLE_NOT_FOUND",
                404,
                "org.lance.namespace.TableNotFoundException",
                "Table ns_existing.table_missing does not exist",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/table/ns_existing.table_missing/exists")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)


# ─────────────────────────────────────────────────────────────────────────────
# DropTable
# ─────────────────────────────────────────────────────────────────────────────


class TestDropTable:
    """POST /v1/table/{id}/drop"""

    def test_drop_table_returns_200(self) -> None:
        """200: table_alpha exists → dropped successfully."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Drop table_alpha in ns_with_tables returns 200")
            .given("table 'ns_with_tables.table_alpha' exists")
            .with_request(method="POST", path="/v1/table/ns_with_tables.table_alpha/drop")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
        )

        with pact.serve() as srv:
            status, _body = _post(srv.url, "/v1/table/ns_with_tables.table_alpha/drop")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200

    def test_drop_table_returns_404(self) -> None:
        """404: table_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Drop non-existent table returns 404")
            .given("table 'ns_existing.table_missing' does not exist")
            .with_request(method="POST", path="/v1/table/ns_existing.table_missing/drop")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(404)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "TABLE_NOT_FOUND",
                404,
                "org.lance.namespace.TableNotFoundException",
                "Table ns_existing.table_missing does not exist",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/table/ns_existing.table_missing/drop")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)


# ─────────────────────────────────────────────────────────────────────────────
# RegisterTable
# ─────────────────────────────────────────────────────────────────────────────


class TestRegisterTable:
    """POST /v1/table/{id}/register"""

    def test_register_table_returns_200(self) -> None:
        """200: table_new does not exist → registered, returns location."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Register new table in ns_existing returns 200")
            .given("namespace 'ns_existing' has 3 tables")
            .with_request(method="POST", path="/v1/table/ns_existing.table_new/register")
            .with_body(
                '{"location":"s3://example/ns_existing/table_new","mode":"Create"}',
                JSON_CONTENT_TYPE,
                part="Request",
            )
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"location": match.like("s3://example/ns_existing/table_new")})
        )

        with pact.serve() as srv:
            status, body = _post(
                srv.url,
                "/v1/table/ns_existing.table_new/register",
                {"location": "s3://example/ns_existing/table_new", "mode": "Create"},
            )

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert isinstance(body.get("location"), str)

    def test_register_table_returns_409(self) -> None:
        """409: table_alpha already exists → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Register already-registered table returns 409")
            .given("table 'ns_with_tables.table_alpha' exists")
            .with_request(method="POST", path="/v1/table/ns_with_tables.table_alpha/register")
            .with_body(
                '{"location":"s3://example/ns_with_tables/table_alpha","mode":"Create"}',
                JSON_CONTENT_TYPE,
                part="Request",
            )
            .will_respond_with(409)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "TABLE_ALREADY_EXISTS",
                409,
                "org.lance.namespace.TableAlreadyExistsException",
                "Table ns_with_tables.table_alpha already exists",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(
                srv.url,
                "/v1/table/ns_with_tables.table_alpha/register",
                {"location": "s3://example/ns_with_tables/table_alpha", "mode": "Create"},
            )

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 409
        assert isinstance(body.get("error"), str)


# ─────────────────────────────────────────────────────────────────────────────
# DeregisterTable
# ─────────────────────────────────────────────────────────────────────────────


class TestDeregisterTable:
    """POST /v1/table/{id}/deregister"""

    def test_deregister_table_returns_200(self) -> None:
        """200: table_alpha exists → deregistered successfully."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Deregister table_alpha from ns_with_tables returns 200")
            .given("table 'ns_with_tables.table_alpha' exists")
            .with_request(method="POST", path="/v1/table/ns_with_tables.table_alpha/deregister")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
        )

        with pact.serve() as srv:
            status, _body = _post(srv.url, "/v1/table/ns_with_tables.table_alpha/deregister")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200

    def test_deregister_table_returns_404(self) -> None:
        """404: table_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Deregister non-existent table returns 404")
            .given("table 'ns_existing.table_missing' does not exist")
            .with_request(method="POST", path="/v1/table/ns_existing.table_missing/deregister")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(404)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "TABLE_NOT_FOUND",
                404,
                "org.lance.namespace.TableNotFoundException",
                "Table ns_existing.table_missing does not exist",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/table/ns_existing.table_missing/deregister")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)
