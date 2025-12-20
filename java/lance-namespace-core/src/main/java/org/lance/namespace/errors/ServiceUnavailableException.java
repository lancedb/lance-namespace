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
package org.lance.namespace.errors;

import javax.annotation.Nullable;

/** Thrown when the service is temporarily unavailable. */
public class ServiceUnavailableException extends LanceNamespaceException {
  public ServiceUnavailableException(String message) {
    super(ErrorCode.SERVICE_UNAVAILABLE, message);
  }

  public ServiceUnavailableException(String message, Throwable cause) {
    super(ErrorCode.SERVICE_UNAVAILABLE, message, cause);
  }

  public ServiceUnavailableException(
      String message, @Nullable String detail, @Nullable String instance) {
    super(ErrorCode.SERVICE_UNAVAILABLE, message, detail, instance);
  }
}
