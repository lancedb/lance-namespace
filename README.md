# Lance Namespace

**Lance Namespace** is an open specification for describing access and operations against a collection of tables.
The spec provides a unified model for table-related objects, their relationships within a hierarchy,
and the operations available on these objects — enabling integration with metadata services and compute engines alike.

The Lance Namespace spec consists of three main parts:

1. **Client-Side Standardized Access Spec**: A consistent abstraction that adapts to various catalog specifications
   (e.g. Apache Gravitino, Apache Polaris, Unity Catalog, Apache Hive Metastore, Apache Iceberg REST Catalog),
   allowing users to choose any catalog to store and use tables.

2. **Directory Namespace Spec**: A natively maintained storage-only catalog spec that is compliant with the
   Lance Namespace client-side access spec. It requires no external metadata service — tables are organized directly
   on storage (local filesystem, S3, GCS, etc.) with metadata stored alongside the data.

3. **REST Namespace Spec**: A natively maintained REST-based catalog spec that is compliant with the Lance
   Namespace client-side access spec. It is suitable for teams that want to develop their own custom handling,
   ideal for adoption by data infrastructure teams in enterprise environments with high customization requirements.

For more details, please visit the [documentation website](https://lance.org/format/namespace).

For development setup and contribution guidelines, please see [CONTRIBUTING.md](CONTRIBUTING.md).
