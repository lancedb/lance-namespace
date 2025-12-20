# Objects & Relationships

This page describes the objects in a namespace and their relationships with each other.

## Namespace Definition

A namespace is a centralized repository for discovering, organizing, and managing tables.
It can not only contain a collection of tables, but also a collection of namespaces recursively.
It is designed to encapsulates concepts including namespace, metastore, database, schema, etc.
that frequently appear in other similar data systems to allow easy integration with any system of any type of object hierarchy.

Here is an example layout of a namespace:

![Namespace Layout](../layout.png)

## Parent & Child

We use the term **parent** and **child** to describe relationship between 2 objects.
If namespace A directly contains B, then A is the parent namespace of B, i.e. B is a child of A.
For examples:

- Namespace `ns1` contains a **child namespace** `ns4`. i.e. `ns1` is the **parent namespace** of `ns4`.
- Namespace `ns2` contains a **child table** `t2`, i.e. `t2` belongs to **parent namespace** `ns2`.

## Root Namespace

A root namespace is a namespace that has no parent.
The root namespace is assumed to always exist and is ready to be connected to by a tool to explore objects in the namespace.
The lifecycle management (e.g. creation, deletion) of the root namespace is out of scope of this specification.

## Object Name

The **name** of an object is a string that uniquely identifies the object within the parent namespace it belongs to.
The name of any object must be unique among all other objects that share the same parent namespace.
For examples:

- `cat2`, `cat3` and `cat4` are all unique names under the root namespace
- `t3` and `t4` are both unique names under `cat4`

## Object Identifier

The **identifier** of an object uniquely identifies the object within the root namespace it belongs to.
The identifier of any object must be unique among all other objects that share the same root namespace.

Based on the uniqueness property of an object name within its parent namespace,
an object identifier is the list of object names starting from (not including) the root namespace to (including) the object itself.
This is also called an **list style identifier**.
For examples:

- the list style identifier of `cat5` is `[cat2, cat5]`
- the list style identifier of `t1` is `[cat2, cat5, t1]`

The dollar (`$`) symbol is used as the default delimiter to join all the names to form an **string style identifier**,
but other symbols could also be used if the dollar sign is used in the object name.
For examples:

- the string style identifier of `cat5` is `cat2$cat5`
- the string style identifier of `t1` is `cat2$cat5$t1`
- the string style identifier of `t3` is `cat4#t3` when using delimiter `#`

## Name and Identifier for Root Namespace

The root namespace itself has no name or identifier.
When represented in code, its name and string style identifier is represented by an empty or null string,
and its list style identifier is represented by an empty or null list.

The actual name and identifier of the root namespace is typically
assigned by users through some configuration when used in a tool.
For example, a root namespace can be called `cat1` in Ray, but called `cat2` in Apache Spark,
and they are both configured to connect to the same root namespace.

## Object Level

The root namespace is always at level 0.
This means if an object has list style identifier with list size `N`,
the object is at the `N`th level in the entire namespace hierarchy,
and its corresponding object identifier has `N` levels.
For examples, a namespace `[ns1, ns2]` is at level 2, and its identifier `ns1$ns2` has 2 levels.
A table `[catalog1, database2, table3]` is at level 3, and its identifier `catalog1$database2$table3` has 3 levels.

### Leveled Namespace

If every table in the root namespace are at the same level `N`, the namespace is called **leveled**,
and we say this namespace is a `N`-level namespace.
For example, a [directory namespace](../dir/catalog-spec.md) is a 1-level namespace,
and a Hive 2.x namespace is a 2-level namespace.
