#!/bin/bash
# Lance Namespace Pact Naming Lint
# Validates consumer/provider/tag names against contract-pack/naming.lock.yaml
#
# Usage:
#   naming_lint.sh --consumer <name> --provider <name>
#   naming_lint.sh --consumer lance-namespace-java-apache --provider lance-namespace-server
#
# Exit codes:
#   0 - all names are valid
#   1 - one or more names violate the naming contract
set -euo pipefail

# ---------------------------------------------------------------------------
# Allowed values (frozen from naming.lock.yaml schemaVersion 1.0, 2026-05-09)
# Do NOT add new values without updating naming.lock.yaml first.
# ---------------------------------------------------------------------------
EXPECTED_CONSUMERS=(
    "lance-namespace-java-apache"
    "lance-namespace-java-async"
    "lance-namespace-python-urllib3"
    "lance-namespace-rust-reqwest"
)
EXPECTED_PROVIDER="lance-namespace-server"
EXPECTED_TAGS=("branch" "git-sha" "deployed")

# ---------------------------------------------------------------------------
# Parse arguments
# ---------------------------------------------------------------------------
CONSUMER=""
PROVIDER=""
TAG=""

usage() {
    echo "Usage: $(basename "$0") --consumer <name> --provider <name> [--tag <tag>]"
    echo ""
    echo "  --consumer  Consumer name (e.g. lance-namespace-java-apache)"
    echo "  --provider  Provider name (e.g. lance-namespace-server)"
    echo "  --tag       Optional tag to validate (e.g. branch, git-sha, deployed)"
    echo ""
    echo "Allowed consumers:"
    for c in "${EXPECTED_CONSUMERS[@]}"; do
        echo "  - ${c}"
    done
    echo ""
    echo "Allowed provider:  ${EXPECTED_PROVIDER}"
    echo ""
    echo "Allowed tags:      ${EXPECTED_TAGS[*]}"
}

if [[ $# -eq 0 ]]; then
    usage >&2
    exit 1
fi

while [[ $# -gt 0 ]]; do
    case "$1" in
        --consumer)
            CONSUMER="${2:-}"
            shift 2
            ;;
        --provider)
            PROVIDER="${2:-}"
            shift 2
            ;;
        --tag)
            TAG="${2:-}"
            shift 2
            ;;
        --help|-h)
            usage
            exit 0
            ;;
        *)
            echo "[ERROR] Unknown argument: $1" >&2
            usage >&2
            exit 1
            ;;
    esac
done

# ---------------------------------------------------------------------------
# Validation helpers
# ---------------------------------------------------------------------------
ERRORS=0

validate_consumer() {
    local name="$1"
    for allowed in "${EXPECTED_CONSUMERS[@]}"; do
        if [[ "${name}" == "${allowed}" ]]; then
            return 0
        fi
    done
    echo "[ERROR] Consumer name '${name}' is not in the naming contract." >&2
    echo "        Allowed values: ${EXPECTED_CONSUMERS[*]}" >&2
    ERRORS=$((ERRORS + 1))
}

validate_provider() {
    local name="$1"
    if [[ "${name}" != "${EXPECTED_PROVIDER}" ]]; then
        echo "[ERROR] Provider name '${name}' is not in the naming contract." >&2
        echo "        Allowed value: ${EXPECTED_PROVIDER}" >&2
        ERRORS=$((ERRORS + 1))
    fi
}

validate_tag() {
    local tag="$1"
    for allowed in "${EXPECTED_TAGS[@]}"; do
        if [[ "${tag}" == "${allowed}" ]]; then
            return 0
        fi
    done
    echo "[ERROR] Tag '${tag}' is not in the naming contract." >&2
    echo "        Allowed values: ${EXPECTED_TAGS[*]}" >&2
    ERRORS=$((ERRORS + 1))
}

# ---------------------------------------------------------------------------
# Run validations
# ---------------------------------------------------------------------------
if [[ -z "${CONSUMER}" && -z "${PROVIDER}" ]]; then
    echo "[ERROR] At least one of --consumer or --provider must be provided." >&2
    usage >&2
    exit 1
fi

if [[ -n "${CONSUMER}" ]]; then
    validate_consumer "${CONSUMER}"
fi

if [[ -n "${PROVIDER}" ]]; then
    validate_provider "${PROVIDER}"
fi

if [[ -n "${TAG}" ]]; then
    validate_tag "${TAG}"
fi

# ---------------------------------------------------------------------------
# Result
# ---------------------------------------------------------------------------
if [[ "${ERRORS}" -gt 0 ]]; then
    echo "[FAIL]  Naming lint failed with ${ERRORS} error(s). See above for details." >&2
    exit 1
fi

echo "[OK]    All provided names conform to the naming contract."
exit 0
