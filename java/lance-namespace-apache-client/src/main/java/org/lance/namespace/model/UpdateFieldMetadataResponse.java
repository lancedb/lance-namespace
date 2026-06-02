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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** UpdateFieldMetadataResponse */
@JsonPropertyOrder({
  UpdateFieldMetadataResponse.JSON_PROPERTY_VERSION,
  UpdateFieldMetadataResponse.JSON_PROPERTY_FIELDS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateFieldMetadataResponse {
  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public static final String JSON_PROPERTY_FIELDS = "fields";
  @javax.annotation.Nullable private Map<String, Map<String, String>> fields = new HashMap<>();

  public UpdateFieldMetadataResponse() {}

  public UpdateFieldMetadataResponse version(@javax.annotation.Nonnull Long version) {

    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(@javax.annotation.Nonnull Long version) {
    this.version = version;
  }

  public UpdateFieldMetadataResponse fields(
      @javax.annotation.Nullable Map<String, Map<String, String>> fields) {

    this.fields = fields;
    return this;
  }

  public UpdateFieldMetadataResponse putFieldsItem(String key, Map<String, String> fieldsItem) {
    if (this.fields == null) {
      this.fields = new HashMap<>();
    }
    this.fields.put(key, fieldsItem);
    return this;
  }

  /**
   * Resulting metadata for each updated field, keyed by field path.
   *
   * @return fields
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, Map<String, String>> getFields() {
    return fields;
  }

  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFields(@javax.annotation.Nullable Map<String, Map<String, String>> fields) {
    this.fields = fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateFieldMetadataResponse updateFieldMetadataResponse = (UpdateFieldMetadataResponse) o;
    return Objects.equals(this.version, updateFieldMetadataResponse.version)
        && Objects.equals(this.fields, updateFieldMetadataResponse.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, fields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFieldMetadataResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `fields` to the URL query string
    if (getFields() != null) {
      for (String _key : getFields().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sfields%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getFields().get(_key),
                  URLEncoder.encode(String.valueOf(getFields().get(_key)), "UTF-8")
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
