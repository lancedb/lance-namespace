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
Pact consumer tests for the Namespace API — consumer lance-namespace-python-urllib3.

Covers all 5 MVP Namespace operations:
  - ListNamespaces   (GET  /v1/namespace/{id}/list)
  - DescribeNamespace (POST /v1/namespace/{id}/describe)
  - CreateNamespace  (POST /v1/namespace/{id}/create)
  - DropNamespace    (POST /v1/namespace/{id}/drop)
  - NamespaceExists  (POST /v1/namespace/{id}/exists)

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
# ListNamespaces
# ─────────────────────────────────────────────────────────────────────────────


class TestListNamespaces:
    """GET /v1/namespace/{id}/list"""

    def test_list_namespaces_returns_items(self) -> None:
        """200: ns_existing has child namespaces → returns array of string names."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("List child namespaces under ns_existing returns 3 items")
            .given("namespace 'ns_existing' has 3 tables")
            .with_request(method="GET", path="/v1/namespace/ns_existing/list")
            .with_header("Accept", JSON_CONTENT_TYPE)
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"namespaces": match.each_like("child_a", min=1)})
        )

        with pact.serve() as srv:
            status, body = _get(srv.url, "/v1/namespace/ns_existing/list")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert isinstance(body.get("namespaces"), list)
        assert len(body["namespaces"]) >= 1
        for ns in body["namespaces"]:
            assert isinstance(ns, str)

    def test_list_namespaces_returns_404(self) -> None:
        """404: ns_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("List child namespaces under non-existent namespace returns 404")
            .given("namespace 'ns_missing' does not exist")
            .with_request(method="GET", path="/v1/namespace/ns_missing/list")
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
            status, body = _get(srv.url, "/v1/namespace/ns_missing/list")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)
        assert isinstance(body.get("code"), int)
        assert isinstance(body.get("type"), str)
        assert isinstance(body.get("detail"), str)


# ─────────────────────────────────────────────────────────────────────────────
# DescribeNamespace
# ─────────────────────────────────────────────────────────────────────────────


class TestDescribeNamespace:
    """POST /v1/namespace/{id}/describe"""

    def test_describe_namespace_returns_properties(self) -> None:
        """200: ns_existing exists → returns properties object."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Describe namespace ns_existing returns properties")
            .given("namespace 'ns_existing' has 3 tables")
            .with_request(method="POST", path="/v1/namespace/ns_existing/describe")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"properties": match.like({})})
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/namespace/ns_existing/describe")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert "properties" in body

    def test_describe_namespace_returns_404(self) -> None:
        """404: ns_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Describe non-existent namespace returns 404")
            .given("namespace 'ns_missing' does not exist")
            .with_request(method="POST", path="/v1/namespace/ns_missing/describe")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
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
            status, body = _post(srv.url, "/v1/namespace/ns_missing/describe")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)
        assert isinstance(body.get("code"), int)


# ─────────────────────────────────────────────────────────────────────────────
# CreateNamespace
# ─────────────────────────────────────────────────────────────────────────────


class TestCreateNamespace:
    """POST /v1/namespace/{id}/create"""

    def test_create_namespace_returns_200(self) -> None:
        """200: ns_new does not exist → namespace is created, returns properties."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Create namespace ns_new returns created namespace")
            .given("namespace 'ns_new' does not exist")
            .with_request(method="POST", path="/v1/namespace/ns_new/create")
            .with_body('{"mode":"Create"}', JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body({"properties": match.like({})})
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/namespace/ns_new/create", {"mode": "Create"})

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200
        assert "properties" in body

    def test_create_namespace_returns_409(self) -> None:
        """409: ns_existing already exists → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Create already-existing namespace returns 409 conflict")
            .given("namespace 'ns_existing' has 3 tables")
            .with_request(method="POST", path="/v1/namespace/ns_existing/create")
            .with_body('{"mode":"Create"}', JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(409)
            .with_header("Content-Type", JSON_CONTENT_TYPE)
            .with_body(error_response_body(
                "NAMESPACE_ALREADY_EXISTS",
                409,
                "org.lance.namespace.NamespaceAlreadyExistsException",
                "Namespace ns_existing already exists",
            ))
        )

        with pact.serve() as srv:
            status, body = _post(srv.url, "/v1/namespace/ns_existing/create", {"mode": "Create"})

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 409
        assert isinstance(body.get("error"), str)
        assert isinstance(body.get("code"), int)


# ─────────────────────────────────────────────────────────────────────────────
# DropNamespace
# ─────────────────────────────────────────────────────────────────────────────


class TestDropNamespace:
    """POST /v1/namespace/{id}/drop"""

    def test_drop_namespace_returns_200(self) -> None:
        """200: ns_empty is empty → namespace is dropped."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Drop empty namespace ns_empty returns 200")
            .given("namespace 'ns_empty' exists and is empty")
            .with_request(method="POST", path="/v1/namespace/ns_empty/drop")
            .with_body('{"mode":"Fail","behavior":"Restrict"}', JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
        )

        with pact.serve() as srv:
            status, _body = _post(
                srv.url, "/v1/namespace/ns_empty/drop", {"mode": "Fail", "behavior": "Restrict"}
            )

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200

    def test_drop_namespace_returns_404(self) -> None:
        """404: ns_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Drop non-existent namespace returns 404")
            .given("namespace 'ns_missing' does not exist")
            .with_request(method="POST", path="/v1/namespace/ns_missing/drop")
            .with_body('{"mode":"Fail","behavior":"Restrict"}', JSON_CONTENT_TYPE, part="Request")
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
            status, body = _post(
                srv.url, "/v1/namespace/ns_missing/drop", {"mode": "Fail", "behavior": "Restrict"}
            )

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)


# ─────────────────────────────────────────────────────────────────────────────
# NamespaceExists
# ─────────────────────────────────────────────────────────────────────────────


class TestNamespaceExists:
    """POST /v1/namespace/{id}/exists"""

    def test_namespace_exists_returns_200(self) -> None:
        """200: ns_existing exists → 200 no-content response."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Check existence of ns_existing returns 200 no content")
            .given("namespace 'ns_existing' has 3 tables")
            .with_request(method="POST", path="/v1/namespace/ns_existing/exists")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
            .will_respond_with(200)
        )

        with pact.serve() as srv:
            status, _body = _post(srv.url, "/v1/namespace/ns_existing/exists")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 200

    def test_namespace_exists_returns_404(self) -> None:
        """404: ns_missing does not exist → returns ErrorResponse."""
        pact = _new_pact()
        (
            pact
            .upon_receiving("Check existence of ns_missing returns 404")
            .given("namespace 'ns_missing' does not exist")
            .with_request(method="POST", path="/v1/namespace/ns_missing/exists")
            .with_body("{}", JSON_CONTENT_TYPE, part="Request")
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
            status, body = _post(srv.url, "/v1/namespace/ns_missing/exists")

        pact.write_file(PACT_DIR, overwrite=True)

        assert status == 404
        assert isinstance(body.get("error"), str)
