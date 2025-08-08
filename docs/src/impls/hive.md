# Lance Hive Namespace

**Lance Hive Namespace** is an implementation using Apache Hive MetaStore (HMS).
For more details about HMS, please read [HMS AdminManual 2.x](https://hive.apache.org/docs/latest/adminmanual-metastore-administration_27362076/) 
and [HMS AdminManual 3.x](https://hive.apache.org/docs/latest/adminmanual-metastore-3-0-administration_75978150/).

## Configuration

The Lance Hive namespace accepts the following configuration properties:

| Property           | Required | Description                                            | Default                   | Example                         |
|--------------------|----------|--------------------------------------------------------|---------------------------|---------------------------------|
| `client.pool-size` | No       | Size of the HMS client connection pool                 | 3                         | `5`                             |
| `root`             | No       | Storage root location of the lakehouse on Hive catalog | Current working directory | `/my/dir`, `s3://bucket/prefix` |
| `storage.*`        | No       | Additional storage configurations to access table      |                           | `storage.region=us-west-2`      |

### Version-specific Configuration

**Hive 2.x**: Uses the configuration properties listed above.

**Hive 3.x**: Uses the same configuration properties as Hive 2.x, with an additional catalog name constant (`hive`) for catalog-level operations. 

## Namespace Mapping

A HMS server can be viewed as the root Lance namespace.

For HMS 2.x and below, a database in HMS maps to the first level Lance namespace
to form a 2-level Lance namespace as a whole.

For HMS 3.x and above, a catalog in HMS maps to the first level Lance namespace,
and a database in HMS maps to the second level Lance namespace
to form a 3-level Lance namespace as a whole.

## Table Definition

A Lance table should appear as a [Table object](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L631) 
in HMS with the following requirements:

1. the [`tableType`](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L643) must be set as `EXTERNAL_TABLE` to indicate this is not a managed Hive table
2. the [`location`](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L467) in [`storageDescriptor`](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L638) must point to the root location of the Lance table
3. the [`parameters`](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L640) must follow:
    1. there is a key `table_type` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
    3. there is a key `version` set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl` 

## Requirement for Implementation Managed Table

An update to the implementation managed table must use Hive's atomic update feature ([HIVE-26882](https://issues.apache.org/jira/browse/HIVE-26882))
and use the `version` parameter value to perform conditional update through [alter_table_with_environment_context](https://github.com/apache/hive/blob/branch-4.0/standalone-metastore/metastore-common/src/main/thrift/hive_metastore.thrift#L2733)