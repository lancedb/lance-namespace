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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** Request for creating an empty table. */
@JsonPropertyOrder({
  CreateEmptyTableRequest.JSON_PROPERTY_ID,
  CreateEmptyTableRequest.JSON_PROPERTY_LOCATION,
  CreateEmptyTableRequest.JSON_PROPERTY_PROPERTIES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateEmptyTableRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nullable private String location;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public CreateEmptyTableRequest() {}

  public CreateEmptyTableRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public CreateEmptyTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public CreateEmptyTableRequest location(@javax.annotation.Nullable String location) {

    this.location = location;
    return this;
  }

  /**
   * Optional storage location for the table. If not provided, the namespace implementation should
   * determine the table location.
   *
   * @return location
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getLocation() {
    return location;
  }

  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocation(@javax.annotation.Nullable String location) {
    this.location = location;
  }

  public CreateEmptyTableRequest properties(
      @javax.annotation.Nullable Map<String, String> properties) {

    this.properties = properties;
    return this;
  }

  public CreateEmptyTableRequest putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getProperties() {
    return properties;
  }

  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(@javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateEmptyTableRequest createEmptyTableRequest = (CreateEmptyTableRequest) o;
    return Objects.equals(this.id, createEmptyTableRequest.id)
        && Objects.equals(this.location, createEmptyTableRequest.location)
        && Objects.equals(this.properties, createEmptyTableRequest.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, location, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateEmptyTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `location` to the URL query string
    if (getLocation() != null) {
      try {
        joiner.add(
            String.format(
                "%slocation%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLocation()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sproperties%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getProperties().get(_key),
                  URLEncoder.encode(String.valueOf(getProperties().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
