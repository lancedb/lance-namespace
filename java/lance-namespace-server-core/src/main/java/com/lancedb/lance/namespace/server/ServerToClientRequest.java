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
package com.lancedb.lance.namespace.server;

import com.lancedb.lance.namespace.client.apache.model.CreateNamespaceRequest;

public class ServerToClientRequest {

  public static CreateNamespaceRequest createNamespace(
      com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest request) {
    CreateNamespaceRequest converted = new CreateNamespaceRequest();
    converted.setMode(CreateNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setParent(request.getParent());
    converted.setOptions(request.getOptions());
    converted.setName(request.getName());
    return converted;
  }
}
