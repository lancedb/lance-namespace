#!/usr/bin/env python3
"""
pact_consistency_check.py — Cross-consumer matcher consistency checker.

Compares matcher semantics across multiple consumer pact files against the
canonical contract-pack/interactions.json, and outputs a difference matrix.

Usage:
    # Check all 3 language consumers (default multi-dir mode):
    python ci/pact_consistency_check.py

    # Check a single directory:
    python ci/pact_consistency_check.py \\
        --pacts-dir java/lance-namespace-apache-client/target/pacts

    # Check multiple directories (comma-separated):
    python ci/pact_consistency_check.py \\
        --pacts-dirs java/lance-namespace-apache-client/target/pacts,\\
                     python/lance_namespace_urllib3_client/tests/pact/pacts,\\
                     rust/lance-namespace-reqwest-client/target/pacts

    # Filter to specific consumers:
    python ci/pact_consistency_check.py \\
        --consumers lance-namespace-java-apache,lance-namespace-python-urllib3

Known consumer names (from contract-pack/naming.lock.yaml):
    lance-namespace-java-apache      (Java / Apache HTTP)
    lance-namespace-python-urllib3   (Python / urllib3)
    lance-namespace-rust-reqwest     (Rust / reqwest)

Exit codes:
    0 — all matchers consistent with contract pack
    1 — matcher drift detected
    2 — contract pack file not found
"""
from __future__ import annotations

import argparse
import glob
import json
import os
import sys
from pathlib import Path
from typing import Any

# ---------------------------------------------------------------------------
# Default pact directories — one per language consumer
# ---------------------------------------------------------------------------

DEFAULT_PACTS_DIRS: list[str] = [
    "java/lance-namespace-apache-client/target/pacts",
    "python/lance_namespace_urllib3_client/tests/pact_tests/pacts",
    "rust/lance-namespace-reqwest-client/target/pacts",
]

# ---------------------------------------------------------------------------
# Types
# ---------------------------------------------------------------------------

Pact = dict[str, Any]
MatcherMap = dict[str, str]  # jsonpath → matcher_type
# interaction_id → consumer_name → MatcherMap
ConsistencyMatrix = dict[str, dict[str, MatcherMap]]


# ---------------------------------------------------------------------------
# Load contract pack
# ---------------------------------------------------------------------------

def load_contract_pack(contract_pack_dir: Path) -> dict[str, Any]:
    """
    Load the canonical interactions.json from the contract pack directory.

    Returns a dict with:
        interactions: list of canonical interaction defs
        canonical_matchers: interaction_id → MatcherMap
    """
    interactions_path = contract_pack_dir / "interactions.json"
    if not interactions_path.exists():
        print(f"[ERROR] Contract pack interactions.json not found: {interactions_path}", file=sys.stderr)
        sys.exit(2)

    data: dict[str, Any] = json.loads(interactions_path.read_text(encoding="utf-8"))
    interactions = data.get("interactions", [])

    canonical_matchers: dict[str, MatcherMap] = {}
    for interaction in interactions:
        iid = interaction.get("id", interaction.get("description", "?"))
        matchers: MatcherMap = {}
        body_rules = interaction.get("response", {}).get("matchingRules", {}).get("body", {})
        for path, rule in body_rules.items():
            matcher_list = rule.get("matchers", [])
            for m in matcher_list:
                match_type = m.get("match", "unknown")
                if "min" in m:
                    match_type = f"{match_type}(min={m['min']})"
                matchers[path] = match_type
        canonical_matchers[iid] = matchers

    return {
        "interactions": interactions,
        "canonical_matchers": canonical_matchers,
    }


# ---------------------------------------------------------------------------
# Load pact files
# ---------------------------------------------------------------------------

def load_pacts_from_dir(pacts_dir: Path, consumers_filter: list[str] | None = None) -> list[Pact]:
    """
    Load all pact JSON files from a single directory (and subdirectories).

    Optionally filter to specific consumer names.
    Returns empty list (with a warning) if directory does not exist.
    """
    if not pacts_dir.exists():
        print(f"[WARN] Pacts directory not found: {pacts_dir}", file=sys.stderr)
        print("[WARN] Run consumer tests first to generate pact files.", file=sys.stderr)
        return []

    pattern = str(pacts_dir / "**" / "*.json")
    pacts: list[Pact] = []

    for filepath in glob.glob(pattern, recursive=True):
        try:
            pact: Pact = json.loads(Path(filepath).read_text(encoding="utf-8"))
        except (json.JSONDecodeError, OSError) as exc:
            print(f"[WARN] Cannot load pact file {filepath!r}: {exc}", file=sys.stderr)
            continue

        consumer_name = pact.get("consumer", {}).get("name", "")
        if consumers_filter and consumer_name not in consumers_filter:
            continue
        pact["_source_file"] = filepath
        pacts.append(pact)

    return pacts


def load_pacts(
    pacts_dirs: list[Path],
    consumers_filter: list[str] | None = None,
) -> list[Pact]:
    """
    Load pact JSON files from multiple directories.

    Deduplicates by (consumer, provider, description) so that the same pact
    file present in multiple directories is only counted once.
    """
    seen: set[tuple[str, str, str]] = set()
    all_pacts: list[Pact] = []
    total_dirs_found = 0

    for pacts_dir in pacts_dirs:
        dir_pacts = load_pacts_from_dir(pacts_dir, consumers_filter)
        if dir_pacts:
            total_dirs_found += 1
        print(
            f"[INFO] Loaded {len(dir_pacts)} pact file(s) from {pacts_dir}",
            file=sys.stderr,
        )
        for pact in dir_pacts:
            consumer = pact.get("consumer", {}).get("name", "")
            provider = pact.get("provider", {}).get("name", "")
            # Use a set of descriptions as the dedup key
            descriptions = frozenset(
                i.get("description", "") for i in pact.get("interactions", [])
            )
            key = (consumer, provider, str(sorted(descriptions)))
            if key not in seen:
                seen.add(key)
                all_pacts.append(pact)

    print(
        f"[INFO] Total: {len(all_pacts)} unique pact file(s) across {len(pacts_dirs)} director(ies)",
        file=sys.stderr,
    )
    return all_pacts


# ---------------------------------------------------------------------------
# Matcher extraction
# ---------------------------------------------------------------------------

def extract_matchers(interaction: dict[str, Any]) -> MatcherMap:
    """
    Extract matcher rules from a single pact interaction's response.

    Returns a MatcherMap: {jsonpath: matcher_type_string}.
    """
    matchers: MatcherMap = {}
    response = interaction.get("response", {})
    body_rules = response.get("matchingRules", {}).get("body", {})

    for path, rule in body_rules.items():
        if isinstance(rule, dict):
            matcher_list = rule.get("matchers", [])
            for m in matcher_list:
                if isinstance(m, dict):
                    match_type = m.get("match", "unknown")
                    if "min" in m:
                        match_type = f"{match_type}(min={m['min']})"
                    matchers[path] = match_type

    return matchers


def _description_to_id(description: str, canonical_interactions: list[dict[str, Any]]) -> str | None:
    """Find the canonical interaction ID matching a description string."""
    for interaction in canonical_interactions:
        if interaction.get("description") == description:
            return interaction.get("id", description)
    return None


# ---------------------------------------------------------------------------
# Comparison
# ---------------------------------------------------------------------------

def compare_matchers(
    pacts: list[Pact],
    contract_pack: dict[str, Any],
) -> tuple[ConsistencyMatrix, list[dict[str, Any]]]:
    """
    Compare matchers in pact files against the canonical contract pack.

    Returns:
        matrix   — ConsistencyMatrix for report generation
        drifts   — list of drift records (deviations from canonical)
    """
    canonical_matchers: dict[str, MatcherMap] = contract_pack["canonical_matchers"]
    canonical_interactions: list[dict[str, Any]] = contract_pack["interactions"]
    matrix: ConsistencyMatrix = {}
    drifts: list[dict[str, Any]] = []

    for pact in pacts:
        consumer = pact.get("consumer", {}).get("name", "unknown")

        for interaction in pact.get("interactions", []):
            description = interaction.get("description", "")
            iid = _description_to_id(description, canonical_interactions)
            if iid is None:
                iid = description  # unknown interaction — still record it

            if iid not in matrix:
                matrix[iid] = {}

            pact_matchers = extract_matchers(interaction)
            matrix[iid][consumer] = pact_matchers

            # Compare against canonical
            canonical = canonical_matchers.get(iid, {})
            for path, canonical_matcher in canonical.items():
                pact_matcher = pact_matchers.get(path)
                if pact_matcher is None:
                    drifts.append({
                        "interaction_id": iid,
                        "consumer": consumer,
                        "path": path,
                        "drift_type": "MISSING_IN_PACT",
                        "canonical": canonical_matcher,
                        "actual": None,
                        "severity": "ERROR",
                    })
                elif pact_matcher != canonical_matcher:
                    drifts.append({
                        "interaction_id": iid,
                        "consumer": consumer,
                        "path": path,
                        "drift_type": "MATCHER_MISMATCH",
                        "canonical": canonical_matcher,
                        "actual": pact_matcher,
                        "severity": "WARNING",
                    })

            # Check for extra matchers not in canonical (permissive — just warn)
            for path, pact_matcher in pact_matchers.items():
                if path not in canonical:
                    drifts.append({
                        "interaction_id": iid,
                        "consumer": consumer,
                        "path": path,
                        "drift_type": "EXTRA_IN_PACT",
                        "canonical": None,
                        "actual": pact_matcher,
                        "severity": "WARNING",
                    })

    return matrix, drifts


# ---------------------------------------------------------------------------
# Report generation
# ---------------------------------------------------------------------------

def _short_consumer(name: str) -> str:
    """Shorten consumer names for compact column headers."""
    return (
        name.replace("lance-namespace-", "")
            .replace("-apache", "/apache")
            .replace("-urllib3", "/urllib3")
            .replace("-reqwest", "/reqwest")
    )


def generate_matrix(
    matrix: ConsistencyMatrix,
    drifts: list[dict[str, Any]],
    contract_pack: dict[str, Any],
) -> str:
    """Generate a Markdown consistency matrix report."""
    canonical_matchers: dict[str, MatcherMap] = contract_pack["canonical_matchers"]
    lines: list[str] = [
        "# Pact Matcher Consistency Matrix",
        "",
        "Compares matcher rules across consumer pact files against `contract-pack/interactions.json`.",
        "",
        "| Symbol | Meaning |",
        "|--------|---------|",
        "| ✅ | Matches canonical |",
        "| ❌ | Differs from canonical |",
        "| ⬜ | Not present in this consumer pact |",
        "",
    ]

    has_errors = any(d["severity"] == "ERROR" for d in drifts)
    status = "FAIL" if has_errors else ("WARN" if drifts else "PASS")
    lines += [
        f"**Status**: {status}",
        f"**Drifts detected**: {len(drifts)} ({sum(1 for d in drifts if d['severity'] == 'ERROR')} errors, "
        f"{sum(1 for d in drifts if d['severity'] == 'WARNING')} warnings)",
        "",
    ]

    # Per-interaction matrix tables
    for iid, consumers in matrix.items():
        canonical = canonical_matchers.get(iid, {})
        all_paths = sorted(set(canonical.keys()) | {p for m in consumers.values() for p in m.keys()})
        consumer_names = sorted(consumers.keys())
        short_names = [_short_consumer(c) for c in consumer_names]

        lines += [f"## Interaction: `{iid}`", ""]
        if not all_paths:
            lines += ["*No body matching rules defined.*", ""]
            continue

        # Header row
        header = "| JSON Path | Canonical |" + "".join(f" {s} |" for s in short_names)
        sep = "|---|---|" + "---|" * len(consumer_names)
        lines += [header, sep]

        for path in all_paths:
            canonical_val = canonical.get(path, "—")
            row = f"| `{path}` | `{canonical_val}` |"
            for consumer in consumer_names:
                consumer_val = consumers.get(consumer, {}).get(path, "—")
                match_icon = "✅" if consumer_val == canonical_val else ("❌" if consumer_val != "—" else "⬜")
                row += f" {match_icon} `{consumer_val}` |"
            lines.append(row)

        lines.append("")

    # Drift details
    if drifts:
        lines += ["## Drift Details", ""]
        lines += [
            "| Severity | Interaction | Consumer | Path | Canonical | Actual | Type |",
            "|---|---|---|---|---|---|---|",
        ]
        for d in sorted(drifts, key=lambda x: (x["severity"], x["interaction_id"])):
            sev_icon = "🔴" if d["severity"] == "ERROR" else "🟡"
            lines.append(
                f"| {sev_icon} {d['severity']} "
                f"| `{d['interaction_id']}` "
                f"| {d['consumer']} "
                f"| `{d['path']}` "
                f"| `{d['canonical']}` "
                f"| `{d['actual']}` "
                f"| {d['drift_type']} |"
            )
        lines.append("")

    return "\n".join(lines)


# ---------------------------------------------------------------------------
# CLI entry point
# ---------------------------------------------------------------------------

def main() -> int:
    parser = argparse.ArgumentParser(
        description="Check matcher consistency across consumer pact files vs contract pack.",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__,
    )
    parser.add_argument(
        "--contract-pack",
        default="contract-pack",
        help="Path to contract-pack directory (default: contract-pack/)",
    )

    dirs_group = parser.add_mutually_exclusive_group()
    dirs_group.add_argument(
        "--pacts-dirs",
        default="",
        help=(
            "Comma-separated list of directories to search for pact JSON files. "
            "Defaults to all 3 language consumer directories: "
            + ", ".join(DEFAULT_PACTS_DIRS)
        ),
    )
    dirs_group.add_argument(
        "--pacts-dir",
        default="",
        help="Single directory to search for pact JSON files (shorthand for --pacts-dirs with one value).",
    )

    parser.add_argument(
        "--consumers",
        default="",
        help="Comma-separated list of consumer names to filter (default: all consumers)",
    )
    parser.add_argument(
        "--output",
        default="-",
        help="Output file path (default: stdout)",
    )
    args = parser.parse_args()

    consumers_filter = [c.strip() for c in args.consumers.split(",") if c.strip()] or None

    # Resolve pact directories
    if args.pacts_dir:
        # Single-dir shorthand (backward-compatible with old --pacts-dir flag)
        raw_dirs = [args.pacts_dir.strip()]
    elif args.pacts_dirs:
        raw_dirs = [d.strip() for d in args.pacts_dirs.split(",") if d.strip()]
    else:
        raw_dirs = DEFAULT_PACTS_DIRS

    pacts_dirs = [Path(d) for d in raw_dirs]

    contract_pack_dir = Path(args.contract_pack)
    contract_pack = load_contract_pack(contract_pack_dir)

    pacts = load_pacts(pacts_dirs, consumers_filter)

    if not pacts:
        print("[WARN] No pact files found. Report will show only canonical matchers.", file=sys.stderr)
        matrix: ConsistencyMatrix = {}
        drifts: list[dict[str, Any]] = []
    else:
        matrix, drifts = compare_matchers(pacts, contract_pack)

    report = generate_matrix(matrix, drifts, contract_pack)

    if args.output == "-":
        print(report)
    else:
        Path(args.output).write_text(report, encoding="utf-8")
        print(f"[OK] Report written to {args.output}", file=sys.stderr)

    has_errors = any(d["severity"] == "ERROR" for d in drifts)
    return 1 if has_errors else 0


if __name__ == "__main__":
    sys.exit(main())
