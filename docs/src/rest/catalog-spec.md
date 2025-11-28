# Lance REST Namespace Spec

In an enterprise environment, typically there is a requirement to store tables in a metadata service
for more advanced governance features around access control, auditing, lineage tracking, etc.
**Lance REST Namespace** is an OpenAPI protocol that enables reading, writing and managing Lance tables
by connecting those metadata services or building a custom metadata server in a standardized way.
The REST server definition can be found in the [OpenAPI specification](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/lance-format/lance-namespace/refs/heads/main/docs/src/rest.yaml).

## Duality with Client-Side Access Spec

The Lance Namespace client-side access spec defines request and response models using OpenAPI.
The REST namespace spec leverages this fact — the REST API is largely identical to the client-side access spec,
with the request and response schemas directly used as HTTP request and response bodies.

This duality minimizes data conversion between client and server:
a client can serialize its request model directly to JSON for the HTTP body,
and deserialize the HTTP response body directly into the response model.

There are a few exceptions where the REST spec diverges from the client-side access spec:

- **Arrow IPC data operations**: For operations like `InsertIntoTable`, `CreateTable`, `MergeInsertIntoTable`,
  the HTTP request body is used for transmitting Arrow IPC binary data.
  In these cases, the operation request fields are transmitted through query parameters instead.
- **Pagination parameters**: For list operations like `ListNamespaces`, `ListTables`, `ListTableTags`,
  pagination tokens and limits may be passed as query parameters
  for easier URL construction and caching.

These non-standard operations are documented in the [Non-Standard Operations](#non-standard-operations) section below.

## Configuration

The Lance REST namespace accepts the following configuration properties:

| Property    | Required | Description                                                            | Default | Example                           |
|-------------|----------|------------------------------------------------------------------------|---------|-----------------------------------|
| `uri`       | Yes      | The URI endpoint for the REST API                                      |         | `https://api.example.com/lance`   |
| `delimiter` | No       | The delimiter used to parse object string identifiers in REST routes   | `$`     | `::`, `__delim__`                 |
| `headers.*` | No       | Additional headers to send with every request                          |         | `headers.Authorization=Bearer...` |

### Headers

Properties with the `headers.` prefix are passed as HTTP headers with every request to the REST server
after removing the prefix. For example, `headers.Authorization` becomes the `Authorization` header.

Common header configurations include:
- `headers.Authorization`: Authentication tokens (Bearer, Basic, etc.)
- `headers.X-API-Key`: API key authentication
- `headers.X-Request-ID`: Request tracking

## REST Routes

The REST route for an operation typically follows the pattern of `POST /<version>/<object>/{id}/<action>`,
for example `POST /v1/namespace/{id}/list` for `ListNamespace`.
The request and response schemas are used as the actual request and response of the route.

The key design principle of the REST route is that all the necessary information for a reverse proxy
(e.g. load balancing, authN, authZ) should be available for access without the need to deserialize request body.

For routes that involve multiple objects, all related objects should be present in the route.
For example, the route for `RenameTable` is thus `POST /v1/table/{from_id}/rename/to/{to_id}`.

## Standard Operations

Standard operations should take the same request and return the same response as any other implementation.

The information in the route could also present in the request body.
When the information in the route and request body both present but do not match, the server must throw a 400 Bad Request error.
When the information in the request body is missing, the server must use the information in the route instead.

## Non-Standard Operations

For request and response that cannot be simply described as a JSON object
the REST server needs to perform special handling to describe equivalent information through path parameters,
query parameters and headers.

### ListNamespaces

**Route:** `GET /v1/namespace/{id}/list`

Uses GET without a request body. Pagination parameters are passed as query parameters.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `page_token` | `page_token` | Query parameter |
| `limit` | `limit` | Query parameter |

### ListTables

**Route:** `GET /v1/namespace/{id}/table/list`

Uses GET without a request body. Pagination parameters are passed as query parameters.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `page_token` | `page_token` | Query parameter |
| `limit` | `limit` | Query parameter |

### ListTableTags

**Route:** `GET /v1/table/{id}/tags/list`

Uses GET without a request body. Pagination parameters are passed as query parameters.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `page_token` | `page_token` | Query parameter |
| `limit` | `limit` | Query parameter |

### CreateTable

**Route:** `POST /v1/table/{id}/create`

**Content-Type:** `application/vnd.apache.arrow.stream`

The request body contains Arrow IPC stream data. The table schema is derived from the Arrow stream schema.
If the stream is empty, an empty table is created.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `mode` | `mode` | Query parameter |
| `location` | `x-lance-table-location` | Header |
| `properties` | `x-lance-table-properties` | Header (JSON-encoded string map) |
| `data` | Request body | Body (Arrow IPC stream) |

### InsertIntoTable

**Route:** `POST /v1/table/{id}/insert`

**Content-Type:** `application/vnd.apache.arrow.stream`

The request body contains Arrow IPC stream data with records to insert.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `mode` | `mode` | Query parameter (`append` or `overwrite`, default: `append`) |
| `data` | Request body | Body (Arrow IPC stream) |

### MergeInsertIntoTable

**Route:** `POST /v1/table/{id}/merge_insert`

**Content-Type:** `application/vnd.apache.arrow.stream`

The request body contains Arrow IPC stream data. Performs a merge insert (upsert) operation
that updates existing rows based on a matching column and inserts new rows that don't match.

| Request Field | REST Form | Location |
|---------------|-----------|----------|
| `id` | `{id}` | Path parameter |
| `on` | `on` | Query parameter (required) |
| `when_matched_update_all` | `when_matched_update_all` | Query parameter (boolean) |
| `when_matched_update_all_filt` | `when_matched_update_all_filt` | Query parameter (SQL expression) |
| `when_not_matched_insert_all` | `when_not_matched_insert_all` | Query parameter (boolean) |
| `when_not_matched_by_source_delete` | `when_not_matched_by_source_delete` | Query parameter (boolean) |
| `when_not_matched_by_source_delete_filt` | `when_not_matched_by_source_delete_filt` | Query parameter (SQL expression) |
| `data` | Request body | Body (Arrow IPC stream) |

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
