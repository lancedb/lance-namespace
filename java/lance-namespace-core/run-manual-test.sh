#!/bin/bash

# Script to run the LanceRestNamespaceManualTest

echo "Running LanceRestNamespaceManualTest..."

# Navigate to the correct directory
cd "$(dirname "$0")"

# Compile test classes
echo "Compiling test classes..."
mvn test-compile

# Run the test
echo "Running test..."
mvn exec:java -Dexec.mainClass="com.lancedb.lance.namespace.LanceRestNamespaceManualTest" -Dexec.classpathScope="test"