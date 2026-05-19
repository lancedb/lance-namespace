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
import java.util.Objects;
import java.util.StringJoiner;

/** AddColumnsEntry */
@JsonPropertyOrder({
  AddColumnsEntry.JSON_PROPERTY_NAME,
  AddColumnsEntry.JSON_PROPERTY_EXPRESSION,
  AddColumnsEntry.JSON_PROPERTY_VIRTUAL_COLUMN
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AddColumnsEntry {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_EXPRESSION = "expression";

  @javax.annotation.Nullable
  private JsonNullable<String> expression = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_VIRTUAL_COLUMN = "virtual_column";

  @javax.annotation.Nullable
  private JsonNullable<AddVirtualColumnEntry> virtualColumn =
      JsonNullable.<AddVirtualColumnEntry>undefined();

  public AddColumnsEntry() {}

  public AddColumnsEntry name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * Name of the new column
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

  public AddColumnsEntry expression(@javax.annotation.Nullable String expression) {
    this.expression = JsonNullable.<String>of(expression);

    return this;
  }

  /**
   * SQL expression for the column (optional if virtual_column is specified)
   *
   * @return expression
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getExpression() {
    return expression.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_EXPRESSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getExpression_JsonNullable() {
    return expression;
  }

  @JsonProperty(JSON_PROPERTY_EXPRESSION)
  public void setExpression_JsonNullable(JsonNullable<String> expression) {
    this.expression = expression;
  }

  public void setExpression(@javax.annotation.Nullable String expression) {
    this.expression = JsonNullable.<String>of(expression);
  }

  public AddColumnsEntry virtualColumn(
      @javax.annotation.Nullable AddVirtualColumnEntry virtualColumn) {
    this.virtualColumn = JsonNullable.<AddVirtualColumnEntry>of(virtualColumn);

    return this;
  }

  /**
   * Get virtualColumn
   *
   * @return virtualColumn
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public AddVirtualColumnEntry getVirtualColumn() {
    return virtualColumn.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_VIRTUAL_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<AddVirtualColumnEntry> getVirtualColumn_JsonNullable() {
    return virtualColumn;
  }

  @JsonProperty(JSON_PROPERTY_VIRTUAL_COLUMN)
  public void setVirtualColumn_JsonNullable(JsonNullable<AddVirtualColumnEntry> virtualColumn) {
    this.virtualColumn = virtualColumn;
  }

  public void setVirtualColumn(@javax.annotation.Nullable AddVirtualColumnEntry virtualColumn) {
    this.virtualColumn = JsonNullable.<AddVirtualColumnEntry>of(virtualColumn);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddColumnsEntry addColumnsEntry = (AddColumnsEntry) o;
    return Objects.equals(this.name, addColumnsEntry.name)
        && equalsNullable(this.expression, addColumnsEntry.expression)
        && equalsNullable(this.virtualColumn, addColumnsEntry.virtualColumn);
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
    return Objects.hash(name, hashCodeNullable(expression), hashCodeNullable(virtualColumn));
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
    sb.append("class AddColumnsEntry {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
    sb.append("    virtualColumn: ").append(toIndentedString(virtualColumn)).append("\n");
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

    // add `expression` to the URL query string
    if (getExpression() != null) {
      try {
        joiner.add(
            String.format(
                "%sexpression%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getExpression()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `virtual_column` to the URL query string
    if (getVirtualColumn() != null) {
      joiner.add(getVirtualColumn().toUrlQueryString(prefix + "virtual_column" + suffix));
    }

    return joiner.toString();
  }
}
