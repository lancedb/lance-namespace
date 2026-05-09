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
package org.lance.namespace.pact.provider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Pact-profile-only servlet filter that wraps empty POST request bodies with {@code {}}.
 *
 * <p>Pact consumer tests omit request bodies so that the mock server accepts any payload the client
 * sends. However, the generated Spring server interfaces declare {@code @RequestBody} as required,
 * causing Spring to throw {@code HttpMessageNotReadableException} (400 Bad Request) when no body is
 * present. This filter intercepts those requests before Spring's argument resolver runs and
 * supplies a minimal empty JSON object so Jackson can deserialize a default-constructed request
 * DTO.
 */
@Component
@Profile("pact")
public class EmptyBodyFilter extends OncePerRequestFilter {

  private static final byte[] EMPTY_JSON = "{}".getBytes(java.nio.charset.StandardCharsets.UTF_8);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String contentType = request.getContentType();
    String method = request.getMethod();

    // Only wrap POST/PUT/PATCH requests with application/json content type and empty body
    if (isJsonPost(method, contentType) && request.getContentLength() <= 0) {
      filterChain.doFilter(new EmptyBodyRequestWrapper(request), response);
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private static boolean isJsonPost(String method, String contentType) {
    return ("POST".equalsIgnoreCase(method)
            || "PUT".equalsIgnoreCase(method)
            || "PATCH".equalsIgnoreCase(method))
        && contentType != null
        && contentType.contains("application/json");
  }

  /** Wraps the original request and returns an empty JSON object for the body stream. */
  private static final class EmptyBodyRequestWrapper extends HttpServletRequestWrapper {

    EmptyBodyRequestWrapper(HttpServletRequest request) {
      super(request);
    }

    @Override
    public jakarta.servlet.ServletInputStream getInputStream() throws IOException {
      InputStream source = new ByteArrayInputStream(EMPTY_JSON);
      return new jakarta.servlet.ServletInputStream() {
        @Override
        public int read() throws IOException {
          return source.read();
        }

        @Override
        public boolean isFinished() {
          try {
            return source.available() == 0;
          } catch (IOException e) {
            return true;
          }
        }

        @Override
        public boolean isReady() {
          return true;
        }

        @Override
        public void setReadListener(jakarta.servlet.ReadListener listener) {
          // no-op for synchronous testing
        }
      };
    }

    @Override
    public int getContentLength() {
      return EMPTY_JSON.length;
    }

    @Override
    public long getContentLengthLong() {
      return EMPTY_JSON.length;
    }
  }
}
