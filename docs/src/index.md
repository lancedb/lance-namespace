# Introduction

![logo](./logo/wide.png)

## Lance Namespace Specification

**Lance Namespace Specification** is an open specification on top of the storage-based Lance data format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS), Apache Gravitino, Unity Catalog, etc.
should store and use Lance tables, as well as how ML/AI tools and analytics compute engines should integrate with Lance tables.

## Why _Namespace_ not _Catalog_?

There are many terms used to describe the concept of a container in database systems
— such as _namespace_, _catalog_, _schema_, _database_, _metastore_, and _metalake_. 
Among these, namespace and catalog have become the most prominent in modern lakehouse architectures.

The term catalog typically implies a hierarchical structure with at least two levels. 
For example, Apache Hive uses a catalog → database → table model, 
while Apache Iceberg’s REST catalog adopts a catalog → multi-level namespace → table hierarchy.

In contrast, the ML and AI communities tend to favor a flatter organizational model. 
It’s common to store datasets in simple directories 
and categorize them using flexible systems like tagging, rather than rigid hierarchies.

To better support this usage pattern, Lance adopts the term **_namespace_** to represent all container concepts 
— including what would traditionally be called a catalog. 
With the **Lance Namespace Specification**, we provide a flexible, multi-level namespace abstraction 
that allows users to structure and manage Lance datasets in ways that best align with their data organization strategies.