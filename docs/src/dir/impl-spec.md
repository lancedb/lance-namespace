# Lance Directory Namespace Implementation Spec

This document describes how the Lance Directory Namespace catalog spec implements the Lance Namespace client spec.

## Object Mapping

### Namespace

| Client Spec Concept | Directory Namespace Mapping |
|---------------------|----------------------------|
| Root Namespace | The root directory specified by the `root` configuration property |
| Child Namespace | A subdirectory within the parent namespace directory (V2 only) |
| Namespace Identifier | The relative path from root, with `$` delimiter between levels |
| Namespace Properties | Stored as JSON in the `metadata` column of the manifest table (V2 only) |

### Table

| Client Spec Concept | Directory Namespace Mapping |
|---------------------|----------------------------|
| Table | A subdirectory containing Lance table data |
| Table Identifier | The relative path from root, with `$` delimiter between namespace levels and table name |
| Table Location | V1: `<table_name>.lance` directory; V2: `<hash>_<object_id>` directory |
| Table Properties | Stored in Lance table metadata |

## Operation Implementation

### Namespace Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| CreateNamespace | V2 only | Insert a new row with `object_type="namespace"` into the manifest table |
| ListNamespaces | V2 only | Query manifest table for rows where `object_type="namespace"` and `object_id` starts with parent namespace prefix |
| DescribeNamespace | V2 only | Query manifest table for the namespace row and return its metadata |
| DropNamespace | V2 only | Delete the namespace row from manifest table; fails if namespace contains children |
| NamespaceExists | V2 only | Check if a row with the namespace `object_id` exists in the manifest table |

### Table Metadata Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| CreateEmptyTable | Yes | Create a new Lance table directory and register in manifest (V2) |
| RegisterTable | Yes | Add an existing table location to the manifest table |
| ListTables | Yes | V1: List directories matching `*.lance`; V2: Query manifest for `object_type="table"`; Compatibility mode merges both |
| DescribeTable | Yes | Open the Lance table and return its schema, version, and metadata |
| TableExists | Yes | V1: Check if `<table_name>.lance` directory exists; V2: Query manifest; Compatibility mode checks both |
| DropTable | Yes | Delete the table directory and remove from manifest (V2) |
| DeregisterTable | V2 only | Remove the table entry from manifest without deleting the underlying data |

### Table Data Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| CreateTable | Yes | Create Lance table with initial data using Lance SDK |
| InsertIntoTable | Yes | Append or overwrite data using Lance SDK |
| MergeInsertIntoTable | Yes | Perform merge insert using Lance SDK |
| UpdateTable | Yes | Update rows matching filter using Lance SDK |
| DeleteFromTable | Yes | Delete rows matching filter using Lance SDK |
| QueryTable | Yes | Execute query using Lance SDK (vector search, full-text search, filtering) |
| CountTableRows | Yes | Count rows using Lance SDK |

### Table Index Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| CreateTableIndex | Yes | Create index using Lance SDK |
| ListTableIndices | Yes | List indices using Lance SDK |
| DescribeTableIndexStats | Yes | Get index statistics using Lance SDK |
| DropTableIndex | Yes | Drop index using Lance SDK |

### Table Version Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| ListTableVersions | Yes | List versions from Lance table `_versions/` directory |
| RestoreTable | Yes | Restore to a previous version using Lance SDK |

### Table Tag Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| ListTableTags | Yes | List tags using Lance SDK |
| GetTableTagVersion | Yes | Get version for a tag using Lance SDK |
| CreateTableTag | Yes | Create tag using Lance SDK |
| DeleteTableTag | Yes | Delete tag using Lance SDK |
| UpdateTableTag | Yes | Update tag using Lance SDK |

### Schema Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| AlterTableAddColumns | Yes | Add columns using Lance SDK |
| AlterTableAlterColumns | Yes | Alter columns using Lance SDK |
| AlterTableDropColumns | Yes | Drop columns using Lance SDK |

### Statistics Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| GetTableStats | Yes | Get statistics using Lance SDK |

### Query Plan Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| ExplainTableQueryPlan | Yes | Explain query plan using Lance SDK |
| AnalyzeTableQueryPlan | Yes | Analyze query plan using Lance SDK |

### Transaction Operations

| Operation | Supported | Implementation |
|-----------|-----------|----------------|
| DescribeTransaction | Yes | Describe transaction using Lance SDK |
| AlterTransaction | Yes | Alter transaction using Lance SDK |
