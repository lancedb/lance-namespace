# Lance Namespace

**Lance Namespace** is an open specification for describing access and operations against a collection of tables in a multimodal lakehouse.
The spec provides a unified model for table-related objects, their relationships within a hierarchy,
and the operations available on these objects — enabling integration with metadata services and compute engines alike.

## Spec Components

The Lance Namespace spec consists of three main parts:

1. **[Client Spec](docs/src/client/index.md)**: A consistent abstraction that adapts to various catalog specs,
   allowing users to choose any catalog to store and use tables. This is the core reason why we call it
   "Namespace" rather than "Catalog" — namespace can mean catalog, schema, metastore, database, metalake, etc.,
   and the spec provides a unified interface across all of them.

2. **Native Catalog Specs**: Natively maintained catalog specs that are compliant with the Lance Namespace client spec:
    - **Directory Namespace Spec**: A storage-only catalog spec that requires no external metadata service dependencies —
      tables are organized directly on storage (local filesystem, S3, GCS, Azure, etc.)
    - **REST Namespace Spec**: A REST-based catalog spec ideal for data infrastructure teams that want to develop
      their own custom handling in their specific enterprise environments.

3. **Implementation Specs**: Defines how a given catalog spec integrates with the client spec.
   It details how an object in a Lance Namespace maps to an object in the specific catalog spec,
   and how each operation in Lance Namespace is fulfilled by the catalog spec.
   The implementation specs for Directory and REST namespaces are part of Lance Namespace spec.
   Implementation specs for other catalog specs
   (e.g. Apache Polaris, Unity Catalog, Apache Hive Metastore, Apache Iceberg REST Catalog)
   are considered integrations — anyone can provide additional implementation specs outside Lance Namespace,
   and they can be owned by external parties without needing to go through the Lance community voting process to be adopted.

## Language Bindings

For each programming language, a Lance Namespace Client SDK provides a unified interface
that compute engines can integrate against:

| Language | Package | Description |
|----------|---------|-------------|
| Python   | `lance-namespace` | Core interface and connect functionality |
| Java     | `org.lance:lance-namespace-core` | Core interface for Spark, Flink, Kafka, Trino, Presto |
| Rust     | `lance-namespace` | Core interface (in [lance-format/lance](https://github.com/lance-format/lance)) |

## Resources

- **Documentation**: [lance.org/format/namespace](https://lance.org/format/namespace)
- **Contributing**: [CONTRIBUTING.md](CONTRIBUTING.md)
- **Integrations**: [lance-format/lance-namespace-impls](https://github.com/lance-format/lance-namespace-impls)
