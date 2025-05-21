# Lance REST Namespace

In an enterprise environment, typically there is a requirement to store tables in a metadata service
for more advanced governance features around access control, auditing, lineage tracking, etc.
**Lance REST Namespace** is an OpenAPI protocol that enables reading, writing and managing Lance tables
by connecting those metadata services or building a custom metadata server in a standardized way.
The detailed OpenAPI specification content can be found in [rest.yaml](./rest.yaml).

## REST Routes

Each Lance namespace operation is available at route `POST /<operationId>`,
for example `POST /ListNamespaces`, `POST /GetTable`.

## Namespace Server and Adapter

Any REST HTTP server that implements this OpenAPI protocol is called a **Lance Namespace server**.
If you are a metadata service provider that is building a custom implementation of Lance namespace,
building a REST server gives you standardized integration to Lance
without the need to worry about tool support and
continuously distribute newer library versions compared to using an implementation.

If the main purpose of this server is to be a proxy on top of an existing metadata service,
converting back and forth between Lance REST API models and native API models of the metadata service,
then this Lance namespace server is called a **Lance Namespace adapter**.

## Choosing between an Adapter vs an Implementation

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
