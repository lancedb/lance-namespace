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
package com.lancedb.lance.namespace.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class HttpClient {

  private final CloseableHttpClient httpClient;
  private final String baseUrl;
  private final Optional<String> authToken;

  public HttpClient(String baseUrl, Optional<String> authToken) {
    this.httpClient = HttpClients.createDefault();
    this.baseUrl = baseUrl;
    this.authToken = authToken;
  }

  public HttpClient(String baseUrl) {
    this(baseUrl, Optional.empty());
  }

  public JsonNode get(String path) throws IOException, HttpException {
    HttpGet request = new HttpGet(URI.create(baseUrl + path));
    addAuthHeader(request);

    try {
      return httpClient.execute(request, this::handleResponse);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      if (e instanceof RuntimeException) {
        RuntimeException re = (RuntimeException) e;
        if (re.getCause() instanceof HttpException) {
          throw (HttpException) re.getCause();
        }
      }
      throw new IOException("Unexpected error", e);
    }
  }

  public JsonNode post(String path, Object body) throws IOException, HttpException {
    HttpPost request = new HttpPost(URI.create(baseUrl + path));
    addAuthHeader(request);

    String jsonBody = JsonUtil.toJsonString(body);
    request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

    try {
      return httpClient.execute(request, this::handleResponse);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      if (e instanceof RuntimeException) {
        RuntimeException re = (RuntimeException) e;
        if (re.getCause() instanceof HttpException) {
          throw (HttpException) re.getCause();
        }
      }
      throw new IOException("Unexpected error", e);
    }
  }

  public JsonNode put(String path, Object body) throws IOException, HttpException {
    HttpPut request = new HttpPut(URI.create(baseUrl + path));
    addAuthHeader(request);

    String jsonBody = JsonUtil.toJsonString(body);
    request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

    try {
      return httpClient.execute(request, this::handleResponse);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      if (e instanceof RuntimeException) {
        RuntimeException re = (RuntimeException) e;
        if (re.getCause() instanceof HttpException) {
          throw (HttpException) re.getCause();
        }
      }
      throw new IOException("Unexpected error", e);
    }
  }

  public void delete(String path) throws IOException, HttpException {
    HttpDelete request = new HttpDelete(URI.create(baseUrl + path));
    addAuthHeader(request);

    try {
      httpClient.execute(
          request,
          response -> {
            if (response.getCode() >= 400) {
              String errorBody = getResponseBody(response);
              throw new RuntimeException(
                  new HttpException(response.getCode(), response.getReasonPhrase(), errorBody));
            }
            return null;
          });
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      if (e instanceof RuntimeException) {
        RuntimeException re = (RuntimeException) e;
        if (re.getCause() instanceof HttpException) {
          throw (HttpException) re.getCause();
        }
      }
      throw new IOException("Unexpected error", e);
    }
  }

  private void addAuthHeader(org.apache.hc.core5.http.message.BasicHttpRequest request) {
    authToken.ifPresent(token -> request.addHeader("Authorization", "Bearer " + token));
  }

  private JsonNode handleResponse(ClassicHttpResponse response) throws IOException {
    String responseBody = getResponseBody(response);

    if (response.getCode() >= 400) {
      throw new RuntimeException(
          new HttpException(response.getCode(), response.getReasonPhrase(), responseBody));
    }

    if (responseBody.isEmpty()) {
      return JsonUtil.createObjectNode();
    }

    return JsonUtil.parseJson(responseBody);
  }

  private String getResponseBody(ClassicHttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    try {
      return entity != null ? EntityUtils.toString(entity) : "";
    } catch (org.apache.hc.core5.http.ParseException e) {
      throw new IOException("Failed to parse response body", e);
    }
  }

  public void close() throws IOException {
    if (httpClient != null) {
      httpClient.close();
    }
  }

  public static class HttpException extends Exception {
    private final int code;
    private final String message;
    private final String responseBody;

    public HttpException(int code, String message, String responseBody) {
      super(String.format("HTTP %d: %s", code, message));
      this.code = code;
      this.message = message;
      this.responseBody = responseBody;
    }

    public int getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    public String getResponseBody() {
      return responseBody;
    }
  }
}
