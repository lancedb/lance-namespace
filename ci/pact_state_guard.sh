#!/usr/bin/env bash
# pact_state_guard.sh — Pre-commit hook: guard against unauthorized Pact provider states.
#
# Checks every passed *.java file for @State( annotations, extracts the state string(s),
# and verifies each is present in the whitelist defined by
# contract-pack/provider-states.lock.json.
#
# Usage (called by pre-commit framework):
#   ci/pact_state_guard.sh [file1.java file2.java ...]
#
# Exit codes:
#   0 — all @State strings are whitelisted (or no @State found)
#   1 — one or more unauthorized @State strings detected
#
# Requirements:
#   - bash 4+
#   - python3 (for JSON parsing of provider-states.lock.json)
#     If python3 is unavailable, falls back to a grep-based approach (less precise).
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel 2>/dev/null || pwd)"
LOCK_FILE="${REPO_ROOT}/contract-pack/provider-states.lock.json"

# ────────────────────────────────────────────────────────────────────────────
# Validate environment
# ────────────────────────────────────────────────────────────────────────────
if [[ ! -f "${LOCK_FILE}" ]]; then
    echo "[ERROR] pact_state_guard: lock file not found: ${LOCK_FILE}" >&2
    echo "        Create contract-pack/provider-states.lock.json before committing." >&2
    exit 1
fi

if [[ $# -eq 0 ]]; then
    # No files passed — nothing to check
    exit 0
fi

# ────────────────────────────────────────────────────────────────────────────
# Load whitelist from provider-states.lock.json
# ────────────────────────────────────────────────────────────────────────────
if command -v python3 &>/dev/null; then
    # Use python3 for reliable JSON parsing
    WHITELIST=$(python3 - "${LOCK_FILE}" <<'PYEOF'
import json, sys
lock = json.loads(open(sys.argv[1], encoding="utf-8").read())
for s in lock.get("states", []):
    print(s["state"])
PYEOF
)
else
    # Fallback: grep-based extraction (works for well-formatted JSON)
    WHITELIST=$(grep -oP '(?<="state": ")[^"]+' "${LOCK_FILE}" || true)
fi

if [[ -z "${WHITELIST}" ]]; then
    echo "[WARN] pact_state_guard: no states found in ${LOCK_FILE}. Allowing all." >&2
    exit 0
fi

# ────────────────────────────────────────────────────────────────────────────
# Check each Java file
# ────────────────────────────────────────────────────────────────────────────
ERRORS=0

for java_file in "$@"; do
    [[ -f "${java_file}" ]] || continue

    # Extract @State("...") and @State({"...", "..."}) patterns
    # Handles:
    #   @State("state string here")
    #   @State(value = "state string here")
    #   @State({"state 1", "state 2"})
    while IFS= read -r line; do
        # Strip leading/trailing whitespace
        line="${line#"${line%%[![:space:]]*}"}"
        line="${line%"${line##*[![:space:]]}"}"

        [[ -z "${line}" ]] && continue

        # Check if this state string is in the whitelist
        found=0
        while IFS= read -r allowed; do
            if [[ "${line}" == "${allowed}" ]]; then
                found=1
                break
            fi
        done <<< "${WHITELIST}"

        if [[ "${found}" -eq 0 ]]; then
            echo "[ERROR] pact_state_guard: unauthorized @State string in ${java_file}:" >&2
            echo "        '${line}'" >&2
            echo "" >&2
            echo "        Allowed states (from contract-pack/provider-states.lock.json):" >&2
            while IFS= read -r allowed_state; do
                echo "          - '${allowed_state}'" >&2
            done <<< "${WHITELIST}"
            echo "" >&2
            echo "        To add a new state, update provider-states.lock.json first." >&2
            ERRORS=$((ERRORS + 1))
        fi
    done < <(
        # Extract state strings from @State annotations using Python for accuracy
        if command -v python3 &>/dev/null; then
            python3 - "${java_file}" <<'PYEOF'
import re, sys

content = open(sys.argv[1], encoding="utf-8", errors="replace").read()

# Match @State("...") or @State(value = "...") or @State({"...", ...})
pattern = re.compile(
    r'@State\s*\('
    r'(?:'
        r'\s*\{([^}]+)\}'         # array form: @State({"a", "b"})
        r'|'
        r'(?:value\s*=\s*)?'      # optional `value =`
        r'"((?:[^"\\]|\\.)*)"'    # single string form
    r')',
    re.MULTILINE,
)

for m in pattern.finditer(content):
    array_part = m.group(1)
    single_part = m.group(2)
    if array_part:
        # Extract each quoted string in the array
        for s in re.findall(r'"((?:[^"\\]|\\.)*)"', array_part):
            print(s)
    elif single_part is not None:
        print(single_part)
PYEOF
        else
            # Fallback: grep for @State("...") single-string form only
            grep -oP '(?<=@State\(")[^"]+' "${java_file}" 2>/dev/null || true
        fi
    )
done

# ────────────────────────────────────────────────────────────────────────────
# Result
# ────────────────────────────────────────────────────────────────────────────
if [[ "${ERRORS}" -gt 0 ]]; then
    echo "[FAIL] pact_state_guard: ${ERRORS} unauthorized @State string(s) found." >&2
    exit 1
fi

exit 0
