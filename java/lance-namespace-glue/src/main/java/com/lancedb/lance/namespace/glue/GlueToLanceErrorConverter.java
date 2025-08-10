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
package com.lancedb.lance.namespace.glue;

import com.lancedb.lance.namespace.LanceNamespaceException;

import software.amazon.awssdk.services.glue.model.GlueException;

public class GlueToLanceErrorConverter {

  private GlueToLanceErrorConverter() {}

  public static LanceNamespaceException notFound(GlueException e, String message, Object... args) {
    String exceptionMessage = e.getMessage();
    return LanceNamespaceException.notFound(
        String.format(message, args),
        exceptionMessage != null ? exceptionMessage.getClass().getSimpleName() : "GlueException",
        e.requestId(),
        exceptionMessage != null ? exceptionMessage : "No error message");
  }

  public static LanceNamespaceException conflict(GlueException e, String message, Object... args) {
    String exceptionMessage = e.getMessage();
    return LanceNamespaceException.notFound(
        String.format(message, args),
        exceptionMessage != null ? exceptionMessage.getClass().getSimpleName() : "GlueException",
        e.requestId(),
        exceptionMessage != null ? exceptionMessage : "No error message");
  }

  public static LanceNamespaceException serverError(
      GlueException e, String message, Object... args) {
    String exceptionMessage = e.getMessage();
    return LanceNamespaceException.serverError(
        String.format(message, args),
        exceptionMessage != null ? exceptionMessage.getClass().getSimpleName() : "GlueException",
        e.requestId(),
        exceptionMessage != null ? exceptionMessage : "No error message");
  }
}
