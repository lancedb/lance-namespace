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
gen_contract_tests.py —— behavioural-contract test generator.

Reads ``docs/src/cts-contracts/main.yaml`` (and every ``include``), turns
it into a Mustache-friendly bundle, and writes one Rust file per
operation under
``rust/lance-namespace-cts/tests/contracts/<op_snake>_contract.rs``.

The generator deliberately ships **no language-specific lambdas** in the
bundle: every conditional inside the Mustache template fires off a
pre-computed boolean (e.g. ``has_required_capabilities``) we set here.
This keeps the templates trivially auditable and matches the openapi-
generator pattern that ``render.py`` was designed for.

Run via:

    uv run python ci/cts/gen_contract_tests.py
    uv run python ci/cts/gen_contract_tests.py --check   # CI: assert tree is up-to-date

Currently the generator emits Rust in-process tests for the operations
listed in ``_SUPPORTED_OPS`` below.  Operations that don't yet have a
method on ``ContractCaller`` are *silently dropped*
so the generator stays robust as new families are filled
in.  The lint pass at ``ci/cts/lint_contracts.py --strict`` is the
authority on coverage; the generator is just an emitter.
"""

from __future__ import annotations

import argparse
import re
import shutil
import subprocess
import sys
from pathlib import Path

# ``ci/cts/`` on sys.path → ``contract_loader``, ``render`` import as
# top-level modules.
sys.path.insert(0, str(Path(__file__).resolve().parent))
from contract_loader import (  # noqa: E402
    Bundle,
    Case,
    Expect,
    OperationBlock,
    Step,
    load_bundle,
)
from render import TemplateRenderer  # noqa: E402

# Operations that currently have a method on the Rust ``ContractCaller``
# trait.  This set will grow as new families land; keep the list
# explicit so a missing method is a hard, obvious lint error rather
# than a silent compilation failure.
_SUPPORTED_OPS: dict[str, dict[str, str]] = {
    # ── namespace family ──────────────────────────────────────────
    "CreateNamespace": {
        "method": "create_namespace",
        "request_struct": "CreateNamespaceRequest",
    },
    "DescribeNamespace": {
        "method": "describe_namespace",
        "request_struct": "DescribeNamespaceRequest",
    },
    "DropNamespace": {
        "method": "drop_namespace",
        "request_struct": "DropNamespaceRequest",
    },
    "NamespaceExists": {
        "method": "namespace_exists",
        "request_struct": "NamespaceExistsRequest",
    },
    "ListNamespaces": {
        "method": "list_namespaces",
        "request_struct": "ListNamespacesRequest",
    },
    # ── table metadata family ──────────────────────────────────────────
    "ListTables": {
        "method": "list_tables",
        "request_struct": "ListTablesRequest",
    },
    "DescribeTable": {
        "method": "describe_table",
        "request_struct": "DescribeTableRequest",
    },
    "TableExists": {
        "method": "table_exists",
        "request_struct": "TableExistsRequest",
    },
    "DropTable": {
        "method": "drop_table",
        "request_struct": "DropTableRequest",
    },
    "RegisterTable": {
        "method": "register_table",
        "request_struct": "RegisterTableRequest",
    },
    "DeregisterTable": {
        "method": "deregister_table",
        "request_struct": "DeregisterTableRequest",
    },
    "RenameTable": {
        "method": "rename_table",
        "request_struct": "RenameTableRequest",
    },
    # ── table write-path family ────────────────────────────────────
    # `body_kind = "bytes"` operations consume an Arrow IPC stream as a
    # second positional argument; the value comes from a YAML
    # `request_data:` key (currently only `"empty"` is supported).
    "CreateTable": {
        "method": "create_table",
        "request_struct": "CreateTableRequest",
        "body_kind": "bytes",
    },
    "InsertIntoTable": {
        "method": "insert_into_table",
        "request_struct": "InsertIntoTableRequest",
        "body_kind": "bytes",
    },
    "CountTableRows": {
        "method": "count_table_rows",
        "request_struct": "CountTableRowsRequest",
    },
    "RestoreTable": {
        "method": "restore_table",
        "request_struct": "RestoreTableRequest",
    },
    "UpdateTableSchemaMetadata": {
        "method": "update_table_schema_metadata",
        "request_struct": "UpdateTableSchemaMetadataRequest",
    },
    "GetTableStats": {
        "method": "get_table_stats",
        "request_struct": "GetTableStatsRequest",
    },
    "AlterTableAddColumns": {
        "method": "alter_table_add_columns",
        "request_struct": "AlterTableAddColumnsRequest",
    },
    "AlterTableAlterColumns": {
        "method": "alter_table_alter_columns",
        "request_struct": "AlterTableAlterColumnsRequest",
    },
    "AlterTableDropColumns": {
        "method": "alter_table_drop_columns",
        "request_struct": "AlterTableDropColumnsRequest",
    },
    # ── index family ───────────────────────────────────────────────
    "CreateTableIndex": {
        "method": "create_table_index",
        "request_struct": "CreateTableIndexRequest",
    },
    "CreateTableScalarIndex": {
        "method": "create_table_scalar_index",
        # `create_table_scalar_index` reuses `CreateTableIndexRequest`
        # in v6.0.0 of the trait — no separate request struct exists.
        "request_struct": "CreateTableIndexRequest",
    },
    "ListTableIndices": {
        "method": "list_table_indices",
        "request_struct": "ListTableIndicesRequest",
    },
    "DescribeTableIndexStats": {
        "method": "describe_table_index_stats",
        "request_struct": "DescribeTableIndexStatsRequest",
    },
    "DropTableIndex": {
        "method": "drop_table_index",
        "request_struct": "DropTableIndexRequest",
    },
    # ── tag family — DirectoryNamespace skips at runtime
    # via `supports_table_tags`; we still emit so other implementations
    # can run them and lint coverage is satisfied.
    "ListTableTags": {
        "method": "list_table_tags",
        "request_struct": "ListTableTagsRequest",
    },
    "GetTableTagVersion": {
        "method": "get_table_tag_version",
        "request_struct": "GetTableTagVersionRequest",
    },
    "CreateTableTag": {
        "method": "create_table_tag",
        "request_struct": "CreateTableTagRequest",
    },
    "DeleteTableTag": {
        "method": "delete_table_tag",
        "request_struct": "DeleteTableTagRequest",
    },
    "UpdateTableTag": {
        "method": "update_table_tag",
        "request_struct": "UpdateTableTagRequest",
    },
    # ── version family ─────────────────────────────────────────────
    "ListTableVersions": {
        "method": "list_table_versions",
        "request_struct": "ListTableVersionsRequest",
    },
    "DescribeTableVersion": {
        "method": "describe_table_version",
        "request_struct": "DescribeTableVersionRequest",
    },
    "CreateTableVersion": {
        "method": "create_table_version",
        "request_struct": "CreateTableVersionRequest",
    },
    "BatchDeleteTableVersions": {
        "method": "batch_delete_table_versions",
        "request_struct": "BatchDeleteTableVersionsRequest",
    },
    # ── transaction family ─────────────────────────────────────────
    "DescribeTransaction": {
        "method": "describe_transaction",
        "request_struct": "DescribeTransactionRequest",
    },
    "AlterTransaction": {
        "method": "alter_transaction",
        "request_struct": "AlterTransactionRequest",
    },
    # ── data family ────────────────────────────────────────────────
    "MergeInsertIntoTable": {
        "method": "merge_insert_into_table",
        "request_struct": "MergeInsertIntoTableRequest",
        "body_kind": "bytes",
    },
    "UpdateTable": {
        "method": "update_table",
        "request_struct": "UpdateTableRequest",
    },
    "DeleteFromTable": {
        "method": "delete_from_table",
        "request_struct": "DeleteFromTableRequest",
    },
    "QueryTable": {
        "method": "query_table",
        "request_struct": "QueryTableRequest",
    },
    "ExplainTableQueryPlan": {
        "method": "explain_table_query_plan",
        "request_struct": "ExplainTableQueryPlanRequest",
    },
    "AnalyzeTableQueryPlan": {
        "method": "analyze_table_query_plan",
        "request_struct": "AnalyzeTableQueryPlanRequest",
    },
}

REPO_ROOT = Path(__file__).resolve().parents[2]
CONTRACTS_DIR = REPO_ROOT / "docs" / "src" / "cts-contracts"
OUT_DIR = REPO_ROOT / "rust" / "lance-namespace-cts" / "tests" / "contracts"


# ---------------------------------------------------------------------------
# Bundle → Mustache view-model
# ---------------------------------------------------------------------------


def _camel_to_snake(name: str) -> str:
    """`CreateNamespace` → `create_namespace`."""
    return re.sub(r"(?<!^)(?=[A-Z])", "_", name).lower()


def _quoted(s: str) -> str:
    """Render a Python string as a Rust string literal."""
    escaped = s.replace("\\", "\\\\").replace('"', '\\"')
    return f'"{escaped}"'


def _render_namespace_id(value: object) -> str:
    """Render a YAML `id` field as a Rust ``Some(vec![...])`` literal.

    ``value`` is either:
      * ``[]`` → ``Some(vec![])`` (root)
      * ``["alpha"]`` / ``["{{ns_a}}", "{{ns_b}}"]`` → varargs
      * a list whose entries may be raw strings *or* ``{{var}}``
        placeholders.  Placeholders are rewritten to bind to a
        ``fixtures.unique_name()`` value created at the top of the test
        body — but the generator currently has no use for late-bound
        placeholders because each fixture YAML already pins the path
        explicitly, so we treat every component as a literal string.
        When late-bound names are needed they will be threaded through
        a small "let bindings" pre-amble on the Mustache view-model.
    """
    if not isinstance(value, list):
        raise TypeError(
            f"`id` must be a list, got {type(value).__name__}: {value!r}"
        )
    if not value:
        return "Some(vec![])"
    parts = ", ".join(_quoted(_strip_placeholder(v)) + ".to_string()" for v in value)
    return f"Some(vec![{parts}])"


_PLACEHOLDER_RE = re.compile(r"^\{\{\s*(?P<name>[a-zA-Z_][a-zA-Z0-9_]*)\s*\}\}$")


def _strip_placeholder(raw: str) -> str:
    """Substitute ``{{ns_a}}`` placeholders with a stable,
    test-unique literal.  We rely on each test running in its own fresh
    `TempDir`-rooted SUT (set by `InProcessDirectoryCaller::fresh()`),
    so collisions across tests cannot happen.

    The literal we emit is just the placeholder name itself (e.g.
    ``ns_a`` → ``"ns_a"``).  This makes generated code human-readable
    and gives every ``{{ns_a}}`` in a single test the same value.
    """
    m = _PLACEHOLDER_RE.match(raw)
    return m.group("name") if m else raw


def _render_request_literal(op_name: str, request: dict) -> str:
    """Render a YAML `request` mapping as a Rust struct literal.

    Only the handful of keys we actually use across all 6 domain files
    is supported; unknown keys cause a hard error so a typo can never
    make it into generated code silently.
    """
    parts: list[str] = []
    for key, val in request.items():
        if key == "id":
            parts.append(f"id: {_render_namespace_id(val)}")
        elif key == "mode":
            parts.append(
                f"mode: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "behavior":
            parts.append(
                f"behavior: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "properties":
            # Encoded as a literal `Some(HashMap::from([("k","v"), ...]))`.
            entries = ", ".join(
                f"({_quoted(k)}.to_string(), {_quoted(str(v))}.to_string())"
                for k, v in (val or {}).items()
            )
            parts.append(
                "properties: Some(std::collections::HashMap::from(["
                f"{entries}]))"
            )
        elif key == "new_table_name":
            # Used by RenameTable. Required (non-Option) String field.
            parts.append(
                f"new_table_name: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "new_namespace_id":
            # Used by RenameTable. Same shape as `id` (Option<Vec<String>>).
            parts.append(f"new_namespace_id: {_render_namespace_id(val)}")
        elif key == "location":
            # RegisterTable's `location` is a required (non-Option) String.
            parts.append(
                f"location: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "version":
            # `version` is required (non-Option) `i64` on a handful of
            # request structs in v6.0.0 of the OpenAPI client and
            # `Option<i64>` on the rest.  Resolve which based on op-name
            # (single source of truth — anything else risks silent
            # drift if the client crate flips a field's optionality).
            if op_name in {
                "RestoreTable",
                "CreateTableTag",
                "UpdateTableTag",
                "CreateTableVersion",
            }:
                parts.append(f"version: {int(val)}i64")
            else:
                parts.append(f"version: Some({int(val)}i64)")
        elif key == "predicate":
            # `DeleteFromTableRequest.predicate` is required `String`.
            # Every other op surfaces `predicate` as `Option<String>`.
            if op_name == "DeleteFromTable":
                parts.append(
                    f"predicate: {_quoted(_strip_placeholder(str(val)))}.to_string()"
                )
            else:
                parts.append(
                    f"predicate: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
                )
        elif key == "on":
            # `MergeInsertIntoTableRequest.on: Option<String>`.
            parts.append(
                f"on: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "when_matched_update_all_filt":
            parts.append(
                f"when_matched_update_all_filt: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "column":
            # CreateTableIndex / CreateTableScalarIndex: required String.
            parts.append(
                f"column: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "index_type":
            parts.append(
                f"index_type: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "name":
            # `name` on CreateTableIndexRequest is `Option<String>`.
            parts.append(
                f"name: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "index_name":
            # `Option<String>` on Describe / Drop index-stats requests.
            parts.append(
                f"index_name: Some({_quoted(_strip_placeholder(str(val)))}.to_string())"
            )
        elif key == "tag":
            # `tag: String` (required) on every tag op that carries it.
            parts.append(
                f"tag: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "manifest_path":
            # CreateTableVersionRequest: required String.
            parts.append(
                f"manifest_path: {_quoted(_strip_placeholder(str(val)))}.to_string()"
            )
        elif key == "actions":
            # AlterTransactionRequest: required `Vec<AlterTransactionAction>`.
            # Empty list is the only shape the contracts currently use
            # (every alter case asserts an error before the actions are
            # interpreted), so we hard-code that path.
            if val:
                raise ValueError(
                    f"{op_name}: non-empty `actions` is not yet supported "
                    "in _render_request_literal"
                )
            parts.append("actions: vec![]")
        elif key == "ranges":
            # BatchDeleteTableVersionsRequest: required `Vec<VersionRange>`.
            # Same story: empty list is the only contract shape today.
            if val:
                raise ValueError(
                    f"{op_name}: non-empty `ranges` is not yet supported "
                    "in _render_request_literal"
                )
            parts.append("ranges: vec![]")
        elif key == "metadata":
            entries = ", ".join(
                f"({_quoted(k)}.to_string(), {_quoted(str(v))}.to_string())"
                for k, v in (val or {}).items()
            )
            parts.append(
                "metadata: Some(std::collections::HashMap::from(["
                f"{entries}]))"
            )
        elif key == "new_columns":
            # AlterTableAddColumns. Each entry is `{name, expression}`.
            entries = ", ".join(
                "lance_namespace_cts::models::AddColumnsEntry { "
                f"name: {_quoted(c['name'])}.to_string(), "
                f"expression: Some(Some({_quoted(c['expression'])}.to_string())), "
                "..Default::default() }"
                for c in (val or [])
            )
            parts.append(f"new_columns: vec![{entries}]")
        elif key == "alterations":
            # AlterTableAlterColumns. Each entry is `{path, ..}`.
            entries = ", ".join(
                "lance_namespace_cts::models::AlterColumnsEntry { "
                f"path: {_quoted(c['path'])}.to_string(), "
                "..Default::default() }"
                for c in (val or [])
            )
            parts.append(f"alterations: vec![{entries}]")
        elif key == "columns":
            # AlterTableDropColumns: `Vec<String>`.
            entries = ", ".join(
                f"{_quoted(_strip_placeholder(str(c)))}.to_string()"
                for c in (val or [])
            )
            parts.append(f"columns: vec![{entries}]")
        elif key == "request_data":
            # Special marker — does NOT correspond to a field on the
            # request struct. Consumed separately to populate the second
            # positional arg of body-bearing trait methods.
            continue
        else:
            raise ValueError(
                f"{op_name}: unsupported request field {key!r} — extend "
                "_render_request_literal in gen_contract_tests.py"
            )
    body = ", ".join(parts) + ("," if parts else "")
    return (
        f"{_SUPPORTED_OPS[op_name]['request_struct']} "
        f"{{ {body} ..Default::default() }}"
    )


def _render_body_expr(op_name: str, request: dict) -> str | None:
    """Return the Rust expression to use as the second positional arg
    for a body-bearing operation (e.g. ``CreateTable``), or ``None`` if
    the op is JSON-only."""
    body_kind = _SUPPORTED_OPS[op_name].get("body_kind")
    if body_kind is None:
        if "request_data" in request:
            raise ValueError(
                f"{op_name}: operation has no body, but `request_data` is set"
            )
        return None
    if body_kind != "bytes":
        raise ValueError(
            f"{op_name}: unknown body_kind {body_kind!r}"
        )
    marker = request.get("request_data", "empty")
    if marker == "empty":
        return "Fixtures::arrow_ipc_empty()"
    raise ValueError(
        f"{op_name}: unsupported request_data marker {marker!r} "
        "(extend _render_body_expr in gen_contract_tests.py)"
    )


def _expected_codes(expect: Expect, fallback: bool) -> tuple[bool, bool, str]:
    """Return ``(expects_ok, expects_error, csv_codes)`` for a step.

    ``fallback`` is true when this step inherits its assertion from the
    case-level ``then`` — used to decide whether intermediate steps
    that lack ``expect`` should silently default to ``Ok`` (we don't —
    a missing expect is a generator bug worth surfacing).
    """
    if expect.error_code is not None:
        codes = [expect.error_code, *expect.error_code_alternatives]
        return False, True, ", ".join(str(c) for c in codes)
    if expect.is_ok():
        return True, False, ""
    if fallback:
        # Single-shot `when` with no `then` block at all — assume Ok.
        return True, False, ""
    raise ValueError(
        "step must declare either `expect.status` or `expect.error_code`"
    )


def _build_step_view(case: Case, step: Step, idx: int, total: int) -> dict:
    op = step.op
    if op not in _SUPPORTED_OPS:
        raise ValueError(
            f"case {case.operation}/{case.id!r}: step {idx}/{total} uses "
            f"operation {op!r} which has no ContractCaller method yet "
            "(supported: " + ", ".join(sorted(_SUPPORTED_OPS)) + ")"
        )
    expects_ok, expects_error, codes = _expected_codes(
        step.expect, fallback=(total == 1)
    )
    response_assertions = []
    if total == 1:
        # The single step inherits the case-level then.response_assertions.
        for ra in case.then.response_assertions:
            response_assertions.append(_render_response_assertion(ra))
    body_expr = _render_body_expr(op, step.request)
    return {
        "op": op,
        "op_method": _SUPPORTED_OPS[op]["method"],
        "request_struct": _SUPPORTED_OPS[op]["request_struct"],
        "request_lit": _render_request_literal(op, step.request),
        "takes_body": body_expr is not None,
        "body_expr": body_expr or "",
        "expects_ok": expects_ok,
        "expects_error": expects_error,
        "expected_codes": codes,
        "has_response_assertions": bool(response_assertions),
        "response_assertions": response_assertions,
    }


def _render_response_assertion(ra) -> dict:
    flags = {
        "namespaces_count": False,
        "namespaces_contains": False,
        "tables_count": False,
        "tables_contains": False,
        "count_eq": False,
    }
    flags[ra.kind] = True
    return {
        "kind": ra.kind,
        "value": ra.value,
        "value_lit": _quoted(_strip_placeholder(str(ra.value))),
        f"response_assertion_{ra.kind}": True,
    }


def _build_case_view(case: Case) -> dict:
    """One element of the Mustache `cases` array."""
    rust_fn = _camel_to_snake(case.id) if case.id[0].isupper() else case.id
    # Strip any character cargo would refuse in a fn name (very unlikely
    # given the schema regex, but cheap insurance).
    rust_fn = re.sub(r"[^a-zA-Z0-9_]", "_", rust_fn)
    description = case.description or case.id
    # Single-line description avoids breaking the doc comment block.
    description = " ".join(description.split())
    given_fixtures = []
    given_table_fixtures = []
    for fx in case.given.fixtures:
        if fx.id is None:
            continue
        if fx.kind == "namespace":
            given_fixtures.append(
                {"parts_quoted": [_quoted(_strip_placeholder(p)) + ".to_string()" for p in fx.id]}
            )
        elif fx.kind == "table":
            given_table_fixtures.append(
                {"parts_quoted": [_quoted(_strip_placeholder(p)) + ".to_string()" for p in fx.id]}
            )
    steps = []
    total = len(case.steps)
    for idx, step in enumerate(case.steps):
        steps.append(_build_step_view(case, step, idx, total))
    return {
        "rust_fn_name": rust_fn,
        "description_or_id": description,
        "has_required_capabilities": bool(case.requires_capabilities),
        "required_capabilities": list(case.requires_capabilities),
        "given_namespace_fixtures": given_fixtures,
        "given_table_fixtures": given_table_fixtures,
        "steps": steps,
    }


def _build_op_view(op: OperationBlock) -> dict:
    return {
        "operation": op.operation,
        "domain_file": op.domain_file,
        "cases": [_build_case_view(c) for c in op.cases if not c.skip_reason],
    }


# ---------------------------------------------------------------------------
# I/O
# ---------------------------------------------------------------------------


_AUTO_HEADER_RE = re.compile(r"^// AUTO-(GENERATED|MAINTAINED) by ", re.MULTILINE)


def _rustfmt_files(paths: list[Path]) -> None:
    """Run rustfmt in-place over a batch of generated Rust files.

    We post-process every emitted file so the long single-line struct
    literals our Mustache templates produce get wrapped to satisfy
    ``cargo fmt --check``.  ``rustfmt`` is invoked with the file paths
    directly (not via stdin) because that is the only mode in which the
    use-reorder pass produces the same fixpoint as ``cargo fmt`` on the
    full crate.  If rustfmt is not on PATH (e.g. minimal CI image
    without a Rust toolchain) we silently skip — the file is still
    valid Rust, only style differs.
    """
    if not paths or not shutil.which("rustfmt"):
        return
    try:
        subprocess.run(
            ["rustfmt", "--edition", "2024", *[str(p) for p in paths]],
            check=True,
            capture_output=True,
        )
    except subprocess.CalledProcessError as exc:
        print(
            f"WARN: rustfmt failed: {exc.stderr.decode(errors='replace').strip()}",
            file=sys.stderr,
        )


def _purge_contracts_dir(out_dir: Path) -> None:
    """Wipe the previous generation, but keep the hand-written `mod.rs`
    placeholder alone.  Anything else with the AUTO-GENERATED header is
    fair game; anything *without* it is a sign of human-authored content
    that the generator must not silently destroy."""
    if not out_dir.is_dir():
        return
    for path in out_dir.iterdir():
        if path.name == "mod.rs":
            continue
        if not path.is_file() or path.suffix != ".rs":
            continue
        head = path.read_text(encoding="utf-8")[:200]
        if not _AUTO_HEADER_RE.search(head):
            raise SystemExit(
                f"refusing to overwrite {path}: missing AUTO-GENERATED header"
            )
        path.unlink()


def _emit(bundle: Bundle, out_dir: Path) -> list[Path]:
    renderer = TemplateRenderer()
    out_dir.mkdir(parents=True, exist_ok=True)
    written: list[Path] = []
    for op in bundle.contracts:
        if op.skip_reason:
            continue
        if op.operation not in _SUPPORTED_OPS:
            # Silently skip until the matching ContractCaller method
            # lands.  When every supported operation has a method this
            # will become zero-skip in practice.
            continue
        view = _build_op_view(op)
        if not view["cases"]:
            continue
        body = renderer.render("rust_inproc_contract", view)
        body = body.rstrip("\n") + "\n"
        target = out_dir / f"{_camel_to_snake(op.operation)}_contract.rs"
        target.write_text(body, encoding="utf-8")
        written.append(target)
    _rustfmt_files(written)
    return written


def _emit_mod_rs(out_dir: Path, files: list[Path]) -> Path:
    body = [
        "// SPDX-License-Identifier: Apache-2.0",
        "// SPDX-FileCopyrightText: Copyright The Lance Authors",
        "//",
        "// AUTO-GENERATED by ci/cts/gen_contract_tests.py.",
        "// Lists every per-operation contract test module emitted in the same",
        "// generator run.  Do not edit by hand — `make gen-cts-behavior`",
        "// rewrites this file.",
        "",
        "#![allow(clippy::needless_pass_by_value)]",
        "#![allow(unused_imports)]",
        "",
    ]
    for f in sorted(files):
        body.append(f"mod {f.stem};")
    body.append("")
    target = out_dir / "mod.rs"
    target.write_text("\n".join(body), encoding="utf-8")
    return target


# ---------------------------------------------------------------------------
# CLI
# ---------------------------------------------------------------------------


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--check",
        action="store_true",
        help="Don't write — fail if the on-disk generated tree differs.",
    )
    parser.add_argument(
        "--out-dir",
        type=Path,
        default=OUT_DIR,
        help="Override output directory (defaults to lance-namespace-cts/tests/contracts).",
    )
    args = parser.parse_args(argv)

    bundle = load_bundle(CONTRACTS_DIR)

    if args.check:
        # Render to a temp dir, run rustfmt over the whole batch (so the
        # use-reorder fixed point matches `cargo fmt`), then compare
        # against the checked-in tree byte-for-byte.
        import tempfile

        renderer = TemplateRenderer()
        seen: set[str] = {"mod.rs"}
        with tempfile.TemporaryDirectory() as tmp:
            tmp_dir = Path(tmp)
            tmp_files: list[Path] = []
            expected: dict[str, Path] = {}
            for op in bundle.contracts:
                if op.skip_reason or op.operation not in _SUPPORTED_OPS:
                    continue
                view = _build_op_view(op)
                if not view["cases"]:
                    continue
                body = renderer.render("rust_inproc_contract", view).rstrip("\n") + "\n"
                fname = f"{_camel_to_snake(op.operation)}_contract.rs"
                tmp_path = tmp_dir / fname
                tmp_path.write_text(body, encoding="utf-8")
                tmp_files.append(tmp_path)
                expected[fname] = tmp_path
                seen.add(fname)
            _rustfmt_files(tmp_files)

            diff: list[str] = []
            for fname, tmp_path in expected.items():
                target = args.out_dir / fname
                want = tmp_path.read_text(encoding="utf-8")
                have = target.read_text(encoding="utf-8") if target.exists() else ""
                if want != have:
                    diff.append(fname)
        if diff:
            print(
                "gen_contract_tests --check: out-of-date file(s):",
                file=sys.stderr,
            )
            for d in diff:
                print(f"  {d}", file=sys.stderr)
            print(
                "Run `make gen-cts-behavior` to regenerate.", file=sys.stderr
            )
            return 1
        # Also flag any unexpected leftover files.
        leftover = sorted(
            p.name for p in args.out_dir.glob("*.rs") if p.name not in seen
        )
        if leftover:
            print("gen_contract_tests --check: orphan file(s):", file=sys.stderr)
            for d in leftover:
                print(f"  {d}", file=sys.stderr)
            return 1
        print("gen_contract_tests --check: OK")
        return 0

    _purge_contracts_dir(args.out_dir)
    files = _emit(bundle, args.out_dir)
    _emit_mod_rs(args.out_dir, files)
    print(f"gen_contract_tests: wrote {len(files)} file(s) under {args.out_dir}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
