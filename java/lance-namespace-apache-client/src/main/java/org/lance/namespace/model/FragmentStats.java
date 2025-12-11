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

/** FragmentStats */
@JsonPropertyOrder({
  FragmentStats.JSON_PROPERTY_NUM_FRAGMENTS,
  FragmentStats.JSON_PROPERTY_NUM_SMALL_FRAGMENTS,
  FragmentStats.JSON_PROPERTY_LENGTHS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class FragmentStats {
  public static final String JSON_PROPERTY_NUM_FRAGMENTS = "num_fragments";
  @javax.annotation.Nonnull private Long numFragments;

  public static final String JSON_PROPERTY_NUM_SMALL_FRAGMENTS = "num_small_fragments";
  @javax.annotation.Nonnull private Long numSmallFragments;

  public static final String JSON_PROPERTY_LENGTHS = "lengths";
  @javax.annotation.Nonnull private FragmentSummary lengths;

  public FragmentStats() {}

  public FragmentStats numFragments(@javax.annotation.Nonnull Long numFragments) {

    this.numFragments = numFragments;
    return this;
  }

  /**
   * The number of fragments in the table minimum: 0
   *
   * @return numFragments
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumFragments() {
    return numFragments;
  }

  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumFragments(@javax.annotation.Nonnull Long numFragments) {
    this.numFragments = numFragments;
  }

  public FragmentStats numSmallFragments(@javax.annotation.Nonnull Long numSmallFragments) {

    this.numSmallFragments = numSmallFragments;
    return this;
  }

  /**
   * The number of uncompacted fragments in the table minimum: 0
   *
   * @return numSmallFragments
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_SMALL_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumSmallFragments() {
    return numSmallFragments;
  }

  @JsonProperty(JSON_PROPERTY_NUM_SMALL_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumSmallFragments(@javax.annotation.Nonnull Long numSmallFragments) {
    this.numSmallFragments = numSmallFragments;
  }

  public FragmentStats lengths(@javax.annotation.Nonnull FragmentSummary lengths) {

    this.lengths = lengths;
    return this;
  }

  /**
   * Statistics on the number of rows in the table fragments
   *
   * @return lengths
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LENGTHS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public FragmentSummary getLengths() {
    return lengths;
  }

  @JsonProperty(JSON_PROPERTY_LENGTHS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLengths(@javax.annotation.Nonnull FragmentSummary lengths) {
    this.lengths = lengths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FragmentStats fragmentStats = (FragmentStats) o;
    return Objects.equals(this.numFragments, fragmentStats.numFragments)
        && Objects.equals(this.numSmallFragments, fragmentStats.numSmallFragments)
        && Objects.equals(this.lengths, fragmentStats.lengths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numFragments, numSmallFragments, lengths);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FragmentStats {\n");
    sb.append("    numFragments: ").append(toIndentedString(numFragments)).append("\n");
    sb.append("    numSmallFragments: ").append(toIndentedString(numSmallFragments)).append("\n");
    sb.append("    lengths: ").append(toIndentedString(lengths)).append("\n");
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

    // add `num_fragments` to the URL query string
    if (getNumFragments() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_fragments%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumFragments()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_small_fragments` to the URL query string
    if (getNumSmallFragments() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_small_fragments%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumSmallFragments()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `lengths` to the URL query string
    if (getLengths() != null) {
      joiner.add(getLengths().toUrlQueryString(prefix + "lengths" + suffix));
    }

    return joiner.toString();
  }
}
