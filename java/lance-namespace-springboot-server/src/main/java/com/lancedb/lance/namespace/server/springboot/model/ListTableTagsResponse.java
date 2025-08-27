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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** ListTableTagsResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ListTableTagsResponse {

  @Valid private Map<String, TagContents> tags = new HashMap<>();

  public ListTableTagsResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ListTableTagsResponse(Map<String, TagContents> tags) {
    this.tags = tags;
  }

  public ListTableTagsResponse tags(Map<String, TagContents> tags) {
    this.tags = tags;
    return this;
  }

  public ListTableTagsResponse putTagsItem(String key, TagContents tagsItem) {
    if (this.tags == null) {
      this.tags = new HashMap<>();
    }
    this.tags.put(key, tagsItem);
    return this;
  }

  /**
   * Map of tag names to their contents
   *
   * @return tags
   */
  @NotNull
  @Valid
  @Schema(
      name = "tags",
      description = "Map of tag names to their contents",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tags")
  public Map<String, TagContents> getTags() {
    return tags;
  }

  public void setTags(Map<String, TagContents> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListTableTagsResponse listTableTagsResponse = (ListTableTagsResponse) o;
    return Objects.equals(this.tags, listTableTagsResponse.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTableTagsResponse {\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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
