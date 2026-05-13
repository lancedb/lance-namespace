# Lance Catalog Specs

A **catalog** manages collections of tables and provides table discovery, management, and transactional coordination. Catalog implementations vary widely across deployments, ranging from lightweight environments to enterprise platforms integrating with authorization systems or metadata services such as Apache Hive metastores.

To support this range of environments, Lance provides two catalog approaches:

## Directory Catalog

The **[Directory Catalog](dir/index.md)** is a storage-native catalog format that requires only a filesystem or object store — no additional services are needed. This makes it suitable for lightweight deployments, or even embedded in-process databases.

Key characteristics:

- **Zero infrastructure**: Requires only storage (local filesystem, S3, GCS, Azure, etc.)
- **Transactional guarantees**: Catalog metadata is stored as a Lance table, inheriting transactional semantics, snapshot isolation, and schema evolution guarantees
- **Simple deployment**: Ideal for ML/AI workloads that favor minimal operational dependencies

## REST Catalog

The **[REST Catalog](rest/index.md)** is an OpenAPI-based protocol that enables reading, writing, and managing Lance tables through a REST API. This is ideal for enterprise environments that require integration with existing governance, access control, and compliance systems.

Key characteristics:

- **Enterprise integration**: Connect to existing metadata services and authorization systems
- **Standardized API**: OpenAPI specification enables consistent client/server implementations
- **External manifest store**: Table version management APIs can act as an external manifest store for governance policies

## Supported Catalogs

Beyond the natively maintained catalog specs, Lance supports integration with external catalog systems through the [Namespace Client Spec](../namespace/index.md). Namespace Client implementation specs for systems like Apache Polaris, Unity Catalog, Apache Hive Metastore, and Apache Iceberg REST Catalog are maintained separately and can be found in the [Supported Catalogs](../namespace/supported-catalogs/index.md) section.
