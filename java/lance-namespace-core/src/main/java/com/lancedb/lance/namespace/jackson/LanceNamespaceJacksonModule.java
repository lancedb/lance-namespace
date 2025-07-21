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
package com.lancedb.lance.namespace.jackson;

import com.lancedb.lance.namespace.model.QueryTableRequest;

import com.fasterxml.jackson.databind.module.SimpleModule;

/** Jackson module that registers custom serializers for LanceDB namespace models. */
public class LanceNamespaceJacksonModule extends SimpleModule {

  public LanceNamespaceJacksonModule() {
    super("LanceNamespaceJacksonModule");

    // Register the custom serializer for QueryTableRequest
    addSerializer(QueryTableRequest.class, new QueryTableRequestSerializer());
  }
}
