//! Example demonstrating per-operation configuration override
//!
//! This example shows how to use the optional configuration parameter
//! to override settings (particularly HTTP headers) on a per-operation basis.

use lance_namespace::rest::RestNamespace;
use lance_namespace::namespace::LanceNamespace;
use lance_namespace_reqwest_client::models::ListNamespacesRequest;
use std::collections::HashMap;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    // Create a RestNamespace with base configuration
    let mut base_config = HashMap::new();
    base_config.insert("uri".to_string(), "http://localhost:8080".to_string());
    base_config.insert("delimiter".to_string(), ".".to_string());
    
    // Add some default headers that will be sent with all requests
    base_config.insert(
        "header.Authorization".to_string(),
        "Bearer default-token".to_string(),
    );
    base_config.insert(
        "header.X-Client-Version".to_string(),
        "1.0".to_string(),
    );

    let namespace = RestNamespace::new(base_config);

    // Example 1: Call without override config (uses base headers)
    println!("Making request with base configuration...");
    let request = ListNamespacesRequest {
        id: Some(vec!["test".to_string()]),
        page_token: None,
        limit: Some(10),
    };
    
    // This call will include:
    // - Authorization: Bearer default-token
    // - X-Client-Version: 1.0
    let _result = namespace.list_namespaces(request.clone(), None).await;

    // Example 2: Call with override config (additional headers)
    println!("Making request with override configuration...");
    let mut override_config = HashMap::new();
    
    // Add additional headers for this specific request
    override_config.insert(
        "header.X-Request-ID".to_string(),
        "unique-request-123".to_string(),
    );
    override_config.insert(
        "header.X-Trace-ID".to_string(),
        "trace-456".to_string(),
    );
    
    // This call will include all base headers PLUS the override headers:
    // - Authorization: Bearer default-token (from base)
    // - X-Client-Version: 1.0 (from base)
    // - X-Request-ID: unique-request-123 (from override)
    // - X-Trace-ID: trace-456 (from override)
    let _result = namespace.list_namespaces(request.clone(), Some(override_config)).await;

    // Example 3: Override existing headers
    println!("Making request with header override...");
    let mut auth_override_config = HashMap::new();
    
    // This will override the base Authorization header
    auth_override_config.insert(
        "header.Authorization".to_string(),
        "Bearer special-token-for-this-request".to_string(),
    );
    
    // This call will include:
    // - Authorization: Bearer special-token-for-this-request (overridden)
    // - X-Client-Version: 1.0 (from base)
    let _result = namespace.list_namespaces(request, Some(auth_override_config)).await;

    // Example 4: Different operations can have different configs
    println!("Making different operations with different configs...");
    
    // First operation with tracing headers
    let mut tracing_config = HashMap::new();
    tracing_config.insert("header.X-Trace-ID".to_string(), "op1-trace".to_string());
    let _list_result = namespace.list_namespaces(
        ListNamespacesRequest {
            id: None,
            page_token: None,
            limit: Some(5),
        },
        Some(tracing_config),
    ).await;
    
    // Second operation with different auth
    let mut auth_config = HashMap::new();
    auth_config.insert(
        "header.Authorization".to_string(),
        "Bearer admin-token".to_string(),
    );
    let _create_result = namespace.create_namespace(
        lance_namespace_reqwest_client::models::CreateNamespaceRequest {
            id: Some(vec!["new_namespace".to_string()]),
            properties: None,
            mode: None,
        },
        Some(auth_config),
    ).await;

    println!("All operations completed!");
    
    Ok(())
}