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
package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.server.springboot.model.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(LanceNamespaceException.class)
  public ResponseEntity<ErrorResponse> handleLanceNamespaceException(LanceNamespaceException ex) {
    Optional<com.lancedb.lance.namespace.model.ErrorResponse> errorResponse = ex.getErrorResponse();

    if (errorResponse.isPresent()) {
      com.lancedb.lance.namespace.model.ErrorResponse response = errorResponse.get();
      if (response.getStatus() != null) {
        return ResponseEntity.status(response.getStatus())
            .body(ClientToServerResponse.errorResponse(errorResponse.get()));
      }
    }

    // Transform error info into ErrorResponse
    com.lancedb.lance.namespace.model.ErrorResponse errResp =
        new com.lancedb.lance.namespace.model.ErrorResponse();
    errResp.setStatus(500);
    errResp.type("Internal Server Error");
    errResp.setTitle(String.format("Lance Namespace Error Status: %d", ex.getCode()));
    errResp.setDetail(ex.getResponseBody());

    return ResponseEntity.status(500).body(ClientToServerResponse.errorResponse(errResp));
  }
}
