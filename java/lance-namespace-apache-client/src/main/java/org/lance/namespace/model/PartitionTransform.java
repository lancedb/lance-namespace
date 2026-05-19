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
import java.util.Objects;
import java.util.StringJoiner;

/** Well-known partition transform */
@JsonPropertyOrder({
  PartitionTransform.JSON_PROPERTY_TYPE,
  PartitionTransform.JSON_PROPERTY_NUM_BUCKETS,
  PartitionTransform.JSON_PROPERTY_WIDTH
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class PartitionTransform {
  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nonnull private String type;

  public static final String JSON_PROPERTY_NUM_BUCKETS = "num_buckets";
  @javax.annotation.Nullable private Integer numBuckets;

  public static final String JSON_PROPERTY_WIDTH = "width";
  @javax.annotation.Nullable private Integer width;

  public PartitionTransform() {}

  public PartitionTransform type(@javax.annotation.Nonnull String type) {

    this.type = type;
    return this;
  }

  /**
   * Transform type (identity, year, month, day, hour, bucket, multi_bucket, truncate)
   *
   * @return type
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(@javax.annotation.Nonnull String type) {
    this.type = type;
  }

  public PartitionTransform numBuckets(@javax.annotation.Nullable Integer numBuckets) {

    this.numBuckets = numBuckets;
    return this;
  }

  /**
   * Number of buckets for bucket transforms
   *
   * @return numBuckets
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_BUCKETS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNumBuckets() {
    return numBuckets;
  }

  @JsonProperty(JSON_PROPERTY_NUM_BUCKETS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumBuckets(@javax.annotation.Nullable Integer numBuckets) {
    this.numBuckets = numBuckets;
  }

  public PartitionTransform width(@javax.annotation.Nullable Integer width) {

    this.width = width;
    return this;
  }

  /**
   * Truncation width for truncate transforms
   *
   * @return width
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WIDTH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getWidth() {
    return width;
  }

  @JsonProperty(JSON_PROPERTY_WIDTH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWidth(@javax.annotation.Nullable Integer width) {
    this.width = width;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartitionTransform partitionTransform = (PartitionTransform) o;
    return Objects.equals(this.type, partitionTransform.type)
        && Objects.equals(this.numBuckets, partitionTransform.numBuckets)
        && Objects.equals(this.width, partitionTransform.width);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, numBuckets, width);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartitionTransform {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    numBuckets: ").append(toIndentedString(numBuckets)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
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

    // add `num_buckets` to the URL query string
    if (getNumBuckets() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_buckets%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumBuckets()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `width` to the URL query string
    if (getWidth() != null) {
      try {
        joiner.add(
            String.format(
                "%swidth%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWidth()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
