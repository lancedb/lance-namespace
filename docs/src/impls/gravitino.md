# Lance Gravitino Namespace

**Apache Gravitino** is a high-performance, geo-distributed, and federated metadata lake. 
It manages metadata directly in different sources, types, and regions, providing unified metadata access for data and AI assets.

Lance at this moment does not provide a dedicated Gravitino namespace implementation. 
However, Gravitino provides a Hive MetaStore-compatible endpoint, 
which allows you to use Gravitino through the **Lance Hive Namespace**.

## Using Gravitino with Lance

To use Apache Gravitino with Lance, you can leverage Gravitino's [Apache Hive Catalog](https://gravitino.apache.org/docs/latest/apache-hive-catalog), 
which exposes a Hive MetaStore-compatible interface.

Simply configure your Lance Hive namespace to connect to Gravitino's Hive MetaStore endpoint. 
All the features and configurations of the [Lance Hive Namespace](hive.md) apply when using Gravitino.
