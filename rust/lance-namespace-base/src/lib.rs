// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Base Lance Namespace implementation using the Lance SDK directly.
//!
//! This crate provides a filesystem-directory-based implementation of the
//! [`LanceNamespace`] trait that delegates all operations to the Lance Rust SDK
//! (`Dataset`, `Scanner`, index APIs, etc.).
//!
//! ## Features
//!
//! - Hierarchical namespaces mapped to filesystem directories
//! - Tables stored as `{namespace_path}/{table_name}.lance`
//! - No manifest table, credential vending, or context providers
//! - Supports local filesystem and cloud storage via Lance's object store
//!
//! ## Usage
//!
//! ```no_run
//! # use lance_namespace_base::BaseLanceNamespaceBuilder;
//! # async fn example() -> Result<(), Box<dyn std::error::Error>> {
//! let namespace = BaseLanceNamespaceBuilder::new("/path/to/data")
//!     .build()
//!     .await?;
//! # Ok(())
//! # }
//! ```

pub mod base;

pub use base::{BaseLanceNamespace, BaseLanceNamespaceBuilder};
