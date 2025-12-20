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

/** Thrown when the user lacks permission for the operation. */
public class PermissionDeniedException extends LanceNamespaceException {
  public PermissionDeniedException(String message) {
    super(ErrorCode.PERMISSION_DENIED, message);
  }

  public PermissionDeniedException(String message, Throwable cause) {
    super(ErrorCode.PERMISSION_DENIED, message, cause);
  }

  public PermissionDeniedException(
      String message, @Nullable String detail, @Nullable String instance) {
    super(ErrorCode.PERMISSION_DENIED, message, detail, instance);
  }
}
