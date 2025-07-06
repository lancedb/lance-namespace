#

![logo](./logo/wide.png)

## Introduction

**Lance Namespace Specification** is an open specification on top of the storage-based Lance data format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS), Apache Gravitino, Unity Catalog, etc.
should store and use Lance tables, as well as how ML/AI tools and analytics compute engines should integrate with Lance tables.

## Why _Namespace_ not _Catalog_?

There are many equivalent terms that provides a container concept in a database system,
including _namespace_, _catalog_, _schema_, _database_, _metastore_, _metalake_, etc.
Namespace and catalog are the 2 most popular terms used in modern lakehouse systems.

Between namespace and catalog, catalog typically implies at least a 2-level hierarchy, 
such as `catalog -> database -> table` in Apache Hive MetaStore,
and `catalog -> multi-level namespace -> table` in Apache Iceberg REST catalog.

Lance is a format for ML and LLM use cases, and we observe a popularity for a 1-level hierarchy in the ML/AI community. 
People commonly just use a simple directory to store datasets,
and categorize them through mechanisms like tagging instead of organizing them into a fixed hierarchy.

To accommodate this architecture as a first-class citizen,
we decide to use the term **_namespace_** to represent all container concepts including a catalog.
By offering a multi-level namespace semantics on top of Lance through the Lance Namespace Specification, 
we are able to flexibly model against any data categorization strategy,
and allow users to store and manage Lance datasets in their system.