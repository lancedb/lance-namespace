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

import com.lancedb.lance.namespace.util.PropertyUtil;

import java.util.Map;

public class RestNamespaceConfig {

  /** The delimiter that is used to parse object string identifiers in REST routes */
  public static final String DELIMITER = "delimiter";

  public static final String DELIMITER_DEFAULT = ".";

  /** The headers that should be sent back in every response */
  public static final String HEADERS_PREFIX = "headers.";

  private final String delimiter;
  private final Map<String, String> additionalHeaders;

  public RestNamespaceConfig(Map<String, String> input) {
    this.delimiter = PropertyUtil.propertyAsString(input, DELIMITER, DELIMITER_DEFAULT);
    this.additionalHeaders = PropertyUtil.propertiesWithPrefix(input, HEADERS_PREFIX);
  }

  public String getDelimiter() {
    return delimiter;
  }

  public Map<String, String> getAdditionalHeaders() {
    return additionalHeaders;
  }
}
