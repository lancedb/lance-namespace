# Lance Unity Namespace

Unity Catalog namespace implementation for Lance.

## Configuration

See [Unity namespace specification](../../docs/src/impls/unity.md) for detailed configuration options.

## Testing

### Prerequisites

To run the Unity namespace tests, you need to have Unity Catalog running locally. The easiest way is to use Docker Compose:

```bash
# Clone Unity Catalog repository
git clone https://github.com/unitycatalog/unitycatalog.git
cd unitycatalog

# Start Unity Catalog with Docker Compose
docker-compose up
```

This will start Unity Catalog server on `http://localhost:8080`.

### Running Tests

Once Unity Catalog is running, you can run the tests:

```bash
# From the java directory
cd java

# Run Unity namespace tests
mvn test -pl lance-namespace-unity -am

# Or run a specific test
mvn test -pl lance-namespace-unity -Dtest=TestUnityNamespace
```

The tests will automatically check if Unity Catalog is available and skip if it's not running.

### Test Coverage

The tests cover:
- Catalog and schema operations (create, list, describe, drop)
- Table lifecycle (create, list, describe, drop)
- Error handling for invalid operations
- Pagination support
- Authentication (when configured)

## Example Usage

```java
import com.lancedb.lance.namespace.unity.UnityNamespace;
import org.apache.arrow.memory.RootAllocator;

// Configure Unity namespace
Map<String, String> config = new HashMap<>();
config.put("endpoint", "http://localhost:8080");
config.put("catalog", "unity");
config.put("root", "/path/to/storage");

// Initialize namespace
LanceNamespace namespace = new UnityNamespace();
namespace.initialize(config, new RootAllocator());

// Create a schema
CreateNamespaceRequest request = new CreateNamespaceRequest();
request.setId(Arrays.asList("unity", "my_schema"));
namespace.createNamespace(request);

// Create a table
CreateTableRequest tableRequest = new CreateTableRequest();
tableRequest.setId(Arrays.asList("unity", "my_schema", "my_table"));
tableRequest.setJsonArrowSchema(arrowSchema);
namespace.createTable(tableRequest);
```

## Integration with Unity Catalog

Lance tables are stored in Unity Catalog as EXTERNAL tables with:
- `table_type` property set to `lance`
- `data_source_format` set to `null` (Lance has its own format)
- `storage_location` pointing to the actual Lance table data
- `columns` set to `null` (Lance manages its own schema)