#!/usr/bin/env python3
"""Apply OAI Overlay 1.0 to an OpenAPI spec.

Applies actions from an overlay file to a spec, writing the result to stdout.

Usage: uv run python ci/cts/apply_overlay.py <spec> <overlay>
"""
import argparse
import copy
import sys
from pathlib import Path
from typing import Any

import yaml
from jsonpath_ng import parse


def load_yaml(path: str) -> dict[str, Any]:
    """Load YAML file."""
    with open(path) as f:
        return yaml.safe_load(f)


def apply_overlay(spec: dict[str, Any], overlay: dict[str, Any]) -> dict[str, Any]:
    """Apply overlay actions to spec using JSONPath targets.

    Actions follow OAI Overlay 1.0 format:
    - target: JSONPath selector
    - update: dict of fields to merge or set

    Returns a deep copy of spec with all actions applied — never mutates the input.
    """
    # Deep copy to avoid mutating the caller's spec dict (jsonpath_ng returns
    # references into the original object, so in-place update() would corrupt it).
    spec = copy.deepcopy(spec)
    actions = overlay.get("actions", [])
    
    for action in actions:
        target_path = action.get("target")
        update_data = action.get("update")
        
        if not target_path or not update_data:
            continue
        
        # Parse JSONPath and apply update to all matches
        try:
            jsonpath_expr = parse(target_path)
        except Exception as e:
            print(f"WARNING: Invalid JSONPath '{target_path}': {e}", file=sys.stderr)
            continue
        
        matches = jsonpath_expr.find(spec)
        if not matches:
            # Silent skip if path doesn't match (overlay may target optional fields)
            continue
        
        for match in matches:
            # Get the parent object and key
            if match.path is None:
                # Root match — replace entire spec
                spec = update_data
            else:
                # Update the matched node by merging with update_data
                parent_path = str(match.path.left) if hasattr(match.path, 'left') else None
                if isinstance(match.value, dict) and isinstance(update_data, dict):
                    # Merge dicts
                    match.value.update(update_data)
                else:
                    # Replace value
                    # Navigate to parent and set key
                    parts = str(match.full_path).split('.')
                    current = spec
                    for part in parts[:-1]:
                        current = current[part]
                    current[parts[-1]] = update_data
    
    return spec


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("spec", help="OpenAPI spec file")
    parser.add_argument("overlay", help="OAI Overlay 1.0 file")
    args = parser.parse_args()
    
    spec_path = Path(args.spec)
    overlay_path = Path(args.overlay)
    
    if not spec_path.exists():
        print(f"ERROR: spec not found: {spec_path}", file=sys.stderr)
        sys.exit(1)
    
    if not overlay_path.exists():
        print(f"ERROR: overlay not found: {overlay_path}", file=sys.stderr)
        sys.exit(1)
    
    spec = load_yaml(str(spec_path))
    overlay = load_yaml(str(overlay_path))
    
    merged = apply_overlay(spec, overlay)
    
    # Output as YAML
    yaml.dump(merged, sys.stdout, default_flow_style=False, allow_unicode=True, sort_keys=False)


if __name__ == "__main__":
    main()
