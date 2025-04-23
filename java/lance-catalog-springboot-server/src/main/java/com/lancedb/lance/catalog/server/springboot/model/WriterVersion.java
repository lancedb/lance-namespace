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
package com.lancedb.lance.catalog.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** WriterVersion */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class WriterVersion {

  private String library;

  private String version;

  public WriterVersion library(String library) {
    this.library = library;
    return this;
  }

  /**
   * The name of the library that created this file.
   *
   * @return library
   */
  @Schema(
      name = "library",
      description = "The name of the library that created this file.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("library")
  public String getLibrary() {
    return library;
  }

  public void setLibrary(String library) {
    this.library = library;
  }

  public WriterVersion version(String version) {
    this.version = version;
    return this;
  }

  /**
   * The version of the library that created this file. Because we cannot assume that the library is
   * semantically versioned, this is a string. However, if it is semantically versioned, it should
   * be a valid semver string without any 'v' prefix. For example: `2.0.0`, `2.0.0-rc.1`.
   *
   * @return version
   */
  @Schema(
      name = "version",
      description =
          "The version of the library that created this file.  Because we cannot assume that the library is semantically versioned, this is a string.  However, if it is semantically versioned, it should be a valid semver string without any 'v' prefix.  For example: `2.0.0`, `2.0.0-rc.1`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WriterVersion writerVersion = (WriterVersion) o;
    return Objects.equals(this.library, writerVersion.library)
        && Objects.equals(this.version, writerVersion.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(library, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WriterVersion {\n");
    sb.append("    library: ").append(toIndentedString(library)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
