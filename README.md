# Lance Catalog

**Lance Catalog** is an OpenAPI specification on top of the storage-based Lance format.
It provides an integration point for catalog service like Apache Hive MetaStore (HMS), Apache Gravitino, etc. 
to store and use Lance tables. To integrate, the catalog service implements a **Lance Catalog Adapter**, 
which is a REST server that converts the Lance catalog requests to native requests against the catalog service.
Different tools can integrate with Lance Catalog using the generated OpenAPI clients in various languages,
and invoke operations in Lance Catalog to read, write and manage Lance tables in the integrated catalog services.

## Repository structure

| Directory                                                                      | Description                                         |
|--------------------------------------------------------------------------------|-----------------------------------------------------|
| [spec](./spec)                                                                 | Lance Catalog OpenAPI specification                 |
| [rust/lance-catalog-reqwest-client](./rust/lance-catalog-reqwest-client)       | Generated Rust reqwest client for Lance Catalog     |
| [java/lance-catalog-apache-client](./java/lance-catalog-apache-client)         | Generated Java Apache HTTP client for Lance Catalog |
| [java/lance-catalog-springboot-server](./java/lance-catalog-springboot-server) | Generated Java SpringBoot server for Lance          |

## Development Guide

## Install OpenAPI Generator

We use OpenAPI Generator to generate various clients and servers for the catalog specification.
We recommend installing the tool through pip for consistent experience across platforms.
First time setup of virtual environment and installation:

```bash
python3 -m venv .env
source .env/bin/activate
pip install -r requirements.txt
```

## Develop Rust

```bash
cd rust

# clean all existing generated modules
make clean

# clean, and then generate all clients and servers
make gen 

# clean, generate and build the modules 
make build
```

## Develop Java

```bash
cd java

# clean all existing generated modules
make clean

# clean, and then generate all clients and servers
make gen 

# clean, generate and build the modules 
make build
```
