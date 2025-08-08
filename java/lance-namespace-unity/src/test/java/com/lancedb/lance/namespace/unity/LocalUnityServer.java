/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lancedb.lance.namespace.unity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class LocalUnityServer {

  private static final Logger LOG = LoggerFactory.getLogger(LocalUnityServer.class);

  private Process serverProcess;
  private int port;
  private Path dataDir;
  private String endpoint;

  public LocalUnityServer() {
    this.port = findAvailablePort();
    this.endpoint = "http://localhost:" + port;
  }

  public void start() throws IOException {
    // Create temporary directory for Unity Catalog data
    dataDir = Files.createTempDirectory("unity-test-");
    LOG.info("Starting Unity Catalog server on port {} with data dir {}", port, dataDir);

    try {
      // Use start-uc-server command available on classpath
      ProcessBuilder pb = new ProcessBuilder("start-uc-server");

      // Set environment variables for Unity Catalog configuration
      pb.environment().put("UC_SERVER_PORT", String.valueOf(port));
      pb.environment().put("UC_DATA_DIR", dataDir.toString());

      // Redirect output for debugging
      pb.redirectErrorStream(true);
      pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

      LOG.info("Starting Unity Catalog server with command: start-uc-server");
      LOG.info("Server port: {}", port);
      LOG.info("Data directory: {}", dataDir);

      serverProcess = pb.start();

      // Wait for server to be ready
      waitForServerReady();

      LOG.info("Unity Catalog server started successfully at {}", endpoint);

    } catch (Exception e) {
      throw new IOException("Failed to start Unity Catalog server", e);
    }
  }

  public void stop() throws IOException {
    LOG.info("Stopping Unity Catalog server");

    if (serverProcess != null && serverProcess.isAlive()) {
      serverProcess.destroy();
      try {
        // Wait up to 5 seconds for graceful shutdown
        if (!serverProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
          LOG.warn("Server didn't stop gracefully, forcing termination");
          serverProcess.destroyForcibly();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOG.warn("Interrupted while waiting for server shutdown");
        serverProcess.destroyForcibly();
      }
    }

    // Clean up data directory
    if (dataDir != null && Files.exists(dataDir)) {
      Files.walk(dataDir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
  }

  public String getEndpoint() {
    return endpoint;
  }

  public int getPort() {
    return port;
  }

  public Path getDataDir() {
    return dataDir;
  }

  private int findAvailablePort() {
    // Find an available port
    try (java.net.ServerSocket socket = new java.net.ServerSocket(0)) {
      return socket.getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException("Failed to find available port", e);
    }
  }

  private void waitForServerReady() {
    int maxAttempts = 30;
    int attempt = 0;

    while (attempt < maxAttempts) {
      try {
        // Try to connect to the server
        java.net.URL url = new java.net.URL(endpoint + "/api/2.1/unity-catalog/catalogs");
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(1000);
        conn.setReadTimeout(1000);

        int responseCode = conn.getResponseCode();
        if (responseCode >= 200 && responseCode < 500) {
          LOG.info("Unity Catalog server is ready");
          return;
        }
      } catch (Exception e) {
        // Server not ready yet
      }

      attempt++;
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException("Interrupted while waiting for server", e);
      }
    }

    throw new RuntimeException("Unity Catalog server failed to start within timeout");
  }
}
