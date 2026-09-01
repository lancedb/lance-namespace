#!/usr/bin/env python3
"""Generate WireMock JSON mapping files for all operations in the OpenAPI spec.

Reads docs/src/spec.yaml and produces one WireMock stub mapping JSON file per
operation (49 total) in the output directory.  Each stub:

  - Matches on HTTP method + URL path pattern (path params become .*  wildcards)
  - Returns HTTP 200 with a minimal JSON body derived from the response schema
  - Sets Content-Type: application/json

Usage:
    uv run python ci/cts/gen_wiremock_mappings.py \\
        --spec docs/src/spec.yaml \\
        --output build/cts/wiremock/src/main/resources/mappings
"""
from __future__ import annotations

import argparse
import json
import logging
import re
import sys
from pathlib import Path
from typing import Any

import yaml

log = logging.getLogger(__name__)

_HTTP_METHODS = frozenset(("get", "post", "put", "patch", "delete"))


# ---------------------------------------------------------------------------
# Schema helpers
# ---------------------------------------------------------------------------

def _resolve_ref(ref: str, spec: dict[str, Any]) -> dict[str, Any]:
    """Follow a single $ref within the same document."""
    if not ref.startswith("#/"):
        return {}
    parts = ref.lstrip("#/").split("/")
    node: Any = spec
    for part in parts:
        if not isinstance(node, dict):
            return {}
        node = node.get(part, {})
    return node if isinstance(node, dict) else {}


def _minimal_body(
    schema: dict[str, Any],
    spec: dict[str, Any],
    _visited: set[str] | None = None,
) -> Any:
    """Produce a minimal valid JSON value for *schema*.

    Handles $ref, allOf, anyOf/oneOf, object, array, and scalar types.
    Cycle detection via *_visited* prevents infinite recursion on circular refs.

    Object handling: only ``required`` properties are emitted.  This keeps
    the stub body strictly schema-compliant (every required+non-nullable
    field has a real value of the right type) and naturally breaks cycles
    that occur only through optional fields (e.g. ``JsonArrowField.type
    → JsonArrowDataType.fields → JsonArrowField`` — the inner ``fields`` is
    optional and so is dropped).  When a schema has no ``required``
    declared we fall back to emitting all properties so existing operations
    relying on full payloads (e.g. ``DescribeTable`` top-level fields) keep
    working.
    """
    if _visited is None:
        _visited = set()

    # Resolve $ref
    if "$ref" in schema:
        ref = schema["$ref"]
        if ref in _visited:
            # Cycle hit on a model that only recurses through optional
            # fields — return ``{}`` and let the caller's required-only
            # filter ensure callers don't rely on this value.
            return {}
        _visited = _visited | {ref}  # immutable update — no mutation
        resolved = _resolve_ref(ref, spec)
        return _minimal_body(resolved, spec, _visited)

    # allOf — merge examples from all sub-schemas
    if "allOf" in schema:
        merged: dict[str, Any] = {}
        for sub in schema.get("allOf", []):
            ex = _minimal_body(sub, spec, _visited)
            if isinstance(ex, dict):
                merged.update(ex)
        return merged

    # anyOf / oneOf — use first candidate
    for key in ("anyOf", "oneOf"):
        if key in schema:
            candidates = schema[key]
            return _minimal_body(candidates[0], spec, _visited) if candidates else {}

    # object / properties — emit only required fields when ``required`` is
    # declared, so cyclic schemas terminate at the first optional edge.
    if schema.get("type") == "object" or "properties" in schema:
        properties: dict[str, Any] = schema.get("properties", {}) or {}
        required = schema.get("required")
        if isinstance(required, list) and required:
            keys = [k for k in required if k in properties]
        else:
            keys = list(properties.keys())
        return {k: _minimal_body(properties[k], spec, _visited) for k in keys}

    # array — honour minItems so schemas like ``actions: minItems=1`` produce
    # at least one element; otherwise emit a single sample item.
    if schema.get("type") == "array":
        items = schema.get("items", {})
        if not items:
            return []
        min_items = schema.get("minItems", 1) or 1
        sample = _minimal_body(items, spec, _visited)
        return [sample] * max(1, int(min_items))

    # scalars
    type_ = schema.get("type", "")
    example = schema.get("example")
    if type_ == "string":
        return example if example is not None else "string"
    if type_ == "integer":
        return example if example is not None else 0
    if type_ == "number":
        return example if example is not None else 0.0
    if type_ == "boolean":
        return example if example is not None else False
    if type_ == "null":
        return None

    return {}


def _response_body(operation: dict[str, Any], spec: dict[str, Any]) -> tuple[Any, int]:
    """Return ``(jsonBody, status)`` for the success response of *operation*.

    Searches the responses block for the first 2xx code (200/201/202/204…)
    in numeric order, since some operations declare only ``202`` (e.g.
    async refresh / backfill jobs) and a hard-coded 200 stub would never
    match them on the client deserialization path.

    Returns ``(None, 200)`` if no 2xx response with a JSON body exists —
    callers then emit an empty-body 200 stub for backwards compatibility
    with no-content endpoints.
    """
    responses = operation.get("responses", {})

    # Sort 2xx codes numerically so 200 wins over 201/202 when multiple
    # success codes are declared (rare but legal).
    success_codes: list[tuple[int, Any]] = []
    for raw_code, resp in responses.items():
        try:
            code = int(raw_code)
        except (TypeError, ValueError):
            continue
        if 200 <= code < 300:
            success_codes.append((code, resp))
    success_codes.sort(key=lambda kv: kv[0])

    for code, resp in success_codes:
        if "$ref" in resp:
            resp = _resolve_ref(resp["$ref"], spec)
        content = resp.get("content", {})
        for mime, media_obj in content.items():
            if "application/json" not in mime:
                continue
            if not isinstance(media_obj, dict):
                continue
            schema = media_obj.get("schema") or {}
            if schema:
                return _minimal_body(schema, spec, None), code
        # 204-style with declared response but no JSON content — return
        # the code with no body so we can emit a no-content stub.
        if not content:
            return None, code

    return None, 200


# ---------------------------------------------------------------------------
# Path conversion
# ---------------------------------------------------------------------------

def _path_to_wiremock_pattern(path: str) -> str:
    """Convert an OpenAPI path template to a WireMock urlPathPattern regex.

    Example: /v1/namespace/{id}/list  →  /v1/namespace/[^/]+/list
    """
    # Replace {param} with a segment-matching pattern
    pattern = re.sub(r"\{[^}]+\}", "[^/]+", path)
    return pattern


def _operation_filename(method: str, path: str, operation_id: str) -> str:
    """Produce a safe filename for the mapping JSON."""
    safe_op = re.sub(r"[^a-zA-Z0-9_-]", "_", operation_id)
    return f"{safe_op}.json"


# ---------------------------------------------------------------------------
# Request matching helpers (P0-1, P0-3)
# ---------------------------------------------------------------------------

def _merged_parameters(
    path_item: dict[str, Any],
    operation: dict[str, Any],
    spec: dict[str, Any],
) -> list[dict[str, Any]]:
    """Return the effective parameter list for *operation*.

    OpenAPI lets the same parameter be declared either at the path-item
    level (``paths/<path>/parameters``) or at the operation level
    (``paths/<path>/<method>/parameters``).  Operation-level declarations
    override path-level ones with the same ``(name, in)`` pair.  All
    ``$ref`` entries are resolved here so callers see plain parameter
    objects.
    """
    by_key: dict[tuple[str, str], dict[str, Any]] = {}

    def _add(raw: Any) -> None:
        if not isinstance(raw, dict):
            return
        param = _resolve_ref(raw["$ref"], spec) if "$ref" in raw else raw
        name = param.get("name")
        loc = param.get("in")
        if not name or not loc:
            return
        by_key[(name, loc)] = param

    for raw in path_item.get("parameters", []) or []:
        _add(raw)
    for raw in operation.get("parameters", []) or []:
        _add(raw)
    return list(by_key.values())


def _build_request_matchers(
    path_item: dict[str, Any],
    operation: dict[str, Any],
    spec: dict[str, Any],
) -> dict[str, Any]:
    """Build the WireMock ``request`` extras for required-param / Arrow body matching.

    Returns a dict with optional ``queryParameters`` / ``headers`` keys to be
    merged into the base ``request`` block.  Path parameters are intentionally
    skipped: ``urlPathPattern`` already enforces their presence by replacing
    ``{x}`` with ``[^/]+``.

    Three matching rules are produced:

    * Required ``in: query`` params  → ``queryParameters[name] = {"matches": ".+"}``
    * Required ``in: header`` params → ``headers[name]         = {"matches": ".+"}``
    * Arrow IPC ``requestBody``      → ``headers["Content-Type"] = {"contains": "arrow.stream"}``

    No matching is added for optional params here — those are handled by
    later passes (P1) that compare specific values.
    """
    extras: dict[str, dict[str, Any]] = {}

    # Required query / header parameters → existence match.
    for param in _merged_parameters(path_item, operation, spec):
        if not param.get("required"):
            continue
        name = param["name"]
        loc = param["in"]
        if loc == "query":
            extras.setdefault("queryParameters", {})[name] = {"matches": ".+"}
        elif loc == "header":
            extras.setdefault("headers", {})[name] = {"matches": ".+"}
        # path: enforced by urlPathPattern; cookie: not used in this spec.

    # Arrow IPC request body → enforce Content-Type so clients sending the
    # wrong mime type fail the contract.  We use ``contains`` to tolerate
    # ``charset=...`` suffixes the client libraries may attach.
    request_body = operation.get("requestBody") or {}
    if "$ref" in request_body:
        request_body = _resolve_ref(request_body["$ref"], spec)
    content = request_body.get("content", {}) if isinstance(request_body, dict) else {}
    for mime in content:
        if "arrow.stream" in mime:
            extras.setdefault("headers", {})["Content-Type"] = {
                "contains": "arrow.stream"
            }
            break

    return extras


# ---------------------------------------------------------------------------
# Main generator
# ---------------------------------------------------------------------------

def generate_mappings(spec: dict[str, Any], output_dir: Path) -> int:
    """Write one WireMock mapping file per operation.  Returns count written."""
    output_dir.mkdir(parents=True, exist_ok=True)
    written = 0

    for path, path_item in spec.get("paths", {}).items():
        if not isinstance(path_item, dict):
            continue

        url_pattern = _path_to_wiremock_pattern(path)

        for method, operation in path_item.items():
            if method not in _HTTP_METHODS:
                continue
            if not isinstance(operation, dict):
                continue

            operation_id = operation.get("operationId", f"{method}_{path}")
            body, status = _response_body(operation, spec)

            # Build WireMock mapping
            mapping: dict[str, Any] = {
                "name": operation_id,
                "request": {
                    "method": method.upper(),
                    "urlPathPattern": url_pattern,
                },
                "response": {
                    "status": status,
                    "headers": {"Content-Type": "application/json"},
                },
            }

            # Augment the request matcher with required-query / required-header
            # / Arrow Content-Type rules so contract tests catch clients that
            # silently drop them.
            extras = _build_request_matchers(path_item, operation, spec)
            for key, value in extras.items():
                mapping["request"][key] = value

            if body is not None:
                mapping["response"]["jsonBody"] = body
            else:
                # No body (e.g. exists/204-style ops that return 200 no content)
                mapping["response"]["body"] = ""
                mapping["response"]["headers"] = {}

            filename = _operation_filename(method, path, operation_id)
            out_path = output_dir / filename
            out_path.write_text(json.dumps(mapping, indent=2))
            log.debug("Wrote %s → %s", operation_id, filename)
            written += 1

    return written


def main() -> None:
    logging.basicConfig(level=logging.WARNING, format="%(levelname)s: %(message)s")

    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--spec",
        default="docs/src/spec.yaml",
        help="Path to OpenAPI spec (default: docs/src/spec.yaml)",
    )
    parser.add_argument(
        "--output",
        default="build/cts/wiremock/src/main/resources/mappings",
        help="Output directory for mapping JSON files",
    )
    parser.add_argument(
        "--verbose", "-v", action="store_true", help="Enable debug logging"
    )
    args = parser.parse_args()

    if args.verbose:
        logging.getLogger().setLevel(logging.DEBUG)

    spec_path = Path(args.spec)
    if not spec_path.exists():
        print(f"ERROR: spec not found: {spec_path}", file=sys.stderr)
        sys.exit(1)

    with open(spec_path) as f:
        spec = yaml.safe_load(f)

    output_dir = Path(args.output)
    count = generate_mappings(spec, output_dir)
    print(f"Generated {count} WireMock mapping files in {output_dir}")


if __name__ == "__main__":
    main()
