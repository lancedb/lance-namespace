# Lance Directory Namespace

**Lance directory namespace** is a lightweight and simple 1-level Lance namespace that only contains a list of tables.
People can easily get started with creating and using Lance tables directly on top of any
local or remote storage system with a Lance directory namespace.

A directory namespace maps to a directory on storage, we call such directory a **namespace directory**.
A Lance table corresponds to a subdirectory in the namespace directory that has the format `<table_name>.lance`.
We call such a subdirectory **table directory**.
Consider the following example namespace directory layout:

```
.
└── /my/dir1/
    ├── table1.lance/
    │   ├── data/
    │   │   ├── 0aa36d91-8293-406b-958c-faf9e7547938.lance
    │   │   └── ed7af55d-b064-4442-bcb5-47b524e98d0e.lance
    │   ├── _versions/
    │   │   └── 9223372036854775707.manifest
    │   └── _indices/
    │       └── 85814508-ed9a-41f2-b939-2050bb7a0ed5-fts/
    │           └── index.idx 
    ├── table2.lance
    └── table3.lance
```

This describes a Lance directory namespace with the namespace directory at `/my/dir1/`.
It contains tables `table1`, `table2`, `table3` sitting at table directories
`/my/dirs/table1.lance`, `/my/dirs/table2.lance`, `/my/dirs/table3.lance` respectively.

## Configuration

The Lance directory namespace accepts the following configuration properties:

| Property      | Required | Description                                                 | Default                   | Example                         |
|---------------|----------|-------------------------------------------------------------|---------------------------|---------------------------------|
| `root`        | No       | The root directory of the namespace where tables are stored | Current working directory | `/my/dir`, `s3://bucket/prefix` |
| `storage.*`   | No       | Storage-specific configuration options                      |                           | `storage.region=us-west-2`      |

### Root Path

There are 3 ways to specify a root path:

1. **URI**: a URI that follows the [RFC 3986 specification](https://datatracker.ietf.org/doc/html/rfc3986), e.g. `s3://mu-bucket/prefix`.
2. **Absolute POSIX storage path**: an absolute file path in a POSIX standard storage, e.g. `/my/dir`.
3. **Relative POSIX storage path**: a relative file path in a POSIX standard storage, e.g. `my/dir2`, `./my/dir3`.
   The absolute path of the root should be derived from the current working directory.

### Storage Options

Properties with the `storage.` prefix are passed directly to the underlying OpenDAL storage system
after removing the prefix. For example, `storage.region` becomes `region` when passed to the storage layer.
Please visit [Apache OpenDAL](https://opendal.apache.org) for more details.

## Table Existence

A table exists in a Lance directory namespace if a table directory of the specific name exists
**and** contains a non-empty `_versions` subdirectory.

When checking if a specific table exists or deciding if a table should be listed, 
the operation must list objects with the `_versions/` prefix and checks if any objects exist.
If the directory exists but there is no file in the directory, it should be treated as non-existent.