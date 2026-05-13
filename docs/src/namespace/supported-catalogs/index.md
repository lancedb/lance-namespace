# Supported Catalogs

Beyond the natively maintained [Directory Catalog](../../catalog/dir/index.md) and [REST Catalog](../../catalog/rest/index.md) specifications, Lance supports integration with external catalog systems through the [Namespace Client Spec](../index.md).

## What are Supported Catalogs?

Supported catalogs are implementation specs for external catalog systems. They describe how a specific catalog system (such as Apache Polaris, Unity Catalog, or Apache Hive Metastore) integrates with Lance. Each implementation defines:

- How catalog objects map to Lance Namespace concepts
- How to identify Lance tables within the catalog
- How each Namespace Client operation is fulfilled by the catalog

## Available Catalogs

Implementation specs are maintained in the [lance-namespace-impls](https://github.com/lance-format/lance-namespace-impls) repository. Supported catalogs include:

- **Apache Polaris**: Integration with Polaris Catalog for multi-engine governance
- **Unity Catalog**: Integration with Databricks Unity Catalog
- **Apache Hive Metastore**: Integration with Hive Metastore for legacy warehouse compatibility
- **Apache Iceberg REST Catalog**: Integration with Iceberg's REST Catalog protocol
- **AWS Glue Data Catalog**: Integration with AWS Glue for cloud-native deployments

## Contributing

Catalog implementations can be owned by external parties without needing to go through the Lance community voting process to be adopted. Anyone can provide additional implementation specs outside the core Lance Namespace spec.

To contribute a new catalog implementation, follow the [Implementation Spec Template](template.md) which defines the standard structure for describing how a catalog system integrates with the Namespace Client.
