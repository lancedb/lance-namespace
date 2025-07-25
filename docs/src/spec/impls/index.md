# Namespace Implementations

A **Lance Namespace Implementation** is an implementation of the Lance namespace specification,
more specifically:

1. It satisfies all the Lance namespace definitions and concepts.
2. It declares and implements a list of supported Lance namespace operations.

## Implementation and Storage

Except for any storage-only implementation (e.g. [directory namespace](../impls/dir)),
a Lance table exists both in the storage and the implementation.
For example, a Lance table exists both in HMS and storage for the [Hive namespace](../impls/hive).
There are 2 possible ways to manage a Lance table under such setting.
A Lance namespace implementation can choose to support one or both:

### Implementation Managed Table

A implementation managed Lance table is a table that is fully managed by the Lance namespace implementation.
The implementation must maintain information about the latest version of the Lance table.
Any modifications to the table must happen through the implementation.
If a user directly modifies the underlying table in the storage bypassing the implementation,
the implementation must not reflect the changes in the table to the namespace users.

This mode ensures the namespace service is aware of all activities in the table,
and can thus fully enforce any governance and management features for the table.

### Storage Managed Table

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

## Native Implementations

A native Lance namespace implementation is a Lance Namespace implementation 
that is maintained in this `lance-namespace` repository.
Any implementation that is outside the repository is considered as a third-party implementation.