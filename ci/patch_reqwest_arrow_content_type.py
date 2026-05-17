#!/usr/bin/env python3
"""Inject ``Content-Type: application/vnd.apache.arrow.stream`` headers into the
generated Rust reqwest client.

The Rust reqwest template shipped with openapi-generator emits

    req_builder = req_builder.body(p_body);

for any operation whose ``requestBody`` is *not* JSON / form / multipart, but
**without** setting the ``Content-Type`` header.  Lance's Arrow IPC operations
(``CreateTable`` / ``InsertIntoTable`` / ``MergeInsertIntoTable``) require the
content type ``application/vnd.apache.arrow.stream`` per the spec.  Servers
that match on this header (including our WireMock contract-test stubs) reject
the request with HTTP 4xx and the call surfaces as a deserialization error.

This script reads the OpenAPI spec, derives the set of operations whose
request body uses ``application/vnd.apache.arrow.stream``, maps them to the
generator's ``snake_case`` Rust function names, and then rewrites each matching
``req_builder = req_builder.body(p_body);`` line to:

    req_builder = req_builder
        .header(
            reqwest::header::CONTENT_TYPE,
            "application/vnd.apache.arrow.stream",
        )
        .body(p_body);

Idempotent: if the header insertion is already present inside a target
function, the function is left untouched.

Why a post-processor instead of a custom mustache template?  The repo
otherwise relies on the stock generator (see ``rust/Makefile``); maintaining a
fork of the entire ``reqwest.mustache`` chain just for one header is
disproportionate, and a tightly scoped patch is easier to audit and to drop
once the upstream template ships the fix.
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path
from typing import Any

import yaml

ARROW_MIME = "application/vnd.apache.arrow.stream"
# Multi-line form so the output already satisfies ``cargo fmt --check`` without
# needing to spawn rustfmt as a post-step.  The single-line form
#   .header(reqwest::header::CONTENT_TYPE, "application/vnd.apache.arrow.stream")
# exceeds rustfmt's default 100-column width once nested under the function's
# 8-space indentation, so rustfmt would reflow it.
HEADER_LINE = (
    '    req_builder = req_builder\n'
    '        .header(\n'
    '            reqwest::header::CONTENT_TYPE,\n'
    '            "' + ARROW_MIME + '",\n'
    '        )\n'
    '        .body(p_body);\n'
)
LEGACY_LINE_RE = re.compile(
    r"^[ \t]*req_builder = req_builder\.body\(p_body\);[ \t]*\n",
    re.MULTILINE,
)


def _pascal_to_snake(name: str) -> str:
    """Replicate openapi-generator's PascalCase → snake_case mapping for fn names."""
    s1 = re.sub(r"(.)([A-Z][a-z]+)", r"\1_\2", name)
    return re.sub(r"([a-z0-9])([A-Z])", r"\1_\2", s1).lower()


def _arrow_operation_ids(spec: dict[str, Any]) -> set[str]:
    """Return operationIds whose request body is Arrow IPC."""
    ops: set[str] = set()
    for path_item in (spec.get("paths") or {}).values():
        if not isinstance(path_item, dict):
            continue
        for method, op in path_item.items():
            if method.lower() not in {
                "get",
                "post",
                "put",
                "patch",
                "delete",
                "options",
                "head",
                "trace",
            }:
                continue
            if not isinstance(op, dict):
                continue
            request_body = op.get("requestBody") or {}
            if not isinstance(request_body, dict):
                continue
            content = request_body.get("content") or {}
            if not isinstance(content, dict):
                continue
            if any(ARROW_MIME in mime for mime in content.keys()):
                op_id = op.get("operationId")
                if op_id:
                    ops.add(op_id)
    return ops


def _arrow_fn_names(spec: dict[str, Any]) -> set[str]:
    return {_pascal_to_snake(op_id) for op_id in _arrow_operation_ids(spec)}


def _patch_function_body(
    source: str,
    fn_name: str,
) -> tuple[str, bool]:
    """Insert the Arrow Content-Type header before the first ``body(p_body)`` call
    inside ``pub async fn <fn_name>(...)``.

    Returns ``(new_source, changed)``.  Idempotent: if the header is already
    present inside the function body, returns the source unchanged.
    """
    # Locate the function header.  The signature spans one line in the
    # generator's output (``pub async fn foo(... ) -> ... {``).
    fn_re = re.compile(
        rf"^pub async fn {re.escape(fn_name)}\b[^{{]*\{{",
        re.MULTILINE,
    )
    match = fn_re.search(source)
    if not match:
        return source, False

    # Walk braces to find the matching closing ``}`` for the function body.
    body_start = match.end() - 1  # position of the opening ``{``
    depth = 0
    end = -1
    for idx in range(body_start, len(source)):
        ch = source[idx]
        if ch == "{":
            depth += 1
        elif ch == "}":
            depth -= 1
            if depth == 0:
                end = idx + 1
                break
    if end < 0:
        return source, False

    body = source[body_start:end]
    if "application/vnd.apache.arrow.stream" in body:
        return source, False  # already patched

    new_body, n = LEGACY_LINE_RE.subn(HEADER_LINE, body, count=1)
    if n != 1:
        # Function body doesn't carry a ``body(p_body)`` line — nothing to do.
        return source, False

    return source[:body_start] + new_body + source[end:], True


def patch_file(path: Path, fn_names: set[str]) -> int:
    """Patch every Arrow function definition found in *path*.

    Returns the number of functions patched.
    """
    source = path.read_text(encoding="utf-8")
    changed_count = 0
    for fn in sorted(fn_names):
        source, changed = _patch_function_body(source, fn)
        if changed:
            changed_count += 1
    if changed_count:
        path.write_text(source, encoding="utf-8")
    return changed_count


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--spec",
        type=Path,
        required=True,
        help="Path to the OpenAPI spec (e.g. docs/src/spec.yaml)",
    )
    parser.add_argument(
        "--client-dir",
        type=Path,
        required=True,
        help="Path to the generated reqwest client crate root",
    )
    args = parser.parse_args()

    if not args.spec.is_file():
        print(f"ERROR: spec not found: {args.spec}", file=sys.stderr)
        return 1
    if not args.client_dir.is_dir():
        print(f"ERROR: client dir not found: {args.client_dir}", file=sys.stderr)
        return 1

    with args.spec.open("r", encoding="utf-8") as f:
        spec = yaml.safe_load(f)

    fn_names = _arrow_fn_names(spec)
    if not fn_names:
        print("patch_reqwest_arrow_content_type: no Arrow operations in spec")
        return 0

    apis_dir = args.client_dir / "src" / "apis"
    if not apis_dir.is_dir():
        print(f"ERROR: apis dir not found: {apis_dir}", file=sys.stderr)
        return 1

    total = 0
    for rs_file in sorted(apis_dir.glob("*.rs")):
        patched = patch_file(rs_file, fn_names)
        if patched:
            print(f"patched {patched} fn(s) in {rs_file}")
            total += patched

    if total == 0:
        print("patch_reqwest_arrow_content_type: nothing to do (already patched?)")
    else:
        print(f"patch_reqwest_arrow_content_type: patched {total} function(s) total")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
