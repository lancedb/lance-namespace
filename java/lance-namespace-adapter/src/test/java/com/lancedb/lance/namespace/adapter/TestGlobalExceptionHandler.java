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

import com.lancedb.lance.namespace.LanceNamespace;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MockExceptionController.class)
public class TestGlobalExceptionHandler {
  @MockBean private LanceNamespace lanceNamespace;

  @Autowired private MockMvc mockMvc;

  @Test
  public void testNotFound() throws Exception {
    mockMvc
        .perform(get("/testNotFound").queryParam("param", "foo"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.error").value("Mock resource not found"))
        .andExpect(jsonPath("$.type").value("Not found Error"))
        .andExpect(jsonPath("$.code").value(404))
        .andExpect(jsonPath("$.instance").value("/v1/namespaces"))
        .andExpect(jsonPath("$.detail").value("foo not found"));
  }

  @Test
  public void testInternalError() throws Exception {
    mockMvc
        .perform(
            get("/testInternalError").queryParam("param", "foo").queryParam("errorCode", "101"))
        .andExpect(status().is(101))
        .andExpect(jsonPath("$.code").value(101))
        .andExpect(jsonPath("$.error").value("foo not found"));
  }
}
