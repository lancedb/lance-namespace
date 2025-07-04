# Contributing

## Lance Contributing Guide

This is a sub-project of the Lance project. 
Please read [its contributing guide](https://jackye1995.github.io/lance/community/contributing/) first. 

## Repository structure

| Component                    | Language | Path                                   | Description                                                |
|------------------------------|----------|----------------------------------------|------------------------------------------------------------|
| spec                         |          | docs/src/spec                          | Lance Namespace Specification                              |
| Rust Reqwest Client          | Rust     | rust/lance-namespace-reqwest-client    | Generated Rust reqwest client for Lance REST Namespace     |
| Python UrlLib3 Client        | Python   | python/lance_namespace_urllib3_client  | Generated Python urllib3 client for Lance REST Namespace   |
| Java Apache Client           | Java     | java/lance-namespace-apache-client     | Generated Java Apache HTTP client for Lance REST Namespace |
| Java Springboot Server       | Java     | java/lance-namespace-springboot-server | Generated Java SpringBoot server for Lance REST Namespace  |
| Java Lance Namespace Core    | Java     | java/lance-namespace-core              | Lance Namespace Java Core SDK                              |
| Java Lance Namespace Adapter | Java     | java/lance-namespace-adaptor           | Lance Namespace adapter server implementation              |

## Install OpenAPI Generator

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

## Install Poetry for Python

If you want to build the entire project, or develop python specifically,
you need to install [poetry](https://python-poetry.org/).
To quickly install it in your virtual environment:

```bash
pip install -r python/requirements.txt
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
