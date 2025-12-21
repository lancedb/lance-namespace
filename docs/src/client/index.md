# Lance Namespace Client Spec

The **Lance Namespace Client Spec** defines a standardized framework for accessing and operating on a collection of tables
in a multimodal lakehouse. It provides a consistent abstraction that adapts to various catalog specs,
allowing users to choose any catalog to store and use Lance tables.

## Why "Namespace" Instead of "Catalog"?

We use the term **Namespace** rather than **Catalog** because we want a generic term that fits into
any hierarchical structure. Different systems use different names for their organizational units.
The Lance Namespace spec provides a **unified framework** across all of these systems.
A "namespace" in Lance can represent a catalog, schema, metastore, database, metalake, or any other
hierarchical container — the spec abstracts away these differences.

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
