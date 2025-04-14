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
import com.lancedb.lance.catalog.client.apache.model.CreateNamespaceRequest;
import com.lancedb.lance.catalog.client.apache.model.CreateNamespaceResponse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** API tests for NamespaceApi */
@Disabled
public class NamespaceApiTest {

  private final NamespaceApi api = new NamespaceApi();

  /**
   * Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to
   * manage one or more tables. There are three modes when trying to create a namespace: * CREATE:
   * Create the namespace if it does not exist. If a namespace of the same name already exists, the
   * operation fails with 400. * EXIST_OK: Create the namespace if it does not exist. If a namespace
   * of the same name already exists, the operation succeeds and the existing namespace is kept. *
   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already
   * exists, the existing namespace is dropped and a new namespace with this name with no table is
   * created.
   *
   * @throws ApiException if the Api call fails
   */
  @Test
  public void createNamespaceTest() throws ApiException {
    CreateNamespaceRequest createNamespaceRequest = null;
    CreateNamespaceResponse response = api.createNamespace(createNamespaceRequest);

    // TODO: test validations
  }
}
