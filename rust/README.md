# Lance Namespace Rust Implementation

This directory contains the Rust implementation of the Lance Namespace specification.

## Structure

- `lance-namespace/` - The main Lance Namespace Rust crate
- `lance-namespace-reqwest-client/` - Auto-generated REST client from OpenAPI spec

## Development

### Prerequisites

- Rust 1.70+ (stable)
- Make

### Common Commands

```bash
# Format all code
make format

# Check formatting
make format-check

# Run clippy linter on lance-namespace (ignores auto-generated code warnings)
make lint-lance-namespace

# Build lance-namespace crate
make build-lance-namespace

# Run tests for lance-namespace
make test-lance-namespace

# Run full CI check (format, lint, build, test)
make check

# Generate/regenerate the REST client from OpenAPI spec
make gen-reqwest-client

# Clean generated files
make clean
```

### CI Workflow

The GitHub Actions workflow (`.github/workflows/rust.yml`) runs on every push and PR that affects Rust code:

1. **Format Check** - Ensures all code is properly formatted with `cargo fmt`
2. **Clippy Lint** - Runs clippy on lance-namespace (warnings from auto-generated code are filtered)
3. **Build** - Builds the lance-namespace crate with all features
4. **Test** - Runs all tests with all features enabled

### Notes

- The `lance-namespace-reqwest-client` is auto-generated from the OpenAPI spec and should not be manually edited
- Clippy warnings from the auto-generated client are intentionally filtered out in CI
- Use `make check` locally before pushing to ensure CI will pass