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
    """
    if _visited is None:
        _visited = set()

    # Resolve $ref
    if "$ref" in schema:
        ref = schema["$ref"]
        if ref in _visited:
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

    # object / properties
    if schema.get("type") == "object" or "properties" in schema:
        return {
            k: _minimal_body(v, spec, _visited)
            for k, v in schema.get("properties", {}).items()
        }

    # array
    if schema.get("type") == "array":
        items = schema.get("items", {})
        return [_minimal_body(items, spec, _visited)] if items else []

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


def _response_body(operation: dict[str, Any], spec: dict[str, Any]) -> Any:
    """Return a minimal JSON body for the 200 response of *operation*.

    Returns None if the 200 response has no JSON content (e.g. 204-style).
    """
    responses = operation.get("responses", {})
    # yaml.safe_load parses HTTP codes as ints
    resp_200 = responses.get(200) or responses.get("200") or {}

    # Resolve $ref at the response level
    if "$ref" in resp_200:
        resp_200 = _resolve_ref(resp_200["$ref"], spec)

    content = resp_200.get("content", {})

    # Prefer application/json; fall back to first JSON-ish content type
    for mime, media_obj in content.items():
        if "application/json" not in mime:
            continue
        if not isinstance(media_obj, dict):
            continue
        schema = media_obj.get("schema") or {}
        if schema:
            return _minimal_body(schema, spec, None)

    # No JSON content → empty body (operation may return 200 with no body)
    return None


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
            body = _response_body(operation, spec)

            # Build WireMock mapping
            mapping: dict[str, Any] = {
                "name": operation_id,
                "request": {
                    "method": method.upper(),
                    "urlPathPattern": url_pattern,
                },
                "response": {
                    "status": 200,
                    "headers": {"Content-Type": "application/json"},
                },
            }

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
