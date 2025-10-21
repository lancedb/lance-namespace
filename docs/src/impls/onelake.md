# Lance OneLake Namespace

**Microsoft OneLake** is a unified, logical data lake for Microsoft Fabric that provides a single SaaS experience and a tenant-wide store for data that serves both professional and citizen data integration needs.

Lance at this moment does not provide a dedicated OneLake namespace implementation.
However, OneLake provides a Unity Catalog-compatible endpoint through its Table APIs,
which allows you to use OneLake through the **Lance Unity Namespace**.

## Using OneLake with Lance

To use Microsoft OneLake with Lance, you can leverage OneLake's [Table APIs](https://learn.microsoft.com/en-us/fabric/onelake/table-apis/onelake-table-apis#delta-lake-rest-api-operations-on-onelake),
which expose a Unity Catalog-compatible interface.

Simply configure your Lance Unity namespace to connect to OneLake's Unity Catalog endpoint at:

```
https://onelake.table.fabric.microsoft.com/delta
```

All the features and configurations of the [Lance Unity Namespace](unity.md) apply when using OneLake.
