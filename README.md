# Lance Namespace Specification

**Lance Namespace Specification** is an open specification on top of the storage-based Lance data format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS), Apache Gravitino, Unity Catalog, etc.
should store and use Lance tables, as well as how ML/AI tools and analytics compute engines should integrate with Lance tables.

## Repository structure

| Directory                                                                          | Description                                                |
|------------------------------------------------------------------------------------|------------------------------------------------------------|
| [spec](./spec)                                                                     | Lance Namespace Specification                              |
| [rust/lance-namespace-reqwest-client](./rust/lance-namespace-reqwest-client)       | Generated Rust reqwest client for Lance REST Namespace     |
| [python/lance_namespace_urllib3_client](./python/lance_namespace_urllib3_client)   | Generated Python urllib3 client for Lance REST Namespace   |
| [java/lance-namespace-apache-client](./java/lance-namespace-apache-client)         | Generated Java Apache HTTP client for Lance REST Namespace |
| [java/lance-namespace-springboot-server](./java/lance-namespace-springboot-server) | Generated Java SpringBoot server for Lance REST Namespace  |

## Development Guide

### Install OpenAPI Generator

We use [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator)
to generate various clients and servers for the namespace specification.
We recommend installing the tool through [pip](https://pypi.org/project/openapi-generator-cli/)
for consistent experience across platforms.
First time setup of virtual environment and installation:

```bash
python3 -m venv .env
source .env/bin/activate
pip install -r requirements.txt
```

### Install Poetry for Python

If you want to build the entire project, or develop python specifically,
you need to install [poetry](https://python-poetry.org/).
To quickly install it in your virtual environment:

```bash
pip install -r python/requirements.txt
```

### Lint
To ensure the OpenAPI definition is valid, you can use the lint command to check it.

```bash
make lint
```

### Build

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
