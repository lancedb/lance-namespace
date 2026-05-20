// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Error-code assertions consumed by every generated test.
//!
//! The tricky part is that the trait under test (`LanceNamespace`)
//! returns `lance_core::Result<_>` — i.e. its error type is
//! `lance_core::Error`, not `NamespaceError`.  The Rust impl
//! (`DirectoryNamespace`) wraps the underlying `NamespaceError` into
//! `lance_core::Error::Namespace { source, .. }` via the
//! `From<NamespaceError>` impl in `lance-namespace/src/error.rs`.
//!
//! Recovering the contract-level error code therefore requires a small
//! cascade:
//!
//!   1. Try `Error::Namespace { source, .. }` → downcast `source` to
//!      `NamespaceError` → call `code().as_u32()`.
//!   2. Treat `Error::NotSupported { .. }` (the trait's default impl
//!      placeholder) as the canonical `Unsupported = 0` code.
//!   3. Fall back to `None` — let the contract harness flag the case as
//!      "unrecognised error" rather than guessing.
//!
//! Treating `NotSupported` specially in (2) is deliberate: a number of
//! `DirectoryNamespace` operations *return* it through the trait
//! default rather than via `NamespaceError::Unsupported`, but to a
//! contract test the two are indistinguishable.

use lance_core::Error as LanceError;
use lance_namespace::error::NamespaceError;

/// Map any `lance_core::Error` to the contract-level error code defined
/// in `errors.md`.  Returns `None` when the error doesn't carry a
/// recognisable namespace code (very rare in practice, but tests should
/// still surface the original error rather than passing silently).
pub fn error_code_of(err: &LanceError) -> Option<u32> {
    if let LanceError::Namespace { source, .. } = err {
        if let Some(ns_err) = source.downcast_ref::<NamespaceError>() {
            return Some(ns_err.code().as_u32());
        }
        // Some adapter layers may double-box; try one more level.
        if let Some(deeper) = source.source()
            && let Some(ns_err) = deeper.downcast_ref::<NamespaceError>()
        {
            return Some(ns_err.code().as_u32());
        }
        return None;
    }
    if let LanceError::NotSupported { .. } = err {
        return Some(0);
    }
    // `dir.rs` short-circuits a few argument-validation paths through
    // `lance_core::Error::invalid_input_source(..)` rather than wrapping
    // a `NamespaceError::InvalidInput`. The contract sees a 13 either
    // way, so collapse the two encodings here.
    if let LanceError::InvalidInput { .. } = err {
        return Some(13);
    }
    None
}

/// Assert the result is an `Err` whose contract-level error code is in
/// `expected_codes`.  `expected_codes` is treated as `code ∪
/// alternatives` so the call site doesn't have to merge them itself.
#[track_caller]
pub fn assert_contract_error<T: std::fmt::Debug>(
    result: &std::result::Result<T, LanceError>,
    expected_codes: &[u32],
) {
    match result {
        Ok(ok) => panic!("expected contract error in {expected_codes:?}, got Ok({ok:?})"),
        Err(err) => {
            let actual = error_code_of(err);
            match actual {
                Some(code) if expected_codes.contains(&code) => (),
                Some(code) => {
                    panic!("expected contract error in {expected_codes:?}, got code {code}: {err}")
                }
                None => panic!(
                    "expected contract error in {expected_codes:?}, but error is not a \
                     recognised NamespaceError: {err:?}"
                ),
            }
        }
    }
}

/// Symmetric helper for happy-path cases: assert the result is `Ok`.
/// Used by generated tests when the bundle's `then.status == "ok"`.
#[track_caller]
pub fn assert_contract_ok<T: std::fmt::Debug>(result: &std::result::Result<T, LanceError>) {
    if let Err(err) = result {
        panic!(
            "expected Ok, got contract error code = {:?}, error = {err}",
            error_code_of(err)
        );
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn extracts_namespace_error_code() {
        let ns_err = NamespaceError::TableNotFound {
            message: "x".into(),
        };
        let lance_err: LanceError = ns_err.into();
        assert_eq!(error_code_of(&lance_err), Some(4));
    }

    #[test]
    fn maps_not_supported_to_zero() {
        let err = LanceError::not_supported("nope".to_string());
        assert_eq!(error_code_of(&err), Some(0));
    }

    #[test]
    fn maps_invalid_input_to_thirteen() {
        let err = LanceError::invalid_input("bad".to_string());
        assert_eq!(error_code_of(&err), Some(13));
    }

    #[test]
    fn assert_error_accepts_alternatives() {
        let ns_err = NamespaceError::NamespaceAlreadyExists {
            message: "dup".into(),
        };
        let lance_err: LanceError = ns_err.into();
        let r: Result<(), _> = Err(lance_err);
        assert_contract_error(&r, &[2, 13]);
    }
}
