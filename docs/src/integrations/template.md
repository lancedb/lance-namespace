# Lance Namespace Implementation Spec Template

This template defines the standard structure for Lance Namespace implementation specs. 
Each implementation spec describes how a specific catalog system integrates with the Lance Namespace client spec.

## Required Sections

### 1. Background

Provide a brief introduction to the catalog system being integrated:

- What the catalog system is and its purpose
- Link to the catalog spec for detailed design information
- Any important context for understanding the implementation

### 2. Namespace Implementation Configuration Properties

List all configuration properties accepted by the namespace implementation:

- Required vs optional properties
- Property descriptions and default values
- Prefix-based properties (e.g., `storage.*`, `headers.*`)

### 3. Object Mapping

Describe how objects in the catalog system map to Lance Namespace concepts using paragraphs (not tables):

**Namespace Mapping:**
- How the root namespace is represented
- How child namespaces are organized
- How namespace identifiers are constructed
- Where namespace properties are stored

**Table Mapping:**
- How tables are represented
- How table identifiers are constructed
- Where table data is stored (location)
- Where table properties are stored

### 4. Lance Table Identification

Describe how to determine if a table in the catalog is a Lance table:

- Required properties, markers, or naming conventions
- Storage location requirements
- How the implementation verifies table validity

### 5. Basic Operations

For each of the 8 recommended basic operations, provide a detailed subsection. Each operation subsection should include:

- A brief description of what the operation does
- Step-by-step implementation details
- An **Error Handling** paragraph describing which errors can occur and under what conditions

The 8 basic operations are:

**Namespace Operations:**

- CreateNamespace
- ListNamespaces
- DescribeNamespace
- DropNamespace (only `Restrict` behavior mode required)

**Table Operations:**

- DeclareTable
- ListTables
- DescribeTable (only `load_detailed_metadata=false` required)
- DeregisterTable

**Note:** For basic implementations, DropNamespace only needs to support the `Restrict` behavior mode
(namespace must be empty before dropping). DescribeTable only needs to support `load_detailed_metadata=false`
(only return table `location` without opening the dataset).

---

## Template Structure

```markdown
# Lance {Catalog Name} Namespace Implementation Spec

This document describes how the {Catalog Name} implements the Lance Namespace client spec.

## Background

{Brief description of the catalog system and its purpose}. For details on the catalog design, see the [{Catalog Name} Catalog Spec](link-to-the-spec).

## Namespace Implementation Configuration Properties

The Lance {Catalog Name} namespace implementation accepts the following configuration properties:

The **{property_name}** property is {required/optional} and {description}. {Default value if optional}.

{Additional properties...}

## Object Mapping

### Namespace

The **root namespace** is {description of how root namespace maps}.

A **child namespace** is {description of child namespace representation}.

The **namespace identifier** is {description of identifier format}.

**Namespace properties** are {description of where/how properties are stored}.

### Table

A **table** is {description of table representation}.

The **table identifier** is {description of identifier format}.

The **table location** is {description of where table data is stored}.

**Table properties** are {description of where/how properties are stored}.

## Lance Table Identification

{Paragraph describing how to identify a Lance table in this catalog system}

## Basic Operations

### CreateNamespace

{Brief description of operation}

The implementation:

1. {Step 1}
2. {Step 2}
3. {Step N}

**Error Handling:**

If {condition}, return error code `N` ({ErrorName}).

{Additional error conditions...}

### ListNamespaces

{Same structure as above}

### DescribeNamespace

{Same structure as above}

### DropNamespace

{Same structure as above}

**Note:** Basic implementations only need to support `Restrict` behavior mode.

### DeclareTable

{Same structure as above}

### ListTables

{Same structure as above}

### DescribeTable

{Same structure as above}

**Note:** Basic implementations only need to support `load_detailed_metadata=false` (only return table `location`).

### DeregisterTable

{Same structure as above}
```

## Error Handling Guidelines

Each operation's error handling section should describe errors in paragraph form, one paragraph per error condition. Include:

- The condition that triggers the error
- The error code number
- The error name in parentheses

Example:

> If the namespace does not exist, return error code `1` (NamespaceNotFound).
>
> If the namespace contains tables or child namespaces, return error code `3` (NamespaceNotEmpty).

For catalog specs that map to HTTP (like REST), also include the HTTP status code:

> If the namespace does not exist, return HTTP `404 Not Found` with error code `1` (NamespaceNotFound).
