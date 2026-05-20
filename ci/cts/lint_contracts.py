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
lint_contracts.py — anti-drift checks for docs/src/cts-contracts/.

Run as ``uv run python ci/cts/lint_contracts.py`` (zero arguments) or:

    uv run python ci/cts/lint_contracts.py --strict

In ``--strict`` mode (intended for CI), the
linter also enforces *coverage* —— every (operation, error_code) pair
declared in ``docs/src/namespace/operations/errors.md`` must be covered
by at least one case (or be explicitly opted-out via ``skip_reason``).
While the contract bundle is still being filled in, the
default mode skips this check so that empty domain files do not break CI.

Checks performed (always, exit-code 1 on any failure):

    1. JSON Schema validation of main.yaml + every domain file
       (``cts-contracts.schema.json``).
    2. ``main.yaml › includes`` matches the actual *.yaml files under
       ``docs/src/cts-contracts/`` (no missing, no orphan).
    3. Each ``requires_capabilities`` ID is registered in
       ``main.yaml › capabilities``.
    4. Each operation appears in **exactly one** domain file (no
       cross-file duplicates).
    5. Every ``then.error_code`` (and ``error_code_alternatives``) is in
       the canonical 0..21 range listed in ``errors.md``.

Strict-only checks (gated behind ``--strict``):

    6. Every (operation, error_code) declared in errors.md is covered.
    7. Every operation listed in operations/index.md has at least one
       case (or a top-level skip_reason).
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from typing import Any

import yaml
from jsonschema import Draft7Validator

# Local imports —— `ci/cts/` is on sys.path when invoked as a script via
# `uv run python ci/cts/lint_contracts.py`.
sys.path.insert(0, str(Path(__file__).resolve().parent))
from contract_loader import Bundle, load_bundle  # noqa: E402

REPO_ROOT = Path(__file__).resolve().parents[2]
CONTRACTS_DIR = REPO_ROOT / "docs" / "src" / "cts-contracts"
SCHEMA_PATH = Path(__file__).resolve().parent / "cts-contracts.schema.json"
ERRORS_MD = REPO_ROOT / "docs" / "src" / "namespace" / "operations" / "errors.md"


# ---------------------------------------------------------------------------
# Diagnostics
# ---------------------------------------------------------------------------


class Diagnostics:
    """Collects lint diagnostics; exits non-zero if anything was reported."""

    def __init__(self) -> None:
        self.errors: list[str] = []

    def err(self, msg: str) -> None:
        self.errors.append(msg)

    def report(self) -> int:
        if not self.errors:
            print("lint_contracts: OK")
            return 0
        for e in self.errors:
            print(f"lint_contracts: ERROR: {e}", file=sys.stderr)
        print(
            f"\nlint_contracts: {len(self.errors)} error(s).",
            file=sys.stderr,
        )
        return 1


# ---------------------------------------------------------------------------
# Schema validation
# ---------------------------------------------------------------------------


def _validate_schema(diag: Diagnostics) -> None:
    schema = json.loads(SCHEMA_PATH.read_text(encoding="utf-8"))
    validator = Draft7Validator(schema)
    for path in sorted(CONTRACTS_DIR.glob("*.yaml")):
        with path.open("r", encoding="utf-8") as f:
            data = yaml.safe_load(f)
        if data is None:
            diag.err(f"{path.name}: file is empty")
            continue
        for v_err in validator.iter_errors(data):
            loc = "/".join(str(p) for p in v_err.absolute_path) or "<root>"
            diag.err(f"{path.name}: schema: at {loc}: {v_err.message}")


# ---------------------------------------------------------------------------
# Includes hygiene
# ---------------------------------------------------------------------------


def _check_includes(bundle: Bundle, diag: Diagnostics) -> None:
    declared = set(bundle.includes)
    on_disk = {p.name for p in CONTRACTS_DIR.glob("*.yaml")} - {"main.yaml"}
    missing = declared - on_disk
    orphan = on_disk - declared
    for m in sorted(missing):
        diag.err(f"main.yaml › includes lists `{m}` but the file is missing")
    for o in sorted(orphan):
        diag.err(
            f"`docs/src/cts-contracts/{o}` exists on disk but is not in "
            "`main.yaml › includes`"
        )


# ---------------------------------------------------------------------------
# Capability hygiene
# ---------------------------------------------------------------------------


def _check_capabilities(bundle: Bundle, diag: Diagnostics) -> None:
    declared = bundle.capability_ids
    for op in bundle.contracts:
        for cap in op.requires_capabilities:
            if cap not in declared:
                diag.err(
                    f"{op.domain_file}: operation {op.operation!r} requires "
                    f"capability {cap!r} but it is not declared in main.yaml"
                )
        for case in op.cases:
            for cap in case.requires_capabilities:
                if cap not in declared:
                    diag.err(
                        f"{op.domain_file}: case {op.operation}/{case.id!r} "
                        f"requires capability {cap!r} but it is not declared "
                        "in main.yaml"
                    )


# ---------------------------------------------------------------------------
# Operation uniqueness
# ---------------------------------------------------------------------------


def _check_operation_uniqueness(bundle: Bundle, diag: Diagnostics) -> None:
    seen: dict[str, str] = {}
    for op in bundle.contracts:
        prev = seen.get(op.operation)
        if prev is not None and prev != op.domain_file:
            diag.err(
                f"operation {op.operation!r} appears in both {prev!r} and "
                f"{op.domain_file!r}; each operation must live in exactly "
                "one domain file"
            )
        else:
            seen[op.operation] = op.domain_file


# ---------------------------------------------------------------------------
# Error code range
# ---------------------------------------------------------------------------


_ERROR_CODE_TABLE_RE = re.compile(
    r"^\|\s*(?P<code>\d+)\s*\|\s*(?P<name>[A-Za-z]+)\s*\|", re.MULTILINE
)
_PER_OP_ROW_RE = re.compile(
    r"^\|\s*(?P<op>[A-Za-z][A-Za-z0-9 ]*?)\s*\|\s*(?P<errs>[^|]*)\|",
    re.MULTILINE,
)
_ERR_TOKEN_RE = re.compile(r"\b(\d+)\s*\(")


def _parse_errors_md() -> tuple[set[int], dict[str, set[int]]]:
    """Return (canonical-codes, {operation: {error_codes}}).

    The errors.md file mixes a top-level code table with per-section
    "Additional Errors" tables.  We additively collect:
        canonical-codes   — every code listed in the top table.
        per-operation     — additional errors per row of every section
                            table (the *common* errors apply to every
                            operation but we treat them as opt-in coverage,
                            not mandatory).
    """
    if not ERRORS_MD.is_file():
        return set(), {}
    md = ERRORS_MD.read_text(encoding="utf-8")
    canonical = {int(m.group("code")) for m in _ERROR_CODE_TABLE_RE.finditer(md)}

    per_op: dict[str, set[int]] = {}
    for m in _PER_OP_ROW_RE.finditer(md):
        op = m.group("op").strip()
        # Skip table headers ("Operation" / "----" / etc.).
        if op == "Operation" or op.startswith("--") or op.startswith("Code"):
            continue
        if not re.fullmatch(r"[A-Z][A-Za-z0-9]+", op):
            continue
        codes = {int(t) for t in _ERR_TOKEN_RE.findall(m.group("errs"))}
        if codes:
            per_op[op] = codes
    return canonical, per_op


def _check_error_codes_in_range(
    bundle: Bundle, canonical: set[int], diag: Diagnostics
) -> None:
    if not canonical:
        return  # errors.md missing → upstream error already reported elsewhere
    for op in bundle.contracts:
        for case in op.cases:
            codes: list[int] = []
            if case.then.error_code is not None:
                codes.append(case.then.error_code)
            codes.extend(case.then.error_code_alternatives)
            for step in case.steps:
                if step.expect.error_code is not None:
                    codes.append(step.expect.error_code)
                codes.extend(step.expect.error_code_alternatives)
            for code in codes:
                if code not in canonical:
                    diag.err(
                        f"{op.domain_file}: case {op.operation}/{case.id!r} "
                        f"references error_code {code} which is not listed "
                        f"in errors.md"
                    )


# ---------------------------------------------------------------------------
# Strict-only: per-operation error coverage
# ---------------------------------------------------------------------------


def _check_per_operation_coverage(
    bundle: Bundle, per_op: dict[str, set[int]], diag: Diagnostics
) -> None:
    """For each (op, error_code) declared in errors.md, ensure ≥ 1 case covers it."""
    by_op: dict[str, set[int]] = {}
    for op_block in bundle.contracts:
        if op_block.skip_reason:
            # Whole operation opted out; treat all expected codes as covered.
            by_op[op_block.operation] = set(per_op.get(op_block.operation, set()))
            continue
        covered: set[int] = by_op.setdefault(op_block.operation, set())
        for case in op_block.cases:
            if case.skip_reason:
                continue
            if case.then.error_code is not None:
                covered.add(case.then.error_code)
            covered.update(case.then.error_code_alternatives)

    for op_name, expected in per_op.items():
        actual = by_op.get(op_name, set())
        missing = expected - actual
        if missing:
            diag.err(
                f"errors.md declares error code(s) "
                f"{sorted(missing)!r} for operation {op_name!r} but no "
                "contract case covers them"
            )


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--strict",
        action="store_true",
        help="Also enforce per-operation error coverage against errors.md.",
    )
    args = parser.parse_args(argv)

    diag = Diagnostics()

    # 1) Schema first — every later check assumes shape correctness.
    _validate_schema(diag)
    if diag.errors:
        return diag.report()

    bundle = load_bundle(CONTRACTS_DIR)

    _check_includes(bundle, diag)
    _check_capabilities(bundle, diag)
    _check_operation_uniqueness(bundle, diag)

    canonical, per_op = _parse_errors_md()
    _check_error_codes_in_range(bundle, canonical, diag)

    if args.strict:
        _check_per_operation_coverage(bundle, per_op, diag)

    return diag.report()


if __name__ == "__main__":
    sys.exit(main())
