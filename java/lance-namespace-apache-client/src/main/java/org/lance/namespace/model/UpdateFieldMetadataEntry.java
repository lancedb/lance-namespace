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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** UpdateFieldMetadataEntry */
@JsonPropertyOrder({
  UpdateFieldMetadataEntry.JSON_PROPERTY_PATH,
  UpdateFieldMetadataEntry.JSON_PROPERTY_METADATA,
  UpdateFieldMetadataEntry.JSON_PROPERTY_REPLACE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateFieldMetadataEntry {
  public static final String JSON_PROPERTY_PATH = "path";
  @javax.annotation.Nonnull private String path;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nonnull private Map<String, String> metadata = new HashMap<>();

  public static final String JSON_PROPERTY_REPLACE = "replace";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> replace = JsonNullable.<Boolean>undefined();

  public UpdateFieldMetadataEntry() {}

  public UpdateFieldMetadataEntry path(@javax.annotation.Nonnull String path) {

    this.path = path;
    return this;
  }

  /**
   * Field (column) path whose metadata to update
   *
   * @return path
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getPath() {
    return path;
  }

  @JsonProperty(JSON_PROPERTY_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPath(@javax.annotation.Nonnull String path) {
    this.path = path;
  }

  public UpdateFieldMetadataEntry metadata(@javax.annotation.Nonnull Map<String, String> metadata) {

    this.metadata = metadata;
    return this;
  }

  public UpdateFieldMetadataEntry putMetadataItem(String key, String metadataItem) {
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Metadata key-value pairs to apply to the field. A null value deletes that key.
   *
   * @return metadata
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.ALWAYS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.ALWAYS)
  public void setMetadata(@javax.annotation.Nonnull Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public UpdateFieldMetadataEntry replace(@javax.annotation.Nullable Boolean replace) {
    this.replace = JsonNullable.<Boolean>of(replace);

    return this;
  }

  /**
   * If true, replace the field&#39;s existing metadata entirely; otherwise merge into it (optional,
   * defaults to false).
   *
   * @return replace
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getReplace() {
    return replace.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_REPLACE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getReplace_JsonNullable() {
    return replace;
  }

  @JsonProperty(JSON_PROPERTY_REPLACE)
  public void setReplace_JsonNullable(JsonNullable<Boolean> replace) {
    this.replace = replace;
  }

  public void setReplace(@javax.annotation.Nullable Boolean replace) {
    this.replace = JsonNullable.<Boolean>of(replace);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateFieldMetadataEntry updateFieldMetadataEntry = (UpdateFieldMetadataEntry) o;
    return Objects.equals(this.path, updateFieldMetadataEntry.path)
        && Objects.equals(this.metadata, updateFieldMetadataEntry.metadata)
        && equalsNullable(this.replace, updateFieldMetadataEntry.replace);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, metadata, hashCodeNullable(replace));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFieldMetadataEntry {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    replace: ").append(toIndentedString(replace)).append("\n");
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

    // add `path` to the URL query string
    if (getPath() != null) {
      try {
        joiner.add(
            String.format(
                "%spath%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPath()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%smetadata%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getMetadata().get(_key),
                  URLEncoder.encode(String.valueOf(getMetadata().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `replace` to the URL query string
    if (getReplace() != null) {
      try {
        joiner.add(
            String.format(
                "%sreplace%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getReplace()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
