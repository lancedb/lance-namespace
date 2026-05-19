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

/** FragmentSummary */
@JsonPropertyOrder({
  FragmentSummary.JSON_PROPERTY_MIN,
  FragmentSummary.JSON_PROPERTY_MAX,
  FragmentSummary.JSON_PROPERTY_MEAN,
  FragmentSummary.JSON_PROPERTY_P25,
  FragmentSummary.JSON_PROPERTY_P50,
  FragmentSummary.JSON_PROPERTY_P75,
  FragmentSummary.JSON_PROPERTY_P99
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class FragmentSummary {
  public static final String JSON_PROPERTY_MIN = "min";
  @javax.annotation.Nonnull private Long min;

  public static final String JSON_PROPERTY_MAX = "max";
  @javax.annotation.Nonnull private Long max;

  public static final String JSON_PROPERTY_MEAN = "mean";
  @javax.annotation.Nonnull private Long mean;

  public static final String JSON_PROPERTY_P25 = "p25";
  @javax.annotation.Nonnull private Long p25;

  public static final String JSON_PROPERTY_P50 = "p50";
  @javax.annotation.Nonnull private Long p50;

  public static final String JSON_PROPERTY_P75 = "p75";
  @javax.annotation.Nonnull private Long p75;

  public static final String JSON_PROPERTY_P99 = "p99";
  @javax.annotation.Nonnull private Long p99;

  public FragmentSummary() {}

  public FragmentSummary min(@javax.annotation.Nonnull Long min) {

    this.min = min;
    return this;
  }

  /**
   * Get min minimum: 0
   *
   * @return min
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getMin() {
    return min;
  }

  @JsonProperty(JSON_PROPERTY_MIN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMin(@javax.annotation.Nonnull Long min) {
    this.min = min;
  }

  public FragmentSummary max(@javax.annotation.Nonnull Long max) {

    this.max = max;
    return this;
  }

  /**
   * Get max minimum: 0
   *
   * @return max
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MAX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getMax() {
    return max;
  }

  @JsonProperty(JSON_PROPERTY_MAX)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMax(@javax.annotation.Nonnull Long max) {
    this.max = max;
  }

  public FragmentSummary mean(@javax.annotation.Nonnull Long mean) {

    this.mean = mean;
    return this;
  }

  /**
   * Get mean minimum: 0
   *
   * @return mean
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MEAN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getMean() {
    return mean;
  }

  @JsonProperty(JSON_PROPERTY_MEAN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMean(@javax.annotation.Nonnull Long mean) {
    this.mean = mean;
  }

  public FragmentSummary p25(@javax.annotation.Nonnull Long p25) {

    this.p25 = p25;
    return this;
  }

  /**
   * Get p25 minimum: 0
   *
   * @return p25
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_P25)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getP25() {
    return p25;
  }

  @JsonProperty(JSON_PROPERTY_P25)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setP25(@javax.annotation.Nonnull Long p25) {
    this.p25 = p25;
  }

  public FragmentSummary p50(@javax.annotation.Nonnull Long p50) {

    this.p50 = p50;
    return this;
  }

  /**
   * Get p50 minimum: 0
   *
   * @return p50
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_P50)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getP50() {
    return p50;
  }

  @JsonProperty(JSON_PROPERTY_P50)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setP50(@javax.annotation.Nonnull Long p50) {
    this.p50 = p50;
  }

  public FragmentSummary p75(@javax.annotation.Nonnull Long p75) {

    this.p75 = p75;
    return this;
  }

  /**
   * Get p75 minimum: 0
   *
   * @return p75
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_P75)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getP75() {
    return p75;
  }

  @JsonProperty(JSON_PROPERTY_P75)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setP75(@javax.annotation.Nonnull Long p75) {
    this.p75 = p75;
  }

  public FragmentSummary p99(@javax.annotation.Nonnull Long p99) {

    this.p99 = p99;
    return this;
  }

  /**
   * Get p99 minimum: 0
   *
   * @return p99
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_P99)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getP99() {
    return p99;
  }

  @JsonProperty(JSON_PROPERTY_P99)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setP99(@javax.annotation.Nonnull Long p99) {
    this.p99 = p99;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FragmentSummary fragmentSummary = (FragmentSummary) o;
    return Objects.equals(this.min, fragmentSummary.min)
        && Objects.equals(this.max, fragmentSummary.max)
        && Objects.equals(this.mean, fragmentSummary.mean)
        && Objects.equals(this.p25, fragmentSummary.p25)
        && Objects.equals(this.p50, fragmentSummary.p50)
        && Objects.equals(this.p75, fragmentSummary.p75)
        && Objects.equals(this.p99, fragmentSummary.p99);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max, mean, p25, p50, p75, p99);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FragmentSummary {\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    mean: ").append(toIndentedString(mean)).append("\n");
    sb.append("    p25: ").append(toIndentedString(p25)).append("\n");
    sb.append("    p50: ").append(toIndentedString(p50)).append("\n");
    sb.append("    p75: ").append(toIndentedString(p75)).append("\n");
    sb.append("    p99: ").append(toIndentedString(p99)).append("\n");
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

    // add `min` to the URL query string
    if (getMin() != null) {
      try {
        joiner.add(
            String.format(
                "%smin%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMin()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `max` to the URL query string
    if (getMax() != null) {
      try {
        joiner.add(
            String.format(
                "%smax%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMax()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `mean` to the URL query string
    if (getMean() != null) {
      try {
        joiner.add(
            String.format(
                "%smean%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMean()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `p25` to the URL query string
    if (getP25() != null) {
      try {
        joiner.add(
            String.format(
                "%sp25%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getP25()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `p50` to the URL query string
    if (getP50() != null) {
      try {
        joiner.add(
            String.format(
                "%sp50%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getP50()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `p75` to the URL query string
    if (getP75() != null) {
      try {
        joiner.add(
            String.format(
                "%sp75%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getP75()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `p99` to the URL query string
    if (getP99() != null) {
      try {
        joiner.add(
            String.format(
                "%sp99%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getP99()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
