# Contributing to Lance Namespace

## Repository structure

| Component                    | Language | Path                                   | Description                                                               |
|------------------------------|----------|----------------------------------------|---------------------------------------------------------------------------|
| spec                         |          | docs/src/spec                          | Lance Namespace Specification                                             |
| Rust Reqwest Client          | Rust     | rust/lance-namespace-reqwest-client    | Generated Rust reqwest client for Lance REST Namespace                    |
| Python UrlLib3 Client        | Python   | python/lance_namespace_urllib3_client  | Generated Python urllib3 client for Lance REST Namespace                  |
| Python Lance Namespace Core  | Python   | python/lance_namespace                 | Lance Namespace Python Core SDK                                           |
| Java Apache Client           | Java     | java/lance-namespace-apache-client     | Generated Java Apache HTTP client for Lance REST Namespace                |
| Java Springboot Server       | Java     | java/lance-namespace-springboot-server | Generated Java SpringBoot server for Lance REST Namespace                 |
| Java Lance Namespace Core    | Java     | java/lance-namespace-core              | Lance Namespace Java Core SDK                                             |
| Java Lance Namespace Adapter | Java     | java/lance-namespace-adaptor           | Lance Namespace adapter server implementation                             |
| Java Lance Namespace Hive    | Java     | java/lance-namespace-hive              | Java Lance Namespace Apache Hive Metastore Implementation                 |
| Java Lance Namespace Glue    | Java     | java/lance-namespace-glue              | Java Lance Namespace AWS Glue Data Catalog Implementation                 |
| Java Lance Namespace LanceDB | Java     | java/lance-namespace-lancedb           | Java Utilities to use Lance Namespace SDK with LanceDB Cloud & Enterprise |


## Install uv

We use [uv](https://docs.astral.sh/uv/getting-started/installation/) for development.
Make sure it is installed, and run:

```bash
uv sync --all-packages
```

## Lint

To ensure the OpenAPI definition is valid, you can use the lint command to check it.

```bash
make lint
```

## Build

There are 3 commands that is available at top level as well as inside each language folder:

- `make clean`: remove all codegen modules
- `make gen`: codegen and lint all modules (depends on `clean`)
- `make build`: build all modules (depends on `gen`)

You can also run `make <command>-<language>` to only run the command in the specific language, for example:

- `make gen-python`: codegen and lint all Python modules
- `make build-rust`: build all Rust modules

You can also run `make <command>-<language>-<module>` inside a language folder to run the command against a specific module, for example:

- `make gen-rust-reqwest-client`: codegen and lint the Rust reqwest client module
- `make build-java-springboot-server`: build the Java Spring Boot server module
