# Lance Namespace Specification

**Lance Namespace Specification** is an open specification on top of the storage-based Lance data format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS), Apache Gravitino, Unity Namespace, etc.
should store and use Lance tables, as well as how ML/AI tools and analytics compute engines should integrate with Lance tables.

## Repository structure

| Directory                                                                      | Description                                         |
|--------------------------------------------------------------------------------|-----------------------------------------------------|
| [spec](./spec)                                                                 | Lance Catalog OpenAPI specification                 |
| [rust/lance-catalog-reqwest-client](./rust/lance-catalog-reqwest-client)       | Generated Rust reqwest client for Lance Catalog     |
| [python/lance_catalog_urllib3_client](./python/lance_catalog_urllib3_client)   | Generated Python urllib3 client for Lance Catalog   |
| [java/lance-catalog-apache-client](./java/lance-catalog-apache-client)         | Generated Java Apache HTTP client for Lance Catalog |
| [java/lance-catalog-springboot-server](./java/lance-catalog-springboot-server) | Generated Java SpringBoot server for Lance          |

## Development Guide

### Install OpenAPI Generator

We use [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) 
to generate various clients and servers for the catalog specification.
We recommend installing the tool through [pip](https://pypi.org/project/openapi-generator-cli/) 
for consistent experience across platforms.
First time setup of virtual environment and installation:

```bash
python3 -m venv .env
source .env/bin/activate
pip install -r requirements.txt
```

### Develop Rust

```bash
cd rust

# clean all existing generated modules
make clean

# clean, and then generate all clients and servers
make gen 

# clean and generate the rust reqwest client
make gen-rust-reqwest-client

# clean, generate and build all clients and servers
make build
```

### Develop Python

We use [poetry](https://python-poetry.org/) for managing dependency of Python modules.
If you are already in a virtual environment for [OpenAPI Generator](#install-openapi-generator),
you can just run the following to install poetry in the same environment:

```bash
pip install -r python/requirements.txt
```

To run codegen and build actions:

```bash
cd python

# clean all existing generated modules
make clean

# clean, and then generate all clients and servers
make gen 

# clean and generate the python urllib3 client
make gen-python-urllib3-client

# clean, generate and build all clients and servers
make build
```

### Develop Java

```bash
cd java

# clean all existing generated modules
make clean

# clean, and then generate all clients and servers
make gen 

# clean and generate the Java Apache client
make gen-java-apache-client

# clean and generate the Java Spring Boot server
make gen-java-springboot-server

# clean, generate and build all clients and servers
make build
```
