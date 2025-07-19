# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## OpenAPI oneOf Pattern Handling

When working with OpenAPI specifications in this project, avoid using `oneOf` patterns as they cause compatibility issues across different language code generators. Instead, use the properties-based pattern similar to `AlterTransactionAction`:

### Don't use oneOf:
```yaml
FtsQuery:
  oneOf:
  - type: object
    required: [match]
    properties:
      match:
        $ref: '#/components/schemas/MatchQuery'
  - type: object
    required: [phrase]
    properties:
      phrase:
        $ref: '#/components/schemas/PhraseQuery'
```

### Do use properties pattern:
```yaml
FtsQuery:
  type: object
  description: |
    Full-text search query. Exactly one query type field must be provided.
  properties:
    match:
      $ref: '#/components/schemas/MatchQuery'
    phrase:
      $ref: '#/components/schemas/PhraseQuery'
    boost:
      $ref: '#/components/schemas/BoostQuery'
```

This pattern ensures better compatibility across different code generators (Java, Python, Rust) and maintains consistency with other parts of the API specification.

## Project Overview

Lance Namespace is an open specification for standardizing access to collections of Lance tables. The project provides:

- An OpenAPI specification for REST-based namespace operations
- Multi-language client and server implementations (Java, Python, Rust)
- Generated code from the OpenAPI specification
- Documentation and examples

## Architecture

The project follows a specification-driven approach:

1. **Core Specification**: OpenAPI spec at `docs/src/spec/rest.yaml` defines all operations
2. **Generated Code**: Client and server code is generated from the spec using `openapi-generator-cli`
3. **Multi-language Support**: Java, Python, and Rust implementations are provided
4. **Documentation**: MkDocs-based documentation in `docs/` directory

### Key Components

- **Java Core**: `java/lance-namespace-core/` - Core Java interface and utilities
- **Java Adapter**: `java/lance-namespace-adapter/` - Spring Boot server adapter
- **Generated Clients**: Auto-generated clients for each language
- **Generated Servers**: Auto-generated Spring Boot server stub

## Development Commands

### Project-wide Commands (from root)
```bash
# Lint the OpenAPI specification
make lint

# Generate all clients and servers
make gen

# Build all components
make build

# Clean all generated code
make clean
```

### Language-specific Commands

#### Java (from `java/` directory)
```bash
# Generate Java clients and servers
make gen

# Build with Maven (includes linting with Spotless)
make build

# Build specific components
make build-java-core
make build-java-adapter
```

#### Python (from `python/` directory)
```bash
# Generate Python client
make gen

# Build and test with Poetry
make build
```

#### Rust (from `rust/` directory)
```bash
# Generate Rust client
make gen

# Build and test with Cargo
make build
```

## Code Generation Workflow

1. All client and server code is generated from `docs/src/spec/rest.yaml`
2. Generated code is placed in respective language directories
3. Build process includes generation, linting, and testing
4. Some generated files are cleaned up post-generation (metadata, git files)

## Testing

- **Java**: Tests run via Maven (`./mvnw install`)
- **Python**: Tests run via Poetry (`poetry run pytest`)
- **Rust**: Tests run via Cargo (`cargo test`)

## Key Files

- `docs/src/spec/rest.yaml`: OpenAPI specification (source of truth)
- `java/lance-namespace-core/src/main/java/com/lancedb/lance/namespace/LanceNamespace.java`: Core Java interface
- `java/lance-namespace-adapter/`: Spring Boot server implementation
- Language-specific Makefiles for build automation