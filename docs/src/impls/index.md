# Namespace Implementations

A **Lance Namespace Implementation** is an implementation of the Lance namespace specification,
more specifically:

1. It satisfies all the Lance namespace definitions and concepts.
2. It declares and implements a list of supported Lance namespace operations.

## Native Implementations

The following implementations are maintained in the [lance](https://github.com/lance-format/lance) repository:

- [REST Namespace](rest/index.md) - Lance REST Namespace implementation
- [Directory Namespace](dir.md) - Storage-only directory namespace

## Catalog Implementations

For implementations that integrate with external catalog systems (Hive, Glue, Unity, Polaris, etc.),
see the [lance-namespace-impls](https://github.com/lancedb/lance-namespace-impls) repository.
