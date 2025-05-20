package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.client.LanceNamespace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bootstrap class for Lance Namespace Server.
 * This class starts the Spring Boot application and configures component scanning
 * to include both the adapter and server-core components.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.lancedb.lance.namespace.adapter",
    "com.lancedb.lance.namespace.server"
})
public class LanceNamespaceAdapter {

    /**
     * Main method to start the Lance Namespace Server.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LanceNamespaceAdapter.class, args);
    }

    /**
     * Creates and configures the LanceNamespace bean.
     * This bean will be used by the controllers to interact with the Lance Namespace implementation.
     *
     * @return configured LanceNamespace implementation
     */
    @Bean
    public LanceNamespace lanceNamespace() {
        // TODO: Configure this using some setting
        throw new UnsupportedOperationException("Not implemented yet");
    }
} 