#!/usr/bin/env python3
"""
openapi_pact_diff.py — OpenAPI vs Pact field coverage checker.

Usage:
    python ci/openapi_pact_diff.py \
        [--broker-url http://localhost:9292] \
        [--openapi-spec docs/src/rest.yaml] \
        [--output report.md]

Environment variables:
    PACT_BROKER_URL   — Pact Broker base URL (overridden by --broker-url)
    PACT_BROKER_TOKEN — Bearer token for broker authentication
    PACT_BROKER_USERNAME / PACT_BROKER_PASSWORD — Basic auth alternative

Exit codes:
    0 — no coverage gaps
    1 — coverage gaps found or fatal error
    2 — broker unavailable (graceful skip, no pact data to check)
"""
from __future__ import annotations

import argparse
import json
import os
import sys
from pathlib import Path
from typing import Any
from urllib.error import URLError
from urllib.request import Request, urlopen

try:
    import yaml  # type: ignore[import]
    _YAML_AVAILABLE = True
except ImportError:
    _YAML_AVAILABLE = False


# ---------------------------------------------------------------------------
# Types
# ---------------------------------------------------------------------------

Pact = dict[str, Any]
OpenApiSpec = dict[str, Any]
FieldSet = set[str]


# ---------------------------------------------------------------------------
# Broker communication
# ---------------------------------------------------------------------------

def _broker_request(url: str, token: str | None, username: str | None, password: str | None) -> bytes:
    """Make an authenticated GET request to the Pact Broker."""
    req = Request(url, headers={"Accept": "application/hal+json, application/json"})
    if token:
        req.add_header("Authorization", f"Bearer {token}")
    elif username and password:
        import base64
        creds = base64.b64encode(f"{username}:{password}".encode()).decode()
        req.add_header("Authorization", f"Basic {creds}")
    with urlopen(req, timeout=10) as resp:
        return resp.read()


def fetch_pacts(broker_url: str) -> list[Pact]:
    """
    Fetch all pacts from the Pact Broker.

    Returns an empty list if the broker is unavailable (graceful skip).
    """
    token = os.environ.get("PACT_BROKER_TOKEN")
    username = os.environ.get("PACT_BROKER_USERNAME")
    password = os.environ.get("PACT_BROKER_PASSWORD")

    # Step 1 — enumerate all pacts via HAL index
    try:
        index_data = _broker_request(f"{broker_url.rstrip('/')}/pacts/latest", token, username, password)
        index = json.loads(index_data)
    except (URLError, OSError, json.JSONDecodeError) as exc:
        print(f"[WARN] Pact Broker unavailable at {broker_url!r}: {exc}", file=sys.stderr)
        print("[WARN] Skipping broker pact fetch — no broker data to compare.", file=sys.stderr)
        return []

    pacts: list[Pact] = []
    links: list[dict[str, Any]] = []

    # HAL _links.pacts or embedded _embedded.pacts
    if "_links" in index and "pacts" in index["_links"]:
        links = index["_links"]["pacts"]
        if isinstance(links, dict):
            links = [links]
    elif "_embedded" in index and "pacts" in index["_embedded"]:
        for item in index["_embedded"]["pacts"]:
            link = item.get("_links", {}).get("self", {})
            if link:
                links.append(link)

    for link in links:
        href = link.get("href", "")
        if not href:
            continue
        try:
            pact_data = _broker_request(href, token, username, password)
            pact = json.loads(pact_data)
            pacts.append(pact)
        except (URLError, OSError, json.JSONDecodeError) as exc:
            print(f"[WARN] Failed to fetch pact from {href!r}: {exc}", file=sys.stderr)

    return pacts


# ---------------------------------------------------------------------------
# OpenAPI parsing
# ---------------------------------------------------------------------------

def _load_yaml_or_json(path: Path) -> dict[str, Any]:
    """Load a YAML or JSON file. Prefers PyYAML if available, falls back to JSON."""
    text = path.read_text(encoding="utf-8")
    if _YAML_AVAILABLE:
        return yaml.safe_load(text)
    # Attempt JSON (works for .json OpenAPI specs)
    try:
        return json.loads(text)
    except json.JSONDecodeError as exc:
        print(f"[ERROR] Cannot parse {path}: PyYAML not installed and file is not valid JSON: {exc}", file=sys.stderr)
        sys.exit(1)


def _resolve_ref(spec: OpenApiSpec, ref: str) -> dict[str, Any]:
    """Resolve a $ref string like '#/components/schemas/Foo' within the spec."""
    if not ref.startswith("#/"):
        return {}
    parts = ref.lstrip("#/").split("/")
    node: Any = spec
    for part in parts:
        if isinstance(node, dict) and part in node:
            node = node[part]
        else:
            return {}
    return node if isinstance(node, dict) else {}


def _collect_properties(spec: OpenApiSpec, schema: dict[str, Any]) -> FieldSet:
    """Recursively collect property names from a schema, resolving $ref."""
    if "$ref" in schema:
        schema = _resolve_ref(spec, schema["$ref"])

    fields: FieldSet = set()
    for prop in schema.get("properties", {}).keys():
        fields.add(prop)
    # allOf / oneOf / anyOf
    for combiner in ("allOf", "oneOf", "anyOf"):
        for sub in schema.get(combiner, []):
            fields |= _collect_properties(spec, sub)
    return fields


def _required_fields(spec: OpenApiSpec, schema: dict[str, Any]) -> FieldSet:
    """Return the set of required field names from a schema."""
    if "$ref" in schema:
        schema = _resolve_ref(spec, schema["$ref"])
    required: FieldSet = set(schema.get("required", []))
    for combiner in ("allOf", "oneOf", "anyOf"):
        for sub in schema.get("combiner", []):
            required |= _required_fields(spec, sub)
    return required


def parse_openapi(spec_path: Path) -> dict[tuple[str, str], dict[str, Any]]:
    """
    Parse an OpenAPI spec and return a map of (path, method) → {fields, required_fields}.

    path  — normalized path string (e.g. '/v1/namespace/{id}/list')
    method — uppercase HTTP method (e.g. 'GET')
    """
    spec: OpenApiSpec = _load_yaml_or_json(spec_path)
    result: dict[tuple[str, str], dict[str, Any]] = {}

    for path_str, path_item in spec.get("paths", {}).items():
        for method, operation in path_item.items():
            if method.upper() not in {"GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"}:
                continue
            if not isinstance(operation, dict):
                continue

            entry: dict[str, Any] = {
                "request_params": set(),
                "responses": {},
            }

            # Request parameters (query/path)
            for param in operation.get("parameters", []):
                if isinstance(param, dict) and "name" in param:
                    entry["request_params"].add(param["name"])

            # Request body fields
            req_body = operation.get("requestBody", {})
            if req_body:
                for media_type in req_body.get("content", {}).values():
                    schema = media_type.get("schema", {})
                    entry["request_body_fields"] = _collect_properties(spec, schema)
                    entry["request_body_required"] = _required_fields(spec, schema)
                    break

            # Response fields per status code
            for status_str, resp in operation.get("responses", {}).items():
                if not isinstance(resp, dict):
                    continue
                for media_type in resp.get("content", {}).values():
                    schema = media_type.get("schema", {})
                    entry["responses"][status_str] = {
                        "fields": _collect_properties(spec, schema),
                        "required": _required_fields(spec, schema),
                    }
                    break

            result[(path_str, method.upper())] = entry

    return result


# ---------------------------------------------------------------------------
# Coverage checking
# ---------------------------------------------------------------------------

def _extract_pact_body_fields(body: Any, prefix: str = "$") -> FieldSet:
    """Recursively extract field paths from a pact body object."""
    fields: FieldSet = set()
    if isinstance(body, dict):
        for key, value in body.items():
            field_path = f"{prefix}.{key}"
            fields.add(field_path)
            fields |= _extract_pact_body_fields(value, field_path)
    elif isinstance(body, list):
        for item in body:
            fields |= _extract_pact_body_fields(item, f"{prefix}[*]")
    return fields


def check_coverage(
    pacts: list[Pact],
    openapi_map: dict[tuple[str, str], dict[str, Any]],
) -> list[dict[str, Any]]:
    """
    For each pact interaction, cross-check field coverage against OpenAPI spec.

    Returns a list of gap records:
    {
        consumer, provider, description,
        path, method, status,
        gap_type: 'pact_has_unknown_field' | 'openapi_required_field_missing_in_pact',
        field,
        severity: 'WARNING' | 'ERROR'
    }
    """
    gaps: list[dict[str, Any]] = []

    for pact in pacts:
        consumer = pact.get("consumer", {}).get("name", "unknown")
        provider = pact.get("provider", {}).get("name", "unknown")

        for interaction in pact.get("interactions", []):
            description = interaction.get("description", "")
            request = interaction.get("request", {})
            response = interaction.get("response", {})

            req_path: str = request.get("path", "")
            req_method: str = request.get("method", "GET").upper()
            resp_status: int = response.get("status", 200)
            resp_body: Any = response.get("body")

            # Find matching OpenAPI entry (exact match first, then template match)
            oas_entry = openapi_map.get((req_path, req_method))
            if oas_entry is None:
                # Try to match path templates: /v1/namespace/ns_existing/list → /v1/namespace/{id}/list
                for (oas_path, oas_method), entry in openapi_map.items():
                    if oas_method != req_method:
                        continue
                    oas_parts = oas_path.split("/")
                    req_parts = req_path.split("/")
                    if len(oas_parts) != len(req_parts):
                        continue
                    matched = all(
                        op == rp or (op.startswith("{") and op.endswith("}"))
                        for op, rp in zip(oas_parts, req_parts)
                    )
                    if matched:
                        oas_entry = entry
                        break

            if oas_entry is None:
                gaps.append({
                    "consumer": consumer,
                    "provider": provider,
                    "description": description,
                    "path": req_path,
                    "method": req_method,
                    "status": resp_status,
                    "gap_type": "path_not_in_openapi",
                    "field": req_path,
                    "severity": "WARNING",
                })
                continue

            # Check response field coverage
            status_key = str(resp_status)
            oas_resp = oas_entry.get("responses", {}).get(status_key, {})
            oas_fields: FieldSet = oas_resp.get("fields", set())
            oas_required: FieldSet = oas_resp.get("required", set())

            pact_body_fields = _extract_pact_body_fields(resp_body)
            pact_top_fields = {f[2:] for f in pact_body_fields if f.count(".") == 1 and "[" not in f}

            # Pact has field not in OpenAPI
            for field in pact_top_fields:
                if oas_fields and field not in oas_fields:
                    gaps.append({
                        "consumer": consumer,
                        "provider": provider,
                        "description": description,
                        "path": req_path,
                        "method": req_method,
                        "status": resp_status,
                        "gap_type": "pact_has_unknown_field",
                        "field": field,
                        "severity": "WARNING",
                    })

            # OpenAPI required field missing from pact body
            for field in oas_required:
                if field not in pact_top_fields:
                    gaps.append({
                        "consumer": consumer,
                        "provider": provider,
                        "description": description,
                        "path": req_path,
                        "method": req_method,
                        "status": resp_status,
                        "gap_type": "openapi_required_field_missing_in_pact",
                        "field": field,
                        "severity": "ERROR",
                    })

    return gaps


# ---------------------------------------------------------------------------
# Report generation
# ---------------------------------------------------------------------------

def generate_report(
    pacts: list[Pact],
    gaps: list[dict[str, Any]],
    openapi_path: Path,
) -> str:
    """Generate a Markdown coverage report."""
    lines: list[str] = [
        "# OpenAPI ↔ Pact Field Coverage Report",
        "",
        f"**OpenAPI spec**: `{openapi_path}`",
        f"**Pacts analysed**: {len(pacts)}",
        "",
    ]

    if not pacts:
        lines += [
            "> **No pacts available** — broker unreachable or no pacts published.",
            "> Run consumer tests first and publish pacts to the broker.",
            "",
        ]
        return "\n".join(lines)

    errors = [g for g in gaps if g["severity"] == "ERROR"]
    warnings = [g for g in gaps if g["severity"] == "WARNING"]

    status = "PASS" if not errors else "FAIL"
    lines += [
        f"**Status**: {status}",
        f"**Errors**: {len(errors)}  |  **Warnings**: {len(warnings)}",
        "",
    ]

    if not gaps:
        lines += [
            "No field coverage gaps detected.",
            "",
        ]
        return "\n".join(lines)

    lines += ["## Coverage Gaps", ""]
    lines += [
        "| Severity | Consumer | Path | Method | Status | Gap Type | Field |",
        "|---|---|---|---|---|---|---|",
    ]
    for gap in sorted(gaps, key=lambda g: (g["severity"], g["path"])):
        sev_icon = "🔴" if gap["severity"] == "ERROR" else "🟡"
        lines.append(
            f"| {sev_icon} {gap['severity']} "
            f"| {gap['consumer']} "
            f"| `{gap['path']}` "
            f"| {gap['method']} "
            f"| {gap['status']} "
            f"| {gap['gap_type']} "
            f"| `{gap['field']}` |"
        )

    lines += ["", "## Details", ""]
    for i, gap in enumerate(gaps, 1):
        lines += [
            f"### Gap #{i}: {gap['gap_type']}",
            "",
            f"- **Consumer**: {gap['consumer']}",
            f"- **Provider**: {gap['provider']}",
            f"- **Interaction**: {gap['description']}",
            f"- **Path**: `{gap['method']} {gap['path']}` → HTTP {gap['status']}",
            f"- **Field**: `{gap['field']}`",
            f"- **Severity**: {gap['severity']}",
            "",
        ]

    return "\n".join(lines)


# ---------------------------------------------------------------------------
# CLI entry point
# ---------------------------------------------------------------------------

def main() -> int:
    parser = argparse.ArgumentParser(
        description="Check field coverage between OpenAPI spec and published Pact contracts.",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__,
    )
    parser.add_argument(
        "--broker-url",
        default=os.environ.get("PACT_BROKER_URL", ""),
        help="Pact Broker base URL (default: $PACT_BROKER_URL)",
    )
    parser.add_argument(
        "--openapi-spec",
        default="docs/src/rest.yaml",
        help="Path to OpenAPI spec file (default: docs/src/rest.yaml)",
    )
    parser.add_argument(
        "--output",
        default="-",
        help="Output file path (default: stdout)",
    )
    args = parser.parse_args()

    spec_path = Path(args.openapi_spec)
    if not spec_path.exists():
        print(f"[ERROR] OpenAPI spec not found: {spec_path}", file=sys.stderr)
        return 1

    # Fetch pacts
    pacts: list[Pact] = []
    if args.broker_url:
        pacts = fetch_pacts(args.broker_url)
    else:
        print("[WARN] No --broker-url provided and $PACT_BROKER_URL not set. Skipping broker fetch.", file=sys.stderr)

    # Parse OpenAPI
    openapi_map = parse_openapi(spec_path)

    # Check coverage
    gaps = check_coverage(pacts, openapi_map) if pacts else []

    # Generate report
    report = generate_report(pacts, gaps, spec_path)

    # Write output
    if args.output == "-":
        print(report)
    else:
        Path(args.output).write_text(report, encoding="utf-8")
        print(f"[OK] Report written to {args.output}", file=sys.stderr)

    has_errors = any(g["severity"] == "ERROR" for g in gaps)
    if not pacts:
        return 2
    return 1 if has_errors else 0


if __name__ == "__main__":
    sys.exit(main())
