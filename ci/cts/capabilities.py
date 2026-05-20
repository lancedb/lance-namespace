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
capabilities.py — capability-flag plumbing shared by linter and generator.

This module is *language-agnostic*: it knows the registered flags from
``main.yaml`` and how to read the fallback flag set declared for the
in-tree ``DirectoryNamespace`` (see ``ci/cts/capabilities.directory.txt``).

The Rust runtime in ``lance-namespace-cts/src/capabilities.rs`` re-implements
the same env-var → file → fallback resolution order; that file is the
single source of truth for what the *test process* will see at runtime.
This Python module is only used by the linter (to validate that every
``requires_capabilities`` ID is registered) and, optionally, by the
generator (to emit human-readable ``SKIP: <reason>`` suffixes).
"""

from __future__ import annotations

from pathlib import Path

# Resolved by callers via Path(__file__).parent for portability with both
# `python ci/cts/...` and `uv run python ci/cts/...` invocation styles.
_CTS_DIR = Path(__file__).resolve().parent

#: Default location of the in-tree fallback flag set.
DEFAULT_FALLBACK_FILE = _CTS_DIR / "capabilities.directory.txt"


def load_fallback_capabilities(path: Path = DEFAULT_FALLBACK_FILE) -> set[str]:
    """Read the static capability set used when no env / config overrides exist.

    The file format is one flag per line; ``#`` introduces a comment;
    blank lines are ignored.  Missing file → empty set (the test process
    will then skip every capability-gated case, which is the safe default).
    """
    if not path.is_file():
        return set()
    out: set[str] = set()
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.split("#", 1)[0].strip()
        if not line:
            continue
        out.add(line)
    return out


def assert_capability_ids(
    declared: set[str],
    referenced: set[str],
) -> list[str]:
    """Return the list of `referenced` flags that are not in `declared`."""
    return sorted(referenced - declared)


__all__ = [
    "DEFAULT_FALLBACK_FILE",
    "assert_capability_ids",
    "load_fallback_capabilities",
]
