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
package org.lance.namespace.pact.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

/**
 * Shared DSL helper for building ErrorResponse pact body matchers.
 *
 * <p>Schema (from contract-pack/interactions.json):
 *
 * <ul>
 *   <li>{@code error} — String matched as stringType
 *   <li>{@code code} — Integer matched as integerType
 *   <li>{@code type} — String matched as stringType
 *   <li>{@code detail} — String matched as stringType
 * </ul>
 */
public final class ErrorResponseDsl {

  private ErrorResponseDsl() {}

  public static DslPart body(String error, int code, String type, String detail) {
    return new PactDslJsonBody()
        .stringType("error", error)
        .integerType("code", code)
        .stringType("type", type)
        .stringType("detail", detail);
  }
}
