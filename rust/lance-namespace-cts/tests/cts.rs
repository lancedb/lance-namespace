// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Cargo integration-test entry point for the Lance Namespace CTS.
//!
//! Cargo only treats files immediately under `tests/` as integration test
//! binaries.  We therefore use this single entry point to pull in every
//! generated and hand-written test module, so all contract cases share
//! one process start-up.
//!
//! - `mod sanity;` — hand-written; verifies the harness wires up
//!   correctly even before any contracts are generated.
//! - `mod contracts;` — produced by `ci/cts/gen_contract_tests.py`;
//!   declared in `tests/contracts/mod.rs` (which is checked-in but only
//!   ever lists generated submodules).

mod sanity;

mod contracts;
