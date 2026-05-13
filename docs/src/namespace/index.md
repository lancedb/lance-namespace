# Lance Namespace Client Spec

The **Lance Namespace Client Spec** defines a standardized interface for catalog interactions such as table discovery, resolving table locations, and coordinating commits. It abstracts both the [Directory Catalog](../catalog/dir/index.md) (operating as an in-process library) and the [REST Catalog](../catalog/rest/index.md) (designed for client-server deployments) behind a single interface called `LanceNamespace`.

![Namespace Overview](../overview.png)

## Why "Namespace" Instead of "Catalog"?

We use the term **Namespace** rather than **Catalog** because we want a generic term that fits into any hierarchical structure. Different systems use different names for their organizational units:

| System                | Container Concepts                          |
|-----------------------|---------------------------------------------|
| Apache Hive           | Metastore → Database → Table                |
| Unity Catalog         | Metastore → Catalog → Schema → Table        |
| Apache Polaris        | Catalog → Namespace (arbitrary levels) → Table     |
| Directory Storage     | Root directory → Tables                     |

The Lance Namespace Client provides a **unified framework** across all of these systems. A "namespace" in Lance can represent a catalog, schema, metastore, database, metalake, or any other hierarchical container — the spec abstracts away these differences.

This further enables integration with external catalog specifications such as the Apache Iceberg REST Catalog, Apache Hive Metastore, Unity Catalog, and Apache Polaris Catalog.

## Examples

The following examples show how different catalog systems map to Lance Namespace.

### Directory (1-level)

The simplest case: tables directly in a storage directory, a common use case for ML/AI scientists:

| Directory       | Lance Namespace    |
|-----------------|--------------------|
| /data/          | Root Namespace     |
| └─ users.lance  | Table `["users"]`  |
| └─ orders.lance | Table `["orders"]` |

### Unity Catalog (3-level)

Unity Catalog uses a 3-level hierarchy under a metastore (one metastore per server):

| Unity Catalog                            | Lance Namespace                        |
|------------------------------------------|----------------------------------------|
| Root Metastore                           | Root Namespace                         |
| └─ Catalog "prod"                        | Namespace `["prod"]`                   |
| &emsp;&emsp;└─ Schema "analytics"        | Namespace `["prod", "analytics"]`      |
| &emsp;&emsp;&emsp;&emsp;└─ Table "users" | Table `["prod", "analytics", "users"]` |

### Apache Polaris (flexible levels)

Apache Polaris supports arbitrary namespace nesting:

| Polaris                                              | Lance Namespace                           |
|------------------------------------------------------|-------------------------------------------|
| Root Catalog                                         | Root Namespace                            |
| └─ Namespace "prod"                                  | Namespace `["prod"]`                      |
| &emsp;&emsp;└─ Namespace "team_a"                    | Namespace `["prod", "team_a"]`            |
| &emsp;&emsp;&emsp;&emsp;└─ Namespace "ml"            | Namespace `["prod", "team_a", "ml"]`      |
| &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;└─ Table "model" | Table `["prod", "team_a", "ml", "model"]` |

## Engine Interoperability

Because compute engines interact with catalogs through the Lance Namespace interface, they can work with Lance tables regardless of how the catalog is implemented or structured. Systems such as Apache DataFusion, Apache Spark, and Ray can interact with Lance tables through this interface, enabling distributed query execution, table maintenance, and multi-table workflows while remaining agnostic to the underlying catalog deployment.

For each programming language, a Lance Namespace Client provides a unified interface that compute engines can integrate against. For example:

- **Java SDK** (`org.lance:lance-namespace-core`): Enables engines like Apache Spark, Apache Flink, Apache Kafka, Trino, Presto, etc. to build their Lance connectors.
- **Python SDK** (`lance_namespace`): Enables frameworks like Ray, Dask, and MLflow to work with Lance tables.
- **Rust SDK** (`lance-namespace`): The core interface used by native implementations.

Each catalog spec has corresponding implementations in supported languages that fulfill the Namespace Client interface/trait.

![Namespace Java SDK Example](../java-sdk-example.png)
