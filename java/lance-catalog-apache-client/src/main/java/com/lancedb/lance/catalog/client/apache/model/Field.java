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
package com.lancedb.lance.catalog.client.apache.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** Field */
@JsonPropertyOrder({
  Field.JSON_PROPERTY_TYPE,
  Field.JSON_PROPERTY_NAME,
  Field.JSON_PROPERTY_ID,
  Field.JSON_PROPERTY_PARENT_ID,
  Field.JSON_PROPERTY_LOGICAL_TYPE,
  Field.JSON_PROPERTY_NULLABLE,
  Field.JSON_PROPERTY_METADATA
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class Field {
  /** Gets or Sets type */
  public enum TypeEnum {
    PARENT(String.valueOf("PARENT")),

    REPEATED(String.valueOf("REPEATED")),

    LEAF(String.valueOf("LEAF"));

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nonnull private TypeEnum type;

  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nonnull private Integer id = null;

  public static final String JSON_PROPERTY_PARENT_ID = "parentId";
  @javax.annotation.Nullable private Integer parentId = null;

  public static final String JSON_PROPERTY_LOGICAL_TYPE = "logicalType";
  @javax.annotation.Nonnull private String logicalType;

  public static final String JSON_PROPERTY_NULLABLE = "nullable";
  @javax.annotation.Nullable private Boolean nullable;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public Field() {}

  public Field type(@javax.annotation.Nonnull TypeEnum type) {

    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public TypeEnum getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(@javax.annotation.Nonnull TypeEnum type) {
    this.type = type;
  }

  public Field name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(@javax.annotation.Nonnull String name) {
    this.name = name;
  }

  public Field id(@javax.annotation.Nonnull Integer id) {

    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@javax.annotation.Nonnull Integer id) {
    this.id = id;
  }

  public Field parentId(@javax.annotation.Nullable Integer parentId) {

    this.parentId = parentId;
    return this;
  }

  /**
   * Get parentId
   *
   * @return parentId
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PARENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getParentId() {
    return parentId;
  }

  @JsonProperty(JSON_PROPERTY_PARENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParentId(@javax.annotation.Nullable Integer parentId) {
    this.parentId = parentId;
  }

  public Field logicalType(@javax.annotation.Nonnull String logicalType) {

    this.logicalType = logicalType;
    return this;
  }

  /**
   * Logical types, currently support parameterized Arrow Type. PARENT types will always have
   * logical type \&quot;struct\&quot;. Logical type \&quot;map\&quot; is represented as a struct
   * with a single child field \&quot;entries\&quot;. \&quot;entries\&quot; is a struct with fields
   * \&quot;key\&quot; and \&quot;value\&quot;. REPEATED types may have logical types: *
   * \&quot;list\&quot; * \&quot;large_list\&quot; * \&quot;list.struct\&quot; *
   * \&quot;large_list.struct\&quot; The final two are used if the list values are structs, and
   * therefore the field is both implicitly REPEATED and PARENT. LEAF types may have logical types:
   * * \&quot;null\&quot; * \&quot;bool\&quot; * \&quot;int8\&quot; / \&quot;uint8\&quot; *
   * \&quot;int16\&quot; / \&quot;uint16\&quot; * \&quot;int32\&quot; / \&quot;uint32\&quot; *
   * \&quot;int64\&quot; / \&quot;uint64\&quot; * \&quot;halffloat\&quot; / \&quot;float\&quot; /
   * \&quot;double\&quot; * \&quot;string\&quot; / \&quot;large_string\&quot; * \&quot;binary\&quot;
   * / \&quot;large_binary\&quot; * \&quot;date32:day\&quot; * \&quot;date64:ms\&quot; *
   * \&quot;decimal:128:{precision}:{scale}\&quot; / \&quot;decimal:256:{precision}:{scale}\&quot; *
   * \&quot;time:{unit}\&quot; / \&quot;timestamp:{unit}\&quot; / \&quot;duration:{unit}\&quot;,
   * where unit is \&quot;s\&quot;, \&quot;ms\&quot;, \&quot;us\&quot;, \&quot;ns\&quot; *
   * \&quot;dict:{value_type}:{index_type}:false\&quot;
   *
   * @return logicalType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LOGICAL_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getLogicalType() {
    return logicalType;
  }

  @JsonProperty(JSON_PROPERTY_LOGICAL_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLogicalType(@javax.annotation.Nonnull String logicalType) {
    this.logicalType = logicalType;
  }

  public Field nullable(@javax.annotation.Nullable Boolean nullable) {

    this.nullable = nullable;
    return this;
  }

  /**
   * Get nullable
   *
   * @return nullable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getNullable() {
    return nullable;
  }

  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNullable(@javax.annotation.Nullable Boolean nullable) {
    this.nullable = nullable;
  }

  public Field metadata(@javax.annotation.Nullable Map<String, String> metadata) {

    this.metadata = metadata;
    return this;
  }

  public Field putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * optional field metadata (e.g. extension type name/parameters)
   *
   * @return metadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(@javax.annotation.Nullable Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Field field = (Field) o;
    return Objects.equals(this.type, field.type)
        && Objects.equals(this.name, field.name)
        && Objects.equals(this.id, field.id)
        && Objects.equals(this.parentId, field.parentId)
        && Objects.equals(this.logicalType, field.logicalType)
        && Objects.equals(this.nullable, field.nullable)
        && Objects.equals(this.metadata, field.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, id, parentId, logicalType, nullable, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Field {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    logicalType: ").append(toIndentedString(logicalType)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

    // add `type` to the URL query string
    if (getType() != null) {
      try {
        joiner.add(
            String.format(
                "%stype%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getType()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `name` to the URL query string
    if (getName() != null) {
      try {
        joiner.add(
            String.format(
                "%sname%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `id` to the URL query string
    if (getId() != null) {
      try {
        joiner.add(
            String.format(
                "%sid%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getId()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `parentId` to the URL query string
    if (getParentId() != null) {
      try {
        joiner.add(
            String.format(
                "%sparentId%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getParentId()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `logicalType` to the URL query string
    if (getLogicalType() != null) {
      try {
        joiner.add(
            String.format(
                "%slogicalType%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLogicalType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `nullable` to the URL query string
    if (getNullable() != null) {
      try {
        joiner.add(
            String.format(
                "%snullable%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNullable()), "UTF-8")
                    .replaceAll("\\+", "%20")));
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

    return joiner.toString();
  }
}
