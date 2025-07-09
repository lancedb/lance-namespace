# Lance Iceberg Namespace

**Lance Iceberg Namespace** is an implementation using Apache Iceberg REST Catalog (IRC).
For more details about IRC, please read the [IRC Specification](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml).

!!! note

    This implementation is designed against the IRC spec as of Iceberg release version 1.9.0.

## Namespace Mapping

An IRC server can be viewed as the root Lance namespace.
The Iceberg multi-level namespaces map to the multi-level child namespaces.
Whether the namespace is leveled and the number of levels depend on the specific IRC provider.

## Table Definition

A Lance table should appear as a table object in IRC in the shape of Iceberg [TableMetadata](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2482),
with the following requirements:

1. the [`location`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2494) must point to the root location of the Lance table
2. the [`properties`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2499) must follow:
    1. there is a key `table_type` set to `lance` (case insensitive)
    2. there is a key `managed_by` set to either `storage` or `impl` (case insensitive). If not set, default to `storage`
3. the [`current-snapshot-id`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2535) is set to the latest numeric version number of the table. This field will only be respected if `managed_by=impl`

When a user performs a `LoadTable`(https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L923) API call to retrieve the table metadata, 
the server must not return a [`metadata-location`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L3260)
in the `LoadTableResponse`(https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L4669).

## Requirement for Implementation Managed Table

An update to the implementation managed table must go through IRC [UpdateTable](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L997) API 
or [CommitTransaction](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L1336) API
with a requirement that the [`assert-ref-snapshot-id`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L3051) is the current Lance table version.
If the commit fails due to unresolvable concurrent commits, the IRC server must fail with `409 Conflict` according to the IRC spec.

## Using Lance Table in IRC with Iceberg Tooling

In order to use the table with Iceberg tooling (e.g. `pyiceberg`), the implementation can optionally set the following
in Iceberg [TableMetadata](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2482):

1. there is at least one schema in the list of [`schemas`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2504)
    1. the schema reflects the latest schema of the Lance table
    2. the schema has ID `1`
    3. the data type conversion follows Apache Arrow to Apache Iceberg data type conversion.
2. the [`current-schema-id`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2508C9-L2508C26) is set to `1`
3. there is at least one snapshot in the list of [`snapshots`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2529).
    1. the snapshot should have [`snapshot-id`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2399) set to the latest numeric version number of the table.
4. there is at least one snapshot log in the list of [`snapshot-log`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2542)
    1. the snapshot log should have [`snapshot-id`](https://github.com/apache/iceberg/blob/apache-iceberg-1.9.0/open-api/rest-catalog-open-api.yaml#L2461) set to the latest numeric version number of the table.
