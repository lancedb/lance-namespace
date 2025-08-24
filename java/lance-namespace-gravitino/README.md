# Lance Namespace Gravitino Integration

This module provides integration between Lance and Apache Gravitino catalog for namespace and table management.

## Overview

The Gravitino namespace implementation allows Lance to use Apache Gravitino as a metadata catalog for managing Lance tables. Gravitino provides a unified metadata layer that can work across different data formats and storage systems.

## Features

- **Schema Management**: Create, list, describe, and drop schemas (databases) in Gravitino
- **Table Management**: Create, list, describe, and drop Lance tables in Gravitino
- **Lance Table Identification**: Tables are marked with `format=lance` property for identification
- **Storage Location Management**: Supports specifying custom storage locations for Lance tables
- **Authentication**: Supports Bearer token authentication for secure access to Gravitino

## Configuration

The following configuration properties are supported:

| Property | Description | Default | Required |
|----------|-------------|---------|----------|
| `gravitino.endpoint` | Gravitino server endpoint URL | `http://localhost:8090` | No |
| `gravitino.metalake` | Gravitino metalake name | - | Yes |
| `gravitino.catalog` | Gravitino catalog name | - | Yes |
| `gravitino.auth.token` | Bearer authentication token | - | No |
| `gravitino.connect.timeout` | Connection timeout in seconds | 10 | No |
| `gravitino.read.timeout` | Read timeout in seconds | 60 | No |
| `gravitino.max.retries` | Maximum number of retries for failed requests | 3 | No |

## Usage

### Java Example

```java
import com.lancedb.lance.namespace.gravitino.GravitinoNamespace;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.*;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;

import java.util.*;

public class GravitinoExample {
    public static void main(String[] args) {
        // Initialize Gravitino namespace
        Map<String, String> config = new HashMap<>();
        config.put("gravitino.endpoint", "http://localhost:8090");
        config.put("gravitino.metalake", "my_metalake");
        config.put("gravitino.catalog", "lance_catalog");
        config.put("gravitino.auth.token", "my-auth-token");
        
        try (RootAllocator allocator = new RootAllocator();
             GravitinoNamespace namespace = new GravitinoNamespace()) {
            
            namespace.initialize(config, allocator);
            
            // Create a schema
            CreateNamespaceRequest createSchemaReq = new CreateNamespaceRequest();
            createSchemaReq.setNamespace(ObjectIdentifier.of(
                ObjectIdentifier.Type.NAMESPACE, "my_schema"));
            Map<String, String> properties = new HashMap<>();
            properties.put("comment", "My Lance schema");
            createSchemaReq.setProperties(properties);
            
            CreateNamespaceResponse schemaResponse = namespace.createNamespace(createSchemaReq);
            System.out.println("Created schema: " + schemaResponse.getNamespace().getName());
            
            // Create a table
            CreateTableRequest createTableReq = new CreateTableRequest();
            createTableReq.setTableIdentifier(ObjectIdentifier.of(
                ObjectIdentifier.Type.TABLE, "my_schema", "my_table"));
            
            // Define table schema
            List<Field> fields = Arrays.asList(
                Field.notNullable("id", new ArrowType.Int(64, true)),
                Field.nullable("name", ArrowType.Utf8.INSTANCE),
                Field.nullable("value", new ArrowType.FloatingPoint(
                    org.apache.arrow.vector.types.FloatingPointPrecision.DOUBLE))
            );
            Schema tableSchema = new Schema(fields);
            createTableReq.setSchema(tableSchema);
            createTableReq.setLocation("s3://my-bucket/lance-tables/my_table");
            
            CreateTableResponse tableResponse = namespace.createTable(createTableReq);
            System.out.println("Created table: " + tableResponse.getTableIdentifier().getName());
            
            // List tables
            ListTablesRequest listReq = new ListTablesRequest();
            listReq.setNamespace(ObjectIdentifier.of(
                ObjectIdentifier.Type.NAMESPACE, "my_schema"));
            
            ListTablesResponse listResponse = namespace.listTables(listReq);
            System.out.println("Tables in schema:");
            for (ObjectIdentifier table : listResponse.getTables()) {
                System.out.println("  - " + table.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Architecture

The Gravitino namespace implementation follows these principles:

1. **Metalake and Catalog**: These are read-only levels that must be pre-configured. The namespace operates within a specific metalake and catalog.

2. **Schema Management**: Schemas (databases) can be created, listed, described, and dropped within the configured catalog.

3. **Table Management**: Lance tables are created as regular tables in Gravitino with specific properties:
   - `format=lance`: Identifies the table as a Lance table
   - `provider=lance`: Specifies Lance as the data provider
   - `location`: Points to the actual Lance table data in storage

4. **REST API Integration**: Uses the Gravitino REST API for all metadata operations, reusing the common RestClient from lance-namespace-core.

## Gravitino API Compatibility

This implementation is compatible with Gravitino's table and schema REST APIs as defined in:
- `/api/v1/metalakes/{metalake}/catalogs/{catalog}/schemas`
- `/api/v1/metalakes/{metalake}/catalogs/{catalog}/schemas/{schema}/tables`

## Limitations

- Direct dataset operations (`openTable`, `createTable` with WriteParams) are not yet implemented as they require Lance Java bindings
- Complex types (arrays, structs, maps) have simplified type conversion that may need enhancement for production use
- Only supports tables within the configured metalake and catalog (no cross-catalog operations)

## Testing

The module includes comprehensive unit tests using WireMock to simulate Gravitino API responses. Run tests with:

```bash
mvn test -pl lance-namespace-gravitino
```

## Dependencies

- `lance-namespace-core`: Core Lance namespace interfaces and utilities
- Apache Arrow: For schema and type definitions
- Jackson: For JSON serialization/deserialization
- Apache HttpClient 5: For REST API communication (via RestClient)

## Future Enhancements

- Support for table partitioning and distribution configurations
- Enhanced type mapping for complex Arrow types
- Direct Lance dataset operations when Java bindings become available
- Support for table update operations (ALTER TABLE)
- Integration with Gravitino's authentication and authorization features