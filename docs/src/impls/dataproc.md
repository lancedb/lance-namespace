# Lance Dataproc Namespace

**Google Dataproc Metastore** is a fully managed, 
highly available, autohealing, serverless metastore that runs on Google Cloud.

To use Google Dataproc Metastore with Lance, you can leverage Dataproc's [Hive metastore](https://cloud.google.com/dataproc-metastore/docs/hive-metastore),
which exposes a Hive MetaStore-compatible interface.

Simply configure your Lance Hive namespace to connect to Dataproc's Hive MetaStore endpoint.
All the features and configurations of the [Lance Hive Namespace](hive.md) apply when using Dataproc Metastore.
