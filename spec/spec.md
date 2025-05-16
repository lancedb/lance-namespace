# Lance Namespace Specification

**Lance Namespace Specification** is an open specification on top of the storage-based Lance data format
to standardize access to a collection of Lance tables (a.k.a. Lance datasets).
It describes how a metadata service like Apache Hive MetaStore (HMS), Apache Gravitino, Unity Catalog, etc.
should store and use Lance tables, as well as how ML/AI tools and analytics compute engines
(will together be called _"tools"_ in this document) should integrate with Lance tables.

## Namespace Concepts

### Namespace Definition

A Lance namespace is a centralized repository for discovering, organizing, and managing Lance tables.
It can either contain a collection of tables, or a collection of Lance namespaces recursively.
It is designed to encapsulates concepts including namespace, metastore, database, schema, etc.
that frequently appear in other similar data systems to allow easy integration with any system of any type of object hierarchy.

Here is an example layout of a Lance namespace:

![Lance namespace layout](./layout.png)

### Parent & Child

We use the term **parent** and **child** to describe relationship between 2 objects.
If namespace A directly contains B, then A is the parent namespace of B, i.e. B is a child of A.
For examples:

- Namespace `ns1` contains a **child namespace** `ns4`. i.e. `ns1` is the **parent namespace** of `ns4`.
- Namespace `ns2` contains a **child table** `t2`, i.e. `t2` belongs to **parent namespace** `ns2`.

### Root Namespace

A root namespace is a namespace that has no parent.
The root namespace is assumed to always exist and is ready to be connected to by a tool to explore objects in the namespace.
The lifecycle management (e.g. creation, deletion) of the root namespace is out of scope of this specification.

### Object Name

The **name** of an object is a string that uniquely identifies the object within the parent namespace it belongs to.
The name of any object must be unique among all other objects that share the same parent namespace.
For examples:

- `cat2`, `cat3` and `cat4` are all unique names under the root namespace
- `t3` and `t4` are both unique names under `cat4`

### Object Identifier

The **identifier** of an object uniquely identifies the object within the root namespace it belongs to.
The identifier of any object must be unique among all other objects that share the same root namespace.

Based on the uniqueness property of an object name within its parent namespace,
an object identifier is the list of object names starting from (not including) the root namespace to (including) the object itself.
This is also called an **list identifier**.
For examples:

- the list identifier of `cat5` is `[cat2, cat5]`
- the list identifier of `t1` is `[cat2, cat5, t1]`

The dot (`.`) symbol is typically used as the delimiter to join all the names to form an **string identifier**,
but other symbols could also be used if dot is used in the object name.
For examples:

- the string identifier of `cat5` is `cat2.cat5`
- the string identifier of `t1` is `cat2.cat5.t1`
- the string identifier of `t3` is `cat4$t3` when using delimiter `$`

### Name and Identifier for Root Namespace

The root namespace itself has no name or identifier.
When represented in code, its name and string identifier is represented by an empty or null string,
and its list identifier is represented by an empty or null list.

The actual name and identifier of the root namespace is typically
assigned by users through some configuration when used in a tool.
For example, a root namespace can be called `cat1` in Ray, but called `cat2` in Apache Spark,
and they are both configured to connect to the same root namespace.

## Namespace Operations

The Lance Namespace Specification defines a list of operations that can be performed against any Lance namespace:

| Operation ID    | Description                                                                                                        |
|-----------------|--------------------------------------------------------------------------------------------------------------------|
| ListOperations  | List the operations that are supported by this Lance namespace                                                     |
| ListNamespaces  | List the names of child namespaces in the parent namespace, or root namespace if parent namespace is not specified |
| NamespaceExists | Check if a namespace exists                                                                                        |
| GetNamespace    | Describe information of a namespace                                                                                |
| CreateNamespace | Create a new namespace under a parent namespace, or root namespace if parent namespace is not specified            |
| AlterNamespace  | Alter information of a namespace                                                                                   |
| DropNamespace   | Drop a namespace from its a parent namespace, or root namespace if parent namespace is not specified               |
| ListTables      | List the names of tables in a namespace                                                                            |
| TableExists     | Check if a table exists                                                                                            |
| GetTable        | Describe information of a Lance table in the namespace                                                             |
| CreateTable     | Create a new Lance table under a namespace                                                                         |
| RegisterTable   | Register an existing table at a given storage location to a namespace                                              |
| AlterTable      | Alter information of a Lance table                                                                                 |
| DropTable       | Drop a table from its namespace                                                                                    |

### Operation Versioning

There is no versioning concept within an operation. When backwards incompatible change is introduced,
a new operation needs to be created, with a naming convention of `<operationId>V<version>`,
for example `ListNamespacesV2`, `GetTableV3`, etc.

### Operation Request and Response Schema

Each operation has a request and response.
The request and response schema is defined using JSON schema in the `components/schemas` section of [rest.yaml](./rest.yaml).

## Namespace Implementations

A **Lance Namespace Implementation** is an implementation of the Lance namespace specification,
more specifically:

1. It satisfies all the Lance namespace definitions and concepts.
2. It declares and implements a list of supported Lance namespace operations.

### Implementation and Storage

Except for any storage-only implementation (e.g. [Lance directory namespace](#lance-directory-namespace)),
a Lance table exists both in the storage and the implementation.
For example, a Lance table exists both in HMS and storage for the [Lance HMS namespace](#lance-hms-namespace).
There are 2 possible ways to manage a Lance table under such setting.
A Lance namespace implementation can choose to support one or both:

#### Implementation Managed Table

A implementation managed Lance table is a table that is fully managed by the Lance namespace implementation.
The implementation must maintain information about the latest version of the Lance table.
Any modifications to the table must happen through the implementation.
If a user directly modifies the underlying table in the storage bypassing the implementation,
the implementation must not reflect the changes in the table to the namespace users.

This mode ensures the namespace service is aware of all activities in the table,
and can thus fully enforce any governance and management features for the table.

#### Storage Managed Table

A storage managed Lance table is a table that is fully managed by the storage
with a metadata definition in the Lance namespace implementation.
The implementation only contains information about the table directory location.
It is expected that a tool finds the latest version of the Lance table based on the contents
in the table directory according to the Lance format specification.
A modification to the table can happen either directly against the storage,
or happen as a request to the implementation, where the implementation is responsible for applying the corresponding
change to the underlying storage according to the Lance format specification.

This mode is more flexible for real world ML/AI workflows
but the implementation loses full visibility and control over the actions performed against the table,
so it will be harder to enforce any governance and management features for storage managed tables.

## Natively Supported Implementations

A natively supported Lance namespace implementation is one that is maintained in this `lance-namespace` repository.
Any implementation that is outside the repository is considered as a third-party implementation.

There are currently the following natively supported Lance namespace implementations:

### Lance Directory Namespace

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

#### Directory Path

There are 3 ways to specify a directory path:

1. **URI**: a URI that follows the [RFC 3986 specification](https://datatracker.ietf.org/doc/html/rfc3986), e.g. `s3://mu-bucket/prefix`.
2. **Absolute POSIX storage path**: an absolute file path in a POSIX standard storage, e.g. `/my/dir`.
3. **Relative POSIX storage path**: a relative file path in a POSIX standard storage, e.g. `my/dir2`, `./my/dir3`.
   The absolute path of the directory should be based on the current directory of the running process.

#### Table Existence

A table exists in a Lance directory namespace if a table directory of the specific name exists.
This is true even if the directory is empty or the contents in the directory does not follow the Lance table format spec.
For such cases, an operation that lists all tables in the directory should show the specific table,
and an operation that checks if a table exists should return true.
However, an operation that loads the Lance table metadata should fail with error
indicating the content in the folder is not compliant with the Lance table format spec.

### Lance HMS Namespace

Lance HMS Namespace directly integrates with HMS to offer a 2-level Lance namespace experience.
The root namespace maps to the entire HMS, which has HMS databases as child namespaces.

### Lance REST Namespace

In an enterprise environment, typically there is a requirement to store tables in a metadata service
for more advanced governance features around access control, auditing, lineage tracking, etc.
**Lance REST Namespace** is an OpenAPI protocol that enables reading, writing and managing Lance tables
by connecting those metadata services or building a custom metadata server in a standardized way.
The detailed OpenAPI specification content can be found in [rest.yaml](./rest.yaml).

#### Routes

Each Lance namespace operation is available at route `POST /<operationId>`,
for example `POST /ListNamespaces`, `POST /GetTable`.

#### Namespace Server and Adapter

Any REST HTTP server that implements this OpenAPI protocol is called a **Lance Namespace server**.
If you are a metadata service provider that is building a custom implementation of Lance namespace,
building a REST server gives you standardized integration to Lance
without the need to worry about tool support and
continuously distribute newer library versions compared to using an implementation.

If the main purpose of this server is to be a proxy on top of an existing metadata service,
converting back and forth between Lance REST API models and native API models of the metadata service,
then this Lance namespace server is called a **Lance Namespace adapter**.

#### Choosing between an Adapter vs an Implementation

Any adapter can always be directly a Lance namespace implementation bypassing the REST server,
and vise versa. In fact, an implementation is basically the backend of an adapter.
For example, we natively support a Lance HMS Namespace implementation,
as well as a Lance namespace adapter for HMS by using the HMS Namespace implementation to fulfill requests in the Lance REST server.

If you are considering between a Lance namespace adapter vs implementation to build or use in your environment,
here are some criteria to consider:

1. **Multi-Language Feasibility & Maintenance Cost**: If you want a single strategy that works across all Lance language bindings, an adapter is preferred.
   Sometimes it is not even possible for an integration to go with the implementation approach since it cannot support all the languages.
   Sometimes an integration is popular or important enough that it is viable to build an implementation and maintain one library per language.
2. **Tooling Support**: each tool needs to declare the Lance namespace implementations it supports.
   That means there will be a preference for tools to always support a REST namespace,
   but it might not always support a specific implementation. This favors the adapter approach.
3. **Security**: if you have security concerns about the adapter being a man-in-the-middle, you should choose an implementation
4. **Performance**: after all, adapter adds one layer of indirection and is thus not the most performant solution.
   If you are performance sensitive, you should choose an implementation

## Tool Integration Guidelines

The following are guidelines for tools to integrate with Lance namespaces.
Note that these are recommendations rather than hard requirements.
The goal of these guidelines is to offer a consistent user experience across different tools.

### Configuring the Implementation

We recommend tools to offer a `impl` config key that allows user to configure the Namespace implementation.
We recommend the following values for the natively supported implementations:

| Implementation        | `impl` Value |
|-----------------------|--------------|
| Directory             | dir          |
| Apache Hive MetaStore | hive         |
| REST                  | rest         |

### Configuring an Implementation Details

We recommend tools to offer implementation specific configurations using the `impl` value as the config key prefix.
For example, all config keys for the directory namespace should start with `dir.`, like `dir.path`.