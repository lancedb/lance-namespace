// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Hand-written sanity test: prove the harness wires up before any
//! contracts are generated.

use lance_namespace_cts::{Capabilities, ContractCallerFactory};

#[tokio::test(flavor = "multi_thread")]
async fn harness_smoke() {
    let caps = Capabilities::from_env();
    // The fallback file declares at least one capability.
    assert!(
        caps.iter().count() > 0,
        "Capabilities::from_env() must yield at least one flag (default \
         fallback file is not empty)"
    );

    // Building the in-process caller must succeed end-to-end (constructs
    // a TempDir + DirectoryNamespace).
    let _api = ContractCallerFactory::build().await;
}
