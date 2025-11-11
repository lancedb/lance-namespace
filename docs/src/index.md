# Lance Namespace Spec

**Lance Namespace** is an open specification on top of the storage-based Lance table and file format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS),
Apache Gravitino, Unity Catalog, etc. should store and use Lance tables, 
as well as how ML/AI tools and analytics compute engines should integrate with Lance tables.

## Why _Namespace_ not _Catalog_?

The specification is called "Namespace" rather than "Catalog" because there are already many open catalog specifications and catalog services in the market today.
The goal of Lance Namespace is not to create yet another catalog spec, but to provide a consistent abstraction that adapts to all of them easily, so that users can choose to use any catalog to store and use Lance tables.
Namespace can mean catalog, schema, metastore, database, metalake, or any other organizational concept.
Lance Namespace provides a consistent interface to convert between different catalog specifications and map them into the connector interfaces of various compute engines.
