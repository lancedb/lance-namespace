# Lance Directory Namespace

**Lance directory namespace** is a lightweight and simple 1-level Lance namespace that only contains a list of tables.
People can easily get started with creating and using Lance tables directly on top of any
local or remote storage system with a Lance directory namespace.

A directory namespace maps to a directory on storage, we call such directory a **namespace directory**.
A Lance table corresponds to a subdirectory in the namespace directory.
We call such a subdirectories **table directory**.
Consider the following example namespace directory layout:

```
.
└── /my/dir1/
    ├── table1/
    │   ├── data/
    │   │   ├── 0aa36d91-8293-406b-958c-faf9e7547938.lance
    │   │   └── ed7af55d-b064-4442-bcb5-47b524e98d0e.lance
    │   ├── _versions/
    │   │   └── 9223372036854775707.manifest
    │   ├── _indices/
    │   │   └── 85814508-ed9a-41f2-b939-2050bb7a0ed5-fts/
    │   │       └── index.idx
    │   └── _deletions/
    │       └── 75c69434-cde5-4c80-9fe1-e79a6d952fbf.bin
    ├── table2
    └── table3
```

This describes a Lance directory namespace with the namespace directory at `/my/dir1/`.
It contains tables `table1`, `table2`, `table3` sitting at table directories
`/my/dirs/table1`, `/my/dirs/table2`, `/my/dirs/table3` respectively.

## Directory Path

There are 3 ways to specify a directory path:

1. **URI**: a URI that follows the [RFC 3986 specification](https://datatracker.ietf.org/doc/html/rfc3986), e.g. `s3://mu-bucket/prefix`.
2. **Absolute POSIX storage path**: an absolute file path in a POSIX standard storage, e.g. `/my/dir`.
3. **Relative POSIX storage path**: a relative file path in a POSIX standard storage, e.g. `my/dir2`, `./my/dir3`.
   The absolute path of the directory should be based on the current directory of the running process.

## Table Existence and Listing

A table exists in a Lance directory namespace if a table directory of the specific name exists
**and** contains a `_versions` subdirectory, which indicates it is a valid Lance dataset.

When checking if a specific table exists:
- The operation should return true only if the directory exists and contains a `_versions` subdirectory
- If the directory exists but lacks `_versions`, it should be treated as non-existent

When listing tables in a namespace:
- Only directories that contain a `_versions` subdirectory should be included in the list
- Empty directories or directories without `_versions` should not be listed as tables
- This ensures that only valid Lance datasets are shown to users

## Configuration

The Lance directory namespace accepts the following configuration properties:

| Property    | Required | Description                                                 | Default                   | Example                         |
|-------------|----------|-------------------------------------------------------------|---------------------------|---------------------------------|
| `root`      | No       | The root directory of the namespace where tables are stored | Current working directory | `/my/dir`, `s3://bucket/prefix` |
| `storage.*` | No       | Storage-specific configuration options                      |                           | `storage.region=us-west-2`      |

### Storage Options

Properties with the `storage.` prefix are passed directly to the underlying OpenDAL storage system
after removing the prefix. For example, `storage.region` becomes `region` when passed to the storage layer.
Please visit [Apache OpenDAL](https://opendal.apache.org) for more details.