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
render.py — Mustache template renderer for the CTS contract-test generator.

Mirrors the openapi-generator template-driven codegen pattern:

  * The Python side (``gen_client_tests.py``) plays the role of
    ``DefaultGenerator`` / ``DefaultCodegen`` — it scans the generated
    clients, builds a ``Map<String, Object>``-shaped *bundle* (a plain Python
    dict), and hands it to the renderer.
  * ``TemplateRenderer`` plays the role of ``MustacheEngineAdapter``: it
    loads ``ci/cts/templates/*.mustache``, registers every
    ``partial_*.mustache`` as a partial, and renders one top-level template
    per output file.

Templates are pure Mustache — no language-specific lambdas, no Jinja2-style
expressions.  All conditional logic must be expressed with the standard
``{{#flag}}…{{/flag}}`` / ``{{^flag}}…{{/flag}}`` constructs and pre-computed
boolean flags inserted into the bundle by Python.

This module is intentionally small and side-effect-free; it does not import
``gen_client_tests`` to keep the dependency direction one-way (generator →
renderer).
"""

from __future__ import annotations

import os
from pathlib import Path
from typing import Any

import chevron

# Templates live alongside this module: ``ci/cts/templates/``.
_TEMPLATES_DIR = Path(__file__).resolve().parent / "templates"


class TemplateRenderer:
    """Thin wrapper around ``chevron.render`` with auto-loaded partials.

    Every file matching ``partial_*.mustache`` in ``templates/`` is exposed
    to top-level templates via ``{{>partial_xxx}}`` (the leading
    ``partial_`` stem is preserved, mirroring openapi-generator's
    ``partial_header.mustache`` convention).
    """

    def __init__(self, tpl_dir: Path = _TEMPLATES_DIR) -> None:
        if not tpl_dir.is_dir():
            raise SystemExit(
                f"ERROR: template directory not found: {tpl_dir}. "
                "Did you delete ci/cts/templates/?"
            )
        self.tpl_dir = tpl_dir
        self._partials: dict[str, str] = self._load_partials()

    def _load_partials(self) -> dict[str, str]:
        """Load every ``partial_*.mustache`` keyed by file stem."""
        return {
            p.stem: p.read_text(encoding="utf-8")
            for p in sorted(self.tpl_dir.glob("partial_*.mustache"))
        }

    def render(self, template_name: str, data: dict[str, Any]) -> str:
        """Render ``<template_name>.mustache`` against ``data``.

        Args:
            template_name: file stem (without ``.mustache``), e.g.
                ``"rust_contract"``.
            data: bundle map; values may be scalars, lists, dicts, or
                strings of pre-rendered code (the latter must be referenced
                with the ``{{{var}}}`` triple-mustache form to bypass HTML
                escaping).

        Returns:
            The rendered template body.  No trailing-newline normalization
            is performed here; callers should rely on the same post-process
            (``content.rstrip("\\n") + "\\n"``) that the generator already
            applies to legacy output.
        """
        tpl_path = self.tpl_dir / f"{template_name}.mustache"
        if not tpl_path.is_file():
            raise SystemExit(
                f"ERROR: template not found: {tpl_path}. "
                f"Available: {sorted(p.name for p in self.tpl_dir.glob('*.mustache'))}"
            )
        template = tpl_path.read_text(encoding="utf-8")
        return chevron.render(
            template=template,
            data=data,
            partials_dict=self._partials,
        )


# ---------------------------------------------------------------------------
# Engine selection
# ---------------------------------------------------------------------------
#
# Mustache is now the sole supported engine.  The legacy ``textwrap.dedent``
# path was removed once every language reached byte-for-byte parity (see
# ``ci/cts/REFACTORING_PROGRESS.md``).  ``--engine`` and the
# ``LANCE_CTS_ENGINE`` env var are kept reachable for forward compatibility,
# but the only accepted value is ``"mustache"``.
#
# Selection order:
#   1. ``--engine=mustache`` CLI flag (parsed by gen_client_tests.py).
#   2. ``LANCE_CTS_ENGINE`` env var (same value).
#   3. Default = ``"mustache"``.
# ---------------------------------------------------------------------------


_DEFAULT_ENGINE = "mustache"
_VALID_ENGINES = frozenset({"mustache"})


def resolve_engine(cli_value: str | None = None) -> str:
    """Pick the active codegen engine for this run.

    ``cli_value`` is the value of the ``--engine`` argparse option (may be
    ``None`` if the flag was not passed).  Falls back to the
    ``LANCE_CTS_ENGINE`` env var, then to ``_DEFAULT_ENGINE``.

    Raises ``SystemExit`` (mapped to exit code 2 by argparse semantics) if
    the resolved value is unknown.
    """
    candidate = cli_value or os.environ.get("LANCE_CTS_ENGINE") or _DEFAULT_ENGINE
    candidate = candidate.lower()
    if candidate not in _VALID_ENGINES:
        raise SystemExit(
            f"ERROR: invalid CTS engine {candidate!r}. "
            f"Choose one of: {sorted(_VALID_ENGINES)}."
        )
    return candidate
