// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! # Lance Namespace —— Behavioural Contract Test Harness
//!
//! Hand-written runtime that backs the auto-generated tests under
//! `tests/contracts/`.  The split mirrors the openapi-generator pattern:
//! a stable, reviewed runtime (this crate) hosts a small surface area
//! (`Capabilities`, `Fixtures`, `assert_contract_error`,
//! `ContractCaller`/`InProcessDirectoryCaller`,
//! `ContractCallerFactory`); the test bodies are produced by
//! `ci/cts/gen_contract_tests.py` from
//! `docs/src/cts-contracts/*.yaml`.
//!
//! See §6.3 of the behavioural-contract design notes (maintained
//! outside this repo) for the full contract.

pub mod assertions;
pub mod caller;
pub mod capabilities;
pub mod fixtures;

pub use assertions::{assert_contract_error, assert_contract_ok, error_code_of};
pub use caller::{ContractCaller, ContractCallerFactory, InProcessDirectoryCaller};
pub use capabilities::Capabilities;
pub use fixtures::Fixtures;

/// Re-exported for the generated tests so they don't need to depend on
/// `lance-namespace` directly.
pub use lance_namespace::models;
