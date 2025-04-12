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
package com.lancedb.lance.catalog.client.apache.api;

import com.lancedb.lance.catalog.client.apache.ApiException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** API tests for DefaultApi */
@Disabled
public class DefaultApiTest {

  private final DefaultApi api = new DefaultApi();

  /**
   * Ping the server for use cases like health check
   *
   * @throws ApiException if the Api call fails
   */
  @Test
  public void pingTest() throws ApiException {
    api.ping();

    // TODO: test validations
  }
}
