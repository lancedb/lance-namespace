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
package org.lance.namespace.pact.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Minimal Spring Boot application entry point for Pact provider verification tests.
 *
 * <p>This class exists solely to satisfy {@code @SpringBootTest}'s requirement for a
 * {@code @SpringBootApplication} class on the classpath. It is test-scoped and not published as
 * part of the library artifact.
 */
@SpringBootApplication(
    scanBasePackages = {
      "org.lance.namespace.server.springboot",
      "org.lance.namespace.pact.provider"
    })
public class PactTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(PactTestApplication.class, args);
  }
}
