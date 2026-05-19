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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** RefreshMaterializedViewRequest */
@JsonPropertyOrder({
  RefreshMaterializedViewRequest.JSON_PROPERTY_IDENTITY,
  RefreshMaterializedViewRequest.JSON_PROPERTY_ID,
  RefreshMaterializedViewRequest.JSON_PROPERTY_SRC_VERSION,
  RefreshMaterializedViewRequest.JSON_PROPERTY_MAX_ROWS_PER_FRAGMENT,
  RefreshMaterializedViewRequest.JSON_PROPERTY_CONCURRENCY,
  RefreshMaterializedViewRequest.JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY,
  RefreshMaterializedViewRequest.JSON_PROPERTY_CLUSTER,
  RefreshMaterializedViewRequest.JSON_PROPERTY_OUTPUT_LIMIT,
  RefreshMaterializedViewRequest.JSON_PROPERTY_MANIFEST
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class RefreshMaterializedViewRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_SRC_VERSION = "src_version";

  @javax.annotation.Nullable
  private JsonNullable<Integer> srcVersion = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_MAX_ROWS_PER_FRAGMENT = "max_rows_per_fragment";

  @javax.annotation.Nullable
  private JsonNullable<Integer> maxRowsPerFragment = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_CONCURRENCY = "concurrency";

  @javax.annotation.Nullable
  private JsonNullable<Integer> concurrency = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY = "intra_applier_concurrency";

  @javax.annotation.Nullable
  private JsonNullable<Integer> intraApplierConcurrency = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_CLUSTER = "cluster";

  @javax.annotation.Nullable
  private JsonNullable<String> cluster = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_OUTPUT_LIMIT = "output_limit";

  @javax.annotation.Nullable
  private JsonNullable<Integer> outputLimit = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_MANIFEST = "manifest";

  @javax.annotation.Nullable
  private JsonNullable<String> manifest = JsonNullable.<String>undefined();

  public RefreshMaterializedViewRequest() {}

  public RefreshMaterializedViewRequest identity(@javax.annotation.Nullable Identity identity) {

    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public RefreshMaterializedViewRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public RefreshMaterializedViewRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Table identifier path (namespace + table name)
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

  public RefreshMaterializedViewRequest srcVersion(@javax.annotation.Nullable Integer srcVersion) {
    this.srcVersion = JsonNullable.<Integer>of(srcVersion);

    return this;
  }

  /**
   * Optional source version to refresh from
   *
   * @return srcVersion
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getSrcVersion() {
    return srcVersion.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_SRC_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getSrcVersion_JsonNullable() {
    return srcVersion;
  }

  @JsonProperty(JSON_PROPERTY_SRC_VERSION)
  public void setSrcVersion_JsonNullable(JsonNullable<Integer> srcVersion) {
    this.srcVersion = srcVersion;
  }

  public void setSrcVersion(@javax.annotation.Nullable Integer srcVersion) {
    this.srcVersion = JsonNullable.<Integer>of(srcVersion);
  }

  public RefreshMaterializedViewRequest maxRowsPerFragment(
      @javax.annotation.Nullable Integer maxRowsPerFragment) {
    this.maxRowsPerFragment = JsonNullable.<Integer>of(maxRowsPerFragment);

    return this;
  }

  /**
   * Optional maximum rows per fragment
   *
   * @return maxRowsPerFragment
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getMaxRowsPerFragment() {
    return maxRowsPerFragment.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MAX_ROWS_PER_FRAGMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getMaxRowsPerFragment_JsonNullable() {
    return maxRowsPerFragment;
  }

  @JsonProperty(JSON_PROPERTY_MAX_ROWS_PER_FRAGMENT)
  public void setMaxRowsPerFragment_JsonNullable(JsonNullable<Integer> maxRowsPerFragment) {
    this.maxRowsPerFragment = maxRowsPerFragment;
  }

  public void setMaxRowsPerFragment(@javax.annotation.Nullable Integer maxRowsPerFragment) {
    this.maxRowsPerFragment = JsonNullable.<Integer>of(maxRowsPerFragment);
  }

  public RefreshMaterializedViewRequest concurrency(
      @javax.annotation.Nullable Integer concurrency) {
    this.concurrency = JsonNullable.<Integer>of(concurrency);

    return this;
  }

  /**
   * Optional concurrency override
   *
   * @return concurrency
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getConcurrency() {
    return concurrency.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_CONCURRENCY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getConcurrency_JsonNullable() {
    return concurrency;
  }

  @JsonProperty(JSON_PROPERTY_CONCURRENCY)
  public void setConcurrency_JsonNullable(JsonNullable<Integer> concurrency) {
    this.concurrency = concurrency;
  }

  public void setConcurrency(@javax.annotation.Nullable Integer concurrency) {
    this.concurrency = JsonNullable.<Integer>of(concurrency);
  }

  public RefreshMaterializedViewRequest intraApplierConcurrency(
      @javax.annotation.Nullable Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = JsonNullable.<Integer>of(intraApplierConcurrency);

    return this;
  }

  /**
   * Optional intra-applier concurrency override
   *
   * @return intraApplierConcurrency
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getIntraApplierConcurrency() {
    return intraApplierConcurrency.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getIntraApplierConcurrency_JsonNullable() {
    return intraApplierConcurrency;
  }

  @JsonProperty(JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY)
  public void setIntraApplierConcurrency_JsonNullable(
      JsonNullable<Integer> intraApplierConcurrency) {
    this.intraApplierConcurrency = intraApplierConcurrency;
  }

  public void setIntraApplierConcurrency(
      @javax.annotation.Nullable Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = JsonNullable.<Integer>of(intraApplierConcurrency);
  }

  public RefreshMaterializedViewRequest cluster(@javax.annotation.Nullable String cluster) {
    this.cluster = JsonNullable.<String>of(cluster);

    return this;
  }

  /**
   * Optional cluster name (operational override)
   *
   * @return cluster
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getCluster() {
    return cluster.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_CLUSTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getCluster_JsonNullable() {
    return cluster;
  }

  @JsonProperty(JSON_PROPERTY_CLUSTER)
  public void setCluster_JsonNullable(JsonNullable<String> cluster) {
    this.cluster = cluster;
  }

  public void setCluster(@javax.annotation.Nullable String cluster) {
    this.cluster = JsonNullable.<String>of(cluster);
  }

  public RefreshMaterializedViewRequest outputLimit(
      @javax.annotation.Nullable Integer outputLimit) {
    this.outputLimit = JsonNullable.<Integer>of(outputLimit);

    return this;
  }

  /**
   * Post-trim cap on view row count after expansion. Valid only for chunker materialized views;
   * returns 400 if set on other kinds.
   *
   * @return outputLimit
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getOutputLimit() {
    return outputLimit.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_OUTPUT_LIMIT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getOutputLimit_JsonNullable() {
    return outputLimit;
  }

  @JsonProperty(JSON_PROPERTY_OUTPUT_LIMIT)
  public void setOutputLimit_JsonNullable(JsonNullable<Integer> outputLimit) {
    this.outputLimit = outputLimit;
  }

  public void setOutputLimit(@javax.annotation.Nullable Integer outputLimit) {
    this.outputLimit = JsonNullable.<Integer>of(outputLimit);
  }

  public RefreshMaterializedViewRequest manifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);

    return this;
  }

  /**
   * Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only;
   * does not mutate the view&#39;s snapshotted manifest. When omitted, the manifest stored in the
   * view&#39;s metadata is used.
   *
   * @return manifest
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getManifest() {
    return manifest.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getManifest_JsonNullable() {
    return manifest;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST)
  public void setManifest_JsonNullable(JsonNullable<String> manifest) {
    this.manifest = manifest;
  }

  public void setManifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefreshMaterializedViewRequest refreshMaterializedViewRequest =
        (RefreshMaterializedViewRequest) o;
    return Objects.equals(this.identity, refreshMaterializedViewRequest.identity)
        && Objects.equals(this.id, refreshMaterializedViewRequest.id)
        && equalsNullable(this.srcVersion, refreshMaterializedViewRequest.srcVersion)
        && equalsNullable(
            this.maxRowsPerFragment, refreshMaterializedViewRequest.maxRowsPerFragment)
        && equalsNullable(this.concurrency, refreshMaterializedViewRequest.concurrency)
        && equalsNullable(
            this.intraApplierConcurrency, refreshMaterializedViewRequest.intraApplierConcurrency)
        && equalsNullable(this.cluster, refreshMaterializedViewRequest.cluster)
        && equalsNullable(this.outputLimit, refreshMaterializedViewRequest.outputLimit)
        && equalsNullable(this.manifest, refreshMaterializedViewRequest.manifest);
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
    return Objects.hash(
        identity,
        id,
        hashCodeNullable(srcVersion),
        hashCodeNullable(maxRowsPerFragment),
        hashCodeNullable(concurrency),
        hashCodeNullable(intraApplierConcurrency),
        hashCodeNullable(cluster),
        hashCodeNullable(outputLimit),
        hashCodeNullable(manifest));
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
    sb.append("class RefreshMaterializedViewRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    srcVersion: ").append(toIndentedString(srcVersion)).append("\n");
    sb.append("    maxRowsPerFragment: ").append(toIndentedString(maxRowsPerFragment)).append("\n");
    sb.append("    concurrency: ").append(toIndentedString(concurrency)).append("\n");
    sb.append("    intraApplierConcurrency: ")
        .append(toIndentedString(intraApplierConcurrency))
        .append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    outputLimit: ").append(toIndentedString(outputLimit)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

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

    // add `src_version` to the URL query string
    if (getSrcVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%ssrc_version%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getSrcVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `max_rows_per_fragment` to the URL query string
    if (getMaxRowsPerFragment() != null) {
      try {
        joiner.add(
            String.format(
                "%smax_rows_per_fragment%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMaxRowsPerFragment()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `concurrency` to the URL query string
    if (getConcurrency() != null) {
      try {
        joiner.add(
            String.format(
                "%sconcurrency%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getConcurrency()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `intra_applier_concurrency` to the URL query string
    if (getIntraApplierConcurrency() != null) {
      try {
        joiner.add(
            String.format(
                "%sintra_applier_concurrency%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIntraApplierConcurrency()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `cluster` to the URL query string
    if (getCluster() != null) {
      try {
        joiner.add(
            String.format(
                "%scluster%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getCluster()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `output_limit` to the URL query string
    if (getOutputLimit() != null) {
      try {
        joiner.add(
            String.format(
                "%soutput_limit%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getOutputLimit()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `manifest` to the URL query string
    if (getManifest() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifest%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifest()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
