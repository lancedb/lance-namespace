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
contract_loader.py — load + merge the cts-contracts YAML bundle.

Single entry point ``load_bundle(contracts_dir)``:

    bundle = load_bundle(Path("docs/src/cts-contracts"))

    bundle.version            # int, taken from main.yaml
    bundle.capabilities       # list[CapabilityDecl]
    bundle.capability_ids     # set[str], cached
    bundle.includes           # list[str], in declared order
    bundle.contracts          # list[OperationBlock] in include order

The loader is intentionally side-effect-free (no CLI, no logging side
streams) and returns plain dataclasses so that both the linter
(:mod:`lint_contracts`) and the test generator
(:mod:`gen_contract_tests`) can consume the same shape without copy-paste.

The loader does **not** validate against the JSON schema — that lives in
:mod:`lint_contracts`, which is the single place that imports
``jsonschema``.  Keeping schema validation out of the hot path lets the
generator skip the dependency when it runs in environments where the
contracts have already been linted upstream.
"""

from __future__ import annotations

import re
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any

import yaml

# ---------------------------------------------------------------------------
# dataclasses
# ---------------------------------------------------------------------------


@dataclass(frozen=True)
class CapabilityDecl:
    id: str
    description: str = ""

    @classmethod
    def from_dict(cls, raw: dict[str, Any]) -> "CapabilityDecl":
        return cls(id=raw["id"], description=raw.get("description", "").strip())


@dataclass(frozen=True)
class Fixture:
    kind: str
    id: tuple[str, ...] | None = None
    namespace: tuple[str, ...] | None = None
    name: str | None = None

    @classmethod
    def from_dict(cls, raw: dict[str, Any]) -> "Fixture":
        return cls(
            kind=raw["kind"],
            id=tuple(raw["id"]) if "id" in raw else None,
            namespace=tuple(raw["namespace"]) if "namespace" in raw else None,
            name=raw.get("name"),
        )


@dataclass(frozen=True)
class Given:
    state: str | None
    fixtures: tuple[Fixture, ...]

    @classmethod
    def from_dict(cls, raw: dict[str, Any] | None) -> "Given":
        raw = raw or {}
        fixtures = tuple(Fixture.from_dict(f) for f in raw.get("fixtures", []))
        return cls(state=raw.get("state"), fixtures=fixtures)


@dataclass(frozen=True)
class Expect:
    status: str | int | None = None
    error_code: int | None = None
    error_code_alternatives: tuple[int, ...] = ()

    @classmethod
    def from_dict(cls, raw: dict[str, Any] | None) -> "Expect":
        raw = raw or {}
        return cls(
            status=raw.get("status"),
            error_code=raw.get("error_code"),
            error_code_alternatives=tuple(raw.get("error_code_alternatives", [])),
        )

    def is_ok(self) -> bool:
        if self.error_code is not None:
            return False
        if self.status is None:
            return False
        if self.status == "ok":
            return True
        if isinstance(self.status, int):
            return 200 <= self.status < 300
        return False


@dataclass(frozen=True)
class Step:
    """One step in a multi-step `when:` block.

    For a single-shot `when: { request: {...} }`, the loader synthesises a
    single Step whose `op` is inherited from the operation block and whose
    `expect` is taken from the case-level `then`.
    """

    op: str
    request: dict[str, Any]
    expect: Expect

    @classmethod
    def from_dict(cls, raw: dict[str, Any]) -> "Step":
        return cls(
            op=raw["op"],
            request=dict(raw.get("request", {})),
            expect=Expect.from_dict(raw.get("expect")),
        )


@dataclass(frozen=True)
class ResponseAssertion:
    kind: str
    value: Any = None

    @classmethod
    def from_dict(cls, raw: dict[str, Any]) -> "ResponseAssertion":
        return cls(kind=raw["kind"], value=raw.get("value"))


@dataclass(frozen=True)
class Then:
    status: str | int | None = None
    error_code: int | None = None
    error_code_alternatives: tuple[int, ...] = ()
    response_assertions: tuple[ResponseAssertion, ...] = ()

    @classmethod
    def from_dict(cls, raw: dict[str, Any] | None) -> "Then":
        raw = raw or {}
        return cls(
            status=raw.get("status"),
            error_code=raw.get("error_code"),
            error_code_alternatives=tuple(raw.get("error_code_alternatives", [])),
            response_assertions=tuple(
                ResponseAssertion.from_dict(r)
                for r in raw.get("response_assertions", [])
            ),
        )

    def is_ok(self) -> bool:
        if self.error_code is not None:
            return False
        return self.status == "ok" or (
            isinstance(self.status, int) and 200 <= self.status < 300
        )


@dataclass(frozen=True)
class Case:
    id: str
    operation: str
    """Inherited from the parent operation_block at load time."""
    description: str
    requires_capabilities: tuple[str, ...]
    given: Given
    steps: tuple[Step, ...]
    """Normalised: a single-shot `when` is wrapped into a single Step."""
    then: Then
    skip_reason: str | None
    domain_file: str
    """Filename of the domain file that owns the case (e.g. namespace.yaml).

    Used by the linter when emitting diagnostics.
    """


@dataclass(frozen=True)
class OperationBlock:
    operation: str
    requires_capabilities: tuple[str, ...]
    skip_reason: str | None
    cases: tuple[Case, ...]
    domain_file: str


@dataclass
class Bundle:
    version: int
    capabilities: list[CapabilityDecl] = field(default_factory=list)
    includes: list[str] = field(default_factory=list)
    contracts: list[OperationBlock] = field(default_factory=list)

    @property
    def capability_ids(self) -> set[str]:
        return {c.id for c in self.capabilities}

    def all_cases(self) -> list[Case]:
        out: list[Case] = []
        for op in self.contracts:
            out.extend(op.cases)
        return out


# ---------------------------------------------------------------------------
# loading
# ---------------------------------------------------------------------------


_VAR_RE = re.compile(r"^[A-Z][A-Za-z0-9]+$")


def _read_yaml(path: Path) -> dict[str, Any]:
    if not path.is_file():
        raise FileNotFoundError(f"contracts file not found: {path}")
    with path.open("r", encoding="utf-8") as f:
        data = yaml.safe_load(f)
    if data is None:
        raise ValueError(f"contracts file is empty: {path}")
    if not isinstance(data, dict):
        raise ValueError(
            f"contracts file must be a mapping at the top level: {path}"
        )
    return data


def _normalise_when(
    raw_when: dict[str, Any] | list[dict[str, Any]],
    parent_operation: str,
    case_then: Then,
) -> tuple[Step, ...]:
    """Turn either form of `when:` into a tuple of Steps.

    - ``when: { request: {...} }`` → 1 step using the parent operation, with
      the case-level `then` carried in via the step's ``expect`` so that
      generated tests can apply the same assertion uniformly.
    - ``when: [ {op, request, expect}, … ]`` → as-is.
    """
    if isinstance(raw_when, dict):
        # Single-shot form: synthesise a single step.
        return (
            Step(
                op=parent_operation,
                request=dict(raw_when.get("request", {})),
                expect=Expect(
                    status=case_then.status,
                    error_code=case_then.error_code,
                    error_code_alternatives=case_then.error_code_alternatives,
                ),
            ),
        )
    if isinstance(raw_when, list):
        return tuple(Step.from_dict(s) for s in raw_when)
    raise TypeError(
        f"`when:` must be a mapping or a list, got {type(raw_when).__name__}"
    )


def _load_main(main_path: Path) -> tuple[int, list[CapabilityDecl], list[str]]:
    raw = _read_yaml(main_path)
    if "includes" not in raw:
        raise ValueError(
            f"{main_path} is not a main.yaml — it has no `includes:` key. "
            "Did you accidentally pass a domain file?"
        )
    version = int(raw.get("version", 1))
    caps = [CapabilityDecl.from_dict(c) for c in raw.get("capabilities", [])]
    includes = list(raw.get("includes", []))
    return version, caps, includes


def _load_domain(
    domain_path: Path,
    domain_file: str,
) -> list[OperationBlock]:
    raw = _read_yaml(domain_path)
    if "contracts" not in raw:
        raise ValueError(
            f"{domain_path} is not a domain file — it has no `contracts:` key."
        )

    out: list[OperationBlock] = []
    for op_idx, op_raw in enumerate(raw.get("contracts") or []):
        if "operation" not in op_raw:
            raise ValueError(
                f"{domain_path}: contracts[{op_idx}] is missing `operation:`."
            )
        op_name = op_raw["operation"]
        if not _VAR_RE.match(op_name):
            raise ValueError(
                f"{domain_path}: contracts[{op_idx}].operation = "
                f"{op_name!r} must be PascalCase."
            )
        op_caps = tuple(op_raw.get("requires_capabilities", []))
        op_skip = op_raw.get("skip_reason")

        cases: list[Case] = []
        for case_idx, case_raw in enumerate(op_raw.get("cases") or []):
            cid = case_raw.get("id")
            if not cid:
                raise ValueError(
                    f"{domain_path}: contracts[{op_idx}].cases[{case_idx}]"
                    " is missing `id:`."
                )
            then = Then.from_dict(case_raw.get("then"))
            steps = _normalise_when(
                case_raw.get("when", {"request": {}}),
                parent_operation=op_name,
                case_then=then,
            )
            case_caps = tuple(case_raw.get("requires_capabilities", []))
            # Effective requires_capabilities = parent ∪ case (preserve order,
            # de-dup).
            effective_caps: list[str] = []
            for c in list(op_caps) + list(case_caps):
                if c not in effective_caps:
                    effective_caps.append(c)
            cases.append(
                Case(
                    id=cid,
                    operation=op_name,
                    description=case_raw.get("description", "").strip(),
                    requires_capabilities=tuple(effective_caps),
                    given=Given.from_dict(case_raw.get("given")),
                    steps=steps,
                    then=then,
                    skip_reason=case_raw.get("skip_reason"),
                    domain_file=domain_file,
                )
            )

        out.append(
            OperationBlock(
                operation=op_name,
                requires_capabilities=op_caps,
                skip_reason=op_skip,
                cases=tuple(cases),
                domain_file=domain_file,
            )
        )
    return out


def load_bundle(contracts_dir: Path, *, main_filename: str = "main.yaml") -> Bundle:
    """Load + merge the cts-contracts bundle.

    Args:
        contracts_dir: directory containing main.yaml and the domain files.
        main_filename: override for the entrypoint filename (used by tests).

    Returns:
        A populated :class:`Bundle`.

    Raises:
        FileNotFoundError: if main.yaml or any include is missing.
        ValueError: on malformed content (use :mod:`lint_contracts` for full
            JSON Schema validation; the loader only enforces the strict
            shape it directly relies on).
    """
    contracts_dir = contracts_dir.resolve()
    main_path = contracts_dir / main_filename
    version, caps, includes = _load_main(main_path)

    contracts: list[OperationBlock] = []
    for inc in includes:
        domain_path = contracts_dir / inc
        contracts.extend(_load_domain(domain_path, domain_file=inc))

    return Bundle(
        version=version,
        capabilities=caps,
        includes=includes,
        contracts=contracts,
    )


__all__ = [
    "Bundle",
    "CapabilityDecl",
    "Case",
    "Expect",
    "Fixture",
    "Given",
    "OperationBlock",
    "ResponseAssertion",
    "Step",
    "Then",
    "load_bundle",
]
