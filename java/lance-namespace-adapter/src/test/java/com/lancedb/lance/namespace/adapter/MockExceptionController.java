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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockExceptionController {
  @GetMapping("/testNotFound")
  public String testNotFound(@RequestParam(required = false) String param) {
    String type = "Mock resource not found";
    String error = "Not found Error";
    String instance = "/v1/namespaces";
    String detail = String.format("%s not found", param);
    throw LanceNamespaceException.notFound(type, error, instance, detail);
  }

  @GetMapping("/testInternalError")
  public String transformIntoErrorResponse(
      @RequestParam(required = false) String param, @RequestParam(required = false) int errorCode) {
    String detail = String.format("%s not found", param);
    throw new LanceNamespaceException(errorCode, detail);
  }
}
