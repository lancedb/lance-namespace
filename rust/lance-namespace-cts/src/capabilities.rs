// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Capability-flag resolution for the in-process CTS harness.
//!
//! Resolution order (matches design §4.3):
//!   1. `LANCE_CTS_CAPABILITIES` env var, comma-separated list of flags.
//!   2. `cts.config.toml` in the current working directory, key
//!      `capabilities = [...]`.
//!   3. The compile-time fallback set baked in from
//!      `ci/cts/capabilities.directory.txt`.

use std::collections::BTreeSet;
use std::path::Path;

/// Compile-time fallback capability set for the in-tree
/// `DirectoryNamespace`.  Lives outside this crate so the linter
/// (`ci/cts/lint_contracts.py`) and the runtime here cannot drift.
const FALLBACK_CAPABILITIES_TXT: &str = include_str!("../../../ci/cts/capabilities.directory.txt");

/// Resolved capability set, used by every generated test to decide
/// whether to run.
#[derive(Debug, Clone)]
pub struct Capabilities {
    flags: BTreeSet<String>,
}

impl Capabilities {
    /// Read flags following the documented resolution order.
    ///
    /// This call is cheap (parses one env var, optionally one TOML file,
    /// otherwise the embedded fallback) so we deliberately do **not**
    /// memoise it — concurrent tests may read different env states.
    pub fn from_env() -> Self {
        if let Ok(raw) = std::env::var("LANCE_CTS_CAPABILITIES") {
            return Self::from_csv(&raw);
        }
        if let Some(set) = read_toml_capabilities(Path::new("cts.config.toml")) {
            return Self { flags: set };
        }
        Self::from_fallback_txt(FALLBACK_CAPABILITIES_TXT)
    }

    /// Build from a comma-separated string (the env-var format).
    pub fn from_csv(raw: &str) -> Self {
        let flags = raw
            .split(',')
            .map(|s| s.trim().to_string())
            .filter(|s| !s.is_empty())
            .collect();
        Self { flags }
    }

    /// Build from a `key = value` style flag file (one flag per line,
    /// `#` starts a comment).
    pub fn from_fallback_txt(txt: &str) -> Self {
        let mut flags = BTreeSet::new();
        for raw_line in txt.lines() {
            let line = raw_line.split('#').next().unwrap_or("").trim();
            if !line.is_empty() {
                flags.insert(line.to_string());
            }
        }
        Self { flags }
    }

    /// True when *every* flag in `required` is present.
    pub fn has_all(&self, required: &[&str]) -> bool {
        required.iter().all(|r| self.flags.contains(*r))
    }

    /// Convenience used by generated tests.  When some capabilities are
    /// missing, prints a single `SKIP: …` line so CI logs make the skip
    /// reason auditable, and returns `true` so the caller can early-return.
    pub fn skip_if_missing(&self, required: &[&str]) -> bool {
        let missing: Vec<&str> = required
            .iter()
            .filter(|r| !self.flags.contains(**r))
            .copied()
            .collect();
        if missing.is_empty() {
            false
        } else {
            // Use `eprintln!` so output survives `cargo test --quiet`.
            eprintln!("SKIP: missing capabilities: {missing:?}");
            true
        }
    }

    /// Direct flag lookup, useful for assertions that *change shape* based
    /// on capabilities (rare; prefer `skip_if_missing`).
    pub fn has(&self, flag: &str) -> bool {
        self.flags.contains(flag)
    }

    /// Iterate over all resolved flags.  Sorted because the underlying
    /// container is a `BTreeSet`.
    pub fn iter(&self) -> impl Iterator<Item = &str> {
        self.flags.iter().map(String::as_str)
    }
}

/// Parse a `cts.config.toml` of shape:
/// ```toml
/// capabilities = ["foo", "bar"]
/// ```
/// We deliberately avoid pulling in `serde_derive` / `toml` for this
/// since one regex-free pass is enough; the file is always under
/// developer control.
fn read_toml_capabilities(path: &Path) -> Option<BTreeSet<String>> {
    let contents = std::fs::read_to_string(path).ok()?;
    let mut in_array = false;
    let mut acc = String::new();
    for line in contents.lines() {
        let trimmed = line.trim();
        if trimmed.is_empty() || trimmed.starts_with('#') {
            continue;
        }
        if !in_array {
            if let Some(rest) = trimmed.strip_prefix("capabilities") {
                let rest = rest.trim_start();
                if !rest.starts_with('=') {
                    continue;
                }
                let after_eq = rest[1..].trim_start();
                if !after_eq.starts_with('[') {
                    return None;
                }
                in_array = true;
                acc.push_str(&after_eq[1..]);
            }
        } else {
            acc.push(' ');
            acc.push_str(trimmed);
        }
        if in_array && acc.contains(']') {
            break;
        }
    }
    if !in_array {
        return None;
    }
    let end = acc.find(']')?;
    let body = &acc[..end];
    let mut out = BTreeSet::new();
    for piece in body.split(',') {
        let s = piece.trim().trim_matches(|c| c == '"' || c == '\'');
        if !s.is_empty() {
            out.insert(s.to_string());
        }
    }
    Some(out)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn fallback_txt_is_parseable() {
        // The bundled fallback should resolve to a non-empty set.
        let caps = Capabilities::from_fallback_txt(FALLBACK_CAPABILITIES_TXT);
        assert!(caps.has("supports_one_level_namespace_path"));
        assert!(!caps.has("supports_two_level_namespace_path"));
        assert!(!caps.has("supports_table_tags"));
    }

    #[test]
    fn skip_if_missing_reports_missing_only() {
        let caps = Capabilities::from_csv("a, b, c");
        assert!(!caps.skip_if_missing(&["a", "b"]));
        assert!(caps.skip_if_missing(&["a", "z"]));
    }

    #[test]
    fn csv_handles_whitespace_and_empties() {
        let caps = Capabilities::from_csv(" foo ,bar,, baz ");
        assert!(caps.has("foo"));
        assert!(caps.has("bar"));
        assert!(caps.has("baz"));
        assert_eq!(caps.iter().count(), 3);
    }
}
