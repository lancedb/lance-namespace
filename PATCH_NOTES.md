# Temporary Patch for Lance-Index

## Issue
Lance versions 0.31-0.33 have a compilation error in the `lance-index` crate where `TempDir::new()?.keep()` is called, but `keep` is a field, not a method, in the version of `tempfile` being used.

## Solution
Created a local patch of `lance-index-0.31.1` in the `lance-index-patched` directory with the following change:

```rust
// Original (broken):
let dir = TempDir::new()?.keep();

// Fixed:
let dir = TempDir::new()?.into_path();
```

## Configuration
Added `.cargo/config.toml` to use the patched version:
```toml
[patch.crates-io]
lance-index = { path = "lance-index-patched" }
```

## TODO
- Remove this patch once Lance is updated to fix the tempfile compatibility issue
- Track upstream issue: https://github.com/lancedb/lance/issues/[TBD]