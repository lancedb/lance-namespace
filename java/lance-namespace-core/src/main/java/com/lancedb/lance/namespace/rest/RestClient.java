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
package com.lancedb.lance.namespace.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Generic REST client for making HTTP requests. This client can be shared across different
 * namespace implementations.
 */
public class RestClient implements Closeable {
  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);
  private static final int DEFAULT_MAX_CONNECTIONS = 20;
  private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 10;

  private final CloseableHttpClient httpClient;
  private final ObjectMapper objectMapper;
  private final String baseUrl;
  private final Map<String, String> defaultHeaders;
  private final int maxRetries;
  private final long retryDelayMs;

  public static class Builder {
    private String baseUrl;
    private Map<String, String> defaultHeaders = new HashMap<>();
    private int connectTimeoutSeconds = 10;
    private int readTimeoutSeconds = 60;
    private int maxRetries = 3;
    private long retryDelayMs = 1000;
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Builder baseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public Builder defaultHeaders(Map<String, String> headers) {
      this.defaultHeaders = headers;
      return this;
    }

    public Builder connectTimeout(int seconds) {
      this.connectTimeoutSeconds = seconds;
      return this;
    }

    public Builder readTimeout(int seconds) {
      this.readTimeoutSeconds = seconds;
      return this;
    }

    public Builder maxRetries(int maxRetries) {
      this.maxRetries = maxRetries;
      return this;
    }

    public Builder retryDelayMs(long retryDelayMs) {
      this.retryDelayMs = retryDelayMs;
      return this;
    }

    public Builder maxConnections(int maxConnections) {
      this.maxConnections = maxConnections;
      return this;
    }

    public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
      this.maxConnectionsPerRoute = maxConnectionsPerRoute;
      return this;
    }

    public Builder objectMapper(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      return this;
    }

    public RestClient build() {
      if (baseUrl == null) {
        throw new IllegalArgumentException("Base URL is required");
      }

      PoolingHttpClientConnectionManager connectionManager =
          new PoolingHttpClientConnectionManager();
      connectionManager.setMaxTotal(maxConnections);
      connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

      RequestConfig requestConfig =
          RequestConfig.custom()
              .setConnectTimeout(Timeout.ofSeconds(connectTimeoutSeconds))
              .setResponseTimeout(Timeout.ofSeconds(readTimeoutSeconds))
              .build();

      CloseableHttpClient httpClient =
          HttpClients.custom()
              .setConnectionManager(connectionManager)
              .setDefaultRequestConfig(requestConfig)
              .build();

      return new RestClient(
          httpClient, objectMapper, baseUrl, defaultHeaders, maxRetries, retryDelayMs);
    }
  }

  private RestClient(
      CloseableHttpClient httpClient,
      ObjectMapper objectMapper,
      String baseUrl,
      Map<String, String> defaultHeaders,
      int maxRetries,
      long retryDelayMs) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    this.defaultHeaders = defaultHeaders;
    this.maxRetries = maxRetries;
    this.retryDelayMs = retryDelayMs;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Execute a GET request. */
  public <T> T get(String path, Class<T> responseType) throws IOException {
    return get(path, Collections.emptyMap(), responseType);
  }

  /** Execute a GET request with query parameters. */
  public <T> T get(String path, Map<String, String> queryParams, Class<T> responseType)
      throws IOException {
    String url = buildUrl(path, queryParams);
    HttpGet request = new HttpGet(url);
    return executeRequest(request, responseType);
  }

  /** Execute a POST request. */
  public <T> T post(String path, Object body, Class<T> responseType) throws IOException {
    return post(path, body, Collections.emptyMap(), responseType);
  }

  /** Execute a POST request with query parameters. */
  public <T> T post(
      String path, Object body, Map<String, String> queryParams, Class<T> responseType)
      throws IOException {
    String url = buildUrl(path, queryParams);
    HttpPost request = new HttpPost(url);
    if (body != null) {
      String jsonBody = objectMapper.writeValueAsString(body);
      request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
    }
    return executeRequest(request, responseType);
  }

  /** Execute a PUT request. */
  public <T> T put(String path, Object body, Class<T> responseType) throws IOException {
    return put(path, body, Collections.emptyMap(), responseType);
  }

  /** Execute a PUT request with query parameters. */
  public <T> T put(String path, Object body, Map<String, String> queryParams, Class<T> responseType)
      throws IOException {
    String url = buildUrl(path, queryParams);
    HttpPut request = new HttpPut(url);
    if (body != null) {
      String jsonBody = objectMapper.writeValueAsString(body);
      request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
    }
    return executeRequest(request, responseType);
  }

  /** Execute a PATCH request. */
  public <T> T patch(String path, Object body, Class<T> responseType) throws IOException {
    return patch(path, body, Collections.emptyMap(), responseType);
  }

  /** Execute a PATCH request with query parameters. */
  public <T> T patch(
      String path, Object body, Map<String, String> queryParams, Class<T> responseType)
      throws IOException {
    String url = buildUrl(path, queryParams);
    HttpPatch request = new HttpPatch(url);
    if (body != null) {
      String jsonBody = objectMapper.writeValueAsString(body);
      request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
    }
    return executeRequest(request, responseType);
  }

  /** Execute a DELETE request. */
  public <T> T delete(String path, Class<T> responseType) throws IOException {
    return delete(path, Collections.emptyMap(), responseType);
  }

  /** Execute a DELETE request with query parameters. */
  public <T> T delete(String path, Map<String, String> queryParams, Class<T> responseType)
      throws IOException {
    String url = buildUrl(path, queryParams);
    HttpDelete request = new HttpDelete(url);
    return executeRequest(request, responseType);
  }

  /** Execute a DELETE request without expecting a response body. */
  public void delete(String path) throws IOException {
    delete(path, Collections.emptyMap());
  }

  /** Execute a DELETE request with query parameters without expecting a response body. */
  public void delete(String path, Map<String, String> queryParams) throws IOException {
    String url = buildUrl(path, queryParams);
    HttpDelete request = new HttpDelete(url);
    executeRequest(request, null);
  }

  private <T> T executeRequest(HttpUriRequestBase request, Class<T> responseType)
      throws IOException {
    // Add default headers
    defaultHeaders.forEach(request::addHeader);

    // Retry logic
    IOException lastException = null;
    for (int attempt = 0; attempt <= maxRetries; attempt++) {
      if (attempt > 0) {
        try {
          TimeUnit.MILLISECONDS.sleep(retryDelayMs * attempt);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new IOException("Interrupted during retry delay", e);
        }
      }

      try (CloseableHttpResponse response = httpClient.execute(request)) {
        int statusCode = response.getCode();
        HttpEntity entity = response.getEntity();
        String responseBody = null;
        if (entity != null) {
          try {
            responseBody = EntityUtils.toString(entity);
          } catch (org.apache.hc.core5.http.ParseException e) {
            throw new IOException("Failed to parse response body", e);
          }
        }

        if (statusCode >= 200 && statusCode < 300) {
          if (responseType == null || responseType == Void.class) {
            return null;
          }
          if (responseBody == null || responseBody.isEmpty()) {
            return null;
          }
          return objectMapper.readValue(responseBody, responseType);
        } else if (statusCode >= 400 && statusCode < 500) {
          // Client error, don't retry
          throw new RestClientException(
              statusCode,
              responseBody,
              String.format("Request failed with status %d: %s", statusCode, responseBody));
        } else {
          // Server error, might retry
          LOG.warn("HTTP request failed with status {}: {}", statusCode, responseBody);
          lastException =
              new IOException(
                  String.format("Request failed with status %d: %s", statusCode, responseBody));
        }
      } catch (RestClientException e) {
        // Client errors should not be retried, rethrow immediately
        throw e;
      } catch (IOException e) {
        lastException = e;
        LOG.warn("Request attempt {} failed: {}", attempt + 1, e.getMessage());
      }
    }

    throw new IOException("Request failed after " + (maxRetries + 1) + " attempts", lastException);
  }

  private String buildUrl(String path, Map<String, String> queryParams) {
    StringBuilder url = new StringBuilder(baseUrl);
    if (!path.startsWith("/")) {
      url.append("/");
    }
    url.append(path);

    if (!queryParams.isEmpty()) {
      url.append("?");
      queryParams.forEach(
          (key, value) -> {
            try {
              url.append(key)
                  .append("=")
                  .append(java.net.URLEncoder.encode(value, "UTF-8"))
                  .append("&");
            } catch (Exception e) {
              throw new RuntimeException("Failed to encode query parameter: " + key, e);
            }
          });
      // Remove trailing &
      url.setLength(url.length() - 1);
    }

    return url.toString();
  }

  @Override
  public void close() throws IOException {
    httpClient.close();
  }

  /** Exception thrown by the REST client for HTTP errors. */
  public static class RestClientException extends IOException {
    private final int statusCode;
    private final String responseBody;

    public RestClientException(int statusCode, String responseBody, String message) {
      super(message);
      this.statusCode = statusCode;
      this.responseBody = responseBody;
    }

    public int getStatusCode() {
      return statusCode;
    }

    public String getResponseBody() {
      return responseBody;
    }
  }
}
