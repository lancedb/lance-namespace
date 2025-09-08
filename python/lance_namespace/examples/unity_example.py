#!/usr/bin/env python3
"""
Example usage of Unity Catalog namespace for Lance.

This example demonstrates how to:
1. Connect to Unity Catalog
2. Create and manage namespaces (schemas)
3. Create and manage Lance tables
4. List and describe resources
"""

import pyarrow as pa
import pyarrow.ipc as ipc
import io
from lance_namespace import connect
from lance_namespace import (
    ListNamespacesRequest,
    CreateNamespaceRequest,
    DescribeNamespaceRequest,
    ListTablesRequest,
    CreateTableRequest,
    CreateEmptyTableRequest,
    DescribeTableRequest,
    DropTableRequest,
    DropNamespaceRequest,
)


def main():
    # Configure Unity Catalog connection
    # Replace these with your actual Unity Catalog settings
    config = {
        "unity.endpoint": "https://your-unity-catalog.example.com",
        "unity.catalog": "main",
        "unity.root": "/data/lance",
        "unity.auth_token": "your-auth-token",  # Optional
    }
    
    # Connect to Unity Catalog namespace
    namespace = connect("unity", config)
    
    print("Connected to Unity Catalog namespace")
    
    # Example 1: List top-level namespaces (catalogs)
    print("\n1. Listing catalogs...")
    list_ns_req = ListNamespacesRequest()
    list_ns_req.id = []
    response = namespace.list_namespaces(list_ns_req)
    print(f"Available catalogs: {response.namespaces}")
    
    # Example 2: List schemas in a catalog
    print("\n2. Listing schemas in catalog 'main'...")
    list_ns_req.id = ["main"]
    response = namespace.list_namespaces(list_ns_req)
    print(f"Available schemas: {response.namespaces}")
    
    # Example 3: Create a new schema
    print("\n3. Creating a new schema...")
    create_ns_req = CreateNamespaceRequest()
    create_ns_req.id = ["main", "test_schema"]
    create_ns_req.properties = {
        "description": "Test schema for Lance tables",
        "owner": "data_team"
    }
    
    try:
        response = namespace.create_namespace(create_ns_req)
        print(f"Schema created with properties: {response.properties}")
    except Exception as e:
        print(f"Schema creation failed (may already exist): {e}")
    
    # Example 4: Describe the schema
    print("\n4. Describing schema...")
    desc_ns_req = DescribeNamespaceRequest()
    desc_ns_req.id = ["main", "test_schema"]
    
    try:
        response = namespace.describe_namespace(desc_ns_req)
        print(f"Schema properties: {response.properties}")
    except Exception as e:
        print(f"Failed to describe schema: {e}")
    
    # Example 5: Create a Lance table with Arrow schema
    print("\n5. Creating a Lance table...")
    
    # Define Arrow schema
    arrow_schema = pa.schema([
        pa.field("id", pa.int64()),
        pa.field("name", pa.string()),
        pa.field("value", pa.float64()),
        pa.field("timestamp", pa.timestamp('us')),
    ])
    
    # Create Arrow IPC stream with schema
    buf = io.BytesIO()
    writer = ipc.new_stream(buf, arrow_schema)
    writer.close()
    ipc_data = buf.getvalue()
    
    # Create table request
    create_table_req = CreateTableRequest()
    create_table_req.id = ["main", "test_schema", "test_table"]
    create_table_req.properties = {
        "description": "Test Lance table",
        "format": "lance"
    }
    
    try:
        response = namespace.create_table(create_table_req, ipc_data)
        print(f"Table created at: {response.location}")
        print(f"Table version: {response.version}")
        print(f"Table properties: {response.properties}")
    except Exception as e:
        print(f"Table creation failed (may already exist): {e}")
    
    # Example 6: Create an empty Lance table (metadata only)
    print("\n6. Creating an empty Lance table...")
    
    create_empty_req = CreateEmptyTableRequest()
    create_empty_req.id = ["main", "test_schema", "empty_table"]
    create_empty_req.location = "/data/lance/main/test_schema/empty_table"
    create_empty_req.properties = {
        "description": "Empty Lance table for later data ingestion"
    }
    
    try:
        response = namespace.create_empty_table(create_empty_req)
        print(f"Empty table created at: {response.location}")
        print(f"Table properties: {response.properties}")
    except Exception as e:
        print(f"Empty table creation failed: {e}")
    
    # Example 7: List tables in schema
    print("\n7. Listing tables in schema...")
    list_tables_req = ListTablesRequest()
    list_tables_req.id = ["main", "test_schema"]
    
    try:
        response = namespace.list_tables(list_tables_req)
        print(f"Tables in schema: {response.tables}")
    except Exception as e:
        print(f"Failed to list tables: {e}")
    
    # Example 8: Describe a table
    print("\n8. Describing table...")
    desc_table_req = DescribeTableRequest()
    desc_table_req.id = ["main", "test_schema", "test_table"]
    
    try:
        response = namespace.describe_table(desc_table_req)
        print(f"Table location: {response.location}")
        print(f"Table properties: {response.properties}")
    except Exception as e:
        print(f"Failed to describe table: {e}")
    
    # Example 9: Drop a table
    print("\n9. Dropping table...")
    drop_table_req = DropTableRequest()
    drop_table_req.id = ["main", "test_schema", "empty_table"]
    
    try:
        response = namespace.drop_table(drop_table_req)
        print(f"Table dropped: {response.id}")
        if response.location:
            print(f"Data location removed: {response.location}")
    except Exception as e:
        print(f"Failed to drop table: {e}")
    
    # Example 10: Drop schema (optional - be careful!)
    # Uncomment to actually drop the schema
    # print("\n10. Dropping schema...")
    # drop_ns_req = DropNamespaceRequest()
    # drop_ns_req.id = ["main", "test_schema"]
    # drop_ns_req.behavior = DropNamespaceRequest.BehaviorEnum.CASCADE  # Drop all tables
    # 
    # try:
    #     response = namespace.drop_namespace(drop_ns_req)
    #     print("Schema dropped successfully")
    # except Exception as e:
    #     print(f"Failed to drop schema: {e}")
    
    print("\n✅ Unity Catalog namespace example completed!")


if __name__ == "__main__":
    main()