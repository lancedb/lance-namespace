#!/usr/bin/env python3
"""Generate OAI Overlay 1.0 examples from OpenAPI spec using jsf.

Usage: uv run python ci/cts/gen_examples_overlay.py [--spec PATH] [--output PATH]
"""
import argparse
import logging
import sys
from pathlib import Path
from typing import Any

import yaml

log = logging.getLogger(__name__)

# Track whether jsf is available so we only warn once.
_JSF_AVAILABLE: bool | None = None
_HTTP_METHODS = frozenset(("get", "post", "put", "patch", "delete"))


def _jsf_available() -> bool:
    global _JSF_AVAILABLE
    if _JSF_AVAILABLE is None:
        try:
            import jsf  # noqa: F401
            _JSF_AVAILABLE = True
        except ImportError:
            log.warning(
                "jsf is not installed; falling back to minimal structural examples. "
                "Install with: pip install jsf>=0.7.0"
            )
            _JSF_AVAILABLE = False
    return _JSF_AVAILABLE


def load_spec(spec_path: str) -> dict[str, Any]:
    """Load and parse OpenAPI spec (read-only)."""
    with open(spec_path) as f:
        data = yaml.safe_load(f)
    if not isinstance(data, dict):
        raise ValueError(f"Expected a YAML mapping at {spec_path}, got {type(data).__name__}")
    return data


def _resolve_schema(raw: dict[str, Any], spec: dict[str, Any]) -> dict[str, Any]:
    """Follow a $ref pointer into components/schemas.

    - Local #/components/schemas/<Name> refs are fully resolved.
    - Other refs (response refs, external refs) are returned unchanged so that
      callers can skip them rather than injecting a useless `example: {}`.
    """
    if "$ref" not in raw:
        return raw
    ref: str = raw["$ref"]
    if not ref.startswith("#/components/schemas/"):
        return raw  # Non-schema ref: let caller decide (skip or log)
    name = ref.removeprefix("#/components/schemas/")
    schemas: dict[str, Any] = spec.get("components", {}).get("schemas", {})
    if name not in schemas:
        log.warning("$ref '%s' not found in components/schemas; skipping", ref)
        return {}
    resolved = schemas[name]
    if not isinstance(resolved, dict):
        log.warning("$ref '%s' resolves to %s, expected dict; skipping", ref, type(resolved).__name__)
        return {}
    return resolved


def _minimal_example(schema: dict[str, Any], spec: dict[str, Any], _visited: set[str] | None = None) -> Any:
    """Generate a minimal structural example without jsf.

    Handles object, array, scalar types, allOf/anyOf/oneOf, and $ref in properties.
    Tracks visited refs to detect cycles and avoid infinite recursion.
    """
    if _visited is None:
        _visited = set()

    # Resolve $ref first
    if "$ref" in schema:
        ref_str = schema["$ref"]
        # Detect cycles: if we've already visited this ref, return empty to break the cycle
        if ref_str in _visited:
            log.debug("Circular $ref detected: %s; breaking cycle with {}", ref_str)
            return {}
        _visited.add(ref_str)
        resolved = _resolve_schema(schema, spec)
        if resolved is schema:
            # Non-schema ref that can't be resolved here — return empty
            return {}
        return _minimal_example(resolved, spec, _visited)

    # allOf — merge all sub-schema examples
    if "allOf" in schema:
        merged: dict[str, Any] = {}
        for sub in schema["allOf"]:
            if isinstance(sub, dict):
                sub_ex = _minimal_example(sub, spec, _visited)
                if isinstance(sub_ex, dict):
                    merged.update(sub_ex)
        return merged

    # anyOf / oneOf — use first candidate
    if "anyOf" in schema or "oneOf" in schema:
        candidates: list[Any] = schema.get("anyOf") or schema.get("oneOf") or []
        return _minimal_example(candidates[0], spec, _visited) if candidates else {}

    if schema.get("type") == "object" or "properties" in schema:
        return {
            k: _minimal_example(v, spec, _visited)
            for k, v in schema.get("properties", {}).items()
        }

    if schema.get("type") == "array":
        items = schema.get("items", {})
        return [_minimal_example(items, spec, _visited)] if items else []

    if schema.get("type") == "string":
        ex = schema.get("example")
        return ex if ex is not None else "string"

    if schema.get("type") == "integer":
        ex = schema.get("example")
        return ex if ex is not None else 0

    if schema.get("type") == "boolean":
        ex = schema.get("example")
        return ex if ex is not None else False

    if schema.get("type") == "number":
        ex = schema.get("example")
        return ex if ex is not None else 0.0

    return {}


def generate_example(raw_schema: dict[str, Any], spec: dict[str, Any]) -> Any:
    """Generate an example for a schema.

    Resolution happens first. jsf is used when available; falls back to
    _minimal_example with an explicit warning on jsf errors.
    """
    # Resolve $ref so downstream always receives a concrete schema dict.
    schema = _resolve_schema(raw_schema, spec)
    if not schema:
        return {}

    # Non-components/schemas ref that we can't resolve — skip rather than inject {}
    if "$ref" in schema:
        log.warning("Unresolvable $ref '%s'; skipping example", schema["$ref"])
        return None  # Caller must omit this action

    if _jsf_available():
        try:
            import jsf
            # jsf exports JSF class, not Jsf
            result = jsf.JSF(schema).generate()
            return result if result is not None else _minimal_example(schema, spec, None)
        except Exception as exc:  # noqa: BLE001
            log.warning("jsf generation failed: %s — using minimal fallback", exc)

    return _minimal_example(schema, spec, None)


def build_overlay(spec: dict[str, Any]) -> dict[str, Any]:
    """Build OAI Overlay 1.0 document from the spec."""
    actions: list[dict[str, Any]] = []
    schemas: dict[str, Any] = spec.get("components", {}).get("schemas", {})

    # Schema-level examples
    for schema_name, schema_def in schemas.items():
        if not isinstance(schema_def, dict):
            continue
        example = generate_example(schema_def, spec)
        if example is None:
            continue
        actions.append({
            "target": f"$.components.schemas.{schema_name}",
            "update": {"example": example},
        })

    # Operation request/response body examples
    for path, path_item in spec.get("paths", {}).items():
        if not isinstance(path_item, dict):
            continue
        for method, operation in path_item.items():
            # Skip path-level keys like 'parameters', 'summary', 'description'
            if method not in _HTTP_METHODS:
                continue
            if not isinstance(operation, dict):
                continue

            # Request body example
            req_body = operation.get("requestBody") or {}
            for media_type, media_obj in req_body.get("content", {}).items():
                if "application/json" not in media_type or not isinstance(media_obj, dict):
                    continue
                raw_schema = media_obj.get("schema") or {}
                if not raw_schema:
                    continue
                ex = generate_example(raw_schema, spec)
                if ex is None:
                    continue
                actions.append({
                    "target": (
                        f"$.paths['{path}'].{method}"
                        ".requestBody.content['application/json']"
                    ),
                    "update": {"example": ex},
                })

            # 200 response example
            # yaml.safe_load parses HTTP status codes as int, so key is 200 not "200".
            response_200_raw = operation.get("responses", {}).get(200) or {}
            # Responses may be $ref into components/responses — resolve before accessing content.
            if "$ref" in response_200_raw:
                resp_name = response_200_raw["$ref"].split("/")[-1]
                response_200_raw = (
                    spec.get("components", {}).get("responses", {}).get(resp_name) or {}
                )
            for media_type, media_obj in response_200_raw.get("content", {}).items():
                if "application/json" not in media_type or not isinstance(media_obj, dict):
                    continue
                raw_schema = media_obj.get("schema") or {}
                if not raw_schema:
                    continue
                ex = generate_example(raw_schema, spec)
                if ex is None:
                    continue
                actions.append({
                    "target": (
                        f"$.paths['{path}'].{method}"
                        ".responses['200'].content['application/json']"
                    ),
                    "update": {"example": ex},
                })

    return {
        "overlay": "1.0.0",
        "info": {
            "title": "Auto-generated examples overlay",
            "version": "1.0.0",
        },
        "actions": actions,
    }


def main() -> None:
    logging.basicConfig(level=logging.WARNING, format="%(levelname)s: %(message)s")

    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--spec", default="docs/src/spec.yaml", help="Path to spec.yaml")
    parser.add_argument(
        "--output",
        default="build/overlays/examples.auto.yaml",
        help="Output overlay path",
    )
    args = parser.parse_args()

    spec_path = Path(args.spec)
    output_path = Path(args.output)

    if not spec_path.exists():
        print(f"ERROR: spec not found at {spec_path}", file=sys.stderr)
        sys.exit(1)

    print(f"Loading spec from {spec_path}...")
    spec = load_spec(str(spec_path))

    print("Building overlay...")
    overlay = build_overlay(spec)

    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, "w") as f:
        yaml.dump(overlay, f, default_flow_style=False, allow_unicode=True, sort_keys=False)

    action_count = len(overlay["actions"])
    print(f"Written {action_count} overlay actions to {output_path}")


if __name__ == "__main__":
    main()
